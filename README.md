# Minpro-3-PBO-KonservasiOrangutanKalimantan

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
### - [11. Output Program](11.-output-program)
### - [12. Kesimpulan](12.-kesimpulan)

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

## 11. Output Program

**1. Menu Utama**
Tampilan awal saat program dijalankan. Terdapat 5 pilihan menu; dummy data sudah otomatis tersedia di memori.

<img width="470" height="207" alt="image" src="https://github.com/user-attachments/assets/06691a50-b5ec-42ff-b5d1-5a74963daf8a" />


----
**2. Fitur Read — Menampilkan Dummy Data Awal**
Saat memilih menu 2, tabel langsung menampilkan 2 data dummy (Boni – Jantan, Sisi – Betina) tanpa input manual. Kolom Kategori, Gender, dan Info Tambahan adalah hasil method overriding (polymorphism).

<img width="1376" height="377" alt="image" src="https://github.com/user-attachments/assets/438f41b8-b2d0-4c9b-8ee0-5ba1c2806320" />



----
**3. Fitur Create Jantan — Validasi Input**
Contoh registrasi Orangutan Jantan. Sengaja dimasukkan input yang salah lebih dulu: jenis kelamin `8` dan wilayah `abc` — program menolak dan meminta ulang (do-while + try-catch) sampai input benar (pilih 1, lalu isi Cheek Pads).

<img width="455" height="387" alt="image" src="https://github.com/user-attachments/assets/0352b6d4-b502-4e22-b0fe-3bd928d6df0b" />

<img width="455" height="340" alt="image" src="https://github.com/user-attachments/assets/b95ef160-44fa-4a9f-a0eb-0c4ea36e3f0a" />

<img width="486" height="340" alt="image" src="https://github.com/user-attachments/assets/6786a67a-abdc-4877-ad7d-c6c70ac366a0" />


----
**4. Fitur Create Betina — Registrasi Orangutan Betina**
Pengisian data Betina: nama, umur, jenis kelamin (pilih 2), lalu Jumlah Anak.

<img width="492" height="442" alt="image" src="https://github.com/user-attachments/assets/797ec475-5c87-49e6-8644-a82152957ac2" />


----
**5. Fitur Read — Setelah Penambahan Data**
Seluruh data (dummy + baru) tampil. Kolom Info Tambahan Jantan (Cheek Pads) dan Betina (Jumlah Anak) berbeda — bukti polymorphism bekerja.

<img width="1222" height="377" alt="image" src="https://github.com/user-attachments/assets/99d71713-01c0-4254-b63e-aca89add9533" />


----
**6. Fitur Update — Memilih ID & Submenu**
User memilih menu 3, memasukkan ID catatan (mis. C2), lalu muncul submenu update [1-6].

<img width="1227" height="563" alt="image" src="https://github.com/user-attachments/assets/b5bfb58b-5381-4acf-82e6-274fa00f6f1b" />


----
**7. Fitur Update — Mengubah Info Khusus (instanceof + casting)**
Opsi 3 mendeteksi tipe runtime: Jantan → cheek pads baru; Betina → jumlah anak baru.

<img width="397" height="240" alt="image" src="https://github.com/user-attachments/assets/891e9965-09b7-40d0-bada-fe8635c5fbb6" />

<img width="392" height="236" alt="image" src="https://github.com/user-attachments/assets/9107794f-3f6a-41c3-9a97-945036d3a3bb" />


----
**8. Fitur Update — Mengubah Status Kesehatan**
Opsi 5. Pilih status baru [1-4]; input di luar rentang ditolak. Perhatikan opsi 4 kini tersimpan sebagai **"Liar / Rilis Penuh"** sesuai teks menu (perbaikan bug Mini Project 2).

<img width="405" height="347" alt="image" src="https://github.com/user-attachments/assets/4134a55e-1658-4cc3-ac36-f1af0c69eb7d" />


----
**9. Fitur Delete — Menghapus Data**
Menu 4: masukkan ID (mis. C2) → data dihapus dari ArrayList, muncul pesan sukses, dan tabel berikutnya sudah tidak memuat data tersebut.

<img width="1227" height="357" alt="image" src="https://github.com/user-attachments/assets/e7d452aa-b173-4194-84a7-874323f5a6ad" />

<img width="1216" height="606" alt="image" src="https://github.com/user-attachments/assets/3eafa819-d6d1-4fb8-9723-61141f708222" />


----
**10. Keluar Program**
Menu 5: pesan penutup ditampilkan, Scanner ditutup, program berakhir.

<img width="595" height="352" alt="image" src="https://github.com/user-attachments/assets/317771bf-e544-4c6f-8512-526023a8ec31" />

## 12. Integrasi Database (JDBC & DAO) 

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

```java

## Bagian 14 — Skema Database & Dummy Data

```markdown
## 14. Skema Database & Dummy Data (.sql)

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


## 15. Kesimpulan

Sistem Konservasi & Rehabilitasi Orangutan Kalimantan pada Mini Project 3 ini berhasil dikembangkan dari Mini Project sebelumnya dengan menerapkan seluruh ketentuan dan nilai tambah secara lengkap:

- **Inheritance** — Orangutan sebagai superclass dengan 2 subclass OrangutanJantan dan OrangutanBetina, melalui keyword extends dan super(...).
- **Polymorphism** — method getJenisKelamin(), getKategori(), dan getInfoTambahan() di-override oleh subclass, dipanggil melalui referensi superclass tanpa tahu tipe  konkretnya; ditambah casting runtime (instanceof) pada fitur update.
- **Validasi** input dua lapis — tipe data ditangani try-catch di KonservasiView, sedangkan rentang pilihan (jenis kelamin [1/2], wilayah [1-4], status [1-4]) ditangani perulangan do-while di KonservasiController agar input salah selalu diminta ulang, tidak "jatuh" ke pilihan default.
- **Abstraction** — melalui penyembunyian detail implementasi di balik method publik dan pemisahan tanggung jawab peran dalam arsitektur MVC.
- **Access Modifier** — private untuk seluruh atribut, public untuk method lintas package, dan default (package-private) untuk inner class CatatanRehabilitasiWrapper.
- **Nilai tambah: Interface** — KonservasiInterface berisi kontrak CRUD yang diimplementasikan KonservasiController dengan implements, menjamin seluruh operasi tersedia sejak kompilasi dan memperkuat loose coupling dengan Main.
- **Arsitektur MVC** — pemisahan model, view, controller, dan main membuat kode mudah dirawat dan dikembangkan.

**Perbaikan spesifik dari Mini Project 2 juga telah diterapkan: teks opsi 4 pada pilihStatus() kini tersimpan sebagai "Liar / Rilis Penuh" sesuai teks menu, dan bug input di luar rentang kini tertangani oleh perulangan validasi.**

**Seluruh fitur CRUD berjalan baik — data dapat dibuat (Create), ditampilkan dalam format tabel (Read), diperbarui melalui submenu (Update), dan dihapus berdasarkan ID (Delete) — dengan 2 dummy data otomatis tersedia sejak program dijalankan.** Dengan demikian, tujuan Mini Project 3 — penguasaan konsep OOP lanjutan (inheritance, polymorphism, abstraction, access modifier, validasi, interface) dalam kerangka MVC — telah tercapai secara penuh.

