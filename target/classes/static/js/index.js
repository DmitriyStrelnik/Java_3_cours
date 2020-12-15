async function genSoftware() {
    let info = document.querySelector(".software");

    let name = input('text', 'name', 'Name');

    let description = input('text', 'description', 'Surname');

    let file = input('text', 'file');

    info.appendChild(name);
    info.appendChild(description);
    info.appendChild(file);

}

async function load() {
    if (await isAuth()) {
        genLogout();
        if (await isAdmin()) {
            await genSoftware();
            await genCreate();
            await genUpdate();
            await genDelete();
            await genListOfSoftwareForAdmin();
        } else {
            await genListOfSoftwareForUser();
        }
    }else{
        genLogReg();
    }

}