function main() {
  var wsEndpoint = "ws://127.0.0.1:7777/wspusher/websocket";
  var ws = new WebSocket(wsEndpoint);
  $(window).unload(function(){
    ws.close();
  });
  ws.onmessage = function(message) {
    $('#content').append(message.data + '<br>');
  };
  ws.send({'name':'js client!'});
}

main();
