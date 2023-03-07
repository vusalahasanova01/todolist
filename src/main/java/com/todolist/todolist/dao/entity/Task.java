package com.todolist.todolist.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todolist.todolist.model.TaskSortType;
import com.todolist.todolist.model.TaskStatus;
import com.todolist.todolist.util.ExceptionUtil;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ag_task")
@JsonIgnoreProperties({"user"})
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

    @Column(name = "task_sort_type")
    private Integer taskSortType;

    @Column(name = "task_status")
    private Integer taskStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "description")
    private String description;

    @Column(name = "task_create_date")
    private LocalDate taskCreateDate;

    @Column(name = "task_deadline_date")
    private LocalDate taskDeadlineDate;

    public TaskSortType getTaskSortType() {
        return Arrays.stream(TaskSortType.values())
                .filter(sortType -> Objects.equals(sortType.getId(), this.taskSortType))
                .findFirst()
                .orElse(TaskSortType.UNSUPPORTED);
    }

    public void setTaskSortType(TaskSortType taskSortType) {
        if (taskSortType != null) {
            this.taskSortType = taskSortType.getId();
        }
    }

    public TaskStatus getTaskStatus() {
        return Arrays.stream(TaskStatus.values())
                .filter(status -> Objects.equals(status.getId(), this.taskStatus))
                .findFirst()
                .orElse(TaskStatus.UNSUPPORTED);
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        if (taskStatus != null) {
            this.taskStatus = taskStatus.getId();
        }
    }

}
