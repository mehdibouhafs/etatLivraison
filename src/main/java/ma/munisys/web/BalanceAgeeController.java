package ma.munisys.web;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.munisys.entities.BalanceAgee;
import ma.munisys.service.BalanceAgeeService;


@RestController
@CrossOrigin(origins = "*")
public class BalanceAgeeController {

	
	
	@Autowired
	private BalanceAgeeService balanceAgeeService;
	
	

	@RequestMapping(value = "/getBalance", method = RequestMethod.GET)
	public Collection<BalanceAgee>  getStockParProjet(){
	
		return balanceAgeeService.getBalance();
	}
	
	@RequestMapping(value = "/getBalanceByClient", method = RequestMethod.GET)
	public Collection<BalanceAgee> getBalanceByClient(String client){
	
	return balanceAgeeService.getBalanceByClient(client);
	
	}

	

	
}

