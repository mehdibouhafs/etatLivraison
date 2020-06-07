package ma.munisys.service;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.EtatRecouvrement;
import ma.munisys.dto.DocumentAnalyse;
import ma.munisys.dto.EtatEncaissement;
import ma.munisys.entities.Document;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Header;




public interface EtatRecouvrementService {
		
	
	public Page<Document> getDocumentsFromEtatRecouvrement(Long idEtatRecouvrement,Boolean cloture,int page,int size);
	
	public Collection<Document> getDocumentsFromEtatRecouvrement(Long idEtatRecouvrement, Boolean cloturer,String chargeRecouvrement,String commercial,String chefProjet,String client,String[] statut,String annee,String age);
	
	public Set<Document> getDocumentsFromInputFile(String fileName);
	
	public Collection<Document> getDocumentsByCommercialOrChefProjet(Long idEtatFacture, Boolean cloturer,String commercialOrChefProjet);
	
	public Collection<Document> getDocumentsByNumDocument(Long idEtatFacture, Boolean cloturer,String numDocument);
	
	
	public void addOrUpdateEtatRecouvrement(EtatRecouvrement etatRecouvrement);
	
	public void addOrUpdateDocument(EtatRecouvrement etatRecouvrement,Document document);
	
	public Document findDocumentFromEtatRecouvrement(EtatRecouvrement etatRecouvrement,String numDocument);
	
	
	public void deleteHeaderFromEtatRecouvrement(Long idEtatRecouvrement, Header header);
	
	public Document updateDocument(String idDocument, Document document);
	
	public List<String> getDistinctAnneePiece();
	
	public List<Document> findAllDocuments();
	
	public EtatRecouvrement getEtatRecouvrement();
	
	public Collection<Document> getDocumentsByCommercial( Long idEtatFacture, Boolean cloturer,String codeCommercial);
	
	public Collection<Document> getDocumentsByChefProjet( Long idEtatFacture, Boolean cloturer,String codeChefProjet);
	
	
	public Resource getReleveClient(String client);
	
	
	public Resource getSituationDocumentsByClient(Boolean cloturer, String client);
	
	public Resource getSituationDocumentsByClient(Boolean cloturer, String client, int month, int year);
		
	
	
	public Collection<Document> getDocumentsByClient( Long idEtatFacture, Boolean cloturer,String codeClient);
	
	public void updateDocumentsFromSap( EtatRecouvrement etatRecouvrement);
	
	
	public Collection<Document> getDocumentsByChargeRecouvrement( Long idEtatFacture, Boolean cloturer,String chargeRecouvrement);
	
	
	public Collection<Document> getDocumentsByCodeProjet( Long idEtatFacture, Boolean cloturer,String codeProjet);
	
	
	public Collection<Document> getDocumentsByCodeProjet( String codeProjet);
	
	
	public List<String> getDistinctClient();
	
	
	public List<String> getDistinctCommercial();
	
	
	public List<String> getDistinctChefProjet();
	
	
	
	public List<DocumentAnalyse> getCountSumDocumentsByClient(Boolean cloturer, String client);
	
	public Collection<Document> getDocumentByClientOnDate( Boolean cloturer, String client,int month, int year);
	
	public Collection<EtatEncaissement> getEncaissementNextMonth();

	public Resource getRapportEncaissementNextMonths();
}
