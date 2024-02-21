var socket;
var rival = undefined;
var turnos = 0;
var jugador = undefined;
var turno = undefined;
var J2_id = undefined;
var mapa=undefined;
let casillaAnterior = 0;
let intentos = 0;
let jsonMaxColumns = [];

function createWebSocket() {
    return new Promise((resolve, reject) => {
        //console.log("Abro el socket");
        socket = new WebSocket("ws:///localhost:8080/connect4/refresh");
        socket.onopen = function () {
            //console.log("socket abierto");
            resolve(socket);
        }
        socket.onerror = function (event) {
            console.log(event);
            reject(event);
        }
        socket.onmessage = function (event) {
            console.log("llego el mensaje: "+event.data);
            if (event.data === 'refresh') {
                console.log("refresco");
                socket.close();
                location.reload(); // Refresh the page
            }
            else if (event.data === 'llego rival' && rival == undefined) {
                // turno = true;
                location.reload();

            }else if (event.data.includes("azules")){
                console.log("vemos el resultado");
                var jsonPlay=JSON.parse(event.data); 
                azul= jsonPlay["azules"];
                rojo=jsonPlay["rojos"];
                console.log(rojo);
                console.log(azul);
                var resultado="empate";
                if (rojo>azul){
                    resultado="victoria";
                }else if (rojo<azul){
                    resultado="derrota";
                }
                var messageElement = document.getElementById("message");

                messageElement.innerHTML=" <p> TU: "+azul+" puntos EL: "+ rojo+ "    "+resultado+ "<p>";

            } 
            else if (!event.data.includes("Received") && !event.data.includes("llego")) {
                turnos=turnos+1;
                turno = true;
                var jsonData = JSON.parse(event.data);
                var disparoRival = jsonData.number;
                nuevoDisparo(disparoRival);
                jsonMaxColumns[disparoRival % 6] = Number(disparoRival);
                Turno();
            }
        }
    });


}

function closeWebSocket() {
    if (socket) {
        socket.close();
    }
}

window.onunload = function () {
    closeWebSocket();
};

function sendMessage(socket, message) {
    if (socket.readyState === WebSocket.OPEN) {
        //console.log("el socket envia :" + message);
        socket.send(message);
    } else {
        console.error("Socket is not open.");
    }
}

// Get the canvas element
const canvas = document.getElementById('myCanvas');
// Get the width and height of the screen
const screenWidth = 650;//window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
const screenHeight = 650;//window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;



// DOM
document.addEventListener('DOMContentLoaded', function () {
    if (canvas.getContext) {
        // Set the width and height of the canvas
        canvas.width = 650; // set your desired width
        canvas.height = 650 // set your desired height
        createWebSocket()
            .then((socket) => {
                //console.log("Websocket connection established");
                return fetchDataFromServlet();
            })
            .then(() => {
                // console.log("Fetch completed");
                // console.log("Voy a entrar a turno");
                return Turno(rival, turno);
            })
            .then(() => {
                //console.log("All functions executed successfully");
            })
            .catch((error) => {
                console.error("Error:", error);
            });
        
    } else {
        // Display an error message or alternative content if the browser doesn't support canvas
        console.error('Canvas not supported in this browser.');
    } 

});

