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


![image](https://github.com/user-attachments/assets/9ebb02ac-87af-4bc0-8353-dc3572e67e52)
![image](https://github.com/user-attachments/assets/e1cfe401-1f2d-4400-b1e6-d7a5a49bebd1)



![image](https://github.com/user-attachments/assets/566de97d-aecd-47df-b96b-f89b9539a7e4)
![image](https://github.com/user-attachments/assets/6880a1b1-90e6-418a-a759-07155ba6fbbe)
![image](https://github.com/user-attachments/assets/c9e39580-f48d-401e-904f-7eb51e580bd3)


![image](https://github.com/user-attachments/assets/3ec98dc0-a3f4-49cd-a7ba-40068b6ed08f)
![image](https://github.com/user-attachments/assets/b696d8ff-8fb2-41bc-b3e8-f70b3de454f2)
![image](https://github.com/user-attachments/assets/fef6d895-fb73-4868-b31d-98068acf691d)
![image](https://github.com/user-attachments/assets/e160546d-4a81-4cf2-a9a8-f2aa448e8ea4)






