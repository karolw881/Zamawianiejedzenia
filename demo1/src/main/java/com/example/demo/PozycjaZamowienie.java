package com.example.demo;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class PozycjaZamowienie {
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
    @Column(name = "id_zamowienie")
    private Integer id_zamowienie;

    public PozycjaZamowienie(Integer id, String sposob_platnosci, String zamawiajacy, String status, Double cena, String opis, Integer id_zamowienie) {
        this.id = id;
        this.sposob_platnosci = sposob_platnosci;
        this.zamawiajacy = zamawiajacy;
        this.status = status;
        this.cena = cena;
        this.opis = opis;
        this.id_zamowienie = id_zamowienie;
    }




}





