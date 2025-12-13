#include "ButtonHandler.h"
#include "SerialProtocol.h"

ButtonHandler button(3, 50);     // pin 3, debounce 50ms
SerialProtocol serialProto(64);  // max message length 64

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
    serialProto.sendMessage("Button Pressed!");
  }
}

enum CommandType : uint8_t {
  CMD_ECHO = 0x01,
  CMD_BUTTON = 0x02,
  CMD_LED = 0x03,
  CMD_LCD = 0x04
};

// Callback for incoming messages
void handleMessage(uint8_t* msg, int len) {

  if (len < 1) return;

  uint8_t type = msg[0];

  switch (type) {
    case CMD_ECHO:
      if (len >= 2) {
        String text = "";
        for (int i = 1; i < len; i++) text += (char)msg[i];
        String echo = "ECHO: " + text;
        serialProto.sendMessage(echo.c_str());
      }
      break;
    case CMD_BUTTON:
      serialProto.sendMessage(SerialProtocol::ERR_NOT_IMPLEMENTED_CMD);
      break;
    case CMD_LCD:
      serialProto.sendMessage(SerialProtocol::ERR_NOT_IMPLEMENTED_CMD);
      break;
    case CMD_LED:
      if (len >= 3) {
        uint8_t ledPin = msg[1];
        uint8_t state = msg[2];
        digitalWrite(ledPin, state);
        serialProto.sendMessage(SerialProtocol::OK);
      }
      break;
    default:
      serialProto.sendMessage(SerialProtocol::ERR_UNKNOWN_CMD);
      break;
  }
}
