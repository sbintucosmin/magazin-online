package magazinOnline.domain;

import jakarta.persistence.*;

@Entity
@Table
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    int id;
    String nume;
    String adresaLivrare;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresaLivrare() {
        return adresaLivrare;
    }

    public void setAdresaLivrare(String adresaLivrare) {
        this.adresaLivrare = adresaLivrare;
    }
}
