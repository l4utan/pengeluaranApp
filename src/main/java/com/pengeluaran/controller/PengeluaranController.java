package com.pengeluaran.controller;

import com.pengeluaran.exception.DataAccessException;
import com.pengeluaran.exception.ValidasiException;
import com.pengeluaran.model.Pengeluaran;
import com.pengeluaran.service.PengeluaranService;
import com.pengeluaran.view.PengeluaranView;
import com.pengeluaran.view.PengeluaranViewListener;

/** Penghubung view dan service. Bergantung pada interface, jadi mudah diuji dan diganti. */
public class PengeluaranController implements PengeluaranViewListener {

    private final PengeluaranService service;
    private final PengeluaranView view;

    public PengeluaranController(PengeluaranService service, PengeluaranView view) {
        this.service = service;
        this.view = view;
    }

    public void muatData() {
        jalankan(this::segarkanTampilan);
    }

    @Override
    public void onTambah() {
        jalankan(() -> {
            service.tambah(view.bacaForm());
            view.kosongkanForm();
            segarkanTampilan();
            view.tampilkanPesan("Data berhasil ditambahkan.");
        });
    }

    @Override
    public void onUbah() {
        jalankan(() -> {
            Pengeluaran pengeluaran = view.bacaForm();
            service.ubah(pengeluaran);
            view.kosongkanForm();
            segarkanTampilan();
            view.tampilkanPesan("Data berhasil diubah.");
        });
    }

    @Override
    public void onHapus() {
        jalankan(() -> {
            Integer id = view.getIdTerpilih();
            if (id == null) {
                throw new ValidasiException("Pilih baris yang mau dihapus.");
            }
            if (!view.konfirmasi("Hapus data terpilih?")) {
                return;
            }
            service.hapus(id);
            view.kosongkanForm();
            segarkanTampilan();
            view.tampilkanPesan("Data berhasil dihapus.");
        });
    }

    private void segarkanTampilan() {
        view.tampilkanData(service.ambilSemua());
        view.tampilkanTotal(service.hitungTotal());
    }

    /** Satu tempat untuk menangani error, supaya tiap aksi tidak mengulang try-catch. */
    private void jalankan(Runnable aksi) {
        try {
            aksi.run();
        } catch (ValidasiException e) {
            view.tampilkanPeringatan(e.getMessage());
        } catch (DataAccessException e) {
            view.tampilkanError(e.getMessage());
        }
    }
}
