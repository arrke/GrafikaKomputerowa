import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class P1 extends P {
    BufferedImage image;

    public P1(int x, int y) {
        image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        setSize(x, y);
        JLabel label = new JLabel("", new ImageIcon(image), 0);
    }
    @Override
    public void paint(int x, int y, int color) {
        image.setRGB(x, y, (color == 1?Color.WHITE:Color.BLACK).getRGB());
    }

    @Override
    public void paint(int x, int y, int red, int greeen, int blue) {

    }


    public void displayImage(Graphics g){
        g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
    }
}
