package com.pengeluaran.view;

import com.pengeluaran.exception.ValidasiException;
import com.pengeluaran.model.Pengeluaran;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;

/** Implementasi Swing dari PengeluaranView. Hanya mengurus tampilan, tanpa logika bisnis atau SQL. */
public class FormPengeluaran extends JFrame implements PengeluaranView {

    private static final String[] DAFTAR_KATEGORI = {
        "Makan", "Transport", "Belanja", "Tagihan", "Hiburan", "Lainnya"
    };

    private final PengeluaranTableModel tableModel = new PengeluaranTableModel();
    private final JTable tabel = new JTable(tableModel);
    private final JSpinner spinnerTanggal = new JSpinner(new SpinnerDateModel());
    private final JComboBox<String> comboKategori = new JComboBox<>(DAFTAR_KATEGORI);
    private final JTextField txtKeterangan = new JTextField();
    private final JTextField txtJumlah = new JTextField();
    private final JButton btnCreate = new JButton("Create");
    private final JButton btnUpdate = new JButton("Update");
    private final JButton btnDelete = new JButton("Delete");
    private final JButton btnReset = new JButton("Reset");
    private final JLabel lblTotal = new JLabel("Total pengeluaran: -");
    private final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));

    private PengeluaranViewListener listener;
    private Integer idTerpilih;

    public FormPengeluaran() {
        super("Aplikasi Pencatat Pengeluaran");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(buatPanelForm(), BorderLayout.NORTH);
        add(buatPanelTabel(), BorderLayout.CENTER);
        add(buatPanelTotal(), BorderLayout.SOUTH);
        pasangEvent();
        kosongkanForm();
        pack();
        setLocationRelativeTo(null);
    }

    public void setListener(PengeluaranViewListener listener) {
        this.listener = listener;
    }

    // ---------- PengeluaranView ----------

    @Override
    public void tampilkanData(List<Pengeluaran> daftar) {
        tableModel.setData(daftar);
    }

    @Override
    public void tampilkanTotal(BigDecimal total) {
        lblTotal.setText("Total pengeluaran: " + formatRupiah.format(total));
    }

    @Override
    public void tampilkanPesan(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void tampilkanPeringatan(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Periksa Input", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void tampilkanError(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public boolean konfirmasi(String pesan) {
        int pilihan = JOptionPane.showConfirmDialog(this, pesan, "Konfirmasi", JOptionPane.YES_NO_OPTION);
        return pilihan == JOptionPane.YES_OPTION;
    }

    @Override
    public void kosongkanForm() {
        idTerpilih = null;
        tabel.clearSelection();
        spinnerTanggal.setValue(new Date());
        comboKategori.setSelectedIndex(0);
        txtKeterangan.setText("");
        txtJumlah.setText("");
        perbaruiStatusTombol();
    }

    @Override
    public Pengeluaran bacaForm() {
        return new Pengeluaran(
                idTerpilih,
                bacaTanggal(),
                (String) comboKategori.getSelectedItem(),
                txtKeterangan.getText().trim(),
                bacaJumlah());
    }

    @Override
    public Integer getIdTerpilih() {
        return idTerpilih;
    }

    // ---------- pembangun tampilan ----------

    private JPanel buatPanelForm() {
        spinnerTanggal.setEditor(new JSpinner.DateEditor(spinnerTanggal, "dd-MM-yyyy"));

        JPanel panelField = new JPanel(new GridLayout(0, 2, 8, 8));
        panelField.add(new JLabel("Tanggal"));
        panelField.add(spinnerTanggal);
        panelField.add(new JLabel("Kategori"));
        panelField.add(comboKategori);
        panelField.add(new JLabel("Keterangan"));
        panelField.add(txtKeterangan);
        panelField.add(new JLabel("Jumlah (Rp)"));
        panelField.add(txtJumlah);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelTombol.add(btnCreate);
        panelTombol.add(btnUpdate);
        panelTombol.add(btnDelete);
        panelTombol.add(btnReset);

        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panel.add(panelField, BorderLayout.CENTER);
        panel.add(panelTombol, BorderLayout.SOUTH);
        return panel;
    }

    private JScrollPane buatPanelTabel() {
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabel.setAutoCreateRowSorter(true);
        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setPreferredSize(new Dimension(640, 260));
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        return scroll;
    }

    private JPanel buatPanelTotal() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        panel.add(lblTotal);
        return panel;
    }

    // ---------- event ----------

    private void pasangEvent() {
        btnCreate.addActionListener(e -> {
            if (listener != null) {
                listener.onTambah();
            }
        });
        btnUpdate.addActionListener(e -> {
            if (listener != null) {
                listener.onUbah();
            }
        });
        btnDelete.addActionListener(e -> {
            if (listener != null) {
                listener.onHapus();
            }
        });
        btnReset.addActionListener(e -> kosongkanForm());
        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiFormDariBaris(tabel.getSelectedRow());
            }
        });
    }

    private void isiFormDariBaris(int baris) {
        if (baris < 0) {
            idTerpilih = null;
            perbaruiStatusTombol();
            return;
        }
        // Baris di layar belum tentu sama dengan baris di data, karena tabel bisa diurutkan.
        int barisModel = tabel.convertRowIndexToModel(baris);
        Pengeluaran pengeluaran = tableModel.getPada(barisModel);
        idTerpilih = pengeluaran.getId();
        spinnerTanggal.setValue(ubahKeDate(pengeluaran.getTanggal()));
        comboKategori.setSelectedItem(pengeluaran.getKategori());
        txtKeterangan.setText(pengeluaran.getKeterangan() == null ? "" : pengeluaran.getKeterangan());
        txtJumlah.setText(pengeluaran.getJumlah().toPlainString());
        perbaruiStatusTombol();
    }

    private void perbaruiStatusTombol() {
        boolean adaBarisTerpilih = idTerpilih != null;
        btnCreate.setEnabled(!adaBarisTerpilih);
        btnUpdate.setEnabled(adaBarisTerpilih);
        btnDelete.setEnabled(adaBarisTerpilih);
        btnReset.setEnabled(adaBarisTerpilih);
    }

    // ---------- konversi ----------

    private LocalDate bacaTanggal() {
        Date tanggal = (Date) spinnerTanggal.getValue();
        return tanggal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private BigDecimal bacaJumlah() {
        try {
            return new BigDecimal(txtJumlah.getText().trim());
        } catch (NumberFormatException e) {
            throw new ValidasiException("Jumlah harus berupa angka, contoh: 25000");
        }
    }

    private Date ubahKeDate(LocalDate tanggal) {
        return Date.from(tanggal.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}