//envia los datos de la nuvea ficha como un form, si se han jugado 36 fichas, se manda el mapa para que se haga el calculo del ganador
function submitForm() {

    turno = false;
    document.getElementById('submitButton').style.visibility = 'hidden';
    console.log("voy a entrar  turno");
    Turno();
    intentos = 0;

    const jsonNumber = Object.keys(mapa);
    // var turnos= jsonNumber.length;
    turnos = turnos + 1;
    console.log("Han juagdo "+ turnos+" turnos");
    jsonMaxColumns[casillaAnterior % 6] = Number(casillaAnterior);
    var esconderBoton=document.getElementById("radioButton"+casillaAnterior%6);
    esconderBoton.checked=false;
    casillaAnterior = casillaAnterior + "";
    var JSONresponse = {
        "number": casillaAnterior.toString(),
        "player": "1",
        "turnos": turnos,

    };
    sendMessage(socket, JSON.stringify(JSONresponse));
    mapa[casillaAnterior.toLocaleString]="blue";
    if (turnos == 36) {
        mapa[casillaAnterior.toString()] = "blue";
        JSONresponse["J1_id"] = J1_id;
        JSONresponse["J2_id"] = J2_id;
    }

    let jsonString = JSON.stringify(JSONresponse);
    // Specify the servlet URL
    const servletUrl = 'PlayServlet';

    // Use the fetch API to send a POST request to the servlet
    fetch(servletUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: jsonString
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text(); // or response.json() if expecting json
        })
        .then(data => {
            console.log("llego del servlet play: ");
            var jsonPlay=JSON.parse(data);
            console.log(jsonPlay);
            var rojo = jsonPlay["rojos"];
            var azul = jsonPlay["azules"];
            console.log(rojo);
            console.log(azul);
            var resultado="empate";
            if (rojo>azul){
                resultado="victoria";
            }else if (rojo<azul){
                resultado="derrota";
            }
            var messageElement = document.getElementById("message");
            if (turnos==36){
                console.log("turnos: "+turnos+" enviamos Resultado");
                messageElement.innerHTML=" <p> TU: "+azul+" puntos EL: "+ rojo+ "    "+resultado+ "<p>";
                var respuesta= {
                    "azules" : rojo,
                    "rojos" : azul,
                }

                
                sendMessage(socket,JSON.stringify(respuesta));
                console.log(JSON.stringify(respuesta));
            }

            // Handle the response data as needed
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
}


function activateFunction(value) {
    toggleSubmitButton();
    let casilla;
    if (jsonMaxColumns[value] !== undefined && jsonMaxColumns[value] !== null) {
        casilla = jsonMaxColumns[value] + 6;
    } else {
        casilla = value;
    }
    let XPosition = 0, YPosition = 0;
    if (intentos != 0) {
        ({ XPosition, YPosition } = coordenada(casillaAnterior));
        drawCircle(XPosition, YPosition, "white");

    }
    ({ XPosition, YPosition } = coordenada(casilla));
    drawCircle(XPosition, YPosition, "blue");
    intentos++;
    casillaAnterior = casilla;

}

function fetchDataFromServlet() {
    return new Promise((resolve, reject) => {

        fetch('PartidaServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            //body: JSON.stringify(params)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok.');
                }
                return response.json();
            })
            .then(data => {
                partida = data["partida"];
                sendMessage(socket, "Partida:" + partida);

                mapa = data["mapa"];
                J1_id = data["J1_ID"];
                J2_id = data["J2_ID"];
                rival = J2_id;
                jugador = data["jugador"];
                turno = (data["turno"] == data["jugador"]) ? true : false;

                const jsonNumber = Object.keys(mapa);
                turnos = jsonNumber.length;

                jsonMaxColumns = [null, null, null, null, null, null];
                jsonNumber.forEach(Number => {
                    jsonMaxColumns[Number % 6] = parseInt(Number, 10);
                });

                const parentElement = document.getElementById('columna');
                for (let i = 0; i < 6; i++) {
                    if((jsonMaxColumns[i]/6)<5){
                    createRadioButton("radioButton" + i, "PosicionColumna", i, 20 + i * 100, 0, (i + 1));//left: canvas.width/6 *i
                    }
                }

                dibujaTablero(mapa);
                resolve();
                console.log(data);
                console.log("turnos= "+turnos);
                var rojo = data["rojo"];
                var azul = data["azul"];
                var resultado = data["ganador"];
                var messageElement = document.getElementById("message");
                if (turnos==36){
                    console.log("cambio el mensaje");
                    messageElement.innerHTML=" <p> TU: "+azul+" puntos EL: "+ rojo+ "    "+resultado+ "<p>";}

            })
            .catch(error => {
                console.error('Error fetching JSON:', error);
                reject();
            });
    });

}


/**
 * 
 * @param {JSONObject} mapa recibido del servelt que corresponde a la partida
 * @param {int []} jsonNumber array de las casillas que esta en uso en este momento
 * Dibuja el tablero adecuado
 */
function dibujaTablero(mapa) {
    const tablero = Object.keys(mapa);
    const context = canvas.getContext('2d');
    context.fillStyle = 'black';
    context.fillRect(0, 0, canvas.width, canvas.height); // x, y, width, height
    for (let ficha = 0; ficha < 36; ficha++) {
        const propertyValue = mapa[ficha] || 'white';// si tablero[ficha] no exite toma el valor white
        let { XPosition, YPosition } = coordenada(ficha);
        //console.log("para la ficha: "+ficha+" la Xposition es: "+XPosition );
        drawCircle(XPosition, YPosition, propertyValue);
    }

}


/**
 * 
 * @param {int} casilla : casilla del juegos
 * @returns XPosition,YPosition : posicion de los pixeles de la ficha
 */
function coordenada(casilla) {
    let YPosition = 600 - Math.floor(casilla / 6) * 100;
    let XPosition = (casilla % 6 + 1) * 100 - 50;
    return { XPosition, YPosition };
}


