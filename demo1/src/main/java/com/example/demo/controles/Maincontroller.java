package com.example.demo.controles;

import com.example.demo.Services.ServiceZamowienie;
import com.example.demo.Zamowienie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api")
@RestController
public class Maincontroller {

    private final ServiceZamowienie serviceZamowienie;

    public Maincontroller(ServiceZamowienie serviceZamowienie) {
        this.serviceZamowienie = serviceZamowienie;
    }



    @GetMapping("/List")
    public String zamowieniaList() {
        List<Zamowienie> zamowienies =  serviceZamowienie.getAllZamowienie();
        return zamowienies.toString();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteZamowienie(@PathVariable Long id) {
        serviceZamowienie.delete(id);
        return ResponseEntity.ok("Zamowienie o ID " + id + " zostało usunięte.");
    }

    @PostMapping("/save")
    public ResponseEntity<Zamowienie> zamowieniaSave() {
        Zamowienie zamowienie = new Zamowienie(100,"a","b",1);
        Zamowienie savedZamowienie = serviceZamowienie.createZamowienie(zamowienie);
        return ResponseEntity.ok(savedZamowienie);
    }






}
