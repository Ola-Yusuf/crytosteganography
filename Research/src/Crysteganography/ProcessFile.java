/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Crysteganography;

import com.spire.presentation.*;
import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.log4j.PropertyConfigurator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.openxml4j.opc.OPCPackage;  
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

/**
 *
 * @author OLATUNDE YUSUF
 */
public class ProcessFile  {
   public  ProcessFile(){//default construstor.
    
    }

          //********************************************************************************//     
    ///Start of Extract content form .pptx file///
    public  List<ArrayList<String>>  XlsxFileExtraction(String filename) throws FileNotFoundException{
        List<ArrayList<String>> xlsxColumStringlist = new ArrayList<>();
        try
        {
            FileInputStream file = new FileInputStream(new File(filename));
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            for(int i = 0; i < workbook.getNumberOfSheets(); i++)
            {
                //Get first/desired sheet from the workbook
                XSSFSheet sheet = workbook.getSheetAt(i);
                
                //Iterate through each rows one by one
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) 
                {
                    ArrayList<String> eachSheet = new ArrayList<>();
                    Row row = rowIterator.next();
                    //For each row, iterate through all the columns
                    Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) 
                    {
                        org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                        //Check the cell type and format accordingly
                        switch (cell.getCellType()) 
                        {
                            case NUMERIC:
                                eachSheet.add(cell.getNumericCellValue()+ "");
                                break;
                            case STRING:
                                eachSheet.add(cell.getStringCellValue());
                                break;
                        }
                    }
                xlsxColumStringlist.add(eachSheet);
                }
            }
            file.close();
        } 
        catch (IOException e) 
        {
            System.out.println(e);
        }
        return xlsxColumStringlist;
    }
    ///End of Extract content form .pptx file///
   
       //********************************************************************************//     
    ///Start of Extract content form .pptx file///
    public  List<ArrayList<String>>  PptxFileExtraction(String filename) throws FileNotFoundException{
    File file = new File(filename);
    //Create a Presentation instance
        Presentation ppt = new Presentation();
       try {
           //Load the PowerPoint document
           ppt.loadFromFile(file.getAbsolutePath());
       } catch (Exception ex) {
           Logger.getLogger(ProcessFile.class.getName()).log(Level.SEVERE, null, ex);
       }
          List<ArrayList<String>> PptxpraStringlist = new ArrayList<ArrayList<String>>(); // creating list all pptx slides
   
        //Loop through the slides in the document and extract text
        for (Object slide : ppt.getSlides()) {
            ArrayList<String> eachSlide = new ArrayList<String>(); // creating list to grabe all paragraph & text in each slide
            for (Object shape : ((ISlide) slide).getShapes()) {
                if (shape instanceof IAutoShape) {
                    for (Object tp : ((IAutoShape) shape).getTextFrame().getParagraphs()) {
                        if((((ParagraphEx) tp).getText()).length() > 0)
                            eachSlide.add(((ParagraphEx) tp).getText()); // add pragraph to list in a list
                    }
                }
            }
            PptxpraStringlist.add(eachSlide);
        }
        ppt.dispose();
        return PptxpraStringlist;
    }
    ///End of Extract content form .pptx file///
   
   
    //********************************************************************************//     
    ///Start of Extract content form .docx file///
    public  List<String>  DocxFileExtraction(String filename) throws FileNotFoundException{
        
        File file = new File(filename);
         List<String> DocxpraStringlist = new ArrayList<>(); // creating list of string to store each of the paragraph     
            try(FileInputStream secretfile = new FileInputStream(file.getAbsolutePath())) { // create instance of docx file for manipulation
               XWPFDocument secretdocx;
             secretdocx = new XWPFDocument(OPCPackage.open(secretfile));
//               getParagraphs()
              List<XWPFParagraph> paragraphList =  secretdocx.getParagraphs(); // put all paragraph in list data structure
                
            // iterate over paragraph list and add to string list array
                for(XWPFParagraph paragraph: paragraphList){
                  DocxpraStringlist.add(paragraph.getText()); // add pragraph to list in a list     
                }
               secretfile.close();
          } catch (Exception ex) {
             Logger.getLogger(RetriveFile.class.getName()).log(Level.SEVERE, null, ex);
         }
     //////////////////// end  procesing of secret document encryption////////////////////////////////
         return DocxpraStringlist;
    }
    ///End of Extract content form .docx file///
    
    //********************************************************************************// 
    ///Start of Extract content form .pdf file///
    public  List<String>  PDFFileExtraction(String filename) throws FileNotFoundException{
        String log4jConfPath = "log4j.properties";
       PropertyConfigurator.configure(log4jConfPath);
       List<String> Text = new ArrayList<>(); 
       List<String> Seperator1 = new ArrayList<>(); 
      File file = new File(filename);
      PDDocument document;        
        try {
//            PDDocument font = PDDocumentType1Font.HELVETICA_BOLD; 
            document = PDDocument.load(file);
             PDFTextStripper pdfStripper = new PDFTextStripper();
//             pdfStripper.
              pdfStripper.setParagraphStart(pdfStripper.getLineSeparator());
              for (String line: pdfStripper.getText(document).split(pdfStripper.getParagraphStart()))
                {
                    Text.add(line);
                }
              document.close();
        } catch (IOException ex) {
            Logger.getLogger(ProcessFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    Seperator1.add(Text.get(0));
    Text.removeAll(Seperator1);
    return Text;
    }
    ///End of Extract content form .pdf file/// 
  //********************************************************************************//  
   public List<String> TreatCipher(List<String> Text){
        List<String> y = new ArrayList<>();  
    String s = null;
    for(String z:Text){
        if (z.startsWith("STAR-_-") && z.endsWith("LAST####")){
            s=z.substring(z.indexOf('R')+4, z.lastIndexOf('L'));
            y.add(s);//add to list
               s=null;
        }else if(z.startsWith("STAR-_-") && !z.endsWith("LAST####")){
            if (s ==null){s = z;}
        }
        else if(!z.startsWith("STAR-_-") && !z.endsWith("LAST####")){
            if (s !=null){s = s+z;}
        }
        else if(!z.startsWith("STAR-_-") && z.endsWith("LAST####")){
            if (s !=null){
                s = s+z;
                s=s.substring(z.indexOf('R')+4, z.lastIndexOf('L'));
                y.add(s);//add to list
               s=null;
            }
       }
     }
    return y;
    }
     
  //********************************************************************************//  
   public List<String> CapturePDFCipher(List<String> Text){
        List<String> y = new ArrayList<>();  
     String s = null;
    for(int i =0; i<=Text.size()-1; i++){
        if (Text.get(i).startsWith("STAR-_-") && Text.get(i).endsWith("LAST####")){
           y.add(Text.get(i));//add to list
        }else if(Text.get(i).startsWith("STAR-_-") && !Text.get(i).endsWith("LAST####") && s==null){
            s = Text.get(i);
            for(int x = i; x<=Text.size()-1; x++){
               if(!Text.get(i).startsWith("STAR-_-") && !Text.get(x).endsWith("LAST####") && s!=null){
                     s=Text.get(i);
                    }
                else if(Text.get(x).endsWith("LAST####") && s!=null){
                    s= s+Text.get(x);
                    y.add(s);
                    x=Text.size()+1;
                    s=null;
                }
           }
        }
     }
    return y;
    }
     
  //********************************************************************************//
    public int HighestLength(List<String> cipherStringlist, List<String> coverStringlist){
        int length;
      //start setting length or content size of Stego document that will be created
           if( cipherStringlist.size()<= coverStringlist.size()){
                    length = coverStringlist.size();
                }else{
                    length = cipherStringlist.size();
                }
      //end setting length or content size of Stego document that will be created
        return length;
      }
    
      //********************************************************************************// 
    
    public void OpenFile(String FileName){
    Desktop desktop = null;
        if (Desktop.isDesktopSupported()){
            desktop = Desktop.getDesktop();
        }
        try {
            desktop.open(new File(FileName));
        } catch (IOException ex) {
            Logger.getLogger(ProcessFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //********************************************************************************//  
    public static void DocumentErrorMessage(){
  JOptionPane.showMessageDialog(null, "Unable to Locate Secret or Cover Document, Please Try Again.");       
 }
  //********************************************************************************//    
    public static void SetDocumentDir(javax.swing.JTextField x){
    JFileChooser choosemyFile = new JFileChooser();
        choosemyFile.showOpenDialog(null);
        //file myfile = choosemyFile.getSelectedFiles();
        File myfile = choosemyFile.getSelectedFile();
        String fileName = myfile.getAbsolutePath();
        String name = myfile.getName();
        x.setText(fileName);
    }
}
