/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LabSession1;

/**
 *
 * @author HP
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // POIN 8: COLLECTION (ArrayList)
        ArrayList<Produk> daftarProduk = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // POIN 9: INPUT DATA & PEMBUATAN OBJECT DARI PENGGUNA
        System.out.println("=========== INPUT DATA PRODUK TOKO ONLINE ===========");

        int jumlahProduk = 0;
        try {
            System.out.print("Masukkan jumlah produk yang ingin ditambahkan: ");
            jumlahProduk = scanner.nextInt();
            scanner.nextLine(); // membuang sisa newline setelah nextInt()
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid (bukan angka), jumlah produk diset 0.");
            scanner.nextLine();
        }

        // POIN 5: LOOPING (input berulang)
        for (int i = 1; i <= jumlahProduk; i++) {
            System.out.println("\n-- Produk ke-" + i + " --");
            System.out.print("Nama produk : ");
            String nama = scanner.nextLine();

            double harga = 0;
            int stok = 0;
            try {
                System.out.print("Harga produk: ");
                harga = Double.parseDouble(scanner.nextLine());
                System.out.print("Stok produk : ");
                stok = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Harga/stok harus berupa angka. Produk ini diberi nilai 0.");
            }

            daftarProduk.add(new Produk(nama, harga, stok)); // Poin 9: pembuatan object
        }

        System.out.println("\n=========== DAFTAR PRODUK TOKO ONLINE ===========\n");

        // POIN 5: LOOPING 
        for (Produk produk : daftarProduk) {
            produk.tampilkanInfo();
            System.out.println("--------------------------------------------------");
        }

        int totalStok = 0;
        for (Produk p : daftarProduk) {
            totalStok += p.stok;
        }
        System.out.println("Total stok seluruh produk saat ini: " + totalStok);

        // POIN 6: EXCEPTION HANDLING (proses pembelian) 
        try {
            if (daftarProduk.isEmpty()) {
                throw new IllegalStateException("Tidak ada produk untuk dibeli!");
            }

            System.out.println("\n--- PROSES PEMBELIAN ---");
            for (int i = 0; i < daftarProduk.size(); i++) {
                Produk p = daftarProduk.get(i);
                System.out.println((i + 1) + ". " + p.namaProduk + " (stok: " + p.stok + ")");
            }

            System.out.print("Pilih nomor produk yang ingin dibeli: ");
            int pilihan = scanner.nextInt() - 1;

            System.out.print("Jumlah yang ingin dibeli: ");
            int jumlahBeli = scanner.nextInt();

            Produk produkDipilih = daftarProduk.get(pilihan); // berpotensi IndexOutOfBoundsException
            produkDipilih.kurangiStok(jumlahBeli); // berpotensi IllegalArgumentException

            System.out.println("Pembelian berhasil! Sisa stok " + produkDipilih.namaProduk
                    + ": " + produkDipilih.stok);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: nomor produk yang dipilih tidak tersedia!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Error: input harus berupa angka!");
        } finally {
            scanner.close();
        }
    }
}