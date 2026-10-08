/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.CatatanRehabilitasi;
import model.LokasiHabitat;
import model.Orangutan;
import model.OrangutanBetina;
import model.OrangutanJantan;
import view.KonservasiView;
import view.KonservasiView.CatatanRehabilitasiWrapper;

/**
 * CONTROLLER: pusat logika bisnis dan pengelolaan data (ArrayList).
 * Berkomunikasi dengan View (tampilan) dan Model (entity).
 *
 * @author riaza
 */
public class KonservasiController {
    private ArrayList<CatatanRehabilitasi> daftarKonservasi;
    private KonservasiView view;
    private int counter;

    public KonservasiController(KonservasiView view) {
        this.view = view;
        this.daftarKonservasi = new ArrayList<>();
        this.counter = 1;
        inisialisasiDataAwal();
    }

    // DUMMY DATA AWAL (minimal 1) 
    private void inisialisasiDataAwal() {
    Orangutan o1 = new OrangutanJantan("ID-001", "Boni", 12, 18.5);
    Orangutan o2 = new OrangutanBetina("ID-002", "Sisi", 8); // overloading: tanpa data anak
    // ... sisanya tetap sama
}

    // CREATE 
    public void tambahData() {
        view.tampilkanPesan("\n--- Registrasi Orangutan Baru ---");
        String idCatatan = "C" + counter++;

        String nama = view.inputString("Nama Orangutan: ");
        int umur = view.inputInteger("Estimasi Umur (Tahun): ");

        int jk;
        do {
            view.tampilkanPesan("Jenis Kelamin: 1. Jantan | 2. Betina");
            jk = view.inputInteger("Pilih [1/2]: ");
            if (jk != 1 && jk != 2) {
            view.tampilkanError("Pilihan tidak valid! Masukkan 1 atau 2.");
            }
        } while (jk != 1 && jk != 2);
        
        Orangutan orangutan;
        if (jk == 1) {
            double cheekPads = view.inputDouble("Ukuran Cheek Pads (cm): ");
            orangutan = new OrangutanJantan("ID-" + (System.currentTimeMillis() % 1000), nama, umur, cheekPads);
        } else {
            int jumlahAnak = view.inputInteger("Jumlah Anak: ");
            orangutan = new OrangutanBetina("ID-" + (System.currentTimeMillis() % 1000), nama, umur, jumlahAnak);
        }

        LokasiHabitat lokasi = pilihLokasi();
        String status = pilihStatus();

        daftarKonservasi.add(new CatatanRehabilitasi(idCatatan, orangutan, lokasi, status));
        view.tampilkanPesan("Sukses: Data berhasil didaftarkan dengan Kode Registrasi: " + idCatatan);
    }

    // READ 
    public void tampilkanData() {
        view.tampilkanHeaderTabel();

        if (daftarKonservasi.isEmpty()) {
            view.tampilkanPesan("|                                                 Belum ada data konservasi.                                                 |");
        } else {
            for (CatatanRehabilitasi c : daftarKonservasi) {
                CatatanRehabilitasiWrapper w = new CatatanRehabilitasiWrapper();
                w.idCatatan = c.getIdCatatan();
                w.kategori = c.getOrangutan().getKategori();          // Polymorphism
                w.nama = c.getOrangutan().getNama();
                w.umur = c.getOrangutan().getUmurTahun() + " Thn";
                w.gender = c.getOrangutan().getJenisKelamin();        // Polymorphism
                w.wilayah = c.getLokasi().getNamaWilayah();
                w.provinsi = c.getLokasi().getProvinsi();
                w.status = c.getStatusKesehatan();
                w.infoTambahan = c.getOrangutan().getInfoTambahan();  // Polymorphism
                view.tampilkanBarisData(w);
            }
        }
        view.tampilkanFooterTabel();
    }

