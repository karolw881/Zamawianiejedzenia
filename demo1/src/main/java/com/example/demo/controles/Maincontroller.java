package com.example.demo.controles;


import com.example.demo.dtos.PozycjaZamowienieDTO;
import com.example.demo.classes.PozycjaZamowienie;
import com.example.demo.services.ZamowienieService;
import com.example.demo.classes.Zamowienie;
import com.example.demo.dtos.ZamowienieDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api")
@RestController
public class Maincontroller {
    private final ZamowienieService zamowienieService;


    public Maincontroller(ZamowienieService zamowienieService) {
        this.zamowienieService = zamowienieService;

    }


    // zamowieni o id 502 wyciagnac z bazy
    // tego dtos skonwertowacna encje
    // do zamowieia o id 502 dodac encja któa skonwertowałęm
    // i w tej encji ustawic zamowienie jakie wyciagniete zostało z bazy 502
    // save na zamowieniu


    // metoda dodaj pozycje zamiast zamowienie_x.getPozycje().add(pozycjaZamowienieDTOEntity );pozycjaZamowienieDTOEntity.setZamowienie(zamowienie_x);
    // dwa razy niewyciagac z bazy id tylko  , metoda update robi to wszystko

    @CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
    @PostMapping("/formularz/{id}")
    public ResponseEntity<?> dodajPozycjeDoZamowienia(@PathVariable String id, @RequestBody PozycjaZamowienieDTO pozycjaZamowienieDTO) {
        Zamowienie zamowienie_x = zamowienieService.findby(id).orElseThrow();

        PozycjaZamowienie pozycjaZamowienieDTOEntity = pozycjaZamowienieDTO.toEntity();
        pozycjaZamowienieDTOEntity.setZamowienie(zamowienie_x);

        zamowienieService.dodajPozycje(zamowienie_x, pozycjaZamowienieDTO);

        // serviceZamowienie.updateZamowienie(id, zamowienie_x);

        zamowienieService.save(zamowienie_x);

        return ResponseEntity.ok("okok");
    }



    @CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
    @GetMapping("/zamowienie/{uuid}")
    public  ResponseEntity<ZamowienieDTO> getCurrentZamowienie(@PathVariable String uuid ){
        Zamowienie zamowienie = zamowienieService.findby(uuid).orElseThrow();

        return ResponseEntity.ok(ZamowienieDTO.toDto(zamowienie));
    }




    @CrossOrigin
    @GetMapping("/lastOrder")
    public ResponseEntity<ZamowienieDTO> getLastOrder() {
        ZamowienieDTO zamowienieDTO = zamowienieService.getOstatnieZamowienie();
        return ResponseEntity.ok(zamowienieDTO);
    }






    @GetMapping("/list/{id}")
    public ZamowienieDTO zamowienieListId(@PathVariable Long id) {
        return  zamowienieService.getIdZamowienie(id);

    }

    @CrossOrigin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteZamowienie(@PathVariable Long id) {
        zamowienieService.delete(id);
        return ResponseEntity.ok("Zamowienie o ID " + id + " zostało usunięte.");
    }

    @CrossOrigin(origins = {"*" } , allowedHeaders = {"*"})
    @PostMapping("/save")
    public ZamowienieDTO zamowieniaSave(@RequestBody Zamowienie zamowienie) {
         ZamowienieDTO zamowienieDTO = zamowienieService.createZamowienie(zamowienie);
        return zamowienieDTO;
    }


    @CrossOrigin
    @PutMapping("/update/{id}")
    public ResponseEntity<Zamowienie> updateZamowienie(@PathVariable Long id, @RequestBody Zamowienie zamowienie) {
        Zamowienie updatedZamowienie = zamowienieService.updateZamowienie(id, zamowienie);
        return ResponseEntity.ok(updatedZamowienie);
    }



   @GetMapping("/zamowienia")
    public ResponseEntity<List<Zamowienie>> getAllZamowieniaWithPozycje() {
        List<Zamowienie> zamowienia = zamowienieService.getAllZamowienie();
        return ResponseEntity.ok(zamowienia);
    }


}
