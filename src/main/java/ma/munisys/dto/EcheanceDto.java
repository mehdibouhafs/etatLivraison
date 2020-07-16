package ma.munisys.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ma.munisys.entities.CommentaireEcheance;
import ma.munisys.entities.ContratModel;
import ma.munisys.entities.FactureEcheance;

public class EcheanceDto {

	
	private Long id;

	
	private Date du;

	
	private Date au;

	private Double montant;

	private Double montantPrevision;

	private ma.munisys.entities.PeriodeFacturation PeriodeFacturation;

	private ma.munisys.entities.OccurenceFacturation OccurenceFacturation;

	
	private Contrat contrat;

	private Double montantFacture;

	private Double montantRestFacture;

	private String factures;

	
	private Set<FactureEcheance> factureEcheances = new HashSet<FactureEcheance>();

	
	private ContratModel contratModel;


	private CommentaireEcheance commentaire;

	private boolean cloture;

	private boolean addedByUser;

	private boolean deletedByUser;

	private String messageDelete;
	
	private String nomModele;
	
	
	public EcheanceDto() {
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDu() {
		return du;
	}

	public void setDu(Date du) {
		this.du = du;
	}

	public Date getAu() {
		return au;
	}

	public void setAu(Date au) {
		this.au = au;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Double getMontantPrevision() {
		return montantPrevision;
	}

	public void setMontantPrevision(Double montantPrevision) {
		this.montantPrevision = montantPrevision;
	}

	public ma.munisys.entities.PeriodeFacturation getPeriodeFacturation() {
		return PeriodeFacturation;
	}

	public void setPeriodeFacturation(ma.munisys.entities.PeriodeFacturation periodeFacturation) {
		PeriodeFacturation = periodeFacturation;
	}

	public ma.munisys.entities.OccurenceFacturation getOccurenceFacturation() {
		return OccurenceFacturation;
	}

	public void setOccurenceFacturation(ma.munisys.entities.OccurenceFacturation occurenceFacturation) {
		OccurenceFacturation = occurenceFacturation;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public Double getMontantFacture() {
		return montantFacture;
	}

	public void setMontantFacture(Double montantFacture) {
		this.montantFacture = montantFacture;
	}

	public Double getMontantRestFacture() {
		return montantRestFacture;
	}

	public void setMontantRestFacture(Double montantRestFacture) {
		this.montantRestFacture = montantRestFacture;
	}

	public String getFactures() {
		return factures;
	}

	public void setFactures(String factures) {
		this.factures = factures;
	}

	public Set<FactureEcheance> getFactureEcheances() {
		return factureEcheances;
	}

	public void setFactureEcheances(Set<FactureEcheance> factureEcheances) {
		this.factureEcheances = factureEcheances;
	}

	public ContratModel getContratModel() {
		return contratModel;
	}

	public void setContratModel(ContratModel contratModel) {
		this.contratModel = contratModel;
	}

	public CommentaireEcheance getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(CommentaireEcheance commentaire) {
		this.commentaire = commentaire;
	}

	public boolean isCloture() {
		return cloture;
	}

	public void setCloture(boolean cloture) {
		this.cloture = cloture;
	}

	public boolean isAddedByUser() {
		return addedByUser;
	}

	public void setAddedByUser(boolean addedByUser) {
		this.addedByUser = addedByUser;
	}

	public boolean isDeletedByUser() {
		return deletedByUser;
	}

	public void setDeletedByUser(boolean deletedByUser) {
		this.deletedByUser = deletedByUser;
	}

	public String getMessageDelete() {
		return messageDelete;
	}

	public void setMessageDelete(String messageDelete) {
		this.messageDelete = messageDelete;
	}

	public String getNomModele() {
		return nomModele;
	}

	public void setNomModele(String nomModele) {
		this.nomModele = nomModele;
	}
	
	
	
}
