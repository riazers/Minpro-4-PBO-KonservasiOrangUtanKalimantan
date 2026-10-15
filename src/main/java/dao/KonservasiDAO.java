/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.Koneksi;
import config.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class KonservasiDAO {
    
    // READ Data menggunakan PreparedStatement dan JOIN
    public List<CatatanRehabilitasi> getAllCatatan() {
        List<CatatanRehabilitasi> list = new ArrayList<>();
        String sql = "SELECT c.*, o.*, l.* FROM catatan_rehabilitasi c " +
                     "JOIN orangutan o ON c.id_orangutan = o.id_orangutan " +
                     "JOIN lokasi_habitat l ON c.id_lokasi = l.id_lokasi";
                     
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             
            while (rs.next()) {
                Orangutan o;
                if (rs.getString("jenis_kelamin").equalsIgnoreCase("Jantan")) {
                    o = new OrangutanJantan(rs.getString("id_orangutan"), rs.getString("nama"), rs.getInt("umur_tahun"), rs.getDouble("cheek_pads"));
                } else {
                    o = new OrangutanBetina(rs.getString("id_orangutan"), rs.getString("nama"), rs.getInt("umur_tahun"), rs.getInt("jumlah_anak"));
                }
                
                LokasiHabitat l = new LokasiHabitat(rs.getString("id_lokasi"), rs.getString("nama_wilayah"), rs.getString("provinsi"), rs.getString("status_zona"));
                list.add(new CatatanRehabilitasi(rs.getString("id_catatan"), o, l, rs.getString("status_kesehatan")));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data: " + e.getMessage());
        }
        return list;
    }

    // CREATE Data (Insert ke 2 tabel)
    public void insertCatatan(CatatanRehabilitasi c) {
        String sqlOrangutan = "INSERT INTO orangutan (id_orangutan, nama, umur_tahun, jenis_kelamin, cheek_pads, jumlah_anak) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlCatatan = "INSERT INTO catatan_rehabilitasi (id_catatan, id_orangutan, id_lokasi, status_kesehatan) VALUES (?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection()) {
            conn.setAutoCommit(false); // Transaction management
            
            try (PreparedStatement pstO = conn.prepareStatement(sqlOrangutan);
                 PreparedStatement pstC = conn.prepareStatement(sqlCatatan)) {
                 
                // Insert Orangutan
                pstO.setString(1, c.getOrangutan().getIdOrangutan());
                pstO.setString(2, c.getOrangutan().getNama());
                pstO.setInt(3, c.getOrangutan().getUmurTahun());
                pstO.setString(4, c.getOrangutan().getJenisKelamin());
                
                if (c.getOrangutan() instanceof OrangutanJantan) {
                    pstO.setDouble(5, ((OrangutanJantan) c.getOrangutan()).getUkuranCheekPads());
                    pstO.setNull(6, Types.INTEGER);
                } else {
                    pstO.setNull(5, Types.DOUBLE);
                    pstO.setInt(6, ((OrangutanBetina) c.getOrangutan()).getJumlahAnak());
                }
                pstO.executeUpdate();

                // Insert Catatan
                pstC.setString(1, c.getIdCatatan());
                pstC.setString(2, c.getOrangutan().getIdOrangutan());
                pstC.setString(3, c.getLokasi().getIdLokasi());
                pstC.setString(4, c.getStatusKesehatan());
                pstC.executeUpdate();
                
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }
    
    // DELETE Data (Menghapus berdasarkan ID Catatan)
    public void deleteCatatan(String idCatatan) {
        String sql = "DELETE FROM catatan_rehabilitasi WHERE id_catatan = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idCatatan);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus data: " + e.getMessage());
        }
    }
}