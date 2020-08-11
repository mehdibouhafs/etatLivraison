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
	
	@RequestMapping(value = "/getBalanceByFiltre", method = RequestMethod.GET)
	public Collection<BalanceAgee> getBalanceByClient(String client,String cr,String age){
	
		if(age.equals("3M")) {
			age = "tois_mois";
		}
		if(age.equals("6M")) {
			age = "six_mois";
		}
		
		if(age.equals("A12M")) {
			age = "douze_mois";
		}
		
		if(age.equals("Sup. 12M")) {
			age = "sup_douze_mois";
		}
		System.out.println("AGE "+age);
	return balanceAgeeService.getBalanceByFiltre(client,cr,age);
	
	}

	@RequestMapping(value = "/getBalanceByStatus", method = RequestMethod.GET)	
	public Collection<String> FindByStatus(String[] status){
		
		return balanceAgeeService.FindByStatus(status);
	}
	
	@RequestMapping(value = "/getBalanceByAM", method = RequestMethod.GET)	
	public Collection<BalanceAgee> FindByAM(String client,String cr,String am){
		
		return balanceAgeeService.FindByAM(client,cr, am);
	}



	

	
}

