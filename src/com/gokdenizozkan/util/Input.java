package com.gokdenizozkan.util;

import java.util.Scanner;

public class Input {
    private static Scanner sc;

    // Initializes class attributes (like Scanner sc)
    static {
        new Input();
    }

    // To reinitialize the class if needed
    private Input() {
        sc = new Scanner(System.in);
    }

    // Methods
    public static int getInt(String... ask) {
        if (!isEmpty(ask)) System.out.println(ask[0]);
        return sc.nextInt();
    }

    public static String getLine(String... ask) {
        if (!isEmpty(ask)) System.out.println(ask[0]);
        return sc.nextLine();
    }

    public static String[] getLine(char separator, String... ask) {
        if (!isEmpty(ask)) System.out.println(ask[0]);
        return sc.nextLine().trim().split(String.valueOf(separator));
    }

    public static String[] getLine(String separator, String... ask) {
        if (!isEmpty(ask)) System.out.println(ask[0]);
        return sc.nextLine().trim().split(separator);
    }
    
    /**
     * Returns the option user selected.
     * @param <T> type
     * @param question to lead the user to select an appropriate option.
     * @param options user can select.
     * @return selection in the type it was given as an option
     */
    public static <T> T ask(String question, T... options) {
    	System.out.println(question);
        for (int i = 0; i < options.length; i++) {
        	System.out.printf("%d > %s\n", i + 1, options[i].toString());
        }
        
        return options[sc.nextInt() - 1];
    }
    
    /**
     * Program pausing input prompt.
     * @param <T> type
     * @param question to lead the user to select an appropriate option.
     */
    public static void await(String question) {
    	System.out.println(question);
    	sc.nextLine();
    }
    
    /**
     * Program pausing input prompt. Informs the user with this text: "Please enter any key to resume."
     * @param <T> type
     */
    public static void await() {
    	System.out.println("Please enter any key to resume.");
    	resetScanner();
    	sc.nextLine();
    }

    // Misc

    /**
     * Resets the scanner by reinitializing the class to avoid buffer issues.
     */
    public static void resetScanner() {
        new Input();
    }
    
    /**
     * Gets user input as a line and it is empty ("") safe
     * @return
     */
    public static String getLineEmptySafe() {
        String s;
        do {
            s = sc.nextLine();
        } while (s.equals(""));
        return s;
    }

    private static <T> boolean isLengthZero(T[] arr) {
        return arr.length == 0;
    }

    private static <T> boolean isEmpty(T[] arr) {
        for (T e : arr) {
        	if (e != null) return false;
        }
        return true;
    }
    
    
}
