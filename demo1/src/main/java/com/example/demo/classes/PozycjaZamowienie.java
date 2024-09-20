package com.example.demo.classes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "pozycja_zamowienie")
@Builder

public class PozycjaZamowienie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id_zamowienia")
    private Integer id;
    @Column(name = "sposob_platnosci" )

    private String sposob_platnosci;
    @Column(name = "zamawiajacy" )

    private String zamawiajacy;
    @Column(name = "status")

    private String status;
    @Column(name = "cena" )
    private Double cena;
    @Column(name = "opis" )

    private String opis;



    @ManyToOne
    @JoinColumn(name = "id_zamowienia")
    @JsonIgnore
    private Zamowienie zamowienie;

}





