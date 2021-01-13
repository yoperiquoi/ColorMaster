package fenetre;

import com.google.gson.*;
import fenetre.commande.ICommande;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DessinerDeserializer implements JsonDeserializer<ICommande> {
    private final String formeTypeElementName;
    private final Gson gson;
    private final Map<String,Class<? extends ICommande>> formeTypeRegistery;

    public DessinerDeserializer(String formeTypeElementName){
        this.formeTypeElementName= formeTypeElementName;
        this.gson = new Gson();
        this.formeTypeRegistery= new HashMap<>();
    }

    public void registerShapeType(String formeTypeName, Class<? extends ICommande> formeType){
        formeTypeRegistery.put(formeTypeName,formeType);
    }

    @Override
    public ICommande deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject formeObject = json.getAsJsonObject();
        JsonElement formeTypeElement= formeObject.get(formeTypeElementName);

        Class<? extends ICommande> formeType = formeTypeRegistery.get(formeTypeElement.getAsString());
        return gson.fromJson(formeObject,formeType);
    }
}
