package unoeste.fipp.springplaymysongs.repository;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import unoeste.fipp.springplaymysongs.entities.Music;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MusicRepository {

    public List<Music> buscarPorTermo(String termo) {
        List<Music> lista = new ArrayList<Music>();
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("my_musics");
            MongoCollection<Document> collection = db.getCollection("musics");

            //criamos um filtro "OU": ou o título contém o termo, ou o artista contém
            Bson filtro = Filters.or(
                    Filters.regex("titulo", termo, "i"),
                    Filters.regex("artista", termo, "i")
            );

            MongoCursor<Document> cursor = collection.find(filtro).iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Music m = new Music();
                m.setTitulo(doc.getString("titulo"));
                m.setArtista(doc.getString("artista"));
                m.setEstilo(doc.getString("estilo"));
                m.setMusicFileName(doc.getString("musicFileName"));
                lista.add(m);
            }
        }
        return lista;
    }

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
