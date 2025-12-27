#include "SerialProtocol.h"
#include "TLVProtocol.h"

const char* ERR_NOT_IMPLEMENTED_CMD = "ERR_NOT_IMPLEMENTED_CMD";
const char* ERR_UNKNOWN_CMD = "ERR_UNKNOWN_CMD";
const char* OK = "OK";

enum CommandType : uint8_t {
  CMD_ACK = 0x00,
  CMD_ECHO = 0x01,
  CMD_BUTTON = 0x02,
  CMD_LED = 0x03,
  CMD_LCD = 0x04
};
void onMessage(uint8_t type, int len, uint8_t* msg);
bool state = false;

TLVIncomingProtocol incoming(64, onMessage);
TLVOutgoingProtocol outgoing;

SerialProtocol serial(&incoming, &outgoing);

void onMessage(uint8_t type, int len, uint8_t* msg) {

  if (len < 1) return;

  switch (type) {
    case CMD_ECHO:
      {
        String text = "";
        for (int i = 0; i < len; i++) text += (char)msg[i];
        String echo = "ECHO: " + text;
        serial.send(CMD_ACK, echo.c_str());
        break;
      }
    case CMD_BUTTON:
      {
        serial.send(CMD_ACK, ERR_NOT_IMPLEMENTED_CMD);
        break;
      }
    case CMD_LCD:
      {
        serial.send(CMD_ACK, ERR_NOT_IMPLEMENTED_CMD);
        break;
      }
    case CMD_LED:
      {
        if (len >= 2) {
          uint8_t ledPin = msg[0];
          uint8_t state = msg[1];
          digitalWrite(ledPin, state);
          serial.send(CMD_ACK, OK);
        }
        break;
      }
    default:
      {
        serial.send(CMD_ACK, ERR_UNKNOWN_CMD);
        break;
      }
  }
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
