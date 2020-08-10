package ma.munisys.service;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.Logger;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
<<<<<<< HEAD
	public Collection<StockProjet> getStockProjetByFiltre(String numLot, String client, String annee, String magasin, String com,String cp){
		

		
	       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){
=======
	public Collection<StockProjet> getStockProjetByFiltre(String numLot, String client, String annee, String magasin, String com,String cp,String type){
		

		
	       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined") && type.contentEquals("undefined")){
>>>>>>> munisysRepo/main

	        return stockProjetRepository.getStockProjet();
	        
	       }

<<<<<<< HEAD
=======
	       // filtre par type uniquement.  E
	       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined") && !type.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byType(type));
	       }
	       
	       // filtre par type mag et numLot uniquement.  E
	       if(!numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined") && !type.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byType(type).and(StockProjetSpecification.byNumLot(numLot)));
	       }
	       
	       // filtre par client et numLot uniquement.  E
	       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined") && !type.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byType(type).and(StockProjetSpecification.byClient(client)));
	       }
	       
	       // filtre par com et numLot uniquement.  E
	       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined") && !type.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byType(type).and(StockProjetSpecification.byCommercial(com)));
	       }

	       // filtre par com et numLot uniquement.  E
	       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined") && !type.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byType(type).and(StockProjetSpecification.byChefProjet(cp)));
	       }

>>>>>>> munisysRepo/main

	       // filtre par numLot uniquement.  E
	       if(!numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byNumLot(numLot));
	       }

	       // filtre par client uniquement.  F
	       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byClient(client));
	       }

	       // filtre par annee uniquement.  G
	       if(numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined" ) && com.equals("undefined") && cp.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byAnnee(annee));
	       }
	       
	       // filtre par commercial uniquement.  G
	       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined" ) && !com.equals("undefined") && cp.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com));
	       }
	       
<<<<<<< HEAD
=======

>>>>>>> munisysRepo/main
	       
	       // filtre par Chef projet uniquement.  G
	       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined" ) && !com.equals("undefined") && !cp.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byChefProjet(cp));
	       }
	       
	       //filtre par magasin uniquement
	       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){
<<<<<<< HEAD
	    		   if(magasin.equals("Stock commercial")) {
	    				return stockProjetRepository.getStockProjetByFiltre3(numLot,client,annee,com,cp);

	    		   }
	    	   	else {
		        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin));
		       }
=======
	    		
		        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin));
		       
>>>>>>> munisysRepo/main
	    		   }


	       // filtre par numLot et client .  EF
	       if(!numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined")  && cp.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byNumLot(numLot).and(StockProjetSpecification.byClient(client)));
	       }

	        // filtre par numLot et annee.  EG
	       if(!numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byNumLot(numLot).and(StockProjetSpecification.byAnnee(annee)));
	       }

	        // filtre par client et année.  FG
	       if(numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){

	        return stockProjetRepository.findAll(StockProjetSpecification.byClient(client).and(StockProjetSpecification.byAnnee(annee)));
	       }
	       
	       

	       
	       // filtre par annee et magasin.  AFG
	       if(numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined")  && cp.equals("undefined")){

<<<<<<< HEAD
    		   if(magasin.equals("Stock commercial")) {
    			   System.out.println("TEEST "+annee);
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)));
	       }
=======
    
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)));
	       
>>>>>>> munisysRepo/main
    		   }
	       
	       // filtre par NumLot et magasin.  AFG
	       if(!numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
    		   if(magasin.equals("Stock commercial")) {
    			  System.out.println("NIVEAU STOCK COMMERCIAL ");
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

    		   }
    	   	else {
  			  System.out.println("NIVEAU AUTRE STOCK ");

	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byNumLot(numLot)));
	       }
=======

	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byNumLot(numLot)));
	       
>>>>>>> munisysRepo/main
    		   }
	       
	       
	       // filtre par Client et magasin.  AFG
	       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byClient(client)));
	       }
=======
    		
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byClient(client)));
	       
>>>>>>> munisysRepo/main
    		   }
	       
	       // filtre par NumLot et année et client.  AFG
	       if(!numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){
    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byClient(client).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byNumLot(numLot)));
	       
    		   }
	             
	       // filtre par Année et numLot et magasin.  AFG
	       if(!numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byNumLot(numLot)));
	       }
=======
    	
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byNumLot(numLot)));
	       
