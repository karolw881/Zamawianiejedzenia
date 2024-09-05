package com.example.demo.Services;

import com.example.demo.PozycjaZamowienieDTO;
import com.example.demo.Zamowienie;
import com.example.demo.Repo.ZamowienieRepository;
import com.example.demo.ZamowienieDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceZamowienie {
    private final ZamowienieRepository zamowienieRepository; // definiuj repozytorium

    public ServiceZamowienie(ZamowienieRepository zamowienieRepository) {
        this.zamowienieRepository = zamowienieRepository;
    }

    public Zamowienie createZamowienie(Zamowienie zamowienie) {
        return zamowienieRepository.save(zamowienie);
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
                        .map(p-> PozycjaZamowienieDTO.builder()
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

    public Zamowienie getLastZamowienie() {
        return zamowienieRepository.findLastZamowienie();
    }

    public Zamowienie save(Zamowienie zamowienie) {
        return zamowienieRepository.save(zamowienie);
    }

    public void delateZamowienie(Zamowienie zamowienie) {
        zamowienieRepository.delete(zamowienie);
    }

    public List<Zamowienie> getAllUsers() {
        return zamowienieRepository.findAll();
    }


}
