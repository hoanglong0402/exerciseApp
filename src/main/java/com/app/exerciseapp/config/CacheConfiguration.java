package com.app.exerciseapp.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;
import org.hibernate.annotations.Cache;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    @Bean
    public Config redissonConfig(JHipsterProperties jHipsterProperties, @Value("${spring.redis.database:0}") int redisDatabaseIndex) {
        URI redisUri = URI.create(jHipsterProperties.getCache().getRedis().getServer()[0]);

        Config config = new Config();
        if (jHipsterProperties.getCache().getRedis().isCluster()) {
            String[] serverCluster = jHipsterProperties.getCache().getRedis().getServer();
            String masterAddress = serverCluster[0];
            String[] slaveAddress = Arrays.copyOfRange(serverCluster, 1, serverCluster.length);
            MasterSlaveServersConfig masterSlaveServersConfig = config
                .useMasterSlaveServers()
                .setMasterAddress(masterAddress)
                .setMasterConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .addSlaveAddress(slaveAddress)
                .setSlaveConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSlaveConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setDatabase(redisDatabaseIndex);

            if (redisUri.getUserInfo() != null) {
                masterSlaveServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .setAddress(jHipsterProperties.getCache().getRedis().getServer()[0])
                .setDatabase(redisDatabaseIndex);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        return config;
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(Config config) {
        return Redisson.create(config);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(RedissonClient redisson, JHipsterProperties jHipsterProperties) {
        return cm -> {
            javax.cache.configuration.Configuration<Object, Object> domainConfig = jcacheConfiguration(
                redisson,
                jHipsterProperties.getCache().getRedis().getExpiration()
            );
            createCustomizeCacheInPackage("com.app.exerciseapp.domain", cm, domainConfig);
        };
    }

    public static javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(
        RedissonClient redisson,
        int expirationInSecond
    ) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();
        jcacheConfig.setStatisticsEnabled(true);
        if (expirationInSecond > 0) {
            jcacheConfig.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, expirationInSecond)));
        } else {
            jcacheConfig.setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(Duration.ONE_HOUR));
        }
        return RedissonConfiguration.fromInstance(redisson, jcacheConfig);
    }

    public void createCustomizeCacheInPackage(
        String basePackage,
        javax.cache.CacheManager cm,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        // create scanner and disable default filters (that is the 'false' argument)
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        // add include filters which matches all the classes (or use your own)
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
        // get matching classes defined in the package
        final Set<BeanDefinition> classes = provider.findCandidateComponents(basePackage);
        // this is how you can load the class type from BeanDefinition instance
        for (BeanDefinition bean : classes) {
            try {
                Class<?> clazz = null;
                clazz = Class.forName(bean.getBeanClassName());
                createCustomizeCacheAnnotation(clazz, cm, jcacheConfiguration);
            } catch (ClassNotFoundException e) {
                continue;
            }
        }
    }

    public void createCustomizeCacheAnnotation(
        Class clazz,
        javax.cache.CacheManager cm,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        // For org.hibernate.annotations.Cache
        createForClass(clazz, Cache.class, cm, jcacheConfiguration);
        createForFieldInClass(clazz, Cache.class, cm, jcacheConfiguration);
        // For org.springframework.cache.annotation.Cacheable
        createForClass(clazz, Cacheable.class, cm, jcacheConfiguration);
        createForFieldInClass(clazz, Cacheable.class, cm, jcacheConfiguration);
    }

    private void createForClass(
        Class clazz,
        Class<? extends Annotation> annotation,
        javax.cache.CacheManager cm,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        Annotation cHibernateCacheAnn = clazz.getDeclaredAnnotation(annotation);
        if (cHibernateCacheAnn != null) {
            String cacheName = clazz.getName();
            createCache(cm, cacheName, jcacheConfiguration);
        }
    }

    private void createForFieldInClass(
        Class clazz,
        Class<? extends Annotation> annotation,
        javax.cache.CacheManager cm,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        for (Field f : clazz.getDeclaredFields()) {
            Annotation fHibernateCacheAnn = f.getDeclaredAnnotation(annotation);
            if (fHibernateCacheAnn != null) {
                String cacheName = clazz.getName() + "." + f.getName();
                createCache(cm, cacheName, jcacheConfiguration);
            }
        }
    }

    private void createCache(
        javax.cache.CacheManager cm,
        String cacheName,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
