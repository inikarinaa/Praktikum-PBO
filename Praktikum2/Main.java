/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum2;

/**
 *
 * @author HP
 */
import java.util.Scanner;
// Kelas Main berisi method utama (main) untuk menjalankan program menerima input, memproses data siswa, dan menampilkan hasil.
// Kelas ini Tidak menyimpan data siswa sendiri, data disimpan dan dikelola oleh objek-objek Student(lihat Student.java).
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Langkah 1: Input jumlah siswa 
        System.out.print("Masukkan jumlah siswa: ");
        int jumlahSiswa = Integer.parseInt(scanner.nextLine().trim());

        // Array of Object untuk menampung semua objek Student
        Student[] students = new Student[jumlahSiswa];

        // Langkah 2: Input data tiap siswa (looping)
        for (int i = 0; i < jumlahSiswa; i++) {
            System.out.println("\nData siswa ke-" + (i + 1) + ":");

            System.out.print("Masukkan nama: ");
            String nama = scanner.nextLine();

            System.out.print("Masukkan nilai: ");
            double nilai = Double.parseDouble(scanner.nextLine().trim());

            // Membuat objek Student baru lewat constructor
            students[i] = new Student(nama, nilai);

            // Memanggil method checkPassed() untuk menentukan status kelulusan
            students[i].checkPassed();

            // Langkah 3: Kondisional untuk menampilkan status 
            String status = students[i].isPassed() ? "Lulus" : "Tidak Lulus";
            System.out.println("Siswa: " + students[i].getName() +
                    " - Nilai: " + students[i].getScore() +
                    " - " + status);
        }

        // Langkah 4: Rekap hasil (rata-rata & jumlah lulus/tidak)
        double totalNilai = 0;
        int jumlahLulus = 0;
        int jumlahTidakLulus = 0;

        for (int i = 0; i < jumlahSiswa; i++) {
            totalNilai += students[i].getScore();

            if (students[i].isPassed()) {
                jumlahLulus++;
            } else {
                jumlahTidakLulus++;
            }
        }

        double rataRata = totalNilai / jumlahSiswa;

        System.out.println("\nRata-rata nilai kelas: " + String.format("%.2f", rataRata));
        System.out.println("Jumlah siswa lulus: " + jumlahLulus);
        System.out.println("Jumlah siswa tidak lulus: " + jumlahTidakLulus);

        scanner.close();
    }
}

