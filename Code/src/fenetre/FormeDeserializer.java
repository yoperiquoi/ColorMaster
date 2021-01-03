package fenetre;

import com.google.gson.*;
import metier.formes.Forme;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class FormeDeserializer implements JsonDeserializer<Forme> {
    private String formeTypeElementName;
    private Gson gson;
    private Map<String,Class<? extends Forme>> formeTypeRegistery;

    public FormeDeserializer(String formeTypeElementName){
        this.formeTypeElementName= formeTypeElementName;
        this.gson = new Gson();
        this.formeTypeRegistery= new HashMap<>();
    }

    public void registerShapeType(String formeTypeName, Class<? extends Forme> formeType){
        formeTypeRegistery.put(formeTypeName,formeType);
    }
    @Override
    public Forme deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject formeObject = json.getAsJsonObject();
        JsonElement formeTypeElement= formeObject.get(formeTypeElementName);

        Class<? extends Forme> formeType = formeTypeRegistery.get(formeTypeElement.getAsString());
        return gson.fromJson(formeObject,formeType);
    }
}
