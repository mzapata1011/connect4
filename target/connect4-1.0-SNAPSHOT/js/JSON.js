
const canvas = document.getElementById('myCanvas');
const screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
const screenHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
const jsonData = {
    "1": "blue",
    "2": "red",
    "3": "green"
};

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

    //dibuja el tablero
    for (let i = 1; i < 7; i++) {
        for (let j = 1; j < 7; j++) {
            drawCircle(100 * i, 100 * j, "white");
        }
    }

    tablero(jsonData);


} else {
    // Display an error message or alternative content if the browser doesn't support canvas
    console.error('Canvas not supported in this browser.');
}



function tablero(jsonData) {
    const jsonNames = Object.keys(jsonData);
    // // Initialize an index for the while loop
    let index = 0;
    // // Use a while loop to iterate through the properties
    //Hace el tablero en funcion de lo que hay en el JSON
    while (index < jsonNames.length) {
        const jsonNumber = parseInt(jsonNames[index], 10);
        const propertyValue = jsonData[jsonNames[index]];
        xPixel = ((jsonNumber % 6) + 1) * 100;
        yPixel = 600 - (Math.floor(jsonNumber / 6) * 100);
        drawCircle(xPixel, yPixel, propertyValue);
        index++;
    }
}
function drawCircle(X, Y, c) {
    const context = canvas.getContext('2d');
    context.fillStyle = c;
    context.beginPath();
    context.arc(X, Y, 40, 0, 2 * Math.PI);
    context.fill();
    context.closePath();
}



