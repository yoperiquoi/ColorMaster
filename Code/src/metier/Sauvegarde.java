package metier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fenetre.commande.ICommande;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class Sauvegarde {

    public static void sauvegarder(String file, Stack<ICommande> historique) {
        if (file != null) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.setPrettyPrinting().create();
            String undo = gson.toJson(historique);
            try (FileWriter writer = new FileWriter(file);
                 BufferedWriter bw = new BufferedWriter(writer)) {
                bw.write(undo);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }
}
