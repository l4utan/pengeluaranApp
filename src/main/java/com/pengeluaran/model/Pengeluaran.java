package com.pengeluaran.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Entitas pengeluaran. Immutable: nilai tidak bisa berubah setelah dibuat. */
public class Pengeluaran {

    private final Integer id;
    private final LocalDate tanggal;
    private final String kategori;
    private final String keterangan;
    private final BigDecimal jumlah;

    public Pengeluaran(Integer id, LocalDate tanggal, String kategori, String keterangan, BigDecimal jumlah) {
        this.id = id;
        this.tanggal = tanggal;
        this.kategori = kategori;
        this.keterangan = keterangan;
        this.jumlah = jumlah;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public String getKategori() {
        return kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public BigDecimal getJumlah() {
        return jumlah;
    }
}
