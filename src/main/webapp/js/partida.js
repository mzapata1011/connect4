// prueba.js

// Get the canvas element
const canvas = document.getElementById('myCanvas');
// Get the width and height of the screen
const screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
const screenHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
let turno = false;
let casillaAnterior = 0;
let intentos = 0;
let jsonMaxColumns = [];
let J1_id=0, J2_id=0;
// ClickListener
document.addEventListener('DOMContentLoaded', function () {
    const clickPositionElement = document.getElementById('clickPosition');
    const parentElement = document.getElementById('columna');
    if (canvas.getContext) {
        // Set the width and height of the canvas
        canvas.width = 650; // set your desired width
        canvas.height = 650 // set your desired height

        fetchDataFromServlet();

    } else {
        // Display an error message or alternative content if the browser doesn't support canvas
        console.error('Canvas not supported in this browser.');
    }

    // Add the click event listener
    document.addEventListener('click', (event) => {
        const clickX = event.clientX;
        const clickY = event.clientY;

        // Display the click position

        clickPositionElement.innerText = `Click Position: ${clickX}, ${clickY}`;

    });
});


//envia los datos de la nuvea ficha como un form, si se han jugado 36 fichas, se manda el mapa para que se haga el calculo del ganador
function submitForm() {
    const jsonNumber = Object.keys(jsonData);
    var turnos= jsonNumber.length;
    turnos=turnos+1;
    casillaAnterior = casillaAnterior + "";
    var JSONresponse = {
        "number": casillaAnterior.toString(),
        "player": "1",
        "turnos": turnos,
        
    };

    if (turnos==36){
        jsonData[casillaAnterior.toString()]="blue";
        JSONresponse["mapa"]=jsonData;
        JSONresponse["J1_id"]=J1_id;
        JSONresponse["J2_id"]=J2_id;
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
            console.log('Server response:', data);
            window.location.href="partida.html";
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
            console.log("PartidaServlet pasandome la info");
            console.log("data= ");
            console.log(data);
            jsonData = data["mapa"];
            J1_id=data["J1_ID"];
            J2_id=data["J2_ID"];
            console.log("j1= "+J1_id);
            console.log("j2= "+J2_id);
            const jsonNumber = Object.keys(jsonData);
            var turnos= jsonNumber.length;
            jsonMaxColumns=[null,null,null,null,null,null];
            jsonNumber.forEach(Number => {
                jsonMaxColumns[Number % 6] = parseInt(Number, 10);
            });
            dibujaTablero(jsonData);
            console.log("username= " + data["jugador"]);
            console.log("turno: " + data["turno"]);
            if (data["jugador"] == data["turno"]) {
                turno = true;
                console.log("es tu turno");
                console.log(turno);
                disparar();
            } else {
                console.log("No es tu turno");
                turno = false;
            }
        })
        .catch(error => {
            console.error('Error fetching JSON:', error);
        });
}


/**
 * 
 * @param {JSONObject} jsonData recibido del servelt que corresponde a la partida
 * @param {int []} jsonNumber array de las casillas que esta en uso en este momento
 * Dibuja el tablero adecuado
 */
function dibujaTablero(jsonData) {
    const tablero = Object.keys(jsonData);
    const context = canvas.getContext('2d');
    context.fillStyle = 'black';
    context.fillRect(0, 0, canvas.width, canvas.height); // x, y, width, height
    for (let ficha = 0; ficha < 36; ficha++) {
        const propertyValue = jsonData[ficha] || 'white';// si tablero[ficha] no exite toma el valor white
        let { XPosition, YPosition } = coordenada(ficha);
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
    let XPosition = (casilla % 6 + 1) * 100 -50;
    return { XPosition, YPosition };
}


/**
 * 
 * @param {int} X cordanada X del centro del circulo
 * @param {int} Y  coordenada Y del centro del circulo
 * @param {String} c color del circulo
 * Dibuja un disco de radio 100 con el centro en {X,Y} y color c
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

    // If at least one radio button is selected, show the submit button; otherwise, hide it
    submitButton.style.display = radioButtons.length > 0 ? 'block' : 'none';
}

function createRadioButton(id, name, value, left, topt) {
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


function disparar() {
    const parentElement = document.getElementById('columna');
    console.log("disparo? ");
    console.log("estamos estudiando donde se puede disparar y los maximos de cada columna son: ");
    console.log(jsonMaxColumns);

    if (parentElement) {
        for (let i=0;i<=6;i++){
            if((jsonMaxColumns[i]/6)<5){
                createRadioButton("radioButton"+i,"PosicionColumna",i,canvas.width/6 *i,0,(i+1));
                console.log("widht= "+canvas.width/6);
            }
        }
    }
    const radioButtons = document.getElementsByName('PosicionColumna');
    radioButtons.forEach(button => {
        button.addEventListener('change', function () {
            if (this.checked) {
                activateFunction(this.value, jsonMaxColumns);

            }
        });
    });
}


