package edu.yu.cs.com1320.project.Impl;

import com.google.gson.*;

import java.lang.reflect.Type;
import org.apache.commons.codec.binary.Base64;

public class DocSerializer implements JsonSerializer<DocumentImpl> {
    @Override
    public JsonElement serialize(DocumentImpl src, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonDocumentImpl = new JsonObject();
        jsonDocumentImpl.addProperty("compressedDoc", Base64.encodeBase64String(src.getDocument()));
        jsonDocumentImpl.addProperty("compressionFormat", src.getCompressionFormat().toString());
        jsonDocumentImpl.addProperty("key", src.getKey().toString());
        jsonDocumentImpl.addProperty("docHashcode", src.getDocumentHashCode());
        return jsonDocumentImpl;
    }
}
