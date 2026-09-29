# Sistem Manajemen Perpustakaan Mini (Java)



## Daftar Isi

1. [Fitur](#1-fitur)
2. [Struktur *Package*](#2-struktur-package)
3. [Konsep yang Diterapkan](#4-konsep-yang-diterapkan)
4. [Penjelasan Kode](#5-penjelasan-kode)

   * [4.1 Book dan Member (model)](#41-book-dan-member-model)
   * [4.2 Kelas Exception](#42-kelas-exception)
   * [4.3 LibraryService](#43-libraryservice)
   * [4.4 MainApp (menu)](#44-mainapp-menu)
5. [Output dan Pembahasan](#5-output-dan-pembahasan)

   * [5.1 Output Lengkap Program](#51-output-lengkap-program)
   * [5.2 Pembahasan](#52-pembahasan)

\---

## 1\. Fitur

|Menu|Fungsi|
|-|-|
|1. Tambah Buku|Menambah buku dengan validasi masukan|
|2. Daftar Buku|Menampilkan koleksi dan jumlah buku per kategori|
|3. Cari Buku|Mencari berdasarkan judul atau kategori|
|4. Pinjam Buku|Meminjam buku (maksimal 3 buku aktif per anggota)|
|5. Kembalikan Buku|Mengembalikan buku yang sedang dipinjam|
|6. Laporan Perpustakaan|Total pinjaman, buku terpopuler, anggota teraktif, kategori terpopuler|
|7. Daftarkan Anggota|Menambah anggota (menu tambahan)|
|8. Keluar|Menghentikan program|

[Kembali ke Daftar Isi](#daftar-isi)

## 2\. Struktur *Package*

```
library
├── model      → Book, Member                  (data)
├── service    → LibraryService                (logika bisnis)
├── exception  → 5 kelas galat khusus          (kondisi error)
└── main       → MainApp                       (menu dan input Scanner)
```

[Kembali ke Daftar Isi](#daftar-isi)

## 3\. Konsep yang Diterapkan

|Konsep|Penerapan|
|-|-|
|*Class*, *constructor*|`Book(judul, penulis, tahunTerbit, kategori)`, `Member(id, nama)`|
|Tipe primitif|`int tahunTerbit`, `boolean tersedia`, `int jumlahDipinjam`|
|Tipe referensi|`String`, `ArrayList<Book>`, `HashMap<String, Member>`|
|Kondisional dan *looping*|`if`, `switch`, `for-each`, `while`|
|*Exception*|5 kelas turunan `Exception`|
|*Assertion*|`assert validasiAnggota(anggota)` sebelum transaksi|
|*Character*|`toUpperCase()`, `isWhitespace()`, `isLetterOrDigit()`|
|*String*|`toLowerCase()`, `contains()`, `trim()`, `equalsIgnoreCase()`, `split()`|

[Kembali ke Daftar Isi](#daftar-isi)

## 4\. Penjelasan Kode

### 4.1 Book dan Member (model)

`Book` menyimpan data buku. Atribut `tersedia` menunjukkan status saat ini, sedangkan `jumlahDipinjam` adalah hitungan kumulatif untuk analisis.

```java
public void pinjam()     { tersedia = false; jumlahDipinjam++; }
public void kembalikan() { tersedia = true; }   // hitungan tidak dikurangi
```

`Member` menyimpan pinjaman aktif dalam `ArrayList<Book>` dan batas pinjaman sebagai konstanta.

```java
public static final int MAKS\_PINJAM = 3;

public boolean isBatasTercapai() {
    return daftarPinjaman.size() >= MAKS\_PINJAM;
}
```

`totalPeminjaman` dipisahkan dari `daftarPinjaman` karena daftar akan berkurang saat buku dikembalikan, sedangkan jumlah aktivitas anggota tidak boleh berkurang.

[Kembali ke Daftar Isi](#daftar-isi)

### 4.2 Kelas *Exception*

|Kelas|Dilempar ketika|
|-|-|
|`BookNotFoundException`|Judul tidak ditemukan|
|`BookAlreadyBorrowedException`|Buku sedang dipinjam|
|`BorrowLimitExceededException`|Anggota sudah meminjam 3 buku|
|`MemberNotFoundException`|ID anggota tidak terdaftar|
|`BookNotBorrowedException`|Anggota mengembalikan buku yang bukan pinjamannya|

[Kembali ke Daftar Isi](#daftar-isi)

### 4.3 LibraryService 

**Pencarian.** Kata kunci dan teks sama-sama diubah ke huruf kecil, lalu dicocokkan sebagian dengan `contains()`. Karena itu `Fiksi` cocok dengan `Fiksi Populer`.

```java
String target = (berdasarkanJudul ? b.getJudul() : b.getKategori()).toLowerCase();
if (target.contains(kunci)) hasil.add(b);
```

**Jumlah buku per kategori.** Satu *looping* dengan `HashMap`; `getOrDefault` menangani kategori yang baru muncul.

```java
jumlah.put(k, jumlah.getOrDefault(k, 0) + 1);
```

**Peminjaman.** Pemeriksaan dilakukan berurutan, dan data baru berubah hanya jika semuanya lolos.

```java
Member anggota = cariAnggota(idAnggota);                    // MemberNotFoundException
assert validasiAnggota(anggota) : "Data anggota tidak valid";
Book buku = cariPersis(judul);
if (buku == null) throw new BookNotFoundException(judul);
if (!buku.isTersedia()) throw new BookAlreadyBorrowedException(buku.getJudul());
if (anggota.isBatasTercapai()) throw new BorrowLimitExceededException(anggota.getNama(), Member.MAKS\_PINJAM);
buku.pinjam();
anggota.tambahPinjaman(buku);
```

**Pengembalian.** Nilai balik `ArrayList.remove()` dipakai untuk memastikan buku memang dipinjam anggota tersebut.

```java
if (!anggota.hapusPinjaman(buku)) throw new BookNotBorrowedException(...);
buku.kembalikan();
```

**Manipulasi *Character*.** Judul dikapitalkan per kata, kecuali kata sambung (*dan*, *di*, *untuk*, dan sejenisnya) sesuai EYD. ID anggota diperiksa dengan `Character.isLetterOrDigit()`.

**Laporan.** Statistik dihitung dari data yang sudah ada, bukan disimpan di variabel terpisah, sehingga tidak bisa tidak sinkron.

|Statistik|Sumber hitungan|
|-|-|
|Total pinjaman|Jumlah `jumlahDipinjam` seluruh buku|
|Buku terpopuler|`jumlahDipinjam` terbesar|
|Kategori terpopuler|Jumlah `jumlahDipinjam` buku per kategori|
|Anggota teraktif|`totalPeminjaman` terbesar|

Jika nilai tertinggi seri, semua yang seri ditampilkan. Jika belum ada peminjaman, laporan menulis "belum ada peminjaman".

[Kembali ke Daftar Isi](#daftar-isi)

### 4.4 MainApp (menu)

Menu berjalan dalam *loop* `while` dan dipilih dengan `switch`. Semua masukan dibaca dengan `nextLine()` agar tidak ada sisa baris baru pada *buffer*. Angka yang salah ketik (misalnya `abc`) ditangani `NumberFormatException` dan diminta ulang.

```java
try {
    Book b = service.pinjamBuku(id, judul);
    System.out.println("Berhasil meminjam: " + b.getJudul());
} catch (MemberNotFoundException | BookNotFoundException
         | BookAlreadyBorrowedException | BorrowLimitExceededException e) {
    System.out.println("Peminjaman gagal: " + e.getMessage());
}
```

[Kembali ke Daftar Isi](#daftar-isi)

## 5\. Output dan Pembahasan

### 5.1 Output Lengkap Program

```text
run:

===== PERPUSTAKAAN MINI =====
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftarkan Anggota
8. Keluar
Pilih menu: 6
========== LAPORAN PERPUSTAKAAN ==========
Total buku                  : 6
Buku tersedia               : 6
Buku sedang dipinjam        : 0
Total anggota               : 4
Jumlah total pinjaman       : 0
Buku paling sering dipinjam : - (belum ada peminjaman)
Anggota paling aktif        : - (belum ada peminjaman)
Kategori paling populer     : - (belum ada peminjaman)
Jumlah buku per kategori    :
  - Pengembangan Diri         : 4 buku
  - Fiksi Populer             : 1 buku
  - Kumpulan Puisi            : 1 buku
==========================================

===== PERPUSTAKAAN MINI =====
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftarkan Anggota
8. Keluar
Pilih menu: 1
--- Tambah Buku ---
Judul        : Home Sweet Loan
Penulis      : Almira Bastari
Tahun terbit : 2023
Kategori     : Fiksi Populer
Berhasil menambah buku: Home Sweet Loan

===== PERPUSTAKAAN MINI =====
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftarkan Anggota
8. Keluar
Pilih menu: 2
--- Daftar Buku ---
No   Judul                            Penulis                Tahun   Kategori                   Status    
1    Seporsi Mie Ayam Sebelum Mati    Brian Khrisna          2025    Pengembangan Diri          Tersedia  
2    3726 MDPL                        Nurwina Sari           2024    Fiksi Populer              Tersedia  
3    Jika Kita Tak Pernah Jadi Apa-Apa Alvi Syahrin           2019    Pengembangan Diri          Tersedia  
4    Kamu Tidak Butuh Motivasi        Astrid Savitri         2025    Pengembangan Diri          Tersedia  
5    Buku Minta Disayang              Rintik Sedu            2026    Kumpulan Puisi             Tersedia  
6    Maaf Tuhan, Aku Hampir Menyerah  Alfialghazi            2020    Pengembangan Diri          Tersedia  
7    Home Sweet Loan                  Almira Bastari         2023    Fiksi Populer              Tersedia  

Jumlah buku per kategori:
  - Pengembangan Diri         : 4 buku
  - Fiksi Populer             : 2 buku
  - Kumpulan Puisi            : 1 buku

===== PERPUSTAKAAN MINI =====
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftarkan Anggota
8. Keluar
Pilih menu: 3
--- Cari Buku ---
1. Berdasarkan judul
2. Berdasarkan kategori
Pilihan: 2
Kata kunci: Fiksi
Ditemukan 2 buku.
No   Judul                            Penulis                Tahun   Kategori                   Status    
1    3726 MDPL                        Nurwina Sari           2024    Fiksi Populer              Tersedia  
2    Home Sweet Loan                  Almira Bastari         2023    Fiksi Populer              Tersedia  

===== PERPUSTAKAAN MINI =====
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftarkan Anggota
8. Keluar
Pilih menu: 4
--- Pinjam Buku ---
Anggota terdaftar:
  L003 - Thom Haye (pinjaman aktif: 0)
  L002 - Neymar (pinjaman aktif: 0)
  L001 - Lionel Messi (pinjaman aktif: 0)
  L004 - Marc Marquez (pinjaman aktif: 0)
ID anggota    : L001
Judul buku    : 3726 MDPL
Berhasil meminjam: 3726 MDPL

===== PERPUSTAKAAN MINI =====
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftarkan Anggota
8. Keluar
Pilih menu: 5
--- Kembalikan Buku ---
Anggota terdaftar:
  L003 - Thom Haye (pinjaman aktif: 0)
  L002 - Neymar (pinjaman aktif: 0)
  L001 - Lionel Messi (pinjaman aktif: 1)
  L004 - Marc Marquez (pinjaman aktif: 0)
ID anggota    : L001
Pinjaman aktif Lionel Messi:
No   Judul                            Penulis                Tahun   Kategori                   Status    
1    3726 MDPL                        Nurwina Sari           2024    Fiksi Populer              Dipinjam  
Judul buku    : 3726 MDPL
Berhasil mengembalikan: 3726 MDPL

===== PERPUSTAKAAN MINI =====
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftarkan Anggota
8. Keluar
Pilih menu: 7
--- Daftarkan Anggota ---
ID (huruf/angka, min. 3 karakter): L005
Nama: Karina Purna Ayunida
Anggota terdaftar: L005 - Karina Purna Ayunida (pinjaman aktif: 0)

===== PERPUSTAKAAN MINI =====
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftarkan Anggota
8. Keluar
Pilih menu: 6
========== LAPORAN PERPUSTAKAAN ==========
Total buku                  : 7
Buku tersedia               : 7
Buku sedang dipinjam        : 0
Total anggota               : 5
Jumlah total pinjaman       : 1
Buku paling sering dipinjam : 3726 MDPL (1 kali)
Anggota paling aktif        : Lionel Messi (L001) (1 kali)
Kategori paling populer     : Fiksi Populer (1 kali)
Jumlah buku per kategori    :
  - Pengembangan Diri         : 4 buku
  - Fiksi Populer             : 2 buku
  - Kumpulan Puisi            : 1 buku
==========================================

===== PERPUSTAKAAN MINI =====
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftarkan Anggota
8. Keluar
Pilih menu: 8
Terima kasih. Program selesai.
BUILD SUCCESSFUL (total time: 1 minute 16 seconds)
```

[Kembali ke Daftar Isi](#daftar-isi)

### 5.2 Pembahasan 

Percobaan dimulai dengan menu 6 (Laporan). Kondisi awal menunjukkan 6 buku (semuanya tersedia), 4 anggota, dan total pinjaman 0, sehingga statistik teratas menampilkan "belum ada peminjaman".



Menu 1 menambahkan buku Home Sweet Loan dan berhasil karena lolos semua validasi. Menu 2 lalu menampilkan 7 buku, dan jumlah kategori Fiksi Populer naik dari 1 menjadi 2. Menu 3 dengan kata kunci Fiksi menemukan 2 buku (3726 MDPL dan Home Sweet Loan) karena contains() mencocokkan sebagian teks.



Pada menu 4, anggota L001 (Lionel Messi) berhasil meminjam 3726 MDPL. Pada menu 5, daftar pinjaman aktif L001 memuat buku itu dengan status "Dipinjam", lalu buku berhasil dikembalikan. Menu 7 mendaftarkan anggota baru L005 yang lolos validasi ID. Terakhir, menu 8 menghentikan program.

Dibandingkan laporan pertama, laporan akhir menunjukkan anggota naik dari 4 menjadi 5 (menu 7), buku naik dari 6 menjadi 7 (menu 1), dan total pinjaman naik dari 0 menjadi 1 (menu 4).



"Buku sedang dipinjam" bernilai 0 karena buku sudah dikembalikan, sedangkan total pinjaman tetap 1 karena merupakan hitungan kumulatif yang tidak berkurang saat pengembalian. Tiga statistik teratas sama-sama bernilai 1 karena berasal dari satu transaksi yang sama. Angka juga konsisten: 7 tersedia + 0 dipinjam = 7 buku, dan 4 + 2 + 1 kategori = 7 buku.



