# 💸 Aplikasi Pencatat Pengeluaran

> Aplikasi desktop berbasis **Java 17 & Swing** untuk mencatat dan memantau pengeluaran harian dengan penyimpanan **PostgreSQL** serta arsitektur berlapis yang menerapkan prinsip **SOLID**.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-JDBC-316192?logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)

---

## 🚀 Cara Menjalankan (Setup)

Pastikan **JDK 17**, **Maven**, dan **PostgreSQL** sudah terpasang di perangkatmu sebelum memulai.

1. **Buat Database**
   Buat database baru di PostgreSQL dengan nama `db_pengeluaran`.
2. **Inisialisasi Tabel**
   Jalankan skrip `sql/schema.sql` pada database `db_pengeluaran`.
3. **Konfigurasi Koneksi**
   Salin file `src/main/resources/db.properties.example` menjadi `src/main/resources/db.properties`, lalu sesuaikan `user` dan `password` PostgreSQL milikmu.
   > ⚠️ **Catatan Keamanan:** File `db.properties` sudah masuk ke dalam `.gitignore`. Jangan pernah *commit* kredensial database asli ke repository!
4. **Jalankan Aplikasi**
   Eksekusi perintah berikut melalui terminal di direktori utama proyek:
   ```bash
   mvn compile exec:java
   ```

---

## 🏗️ Struktur Proyek & Alur Data

Aplikasi ini memisahkan tanggung jawab tiap lapisan (*layered architecture*) agar kode tetap bersih, mudah diuji, dan tidak saling tumpang tindih:

```text
src/main/java/com/pengeluaran/
├── Main.java          # Composition root (merakit semua dependensi objek)
├── config/            # Konfigurasi & penyedia koneksi database
├── model/             # Representasi entitas data Pengeluaran
├── validation/        # Aturan validasi input pengguna
├── repository/        # Akses database (query SQL hanya tinggal di sini)
├── service/           # Logika bisnis utama aplikasi
├── controller/        # Jembatan komunikasi antara View dan Service
├── view/              # Antarmuka grafis Swing (JFrame, TableModel)
└── exception/         # Penanganan error khusus aplikasi
```

### 🔄 Alur Eksekusi
```text
[ View ] ──▶ [ Controller ] ──▶ [ Service ] ──▶ [ Repository ] ──▶ [ PostgreSQL ]
```

---

## 🧱 Penerapan Prinsip SOLID

| Prinsip | Nama | Implementasi di Proyek Ini |
| :---: | :--- | :--- |
| **S** | *Single Responsibility* | Setiap kelas punya satu fokus utama. `Validator` tidak mengurus SQL, dan `View` tidak ikut campur urusan logika bisnis. |
| **O** | *Open/Closed* | Ingin menambah aturan validasi baru? Cukup buat kelas `Validator` baru tanpa perlu membongkar kode di `Service`. |
| **L** | *Liskov Substitution* | `PengeluaranRepositoryJdbc` dapat ditukar dengan implementasi penyimpanan lain tanpa merusak cara kerja `Service`. |
| **I** | *Interface Segregation* | Interface dipecah kecil dan spesifik (`ConnectionProvider`, `Validator`, `PengeluaranViewListener`) sehingga kelas tidak dipaksa mengimplementasikan method yang tidak dibutuhkan. |
| **D** | *Dependency Inversion* | `Controller`, `Service`, dan `Repository` bergantung pada abstraksi (*interface*), bukan kelas konkret. Semua dependensi disambungkan terpusat di `Main`. |
