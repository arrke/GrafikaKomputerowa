import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Frame extends JFrame {
    private String fileName;
    private P panel;
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Frame() {
        super("Hello World");

        setSize(new Dimension(1000, 1000));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    public void loadFile() throws IOException {
        long start = System.currentTimeMillis();

        FileReader instance = new FileReader();
        InputStream is = instance.getFileAsIOStream(fileName);
        instance.setParameters(is);

        switch(instance.getFormat()){
            case "P1": {
                is = instance.getFileAsIOStream(fileName);
                instance.printFileContent(is);
                this.panel = new P1(instance.x, instance.y);
                break;
            }
            case "P2": {
                is = instance.getFileAsIOStream(fileName);
                instance.printFileContent(is);
                this.panel = new P2(instance.x, instance.y, instance.scale);
                break;
            }
            case "P3": {
                is = instance.getFileAsIOStream(fileName);
                instance.printFileContent(is);
                this.panel = new P3(instance.x, instance.y, instance.scale);
                break;
            }
            case "P4": {
                instance.printBinaryFileContent(new FileInputStream(fileName));
                this.panel = new P1(instance.x, instance.y);
                break;
            }
            case "P5": {
                instance.printBinaryFileContent(new FileInputStream(fileName));
                this.panel = new P2(instance.x, instance.y, instance.scale);
                break;
            }
            case "P6": {
                instance.printBinaryFileContent(new FileInputStream(fileName));
                this.panel = new P3(instance.x, instance.y, instance.scale);
                break;
            }
            default: {
                break;
            }
        }
        this.panel.setInstance(instance);
        this.setContentPane(this.panel);
        switch(instance.getFormat()){
            case "P3":
            case "P6": {
                for (int i = 0; i < instance.x; i++) {
                    for (int j = 0; j < instance.y; j++) {
                        this.panel.paint(i, j, instance.pixels[i][j][0], instance.pixels[i][j][1], instance.pixels[i][j][2]);
                    }
                }
                break;
            }
            case "P1":
            case "P2":
            case "P4":
            case "P5":{
                for (int i = 0; i <instance.x;i++) {
                    for (int j = 0; j< instance.y;j++){
                        this.panel.paint(i,j,instance.pixels[i][j][0]);
                    }
                }
                break;
            }
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + "ms");
        this.revalidate();
        this.repaint();
    }

    public P getPanel() {
        return panel;
    }

    public void setPanel(P panel) {
        this.panel = panel;
    }
}
