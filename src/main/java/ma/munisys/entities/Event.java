package ma.munisys.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Events")
public class Event implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private Date date;
	
	@OneToOne
	
	private AppUser createdBy;
	
	@OneToOne
	
	private AppUser user;
	
	private String actions;
	
	@OneToOne

	private Projet projet;
	
	@OneToOne
	
	private Document document;
	
	@OneToOne
	private Produit produit;
	
	private Boolean statut;

	
	public Event() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public AppUser getUser() {
		return user;
	}


	public void setUser(AppUser user) {
		this.user = user;
	}


	public String getActions() {
		return actions;
	}


	public void setActions(String actions) {
		this.actions = actions;
	}


	public Projet getProjet() {
		return projet;
	}


	public void setProjet(Projet projet) {
		this.projet = projet;
	}


	public Document getDocument() {
		return document;
	}


	public void setDocument(Document document) {
		this.document = document;
	}


	public Produit getProduit() {
		return produit;
	}


	public void setProduit(Produit produit) {
		this.produit = produit;
	}


	public Boolean getStatut() {
		return statut;
	}


	public void setStatut(Boolean statut) {
		this.statut = statut;
	}


	public AppUser getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(AppUser createdBy) {
		this.createdBy = createdBy;
	}
	
	
	

	
	
	

}