/**
 * 
 * @param {int} X cordanada X del centro del circulo
 * @param {int} Y  coordenada Y del centro del circulo
 * @param {String} c color del circulo
 * Dibuja un disco de radio 50 con el centro en {X,Y} y color c
 */
function drawCircle(X, Y, c) {
    const context = canvas.getContext('2d');
    context.fillStyle = c;
    context.beginPath();

    context.arc(X, Y, 50, 0, 2 * Math.PI);
    context.fill();
    context.closePath();
}

// Function to toggle the visibility of the submit button
function toggleSubmitButton() {
    const submitButton = document.getElementById('submitButton');
    const radioButtons = document.querySelectorAll('input[name="PosicionColumna"]:checked');

    submitButton.style.visibility = 'visible';


    submitButton.style.pointerEvents = 'auto';

}

function createRadioButton(id, name, value, left, topt) {
    //console.log("I just created the button: " + name);
    var radioButton = document.createElement("input");
    radioButton.type = "radio";
    radioButton.id = id;
    radioButton.name = name;
    radioButton.value = value;

    // Optional: Set additional class for styling
    radioButton.classList.add("customRadioButton");

    // Set position
    radioButton.style.left = left + "px";
    radioButton.style.top = topt + "px";

    // Create label for the radio button
    var label = document.createElement("label");
    label.htmlFor = id;
    // label.appendChild(document.createTextNode(labelText));

    // Append the radio button and label to the container
    document.getElementById("columna").appendChild(radioButton);
    document.getElementById("columna").appendChild(label);

}
function toggleRadioButton(id, value) {
    //console.log("id= "+id);
    var radioButton = document.querySelector('input[id="' + id + '"]');

    if (radioButton) {
        // Button with the specified id exists
        //console.log("Existe el boton");
        if (value) {
            //console.log("Button with id " + id + " is now disabled and hidden");
            radioButton.disabled = true;
            radioButton.style.visibility = 'hidden';
        }
        else {
            // console.log("Button " + id + " is now able");
            radioButton.disabled = false;
            radioButton.style.visibility = 'visible';
            var button = document.getElementById("buttonId");
        }
    } else {
        // No button with the specified id found
       // console.log("No button found with id " + id);
    }

}


function Turno() {
    return new Promise((resolve, reject) => {
        
        var messageElement = document.getElementById("message");
        if(turnos!=36){
            if (J2_id == undefined) {

                messageElement.innerHTML = "<p>Esperando rival </p>";
    
            } else if (turno) {
                // console.log("Es tu turno");
                for (let i = 0; i < 6; i++) {
                    if ((jsonMaxColumns[i] / 6) < 5) {
                        // console.log("entro al condicional");
                        toggleRadioButton("radioButton" + i, false);
                    }
                    // var boton = document.getElementById("radioButton" + i);  
                }
                messageElement.innerHTML = "<p>Es tu turno </p>";
                disparar();
            } else {
                for (let i = 0; i < 6; i++) {
                    toggleRadioButton("radioButton" + i, true);
    
                }
                messageElement.innerHTML = "<p>No Es tu turno </p>";
    
            }
        }

        
    });
}

// if(turnos==36){
//     var rojo=data["rojo"];
//     var azul= data["azul"];
//     var resultado= data["ganador"];
//     messageElement.innerHTML=" <p> TU: "+azul+" puntos EL: "+ rojo+ "    "+resultado+ "<p>";
// }


function disparar() {
    const radioButtons = document.getElementsByName('PosicionColumna');
    radioButtons.forEach(button => {
        button.addEventListener('change', function () {
            if (this.checked) {
                activateFunction(this.value, jsonMaxColumns);

            }
        });
    });
}

function nuevoDisparo(jugada) {
    let XPosition = 0, YPosition = 0;
    let casillamax = (jugada % 6) + 6 * 5;
    ({ XPosition, YPosition } = coordenada(jugada));
    // drawCircle(XPosition, YPosition, "red"); 
    var filactual= Math.floor(jugada/6);
    for(let i=5;i>=filactual;i--){
        animacion(jugada,i);
        // drawCircle(XPosition,YPosition,"white"); 
    }
}

function animacion(jugada,i){
    setTimeout(function(){
        ({ XPosition, YPosition } = coordenada((jugada%6)+6*i));
        drawCircle(XPosition,YPosition,"red");
        if(i!=5){
            ({ XPosition, YPosition } = coordenada((jugada%6)+6*(i+1)));
            drawCircle(XPosition,YPosition,"white");
        }

    },(6-i)*50); //timeout de 1000= 1s
}

