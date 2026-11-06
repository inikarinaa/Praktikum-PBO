/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LabSession2;

/**
 *
 * @author HP
 */
// SUBCLASS 2 (Ketentuan 2): extends ObjekWisata, implements Tiket.
public class WisataBudaya extends ObjekWisata implements Tiket {

    private boolean adaPemandu;

    public WisataBudaya(String nama, String lokasi, double hargaDasar,
                        int kuotaHarian, boolean adaPemandu) {
        super(nama, lokasi, hargaDasar, kuotaHarian);
        this.adaPemandu = adaPemandu;
    }

    public boolean isAdaPemandu() { return adaPemandu; }

    @Override
    public double hitungBiayaMasuk(int jumlahOrang) {
        double total = getHargaDasar() * jumlahOrang;
        if (adaPemandu) {
            total += 25000; // biaya pemandu tetap per rombongan
        }
        return total;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("[WISATA BUDAYA]");
        super.tampilkanInfo();
        System.out.println("Pemandu     : " + (adaPemandu ? "Tersedia" : "Tidak ada"));
    }

    @Override
    public String jenisTiket() {
        return "Tiket Wisata Budaya";
    }

    @Override
    public void cetakTiket(String pengunjung, int jumlah) {
        System.out.println("[" + jenisTiket() + "] " + getNama()
                + " | a.n. " + pengunjung + " | " + jumlah + " orang");
    }
}
