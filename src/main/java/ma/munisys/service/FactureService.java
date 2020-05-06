package ma.munisys.service;

import java.util.concurrent.CompletableFuture;

import ma.munisys.entities.Facture;
import ma.munisys.entities.FactureEcheance;


public interface FactureService {
	
	public Facture saveFacutre(Facture facture);
	
	public  CompletableFuture<String>  loadFactureFromSap() ;
	
	public  void  loadFactureFromSap2() ;
	
	
	public FactureEcheance updateFactureEcheance(Long numContrat,FactureEcheance factureEcheance);
	
	
	
	

}
