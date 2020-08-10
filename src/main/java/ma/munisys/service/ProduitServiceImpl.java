package ma.munisys.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.munisys.dao.CommentaireRepository;
import ma.munisys.dao.EventRepository;
import ma.munisys.dao.ProduitRepository;
import ma.munisys.dao.ProjetRepository;
import ma.munisys.dao.UserRepository;
import ma.munisys.entities.AppUser;
import ma.munisys.entities.Event;
import ma.munisys.entities.Produit;
import ma.munisys.entities.Projet;
import ma.munisys.sap.dao.DBA;

@Service
public class ProduitServiceImpl implements ProduitService {
	
	private static final Logger LOGGER = LogManager.getLogger(ProduitServiceImpl.class);
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjetRepository projetRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	public ProduitServiceImpl() {

	}
<<<<<<< HEAD
=======
	
	
	
	// stock projet dep
	

	
	
	
	
>>>>>>> munisysRepo/main

	@Override
	@Transactional
	public void loadProduitFromSap() {

		LOGGER.info("load stock from SAP");
		Set<Produit> produits = new HashSet<Produit>();

		ResultSet rs1 = null;
		try {
<<<<<<< HEAD
			String req1 = "SELECT * FROM DB_MUNISYS.\"V_STK_PRJ\" ";
=======
			String req1 = "SELECT * FROM DB_MUNISYS.\"V_ALL_STK\" ";
>>>>>>> munisysRepo/main
			rs1 = DBA.request(req1);
			java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
			for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
				final String name = rsmd.getColumnName(i);
				//System.out.println("column Name " + name);
			}

			while (rs1.next()) {

				Produit p = new Produit();
				p.setLastUpdate(new Date());

				if (rs1.getString(1) != null && !rs1.getString(1).equals("null")) {
					p.setCodeMagasin(rs1.getString(1));
				}
				if (rs1.getString(2) != null && !rs1.getString(2).equals("null")) {
					p.setNomMagasin(rs1.getString(2));
				}
<<<<<<< HEAD
=======
				
				if(rs1.getString(2).equals("Stock commercial") || rs1.getString(2).equals("Rabat - stock commercial")) {
					p.setType_magasin("Commercial");
				}
				
				
				if(rs1.getString(2).equals("Déploiement") || rs1.getString(2).equals("Déploiement Rabat")) {
					p.setType_magasin("Déploiement");
				}
				
				if(rs1.getString(2).equals("Obsolète Stock Commercial")) {
					p.setType_magasin("Obsolète Stock Commercial");
				}
				
				if(rs1.getString(2).equals("Réparation commerciale")) {
					p.setType_magasin("Réparation commerciale");
				}
				
				if(rs1.getString(2).equals("Stock Approvisionnement")) {
					p.setType_magasin("Stock Approvisionnement");
				}
				
				if(rs1.getString(2).equals("Stock Disponible")) {
					p.setType_magasin("Stock Disponible");
				}
				
				if(rs1.getString(2).equals("Pré Obsolète Stock Commercial")) {
					p.setType_magasin("Pré Obsolète Stock Commercial");
				}
				
>>>>>>> munisysRepo/main
				if (rs1.getString(3) != null && !rs1.getString(3).equals("null")) {

					p.setItemCode(rs1.getString(3));

				}
				if (rs1.getString(4) != null && !rs1.getString(4).equals("null")) {

					p.setItemName(rs1.getString(4));
				}
				if (rs1.getString(5) != null && !rs1.getString(5).equals("null")) {
					p.setGerePar(rs1.getString(5));
				}
				if (rs1.getString(6) != null && !rs1.getString(6).equals("null")) {
					p.setNature(rs1.getString(6));
				}
				if (rs1.getString(7) != null && !rs1.getString(7).equals("null")) {
					p.setSousNature(rs1.getString(7));
				}
				if (rs1.getString(8) != null && !rs1.getString(8).equals("null")) {
					p.setDomaine(rs1.getString(8));
				}
				if (rs1.getString(9) != null && !rs1.getString(9).equals("null")) {
					p.setSousDomaine(rs1.getString(9));
				}
				if (rs1.getString(10) != null && !rs1.getString(10).equals("null")) {
					p.setMarque(rs1.getString(10));
				}
				if (rs1.getString(11) != null && !rs1.getString(11).equals("null")) {
					p.setNumLot(rs1.getString(11));
				} else {
					p.setNumLot("");
				}
				if (rs1.getString(12) != null && !rs1.getString(12).equals("null")) {
					p.setClient(rs1.getString(12));
				}
				if (rs1.getString(13) != null && !rs1.getString(13).equals("null")) {
					p.setCommercial(rs1.getString(13));
				}
				if (rs1.getString(14) != null && !rs1.getString(14).equals("null")) {
					p.setChefProjet(rs1.getString(14));
				}
				if (rs1.getString(15) != null && !rs1.getString(15).equals("null")) {
					//System.out.println("codeProjet " + rs1.getString(1) + " dateCMD " + rs1.getString(3));
					SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
					p.setDateCmd(sp.parse(rs1.getString(15).split("\\s+")[0]));
				}
				if (rs1.getString(16) != null && !rs1.getString(16).equals("null")) {
					p.setBu(rs1.getString(16));
				}
				if (rs1.getString(17) != null && !rs1.getString(17).equals("null")) {
					p.setQte(rs1.getFloat(17));
				}
				if (rs1.getString(18) != null && !rs1.getString(18).equals("null")) {
					p.setQteRal(rs1.getFloat(18));
				}
				if (rs1.getString(19) != null && !rs1.getString(19).equals("null")) {
					p.setPmp(rs1.getDouble(19));
				}
				if (rs1.getString(20) != null && !rs1.getString(20).equals("null")) {
<<<<<<< HEAD
					p.setMontant(rs1.getDouble(20));
=======
					if (rs1.getString(2).equals("Déploiement Rabat") || rs1.getString(2).equals("Déploiement" )) {
						p.setMontant(0.0);
					}
					else {
				
					p.setMontant(rs1.getDouble(20));
					}
>>>>>>> munisysRepo/main
				}
				if (rs1.getString(21) != null && !rs1.getString(21).equals("null")) {
					//System.out.println("codeProjet " + rs1.getString(1) + " dateCMD " + rs1.getString(3));
					SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
					p.setDateEntre(sp.parse(rs1.getString(21).split("\\s+")[0]));
				}

				if (p.getCodeMagasin() != null && p.getItemCode() != null && p.getNumLot() != null) {
					p.setId(p.getCodeMagasin() + p.getItemCode() + p.getNumLot());
					//System.out.println("id " + p.getId());
					//System.out.println("produit " + p.toString());

					produits.add(p);
				}

			}

			Set<Produit> dupplicatedId = new HashSet<Produit>();

			// update qte = 0 for lastProduct
			for (Produit p : produitRepository.findAll()) {
				p.setQte(0);
				p.setQteRal(0);
				produitRepository.save(p);
			}

			//System.out.println("produits " + produits);
			int j = 0;
			for (Produit p : produits) {

				try {
					Produit lastProduit = produitRepository.findById(p.getId()).orElse(null);
					if (lastProduit != null) {
						if (lastProduit.getCommentaires() != null)
							p.setCommentaires(lastProduit.getCommentaires());
						if (lastProduit.getCommentaireArtcileProjet() != null)
							p.setCommentaireArtcileProjet(lastProduit.getCommentaireArtcileProjet());
						if (lastProduit.getCommentaireReference() != null)
							p.setCommentaireReference(lastProduit.getCommentaireReference());
						if (lastProduit.getCommentaireLot() != null)
							p.setCommentaireLot(lastProduit.getCommentaireLot());
					}
				} catch (Exception e) {
					LOGGER.error("error load stock from SAP" + e.getMessage());
					e.printStackTrace();
				}

				for (Produit p2 : produits) {

					if (p2.getId().equals(p.getId())) {
						j++;
					}
					if (j > 1) {

						dupplicatedId.add(p2);
						break;
					}

				}
				j = 0;

				produitRepository.save(p);
			}
			
			Collection<Produit> produitsToDelete = produitRepository.findAll(ProduitSpecification.byQteEqualsZero());
			
			for(Produit p : produitsToDelete) {
				eventRepository.deleteAll(eventRepository.getEventProduit(p.getId()));
			}
			
			produitRepository.deleteAll(produitsToDelete);
				
			Collection<Projet> projets = projetRepository.findAll();
			for(Projet p : projets) {
				
				Double sumStock = produitRepository.getMontantStock(p.getCodeProjet());
				if(sumStock!=null) {
					p.setMontantStock(sumStock);
				}else {
					p.setMontantStock(0.0);
				}
				
			}
			projetRepository.saveAll(projets);

		} catch (Exception e) {
			LOGGER.error("error " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (rs1 != null) {
				try {
					rs1.close();
					DBA.getConnection().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					LOGGER.error("error " + e.getMessage());
					e.printStackTrace();
				}
			}

		}
		
		for(Produit p : produitRepository.findAll()) {
			p.setLastUpdate(new Date());
			produitRepository.save(p);
		}

	}

