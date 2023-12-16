/**
 * #TODO: Login y register tienen que mandar las contraseñas en hash
 */
function login() {
    const loginForm = document.getElementById('loginForm');
    const formData = new FormData(loginForm);
    fetch('loginServlet', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.text(); // or response.json() if expecting JSON
    })
    .then(data => {
        
        console.log('Login response:' +  data);
        // Handle the login response as needed
    })
    .catch(error => {
        console.error('Fetch error:', error);
    });
}

function register() {
    const registerForm = document.getElementById('registerForm');
    const formData = new FormData(registerForm);

    fetch('registerServlet', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.text(); // or response.json() if expecting JSON
    })
    .then(data => {
        console.log('Register response:', data);
        // Handle the register response as needed
    })
    .catch(error => {
        console.error('Fetch error:', error);
    });
}
