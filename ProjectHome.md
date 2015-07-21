# Java Speed-O-Meter #

## Network speed and reliability test ##

**Please note that the binaries are here now**:

https://drive.google.com/folderview?id=0By9So7eKtpjuU3paWE1JTGNLclE&usp=sharing

### Introduction ###

This is a Java application to measure the network and speed and reliability between two hosts. This application has two components, a server that listens to connections and a client that connects to that server. Once the connection has been established, both components will send data to each other and the transmit/ receive speed are plotted on a graph and printed on the console. It is possible to toggle the transmission and receiving of messages by typing T and R in the console.

Each message is randomly generated with a variable size between 1000 and 2000 bytes, plus 4 bytes to hold the message size and 8 bytes for CRC. Each message is checked for CRC errors and the application is aborted if a CRC error is encountered.

### Run from sources ###

To run this application, download its source code and run the following command in the root directory of the source code:

```
mvn clean assembly:assembly
```

In the directory `target/speed-o-meter-1.0-bin/speed-o-meter-1.0` you will find the application directory with a script to run the client and another to run the server.

You must have an environment variable named `JAVA_HOME` that points to a valid Java 6 JRE.

### Download binary ###

**Alternatively**, you can download the compiled binary files and execute the scripts `client.cmd` and `server.cmd` to run the application.

You must have an environment variable named `JAVA_HOME` that points to a valid Java 6 JRE.
2

To download the binary files, please follow this link:

https://drive.google.com/folderview?id=0By9So7eKtpjuU3paWE1JTGNLclE&usp=sharing

### Screen shots ###

![https://lh4.googleusercontent.com/-7v6o9OCVtEs/TkBSOxAIwqI/AAAAAAAAdQE/UcBRRuuKO6c/s640/speed-o-meter.png](https://lh4.googleusercontent.com/-7v6o9OCVtEs/TkBSOxAIwqI/AAAAAAAAdQE/UcBRRuuKO6c/s640/speed-o-meter.png)