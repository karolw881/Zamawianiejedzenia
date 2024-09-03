package com.example.demo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
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


    public Zamowienie(long id, String link, String do_kiedy, String typ) {
        this.id = id;
        this.link = link;
        this.do_kiedy = do_kiedy;
        this.typ = typ;
    }


}



