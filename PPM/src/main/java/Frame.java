import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame(int x, int y) {
        super("Hello World");

        setSize(new Dimension(1000, 1000));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