    // UPDATE 
    public void updateData() {
        tampilkanData();
        if (daftarKonservasi.isEmpty()) return;

        String idCari = view.inputString("\nMasukkan ID Catatan yang ingin diubah: ");
        CatatanRehabilitasi target = cariDataById(idCari);

        if (target == null) {
            view.tampilkanError("Data dengan ID " + idCari + " tidak ditemukan.");
            return;
        }

        int pilihanUpdate;
        do {
            view.tampilkanPesan("\n--- Update Data: " + target.getIdCatatan() + " ---");
            view.tampilkanPesan("1. Ubah Nama Orangutan");
            view.tampilkanPesan("2. Ubah Umur");
            view.tampilkanPesan("3. Ubah Info Khusus (Cheek Pads / Jumlah Anak)");
            view.tampilkanPesan("4. Ubah Lokasi Habitat");
            view.tampilkanPesan("5. Ubah Status Kesehatan");
            view.tampilkanPesan("6. Selesai");
            pilihanUpdate = view.inputInteger("Pilih menu update [1-6]: ");

            switch (pilihanUpdate) {
                case 1:
                    target.getOrangutan().setNama(view.inputString("Nama baru: "));
                    view.tampilkanPesan("Nama berhasil diubah.");
                    break;
                case 2:
                    target.getOrangutan().setUmurTahun(view.inputInteger("Umur baru (tahun): "));
                    view.tampilkanPesan("Umur berhasil diubah.");
                    break;
                case 3:
                    // Polymorphism: cek tipe runtime
                    if (target.getOrangutan() instanceof OrangutanJantan) {
                        OrangutanJantan oj = (OrangutanJantan) target.getOrangutan();
                        oj.setUkuranCheekPads(view.inputDouble("Cheek Pads baru (cm): "));
                        view.tampilkanPesan("Cheek Pads berhasil diubah.");
                    } else if (target.getOrangutan() instanceof OrangutanBetina) {
                        OrangutanBetina ob = (OrangutanBetina) target.getOrangutan();
                        ob.setJumlahAnak(view.inputInteger("Jumlah anak baru: "));
                        view.tampilkanPesan("Jumlah anak berhasil diubah.");
                    }
                    break;
                case 4:
                    target.setLokasi(pilihLokasi());
                    view.tampilkanPesan("Lokasi berhasil diubah.");
                    break;
                case 5:
                    target.setStatusKesehatan(pilihStatus());
                    view.tampilkanPesan("Status berhasil diubah.");
                    break;
                case 6:
                    view.tampilkanPesan("Update selesai.");
                    break;
                default:
                    view.tampilkanError("Pilihan tidak valid.");
            }
        } while (pilihanUpdate != 6);
    }

    // DELETE 
    public void hapusData() {
        tampilkanData();
        if (daftarKonservasi.isEmpty()) return;

        String idCari = view.inputString("\nMasukkan ID Catatan yang ingin dihapus: ");
        CatatanRehabilitasi target = cariDataById(idCari);

        if (target != null) {
            daftarKonservasi.remove(target);
            view.tampilkanPesan("Data catatan " + idCari + " berhasil dihapus dari sistem!");
        } else {
            view.tampilkanError("Data dengan ID " + idCari + " tidak ditemukan.");
        }
    }

    // HELPER
    private LokasiHabitat pilihLokasi() {
    view.tampilkanPesan("\nPilih Wilayah Konservasi di Kalimantan:");
    view.tampilkanPesan("1. TN Tanjung Puting (Kalimantan Tengah)");
    view.tampilkanPesan("2. TN Sebangau (Kalimantan Tengah)");
    view.tampilkanPesan("3. TN Betung Kerihun (Kalimantan Barat)");
    view.tampilkanPesan("4. TN Kutai (Kalimantan Timur)");

    int opsi;
    do {
        opsi = view.inputInteger("Pilih Wilayah [1-4]: ");
        if (opsi < 1 || opsi > 4) {
            view.tampilkanError("Pilihan tidak valid! Masukkan angka 1-4.");
        }
    } while (opsi < 1 || opsi > 4);

    switch (opsi) {
        case 1: return new LokasiHabitat("LOC-01", "TN Tanjung Puting", "Kalimantan Tengah", "Taman Nasional");
        case 2: return new LokasiHabitat("LOC-02", "TN Sebangau", "Kalimantan Tengah", "Taman Nasional");
        case 3: return new LokasiHabitat("LOC-03", "TN Betung Kerihun", "Kalimantan Barat", "Taman Nasional");
        default: return new LokasiHabitat("LOC-04", "TN Kutai", "Kalimantan Timur", "Taman Nasional");
    }
}

    private String pilihStatus() {
    view.tampilkanPesan("\nStatus Tahap Konservasi:");
    view.tampilkanPesan("1. Karantina Medis");
    view.tampilkanPesan("2. Sekolah Hutan (Forest School)");
    view.tampilkanPesan("3. Pulau Pra-Rilis");
    view.tampilkanPesan("4. Liar / Rilis Penuh");

    int opsi;
    do {
        opsi = view.inputInteger("Pilih Status [1-4]: ");
        if (opsi < 1 || opsi > 4) {
            view.tampilkanError("Pilihan tidak valid! Masukkan angka 1-4.");
        }
    } while (opsi < 1 || opsi > 4);

    switch (opsi) {
        case 1: return "Karantina Medis";
        case 2: return "Sekolah Hutan";
        case 3: return "Pra-Rilis";
        default: return "Liar / Rilis";
    }
}

    private CatatanRehabilitasi cariDataById(String id) {
        for (CatatanRehabilitasi c : daftarKonservasi) {
            if (c.getIdCatatan().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }
}
