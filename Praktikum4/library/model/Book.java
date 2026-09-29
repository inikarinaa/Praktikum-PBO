/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum4.library.model;

/**
 *
 * @author HP
 */
// Model data buku.
public class Book {
    private final String judul;
    private final String penulis;
    private final int tahunTerbit;
    private final String kategori;
    private boolean tersedia;      // statusKetersediaan
    private int jumlahDipinjam;    // statistik: berapa kali buku ini dipinjam

    public Book(String judul, String penulis, int tahunTerbit, String kategori) {
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.kategori = kategori;
        this.tersedia = true;
        this.jumlahDipinjam = 0;
    }

    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public int getTahunTerbit() { return tahunTerbit; }
    public String getKategori() { return kategori; }
    public boolean isTersedia() { return tersedia; }
    public int getJumlahDipinjam() { return jumlahDipinjam; }

    public String getStatus() {
        return tersedia ? "Tersedia" : "Dipinjam";
    }

    // Menandai buku dipinjam dan menambah hitungan statistik.
    public void pinjam() {
        tersedia = false;
        jumlahDipinjam++;
    }

    // Menandai buku sudah dikembalikan.
    public void kembalikan() {
        tersedia = true;
    }

    @Override
    public String toString() {
        return judul + " (" + penulis + ", " + tahunTerbit + ") [" + kategori + "] - " + getStatus();
    }
}
