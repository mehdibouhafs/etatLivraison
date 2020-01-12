package ma.munisys.entities;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;


@Entity
@Table(name = "app_users")
public class AppUser {
	
	@Id 
	private String username;
	
	private String password;
	
	private String lastName;
	
	private String firstName;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Authorisation> authorities = new HashSet<>();
	
	@ManyToOne
	private Service service;
	
	private String img;
	
	private String sigle;
	
	
	public AppUser() {
		
	}

	public AppUser(String firstName,String lastName,String username, String password,Service service,String img) {
		this.username = username;
		this.password = password;
		this.service = service;
		this.img = img;
		this.firstName = firstName;
		this.lastName = lastName;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonSetter
	public void setPassword(String password) {
		this.password = password;
	}

	

	public Set<Authorisation> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authorisation> authorities) {
		this.authorities = authorities;
	}

	
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	

	public String getSigle() {
		return sigle;
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}
	
	

	@Override
	public String toString() {
		return "AppUser [username=" + username + ", password=" + password + ", lastName=" + lastName + ", firstName="
				+ firstName + ", authorities="  + ", service=" + service + ", img=" + img + "]";
	}
	
	
	

}
