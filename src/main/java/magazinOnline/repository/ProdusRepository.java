package magazinOnline.repository;

import magazinOnline.domain.Produs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdusRepository extends JpaRepository<Produs, Integer> {
    List<Produs> findByCategorie(String categorie);

    Produs findByNumeProdus(String nume);


    @Query("""
            SELECT p FROM Produs p WHERE p.categorie = :categorie AND p.pret <= :pret
            """)
    List<Produs> filtreazaCategorieSiPret(String categorie, Double pret);

}
