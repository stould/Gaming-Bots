import com.sun.glass.ui.Pixels;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Helper {

    private static final long MOD = 100000007;
    private static final long BASE = 3311;

    private static long power(long base, int exp) {
        if (exp == 0) {
            return 1;
        } else {
            if ((exp & 1) > 0) {
                return (base * power(base, exp - 1)) % MOD;
            } else {
                return power((base * base) % MOD, exp >> 1);
            }
        }
    }

    public static long getHash(Shape input) throws Exception {
        Shape<Integer> shape = (Shape<Integer>) input;
        int n = shape.getN();
        if (n == 0) throw new Exception("Shape is empty.");
        int m = shape.getM();
        if (m == 0) throw new Exception("Shape is empty.");
        long ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans = ((BASE * ans) % MOD + shape.getShapeMatrix()[i][j]) % MOD;
            }
        }
        return (ans + power(BASE, n * m - 1)) % MOD;
    }

    public static int getFirstAvailableShapeName(String path) throws FileNotFoundException {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        Set<Integer> mapper = new HashSet<>();
        for(File file : listOfFiles){
            mapper.add(Integer.parseInt(file.getName()));
        }
        int ans = 0;
        for(Integer i : mapper){
            if(i - 1 >= 0 && !mapper.contains(i - 1)){
                ans = i - 1;
                break;
            }else if(!mapper.contains(i + 1)){
                ans = i + 1;
                break;
            }
        }
        return ans;
    }

    public static int toInteger(int arr[]){
        int n = arr.length;
        int ans = 0;
        for(int i = 0; i < n; i++){
            ans = ans * 10 + arr[i];
        }
        return ans;
    }


    public static BufferedImage convertToImage(Pixels glassPixels) {
        int width = glassPixels.getWidth();
        int height = glassPixels.getHeight();
        WritableImage writableImage = new WritableImage(width, height);
        IntBuffer intBuffer = (IntBuffer) glassPixels.getPixels();
        BufferedImage image = writeIntBufferToImage(intBuffer, writableImage);
        return image;
    }

    public static BufferedImage writeIntBufferToImage(IntBuffer intBuffer, WritableImage writableImage) {
        int width = (int) writableImage.getWidth();
        int height = (int) writableImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = intBuffer.get();
                image.setRGB(x, y, argb);
            }
        }
        return image;
    }

}
