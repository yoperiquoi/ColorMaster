package metier.persistance;

import com.google.gson.*;
import fenetre.commande.ICommande;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe permettant la deserialization du Json
 */
public class DessinerDeserializer implements JsonDeserializer<ICommande> {
    /**
     * Propriété responsable de définir le type à deserialization
     */
    private final String formeTypeElementName;

    /**
     * Instanciation gson réalisant la deserialization
     */
    private final Gson gson;

    /**
     * Registre contenant le nom de la classe en clé et la classe correspondant en valeur
     */
    private final Map<String,Class<? extends ICommande>> formeTypeRegistery;

    /**
     * Constructeur definissant le type qui devra être deserialized
     * @param formeTypeElementName type deserialized
     */
    public DessinerDeserializer(String formeTypeElementName){
        this.formeTypeElementName= formeTypeElementName;
        this.gson = new Gson();
        this.formeTypeRegistery=new HashMap<>();
    }

    /**
     * Méthode permettant d'enregistrer une forme à deserialized
     * @param formeTypeName Le nom de la forme
     * @param formeType le type de la forme
     */
    public void registerShapeType(String formeTypeName, Class<? extends ICommande> formeType){
        formeTypeRegistery.put(formeTypeName,formeType);
    }

    /**
     * Méthode assurant la deserialization
     * @param json objet Json permettant la deserialization
     * @param type type à deserialized
     * @param jsonDeserializationContext contexte de deserialisation
     * @return une commande
     * @throws JsonParseException si il y a un problème lors de la lecture du Json
     */
    @Override
    public ICommande deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject formeObject = json.getAsJsonObject();
        JsonElement formeTypeElement= formeObject.get(formeTypeElementName);

        Class<? extends ICommande> formeType = formeTypeRegistery.get(formeTypeElement.getAsString());
        return gson.fromJson(formeObject,formeType);
    }
}
