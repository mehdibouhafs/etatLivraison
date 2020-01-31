package ma.munisys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import ma.munisys.entities.Document;
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
	
	
	
	
	
	public static void main(String[] args) {
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
			
			/* Create PDF  */
			
			FileOutputStream fileOutputStream = new FileOutputStream(new File(outPutFile));
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, fileOutputStream);
		} catch (FileNotFoundException | JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
