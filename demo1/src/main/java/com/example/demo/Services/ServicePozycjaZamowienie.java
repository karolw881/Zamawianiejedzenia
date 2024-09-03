package com.example.demo.Services;

import com.example.demo.Repo.PozycjaZamowienieRepository;
import com.example.demo.PozycjaZamowienie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicePozycjaZamowienie {
    private final PozycjaZamowienieRepository pozycjaZamowienierepository ;

    public ServicePozycjaZamowienie(PozycjaZamowienieRepository pozycjaZamowienierepository) {
        this.pozycjaZamowienierepository = pozycjaZamowienierepository;
    }

    public PozycjaZamowienie createPozycjaZamowienie(PozycjaZamowienie pz){
        return pozycjaZamowienierepository.save(pz);
    }


    public void deletePozycjaZamowienie(PozycjaZamowienie pz){
       pozycjaZamowienierepository.delete(pz);
    }

    public List<PozycjaZamowienie> getAllpozycjaZamowienie(){
       return pozycjaZamowienierepository.findAll();
    }

    public void updatePozycjaZamowienie(PozycjaZamowienie pz){
        pozycjaZamowienierepository.save(pz);
    }






}
