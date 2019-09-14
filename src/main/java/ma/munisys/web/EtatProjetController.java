package ma.munisys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ma.munisys.entities.EtatProjet;
import ma.munisys.service.EtatProjetService;


@RestController
@CrossOrigin(origins="*")
public class EtatProjetController {
	
	@Autowired
	private EtatProjetService etatProjetService;
	
	
	
	@RequestMapping(value="/getEtatProjet", method=RequestMethod.GET)
	public EtatProjet getEtatProjet(@RequestParam(name="id") Long id) {
		return etatProjetService.getEtatProjet(id);
	}
	
}
