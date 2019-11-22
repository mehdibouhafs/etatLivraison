package ma.munisys.service;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.EtatRecouvrement;
import ma.munisys.entities.Document;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Header;




public interface EtatRecouvrementService {
		
	
	public Page<Document> getDocumentsFromEtatRecouvrement(Long idEtatRecouvrement,Boolean cloture,int page,int size);
	
	public Collection<Document> getDocumentsFromEtatRecouvrement(Long idEtatRecouvrement,Boolean cloture);
	
	public Set<Document> getDocumentsFromInputFile(String fileName);
	
	public Collection<Document> getDocumentsByCommercialOrChefProjet(Long idEtatFacture, Boolean cloturer,String commercialOrChefProjet);
	
	public void addOrUpdateEtatRecouvrement(EtatRecouvrement etatRecouvrement);
	
	public void addOrUpdateDocument(EtatRecouvrement etatRecouvrement,Document document);
	
	public Document findDocumentFromEtatRecouvrement(EtatRecouvrement etatRecouvrement,String numDocument);
	
	
	public void deleteHeaderFromEtatRecouvrement(Long idEtatRecouvrement, Header header);
	
	public Document updateDocument(String idDocument, Document document);
	
	
	
	public List<Document> findAllDocuments();
	
	public EtatRecouvrement getEtatRecouvrement();
	
	public Collection<Document> getDocumentsByCommercial( Long idEtatFacture, Boolean cloturer,String codeCommercial);
	
	public Collection<Document> getDocumentsByChefProjet( Long idEtatFacture, Boolean cloturer,String codeChefProjet);
	
	
	public Collection<Document> getDocumentsByClient( Long idEtatFacture, Boolean cloturer,String codeClient);
	
	public void updateDocumentsFromSap( EtatRecouvrement etatRecouvrement);
	
	
	public Collection<Document> getDocumentsByChargeRecouvrement( Long idEtatFacture, Boolean cloturer,String chargeRecouvrement);
	
	
	public Collection<Document> getDocumentsByCodeProjet( Long idEtatFacture, Boolean cloturer,String codeProjet);
	
	
	
	


}
