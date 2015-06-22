/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interaction;

import java.awt.Color;
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
        btnTutup.setVisible(false);
        open_db(); 
        setTombol(true);
        kosong();
        inisialisasi_tabel();
        
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
                ComboTujuan.addItem(rfr.getString(4).trim());
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
        DefaultTableModel model = (DefaultTableModel) TabelTrans.getModel();
        model.setRowCount(0);
        ComboID.setSelectedIndex(-1);
        ComboRegion.setSelectedIndex(-1);
        TextTujuan.setText("");
        TextnmBus.setText("");
        TextHarga.setText("");
        TextnmCustomer.setText("");
        TextContact.setText("");
        TextTotal.setText("");
        TextBayar.setText("");
        TextKembali.setText("");
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
        btnCetak.setEnabled(!t);
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
private void hitung_total() {
    double tot = 0;
    for(int i=0;i<TabelTrans.getRowCount();i++) {
        
        tot = tot+(Double)TabelTrans.getValueAt(i,3);
        TextTotal.setText(Double.toString(tot));
    }
}
private void checking(){
    System.out.println(sTujuan);
    try{ 
            stm=con.createStatement(); 
            ResultSet rfr=stm.executeQuery("select * from listbangku where tujuan='"+sTujuan+"'");
            rfr.beforeFirst();
            while(rfr.next()) {  
                ecoBgk.setText(rfr.getString(2).trim());
                vipBgk.setText(rfr.getString(3).trim());
                acBgk.setText(rfr.getString(4).trim());
                ptsBgk.setText(rfr.getString(5).trim());
                } 
            rfr.close(); 
        } catch(SQLException e) {
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
	//String xkode=cmbKd_Kons.getSelectedItem().toString(); 
	for(int i=0;i<TabelTrans.getRowCount();i++) {
		String xKd_route=(String)TabelTrans.getValueAt(i,0); 
                //Comuting Nomer Bangku belum di buat
                int xBgku = 3;
		double xHrg=(Double)TabelTrans.getValueAt(i,3); 
		String zsql="insert into listTransaksiDetail values('"+xTrans+"','"+xKd_route+"',"+xBgku+","+xHrg+")"; stm.executeUpdate(zsql);
		}
        //Penghitung Total Belum Di buat
        double xHrgtot=Double.parseDouble(TextTotal.getText());
        String msql="insert into listTransaksi values('"+xTrans+"','"+tanggal+"', '"+xNm_customer+"', '"+xContact+"', "+xHrgtot+")"; stm.executeUpdate(msql);
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
	tanggal=Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day); 
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TextIDtrans = new javax.swing.JTextField();
        TextnmCustomer = new javax.swing.JTextField();
        Spinnertgl = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        ComboRegion = new javax.swing.JComboBox();
        ComboTujuan = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        TextContact = new javax.swing.JTextField();
        btnNew = new javax.swing.JButton();
        btnBangku = new javax.swing.JButton();
        btnTutup = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel3 = new javax.swing.JPanel();
        ComboID = new javax.swing.JComboBox();
        TextTujuan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelTrans = new javax.swing.JTable();
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
        jLabel9 = new javax.swing.JLabel();
        TextBayar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TextKembali = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        vipBgk = new javax.swing.JLabel();
        vipWkt = new javax.swing.JLabel();
        ecoBgk = new javax.swing.JLabel();
        ecoWkt = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        acBgk = new javax.swing.JLabel();
        acWkt = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        ptsBgk = new javax.swing.JLabel();
        ptsWkt = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID trasnsaksi");

        jLabel2.setText("Tanggal");

        jLabel3.setText("Nama Customer");

        jLabel4.setText("Contact");

        Spinnertgl.setModel(new javax.swing.SpinnerDateModel());

        jLabel5.setText("Region");

        ComboRegion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jawa", "Sumatra" }));
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

        btnNew.setText("Transaksi Baru");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnBangku.setText("Cek Bangku");
        btnBangku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBangkuMouseEntered(evt);
            }
        });
        btnBangku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangkuActionPerformed(evt);
            }
        });

        btnTutup.setText("Tutup");
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });

        jLayeredPane1.setBackground(new java.awt.Color(255, 102, 102));
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(79, 193, 233));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel3.add(ComboID, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 13, 96, 28));
        jPanel3.add(TextTujuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 11, 155, 32));

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

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 90, 616, 75));

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel3.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 287, 79, 40));

        btnKeluar.setText("keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        jPanel3.add(btnKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(567, 287, 73, 39));
        jPanel3.add(TextnmBus, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 11, 143, 32));
        jPanel3.add(TextHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(497, 11, 143, 32));

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        jPanel3.add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 287, 74, 39));

        btnCetak.setText("Cetak");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });
        jPanel3.add(btnCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 287, 72, 40));
        jPanel3.add(TextTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(497, 176, 143, 32));

        jLabel8.setText("Total");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(438, 185, -1, -1));

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel3.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, -1, -1));

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel9.setText("Jumlah Bayar");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(398, 214, -1, -1));

        TextBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBayarActionPerformed(evt);
            }
        });
        jPanel3.add(TextBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(497, 214, 143, 29));

        jLabel10.setText("Kembalian");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 249, -1, -1));
        jPanel3.add(TextKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(497, 249, 143, 32));

        jPanel2.setBackground(new java.awt.Color(59, 175, 218));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 670, 60));

        jLayeredPane1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 670, 342));

        jPanel1.setBackground(new java.awt.Color(59, 175, 218));
        jPanel1.setForeground(new java.awt.Color(59, 175, 218));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("VIP : ");

        vipBgk.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        vipBgk.setForeground(new java.awt.Color(255, 255, 255));
        vipBgk.setText("20");

        vipWkt.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        vipWkt.setForeground(new java.awt.Color(255, 255, 255));
        vipWkt.setText("Malam");

        ecoBgk.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        ecoBgk.setForeground(new java.awt.Color(255, 255, 255));
        ecoBgk.setText("20");

        ecoWkt.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        ecoWkt.setForeground(new java.awt.Color(255, 255, 255));
        ecoWkt.setText("Malam");

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Eco :");

        acBgk.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        acBgk.setForeground(new java.awt.Color(255, 255, 255));
        acBgk.setText("20");

        acWkt.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        acWkt.setForeground(new java.awt.Color(255, 255, 255));
        acWkt.setText("Malam");

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("AC : ");

        ptsBgk.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        ptsBgk.setForeground(new java.awt.Color(255, 255, 255));
        ptsBgk.setText("20");

        ptsWkt.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        ptsWkt.setForeground(new java.awt.Color(255, 255, 255));
        ptsWkt.setText("Malam");

        jLabel22.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Pts :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ecoBgk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ecoWkt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(2, 2, 2)
                .addComponent(vipBgk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vipWkt)
                .addGap(54, 54, 54)
                .addComponent(jLabel19)
                .addGap(2, 2, 2)
                .addComponent(acBgk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(acWkt)
                .addGap(50, 50, 50)
                .addComponent(jLabel22)
                .addGap(2, 2, 2)
                .addComponent(ptsBgk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ptsWkt)
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(ecoBgk)
                        .addComponent(ecoWkt))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(vipBgk)
                        .addComponent(vipWkt)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(acBgk)
                            .addComponent(acWkt)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel22)
                                .addComponent(ptsBgk)
                                .addComponent(ptsWkt)))))
                .addGap(34, 34, 34))
        );

        jLayeredPane1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 670, 90));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addGap(167, 167, 167)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(ComboRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ComboTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBangku, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnTutup))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(TextIDtrans, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(Spinnertgl, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(TextnmCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextContact, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5)
                        .addGap(6, 6, 6)
                        .addComponent(ComboRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBangku, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnTutup, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addComponent(TextIDtrans, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(Spinnertgl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(TextnmCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(TextContact, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboRegionActionPerformed
        // TODO add your handling code here:
        JComboBox cRegion= (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        sRegion = (String)cRegion.getSelectedItem();
        filter_region(sRegion);
    }//GEN-LAST:event_ComboRegionActionPerformed

    private void ComboRegionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboRegionMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ComboRegionMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        setTombol(false);
        nomor_trans();
    }//GEN-LAST:event_btnNewActionPerformed

    private void ComboTujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboTujuanActionPerformed
        // TODO add your handling code here:
        JComboBox cTujuan = (javax.swing.JComboBox)evt.getSource();
        sTujuan = (String)cTujuan.getSelectedItem();
        FillcomboID();
        TextnmBus.setText("");
        TextHarga.setText("");
        TextTujuan.setText("");
    }//GEN-LAST:event_ComboTujuanActionPerformed

    private void TextBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBayarActionPerformed
        // TODO add your handling code here:
        double kembali = Double.parseDouble(TextBayar.getText()) - Double.parseDouble(TextTotal.getText());
        TextKembali.setText(Double.toString(kembali));
    }//GEN-LAST:event_TextBayarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) this.TabelTrans.getModel();
        int[] rows = TabelTrans.getSelectedRows();
        for(int i=0;i<rows.length;i++){
            model.removeRow(rows[i]-i);
        }
        hitung_total();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        setField();
        hitung_total();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        setTombol(true);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
        new fromMenu().setVisible(true);
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        simpan_transaksi();
        setTombol(true);
        kosong();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void ComboIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboIDActionPerformed
        // TODO add your handling code here:
        JComboBox cRegion= (javax.swing.JComboBox)evt.getSource();
        sKode = (String)cRegion.getSelectedItem();
        detail_bis(sKode);
    }//GEN-LAST:event_ComboIDActionPerformed

    private void ComboIDMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboIDMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_ComboIDMousePressed

    private void ComboIDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboIDMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboIDMouseEntered

    private void ComboIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboIDMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboIDMouseClicked

    private void ComboIDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ComboIDFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboIDFocusGained

    private void btnBangkuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangkuActionPerformed
        // TODO add your handling code here:
        btnTutup.setVisible(true);
        checking();
        jLayeredPane1.moveToFront(jPanel1);
        System.out.println(jLayeredPane1.getLayer(jPanel1));
        System.out.println(jLayeredPane1.getLayer(jPanel3));
        
    }//GEN-LAST:event_btnBangkuActionPerformed

    private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
        // TODO add your handling code here:
        jLayeredPane1.moveToBack(jPanel1);
        btnTutup.setVisible(false);
    }//GEN-LAST:event_btnTutupActionPerformed

    private void btnBangkuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBangkuMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_btnBangkuMouseEntered

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
    private javax.swing.JSpinner Spinnertgl;
    private javax.swing.JTable TabelTrans;
    private javax.swing.JTextField TextBayar;
    private javax.swing.JTextField TextContact;
    private javax.swing.JTextField TextHarga;
    private javax.swing.JTextField TextIDtrans;
    private javax.swing.JTextField TextKembali;
    private javax.swing.JTextField TextTotal;
    private javax.swing.JTextField TextTujuan;
    private javax.swing.JTextField TextnmBus;
    private javax.swing.JTextField TextnmCustomer;
    private javax.swing.JLabel acBgk;
    private javax.swing.JLabel acWkt;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBangku;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTutup;
    private javax.swing.JLabel ecoBgk;
    private javax.swing.JLabel ecoWkt;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel ptsBgk;
    private javax.swing.JLabel ptsWkt;
    private javax.swing.JLabel vipBgk;
    private javax.swing.JLabel vipWkt;
    // End of variables declaration//GEN-END:variables
}
