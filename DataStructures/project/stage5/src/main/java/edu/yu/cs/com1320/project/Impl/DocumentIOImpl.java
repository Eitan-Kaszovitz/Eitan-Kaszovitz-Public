package edu.yu.cs.com1320.project.Impl;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import edu.yu.cs.com1320.project.Document;
import edu.yu.cs.com1320.project.DocumentIO;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class DocumentIOImpl extends DocumentIO {

    private HashMap<URI, File> fileMap;

    public DocumentIOImpl(File b)
    {
        super(b);
        this.fileMap = new HashMap<>(100);
    }

    public DocumentIOImpl()
    {
        super();
        this.fileMap = new HashMap<>(100);
    }

    @Override
    public File serialize(Document doc) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            DocSerializer serializer = new DocSerializer();
            gsonBuilder.registerTypeAdapter(DocumentImpl.class, serializer);
            Gson gson = gsonBuilder.create();
            if (this.baseDir == null) {
                String uriPath = doc.getKey().toString().replace("http://", "").replace("https://", "");
                String jsonString = gson.toJson(doc);
                File jsonFile = new File(System.getProperty("user.dir") + "/" + uriPath + ".json");
                jsonFile.createNewFile();
                FileWriter fileWriter = new FileWriter(jsonFile);
                fileWriter.write(jsonString);
                fileWriter.close();
                this.fileMap.put(doc.getKey(), jsonFile);
                return jsonFile;
            } else {
                String uriPath = doc.getKey().toString().replace("http://", "").replace("https://", "");
                String jsonString = gson.toJson(doc);
                File jsonFile = new File(this.baseDir.getAbsolutePath() + "/" + uriPath + ".json");
                FileWriter fileWriter = new FileWriter(jsonFile);
                fileWriter.write(jsonString);
                fileWriter.close();
                this.fileMap.put(doc.getKey(), jsonFile);
                return jsonFile;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void setFileMap (URI uri, File file) {
        this.fileMap.put(uri, file);
    }

    @Override
    public Document deserialize(URI uri) {
        File file = this.fileMap.get(uri);
        String json = null;
        try {
            json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        DocDeserializer deserializer = new DocDeserializer();
        gsonBuilder.registerTypeAdapter(DocumentImpl.class, deserializer);
        Type docType = new TypeToken<DocumentImpl>() {
        }.getType();

        Gson gson = gsonBuilder.create();
        DocumentImpl doc = gson.fromJson(json, docType);
        file.delete();

        return doc;
    }

}
