package unoeste.fipp.springplaymysongs.repository;

import com.google.gson.Gson;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import unoeste.fipp.springplaymysongs.entities.Style;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class StyleRepository
{
    public List<Style> findAll() {
        List<Style> styleList = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017"))
        {
            MongoDatabase database = mongoClient.getDatabase("my_musics");
            MongoCollection<Document> collection = database.getCollection("styles");

            MongoCursor<Document> mongoCursor = collection.find().sort(eq("nome", 1L)).iterator();
            while(mongoCursor.hasNext()){
                styleList.add(new Gson().fromJson(mongoCursor.next().toJson(), Style.class));
            }
        }
        return styleList;
    }
}
