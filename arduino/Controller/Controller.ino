#include "ButtonHandler.h"
#include "SerialProtocol.h"

ButtonHandler button(3, 50);     // pin 3, debounce 50ms
SerialProtocol serialProto(64);  // max message length 64

enum CommandType : uint8_t {
  CMD_ACK = 0x00,
  CMD_ECHO = 0x01,
  CMD_BUTTON = 0x02,
  CMD_LED = 0x03,
  CMD_LCD = 0x04
};

void setup() {
  // LED setup
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);

  button.begin();
  serialProto.begin();
  serialProto.setMessageHandler(handleMessage);
}

void loop() {
  serialProto.update();

  if (button.isPressed()) {
    serialProto.sendMessage(CMD_BUTTON, "Button Pressed!");
  }
}


// Callback for incoming messages
void handleMessage(uint8_t type, int len, uint8_t* msg) {

  if (len < 1) return;

  switch (type) {
    case CMD_ECHO:
      {
        String text = "";
        for (int i = 0; i < len; i++) text += (char)msg[i];
        String echo = "ECHO: " + text;
        serialProto.sendMessage(CMD_ACK, echo.c_str());
        break;
      }
    case CMD_BUTTON:
      {
        serialProto.sendMessage(CMD_ACK, SerialProtocol::ERR_NOT_IMPLEMENTED_CMD);
        break;
      }
    case CMD_LCD:
      {
        serialProto.sendMessage(CMD_ACK, SerialProtocol::ERR_NOT_IMPLEMENTED_CMD);
        break;
      }
    case CMD_LED:
      {
        if (len >= 2) {
          uint8_t ledPin = msg[0];
          uint8_t state = msg[1];
          digitalWrite(ledPin, state);
          serialProto.sendMessage(CMD_ACK, SerialProtocol::OK);
        }
        break;
      }
    default:
      {
        serialProto.sendMessage(CMD_ACK, SerialProtocol::ERR_UNKNOWN_CMD);
        break;
      }
  }
}
/*
char out[64];
buildMessage(type, msg, len, out, 64);
serialProto.sendMessage(CMD_ACK, out);

void buildMessage(
  uint8_t type,
  const uint8_t* msg,
  int len,
  char* out,
  size_t outSize) {
  char* p = out;
  size_t remaining = outSize;

  // Header: "type|len|"
  int written = snprintf(p, remaining, "%d|%d|", type, len);
  p += written;
  remaining -= written;

  // Payload: "1,164,255"
  for (int i = 0; i < len; i++) {
    written = snprintf(
      p,
      remaining,
      "%d%s",
      msg[i],
      (i < len - 1) ? "," : "");

    p += written;
    remaining -= written;
  }
}
*/