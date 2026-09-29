/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Praktikum4.library.exception;

/**
 *
 * @author HP
 */
// Dilempar ketika ID anggota tidak terdaftar.
public class MemberNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public MemberNotFoundException(String id) {
        super("Anggota dengan ID \"" + id + "\" tidak terdaftar.");
    }
}
