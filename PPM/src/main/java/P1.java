import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class P1 extends P {
    BufferedImage image;

    public P1(int x, int y) {
        setBorder(new LineBorder(Color.BLACK, 1));
        image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        setSize(x, y);
        JLabel label = new JLabel("", new ImageIcon(image), 0);
    }

    public void paint(int x, int y, int color) {
        image.setRGB(x, y, (color == 1?Color.WHITE:Color.BLACK).getRGB());
    }


    public void displayImage(){
        Graphics g = getGraphics();
        g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
    }
}
