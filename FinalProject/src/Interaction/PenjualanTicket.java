/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interaction;

import java.sql.*;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bima
 */

public class PenjualanTicket extends javax.swing.JFrame {
    
    Connection con;
    ResultSet RsBrg;
    Statement stm;
    PreparedStatement pst;
    
    String tanggal;
    String sKode;
    String sRegion;
    String sTujuan;
    String nm_bus;
    /**
     * Creates new form PenjualanTicket
     */
    DefaultTableModel tableModel = new DefaultTableModel(
            new Object [][] {}, 
            new String [] { "ID Bus", "Nama Bus","Tujuan","Harga" 
            }); 
    
    public PenjualanTicket() {
        initComponents();
        setLocationRelativeTo(null);
        open_db();
        
        inisialisasi_tabel();
        kosong();
        
        
        Spinnertgl.setEditor(new JSpinner.DateEditor(Spinnertgl, "yyyy/MM/dd")); 
    }
    private void open_db(){       
        try{
            koneksiMysql kon = new koneksiMysql("localhost","root","","masterDB");
            con = kon.getConnection();
            //System.out.println("Berhasil ");
            }catch (Exception e) {
            System.out.println("Error : "+e);
        }
        }
    private void FillcomboID(){
	try {
		String sql = "select * from listPerjalanan where tujuan='"+sTujuan+"'";
		pst=con.prepareStatement(sql);
		RsBrg=pst.executeQuery();
		ComboID.removeAllItems();
		while(RsBrg.next()){
			String kode = RsBrg.getString("kd_prjlnan");
			ComboID.addItem(kode);
                        
		}
	}catch(Exception e){
	JOptionPane.showMessageDialog(null, e); 
                }
}
    
    private void detail_bis(String xkode) { 
        try{ 
            stm=con.createStatement(); 
            ResultSet rs=stm.executeQuery("select * from listPerjalanan where kd_prjlnan='"+xkode+"'");
            rs.beforeFirst(); 
            while(rs.next()) { 
                nm_bus = rs.getString(2); 
                TextTujuan.setText(rs.getString(5).trim()); 
                TextHarga.setText(Double.toString((Double)rs.getDouble(6)));
                stm=con.createStatement(); 
                ResultSet rn=stm.executeQuery("select * from listBus where kode='"+nm_bus+"'");
                rn.beforeFirst();
                while(rn.next()) { 
                    TextnmBus.setText(rn.getString(2).trim());
                }rn.close(); 
            } 
            rs.close(); 
        } catch(SQLException e) {
        System.out.println("Error : "+e);
}
}
    private void filter_region(String xRegion) { 
        try{ 
            stm=con.createStatement(); 
            ResultSet rfr=stm.executeQuery("select * from listPerjalanan where region='"+xRegion+"'");
            rfr.beforeFirst();
            ComboTujuan.removeAllItems();
            while(rfr.next()) {  
                ComboTujuan.addItem(rfr.getString(5).trim());
                } 
            rfr.close(); 
        } catch(SQLException e) {
        System.out.println("Error : "+e);
}
}
    //method set model tabel 
    public void inisialisasi_tabel(){
        TabelTrans.setModel(tableModel);
    }
    
     private void kosong()
    {
        
        ComboID.setSelectedIndex(-1);
        ComboWaktu.setSelectedIndex(-1);
        ComboRegion.setSelectedIndex(-1);
        ComboWaktu.setSelectedIndex(-1);
        TextTujuan.setText("");
        TextnmBus.setText("");
        TextHarga.setText("");
   }
     
     private void aktif(boolean x) {
        ComboID.setEnabled(x); 
        ComboRegion.setEnabled(x); 
        Spinnertgl.setEnabled(x); 
}
     private void setTombol(boolean t) {
        btnNew.setEnabled(t); 
        btnSimpan.setEnabled(!t); 
        btnBatal.setEnabled(!t); 
        btnKeluar.setEnabled(t); 
        //btnHapus.setEnabled(!t);
}
   
