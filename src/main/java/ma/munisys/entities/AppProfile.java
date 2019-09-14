package ma.munisys.entities;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity

public class AppProfile {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String prflName;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_profile",
    joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "user_username", referencedColumnName = "username"))
	private Set<AppUser> appUsers = new HashSet<>();
	
	@ManyToMany
	private Set<Authorisation> authorities = new HashSet<>();
	
	
	
	public AppProfile() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getPrflName() {
		return prflName;
	}



	public void setPrflName(String prflName) {
		this.prflName = prflName;
	}



	public Set<AppUser> getAppUsers() {
		return appUsers;
	}



	public void setAppUsers(Set<AppUser> appUsers) {
		this.appUsers = appUsers;
	}



	public Set<Authorisation> getAuthorities() {
		return authorities;
	}



	public void setAuthorities(Set<Authorisation> authorities) {
		this.authorities = authorities;
	}



	@Override
	public String toString() {
		return "AppProfile [id=" + id + ", prflName=" + prflName + ", authoritiesProfile=" + authorities + "]";
	}

	
}
