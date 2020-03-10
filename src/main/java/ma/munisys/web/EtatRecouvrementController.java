package ma.munisys.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ma.munisys.EtatLvSvcApplication;
import ma.munisys.dto.DocumentAnalyse;
import ma.munisys.entities.Commentaire;
import ma.munisys.entities.EtatRecouvrement;
import ma.munisys.entities.Document;
import ma.munisys.service.EtatRecouvrementService;


@RestController
@CrossOrigin(origins="*")
public class EtatRecouvrementController {
	
	private static final Logger LOGGER = LogManager.getLogger(EtatRecouvrementController.class);
	
	
	@Autowired
	private EtatRecouvrementService etatRecouvrementService;
	
	



	@RequestMapping(value="/getDistinctClientDocument",method=RequestMethod.GET)
	public List<String> getDistinctClient() {
		return etatRecouvrementService.getDistinctClient();
	}
	
	

	@RequestMapping(value="/getDistinctCommercialDocument",method=RequestMethod.GET)
	public List<String> getDistinctCommercial() {
		return etatRecouvrementService.getDistinctCommercial();
	}

	@RequestMapping(value="/getDistinctChefProjetDocument",method=RequestMethod.GET)
	public List<String> getDistinctChefProjet() {
		return etatRecouvrementService.getDistinctChefProjet();
	}

	@RequestMapping(value="/getDistinctAnneePiece",method=RequestMethod.GET)
	public List<String> getDistinctAnneePiece() {
		return etatRecouvrementService.getDistinctAnneePiece();
	}

