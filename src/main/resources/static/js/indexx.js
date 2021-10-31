const baseURL = 'http://localhost:8080/publishers';
const tbody = document.querySelector('tbody');

document.addEventListener('DOMContentLoaded', showAllPublishers);
const createPublisherBtn = document.querySelector('.add-publisher-btn');
createPublisherBtn.addEventListener('click', createPublisher);

async function fetchData(url, methodName) {
    return (await fetch(url, {
        method: methodName
    })).json();
}

async function getAllPublishers() {
    return (await fetchData(baseURL, 'GET'));
}

async function showAllPublishers() {
    let publishers = await getAllPublishers();
    console.log(publishers);
    for (let publisher of publishers) {
        tbody.appendChild(createPublisherTr(publisher));
    }
}

function createPublisherTr(publisher) {
    let tr = document.createElement('tr');
    let td1 = document.createElement('td');
    let td2 = document.createElement('td');
    let td3 = document.createElement('td');
    let td4 = document.createElement('td');

    let a = document.createElement('a');
    a.textContent = publisher.name;
    a.setAttribute('href', 'http://localhost:8080/publications-page');

    //td1.innerHTML = '<a href="http://localhost:8080/publications-page"></a>'

    td1.appendChild(a);
    td2.textContent = publisher.phone;
    td3.textContent = publisher.email;
    td4.textContent = publisher.information;

    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);

    return tr;
}

async function createPublisher() {
    let name = document.querySelector('.publisher-name-input').value;
    let phone = document.querySelector('.publisher-phone-input').value;
    let email = document.querySelector('.publisher-email-input').value;
    let information = document.querySelector('.publisher-information-input').value;

    let publisher = {
        name:name,
        phone:phone,
        email:email,
        information: information
    };

    let response = await fetch(baseURL, {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify(publisher),
        baseURL
    });

    if (response.status === 200) {
        alert('Издатель добавлен!');
    }
    let tableData = document.getElementById("publisher-table");
    tableData.innerHTML = "";
    await showAllPublishers();

    document.querySelector('.publisher-name-input').value = '';
    document.querySelector('.publisher-phone-input').value = '';
    document.querySelector('.publisher-email-input').value = '';
    document.querySelector('.publisher-information-input').value = '';
}