>>>>>>> munisysRepo/main
    		   }
	       
	       // filtre par Année et client et magasin.  AFG
	       if(numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byClient(client)));
	       }
=======
    	
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byClient(client)));
	       
>>>>>>> munisysRepo/main
    		   }
	       
	       // filtre par NumlOT et client et magasin.  AFG
	       if(!numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)));
	       }
=======
    	
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)));
	       
>>>>>>> munisysRepo/main
    		   }
	       
	       // filtre par Tout.  AFG
	       if(!numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
    		   if(magasin.equals("Stock commercial")) {
    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

    		   }
    	   	else {
	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byNumLot(numLot)));
	       }
=======

	    	   
	        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byNumLot(numLot)));
	       
>>>>>>> munisysRepo/main
    		   }
	       
	       
	       
	       // ** Commercial filters ** //
	       
	        // filtre par commercial et année.  FG
		       if(numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

		        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com).and(StockProjetSpecification.byAnnee(annee)));
		       }
	       
	      
		        // filtre par commercial et code projet.  FG
			       if(!numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

			        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com).and(StockProjetSpecification.byNumLot(numLot)));
			       }

			        // filtre par commercial et client.  FG
				       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

				        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com).and(StockProjetSpecification.byClient(client)));
				       }
				       
				       // filtre par commercial et magasin.  AFG
				       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
			    		   if(magasin.equals("Stock commercial")) {
			    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

			    		   }
			    	   	else {
				    	   
				        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)));
				       }
=======
			    		 
				        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)));
				       
>>>>>>> munisysRepo/main
			    		   }

				        // filtre par commercial et année et numlot.  FG
					       if(!numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

					        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byAnnee(annee)));
					       }

					        // filtre par commercial et année et client.  FG
						       if(numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

						        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byAnnee(annee)));
						       }
						       
						       
						       // filtre par année et commercial et magasin.  AFG
						       if(numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
					    		   if(magasin.equals("Stock commercial")) {
					    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

					    		   }
					    	   	else {
						    	   
						        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byAnnee(annee)));
						       }
=======
					    		  
						    	   
						        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byAnnee(annee)));
						       
>>>>>>> munisysRepo/main
					    		   }
						       
						        // filtre par commercial et code prj et client.  FG
							       if(!numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

							        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byNumLot(numLot)));
							       }
							       
							       // filtre par cod prj et commercial et magasin.  AFG
							       if(!numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
						    		   if(magasin.equals("Stock commercial")) {
						    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

						    		   }
						    	   	else {
							    	   
							        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byNumLot(numLot)));
							       }
=======
						    		
							    	   
							        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byNumLot(numLot)));
							       
>>>>>>> munisysRepo/main
						    		   }	
							       
							       // filtre par client et commercial et magasin.  AFG
							       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
						    		   if(magasin.equals("Stock commercial")) {
						    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

						    		   }
						    	   	else {
							    	   
							        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byClient(client)));
							       }
=======
						    		 
							    	   
							        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byClient(client)));
							       
>>>>>>> munisysRepo/main
						    		   }
							       
							       // filtre par année et commercial et magasin et client.  AFG
							       if(numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
						    		   if(magasin.equals("Stock commercial")) {
						    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

						    		   }
						    	   	else {
							    	   
							        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byClient(client)));
							       }
=======
							    	   
							        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byClient(client)));
							       
>>>>>>> munisysRepo/main
						    		   }

							       // filtre par cod prj et commercial et magasin et client.  AFG
							       if(!numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
						    		   if(magasin.equals("Stock commercial")) {
						    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

						    		   }
						    	   	else {
							    	   
							        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)));
							       }
=======
						    		  
							    	   
							        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)));
							       
>>>>>>> munisysRepo/main
						    		   }

							       // filtre par cod prj et commercial et magasin et client.  AFG
							       if(!numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && !com.equals("undefined") && cp.equals("undefined")){

<<<<<<< HEAD
						    		   if(magasin.equals("Stock commercial")) {
						    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

						    		   }
						    	   	else {
							    	   
							        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byAnnee(annee)));
							       }
=======
							    	   
							        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byCommercial(com)).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byAnnee(annee)));
							       
>>>>>>> munisysRepo/main
						    		   }
							       
							       
							       // ** CDP filters ** //
							       
							        // filtre par CDP et année.  FG
								       if(numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

								        return stockProjetRepository.findAll(StockProjetSpecification.byChefProjet(cp).and(StockProjetSpecification.byAnnee(annee)));
								       }
							       
