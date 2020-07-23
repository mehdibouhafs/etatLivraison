package ma.munisys.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reunions")
public class Reunion implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	private Date dateCreation;
	
	@Column(columnDefinition = "TEXT")
	private String commentaire;
	
	private Date dateReunion;
	
	private String statut;
	
	private String client;
	
	private String collaborateur;
	
	private String fournisseur;
	
	public Reunion() {
		
	}


	public Long getId() {
		return id;
	}


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public String getCommentaire() {
		return commentaire;
	}


	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}


	public Date getDateReunion() {
		return dateReunion;
	}


	public void setDateReunion(Date dateReunion) {
		this.dateReunion = dateReunion;
	}


	public String getStatut() {
		return statut;
	}


	public void setStatut(String statut) {
		this.statut = statut;
	}


	public String getClient() {
		return client;
	}


	public void setClient(String client) {
		this.client = client;
	}


	public String getCollaborateur() {
		return collaborateur;
	}


	public void setCollaborateur(String collaborateur) {
		this.collaborateur = collaborateur;
	}


	public String getFournisseur() {
		return fournisseur;
	}


	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Reunion [id=" + id + ", dateCreation=" + dateCreation + ", commentaire=" + commentaire
				+ ", dateReunion=" + dateReunion + ", statut=" + statut + ", client=" + client + ", collaborateur="
				+ collaborateur + ", fournisseur=" + fournisseur + "]";
	}


	
	
	



	
	
	
	
	

}
