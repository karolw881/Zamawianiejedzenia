package com.example.demo.Services;

import com.example.demo.Repo.PozycjaZamowienieRepository;
import com.example.demo.pozycjaZamowienie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PozycjaZamowienieService {
    private final PozycjaZamowienieRepository pozycjaZamowienierepository ;

    public PozycjaZamowienieService(PozycjaZamowienieRepository pozycjaZamowienierepository) {
        this.pozycjaZamowienierepository = pozycjaZamowienierepository;
    }

    public pozycjaZamowienie createPozycjaZamowienie(pozycjaZamowienie  pz){
        return pozycjaZamowienierepository.save(pz);
    }
    public void deletePozycjaZamowienie(pozycjaZamowienie  pz){
       pozycjaZamowienierepository.delete(pz);
    }

    public List<pozycjaZamowienie> getAllpozycjaZamowienie(){
       return pozycjaZamowienierepository.findAll();
    }

    public void updatePozycjaZamowienie(pozycjaZamowienie  pz){
        pozycjaZamowienierepository.save(pz);
    }






}
