/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LabSession1;

/**
 *
 * @author HP
 */
// POIN 1: CLASS & OBJECT (atribut)
public class Produk {

    // POIN 3: KONSTANTA 
    static final double PPN = 0.11; // Pajak Pertambahan Nilai sebesar 11%

    String namaProduk;
    double harga;
    int stok;

    // POIN 2: CONSTRUCTOR
    public Produk(String namaProduk, double harga, int stok) {
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
    }

    // POIN 1: METHOD 1 
    // Menghitung harga jual setelah ditambah pajak (memakai konstanta PPN)
    double hitungHargaSetelahPajak() {
        return harga + (harga * PPN);
    }

    // POIN 1: METHOD 2 & POIN 4: KONDISIONAL 
    // Menentukan status stok berdasarkan jumlah stok yang tersedia
    String cekStatusStok() {
        if (stok == 0) {
            return "Habis";
        } else if (stok < 5) {
            return "Menipis";
        } else {
            return "Tersedia";
        }
    }

    // POIN 7: CHARACTER & STRING
    // Menampilkan informasi produk memakai tipe char dan method-method String
    void tampilkanInfo() {
        char inisial = namaProduk.charAt(0); // tipe data char
        String namaUpper = namaProduk.toUpperCase(); // String method ke-1
        String kodeProduk = namaProduk
                .substring(0, Math.min(3, namaProduk.length()))
                .toUpperCase(); // String method ke-2

        System.out.println("Nama Produk : " + namaUpper);
        System.out.println("Inisial     : " + inisial);
        System.out.println("Kode Produk : " + kodeProduk);
        System.out.printf("Harga+PPN   : Rp%,.2f%n", hitungHargaSetelahPajak());
        System.out.println("Status Stok : " + cekStatusStok());
    }

    // PENDUKUNG POIN 4 & 6: validasi saat pembelian 
    // Mengurangi stok ketika ada pembelian; melempar exception jika tidak valid
    void kurangiStok(int jumlahBeli) {
        if (jumlahBeli <= 0) {
            throw new IllegalArgumentException("Jumlah pembelian harus lebih dari 0!");
        }
        if (jumlahBeli > stok) {
            throw new IllegalArgumentException("Stok tidak mencukupi! Stok tersedia: " + stok);
        }
        stok -= jumlahBeli;
    }
}