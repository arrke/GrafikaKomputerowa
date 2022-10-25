import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class P3 extends P {
    int scale = 0;
    BufferedImage image;

    public P3(int x, int y, int scale) {
        setBorder(new LineBorder(Color.BLACK, 1));
        image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        this.scale = scale;
        setSize(x, y);
        JLabel label = new JLabel("", new ImageIcon(image), 0);
    }

    @Override
    public void paint(int x, int y, int color) {

    }

    public void paint(int x, int y, int red, int greeen, int blue) {
        image.setRGB(x, y, new Color(255*red/scale,255*greeen/scale,255*blue/scale).getRGB());
    }

    public void displayImage(Graphics g){
        g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
    }

    @Override
    public void paintComponent(Graphics g){
        this.displayImage(g);
    }

}
