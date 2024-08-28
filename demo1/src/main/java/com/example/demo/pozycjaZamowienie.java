package com.example.demo;



import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class pozycjaZamowienie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column(name = "sposob_platnosci")
    private String sposob_platnosci;
    @Column(name = "zamawiajacy")
    private String zamawiajacy;
    @Column(name = "status")
    private String status;
    @Column(name = "cena")
    private Double cena;
    @Column(name = "opis")
    private String opis;

    public pozycjaZamowienie(Integer id, String sposob_platnosci, String zamawiajacy, String status, Double cena, String opis) {
        this.id = id;
        this.sposob_platnosci = sposob_platnosci;
        this.zamawiajacy = zamawiajacy;
        this.status = status;
        this.cena = cena;
        this.opis = opis;
    }

    public pozycjaZamowienie() {

    }


}





