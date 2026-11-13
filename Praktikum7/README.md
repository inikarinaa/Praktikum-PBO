# Program Pengelolaan Data Nilai Mahasiswa



## Daftar Isi

1. [Deskripsi Program](#1-deskripsi-program)
2. [Isi Folder Proyek](#2-isi-folder-proyek)
3. [Penjelasan Setiap Berkas](#3-penjelasan-setiap-berkas)
4. [Aturan Nilai dan Kelulusan](#4-aturan-nilai-dan-kelulusan)
5. [Jika Masukan Salah](#5-jika-masukan-salah)
6. [Kesesuaian dengan Ketentuan Tugas](#6-kesesuaian-dengan-ketentuan-tugas)
7. [Istilah Penting](#7-istilah-penting)
8. [Output](#9-output)
9. [Penjelasan Keluaran di Layar](#10-penjelasan-keluaran-di-layar)
10. [Alur Pembuatan Berkas](#11-alur-pembuatan-berkas)

\---

## 1\. Deskripsi Program

Program ini adalah aplikasi berbasis teks (*console*) yang dibuat dengan bahasa Java menggunakan pendekatan pemrograman berorientasi objek untuk mengelola data nilai mahasiswa. Pengguna diminta memasukkan jumlah mahasiswa, kemudian nama, NIM, dan nilai setiap mahasiswa melalui `Scanner`. Setiap masukan dirapikan dan diperiksa sebelum diterima: nama dan NIM dirapikan dengan `trim()` dan `toUpperCase()`, NIM harus berisi angka saja dan tidak boleh kosong (diperiksa dengan `Character.isDigit()` dan *assertion*), sedangkan nilai harus berada pada rentang 0 sampai 100 (diperiksa dengan *custom exception* `NilaiInvalidException`). Jika ada masukan yang tidak sesuai, program menampilkan pesan galat dan meminta pengguna mengisi ulang, sehingga program tidak berhenti di tengah jalan.

Data yang sudah valid disimpan dalam `ArrayList<Mahasiswa>`, lalu ditelusuri dengan *for-each* untuk menentukan *grade* huruf (A, B, C, atau D), status kelulusan berdasarkan batas 70, rata-rata nilai kelas, serta jumlah mahasiswa yang lulus dan tidak lulus. Hasilnya ditampilkan di layar dalam bentuk laporan bertabel dan ditulis ke berkas `laporan\_nilai.txt` menggunakan `BufferedWriter` dan `FileWriter` dengan *try-with-resources*, sehingga rekapitulasi tersimpan secara permanen. Kode program dibagi menjadi dua *package*, yaitu `model` (kelas data `Mahasiswa` dan `NilaiInvalidException`) dan `app` (kelas utama `Main`).

## 2\. Isi Folder Proyek

```
nilai\_mahasiswa/
├── model/
│   ├── Mahasiswa.java
│   └── NilaiInvalidException.java
├── app/
│   └── Main.java
├── README.md
└── laporan\_nilai.txt      (muncul sendiri setelah program dijalankan)
```

|Folder|Berkas|Tugasnya|
|-|-|-|
|`model`|`Mahasiswa.java`|Menyimpan data satu mahasiswa dan memeriksa kebenarannya|
|`model`|`NilaiInvalidException.java`|Pesan galat khusus untuk nilai di luar 0–100|
|`app`|`Main.java`|Program utama: meminta masukan, menghitung, dan menyimpan laporan|

## 3\. Penjelasan Setiap Berkas

### 3.1 `NilaiInvalidException.java`

Pesan galat buatan sendiri (*custom exception*). Dilempar (*throw*) ketika nilai kurang dari 0 atau lebih dari 100.

### 3.2 `Mahasiswa.java`

Cetakan data satu mahasiswa.

|Bagian|Isi dan fungsi|
|-|-|
|`FINAL\_PASSING\_GRADE`|Konstanta batas lulus, bernilai 70.0|
|`nama`, `nim`, `nilai`|Data mahasiswa|
|Konstruktor|Merapikan nama dan NIM, memeriksa NIM dan nilai, lalu menyimpan data|
|`getGrade()`|Menentukan *grade* A, B, C, atau D|
|`isLulus()`|Bernilai `true` jika nilai 70 ke atas|
|`getStatus()`|Menghasilkan teks `LULUS` atau `TIDAK LULUS`|

Yang dilakukan konstruktor, berurutan:

1. `trim()` membuang spasi di awal dan akhir, `toUpperCase()` mengubah nama menjadi huruf kapital.
2. `assert` memastikan NIM tidak kosong.
3. `Character.isDigit()` memastikan setiap karakter NIM adalah angka.
4. Nilai di luar 0–100 memicu `NilaiInvalidException`.

### 3.3 `Main.java`

|Metode|Fungsi|
|-|-|
|`main`|Menjalankan alur utama|
|`bacaJumlah`|Meminta jumlah mahasiswa sampai masukannya benar|
|`bacaMahasiswa`|Meminta nama, NIM, dan nilai sampai semuanya benar|
|`buatLaporan`|Menyusun teks laporan lengkap dengan statistik|
|`tampilkanRekap`|Menampilkan laporan di layar|
|`simpanKeFile`|Menulis laporan ke `laporan\_nilai.txt`|

## 4\. Aturan Nilai dan Kelulusan

Batas lulus adalah **70**.

|Nilai|*Grade*|Status|
|-|-|-|
|85 – 100|A|Lulus|
|70 – 84,99|B|Lulus|
|55 – 69,99|C|Tidak lulus|
|0 – 54,99|D|Tidak lulus|

Rata-rata dihitung dengan rumus:

```
rata-rata = jumlah seluruh nilai / banyak mahasiswa
```

Rentang *grade* dapat diubah pada metode `getGrade()` di `Mahasiswa.java`.

## 5\. Jika Masukan Salah

Program tidak berhenti saat masukan salah. Program menampilkan pesan `\[Galat]` lalu meminta pengguna mengisi ulang.

|Kesalahan|Akibat|
|-|-|
|Jumlah mahasiswa bukan bilangan bulat, atau 0 atau kurang|Diminta mengisi ulang|
|Nama kosong|Diminta mengisi ulang|
|NIM kosong|Diminta mengisi ulang|
|NIM memuat huruf atau simbol|Diminta mengisi ulang|
|Nilai bukan angka|Diminta mengisi ulang|
|Nilai kurang dari 0 atau lebih dari 100|Diminta mengisi ulang|
|Berkas gagal ditulis|Muncul pesan `Gagal menyimpan file`|

## 6\. Kesesuaian dengan Ketentuan Tugas

|Pertemuan|Ketentuan|Wujud di program|Berkas|
|-|-|-|-|
|2|*Package*, kelas, konstruktor, `final`|*Package* `model` dan `app`; kelas `Mahasiswa`; konstanta `FINAL\_PASSING\_GRADE = 70.0`|`Mahasiswa.java`|
|3|*If-else* atau *switch*, *looping*, tipe primitif dan *reference*|*If-else* pada `getGrade()`; *for-each* untuk rata-rata; `double` dan `boolean` (primitif); `String` dan `Mahasiswa` (*reference*)|`Mahasiswa.java`, `Main.java`|
|4|*Custom exception*, *assertion*, `Character`, `String`|`NilaiInvalidException`; `assert !nimRapi.isEmpty()`; `Character.isDigit()`; `trim()` dan `toUpperCase()`|`Mahasiswa.java`|
|5|`ArrayList`, `add()`, `size()`, *for-each*|`ArrayList<Mahasiswa> daftar`|`Main.java`|
|6|`Scanner`, `BufferedWriter`, `FileWriter`, *try-with-resources*|Masukan lewat `Scanner`; laporan ditulis ke `laporan\_nilai.txt`|`Main.java`|

## 7\. Istilah Penting

|Istilah|Arti singkat|
|-|-|
|*Package*|Folder untuk mengelompokkan kelas|
|*Constructor* (konstruktor)|Bagian kelas yang berjalan saat objek dibuat, dipakai untuk mengisi data awal|
|`final`|Penanda bahwa nilai tidak boleh diubah|
|*Exception*|Kejadian galat saat program berjalan|
|*Custom exception*|*Exception* buatan sendiri untuk kasus tertentu|
|*Assertion* (`assert`)|Pemeriksaan kondisi yang harus benar; hanya aktif jika memakai `-ea`|
|*Collection* (`ArrayList`)|Daftar dinamis yang ukurannya bisa bertambah|
|*File I/O*|Proses membaca dan menulis berkas|
|*Try-with-resources*|Cara menulis `try` yang menutup berkas secara otomatis|
|Primitif|Tipe data dasar, misalnya `double` dan `boolean`|
|*Reference type*|Tipe data berupa objek, misalnya `String` dan `Mahasiswa`|

## 8\. Output

Contoh ini sengaja memuat dua masukan salah agar terlihat cara program menanganinya. Teks setelah titik dua pada `Nama`, `NIM`, dan `Nilai` adalah ketikan pengguna.

```run

=== PENGELOLAAN DATA NILAI MAHASISWA ===

Masukkan jumlah mahasiswa: 3



Data mahasiswa ke-1

&#x20; Nama  : Karina

&#x20; NIM   : 006

&#x20; Nilai : 96



Data mahasiswa ke-2

&#x20; Nama  : Purna

&#x20; NIM   : 007

&#x20; Nilai : 67



Data mahasiswa ke-3

&#x20; Nama  : Ayunida

&#x20; NIM   : 008

&#x20; Nilai : 70



====================================================================

&#x20;                LAPORAN NILAI MAHASISWA

====================================================================

No   Nama                      NIM              Nilai Grade  Status      

\--------------------------------------------------------------------

1    KARINA                    006              96,00 A      LULUS       

2    PURNA                     007              67,00 C      TIDAK LULUS 

3    AYUNIDA                   008              70,00 B      LULUS       

====================================================================

STATISTIK KELAS

Jumlah mahasiswa : 3

Rata-rata nilai  : 77,67

Jumlah lulus     : 2

Jumlah tdk lulus : 1

Batas kelulusan  : 70,0

====================================================================



Laporan berhasil disimpan ke file laporan\_nilai.txt

BUILD SUCCESSFUL (total time: 25 seconds)
```

## 9\. Penjelasan Keluaran di Layar



### 9.1 Bagian tabel laporan

|Kolom|Arti|
|-|-|
|`No`|Nomor urut sesuai urutan masukan|
|`Nama`, `NIM`|Data yang sudah dirapikan|
|`Nilai`|Nilai angka dengan dua angka desimal|
|`Grade`|Huruf hasil pemetaan nilai (88 menjadi A, 72,5 menjadi B)|
|`Status`|`LULUS` jika nilai 70 ke atas, selain itu `TIDAK LULUS`|

### 9.2 Bagian statistik kelas

|Baris|Arti|Hasil pada contoh|
|-|-|-|
|`Jumlah mahasiswa`|Banyak data dalam daftar|2|
|`Rata-rata nilai`|(96 + 67 +70) / 3|77.67|
|`Jumlah lulus`|Banyak mahasiswa dengan nilai 70 ke atas|2|
|`Jumlah tdk lulus`|Banyak mahasiswa dengan nilai di bawah 70|0|
|`Batas kelulusan`|Nilai konstanta `FINAL\_PASSING\_GRADE`|70.0|

Baris terakhir, `Laporan berhasil disimpan ke file laporan\_nilai.txt`, menandakan penulisan berkas berhasil.

## 10\. Alur Pembuatan berkas

Setelah semua data terkumpul, program menjalankan langkah berikut:

1. `buatLaporan()` menyusun seluruh teks laporan (judul, tabel, dan statistik) menjadi **satu `String`** memakai `StringBuilder`.
2. `tampilkanRekap()` mencetak `String` itu ke layar.
3. `simpanKeFile()` membuka berkas `laporan\_nilai.txt` dengan `FileWriter`, dibungkus `BufferedWriter`. Jika berkas belum ada, berkas dibuat. Jika sudah ada, isinya **ditimpa**.
4. `writer.write(...)` menuliskan `String` laporan yang **sama** ke berkas.
5. Karena memakai *try-with-resources*, berkas **ditutup otomatis** setelah selesai atau jika terjadi galat. Data dijamin tuntas tertulis.
6. Jika penulisan berhasil, layar menampilkan `Laporan berhasil disimpan ke file laporan\_nilai.txt`. Jika gagal, `IOException` ditangkap dan layar menampilkan `Gagal menyimpan file` beserta penyebabnya.

Ringkasnya:

```
data mahasiswa (ArrayList)
        |
        v
  buatLaporan()  -->  satu String laporan
        |                   |
        v                   v
 tampilkanRekap()     simpanKeFile()
  (cetak ke layar)    (tulis ke laporan\_nilai.txt)
```

Karena layar dan berkas memakai `String` yang sama, isi keduanya selalu sama. Satu-satunya perbedaan: baris `Laporan berhasil disimpan...` hanya tampil di layar dan tidak ditulis ke berkas.

