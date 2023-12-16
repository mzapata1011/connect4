// prueba.js
// Get the canvas element
const canvas = document.getElementById('myCanvas');
// Get the width and height of the screen
const screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
const screenHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

const colorMapping = {
    "11": "blue",
    "12": "blue",
    "23": "red",
    "14": "blue",
    "17": "blue",
    "0": "blue",
    "1": "blue",
    "2": "red",
    "3": "blue",
    "4": "red",
    "5": "blue",
    "6": "red",
    "7": "red",
    "8": "blue",
    "9": "red",
    "10": "red"
};
const jsonNames = Object.keys(colorMapping);
const jsonNumber = jsonNames.map(name => parseInt(name, 10));
let jsonMaxColumns = [];
jsonNumber.forEach(Number => {
    jsonMaxColumns[Number % 6] = Number;
});
let casillaAnterior = 0;
let intentos = 0;

// ClickListener
document.addEventListener('DOMContentLoaded', function () {
    const clickPositionElement = document.getElementById('clickPosition');
    const parentElement = document.getElementById('columna');

    if (parentElement) {
        createRadioButton("radioButton1", "PosicionColumna", "0", 100, 700, "1");
        createRadioButton("radioButton2", "PosicionColumna", "1", 200, 700, "2");
        createRadioButton("radioButton3", "PosicionColumna", "2", 300, 700, "3");
        createRadioButton("radioButton4", "PosicionColumna", "3", 400, 700, "4");
        createRadioButton("radioButton4", "PosicionColumna", "4", 500, 700, "4");
        createRadioButton("radioButton4", "PosicionColumna", "5", 600, 700, "4");


    } else {
        console.log("Nice to know u, soy tu papa");
    }
    // Add the click event listener
    document.addEventListener('click', (event) => {
        const clickX = event.clientX;
        const clickY = event.clientY;

        // Display the click position

        clickPositionElement.innerText = `Click Position: ${clickX}, ${clickY}`;

        // // pone la ficha con la columna: añadir animacion y logica para ver si puede ir ahi
        // let x = Math.round(clickX / 100);
        // drawCircle(x * 100, 600, "blue");
    });
    const radioButtons = document.getElementsByName('PosicionColumna');
    radioButtons.forEach(button => {
        button.addEventListener('change', function () {
            if (this.checked) {
                activateFunction(this.value, jsonMaxColumns);

            }
        });
    });

});

function submitForm() {
    casillaAnterior = casillaAnterior + "";
    console.log("casilla = " + casillaAnterior);
    var JSONresponse = {
        [casillaAnterior.toString()]: "rojo"
    };
    let jsonString = JSON.stringify(JSONresponse);
    console.log(jsonString);
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
            // Handle the response data as needed
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
}
function activateFunction(value) {
    toggleSubmitButton();
    let casilla = jsonMaxColumns[value] + 6;
    let XPosition = 0, YPosition = 0;
    if (intentos != 0) {
        ({ XPosition, YPosition } = coordenada(casillaAnterior));
        drawCircle(XPosition, YPosition, "white");

    }
    ({ XPosition, YPosition } = coordenada(casilla));
    drawCircle(XPosition, YPosition, "green");
    intentos++;
    casillaAnterior = casilla;

}

// Check if the browser supports the HTML5 canvas element
//#TODO: tablero se ajusta a las proporciones de la pantalla
if (canvas.getContext) {
    // Set the width and height of the canvas
    canvas.width = 650; // set your desired width
    canvas.height = screenHeight - 50; // set your desired height

    // Get the 2D rendering context
    const context = canvas.getContext('2d');

    // Now you can use the 'context' to draw on the canvas
    // For example, draw a rectangle with a fill color
    context.fillStyle = 'black';
    context.fillRect(50, 0, 600, canvas.height); // x, y, width, height

    tablero(jsonNumber, colorMapping);
} else {
    // Display an error message or alternative content if the browser doesn't support canvas
    console.error('Canvas not supported in this browser.');
}




/**
 * 
 * @param {JSONObject} jsonData recibido del servelt que corresponde a la partida
 * @param {int []} jsonNumber array de las casillas que esta en uso en este momento
 * Dibuja el tablero adecuado
 */
function tablero(jsonNumber, jsonData) {
    for (let casilla = 0; casilla < 36; casilla++) {
        const propertyValue = jsonData[casilla];
        let { XPosition, YPosition } = coordenada(casilla);
        let color = (propertyValue === undefined) ? "white" : propertyValue;
        drawCircle(XPosition, YPosition, color);
    }
}
/**
 * 
 * @param {int} casilla : casilla del juegos
 * @returns XPosition,YPosition : posicion de los pixeles de la ficha
 */
function coordenada(casilla) {
    let YPosition = 600 - Math.floor(casilla / 6) * 100;
    let XPosition = (casilla % 6 + 1) * 100;
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
    context.arc(X, Y, 40, 0, 2 * Math.PI);
    context.fill();
    context.closePath();
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
// Function to toggle the visibility of the submit button
function toggleSubmitButton() {
    const submitButton = document.getElementById('submitButton');
    const radioButtons = document.querySelectorAll('input[name="PosicionColumna"]:checked');

    // If at least one radio button is selected, show the submit button; otherwise, hide it
    submitButton.style.display = radioButtons.length > 0 ? 'block' : 'none';
}

// Example: Create multiple radio buttons with different positions
