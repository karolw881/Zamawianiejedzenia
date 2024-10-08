function logError(message, error) {
    console.log(`${message}`, error);
}

function handleFetchResponse(response) {
    if (!response.ok) {
        return response.text().then(text => {
            throw new Error(`Error ${response.status}: ${text}`);
        });
    }

    // Check if the response is in JSON format by inspecting the content-type header
    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
        return response.json(); // Parse as JSON if it's JSON
    }

    // If not JSON, return the text as fallback
    return response.text().then(text => {
        console.warn('Response was not JSON:', text);
        return text; // or handle as needed
    });
}


// Existing postData function
function postData(url, data) {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(handleFetchResponse);
}


function  fetchlast(){
    fetch(`http://localhost:80/api/lastOrder`)
        .then(handleFetchResponse)
        .then(data => {

            console.log(data.link);
            let link = document.getElementById('food-link');
            let status_zamowienia = document.getElementById('status-zamowienia');
            let do_kiedy = document.getElementById('do-kiedy');

            link.textContent = data.link;
            status_zamowienia.textContent = data.typ;
            do_kiedy.textContent = data.do_kiedy;
            console.log("aaa" + data.statu);


        })
}

function fetchYour() {

    const urlParams = new URLSearchParams(window.location.hash.slice(1)); // get params from hash
    const id = urlParams.get('id')
    console.log(id);

    if (!id) {
        // Jeśli brak ID w URL lub ID nie jest poprawnym UUID - przekierowanie do strony błędu
        console.error('No valid ID found in the URL.');
        //  window.location.href = 'http://szybkaszama.pl/error.html';

    } else {
        console.log(id);
    }

    return fetch(`http://localhost/api/zamowienie/${id}`)
        .then(handleFetchResponse)
        .then(data => {
            // Sprawdzenie czy w odpowiedzi są dane, jeśli nie, przekieruj na error.html
            if (!data) {
                console.error('Order not found.');
                //     window.location.href = 'http://szybkaszama.pl/error.html'; // Przekierowanie jeśli zamówienia nie ma
                return;
            }

            // Jeśli dane są poprawne, wyświetl zamówienie
            console.log('Data received:', data);
            displayDataInTable(data);
        })
}


// Refactored addPositionOrder() using postData()
function addPositionOrder() {
    const submitButton = document.getElementById('submit-btn');
    if (submitButton) {
        submitButton.addEventListener('click', function (event) {
            event.preventDefault();

            const zamawiajacy = document.getElementById('zamawiajacy').value;
            const opis = document.getElementById('opis').value;
            const cena = document.getElementById('cenax').value;
            const urlParams = new URLSearchParams(window.location.hash.slice(1)); // get params from hash
            const id = urlParams.get('id');
            if (!validateForm()) { // Sprawdzamy, czy formularz jest poprawny
                return false; // Zatrzymujemy dalsze wykonanie
            }
            if (id) {
                const pozycjaZamowienie = {
                    zamawiajacy,
                    opis,
                    id_zamowienia: id,
                    cena
                };

                postData(`http://localhost:80/api/formularz/${id}`, pozycjaZamowienie)
                    .then(data => {
                        console.log('Submission successful:', data);
                        window.location.href = `http://szybkaszama.pl/zamowienie.html#id=${id}`;
                    })
                    .catch(error => logError('Error submitting form:', error));
            } else {
                console.error('ID parameter not found in URL.');
            }
        });
    } else {
        console.error('submit-btn element not found.');
    }
}

// Refactored addOrder() using postData()
function addOrder() {
    const submitButton2 = document.getElementById('submit-btn2');
    if (submitButton2) {
        submitButton2.addEventListener('click', function (event) {
            event.preventDefault();

            const typ = document.querySelector('input[name="typ"]:checked')?.value;
            const doKiedy = document.getElementById('do_kiedy').value;
            const link = document.getElementById('link').value;
            if (!validateAdminDate()){return false;};
            const zamowienie = {
                typ: typ || '',
                do_kiedy: doKiedy || '',
                link: link || ''
            };

            // Use postData for sending the order
            postData('http://localhost:80/api/save', zamowienie)
                .then(data => {
                    console.log('Order submitted successfully:', data);
                    window.location.href = `http://szybkaszama.pl/zamowienie.html#id=${data.id}`;

                })
                .catch(error => logError('Error submitting order:', error));
        });
    }
}

function displayDataInTable(data) {
    const container = getContainer('dataDisplay');
    if (!container) return;

    if (!isValidData(data)) return;

    const tableHTML = buildTableHTML(data.pozycje);
    container.innerHTML = tableHTML;
}

// Podfunkcja do pobierania kontenera
function getContainer(containerId) {
    const container = document.getElementById(containerId);
    if (!container) {
        logError(`Element with id "${containerId}" not found.`, new Error('Container not found'));
    }
    return container;
}

