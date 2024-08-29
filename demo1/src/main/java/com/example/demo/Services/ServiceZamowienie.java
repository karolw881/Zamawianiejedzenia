package com.example.demo.Services;
import com.example.demo.Zamowienie;
import com.example.demo.Repo.ZamowienieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceZamowienie {

  private final ZamowienieRepository zamowienieRepository;

    public ServiceZamowienie(ZamowienieRepository zamowienieRepository) {
        this.zamowienieRepository = zamowienieRepository;
    }

    public Zamowienie createZamowienie(Zamowienie zamowienie){
        return zamowienieRepository.save(zamowienie);
    }

   public void delateZamowienie(Zamowienie zamowienie){
         zamowienieRepository.delete(zamowienie);
    }

    public List<Zamowienie> getAllUsers() {
        return zamowienieRepository.findAll();
    }

    public Zamowienie updateZamowienie(Zamowienie zamowienie){
        return zamowienieRepository.save(zamowienie);
    }


    public List<Zamowienie> getAllZamowienie() {
        return zamowienieRepository.findAll();
    }


    public void delete(Long i) {
        zamowienieRepository.deleteById(i);
    }



    public Zamowienie save(Zamowienie zamowienie) {
        return zamowienieRepository.save(zamowienie);
    }
}
