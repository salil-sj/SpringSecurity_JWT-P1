package com.example.security.jwt.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="userdetails")
@Data
public class User
{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "uid")
private Integer id;
@Column(name="name")
private String userName;
private String password;

@ElementCollection(fetch = FetchType.EAGER)
@CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "uid"))
@Column(name = "role") // Column name for the roles in the roles table
private List<String> roles;
}
