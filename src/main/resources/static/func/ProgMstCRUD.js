export function newProject(progMst) {
    fetch('/diagram/project', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(progMst)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();  // json 파싱
        })
        .then(data => {
            console.log('success:', data);
            progMst.progId = data;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

export function loadProject(ID) {
    return fetch(`/diagram/project/load/${ID}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(JSON.parse(data.viewAttr));
            return data;
        })
        .catch((error) => {
            console.error('Error:', error);
            throw error;
        });
}

export function saveProject(progMst) {
    fetch(`/diagram/project/update/${progMst.progId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(progMst)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('success:', data);
            return data;
        })
        .catch((error) => {
            console.error('Error:', error);
            throw error;
        });
}

export function deleteProject(ID) {
    console.log("delete Project");
}