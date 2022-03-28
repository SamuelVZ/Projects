let loginBtn = document.querySelector('#login-btn');

loginBtn.addEventListener('click', async () => {
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');

    const URL = 'http://localhost:8081/login';

    const jsonString = JSON.stringify({
        "username": usernameInput.value,
        "password": passwordInput.value
    });

    let res = await fetch(URL, {
        method: 'POST',
        body: jsonString
    });

    console.log(res.headers.get('Token'));
    
    
    if (res.status === 200) {
        
        let user = await res.json();
        

        // Get the token and store the token into localStorage
        // LocalStorage is accessible from anywhere in the browser
        let token = res.headers.get('Token');
        localStorage.setItem('jwt', token);
        localStorage.setItem('user_id', user.id); 
        localStorage.setItem('user_role', user.role);

        if (user.role === 'manager') {
            window.location = '/manager-page.html';
        } else if (user.role === 'employee') {
            window.location = '/employee-page.html';
        } 
    } else {
        let errormsg1 =  await res.text(); // to get the error message from the backend
        let errorMsg = document.querySelector('#error-msg');
        errorMsg.innerText = errormsg1;
        errorMsg.style.color = 'red';
    }
    
});