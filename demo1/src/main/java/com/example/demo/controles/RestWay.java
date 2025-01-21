package com.example.demo.controles;


import com.example.demo.classes.PozycjaZamowienie;
import com.example.demo.dtos.PozycjaZamowienieDTO;
import com.example.demo.dtos.ZamowienieDTO;
import com.example.demo.classes.Zamowienie;
import com.example.demo.services.ZamowienieService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;





@RestController
@RequestMapping("/zamowienia")

public class RestWay{

    private final ZamowienieService zamowienieService;

    public RestWay(ZamowienieService zamowienieService) {
        this.zamowienieService = zamowienieService;
    }

    // GET /zamowienia - Get all orders
    @GetMapping
    @CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
    public ResponseEntity<List<ZamowienieDTO>> getAllZamowieniaWithPozycje() {
        List<ZamowienieDTO> zamowienia = zamowienieService.getAllZamowienie();
        return ResponseEntity.ok(zamowienia);
    }
    // GET /zamowienia/{id} - Get single order
    @GetMapping("/{id}")
    public ResponseEntity<ZamowienieDTO> getZamowienie(@PathVariable String id) {
        Zamowienie zamowienie = zamowienieService.findby(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zamowienie not found with id: " + id));
        return ResponseEntity.ok(ZamowienieDTO.toDto(zamowienie));
    }

    // POST /zamowienia - Create new order
    @PostMapping
    public ResponseEntity<?> createZamowienie(@RequestHeader HttpHeaders headers, @RequestBody Zamowienie zamowienie) {
        // Weryfikacja nagłówka Authorization
        if (headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
                // Dekodowanie Base64
                String base64Credentials = authorizationHeader.substring("Basic ".length());
                String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                String[] values = credentials.split(":", 2);

                // Sprawdzanie poprawności loginu i hasła
                String username = values[0];
                String password = values[1];

                if (isAuthenticated(username, password)) {
                    // Jeśli uwierzytelnianie się powiodło, wykonaj zapis zamówienia
                    ZamowienieDTO createdZamowienie = zamowienieService.createZamowienie(zamowienie);
                    return ResponseEntity.ok(createdZamowienie);
                }
            }
        }
        // Zwrot odpowiedzi Unauthorized, jeśli uwierzytelnianie się nie powiodło
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    private boolean isAuthenticated(String username, String password) {
        // Przykładowe uwierzytelnianie
        // Tutaj możesz podłączyć logikę sprawdzania w bazie danych lub innym systemie
        return "admin".equals(username) && "password".equals(password);
    }

    // PUT /zamowienia/{id} - Update order
    @PutMapping("/{id}")
    public ResponseEntity<Zamowienie> updateZamowienie(@PathVariable UUID id, @RequestBody Zamowienie zamowienie) {
        Zamowienie updatedZamowienie = zamowienieService.updateZamowienie(id, zamowienie);
        return ResponseEntity.ok(updatedZamowienie);
    }

    // DELETE /zamowienia/{id} - Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteZamowienie(@PathVariable UUID id) {
        zamowienieService.delete(id);
        //return ResponseEntity.noContent().build();
        return  ResponseEntity.ok("sie udalo");
    }

    // POST /zamowienia/{id}/pozycje - Add position to order
    @PostMapping("/{id}/pozycje")
    public ResponseEntity<?> dodajPozycjeDoZamowienia(@PathVariable String id, @Valid  @RequestBody  PozycjaZamowienieDTO pozycjaZamowienieDTO , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            });
            return ResponseEntity.badRequest().body(errors.toString());
        }
        Zamowienie zamowienie_x = zamowienieService.findby(id).orElseThrow();

        PozycjaZamowienie pozycjaZamowienieDTOEntity = pozycjaZamowienieDTO.toEntity();
        pozycjaZamowienieDTOEntity.setZamowienie(zamowienie_x);

        zamowienieService.dodajPozycje(zamowienie_x, pozycjaZamowienieDTO);

        // serviceZamowienie.updateZamowienie(id, zamowienie_x);

        zamowienieService.save(zamowienie_x);

        return ResponseEntity.ok("okok");
    }


    // GET /zamowienia/latest - Get latest order
    @GetMapping("/latest")
    public ResponseEntity<ZamowienieDTO> getLatestZamowienie() {
        ZamowienieDTO zamowienieDTO = zamowienieService.getOstatnieZamowienie();
        return ResponseEntity.ok(zamowienieDTO);
    }


    // PUT /zamowienia/uuid/pozycje/id - get choosen position from choosen order

    @PutMapping("/{id1}/pozycje/{id2}")
    public ResponseEntity<?> updateChoosenPositionFromChoosenOrder(
            @PathVariable UUID id1,
            @PathVariable Integer id2,
            @Valid @RequestBody PozycjaZamowienieDTO pozycjaZamowienieDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        zamowienieService.updatePositionInOrder(id1, id2, pozycjaZamowienieDTO); // Teraz nie musimy try-catch
        return ResponseEntity.noContent().build(); // Zwracamy 204 No Content po udanej aktualizacji
    }


    @GetMapping("/secureAPI")
    public ResponseEntity securedApi(@RequestHeader HttpHeaders headers) {
        if (headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader.startsWith("Basic ")) {
                return new ResponseEntity<>("Authentication passed", HttpStatus.OK);
            }
        }
        return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
    }



    @GetMapping("/404")
    public ResponseEntity<String> testNotFound() {
        throw new EntityNotFoundException("Zasób nie został znaleziony");
    }

    @GetMapping("/401")
    public ResponseEntity<String> testUnauthorized() throws AccessDeniedException {
        throw new AccessDeniedException("Brak autoryzacji");
    }

    @GetMapping("/403")
    public ResponseEntity<String> testForbidden() throws AccessDeniedException {
        throw new AccessDeniedException("Brak uprawnień do zasobu");
    }

    @GetMapping("/400")
    public ResponseEntity<String> testBadRequest() {
        throw new IllegalArgumentException("Nieprawidłowe parametry żądania");
    }

    @GetMapping("/500")
    public ResponseEntity<String> testInternalError() {
        throw new RuntimeException("Wystąpił wewnętrzny błąd serwera");
    }




    // Exception handler class
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }


}