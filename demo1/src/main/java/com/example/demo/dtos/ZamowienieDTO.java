package com.example.demo.dtos;
import com.example.demo.classes.PozycjaZamowienie;
import com.example.demo.classes.Zamowienie;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


// rozbudowac klase op zamowienie ma wielepozycji
// task1 zamiast query uzyc hibernata i mapowa
// task 2
//

@Value
@Builder
public class ZamowienieDTO {

    private Long id;
    private String link;
    private String do_kiedy;
    private String typ;
    private List<PozycjaZamowienieDTO> pozycje;


    public Zamowienie toEntity(){

        List<PozycjaZamowienie> pozycjaZamowienies = pozycje.stream().map(PozycjaZamowienieDTO::toEntity).collect(Collectors.toList());
        return  Zamowienie.builder()
                .id(this.id)
                .link(this.link)
                .do_kiedy(this.do_kiedy)
                .typ(this.typ)
                .pozycje(pozycjaZamowienies)
                .build();

    }





}



