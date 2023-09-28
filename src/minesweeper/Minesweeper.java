package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    Scanner gir = new Scanner(System.in);
    int enlem;
    int boylam;
    int[][] harita;
    int güvenliBölgeSayısı;
    
    
    public Minesweeper() {
        this.enlem = girdiİste("Azami enlemi girin:");
        this.boylam = girdiİste("Azami boylamı girin:");
        hazırla();
        başlat();
    }
    
    private void hazırla() {
        harita = new int[enlem][boylam];
        
        int bölgeSayısı = enlem * boylam;
        int bomba = bölgeSayısı / 4;
        güvenliBölgeSayısı = bölgeSayısı - bomba;
        
        // bomba atılacak bölgeleri belirle
        Random kader = new Random();
        while (bomba > 0) {
            // rastgele bir koordinat seç
            int en = kader.nextInt(enlem);
            int boy = kader.nextInt(boylam);
            
            // seçilen koordinat halihazırda değilse onu bomba atılacak bölge eyle.
            if (harita[en][boy] != -1) {
                harita[en][boy] = -11; // -1 bomba demek, 10 eklenmiş hali (-11) keşfedilmediği manasına gelmektedir.
                bomba--;
            }
        }
        
        // her bomba olmayan bölge için çevresindeki bomba düşecek bölge sayısını hesapla ve ata
        // 10 ekleme ve 10 çıkarma işlemleri, ilgili koordinatın oyuncu tarafından keşfedilip keşfedilmediğini belirtir
        // oyuncu o bölgeyi açarsa ör. 14 olan değer 4'e dönüşecek.
        for (int en = 0; en < enlem; en++) {
            for (int boy = 0; boy < boylam; boy++) {
                // eğer koordinat bomba değilse çevre bölgeleri keşfet ve değerini ata
                if (harita[en][boy] != -11) harita[en][boy] = çevreBölgeleriKeşfet(en, boy) + 10;
                
                // çözümü ekrana bir kere yazdırmak ve tekrar döngü açmamak için bu döngüden faydalanılmaktadır
                System.out.print(harita[en][boy] < 0 ? "*  " : harita[en][boy] % 10 + "  ");
            }
            System.out.println();
        }
    }
    
    private void başlat() {
        System.out.println("-------------------\n1'den başlayarak arzu ettiğin bölgenin koordinatını gir!\nGüvenli bölgeleri bul, hayatta kal!\n-------------------");
        int keşfedilenBölgeSayısı = 0;
        // değerlendirme formu 11
        while (keşfedilenBölgeSayısı < güvenliBölgeSayısı) {
            haritayıYazdır();
            // değerlendirme formu 9
            int en = girdiİste("Keşfedeceğin enlemi gir:") - 1;
            int boy = girdiİste("Keşfedeceğin boylamı gir:") - 1;
            
            System.out.println(); // bir şeyler daha belirgin gözüksün, yapışık durmasın diye.
            
            // değerlendirme formu 10
            if (!haritadaMı(new int[]{en, boy})) {
                System.out.println("Müdahale etmeseydim şu anda uzay boşluğunda yuvarlanıyordun!\nDünya'nın sınırlarını aşmamaya çalışalım, ne dersin?");
                continue;
            }
            
            // daha önce keşfedilmediyse keşfedilen bölge sayısını bir artırırız
            if (!keşfedildiMi(en, boy)) keşfedilenBölgeSayısı++; 
            // o bölgeyi değersel açıdan da keşfedilmiş kılmak için mod 10 işlemi yaparız
            harita[en][boy] %= 10;
            
            // değerlendirme formu 13
            if (harita[en][boy] == -1) {
                haritayıYazdır();
                System.out.println("Sığınmak için gelmiştin, başına bombalar yağdı.\nNe yapalım, misafir umduğunu değil bulduğunu yermiş.\nÖldün.");
                return;
            }    
        }
        // while döngüsünden başarıyla çıkılırsa oyun kazanılmıştır
        System.out.println("Jitler'in ülkeni getirdiği bu korkunç yeryüzünde hayatta kalmayı başardın.\nKazandın.");
    }
    
    private int girdiİste(String istem) {
        System.out.print(istem + " ");
        int girdi = gir.nextInt();
        return girdi;
    }
    
    private void haritayıYazdır() {
        for (int en = 0; en < enlem; en++) {
            for (int boy = 0; boy < boylam; boy++) {
                // değerlendirme formu 12
                if (keşfedildiMi(en, boy)) {
                    System.out.print(
                            harita[en][boy] == -1 ? "*  " : + harita[en][boy] + "  ");
                }
                else System.out.print("-  ");
            }
            System.out.println();
        }
    }
    
    private int çevreBölgeleriKeşfet(int mevcutEnlem, int mevcutBoylam) {
        int[] kuzey = {mevcutEnlem - 1, mevcutBoylam};
        int[] güney = {mevcutEnlem + 1, mevcutBoylam};
        int[] doğu = {mevcutEnlem, mevcutBoylam + 1};
        int[] batı = {mevcutEnlem, mevcutBoylam - 1};
        int[] kuzeydoğu = {kuzey[0], doğu[1]};
        int[] kuzeybatı = {kuzey[0], batı[1]};
        int[] güneydoğu = {güney[0], doğu[1]};
        int[] güneybatı = {güney[0], batı[1]};
        
        int[][] yönler = {
                kuzey, güney, doğu, batı,
                kuzeydoğu, kuzeybatı, güneydoğu, güneybatı
        };
        
        int riskliBölgeSayısı = 0;
        for (int[] yön : yönler) {
            if (haritadaMı(yön)) {
                riskliBölgeSayısı += harita[yön[0]][yön[1]] == -11 ? 1 : 0; // verilen koordinata bomba düşecek ise riskli bölge sayısını bir artır
            }
        }
        return riskliBölgeSayısı;
    }
    
    private boolean haritadaMı(int[] koordinat) {
        return koordinat[0] >= 0 && koordinat[0] < enlem && koordinat[1] >= 0 && koordinat[1] < boylam;
    }
    
    private boolean keşfedildiMi(int en, int boy) {
        int değer = harita[en][boy];
        // eğer tek haneli bir sayı değilse bu bölge keşfedilmiştir.
        return değer >= -1 && değer <= 8;
    }
}