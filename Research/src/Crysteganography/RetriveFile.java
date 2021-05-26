/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crysteganography;

import static Crysteganography.BlowfishOperation.Decrpt_Blow;
import static Crysteganography.BlowfishOperation.KeyErrorMessage;
import static Crysteganography.BlowfishOperation.isKeyPresent;
import static Crysteganography.Start.KeyLocation;
import static Crysteganography.Start.default_dir;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author OLATUNDE YUSUF
 */
public class RetriveFile {

    public RetriveFile() {

    }
    ProcessFile f = new ProcessFile();
    // private final String KeyLocation = default_dir +"BlowFish key/BlowFish Secret Key.key";

    public List<ArrayList<String>> ExtractCipherOnlyPptx(List<ArrayList<String>> steg, List<ArrayList<String>> cov) throws FileNotFoundException {
        List<ArrayList<String>> Cipher = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < steg.size(); i++) {
            if (i < cov.size()) {
                Cipher.add((ArrayList<String>) ExtractCipherOnly(steg.get(i), cov.get(i)));
            }
        }

        if (Cipher.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Stego Document and Cover Document have no Difference \n "
                    + "Please comfirm and Resubmit");
        }
        return Cipher;
    }

    //********************************************************************************//  
    ///start Extract Cipher from Stego_String list////
    public List<String> ExtractCipherOnly(List<String> steg, List<String> cov) throws FileNotFoundException {
        List<String> Cipher = null, ExtractCipher;
        ExtractCipher = steg;
        boolean v = ExtractCipher.removeAll(cov);
        if (v) {
            Cipher = ExtractCipher;
        } else {
            JOptionPane.showMessageDialog(null, "Stego Document and Cover Document have no Difference \n "
                    + "Please comfirm and Resubmit");
        }
        return Cipher;
    }

    ///end Extract Cipher from Stego_String list////
