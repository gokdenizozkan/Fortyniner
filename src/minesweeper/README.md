# Minesweeper
Yazı tabanlı mayın tarlası klonu.

# Nasıl çalışır
Minesweeper.java dosyası içerisinde tüm oyun kurgulanmıştır.
Constructor içerisinde kullanıcıdan oyun tahtasının büyüklüğünü girmesi istenir.
Bu bilgiye göre tahta oluşturulmasını sağlamak için hazırla() metodu çağırılır.

**hazırla()**
Bu metot içerisinde öncelikle tahtanın değer ataması gerçekleştirilir.
İkinci adım bomba atılacak, yani mayın yerleştirilecek, bölgelerin belirlenmesidir.
- Toplam bölge sayısı hesaplanır.
- Atılacak bomba sayısı belirlenir (4 ile bölünerek).
- Bu iki verinin ışığında güvenli bölgelerin sayısı hesaplanır.

Bu hesaplamaların ardından Random sınıfı kullanılarak tüm bombalar yerleştirilene kadar rastgele bir koordinata erişim sağlanır.
- Halihazırda bomba değilse bomba eylenir.

Buradaki önemli nokta bombanın verisel karşılığı olan "-1"dir. Ancak -1 yerine -1 + -10 değer hesaplanır.
10 sayısının eklenmesi, bu bölgenin henüz keşfedilmediği, yani oyuncu tarafından açılmadığını yansıtır.
- Diğer tüm bölgeler için de 10 değerinin eklemesi yapılacaktır. +10, "revealed board" üretmemizin önüne geçmektedir.

Bombalar yerleştirildikten sonra sıra kalan diğer bölgelerin çevresindeki riskli bölgeleri (bomba/mayın miktarlarını) hesaplamaya gelir.
Bu işlem de gerçekleştirildikten sonra hazırla metodu görevini tamamlar.

**başlat()**
Tüm oyun döngüsünün gerçekleştiği metottur. Ekrana oyun tahtası yazdırılır ve devamında kullanıcıdan keşfetmek istediği bölge bilgisi istenir.
- Verilen koordinatın önce *haritadaMı()* olduğu sorgulanır.
- Haritada ise *keşfedildiMi()* sorgulanır.

Bu sorguları ilgili koordinattaki sayısal değere mod 10 işlemi yaparak veya tek haneli olup olmadığı anlaşılarak cevap bulunur.

Son olarak kullanıcı mayına basmışsa oyun kaybedilir ve eğer while döngüsünden çıkmayı başarırsa oyunu kazanmış sayılır.