	@RequestMapping(value="/getDocumentsByCommercialOrChefProjet",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByCommercialOrChefProjet(@RequestParam(name="idEtatRecouvrement",defaultValue="1")Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false") Boolean cloturer,
			 @RequestParam(name="commercialOrChefProjet") String commercialOrChefProjet) {
		return etatRecouvrementService.getDocumentsByCommercialOrChefProjet(idEtatFacture, cloturer,
				commercialOrChefProjet);
	}
	
	@RequestMapping(value="/getDocumentsByNumDocument",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByNumDocument(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="numDocument")  String numDocument) {
		return etatRecouvrementService.getDocumentsByNumDocument(idEtatFacture, cloturer, numDocument);
	}

	@RequestMapping(value="/getDocumentsByChargeRecouvrement",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByChargeRecouvrement(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="chargeRecouvrement")  String chargeRecouvrement) {
		return etatRecouvrementService.getDocumentsByChargeRecouvrement(idEtatFacture, cloturer, chargeRecouvrement);
	}
		
	@RequestMapping(value="/getDocumentsByCodeCommercial",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByCommercial(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="codeCommercial")  String codeCommercial) {
		return etatRecouvrementService.getDocumentsByCommercial(idEtatFacture, cloturer, codeCommercial);
	}
	
	@RequestMapping(value="/getDocumentsByChefProjet",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByChefProjet(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="chefProjet")  String chefProjet) {
		return etatRecouvrementService.getDocumentsByChefProjet(idEtatFacture, cloturer,chefProjet);
	}

	@RequestMapping(value="/getDocumentsByCodeProjet",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByCodeProjet(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="codeProjet")  String codeProjet) {
		return etatRecouvrementService.getDocumentsByCodeProjet(idEtatFacture, cloturer,codeProjet);
	}
	
	@RequestMapping(value="/getDocumentsByCodeClient",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByClient(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="codeClient")  String codeClient) {
		return etatRecouvrementService.getDocumentsByClient(idEtatFacture, cloturer, codeClient);
	}

	@RequestMapping(value="/getEtatRecouvrement",method=RequestMethod.GET)
	public EtatRecouvrement getEtatProjet() {
		return etatRecouvrementService.getEtatRecouvrement();
	}
	
	@RequestMapping(value="/getPageDocuments",method=RequestMethod.GET)
	public Page<Document> getDocuments(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatRecouvrement,@RequestParam(name="cloturer",defaultValue="false") Boolean cloturer, @RequestParam(name="page",defaultValue="0")int page,@RequestParam(name="size",defaultValue="5")int size) {
		return  etatRecouvrementService.getDocumentsFromEtatRecouvrement(idEtatRecouvrement,cloturer,page, size);
	}
	
	@RequestMapping(value="/getDocuments",method=RequestMethod.GET)
	public Collection<Document> getDocumentsWithoutPagination(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatRecouvrement,@RequestParam(name="cloturer",defaultValue="false") Boolean cloturer,@RequestParam(name = "statut") String statut, 
			@RequestParam(name = "commercial") String commercial,@RequestParam(name = "client") String client,@RequestParam(name = "chefProjet") String chefProjet,@RequestParam(name = "chargeRecouvrement") String chargeRecouvrement,@RequestParam(name = "anneePiece") String anneePiece) {
		return  etatRecouvrementService.getDocumentsFromEtatRecouvrement(idEtatRecouvrement, cloturer, chargeRecouvrement, commercial, chefProjet, client, statut,anneePiece);
	}
	
	@RequestMapping(value="/documents",method=RequestMethod.PUT)
	public Document updateDocuments(@RequestBody Document document,Authentication authentication) {
		
		EtatRecouvrement etatRecouvrement = new EtatRecouvrement();
		etatRecouvrement.setId(1L);
		document.setEtatRecouvrement(etatRecouvrement);
		
		for(Commentaire c : document.getCommentaires()) {
			c.setDocument(document);
		}
		
		return etatRecouvrementService.updateDocument(document.getCodePiece(), document);
	}
	
	
	@RequestMapping(value="/refreshDocuments",method=RequestMethod.GET)
	public String refreshProjects(Authentication authentication) {
		EtatLvSvcApplication.loadDocumentsFromSap();
		return  "ok";
	}
	
	
	
	@RequestMapping("/exportReleveClient")
	@ResponseBody
	public void exportDocumentsExcel(Authentication authentication,HttpServletResponse response,@RequestParam(name="client") String client)  {
		     
	   
	      Resource file =  etatRecouvrementService.getReleveClient(client);
	      
	      
	      response.setContentType("application/pdf");
	      response.setHeader("Content-disposition", "attachment; filename=\"" + file.getFilename() + "\"");

	      OutputStream out;
		try {
			out = response.getOutputStream();
			 FileInputStream in = new FileInputStream(file.getFile());

		      // copy from in to out
		      IOUtils.copy(in,out);

		      out.close();
		      in.close();
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			File f;
			try {
				f = file.getFile();
				Files.delete(f.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		}
	     
	   /* return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);*/
	         
	       
	    }
	
	@RequestMapping(value="/getCountSumDocumentsByClient",method=RequestMethod.GET)
	public List<DocumentAnalyse> getCountSumDocumentsByClient(@RequestParam(name="cloturer",defaultValue="false")Boolean cloturer,@RequestParam(name="client") String client) {
		
		return etatRecouvrementService.getCountSumDocumentsByClient(cloturer, client);
	}

	@RequestMapping(value="/getDocumentByClientOnDate",method=RequestMethod.GET)
	public Collection<Document> getDocumentByClientOnDate(@RequestParam(name="cloturer",defaultValue="false")Boolean cloturer,@RequestParam(name="client") String client,@RequestParam(name="month") int month,@RequestParam(name="year") int year) {
		return etatRecouvrementService.getDocumentByClientOnDate(cloturer, client, month, year);
	}
	
	
	@RequestMapping("/exportSituationDocumentsByClient")
	@ResponseBody
	public void exportSituationDocumentsByClient(HttpServletResponse response,@RequestParam(name="client") String client)  {
		     
	   
	      Resource file =  etatRecouvrementService.getSituationDocumentsByClient(false, client);
	      
	      
	      response.setContentType("application/pdf");
	      response.setHeader("Content-disposition", "attachment; filename=\"" + file.getFilename() + "\"");

	      OutputStream out;
		try {
			out = response.getOutputStream();
			 FileInputStream in = new FileInputStream(file.getFile());

		      // copy from in to out
		      IOUtils.copy(in,out);

		      out.close();
		      in.close();
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			File f;
			try {
				f = file.getFile();
				Files.delete(f.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		}
	     
	   /* return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);*/
	         
	       
	    }
	
	@RequestMapping("/exportSituationDocumentsByClientDate")
	@ResponseBody
	public void exportSituationDocumentsByClient(HttpServletResponse response,@RequestParam(name="client") String client,@RequestParam(name="month") int month,@RequestParam(name="year") int year)  {
		     
	   
	      Resource file =  etatRecouvrementService.getSituationDocumentsByClient(false, client, month, year);
	      
	      response.setContentType("application/pdf");
	      response.setHeader("Content-disposition", "attachment; filename=\"" + file.getFilename() + "\"");

	      OutputStream out;
		try {
			out = response.getOutputStream();
			 FileInputStream in = new FileInputStream(file.getFile());

		      // copy from in to out
		      IOUtils.copy(in,out);

		      out.close();
		      in.close();
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			File f;
			try {
				f = file.getFile();
				Files.delete(f.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		}
	     
	   /* return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);*/
	         
	       
	    }
	
	
	
	@RequestMapping("/exportEncaissementNextMonth")
	@ResponseBody
	public void exportEncaissementNextMonth(HttpServletResponse response)  {
		     
	   
	      Resource file =  etatRecouvrementService.getRapportEncaissementNextMonths();
	      
	      response.setContentType("application/pdf");
	      response.setHeader("Content-disposition", "attachment; filename=\"" + file.getFilename() + "\"");

	      OutputStream out;
		try {
			out = response.getOutputStream();
			 FileInputStream in = new FileInputStream(file.getFile());

		      // copy from in to out
		      IOUtils.copy(in,out);

		      out.close();
		      in.close();
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			File f;
			try {
				f = file.getFile();
				Files.delete(f.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		}
	     
	   /* return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);*/
	         
	       
	    }
	


	
	
	
}
