/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum4.library.service;

/**
 *
 * @author HP
 */
import Praktikum4.library.exception.BookAlreadyBorrowedException;
import Praktikum4.library.exception.BookNotBorrowedException;
import Praktikum4.library.exception.BookNotFoundException;
import Praktikum4.library.exception.BorrowLimitExceededException;
import Praktikum4.library.exception.MemberNotFoundException;
import Praktikum4.library.model.Book;
import Praktikum4.library.model.Member;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Logika bisnis perpustakaan: buku, anggota, transaksi, dan analisis. */
public class LibraryService {
    private static final String KATA_SAMBUNG = " dan yang di ke dari untuk dengan pada atau ";

    private final ArrayList<Book> daftarBuku = new ArrayList<>();
    private final HashMap<String, Member> daftarAnggota = new HashMap<>();

    // Manajemen Buku
    public Book tambahBuku(String judul, String penulis, int tahunTerbit, String kategori) {
        if (kosong(judul)) throw new IllegalArgumentException("Judul tidak boleh kosong.");
        if (kosong(penulis)) throw new IllegalArgumentException("Penulis tidak boleh kosong.");
        if (kosong(kategori)) throw new IllegalArgumentException("Kategori tidak boleh kosong.");

        int tahunSekarang = Year.now().getValue();
        if (tahunTerbit < 1000 || tahunTerbit > tahunSekarang) {
            throw new IllegalArgumentException(
                    "Tahun terbit harus berada pada rentang 1000 sampai " + tahunSekarang + ".");
        }

        String judulRapi = kapitalisasiJudul(judul);
        if (cariPersis(judulRapi) != null) {
            throw new IllegalArgumentException("Buku berjudul \"" + judulRapi + "\" sudah ada.");
        }

        Book buku = new Book(judulRapi, kapitalisasiKata(penulis), tahunTerbit, normalisasiKategori(kategori));
        daftarBuku.add(buku);
        return buku;
    }

    public ArrayList<Book> getDaftarBuku() {
        return new ArrayList<>(daftarBuku);
    }

    // Pencarian & Analisis
    // Mencari buku berdasarkan judul (true) atau kategori (false), tanpa peka huruf besar/kecil.
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

    // Menghitung jumlah buku pada tiap kategori dengan looping.
    public HashMap<String, Integer> hitungBukuPerKategori() {
        HashMap<String, Integer> jumlah = new HashMap<>();
        for (Book b : daftarBuku) {
            String k = b.getKategori();
            jumlah.put(k, jumlah.getOrDefault(k, 0) + 1);
        }
        return jumlah;
    }

    // Sistem Anggota 
    public Member daftarkanAnggota(String id, String nama) {
        if (kosong(id) || !idValid(id.trim())) {
            throw new IllegalArgumentException("ID anggota minimal 3 karakter dan hanya berisi huruf atau angka.");
        }
        if (kosong(nama)) throw new IllegalArgumentException("Nama anggota tidak boleh kosong.");

        String idBaku = id.trim().toUpperCase();
        if (daftarAnggota.containsKey(idBaku)) {
            throw new IllegalArgumentException("ID \"" + idBaku + "\" sudah digunakan.");
        }

        Member anggota = new Member(idBaku, kapitalisasiKata(nama));
        daftarAnggota.put(idBaku, anggota);
        return anggota;
    }

    public Member cariAnggota(String id) throws MemberNotFoundException {
        Member anggota = (id == null) ? null : daftarAnggota.get(id.trim().toUpperCase());
        if (anggota == null) throw new MemberNotFoundException(id);
        return anggota;
    }

    public ArrayList<Member> getDaftarAnggota() {
        return new ArrayList<>(daftarAnggota.values());
    }

    // Peminjaman & Pengembalian 
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

    public Book kembalikanBuku(String idAnggota, String judul)
            throws MemberNotFoundException, BookNotFoundException, BookNotBorrowedException {

        Member anggota = cariAnggota(idAnggota);
        assert validasiAnggota(anggota) : "Data anggota tidak valid sebelum transaksi pengembalian";

        Book buku = cariPersis(judul);
        if (buku == null) throw new BookNotFoundException(judul);
        if (!anggota.hapusPinjaman(buku)) throw new BookNotBorrowedException(anggota.getNama(), buku.getJudul());

        buku.kembalikan();
        return buku;
    }

    // Analisis Aktivitas
    public int getTotalPinjaman() {
        int total = 0;
        for (Book b : daftarBuku) {
            total += b.getJumlahDipinjam();
        }
        return total;
    }

