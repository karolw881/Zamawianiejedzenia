package com.example.demo.controles;

import com.example.demo.Zamowienie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Services.ServicePozycjaZamowienie;

import com.example.demo.PozycjaZamowienie;

import java.util.List;

@RequestMapping("/api")
@RestController
public class IndexController {
   private final ServicePozycjaZamowienie servicePozycjaZamowienie;

    public IndexController(ServicePozycjaZamowienie servicePozycjaZamowienie) {
        this.servicePozycjaZamowienie = servicePozycjaZamowienie;
    }


    @CrossOrigin
    @GetMapping("/ListPoz")
    public List<PozycjaZamowienie> PozycjaZamowieniaList(){
        List<PozycjaZamowienie> la = servicePozycjaZamowienie.getAllpozycjaZamowienie();
        return  la;

    }

    @CrossOrigin
    @GetMapping("/LastPositionOrder")
    public  ResponseEntity<PozycjaZamowienie>  getLastPosiotionOrder(){
        PozycjaZamowienie pozycjaZamowienie = servicePozycjaZamowienie.getLastPozycjaZamowienie();
        return ResponseEntity.ok(pozycjaZamowienie);
    }



    @CrossOrigin
    @GetMapping("/formularz/{id}")
    public Long formularz(@PathVariable("id") Long id) {
        return id;
    }



}