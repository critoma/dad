*. Erase firmware & Flash the Espruino firmware from Linux/Raspberry Pi 
to ESP8266, after USB connection between boards (also have driver USB to UART – no need in Rpi 3 - http://www.silabs.com/products/mcu/pages/usbtouartbridgevcpdrivers.aspx):
RPi: sudo esptool.py --port /dev/ttyUSB0 erase_flash
Mac: sudo esptool.py --port /dev/tty.wchusbserial14310 erase_flash 

RPi: sudo python esptool.py --port /dev/ttyUSB0 -b 115200 write_flash -ff 80m -fm qio -fs 32m 0x0000 "boot_v1.4(b1).bin" 0x1000 espruino_esp8266_user1.bin 0x37E000 blank.bin
Mac: sudo python esptool.py --port /dev/tty.wchusbserial14310 -b 115200 write_flash -ff 80m -fm qio -fs 32m 0x0000 "boot_v1.4(b1).bin" 0x1000 espruino_esp8266_user1.bin 0x37E000 blank.bin 


cd /opt/software/esptool
ls
cd /home/pi/Downloads/Kits/espruino/espruino_1v89_esp8266/
sudo python /opt/software/esptool/esptool.py --port /dev/ttyUSB0 -b 115200 write_flash -ff 80m -fm qio -fs 32m 0x0000 "boot_v1.6.bin" 0x1000 espruino_esp8266_user1.bin 0x37E000 blank.bin



// http://www.espruino.com/Reference#NodeMCU
//Copyright: http://justjibba.net/flashing-nodemcu-with-espruino/ | http://www.espruino.com/Tutorials
// http://www.espruino.com/Reference#NodeMCU
//Copyright: http://justjibba.net/flashing-nodemcu-with-espruino/ | http://www.espruino.com/Tutorials

###############################


// EX.01 - Hello World
console.log("Hello...");
//digitalWrite(NodeMCU.D1, 1);
//digitalWrite(NodeMCU.D1, 0);

// EX. 02 -  set interfal of LED flashing
//working
var  on = 0;
setInterval(function() {
  if (on == 1) on = 0; else on = 1;
  digitalWrite(NodeMCU.D1, on);
}, 1500);

/*
//not working
var  on = false;
setInterval(function() {
  on = !on;
  LED1.write(on);
}, 500);
*/


// EX. 03 - set WiFi AP and pass as IEEE 802.11 client
//var wifi = require("Wifi");
//wifi.connect("AndroidHotspotS3Solaris2.4", {password:"..."}, function(err){
//  console.log("connected? err=", err, "info=", wifi.getIP());
//});
wifi.connect("Cristian-Valeriu's iPhone", {password:"..."}, function(err) {
  console.log("connected? err=", err, "info=", wifi.getIP());
});

wifi.save();
wifi.stopAP();

// EX. 04 - http client
var wifi = require("Wifi");
var http = require("http");

var  on = 0;
setInterval(function() {
  if (on == 1) on = 0; else on = 1;
  digitalWrite(NodeMCU.D1, on);
}, 1500);

//wifi.connect("Cristian-Valeriu's iPhone", {password:"..."}, function(err) {
//  if (err) throw err;
setInterval(function() {
  console.log("connected info=", wifi.getIP());
  // http://www.pur3.co.uk/hello.txt
  http.get("http://172.20.10.2:8888/index.html", function(res) {
        console.log("Response: ",res);
        res.on('data', function(d) {
          console.log("--->"+d);
        });
  });
}, 5000);
//});


// EX. 05 - tcp server on port 1234
// access from RPi browser - e.g.: http://192.168.1.250:1234

var wifi = require("Wifi");

var  on = 0;
setInterval(function() {
  if (on == 1) on = 0; else on = 1;
  digitalWrite(NodeMCU.D1, on);
}, 500);

console.log("Wi-Fi connected info=", wifi.getIP());

var server = require("net").createServer(function(c) {
  // A new client as connected
  c.write("Hello");
  c.on('data', function(data) {
    console.log(">"+JSON.stringify(data));
  });
  c.end();
});
server.listen(1234);


// EX. 06 - http server on port 8080
// http://www.espruino.com/Internet
// http://172.20.10.3:8080/?led=0
// http://192.168.1.250:8080/?led=0

var BTN = NodeMCU.D1;
var status = false;
function onPageRequest(req, res) {
  var a = url.parse(req.url, true);
  res.writeHead(200, {'Content-Type': 'text/html'});
  res.write('<html><body>');
  res.write('<p>Pin is '+(status?'on':'off')+'</p>');
  res.write('<a href="?led=1">on</a><br/><a href="?led=0">off</a>');
  res.end('</body></html>');
  if (a.query && "led" in a.query) {
    console.log(a.query.led);
    if (a.query.led !== "0") { status = true; } else { status = false; }

    console.log("status = " + status);
    BTN.write(status);
    
    //digitalWrite(LED1, a.query["led"]);
  }
}

require("http").createServer(onPageRequest).listen(8080);


#############################
