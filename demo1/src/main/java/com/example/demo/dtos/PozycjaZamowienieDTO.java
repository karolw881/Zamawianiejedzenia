package com.example.demo.dtos;



import com.example.demo.classes.PozycjaZamowienie;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PozycjaZamowienieDTO {

    private Integer id;
    private String sposob_platnosci;
    private String zamawiajacy;
    private String status;
    private Double cena;
    private String opis;
    private Long id_zamowienia;



    public PozycjaZamowienie toEntity(){
        return PozycjaZamowienie.builder()
                .id(this.id)
                .sposob_platnosci(this.sposob_platnosci)
                .zamawiajacy(this.zamawiajacy)
                .status(this.status)
                .cena(this.cena)
                .opis(this.opis)
               // .zamowienie(this.zamowienie)
                .build();
    }




}





