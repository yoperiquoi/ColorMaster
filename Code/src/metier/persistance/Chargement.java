package metier.persistance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fenetre.commande.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

/**
 * Classe définissant le chargement d'un fichier
 */
public class Chargement {

    /**
     * Méthode permettant de charger un fichier dans un historique
     * @param file fichier à charger
     * @return la pile de commande
     * @throws IllegalArgumentException en cas de fichier invalide
     */
    public static Stack<ICommande> charger(String file) throws IllegalArgumentException {
        if (file != null) {
            try {
                //On instancie un deserializer et on enregistre toute les forme à récupérer
                DessinerDeserializer deserializer = new DessinerDeserializer("type");
                deserializer.registerShapeType("Carre", DessinerCarre.class);
                deserializer.registerShapeType("Cercle", DessinerCercle.class);
                deserializer.registerShapeType("Dessin", DessinerDessin.class);
                deserializer.registerShapeType("Effacement", DessinerEffacement.class);
                deserializer.registerShapeType("Ellipse", DessinerEllipse.class);
                deserializer.registerShapeType("Ligne", DessinerLigne.class);
                deserializer.registerShapeType("Rectangle", DessinerRectangle.class);
                deserializer.registerShapeType("Text", DessinerText.class);

                //On instancie ensuite un objet Gson avec notre adapter
                Gson gson = new GsonBuilder().registerTypeAdapter(ICommande.class, deserializer).create();


                if (Files.exists(Paths.get(file))) {
                    return gson.fromJson(new FileReader(file), new TypeToken<Stack<ICommande>>() {
                    }.getType());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}