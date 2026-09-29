# Sistem Manajemen Perpustakaan Mini (Java)

Aplikasi konsol berbahasa Java untuk mengelola koleksi buku, anggota, serta transaksi peminjaman dan pengembalian. Selain operasi dasar (*CRUD*), aplikasi ini melakukan analisis sederhana terhadap koleksi buku dan aktivitas peminjaman.

## Daftar Isi

1. [Fitur](#1-fitur)
2. [Struktur *Package*](#2-struktur-package)
3. [Cara Menjalankan](#3-cara-menjalankan)
4. [Penerapan Konsep pada Kode](#4-penerapan-konsep-pada-kode)
5. [Penjelasan *Source Code*](#5-penjelasan-source-code)
6. [Pembahasan Output](#6-pembahasan-output)
7. [Pengujian Jalur Galat](#7-pengujian-jalur-galat)
8. [Keterbatasan dan Pengembangan](#8-keterbatasan-dan-pengembangan)

---

## 1. Fitur

| No | Fitur | Keterangan |
|----|-------|------------|
| 1 | Tambah buku | Validasi masukan, pembakuan huruf kapital, penolakan judul ganda |
| 2 | Daftar buku | Tabel seluruh koleksi beserta jumlah buku per kategori |
| 3 | Cari buku | Berdasarkan judul atau kategori, tidak peka huruf besar/kecil, cocok sebagian |
| 4 | Pinjam buku | Dengan tiga pemeriksaan: buku ada, buku tersedia, batas 3 buku |
| 5 | Kembalikan buku | Memeriksa bahwa buku memang dipinjam oleh anggota tersebut |
| 6 | Laporan perpustakaan | Total pinjaman, buku terpopuler, anggota teraktif, kategori terpopuler |
| 7 | Daftarkan anggota | Validasi ID dan nama (menu tambahan karena peminjaman memerlukan anggota) |
| 8 | Keluar | Menghentikan program |

## 2. Struktur *Package*

```
library
├── model
│   ├── Book.java
│   └── Member.java
├── service
│   └── LibraryService.java
├── exception
│   ├── BookNotFoundException.java
│   ├── BookAlreadyBorrowedException.java
│   ├── BorrowLimitExceededException.java
│   ├── MemberNotFoundException.java
│   └── BookNotBorrowedException.java
└── main
    └── MainApp.java
```

Pembagian tanggung jawab:

| *Package* | Tanggung jawab |
|-----------|----------------|
| `model` | Menyimpan data (`Book`, `Member`) dan perilaku yang melekat pada data itu |
| `service` | Menyimpan seluruh logika bisnis: validasi, transaksi, pencarian, analisis |
| `exception` | Kelas galat khusus (*custom exception*) untuk kondisi bisnis |
| `main` | Antarmuka pengguna berbasis `Scanner`; tidak memuat logika bisnis |

Pemisahan ini membuat `MainApp` hanya bertugas membaca masukan dan menampilkan hasil, sedangkan aturan bisnis berada di satu tempat (`LibraryService`).

## 3. Cara Menjalankan

Jalankan dari direktori `src`. Bendera `-ea` wajib agar *assertion* aktif.

```
mkdir out
javac -d out library/model/*.java library/exception/*.java library/service/*.java library/main/*.java
java -ea -cp out library.main.MainApp
```

Pada IDE (misalnya NetBeans), *assertion* diaktifkan melalui *VM Options*: `-ea`.

## 4. Penerapan Konsep pada Kode

| Konsep | Lokasi dan penerapan |
|--------|----------------------|
| *Class* dan *object* | `Book`, `Member`, `LibraryService`, `MainApp`; objek dibuat dengan `new` |
| *Constructor* | `Book(judul, penulis, tahunTerbit, kategori)` dan `Member(id, nama)` |
| *Method* | `pinjam()`, `kembalikan()`, `cariBuku()`, `pinjamBuku()`, `buatLaporan()`, dan lainnya |
| *Package* | `library.model`, `library.service`, `library.exception`, `library.main` |
| Variabel | Atribut *instance*, konstanta `MAKS_PINJAM` dan `KATA_SAMBUNG`, variabel lokal |
| Tipe primitif | `int tahunTerbit`, `boolean tersedia`, `int jumlahDipinjam`, `char c` |
| Tipe referensi | `String`, `ArrayList<Book>`, `HashMap<String, Member>`, `Scanner`, `StringBuilder` |
| Kondisional | `if`, `else if`, `switch`, operator ternary |
| *Looping* | `for`, `for-each`, `while` |
| *Exception* | Lima kelas turunan `Exception` dengan `throw`, `throws`, `try-catch`, dan *multi-catch* |
| *Assertion* | `assert validasiAnggota(anggota) : "..."` pada `pinjamBuku()` dan `kembalikanBuku()` |
| *Character* | `Character.toUpperCase()`, `toLowerCase()`, `isWhitespace()`, `isLetterOrDigit()` |
| *String* | `trim()`, `toLowerCase()`, `contains()`, `equalsIgnoreCase()`, `replaceAll()`, `split()`, `String.join()`, `String.format()` |

## 5. Penjelasan *Source Code*

### 5.1 `Book` (*model*)

Kelas ini merepresentasikan satu buku. Semua atribut bersifat `private` (enkapsulasi). Atribut identitas buku dibuat `final` karena tidak berubah setelah buku dibuat; hanya status ketersediaan dan hitungan peminjaman yang berubah.

```java
private final String judul;
private final String penulis;
private final int tahunTerbit;
private final String kategori;
private boolean tersedia;      // status ketersediaan
private int jumlahDipinjam;    // statistik: berapa kali buku dipinjam
```

*Constructor* menginisialisasi atribut. Buku baru selalu berstatus tersedia dan belum pernah dipinjam:

```java
public Book(String judul, String penulis, int tahunTerbit, String kategori) {
    this.judul = judul;
    this.penulis = penulis;
    this.tahunTerbit = tahunTerbit;
    this.kategori = kategori;
    this.tersedia = true;
    this.jumlahDipinjam = 0;
}
```

Perubahan status hanya melalui dua *method* sehingga aturannya terpusat:

```java
public void pinjam() {
    tersedia = false;
    jumlahDipinjam++;      // hitungan bertambah setiap kali dipinjam
}

public void kembalikan() {
    tersedia = true;       // hitungan TIDAK berkurang
}
```

Perhatikan bahwa `kembalikan()` tidak mengurangi `jumlahDipinjam`. Ini disengaja: `jumlahDipinjam` adalah statistik kumulatif untuk analisis, sedangkan `tersedia` adalah keadaan saat ini.

### 5.2 `Member` (*model*)

```java
public static final int MAKS_PINJAM = 3;

private final String id;
private final String nama;
private final ArrayList<Book> daftarPinjaman;  // pinjaman yang sedang aktif
private int totalPeminjaman;                   // akumulasi seluruh peminjaman
```

`daftarPinjaman` (`ArrayList<Book>`) menyimpan buku yang sedang dipinjam, sehingga ukurannya naik-turun mengikuti transaksi. Karena itu diperlukan `totalPeminjaman` terpisah: saat buku dikembalikan, daftar berkurang, tetapi jumlah aktivitas anggota tidak boleh berkurang (dipakai untuk menentukan anggota paling aktif).

```java
public boolean isBatasTercapai() {
    return daftarPinjaman.size() >= MAKS_PINJAM;
}

public void tambahPinjaman(Book buku) {
    daftarPinjaman.add(buku);
    totalPeminjaman++;
}
```

`getDaftarPinjaman()` mengembalikan **salinan** (`new ArrayList<>(daftarPinjaman)`), bukan daftar aslinya. Dengan cara ini kode di luar kelas tidak dapat menambah buku secara langsung dan melewati batas 3 buku.

### 5.3 Kelas *Exception* (*exception*)

Semua kelas mewarisi `Exception` sehingga merupakan *checked exception*: kompilator memaksa pemanggil menanganinya.

| Kelas | Dilempar ketika |
|-------|-----------------|
| `BookNotFoundException` | Judul tidak ada dalam koleksi |
| `BookAlreadyBorrowedException` | Buku yang hendak dipinjam sedang dipinjam |
| `BorrowLimitExceededException` | Anggota sudah memegang 3 buku aktif |
| `MemberNotFoundException` | ID anggota tidak terdaftar |
| `BookNotBorrowedException` | Anggota mengembalikan buku yang tidak ia pinjam |

Contoh bentuk kelas (pesan galat disusun di dalam *constructor* agar konsisten di seluruh program):

```java
public class BookNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public BookNotFoundException(String judul) {
        super("Buku berjudul \"" + judul + "\" tidak ditemukan.");
    }
}
```

### 5.4 `LibraryService` (*service*)

Kelas ini memegang dua struktur data utama:

```java
private final ArrayList<Book> daftarBuku = new ArrayList<>();
private final HashMap<String, Member> daftarAnggota = new HashMap<>();
```

`ArrayList` dipilih untuk buku karena koleksi bersifat berurutan dan sering ditelusuri. `HashMap` dipilih untuk anggota karena pencarian berdasarkan ID (kunci) berlangsung cepat.

#### a. Pencarian buku

```java
public ArrayList<Book> cariBuku(String kataKunci, boolean berdasarkanJudul) {
    if (kosong(kataKunci)) throw new IllegalArgumentException("Kata kunci tidak boleh kosong.");

    String kunci = kataKunci.trim().toLowerCase();
    ArrayList<Book> hasil = new ArrayList<>();
    for (Book b : daftarBuku) {
        String target = (berdasarkanJudul ? b.getJudul() : b.getKategori()).toLowerCase();
        if (target.contains(kunci)) {
            hasil.add(b);
        }
    }
    return hasil;
}
```

Kata kunci dan teks target sama-sama diubah ke huruf kecil sebelum dibandingkan dengan `contains()`, sehingga pencarian tidak peka huruf besar/kecil dan cocok sebagian. Contohnya, kata kunci `Fiksi` cocok dengan kategori `Fiksi Populer`.

#### b. Menghitung buku per kategori

```java
public HashMap<String, Integer> hitungBukuPerKategori() {
    HashMap<String, Integer> jumlah = new HashMap<>();
    for (Book b : daftarBuku) {
        String k = b.getKategori();
        jumlah.put(k, jumlah.getOrDefault(k, 0) + 1);
    }
    return jumlah;
}
```

Setiap buku menaikkan hitungan kategorinya satu tingkat; `getOrDefault(k, 0)` menangani kategori yang baru pertama kali muncul. Kompleksitas waktunya O(n) untuk n buku. Agar "Informatika" dan "informatika" tidak terhitung sebagai dua kategori, kategori dibakukan lebih dahulu oleh `normalisasiKategori()`.

#### c. Peminjaman

```java
public Book pinjamBuku(String idAnggota, String judul)
        throws MemberNotFoundException, BookNotFoundException,
               BookAlreadyBorrowedException, BorrowLimitExceededException {

    Member anggota = cariAnggota(idAnggota);
    assert validasiAnggota(anggota) : "Data anggota tidak valid sebelum transaksi peminjaman";

    Book buku = cariPersis(judul);
    if (buku == null) throw new BookNotFoundException(judul);
    if (!buku.isTersedia()) throw new BookAlreadyBorrowedException(buku.getJudul());
    if (anggota.isBatasTercapai()) {
        throw new BorrowLimitExceededException(anggota.getNama(), Member.MAKS_PINJAM);
    }

    buku.pinjam();
    anggota.tambahPinjaman(buku);
    assert anggota.getDaftarPinjaman().size() <= Member.MAKS_PINJAM : "Pinjaman melebihi batas";
    return buku;
}
```

Urutan pemeriksaan (validasi dahulu, perubahan data terakhir) membuat transaksi bersifat *all-or-nothing*: bila salah satu pemeriksaan gagal, tidak ada data yang berubah.

| Langkah | Pemeriksaan | Bila gagal |
|---------|-------------|------------|
| 1 | Anggota terdaftar | `MemberNotFoundException` |
| 2 | *Assertion* data anggota sah | `AssertionError` (hanya bila `-ea`) |
| 3 | Buku ada | `BookNotFoundException` |
| 4 | Buku tersedia | `BookAlreadyBorrowedException` |
| 5 | Pinjaman aktif < 3 | `BorrowLimitExceededException` |
| 6 | Ubah status buku dan daftar anggota | (berhasil) |

#### d. Pengembalian

`kembalikanBuku()` memakai pola yang sama. Pemeriksaan kepemilikan memanfaatkan nilai balik `boolean` dari `ArrayList.remove()`:

```java
if (!anggota.hapusPinjaman(buku)) throw new BookNotBorrowedException(anggota.getNama(), buku.getJudul());
buku.kembalikan();
```

Bila buku tidak ada di daftar pinjaman anggota, `remove()` mengembalikan `false` dan galat dilempar; buku milik orang lain tidak dapat "dikembalikan" oleh anggota yang salah.

#### e. Manipulasi *Character* dan *String*

Empat *method* privat memakai kelas `Character`:

```java
// Judul: kata sambung tidak dikapitalkan (sesuai EYD), kecuali kata pertama
private String kapitalisasiJudul(String teks) {
    String[] kata = teks.trim().replaceAll("\\s+", " ").split(" ");
    StringBuilder hasil = new StringBuilder();
    for (int i = 0; i < kata.length; i++) {
        String k = kata[i];
        if (i > 0 && KATA_SAMBUNG.contains(" " + k.toLowerCase() + " ")) {
            hasil.append(k.toLowerCase());
        } else {
            hasil.append(Character.toUpperCase(k.charAt(0))).append(k.substring(1));
        }
        if (i < kata.length - 1) hasil.append(' ');
    }
    return hasil.toString();
}

// ID anggota: hanya huruf atau angka, minimal 3 karakter
private boolean idValid(String id) {
    if (id.length() < 3) return false;
    for (int i = 0; i < id.length(); i++) {
        if (!Character.isLetterOrDigit(id.charAt(i))) return false;
    }
    return true;
}
```

| *Method* | Kelas `Character` yang dipakai | Tujuan |
|----------|-------------------------------|--------|
| `kapitalisasiJudul()` | `toUpperCase()` | Kapitalisasi judul sesuai EYD |
| `kapitalisasiKata()` | `isWhitespace()`, `toUpperCase()` | Kapitalisasi nama penulis dan anggota |
| `normalisasiKategori()` | `isWhitespace()`, `toUpperCase()`, `toLowerCase()` | Pembakuan kategori |
| `idValid()` | `isLetterOrDigit()` | Validasi format ID |

Untuk judul, hanya huruf pertama tiap kata yang diubah dan sisanya dipertahankan. Dengan begitu singkatan seperti `OOP` atau judul berangka seperti `3726 MDPL` tidak rusak (`Character.toUpperCase('3')` menghasilkan `'3'` kembali).

#### f. Analisis aktivitas

Statistik tidak disimpan dalam variabel terpisah, tetapi **dihitung dari satu sumber**, yaitu `Book.jumlahDipinjam` dan `Member.totalPeminjaman`:

```java
public int getTotalPinjaman() {
    int total = 0;
    for (Book b : daftarBuku) {
        total += b.getJumlahDipinjam();
    }
    return total;
}
```

Untuk mencari yang terbanyak, `buatLaporan()` menyusun tiga peta (`HashMap<String, Integer>`) lalu memanggil satu *method* pencari nilai maksimum yang dipakai bersama:

```java
private String ringkasTeratas(HashMap<String, Integer> peta) {
    int maks = 0;
    ArrayList<String> teratas = new ArrayList<>();
    for (Map.Entry<String, Integer> e : peta.entrySet()) {
        int nilai = e.getValue();
        if (nilai > maks) {
            maks = nilai;
            teratas.clear();
            teratas.add(e.getKey());
        } else if (nilai == maks && maks > 0) {
            teratas.add(e.getKey());
        }
    }
    if (teratas.isEmpty()) return "- (belum ada peminjaman)";
    return String.join(", ", teratas) + " (" + maks + " kali)";
}
```

| Peta | Kunci | Nilai | Menjawab |
|------|-------|-------|----------|
| `pinjamPerBuku` | Judul | `jumlahDipinjam` | Buku paling sering dipinjam |
| `pinjamPerKategori` | Kategori | Jumlah `jumlahDipinjam` semua buku pada kategori itu | Kategori paling populer |
| `pinjamPerAnggota` | Nama dan ID | `totalPeminjaman` | Anggota paling aktif |

Penanganan kasus khusus: bila beberapa entri seri di nilai tertinggi, semuanya ditampilkan; bila nilai tertinggi 0, laporan menuliskan "belum ada peminjaman" dan tidak memilih pemenang secara acak.

### 5.5 `MainApp` (*main*)

`MainApp` menjalankan menu dalam *loop* `while` dan mengarahkan pilihan dengan `switch`:

```java
while (berjalan) {
    tampilkanMenu();
    int pilihan = bacaAngka("Pilih menu: ");
    switch (pilihan) {
        case 1: menuTambahBuku(); break;
        ...
        case 8: berjalan = false; break;
        default: System.out.println("Pilihan tidak tersedia. Pilih angka 1 sampai 8.");
    }
}
```

Dua *method* bantu membaca masukan. Semua masukan dibaca dengan `nextLine()` (bukan `nextInt()`) untuk menghindari sisa karakter baris baru pada *buffer* `Scanner`. Angka diubah dengan `Integer.parseInt()` dan `NumberFormatException` ditangani dengan meminta masukan ulang:

```java
private static int bacaAngka(String prompt) {
    while (true) {
        String teks = bacaTeks(prompt);
        try {
            return Integer.parseInt(teks);
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa bilangan bulat. Coba lagi.");
        }
    }
}
```

Penanganan galat pada menu peminjaman memakai *multi-catch* karena keempat *exception* ditangani dengan cara yang sama, yaitu menampilkan pesannya:

```java
try {
    Book b = service.pinjamBuku(id, judul);
    System.out.println("Berhasil meminjam: " + b.getJudul());
} catch (MemberNotFoundException | BookNotFoundException
         | BookAlreadyBorrowedException | BorrowLimitExceededException e) {
    System.out.println("Peminjaman gagal: " + e.getMessage());
}
```

Sebelum menu ditampilkan, `isiDataAwal()` mengisi beberapa buku dan anggota agar aplikasi langsung dapat didemonstrasikan.

## 6. Pembahasan Output

Berikut adalah satu sesi eksekusi. Baris `BUILD SUCCESSFUL` di akhir keluaran menandakan proses *build* dan eksekusi dari lingkungan pengembangan terpadu (*IDE*) berjalan tanpa galat.

### 6.1 Urutan langkah dan hasilnya

| Langkah | Menu | Masukan | Hasil pada output | Pembahasan |
|---------|------|---------|-------------------|------------|
| 1 | 6 (Laporan) | (awal) | 6 buku, 6 tersedia, 4 anggota, total pinjaman 0 | Kondisi awal dari `isiDataAwal()`. Semua statistik "belum ada peminjaman" karena belum ada transaksi |
| 2 | 1 (Tambah Buku) | *Home Sweet Loan*, Almira Bastari, 2023, Fiksi Populer | "Berhasil menambah buku" | Lolos validasi: judul tidak kosong, tahun 2023 dalam rentang 1000 sampai tahun berjalan, judul belum ada |
| 3 | 2 (Daftar Buku) | (tanpa masukan) | 7 buku dan rekap kategori | Buku baru muncul di baris ke-7. Rekap kategori berubah dari Fiksi Populer 1 menjadi 2 |
| 4 | 3 (Cari Buku) | Kategori, kata kunci `Fiksi` | 2 buku ditemukan | `contains()` mencocokkan sebagian: `fiksi` termuat dalam `fiksi populer` |
| 5 | 4 (Pinjam Buku) | `L001`, `3726 MDPL` | "Berhasil meminjam: 3726 MDPL" | Semua pemeriksaan lolos; `Book.pinjam()` dan `Member.tambahPinjaman()` dijalankan |
| 6 | 5 (Kembalikan Buku) | `L001`, `3726 MDPL` | Menampilkan pinjaman aktif lalu "Berhasil mengembalikan" | Sebelum kembali, daftar anggota menunjukkan `L001 ... (pinjaman aktif: 1)` dan buku berstatus Dipinjam |
| 7 | 7 (Daftarkan Anggota) | `L005`, nama anggota | "Anggota terdaftar: L005 ..." | ID lolos `idValid()` (5 karakter, huruf dan angka) |
| 8 | 6 (Laporan) | (tanpa masukan) | Total pinjaman 1, dst. | Dibahas pada 6.2 |
| 9 | 8 (Keluar) | (tanpa masukan) | "Terima kasih. Program selesai." | `berjalan = false`, *loop* berhenti |

### 6.2 Analisis laporan akhir

```
Total buku                  : 7
Buku tersedia               : 7
Buku sedang dipinjam        : 0
Total anggota               : 5
Jumlah total pinjaman       : 1
Buku paling sering dipinjam : 3726 MDPL (1 kali)
Anggota paling aktif        : Lionel Messi (L001) (1 kali)
Kategori paling populer     : Fiksi Populer (1 kali)
```

1. **Konsistensi angka.** Buku tersedia (7) ditambah buku dipinjam (0) sama dengan total buku (7). Total buku bertambah dari 6 menjadi 7 dan total anggota dari 4 menjadi 5 sesuai penambahan pada langkah 2 dan 7.
2. **Statistik kumulatif berbeda dari status saat ini.** "Buku sedang dipinjam" bernilai 0 karena buku sudah dikembalikan, tetapi "Jumlah total pinjaman" tetap 1. Ini membuktikan bahwa `jumlahDipinjam` tidak dikurangi saat `kembalikan()`, sesuai rancangan pada 5.1.
3. **Ketiga statistik teratas berasal dari satu peristiwa.** Satu transaksi (L001 meminjam *3726 MDPL*) menaikkan tiga hitungan sekaligus: buku (`3726 MDPL`), anggota (`L001`), dan kategori (`Fiksi Populer`, kategori buku itu). Karena hanya ada satu transaksi, tidak terjadi seri.
4. **Jumlah per kategori.** 4 + 2 + 1 = 7, sama dengan total buku, sehingga tidak ada buku yang terlewat oleh *looping* penghitung.

### 6.3 Hal yang teramati pada output

| Pengamatan | Penjelasan | Saran perbaikan |
|------------|-----------|-----------------|
| Baris ke-3 pada tabel buku: judul *Jika Kita Tak Pernah Jadi Apa-Apa* menempel dengan nama penulis | Judul 34 karakter melebihi lebar kolom `%-32s`, sehingga tidak ada spasi pemisah | Perlebar menjadi `%-40s` pada kedua `printf` (judul dan isi), atau potong judul panjang |
| Urutan anggota (L003, L002, L001, L004) dan urutan kategori tidak terurut | `HashMap` tidak menjamin urutan. Urutan tetap yang sama di satu komputer, tetapi tidak dapat diandalkan | Gunakan `TreeMap` (urut kunci) atau `LinkedHashMap` (urut masuk) |
| Format `Lionel Messi (L001) (1 kali)` memuat dua pasang tanda kurung | Kunci peta berbentuk `Nama (ID)`, lalu jumlah ditambahkan dalam kurung | Ubah ke `Lionel Messi [L001] (1 kali)` atau `... - 1 kali` |
| Judul `3726 MDPL` dan `Home Sweet Loan` tampil utuh | `kapitalisasiJudul()` hanya menaikkan huruf pertama tiap kata dan tidak mengubah angka atau huruf lain | (tidak perlu) |
| Hanya jalur sukses yang tampak pada sesi ini | Jalur galat perlu diuji terpisah | Lihat bagian 7 |

## 7. Pengujian Jalur Galat

Pesan berikut adalah keluaran nyata program pada pengujian (dengan `-ea`), ditulis di sini sebagai bukti bahwa setiap *exception* bekerja. Contoh memakai data uji sendiri: anggota `A001` dan `A002` serta beberapa judul buku.

| Skenario | Masukan | Pesan yang tampil |
|----------|---------|-------------------|
| Buku tidak ditemukan | Pinjam judul `buku hantu` | `Peminjaman gagal: Buku berjudul "buku hantu" tidak ditemukan.` |
| Buku sudah dipinjam | Anggota lain meminjam buku yang sedang dipinjam | `Peminjaman gagal: Buku "Pemrograman Java Dasar" sedang dipinjam.` |
| Melebihi batas 3 buku | Meminjam buku ke-4 | `Peminjaman gagal: Anggota Sari Wulandari sudah meminjam 3 buku (batas maksimum). Kembalikan buku terlebih dahulu.` |
| Anggota tidak terdaftar | ID `X999` | `Peminjaman gagal: Anggota dengan ID "X999" tidak terdaftar.` |
| Mengembalikan buku yang tidak dipinjam | Anggota tanpa pinjaman mengembalikan buku | `Pengembalian gagal: Anggota Budi Santoso tidak sedang meminjam buku "Jaringan Komputer".` |
| Tahun tidak berupa angka | `abc` | `Input harus berupa bilangan bulat. Coba lagi.` |
| Tahun di luar rentang | `2050` | `Gagal menambah buku: Tahun terbit harus berada pada rentang 1000 sampai 2026.` |
| Judul ganda | `clean code` setelah `Clean Code` ada | `Gagal menambah buku: Buku berjudul "Clean Code" sudah ada.` |
| ID anggota tidak sah | `ab` atau `a-1` | `Pendaftaran gagal: ID anggota minimal 3 karakter dan hanya berisi huruf atau angka.` |
| Kata kunci kosong | (kosong) | `Pencarian gagal: Kata kunci tidak boleh kosong.` |

Catatan penting tentang batas pinjaman: aturan yang dipakai adalah **maksimum 3 buku aktif secara bersamaan**. Percobaan meminjam buku ke-4 ditolak, tetapi setelah satu buku dikembalikan, anggota dapat meminjam lagi.

## 8. Keterbatasan dan Pengembangan

1. ***Assertion* nonaktif secara bawaan.** Tanpa `-ea`, baris `assert` diabaikan. Karena itu *assertion* dipakai untuk invarian internal (kondisi yang seharusnya mustahil salah), sedangkan kesalahan masukan pengguna ditangani dengan *exception*. Pada aplikasi ini data anggota selalu dibuat melalui `daftarkanAnggota()` yang sudah memvalidasi, sehingga *assertion* berfungsi sebagai jaring pengaman bila kelak ada kode lain yang membuat `Member` tidak sah.
2. **Data tidak persisten.** Seluruh data hilang saat program ditutup. Pengembangan lanjutan: simpan ke berkas (CSV atau JSON) atau basis data melalui JDBC.
3. **Judul sebagai kunci pencarian.** Buku diidentifikasi lewat judul persis, sehingga judul ganda ditolak. Pada sistem nyata, gunakan ID buku atau ISBN yang unik.
4. **Belum ada tanggal dan denda.** Peminjaman belum mencatat tanggal pinjam, tenggat, dan keterlambatan. Pengembangan: tambahkan atribut `LocalDate` pada transaksi.
5. **Belum ada menu ubah dan hapus buku.** Fitur *update* dan *delete* dapat ditambahkan dengan pola yang sama seperti `tambahBuku()`, dengan pemeriksaan bahwa buku yang sedang dipinjam tidak boleh dihapus.
6. **Antarmuka konsol.** Logika bisnis sudah terpisah di `LibraryService`, sehingga antarmuka dapat diganti (misalnya JavaFX atau layanan web) tanpa mengubah kelas `model` dan `service`.
