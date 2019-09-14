package ma.munisys.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class EtatProjet implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy="etatProjet",fetch=FetchType.LAZY)
	
	private Set<Projet> projets  = new HashSet<Projet>();
	
	
	private String version;
	
	private boolean lastState;
	
	
	public EtatProjet() {
		// TODO Auto-generated constructor stub
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Collection<Projet> getProjets() {
		return projets;
	}


	


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public boolean isLastState() {
		return lastState;
	}


	public void setLastState(boolean lastState) {
		this.lastState = lastState;
	}

	
	
}
