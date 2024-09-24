package com.example.demo.dtos;


import com.example.demo.DoubleDeserializer;
import com.example.demo.classes.PozycjaZamowienie;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;


import java.util.UUID;

@Value
@Builder
@Jacksonized
@Setter
public class PozycjaZamowienieDTO {

    private Integer id;
    private String sposob_platnosci;
    @NotBlank(message = "Zamawiajacy nie może być pusty")
    private String zamawiajacy;
    private String status;
    @NotNull(message = "Blad w cenie")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cena musi być liczbą dodatnią")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double cena;
    @NotBlank(message = "Opis nie może być pusta")
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