    public String buatLaporan() {
        int tersedia = 0;
        HashMap<String, Integer> pinjamPerBuku = new HashMap<>();
        HashMap<String, Integer> pinjamPerKategori = new HashMap<>();
        for (Book b : daftarBuku) {
            if (b.isTersedia()) tersedia++;
            pinjamPerBuku.put(b.getJudul(), b.getJumlahDipinjam());
            String k = b.getKategori();
            pinjamPerKategori.put(k, pinjamPerKategori.getOrDefault(k, 0) + b.getJumlahDipinjam());
        }

        HashMap<String, Integer> pinjamPerAnggota = new HashMap<>();
        for (Member m : daftarAnggota.values()) {
            pinjamPerAnggota.put(m.getNama() + " (" + m.getId() + ")", m.getTotalPeminjaman());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("========== LAPORAN PERPUSTAKAAN ==========\n");
        sb.append(String.format("Total buku                  : %d%n", daftarBuku.size()));
        sb.append(String.format("Buku tersedia               : %d%n", tersedia));
        sb.append(String.format("Buku sedang dipinjam        : %d%n", daftarBuku.size() - tersedia));
        sb.append(String.format("Total anggota               : %d%n", daftarAnggota.size()));
        sb.append(String.format("Jumlah total pinjaman       : %d%n", getTotalPinjaman()));
        sb.append("Buku paling sering dipinjam : ").append(ringkasTeratas(pinjamPerBuku)).append("\n");
        sb.append("Anggota paling aktif        : ").append(ringkasTeratas(pinjamPerAnggota)).append("\n");
        sb.append("Kategori paling populer     : ").append(ringkasTeratas(pinjamPerKategori)).append("\n");
        sb.append("Jumlah buku per kategori    :\n");
        for (Map.Entry<String, Integer> e : hitungBukuPerKategori().entrySet()) {
            sb.append(String.format("  - %-26s: %d buku%n", e.getKey(), e.getValue()));
        }
        sb.append("==========================================");
        return sb.toString();
    }

    // Utilitas privat 
    private boolean kosong(String teks) {
        return teks == null || teks.trim().isEmpty();
    }

    private Book cariPersis(String judul) {
        if (judul == null) return null;
        String kunci = judul.trim();
        for (Book b : daftarBuku) {
            if (b.getJudul().equalsIgnoreCase(kunci)) return b;
        }
        return null;
    }

    private boolean validasiAnggota(Member m) {
        return m != null
                && m.getId() != null && !m.getId().isEmpty()
                && m.getNama() != null && !m.getNama().trim().isEmpty()
                && m.getDaftarPinjaman().size() <= Member.MAKS_PINJAM;
    }

    // Judul: tiap kata berawalan kapital, kecuali kata sambung (bukan kata pertama), sesuai EYD.
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

    // Manipulasi Character #1: huruf pertama tiap kata (nama orang) dijadikan kapital, sisanya dipertahankan.
    private String kapitalisasiKata(String teks) {
        StringBuilder hasil = new StringBuilder();
        boolean awalKata = true;
        for (char c : teks.trim().replaceAll("\\s+", " ").toCharArray()) {
            if (Character.isWhitespace(c)) {
                awalKata = true;
                hasil.append(c);
            } else if (awalKata) {
                hasil.append(Character.toUpperCase(c));
                awalKata = false;
            } else {
                hasil.append(c);
            }
        }
        return hasil.toString();
    }

    // Manipulasi Character #2: kategori dibakukan (huruf awal kapital, sisanya kecil) agar hitungan konsisten.
    private String normalisasiKategori(String teks) {
        StringBuilder hasil = new StringBuilder();
        boolean awalKata = true;
        for (char c : teks.trim().replaceAll("\\s+", " ").toCharArray()) {
            if (Character.isWhitespace(c)) {
                awalKata = true;
                hasil.append(c);
            } else {
                hasil.append(awalKata ? Character.toUpperCase(c) : Character.toLowerCase(c));
                awalKata = false;
            }
        }
        return hasil.toString();
    }

    // Manipulasi Character #3: ID hanya boleh huruf atau angka, minimal 3 karakter.
    private boolean idValid(String id) {
        if (id.length() < 3) return false;
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isLetterOrDigit(id.charAt(i))) return false;
        }
        return true;
    }

    // Mencari kunci dengan nilai terbesar (> 0); bila seri, semua kunci seri ditampilkan.
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
}
