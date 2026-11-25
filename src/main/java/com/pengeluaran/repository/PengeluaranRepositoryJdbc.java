package com.pengeluaran.repository;

import com.pengeluaran.config.ConnectionProvider;
import com.pengeluaran.exception.DataAccessException;
import com.pengeluaran.model.Pengeluaran;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Implementasi JDBC. Satu-satunya kelas yang tahu SQL dan java.sql. */
public class PengeluaranRepositoryJdbc implements PengeluaranRepository {

    private static final String SQL_CARI_SEMUA = """
            SELECT
                pengeluaran.id,
                pengeluaran.tanggal,
                pengeluaran.kategori,
                pengeluaran.keterangan,
                pengeluaran.jumlah
            FROM pengeluaran
            ORDER BY
                pengeluaran.tanggal DESC,
                pengeluaran.id DESC
            """;

    private static final String SQL_SIMPAN = """
            INSERT INTO pengeluaran (
                tanggal,
                kategori,
                keterangan,
                jumlah
            )
            VALUES (?, ?, ?, ?)
            """;

    private static final String SQL_UBAH = """
            UPDATE pengeluaran
            SET
                tanggal = ?,
                kategori = ?,
                keterangan = ?,
                jumlah = ?
            WHERE pengeluaran.id = ?
            """;

    private static final String SQL_HAPUS = """
            DELETE FROM pengeluaran
            WHERE pengeluaran.id = ?
            """;

    private static final String SQL_TOTAL = """
            SELECT COALESCE(SUM(pengeluaran.jumlah), 0) AS totalPengeluaran
            FROM pengeluaran
            """;

    private final ConnectionProvider connectionProvider;

    public PengeluaranRepositoryJdbc(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public List<Pengeluaran> cariSemua() {
        try (Connection koneksi = connectionProvider.getConnection();
             PreparedStatement statement = koneksi.prepareStatement(SQL_CARI_SEMUA);
             ResultSet hasil = statement.executeQuery()) {
            List<Pengeluaran> daftar = new ArrayList<>();
            while (hasil.next()) {
                daftar.add(petakanBaris(hasil));
            }
            return daftar;
        } catch (SQLException e) {
            throw new DataAccessException("Gagal mengambil data: " + e.getMessage(), e);
        }
    }

    @Override
    public void simpan(Pengeluaran pengeluaran) {
        try (Connection koneksi = connectionProvider.getConnection();
             PreparedStatement statement = koneksi.prepareStatement(SQL_SIMPAN)) {
            isiParameterData(statement, pengeluaran);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Gagal menyimpan data: " + e.getMessage(), e);
        }
    }

    @Override
    public void ubah(Pengeluaran pengeluaran) {
        try (Connection koneksi = connectionProvider.getConnection();
             PreparedStatement statement = koneksi.prepareStatement(SQL_UBAH)) {
            isiParameterData(statement, pengeluaran);
            statement.setInt(5, pengeluaran.getId());
            pastikanAdaYangBerubah(statement.executeUpdate());
        } catch (SQLException e) {
            throw new DataAccessException("Gagal mengubah data: " + e.getMessage(), e);
        }
    }

    @Override
    public void hapus(int id) {
        try (Connection koneksi = connectionProvider.getConnection();
             PreparedStatement statement = koneksi.prepareStatement(SQL_HAPUS)) {
            statement.setInt(1, id);
            pastikanAdaYangBerubah(statement.executeUpdate());
        } catch (SQLException e) {
            throw new DataAccessException("Gagal menghapus data: " + e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal hitungTotal() {
        try (Connection koneksi = connectionProvider.getConnection();
             PreparedStatement statement = koneksi.prepareStatement(SQL_TOTAL);
             ResultSet hasil = statement.executeQuery()) {
            return hasil.next() ? hasil.getBigDecimal(1) : BigDecimal.ZERO;
        } catch (SQLException e) {
            throw new DataAccessException("Gagal menghitung total: " + e.getMessage(), e);
        }
    }

    private void isiParameterData(PreparedStatement statement, Pengeluaran pengeluaran) throws SQLException {
        statement.setDate(1, Date.valueOf(pengeluaran.getTanggal()));
        statement.setString(2, pengeluaran.getKategori());
        statement.setString(3, pengeluaran.getKeterangan());
        statement.setBigDecimal(4, pengeluaran.getJumlah());
    }

    private void pastikanAdaYangBerubah(int jumlahBaris) {
        if (jumlahBaris == 0) {
            throw new DataAccessException("Data tidak ditemukan. Mungkin sudah dihapus.");
        }
    }

    private Pengeluaran petakanBaris(ResultSet hasil) throws SQLException {
        return new Pengeluaran(
                hasil.getInt("id"),
                hasil.getDate("tanggal").toLocalDate(),
                hasil.getString("kategori"),
                hasil.getString("keterangan"),
                hasil.getBigDecimal("jumlah"));
    }
}
