package controller;

import dao.KonservasiDAO;
import java.util.List;
import model.*;
import view.KonservasiView;
import view.KonservasiView.CatatanRehabilitasiWrapper;

public class KonservasiController {
    private KonservasiDAO dao;
    private KonservasiView view;
    private int counter;

    public KonservasiController(KonservasiView view) {
        this.view = view;
        this.dao = new KonservasiDAO();
        this.counter = dao.getAllCatatan().size() + 1;
    }

    // CREATE
    public void tambahData() {
        view.tampilkanPesan("\n--- Registrasi Orangutan Baru ---");
        String idCatatan = "C" + counter++;
        String idOrangutan = "ID-" + (System.currentTimeMillis() % 1000);

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
            orangutan = new OrangutanJantan(idOrangutan, nama, umur, cheekPads);
        } else {
            int jumlahAnak = view.inputInteger("Jumlah Anak: ");
            orangutan = new OrangutanBetina(idOrangutan, nama, umur, jumlahAnak);
        }

        LokasiHabitat lokasi = pilihLokasi();
        String status = pilihStatus();

        CatatanRehabilitasi catatanBaru = new CatatanRehabilitasi(idCatatan, orangutan, lokasi, status);
        dao.insertCatatan(catatanBaru);
        view.tampilkanPesan("Sukses: Data berhasil disimpan ke database dengan Kode Registrasi: " + idCatatan);
    }

    // READ
    public void tampilkanData() {
        view.tampilkanHeaderTabel();
        List<CatatanRehabilitasi> daftarKonservasi = dao.getAllCatatan();

        if (daftarKonservasi.isEmpty()) {
            view.tampilkanPesan("|                                                 Belum ada data konservasi.                                                 |");
        } else {
            for (CatatanRehabilitasi c : daftarKonservasi) {
                CatatanRehabilitasiWrapper w = new CatatanRehabilitasiWrapper();
                w.idCatatan = c.getIdCatatan();
                w.kategori = c.getOrangutan().getKategori();
                w.nama = c.getOrangutan().getNama();
                w.umur = c.getOrangutan().getUmurTahun() + " Thn";
                w.gender = c.getOrangutan().getJenisKelamin();
                w.wilayah = c.getLokasi().getNamaWilayah();
                w.provinsi = c.getLokasi().getProvinsi();
                w.status = c.getStatusKesehatan();
                w.infoTambahan = c.getOrangutan().getInfoTambahan();
                view.tampilkanBarisData(w);
            }
        }
        view.tampilkanFooterTabel();
    }

    // UPDATE
    public void updateData() {
        tampilkanData();
        List<CatatanRehabilitasi> daftar = dao.getAllCatatan();
        if (daftar.isEmpty()) return;

        String idCari = view.inputString("\nMasukkan ID Catatan yang ingin diubah: ");
        CatatanRehabilitasi target = cariDataById(daftar, idCari);

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
                    if (target.getOrangutan() instanceof OrangutanJantan) {
                        OrangutanJantan oj = (OrangutanJantan) target.getOrangutan();
                        oj.setUkuranCheekPads(view.inputDouble("Cheek Pads baru (cm): "));
                    } else if (target.getOrangutan() instanceof OrangutanBetina) {
                        OrangutanBetina ob = (OrangutanBetina) target.getOrangutan();
                        ob.setJumlahAnak(view.inputInteger("Jumlah anak baru: "));
                    }
                    view.tampilkanPesan("Info khusus berhasil diubah.");
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
        List<CatatanRehabilitasi> daftar = dao.getAllCatatan();
        if (daftar.isEmpty()) return;

        String idCari = view.inputString("\nMasukkan ID Catatan yang ingin dihapus: ");
        dao.deleteCatatan(idCari);
        view.tampilkanPesan("Data catatan " + idCari + " berhasil dihapus!");
    }

    // HELPER METHODS
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

    private CatatanRehabilitasi cariDataById(List<CatatanRehabilitasi> daftar, String id) {
        for (CatatanRehabilitasi c : daftar) {
            if (c.getIdCatatan().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }
}