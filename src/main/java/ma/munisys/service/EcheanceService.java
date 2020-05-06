package ma.munisys.service;


import ma.munisys.entities.Echeance;



public interface EcheanceService {
	
	
	
	
	public Echeance updateEcheance(Long id, Long idCommentaire);
	
	
	public Echeance editEcheance(Long numContrat, Echeance e);
	
	public void deleteEcheance(Long idEcheance);

	
	
	
	

}
