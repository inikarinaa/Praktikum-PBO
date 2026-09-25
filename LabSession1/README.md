run:

=========== INPUT DATA PRODUK TOKO ONLINE ===========

Masukkan jumlah produk yang ingin ditambahkan: 2



\-- Produk ke-1 --

Nama produk : Kaos

Harga produk: 75000

Stok produk : 10



\-- Produk ke-2 --

Nama produk : Sepatu

Harga produk: 330000

Stok produk : 3



=========== DAFTAR PRODUK TOKO ONLINE ===========



Nama Produk : KAOS

Inisial     : K

Kode Produk : KAO

Harga+PPN   : Rp83.250,00

Status Stok : Tersedia

\--------------------------------------------------

Nama Produk : SEPATU

Inisial     : S

Kode Produk : SEP

Harga+PPN   : Rp366.300,00

Status Stok : Menipis

\--------------------------------------------------

Total stok seluruh produk saat ini: 13



\--- PROSES PEMBELIAN ---

1\. Kaos (stok: 10)

2\. Sepatu (stok: 3)

Pilih nomor produk yang ingin dibeli: 1

Jumlah yang ingin dibeli: 3

Pembelian berhasil! Sisa stok Kaos: 7





**Ringkasan Program "Toko Online"**



Inti Program

Program menyimpan data produk (nama, harga, stok) dalam sebuah class, membiarkan pengguna menambah produk lewat input, menampilkan katalog, lalu memproses satu transaksi pembelian dengan validasi stok.



Pemetaan ke Poin Soal

|No.|Poin|Implementasi|Letak|
|-|-|-|-|
|1.|Class \& Object|Class Produk (atribut: nama, harga, stok) + method hitungHargaSetelahPajak(), cekStatusStok(), kurangiStok()|Produk.java|
|2.|Constructor|Produk(String nama, double harga, int stok)|Produk.java|
|3.|Konstanta|static final double PPN = 0.11|Produk.java|
|4.|Kondisional|if–else if–else menentukan status stok: Habis / Menipis / Tersedia|Produk.java|
|5.|Looping|for mengulang input produk \& menampilkan katalog|Main.java|
|6.|Exception Handling|try-catch di 3 titik: input angka salah, stok tidak cukup, nomor produk tidak ada|Main.java|
|7.|Character \& String|charAt() (char), toUpperCase(), substring()|Produk.java|
|8.|Collection|ArrayList<Produk> menyimpan semua produk|Main.java|
|9.|Object + println|Object Produk dibuat dari input pengguna, ditampilkan via tampilkanInfo()|Main.java|



Alur Program (3 Langkah)

1\. INPUT   → pengguna masukkan data produk satu per satu → simpan ke ArrayList

2\. TAMPIL  → semua produk ditampilkan (nama, harga+pajak, status stok)

3\. BELI    → pengguna pilih 1 produk + jumlah beli → stok dikurangi (divalidasi)

