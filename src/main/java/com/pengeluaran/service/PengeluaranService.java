package com.pengeluaran.service;

import com.pengeluaran.model.Pengeluaran;
import java.math.BigDecimal;
import java.util.List;

/** Kontrak logika bisnis yang dipakai controller. */
public interface PengeluaranService {

    List<Pengeluaran> ambilSemua();

    void tambah(Pengeluaran pengeluaran);

    void ubah(Pengeluaran pengeluaran);

    void hapus(int id);

    BigDecimal hitungTotal();
}
