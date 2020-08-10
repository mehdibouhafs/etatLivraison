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
	private String num_lot;
	private String commercial;
	private Double montant;
    @Column(name = "chef_projet")
	private String chef_projet;
    @Column(name="nom_lot")
    private String nom_lot; 
<<<<<<< HEAD
    //private String magasin;
    @Column(name="date_rec")
    private Date date_rec;
    
=======
    @Column(name="date_rec")
    private Date date_rec;
    
    @Column(name="type_magasin")
    private String type_magasin;
   
>>>>>>> munisysRepo/main
   @JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "stock", cascade = { CascadeType.ALL }, orphanRemoval = true)
    @OrderBy("date DESC")
    private Set<CommentaireStock> commentaires = new HashSet<CommentaireStock>();

    
	

	
	
	public StockProjet(Long id_stock, String annee, String client, String numLot, String commercial, Double montant,
<<<<<<< HEAD
		String chefProjet, String nomLot, String magasin, Date dateRec, Set<CommentaireStock> commentaires) {
=======
		String chefProjet, String nomLot, Date dateRec,String type, Set<CommentaireStock> commentaires) {
>>>>>>> munisysRepo/main
	super();
	this.id_stock = id_stock;
	this.annee = annee;
	this.client = client;
	this.num_lot = numLot;
	this.commercial = commercial;
	this.montant = montant;
	this.chef_projet = chefProjet;
	this.nom_lot = nomLot;
<<<<<<< HEAD
	//this.magasin = magasin;
	this.date_rec = dateRec;
=======
	this.date_rec = dateRec;
	this.type_magasin= type;
>>>>>>> munisysRepo/main
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


	public String getCommercial() {
		return commercial;
	}

	public void setCommercial(String commercial) {
		this.commercial = commercial;
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

<<<<<<< HEAD
	/*public String getMagasin() {
		return magasin;
	}
	public void setMagasin(String magasin) {
		this.magasin = magasin;
	}*/

=======
>>>>>>> munisysRepo/main
	public String getNom_lot() {
		return nom_lot;
	}
	public void setNom_lot(String nom_lot) {
		this.nom_lot = nom_lot;
	}
	public Date getDate_rec() {
		return date_rec;
	}
	public void setDate_rec(Date date_rec) {
		this.date_rec = date_rec;
	}
	public String getNum_lot() {
		return num_lot;
	}
	public void setNum_lot(String num_lot) {
		this.num_lot = num_lot;
	}
	public String getChef_projet() {
		return chef_projet;
	}
	public void setChef_projet(String chef_projet) {
		this.chef_projet = chef_projet;
	}
<<<<<<< HEAD
=======
	public String getType_magasin() {
		return type_magasin;
	}
	public void setType_magasin(String type_magasin) {
		this.type_magasin = type_magasin;
	}
	
>>>>>>> munisysRepo/main
	
	
	
	
	
	

	
	

	
	



	
	
	
	
	
	


}

