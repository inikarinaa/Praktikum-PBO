/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LabSession2;

/**
 *
 * @author HP
 */
 // ABSTRACT CLASS (Ketentuan 4) sekaligus SUPERCLASS (Ketentuan 2).
 // Cetakan umum untuk semua objek wisata di Geopark Kebumen.
public abstract class ObjekWisata {

    // ENCAPSULATION (Ketentuan 1): atribut dibuat private
    private String nama;
    private String lokasi;
    private double hargaDasar;
    private int kuotaHarian;

    // Constructor superclass
    public ObjekWisata(String nama, String lokasi, double hargaDasar, int kuotaHarian) {
        this.nama = nama;
        this.lokasi = lokasi;
        setHargaDasar(hargaDasar); // lewat setter agar divalidasi
        setKuotaHarian(kuotaHarian);
    }

    // Getter
    public String getNama()        { return nama; }
    public String getLokasi()      { return lokasi; }
    public double getHargaDasar()  { return hargaDasar; }
    public int getKuotaHarian()    { return kuotaHarian; }

    // Setter (dengan validasi) 
    public void setHargaDasar(double hargaDasar) {
        if (hargaDasar >= 0) {
            this.hargaDasar = hargaDasar;
        } else {
            System.out.println("Harga tidak boleh negatif!");
        }
    }

    public void setKuotaHarian(int kuotaHarian) {
        if (kuotaHarian > 0) {
            this.kuotaHarian = kuotaHarian;
        } else {
            System.out.println("Kuota harus lebih dari 0!");
        }
    }

    // Mengurangi kuota hanya lewat method ini (perubahan data terkontrol)
    public boolean pesan(int jumlah) {
        if (jumlah > 0 && jumlah <= kuotaHarian) {
            kuotaHarian -= jumlah;
            return true;
        }
        return false;
    }

    // ABSTRACT METHOD (Ketentuan 4): wajib diisi oleh subclass
    public abstract double hitungBiayaMasuk(int jumlahOrang);

    // POLYMORPHISM - OVERLOADING (Ketentuan 3): nama sama, parameter beda
    public double hitungBiayaMasuk(int jumlahOrang, boolean pelajar) {
        double total = hitungBiayaMasuk(jumlahOrang);
        return pelajar ? total * 0.8 : total; // pelajar diskon 20%
    }

    // Method biasa yang diwariskan ke subclass
    public void tampilkanInfo() {
        System.out.println("Nama        : " + nama);
        System.out.println("Lokasi      : " + lokasi);
        System.out.println("Harga dasar : Rp" + String.format("%,.0f", hargaDasar));
        System.out.println("Sisa kuota  : " + kuotaHarian + " orang");
    }
}
