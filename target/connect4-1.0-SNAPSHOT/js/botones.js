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
    radioButton.style.top = top + "px";

    // Create label for the radio button
    var label = document.createElement("label");
    label.htmlFor = id;
    // label.appendChild(document.createTextNode(labelText));

    // Append the radio button and label to the container
    document.getElementById("radioButtonContainer").appendChild(radioButton);
    document.getElementById("radioButtonContainer").appendChild(label);
}

// Example: Create multiple radio buttons with different positions
createRadioButton("radioButton1", "columna", "1", 100, 700, "1");
createRadioButton("radioButton2", "columna", "2", 200, 700,"2");
createRadioButton("radioButton3", "columna", "3", 300, 700, "3");
createRadioButton("radioButton4", "columna", "4", 400, 700, "4");
createRadioButton("radioButton4", "columna", "4", 500, 700, "5");
createRadioButton("radioButton4", "columna", "4", 600, 700, "6");

function submitForm(){
    var columna= document.querySelector('input[name="columna"]:checked').value;

    var url ="tablero.html?columna="+columna;
    
    window.location.href=url;

    return false;
}
