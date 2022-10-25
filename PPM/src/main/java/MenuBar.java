import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MenuBar extends JMenuBar {
    Frame frame;
    MenuBar that;
    String file;
    String saveFile;

    final String[] EXTENSIONS = {"pgm", "pbm", "ppm"};
    public MenuBar(Frame frame){
        this.frame = frame;
        this.that = this;
        var menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "Open");
        this.add(menu);
        var openMenuItem = new JMenuItem("Open",
                KeyEvent.VK_T);
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        openMenuItem.getAccessibleContext().setAccessibleDescription(
                "Open");
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(that);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    that.file = selectedFile.getAbsolutePath();
                    try {
                        that.loadFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        menu.add(openMenuItem);

        var saveMenuItem = new JMenuItem("Save",
                KeyEvent.VK_T);
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        saveMenuItem.getAccessibleContext().setAccessibleDescription(
                "Save");
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showSaveDialog(that);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    that.saveFile = selectedFile.getAbsolutePath();
                    String[] splitedFileName = selectedFile.getName().split("\\.");
                    if(that.frame.getPanel() != null){
                        if(splitedFileName.length == 2 && splitedFileName[0] != "" && isProperExt(splitedFileName[1])){
                            Object[] options = {"ASCII",
                                    "RAW"};
                            int n = JOptionPane.showOptionDialog(frame,
                                    "Would you like green eggs and ham?",
                                    "A Silly Question",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,     //do not use a custom Icon
                                    options,  //the titles of buttons
                                    options[0]);
                            try {
                                new Save(selectedFile.getAbsolutePath(), that.frame.getPanel(),splitedFileName[1]).action(n);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            } catch (UnsupportedEncodingException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(new JFrame(), "Bad file name or extension", "Dialog",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(new JFrame(), "Content is empty", "Dialog",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
        menu.add(saveMenuItem);
    }

    private void loadFile() throws IOException {
        this.frame.setFileName(this.file);
        this.frame.loadFile();
    }


    private boolean isProperExt(String ext){
        return Arrays.stream(EXTENSIONS).anyMatch(ext::equals);
    }

    private void saveAscii(String filename){

    }

    private void saveRaw(String filename){

    }
}
