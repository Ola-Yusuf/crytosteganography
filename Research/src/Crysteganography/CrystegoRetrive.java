/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crysteganography;

import static Crysteganography.BlowfishOperation.Decrpt_Blow;
import static Crysteganography.BlowfishOperation.KeyErrorMessage;
import static Crysteganography.BlowfishOperation.isKeyPresent;
import static Crysteganography.ProcessFile.DocumentErrorMessage;
import static Crysteganography.ProcessFile.SetDocumentDir;
import static Crysteganography.Start.KeyLocation;
import static Crysteganography.Start.default_Icon;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

/**
 *
 * @author OLATUNDE YUSUF
 */
public class CrystegoRetrive extends javax.swing.JFrame {

    /**
     * Creates new form Crysteganography
     */
    public CrystegoRetrive() {
        initComponents();
        setIcon();
        this.getContentPane().setBackground(new Color(75, 144, 203));
    }

    private void setIcon() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(default_Icon)));
    }

    public void performRetrivePptx() {
        RetriveFile bat = new RetriveFile();
        ProcessFile f = new ProcessFile();
        List<String> StegoPdf = null, Text = new ArrayList<>();
        List<ArrayList<String>> StegoKey, Stego,
                Cipher = new ArrayList<ArrayList<String>>(),
                PptxText = new ArrayList<ArrayList<String>>();
        try {
            if (stego_fileName_retr.getText().contains(".pptx")) {
                Stego = f.PptxFileExtraction(stego_fileName_retr.getText());
                StegoKey = f.PptxFileExtraction(c_fileName_retr.getText());
                Cipher = bat.ExtractCipherOnlyPptx(Stego, StegoKey);
            } else if (stego_fileName_retr.getText().contains(".pdf")) {
//                StegoPdf = f.PDFFileExtraction(stego_fileName_retr.getText());
//                  StegoKey = f.PptxFileExtraction(c_fileName_retr.getText());
////                Cipher = f.CapturePDFCipher(StegoPdf);
//                System.out.println("Cipher Only from pdf");
//                System.out.println(Cipher);
            }
            if (!Cipher.isEmpty()) {
                //El-Gamal Encryption start here.{Blowfish SECRETE KEY WILL BE ENCRYPTED}
                int pk = Integer.parseInt(JOptionPane.showInputDialog("Supply your Private key"));
                int puk = Integer.parseInt(JOptionPane.showInputDialog("Supply the sender's public key"));
                for (List<String> list : Cipher) {
                    Text = bat.DecryptCipherText(list, pk, puk); //Cryptography of text paragrah
                    if (!Text.isEmpty()) {
                        PptxText.add((ArrayList<String>) Text);
                    }
                }

                if (PptxText.stream().allMatch(x -> x != null && !x.isEmpty())) {
                    bat.PrepareRetrivedSecretePptx(PptxText);
                } else {
                    JOptionPane.showMessageDialog(null, "Decrypted Cipher Text Can't be Empty \n "
                            + "Please Try Again");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error Extracting Cipher Text from Stego Document \n "
                        + "Please Try Again");
            }

        } catch (Exception e) {

        }
    }

    public void performRetriveXlsx() {
        RetriveFile bat = new RetriveFile();
        ProcessFile f = new ProcessFile();
        List<String> StegoPdf = null, Text = new ArrayList<>();
        List<ArrayList<String>> StegoKey, Stego,
                Cipher = new ArrayList<ArrayList<String>>(),
                XlsxText = new ArrayList<ArrayList<String>>();
       try {
        if (stego_fileName_retr.getText().contains(".xlsx")) {
            
                Stego = f.XlsxFileExtraction(stego_fileName_retr.getText());
                StegoKey = f.XlsxFileExtraction(c_fileName_retr.getText());
                Stego.removeAll(StegoKey);
                Cipher = Stego;
//
//                System.out.println(Stego);
//                System.out.println(StegoKey);
//                System.out.println(Cipher);


                if (!Cipher.isEmpty()) {
                    //El-Gamal Encryption start here.{Blowfish SECRETE KEY WILL BE ENCRYPTED}
                    int pk = Integer.parseInt(JOptionPane.showInputDialog("Supply your Private key"));
                    int puk = Integer.parseInt(JOptionPane.showInputDialog("Supply the sender's public key"));
                    for (List<String> list : Cipher) {
                        Text = bat.DecryptCipherText(list, pk, puk); //Cryptography of text paragrah
                        if (!Text.isEmpty()) {
                            XlsxText.add((ArrayList<String>) Text);
                        }
                    }

                    if (XlsxText.stream().allMatch(x -> x != null && !x.isEmpty())) {
                        bat.PrepareRetrivedSecreteXlsx(XlsxText);
                    } else {
                        JOptionPane.showMessageDialog(null, "Decrypted Cipher Text Can't be Empty \n "
                                + "Please Try Again");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error Extracting Cipher Text from Stego Document \n "
                            + "Please Try Again");
                }


            } else if (stego_fileName_retr.getText().contains(".pdf")) {
//                StegoPdf = f.PDFFileExtraction(stego_fileName_retr.getText());
//                  StegoKey = f.PptxFileExtraction(c_fileName_retr.getText());
////                Cipher = f.CapturePDFCipher(StegoPdf);
//                System.out.println("Cipher Only from pdf");
//                System.out.println(Cipher);
            }
            
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CrystegoRetrive.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }

    public void performRetriveDocx() {
////////// Start Retriving Secret content from file /////////////
        RetriveFile bat = new RetriveFile();
        ProcessFile f = new ProcessFile();
        List<String> StegoKey, Stego = null, Cipher = null, Text = new ArrayList<>();
        try {
            if (stego_fileName_retr.getText().contains(".docx")) {
                Stego = f.DocxFileExtraction(stego_fileName_retr.getText());
                StegoKey = f.DocxFileExtraction(c_fileName_retr.getText());
                Cipher = bat.ExtractCipherOnly(Stego, StegoKey);
            } else if (stego_fileName_retr.getText().contains(".pdf")) {
                Stego = f.PDFFileExtraction(stego_fileName_retr.getText());
                Cipher = f.CapturePDFCipher(Stego);
            }
            if (Cipher != null) {
                //El-Gamal Encryption start here.{Blowfish SECRETE KEY WILL BE ENCRYPTED}
                int pk = Integer.parseInt(JOptionPane.showInputDialog("Supply your Private key"));
                int puk = Integer.parseInt(JOptionPane.showInputDialog("Supply the sender's public key"));
                Text = bat.DecryptCipherText(Cipher, pk, puk);
                if (Text.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Decrypted Cipher Text Can't be Empty \n "
                            + "Please Try Again");
                } else {
                    bat.PrepareRetrivedSecreteDocx(Text);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error Extracting Cipher Text from Stego Document \n "
                        + "Please Try Again");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrystegoRetrive.class.getName()).log(Level.SEVERE, null, ex);
        }
        ////////// END Retriving Secret content from file ///////////// 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        stego_fileName_retr = new javax.swing.JTextField();
        c_fileName_retr = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RETRIVE DOCUMENT");
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(null);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton9.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/L22.png"))); // NOI18N
        jButton9.setText("RETRIVE FILE");
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
        jPanel4.setBounds(190, 350, 241, 90);

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton8.setText("Choose Stego File");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton8);
        jButton8.setBounds(30, 210, 140, 44);

        jButton10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton10.setText("Choose Cover File");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton10);
        jButton10.setBounds(28, 280, 140, 44);

        stego_fileName_retr.setText("CHOOSE STEGO DOCUMENT");
        getContentPane().add(stego_fileName_retr);
        stego_fileName_retr.setBounds(182, 210, 350, 44);

        c_fileName_retr.setText("CHOOSE COVER  DOCUMENT");
        getContentPane().add(c_fileName_retr);
        c_fileName_retr.setBounds(182, 280, 350, 42);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/1_2.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 20, 540, 170);

        setSize(new java.awt.Dimension(589, 548));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        SetDocumentDir(stego_fileName_retr);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        SetDocumentDir(c_fileName_retr);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        boolean n = isKeyPresent(stego_fileName_retr.getText());
        boolean m = isKeyPresent(c_fileName_retr.getText());

        if (n && m) {
            if ((stego_fileName_retr.getText().contains(".pptx") || stego_fileName_retr.getText().contains(".pdf"))
                    && c_fileName_retr.getText().contains(".pptx")) {
                performRetrivePptx();
            } else if ((stego_fileName_retr.getText().contains(".xlsx") || stego_fileName_retr.getText().contains(".pdf"))
                    && c_fileName_retr.getText().contains(".xlsx")) {
                performRetriveXlsx();
            } else if ((stego_fileName_retr.getText().contains(".docx") || stego_fileName_retr.getText().contains(".pdf"))
                    && c_fileName_retr.getText().contains(".docx")) {
                performRetriveDocx();
            } else {
                JOptionPane.showMessageDialog(null, "Both file should be of the same file format e.g: \n \t .docx \n \t .pptx \n \t .xlsx");
                DocumentErrorMessage();
            }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

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
            java.util.logging.Logger.getLogger(CrystegoRetrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrystegoRetrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrystegoRetrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrystegoRetrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrystegoRetrive().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField c_fileName_retr;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField stego_fileName_retr;
    // End of variables declaration//GEN-END:variables
}
