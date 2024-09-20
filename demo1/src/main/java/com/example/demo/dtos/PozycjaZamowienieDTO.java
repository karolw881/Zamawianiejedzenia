package com.example.demo.dtos;



import com.example.demo.classes.PozycjaZamowienie;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
@Setter
public class PozycjaZamowienieDTO {

    private Integer id;
    private String sposob_platnosci;
    private String zamawiajacy;
    private String status;
    private Double cena;
    private String opis;
    private UUID id_zamowienia;



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

    public static PozycjaZamowienieDTO toDto(PozycjaZamowienie pozycjaZamowienie){
        return PozycjaZamowienieDTO.builder()
                .id(pozycjaZamowienie.getId())
                .sposob_platnosci(pozycjaZamowienie.getSposob_platnosci())
                .zamawiajacy(pozycjaZamowienie.getZamawiajacy())
                .status(pozycjaZamowienie.getStatus())
                .cena(pozycjaZamowienie.getCena())
                .opis(pozycjaZamowienie.getOpis())
                .id_zamowienia(pozycjaZamowienie.getZamowienie().getId())
                .status(pozycjaZamowienie.getStatus())

                .build();
    }




}





