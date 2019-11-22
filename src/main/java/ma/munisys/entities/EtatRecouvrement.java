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
@Table(name = "etat_recouvrements")
public class EtatRecouvrement implements Serializable {
	
	@Id
	private Long id;
	
	
	private Date dateCreation;
	
	private Date lastUpdate;
	
	@OneToOne
	private AppUser updatedBy;
	
	/*@OneToMany(mappedBy = "etatRecouvrement",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Header> headers = new HashSet<Header>();*/
	
	@OneToMany(mappedBy = "etatRecouvrement",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Document> documents = new HashSet<Document>();
	
	public EtatRecouvrement() {
		
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}

	
	

	public void addHeader(Header header) {
		header.setEtatRecouvrement(this);
		//this.getHeaders().add(header);
	}
	
	public void addDocument(Document document) {
		
		/*for(Detail detail : document.getDetails()) {
			if(checkHeader(detail)==false) {
				throw new RuntimeException("Header doesn't exist "+ detail.getHeader().getLabel());
			}
		}*/
		document.setEtatRecouvrement(this);
		this.getDocuments().add(document);
	}
	
	public boolean checkHeader(Detail detail) {
		Boolean validHeader=false;
		/*for(Header header : this.getHeaders()) {
			if(header.getLabel().equals(detail.getHeader().getLabel())) {
				validHeader = true;
				break;
			}
		}*/
		return validHeader;
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

	

	@JsonIgnore
	public Set<Document> getDocuments() {
		return documents;
	}



	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}



	@Override
	public String toString() {
		return "EtatRecouvrement [id=" + id  + ", documents=" + documents + "]";
	}
	
	
	
	
	
	
	

}
