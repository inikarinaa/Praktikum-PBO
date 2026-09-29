/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum4.library.exception;

/**
 *
 * @author HP
 */
// Dilempar ketika anggota melebihi batas maksimum jumlah pinjaman.
public class BorrowLimitExceededException extends Exception {
    private static final long serialVersionUID = 1L;

    public BorrowLimitExceededException(String namaAnggota, int batas) {
        super("Anggota " + namaAnggota + " sudah meminjam " + batas
                + " buku (batas maksimum). Kembalikan buku terlebih dahulu.");
    }
}
