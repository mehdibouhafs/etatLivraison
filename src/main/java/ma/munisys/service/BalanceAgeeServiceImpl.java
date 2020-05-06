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
	

	public Collection<BalanceAgee> getBalanceByFiltre(String client,String cr){
		
	       if(client.equals("undefined") && cr.equals("undefined")){

		        return balanceAgeeRepo.getBalance();
		        
		       }
	       
	       
	       if(!client.equals("undefined") && cr.equals("undefined")){

		        return balanceAgeeRepo.findAll(BalanceAgeeSpecification.byClient(client));
		       }
	       
	       if(client.equals("undefined") && !cr.equals("undefined")){

		        return balanceAgeeRepo.findAll(BalanceAgeeSpecification.byCR(cr));
		       }
	       
	       if(!client.equals("undefined") && !cr.equals("undefined")){

		        return balanceAgeeRepo.findAll(BalanceAgeeSpecification.byCR(cr).and(BalanceAgeeSpecification.byClient(client)));
		       }
		
		return balanceAgeeRepo.getBalanceByFiltre(client,cr);
		
	}
	






	

}