	@Override
	public Collection<Produit> getAllProduitsInStock() {
		// TODO Auto-generated method stub
		Collection<Produit> produits = null;
		try{
			 produits = produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	
		return produits;
	}

	@Override
	public Collection<Produit> getProduitByPredicate(String nature, String sousNature, String domaine,
<<<<<<< HEAD
			String sousDomaine, String numLot, String client, String nomMagasin) {
=======
			String sousDomaine, String numLot, String client, String nomMagasin,String type) {
>>>>>>> munisysRepo/main
		// TODO Auto-generated method stub

		// aucun filtre
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") // all undefined
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") ){
	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero());
	        
	       }

	       // filtre par nature uniquement.  A
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined")  ){
	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero());
	        
	       }
	       
			// type filtre
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") // all undefined
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && !type.equals("undefined")  ){
	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byType(type)));
	        
	       }
	       
	       
	       
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") // all undefined
	 	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && !type.equals("undefined")  ){
	 	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byType(type).and(ProduitSpecification.byNature(nature))));
	 	        
	 	       }
	       
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") // all undefined
		 	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && !type.equals("undefined")  ){
		 	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byType(type).and(ProduitSpecification.bySousNature(sousNature))));
		 	        
		 	       }
	       
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") // all undefined
		 	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && !type.equals("undefined")  ){
		 	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byType(type).and(ProduitSpecification.byDomaine(domaine))));
		 	        
		 	       }
	       
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") // all undefined
		 	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && !type.equals("undefined")  ){
		 	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byType(type).and(ProduitSpecification.bySousDomaine(sousDomaine))));
		 	        
		 	       }

	       
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") // all undefined
		 	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && !type.equals("undefined")  ){
		 	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byType(type).and(ProduitSpecification.byNumLot(numLot))));
		 	        
		 	       }
	       
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") // all undefined
		 	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && !type.equals("undefined")  ){
		 	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byType(type).and(ProduitSpecification.byClient(numLot))));
		 	        
		 	       }

	       // filtre par nature uniquement.  A
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined")   ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature)));
	       }

