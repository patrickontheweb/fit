package com.patrickontheweb.fit.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.patrickontheweb.fit.model.BaseEntity;

@Entity
@Table(name = "USER")
public class User extends BaseEntity {

	@NotBlank(message = "Username is required")
	private String username;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "Password is required")
	private String password;
	
	private String firstName;

	private String lastName;
	
	@NotBlank(message = "E-mail is required")
	@Pattern(regexp=".+@.+\\..+", message="E-mail must be in the format user@domain.extension")
	private String email;
	
	private Set<Role> roles;
	
	@Column(name = "USERNAME", nullable = false, unique = false)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "PASSWORD", nullable = false) 
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "EMAIL", nullable = false, unique = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "MM_USER_ROLE", 
			joinColumns = {@JoinColumn(name = "USER_ID")}, 
			inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")}
	)
	public Set<Role> getRoles() {
		return roles != null ? roles : new HashSet<Role>();
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public boolean equals(Object object) {
		boolean equals = false;
		
		if(object instanceof User) {
			User other = User.class.cast(object);

			equals = new EqualsBuilder()
					.append(this.getUsername(), other.getUsername())
					.append(this.getEmail(), other.getEmail())
					.append(this.getFirstName(), other.getFirstName())
					.append(this.getLastName(), other.getLastName())
					.isEquals();
		}
				
		return equals;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getUsername())
				.append(getEmail())
				.append(getFirstName())
				.append(getLastName())
				.toHashCode();
	}
}
