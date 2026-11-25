package com.pengeluaran;

import com.pengeluaran.config.ConnectionProvider;
import com.pengeluaran.config.DatabaseConfig;
import com.pengeluaran.config.PostgresConnectionProvider;
import com.pengeluaran.controller.PengeluaranController;
import com.pengeluaran.model.Pengeluaran;
import com.pengeluaran.repository.PengeluaranRepository;
import com.pengeluaran.repository.PengeluaranRepositoryJdbc;
import com.pengeluaran.service.PengeluaranService;
import com.pengeluaran.service.PengeluaranServiceImpl;
import com.pengeluaran.validation.PengeluaranValidator;
import com.pengeluaran.validation.Validator;
import com.pengeluaran.view.FormPengeluaran;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::mulai);
    }

    private static void mulai() {
        pasangLookAndFeel();
        try {
            DatabaseConfig config = DatabaseConfig.dariClasspath("db.properties");
            ConnectionProvider connectionProvider = new PostgresConnectionProvider(config);
            PengeluaranRepository repository = new PengeluaranRepositoryJdbc(connectionProvider);
            Validator<Pengeluaran> validator = new PengeluaranValidator();
            PengeluaranService service = new PengeluaranServiceImpl(repository, validator);

            FormPengeluaran form = new FormPengeluaran();
            PengeluaranController controller = new PengeluaranController(service, form);
            form.setListener(controller);

            form.setVisible(true);
            controller.muatData();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Gagal memulai aplikasi", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void pasangLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            
        }
    }
}
