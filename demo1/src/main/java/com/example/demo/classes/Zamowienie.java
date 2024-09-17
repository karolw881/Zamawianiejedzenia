package com.example.demo.classes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

// ...


import java.util.Date;
import java.util.List;
import java.util.UUID;


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
    @GeneratedValue(strategy = GenerationType.UUID)
    //@Type(type="pg-uuid")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "link")
    private String link;

    @Column(name = "do_kiedy")
    private String do_kiedy;

    @Column(name = "typ")
    private String typ;

    @Column(name = "data")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime data;



    @OneToMany(mappedBy = "zamowienie", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<PozycjaZamowienie> pozycje = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        if(data == null){
            data = LocalDateTime.now();

        }
    }


}



