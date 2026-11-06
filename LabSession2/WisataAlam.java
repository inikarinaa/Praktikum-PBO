/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LabSession2;

/**
 *
 * @author HP
 */
// SUBCLASS 1 (Ketentuan 2): extends ObjekWisata, implements Tiket.
public class WisataAlam extends ObjekWisata implements Tiket {

    private double biayaParkir;

    public WisataAlam(String nama, String lokasi, double hargaDasar,
                      int kuotaHarian, double biayaParkir) {
        super(nama, lokasi, hargaDasar, kuotaHarian); // constructor superclass
        this.biayaParkir = biayaParkir;
    }

    public double getBiayaParkir() { return biayaParkir; }

    // Isi abstract method + OVERRIDING
    @Override
    public double hitungBiayaMasuk(int jumlahOrang) {
        return (getHargaDasar() * jumlahOrang) + biayaParkir; // tiket + parkir
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("[WISATA ALAM]");
        super.tampilkanInfo();
        System.out.println("Parkir      : Rp" + String.format("%,.0f", biayaParkir));
    }

    // Kontrak interface Tiket
    @Override
    public String jenisTiket() {
        return "Tiket Wisata Alam";
    }

    @Override
    public void cetakTiket(String pengunjung, int jumlah) {
        System.out.println("[" + jenisTiket() + "] " + getNama()
                + " | a.n. " + pengunjung + " | " + jumlah + " orang");
    }
}
