/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum7.NilaiSiswa.model;

/**
 *
 * @author HP
 */
// Kelas data yang merepresentasikan seorang mahasiswa beserta nilainya.
public class Mahasiswa {

    // Konstanta standar kelulusan (final = nilainya tidak dapat diubah)
    public static final double FINAL_PASSING_GRADE = 70.0;

    // Atribut: String adalah reference type, double adalah primitive type
    private String nama;
    private String nim;
    private double nilai;

    // Constructor: merapikan input, memvalidasi, lalu menginisialisasi atribut.
    // @throws NilaiInvalidException    jika nilai < 0 atau nilai > 100
    // @throws IllegalArgumentException jika NIM mengandung karakter bukan angka
    public Mahasiswa(String nama, String nim, double nilai) throws NilaiInvalidException {
        // Manipulasi String: trim() membuang spasi, toUpperCase() menjadikan huruf kapital
        String namaRapi = nama.trim().toUpperCase();
        String nimRapi = nim.trim();

        // Assertion: NIM tidak boleh kosong (aktif jika dijalankan dengan opsi -ea)
        assert !nimRapi.isEmpty() : "NIM tidak boleh kosong!";

        // Manipulasi Character: NIM hanya boleh berisi angka
        for (char c : nimRapi.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException(
                        "NIM hanya boleh berisi angka, ditemukan karakter: '" + c + "'");
            }
        }

        // Custom Exception: nilai harus berada pada rentang 0 - 100
        if (nilai < 0 || nilai > 100) {
            throw new NilaiInvalidException(
                    "Nilai " + nilai + " tidak valid. Nilai harus antara 0 dan 100.");
        }

        this.nama = namaRapi;
        this.nim = nimRapi;
        this.nilai = nilai;
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public double getNilai() {
        return nilai;
    }

    // Memetakan nilai angka menjadi Grade huruf dengan If-Else.
    public String getGrade() {
        if (nilai >= 85) {
            return "A";
        } else if (nilai >= FINAL_PASSING_GRADE) {
            return "B";
        } else if (nilai >= 55) {
            return "C";
        } else {
            return "D";
        }
    }

    // Menentukan status kelulusan (boolean = primitive type).
    public boolean isLulus() {
        return nilai >= FINAL_PASSING_GRADE;
    }

    public String getStatus() {
        return isLulus() ? "LULUS" : "TIDAK LULUS";
    }
}