<<<<<<< HEAD
	       // filtre par sousNature uniquement.  B
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	       // filtre par nature uniquement.  A
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined")  ){

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature)));
	       }
	       
	       // filtre par sousNature uniquement.  B
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined")  ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature)));
	       }

	       // filtre par domaine uniquement.  C
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine)));
	       }

	       // filtre par SousDomaine uniquement.  D
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined")  ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousDomaine(sousDomaine)));
	       }

	       // filtre par SousDomaine uniquement.  E
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined")  ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNumLot(numLot)));
	       }

	       // filtre par SousDomaine uniquement.  F
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined")  ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byClient(client)));
	       }

	       // filtre par SousDomaine uniquement.  G
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNomMagasin(nomMagasin)));
	       }

	       // filtre par nature et sousNature uniquement.  AB
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature))));
	       }

	       // filtre par nature et domaine uniquement.  AC
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byDomaine(domaine))));
	       }

	       // filtre par nature et sousDomaine uniquement.  AD
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousDomaine(sousDomaine))));
	       }

	       // filtre par nature et numLot uniquement.  AD
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byNumLot(numLot))));
	       }

	       // filtre par nature et client uniquement.  AE
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byClient(client))));
	       }

	       // filtre par nature et client uniquement.  AF
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }
	       
	       // filtre par nature et client uniquement.  AF
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }

>>>>>>> munisysRepo/main


	       // filtre par sousNature et domaine uniquement.  BC
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine))));
	       }

	        // filtre par sousNature et Sousdomaine uniquement.  BD
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.bySousDomaine(sousDomaine))));
	       }

	         // filtre par sousNature et Sousdomaine uniquement.  BE
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byNumLot(numLot))));
	       }

	        // filtre par sousNature et client uniquement.  BF
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byClient(client))));
	       }

	         // filtre par sousNature et client uniquement.  BG
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }


	         // filtre par sousNature et client uniquement.  CE
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine))));
	       }

	        // filtre par sousNature et client uniquement.  CD
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byNumLot(numLot))));
	       }


	        // filtre par sousNature et client uniquement.  CF
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byClient(client))));
	       }

	        // filtre par sousNature et client uniquement.  CG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }

	        // filtre par sousNature et client uniquement.  DE
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot))));
	       }

	        // filtre par sousNature et client uniquement.  DF
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byClient(client))));
	       }

	        // filtre par sousNature et client uniquement.  DG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }

	       // filtre par sousNature et client uniquement.  EF
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNumLot(sousDomaine).and(ProduitSpecification.byClient(client))));
	       }

	        // filtre par sousNature et client uniquement.  EG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNumLot(sousDomaine).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }

	        // filtre par sousNature et client uniquement.  FG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byClient(client).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }


	       // filtre par nature et sousNature  et domaineuniquement.  ABC
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine)))));
	       }

	        // filtre par nature et sousNature et sousDomaine uniquement.  ABD
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.bySousDomaine(sousDomaine)))));
	       }

	        // filtre par nature et sousNature et numLot uniquement.  ABE
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byNumLot(numLot)))));
	       }

	        // filtre par nature et sousNature uniquement.  ABF
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byClient(numLot)))));
	       }

	         // filtre par nature et sousNature uniquement.  ABG
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byNomMagasin(nomMagasin)))));
	       }
	       
	    // filtre par nature et sousNature  et domaineuniquement.  ACD
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine)))));
	       }
	       
	    // filtre par nature et sousNature  et domaineuniquement.  ACE
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byNumLot(numLot)))));
	       }
	       
	       // filtre par nature et sousNature  et domaineuniquement.  ACF
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byClient(client)))));
	       }
	       
	    // filtre par nature et sousNature  et domaineuniquement.  ACG
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byNomMagasin(nomMagasin)))));
	       }
	       
	       // filtre par nature et sousNature  et domaineuniquement.  ADE
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot)))));
	       }
	       
	       // filtre par nature et sousNature  et domaineuniquement.  ADF
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byClient(client)))));
	       }
	       
	    // filtre par nature et sousNature  et domaineuniquement.  ADF
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNomMagasin(nomMagasin)))));
	       }
	       
	    // filtre par nature et sousNature  et domaineuniquement.  AEF
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client)))));
	       }
	       
	       // filtre par nature et sousNature  et domaineuniquement.  AEG
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byNomMagasin(nomMagasin)))));
	       }
	       
	       // filtre par nature et sousNature  et domaineuniquement.  AFG
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byClient(client).and(ProduitSpecification.byNomMagasin(nomMagasin)))));
	       }
	       
	       
	         // filtre par nature et sousNature uniquement.  BCD
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine)).and(ProduitSpecification.bySousDomaine(sousDomaine))));
	       }

	       // filtre par nature et sousNature uniquement.  BCE
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine)).and(ProduitSpecification.byNumLot(numLot))));
	       }

	       // filtre par nature et sousNature uniquement.  BCF
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine)).and(ProduitSpecification.byClient(client))));
	       }

	        // filtre par nature et sousNature uniquement.  BCG
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine)).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }
	       
	    // filtre par nature et sousNature uniquement.  BDE
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.bySousDomaine(sousDomaine)).and(ProduitSpecification.byNumLot(numLot))));
	       }
	       
	    // filtre par nature et sousNature uniquement.  BDF
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.bySousDomaine(sousDomaine)).and(ProduitSpecification.byClient(client))));
	       }
	       
	       // filtre par nature et sousNature uniquement.  BDG
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.bySousDomaine(sousDomaine)).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }
	       
	    // filtre par nature et sousNature uniquement.  BEF
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byNumLot(numLot)).and(ProduitSpecification.byClient(client))));
	       }
	       
	       // filtre par nature et sousNature uniquement.  BEG
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byNumLot(numLot)).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }
	       
	    // filtre par nature et sousNature uniquement.  BFG
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byClient(client)).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }

	        // filtre par domaine et sousDoamne et numLot uniquement.  CDE
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine)).and(ProduitSpecification.byNumLot(numLot))));
	       }

	        // filtre par domaine et sousDoamne et numLot uniquement.  CDF
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine)).and(ProduitSpecification.byClient(client))));
	       }

	       // filtre par domaine et sousDoamne et numLot uniquement.  CDG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine)).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }
	       
	    // filtre par domaine et sousDoamne et numLot uniquement.  CEF
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byNumLot(numLot)).and(ProduitSpecification.byClient(client))));
	       }
	       
	       // filtre par domaine et sousDoamne et numLot uniquement.  CEG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byNumLot(numLot)).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }
	       
	    // filtre par domaine et sousDoamne et numLot uniquement.  CFG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byClient(client)).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }
	       

	       // filtre par domaine et sousDoamne et numLot uniquement.  DEF
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot)).and(ProduitSpecification.byClient(client))));
	       }

	       // filtre par domaine et sousDoamne et numLot uniquement.  DEG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot)).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }
	       
	    // filtre par domaine et sousDoamne et numLot uniquement.  DFG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byClient(client)).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }

	       // filtre par domaine et sousDoamne et numLot uniquement.  EFG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNumLot(sousDomaine).and(ProduitSpecification.byClient(client)).and(ProduitSpecification.byNomMagasin(nomMagasin))));
	       }

	        // filtre par nature et sousNature  et domaineuniquement.  ABCD
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine))))));
	       }

	       // filtre par nature et sousNature  et domaineuniquement.  ABCE
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byNumLot(numLot))))));
	       }

	        // filtre par nature et sousNature  et domaineuniquement.  ABCF
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byClient(client))))));
	       }

	        // filtre par nature et sousNature  et domaineuniquement.  ABCG
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byNomMagasin(nomMagasin))))));
	       }
	       
	    // filtre par nature et sousNature  et domaineuniquement.  ACDE
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot))))));
	       }
	       
	       // filtre par nature et sousNature  et domaineuniquement.  ACDF
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byClient(client))))));
	       }
	       
	       // filtre par nature et sousNature  et domaineuniquement.  ACDG
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNomMagasin(nomMagasin))))));
	       }
	       
	       // filtre par nature et sousNature  et domaineuniquement.  ADEF
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client))))));
	       }
	       
	       // filtre par nature et sousNature  et domaineuniquement.  ADEG
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byNomMagasin(nomMagasin))))));
	       }
	       
	    // filtre par nature et sousNature  et domaineuniquement.  AEFG
	       if(!nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.byClient(client).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byNomMagasin(nomMagasin))))));
	       }
	       
	       
	       
	    

	       // filtre par nature et sousNature  et domaineuniquement.  BCDE
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot))))));
	       }

	       // filtre par nature et sousNature  et domaineuniquement.  BCDF
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byClient(client))))));
	       }

	        // filtre par nature et sousNature  et domaineuniquement.  BCDG
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNomMagasin(nomMagasin))))));
	       }
	       
	       // filtre par nature et sousNature  et domaineuniquement.  BDEF
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(nature).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client))))));
	       }
	       
	    // filtre par nature et sousNature  et domaineuniquement.  BDEG
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(nature).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byNomMagasin(nomMagasin))))));
	       }
	       
	       //filtre par nature et sousNature  et domaineuniquement.  BEFG
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(nature).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client).and(ProduitSpecification.byNomMagasin(nomMagasin))))));
	       }

	       // filtre par nature et sousNature  et domaineuniquement.  CDEF
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client))))));
	       }

	       // filtre par nature et sousNature  et domaineuniquement.  CDEG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byNomMagasin(nomMagasin))))));
	       }
	       
	    // filtre par nature et sousNature  et domaineuniquement.  CEFG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.byClient(client).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byNomMagasin(nomMagasin))))));
	       }

	       // filtre par nature et sousNature  et domaineuniquement.  DEFG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client).and(ProduitSpecification.byNomMagasin(nomMagasin))))));
	       }

	         // filtre par nature et sousNature  et domaineuniquement.  ABCDE
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot)))))));
	       }

	         // filtre par nature et sousNature  et domaineuniquement.  ABCDF
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byClient(client)))))));
	       }

	         // filtre par nature et sousNature  et domaineuniquement.  ABCDG
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNomMagasin(nomMagasin)))))));
	       }

	       // filtre par nature et sousNature  et domaineuniquement.  BCDEF
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client)))))));
	       }

	       // filtre par nature et sousNature  et domaineuniquement.  BCDEG
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byNomMagasin(nomMagasin)))))));
	       }

	       // filtre par nature et sousNature  et domaineuniquement.  CDEFG
	       if(nature.equals("undefined") && sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client).and(ProduitSpecification.byNomMagasin(nomMagasin)))))));
	       }

	          // filtre par nature et sousNature  et domaineuniquement.  ABCDEF
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client))))))));
	       }

	          // filtre par nature et sousNature  et domaineuniquement.  ABCDEG
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byNomMagasin(nomMagasin))))))));
	       }

	        // filtre par nature et sousNature  et domaineuniquement.  BCDEFG
	       if(nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  && type.equals("undefined")){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.bySousNature(nature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client).and(ProduitSpecification.byNomMagasin(nomMagasin))))))));
	       }

	          // filtre par nature et sousNature  et domaineuniquement.  ABCDEFG
	       if(!nature.equals("undefined") && !sousNature.equals("undefined") && !domaine.equals("undefined") 
<<<<<<< HEAD
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined")  ){
=======
	          && !sousDomaine.equals("undefined") && !numLot.equals("undefined") && !client.equals("undefined") && !nomMagasin.equals("undefined") && type.equals("undefined") ){
>>>>>>> munisysRepo/main

	        return produitRepository.findAll(ProduitSpecification.byQteGreaterThanZero().and(ProduitSpecification.byNature(nature).and(ProduitSpecification.bySousNature(sousNature).and(ProduitSpecification.byDomaine(domaine).and(ProduitSpecification.bySousDomaine(sousDomaine).and(ProduitSpecification.byNumLot(numLot).and(ProduitSpecification.byClient(client).and(ProduitSpecification.byNomMagasin(nomMagasin)))))))));
	       }




		return produitRepository.getAllStockByFiltre(nature, sousNature, domaine, sousDomaine, numLot, client,
<<<<<<< HEAD
				nomMagasin);
