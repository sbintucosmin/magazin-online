package magazinOnline.domain;

import jakarta.persistence.*;

@Entity
@Table
public class Produs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    int id;
    String numeProdus;
    double pret;
    String categorie;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
