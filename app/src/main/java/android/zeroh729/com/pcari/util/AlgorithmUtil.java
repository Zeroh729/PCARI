package android.zeroh729.com.pcari.util;

import android.graphics.Point;

import java.util.Random;

public class AlgorithmUtil {
    public static Point calculatePCA(int[] numbers){
        Point p = new Point();
        int x = new Random().nextInt() % 10;
        int y = new Random().nextInt() % 10;
        p.set(x, y);
        return p;
    }
}
