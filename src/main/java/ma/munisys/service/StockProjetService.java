package ma.munisys.service;

import java.util.Collection;

import ma.munisys.entities.StockProjet;

public interface StockProjetService {

	
	
	public Collection<StockProjet> getStockProjetByFiltre(String numLot, String client, String annee, String magasin);

	public Collection<StockProjet>  getStockParProjet();
	
	public void loadStockFromSap();
	
}
