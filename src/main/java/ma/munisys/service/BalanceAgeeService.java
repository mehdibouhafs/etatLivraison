package ma.munisys.service;

import java.util.Collection;
import ma.munisys.entities.BalanceAgee;

public interface BalanceAgeeService {
	
	public Collection<BalanceAgee> getBalance();
	public Collection<BalanceAgee> getBalanceByFiltre(String client,String cr);



}
