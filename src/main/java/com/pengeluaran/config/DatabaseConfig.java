package com.pengeluaran.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Hanya bertugas membaca konfigurasi database. Tidak tahu cara membuat koneksi. */
public final class DatabaseConfig {

    private final String url;
    private final String user;
    private final String password;

    private DatabaseConfig(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static DatabaseConfig dariClasspath(String namaFile) {
        try (InputStream aliran = DatabaseConfig.class.getClassLoader().getResourceAsStream(namaFile)) {
            if (aliran == null) {
                throw new IllegalStateException(namaFile + " tidak ditemukan di src/main/resources.");
            }
            Properties properti = new Properties();
            properti.load(aliran);
            return new DatabaseConfig(
                    properti.getProperty("db.url"),
                    properti.getProperty("db.user"),
                    properti.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Gagal membaca " + namaFile, e);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
