import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class P2 extends P {
    int scale = 0;
    BufferedImage image;

    public P2(int x, int y,int scale) {
        image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        this.scale = scale;
        setSize(x, y);
        JLabel label = new JLabel("", new ImageIcon(image), 0);
    }

    public void paint(int x, int y, int color) {
        image.setRGB(x, y, new Color(255*color/scale,255*color/scale,255*color/scale).getRGB());
    }

    @Override
    public void paint(int x, int y, int red, int greeen, int blue) {

    }

    public void displayImage(Graphics g){
        g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
    }
}
