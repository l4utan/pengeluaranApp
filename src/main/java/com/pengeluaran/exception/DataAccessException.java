package com.pengeluaran.exception;

/** Dilempar saat akses database gagal. Membungkus SQLException agar layer atas bebas dari java.sql. */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String pesan, Throwable penyebab) {
        super(pesan, penyebab);
    }

    public DataAccessException(String pesan) {
        super(pesan);
    }
}
