/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum7.NilaiSiswa.model;

/**
 *
 * @author HP
 */
// Custom Exception (checked) yang dilempar ketika nilai berada di luar rentang 0 sampai 100.
public class NilaiInvalidException extends Exception {

    public NilaiInvalidException(String pesan) {
        super(pesan);
    }
}
