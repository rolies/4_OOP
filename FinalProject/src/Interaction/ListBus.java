/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interaction;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bima
 */
public class ListBus extends javax.swing.JFrame {

    /**
     * Creates new form ListBus
     */
    Connection con;
    ResultSet RsBus;
    Statement stm;
    
    Boolean ada = false;
    Boolean edit = false;
    private Object[][] dataTable = null;
    private String[] header = {"ID","Nama Bus","Jmlh Bus","Jmlh Bangku"};
    
    public ListBus() {
        initComponents();
        setLocationRelativeTo(null);
        open_db();
        baca_data();
        aktif(false);
        setTombol(true);
    }
    
    private void setField()
    {
        int row=TableBus.getSelectedRow();
        TextID.setText((String)TableBus.getValueAt(row,0));
        TextNamaBus.setText((String)TableBus.getValueAt(row,1));
        
        String nama_bus = Integer.toString((Integer)TableBus.getValueAt(row,2)); 
        TextJmlBus.setText(nama_bus);
        String jmlh_bgku = Integer.toString((Integer)TableBus.getValueAt(row,3)); 
        TextJmlBangku.setText(jmlh_bgku);
    }
    
        private void open_db()
    {       
        try{
            koneksiMysql kon = new koneksiMysql("localhost","root","","masterDB");
            con = kon.getConnection();
            //System.out.println("Berhasil ");
            }catch (Exception e) {
            System.out.println("Error : "+e);
    }
    }
        
