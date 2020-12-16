async function authorizedUser(token) {
    return await fetch("/authorized", {
        method: "POST",
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
}

async function getUserByToken(token) {
    return await fetch(`/getUser`, {
        method: 'POST',
        headers: {
            'Authorisation': `Bearer ${token}`
        }
    });
}

async function regUser(data) {
    return  await fetch("/register", {
        method: "POST",
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify(data)
    });
}
async function logUser(data) {
    return await fetch("/login", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}
async function downloadFile(name,token) {
    return await fetch(`/user/download/${name}`, {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${token}`,
            'content-type': 'application/json'
        },
    });
}