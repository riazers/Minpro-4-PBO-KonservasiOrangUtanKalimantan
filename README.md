# Minpro-4-PBO-KonservasiOrangutanKalimantan

    Dibuat oleh: Riaz Ramadhan Al Fattah
    NIM: 2509116106

<p align="center">
<img width="900" height="200" alt="image" src="https://github.com/user-attachments/assets/8dc0e694-bbe1-461f-ac26-e6d4f6f822b0" />
</p>

## Daftar Isi
### - [1. Deskripsi Program](1.-deskripsi-program) 
### - [2. Penjelasan Alur Program](2.-penjelasan-alur-program)
### - [3. Struktur Package (Model-View-Controller)](3.-struktur-package-mvc)
### - [4. Penerapan Encapsulation](4.-penerapan-encapsulation)
### - [5. Penerapan Inheritance](5.-penerapan-inheritance)
### - [6. Penerapan Polymorphism](6.-penerapan-polymorphism)
### - [7. Penerapan Validasi Input](7.-penerapan-validasi-input)
### - [8. Penerapan Abstraction](8.-penerapan-abstraction)
### - [9. Penerapan Access Modifer](9.-penerapan-access-modifier)
### - [10. Penerapan Interface](10.-penerapan-interface)
### - [11. Integrasi Database (JDBC & DAO)(11-integrasi-database-jdbc--dao)
### - [12. Skema Database & Dummy Data (.sql)](13-skema-database--dummy-data-sql)
### - [13. Output Program](14-output-program)
### - [14. Kesimpulan Mini Project 4](15-kesimpulan-mini-project-4)

## 1. Deskripsi Program
**Sistem Konservasi & Rehabilitasi Orangutan Kalimantan** adalah aplikasi berbasis **Java CLI** lanjutan yang dikembangkan dari **Mini Project 1**. Program ini digunakan untuk mendata, memantau, dan mengelola tahapan rehabilitasi orangutan di berbagai Taman Nasional di Pulau Kalimantan. Program menerapkan **CRUD penuh** dengan pendekatan **Object-Oriented Programming (OOP)** serta **arsitektur MVC (Model–View–Controller)**.

Pada Mini Project 2 ini ditambahkan:
- **Inheritance**: `Orangutan` (superclass) dengan 2 subclass `OrangutanJantan` dan `OrangutanBetina`.
- **Polymorphism**: method `getJenisKelamin()`, `getKategori()`, dan `getInfoTambahan()` di-*override* oleh subclass, serta *casting* runtime (`instanceof`) pada saat update.
- **Struktur MVC**: pemisahan `model`, `view`, `controller`, `main`.
- **Dummy data awal**: 2 data orangutan langsung tampil saat fitur Read pertama kali dibuka.


## 2. Penjelasan Alur Program

**a. Inisialisasi** → Saat `Main` dijalankan, ia membuat objek `KonservasiView` lalu `KonservasiController`. Di dalam constructor Controller, method `inisialisasiDataAwal()` otomatis mengisi **2 dummy data** (Boni – Jantan, Sisi – Betina) ke `ArrayList<CatatanRehabilitasi>`.

**b. Menu Utama (loop do-while)** → Menu ditampilkan berulang-ulang. Program hanya berhenti jika user memilih **5. Keluar**. Input menu divalidasi `inputInteger()`, sehingga mengetik huruf tidak membuat program crash.

**c. Create (Menu 1)** →
1. User memasukkan Nama (divalidasi tidak boleh kosong) dan Umur (wajib angka).
2. Program meminta jenis kelamin **[1/2] dalam perulangan do-while** — input di luar 1/2 ditolak dan diminta ulang.
3. Jika **Jantan** → diminta Ukuran Cheek Pads (desimal); jika **Betina** → diminta Jumlah Anak. Objek dibuat sebagai `OrangutanJantan` / `OrangutanBetina` (polymorphism).
4. User memilih Wilayah **[1-4]** dan Status **[1-4]** — keduanya juga divalidasi dengan perulangan.
5. Catatan tersimpan dengan ID otomatis `C1`, `C2`, dst., lalu muncul pesan sukses.

