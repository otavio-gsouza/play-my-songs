package unoeste.fipp.springplaymysongs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.springplaymysongs.entities.Style;
import unoeste.fipp.springplaymysongs.repository.StyleRepository;

import java.util.List;

@Service
public class StyleService {

    @Autowired
    private StyleRepository styleRepository; // O Spring "conecta" o repository aqui

    public List<Style> listarTodos() {
        return styleRepository.findAll();
    }
}
