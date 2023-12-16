let ready= false;

/**
 * Manda los datos del login al servlet
 * #TODO: hacer el hash para las contraseñas.
 * #TODO: mostrar el resultado del login incorrecto en caso de que pase
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

        window.location.href="index.html";
        // Handle the login response as needed
    })
    .catch(error => {
        console.error('Fetch error:', error);
    });
}

/**
 * manda los datos de registro al servlet.
 * #TODO: mandar la contraseñas en hash
 */
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
