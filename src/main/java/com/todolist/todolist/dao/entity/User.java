package com.todolist.todolist.dao.entity;


import com.todolist.todolist.annotation.ValidPassword;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * TODO
 * It is better to override ToString and EqualsAndHashCode instead of Data annotation.
 * When we override these 2 methods, we override only the fields we need,
 * and thus future recursion is prevented.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ag_user")
public class User extends Auditable<User> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "user_sequence",
            sequenceName = "user_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    @Column(name = "id")
    @Comment("id")
    private Long id;

    @Column(name = "full_name")
    @Comment("tam adı")
    private String fullName;

    @Email
    @Column(name = "email")
    @Comment("email address-i")
    private String email;


    @ValidPassword
    @Column(name = "password")
    @Comment("şifrə")
    private String password;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    @Comment("tapşırıqlar")
    private List<Task> tasks;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getFullName(), user.getFullName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullName(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", regUserId=" + regUserId +
                ", regDate=" + regDate +
                ", editUserId=" + editUserId +
                ", editDate=" + editDate +
                ", inPower=" + inPower +
                '}';
    }
}
