package com.todolist.todolist.dao.entity;

import com.todolist.todolist.model.enums.TaskSortType;
import com.todolist.todolist.model.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ag_task")
public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "task_sequence",
            sequenceName = "task_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "task_sequence")
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "description")
    private String description;

    @Column(name = "task_create_date")
    private LocalDate taskCreateDate;
    @Column(name = "task_deadline_date")
    private LocalDate taskDeadlineDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_sort_type")
    private TaskSortType taskSortType;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;


}