**d. Read (Menu 2)** → Seluruh isi ArrayList dicetak sebagai tabel (perulangan `for-each`). Kolom **Kategori**, **Gender**, dan **Info Tambahan** diambil dari method yang di-override subclass.

**e. Update (Menu 3)** →
1. Tabel ditampilkan dulu, lalu user memasukkan ID catatan. Jika ID tidak ditemukan, muncul pesan error dan kembali ke menu utama.
2. Jika ditemukan, muncul **submenu update [1-6]** (Nama, Umur, Info Khusus, Lokasi, Status, Selesai) yang berulang sampai user memilih 6.
3. Opsi 3 memakai `instanceof` + casting: Jantan → minta cheek pads baru; Betina → minta jumlah anak baru.
4. Setiap perubahan sukses ditampilkan pesan konfirmasi.

**f. Delete (Menu 4)** → Tabel ditampilkan, user memasukkan ID. Jika ditemukan, data dihapus dari ArrayList dan muncul pesan sukses; jika tidak, muncul pesan error.

**g. Keluar (Menu 5)** → Program menampilkan pesan penutup, Scanner ditutup (`closeScanner()`), dan loop berakhir.

---

## 3. Struktur Package (MVC)

      Pada Mini Project 4, arsitektur diperluas dengan 2 package baru: `config` (jembatan koneksi MySQL) dan `dao` (Data Access Object — satu-satunya pintu eksekusi query SQL).

      KonservasiOrangutanKalimantan/
      ├── konservasi_db.sql              ← (BARU) Skema database + dummy data awal
      ├── pom.xml                        ← dependency mysql-connector-j 8.4.0
      └── src/
          ├── config/                    ← (BARU) Konfigurasi Database
          │   └── Koneksi.java            ← Jembatan koneksi Java → MySQL (phpMyAdmin/Laragon)
          ├── dao/                       ← (BARU) Data Access Object
          │   └── KonservasiDAO.java     ← Eksekusi query SQL (CRUD) via PreparedStatement
          ├── controller/                ← C (Controller)
          │   └── KonservasiController.java  ← Pusat logika; memanggil DAO, tidak menyentuh SQL langsung
          ├── main/
          │   └── Main.java              ← Entry point (menu loop)
          ├── model/                     ← M (Model)
          │   ├── Orangutan.java         ← Superclass (abstract)
          │   ├── OrangutanJantan.java   ← Subclass 1
          │   ├── OrangutanBetina.java   ← Subclass 2
          │   ├── LokasiHabitat.java
          │   └── CatatanRehabilitasi.java
          └── view/                       ← V (View)
              └── KonservasiView.java    ← Tampilan & input user

      

<img width="592" height="431" alt="image" src="https://github.com/user-attachments/assets/dadfef8a-3a57-4b91-96a0-1b252acd6f71" />



## 4. Penerapan Encapsulation
Semua atribut pada class `Orangutan`, `OrangutanJantan`, `OrangutanBetina`, `LokasiHabitat`, dan `CatatanRehabilitasi` dideklarasikan `private`. Akses hanya melalui **getter** dan **setter** publik, sehingga data tidak dapat diubah sembarangan dari luar class.

Contoh:
```java
private String nama;
public String getNama() { return nama; }
public void setNama(String nama) { this.nama = nama; }
```
## 5. Penerapan Inheritance
**Inheritance adalah mekanisme di mana sebuah kelas (Subclass) mewarisi atribut (property) dan perilaku (method) dari kelas lain (Superclass).**

- Superclass: Orangutan — atribut umum: idOrangutan, nama, umurTahun.

- Subclass 1: OrangutanJantan extends Orangutan — tambahan ukuranCheekPads.

- Subclass 2: OrangutanBetina extends Orangutan — tambahan jumlahAnak.

