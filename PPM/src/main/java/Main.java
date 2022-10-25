import javax.swing.*;
import java.io.*;

public class Main {
    public ClassLoader classLoader = getClass().getClassLoader();
    static Frame frame;

    public static void main(String[] args) throws IOException, InterruptedException {
        frame = new Frame();
        MenuBar menuBar = new MenuBar(frame);
        frame.setJMenuBar(menuBar);
        Thread.sleep(100);
        frame.setVisible (true);
    }
}
