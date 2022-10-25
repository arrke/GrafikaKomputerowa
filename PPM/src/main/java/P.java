import javax.swing.*;
import java.awt.*;

public abstract class P extends JPanel {

    private FileReader instance;
    public abstract void paint(int x, int y, int color);
    public abstract void paint(int x, int y, int red, int greeen, int blue);
    public abstract void displayImage(Graphics g);

    @Override
    public void paintComponent(Graphics g){
        this.displayImage(g);
    }

    public FileReader getInstance() {
        return instance;
    }

    public void setInstance(FileReader instance) {
        this.instance = instance;
    }
}
