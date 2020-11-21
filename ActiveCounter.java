import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class ActiveCounter {

    static int num;
    static int exp;
    static int max;
    static Random rd;

    public ActiveCounter() {
        num = 0;
        exp = 0;
        max = 65535;
        rd = new Random();

    }

    public static void callActiveCounter() {
        ActiveCounter activeCounter = new ActiveCounter();
        increaseProb(1000000);
        BigDecimal value = new BigDecimal((num * Math.pow(2, exp)));
        System.out.println(value);
        writeToFile(value);
    }

    static void writeToFile(BigDecimal value)
    {
        BufferedWriter outputFile = null;
        try {
            File file = new File("ActiveCounter.txt");
            outputFile = new BufferedWriter(new FileWriter(file));
            outputFile.write(String.valueOf(value));
            outputFile.newLine();

        } catch ( IOException e ) {
        } finally {
            if ( outputFile != null ) {
                try {
                    outputFile.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /*
    static public void increase(int times) {
        int counter = 0;
        double power = 1;
        int state = 0;
        for (int i = 1; i <= times; ) {
            if (num + 1 > max) {
                i++;
                num = num + 1;
                num = num >>> 1;
                exp = exp + 1;
                power = Math.pow(2, exp);
            } else {

                if (state  == power) {
                    num++;
                    state = 0;
                }
                state++;
                i++;
            }
        }
    }
    */

    static public void increaseProb(int times) {
        int counter;
        int key = 0;
        for (int i = 0; i < times; i++) {
                counter = rd.nextInt((int) Math.pow(2, exp));
                if (counter == key) {
                    num++;
                    if (num + 1 > max) {
                        num = num + 1;
                        num = num / 2;
                        exp = exp + 1;
                }
            }
        }
    }
}
