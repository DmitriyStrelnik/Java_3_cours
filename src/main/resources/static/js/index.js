

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
            await genPrev();
            await genNext();
            await genUserSearch();
        }
    } else {
        genLogReg();
    }

}