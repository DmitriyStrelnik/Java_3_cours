async function isSoftwareExist(data,token) {
    return await fetch("/admin/isSoftwareExist",{
        method :'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'content-type': 'application/json'
        },
        body:JSON.stringify(data)

    });
}
async function createSoftware(data,token) {
    return await fetch("/admin/createSoftware",{
        method :'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'content-type': 'application/json'
        },
        body:JSON.stringify(data)

    });
}
async function getSoftware(data,token) {
    return await fetch(`/admin/getSoftware/${data}`,{
        method :'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'content-type': 'application/json'
        },

    });
}

async function updateSoftware(data,token) {
    return await fetch("/admin/updateSoftware",{
        method :'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'content-type': 'application/json'
        },
        body:JSON.stringify(data)

    });
}
async function deleteSoftware(data, token) {
    return await fetch("/admin/deleteSoftware",{
        method :'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
            'content-type': 'application/json'
        },
        body:JSON.stringify(data)

    });
}