   private void baca_data()
    {
        try{
            stm = con.createStatement();
            RsBus = stm.executeQuery("select * from listBus");
                
            ResultSetMetaData meta = RsBus.getMetaData();
            int col = meta.getColumnCount();            
            int baris = 0;
            while(RsBus.next()) {
                baris = RsBus.getRow();
            }
            
            dataTable = new Object[baris][col];
            int x = 0;
            RsBus.beforeFirst();
            while(RsBus.next()) {
                dataTable[x][0] = RsBus.getString("kode");
                dataTable[x][1] = RsBus.getString("nm_bus");
                dataTable[x][2] = RsBus.getInt("jmlh_Bus");
                dataTable[x][3] = RsBus.getInt("jmlh_Bangku");
                x++;
            }
            TableBus.setModel(new DefaultTableModel(dataTable,header));        
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
            
    private void kosong()
    {
        TextID.setText("");
        TextNamaBus.setText("");
        TextJmlBus.setText(""); 
        TextJmlBangku.setText("");
    }
    
    private void aktif(boolean x)
    {
        TextID.setEditable(x);
        TextNamaBus.setEditable(x);
        TextJmlBus.setEditable(x);
        TextJmlBangku.setEditable(x);
    }
    
        private void setTombol(boolean t)
    {
        Buttontambah.setEnabled(t);
        Buttonkoreksi.setEnabled(t);
        Buttonhapus.setEnabled(t);
        Buttonsimpan.setEnabled(!t);
        Buttonbatal.setEnabled(!t);
        Buttonkeluar.setEnabled(t);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TextJmlBangku = new javax.swing.JTextField();
        TextJmlBus = new javax.swing.JTextField();
        TextNamaBus = new javax.swing.JTextField();
        TextID = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableBus = new javax.swing.JTable();
        Buttonkeluar = new javax.swing.JButton();
        Buttontambah = new javax.swing.JButton();
        Buttonsimpan = new javax.swing.JButton();
        Buttonkoreksi = new javax.swing.JButton();
        Buttonhapus = new javax.swing.JButton();
        Buttonbatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jLabel1.setText("ID");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jLabel2.setText("Nama Bus");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jLabel3.setText("Jumlah Bus");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jLabel4.setText("Jumlah Bangku");

        TextID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextIDActionPerformed(evt);
            }
        });

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        TableBus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ));
        TableBus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableBusMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableBus);

        Buttonkeluar.setText("Keluar");
        Buttonkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonkeluarActionPerformed(evt);
            }
        });

        Buttontambah.setText("Tambah");
        Buttontambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtontambahActionPerformed(evt);
            }
        });

        Buttonsimpan.setText("Simpan");
        Buttonsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonsimpanActionPerformed(evt);
            }
        });

        Buttonkoreksi.setText("Koreksi");
        Buttonkoreksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonkoreksiActionPerformed(evt);
            }
        });

        Buttonhapus.setText("Hapus");
        Buttonhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonhapusActionPerformed(evt);
            }
        });

        Buttonbatal.setText("Batal");
        Buttonbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonbatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TextID, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TextNamaBus, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(TextJmlBus, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(TextJmlBangku, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Buttontambah)
                        .addGap(18, 18, 18)
                        .addComponent(Buttonsimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Buttonhapus)
                        .addGap(18, 18, 18)
                        .addComponent(Buttonkoreksi)
                        .addGap(18, 18, 18)
                        .addComponent(Buttonbatal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Buttonkeluar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(TextID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(TextNamaBus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(TextJmlBus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(TextJmlBangku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Buttontambah)
                    .addComponent(Buttonsimpan)
                    .addComponent(Buttonkoreksi)
                    .addComponent(Buttonhapus)
                    .addComponent(Buttonbatal)
                    .addComponent(Buttonkeluar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonkeluarActionPerformed
        dispose();
        new fromMenu().setVisible(true);
    }//GEN-LAST:event_ButtonkeluarActionPerformed

    private void ButtontambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtontambahActionPerformed
        aktif(true);
        setTombol(false);
        kosong();
        TextID.requestFocusInWindow();
        edit = false;
    }//GEN-LAST:event_ButtontambahActionPerformed

    private void ButtonsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonsimpanActionPerformed
        String tKode=TextID.getText();
        String tNama=TextNamaBus.getText();
        int tJmlhBus=Integer.parseInt(TextJmlBus.getText());
        int tJmlhBangku=Integer.parseInt(TextJmlBangku.getText());
        
        try{
            if (edit==true)
            {
                stm.executeUpdate("update listBus set nm_bus='"+tNama+"',jmlh_Bus="+tJmlhBus+", jmlh_Bangku="+tJmlhBangku+" where kode='" +tKode+ "'");
            }else
            {
                stm.executeUpdate("INSERT into listBus VALUES('"+tKode+"','"+tNama+
                    "',"+tJmlhBus+","+tJmlhBangku+")");
            }
            TableBus.setModel(new DefaultTableModel(dataTable,header));
            baca_data();
            aktif(false);
            setTombol(true);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_ButtonsimpanActionPerformed

    private void ButtonkoreksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonkoreksiActionPerformed
        edit=true;
        aktif(true);
        setTombol(false);
        TextID.setEditable(false);
    }//GEN-LAST:event_ButtonkoreksiActionPerformed

    private void ButtonhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonhapusActionPerformed

        try{
            String sql="delete from listBus where kode='" + TextID.getText()+ "'";
            stm.executeUpdate(sql);
            baca_data();
            kosong();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_ButtonhapusActionPerformed

    private void ButtonbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonbatalActionPerformed
        aktif(false);
        setTombol(true);
    }//GEN-LAST:event_ButtonbatalActionPerformed

    private void TextIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextIDActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void TableBusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableBusMouseClicked
        setField();
    }//GEN-LAST:event_TableBusMouseClicked

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListBus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListBus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListBus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListBus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListBus().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Buttonbatal;
    private javax.swing.JButton Buttonhapus;
    private javax.swing.JButton Buttonkeluar;
    private javax.swing.JButton Buttonkoreksi;
    private javax.swing.JButton Buttonsimpan;
    private javax.swing.JButton Buttontambah;
    private javax.swing.JTable TableBus;
    private javax.swing.JTextField TextID;
    private javax.swing.JTextField TextJmlBangku;
    private javax.swing.JTextField TextJmlBus;
    private javax.swing.JTextField TextNamaBus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