Keyword super(...) dipakai di constructor subclass untuk memanggil constructor superclass.

## 6. Penerapan Polymorphism

```java
@Override
public String getJenisKelamin() { return "Jantan"; }

@Override
public String getInfoTambahan() {
    return String.format("Cheek Pads: %.1f cm", ukuranCheekPads);
}
```
Method yang sama (getJenisKelamin, getKategori, getInfoTambahan) dipanggil pada CatatanRehabilitasi tanpa tahu tipe konkretnya, inilah polymorphism runtime.
Casting runtime pada fitur update:

```java
if (target.getOrangutan() instanceof OrangutanJantan) {
    OrangutanJantan oj = (OrangutanJantan) target.getOrangutan();
    oj.setUkuranCheekPads(...);
}
```

---
## 7. Penerapan Validasi Input
Seluruh input user melewati validasi di KonservasiView.java sebelum diproses oleh Controller, sehingga program tidak pernah crash karena input salah:
- inputInteger() — menggunakan try-catch (NumberFormatException) di dalam perulangan while(true), sehingga input non-angka (misal "abc") akan diminta ulang sampai valid.
- inputDouble() — pola yang sama untuk angka desimal (misal input ukuran cheek pads "x.y").
- inputString() — memastikan teks tidak kosong (.trim() + pengecekan isEmpty()).
- Validasi rentang pilihan menu — pilihan jenis kelamin [1/2], wilayah [1-4], dan status [1-4] dibungkus perulangan do-while, sehingga input angka di luar rentang (misal 9) tidak akan "jatuh" ke pilihan default, melainkan diminta ulang.

```java
// Contoh: inputInteger() di KonservasiView
public int inputInteger(String pesan) {
    while (true) {
        System.out.print(pesan);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            tampilkanError("Masukkan angka yang valid!");
        }
    }
}
```
```java
// Contoh: validasi rentang di KonservasiController (pilihStatus)
int opsi;
do {
    opsi = view.inputInteger("Pilih Status [1-4]: ");
    if (opsi < 1 || opsi > 4) {
        view.tampilkanError("Pilihan tidak valid! Masukkan angka 1-4.");
    }
} while (opsi < 1 || opsi > 4);
```
Validasi dilakukan berlapis: tipe data (teks/angka) ditangani View, rentang nilai ditangani Controller dengan do-while.

---

## 8. Penerapan Abstraction
Abstraction diterapkan dengan dua cara:

1. Menyembunyikan detail implementasi di balik getter publik. Class Orangutan hanya mengekspos perilaku (method) seperti getKategori(), getJenisKelamin(), dan getInfoTambahan() — pemanggil tidak perlu tahu bagaimana nilai tersebut dihitung. Method yang sama menghasilkan keluaran berbeda tergantung subclass (Jantan/Betina), dan CatatanRehabilitasi cukup memanggil getInfoTambahan() tanpa peduli detail di dalamnya:

```java
// Controller cukup memanggil method — detail "apa yang di-return" disembunyikan
w.infoTambahan = c.getOrangutan().getInfoTambahan();
```
2. Pemisahan tanggung jawab berdasarkan abstraksi peran (MVC). Program diabstraksi menjadi 3 peran:
- KonservasiView → abstraksi dari "cara user berkomunikasi" (tampilan & input)
- KonservasiController → abstraksi dari "aturan bisnis" (CRUD, ArrayList)
- model → abstraksi dari "data dunia nyata" (Orangutan, Lokasi, Catatan)
Sebagai contoh, KonservasiView tidak tahu sama sekali bagaimana data disimpan atau dihapus — ia hanya menerima CatatanRehabilitasiWrapper (inner class) untuk menampilkan tabel. Ini menyembunyikan kompleksitas model dari View.

---

## 9. Access Modifier
Program menggunakan 3 dari 4 access modifier Java secara konsisten:

