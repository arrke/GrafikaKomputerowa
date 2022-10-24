import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FileReader {
    String format = "";
    int x = 0, y = 0;
    int scale = -1;
    int[][][] pixels;

    public synchronized InputStream getFileAsIOStream(final String fileName) {
        InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    public synchronized void printBinaryFileContent(FileInputStream is) throws IOException {
        try (DataInputStream br = new DataInputStream(is)) {
            String line;
            this.clear();
            final int[] iterationX = {0};
            final int[] iterationY = {0};
            final int[] iterationZ = {0};

            while ((line = br.readLine()) != null) {
                // removing comments and whitespaces
                Stream<String> values = removeComments(line);

                values.forEach(value -> {
                    if (scale == -1) {
                        if (format == "") {
                            setFormat(value);
                        } else if (x == 0) {
                            x = Integer.parseInt(value);
                        } else if (y == 0) {
                            y = Integer.parseInt(value);
                            switch (format) {
                                case "P4", "P5" -> {
                                    if (pixels == null) {
                                        pixels = new int[x][y][1];
                                    }
                                    break;
                                }
                                case "P6" -> {
                                    if (pixels == null) {
                                        pixels = new int[x][y][3];
                                    }
                                    break;
                                }
                                default -> {

                                }
                            }
                        } else if (scale == -1) {
                            scale = Integer.parseInt(value);
                        }
                    }
                });
                if (scale != -1 || (format == "P4" && y != 0)) {
                    break;
                }
            }

            byte[] bytes = !Objects.equals(format, "P4") ? new byte[x * y] : new byte[x * y / 8];
            while (br.read(bytes) != -1) {
                for (byte pixel : bytes) {
                    switch (format) {
                        case "P4" -> {
                            if (pixels == null) {
                                pixels = new int[x][y][1];
                            } else {
                                for (int i = 0; i <= 7; i++) {

                                    pixels[iterationX[0]][iterationY[0]][0] = (pixel >= 0 ? (pixel >= Math.pow(2, 7 - i) ? 1 : 0) : ((256 + pixel) >= Math.pow(2, 7 - i) ? 1 : 0));

                                    pixel = (byte) (pixel >= 0 ? (
                                            pixel >= Math.pow(2, 7 - i) ? (pixel - Math.pow(2, 7 - i)) : pixel)
                                            : (256 + pixel) - Math.pow(2, 7 - i));
                                    if (iterationX[0] >= x - 1) {
                                        iterationX[0] = 0;
                                        iterationY[0]++;
                                    } else iterationX[0]++;
                                }
                            }
                            break;
                        }
                        case "P5" -> {
                            if (pixels == null) {
                                pixels = new int[x][y][1];
                            } else {
                                pixels[iterationX[0]][iterationY[0]][0] = pixel >= 0 ? pixel : 256 + pixel;
                                if (iterationX[0] >= x - 1) {
                                    iterationX[0] = 0;
                                    iterationY[0]++;
                                } else iterationX[0]++;
                            }
                            break;
                        }
                        case "P6" -> {
                            if (pixels == null) {
                                pixels = new int[x][y][3];
                                scale = pixel;
                            } else {
                                pixels[iterationX[0]][iterationY[0]][iterationZ[0]] = pixel >= 0 ? pixel : 256 + pixel;
                                if (iterationZ[0] >= 2) {
                                    iterationZ[0] = 0;
                                    iterationX[0]++;
                                } else iterationZ[0]++;
                                if (iterationX[0] >= x) {
                                    iterationX[0] = 0;
                                    iterationY[0]++;
                                }
                            }
                            break;
                        }
                        default -> {

                        }
                    }
                }
            }

//                char[] array = line.toCharArray();
//                for (int i = 0; i < array.length; i++){
//                    System.out.println((int)array[i]);
//                }
//                System.out.println(line.toCharArray());
        }
    }

    public void setParameters(InputStream is) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr);) {
            String line;

            while ((line = br.readLine()) != null &&
                    (scale == -1 || ((!format.equals("P4") || (!format.equals("P1"))) && pixels == null))) {
                Stream<String> values = removeComments(line);

                values.forEach(value -> {
                    if (format == "") {
                        setFormat(value);
                    } else if (x == 0) {
                        x = Integer.parseInt(value);
                    } else if (y == 0) {
                        y = Integer.parseInt(value);
                        switch (format){
                            case "P1","P2","P4","P5": {
                                pixels = new int[x][y][1];
                                break;
                            }
                            case "P3","P6":{
                                pixels = new int[x][y][3];
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    } else if (scale == -1){
                        scale = Integer.parseInt(value);
                    }
                });

            }
        }
    }

    private Stream<String> removeComments(String line) {
        if (line.contains(new StringBuffer("#"))) {
            line = line.substring(0, line.indexOf('#'));
        }

        Iterable<String> splicedLine = Splitter.on(CharMatcher.whitespace()).split(line);
        Stream<String> values = Stream.of(
                StreamSupport.stream(splicedLine.spliterator(), false).toArray(String[]::new)
        ).filter(s -> !s.isBlank());
        return values;
    }

    public synchronized void printFileContent(InputStream is) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr);) {
            String line;

            final int[] iterationX = {0};
            final int[] iterationY = {0};
            final int[] iterationZ = {0};
            this.clear();

            while ((line = br.readLine()) != null) {

                // removing comments and whitespaces
                Stream<String> values = removeComments(line);
                //
                values.forEach(value -> {
                    if (format == "") {
                        setFormat(value);
                    } else if (x == 0) {
                        x = Integer.parseInt(value);
                    } else if (y == 0) {
                        y = Integer.parseInt(value);
                    } else {

                        switch (format) {
                            case "P1": {
                                if (pixels == null) {
                                    pixels = new int[x][y][1];
                                } else {
                                    pixels[iterationX[0]][iterationY[0]][0] = Integer.parseInt(value);
                                    if (iterationX[0] >= x - 1) {
                                        iterationX[0] = 0;
                                        iterationY[0]++;
                                    } else iterationX[0]++;

                                }
                                break;
                            }
                            case "P2": {
                                {
                                    if (pixels == null) {
                                        pixels = new int[x][y][1];
                                        scale = Integer.parseInt(value);
                                    } else {
                                        pixels[iterationX[0]][iterationY[0]][0] = Integer.parseInt(value);
                                        if (iterationX[0] >= x - 1) {
                                            iterationX[0] = 0;
                                            iterationY[0]++;
                                        } else iterationX[0]++;
                                    }
                                }
                                break;
                            }
                            case "P3": {
                                if (pixels == null) {
                                    pixels = new int[x][y][3];
                                    scale = Integer.parseInt(value);
                                } else {
                                    pixels[iterationX[0]][iterationY[0]][iterationZ[0]] = Integer.parseInt(value);
                                    if (iterationZ[0] >= 2) {
                                        iterationZ[0] = 0;
                                        iterationX[0]++;
                                    } else iterationZ[0]++;
                                    if (iterationX[0] >= x) {
                                        iterationX[0] = 0;
                                        iterationY[0]++;
                                    }
                                }
                                break;
                            }
                            default: {
                            }
                        }

                    }
                });
            }
            is.close();
        }
    }

    private void clear() {
        this.format = "";
        this.x = 0;
        this.y = 0;
        this.scale = -1;
        this.pixels = null;
    }

    private void setFormat(String value) {
        switch (value) {
            case "P1": {
                format = "P1";
                break;
            }
            case "P2": {
                format = "P2";
                break;
            }
            case "P3": {
                format = "P3";
                break;
            }
            case "P4": {
                format = "P4";
                break;
            }
            case "P5": {
                format = "P5";
                break;
            }
            case "P6": {
                format = "P6";
                break;
            }
            default: {
                break;
            }
        }

    }
}
