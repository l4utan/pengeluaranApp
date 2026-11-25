package com.pengeluaran.view;

import com.pengeluaran.model.Pengeluaran;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/** Menjembatani List<Pengeluaran> ke JTable. */
public class PengeluaranTableModel extends AbstractTableModel {

    private static final String[] NAMA_KOLOM = {"Tanggal", "Kategori", "Keterangan", "Jumlah"};

    private List<Pengeluaran> daftar = new ArrayList<>();

    public void setData(List<Pengeluaran> dataBaru) {
        this.daftar = new ArrayList<>(dataBaru);
        fireTableDataChanged();
    }

    public Pengeluaran getPada(int baris) {
        return daftar.get(baris);
    }

    @Override
    public int getRowCount() {
        return daftar.size();
    }

    @Override
    public int getColumnCount() {
        return NAMA_KOLOM.length;
    }

    @Override
    public String getColumnName(int kolom) {
        return NAMA_KOLOM[kolom];
    }

    @Override
    public Class<?> getColumnClass(int kolom) {
        return switch (kolom) {
            case 0 -> LocalDate.class;
            case 3 -> BigDecimal.class;
            default -> String.class;
        };
    }

    @Override
    public Object getValueAt(int baris, int kolom) {
        Pengeluaran pengeluaran = daftar.get(baris);
        return switch (kolom) {
            case 0 -> pengeluaran.getTanggal();
            case 1 -> pengeluaran.getKategori();
            case 2 -> pengeluaran.getKeterangan();
            case 3 -> pengeluaran.getJumlah();
            default -> null;
        };
    }
}