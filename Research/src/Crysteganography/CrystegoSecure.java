/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Crysteganography;

import static Crysteganography.BlowfishOperation.isKeyPresent;
import static Crysteganography.ProcessFile.DocumentErrorMessage;
import static Crysteganography.ProcessFile.SetDocumentDir;
import static Crysteganography.Start.default_Icon;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author OLATUNDE YUSUF
 */
public class CrystegoSecure extends javax.swing.JFrame {

    /**
     * Creates new form Crysteganography
     */
    public CrystegoSecure() {
        initComponents();
    setIcon();
        this.getContentPane().setBackground(new Color(75,144,203));
    //    this.getContentPane().setBackground(new Color(10,66,159));
    
    }
private  void setIcon(){
    this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(default_Icon)));
    }  
    
    private void performCryStegoForPptx(){
        
    SecureFile t = new SecureFile();
    ProcessFile f = new ProcessFile();
     List<String> SecretText = null,CoverText = null, cipherDocx, Cipher;
     List<ArrayList<String>> PptxSecretText = null, PptxCoverText = null;
     List<ArrayList<String>>  PptxCipher = new ArrayList<ArrayList<String>>();

                try {
                        
                        PptxSecretText = f.PptxFileExtraction(s_file_path.getText());
                        PptxCoverText = f.PptxFileExtraction(c_file_path.getText());
                        boolean secretContent = PptxSecretText.stream().allMatch(x -> x != null && !x.isEmpty());
                        boolean coverContent = PptxCoverText.stream().allMatch(x -> x != null && !x.isEmpty());

                   if (secretContent && coverContent){
                       //El-Gamal Encryption start here.{Blowfish SECRETE KEY WILL BE ENCRYPTED}
                        final int pk = Integer.parseInt(JOptionPane.showInputDialog("Supply your Private key"));
                        final int puk = Integer.parseInt(JOptionPane.showInputDialog("Supply the reciever's public key"));
//                        
                        for (ArrayList<String> list : PptxSecretText) {
                            Cipher = t.EncryptPlainTextList(list, puk, pk); //Cryptography of text paragrah
                            if(!Cipher.isEmpty())
                                PptxCipher.add((ArrayList<String>) Cipher);
                        }
//                     Cipher = t.EncryptPlainTextList(SecretText, puk, pk); //Cryptography of text paragrah
                     if (PptxCipher.stream().allMatch(x -> x != null && !x.isEmpty())){
                            t.GenerateStegoPptxFile(PptxCipher, PptxCoverText); //Appling font and size steganography get .docx
                     } else{ 
                  JOptionPane.showMessageDialog(null, "Encryption Problem Occur \n or Please Try Again ");}
                   }else{
                   JOptionPane.showMessageDialog(null, "Secret & Cover document must not be Empty \n Please Confirm  Try Again ");
                   }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CrystegoSecure.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    
    private void performCryStegoForDocx(){
        
    SecureFile t = new SecureFile();
    ProcessFile f = new ProcessFile();
     List<String> SecretText = null,CoverText = null, cipherDocx, Cipher;
     List<ArrayList<String>> PpptSecretText = null;
                try {
                    if(s_file_path.getText().contains(".pptx") ){
                        PpptSecretText = f.PptxFileExtraction(s_file_path.getText());
                    }else{
                     SecretText = f.DocxFileExtraction(s_file_path.getText());
                     CoverText = f.DocxFileExtraction(c_file_path.getText());
                    }
                     
                   if (!SecretText.isEmpty()&& !CoverText.isEmpty()){
                       
                       //El-Gamal Encryption start here.{Blowfish SECRETE KEY WILL BE ENCRYPTED}
                        int pk = Integer.parseInt(JOptionPane.showInputDialog("Supply your Private key"));
                        int puk = Integer.parseInt(JOptionPane.showInputDialog("Supply the reciever's public key"));
        
                     Cipher = t.EncryptPlainTextList(SecretText, puk, pk); //Cryptography of text paragrah
                     if (!Cipher.isEmpty()){
                     int r = JOptionPane.showConfirmDialog(rootPane,  
                           "Click YES for Stego Document in .docx \n or NO for .pdf ", 
                           "SELECT TYPE OF FILE", 0);
                   if (r == 0){
                    t.GenerateStegoDocxFile(Cipher, CoverText); //Appling font and size steganography get .docx
                   }else{
                       t.GenerateStegoPDFFile(Cipher, CoverText); //Appling font and size steganography get .pdf
                   }
                     } else{ 
                  JOptionPane.showMessageDialog(null, "Encryption Problem Occur \n or Please Try Again ");}
                   }else{
                   JOptionPane.showMessageDialog(null, "Secret & Cover document must not be Empty \n Please Confirm  Try Again ");
                   }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CrystegoSecure.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    
    private void performCryStegoForXlsx(){
        SecureFile t = new SecureFile();
        ProcessFile f = new ProcessFile();
        List<String> SecretText = null,CoverText = null, cipherDocx, Cipher;
        List<ArrayList<String>> XlsxSecretText = null, XlsxCoverText = null;
        List<ArrayList<String>>  XlsxCipher = new ArrayList<ArrayList<String>>();
        
        try {
            XlsxSecretText = f.XlsxFileExtraction(s_file_path.getText());
            XlsxCoverText = f.XlsxFileExtraction(c_file_path.getText());
            
            boolean secretContent = XlsxSecretText.stream().allMatch(x -> x != null && !x.isEmpty());
            boolean coverContent = XlsxCoverText.stream().allMatch(x -> x != null && !x.isEmpty());

            if(secretContent && coverContent){
                 //El-Gamal Encryption start here.{Blowfish SECRETE KEY WILL BE ENCRYPTED}
                        final int pk = Integer.parseInt(JOptionPane.showInputDialog("Supply your Private key"));
                        final int puk = Integer.parseInt(JOptionPane.showInputDialog("Supply the reciever's public key"));
//                        
                        for (ArrayList<String> list : XlsxSecretText) {
                            Cipher = t.EncryptPlainTextList(list, puk, pk); //Cryptography of text paragrah
                            if(!Cipher.isEmpty())
                                XlsxCipher.add((ArrayList<String>) Cipher);
                        }
//                     Cipher = t.EncryptPlainTextList(SecretText, puk, pk); //Cryptography of text paragrah
                     if (XlsxCipher.stream().allMatch(x -> x != null && !x.isEmpty())){
                            t.GenerateStegoXlsxFile(XlsxCipher, XlsxCoverText); //Appling font and size steganography get .docx
                     } else{ 
                  JOptionPane.showMessageDialog(null, "Encryption Problem Occur \n or Please Try Again ");}
                   }else{
                   JOptionPane.showMessageDialog(null, "Secret & Cover document must not be Empty \n Please Confirm  Try Again ");
                   }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrystegoSecure.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        s_file_path = new javax.swing.JTextField();
        c_file_path = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SECURE DOCUMENT");
        setBackground(new java.awt.Color(153, 153, 255));
        getContentPane().setLayout(null);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Choose Secret File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(30, 220, 140, 40);

        s_file_path.setText("CHOOSE SECRET DOCUMENT");
        getContentPane().add(s_file_path);
        s_file_path.setBounds(180, 220, 340, 40);

        c_file_path.setText("CHOOSE COVER DOCUMENT");
        getContentPane().add(c_file_path);
        c_file_path.setBounds(180, 290, 340, 40);

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton8.setText("Choose Cover File");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton8);
        jButton8.setBounds(30, 290, 140, 40);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton9.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/L11.png"))); // NOI18N
        jButton9.setText("SECURE FILE");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4);
        jPanel4.setBounds(180, 370, 241, 90);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/1.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 20, 540, 170);

        setSize(new java.awt.Dimension(587, 553));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        
         boolean n = isKeyPresent(s_file_path.getText());
         boolean m = isKeyPresent(c_file_path.getText());
         
        if(n&&m){
            if(s_file_path.getText().contains(".pptx") && c_file_path.getText().contains(".pptx") ){
                performCryStegoForPptx();
            }else if(s_file_path.getText().contains(".xlsx") && c_file_path.getText().contains(".xlsx")){
                performCryStegoForXlsx();
            }else if(s_file_path.getText().contains(".docx") && c_file_path.getText().contains(".docx")){
                performCryStegoForDocx();
            }else{
                JOptionPane.showMessageDialog(null, "Both file should be of the same file format e.g: \n \t .docx \n \t .pptx \n \t .xlsx");
            }

        }else{ 
          DocumentErrorMessage();
        }    
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
         SetDocumentDir(c_file_path); 
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         SetDocumentDir(s_file_path); 
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CrystegoSecure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrystegoSecure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrystegoSecure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrystegoSecure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrystegoSecure().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField c_file_path;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField s_file_path;
    // End of variables declaration//GEN-END:variables
}
