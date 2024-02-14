//index.js
var partidas;
function fetchSession() {
    return new Promise(function (resolve, reject) {
        fetch("InicioServlet")
            .then(response => response.json())
            .then(data => {
                console.log("Viendo la sesion");
                console.log("data:");
                console.log(data);
                var username = data["username"];
                partidas=data["partidas"];
                console.log(partidas);
                console.log("Username: " + username);
                if (username == "guest") {
                    window.location.href = 'login.html';
                } else {
                    console.log("numero de partidas= "+partidas.length);


                }
                resolve();
            })
            .catch(error => reject());
    });
}

/**
 * 
 * @param {*} id : ide del boto
 * @param {*} name : nombre del boton
 * @param {*} value : valor del boton : como sale por pantalla
 * @param {*} left : pixel mas a la izquierda del boton
 * @param {*} topt : pixel mas a la derecha del boton
 * crea un boton dada las especificaciones
 */
function createButton(id, name, value, left, topt, action) {
    
    var Button = document.createElement("input");
    Button.type = "submit";
    Button.id = id;
    Button.name = name;
    Button.value = value;

    Button.classList.add("BotonPartida");

    Button.style.left = left + "px";
    Button.style.top = top + "px";

    Button.addEventListener("click", action);

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
            
            
            for (let i = 0; i < partidas.length; i++) {
                //hacer que cada boton vaya a tablero del tablero necesario
                const nombreBoton= "partida numero: "+partidas[i];
                createButton(partidas[i], "boton", nombreBoton, i * 200, 400, myAction); // ver si de id le puedes poner i
                //
            }

        })
        .catch(function(error){
            console.error("error fetching data ", error);
        });

});

function myAction(event){ 
    //#TODO: enviar ambien el numero de jugador 

  // Access the button element that triggered the event
  var clickedButton = event.target;

  // Get the ID of the clicked button
  var id = clickedButton.id;

  // Create a JSON object containing the ID
  var data = { "id": id};

  // Make a POST request to the server with the ID as JSON
  fetch('EscogerPartidaServlet', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
  })
  .then(response => {
      if (response.ok) {
          // Server accepted the data
          console.log('Data sent successfully');
      } else {
          // Server returned an error
          console.error('Failed to send data');
      }
  })
  .catch(error => {
      // An error occurred while sending the data
      console.error('Error:', error);
  });
  window.location.href = 'partida.html';
}