// Podfunkcja do sprawdzania poprawności danych
function isValidData(data) {
    if (!data || !Array.isArray(data.pozycje) || data.pozycje === null) {
        console.warn('No valid pozycje found in the response.');
        return false;
    }
    return true;
}

function updateOrderInfo(data) {
    // Debugowanie danych wejściowych
    console.log('Dane wejściowe:', data);

    // Praca z linkiem do jedzenia
    const foodLink = document.getElementById('food-link');
    if (foodLink && data.link) {
        let mesyyLink = data.link;

        // Debug the original link
        console.log('Oryginalny link:', mesyyLink);
        console.log('Link before modification:', foodLink.href);

        // Remove 'szybkaszama.pl' from the link if it exists
        const cleanLink = mesyyLink.replace(/^(https?:\/\/)?(www\.)?szybkaszama\.pl\/?/, '');

        // Ensure the cleaned link starts with 'http://'
        foodLink.href = cleanLink.startsWith('http') ? cleanLink : `http://${cleanLink}`;
        foodLink.textContent = cleanLink;

        // Debug after modification
        console.log('Updated href:', foodLink.href);
        console.log('Link after removing domain:', cleanLink);
    }


    // Praca z czasem
    const czasUplynie = document.getElementById('do-kiedy');
    if (czasUplynie) {
        console.log('Aktualizacja czasu:', data.do_kiedy);
        czasUplynie.textContent = data.do_kiedy;
    }

    // Praca ze statusem zamówienia
    const statusZamowienia = document.getElementById('status-zamowienia');
    if (statusZamowienia) {
        console.log('Aktualizacja statusu:', data.typ);
        statusZamowienia.textContent = data.typ;
    }
}
// Podfunkcja do budowania kodu HTML tabeli
function buildTableHTML(pozycje) {
    let tableHTML = `
        <table border='1'>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Opis </th>
                    <th>Cena</th> 
                    <th>Zamawiający</th>
                    
                 
                </tr>
            </thead>
            <tbody>
    `;

    pozycje.forEach(pozycja => {
        tableHTML += `
            <tr>
                <td>${pozycja.id}</td>
                <td>${pozycja.opis}</td>
                <td>${pozycja.cena}</td>
                <td>${pozycja.zamawiajacy}</td>
            </tr>`;
    });

    tableHTML += `</tbody></table>`;
    return tableHTML;
}



function wyswietlZamowienia(){
    fetch(`http://localhost:80/api/zamowienia`)    .then(handleFetchResponse)
        .then(data => {
            // Sprawdzenie czy w odpowiedzi są dane, jeśli nie, przekieruj na error.html
            if (!data) {
                console.error('Order not found.');
                //     window.location.href = 'http://szybkaszama.pl/error.html'; // Przekierowanie jeśli zamówienia nie ma
                return;
            }

            // Jeśli dane są poprawne, wyświetl zamówienie
            console.log('Data received:', data);
            displaydata2(data);
        })

}

function displaydata2(data){
    const container = getContainer('dataDisplay2');
    if (!container) return;
    const tableHTML = buildTableHTML2(data);
    container.innerHTML = tableHTML;
}


function buildTableHTML2(pozycje) {
    let tableHTML = `
        <table border='1'>
            <thead>
                <tr>
                         <th>do_kiedy</th>
                    <th>ID</th>
               
                    <th>link</th>
                    <th>data</th>
                   
                </tr>
            </thead>
            <tbody>
    `;

    pozycje.forEach(pozycja => {
        tableHTML += `
            <tr>
                    <td>${pozycja.do_kiedy}</td>
                <td>${pozycja.id}
            
                <td>${pozycja.link}</td>
                <td>${pozycja.data}</td>
                
             
            </tr>`;
    });

    tableHTML += `</tbody></table>`;
    return tableHTML;
}

function fetchDataAndDisplay() {

    fetchYour().then(data => {
        displayDataInTable(data);
        gotoformularz(data);
       // updateOrderInfo(data);
    });


}


function gotoformularz(data) {
    const dodajPozycjeBtn = document.getElementById('dodaj-pozycje3');
    if (dodajPozycjeBtn) {
        dodajPozycjeBtn.removeEventListener('click', handleClick);
        dodajPozycjeBtn.addEventListener('click', handleClick);

        function handleClick() {
            const id = data.id; // Use the id from the server response
            console.log(id);
            window.location.href = `/formularz.html#id=${id}`;
        }
    } else {
        logError('Element with id "dodaj-pozycje3" not found.', new Error('Button not found'));
    }
}


function wyswietlObecneZamowienieZuuid() {
    const controller = new AbortController();
    const urlParams = new URLSearchParams(window.location.hash.slice(1)); // get params from hash
    const id = urlParams.get('id')
    console.log(id);

    if (!id) {
        console.error('No valid ID found in the URL.');
    } else {
        console.log(id);
    }

    fetch(`http://localhost/api/zamowienie/${id}`, {signal: controller.signal})
        .then(handleFetchResponse)
        .then(data => {
            if (!data) {
                console.error('Order not found.');
            }
            console.log('Data received:', data);
            gotoformularz(data);
            displayDataInTable(data)
            updateOrderInfo(data);
        })

}


