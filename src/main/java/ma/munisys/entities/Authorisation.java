package ma.munisys.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "authorisations")
public class Authorisation {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAuth;
	
	private String authName;
	
	
	public Authorisation() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Long getIdAuth() {
		return idAuth;
	}



	public void setIdAuth(Long idAuth) {
		this.idAuth = idAuth;
	}



	public String getAuthName() {
		return authName;
	}



	public void setAuthName(String authName) {
		this.authName = authName;
	}



	@Override
	public String toString() {
		return "Authorisation [idAuth=" + idAuth + ", authName=" + authName + "]";
	}
	
	
	
}
