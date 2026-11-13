/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum6;

/**
 *
 * @author HP
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

// Program pengelola nilai siswa (gaya prosedural/fungsional).
// Alur:
//   1. Baca data siswa dari konsol (Scanner)   -> materi Console I/O
//   2. Tulis data + ringkasan ke file teks     -> materi File I/O (FileWriter, BufferedWriter)
//   3. Baca kembali file dan tampilkan         -> materi File I/O (FileReader, BufferedReader)
//   4. Semua stream ditutup otomatis           -> materi Try with Resources
public class LaporanNilai {

    // Konstanta agar mudah diubah dan tidak ada "angka ajaib" (magic number)
    private static final String NAMA_FILE = "laporan_nilai.txt";
    private static final double BATAS_LULUS = 70.0;

    public static void main(String[] args) {
        // Scanner(System.in) = membaca dari keyboard (Console I/O)
        try (Scanner input = new Scanner(System.in)) {

            int jumlahSiswa = bacaJumlahSiswa(input);

            // Langkah 1: input konsol lalu tulis ke file
            tulisLaporan(input, jumlahSiswa);

            // Langkah 2: baca kembali file lalu tampilkan di konsol
            bacaLaporan();
        }
    }

    // Fungsi bantu untuk input yang aman (validasi)
    // Meminta jumlah siswa sampai pengguna memasukkan bilangan bulat > 0.
    private static int bacaJumlahSiswa(Scanner input) {
        while (true) {
            System.out.print("Masukkan jumlah siswa: ");
            String teks = input.nextLine().trim();
            try {
                int jumlah = Integer.parseInt(teks);
                if (jumlah > 0) {
                    return jumlah;
                }
                System.out.println("Jumlah siswa harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("Masukan tidak valid. Isi dengan bilangan bulat.");
            }
        }
    }

    // Meminta nama siswa sampai tidak kosong.
    private static String bacaNama(Scanner input) {
        while (true) {
            System.out.print("Nama  : ");
            String nama = input.nextLine().trim();
            if (!nama.isEmpty()) {
                return nama;
            }
            System.out.println("Nama tidak boleh kosong.");
        }
    }

    // Meminta nilai (0 - 100) sampai valid. Koma desimal diterima (mis. 85,5).
    private static double bacaNilai(Scanner input) {
        while (true) {
            System.out.print("Nilai : ");
            String teks = input.nextLine().trim().replace(',', '.');
            try {
                double nilai = Double.parseDouble(teks);
                if (nilai >= 0 && nilai <= 100) {
                    return nilai;
                }
                System.out.println("Nilai harus berada pada rentang 0 - 100.");
            } catch (NumberFormatException e) {
                System.out.println("Masukan tidak valid. Isi dengan angka.");
            }
        }
    }

    // Menentukan status kelulusan: nilai >= 70 -> Lulus, selain itu Tidak Lulus.
    private static String tentukanStatus(double nilai) {
        return (nilai >= BATAS_LULUS) ? "Lulus" : "Tidak Lulus";
    }

    // Langkah 1: baca dari konsol dan tulis ke file
    private static void tulisLaporan(Scanner input, int jumlahSiswa) {
        double totalNilai = 0;
        int jumlahLulus = 0;
        int jumlahTidakLulus = 0;

        // Try with resources: BufferedWriter (dan FileWriter di dalamnya)
        // otomatis ditutup ketika blok try selesai, baik sukses maupun error.
        try (BufferedWriter penulis = new BufferedWriter(new FileWriter(NAMA_FILE))) {

            penulis.write("=== LAPORAN NILAI SISWA ===");
            penulis.newLine();
            penulis.write(String.format("%-4s %-20s %-8s %s", "No", "Nama", "Nilai", "Status"));
            penulis.newLine();
            penulis.write("----------------------------------------------");
            penulis.newLine();

            System.out.println();
            for (int i = 1; i <= jumlahSiswa; i++) {
                System.out.println("Data siswa ke-" + i);
                String nama = bacaNama(input);
                double nilai = bacaNilai(input);
                String status = tentukanStatus(nilai);

                // Perbarui statistik di dalam loop
                totalNilai += nilai;
                if (nilai >= BATAS_LULUS) {
                    jumlahLulus++;
                } else {
                    jumlahTidakLulus++;
                }

                // Locale.US menjamin pemisah desimal berupa titik di semua perangkat
                penulis.write(String.format(Locale.US, "%-4d %-20s %-8.2f %s",
                        i, nama, nilai, status));
                penulis.newLine();
                System.out.println();
            }

            // Ringkasan di akhir file
            double rataRata = totalNilai / jumlahSiswa;
            penulis.write("----------------------------------------------");
            penulis.newLine();
            penulis.write("RINGKASAN");
            penulis.newLine();
            penulis.write(String.format(Locale.US, "Rata-rata nilai kelas : %.2f", rataRata));
            penulis.newLine();
            penulis.write("Jumlah siswa lulus    : " + jumlahLulus);
            penulis.newLine();
            penulis.write("Jumlah tidak lulus    : " + jumlahTidakLulus);
            penulis.newLine();

            System.out.println("Penulisan file selesai: " + NAMA_FILE);

        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menulis file: " + e.getMessage());
        }
    }

    // Langkah 2: baca kembali isi file
    private static void bacaLaporan() {
        System.out.println();
        System.out.println(">>> Isi file " + NAMA_FILE + " <<<");

        try (BufferedReader pembaca = new BufferedReader(new FileReader(NAMA_FILE))) {
            String baris;
            // readLine() mengembalikan null jika sudah melewati baris terakhir
            while ((baris = pembaca.readLine()) != null) {
                System.out.println(baris);
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }
}
