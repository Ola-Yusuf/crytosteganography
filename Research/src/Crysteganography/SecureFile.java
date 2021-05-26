/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crysteganography;

import static Crysteganography.BlowfishOperation.Encrpt_Blow;
import static Crysteganography.BlowfishOperation.isKeyPresent;
import static Crysteganography.Start.KeyLocation;
import static Crysteganography.Start.default_dir;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.spire.presentation.FileFormat;
import com.spire.presentation.Presentation;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

/**
 *
 * @author OLATUNDE YUSUF
 */
public class SecureFile {

    public SecureFile() {

    }
    //private final String KeyLocation = default_dir+"BlowFish key/BlowFish Secret Key.key";
    ProcessFile f = new ProcessFile();
//********************************************************************************//    

    ///start stego cipher Encryption////
    public List<String> EncryptPlainTextList(List<String> cipherText, int receiverPublicKey, int senderPrivateKey) throws FileNotFoundException {
        List<String> Text = new ArrayList<>();
        ElGamalOperation elgamal = new ElGamalOperation();
        if (isKeyPresent(KeyLocation)) {
            int q = cipherText.size();
            for (int i = 0; i <= q - 1; i++) {
                try {
                    Text.add("STAR-_-" + Encrpt_Blow(cipherText.get(i), KeyLocation) + "_-_-_"
                            + elgamal.encryptionOperation(KeyLocation, receiverPublicKey, senderPrivateKey) + "LAST####"
                    );
                } catch (Exception ex) {
                    Logger.getLogger(SecureFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return Text;
    }
    ///end stego cipher Encryption////
    //********************************************************************************//    

    public String GenerateStegoDocxFile(List<String> cipherStringlist, List<String> coverStringlist) throws FileNotFoundException {
        int length;
        try {
            String SecuredFileName = JOptionPane.showInputDialog("Save Stego Document as ") + ".docx";
            XWPFDocument stegodocument = new XWPFDocument(); //Blank Document
            //Write the Document in file system
            try (FileOutputStream out = new FileOutputStream(new File(default_dir + SecuredFileName))) {
                length = f.HighestLength(cipherStringlist, coverStringlist);//get length for stego document to be created

                //Star wrtting to docx document from the arrary list in the argument
                for (int i = 0; i <= length; i++) {
                    //put each paragraph in docx file  in to .docx file in alternating style.
                    if (!(i >= coverStringlist.size())) {
                        //  1st paragraph
                        XWPFParagraph paragraph = stegodocument.createParagraph();//create Paragraph
                        XWPFRun run = paragraph.createRun();
                        run.setFontSize(14);
                        run.setFontFamily("Times New Roman");
                        run.setText(coverStringlist.get(i));
                        run.setColor("000000");
                    }
                    if (!(i >= cipherStringlist.size())) {
                        //2nd paragraph
                        XWPFParagraph paragraph1 = stegodocument.createParagraph();//create Paragraph
                        XWPFRun run1 = paragraph1.createRun();
                        run1.setFontSize(1);
                        run1.setFontFamily("Times New Roman");
                        run1.setText(cipherStringlist.get(i));
                        run1.setColor("ffffff");
                    }
                }
                stegodocument.write(out);
                //stegodocument.enforceReadonlyProtection();
                out.close();
            }
            int r = JOptionPane.showConfirmDialog(null,
                    "Stego Document in docx format Successfully Created \n Click YES to Open Document ",
                    "SELECT TYPE OF FILE", 0);
            if (r == 0) {
                boolean P = isKeyPresent(default_dir + SecuredFileName);
                if (P) {
                    f.OpenFile(default_dir + SecuredFileName);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SecureFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SecureFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    //********************************************************************************//  
    public String GenerateStegoPDFFile(List<String> cipherStringlist, List<String> coverStringlist) {
        int length;
        try {
            String SecuredFileName = JOptionPane.showInputDialog("Save PDF Stego Document as ") + ".pdf";
            Document mark = new Document();
            try {
                PdfWriter.getInstance(mark, new FileOutputStream(default_dir + SecuredFileName));
            } catch (DocumentException ex) {
                Logger.getLogger(SecureFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            length = f.HighestLength(cipherStringlist, coverStringlist);//get length for stego document to be created
            mark.open();
            for (int i = 0; i <= length; i++) {
                //put each paragraph in docx file  in to .docx file in alternating style.
                if (!(i >= coverStringlist.size())) {
                    try {
                        //  1st paragraph
                        mark.add(new Paragraph(coverStringlist.get(i),
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 14,
                                        Font.NORMAL, BaseColor.BLACK)));
                    } catch (DocumentException ex) {
                        Logger.getLogger(SecureFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (!(i >= cipherStringlist.size())) {
                    try {
                        //2nd paragraph
                        mark.add(new Paragraph(cipherStringlist.get(i),
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 1,
                                        Font.NORMAL, BaseColor.WHITE)));
                    } catch (DocumentException ex) {
                        Logger.getLogger(SecureFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            mark.close();
            int r = JOptionPane.showConfirmDialog(null,
                    "Stego Document in pdf format Successfully Created \n Click YES to Open Document ",
                    "SELECT TYPE OF FILE", 0);
            if (r == 0) {
                boolean P = isKeyPresent(default_dir + SecuredFileName);
                if (P) {
                    f.OpenFile(default_dir + SecuredFileName);
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;

    }

    public String GenerateStegoPptxFile(List<ArrayList<String>> cipherStringlist, List<ArrayList<String>> coverStringlist) throws FileNotFoundException {
        int length;
        String SecuredFileName = JOptionPane.showInputDialog("Save Stego Document as ") + ".pptx";
        try {
            XWPFDocument stegodocument = new XWPFDocument(); //Blank Document
            XMLSlideShow ppt = new XMLSlideShow();
            //Write the Document in file system
            try (FileOutputStream out = new FileOutputStream(new File(default_dir + SecuredFileName))) {

                length = Math.max(cipherStringlist.size(), coverStringlist.size());

                //Star wrtting to docx document from the arrary list in the argument
                for (int i = 0; i <= length - 1; i++) {//slide level
                    XSLFSlideMaster defaultMaster = ppt.getSlideMasters().get(0);
                    XSLFSlideLayout layout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
                    XSLFSlide slide = ppt.createSlide(layout);
                    XSLFTextShape titleShape = slide.getPlaceholder(0);
                    XSLFTextShape contentShape = slide.getPlaceholder(1);
                    titleShape.clearText();
                    contentShape.clearText();
                    if (coverStringlist.size() - 1 >= i) {
                        titleShape.setText(coverStringlist.get(i).get(0));
                    }
                    if (cipherStringlist.size() - 1 >= i && coverStringlist.size() - 1 >= i) {
                        int dt = Math.max(cipherStringlist.get(i).size(), coverStringlist.get(i).size());
                        //put each paragraph in docx file  in to .docx file in alternating style.
                        for (int y = 0; y <= dt - 1; y++) { //paragraph level
                            if (!(y >= coverStringlist.get(i).size()) && y > 0) {
                                // 1st paragraph 
                                XSLFTextParagraph p = contentShape.addNewTextParagraph();
                                XSLFTextRun r = p.addNewTextRun();
                                r.setText(coverStringlist.get(i).get(y));
                                r.setFontColor(Color.black);
                                r.setFontSize(18.0);
                            }
                            if (!(y >= cipherStringlist.get(i).size())) {
                                //  2nd paragraph
                                XSLFTextParagraph p = contentShape.addNewTextParagraph();
                                p.setBullet(false);
                                XSLFTextRun r = p.addNewTextRun();
                                r.setText(cipherStringlist.get(i).get(y));
                                r.setFontColor(Color.white);
                                r.setFontSize(1.0);
                            }
                        }
                    }

                }
                ppt.write(out);
                out.close();
            }

            int r = JOptionPane.showConfirmDialog(null,
                    "Click YES for Stego Document in .pptx \n or NO for .pdf ",
                    "SELECT TYPE OF FILE", 0);
            if (r == 0) {
                int i = JOptionPane.showConfirmDialog(null,
                        "Stego Document in pptx format Successfully Created \n Click YES to Open Document ",
                        "SELECT TYPE OF FILE", 0);
                if (i == 0) {
                    boolean P = isKeyPresent(default_dir + SecuredFileName);
                    if (P) {
                        f.OpenFile(default_dir + SecuredFileName);
                    }
                }
            } else {
                String SecuredFileNamePdf = SecuredFileName.split(".pptx", 2)[0];
                ConvertPptxToPDFFile(SecuredFileNamePdf);
                boolean P = isKeyPresent(default_dir + SecuredFileNamePdf + ".pdf");
                if (P) {
                    f.OpenFile(default_dir + SecuredFileNamePdf + ".pdf");
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SecureFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SecureFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void ConvertPptxToPDFFile(String pptxFileName) {
        //create a Presentataion instance
        Presentation presentation = new Presentation();
        try {
            //load the sample PowerPoint file
            presentation.loadFromFile(default_dir + pptxFileName + ".pptx");
            //save to PDF file
            presentation.saveToFile(default_dir + pptxFileName + ".pdf", FileFormat.PDF);
        } catch (Exception ex) {
            Logger.getLogger(SecureFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        presentation.dispose();
    }

    public String GenerateStegoXlsxFile(List<ArrayList<String>> cipherStringlist, List<ArrayList<String>> coverStringlist) throws FileNotFoundException {

        String SecuredFileName = JOptionPane.showInputDialog("Save Stego Document as ") + ".xlsx";
        int length = Math.addExact(cipherStringlist.size(), coverStringlist.size());
        int lengthhhh = Math.max(cipherStringlist.size(), coverStringlist.size());
        List<ArrayList<String>> combo = new ArrayList<>();
        for(int j = 0; j < lengthhhh; j++ ){
            if(coverStringlist.size() > j)
                combo.add(coverStringlist.get(j));
            if(cipherStringlist.size() > j)
                combo.add(cipherStringlist.get(j));
        }

        try (FileOutputStream os = new FileOutputStream(new File(default_dir + SecuredFileName))) {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Sheet1");
            for (int i = 0; i < combo.size(); i++) {
                   Row row = sheet.createRow(i);
                   
                    for (int y = 0; y < combo.get(i).size(); y++) {
                        XSSFFont font1 = (XSSFFont) wb.createFont();
                        XSSFFont font2 = (XSSFFont) wb.createFont();
                        XSSFCell hssfCell = (XSSFCell) row.createCell(y);

                        XSSFRichTextString richString = new XSSFRichTextString(combo.get(i).get(y));

                        if(i%2 == 0){
                            font1.setColor(IndexedColors.BLACK.index); //black   
                            richString.applyFont(font1);
                        }else{
                           font2.setColor(IndexedColors.WHITE.index);  // white 
                           richString.applyFont(font2);
                           row.setZeroHeight(true);  
                        }
                        hssfCell.setCellValue(richString);   
                    }
            }
            wb.write(os);
            os.close();
            int i = JOptionPane.showConfirmDialog(null,
                    "Stego Document in .xlsx format Successfully Created \n Click YES to Open Document ",
                    "SELECT TYPE OF FILE", 0);
            if (i == 0) {
                boolean P = isKeyPresent(default_dir + SecuredFileName);
                if (P) {
                    f.OpenFile(default_dir + SecuredFileName);
                }
            }

        } catch (HeadlessException | IOException e) {
            System.out.println(e);
        }
        return null;
    }
}