| Access Modifier | Penerapan di Program |
| --- | --- |
| `private` | Semua atribut di setiap class model (`idOrangutan`, `nama`, `umurTahun`, `ukuranCheekPads`, `jumlahAnak`, dll.) serta atribut `daftarKonservasi`, `view`, dan `counter` di Controller. Atribut tidak bisa diakses langsung dari luar class. |
| `public` | Constructor semua class, semua getter/setter, dan seluruh method lintas package (`tambahData()`, `tampilkanData()`, `main()`, dll.) agar dapat dipanggil dari package lain (`main` -> `controller` -> `view`). |
| `default` (package-private) | Class `CatatanRehabilitasiWrapper` di dalam `KonservasiView` tidak diberi modifier, sehingga hanya relevan dalam konteks package `view`. |

```java
// private: hanya bisa diakses lewat getter/setter
private ArrayList<CatatanRehabilitasi> daftarKonservasi;
private double ukuranCheekPads;

// public: method yang dipanggil lintas package
public void tampilkanData() { ... }
```
Kombinasi private + getter/setter inilah yang membuat Encapsulation (bagian 4) berfungsi penuh: tidak ada cara mengubah data orangutan dari luar tanpa melalui method yang sudah disediakan.

---

## 10. Penerapan Interface
Interface adalah "kontrak" berisi method-method kosong (tanpa body) yang harus diimplementasikan oleh class manapun yang menggunakannya. Berbeda dengan inheritance (yang dibatasi satu superclass), satu class bisa mengimplementasikan banyak interface sekaligus.

Dalam program ini, interface diterapkan dengan mendefinisikan kontrak operasi CRUD dan kontrak tampilan data, sehingga Controller diwajibkan menyediakan seluruh operasi tersebut:

```java
// file: interface/KonservasiInterface.java
public interface KonservasiInterface {
    // Kontrak operasi CRUD — semua method tanpa body
    void tambahData();          // Create
    void tampilkanData();       // Read
    void updateData();          // Update
    void hapusData();           // Delete
}
```

Kemudian KonservasiController mengimplementasikan kontrak tersebut dengan keyword implements:

```java
// file: controller/KonservasiController.java
public class KonservasiController implements KonservasiInterface {

    @Override
    public void tambahData() {
        // logika Create (sama seperti sebelumnya)
    }

    @Override
    public void tampilkanData() {
        // logika Read
    }

    @Override
    public void updateData() {
        // logika Update (instanceof + casting)
    }

    @Override
    public void hapusData() {
        // logika Delete
    }
}
```
Dengan struktur ini, Main bisa memperlakukan Controller polymorphically melalui interface:

```java
// Main.java — Controller di-upcast ke tipe interface-nya
KonservasiInterface controller = new KonservasiController(view);
controller.tampilkanData();   // method dipanggil lewat "kontrak", bukan class konkret
```

Manfaat interface dalam program ini:
- Kontrak yang dijamin — Compiler memaksa KonservasiController menyediakan keempat method CRUD. Kalau ada satu method yang lupa dibuat, program gagal compile, bukan gagal saat dijalankan.
- Ketergantungan yang rendah (loose coupling) — Main cukup mengenal KonservasiInterface, tidak perlu tahu detail KonservasiController. Jika nanti logika CRUD dipindah ke database, kode di Main tidak perlu diubah.
- Kerangka untuk pengembangan lanjut — Interface bisa dipakai juga untuk model, misalnya interface InfoTampil { String getKategori(); String getInfoTambahan(); } yang diimplementasikan OrangutanJantan dan OrangutanBetina, memperkuat polymorphism di bagian 6.

---



## 11. Integrasi Database (JDBC & DAO) 

### a. Koneksi JDBC (`config/Koneksi.java`)
Class `Koneksi` menyediakan satu objek `Connection` yang dipakai bersama (singleton sederhana). URL menunjuk ke database `konservasi_db` di MySQL bawaan **Laragon** (phpMyAdmin):

