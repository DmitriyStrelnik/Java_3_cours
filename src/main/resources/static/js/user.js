var start = 0;
var showCount = 5;
var end = 5;

async function downloadCertainSoft(listSoftElement) {
    let token = localStorage.getItem('token');

    let result =  await downloadFile(listSoftElement['softwareName'] ,token );
    let errMes = document.getElementById('errMes');
   if(result.ok){
       errMes.innerHTML = listSoftElement['softwareName'] + ' downloaded';
   }else{
       errMes.innerHTML = listSoftElement['softwareName'] + ' NOT downloaded';
   }
}

async function genListOfSoftwareForUser() {



    let token = localStorage.getItem('token');
    let someList = document.querySelector('.someList');
    someList.innerHTML='';
    let listSoft = await getAllSoftware(token);

    let showed = listSoft.length;
    if (showed > end) {
        showed = end
    }

    for (let i = start; i < showed; i++) {
        let genDiv = div();
        let genP1 = p(listSoft[i]['name'] + ' ' + listSoft[i]['description']);
        let genBut = buttonWithParams(listSoft[i]['softwareName']);

        genBut.onclick = async()=>{
            await downloadCertainSoft(listSoft[i]);
        };
        genDiv.appendChild(genP1);
        genDiv.appendChild(genBut);
        someList.appendChild(genDiv);
    }
}

async function prev() {
    let result = document.querySelector('.results');
    if (start - showCount >= 0) {
        start -= showCount;
        end -= showCount;
    }
    await genListOfSoftwareForUser();
}
async function next(){
    let result = document.querySelector('.results');
    await fetch("/getAllSoftware", {
        method: "GET",
    }).then(function (res) {
        return res.json();
    }).then(function (data) {
        if (end < data.length) {
            start += showCount;
            end += showCount;
        }
        genListOfSoftwareForUser(result);
    });
}
async function genPrev() {
    let div = document.querySelector('.nextPrev');
    let logoutBtn = button(prev, 'Prev');
    div.appendChild(logoutBtn)
}

async function genNext() {
    let div = document.querySelector('.nextPrev');
    let logoutBtn = button(await next, 'Next');
    div.appendChild(logoutBtn)
}
async function genUserSearch() {
    let search = document.querySelector('.search');
    let searchButton = button(await genSearchButton, 'Search');
    let searchPatientCard = input('text','searchSoft','soft name');
    searchButton.id = 'searchButton';
    search.appendChild(searchPatientCard);
    search.appendChild(searchButton);
}
async function genSearchButton() {
    let token = localStorage.getItem('token');

    let someList = document.querySelector('.someList');
    someList.innerHTML = '';
    let listSoft = await getAllSoftware(token);

    if (document.getElementById('searchSoft').value.length === 0) {
        await genListOfSoftwareForUser()
    } else {

        for (let i = 0; i < listSoft.length; i++) {
            if (listSoft[i]['name'] === document.getElementById('searchSoft').value) {
                let genDiv = div();
                let genP1 = p(listSoft[i]['name'] + ' ' + listSoft[i]['description']);
                let genBut = buttonWithParams(listSoft[i]['softwareName']);

                genBut.onclick = async () => {
                    await downloadCertainSoft(listSoft[i]);
                };
                genDiv.appendChild(genP1);
                genDiv.appendChild(genBut);
                someList.appendChild(genDiv);
            }
        }
    }
}