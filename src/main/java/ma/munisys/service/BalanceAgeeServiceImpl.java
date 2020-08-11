package ma.munisys.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.munisys.dao.BalanceAgeeRepository;
import ma.munisys.dao.CommentaireStockRepository;
import ma.munisys.entities.BalanceAgee;

@Service
@Transactional 
public class BalanceAgeeServiceImpl implements BalanceAgeeService {

	
	
	@Autowired 
	BalanceAgeeRepository balanceAgeeRepo;
	
	public Collection<BalanceAgee> getBalance(){
		
		return balanceAgeeRepo.getBalance();
		
	}
	

	public Collection<BalanceAgee> getBalanceByFiltre(String client,String cr,String age){
		
	       if(client.equals("undefined") && cr.equals("undefined") && age.equals("undefined")){

		        return balanceAgeeRepo.getBalance();
		        
		       }
	       
	       
	       if(!client.equals("undefined") && cr.equals("undefined") && age.equals("undefined")){

		        return balanceAgeeRepo.findAll(BalanceAgeeSpecification.byClient(client));
		       }
	       
	       if(client.equals("undefined") && !cr.equals("undefined") && age.equals("undefined")){

		        return balanceAgeeRepo.findAll(BalanceAgeeSpecification.byCR(cr));
		       }
	       
	       if(!client.equals("undefined") && !cr.equals("undefined") && age.equals("undefined")){

		        return balanceAgeeRepo.findAll(BalanceAgeeSpecification.byCR(cr).and(BalanceAgeeSpecification.byClient(client)));
		       }
	       
	       if(client.equals("undefined") && cr.equals("undefined") && !age.equals("undefined")) {

	    	   if(age == "tois_mois") {
	    	   return balanceAgeeRepo.findAllByAge3();
	    			   }
	    	   
	    	   if(age == "six_mois") {
	    	   return balanceAgeeRepo.findAllByAge6();
	    			   }
	       	   if(age == "douze_mois") {
		    	   return balanceAgeeRepo.findAllByAge12();
		    			   }
	       	   if(age == "sup_douze_mois") {
		    	   return balanceAgeeRepo.findAllByAgeSup12();
		    			   }
	       }
	       
	       if((client.equals("undefined") && !cr.equals("undefined") && !age.equals("undefined")) || (!client.equals("undefined") && cr.equals("undefined") && !age.equals("undefined")) ) {

	    	   if(age == "tois_mois") {
	    	   return balanceAgeeRepo.findAllByAge3(client,cr);
	    			   }
	    	   
	    	   if(age == "six_mois") {
	    	   return balanceAgeeRepo.findAllByAge6(client,cr);
	    			   }
	       	   if(age == "douze_mois") {
		    	   return balanceAgeeRepo.findAllByAge12(client,cr);
		    			   }
	       	   if(age == "sup_douze_mois") {
		    	   return balanceAgeeRepo.findAllByAgeSup12(client,cr);
		    			   }
	       }
	       
	       
	       if(!client.equals("undefined") && !cr.equals("undefined") && !age.equals("undefined")) {

	    	   if(age == "tois_mois") {
	    	   return balanceAgeeRepo.findAllByAge33(client,cr);
	    			   }
	    	   
	    	   if(age == "six_mois") {
	    	   return balanceAgeeRepo.findAllByAge66(client,cr);
	    			   }
	       	   if(age == "douze_mois") {
		    	   return balanceAgeeRepo.findAllByAge122(client,cr);
		    			   }
	       	   if(age == "sup_douze_mois") {
		    	   return balanceAgeeRepo.findAllByAgeSup122(client,cr);
		    			   }
	       }

		
		return balanceAgeeRepo.getBalanceByFiltre(client,cr);
		
	}
	

	public Collection<String> FindByStatus(String[] status){
		
		return balanceAgeeRepo.FindByStatus(status);
	}

	public Collection<BalanceAgee> FindByAM(String client,String cr,String am){
		
		return balanceAgeeRepo.FindByAM(am);
	}






	

}
