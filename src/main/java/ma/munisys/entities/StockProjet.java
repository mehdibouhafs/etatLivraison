package ma.munisys.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name="stock_projets")
public class StockProjet implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_stock")
	private Long id_stock;
	private String annee;
	private String client;
    @Column(name = "num_lot")
	private String numLot;
	private String commercial;
	private Double montant;
    @Column(name = "chef_projet")
	private String chefProjet;
    @Column(name="nom_lot")
    private String nomLot; 
    private String magasin;
    @Column(name="date_rec")
    private Date dateRec;
    
   @JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "stock", cascade = { CascadeType.ALL }, orphanRemoval = true)
    @OrderBy("date DESC")
    private Set<CommentaireStock> commentaires = new HashSet<CommentaireStock>();

    
	

	
	
	public StockProjet(Long id_stock, String annee, String client, String numLot, String commercial, Double montant,
		String chefProjet, String nomLot, String magasin, Date dateRec, Set<CommentaireStock> commentaires) {
	super();
	this.id_stock = id_stock;
	this.annee = annee;
	this.client = client;
	this.numLot = numLot;
	this.commercial = commercial;
	this.montant = montant;
	this.chefProjet = chefProjet;
	this.nomLot = nomLot;
	this.magasin = magasin;
	this.dateRec = dateRec;
	this.commentaires = commentaires;
}
	public StockProjet() {
        this.commentaires = new HashSet<CommentaireStock>();

	}
	public StockProjet(String client) {
		super();
		System.out.println("NIVEAU 0" + client);

		this.client = client;
	}

	public StockProjet(Long id) {
		this.id_stock = id;
	}


	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getNumLot() {
		return numLot;
	}

	public void setNumLot(String numLot) {
		this.numLot = numLot;
	}

	public String getCommercial() {
		return commercial;
	}

	public void setCommercial(String commercial) {
		this.commercial = commercial;
	}
	
	

	public String getChefProjet() {
		return chefProjet;
	}

	public void setChefProjet(String chefProjet) {
		this.chefProjet = chefProjet;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}


	
	public Set<CommentaireStock> getCommentaires() {
		return commentaires;
	}

    
	public void setCommentaires(Set<CommentaireStock> commentaires) {
		this.commentaires = commentaires;
	}


	public Long getId_stock() {
		return id_stock;
	}


	public void setId_stock(Long id_stock) {
		this.id_stock = id_stock;
	}
	public String getNomLot() {
		return nomLot;
	}
	public void setNomLot(String nomLot) {
		this.nomLot = nomLot;
	}
	public String getMagasin() {
		return magasin;
	}
	public void setMagasin(String magasin) {
		this.magasin = magasin;
	}
	public Date getDateRec() {
		return dateRec;
	}
	public void setDateRec(Date dateRec) {
		this.dateRec = dateRec;
	}

	

	
	



	
	
	
	
	
	


}

