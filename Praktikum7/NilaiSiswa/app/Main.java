/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum7.NilaiSiswa.app;

/**
 *
 * @author HP
 */
import Praktikum7.NilaiSiswa.model.Mahasiswa;
import Praktikum7.NilaiSiswa.model.NilaiInvalidException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Kelas utama: membaca input, menyimpan data ke ArrayList,
// menghitung statistik, dan menulis laporan ke laporan_nilai.txt.
public class Main {

    private static final String NAMA_FILE = "laporan_nilai.txt";

    public static void main(String[] args) {
        // Collection Framework: ArrayList dinamis (bukan array statis)
        ArrayList<Mahasiswa> daftar = new ArrayList<>();

        // Console I/O: Scanner membaca input dari pengguna
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== PENGELOLAAN DATA NILAI MAHASISWA ===");
        int jumlah = bacaJumlah(scanner);

        for (int i = 1; i <= jumlah; i++) {
            System.out.println("\nData mahasiswa ke-" + i);
            Mahasiswa m = bacaMahasiswa(scanner);
            daftar.add(m);
        }

        tampilkanRekap(daftar);
        simpanKeFile(daftar);

        scanner.close();
    }

    // Membaca jumlah mahasiswa; diulang sampai input berupa bilangan bulat positif.
    private static int bacaJumlah(Scanner scanner) {
        while (true) {
            System.out.print("Masukkan jumlah mahasiswa: ");
            try {
                int n = Integer.parseInt(scanner.nextLine().trim());
                if (n > 0) {
                    return n;
                }
                System.out.println("Jumlah harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa bilangan bulat.");
            }
        }
    }

    // Membaca satu mahasiswa; diulang sampai seluruh data valid.
    private static Mahasiswa bacaMahasiswa(Scanner scanner) {
        while (true) {
            System.out.print("  Nama  : ");
            String nama = scanner.nextLine();
            System.out.print("  NIM   : ");
            String nim = scanner.nextLine();
            System.out.print("  Nilai : ");
            String teksNilai = scanner.nextLine().trim().replace(',', '.');

            try {
                // Validasi awal agar assertion tidak menjadi satu-satunya pengaman
                if (nim.trim().isEmpty()) {
                    throw new IllegalArgumentException("NIM tidak boleh kosong!");
                }
                if (nama.trim().isEmpty()) {
                    throw new IllegalArgumentException("Nama tidak boleh kosong!");
                }
                double nilai = Double.parseDouble(teksNilai);
                return new Mahasiswa(nama, nim, nilai);
            } catch (NumberFormatException e) {
                System.out.println("  [Galat] Nilai harus berupa angka. Ulangi input.\n");
            } catch (NilaiInvalidException e) {
                System.out.println("  [Galat] " + e.getMessage() + " Ulangi input.\n");
            } catch (IllegalArgumentException e) {
                System.out.println("  [Galat] " + e.getMessage() + " Ulangi input.\n");
            }
        }
    }

    // Menampilkan rekapitulasi ke layar.
    private static void tampilkanRekap(ArrayList<Mahasiswa> daftar) {
        System.out.println("\n" + buatLaporan(daftar));
    }

    // Menyusun isi laporan sebagai satu String (dipakai untuk layar dan file).
    private static String buatLaporan(ArrayList<Mahasiswa> daftar) {
        StringBuilder sb = new StringBuilder();
        String garis = "=".repeat(68);

        sb.append(garis).append("\n");
        sb.append("                 LAPORAN NILAI MAHASISWA\n");
        sb.append(garis).append("\n");
        sb.append(String.format("%-4s %-25s %-14s %7s %-6s %-12s%n",
                "No", "Nama", "NIM", "Nilai", "Grade", "Status"));
        sb.append("-".repeat(68)).append("\n");

        double total = 0;
        int lulus = 0;
        int tidakLulus = 0;
        int no = 1;

        // Penelusuran elemen ArrayList dengan for-each
        for (Mahasiswa m : daftar) {
            sb.append(String.format("%-4d %-25s %-14s %7.2f %-6s %-12s%n",
                    no++, m.getNama(), m.getNim(), m.getNilai(), m.getGrade(), m.getStatus()));
            total += m.getNilai();
            if (m.isLulus()) {
                lulus++;
            } else {
                tidakLulus++;
            }
        }

        double rataRata = total / daftar.size();

        sb.append(garis).append("\n");
        sb.append("STATISTIK KELAS\n");
        sb.append(String.format("Jumlah mahasiswa : %d%n", daftar.size()));
        sb.append(String.format("Rata-rata nilai  : %.2f%n", rataRata));
        sb.append(String.format("Jumlah lulus     : %d%n", lulus));
        sb.append(String.format("Jumlah tdk lulus : %d%n", tidakLulus));
        sb.append(String.format("Batas kelulusan  : %.1f%n", Mahasiswa.FINAL_PASSING_GRADE));
        sb.append(garis).append("\n");

        return sb.toString();
    }

    // File I/O: menulis laporan menggunakan BufferedWriter dan FileWriter.
    private static void simpanKeFile(ArrayList<Mahasiswa> daftar) {
        // Try-with-resources: writer ditutup otomatis walau terjadi galat
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            writer.write(buatLaporan(daftar));
            System.out.println("Laporan berhasil disimpan ke file " + NAMA_FILE);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan file: " + e.getMessage());
        }
    }
}
