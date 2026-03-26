package unoeste.fipp.springplaymysongs.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import unoeste.fipp.springplaymysongs.entities.Music;

@Repository
public class MusicRepository {
    public void salvar(Music musica) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017"))
        {
            MongoDatabase db = mongoClient.getDatabase("my_musics");
            MongoCollection<Document> collection = db.getCollection("musics");

            Document doc = new Document("titulo", musica.getTitulo())
                    .append("estilo", musica.getEstilo())
                    .append("artista", musica.getArtista())
                    .append("musicFileName", musica.getMusicFileName());

            collection.insertOne(doc);
        }
    }
}
