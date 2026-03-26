package unoeste.fipp.springplaymysongs.services;

import com.google.gson.Gson;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unoeste.fipp.springplaymysongs.entities.Music;
import unoeste.fipp.springplaymysongs.entities.Style;
import unoeste.fipp.springplaymysongs.repository.MusicRepository;
import unoeste.fipp.springplaymysongs.repository.StyleRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private StyleRepository styleRepository;

    public Music salvarMusica(MultipartFile file, String titulo, String artista, String estilo) throws Exception {
        //definir a pasta (O professor quer na área estática)
        final String UPLOAD_FOLDER = "src/main/resources/static/uploads/";

        //criar a pasta se não existir
        File pasta = new File(UPLOAD_FOLDER);
        if (!pasta.exists()) pasta.mkdirs();

        //pegar a extensão original (.mp3 ou .ogg)
        String nomeOriginal = file.getOriginalFilename();
        String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));

        //usar a função de limpar o nome
        String nomeLimpo = gerarNomeArquivo(titulo, estilo, artista, extensao);

        //salvar o arquivo físico no HD
        File arquivoDestino = new File(pasta.getAbsolutePath() + File.separator + nomeLimpo);
        file.transferTo(arquivoDestino);

        //criar o objeto Music para salvar no Banco
        Music novaMusica = new Music(titulo, estilo, artista);
        novaMusica.setMusicFileName(nomeLimpo); //guardamos o nome que geramos
        musicRepository.salvar(novaMusica);

        return novaMusica;
    }

    public List<Style> findMusicStyles() {
        return styleRepository.findAll();
    }

    public String gerarNomeArquivo(String titulo, String estilo, String artista, String extensao) {
        String nome = titulo + "_" + estilo + "_" + artista;

        return nome.toLowerCase()//tudo minúsculo
                .replace(" ", "") //tira espaços
                .replaceAll("[^a-z0-9_]", "") //tira acentos e símbolos (deixa só letras, números e _)
                + extensao; //coloca o .mp3 ou .ogg no final
    }
}