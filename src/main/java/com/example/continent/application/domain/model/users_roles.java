//package com.example.continent.application.domain.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.experimental.FieldDefaults;
//import org.hibernate.annotations.FilterDef;
//
//@Entity
//@Table(name="users_roles")
//@Data
//@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
//// This class represents a many-to-many relationship between users and roles.
//public class users_roles {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    private Long userId;
//    private Long roleId;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    User user;
//
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;
//
//}
