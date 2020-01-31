package ma.munisys.service;

import java.util.Collection;

import ma.munisys.entities.Produit;

public interface ProduitService {
	
	public  void loadProduitFromSap();
	
	public Produit saveProduit(String idProduit,Produit produit);
	
	public Collection<Produit> getAllProduitsInStock();
	
	public Collection<Produit>  getProduitByPredicate(String nature, String sousNature,String domaine,String sousDomaine,String numLot,String client,String nomMagasin);

}
