/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum4.library.exception;

/**
 *
 * @author HP
 */
// Dilempar ketika buku dengan judul tertentu tidak ada dalam koleksi.
public class BookNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public BookNotFoundException(String judul) {
        super("Buku berjudul \"" + judul + "\" tidak ditemukan.");
    }
}
