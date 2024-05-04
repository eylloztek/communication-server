/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CommunicationServer;

/**
 *
 * @author Eylül Öztek
 */
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UniqueKeyGenerator {
    private static final Random random = new Random();

    public static int generateKey() {
        // 100000 ile 999999 arasında rastgele bir sayı üretir.
        return 100000 + random.nextInt(900000);
    }
}