```java
private static final String URL = "jdbc:mysql://localhost:3306/konservasi_db";
private static final String USER = "root";

public static Connection getConnection() {
    try {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
    } catch (SQLException e) { ... }
    return conn;
}
```


b. PreparedStatement (bukan Statement biasa)
Seluruh query di DAO menggunakan PreparedStatement dengan placeholder ?:

Mencegah SQL Injection — nilai input tidak pernah digabung langsung ke string SQL.
Type-safe — setString(), setInt(), setDouble() memastikan tipe data sesuai kolom.
Performa lebih baik — query dikompilasi sekali oleh database.

```java
// Contoh di KonservasiDAO.getAllCatatan()
String sql = "SELECT c.*, o.*, l.* FROM catatan_rehabilitasi c " +
             "JOIN orangutan o ON c.id_orangutan = o.id_orangutan " +
             "JOIN lokasi_habitat l ON c.id_lokasi = l.id_lokasi";
PreparedStatement pstmt = conn.prepareStatement(sql);
ResultSet rs = pstmt.executeQuery();
```

c. DAO (Data Access Object) — dao/KonservasiDAO.java
DAO memisahkan logika akses data dari logika bisnis. Controller hanya memanggil method DAO dan tidak pernah menulis SQL:

| Method DAO | Operasi | Teknik |
| :--- | :---: | :--- |
| `getAllCatatan()` | **Read** | `JOIN` 3 tabel, hasil dipetakan ke objek `OrangutanJantan` / `OrangutanBetina` (*polymorphism*) |
| `insertCatatan()` | **Create** | Transaction (`setAutoCommit(false)`, `commit`, `rollback`) — *insert* ke 2 tabel atomik: jika salah satu gagal, keduanya dibatalkan |
| `updateOrangutan()` | **Update** | `UPDATE` tabel `orangutan` berdasarkan `id_orangutan` |
| `updateCatatan()` | **Update** | `UPDATE` tabel `catatan_rehabilitasi` (lokasi & status) |
| `deleteCatatan()` | **Delete** | `DELETE` + mengembalikan nilai `boolean` (berdasarkan jumlah baris yang terpengaruh) agar Controller dapat memverifikasi keberhasilan operasi |

d. Resource management
Semua DAO memakai try-with-resources sehingga Connection, PreparedStatement, dan ResultSet otomatis tertutup — tidak ada connection leak.


## Bagian 12 — Skema Database & Dummy Data

```java
Database `konservasi_db` terdiri dari **3 tabel** dengan relasi foreign key:

      orangutan (PK id_orangutan) ──< catatan_rehabilitasi (PK id_catatan, FK id_orangutan, FK id_lokasi) >── lokasi_habitat (PK id_lokasi)

- `orangutan` — data individu: nama, umur, jenis_kelamin, plus kolom khusus `cheek_pads` (DOUBLE, NULL untuk Betina) dan `jumlah_anak` (INT, NULL untuk Jantan). Pola ini merepresentasikan inheritance Jantan/Betina dalam satu tabel (single-table inheritance).
- `lokasi_habitat` — 4 lokasi Taman Nasional di Kalimantan.
- `catatan_rehabilitasi` — penghubung orangutan dengan lokasi + status rehabilitasi.

### Dummy data awal — sesuai ketentuan, diisi **lewat file .sql / phpMyAdmin, bukan lewat kode**:

```sql
INSERT INTO lokasi_habitat VALUES
('LOC-01', 'TN Tanjung Puting', 'Kalimantan Tengah', 'Taman Nasional'),
('LOC-02', 'TN Sebangau', 'Kalimantan Tengah', 'Taman Nasional'),
('LOC-03', 'TN Betung Kerihun', 'Kalimantan Barat', 'Taman Nasional'),
('LOC-04', 'TN Kutai', 'Kalimantan Timur', 'Taman Nasional');

INSERT INTO orangutan VALUES
('ID-001', 'Boni', 12, 'Jantan', 18.5, NULL),
('ID-002', 'Sisi', 8, 'Betina', NULL, 1);

