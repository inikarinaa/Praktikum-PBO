/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum2;

/**
 *
 * @author HP
 */
// Kelas Student merepresentasikan satu objek siswa.
// Menyimpan data nama, nilai, dan status kelulusan siswa.
public class Student {
    // Atribut (variabel instance)
    private String name;    // nama siswa
    private double score;   // nilai siswa
    private boolean passed; // status kelulusan (true/false)

    // Constructor
    // Dipanggil saat objek Student dibuat, untuk mengisi name dan score.
    public Student(String name, double score) {
        this.name = name;
        this.score = score;
        this.passed = false; // nilai awal, akan diperbarui oleh checkPassed()
    }

    // Method checkPassed()
    // Mengecek kelulusan: jika nilai >= 70, maka passed diubah menjadi true.
    public void checkPassed() {
        if (this.score >= 70) {
            this.passed = true;
        } else {
            this.passed = false;
        }
    }

    // Getter
    // Diperlukan agar kelas lain (mis. Main) bisa membaca atribut private di atas.
    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public boolean isPassed() {
        return passed;
    }
}

