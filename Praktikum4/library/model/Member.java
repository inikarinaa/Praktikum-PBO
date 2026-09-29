/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum4.library.model;

/**
 *
 * @author HP
 */
import java.util.ArrayList;

// Model data anggota perpustakaan.
public class Member {
    public static final int MAKS_PINJAM = 3;

    private final String id;
    private final String nama;
    private final ArrayList<Book> daftarPinjaman;  // pinjaman yang sedang aktif
    private int totalPeminjaman;                   // akumulasi seluruh peminjaman

    public Member(String id, String nama) {
        this.id = id;
        this.nama = nama;
        this.daftarPinjaman = new ArrayList<>();
        this.totalPeminjaman = 0;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public int getTotalPeminjaman() { return totalPeminjaman; }

    // Mengembalikan salinan agar daftar asli tidak dapat diubah dari luar.
    public ArrayList<Book> getDaftarPinjaman() {
        return new ArrayList<>(daftarPinjaman);
    }

    public boolean isBatasTercapai() {
        return daftarPinjaman.size() >= MAKS_PINJAM;
    }

    public void tambahPinjaman(Book buku) {
        daftarPinjaman.add(buku);
        totalPeminjaman++;
    }

    public boolean hapusPinjaman(Book buku) {
        return daftarPinjaman.remove(buku);
    }

    @Override
    public String toString() {
        return id + " - " + nama + " (pinjaman aktif: " + daftarPinjaman.size() + ")";
    }
}
