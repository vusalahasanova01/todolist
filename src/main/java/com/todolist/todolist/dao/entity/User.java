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


@Data
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
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Email
    @Column(name = "email")
    private String email;


    @ValidPassword
    @Column(name = "password")
    private String password;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    private List<Task> tasks;


}
