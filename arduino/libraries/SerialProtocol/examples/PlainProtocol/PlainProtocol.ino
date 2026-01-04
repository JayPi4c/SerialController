#include "SerialProtocol.h"
#include "PlainProtocol.h"


void onMessage(uint8_t type, int len, uint8_t* msg);
bool state = false;

PlainIncomingProtocol incoming(64, onMessage);
PlainOutgoingProtocol outgoing;

SerialProtocol serial(&incoming, &outgoing);

void onMessage(uint8_t type, int len, uint8_t* msg) {
  String text = "";
  for (int i = 0; i < len; i++) text += (char)msg[i];
  String echo = "ECHO: " + text;
  serial.send(0, echo.c_str());
  state = !state;
  digitalWrite(LED_BUILTIN, state ? HIGH : LOW);
}

void setup() {
  serial.begin();

  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);
}

void loop() {
}

void serialEvent() {
  serial.update();
}
