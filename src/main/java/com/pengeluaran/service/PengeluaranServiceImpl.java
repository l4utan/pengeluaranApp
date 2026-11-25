package com.pengeluaran.service;

import com.pengeluaran.exception.ValidasiException;
import com.pengeluaran.model.Pengeluaran;
import com.pengeluaran.repository.PengeluaranRepository;
import com.pengeluaran.validation.Validator;
import java.math.BigDecimal;
import java.util.List;

/** Aturan bisnis: validasi dulu, baru simpan. Bergantung pada abstraksi saja. */
public class PengeluaranServiceImpl implements PengeluaranService {

    private final PengeluaranRepository repository;
    private final Validator<Pengeluaran> validator;

    public PengeluaranServiceImpl(PengeluaranRepository repository, Validator<Pengeluaran> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public List<Pengeluaran> ambilSemua() {
        return repository.cariSemua();
    }

    @Override
    public void tambah(Pengeluaran pengeluaran) {
        validator.validasi(pengeluaran);
        repository.simpan(pengeluaran);
    }

    @Override
    public void ubah(Pengeluaran pengeluaran) {
        validator.validasi(pengeluaran);
        if (pengeluaran.getId() == null) {
            throw new ValidasiException("Pilih baris yang mau diubah.");
        }
        repository.ubah(pengeluaran);
    }

    @Override
    public void hapus(int id) {
        repository.hapus(id);
    }

    @Override
    public BigDecimal hitungTotal() {
        return repository.hitungTotal();
    }
}
