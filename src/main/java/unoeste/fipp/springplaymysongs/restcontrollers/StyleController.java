package unoeste.fipp.springplaymysongs.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unoeste.fipp.springplaymysongs.entities.Style;
import unoeste.fipp.springplaymysongs.services.StyleService;

import java.util.List;

@RestController
@RequestMapping("api")
public class StyleController {
    @Autowired
    private StyleService service;

    @GetMapping("/get-music-styles")
    public ResponseEntity<List<Style>> getMusicStyles() {
        List<Style> estilos = service.listarTodos();
        return ResponseEntity.ok(estilos); //retorna 200 OK com a lista JSON
    }
}
