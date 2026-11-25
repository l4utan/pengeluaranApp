package com.pengeluaran.view;

/** Event yang dikirim view ke controller. View tidak tahu siapa yang mendengarkan. */
public interface PengeluaranViewListener {

    void onTambah();

    void onUbah();

    void onHapus();
}
