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
