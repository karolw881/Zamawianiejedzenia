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

            const zamowienie = {
                typ: typ || '',
                do_kiedy: doKiedy || '',
                link: link || ''
            };

            // Use postData for sending the order
            postData('http://localhost:80/api/save', zamowienie)
                .then(data => {
                    console.log('Order submitted successfully:', data);
                    window.location.href = 'http://szybkaszama.pl';
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

// Podfunkcja do budowania kodu HTML tabeli
function buildTableHTML(pozycje) {
    let tableHTML = `
        <table border='1'>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Opis</th>
                    <th>Cena</th>
                    <th>Status</th>
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
                <td>${pozycja.status}</td>
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
                    <th>ID</th>
                    <th>do_kiedy</th>
                    <th>link</th>
                    <th>data</th>
                   
                </tr>
            </thead>
            <tbody>
    `;

    pozycje.forEach(pozycja => {
        tableHTML += `
            <tr>
                <td>${pozycja.id}
                <td>${pozycja.link}</td>
                <td>${pozycja.do_kiedy}</td>
             
            </tr>`;
    });

    tableHTML += `</tbody></table>`;
    return tableHTML;
}





function fetchDataAndDisplay() {

    fetchYour().then(data => {
        displayDataInTable(data);
        gotoformularz(data);
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
    return currentPath.includes('admin.html');
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


    }


};
