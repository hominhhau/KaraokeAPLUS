
package dao;

import connectDB.ConnectDB;
import entity.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.ArrayList;

import java.util.ArrayList;

import connectDB.ConnectDB;

import java.util.ArrayList;

/**
 *
 * @author 84934 NguyenThiQuynhGiang
 */
public class KhachHang_DAO {
    
    public KhachHang_DAO(){
        
    }
    
    public ArrayList<KhachHang> getalltbKhachHang(){
        ArrayList<KhachHang> dsKH = new ArrayList<KhachHang>();
        try{
           ConnectDB db = ConnectDB.getInstance();
           db.connect();
           Connection con = db.getConnection();
           String sql = "SELECT *FROM KhachHang";
           Statement statement = con.createStatement();
           
           ResultSet rs = statement.executeQuery(sql);
           while(rs.next()){
               String maKH = rs.getString(1);
               String tenKH = rs.getString(2);
               String SDT = rs.getString(3);
               Boolean GioiTinh = rs.getBoolean(4);
               

               KhachHang kh = new KhachHang(maKH, tenKH, SDT, GioiTinh);

               dsKH.add(kh);
           }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return dsKH;
    }
    
    public ArrayList<KhachHang> getKhachHangTheoMaKH(String id){
        ArrayList<KhachHang> dsKH = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        
        try{
            String sql = "SELECT *FROM KhachHang WHERE maKH=?";
           statement = con.prepareStatement(sql);
           statement.setString(1, id);
           
           ResultSet rs = statement.executeQuery(sql);
           while(rs.next()){
               String maKH = rs.getString(1);
               String tenKH = rs.getString(2);
               String SDT = rs.getString(3);
               Boolean GioiTinh = rs.getBoolean(4);
               
               KhachHang kh = new KhachHang(maKH, tenKH, SDT, GioiTinh);
               dsKH.add(kh);
           }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsKH;
    }
    
    public ArrayList<KhachHang> getKhachHangTheoSdtKH(String id){
        ArrayList<KhachHang> dsKH = new ArrayList<KhachHang>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        
        try{
            String sql = "SELECT *FROM KhachHang WHERE SDT=?";
            statement = con.prepareStatement(sql);
            statement.setString(1, id);
            
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
               String maKH = rs.getString(1);
               String tenKH = rs.getString(2);
               String SDT = rs.getString(3);
               Boolean GioiTinh = rs.getBoolean(4);

               KhachHang kh = new KhachHang(maKH, tenKH, SDT, GioiTinh);

               dsKH.add(kh);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsKH;
    }
    
    public boolean addCustomer(KhachHang kh){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        
        try{
            stmt = con.prepareStatement("insert into" +
                    "KhachHang(maKH,tenKH,SDT,GioiTinh) values (?, ?, ?, ?)");
            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getTenKH());
            stmt.setString(3, kh.getSdt());
            stmt.setBoolean(4, kh.isGioitinh());
            
            n = stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                stmt.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
       return n > 0; 
    }
    
    public boolean DeleteCustomer(String maKH){
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        
        try{
            stmt = con.prepareStatement("delete KhachHang from KhachHang where maKH=?");
            stmt.setString(1, maKH);
            n = stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return n > 0;
    }
    
    public KhachHang findCustomer(String maTim){
        KhachHang kh = null;
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stmt = null;
        
        try{
            String sql = "SELECT *FROM KhachHang WHERE maKH=?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maTim);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
               String maKH = rs.getString(1);
               String tenKH = rs.getString(2);
               String SDT = rs.getString(3);
               Boolean GioiTinh = rs.getBoolean(4);
               

               kh = new KhachHang(maKH, tenKH, SDT, GioiTinh);

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return kh;
    }
    
    public boolean editCustomer(KhachHang kh){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        
        try{
            stmt = con.prepareStatement("update" + 
                    "KhachHang set tenKH=?, SDT=?, GioiTinh=? where maKH=?");
            stmt.setString(1, kh.getTenKH());
            stmt.setString(2, kh.getSdt());
            stmt.setBoolean(3, kh.isGioitinh());
            stmt.setString(4, kh.getMaKH());
            
            n = stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                stmt.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return n > 0;
    }
}
