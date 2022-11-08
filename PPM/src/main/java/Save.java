import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Save {
    private String fileName;
    private P panel;
    private String ext;

    public Save(String fileName, P panel, String ext) {
        this.fileName = fileName;
        this.panel = panel;
        this.ext = ext;
    }

    public void action(int ascii) throws IOException {
        String format = this.resolveFormat(ascii == 0 ? "ascii" : "raw");
        FileReader instance = panel.getInstance();
        int[][][] newPixels = null;
        switch(format){
            case "P4":
            case "P1":{
                if(instance.format == "P1" || instance.format == "P4"){
                    newPixels = instance.pixels;
                    break;
                }
                newPixels = new int[instance.x][instance.y][1];
                int[][][] pixels = instance.pixels;
                for(int i = 0; i < instance.x; i++){
                    for(int j = 0; j < instance.y ; j++){
                        for(int elem = 0; elem < pixels[i][j].length; elem++){
                            if(pixels[i][j][elem] > 0){
                                newPixels[i][j][0] = 1;
                                continue;
                            }
                            newPixels[i][j][0] = 0;
                        }
                    }
                }
                break;
            }
            case "P5":
            case "P2":{
                if(instance.format == "P2" || instance.format == "P5"){
                    newPixels = instance.pixels;
                    break;
                }
                newPixels = new int[instance.x][instance.y][1];
                int[][][] pixels = instance.pixels;
                for(int i = 0; i < instance.x; i++){
                    for(int j = 0; j < instance.y ; j++){
                        for(int elem = 0; elem < pixels[i][j].length; elem++){
                            if(pixels[i][j][elem] > 0){
                                newPixels[i][j][0] = pixels[i][j][elem];
                                continue;
                            }
                            newPixels[i][j][0] = 0;
                        }
                    }
                }
                break;
            }
            case "P6":
            case "P3":{
                newPixels = new int[instance.x][instance.y][3];
                if(instance.format == "P3" || instance.format == "P6"){
                    newPixels = instance.pixels;
                    break;
                }
                int[][][] pixels = instance.pixels;
                for(int i = 0; i < instance.y; i++){
                    for(int j = 0; j < instance.x ; j++){
                        if(pixels[i][j].length < 3){
                            if(instance.format == "P1" || instance.format == "P4"){
                                if(pixels[i][j][0] == 1){
                                    newPixels[i][j][0] = 0;
                                    newPixels[i][j][1] = 0;
                                    newPixels[i][j][2] = 0;
                                }
                                else{
                                    newPixels[i][j][0] = 255;
                                    newPixels[i][j][1] = 255;
                                    newPixels[i][j][2] = 255;
                                }
                            }
                            if(instance.format == "P2" || instance.format == "P5"){
                                if(pixels[i][j][0] > 0){
                                    newPixels[i][j][0] = 255*pixels[i][j][0]/instance.scale;
                                    newPixels[i][j][1] = 255*pixels[i][j][0]/instance.scale;
                                    newPixels[i][j][2] = 255*pixels[i][j][0]/instance.scale;
                                }
                                else{
                                    newPixels[i][j][0] = 255;
                                    newPixels[i][j][1] = 255;
                                    newPixels[i][j][2] = 255;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        String content = format == "P4" ||
                         format == "P5" ||
                         format == "P6" ? "\n" :
                Arrays.deepToString(newPixels)
                .replace(",","")
                .replace("[","")
                .replace("]","\n");

        String scale = format == "P1" ? "\n" : Integer.toString(instance.scale);
        List<String> lines = Arrays.asList(format, instance.x + " " + instance.y,scale, content);
        Path file = Paths.get(fileName);
        Files.write(file, lines, StandardCharsets.UTF_8);
        if(content == "\n"){
            FileOutputStream fos = new FileOutputStream(new File(fileName), true);
            DataOutputStream bos = new DataOutputStream(fos);
            char[] x;
            char[][][] binaryPixels = new char[instance.y][instance.x][newPixels[0][0].length];
            for(int i = 0; i < instance.y; i++){
                for(int j = 0; j < instance.x ; j++){
                    for(int z = 0; z < newPixels[0][0].length; z++){
                        fos.write((char) newPixels[j][i][z]);
                        fos.flush();
                    }
                }
            }
            fos.flush();
            fos.close();
        }
    }


    private String resolveFormat(String type){
        String format = null;
        switch(ext){
            case "pbm":{
                format = type == "ascii" ? "P1" : "P4";
                break;
            }
            case "pgm":{
                format = type == "ascii" ? "P2" : "P5";
                break;
            }
            case "ppm":{
                format = type == "ascii" ? "P3" : "P6";
                break;
            }
        }
        return format;
    }
}
