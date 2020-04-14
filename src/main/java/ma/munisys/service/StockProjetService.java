package ma.munisys.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.StockProjet;

public interface StockProjetService {

	
	
	public Collection<StockProjet> getStockProjetByFiltre(String numLot, String client, String annee, String magasin,String com);

	public Collection<StockProjet>  getStockParProjet();
	
	public void loadStockFromSap();

	public List<String> getMontantByNature(String numLot);

	
}
