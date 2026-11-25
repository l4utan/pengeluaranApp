package com.pengeluaran.validation;

import com.pengeluaran.exception.ValidasiException;
import com.pengeluaran.model.Pengeluaran;
import java.math.BigDecimal;

public class PengeluaranValidator implements Validator<Pengeluaran> {

    private static final int MAKS_KATEGORI = 50;
    private static final int MAKS_KETERANGAN = 100;
    private static final int MAKS_DIGIT_BULAT = 10;
    private static final int MAKS_DESIMAL = 2;

    @Override
    public void validasi(Pengeluaran pengeluaran) {
        if (pengeluaran == null) {
            throw new ValidasiException("Data tidak boleh kosong.");
        }
        if (pengeluaran.getTanggal() == null) {
            throw new ValidasiException("Tanggal wajib diisi.");
        }
        validasiKategori(pengeluaran.getKategori());
        validasiKeterangan(pengeluaran.getKeterangan());
        validasiJumlah(pengeluaran.getJumlah());
    }

    private void validasiKategori(String kategori) {
        if (kategori == null || kategori.isBlank()) {
            throw new ValidasiException("Kategori wajib dipilih.");
        }
        if (kategori.length() > MAKS_KATEGORI) {
            throw new ValidasiException("Kategori maksimal " + MAKS_KATEGORI + " karakter.");
        }
    }

    private void validasiKeterangan(String keterangan) {
        if (keterangan != null && keterangan.length() > MAKS_KETERANGAN) {
            throw new ValidasiException("Keterangan maksimal " + MAKS_KETERANGAN + " karakter.");
        }
    }

    private void validasiJumlah(BigDecimal jumlah) {
        if (jumlah == null || jumlah.signum() <= 0) {
            throw new ValidasiException("Jumlah harus lebih dari 0.");
        }
        if (jumlah.scale() > MAKS_DESIMAL) {
            throw new ValidasiException("Jumlah maksimal " + MAKS_DESIMAL + " angka desimal.");
        }
        if (jumlah.precision() - jumlah.scale() > MAKS_DIGIT_BULAT) {
            throw new ValidasiException("Jumlah terlalu besar.");
        }
    }
}
