package unoeste.fipp.springplaymysongs.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unoeste.fipp.springplaymysongs.entities.Music;
import unoeste.fipp.springplaymysongs.entities.Style;
import unoeste.fipp.springplaymysongs.services.MusicService;
import unoeste.fipp.springplaymysongs.services.StyleService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("apis")
public class MusicRestController {
    @Autowired
    private MusicService musicService;
    @Autowired
    private StyleService styleService;
    @PostMapping("/music-upload")
    public ResponseEntity<Object> uploadMusic(
            @RequestParam("file") MultipartFile file,
            @RequestParam("titulo") String titulo,
            @RequestParam("artista") String artista,
            @RequestParam("estilo") String estilo)
    {

        try {
            Music music = musicService.salvarMusica(file, titulo, artista, estilo);
            return ResponseEntity.ok(music);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-musics-by-keyword")
    public ResponseEntity<List<Music>> buscarMusicas(@RequestParam("termo") String termo) {
        try {
            List<Music> lista = musicService.pesquisarMusicas(termo);
            return ResponseEntity.ok(lista);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get-all-styles")
    public ResponseEntity<List<Style>> buscarTodosEstilos() {
        try {
            // Supondo que você tenha o musicService.buscarEstilos() ou use o styleRepository direto
            List<Style> estilos = styleService.listarTodos();
            return ResponseEntity.ok(estilos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
