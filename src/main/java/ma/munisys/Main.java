package ma.munisys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.map.HashedMap;
import org.joda.time.DateTime;

import ma.munisys.entities.Document;
import ma.munisys.entities.Echeance;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Main {
	
	
	
	
	
	public static void main(String[] args) throws ParseException {
		
		 String date11="01/01/2020";
		 String date22="31/12/2022";
		 
		 Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(date11);  
		 Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(date22); 
		List<Echeance> echeances = generateEcheanceModele(date1, date2, 12);
		
		System.out.println(echeances.size());
		 for(Echeance e : echeances) {
			 System.out.println(e.toString());
		 }
		
		
		/*
		Map<String,Integer> mp=new HashedMap<String, Integer>();
		mp.put("a", 10);
	      mp.put("a", 11);
	      mp.put("a", 12);
	      mp.put("b", 13);
	      mp.put("c", 14);
	      mp.put("e", 15);
	      
	      System.out.println(mp.get("a"));
		
		
		
		 String outPutFile  = "rapport/jasperReportExample.pdf";
		Document d = new Document();
		d.setNumPiece("xxxx");
		d.setTypeDocument("Faceture");
		d.setMontantPiece(120.0);
		d.setMontantOuvert(100.0);
		
		Document d1 = d;
		d1.setNumPiece("d2");
		List<Document> documents  = new ArrayList<Document>();
		documents.add(d);
		documents.add(d1);
		
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(documents);
		
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("collectionDataSource", jrBeanCollectionDataSource);
		
		
		
				
		try {
			InputStream inputS= new FileInputStream("rapport/releveClient.jrxml");
			JasperDesign jD =  JRXmlLoader.load(inputS);
			JasperReport jasperReport = JasperCompileManager.compileReport(jD);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
			JasperViewer.viewReport(jasperPrint);
			
		
			
			FileOutputStream fileOutputStream = new FileOutputStream(new File(outPutFile));
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, fileOutputStream);
		} catch (FileNotFoundException | JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		
		
	}
	
	
	
public static  List<Echeance> generateEcheanceModele(Date debut,Date fin,int nbMonth) {
	
	List<Echeance> echeances = new ArrayList<Echeance>();

	Integer nbMonthPeriod = nbMonth;

	if (debut != null && fin != null) {
		DateTime start = new DateTime(debut);
		
		DateTime end = new DateTime(fin);
		
		
			while (start.compareTo(end) < 0 ) {
				Echeance c = new Echeance();
				c.setContrat(null);
				c.setDu(start.toDate());
				c.setContratModel(null);
				c.setCloture(false);
				DateTime dateBetween = start.plusMonths(nbMonthPeriod);
				start = dateBetween;
				System.out.println("start "+ start);
				c.setAu(start.plusDays(-1).toDate());
				c.setMontant(0.0);
				c.setMontantRestFacture(c.getMontant());
				c.setMontantFacture(0.0);
			
				
				if (start.compareTo(end) >= 0 ) {
					c.setAu(end.toDate());
					
				}
			
				echeances.add(c);

			}
		}
	

	return echeances;

}

}
