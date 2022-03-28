let logout = document.querySelector('#logout-btn');

logout.addEventListener('click', () => {

    localStorage.removeItem('jwt');
    localStorage.removeItem('user_id'); 
    localStorage.removeItem('user_role');


    window.location = '/index.html';
});

window.addEventListener('load', (event) => {
    populateReimbursementTable();
});

async function populateReimbursementTable(){
    const URL = `http://localhost:8081/employees/${localStorage.getItem('user_id')}/reimbursements`;

    let res = await fetch(URL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })

    if(res.status === 200){
        let reimbursemnts = await res.json();
        for (let reimbursemnt of reimbursemnts){
            let tr = document.createElement('tr');

            let td1 = document.createElement('td');
            td1.innerText = reimbursemnt.id;

            let td2 = document.createElement('td');
            td2.innerText = reimbursemnt.amount;

            let td3 = document.createElement('td');
            td3.innerText = reimbursemnt.dateSubmitted;

            let td4 = document.createElement('td');
            td4.innerText = (reimbursemnt.managerUsername ? reimbursemnt.dateResolved : 'Not reviewed'); 
            td4.style.color = (reimbursemnt.managerUsername ? td4.style.color : 'red');

            let td5 = document.createElement('td');
            td5.innerText = reimbursemnt.description;

            let td6 = document.createElement('td');
            td6.innerText = reimbursemnt.employeeUsername;
            
            let td7 = document.createElement('td');
            td7.innerText = (reimbursemnt.managerUsername ? reimbursemnt.managerUsername : 'Not assigned')
            td7.style.color = (reimbursemnt.managerUsername ? td7.style.color : 'red');

            let td8 = document.createElement('td');
            td8.innerText = reimbursemnt.statusName;

            let td9 = document.createElement('td');
            td9.innerText = reimbursemnt.typeName;

            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tr.appendChild(td6);
            tr.appendChild(td7);
            tr.appendChild(td8);
            tr.appendChild(td9);

            let tbody = document.querySelector('#reimbursements-tbl > tbody');
            tbody.appendChild(tr);
        }
    }
        
      
   

}