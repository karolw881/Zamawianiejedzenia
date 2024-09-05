package com.example.demo;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


// rozbudowac klase op zamowienie ma wielepozycji
// task1 zamiast query uzyc hibernata i mapowa
// task 2
//

@NoArgsConstructor
@Data
@Entity
@Table(name = "zamowienie")
@AllArgsConstructor
public class Zamowienie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//generuj automatycznie id
    private Long id;

    @Column(name = "link")
    private String link;

    @Column(name = "do_kiedy")
    private String do_kiedy;

    @Column(name = "typ")
    private String typ;


    @OneToMany(mappedBy = "zamowienie", fetch = FetchType.EAGER)
    private List<PozycjaZamowienie> pozycje;




}



