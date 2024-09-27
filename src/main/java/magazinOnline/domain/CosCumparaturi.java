package magazinOnline.domain;

import jakarta.persistence.*;

@Entity
@Table
public class CosCumparaturi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    int id;
    String numeProdus;
    int cantitate;
    String numeClient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }
}
