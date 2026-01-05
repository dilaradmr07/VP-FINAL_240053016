# VP-FINAL_240053016

[BPR 215 RAPOR ŞABLONU.docx](https://github.com/user-attachments/files/24439265/BPR.215.RAPOR.SABLONU.docx)
Abonelik Detoksu v2.5Abonelik Detoksu,
kullanıcıların dijital platform harcamalarını takip etmelerini, bütçe hedeflerine göre analiz yapmalarını ve bu harcamaların iş gücü karşılığını hesaplamalarını sağlayan Java tabanlı bir masaüstü uygulamasıdır.
# Özellikler
1.Dinamik Takip: Kullanıcılar platform adı ve ücret bilgisini girerek anlık olarak listeye ekleme yapabilirler
2.Akıllı Analiz: Toplam aylık gideri hesaplar ve yıllık maliyet projeksiyonu sunar
3.Emek-Maliyet Hesabı: Mevcut harcamaları karşılamak için kullanıcının kaç saat çalışması gerektiğini, saatlik kazanç üzerinden hesaplar
4.Görsel Geri Bildirim: Harcamalar bütçe limitine yaklaştığında renk değiştiren (Yeşil, Turuncu, Kırmızı) dinamik bir ilerleme çubuğu (JProgressBar) içerir
5.Kalıcı Veri: Eklenen tüm abonelikler yerel bir metin dosyasında (abonelikler.txt) saklanır ve uygulama her açıldığında otomatik olarak yüklenir.
# Algoritma Analizi ve Karmaşıklık (Big O)
Proje kapsamında kullanılan temel işlemlerin algoritma karmaşıklığı aşağıda teknik olarak analiz edilmiştir:
İşlem            Kullanılan Yöntem       Karmaşıklık (Big O)  Açıklama
Veri Ekleme      model.addRow            O(1)                 Dinamik tablo modelinin sonuna veri ekleme işlemidir.
Gider Hesaplama  for döngüsü ile toplam  O(n)                 Listedeki tüm elemanlar (n) tek tek gezilerek toplama yapılır.
Dosyaya Yazma    PrintWriter             O(n)                 Tablodaki her bir satır dosyaya seri hale getirilerek yazılır.
Dosyadan Okuma   Scanner                 O(n)                 Dosyadaki her bir satır ayrıştırılarak tabloya aktarılır.
Veri Silme       model.removeRow         O(n)                 Dizideki bir eleman silindiğinde, diğer elemanların kaydırılması gerekir.
# Teknik Detaylar
Materyal ve Metotlar
- Dil: Java.
- Arayüz: Java Swing (BorderLayout, GridLayout).
- Veri Yapısı: DefaultTableModel (Dinamik Dizi mantığı ile çalışır).
- Dosya Yönetimi: java.io kütüphanesi ile CSV formatında saklama.
# Mimari Tasarım
Uygulama, Veri Agregasyonu mantığıyla çalışır. Her veri girişinde bütçe analizi tetiklenerek kullanıcıya gerçek zamanlı veri görselleştirmesi sunulur.
# Gelecek Geliştirmeler ve Önerileri
- Sıralama (Sorting): Maliyete göre en yüksek harcamayı belirlemek için Quick Sort veya Merge Sort entegrasyonu yapılabilir.
- Arama (Search): Abonelik sayısı arttığında verimliliği korumak adına isim bazlı Binary Search algoritması eklenebilir.
- Veri Yapısı Değişimi: Hızlı erişim için veriler arka planda bir HashMap yapısında tutulabilir.
- # Kurulum ve Çalıştırma
- Proje dosyalarını indirin.
- AbonelikSistemi.java dosyasını bir Java IDE'si (IntelliJ, Eclipse vb.) ile açın.
- Kodu derleyin ve çalıştırın.
- Program kapandığında verileriniz abonelikler.txt dosyasına otomatik olarak kaydedilecektir.
#
Geliştiren: Nesime Dilara Demir
Ders: BPR 215 – Veri Yapıları
