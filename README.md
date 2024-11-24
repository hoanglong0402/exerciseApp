1. Pre-requisites

Before running this application, ensure that:

Your computer has Docker installed.

2. Steps to Run

Start Docker Containers: To set up the required services (MySQL and Redis), run the following command in your terminal:  
docker compose -f <project-folder>\src\main\docker\app.yml -p exerciseapp up -d  
This command will start two containers (MySQL and Redis) on your local machine.

2. Run the Application

You can run the application with one of two existing profiles:

- Profile: dev - Uses MySQL running locally.

  - Database credentials:
  - Username: root
  - Password: (No password)

- Profile: prod - Uses MySQL hosted on AWS.
  - Database connection information:
  - URL: exerciseapp-1.czkeg0myaczf.ap-southeast-1.rds.amazonaws.com:3306
  - Username: admin
  - Password: 123123123

3. The application will run on: localhost:8081

4. Check Logs  
   The application's log file is saved in the following location:  
   <project-folder>/application.log  
   You can check this file for details about the application's behavior and runtime information.
