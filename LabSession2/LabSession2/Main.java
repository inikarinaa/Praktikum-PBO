/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LabSession2;

/**
 *
 * @author HP
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("===== SISTEM TIKET WISATA GEOPARK KEBUMEN =====");
        System.out.println("(Harga dalam program ini hanya contoh simulasi)\n");

        // RUNTIME POLYMORPHISM: referensi superclass, objek subclass
        ObjekWisata w1 = new WisataAlam("Pantai Menganti", "Ayah", 10000, 500, 5000);
        ObjekWisata w2 = new WisataAlam("Goa Jatijajar", "Ayah", 15000, 300, 5000);
        ObjekWisata w3 = new WisataBudaya("Benteng Van der Wijck", "Gombong", 8000, 200, true);
        ObjekWisata w4 = new WisataEdukasi("Karangsambung", "Karangsambung", 20000, 100,
                "Batuan purba dan tektonik lempeng");

        ObjekWisata[] daftar = { w1, w2, w3, w4 };

        // 1. Tampilkan info (method override dipanggil sesuai objek aslinya)
        System.out.println("--- 1. Daftar Objek Wisata ---");
        for (ObjekWisata w : daftar) {
            w.tampilkanInfo();
            System.out.println();
        }

        // 2. Biaya masuk untuk 12 orang (hitungBiayaMasuk versi 1 parameter)
        System.out.println("--- 2. Biaya Masuk Rombongan 12 Orang ---");
        for (ObjekWisata w : daftar) {
            System.out.println(w.getNama() + " = Rp"
                    + String.format("%,.0f", w.hitungBiayaMasuk(12)));
        }

        // 3. OVERLOADING: versi dengan parameter pelajar
        System.out.println("\n--- 3. Biaya Masuk 12 Orang (Pelajar, diskon 20%) ---");
        System.out.println(w4.getNama() + " = Rp"
                + String.format("%,.0f", w4.hitungBiayaMasuk(12, true)));

        // 4. Interface Tiket: cetak tiket
        System.out.println("\n--- 4. Cetak Tiket (Interface) ---");
        for (ObjekWisata w : daftar) {
            if (w instanceof Tiket) {
                Tiket t = (Tiket) w;
                t.cetakTiket("Nana", 12);
            }
        }

        // 5. Encapsulation: ubah data lewat setter/method
        System.out.println("\n--- 5. Encapsulation ---");
        w1.setHargaDasar(-1000);          // ditolak
        w1.setHargaDasar(12000);          // diterima
        boolean berhasil = w1.pesan(12);  // kuota berkurang 12
        System.out.println("Pemesanan berhasil? " + berhasil);
        w1.tampilkanInfo();
    }
}
