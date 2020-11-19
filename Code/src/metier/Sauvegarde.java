package metier;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Sauvegarde {

    static void sauvegarder(File fichier, Canvas canvas){
        if(fichier !=null){
            try {
                WritableImage writableImage= new WritableImage(1080,720);
                canvas.snapshot(null,writableImage);
                RenderedImage renderedImage= SwingFXUtils.fromFXImage(writableImage,null);
                ImageIO.write(renderedImage,"png",fichier);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error !");
            }
        }else{
            throw new IllegalArgumentException("Le fichier a sauvegarder n'est pas valide");
        }
    }
}