//********************************************************************************//    
    ///start stego cipher decryption////
    public List<String> DecryptCipherText(List<String> RoughcipherText, int receiverPrivateKey, int senderPublicKey) throws FileNotFoundException {
        List<String> Text = new ArrayList<>();
        ElGamalOperation elgamal = new ElGamalOperation();
        boolean P = isKeyPresent(KeyLocation);
        SecretKey keyy = null;
        if (P) {
            ProcessFile h = new ProcessFile();
            List<String> cipherText = h.TreatCipher(RoughcipherText);
            for (int i = 0; i <= cipherText.size() - 1; i++) {
                String seperate[] = cipherText.get(i).split("_-_-_");
                for (int x = 0; x < seperate.length - 1; x++) {
                    if (keyy == null) {
                        keyy = elgamal.decryptionOperation(seperate[1], receiverPrivateKey, senderPublicKey);
                    }
                    ObjectInputStream inputStream;
                    try {
                        inputStream = new ObjectInputStream(new FileInputStream(KeyLocation));
                        final SecretKey keyb = (SecretKey) inputStream.readObject();
                        Text.add(Decrpt_Blow(seperate[0], keyb));
                    } catch (Exception ex) {
                        Logger.getLogger(RetriveFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                seperate = new String[0];
            }
        } else {
            KeyErrorMessage();
        }
        return Text;
    }
    ///end stego cipher decryption////

//********************************************************************************//
    ///start prepare retrived Secret document////
    public String PrepareRetrivedSecreteDocx(List<String> Text) throws FileNotFoundException {
        try {
            String RetriveFileName = JOptionPane.showInputDialog("Save Retrive Document as ") + ".docx";
            //Blank Document
            XWPFDocument retrivedocument = new XWPFDocument();
            //Write the Document in file system
            FileOutputStream retriveout = new FileOutputStream(new File(default_dir + RetriveFileName));
            for (int i = 0; i <= Text.size() - 1; i++) {
                //create Paragraph
                XWPFParagraph retriveparagraph = retrivedocument.createParagraph();
                XWPFRun run = retriveparagraph.createRun();
                run.setFontSize(14);
                run.setFontFamily("Times New Roman");
                run.setText(Text.get(i));
                run.setColor("000000");
            }
            retrivedocument.write(retriveout);
            retriveout.close();
            int r = JOptionPane.showConfirmDialog(null,
                    "Secret Document Retrived Successfully \n Click YES to Open Document ",
                    "SECRET FILE STATUS", 0);
            if (r == 0) {
                if (isKeyPresent(default_dir + RetriveFileName)) {
                    f.OpenFile(default_dir + RetriveFileName);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Unable to locate the Secret Document Retrived \n Please try Again ");
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(RetriveFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    ///end prepare retrived Secret document////

    ///start prepare retrived Secret document////
    public String PrepareRetrivedSecretePptx(List<ArrayList<String>> Text) throws FileNotFoundException {
        try {
            String RetriveFileName = JOptionPane.showInputDialog("Save Retrive Document as ") + ".pptx";
            XWPFDocument retrivedocument = new XWPFDocument(); //Blank Document
            XMLSlideShow ppt = new XMLSlideShow();
            //Write the Document in file system
            try (FileOutputStream out = new FileOutputStream(new File(default_dir + RetriveFileName))) {
                //Star wrtting to docx document from the arrary list in the argument
                for (int i = 0; i <= Text.size() - 1; i++) {//slide level
                    XSLFSlideMaster defaultMaster = ppt.getSlideMasters().get(0);
                    XSLFSlideLayout layout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
                    XSLFSlide slide = ppt.createSlide(layout);
                    XSLFTextShape titleShape = slide.getPlaceholder(0);
                    XSLFTextShape contentShape = slide.getPlaceholder(1);
                    titleShape.clearText();
                    contentShape.clearText();
                    titleShape.setText(Text.get(i).get(0));
                    for (int y = 1; y <= Text.get(i).size() - 1; y++) { //paragraph level
                        // 1st paragraph 
                        XSLFTextParagraph p = contentShape.addNewTextParagraph();
                        XSLFTextRun r = p.addNewTextRun();
                        r.setText(Text.get(i).get(y));
                        r.setFontColor(Color.black);
                        r.setFontSize(18.0);
                    }
                }
                ppt.write(out);
                out.close();
                
                int r = JOptionPane.showConfirmDialog(null,
                        "Secret Document Retrived Successfully \n Click YES to Open Document ",
                        "SECRET FILE STATUS", 0);
                if (r == 0) {
                    if (isKeyPresent(default_dir + RetriveFileName)) {
                        f.OpenFile(default_dir + RetriveFileName);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Unable to locate the Secret Document Retrived \n Please try Again ");
                    }
                }
                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(RetriveFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    ///end prepare retrived Secret document////

    
        public String PrepareRetrivedSecreteXlsx(List<ArrayList<String>> Text) throws FileNotFoundException {
            
        String SecuredFileName = JOptionPane.showInputDialog("Save Retrived Document as ") + ".xlsx";

        try (FileOutputStream os = new FileOutputStream(new File(default_dir + SecuredFileName))) {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Sheet1");
            for (int i = 0; i < Text.size(); i++) {
                   Row row = sheet.createRow(i);
                    for (int y = 0; y < Text.get(i).size(); y++) {
                        XSSFFont font1 = (XSSFFont) wb.createFont();
                        XSSFCell hssfCell = (XSSFCell) row.createCell(y);
                        XSSFRichTextString richString = new XSSFRichTextString(Text.get(i).get(y));
                        font1.setColor(IndexedColors.BLACK.index); //black   
                        richString.applyFont(font1);
                        hssfCell.setCellValue(richString);   
                    }
            }
            wb.write(os);
            os.close();
            int i = JOptionPane.showConfirmDialog(null,
                    "Secrete Document in .xlsx format Successfully Retrived \n Click YES to Open Document ",
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