<<<<<<< HEAD
							      
=======
								       // filtre par commercial uniquement.  G
								       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined" ) && !com.equals("undefined") && !cp.equals("undefined") && type.equals("undefined")){

								        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com).and(StockProjetSpecification.byChefProjet(cp)));
								       }
							      
								       // filtre par commercial uniquement.  G
								       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined" ) && !com.equals("undefined") && !cp.equals("undefined")){

								        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byClient(client)));
								       }
								       
								       // filtre par commercial uniquement.  G
								       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined" ) && !com.equals("undefined") && cp.equals("undefined") && !type.equals("undefined")){

								        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com).and(StockProjetSpecification.byType(type)).and(StockProjetSpecification.byClient(client)));
								       }
								       
								       // filtre par commercial uniquement.  G
								       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined" ) && !com.equals("undefined") && !cp.equals("undefined") && !type.equals("undefined")){

								        return stockProjetRepository.findAll(StockProjetSpecification.byCommercial(com).and(StockProjetSpecification.byType(type)).and(StockProjetSpecification.byChefProjet(cp)));
								       }
								       
								       
>>>>>>> munisysRepo/main
								        // filtre par cdp et code projet.  FG
									       if(!numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

									        return stockProjetRepository.findAll(StockProjetSpecification.byChefProjet(cp).and(StockProjetSpecification.byNumLot(numLot)));
									       }

									        // filtre par cdp et client.  FG
										       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

										        return stockProjetRepository.findAll(StockProjetSpecification.byChefProjet(cp).and(StockProjetSpecification.byClient(client)));
										       }
										       
										        // filtre par cdp et comm.  FG
											       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && !com.equals("undefined") && !cp.equals("undefined")){

											        return stockProjetRepository.findAll(StockProjetSpecification.byChefProjet(cp).and(StockProjetSpecification.byCommercial(com)));
											       }
										       
										       // filtre par cdp et magasin.  AFG
										       if(numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

<<<<<<< HEAD
									    		   if(magasin.equals("Stock commercial")) {
									    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

									    		   }
									    	   	else {
										    	   
										        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)));
										       }
=======
									    	
										    	   
										        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)));
										       
>>>>>>> munisysRepo/main
									    		   }

										        // filtre par cdp et année et numlot.  FG
											       if(!numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

											        return stockProjetRepository.findAll(StockProjetSpecification.byChefProjet(cp).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byAnnee(annee)));
											       }

											        // filtre par cdp et année et client.  FG
												       if(numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

												        return stockProjetRepository.findAll(StockProjetSpecification.byChefProjet(cp).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byAnnee(annee)));
												       }
												       
												       
												       // filtre par année et cdp et magasin.  AFG
												       if(numLot.equals("undefined") && client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

<<<<<<< HEAD
											    		   if(magasin.equals("Stock commercial")) {
											    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

											    		   }
											    	   	else {
												    	   
												        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byAnnee(annee)));
												       }
=======
											    		 
												    	   
												        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byAnnee(annee)));
												       
>>>>>>> munisysRepo/main
											    		   }
												       
												        // filtre par cdp et code prj et client.  FG
													       if(!numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

													        return stockProjetRepository.findAll(StockProjetSpecification.byChefProjet(cp).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byNumLot(numLot)));
													       }
													       
													       // filtre par cod prj et cdp et magasin.  AFG
													       if(!numLot.equals("undefined") && client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

<<<<<<< HEAD
												    		   if(magasin.equals("Stock commercial")) {
												    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

												    		   }
												    	   	else {
													    	   
													        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byNumLot(numLot)));
													       }
=======
												    		
													    	   
													        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byNumLot(numLot)));
													       
>>>>>>> munisysRepo/main
												    		   }	
													       
													       // filtre par client et cdp et magasin.  AFG
													       if(numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

<<<<<<< HEAD
												    		   if(magasin.equals("Stock commercial")) {
												    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

												    		   }
												    	   	else {
													    	   
													        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byClient(client)));
													       }
=======
												    		
													    	   
													        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byClient(client)));
													       
