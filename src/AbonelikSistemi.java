import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class AbonelikSistemi extends JFrame {
    private DefaultTableModel model;
    private JProgressBar butceBar;
    private JLabel toplamGiderLabel, calismaSuresiLabel;
    private JTextField adField, fiyatField, butceLimitField, saatlikUcretField;
    private double toplamGider = 0;
    private final String DOSYA_ADI = "abonelikler.txt"; // Kayıt dosyası

    public AbonelikSistemi() {
        setTitle("Abonelik Detoksu v2.5 - Dosya Kayit Destekli");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // 1. ÜST PANEL: GİRİŞ ALANLARI
        JPanel ustPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        ustPanel.setBorder(BorderFactory.createTitledBorder("Veri Girisi"));

        JPanel ayarPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        butceLimitField = new JTextField("2000");
        saatlikUcretField = new JTextField("150");
        adField = new JTextField();
        fiyatField = new JTextField();

        ayarPanel.add(new JLabel("Butce Hedefi (TL):")); ayarPanel.add(butceLimitField);
        ayarPanel.add(new JLabel("Saatlik Kazanc (TL):")); ayarPanel.add(saatlikUcretField);
        ayarPanel.add(new JLabel("Platform Adi:")); ayarPanel.add(adField);
        ayarPanel.add(new JLabel("Aylik Ucret:")); ayarPanel.add(fiyatField);

        JPanel butonPanel = new JPanel(new FlowLayout());
        JButton ekleButon = new JButton("Kaydet ve Ekle");
        JButton silButon = new JButton("Seciliyi Sil");
        ekleButon.setBackground(new Color(46, 204, 113));
        silButon.setBackground(new Color(231, 76, 60));
        butonPanel.add(ekleButon);
        butonPanel.add(silButon);

        ustPanel.add(ayarPanel);
        ustPanel.add(butonPanel);

        // 2. ORTA PANEL: TABLO
        model = new DefaultTableModel(new String[]{"Platform", "Maliyet (TL)", "Yillik"}, 0);
        JTable tablo = new JTable(model);
        add(new JScrollPane(tablo), BorderLayout.CENTER);

        // 3. ALT PANEL: ANALİZ
        JPanel altPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        toplamGiderLabel = new JLabel("Toplam: 0.00 TL", SwingConstants.CENTER);
        calismaSuresiLabel = new JLabel("Gereken Calisma: 0 saat", SwingConstants.CENTER);
        butceBar = new JProgressBar(0, 100);
        butceBar.setStringPainted(true);
        altPanel.add(toplamGiderLabel);
        altPanel.add(calismaSuresiLabel);
        altPanel.add(butceBar);

        add(ustPanel, BorderLayout.NORTH);
        add(altPanel, BorderLayout.SOUTH);

        // Aksiyonlar
        ekleButon.addActionListener(e -> veriEkle());
        silButon.addActionListener(e -> {
            int satir = tablo.getSelectedRow();
            if (satir != -1) {
                model.removeRow(satir);
                dosyayaKaydet(); // Silme sonrası dosyayı güncelle
                guncelleAnaliz();
            }
        });

        // Uygulama açıldığında verileri dosyadan oku
        dosyadanOku();
    }

    private void veriEkle() {
        try {
            String ad = adField.getText();
            String fiyatStr = fiyatField.getText();
            if(ad.isEmpty() || fiyatStr.isEmpty()) throw new Exception();

            model.addRow(new Object[]{ad, Double.parseDouble(fiyatStr), Double.parseDouble(fiyatStr) * 12});
            dosyayaKaydet(); // Yeni veri eklenince dosyaya yaz
            guncelleAnaliz();
            adField.setText("");
            fiyatField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gecerli bir veri giriniz!");
        }
    }

    private void dosyayaKaydet() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DOSYA_ADI))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.println(model.getValueAt(i, 0) + "," + model.getValueAt(i, 1));
            }
        } catch (IOException e) {
            System.out.println("Dosya yazma hatasi!");
        }
    }

    private void dosyadanOku() {
        File dosya = new File(DOSYA_ADI);
        if (!dosya.exists()) return;

        try (Scanner sc = new Scanner(dosya)) {
            while (sc.hasNextLine()) {
                String[] parcalar = sc.nextLine().split(",");
                if (parcalar.length == 2) {
                    double fiyat = Double.parseDouble(parcalar[1]);
                    model.addRow(new Object[]{parcalar[0], fiyat, fiyat * 12});
                }
            }
            guncelleAnaliz();
        } catch (Exception e) {
            System.out.println("Dosya okuma hatasi!");
        }
    }

    private void guncelleAnaliz() {
        toplamGider = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            toplamGider += (double) model.getValueAt(i, 1);
        }

        double limit = Double.parseDouble(butceLimitField.getText());
        double saatlik = Double.parseDouble(saatlikUcretField.getText());

        toplamGiderLabel.setText(String.format("Toplam Aylik Gider: %.2f TL", toplamGider));
        calismaSuresiLabel.setText(String.format("Gereken Calisma: %.1f saat", toplamGider / saatlik));

        int yuzde = (int) ((toplamGider / limit) * 100);
        butceBar.setValue(Math.min(yuzde, 100));
        butceBar.setString("%" + yuzde);
        butceBar.setForeground(yuzde > 100 ? Color.RED : (yuzde > 80 ? Color.ORANGE : Color.GREEN));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AbonelikSistemi().setVisible(true));
    }
}