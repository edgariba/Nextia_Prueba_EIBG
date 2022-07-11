package com.nextia.PruebaEb.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_users")
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "hash_user")
    public String hashUser;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "mofified_date")
    private Date modifiedDate;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public enum Role {
        SuperAdmin
    }

}