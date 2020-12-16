async function isAdmin() {
    let user = await getUser();
    return user['role'] === 'ROLE_ADMIN';
}

async function getCertainSoft(softObj) {
    console.log(softObj);
    let token = localStorage.getItem('token');
    let soft = await getSoftware(softObj['name'],token);

    document.getElementById('name').value=soft['name'];
    document.getElementById('description').value=soft['description'];
    // document.getElementById('file').value=soft['softwareName'];
}

async function genListOfSoftwareForAdmin() {
    let token = localStorage.getItem('token');
    let someList = document.querySelector('.someList');
    someList.innerHTML='';
    let listSoft = await getAllSoftware(token);

    for (let i = 0; i < listSoft.length; i++) {
        let genDiv = div();
        let genP1 = p(listSoft[i]['name'] + ' ' + listSoft[i]['description']);
        let genBut = buttonWithParams(listSoft[i]['softwareName']);

        genBut.onclick = async()=>{
            await getCertainSoft(listSoft[i]);
        };
        genDiv.appendChild(genP1);
        genDiv.appendChild(genBut);
        someList.appendChild(genDiv);
    }

}

async function genCreate() {

    let create = document.querySelector('.create');
    let createButton = button(await genSoftCreateButton, 'Create');
    createButton.id = 'docCreateButton';
    create.appendChild(createButton);

}

async function genSoftware() {
    let info = document.querySelector(".software");

    let name = input('text', 'name', 'Name');

    let description = input('text', 'description', 'Surname');

    let file = input('file', 'file');

    info.appendChild(name);
    info.appendChild(description);
    info.appendChild(file);

}

async function genSoftCreateButton() {
    let token = localStorage.getItem('token');
    let name = document.getElementById('name').value;
    let isNotExist = await isSoftwareExist({name: name}, token);
    let errMes = document.getElementById('errMes');
    if (validateSoftware() && await isAuth() && isNotExist.ok) {

        let description= document.getElementById('description').value;
        let file= document.getElementById('file').value;
        console.log(file);

        let data = {
            name: name,
            description: description,
            file: file
        };

        await createSoftware(data, token);
        console.log(data);
        errMes.innerHTML = 'Created';
        await genListOfSoftwareForAdmin();
    } else {
        errMes.innerHTML = 'Not all fields are correct or soft exist';
    }
}

function validateSoftware() {
    let nameL = document.getElementById('name').value.length;
    let descriptionL = document.getElementById('description').value.length;
    let fileL = document.getElementById('file').value.length;

    if (!(nameL >= 2 && nameL <= 16)) {
        return false;
    }
    if (!(descriptionL >= 4)) {
        return false;
    }
    if (!(fileL >= 4)) {
        return false;
    }

    return true;

}

async function genUpdate() {
    let create = document.querySelector('.update');
    let deleteButton = button(genUpdateSoftware, 'update');

    create.appendChild(deleteButton);
}

async function genUpdateSoftware() {
    let errMes = document.getElementById('errMes');
    let token = localStorage.getItem('token');

    let name = document.getElementById('name').value;

    let isNotExist = await isSoftwareExist({name: name}, token);
    if (await isAuth() && !isNotExist.ok) {

        let softwareInfo = await getSoftware(name, token);


        if (validateSoftware()) {
            await updateSoftware({
                id: softwareInfo['id'],
                name: name,
                description: document.getElementById('description').value,
                file:document.getElementById('file').value

            }, token);
            errMes.innerHTML = 'updated';
            await genListOfSoftwareForAdmin();
        } else {
            errMes.innerHTML = 'not validate data';
        }

    } else {
        errMes.innerHTML = 'nothing to update';
    }
}

async function genDelete() {

    let create = document.querySelector('.create');
    let deleteButton = button(genDeleteSoftware, 'Delete');
    create.appendChild(deleteButton);
}
async function genDeleteSoftware() {
    let errMes = document.getElementById('errMes');
    let token = localStorage.getItem('token');
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let file = document.getElementById('file').value;
    let isNotExist = await isSoftwareExist({name: name}, token);

    if (await isAuth() && !isNotExist.ok) {

        let result = await deleteSoftware({name: name}, token);
        if(result.ok) {
            errMes.innerHTML = 'deleted';
            name.value='';
            description.value='';
            file.value='';
            await genListOfSoftwareForAdmin();
        }else{
            errMes.innerHTML = 'smt not ok';
        }

    } else {
        errMes.innerHTML = 'nothing to delete';
    }
}