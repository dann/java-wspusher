java-wspusher
========================== 

Publish the data to the web using WebSocket.
This is just a WebSocket usage example.

How to use
--------------------------
### Run rabbitmq server

    % rabbitmq-server &

### Run Jetty Server

For production

    Build war with maven and deploy wspush.war to Jetty.

For testing
 
    % mvn clean compile jetty:run

Endpoint
--------------------------
### HTTP Endpoint

    http://<server>:<port>/wspusher/receiver

### WebSocket Endpoint

    http://<server>:<port>/wspusher/websocket
