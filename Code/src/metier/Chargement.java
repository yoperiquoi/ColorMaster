package metier;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Chargement {

    static void charger(File fichier) throws IllegalArgumentException{
        if (fichier!=null){
            try {
                InputStream io = new FileInputStream(fichier);
                Image image=new Image(io);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error!");
            }
        }else{
            throw new IllegalArgumentException("Le fichier sélectionné ne correspond pas à une image");
        }
    }
}
