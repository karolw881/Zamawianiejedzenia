package com.example.demo.controles;


import com.example.demo.Services.ServiceZamowienie;
import com.example.demo.Zamowienie;
import com.example.demo.ZamowienieDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
public class Maincontroller {

    private final ServiceZamowienie serviceZamowienie; // definiuj Serwis w kontrolerze


    public Maincontroller(ServiceZamowienie serviceZamowienie) {
        this.serviceZamowienie = serviceZamowienie;

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

    @CrossOrigin
    @PostMapping("/save")
    public ResponseEntity<Zamowienie> zamowieniaSave(@RequestBody Zamowienie zamowienie) {
        Zamowienie savedZamowienie = serviceZamowienie.createZamowienie(zamowienie);
        return ResponseEntity.ok(savedZamowienie);
    }


    @CrossOrigin
    @PutMapping("/update/{id}")
    public ResponseEntity<Zamowienie> updateZamowienie(@PathVariable Long id, @RequestBody Zamowienie zamowienie) {
        Zamowienie updatedZamowienie = serviceZamowienie.updateZamowienie(id, zamowienie);
        return ResponseEntity.ok(updatedZamowienie);
    }

    @CrossOrigin
    @GetMapping("/LastOrder")
    public ResponseEntity<Zamowienie> getLastOrder() {
     Zamowienie zamowienie = serviceZamowienie.getLastZamowienie();
        return ResponseEntity.ok(zamowienie);
    }


    @GetMapping("/zamowienia")
    public ResponseEntity<List<Zamowienie>> getAllZamowieniaWithPozycje() {
        List<Zamowienie> zamowienia = serviceZamowienie.getAllZamowienie();



        return ResponseEntity.ok(zamowienia);
    }



}
