package com.todolist.todolist.dao.entity;

import com.todolist.todolist.model.enums.TaskSortType;
import com.todolist.todolist.model.enums.TaskStatus;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ag_task")
public class Task extends Auditable<Task> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "task_sequence",
            sequenceName = "task_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "task_sequence")
    @Column(name = "id")
    @Comment("id")
    private Long id;

    @Column(name = "task_name")
    @Comment("tapşırıq adı")
    private String taskName;

    @Column(name = "photo")
    @Comment("tapşırığın hansı mövzuda olmasını ifadə edən şəkil")
    private byte[] photo;

    @Column(name = "description")
    @Comment("tapşırığın təsviri")
    private String description;

    @Column(name = "task_deadline_date")
    @Comment("tapşırığın bitmə tarixi")
    private LocalDate taskDeadlineDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_sort_type")
    @Comment("taskın filtirlənməsinin növü")
    private TaskSortType taskSortType;

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("istifadəçi")
    private User user;


    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    @Comment("tapşırığın statusu")
    private TaskStatus taskStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(getId(), task.getId()) && Objects.equals(getTaskName(), task.getTaskName()) && Arrays.equals(getPhoto(), task.getPhoto()) && Objects.equals(getDescription(), task.getDescription()) && Objects.equals(getTaskDeadlineDate(), task.getTaskDeadlineDate()) && getTaskSortType() == task.getTaskSortType() && getTaskStatus() == task.getTaskStatus();
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getTaskName(), getDescription(), getTaskDeadlineDate(), getTaskSortType(), getTaskStatus());
        result = 31 * result + Arrays.hashCode(getPhoto());
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", description='" + description + '\'' +
                ", taskStartDate='" + editDate +
                ", taskDeadlineDate=" + taskDeadlineDate +
                ", taskSortType=" + taskSortType +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
