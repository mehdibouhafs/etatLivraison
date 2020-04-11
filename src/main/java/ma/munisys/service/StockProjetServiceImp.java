package ma.munisys.service;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.Logger;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.munisys.dao.StockProjetRepository;
import ma.munisys.entities.Produit;
import ma.munisys.entities.StockProjet;
import ma.munisys.sap.dao.DBA;

@Service
public class StockProjetServiceImp implements StockProjetService {

	
	@Autowired
	StockProjetRepository stockProjetRepository;
	
	@Override
	public Collection<StockProjet>  getStockParProjet(){
		
		return stockProjetRepository.getStockProjet();
	}
	
	
	
	@Override
	public Collection<StockProjet> getStockProjetByFiltre(String numLot, String client, String annee, String magasin){
		

		
	       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined")){

	        return stockProjetRepository.getStockProjet();
	        
	       }


	       // filtre par numLot uniquement.  E
	       if(!numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byNumLot(numLot));
	       }

	       // filtre par client uniquement.  F
	       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") ){

	        return stockProjetRepository.findAll(StockProjetSpecification.byClient(client));
	       }

	       // filtre par annee uniquement.  G
	       if(numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined" )){

	        return stockProjetRepository.findAll(StockProjetSpecification.byAnnee(annee));
	       }
	       
	       //filtre par magasin uniquement
	       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined")){
	    		   if(magasin.equals("Stock commercial")) {
	    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee);

	    		   }
	    	   	else {
		        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin));
		       }
	    		   }


	       // filtre par numLot et client .  EF
	       if(!numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byNumLot(numLot).and(StockProjetSpecification.byClient(client)));
	       }

	        // filtre par numLot et annee.  EG
	       if(!numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byNumLot(numLot).and(StockProjetSpecification.byAnnee(annee)));
	       }

	        // filtre par client et année.  FG
	       if(numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byClient(client).and(StockProjetSpecification.byAnnee(annee)));
	       }

	       
	       // filtre par annee et magasin.  AFG
	       if(numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined")){

    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)));
	       }
    		   }
	       
	       // filtre par NumLot et magasin.  AFG
	       if(!numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined")){

    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byNumLot(numLot)));
	       }
    		   }
	       
	       
	       // filtre par Client et magasin.  AFG
	       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined")){

    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byClient(client)));
	       }
    		   }
	       
	       // filtre par NumLot et année et client.  AFG
	       if(!numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined")){
    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byClient(client).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byNumLot(numLot)));
	       
    		   }
	             
	       // filtre par Année et numLot et magasin.  AFG
	       if(!numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined")){

    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byNumLot(numLot)));
	       }
    		   }
	       
	       // filtre par Année et client et magasin.  AFG
	       if(numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined")){

    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byClient(client)));
	       }
    		   }
	       
	       // filtre par NumlOT et client et magasin.  AFG
	       if(!numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined")){

    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)));
	       }
    		   }
	       
	       // filtre par Tout.  AFG
	       if(!numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined")){

    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byNumLot(numLot)));
	       }
    		   }
	       
	       
	       
	      

	 
		return stockProjetRepository.getStockProjetByFiltre(numLot,client,annee,magasin);
	}
	
	@Override
	@Transactional
	public void loadStockFromSap() {

		System.out.println("load stock PROJET from SAP");
		Set<StockProjet> produits = new HashSet<StockProjet>();

		ResultSet rs1 = null;
		try {
			String req1 = "SELECT * FROM DB_MUNISYS.\"V_STKPRJ\" ";
			rs1 = DBA.request(req1);
			java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
			for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
				final String name = rsmd.getColumnName(i);
				System.out.println("column Name " + name);
			}

			while (rs1.next()) {


				StockProjet p = new StockProjet();

				if (rs1.getString(1) != null && !rs1.getString(1).equals("null")) {
					p.setAnnee(rs1.getString(1));
				}
				if (rs1.getString(2) != null && !rs1.getString(2).equals("null")) {
					p.setDateRec(rs1.getDate(2));
				}
				
				if (rs1.getString(3) != null && !rs1.getString(3).equals("null")) {
					p.setClient(rs1.getString(3));
				}
				
				
				if (rs1.getString(4) != null && !rs1.getString(4).equals("null")) {

					p.setNumLot(rs1.getString(4));

				}
				else {
					p.setNumLot("");
				}
				
				
				if (rs1.getString(5) != null && !rs1.getString(5).equals("null")) {

					p.setNomLot(rs1.getString(5));

				}
				else {
					p.setNomLot("");
				}
				if (rs1.getString(6) != null && !rs1.getString(6).equals("null")) {

					p.setCommercial(rs1.getString(6));
				}
				if (rs1.getString(7) != null && !rs1.getString(7).equals("null")) {
					p.setChefProjet(rs1.getString(7));
				}
	
				if (rs1.getString(8) != null && !rs1.getString(8).equals("null")) {
				
						p.setMagasin(rs1.getString(8));

					
				}
	

				if (rs1.getString(9) != null && !rs1.getString(9).equals("null")) {
					p.setMontant(rs1.getDouble(9));
				}
				produits.add(p);

			}

			Set<StockProjet> dupplicatedId = new HashSet<StockProjet>();
			for (StockProjet p : stockProjetRepository.findAll()) {
		
				stockProjetRepository.save(p);
			}
			int j = 0;
			for (StockProjet p : produits) {

				stockProjetRepository.save(p);
			}
			

}
		catch (Exception e) {
	System.out.println("error load stock from SAP" + e.getMessage());
	e.printStackTrace();
}
		}
		}