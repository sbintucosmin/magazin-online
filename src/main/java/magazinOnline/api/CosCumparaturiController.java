package magazinOnline.api;


import magazinOnline.domain.CosCumparaturi;
import magazinOnline.dto.cosCumparaturi.CosCumparaturiDTOAdaugaProdus;
import magazinOnline.dto.cosCumparaturi.CosCumparaturiDTOSchimbaCantitatea;
import magazinOnline.repository.ClientRepository;
import magazinOnline.repository.CosCumparaturiRepository;
import magazinOnline.repository.ProdusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cos-cumparaturi")
public class CosCumparaturiController {
    CosCumparaturiRepository cosCumparaturiRepository;
    ProdusRepository produsRepository;
    ClientRepository clientRepository;

    public CosCumparaturiController(CosCumparaturiRepository cosCumparaturiRepository,
                                    ProdusRepository produsRepository, ClientRepository clientRepository) {
        this.cosCumparaturiRepository = cosCumparaturiRepository;
        this.produsRepository = produsRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/lista-produse")
    public List<CosCumparaturi> afiseazaListaCosuriCumparaturi() {
        return cosCumparaturiRepository.findAll();
    }

    @PostMapping("/adauga-produs")
    CosCumparaturi adaugaProdus(@RequestBody CosCumparaturiDTOAdaugaProdus produsNou,
                                @RequestParam String numeProdus,
                                @RequestParam String numeClient) {
        CosCumparaturi produsDeAdaugat = new CosCumparaturi();

        if (cosCumparaturiRepository.findByNumeProdus(numeProdus) != null) {
            throw new RuntimeException("Produsul este deja in cos, modificati cantitatea");
        } else {
            if (produsRepository.findByNumeProdus(numeProdus) == null) {
                throw new RuntimeException("Produsul nu exista in inventar");
            } else {
                produsDeAdaugat.setNumeProdus(numeProdus);
            }
            if (produsNou.getCantitate() < 1) {
                throw new RuntimeException("Trebuie sa adaugi cel putin un produs in cos");
            } else {
                produsDeAdaugat.setCantitate(produsNou.getCantitate());
            }
            if (clientRepository.findByNume(numeClient) == null) {
                throw new RuntimeException("Clientul nu exista in baza de date!");
            } else {
                produsDeAdaugat.setNumeClient(numeClient);
            }
        }
        return cosCumparaturiRepository.save(produsDeAdaugat);
    }

    @PostMapping("/modifica-produs")
    CosCumparaturi schimbaCantitatea(@RequestBody CosCumparaturiDTOSchimbaCantitatea cantitateNoua,
                                     @RequestParam String numeProdus) {
        if(cosCumparaturiRepository.findByNumeProdus(numeProdus) == null) {
            throw new RuntimeException("Produsul nu exista in cos");
        }
        CosCumparaturi produsDeModificat = cosCumparaturiRepository.findByNumeProdus(numeProdus);
        if (cantitateNoua.getCantitate()<1) {
            throw new RuntimeException("Cantitatea minima pentru un produs este 1");
        } else {
            produsDeModificat.setCantitate(cantitateNoua.getCantitate());
        }

        return cosCumparaturiRepository.save(produsDeModificat);
    }

    @DeleteMapping("/sterge-produs/{numeProdus}")
    ResponseEntity<String> stergeProdus(@PathVariable String numeProdus) {
        if (cosCumparaturiRepository.findByNumeProdus(numeProdus) == null) {
            throw new RuntimeException("Produsul nu exista in cos");
        }
        cosCumparaturiRepository.delete(cosCumparaturiRepository.findByNumeProdus(numeProdus));
        return ResponseEntity.ok("Produsul a fost sters din cos!");
    }

    @GetMapping("/checkout/{numeClient}")
    ResponseEntity<String> finalizareComanda(@PathVariable String numeClient) {
        if (cosCumparaturiRepository.findAllByNumeClient(numeClient).isEmpty()) {
            throw new RuntimeException("Cosul/Clientul nu exista in baza de date");
        } else {
            String comanda = "Ati comandat: ";
            double totalPlata = 0;
            for (CosCumparaturi produs : cosCumparaturiRepository.findAllByNumeClient(numeClient)) {
                comanda = comanda + produs.getCantitate() + "x " + produs.getNumeProdus() + "; ";
                totalPlata =
                        totalPlata + produs.getCantitate() * produsRepository.findByNumeProdus(produs.getNumeProdus()).getPret();
            }
            return ResponseEntity.ok(comanda + "\nTotal de plata: " + totalPlata + "Lei");
        }
    }
}
