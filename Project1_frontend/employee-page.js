window.addEventListener('load', (event) => {
    populateReimbursementTable();
});


let logout = document.querySelector('#logout-btn');

logout.addEventListener('click', () => {

    localStorage.removeItem('jwt');
    localStorage.removeItem('user_id'); 
    localStorage.removeItem('user_role');


    window.location = '/index.html';
});





let rembursementSubmitBtn = document.querySelector('#submit-btn');

rembursementSubmitBtn.addEventListener('click', async () => {
    let reimbursementNameInput = document.querySelector('#amount-input');
    let reimbursementDescriptionInput = document.querySelector('#description-input');
    let reimbursementTypeInput = document.querySelector('#type-input');
    let reimbursementImageInput = document.querySelector('#image-input');


    let formData = new FormData();
    formData.append('amount', reimbursementNameInput.value);
    formData.append('description', reimbursementDescriptionInput.value);
    formData.append('typeId', reimbursementTypeInput.value);
    formData.append('image', reimbursementImageInput.files[0]);


    try{
        console.log(localStorage.getItem('user_id'));
        let res = await fetch(`http://localhost:8081/employees/${localStorage.getItem('user_id')}/reimbursements`, {
            method: 'POST',
            body: formData,
            headers : {
                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            }
        });


        populateReimbursementTable();

    }catch (e){
        console.log(e);
    }

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

        document.querySelector('#reimbursements-tbl > tbody').innerHTML = '';

        for (let reimbursemnt of reimbursemnts){
            let tr = document.createElement('tr');

            let td1 = document.createElement('td');
            td1.innerText = reimbursemnt.id;

            let td2 = document.createElement('td');
            td2.innerText = reimbursemnt.amount;

            let td3 = document.createElement('td');
            td3.innerText = reimbursemnt.dateSubmitted;

            let td4 = document.createElement('td');
            td4.innerText = (reimbursemnt.managerUsername ? reimbursemnt.dateResolved : 'Not resolved'); 
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


            let td10 = document.createElement('td');

            let res2 = await fetch(`http://localhost:8081/reimbursements/${reimbursemnt.id}/image`, {
                method: 'GET'
            })
            if(res2.status === 200){
                let imgElement = document.createElement('img');
                imgElement.setAttribute('src', `http://localhost:8081/reimbursements/${reimbursemnt.id}/image`);
                imgElement.style.height = '200px';
                td10.appendChild(imgElement);
            }else {
               
                let errormsg1 =  await res2.text(); // to get the error message from the backend
                td10.innerText = errormsg1;
                td10.style.color = 'red';
            }



            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tr.appendChild(td6);
            tr.appendChild(td7);
            tr.appendChild(td8);
            tr.appendChild(td9);
            tr.appendChild(td10);



            let tbody = document.querySelector('#reimbursements-tbl > tbody');
            tbody.appendChild(tr);
        }
    }
        
      
   

}