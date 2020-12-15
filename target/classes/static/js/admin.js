async function isAdmin() {
    let user = await getUser();
    return user['role'] === 'ROLE_ADMIN';
}

async function genCreate() {

    let create = document.querySelector('.create');
    let createButton = button(await genSoftCreateButton, 'Create');
    createButton.id = 'docCreateButton';
    create.appendChild(createButton);

}

async function genSoftCreateButton() {
    let token = localStorage.getItem('token');
    let name = document.getElementById('name').value;
    let isNotExist = await isSoftwareExist({name: name}, token);
    let errMes = document.getElementById('errMes');
    if (validateSoftware() && await isAuth() && isNotExist.ok) {

        let description= document.getElementById('description').value;
        let file= document.getElementById('file').value;

        let data = {
            name: name,
            surname: description,
            file: file
        };

        await createSoftware(data, token);
        console.log(data);
        errMes.innerHTML = 'Created';
    } else {
        errMes.innerHTML = 'Not all fields are correct or document exist';
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

        let res = await getSoftware(name, token);
        let text = await res.text();
        let softwareInfo = JSON.parse(text);

        if (validateSoftware()) {
            await updateSoftware({
                id: softwareInfo['id'],
                name: name,
                description: document.getElementById('description').value,

            }, token);
            errMes.innerHTML = 'updated';
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
        }else{
            errMes.innerHTML = 'U still have patients';
        }

    } else {
        errMes.innerHTML = 'nothing to delete';
    }
}