INSERT INTO catatan_rehabilitasi VALUES
('C1', 'ID-001', 'LOC-01', 'Sekolah Hutan'),
('C2', 'ID-002', 'LOC-02', 'Pra-Rilis');
```
File konservasi_db.sql diimport melalui phpMyAdmin (Laragon): buat database konservasi_db → tab Import → pilih file .sql → Go. Setelah itu, fitur Read langsung menampilkan data tanpa input manual.


## Bagian 13 — Output Program Mini Project 4 (panduan screenshot)

Sesuai permintaan, ini daftar screenshot yang perlu kamu ambil — urutannya sengaja dirancang untuk **membuktikan data tersimpan permanen di database**:

**1. Struktur Database di phpMyAdmin (Laragon)**
Bukti database sudah terimport: tampil database `konservasi_db` dengan 3 tabel (orangutan, catatan_rehabilitasi, lokasi_habitat) dan struktur kolomnya.
<img width="1037" height="355" alt="image" src="https://github.com/user-attachments/assets/27b51c41-fec7-41a1-97f2-c11bdb4d5b47" />


----
**2. Dummy Data di phpMyAdmin**
Isi tabel `orangutan` dan `catatan_rehabilitasi` — terlihat data dummy Boni (Jantan) & Sisi (Betina). Ini bukti dummy data diisi **lewat database, bukan kode**.
<img width="835" height="268" alt="image" src="https://github.com/user-attachments/assets/e8498c69-722d-4d5d-b42b-68847042db21" />


----
**3. Menu Utama Program**
Program berhasil terhubung ke database dan menampilkan menu 1-5.
<img width="466" height="198" alt="image" src="https://github.com/user-attachments/assets/abca3d17-5634-4f9d-82ad-2996231a12fc" />


----
**4. Fitur Read — Data Langsung Tampil dari Database**
Saat memilih menu 2, tabel menampilkan 2 dummy data (Boni, Sisi) yang diambil langsung dari MySQL via `getAllCatatan()` — bukan dari ArrayList.
<img width="1225" height="328" alt="image" src="https://github.com/user-attachments/assets/a1b835e8-6902-4935-bd10-5ce93712e7b6" />


----
**5. Fitur Create — Data Masuk ke Database**
Registrasi orangutan baru, lalu buka phpMyAdmin → tabel `orangutan` & `catatan_rehabilitasi` → baris baru muncul. Dua screenshot berdampingan membuktikan insert berhasil.
<img width="596" height="491" alt="image" src="https://github.com/user-attachments/assets/79bc5e95-e586-48b8-8f3a-21db03ac1e64" />

<img width="837" height="327" alt="image" src="https://github.com/user-attachments/assets/d0e12bb2-a3c1-4e82-92d1-5680222864fa" />


----
**6. Fitur Update — Perubahan Tersimpan Permanen**
Ubah nama/status salah satu data lewat program, lalu tunjukkan phpMyAdmin yang nilainya juga berubah.
<img width="1225" height="427" alt="image" src="https://github.com/user-attachments/assets/d7455af1-57a5-4292-8e6f-7fceb4d29a52" />
<img width="447" height="217" alt="image" src="https://github.com/user-attachments/assets/0adfa212-f8e1-42b0-8710-769133c0aac0" />



<img width="840" height="285" alt="image" src="https://github.com/user-attachments/assets/a171e07b-319b-4cab-a301-b88a0eee5f65" />


----
**7. Bukti Persistensi — Tutup & Jalankan Ulang Program**
Tutup program (menu 5), jalankan ulang, buka menu 2 → data hasil Create/Update **tetap ada**. Ini pembeda utama MP4 dari MP3 (yang datanya hilang karena hanya di ArrayList).
<img width="1251" height="447" alt="image" src="https://github.com/user-attachments/assets/ebf8ef24-2f71-4067-ab65-bae74fcf4ef7" />


----
**8. Fitur Delete — Data Hilang dari Database**
Hapus satu catatan lewat program, lalu tampilkan phpMyAdmin → baris catatan tersebut sudah tidak ada.
<img width="1221" height="396" alt="image" src="https://github.com/user-attachments/assets/a482c425-928d-41d4-8c2c-fa8bbb06772a" />

<img width="840" height="261" alt="image" src="https://github.com/user-attachments/assets/e5c4ec9a-b4ba-4a6e-8b8c-c80d4586bd89" />


----
**9. Keluar Program**
Pesan penutup, Scanner ditutup, program berakhir.
<img width="610" height="360" alt="image" src="https://github.com/user-attachments/assets/9151fddd-3e4d-4f2a-ac44-4bb4bf0749a8" />


## 14. Kesimpulan

Sistem Konservasi & Rehabilitasi Orangutan Kalimantan pada Mini Project 3 ini berhasil dikembangkan dari Mini Project sebelumnya dengan menerapkan seluruh ketentuan dan nilai tambah secara lengkap:

- **Inheritance** — Orangutan sebagai superclass dengan 2 subclass OrangutanJantan dan OrangutanBetina, melalui keyword extends dan super(...).
- **Polymorphism** — method getJenisKelamin(), getKategori(), dan getInfoTambahan() di-override oleh subclass, dipanggil melalui referensi superclass tanpa tahu tipe  konkretnya; ditambah casting runtime (instanceof) pada fitur update.
- **Validasi** input dua lapis — tipe data ditangani try-catch di KonservasiView, sedangkan rentang pilihan (jenis kelamin [1/2], wilayah [1-4], status [1-4]) ditangani perulangan do-while di KonservasiController agar input salah selalu diminta ulang, tidak "jatuh" ke pilihan default.
- **Abstraction** — melalui penyembunyian detail implementasi di balik method publik dan pemisahan tanggung jawab peran dalam arsitektur MVC.
- **Access Modifier** — private untuk seluruh atribut, public untuk method lintas package, dan default (package-private) untuk inner class CatatanRehabilitasiWrapper.
- **Nilai tambah: Interface** — KonservasiInterface berisi kontrak CRUD yang diimplementasikan KonservasiController dengan implements, menjamin seluruh operasi tersedia sejak kompilasi dan memperkuat loose coupling dengan Main.
- **Arsitektur MVC** — pemisahan model, view, controller, dan main membuat kode mudah dirawat dan dikembangkan.

- **Koneksi JDBC** — `Koneksi.java` menyediakan koneksi ke database `konservasi_db` (MySQL bawaan Laragon, diakses lewat phpMyAdmin).
- **PreparedStatement** — seluruh query memakai placeholder `?`, mencegah SQL Injection dan menjaga type-safety.
- **DAO Pattern** — `KonservasiDAO` menjadi satu-satunya pintu eksekusi SQL; Controller sepenuhnya bersih dari kode database (separation of concerns dalam MVC tetap terjaga).
- **Transaction management** — Create memakai `setAutoCommit(false)` + `commit()`/`rollback()` agar insert ke 2 tabel bersifat atomik.
- **Dummy data via .sql** — 2 data awal (Boni, Sisi) diimport langsung ke database melalui phpMyAdmin, sesuai ketentuan (tidak diisi lewat kode).
- **Persistensi terbukti** — data hasil Create/Update tetap ada setelah program ditutup dan dijalankan ulang, serta tercekap langsung di phpMyAdmin.



Seluruh fitur CRUD kini beroperasi penuh terhadap database: **Create** (insert 2 tabel dengan transaction), **Read** (JOIN 3 tabel), **Update** (pembaharuan tersimpan permanen — perbaikan dari draf awal yang hanya mengubah objek di memori), dan **Delete** (dengan pengecekan baris terpengaruh). Dengan demikian, tujuan Mini Project 4 — integrasi JDBC + PreparedStatement + DAO + dummy data via .sql — telah tercapai secara penuh.

