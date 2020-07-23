package ma.munisys.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class EtatProjet implements Serializable {
	
	@Id
	private Long id;
	
	
	private Date dateCreation;
	
	private Date lastUpdate;
	
	@OneToOne
	private AppUser updatedBy;
	
	@OneToMany(mappedBy = "etatProjet",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Header> headers = new HashSet<Header>();
	
	@OneToMany(mappedBy = "etatProjet",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Projet> projets = new HashSet<Projet>();
	
	public EtatProjet() {
		
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}

	
	

	public void addHeader(Header header) {
		header.setEtatProjet(this);
		this.getHeaders().add(header);
	}
	
	public void addProjet(Projet projet) {
		
		/*for(Detail detail : projet.getDetails()) {
			if(checkHeader(detail)==false) {
				throw new RuntimeException("Header doesn't exist "+ detail.getHeader().getLabel());
			}
		}*/
		projet.setEtatProjet(this);
		this.getProjets().add(projet);
	}
	
	public boolean checkHeader(Detail detail) {
		Boolean validHeader=false;
		for(Header header : this.getHeaders()) {
			if(header.getLabel().equals(detail.getHeader().getLabel())) {
				validHeader = true;
				break;
			}
		}
		return validHeader;
	}

	@JsonIgnore
	public Set<Header> getHeaders() {
		return headers;
	}



	public void setHeaders(Set<Header> headers) {
		this.headers = headers;
	}


	@JsonIgnore
	public Set<Projet> getProjets() {
		return projets;
	}



	public void setProjets(Set<Projet> projets) {
		this.projets = projets;
	}
	


	public Date getDateCreation() {
		return dateCreation;
	}



	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}



	public Date getLastUpdate() {
		return lastUpdate;
	}



	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}



	public AppUser getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(AppUser updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	



	@Override
	public String toString() {
		return "EtatProjet [id=" + id + ", headers=" + headers + ", projets=" + projets + "]";
	}
	
	
	
	
	
	
	

}
