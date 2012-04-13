$(function () {

var maxTotalData = 100;

function create_plotter() {
  var options = {
    series: { shadowSize: 0 },
    yaxis: { min: 0, max: 10},
    xaxis: { min: 0, max: maxTotalData, show: false }
  };
  var plot = $.plot($("#content"), [], options);
  return plot;
}

var data = [];
function createPlotData(channel, channelData) {
  while(data.length > maxTotalData) {
      data.shift();       
  }

  var channelDataLength = channelData.length;
  for (var i=0; i < channelDataLength; i++) { 
    data.push(channelData[i]);
  }

  return zipData(data);
}

function zipData(data) {
  var res = [];
  var length = data.length;
  for (var i = 0; i < length; ++i) {
    res.push([i, data[i]]);
  }
  return res;
}

function main() {
  var wsEndpoint = "ws://127.0.0.1:7777/wspusher/websocket";
  var ws = new WebSocket(wsEndpoint);
  $(window).unload(function(){
    ws.close();
  });
  var plot = create_plotter();
  ws.onmessage = function(event) {
    var oscciloscopeData = $.parseJSON( event.data );
    var channel = "ch1";
    var channelData = oscciloscopeData[channel];
    plot.setData([ createPlotData(channel, channelData) ]);
    plot.draw();
  };
  ws.send({'name':'js client!'});
}

main();

});