>>>>>>> munisysRepo/main
												    		   }
													       
													       // filtre par année et cdp et magasin et client.  AFG
													       if(numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

<<<<<<< HEAD
												    		   if(magasin.equals("Stock commercial")) {
												    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

												    		   }
												    	   	else {
													    	   
													        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byClient(client)));
													       }
=======
												    		
													    	   
													        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byAnnee(annee)).and(StockProjetSpecification.byClient(client)));
													       
>>>>>>> munisysRepo/main
												    		   }

													       // filtre par cod prj et cdp et magasin et client.  AFG
													       if(!numLot.equals("undefined") && !client.equals("undefined") && annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

<<<<<<< HEAD
												    		   if(magasin.equals("Stock commercial")) {
												    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

												    		   }
												    	   	else {
													    	   
													        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)));
													       }
=======
												    	
													    	   
													        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)));
													       
>>>>>>> munisysRepo/main
												    		   }

													       // filtre par cod prj et cdp et magasin et client.  AFG
													       if(!numLot.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined") && !magasin.equals("undefined") && com.equals("undefined") && !cp.equals("undefined")){

<<<<<<< HEAD
												    		   if(magasin.equals("Stock commercial")) {
												    				return stockProjetRepository.getStockProjetByFiltre2(numLot,client,annee,com,cp);

												    		   }
												    	   	else {
													    	   
													        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byAnnee(annee)));
													       }
												    		   }								     
							       
	 
		return stockProjetRepository.getStockProjetByFiltre(numLot,client,annee,com,cp);
=======
		
													        return stockProjetRepository.findAll(StockProjetSpecification.byMagasin(magasin).and(StockProjetSpecification.byChefProjet(cp)).and(StockProjetSpecification.byNumLot(numLot)).and(StockProjetSpecification.byClient(client)).and(StockProjetSpecification.byAnnee(annee)));
													       }
												    		   								     
							       
	 
		return stockProjetRepository.getStockProjetByFiltre(numLot,client,annee,magasin,com,cp);
>>>>>>> munisysRepo/main
	}
	
	@Override
	@Transactional
	public void loadStockFromSap() {

		stockProjetRepository.deleteAll();
		System.out.println("load stock PROJET from SAP");
		Set<StockProjet> produits = new HashSet<StockProjet>();

		ResultSet rs1 = null;
		try {
<<<<<<< HEAD
			String req1 = "SELECT * FROM DB_MUNISYS.\"V_STKPRJ\" ";
=======
			String req1 = "SELECT * FROM DB_MUNISYS.\"V_STK_TYP\" ";
>>>>>>> munisysRepo/main
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
					p.setDate_rec(rs1.getDate(2));
				}
				
				if (rs1.getString(3) != null && !rs1.getString(3).equals("null")) {
					p.setClient(rs1.getString(3));
				}
				
				
				if (rs1.getString(4) != null && !rs1.getString(4).equals("null")) {

					p.setNum_lot(rs1.getString(4));

				}
				else {
					p.setNum_lot("");
				}
				
				
				if (rs1.getString(5) != null && !rs1.getString(5).equals("null")) {

					p.setNom_lot(rs1.getString(5));

				}
				else {
					p.setNom_lot("");
				}
				if (rs1.getString(6) != null && !rs1.getString(6).equals("null")) {

					p.setCommercial(rs1.getString(6));
				}
				if (rs1.getString(7) != null && !rs1.getString(7).equals("null")) {
					p.setChef_projet(rs1.getString(7));
				}
	
<<<<<<< HEAD
				/*if (rs1.getString(8) != null && !rs1.getString(8).equals("null")) {
				
						//p.setMagasin(rs1.getString(8));

					
				}*/
	

				if (rs1.getString(9) != null && !rs1.getString(9).equals("null")) {
					p.setMontant(rs1.getDouble(9));
				}
=======
				if (rs1.getString(8) != null && !rs1.getString(8).equals("null")) {
				
						p.setType_magasin(rs1.getString(8));

					
				}
	


				if (rs1.getString(9) != null && !rs1.getString(9).equals("null")) {
				
						p.setMontant(rs1.getDouble(9));
					}
				
>>>>>>> munisysRepo/main
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
	
	
	
	
	public List<String> getMontantByNature(String numLot,String magasin){
		
<<<<<<< HEAD
		return stockProjetRepository.getMontantByNature(numLot);
=======
		return stockProjetRepository.getMontantByNature(numLot,magasin);
>>>>>>> munisysRepo/main
	}

	
	
		}