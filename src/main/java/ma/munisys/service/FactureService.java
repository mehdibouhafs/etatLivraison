package ma.munisys.service;

import java.util.concurrent.CompletableFuture;

import ma.munisys.entities.Facture;


public interface FactureService {
	
	public Facture saveFacutre(Facture facture);
	
	public  CompletableFuture<String>  loadFactureFromSap() ;
	
	
	
	

}
