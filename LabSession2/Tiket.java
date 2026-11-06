/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LabSession2;

/**
 *
 * @author HP
 */
// INTERFACE (Ketentuan 5): kontrak layanan tiket.
// Class yang meng-implements wajib membuat semua method ini.
public interface Tiket {
    String jenisTiket();                          // nama jenis tiket
    void cetakTiket(String pengunjung, int jumlah); // mencetak tiket
}
