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

// Refactored zatwierdz() using postData()
function zatwierdz() {
    const submitButton = document.getElementById('submit-btn');
    if (submitButton) {
        submitButton.addEventListener('click', function(event) {
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
                        window.location.href = 'http://szybkaszama.pl';
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
        submitButton2.addEventListener('click', function(event) {
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
    const container = document.getElementById('dataDisplay');
    if (!container) {
        logError('Element with id "dataDisplay" not found.', new Error('Container not found'));
        return;
    }

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

    data.pozycje.forEach(pozycja => {
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
    container.innerHTML = tableHTML;
}

function fetchDataAndDisplay() {
    const controller = new AbortController();
    fetch('http://localhost/api/lastOrder')
        .then(handleFetchResponse)
        .then(data => {
            console.log('Data received:', data);
            displayDataInTable(data);
            updateOrderInfo(data);
            gotoformularz(data);

        })
        .catch(error => logError('There was a problem with the fetch operation:', error))
        .finally(() => controller.abort());
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


window.onload = function() {
    if (document.getElementById('dataDisplay')) {
        fetchDataAndDisplay();
    }
    if (window.location.pathname.includes('formularz.html')) {
        zatwierdz();
    }
    if (window.location.pathname.includes('admin.html')) {
        addOrder();
    }
};
