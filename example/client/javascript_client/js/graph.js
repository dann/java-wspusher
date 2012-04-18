$(function () {

var maxTotalData = 100;
var timeLine = new TimeSeries();

function create_plotter() {
  var smoothie = new SmoothieChart({ 
    millisPerPixel: 20, 
    grid: { 
      strokeStyle: '#555555',
      lineWidth: 1,
      millisPerLine: 1000,
      verticalSections: 4 
    }
  });
  smoothie.streamTo(document.getElementById("content"), 1000);
  smoothie.addTimeSeries(timeLine, { 
    strokeStyle:'rgb(0, 255, 0)', 
    fillStyle:'rgba(0, 255, 0, 0.4)',
    lineWidth:3 
  });
}

function plot_channel_data(channelData) {
  var channelDataLength = channelData.length;
  var now = new Date().getTime();
  for (var i=0; i < channelDataLength; i++) {
    timeLine.append(now + i, channelData[i]);
  }
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
    plot_channel_data(channelData);
  };
  ws.send({'name':'js client!'});
}

main();

});
