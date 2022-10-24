import javax.swing.*;
import java.io.*;

public class Main {
    public ClassLoader classLoader = getClass().getClassLoader();
    static JFrame frame;

    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();

        FileReader instance = new FileReader();
        String fileName = "ppm-test-07-p3-big.ppm";
        InputStream is = instance.getFileAsIOStream(fileName);
        instance.setParameters(is);

        is = instance.getFileAsIOStream(fileName);
        instance.printFileContent(is);
//        instance.printBinaryFileContent(new FileInputStream("C:\\Users\\konra\\Desktop\\PPM\\src\\main\\resources\\ppm-test-07-p3-big.ppm"));

//        P1 panel = new P1(instance.x, instance.y);
//        P2 panel = new P2(instance.x, instance.y, instance.scale);
        P3 panel = new P3(instance.x, instance.y, instance.scale);

        frame = new Frame(instance.x, instance.y);
        frame.add(panel);
        Thread.sleep(100);


// p3,p6
        for (int i = 0; i < instance.x; i++) {
            for (int j = 0; j < instance.y; j++) {
                panel.paint(i, j, instance.pixels[i][j][0], instance.pixels[i][j][1], instance.pixels[i][j][2]);
            }
        }

// p1,p2,p4,p5
//        for (int i = 0; i <instance.x;i++) {
//            for (int j = 0; j< instance.y;j++){
//                panel.paint(i,j,instance.pixels[i][j][0]);
//            }
//        }
        panel.displayImage();
        // ...
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + "ms");
    }
}
