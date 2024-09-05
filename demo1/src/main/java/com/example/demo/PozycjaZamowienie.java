package com.example.demo;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table( name = "pozycja_zamowienie")
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


    @ManyToOne
    @JoinColumn(name = "id_zamowienia")
    private Zamowienie zamowienie;




}