=======
				nomMagasin,type);
>>>>>>> munisysRepo/main
	}

	@Override
	@Transactional
	public Produit saveProduit(String idProduit, Produit produit) {
		LOGGER.info("save Product " + idProduit);
		produit.setId(idProduit);
		
		
		Produit lastProduit = produitRepository.findById(idProduit).get();
		
		StringJoiner stringJoiner = new StringJoiner(" || ");
		
		
		if(!lastProduit.getCommentaires().equals(produit.getCommentaires())) {
			stringJoiner.add("Ajout de commentaires");
		}
			
		if(stringJoiner.length()>0) {
			Collection<AppUser> users = userRepository.findUserByServices(Arrays.asList("Commercial","Chef Projet","SI","Direction"));
			//System.out.println("size user " + users.size());
			//System.out.println("users : " + users );
			
			Date date =new Date();
			for(AppUser appUser : users) {
				
				boolean addNotification=false;
				
				if( appUser.getService().getServName().equals("Commercial") || appUser.getService().getServName().equals("Chef Projet")) {
					if( (produit.getCommercial()!=null && produit.getCommercial().equals(appUser.getLastName()) || (produit.getChefProjet()!=null && produit.getChefProjet().equals(appUser.getUsername())) )) {
						addNotification = true;	
					}
					
				}else {
					addNotification = true;
				}
				
				if(addNotification) {
					Event event=new Event();
					event.setProduit(produit);
					event.setStatut(false);
					event.setCreatedBy(produit.getUpdatedBy());
					event.setActions(stringJoiner.toString());
					event.setDate(date);
					event.setUser(appUser);
					Collection<Event> events = eventRepository.getEventProduit(appUser.getUsername(), produit.getId());
					if(events!=null && events.size()>0)
					eventRepository.deleteAll(events);
					eventRepository.save(event);
				}
			}
		}
		
		
		Produit ps = produitRepository.save(produit);
		Collection<Produit> produits = produitRepository.findAll();
		for (Produit product : produits) {
			if (!ps.getId().equals(product.getId())) {
				if (produit.getItemCode().equals(product.getItemCode())) {
					product.setCommentaireArtcileProjet(produit.getCommentaireReference());
				}

				if (produit.getNumLot().equals(product.getNumLot())) {
					product.setCommentaireLot(produit.getCommentaireLot());
				}
				produitRepository.save(product);
			}
		}

		return ps;
	}
	






}
