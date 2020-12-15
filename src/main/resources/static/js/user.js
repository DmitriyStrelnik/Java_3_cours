
function isTokenExist() {
    return localStorage.getItem('token') != null;
}

async function isAuth() {
    if (isTokenExist()) {
        let token = localStorage.getItem('token');
        await authorizedUser(token);
        return true;
    }
    return false;
}