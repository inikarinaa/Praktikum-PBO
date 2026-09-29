/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum4.library.exception;

/**
 *
 * @author HP
 */
// Dilempar ketika buku yang hendak dipinjam sedang dipinjam. 
public class BookAlreadyBorrowedException extends Exception {
    private static final long serialVersionUID = 1L;

    public BookAlreadyBorrowedException(String judul) {
        super("Buku \"" + judul + "\" sedang dipinjam.");
    }
}
