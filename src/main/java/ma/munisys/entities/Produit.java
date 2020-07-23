package ma.munisys.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name = "produits")
public class Produit {
	
	@Id
	private String id;
	
	private String codeMagasin;
	
	private String nomMagasin;
	
	private String itemCode;
	
	private String itemName;
	
	private String gerePar;
	
	private String nature;
	
	private String sousNature;
	
	private String domaine;
	
	private String sousDomaine;
	
	private String marque;
	
	private String numLot;
	
	
	private String client;
	
	private String commercial;
	
	private String chefProjet;
	
	private Date dateCmd;
	
	private String bu;
	
	private float qte;
	private float qteRal;
	private double pmp;
	private double montant;
	
	private Date dateEntre;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "produit", cascade = { CascadeType.ALL }, orphanRemoval = true)
    @OrderBy("date DESC")
    private Set<CommentaireProduit> commentaires;
	@Column(columnDefinition = "TEXT")
	private String commentaireArtcileProjet;
	
	@Column(columnDefinition = "TEXT")
	private String commentaireLot;
	
	@Column(columnDefinition = "TEXT")
	private String commentaireReference;
	
	private Date lastUpdate;
	
	@OneToOne
	private AppUser updatedBy;
	
	private String type_magasin;

	public Produit() {
		this.commentaires = new HashSet<CommentaireProduit>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeMagasin() {
		return codeMagasin;
	}

	public void setCodeMagasin(String codeMagasin) {
		this.codeMagasin = codeMagasin;
	}

	public String getNomMagasin() {
		return nomMagasin;
	}

	public void setNomMagasin(String nomMagasin) {
		this.nomMagasin = nomMagasin;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getGerePar() {
		return gerePar;
	}

	public void setGerePar(String gerePar) {
		this.gerePar = gerePar;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getSousNature() {
		return sousNature;
	}

	public void setSousNature(String sousNature) {
		this.sousNature = sousNature;
	}

	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	public String getSousDomaine() {
		return sousDomaine;
	}

	public void setSousDomaine(String sousDomaine) {
		this.sousDomaine = sousDomaine;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getNumLot() {
		return numLot;
	}

	public void setNumLot(String numLot) {
		this.numLot = numLot;
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

	public String getChefProjet() {
		return chefProjet;
	}

	public void setChefProjet(String chefProjet) {
		this.chefProjet = chefProjet;
	}

	public Date getDateCmd() {
		return dateCmd;
	}

	public void setDateCmd(Date dateCmd) {
		this.dateCmd = dateCmd;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	
	public float getQte() {
		return qte;
	}

	public void setQte(float qte) {
		this.qte = qte;
	}

	public float getQteRal() {
		return qteRal;
	}

	public void setQteRal(float qteRal) {
		this.qteRal = qteRal;
	}

	public double getPmp() {
		return pmp;
	}

	public void setPmp(double pmp) {
		this.pmp = pmp;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Date getDateEntre() {
		return dateEntre;
	}

	public void setDateEntre(Date dateEntre) {
		this.dateEntre = dateEntre;
	}
	
	

	public Set<CommentaireProduit> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(Set<CommentaireProduit> commentaires) {
		this.commentaires = commentaires;
	}

	public String getCommentaireArtcileProjet() {
		return commentaireArtcileProjet;
	}

	public void setCommentaireArtcileProjet(String commentaireArtcileProjet) {
		this.commentaireArtcileProjet = commentaireArtcileProjet;
	}

	public String getCommentaireLot() {
		return commentaireLot;
	}

	public void setCommentaireLot(String commentaireLot) {
		this.commentaireLot = commentaireLot;
	}

	public String getCommentaireReference() {
		return commentaireReference;
	}

	public void setCommentaireReference(String commentaireReference) {
		this.commentaireReference = commentaireReference;
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

	
	
	
	public String getType_magasin() {
		return type_magasin;
	}

	public void setType_magasin(String type_magasin) {
		this.type_magasin = type_magasin;
	}

	@Override
	public String toString() {
		return "Produit [id=" + id + ", codeMagasin=" + codeMagasin + ", nomMagasin=" + nomMagasin + ", itemCode="
				+ itemCode + ", itemName=" + itemName + ", gerePar=" + gerePar + ", nature=" + nature + ", sousNature="
				+ sousNature + ", domaine=" + domaine + ", sousDomaine=" + sousDomaine + ", marque=" + marque
				+ ", numLot=" + numLot + ", client=" + client + ", commercial=" + commercial + ", chefProjet="
				+ chefProjet + ", dateCmd=" + dateCmd + ", bu=" + bu + ", qte=" + qte + ", qteRal=" + qteRal + ", pmp="
				+ pmp + ", montant=" + montant + ", dateEntre=" + dateEntre + "]";
	}
	

	
	
	
	
	
	

}
