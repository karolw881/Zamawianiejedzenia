package com.example.demo;



import jakarta.persistence.*;
import lombok.*;

@Value
@Builder
public class PozycjaZamowienieDTO {

    private Integer id;
    private String sposob_platnosci;
    private String zamawiajacy;
    private String status;
    private Double cena;
    private String opis;
    private Zamowienie zamowienie;




}





