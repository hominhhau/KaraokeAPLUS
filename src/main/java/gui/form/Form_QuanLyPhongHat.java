package gui.form;

import dao.PhongHat_DAO;
import entity.PhongHat;
import gui_dialog.DL_ThemPhong;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HO MINH HAU
 */
public class Form_QuanLyPhongHat extends javax.swing.JPanel {

    private PhongHat_DAO ph_dao;
    private DefaultTableModel dtmPhongHat;
    
  
    public Form_QuanLyPhongHat() {
        initComponents();
        ph_dao = new PhongHat_DAO();
        dtmPhongHat = (DefaultTableModel) tblDSPH.getModel();
//        loadTable(ph_dao.getAllPhongHat());
        DocDuLieu();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPhongHat = new javax.swing.JPanel();
        pnlHeader = new javax.swing.JPanel();
        pnlTim = new javax.swing.JPanel();
        lblTim = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        btnTim = new gui.swing.RadiusButton();
        btnThem = new gui.swing.RadiusButton();
        lblDSPH = new javax.swing.JLabel();
        scr = new javax.swing.JScrollPane();
        tblDSPH = new javax.swing.JTable();
        lblTong = new javax.swing.JLabel();
        lblTrong = new javax.swing.JLabel();
        txtTrong = new javax.swing.JTextField();
        lblSuDung = new javax.swing.JLabel();
        txtSuDung = new javax.swing.JTextField();
        lblCho = new javax.swing.JLabel();
        txtCho = new javax.swing.JTextField();
        txtTong = new javax.swing.JTextField();

        pnlPhongHat.setBackground(new java.awt.Color(235, 249, 249));

        pnlHeader.setBackground(new java.awt.Color(235, 249, 249));
        pnlHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        pnlTim.setBackground(new java.awt.Color(255, 255, 255));

        lblTim.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTim.setText("Tìm kiếm");

        btnTim.setBackground(new java.awt.Color(166, 208, 238));
        btnTim.setText("Tìm");

        javax.swing.GroupLayout pnlTimLayout = new javax.swing.GroupLayout(pnlTim);
        pnlTim.setLayout(pnlTimLayout);
        pnlTimLayout.setHorizontalGroup(
            pnlTimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTim)
                .addGap(28, 28, 28)
                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        pnlTimLayout.setVerticalGroup(
            pnlTimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTimLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(pnlTimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTim)
                    .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        btnThem.setBackground(new java.awt.Color(41, 173, 86));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm phòng hát");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(pnlTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        lblDSPH.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblDSPH.setText("DANH SÁCH PHÒNG HÁT");

        tblDSPH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phòng", "Tên phòng", "Mã loại phòng", "Trạng thái", "Hành động", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scr.setViewportView(tblDSPH);

        lblTong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTong.setText("Tổng phòng hát");

        lblTrong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTrong.setText("Phòng trống");

        txtTrong.setEditable(false);

        lblSuDung.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSuDung.setText("Phòng đang sử dụng");

        txtSuDung.setEditable(false);

        lblCho.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCho.setText("Phòng chờ");

        txtCho.setEditable(false);

        txtTong.setEditable(false);

        javax.swing.GroupLayout pnlPhongHatLayout = new javax.swing.GroupLayout(pnlPhongHat);
        pnlPhongHat.setLayout(pnlPhongHatLayout);
        pnlPhongHatLayout.setHorizontalGroup(
            pnlPhongHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlPhongHatLayout.createSequentialGroup()
                .addGroup(pnlPhongHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPhongHatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scr))
                    .addGroup(pnlPhongHatLayout.createSequentialGroup()
                        .addGroup(pnlPhongHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPhongHatLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(lblTong)
                                .addGap(18, 18, 18)
                                .addComponent(txtTong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(120, 120, 120)
                                .addComponent(lblTrong)
                                .addGap(18, 18, 18)
                                .addComponent(txtTrong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(155, 155, 155)
                                .addComponent(lblSuDung)
                                .addGap(54, 54, 54)
                                .addComponent(txtSuDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(152, 152, 152)
                                .addComponent(lblCho)
                                .addGap(18, 18, 18)
                                .addComponent(txtCho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlPhongHatLayout.createSequentialGroup()
                                .addGap(474, 474, 474)
                                .addComponent(lblDSPH)))
                        .addGap(0, 42, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlPhongHatLayout.setVerticalGroup(
            pnlPhongHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPhongHatLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDSPH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scr, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(pnlPhongHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTong)
                    .addComponent(lblTrong)
                    .addComponent(txtTrong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSuDung)
                    .addComponent(txtSuDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCho)
                    .addComponent(txtCho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPhongHat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPhongHat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

//    public void loadTable(ArrayList<PhongHat> ds) {
//        dtmPhongHat.setRowCount(0);//reset nd trong bang ve 0
//        if (ds == null) {
//            clearJTable();
//            return;
//        }
//        clearJTable();
//        for (PhongHat ph : ds) {
//            dtmPhongHat.addRow(new Object[] {ph.getMaPhong(), ph.getTenPhong(), ph.getLoaiPhong().getMaLoaiPhong(), ph.getTinhTrangPhong()});
//        }
//    }
//    
//    public void clearJTable() {
//        while (tblDSPH.getRowCount() > 0) {
//            dtmPhongHat.removeRow(0);
//        }
//    }
    
    public void DocDuLieu() {
        List<PhongHat> list = ph_dao.getAllPhongHat();
        for (PhongHat ph : list) {
            dtmPhongHat.addRow(new Object[] {ph.getMaPhong(), ph.getTenPhong(), ph.getLoaiPhong().getMaLoaiPhong(), ph.getTinhTrangPhong()});
        }
    }
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        new DL_ThemPhong().setVisible(true);
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.swing.RadiusButton btnThem;
    private gui.swing.RadiusButton btnTim;
    private javax.swing.JLabel lblCho;
    private javax.swing.JLabel lblDSPH;
    private javax.swing.JLabel lblSuDung;
    private javax.swing.JLabel lblTim;
    private javax.swing.JLabel lblTong;
    private javax.swing.JLabel lblTrong;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlPhongHat;
    private javax.swing.JPanel pnlTim;
    private javax.swing.JScrollPane scr;
    private javax.swing.JTable tblDSPH;
    private javax.swing.JTextField txtCho;
    private javax.swing.JTextField txtSuDung;
    private javax.swing.JTextField txtTim;
    private javax.swing.JTextField txtTong;
    private javax.swing.JTextField txtTrong;
    // End of variables declaration//GEN-END:variables
}
