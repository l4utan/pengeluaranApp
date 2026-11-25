package com.pengeluaran.exception;

/** Dilempar saat input pengguna tidak valid. Pesannya aman ditampilkan ke pengguna. */
public class ValidasiException extends RuntimeException {

    public ValidasiException(String pesan) {
        super(pesan);
    }
}
