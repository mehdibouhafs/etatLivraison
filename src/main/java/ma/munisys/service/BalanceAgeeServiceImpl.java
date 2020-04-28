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
	
	public Collection<BalanceAgee> getBalanceByClient(String client){
		

		
		return balanceAgeeRepo.getBalanceByClient(client);
		
	}

	

}
