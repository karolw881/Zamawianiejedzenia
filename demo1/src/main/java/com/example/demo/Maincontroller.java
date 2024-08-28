package com.example.demo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Maincontroller {
    private final ServiceZamowienie serviceZamowienie;

    public Maincontroller(ServiceZamowienie serviceZamowienie) {
        this.serviceZamowienie = serviceZamowienie;
    }

    @GetMapping("/zamowienia")
    public String zamowienia(Model model) {
        Zamowienie zamowienie = new Zamowienie(1,"a","b",1);
        serviceZamowienie.save(zamowienie);
        serviceZamowienie.delete(zamowienie);
        List<Zamowienie> zamowienies =  serviceZamowienie.getAllZamowienie(zamowienie);
        return zamowienies.toString();
    }





}
