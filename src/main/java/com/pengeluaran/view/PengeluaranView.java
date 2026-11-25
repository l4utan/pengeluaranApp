package com.pengeluaran.view;

import com.pengeluaran.model.Pengeluaran;
import java.math.BigDecimal;
import java.util.List;

public interface PengeluaranView {

    void tampilkanData(List<Pengeluaran> daftar);

    void tampilkanTotal(BigDecimal total);

    void tampilkanPesan(String pesan);

    void tampilkanPeringatan(String pesan);

    void tampilkanError(String pesan);

    boolean konfirmasi(String pesan);

    void kosongkanForm();

    /** @throws com.pengeluaran.exception.ValidasiException jika isian tidak bisa dibaca (misal jumlah bukan angka) */
    Pengeluaran bacaForm();

    Integer getIdTerpilih();
}
