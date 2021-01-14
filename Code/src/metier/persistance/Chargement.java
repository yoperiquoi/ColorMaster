package metier.persistance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fenetre.DessinerDeserializer;
import fenetre.commande.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

public class Chargement {

    public static Stack<ICommande> charger(String file) throws IllegalArgumentException {
        if (file != null) {
            try {

                DessinerDeserializer deserializer = new DessinerDeserializer("type");
                deserializer.registerShapeType("Carre", DessinerCarre.class);
                deserializer.registerShapeType("Cercle", DessinerCercle.class);
                deserializer.registerShapeType("Dessin", DessinerDessin.class);
                deserializer.registerShapeType("Effacement", DessinerEffacement.class);
                deserializer.registerShapeType("Ellipse", DessinerEllipse.class);
                deserializer.registerShapeType("Ligne", DessinerLigne.class);
                deserializer.registerShapeType("Rectangle", DessinerRectangle.class);
                deserializer.registerShapeType("Text", DessinerText.class);

                Gson gson = new GsonBuilder().registerTypeAdapter(ICommande.class, deserializer).create();


                if (Files.exists(Paths.get(file))) {
                    Stack<ICommande> undo = gson.fromJson(new FileReader(file), new TypeToken<Stack<ICommande>>() {
                    }.getType());
                    return undo;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}