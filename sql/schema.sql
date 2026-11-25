-- Jalankan dulu (di database postgres):
-- CREATE DATABASE db_pengeluaran;
-- Lalu connect ke db_pengeluaran dan jalankan file ini.

CREATE TABLE pengeluaran (
    id SERIAL PRIMARY KEY,
    tanggal DATE NOT NULL,
    kategori VARCHAR(50) NOT NULL,
    keterangan VARCHAR(100),
    jumlah NUMERIC(12, 2) NOT NULL CHECK (jumlah > 0)
);

INSERT INTO pengeluaran (
    tanggal,
    kategori,
    keterangan,
    jumlah
)
VALUES
    ('2026-11-20', 'Makan', 'Nasi goreng', 18000),
    ('2026-11-21', 'Transport', 'Bensin', 35000);
