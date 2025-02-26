# Java Instagram Clone

Bu proje, Java ve Firebase kullanılarak geliştirilen basit bir Instagram klonudur. Kullanıcıların hesap oluşturup giriş yapmalarını, fotoğraf yüklemelerini ve diğer kullanıcıların gönderilerini görmelerini sağlar.

## İçindekiler
- [Özellikler](#özellikler)
- [Kurulum](#kurulum)
- [Kullanılan Teknolojiler](#kullanılan-teknolojiler)
- [Proje Yapısı](#proje-yapısı)
- [Ekran Görüntüleri](#ekran-gör%C3%BCnt%C3%BCleri)


## Özellikler
- Kullanıcı kayıt olma ve giriş yapma
- Firebase Authentication ile kimlik doğrulama
- Firebase Firestore ile gönderi verilerinin saklanması
- Firebase Storage ile resimlerin yüklenmesi
- Kullanıcıların fotoğraf paylaşması ve diğer kullanıcıların paylaşımlarını görmesi

## Kurulum
1. **Projeyi klonlayın:**
   ```sh
   git clone https://github.com/kullaniciadi/java-instagram-clone.git
   ```
2. **Android Studio ile açın**
3. **Gerekli bağımlılıkları yükleyin** (Gradle Sync yapın)
4. **Firebase yapılandırmasını tamamlayın:**
   - Firebase Console'dan bir proje oluşturun
   - `google-services.json` dosyasını indirin ve `app` klasörüne ekleyin
   - Authentication, Firestore ve Storage'ı etkinleştirin
5. **Uygulamayı çalıştırın**

## Kullanılan Teknolojiler
- Java
- Android SDK
- Firebase Authentication
- Firebase Firestore
- Firebase Storage

## Proje Yapısı
```
java-instagram-clone/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/javainstagramclone/
│   │   │   │   ├── view/
│   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   ├── UploadActivity.java
│   │   │   │   │   ├── FeedActivity.java
│   │   │   │   ├── adapter/
│   │   │   │   │   ├── PostAdapter.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── Post.java
│   │   │   │   ├── util/
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── drawable/
│   │   │   │   ├── values/
│   │   ├── AndroidManifest.xml
├── build.gradle
├── settings.gradle
└── README.md
```

## Ekran Görüntüleri
(Ekran görüntüleri buraya eklenebilir)
| Ekran 1 | Ekran 2 | Ekran 3 |
|---------|---------|---------|
| <img src="https://github.com/user-attachments/assets/5c1e7cee-ec72-4752-a511-88256357ae14" width="200"> | <img src="https://github.com/user-attachments/assets/baa5849b-542f-42ba-b966-025a0d8a119c" width="200"> | <img src="https://github.com/user-attachments/assets/cde49d3b-a108-49ed-87aa-38f4f1b7f23e" width="200"> |



