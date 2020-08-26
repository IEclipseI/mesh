package com.example.mesh_test_task.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(indexes = @Index(columnList = "created"), uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    private long age;

    @CreationTimestamp
    private Date created;
}
