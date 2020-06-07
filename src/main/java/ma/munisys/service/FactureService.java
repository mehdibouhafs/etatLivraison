package ma.munisys.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;

import ma.munisys.entities.Contrat;
import ma.munisys.entities.Facture;
import ma.munisys.entities.FactureEcheance;


public interface FactureService {
	
	public Facture saveFacutre(Facture facture);
	
	public  void  loadFactureFromSap() ;
	
	public void loadFactureFromSapByContrat(Long numContrat);
	
	//public  void  loadFactureFromSap2() ;
	
	
	public FactureEcheance updateFactureEcheance(Long numContrat,FactureEcheance factureEcheance);
	
	public Page<FactureEcheance> getFactureEcheance(Long numContrat ,int page,int size,String sortBy,String sortType);
 	
	
	
	

}
