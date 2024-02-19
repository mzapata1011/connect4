console.log("antes de envia el socket partida= ");
function connectWebSocket() {
            var socket = new WebSocket("ws://localhost:8080/connect4/refresh?");
            window.addEventListener('beforeunload', function() {

                window.addEventListener('beforeunload', function(event) {
                    // Close the WebSocket connection before leaving the page
                    socket.close();
                    // Optionally, you can provide a message to the user when closing the connection
                    event.returnValue = 'Are you sure you want to leave?';
                    console.alert("are u leaving?");
                });
            });
            socket.onopen = function(event) {
                console.log('WebSocket connection established.');
            };
            
            socket.onerror = function(event){
                console.log(event);
            }
            socket.onmessage = function(event) {
                console.log("websocket recibe: ");
                console.log(event["data"]);
            if (event.data === 'refresh') {
                console.log("refresco");
                socket.close
                location.reload(); // Refresh the page
            }
            
         };

         document.addEventListener('dataUpdated', function(event) {
            console.log(event.detail.message); // Outputs: "Hello from one.js"
            socket.onopen= () => socket.send(event.detail.message);
        });

}
        // Establish WebSocket connection when the page loads
const webSocket = connectWebSocket();