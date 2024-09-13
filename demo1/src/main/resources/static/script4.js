function logError(message, error){
    console.log(`${message}`,error);
}

function handleFetchResponse(response){
    if(!response.ok){
        return response.text().then(text =>{throw new Error('Error ${response.status}: ${text}');} );
    }
    return response.json();
}


function postData(url,data){
    return  fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(handleFetchResponse)
}



function displayDataInTable(data) {
    const container = document.getElementById('dataDisplay');
    if (!container) {
        logError('Element with id "dataDisplay" not found.', new Error('Container not found'));
        return;
    }

    let tableHTML = "<table border='1'><thead><tr><th>ID</th><th>Opis</th><th>Cena</th><th>Status</th><th>zamawiajacy</th></tr></thead><tbody>";
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
    container.innerHTML = tableHTML;
}

function fetchDataAndDisplay() {
    fetch('http://localhost/api/lastOrder')
        .then(handleFetchResponse)
        .then(data => {
            console.log('Data received:', data);
            displayDataInTable(data);  
            updateOrderInfo(data);
            gotoformularz(data); 
        })
        .catch(error => {
            logError('There was a problem with the fetch operation:', error);
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
        dodajPozycjeBtn.removeEventListener('click',handleClick)
        dodajPozycjeBtn.addEventListener('click', handleClick );

        function handleClick(){
            const id = data.id;  // pobierz id zamowienia
            window.location.href = `/formularz.html#id=${id}`;

        }

    }
    else {
        logError('Element with id "dodaj-pozycje3" not found.', new Error('Button not found'));
    }
}


function addOrder() {
    const submitButton2 = document.getElementById("submit-btn2");
    if (submitButton2) {
        submitButton2.addEventListener("click", function(event) {
            event.preventDefault();

            const typ = document.querySelector('input[name="typ"]:checked')?.value;
            const doKiedy = document.getElementById('do_kiedy').value;
            const link = document.getElementById('link').value;

            const Zamowienie = {
                typ: typ || '',
                do_kiedy: doKiedy || '',
                link: link || ''
            };

            postData('http://localhost/api/save', Zamowienie)
                .then(handleFetchResponse)
                .then(response => {
                    console.log("Zamówienie wysłane:", response);
                    window.location.href = `http://szybkaszama.pl`;
                })
                .catch(error => {
                    logError('Error sending order:', error);
                });
        });
    } else {
        logError('submit-btn2 element not found.', new Error('Submit button not found'));
    }
}
// Pobierz dane po załadowaniu strony
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




