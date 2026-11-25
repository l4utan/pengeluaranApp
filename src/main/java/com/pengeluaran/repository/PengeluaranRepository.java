package com.pengeluaran.repository;

import com.pengeluaran.model.Pengeluaran;
import java.math.BigDecimal;
import java.util.List;

/** Kontrak akses data. Implementasi bisa diganti (JDBC, file, in-memory) tanpa menyentuh service. */
public interface PengeluaranRepository {

    List<Pengeluaran> cariSemua();

    void simpan(Pengeluaran pengeluaran);

    void ubah(Pengeluaran pengeluaran);

    void hapus(int id);

    BigDecimal hitungTotal();
}
