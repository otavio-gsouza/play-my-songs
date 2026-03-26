package unoeste.fipp.springplaymysongs.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unoeste.fipp.springplaymysongs.entities.Music;
import unoeste.fipp.springplaymysongs.entities.Style;
import unoeste.fipp.springplaymysongs.services.MusicService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("apis")
public class MusicRestController {
    @Autowired
    private MusicService musicService;

    @PostMapping("/music-upload")
    public ResponseEntity<Object> uploadMusic(
            @RequestParam("file") MultipartFile file,
            @RequestParam("titulo") String titulo,
            @RequestParam("artista") String artista,
            @RequestParam("estilo") String estilo) {

        try {
            // O Controller apenas delega a função para o Service
            Music music = musicService.salvarMusica(file, titulo, artista, estilo);
            return ResponseEntity.ok(music);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    
}
