import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by lgm on 16/7/17.
 */
public class ClassJsonAdapter implements JsonSerializer<Class>, JsonDeserializer<Class> {

    @Override
    public Class deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return Class.forName(jsonElement.getAsString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonElement serialize(Class aClass, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(aClass.getName());
    }
}