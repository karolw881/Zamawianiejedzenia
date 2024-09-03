package com.example.demo.controles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @GetMapping("ListPoz")
    public List<PozycjaZamowienie> PozycjaZamowieniaList(){
        List<PozycjaZamowienie> la = servicePozycjaZamowienie.getAllpozycjaZamowienie();
        return  la;
    }


}