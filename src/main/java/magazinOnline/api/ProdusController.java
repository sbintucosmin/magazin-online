package magazinOnline.api;

import magazinOnline.domain.Produs;
import magazinOnline.dto.produs.ProdusDTOModificaProdus;
import magazinOnline.dto.produs.ProdusDTOAdaugaProdus;
import magazinOnline.repository.ProdusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/produse")
public class ProdusController {
    ProdusRepository produsRepository;

    public ProdusController(ProdusRepository produsRepository) {
        this.produsRepository = produsRepository;
    }

    @GetMapping("/toate-produsele")
    public List<Produs> afiseazaToateProdusele() {
        return produsRepository.findAll();
    }

    @GetMapping("/{param}")
    public List<Produs> afiseazaCategorie(@PathVariable String param) {
        try{
            int id = Integer.parseInt(param);
            if (produsRepository.existsById(id)) {
                return Collections.singletonList(produsRepository.findById(id).get());
            } else {
                throw new RuntimeException("Produsul cu ID-ul " + param + " nu exista");
            }
        } catch (NumberFormatException e) {
            if (!produsRepository.findByCategorie(param).isEmpty()) {
            return produsRepository.findByCategorie(param);
        } else {
            throw new RuntimeException("Categoria " + param + " nu exista");
        }
        }
    }

    @PostMapping("/adauga-produs")
    Produs adaugaProdus(@RequestBody ProdusDTOAdaugaProdus produsNou) {
        Produs produsDeAdaugat = new Produs();

        produsDeAdaugat.setNumeProdus(produsNou.getNumeProdus());
        produsDeAdaugat.setCategorie(produsNou.getCategorie());
        produsDeAdaugat.setPret(produsNou.getPret());
        return produsRepository.save(produsDeAdaugat);
    }


    @PostMapping("/modifica/{id}")
    Produs modificaProdus(@PathVariable Integer id, @RequestBody ProdusDTOModificaProdus produsModificat) {
        Produs produsDeModificat = produsRepository.findById(id).orElseThrow(()-> new RuntimeException("Produsul " +
                "nu exista"));

        produsDeModificat.setNumeProdus(produsModificat.getNumeProdus());
        produsDeModificat.setPret(produsModificat.getPret());
        return produsRepository.save(produsDeModificat);
    }

    @DeleteMapping("/sterge/{id}")
    ResponseEntity<String> stergeProdus(@PathVariable Integer id) {
        Produs produsDeSters = produsRepository.findById(id).orElseThrow(()-> new RuntimeException("Produsul nu se " +
                "afla in inventar"));
        produsRepository.delete(produsDeSters);
        return ResponseEntity.ok("Produsul a fost sters!");
    }

    @GetMapping("/filtreaza-produse")
    public ResponseEntity<List<Produs>> filtruProduse(
            @RequestParam String categorie, @RequestParam Double pret) {
        List<Produs> produse = produsRepository.filtreazaCategorieSiPret(categorie, pret);
        return ResponseEntity.ok(produse);
    }

}
