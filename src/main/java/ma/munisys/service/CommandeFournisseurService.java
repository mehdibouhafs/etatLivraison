package ma.munisys.service;
import java.util.concurrent.CompletableFuture;

import ma.munisys.entities.CommandeFournisseur;


public interface CommandeFournisseurService {
	
	public CommandeFournisseur saveCommandeFournisseur(CommandeFournisseur commandeFournisseur);
	
	public  CompletableFuture<String> loadCommandeFournisseurFromSap() ;
	
	
	
	

}
