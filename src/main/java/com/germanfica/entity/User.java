package com.germanfica.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@RequiredArgsConstructor
//@NoArgsConstructor // JPA requires that this constructor be defined as public or protected
@Entity(name="User")
@Table(name="user")
public class User  {
	// == fields ==
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;

	@Column(name="email")
	private String email;

	@Column(name="password")
	private String password;

	// == associations ==

	// == privileges relationship ==
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "privileges",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
}
