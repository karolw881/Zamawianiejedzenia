package com.example.demo;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Zamowienie {
    public Zamowienie() {

    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "link")
    private String link;
    @Column(name = "do_kiedy")
    private String do_kiedy;
    @Column(name = "id_zamowienie")
    private Integer id_zamowienia;


    public Zamowienie(long id, String link, String do_kiedy, Integer id_zamowienia) {
        this.id = id;
        this.link = link;
        this.do_kiedy = do_kiedy;
        this.id_zamowienia = id_zamowienia;
    }
}



