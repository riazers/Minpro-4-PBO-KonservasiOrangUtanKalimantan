/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package model;

/**
 * ABSTRACTION: Orangutan adalah abstract class.
 * Tidak bisa diinstansiasi langsung — hanya lewat subclass.
 */
public abstract class Orangutan {
    private String idOrangutan;
    private String nama;
    private int umurTahun;

    public Orangutan(String idOrangutan, String nama, int umurTahun) {
        this.idOrangutan = idOrangutan;
        this.nama = nama;
        this.umurTahun = umurTahun;
    }

    public String getIdOrangutan() { return idOrangutan; }
    public void setIdOrangutan(String idOrangutan) { this.idOrangutan = idOrangutan; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getUmurTahun() { return umurTahun; }
    public void setUmurTahun(int umurTahun) { this.umurTahun = umurTahun; }

    // ABSTRACT METHODS — wajib di-override subclass
    public abstract String getJenisKelamin();
    public abstract String getKategori();
    public abstract String getInfoTambahan();
}