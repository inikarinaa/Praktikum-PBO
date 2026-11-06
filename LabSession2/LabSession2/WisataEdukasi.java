/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LabSession2;

/**
 *
 * @author HP
 */
// SUBCLASS 3: wisata edukasi geologi. Rombongan >= 10 orang mendapat potongan 15%.
public class WisataEdukasi extends ObjekWisata implements Tiket {

    private String topikGeologi;

    public WisataEdukasi(String nama, String lokasi, double hargaDasar,
                         int kuotaHarian, String topikGeologi) {
        super(nama, lokasi, hargaDasar, kuotaHarian);
        this.topikGeologi = topikGeologi;
    }

    public String getTopikGeologi() { return topikGeologi; }

    @Override
    public double hitungBiayaMasuk(int jumlahOrang) {
        double total = getHargaDasar() * jumlahOrang;
        if (jumlahOrang >= 10) {
            total = total * 0.85; // diskon rombongan 15%
        }
        return total;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("[WISATA EDUKASI]");
        super.tampilkanInfo();
        System.out.println("Topik       : " + topikGeologi);
    }

    @Override
    public String jenisTiket() {
        return "Tiket Wisata Edukasi";
    }

    @Override
    public void cetakTiket(String pengunjung, int jumlah) {
        System.out.println("[" + jenisTiket() + "] " + getNama()
                + " | a.n. " + pengunjung + " | " + jumlah + " orang");
    }
}
