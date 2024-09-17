function displayDataInTable(data) {
    const container = document.getElementById('dataDisplay');
    if (!container) {
        console.error('Element with id "dataDisplay" not found.');
        return;
    }

    let tableHTML = "<table border='1'><thead><tr><th>ID</th><th>Opis</th><th>Cena</th><th>Status</th><th>zamawiajacy</th></tr></thead><tbody>";
    // Pętla po pozycjach zamówienia
    data.pozycje.forEach(pozycja => {
        tableHTML += `<tr>
                        <td>${pozycja.id}</td>
                        <td>${pozycja.opis}</td>
                        <td>${pozycja.cena}</td>
                        <td>${pozycja.status}</td>
                        <td>${pozycja.zamawiajacy}</td>
                      </tr>`;
    });
    tableHTML += "</tbody></table>";

    // Wstaw tabelę do diva
    container.innerHTML = tableHTML;
}

function fetchDataAndDisplay() {
    fetch('http://localhost:80/api/lastOrder')
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(`Error ${response.status}: ${text}`);
                });
            }
            return response.json();
        })
        .then(data => {
            console.log('Data received:', data);
            displayDataInTable(data);  // Wywołanie funkcji do wyświetlenia danych w tabeli
            updateOrderInfo(data);
            gotoformularz(data);  // Call to set up the "Dodaj pozycje" button
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

function updateOrderInfo(data) {
    const foodLink = document.getElementById('food-link');
    if (foodLink) {
        foodLink.href = data.link;
        foodLink.textContent = data.link;
    }

    const czasUplynie = document.getElementById('do-kiedy');
    if (czasUplynie) {
        czasUplynie.textContent = data.do_kiedy;
    }

    const statusZamowienia = document.getElementById('status-zamowienia');
    if (statusZamowienia) {
        statusZamowienia.textContent = data.typ;
    }
}

function gotoformularz(data) {
    const dodajPozycjeBtn = document.getElementById('dodaj-pozycje3');
    if (dodajPozycjeBtn) {
        dodajPozycjeBtn.addEventListener('click', function() {
            const id = data.id;  // pobierz id zamowienia
            window.location.href = `/formularz.html#id=${id}`;  // Przenosi użytkownika na stronę formularz.html z dynamicznym id_zamowienia
        });
    } else {
        console.error('Element with id "dodaj-pozycje" not found.');
    }
}




// Define the zatwierdz function that listens for the submit button click
function zatwierdz() {




    const submitButton = document.getElementById('submit-btn');
    if (submitButton) {
        submitButton.addEventListener('click', function(event) {
            event.preventDefault();  // Prevent the default form submission behavior

            // Pobieranie wartości z formularza
            const zamawiajacy = document.getElementById('zamawiajacy').value;
            const opis = document.getElementById('opis').value;
            const cena = document.getElementById('cenax').value;

            console.log(cena);







            // Pobierz ID zamówienia z URL (assuming ID is passed via URL fragment after #)
            const urlParams = new URLSearchParams(window.location.hash.substring(1));
            const id = urlParams.get('id');

            if (id) {
                // Tworzenie obiektu danych do wysłania
                const pozycjaZamowienie = {
                    zamawiajacy: zamawiajacy,
                    opis: opis,
                    id_zamowienia:id,
                    cena:cena

                };		console.log(id);


                console.log(pozycjaZamowienie);

                // Wysłanie danych do serwera za pomocą fetch
                fetch('http://localhost:80/api/formularz/${id}', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(pozycjaZamowienie)
                }).then(response => {
                    if (!response.ok) {
                        return response.text().then(text => {
                            throw new Error(`Error ${response.status}: ${text}`);

                        });
                    }else{window.location.href = `http://szybkaszama.pl`; }
                    return response.json();
                })
            }
        });

    } else {
        console.error('submit-btn element not found.');
    }
}



function zwrocIdzamowienia() {
    return fetch('http://localhost:80/api/lastOrder')
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(`Error ${response.status}: ${text}`);
                });
            }
            return response.json();
        })
        .then(data => {
            return data.id; // Zakładamy, że 'id' jest w obiekcie JSON
        })
        .catch(error => {
            console.error('Wystąpił błąd:', error);
        });
}


function addOrder() {
    const submitButton2 = document.getElementById("submit-btn2");

    submitButton2.addEventListener("click", function(event) {
        event.preventDefault(); // Zatrzymanie domyślnej akcji wysyłki formularza


        const typ = document.querySelector('input[name="typ"]:checked')?.value;


        const doKiedy = document.getElementById('do_kiedy').value;


        const link = document.getElementById('link').value;


        const Zamowienie = {
            typ: typ || '',
            do_kiedy: doKiedy || '',
            link: link || ''
        };

        // console.log(Zamowienie); // Wyświetlenie danych w konsoli do sprawdzenia

        // Wysłanie danych do serwera za pomocą fetch
        fetch('http://localhost:80/api/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Zamowienie)
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(`Error ${response.status}: ${text}`);
                    });
                } else {
                    console.log("Zamówienie wysłane:", response);
                    window.location.href = `http://szybkaszama.pl`;
                    return response.json();
                }
            })
            .catch(error => {
                console.error('Błąd przy wysyłce:', error);
            });
    });
}


// Pobierz dane po załadowaniu strony
window.onload = function() {
    // Only fetch data and display it if the page is index.html
    if (document.getElementById('dataDisplay')) {
        fetchDataAndDisplay();
    }

    // Call zatwierdz only if we are on formularz.html
    if (window.location.pathname.includes('formularz.html')) {
        zatwierdz();
    }

    if (window.location.pathname.includes('admin.html')) {
        addOrder();
    }





};




