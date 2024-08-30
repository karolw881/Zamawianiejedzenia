CREATE TABLE pozycja_zamowienie_new (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
  	sposob_platnosci VARCHAR(100) CHECK(status IN ('blik', 'karta', 'gotówka')),
    zamawiajacy VARCHAR(100) NOT NULL,
    status VARCHAR(100) CHECK(status IN ('przedzamowieniem', 'skladaniezamowienia', 'zamowieniezlozone(realizowane)' , 'dostarczonedofirmy')),
    cena DOUBLE,
    opis VARCHAR(1000)
);
 
 
CREATE TABLE zamowienie (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
link VARCHAR(100),
do_kiedy VARCHAR(100),
  id_zamowienia INTEGER,
FOREIGN KEY(id_zamowienia) REFERENCES zamowienie(id)
  );  


package com.example.demo.controles;

import com.example.demo.ServiceZamowienie;
import com.example.demo.Zamowienie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api")
@RestController
public class Maincontroller {

    private final ServiceZamowienie serviceZamowienie;

    public Maincontroller(ServiceZamowienie serviceZamowienie) {
        this.serviceZamowienie = serviceZamowienie;
    }



    @GetMapping("/List")
    public String zamowieniaList() {
        List<Zamowienie> zamowienies =  serviceZamowienie.getAllZamowienie();
        return zamowienies.toString();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteZamowienie(@PathVariable Long id) {
        serviceZamowienie.delete(id);
        return ResponseEntity.ok("Zamowienie o ID " + id + " zostało usunięte.");
    }

    @PostMapping("/save")
    public ResponseEntity<Zamowienie> zamowieniaSave() {
        Zamowienie zamowienie = new Zamowienie(100,"a","b",1);
        Zamowienie savedZamowienie = serviceZamowienie.createZamowienie(zamowienie);
        return ResponseEntity.ok(savedZamowienie);
    }


    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("Metoda HTTP nie jest obsługiwana dla tego endpointu.");
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleGenericException(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Wystąpił błąd: " + ex.getMessage());
        }
    }



}

![image](https://github.com/user-attachments/assets/2a4ec7a3-c340-4470-b9c6-d9aade9d9d6d)
![image](https://github.com/user-attachments/assets/b2c9d891-fd3a-42ad-b4bf-fe29740855af)