function isValidUUID(uuid) {
    // Regular expression to validate UUID format (version 4 UUID)
    const uuidRegex = /^[0-9a-f]{8}-[0-9a-f]{4}-[4][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i;
    return uuidRegex.test(uuid);
}

function checkURLAndRedirect() {
    const urlParams = new URLSearchParams(window.location.hash.slice(1)); // Get the part after '#'
    const id = urlParams.get('id'); // Get 'id' parameter from URL
    const currentPath = window.location.pathname; // Get the current page path

    // Sprawdź, czy jest to strona admin, zamowienie, lub formularz
    if (isAdminPage(currentPath)) return;

    if (shouldRedirectToErrorPage(currentPath, id)) {
        redirectToErrorPage();
    }
}

// Sprawdź, czy bieżąca strona to 'admin.html'
function isAdminPage(currentPath) {
    return currentPath.includes('admin/admin.html');
}

// Sprawdź, czy bieżąca strona to 'zamowienie.html' lub 'formularz.html'
function isZamowienieOrFormularzPage(currentPath) {
    return currentPath.includes('zamowienie.html') || currentPath.includes('formularz.html');
}

// Sprawdź, czy powinna być przekierowana do strony błędu
function shouldRedirectToErrorPage(currentPath, id) {
    if (isZamowienieOrFormularzPage(currentPath) && !isValidUUID(id)) {
        console.error('Invalid or missing UUID in the URL.');
        return true;
    }

    if (!isZamowienieOrFormularzPage(currentPath) && !isAdminPage(currentPath)) {
        console.error('Access to this page is restricted.');
        return true;
    }

    return false;
}

// Funkcja do przekierowania do strony błędu
function redirectToErrorPage() {
    window.location.href = 'http://szybkaszama.pl/error.html';
}


function validateForm() {
    var cena = document.getElementById("cenax").value;
    var opis = document.getElementById("opis").value;
    var zamawiajacy = document.getElementById("zamawiajacy").value;

    // Pobieranie elementów z błędami walidacji
    var validationError = document.getElementById("validationError");
    var validationError2 = document.getElementById("validationError2");
    var validationError3 = document.getElementById("validationError3");
    var validationError4 = document.getElementById("validationError4");
    var validationError5 = document.getElementById("validationError5");

    var isValid = true;

    // Walidacja ceny - sprawdzamy, czy cena jest liczbą
    if (isNaN(cena)) {
        validationError.style.display = "block";
        isValid = false;
    } else {
        validationError.style.display = "none";
    }

    // Sprawdzamy, czy cena jest pusta
    if (cena === '') {
        validationError2.style.display = "block";
        isValid = false;
    } else {
        validationError2.style.display = "none";
    }

    // Sprawdzamy, czy cena jest większa od 0
    if (parseFloat(cena) <= 0) {
        validationError3.style.display = "block";
        isValid = false;
    } else {
        validationError3.style.display = "none";
    }

    // Sprawdzamy, czy zamawiający nie jest pusty
    if (zamawiajacy === "") {
        validationError5.style.display = "block";
        isValid = false;
    } else {
        validationError5.style.display = "none";
    }

    // Sprawdzamy, czy opis nie jest pusty
    if (opis === "") {
        validationError4.style.display = "block";
        isValid = false;
    } else {
        validationError4.style.display = "none";
    }

    return isValid; // Zwracamy prawdę tylko, gdy wszystkie pola są poprawne
}

function validateAdminDate() {
    var dateString = document.getElementById("do_kiedy").value;
    var dateRegex = /^([01]\d|2[0-3]):([0-5]\d):([0-5]\d)$/;

    if (dateRegex.test(dateString)) {
        document.getElementById("validationError6").style.display = "none";
        return true;
    } else {
        document.getElementById("validationError6").style.display = "block";
        return false;
    }
}
window.onload = function () {
    // Sprawdzenie, czy aktualna strona to admin.html
    if (!window.location.pathname.includes('admin.html')) {
        fetchDataAndDisplay();
        // Dodanie event listenerów dla hashchange

        window.addEventListener('hashchange', wyswietlObecneZamowienieZuuid);
        wyswietlObecneZamowienieZuuid();

        window.addEventListener('hashchange', checkURLAndRedirect);
        checkURLAndRedirect();

    }

    // Sprawdzenie, czy aktualna strona to formularz.html
    if (window.location.pathname.includes('formularz.html')) {
        addPositionOrder();

    }

    // Sprawdzenie, czy aktualna strona to admin.html
    if (window.location.pathname.includes('admin.html')) {
        addOrder();
        // Jeśli chcesz dodać inne funkcje tylko dla admin.html, możesz je tutaj umieścić
        wyswietlZamowienia();
        fetchlast();



    }


};
