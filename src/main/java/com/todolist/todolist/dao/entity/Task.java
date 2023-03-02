package com.todolist.todolist.dao.entity;

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


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
                .filter(sortType -> sortType.getId() == this.taskSortType)
                .findFirst()
                .orElseThrow(ExceptionUtil::exUnsupported);
    }

    public void setTaskSortType(TaskSortType taskSortType) {
        this.taskSortType = taskSortType.getId();
    }

    public TaskStatus getTaskStatus() {
        return Arrays.stream(TaskStatus.values())
                .filter(status -> status.getId() == this.taskStatus)
                .findFirst()
                .orElseThrow(ExceptionUtil::exUnsupported);
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus.getId();
    }

}
