package com.app.exercise.domain;

import com.app.exercise.domain.enumeration.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TaskSequenceGenerator")
    @SequenceGenerator(name = "TaskSequenceGenerator", sequenceName = "task_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "assigned_to")
    private Long assignedTo;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    @NotNull
    @JsonIgnore
    private Project project;
}
