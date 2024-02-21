document.addEventListener("DOMContentLoaded", function () {
    fetch('stats', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        //body: JSON.stringify(params)
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }
        return response.json();
    })
        .then(data => {
            // creo la tabla
            var table = document.createElement("table");
            // Create a table header row
            var headerRow = table.insertRow();

            // Add header cells
            var headers = ["partidas ganadas", "partidas perdidas", "partidas empatadas", "partidas totales", "Puntos horizontales", "Puntos verticales", "Puntos diagonales", "Puntos totales", "Puntos por partido"];
            for (var i = 0; i < headers.length; i++) {
                var headerCell = document.createElement("th");
                headerCell.textContent = headers[i];
                headerRow.appendChild(headerCell);
            }
            console.log("headers 8= "+ headers[8]);
            var row = table.insertRow();
            for (var i = 0; i < headers.length; i++) {
                var cell = row.insertCell();
                var stat = data[headers[i]]
                var total = (i < 4) ? 3 : 7;
                var promedio = stat * 100 / data[headers[total]];
                if (i == 8) {
                    console.log("data[headers[3]]= "+data[headers[3]]);
                    cell.textContent = data[headers[7]] / data[headers[3]];
                }else if (i != 3 && i != 7) {
                    cell.textContent = stat + " ( " + promedio.toFixed + "%)";
                } else {
                    cell.textContent = stat;
                }


            }
            document.body.appendChild(table);
        })
        .catch(error => {
            console.error('Error fetching JSON:', error);
        });
    // Create data rows and cells
    // var data = [
    //     ["Data 1", "Data 2", "Data 3"],
    //     ["Data 4", "Data 5", "Data 6"]
    // ];

    // for (var i = 0; i < data.length; i++) {
    //     var row = table.insertRow();
    //     for (var j = 0; j < data[i].length; j++) {
    //         var cell = row.insertCell();
    //         cell.textContent = data[i][j];
    //     }
    // }

    // Add the table to the document

});

