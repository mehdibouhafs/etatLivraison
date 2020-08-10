package ma.munisys.service;

import java.util.Collection;
import ma.munisys.entities.BalanceAgee;

public interface BalanceAgeeService {
	
	public Collection<BalanceAgee> getBalance();

	public Collection<BalanceAgee> getBalanceByFiltre(String client,String cr,String age);
<<<<<<< HEAD
=======
	public Collection<String> FindByStatus(String[] status);
	public Collection<BalanceAgee> FindByAM(String client,String cr,String am);

>>>>>>> munisysRepo/main




}
