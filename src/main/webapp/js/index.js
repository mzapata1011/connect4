//index.js
var numeroPatidas;
function fetchSession() {
    return new Promise(function (resolve, reject) {
        fetch("InicioServlet")
            .then(response => response.json())
            .then(data => {
                console.log("Viendo la sesion");
                var username = data["username"];

                console.log("Username: " + username);
                if (username == "guest") {
                    window.location.href = 'login.html';
                } else {
                    numeroPatidas = data["numero de partidas"];
                    console.log("numero de partidas= " + numeroPatidas);


                }
                resolve();
            })
            .catch(error => reject());
    });
}

document.getElementById(`navigateButton`).addEventListener(`click`, function () {
    window.location.href = 'partida.html';
})
var login = document.getElementById('IniciarSesion');

document.getElementById(`IniciarSesion`).addEventListener(`click`, function () {
    window.location.href = 'login.html';
})


/**
 * 
 * @param {*} id : ide del boto
 * @param {*} name : nombre del boton
 * @param {*} value : valor del boton : como sale por pantalla
 * @param {*} left : pixel mas a la izquierda del boton
 * @param {*} topt : pixel mas a la derecha del boton
 * crea un boton dada las especificaciones
 */
function createButton(id, name, value, left, topt) {
    
    var Button = document.createElement("input");
    Button.type = "submit";
    Button.id = id;
    Button.name = name;
    Button.value = value;

    Button.classList.add("BotonPartida");

    Button.style.left = left + "px";
    Button.style.top = top + "px";
    var label = document.createElement("label");
    label.htmlFor = id;
    document.getElementById("partidas").appendChild(Button);
    document.getElementById("partidas").appendChild(label);
}


document.addEventListener('DOMContentLoaded', function () {
    fetchSession()
        /**
         * crea los botones para ir a una partida segun el usuario
         */
        .then(function(){
            
            
            for (let i = 0; i < numeroPatidas; i++) {
                //hacer que cada boton vaya a tablero del tablero necesario
                const nombreBoton= "partida numero: "+i;
                createButton("partidas", "boton", nombreBoton, i * 200, 400); // ver si de id le puedes poner i
                //
            }

        })
        .catch(function(error){
            console.error("error fetching data ", error);
        });

});
