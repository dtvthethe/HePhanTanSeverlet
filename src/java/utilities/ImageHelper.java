/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageHelper {

    public static void downloadImage(String sourceUrl, String pathSaveFile) throws Exception {
        URL url = new URL(sourceUrl);
        BufferedImage image = ImageIO.read(url);
        File file = new File(pathSaveFile.substring(1, pathSaveFile.length()));
//        file.setReadable(true, false);
//        file.setExecutable(true, false);
//        file.setWritable(true, false);
        ImageIO.write(image, "jpg", file);
    }

    public static void deleteImage(String pathSaveFile) throws Exception {
        File file = new File(pathSaveFile);
//        file.setReadable(true, false);
//        file.setExecutable(true, false);
//        file.setWritable(true, false);
        file.delete();
    }

}
