package ma.munisys.service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import ma.munisys.entities.AppUser;
import ma.munisys.entities.Commentaire;
import ma.munisys.entities.CommentaireProduit;
import ma.munisys.entities.Document;
import ma.munisys.entities.Employer;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Produit;
import ma.munisys.entities.Projet;

@Service
public class StorageServiceImpl {
  
	private static final Logger LOGGER = LogManager.getLogger(StorageServiceImpl.class);
	
  private final Path rootLocation = Paths.get("upload-dir");
 
  public String store(MultipartFile file) {
    try {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
      String date = dateFormat.format(new Date()).replace(" ", "-").replace(":", "-");
      String newFileName = file.getOriginalFilename().split("")[0]+date+".xlsx";
      Files.copy(file.getInputStream(), this.rootLocation.resolve(newFileName));
      return newFileName;
    } catch (Exception e) {
      throw new RuntimeException("FAILed to store new file !" + e.getMessage());
    }
  }
 
  public Resource loadFile(String filename) {
    try {
      Path file = rootLocation.resolve(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("FAIL!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("FAIL!");
    }
  }
 
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }
 
  public void init() {
    try {
      Files.createDirectory(rootLocation);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage! " + e.getMessage());
    }
  }
  
  public Workbook generateWorkBookDocuments(List<Document> documents) {
     String pattern = "dd/MM/yyyy HH:mm";
     String pattern2 = "dd/MM/yyyy";
      DateFormat df = new SimpleDateFormat(pattern2);
      DateFormat df2 = new SimpleDateFormat(pattern);
    Workbook workbook = new XSSFWorkbook();
    
    
    /* CreationHelper helps us create instances of various things like DataFormat, 
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
     CreationHelper createHelper = workbook.getCreationHelper();

     // Create a Sheet
     Sheet sheet = workbook.createSheet("Piéces");
     
    
     // Create a Font for styling header cells
     Font headerFont = workbook.createFont();
     headerFont.setBold(true);
     headerFont.setFontHeightInPoints((short) 14);
     headerFont.setColor(IndexedColors.BLUE.getIndex());

     // Create a CellStyle with the font
     CellStyle headerCellStyle = workbook.createCellStyle();
     headerCellStyle.setFont(headerFont);

     // Create a Row
     Row headerRow = sheet.createRow(0);
     
     // Create a Row Comment
     
     /*Row HeaderRowComment = sheetComments.createRow(0);
     String[] columnsComment = {"codeProjet","commentaire","date","utilisateur"};*/
     
     
     String[] columns = {"N°Document","Date Document","Client","Ref Client","Mnt Piéce","Mnt ouvert","Type document","Age Document",
         "Code Projet","Projet","Commercial","chefProjet",
         "Age","Chargé recouvrement ","Montant Payé","Condition Paiement",
         "Caution","N°Caution","Type Caution","Montant Caution",
         "Date libération caution","Statut","Motif","Montant Garantie",
         "Montant Provision","Date Fin Garantie","Daté Prévu Encaissement",
         "Action","Durée garantie","Date Pv Provisoire","Responsable",
         "Date Dépot","Date Prévu Réception Définitive","Motif changement date","Commentaires"};

     
     // Create cells
     for(int i = 0; i < columns.length; i++) {
         Cell cell = headerRow.createCell(i);
         cell.setCellValue(columns[i]);
         cell.setCellStyle(headerCellStyle);
     }
     
  /* Create cellsComment
     for(int i = 0; i < columnsComment.length; i++) {
         Cell cell = HeaderRowComment.createCell(i);
         cell.setCellValue(columnsComment[i]);
         cell.setCellStyle(headerCellStyle);
     }*/

     // Create Cell Style for formatting Date
     CellStyle dateCellStyle = workbook.createCellStyle();
     dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

     
     int rowNum = 1;
     for(Document document: documents) {
         Row row = sheet.createRow(rowNum++);

         Cell cell0 = row.createCell(0);
         if(document.getNumPiece()!=null)
        cell0.setCellValue(document.getNumPiece()); 

            Cell cell1= row.createCell(1);
         if(document.getDatePiece()!=null)
        cell1.setCellValue(df.format(document.getDatePiece())); 

         Cell cell2=  row.createCell(2);
         if(document.getClient()!=null)
        cell2 .setCellValue(document.getClient());

        Cell cell3=  row.createCell(3);
         if(document.getRefClient()!=null)
        cell3 .setCellValue(document.getRefClient());

         Cell cell4 = row.createCell(4);
         cell4.setCellType(CellType.NUMERIC);
         if(document.getMontantPiece()!=null)
         cell4.setCellValue(document.getMontantPiece());
         
         Cell cell5= row.createCell(5);
         cell5.setCellType(CellType.NUMERIC);
         if(document.getMontantOuvert()!=null)
         cell5.setCellValue(document.getMontantOuvert());

         Cell cell6 = row.createCell(6);
         if(document.getTypeDocument()!=null)
         cell6.setCellValue(document.getTypeDocument());// 2
         
         Cell cell7 =  row.createCell(7);
        cell7.setCellValue(document.getAgePiece()); // 3
         
         
         Cell cell8 = row.createCell(8);
         if(document.getCodeProjet()!=null)
         cell8.setCellValue(document.getCodeProjet());
         
         Cell cell9  =row.createCell(9);
         if(document.getProjet()!=null)
         cell9.setCellValue(document.getProjet());
         
         Cell cell10 =row.createCell(10);
         if(document.getCommercial()!=null)
          cell10.setCellValue(document.getCommercial());
         
         Cell cell11 = row.createCell(11);
         if(document.getChefProjet()!=null)
         cell11.setCellValue(document.getChefProjet());
                 
         
         Cell cell12 = row.createCell(12);
         if(document.getAge()!=null)
         cell12.setCellValue(document.getAge());
         
         
         Cell cell13 = row.createCell(13);
         if(document.getChargerRecouvrement()!=null)
         cell13.setCellValue(document.getChargerRecouvrement());
         
         Cell cell14 = row.createCell(14);
         cell14.setCellType(CellType.NUMERIC);
         if(document.getMontantPayer()!=null)
         cell14.setCellValue(document.getMontantPayer());
         
         Cell cell15 = row.createCell(15);
         if(document.getConditionDePaiement()!=null)
         cell15.setCellValue(document.getConditionDePaiement());
         
         Cell cell16 = row.createCell(16);
         cell16.setCellType(CellType.BOOLEAN);
         cell16.setCellValue(document.isCaution());
         
         Cell cell17 =row.createCell(17);
         if(document.getNumCaution()!=null)
         cell17.setCellValue(document.getNumCaution());
         
         
         
         Cell cell18 = row.createCell(18);
         if(document.getTypeCaution()!=null)
         cell18.setCellValue(document.getTypeCaution());
         
         Cell cell19 = row.createCell(19);
         cell19.setCellType(CellType.NUMERIC);
         if(document.getMontantCaution()!=null)
         cell19.setCellValue(document.getMontantCaution());
         
         Cell cell20 = row.createCell(20);
         if(document.getDateLiberationCaution()!=null)
         cell20.setCellValue(df.format(document.getDateLiberationCaution()));
         
         Cell cell21 = row.createCell(21);
         if(document.getStatut()!=null)
         cell21.setCellValue(document.getStatut());
         
         Cell cell22 = row.createCell(22);
         if(document.getMotif()!=null)
         cell22.setCellValue(document.getMotif());
         
         
         Cell cell23 = row.createCell(23);
         cell23.setCellType(CellType.NUMERIC);
         cell23.setCellValue(document.getMontantGarantie());
         
         Cell cell24 = row.createCell(24);
         cell24.setCellType(CellType.NUMERIC);
        cell24.setCellValue(document.getMontantProvision());
         
        Cell cell25= row.createCell(25);
        if(document.getDateFinGarantie() !=null)
        cell25.setCellValue(df.format(document.getDateFinGarantie()));
         
         
         Cell cell26 = row.createCell(26);
         if(document.getDatePrevuEncaissement() !=null)
         cell26.setCellValue(df.format(document.getDatePrevuEncaissement()));
         
         Cell cell27 = row.createCell(27);
         if(document.getAction() != null)
         cell27.setCellValue(document.getAction());
         
        
         
         Cell cell28 = row.createCell(28);
         if(document.getDureeGarantie() != null)
         cell28.setCellValue(document.getDureeGarantie());
         
         Cell cell29 = row.createCell(29);
         if(document.getDatePvProvisoire() != null) {
           cell29.setCellValue(df.format( document.getDatePvProvisoire()));
         }
        
        Cell cell30 = row.createCell(30);
        if(document.getResponsable()!=null)
         cell30.setCellValue(document.getResponsable());
         
         Cell cell31 = row.createCell(31);
         if(document.getDateDepot() !=null)
         cell31.setCellValue(df.format(document.getDateDepot()));
         
         Cell cell32 = row.createCell(32);
         if(document.getDatePrevuReceptionDefinitive() !=null)
         cell32.setCellValue(df.format(document.getDatePrevuReceptionDefinitive()));
         
         
         Cell cell33 = row.createCell(33);
         if(document.getMotifChangementDate()!=null)
          cell33.setCellValue(document.getMotifChangementDate());
         
         Cell cell34 = row.createCell(34);
         
         
         // Create an instance of SimpleDateFormat used for formatting 
         // the string representation of date according to the chosen pattern
            
            
            if(document.getCommentaires()!=null) {
             List<Commentaire> comments = new ArrayList<Commentaire>(document.getCommentaires());
             StringBuilder s = new StringBuilder();
             if(comments.size()>=1) {
               s.append(df2.format(comments.get(comments.size()-1).getDate()) + " " +comments.get(comments.size()-1).getUser().getSigle() + " : " + comments.get(comments.size()-1).getContent() + "\n");
             }
             
             if(comments.size()>=2) {
               s.append(df2.format(comments.get(comments.size()-2).getDate()) + " " +comments.get(comments.size()-2).getUser().getSigle() + " : " + comments.get(comments.size()-2).getContent() + "\n");
             }
             
             if(comments.size()>=3) {
               s.append(df2.format(comments.get(comments.size()-3).getDate()) + " " +comments.get(comments.size()-3).getUser().getSigle() + " : " + comments.get(comments.size()-3).getContent() + "\n");
             }
             
             
             
             XSSFRichTextString richString = new XSSFRichTextString(s.toString());
             
             cell34.setCellType(CellType.STRING);
             cell34.setCellValue(richString);
          
            
            }
            
            //to enable newlines you need set a cell styles with wrap=true
            CellStyle cs = workbook.createCellStyle();
            cs.setWrapText(true);
            cell34.setCellStyle(cs);
         
         /*Cell cell33 = row.createCell(33);
         
         
         StringBuilder s = new StringBuilder();
        

      // Create an instance of SimpleDateFormat used for formatting 
      // the string representation of date according to the chosen pattern
         if(projet.getCommentaires()!=null) {
         
           
           for(Commentaire cmt : projet.getCommentaires()) {
             s.append(df2.format(cmt.getDate()) + " " +cmt.getUser().getLastName() + " : " + cmt.getContent() + "\n");
           }
           
           XSSFRichTextString richString = new XSSFRichTextString(s.toString());
           
           cell33.setCellType(CellType.STRING);
           cell33.setCellValue(richString);
         
         }*/
         
         
         
    
        
     }
     /*
     int rowNumComment = 1;
     for(Projet projet: projets) {
       
       for(Commentaire com : projet.getCommentaires()) {
         Row row = sheetComments.createRow(rowNumComment++);
         
         row.createCell(0)
             .setCellValue(projet.getCodeProjet());
         
         row.createCell(1)
             .setCellValue(com.getContent());
         
         row.createCell(2)
             .setCellValue(com.getDate());
         
         row.createCell(3)
             .setCellValue(com.getUser().getFirstName() + "" + com.getUser().getLastName());   
         
       }
       
         
     }*/

    // Resize all columnss to fit the content size
     for(int i = 0; i < columns.length; i++) {
         sheet.autoSizeColumn(i);
     }
    
    return workbook;
  }
  
  
  public Workbook generateWorkBookStock(List<Produit> produits) {
     String pattern = "dd/MM/yyyy HH:mm";
     String pattern2 = "dd/MM/yyyy";
      DateFormat df = new SimpleDateFormat(pattern2);
      DateFormat df2 = new SimpleDateFormat(pattern);
    Workbook workbook = new XSSFWorkbook();
    
    
    /* CreationHelper helps us create instances of various things like DataFormat, 
       Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
    CreationHelper createHelper = workbook.getCreationHelper();

    // Create a Sheet
    Sheet sheet = workbook.createSheet("Stock");
    
   
    // Create a Font for styling header cells
    Font headerFont = workbook.createFont();
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints((short) 14);
    headerFont.setColor(IndexedColors.BLUE.getIndex());

    // Create a CellStyle with the font
    CellStyle headerCellStyle = workbook.createCellStyle();
    headerCellStyle.setFont(headerFont);

    // Create a Row
    Row headerRow = sheet.createRow(0);
    
    // Create a Row Comment
    
    /*Row HeaderRowComment = sheetComments.createRow(0);
    String[] columnsComment = {"codeProjet","commentaire","date","utilisateur"};*/
    
    
    String[] columns = {"codeMagasin",
       "Nom Magasin","Réference","Description","Gérer par","Nature","Sous Nature",
       "Domaine","Sous Domaine","Marque","Num Lot","Client","Commercial",
       "Chef Projet","Date CMD","Bu","Qte","Montant","Commentaire Article","Commentaire Lot",
       "Commentaire Réfèrence","Commentaires"};

 
    // Create cells
    for(int i = 0; i < columns.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(columns[i]);
        cell.setCellStyle(headerCellStyle);
    }
   
    // Create Cell Style for formatting Date
    CellStyle dateCellStyle = workbook.createCellStyle();
    dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

    
    int rowNum = 1;
    for(Produit produit: produits) {
        Row row = sheet.createRow(rowNum++);

        Cell cell0 = row.createCell(0);
        if(produit.getCodeMagasin()!=null)
       cell0.setCellValue(produit.getCodeMagasin());

        Cell cell1 = row.createCell(1);
        if(produit.getNomMagasin()!=null)
        cell1.setCellValue(produit.getNomMagasin());
        
        Cell cell2 =  row.createCell(2);
       cell2.setCellValue(produit.getItemCode());
        
        Cell cell3= row.createCell(3);
        
       cell3.setCellValue(produit.getItemName());
        
        Cell cell4=  row.createCell(4);
        
       cell4 .setCellValue(produit.getGerePar());
        
        Cell cell5 = row.createCell(5);
      
        cell5.setCellValue(produit.getNature());
        
       

        
        Cell cell6  =row.createCell(6);
        
        cell6.setCellValue(produit.getSousNature());
        
        Cell cell7 =row.createCell(7);
      
        
         cell7.setCellValue(produit.getDomaine());
        
        Cell cell8 = row.createCell(8);
        
        cell8.setCellValue(produit.getSousDomaine());
        
        Cell cell9 = row.createCell(9);
        
       
        cell9.setCellValue(produit.getMarque());
        
        Cell cell10= row.createCell(10);
       
        cell10.setCellValue(produit.getNumLot());
        
       
        
        Cell cell11 = row.createCell(11);
        if(produit.getClient()!=null)
        cell11.setCellValue(produit.getClient());
        
        
        Cell cell12 = row.createCell(12);
        if(produit.getCommercial()!=null)
        cell12.setCellValue(produit.getCommercial());
        
        Cell cell13 = row.createCell(13);
       
        if(produit.getChefProjet()!=null)
        cell13.setCellValue(produit.getChefProjet());
        
         
        
        Cell cell14 = row.createCell(14);
        
        if(produit.getDateCmd()!=null)
        cell14.setCellValue(df.format(produit.getDateCmd()));
        
        Cell cell115 = row.createCell(15);
        
        
        cell115.setCellValue(produit.getBu());



        Cell cell116 = row.createCell(16);
        cell116.setCellType(CellType.NUMERIC);
        cell116.setCellValue(produit.getQte());

        
        Cell cell117 = row.createCell(17);
        cell117.setCellType(CellType.NUMERIC);
        
        cell117.setCellValue(produit.getMontant());
        
        
        Cell cell118 = row.createCell(18);
        
        if(produit.getCommentaireArtcileProjet()!=null)
        cell118.setCellValue(produit.getCommentaireArtcileProjet());
        
        Cell cell119 = row.createCell(19);
        
        if(produit.getCommentaireLot()!=null)
        cell119.setCellValue(produit.getCommentaireLot());
        
        Cell cell120 = row.createCell(20);
        
        if(produit.getCommentaireReference()!=null)
        cell120.setCellValue(produit.getCommentaireReference());
        
        
        
        
        
         
         Cell cell121 = row.createCell(21);
         
         
      // Create an instance of SimpleDateFormat used for formatting 
      // the string representation of date according to the chosen pattern
         
         
         if(produit.getCommentaires()!=null) {
           List<CommentaireProduit> comments = new ArrayList<CommentaireProduit>(produit.getCommentaires());
           StringBuilder s = new StringBuilder();
           if(comments.size()>=1) {
             s.append(df2.format(comments.get(comments.size()-1).getDate()) + " " +comments.get(comments.size()-1).getUser().getSigle() + " : " + comments.get(comments.size()-1).getContent() + "\n");
           }
           
           if(comments.size()>=2) {
             s.append(df2.format(comments.get(comments.size()-2).getDate()) + " " +comments.get(comments.size()-2).getUser().getSigle() + " : " + comments.get(comments.size()-2).getContent() + "\n");
           }
           
           if(comments.size()>=3) {
             s.append(df2.format(comments.get(comments.size()-3).getDate()) + " " +comments.get(comments.size()-3).getUser().getSigle() + " : " + comments.get(comments.size()-3).getContent() + "\n");
           }
           
           
           
           XSSFRichTextString richString = new XSSFRichTextString(s.toString());
           
           cell121.setCellType(CellType.STRING);
           cell121.setCellValue(richString);
        
         
         }
         
         //to enable newlines you need set a cell styles with wrap=true
         CellStyle cs = workbook.createCellStyle();
         cs.setWrapText(true);
         cell121.setCellStyle(cs);
         
    
        
     }
     /*
     int rowNumComment = 1;
     for(Projet projet: projets) {
       
       for(Commentaire com : projet.getCommentaires()) {
         Row row = sheetComments.createRow(rowNumComment++);
         
         row.createCell(0)
             .setCellValue(projet.getCodeProjet());
         
         row.createCell(1)
             .setCellValue(com.getContent());
         
         row.createCell(2)
             .setCellValue(com.getDate());
         
         row.createCell(3)
             .setCellValue(com.getUser().getFirstName() + "" + com.getUser().getLastName());   
         
       }
       
         
     }*/

    // Resize all columns to fit the content size
     for(int i = 0; i < columns.length; i++) {
         sheet.autoSizeColumn(i);
     }
    
    return workbook;
    
  }
  
  public Workbook generateWorkBook(List<Projet> projets) {
		 String pattern = "dd/MM/yyyy HH:mm";
		 String pattern2 = "dd/MM/yyyy";
		  DateFormat df = new SimpleDateFormat(pattern2);
		  DateFormat df2 = new SimpleDateFormat(pattern);
		Workbook workbook = new XSSFWorkbook();
		
		
		/* CreationHelper helps us create instances of various things like DataFormat, 
     Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
  CreationHelper createHelper = workbook.getCreationHelper();

  // Create a Sheet
  Sheet sheet = workbook.createSheet("Projets");
  
 

  // Create a Font for styling header cells
  Font headerFont = workbook.createFont();
  headerFont.setBold(true);
  headerFont.setFontHeightInPoints((short) 14);
  headerFont.setColor(IndexedColors.BLUE.getIndex());

  // Create a CellStyle with the font
  CellStyle headerCellStyle = workbook.createCellStyle();
  headerCellStyle.setFont(headerFont);

  // Create a Row
  Row headerRow = sheet.createRow(0);
  
  // Create a Row Comment
  
  /*Row HeaderRowComment = sheetComments.createRow(0);
  String[] columnsComment = {"codeProjet","commentaire","date","utilisateur"};*/
  
  
  String[] columns = {"Code Projet","Projet","Date CMD","Ref.COM","Age (mois)",
 		 "Code Client","client","commercial","chefProjet","BU","Mnt CMD",
 		 "RAL","LIV","LNF","LFP","Mnt Payé","%Facturation",
 		  "Prestation Commandé","RAL JRS PREST CALC","Fact EC","Risque","Maintenance",
 		  "DateFinProjet","CondPaiement","Prérequis","Livraison","Action","Garantie"
 		  ,"Synthese du projet","Avant Vente","Périmetre projet","Intervenant Principal","Ne plus suivre","Commentaires","Mnt Stock"};

  
  // Create cells
  for(int i = 0; i < columns.length; i++) {
      Cell cell = headerRow.createCell(i);
      cell.setCellValue(columns[i]);
      cell.setCellStyle(headerCellStyle);
  }
  
/* Create cellsComment
  for(int i = 0; i < columnsComment.length; i++) {
      Cell cell = HeaderRowComment.createCell(i);
      cell.setCellValue(columnsComment[i]);
      cell.setCellStyle(headerCellStyle);
  }*/

  // Create Cell Style for formatting Date
  CellStyle dateCellStyle = workbook.createCellStyle();
  dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

  // Create Other rows and cells with employees data
  int rowNum = 1;
  for(Projet projet: projets) {
      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      if(projet.getCodeProjet()!=null)
     cell0.setCellValue(projet.getCodeProjet()); //Code Projet

      Cell cell1 = row.createCell(1);
      if(projet.getProjet()!=null)
      cell1.setCellValue(projet.getProjet()); //Projet
      
      Cell cell2 =  row.createCell(2);
      if(projet.getDateCmd()!=null)
     cell2.setCellValue(df.format(projet.getDateCmd()));//Date CMD
      
      Cell cell3= row.createCell(3);
      if(projet.getRefCom()!=null)
     cell3.setCellValue(projet.getRefCom());//Ref.COM
      
      Cell cell4=  row.createCell(4);
      
     cell4 .setCellValue(projet.getAge());//Age (mois)
      
      Cell cell5 = row.createCell(5);
      if(projet.getCodeClient()!=null)
      cell5.setCellValue(projet.getCodeClient());//Code Client
      
      Cell cell6  =row.createCell(6);
      if(projet.getClient()!=null)
     		 cell6.setCellValue(projet.getClient());//client
      
      Cell cell7 =row.createCell(7);
    
      if(projet.getCommercial()!=null)
     	 cell7.setCellValue(projet.getCommercial());//commercial
      
      Cell cell8 = row.createCell(8);
      cell8.setCellValue(projet.getChefProjet());//chefprojet
      
      Cell cell9 = row.createCell(9);
      if(projet.getBu()!=null)
      cell9.setCellValue(projet.getBu());// bu
      
      Cell cell10= row.createCell(10);
      cell10.setCellType(CellType.NUMERIC);
      if(projet.getMontantCmd()!=null)
      cell10.setCellValue(projet.getMontantCmd()); // mnt cmd
     
      
      Cell cell11 = row.createCell(11);
      cell11.setCellType(CellType.NUMERIC);
      if(projet.getRestAlivrer()!=null)
      cell11.setCellValue(projet.getRestAlivrer());//ral
      
      
      Cell cell12 = row.createCell(12);
      cell12.setCellType(CellType.NUMERIC);
      if(projet.getLivrer()!=null)
      cell12.setCellValue(projet.getLivrer());//liv
      
      Cell cell13 = row.createCell(13);
      cell13.setCellType(CellType.NUMERIC);
      if(projet.getLivrerNonFacture()!=null)
      cell13.setCellValue(projet.getLivrerNonFacture());// lnf
      
      Cell cell14 = row.createCell(14);
      cell14.setCellType(CellType.NUMERIC);
      cell14.setCellValue(projet.getLivreFacturePayer());// lfp
      
      Cell cell15 = row.createCell(15);
      cell15.setCellType(CellType.NUMERIC);
      if(projet.getMontantPayer()!=null)
      cell15.setCellValue(projet.getMontantPayer());//mont paye
      
      
      
      Cell cell16 =row.createCell(16);
      if(projet.getFacturation()!=null)
      cell16.setCellType(CellType.NUMERIC);
      cell16.setCellValue(projet.getFacturation());// facturation
      
      
      
      Cell cell17 = row.createCell(17);
      if(projet.getPrestationCommande()!=null)
      cell17.setCellValue(projet.getPrestationCommande());// prestation cmmd
      
      Cell cell18 = row.createCell(18);
      if(projet.getRalJrsPrestCalc()!=null)
      cell18.setCellValue(projet.getRalJrsPrestCalc());//RAL JRS PREST CALC
      
      Cell cell19 = row.createCell(19);
      if(projet.getFactEncours()!=null)
      cell19.setCellValue(projet.getFactEncours()); //Fact EC
      
      Cell cell20 = row.createCell(20);
      if(projet.getRisque()!=null)
      cell20.setCellValue(projet.getRisque()); // Risque
      
      Cell cell21 = row.createCell(21);
      if(projet.getMaintenance()!=null)
      cell21.setCellValue(projet.getMaintenance()); // maint
      
      Cell cell22 = row.createCell(22);
      if(projet.getDateFinProjet() !=null)
      cell22.setCellValue(df.format(projet.getDateFinProjet())); // dateFinProjet
      
      Cell cell23 = row.createCell(23);
      if(projet.getCondPaiement() !=null)
      cell23.setCellValue(projet.getCondPaiement());// condPaiement
      
      Cell cell24 = row.createCell(24);
      if(projet.getPreRequis() !=null)
     cell24.setCellValue(projet.getPreRequis()); //Prérequis
      
     Cell cell25= row.createCell(25);
     if(projet.getLivraison() !=null)
     cell25.setCellValue(projet.getLivraison()); // livrasion
      
      
      Cell cell26 = row.createCell(26);
      if(projet.getAction() !=null)
      cell26.setCellValue(projet.getAction()); // action
      
      Cell cell27 = row.createCell(27);
      if(projet.getGarantie() != null)
      cell27.setCellValue(projet.getGarantie()); // garantie
      
     
      
      Cell cell28 = row.createCell(28);
      if(projet.getSyntheseProjet() != null)
      cell28.setCellValue(projet.getSyntheseProjet());//synthseprojet
      
      Cell cell29 = row.createCell(29);
      if(projet.getAvantVente() != null) {
     	 cell29.setCellValue(projet.getAvantVente()); // avantVente
      }
     
     Cell cell30 = row.createCell(30);
     if(projet.getPerimetreProjet()!=null)
      cell30.setCellValue(projet.getPerimetreProjet()); // perimetreprojet
      
      Cell cell31 = row.createCell(31);
      if(projet.getIntervenantPrincipal() !=null)
      cell31.setCellValue(projet.getIntervenantPrincipal()); //Intervenant Principal
      
      Cell cell32 = row.createCell(32);
      cell32.setCellType(CellType.BOOLEAN);
      if(projet.getSuivre() == null) {
     	 cell32.setCellValue(false);
      }else {
     	 cell32.setCellValue(projet.getSuivre());// ne plus suivre
      }
      
      Cell cell33 = row.createCell(33);
      
      
   // Create an instance of SimpleDateFormat used for formatting 
   // the string representation of date according to the chosen pattern
      
      
      if(projet.getCommentaires()!=null) {
     	 List<Commentaire> comments = new ArrayList<Commentaire>(projet.getCommentaires());
     	 StringBuilder s = new StringBuilder();
     	 if(comments.size()>=1) {
     		 s.append(df2.format(comments.get(comments.size()-1).getDate()) + " " +comments.get(comments.size()-1).getUser().getSigle() + " : " + comments.get(comments.size()-1).getContent() + "\n");
     	 }
     	 
     	 if(comments.size()>=2) {
     		 s.append(df2.format(comments.get(comments.size()-2).getDate()) + " " +comments.get(comments.size()-2).getUser().getSigle() + " : " + comments.get(comments.size()-2).getContent() + "\n");
     	 }
     	 
     	 if(comments.size()>=3) {
     		 s.append(df2.format(comments.get(comments.size()-3).getDate()) + " " +comments.get(comments.size()-3).getUser().getSigle() + " : " + comments.get(comments.size()-3).getContent() + "\n");
     	 }
	         
	         
	         
	         XSSFRichTextString richString = new XSSFRichTextString(s.toString());
	         
	         cell33.setCellType(CellType.STRING);
	         cell33.setCellValue(richString); // commentaires
	      
      
      }
      
      //to enable newlines you need set a cell styles with wrap=true
      CellStyle cs = workbook.createCellStyle();
      cs.setWrapText(true);
      cell33.setCellStyle(cs);
      
      Cell cell34 = row.createCell(34);
      cell34.setCellType(CellType.NUMERIC);
      if(projet.getMontantStock()!=null)
      cell34.setCellValue(projet.getMontantStock()); // montantStock
      
 
     
  }
  /*
  int rowNumComment = 1;
  for(Projet projet: projets) {
 	 
 	 for(Commentaire com : projet.getCommentaires()) {
 		 Row row = sheetComments.createRow(rowNumComment++);
 		 
 		 row.createCell(0)
          .setCellValue(projet.getCodeProjet());
 		 
 		 row.createCell(1)
          .setCellValue(com.getContent());
 		 
 		 row.createCell(2)
          .setCellValue(com.getDate());
 		 
 		 row.createCell(3)
          .setCellValue(com.getUser().getFirstName() + "" + com.getUser().getLastName());	 
 		 
 	 }
 	 
      
  }*/

		// Resize all columns to fit the content size
  for(int i = 0; i < columns.length; i++) {
      sheet.autoSizeColumn(i);
  }
		
		return workbook;
		
	}

}
