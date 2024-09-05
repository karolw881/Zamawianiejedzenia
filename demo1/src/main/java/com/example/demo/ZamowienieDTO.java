package com.example.demo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


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




}



