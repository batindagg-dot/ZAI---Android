# ZAI – Android Studio projesi

ZAI, kadın ve erkek kullanıcılar için saç modeli keşfi, tercih bazlı öneriler ve fotoğraf seçimi sunan Android prototipidir.

## Açma
1. ZIP dosyasını çıkarın.
2. Android Studio'da `ZAI-Android` klasörünü açın.
3. Gradle senkronizasyonunun tamamlanmasını bekleyin.
4. Android SDK 35 ve JDK 17 kurulu olmalıdır.
5. Telefon/emülatör seçip Run ile çalıştırın.
6. APK için **Build > Build Bundle(s) / APK(s) > Build APK(s)** seçin.

APK çıktısı genellikle `app/build/outputs/apk/debug/app-debug.apk` konumuna yazılır.

## Bu prototipte bulunanlar
- ZAI ana ekranı ve modern arayüz
- Galeriden fotoğraf seçme
- Kadın/erkek/herkes kategorisi
- Yüz şekli ve saç yapısı tercihleri
- Kısa, orta ve uzun saç filtreleri
- Dokuz saç modeli ve açıklamaları
- Basit yüz şekli eşleşmesine dayalı öneri etiketi

## Önemli: AI entegrasyon durumu
Bu proje şu an **AI entegrasyonuna hazır prototiptir**, gerçek yüz analizi veya fotoğraf üzerinde saç değiştirme işlemi henüz yapılmaz. Fotoğraf cihazdan seçilir ve uygulama tarafından bir sunucuya gönderilmez.

Gerçek AI özelliği için:
- yüz/saç segmentasyonu ve yüz şekli analizi,
- görsel düzenleme (image-to-image) servisi,
- güvenli backend ve kullanıcı onayı,
- API anahtarlarının Android uygulamasına gömülmemesi
gereklidir.

Servis bağlantısı eklenirken fotoğraf yükleme ve sonuç indirme akışı backend üzerinden kurulmalıdır. Kullanıcının fotoğrafı için açık rıza, silme politikası ve veri saklama süresi belirlenmelidir.

## Bilgisayar olmadan APK oluşturma (GitHub Actions)
Bu projeye `.github/workflows/android-apk.yml` dosyası eklendi. Projeyi bir GitHub deposuna yükledikten sonra:
1. GitHub'da proje deposunu açın.
2. **Actions** sekmesine girin.
3. **Build ZAI APK** iş akışını seçin ve **Run workflow** düğmesine basın.
4. İşlem tamamlanınca başarılı çalıştırmayı açın; sayfanın altındaki **Artifacts** bölümünden **ZAI-debug-APK** dosyasını indirin.
5. ZIP'i telefonda açın; içindeki `app-debug.apk` dosyasına dokunarak yükleyin. Android izin isterse bu kaynaktan uygulama yüklemeye izin verin.

Not: Bu, test amaçlı imzasız debug APK'sıdır. Play Store yayını için ayrıca imzalama ve yayın hazırlığı gerekir. GitHub Actions derlemesi internet bağlantısı ve GitHub hesabı gerektirir.
