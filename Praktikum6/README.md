# Program Pengelola Nilai Siswa (Java)

Program konsol sederhana berbahasa Java untuk mengelola data nilai siswa. Program membaca data dari papan ketik, menuliskannya ke berkas teks `laporan\_nilai.txt`, lalu membaca kembali berkas itu dan menampilkannya di layar.

Dibuat sebagai latihan materi **Input/Output** pada mata kuliah Pemrograman Berorientasi Objek (PBO), dengan gaya pemrograman prosedural/fungsional (tanpa membuat kelas tambahan selain kelas utama).

\---

## Daftar Isi

1. [Deskripsi Program](#1-deskripsi-program)
2. [Tujuan Pembelajaran](#2-tujuan-pembelajaran)
3. [Fitur Program](#3-fitur-program)
4. [Struktur Berkas](#4-struktur-berkas)
5. [Output](#7-output) 
6. [Format Isi Berkas Laporan](#8-format-isi-berkas-laporan) 
7. [Alur Program](#9-alur-program) 
8. [Penjelasan Kode per Bagian](#11-penjelasan-kode-per-bagian) 
9. [Penanganan Masukan Tidak Valid](#12-penanganan-masukan-tidak-valid)

\---

## 1\. Deskripsi Program

Program ini menyelesaikan tiga pekerjaan secara berurutan:

1. **Membaca masukan** jumlah siswa, lalu nama dan nilai tiap siswa dari papan ketik.
2. **Menulis laporan** ke berkas `laporan\_nilai.txt`, lengkap dengan status kelulusan tiap siswa dan ringkasan di akhir berkas.
3. **Membaca kembali** berkas tersebut baris demi baris dan menampilkannya di konsol sebagai bukti bahwa data berhasil tersimpan.

Aturan kelulusan:

```
nilai >= 70  ->  Lulus
nilai <  70  ->  Tidak Lulus
```

## 2\. Tujuan Pembelajaran

Setelah mempelajari program ini, pembaca diharapkan mampu:

* membaca masukan dari papan ketik memakai `Scanner`;
* menulis teks ke berkas memakai `FileWriter` dan `BufferedWriter`;
* membaca teks dari berkas memakai `FileReader` dan `BufferedReader`;
* menangani galat berkas dengan `try-catch` dan `IOException`;
* menutup *stream* secara otomatis dengan *try-with-resources*.

## 3\. Fitur Program

|No|Fitur|Keterangan|
|-|-|-|
|1|Masukan banyak siswa|Jumlah siswa ditentukan pengguna saat program berjalan|
|2|Penentuan status|Lulus atau Tidak Lulus ditentukan di dalam *loop*|
|3|Laporan berkas teks|Data tiap siswa ditulis ke `laporan\_nilai.txt`|
|4|Ringkasan kelas|Rata-rata nilai, jumlah lulus, dan jumlah tidak lulus|
|5|Pembacaan ulang berkas|Isi berkas ditampilkan kembali di konsol|
|6|Validasi masukan|Masukan yang salah diminta ulang, program tidak berhenti|
|7|Penutupan otomatis|*Stream* ditutup oleh *try-with-resources*|

## 4\. Struktur Berkas

```
.
├── LaporanNilai.java     # kode sumber program
├── README.md             # dokumentasi ini
└── laporan\_nilai.txt     # dibuat otomatis setelah program dijalankan
```

## 5\. Output

Teks setelah titik dua (misalnya `Siti` dan `60`) adalah ketikan pengguna. Contoh ini memakai dua siswa.

```run

Masukkan jumlah siswa: 2



Data siswa ke-1

Nama  : Putri

Nilai : 60



Data siswa ke-2

Nama  : Karina

Nilai : 90.50



Penulisan file selesai: laporan\_nilai.txt



>>> Isi file laporan\_nilai.txt <<<

=== LAPORAN NILAI SISWA ===

No   Nama                 Nilai    Status

\----------------------------------------------

1    Putri                60.00    Tidak Lulus

2    Karina               90.50    Lulus

\----------------------------------------------

RINGKASAN

Rata-rata nilai kelas : 75.25

Jumlah siswa lulus    : 1

Jumlah tidak lulus    : 1

BUILD SUCCESSFUL (total time: 20 seconds)
```

### 5.1 Penjelasan Keluaran 

Keluaran di konsol terbagi menjadi tiga tahap.

|Bagian keluaran|Penjelasan|
|-|-|
|`Masukkan jumlah siswa: 2`|*Prompt* pertama. Angka `2` diketik pengguna dan dibaca oleh `bacaJumlahSiswa`. Angka ini menentukan berapa kali *loop* berjalan|
|`Data siswa ke-1`|Judul setiap siswa, dicetak di awal setiap putaran *loop* sehingga pengguna tahu sedang mengisi data siswa yang mana|
|`Nama  :` dan `Nilai :`|*Prompt* dari `bacaNama` dan `bacaNilai`. Keduanya tampil menurun, satu baris untuk satu isian|
|Baris kosong antarsiswa|Pemisah agar data siswa yang satu tidak menempel dengan siswa berikutnya|
|`Penulisan file selesai: laporan\_nilai.txt`|Penanda bahwa seluruh data dan ringkasan sudah ditulis ke berkas|
|`>>> Isi file laporan\_nilai.txt <<<`|Pembuka tahap kedua: program membuka kembali berkas untuk dibaca|
|Baris setelah pembuka tersebut|Isi berkas yang dibaca baris demi baris dengan `readLine()`. Bagian inilah bukti bahwa data benar-benar tersimpan di berkas|

### 5.2 Penjelasan Isi Berkas Laporan

Setiap baris laporan pada contoh di atas dihasilkan dengan cara berikut.

|Baris pada laporan|Asal dan penjelasan|
|-|-|
|`=== LAPORAN NILAI SISWA ===`|Judul laporan, ditulis sekali di awal berkas|
|`No   Nama   Nilai   Status`|Judul kolom, ditulis dengan `String.format` agar kolom sejajar|
|`----------------------------------------------`|Garis pemisah antarbagian|
|`1    Putri    60.00    Tidak Lulus`|Nilai `60` kurang dari 70, maka status **Tidak Lulus**|
|`2    Karina    90.50    Lulus`|Nilai `90.5` lebih besar dari 70, maka status **Lulus**|
|`Rata-rata nilai kelas : 75.25`|`(60 + 90.5) / 2`, ditampilkan dengan dua angka di belakang koma|
|`Jumlah siswa lulus    : 1`|Karina|
|`Jumlah tidak lulus    : 1`|Putri|

Catatan tentang batas kelulusan: syaratnya `nilai >= 70`, sehingga nilai tepat `70` berstatus **Lulus**.

Cara memeriksa ringkasan: jumlah lulus dan tidak lulus harus selalu sama dengan jumlah siswa.

```
jumlahLulus + jumlahTidakLulus = jumlahSiswa
```

## 6\. Format Isi Berkas Laporan

Berkas `laporan\_nilai.txt` terdiri atas tiga bagian.

|Bagian|Isi|
|-|-|
|Kepala|Judul laporan dan judul kolom `No`, `Nama`, `Nilai`, `Status`|
|Data|Satu baris untuk setiap siswa|
|Ringkasan|Rata-rata nilai kelas, jumlah lulus, dan jumlah tidak lulus|

Rumus yang dipakai pada bagian ringkasan:

```
rataRata         = totalNilai / jumlahSiswa
jumlahLulus      = banyak siswa dengan nilai >= 70
jumlahTidakLulus = banyak siswa dengan nilai <  70
```

## 7\. Alur Program

```
Mulai
  |
  v
Baca jumlah siswa (diulang sampai valid)
  |
  v
Buka BufferedWriter ke laporan\_nilai.txt
  |
  v
Tulis kepala laporan
  |
  v
Ulangi untuk setiap siswa:
    - baca nama
    - baca nilai
    - tentukan status (>= 70 Lulus, selain itu Tidak Lulus)
    - perbarui total nilai dan jumlah lulus / tidak lulus
    - tulis satu baris data ke berkas
  |
  v
Tulis ringkasan (rata-rata, jumlah lulus, jumlah tidak lulus)
  |
  v
Tutup writer otomatis (try-with-resources)
  |
  v
Buka BufferedReader ke laporan\_nilai.txt
  |
  v
Baca baris demi baris sampai null, tampilkan di konsol
  |
  v
Tutup reader otomatis
  |
  v
Selesai
```

## 8\. Penjelasan Kode per Bagian

Seluruh kode berada dalam satu kelas `LaporanNilai` dengan beberapa metode `static`.

|Metode|Tugas|
|-|-|
|`main`|Membuka `Scanner`, lalu memanggil tiga langkah secara berurutan|
|`bacaJumlahSiswa`|Meminta jumlah siswa sampai berupa bilangan bulat lebih dari 0|
|`bacaNama`|Meminta nama sampai tidak kosong|
|`bacaNilai`|Meminta nilai sampai berupa angka pada rentang 0 sampai 100|
|`tentukanStatus`|Mengembalikan `"Lulus"` atau `"Tidak Lulus"`|
|`tulisLaporan`|Membaca data siswa dan menulis laporan ke berkas|
|`bacaLaporan`|Membaca berkas dan menampilkannya di konsol|

### a. Penentuan status kelulusan

```java
private static String tentukanStatus(double nilai) {
    return (nilai >= BATAS\_LULUS) ? "Lulus" : "Tidak Lulus";
}
```

`BATAS\_LULUS` adalah konstanta bernilai `70.0`. Batas kelulusan cukup diubah di satu tempat.

### b. Menulis berkas dengan *try-with-resources*

```java
try (BufferedWriter penulis = new BufferedWriter(new FileWriter(NAMA\_FILE))) {
    penulis.write("=== LAPORAN NILAI SISWA ===");
    penulis.newLine();
    // ... tulis data setiap siswa dan ringkasan
} catch (IOException e) {
    System.out.println("Terjadi kesalahan saat menulis file: " + e.getMessage());
}
```

* `FileWriter` menghubungkan program dengan berkas.
* `BufferedWriter` membungkus `FileWriter` agar penulisan lebih efisien lewat *buffer*.
* `newLine()` menambahkan pemisah baris sesuai sistem operasi.
* Penulis ditutup otomatis saat blok `try` selesai, baik berhasil maupun terjadi galat.

### c. Membaca berkas baris demi baris

```java
try (BufferedReader pembaca = new BufferedReader(new FileReader(NAMA\_FILE))) {
    String baris;
    while ((baris = pembaca.readLine()) != null) {
        System.out.println(baris);
    }
} catch (IOException e) {
    System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
}
```

`readLine()` mengembalikan `null` setelah melewati baris terakhir. Itulah tanda untuk menghentikan *loop*.

### d. Menghitung statistik di dalam *loop*

```java
totalNilai += nilai;
if (nilai >= BATAS\_LULUS) {
    jumlahLulus++;
} else {
    jumlahTidakLulus++;
}
```

Setelah *loop* selesai, rata-rata dihitung dengan:

```java
double rataRata = totalNilai / jumlahSiswa;
```

### e. Format angka

```java
String.format(Locale.US, "%-4d %-20s %-8.2f %s", i, nama, nilai, status)
```

* `%-4d`: bilangan bulat, rata kiri, lebar 4.
* `%-20s`: teks, rata kiri, lebar 20.
* `%-8.2f`: bilangan desimal dengan dua angka di belakang koma, rata kiri, lebar 8.
* `Locale.US` memastikan pemisah desimal selalu titik, apa pun pengaturan bahasa komputer.

## 9\. Penanganan Masukan Tidak Valid

Bagian ini menjelaskan apa yang terjadi **jika pengguna salah mengetik**. Program tidak berhenti dan tidak menampilkan galat panjang. Program menampilkan satu pesan singkat, lalu menanyakan isian yang sama sekali lagi sampai benar.

### 9.1 Jika Masukan Salah, Apa yang Terjadi

**Pada isian jumlah siswa**

* Jika diketik `abc` atau `2.5`, keluarnya:
`Masukan tidak valid. Isi dengan bilangan bulat.`
Penyebabnya: teks tersebut tidak dapat diubah menjadi bilangan bulat. Setelah pesan, `Masukkan jumlah siswa:` ditanyakan lagi.
* Jika diketik `0` atau `-1`, keluarnya:
`Jumlah siswa harus lebih dari 0.`
Penyebabnya: angkanya valid, tetapi jumlah siswa tidak mungkin nol atau negatif. Pertanyaan yang sama ditanyakan lagi.

**Pada isian nama**

* Jika langsung menekan *Enter* (atau hanya mengetik spasi), keluarnya:
`Nama tidak boleh kosong.`
Penyebabnya: nama tidak berisi huruf apa pun. Setelah pesan, `Nama  :` ditanyakan lagi. Judul `Data siswa ke-N` tidak tercetak ulang.

**Pada isian nilai**

* Jika diketik `abc`, keluarnya:
`Masukan tidak valid. Isi dengan angka.`
Penyebabnya: teks tersebut tidak dapat diubah menjadi angka. Setelah pesan, `Nilai :` ditanyakan lagi.
* Jika diketik `150` atau `-5`, keluarnya:
`Nilai harus berada pada rentang 0 - 100.`
Penyebabnya: angkanya valid, tetapi di luar rentang nilai. Pertanyaan yang sama ditanyakan lagi.
* Jika diketik `85,5` (memakai koma), **bukan kesalahan**. Program mengganti koma dengan titik, sehingga nilai terbaca `85.5`.

**Ringkasan**

1. Masukan salah menampilkan satu pesan sesuai jenis kesalahannya.
2. Isian yang sama ditanyakan lagi, berulang sampai masukannya benar.
3. Masukan yang salah tidak pernah ditulis ke berkas. Hanya data yang valid yang masuk ke laporan.

### 9.2 Cara Kerja Penanganan Kesalahan

Setiap isian dibaca di dalam *loop* `while (true)` yang baru berhenti saat masukan valid (dengan `return`). Berikut metode `bacaNilai`:

```java
private static double bacaNilai(Scanner input) {
    while (true) {
        System.out.print("Nilai : ");
        String teks = input.nextLine().trim().replace(',', '.');
        try {
            double nilai = Double.parseDouble(teks);
            if (nilai >= 0 \&\& nilai <= 100) {
                return nilai;                       // valid: keluar dari loop
            }
            System.out.println("Nilai harus berada pada rentang 0 - 100.");
        } catch (NumberFormatException e) {
            System.out.println("Masukan tidak valid. Isi dengan angka.");
        }
    }
}
```

|Baris|Fungsi|
|-|-|
|`input.nextLine().trim()`|Membaca satu baris dan membuang spasi di awal dan akhir|
|`.replace(',', '.')`|Menerima koma desimal|
|`Double.parseDouble(teks)`|Mengubah teks menjadi angka. Jika teks bukan angka, muncul `NumberFormatException`|
|`catch (NumberFormatException e)`|Menangkap galat tersebut dan menampilkan pesan, tanpa menghentikan program|
|`if (nilai >= 0 \&\& nilai <= 100)`|Memeriksa rentang. Hanya nilai yang lolos yang dikembalikan|

Metode `bacaJumlahSiswa` dan `bacaNama` bekerja dengan pola yang sama.

Kesalahan yang berbeda jenis ditangani terpisah. `NumberFormatException` adalah galat masukan dari pengguna, sedangkan `IOException` adalah galat berkas (misalnya folder tidak dapat ditulisi). Keduanya ditangani pada tempat yang berbeda di dalam program.



