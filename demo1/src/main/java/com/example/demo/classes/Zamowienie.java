package com.example.demo.classes;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


// rozbudowac klase op zamowienie ma wielepozycji
// task1 zamiast query uzyc hibernata i mapowa
// task 2
//

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "zamowienie")
@AllArgsConstructor
@Builder


public class Zamowienie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "link")
    private String link;

    @Column(name = "do_kiedy")
    private String do_kiedy;

    @Column(name = "typ")
    private String typ;




    @OneToMany(mappedBy = "zamowienie", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<PozycjaZamowienie> pozycje = new ArrayList<>();




}



