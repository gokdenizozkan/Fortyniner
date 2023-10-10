package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    Scanner sc = new Scanner(System.in);
    int row;
    int column;
    int[][] board;
    int safeZoneCount;
    
    
    public Minesweeper() {
        // değerlendirme formu 7
        this.row = getInput("Azami enlemi girin:");
        this.column = getInput("Azami boylamı girin:");
        init();
        loop();
    }
    
    // değerlendirme formu 6
    private void init() {
        board = new int[row][column];
        
        int zoneCount = row * column;
        int riskyZoneCount = zoneCount / 4;
        safeZoneCount = zoneCount - riskyZoneCount;
        
        // değerlendirme formu 8
        // riskyZoneCount atılacak bölgeleri belirle
        Random dice = new Random();
        while (riskyZoneCount > 0) {
            // rastgele bir koordinat seç
            int r = dice.nextInt(row);
            int c = dice.nextInt(column);
            
            // seçilen koordinat halihazırda değilse onu riskyZoneCount atılacak bölge eyle.
            if (board[r][c] != -1) {
                board[r][c] = -11; // -1 riskyZoneCount demek, 10 eklenmiş hali (-11) keşfedilmediği manasına gelmektedir.
                riskyZoneCount--;
            }
        }
        
        // her riskyZoneCount olmayan bölge için çevresindeki riskyZoneCount düşecek bölge sayısını hesapla ve ata
        // 10 ekleme ve 10 çıkarma işlemleri, ilgili koordinatın oyuncu tarafından keşfedilip keşfedilmediğini belirtir
        // oyuncu o bölgeyi açarsa ör. 14 olan değer 4'e dönüşecek.
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < column; c++) {
                // eğer koordinat riskyZoneCount değilse çevre bölgeleri keşfet ve değerini ata
                if (board[r][c] != -11) board[r][c] = checkZones(r, c) + 10;
                
                // çözümü ekrana bir kere yazdırmak ve tekrar döngü açmamak için bu döngüden faydalanılmaktadır
                System.out.print(board[r][c] < 0 ? "*  " : board[r][c] % 10 + "  ");
            }
            System.out.println();
        }
    }
    
 // değerlendirme formu 6
    private void loop() {
        System.out.println("-------------------\n1'den başlayarak arzu ettiğin bölgenin koordinatını gir!\nGüvenli bölgeleri bul, hayatta kal!\n-------------------");
        int checkedZoneCount = 0;
        // değerlendirme formu 14
        while (checkedZoneCount < safeZoneCount) {
            // değerlendirme formu 11
            printBoard();
            // değerlendirme formu 9
            int r = getInput("Keşfedeceğin enlemi gir:") - 1;
            int c = getInput("Keşfedeceğin boylamı gir:") - 1;
            
            System.out.println(); // bir şeyler daha belirgin gözüksün, yapışık durmasın diye.
            
            // değerlendirme formu 10
            if (!isOnBoard(new int[]{r, c})) {
                System.out.println("Müdahale etmeseydim şu anda uzay boşluğunda yuvarlanıyordun!\nDünya'nın sınırlarını aşmamaya çalışalım, ne dersin?");
                continue;
            }
            
            // daha önce keşfedilmediyse keşfedilen bölge sayısını bir artırırız
            if (!isChecked(r, c)) checkedZoneCount++;
            // o bölgeyi değersel açıdan da keşfedilmiş kılmak için mod 10 işlemi yaparız
            board[r][c] %= 10;
            
            // değerlendirme formu 13
            if (board[r][c] == -1) {
                printBoard();
                System.out.println("Sığınmak için gelmiştin, başına bombalar yağdı.\nNe yapalım, misafir umduğunu değil bulduğunu yermiş.\nÖldün.");
                return;
            }    
        }
        // while döngüsünden başarıyla çıkılırsa oyun kazanılmıştır
        System.out.println("Jitler'in ülkeni getirdiği bu korkunç yeryüzünde hayatta kalmayı başardın.\nKazandın.");
    }
    
    private int getInput(String question) {
        System.out.print(question + " ");
        return sc.nextInt();
    }
    
    private void printBoard() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < column; c++) {
                // değerlendirme formu 12
                if (isChecked(r, c)) {
                    System.out.print(
                            board[r][c] == -1 ? "*  " : board[r][c] + "  ");
                }
                else System.out.print("-  ");
            }
            System.out.println();
        }
    }
    
    private int checkZones(int mevcutEnlem, int mevcutBoylam) {
        int[] north = {mevcutEnlem - 1, mevcutBoylam};
        int[] south = {mevcutEnlem + 1, mevcutBoylam};
        int[] east = {mevcutEnlem, mevcutBoylam + 1};
        int[] west = {mevcutEnlem, mevcutBoylam - 1};
        int[] northeast = {north[0], east[1]};
        int[] northwest = {north[0], west[1]};
        int[] southeast = {south[0], east[1]};
        int[] southwest = {south[0], west[1]};
        
        int[][] directions = {
                north, south, east, west,
                northeast, northwest, southeast, southwest
        };
        
        int riskyZoneCount = 0;
        for (int[] direction : directions) {
            if (isOnBoard(direction)) {
                // verilen koordinata bomba düşecek ise riskli bölge sayısını bir artır
                riskyZoneCount += board[direction[0]][direction[1]] == -11 ? 1 : 0;
            }
        }
        return riskyZoneCount;
    }
    
    private boolean isOnBoard(int[] coord) {
        return coord[0] >= 0 && coord[0] < row && coord[1] >= 0 && coord[1] < column;
    }
    
    private boolean isChecked(int r, int c) {
        int value = board[r][c];
        // eğer tek haneli bir sayı değilse bu bölge keşfedilmiştir.
        return value >= -1 && value <= 8;
    }
}