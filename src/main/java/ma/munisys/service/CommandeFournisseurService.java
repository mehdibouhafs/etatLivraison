package ma.munisys.service;

import org.springframework.data.domain.Page;
import ma.munisys.entities.CommandeFournisseur;


public interface CommandeFournisseurService {
	
	public CommandeFournisseur saveCommandeFournisseur(CommandeFournisseur commandeFournisseur);
	
	public  void loadCommandeFournisseurFromSap() ;
	
	
	public Page<CommandeFournisseur> getCommandeFournisseur (Long numContrat ,int page,int size);
	
	
	
	

}
