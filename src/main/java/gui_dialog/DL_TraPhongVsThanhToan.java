/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package gui_dialog;

import connectDB.ConnectDB;
import dao.*;
import entity.*;
import gui.form.Form_QuanLyDatPhong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author HO MINH HAU
 */
public class DL_TraPhongVsThanhToan extends javax.swing.JDialog {
    private HoaDon_DAO hd_dao;
    private ChiTietHoaDonPhong_Dao cthdp_dao;
    private ChiTietHoaDonDichVu_DAO cthddv_dao;
    private KhachHang_DAO kh_dao;
    private PhongHat_DAO ph_dao;
    private Float gia;
    private Float thanhTien;
    private LocalDateTime gioVao;
    private static String maHDDSD;
    private Double tongTien;

    /**
     * Creates new form DL_TraPhongVsThanhToan
     */
    public DL_TraPhongVsThanhToan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        hd_dao = new HoaDon_DAO();
        cthdp_dao = new ChiTietHoaDonPhong_Dao();
        cthddv_dao = new ChiTietHoaDonDichVu_DAO();
        kh_dao = new KhachHang_DAO();
        ph_dao = new PhongHat_DAO();
        tinhTien();
        setlblThanhToan();
    }

    public String getMaHDDSD() {
        return maHDDSD;
    }

    public void setMaHDDSD(String maHDDSD) {
        this.maHDDSD = maHDDSD;
    }


    public void tinhTien() {
        //check connect
        ConnectDB db = ConnectDB.getInstance();
        try {
            db.connect();
            if (db != null) {
                System.out.println("Connect success");
                DL_PhongDangSuDung dialog = new DL_PhongDangSuDung(new javax.swing.JFrame(), true);
                String maHD = dialog.getMaHDPDSD();
                setMaHDDSD(maHD);

                Form_QuanLyDatPhong frmPH = new Form_QuanLyDatPhong();

                PhongHat ph = ph_dao.getPhongHatByMaPhong(frmPH.getRoomSelected());
                if (ph.getLoaiPhong().getMaLoaiPhong().equals("LP001")) {
                    gia = 100000f;
                } else {
                    gia = 60000f;
                }
                System.out.println(gia);
                ChiTietHoaDonPhong ct = cthdp_dao.getChiTietHoaDonPhongTheoMaHD(maHD);
                gioVao = ct.getGioVao();
                LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
                if (ct.getGioVao().isBefore(oneHourAgo)) {
                    long durationInMinutes = Duration.between(gioVao, LocalDateTime.now()).toMinutes();
                    thanhTien = (durationInMinutes / 60.0f) * gia;
                    System.out.println(durationInMinutes);
                    System.out.println(thanhTien);
                } else {
                    thanhTien = gia;
                }


                cthdp_dao.updateGioRaVsGia(maHD, LocalDateTime.now(), thanhTien);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setlblThanhToan() {
        //check connect
        ConnectDB db = ConnectDB.getInstance();
        try {
            db.connect();
            if (db != null) {
                System.out.println("Connect success");

                HoaDon hd = hd_dao.getHoaDonTheoMaHD(maHDDSD);
                db.connect();
                KhachHang loadKH = kh_dao.getdataKH(hd.getKhachHang().getMaKH());
                lblTenKH.setText(loadKH.getTenKH());
                lblSDT.setText(loadKH.getSdt());
                ChiTietHoaDonPhong ct = cthdp_dao.getChiTietHoaDonPhongTheoMaHD(maHDDSD);
                ChiTietHoaDonDV ctdv = cthddv_dao.getChiTietHoaDonDVTheoMaHD(maHDDSD);
                // Lấy giờ vào
                int gioVao = ct.getGioVao().getHour();
                int phutVao = ct.getGioVao().getMinute();
                int gioRa = ct.getGioRa().getHour();
                int phutRa = ct.getGioRa().getMinute();

                lblGiovao.setText(String.format("%02d:%02d", gioVao, phutVao));
                lblGioRa.setText(String.format("%02d:%02d", gioRa, phutRa));
                //loaddata in table
                cthdp_dao = new ChiTietHoaDonPhong_Dao();
                cthddv_dao = new ChiTietHoaDonDichVu_DAO();
                int i = 1;

                DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
                model.setRowCount(0);
                for (ChiTietHoaDonPhong cthdp : cthdp_dao.getalltbChiTietHoaDonPhong()) {
                    if (cthdp.getHoaDon().getMaHD().equals(maHDDSD)) {
                        int gioVaoHour = cthdp.getGioVao().getHour();
                        int gioVaoMinute = cthdp.getGioVao().getMinute();
                        int gioRaHour = cthdp.getGioRa().getHour();
                        int gioRaMinute = cthdp.getGioRa().getMinute();

                        // Calculate duration in minutes
                        int durationMinutes = (gioRaHour - gioVaoHour) * 60 + (gioRaMinute - gioVaoMinute);

                        // If duration is less than 1 hour, set it to 1 hour
                        if (durationMinutes < 60) {
                            durationMinutes = 60;
                        }

                        // Calculate hours and minutes for display
                        int displayHours = durationMinutes / 60;
                        int displayMinutes = durationMinutes % 60;

                        String thoigian = gioVaoHour + ":" + gioVaoMinute + " - " + gioRaHour + ":" + gioRaMinute;
                        String displayTime = displayHours + " giờ " + displayMinutes + " phút";

                        model.addRow(new Object[]{
                                i++, cthdp.getPhongHat().getMaPhong(), displayTime, cthdp.getGia()
                        });
                    }
                }
                for (ChiTietHoaDonDV cthddv : cthddv_dao.getalltbChiTietHoaDonDV()) {
                    if (cthddv.getHoaDon().getMaHD().equals(maHDDSD)) {
                        model.addRow(new Object[]{
                                i++, cthddv.getMatHang().getMaMH(), cthddv.getSoLuong(), cthddv.getGia()
                        });
                    }
                }
                //tinh tong tien
                tongTien = 0.0;
                for (int j = 0; j < tblHoaDon.getRowCount(); j++) {
                    tongTien += Double.parseDouble(tblHoaDon.getValueAt(j, 3).toString());
                }
                lblTongTien.setText(String.valueOf(tongTien));
                lblTongTienThanhtoan.setText(String.valueOf(tongTien));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCover = new javax.swing.JPanel();
        lblThanhToan = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTenKH = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblGiovao = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblGioRa = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaGiamGia = new javax.swing.JTextField();
        btnKiemTra = new gui.swing.RadiusButton();
        jLabel9 = new javax.swing.JLabel();
        lblTienGiam = new javax.swing.JLabel();
        btnThanhToan = new gui.swing.RadiusButton();
        jLabel11 = new javax.swing.JLabel();
        lblTongTienThanhtoan = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        btnExit = new gui.swing.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlCover.setBackground(new java.awt.Color(255, 255, 255));
        pnlCover.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblThanhToan.setForeground(new java.awt.Color(41, 173, 86));
        lblThanhToan.setText("THANH TOÁN");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tên khách hàng:");

        lblTenKH.setText("Hồ Minh Hậu");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Số điện thoại:");

        lblSDT.setText("0585576500");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Giờ nhận phòng:");

        lblGiovao.setText("11h30");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Giờ trả phòng");

        lblGioRa.setText("11h30");

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên", "Thời gian/ Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setRowHeight(30);
        jScrollPane1.setViewportView(tblHoaDon);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Tổng tiền:");

        lblTongTien.setText("40000");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Mã giảm giá:");

        txtMaGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaGiamGiaActionPerformed(evt);
            }
        });

        btnKiemTra.setBackground(new java.awt.Color(166, 208, 238));
        btnKiemTra.setText("Kiểm tra");
        btnKiemTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKiemTraActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Tiền giảm:");

        lblTienGiam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTienGiam.setText("0");

        btnThanhToan.setBackground(new java.awt.Color(41, 173, 86));
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel11.setText("Tổng tiền cần thanh toán:");

        lblTongTienThanhtoan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTongTienThanhtoan.setText("10000000");

        jCheckBox1.setText("Xuất hóa đơn");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(255, 0, 51));
        btnExit.setBorder(null);
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("  X  ");
        btnExit.setEffectColor(new java.awt.Color(255, 255, 255));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCoverLayout = new javax.swing.GroupLayout(pnlCover);
        pnlCover.setLayout(pnlCoverLayout);
        pnlCoverLayout.setHorizontalGroup(
            pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCoverLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnKiemTra, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlCoverLayout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(lblThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlCoverLayout.createSequentialGroup()
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCoverLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCoverLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTenKH))
                            .addGroup(pnlCoverLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(lblGiovao, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)
                        .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGioRa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlCoverLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCoverLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCoverLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCoverLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(lblTongTienThanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCoverLayout.createSequentialGroup()
                        .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        pnlCoverLayout.setVerticalGroup(
            pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCoverLayout.createSequentialGroup()
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblTenKH)
                    .addComponent(jLabel3)
                    .addComponent(lblSDT))
                .addGap(18, 18, 18)
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblGiovao)
                    .addComponent(jLabel5)
                    .addComponent(lblGioRa))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblTongTien))
                .addGap(18, 18, 18)
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtMaGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKiemTra, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTienGiam)
                    .addComponent(jCheckBox1))
                .addGap(37, 37, 37)
                .addGroup(pnlCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblTongTienThanhtoan))
                .addGap(18, 18, 18)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaGiamGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaGiamGiaActionPerformed

    private void btnKiemTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKiemTraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKiemTraActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        //check connect
        ConnectDB db = ConnectDB.getInstance();
        try {
            db.connect();
            if (db != null) {
                System.out.println("Connect success");
                HoaDon hd = hd_dao.getHoaDonTheoMaHD(maHDDSD);
                db.connect();
                hd_dao.updateTongTien(maHDDSD, tongTien);
                JOptionPane.showMessageDialog(this, "Thanh toán thành công");
                this.dispose();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//GEN-LAST:event_btnThanhToanActionPerformed

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
            java.util.logging.Logger.getLogger(DL_TraPhongVsThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DL_TraPhongVsThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DL_TraPhongVsThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DL_TraPhongVsThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DL_TraPhongVsThanhToan dialog = new DL_TraPhongVsThanhToan(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.swing.Button btnExit;
    private gui.swing.RadiusButton btnKiemTra;
    private gui.swing.RadiusButton btnThanhToan;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGioRa;
    private javax.swing.JLabel lblGiovao;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblThanhToan;
    private javax.swing.JLabel lblTienGiam;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTongTienThanhtoan;
    private javax.swing.JPanel pnlCover;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtMaGiamGia;
    // End of variables declaration//GEN-END:variables
}