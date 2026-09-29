/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum4.library.exception;

/**
 *
 * @author HP
 */
// Dilempar ketika anggota mengembalikan buku yang tidak ia pinjam. 
public class BookNotBorrowedException extends Exception {
    private static final long serialVersionUID = 1L;

    public BookNotBorrowedException(String namaAnggota, String judul) {
        super("Anggota " + namaAnggota + " tidak sedang meminjam buku \"" + judul + "\".");
    }
}
