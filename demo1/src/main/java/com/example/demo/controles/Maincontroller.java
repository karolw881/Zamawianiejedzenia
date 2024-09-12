package com.example.demo.controles;


import com.example.demo.DTO.PozycjaZamowienieDTO;
import com.example.demo.Session.HibernateUtil;
import com.example.demo.classes.PozycjaZamowienie;
import com.example.demo.repo.ZamowienieRepository;
import com.example.demo.services.ServiceZamowienie;
import com.example.demo.classes.Zamowienie;
import com.example.demo.DTO.ZamowienieDTO;
import jakarta.persistence.Access;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
public class Maincontroller {
    private final ServiceZamowienie serviceZamowienie;


    public Maincontroller(ServiceZamowienie serviceZamowienie) {
        this.serviceZamowienie = serviceZamowienie;

    }


    // zamowieni o id 502 wyciagnac z bazy
    // tego dtos skonwertowacna encje
    // do zamowieia o id 502 dodac encja któa skonwertowałęm
    // i w tej encji ustawic zamowienie jakie wyciagniete zostało z bazy 502
    // save na zamowieniu


    // metoda dodaj pozycje zamiast zamowienie_x.getPozycje().add(pozycjaZamowienieDTOEntity );pozycjaZamowienieDTOEntity.setZamowienie(zamowienie_x);
    // dwa razy niewyciagac z bazy id tylko  , metoda update robi to wszystko

    @CrossOrigin(origins = {"*" } , allowedHeaders = {"*"})
    @PostMapping("/formularz/{id}")
    public ResponseEntity<?> dodajPozycjeDoZamowienia(@RequestBody PozycjaZamowienieDTO pozycjaZamowienieDTO) {
        Zamowienie zamowienie_x = serviceZamowienie.findby(pozycjaZamowienieDTO.getId_zamowienia()).orElseThrow();

        PozycjaZamowienie pozycjaZamowienieDTOEntity = pozycjaZamowienieDTO.toEntity();

        pozycjaZamowienieDTOEntity.setZamowienie(zamowienie_x);

        serviceZamowienie.dodajPozycje(zamowienie_x,pozycjaZamowienieDTO);

        // serviceZamowienie.updateZamowienie(pozycjaZamowienieDTO.getId_zamowienia() ,zamowienie_x  );

        serviceZamowienie.save(zamowienie_x );

        return  ResponseEntity.ok("okok");


    }


    @CrossOrigin
    @GetMapping("/LastOrder")
    public ResponseEntity<ZamowienieDTO> getLastOrder() {
        ZamowienieDTO zamowienieDTO = serviceZamowienie.getOstatnieZamowienie();
        return ResponseEntity.ok(zamowienieDTO);
    }


    @CrossOrigin
    @GetMapping("/List")
    public ResponseEntity<List<Zamowienie>> zamowieniaList() {
        List<Zamowienie> zamowienies = serviceZamowienie.getAllZamowienie();
        return ResponseEntity.ok(zamowienies);
    }



    @GetMapping("/List/{id}")
    public ZamowienieDTO zamowienieListId(@PathVariable Long id) {
        return  serviceZamowienie.getIdZamowienie(id);

    }

    @CrossOrigin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteZamowienie(@PathVariable Long id) {
        serviceZamowienie.delete(id);
        return ResponseEntity.ok("Zamowienie o ID " + id + " zostało usunięte.");
    }

    @CrossOrigin(origins = {"*" } , allowedHeaders = {"*"})
    @PostMapping("/save")
    public ZamowienieDTO zamowieniaSave(@RequestBody Zamowienie zamowienie) {
         ZamowienieDTO zamowienieDTO = serviceZamowienie.createZamowienie(zamowienie);
        return zamowienieDTO;
    }










    @CrossOrigin
    @PutMapping("/update/{id}")
    public ResponseEntity<Zamowienie> updateZamowienie(@PathVariable Long id, @RequestBody Zamowienie zamowienie) {
        Zamowienie updatedZamowienie = serviceZamowienie.updateZamowienie(id, zamowienie);
        return ResponseEntity.ok(updatedZamowienie);
    }




    @GetMapping("/zamowienia")
    public ResponseEntity<List<Zamowienie>> getAllZamowieniaWithPozycje() {
        List<Zamowienie> zamowienia = serviceZamowienie.getAllZamowienie();
        return ResponseEntity.ok(zamowienia);
    }





}
