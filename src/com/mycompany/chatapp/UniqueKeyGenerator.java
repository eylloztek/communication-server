package com.mycompany.chatapp;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UniqueKeyGenerator {

    private static final Random random = new Random();
    private static final Set<String> generatedKeys = new HashSet<>();

    public static String generateUniqueKey() {
        String key = generateRandomKey();

        // Eğer oluşturulan anahtar daha önce kullanılmamışsa, kullanılmış anahtarlar setine ekle
        while (generatedKeys.contains(key)) {
            key = generateRandomKey();
        }
        generatedKeys.add(key);

        return key;
    }

    private static String generateRandomKey() {
        // 6 haneli rastgele bir sayı üretir.
        int keyInt = 100000 + random.nextInt(900000);
        return String.valueOf(keyInt);
    }
}