     private void nomor_trans() {
	try{ 
		stm=con.createStatement(); 
		ResultSet rs=stm.executeQuery("select ID_trans from listTransaksi"); 
		int brs=0; while(rs.next()) { 
			brs=rs.getRow(); 
		} 
		if(brs==0) 
			TextIDtrans.setText("1"); 
		else {
			int nom=brs+1; 
			TextIDtrans.setText(Integer.toString(nom));
		}
		rs.close();
		} 
	catch(SQLException e) {
		System.out.println("Error : "+e);
	}
}
     
    //method simpan detail jual di tabel 
private void setField() {
	try { 
		String tKode=ComboID.getSelectedItem().toString(); 
		String tNama=TextnmBus.getText();
                String tTujuan=TextTujuan.getText();
		double hrg=Double.parseDouble(TextHarga.getText()); 
		tableModel.addRow(new Object[]{tKode,tNama,tTujuan,hrg}); 
		inisialisasi_tabel(); 
	} catch(Exception e) {
			System.out.println("Error : "+e);
	}
} 

//method simpan transaksi penjualan pada table di MySql 
private void simpan_transaksi() {
	try{ 
	String xTrans=TextIDtrans.getText(); 
	format_tanggal(); 
        String xNm_customer=TextnmCustomer.getText(); 
        String xContact=TextContact.getText(); 
        //Penghitung Total Belu, Di buat
        double xHrgtot=300000;
	//String xkode=cmbKd_Kons.getSelectedItem().toString(); 
	String msql="insert into listTransaksi values('"+xTrans+"','"+tanggal+"', '"+xNm_customer+"', '"+xContact+"', "+xHrgtot+")"; stm.executeUpdate(msql);
	for(int i=0;i<TabelTrans.getRowCount();i++) {
		String xKd_route=(String)TabelTrans.getValueAt(i,0); 
                //Comuting Nomer Bangku belum di buat
                int xBgku = 3;
		double xHrg=(Double)TabelTrans.getValueAt(i,3); 
		String zsql="insert into listTransaksiDetail values('"+xTrans+"','"+xKd_route+"',"+xBgku+","+xHrg+")"; stm.executeUpdate(zsql);
		}
	} catch(SQLException e) {
		System.out.println("Error : "+e);
	}
}
//method membuat format tanggal sesuai dengan MySQL 
private void format_tanggal() {
	String DATE_FORMAT = "yyyy-MM-dd"; 
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT); 
	Calendar c1 = Calendar.getInstance(); 
	int year=c1.get(Calendar.YEAR); 
	int month=c1.get(Calendar.MONTH)+1; 
	int day=c1.get(Calendar.DAY_OF_MONTH); 
	tanggal=Integer.toString(year)+"-"+Integer.toString(month)+"- "+Integer.toString(day); 
}
//method simpan transaksi penjualan pada table di MySql private 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TextIDtrans = new javax.swing.JTextField();
        TextnmCustomer = new javax.swing.JTextField();
        Spinnertgl = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        ComboID = new javax.swing.JComboBox();
        TextTujuan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelTrans = new javax.swing.JTable();
        btnNew = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        TextnmBus = new javax.swing.JTextField();
        TextHarga = new javax.swing.JTextField();
        btnBatal = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        TextTotal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        ComboRegion = new javax.swing.JComboBox();
        ComboTujuan = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ComboWaktu = new javax.swing.JComboBox();
        TextContact = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(55, 198, 24));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        jLabel1.setText("ID trasnsaksi");

        jLabel2.setText("Tanggal");

        jLabel3.setText("Nama Customer");

        jLabel4.setText("Contact");

        Spinnertgl.setModel(new javax.swing.SpinnerDateModel());

        jPanel2.setBackground(new java.awt.Color(106, 248, 131));

        ComboID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ComboIDFocusGained(evt);
            }
        });
        ComboID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboIDMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ComboIDMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ComboIDMousePressed(evt);
            }
        });
        ComboID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboIDActionPerformed(evt);
            }
        });

        TabelTrans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TabelTrans);

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnKeluar.setText("keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");

        btnCetak.setText("Cetak");

        jLabel8.setText("Total");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jButton2.setText("Delete");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(35, 35, 35)
                        .addComponent(TextTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAdd))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnNew)
                            .addGap(18, 18, 18)
                            .addComponent(btnSimpan)
                            .addGap(18, 18, 18)
                            .addComponent(btnBatal)
                            .addGap(18, 18, 18)
                            .addComponent(btnCetak)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnKeluar))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(ComboID, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TextnmBus, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(TextTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(TextHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboID, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextnmBus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(jButton2))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnSimpan)
                    .addComponent(btnKeluar)
                    .addComponent(btnBatal)
                    .addComponent(btnCetak))
                .addGap(173, 173, 173))
        );

        jLabel5.setText("Region");

        ComboRegion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jawa", "Sumatra", "Kalimantan", "Sulawesi", "Bali & Nt" }));
        ComboRegion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboRegionMouseClicked(evt);
            }
        });
        ComboRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboRegionActionPerformed(evt);
            }
        });

        ComboTujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboTujuanActionPerformed(evt);
            }
        });

        jLabel6.setText("Tujuan");

        jLabel7.setText("Waktu");

        ComboWaktu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Siang", "Malam" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(ComboTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(ComboWaktu, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(TextIDtrans, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                            .addComponent(Spinnertgl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(TextnmCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(TextContact, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addContainerGap())))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TextnmCustomer)
                    .addComponent(TextIDtrans))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Spinnertgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComboTujuan)
                    .addComponent(ComboRegion)
                    .addComponent(ComboWaktu))
                .addGap(31, 31, 31)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void ComboIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboIDActionPerformed
        // TODO add your handling code here:
        JComboBox cRegion= (javax.swing.JComboBox)evt.getSource();
        sKode = (String)cRegion.getSelectedItem();
        detail_bis(sKode);
        System.out.println(sTujuan);
    }//GEN-LAST:event_ComboIDActionPerformed

    private void ComboRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboRegionActionPerformed
        // TODO add your handling code here:
        JComboBox cRegion= (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sRegion = (String)cRegion.getSelectedItem();
        filter_region(sRegion);
    }//GEN-LAST:event_ComboRegionActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        simpan_transaksi();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void ComboRegionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboRegionMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ComboRegionMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        nomor_trans();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        setField();
    }//GEN-LAST:event_btnAddActionPerformed

    private void ComboTujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboTujuanActionPerformed
        // TODO add your handling code here:
        JComboBox cTujuan = (javax.swing.JComboBox)evt.getSource();
        sTujuan = (String)cTujuan.getSelectedItem();
        FillcomboID();
        TextnmBus.setText("");
        TextHarga.setText("");
        TextTujuan.setText("");
    }//GEN-LAST:event_ComboTujuanActionPerformed

    private void ComboIDMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboIDMousePressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ComboIDMousePressed

    private void ComboIDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ComboIDFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboIDFocusGained

    private void ComboIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboIDMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboIDMouseClicked

    private void ComboIDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboIDMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboIDMouseEntered

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
            java.util.logging.Logger.getLogger(PenjualanTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PenjualanTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PenjualanTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PenjualanTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PenjualanTicket().setVisible(true);
            }
        });
    }
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ComboID;
    private javax.swing.JComboBox ComboRegion;
    private javax.swing.JComboBox ComboTujuan;
    private javax.swing.JComboBox ComboWaktu;
    private javax.swing.JSpinner Spinnertgl;
    private javax.swing.JTable TabelTrans;
    private javax.swing.JTextField TextContact;
    private javax.swing.JTextField TextHarga;
    private javax.swing.JTextField TextIDtrans;
    private javax.swing.JTextField TextTotal;
    private javax.swing.JTextField TextTujuan;
    private javax.swing.JTextField TextnmBus;
    private javax.swing.JTextField TextnmCustomer;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
