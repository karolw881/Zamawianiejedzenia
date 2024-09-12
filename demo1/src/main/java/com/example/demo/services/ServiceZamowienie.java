package com.example.demo.services;

import com.example.demo.DTO.PozycjaZamowienieDTO;
import com.example.demo.classes.Zamowienie;
import com.example.demo.DTO.ZamowienieDTO;
import com.example.demo.repo.ZamowienieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Component
public class ServiceZamowienie {

    @Autowired
    private final ZamowienieRepository zamowienieRepository; // definiuj repozytorium

    public ServiceZamowienie(ZamowienieRepository zamowienieRepository) {
        this.zamowienieRepository = zamowienieRepository;
    }


    public ZamowienieDTO createZamowienie(Zamowienie zamowienie) {
        Zamowienie z = zamowienieRepository.save(zamowienie);
        return ZamowienieDTO.builder()
                .id(z.getId())
                .typ(z.getTyp())
                .link(z.getLink())
                .do_kiedy(z.getDo_kiedy())
                .pozycje(z.getPozycje().stream()
                        .map(p -> PozycjaZamowienieDTO.builder()
                                .id(p.getId())
                                .opis(p.getOpis())
                                .cena(p.getCena())
                                .status(p.getStatus())
                                .sposob_platnosci(p.getSposob_platnosci())
                                .zamawiajacy(p.getZamawiajacy())
                                .build()
                        ).toList())
                .build();

    }



    public Zamowienie updateZamowienie(Long id, Zamowienie pZamowienie) {
        Optional<Zamowienie> oZamowienie = zamowienieRepository.findById(id);
        if (oZamowienie.isPresent()) {
            Zamowienie dopodmiany = oZamowienie.get();
            dopodmiany.setDo_kiedy(pZamowienie.getDo_kiedy());
            dopodmiany.setId(pZamowienie.getId());
            dopodmiany.setLink(pZamowienie.getLink());
            dopodmiany.setTyp(pZamowienie.getTyp());
            return zamowienieRepository.save(dopodmiany);
        } else {
            throw new RuntimeException("Zamówienie o ID " + id + " nie zostało znalezione.");
        }
    }

    // dokoncz



    public void dodajPozycje(Zamowienie zamowienie, PozycjaZamowienieDTO pozycjaZamowienieDTO) {
        zamowienie.getPozycje().add(pozycjaZamowienieDTO.toEntity());

    }




    public List<Zamowienie> getAllZamowienie() {
        return zamowienieRepository.findAll();
    }

    public void delete(Long i) {
        zamowienieRepository.deleteById(i);
    }

    public ZamowienieDTO getIdZamowienie(Long id) {

        return zamowienieRepository.findById(id).map(z -> ZamowienieDTO.builder()
                .id(z.getId())
                .typ(z.getTyp())
                .link(z.getLink())
                .do_kiedy(z.getDo_kiedy())
                .pozycje(z.getPozycje().stream()
                                .map(p -> PozycjaZamowienieDTO.builder()
                                                .id(p.getId())
                                                .opis(p.getOpis())
                                                .cena(p.getCena())
                                                .status(p.getStatus())
                                                .sposob_platnosci(p.getSposob_platnosci())
                                                .zamawiajacy(p.getZamawiajacy())

                                                .build()
                        ).toList())
                .build()).orElse(null);
    }

    public Optional<Zamowienie> findby(Long id){
       return zamowienieRepository.findById((id));
    }



    public ZamowienieDTO getOstatnieZamowienie() {

        return zamowienieRepository.findFirstByOrderByIdDesc().map(z -> ZamowienieDTO.builder()
                .id(z.getId())
                .typ(z.getTyp())
                .link(z.getLink())
                .do_kiedy(z.getDo_kiedy())
                .pozycje(z.getPozycje().stream()
                        .map(p -> PozycjaZamowienieDTO.builder()
                                .id(p.getId())
                                .opis(p.getOpis())
                                .cena(p.getCena())
                                .status(p.getStatus())
                                .sposob_platnosci(p.getSposob_platnosci())
                                .zamawiajacy(p.getZamawiajacy())
                                .build()
                        ).toList())
                .build()).orElse(null);
    }

    public Zamowienie save(Zamowienie zamowienie) {
        Zamowienie za =  zamowienieRepository.save(zamowienie);
        zamowienieRepository.flush();
        return  za;
    }

    public void delateZamowienie(Zamowienie zamowienie) {
        zamowienieRepository.delete(zamowienie);
    }

    public List<Zamowienie> getAllUsers() {
        return zamowienieRepository.findAll();
    }


}
