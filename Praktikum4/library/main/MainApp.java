/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum4.library.main;

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
import Praktikum4.library.service.LibraryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainApp {
    private static final Scanner input = new Scanner(System.in);
    private static final LibraryService service = new LibraryService();

    public static void main(String[] args) {
        isiDataAwal();
        boolean berjalan = true;

        while (berjalan) {
            tampilkanMenu();
            int pilihan = bacaAngka("Pilih menu: ");

            switch (pilihan) {
                case 1: menuTambahBuku(); break;
                case 2: menuDaftarBuku(); break;
                case 3: menuCariBuku(); break;
                case 4: menuPinjamBuku(); break;
                case 5: menuKembalikanBuku(); break;
                case 6: System.out.println(service.buatLaporan()); break;
                case 7: menuDaftarkanAnggota(); break;
                case 8:
                    berjalan = false;
                    System.out.println("Terima kasih. Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia. Pilih angka 1 sampai 8.");
            }
        }
        input.close();
    }

    // Tampilan
    private static void tampilkanMenu() {
        System.out.println();
        System.out.println("===== PERPUSTAKAAN MINI =====");
        System.out.println("1. Tambah Buku");
        System.out.println("2. Daftar Buku");
        System.out.println("3. Cari Buku");
        System.out.println("4. Pinjam Buku");
        System.out.println("5. Kembalikan Buku");
        System.out.println("6. Laporan Perpustakaan");
        System.out.println("7. Daftarkan Anggota");
        System.out.println("8. Keluar");
    }

    private static void tampilkanTabelBuku(ArrayList<Book> daftar) {
        if (daftar.isEmpty()) {
            System.out.println("(tidak ada data)");
            return;
        }
        System.out.printf("%-4s %-32s %-22s %-7s %-26s %-10s%n",
                "No", "Judul", "Penulis", "Tahun", "Kategori", "Status");
        for (int i = 0; i < daftar.size(); i++) {
            Book b = daftar.get(i);
            System.out.printf("%-4d %-32s %-22s %-7d %-26s %-10s%n",
                    i + 1, b.getJudul(), b.getPenulis(), b.getTahunTerbit(), b.getKategori(), b.getStatus());
        }
    }

    // Menu
    private static void menuTambahBuku() {
        System.out.println("--- Tambah Buku ---");
        String judul = bacaTeks("Judul        : ");
        String penulis = bacaTeks("Penulis      : ");
        int tahun = bacaAngka("Tahun terbit : ");
        String kategori = bacaTeks("Kategori     : ");
        try {
            Book b = service.tambahBuku(judul, penulis, tahun, kategori);
            System.out.println("Berhasil menambah buku: " + b.getJudul());
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal menambah buku: " + e.getMessage());
        }
    }

    private static void menuDaftarBuku() {
        System.out.println("--- Daftar Buku ---");
        tampilkanTabelBuku(service.getDaftarBuku());

        System.out.println("\nJumlah buku per kategori:");
        for (Map.Entry<String, Integer> e : service.hitungBukuPerKategori().entrySet()) {
            System.out.printf("  - %-26s: %d buku%n", e.getKey(), e.getValue());
        }
    }

    private static void menuCariBuku() {
        System.out.println("--- Cari Buku ---");
        System.out.println("1. Berdasarkan judul");
        System.out.println("2. Berdasarkan kategori");
        int mode = bacaAngka("Pilihan: ");
        if (mode != 1 && mode != 2) {
            System.out.println("Pilihan tidak valid.");
            return;
        }
        String kata = bacaTeks("Kata kunci: ");
        try {
            ArrayList<Book> hasil = service.cariBuku(kata, mode == 1);
            System.out.println("Ditemukan " + hasil.size() + " buku.");
            tampilkanTabelBuku(hasil);
        } catch (IllegalArgumentException e) {
            System.out.println("Pencarian gagal: " + e.getMessage());
        }
    }

    private static void menuPinjamBuku() {
        System.out.println("--- Pinjam Buku ---");
        tampilkanAnggota();
        String id = bacaTeks("ID anggota    : ");
        String judul = bacaTeks("Judul buku    : ");
        try {
            Book b = service.pinjamBuku(id, judul);
            System.out.println("Berhasil meminjam: " + b.getJudul());
        } catch (MemberNotFoundException | BookNotFoundException
                 | BookAlreadyBorrowedException | BorrowLimitExceededException e) {
            System.out.println("Peminjaman gagal: " + e.getMessage());
        }
    }

    private static void menuKembalikanBuku() {
        System.out.println("--- Kembalikan Buku ---");
        tampilkanAnggota();
        String id = bacaTeks("ID anggota    : ");
        try {
            Member m = service.cariAnggota(id);
            System.out.println("Pinjaman aktif " + m.getNama() + ":");
            tampilkanTabelBuku(m.getDaftarPinjaman());
            String judul = bacaTeks("Judul buku    : ");
            Book b = service.kembalikanBuku(id, judul);
            System.out.println("Berhasil mengembalikan: " + b.getJudul());
        } catch (MemberNotFoundException | BookNotFoundException | BookNotBorrowedException e) {
            System.out.println("Pengembalian gagal: " + e.getMessage());
        }
    }

    private static void menuDaftarkanAnggota() {
        System.out.println("--- Daftarkan Anggota ---");
        String id = bacaTeks("ID (huruf/angka, min. 3 karakter): ");
        String nama = bacaTeks("Nama: ");
        try {
            Member m = service.daftarkanAnggota(id, nama);
            System.out.println("Anggota terdaftar: " + m);
        } catch (IllegalArgumentException e) {
            System.out.println("Pendaftaran gagal: " + e.getMessage());
        }
    }

    private static void tampilkanAnggota() {
        System.out.println("Anggota terdaftar:");
        for (Member m : service.getDaftarAnggota()) {
            System.out.println("  " + m);
        }
    }

    // Input 
    private static String bacaTeks(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

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

    // Data awal untuk demonstrasi
    private static void isiDataAwal() {
        service.tambahBuku("Seporsi Mie Ayam Sebelum Mati", "Brian Khrisna", 2025, "Pengembangan Diri");
        service.tambahBuku("3726 MDPL", "Nurwina Sari", 2024, "Fiksi Populer");
        service.tambahBuku("Jika Kita Tak Pernah Jadi Apa-Apa", "Alvi Syahrin", 2019, "Pengembangan Diri");
        service.tambahBuku("Kamu Tidak Butuh Motivasi", "Astrid Savitri", 2025, "Pengembangan Diri");
        service.tambahBuku("Buku Minta Disayang", "Rintik Sedu", 2026, "Kumpulan Puisi");
        service.tambahBuku("Maaf Tuhan, Aku Hampir Menyerah", "Alfialghazi", 2020, "Pengembangan Diri");

        service.daftarkanAnggota("L001", "Lionel Messi");
        service.daftarkanAnggota("L002", "Neymar");
        service.daftarkanAnggota("L003", "Thom Haye");
        service.daftarkanAnggota("L004", "Marc Marquez");
    }
}
