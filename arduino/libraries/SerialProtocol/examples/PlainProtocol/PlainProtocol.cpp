#include "PlainProtocol.h"

PlainIncomingProtocol::PlainIncomingProtocol(
  int maxLen,
  MessageHandler handler
)
  : _handler(handler) {
  _buffer = new uint8_t[maxLen];
  reset();
}

void PlainIncomingProtocol::onByte(uint8_t b) {
  if(b == '\n' || b == '\r') {
    // End of message
    _handler(0, _bytesRead, _buffer);
    reset();
  } else {
    _buffer[_bytesRead++] = b;
  }
}

void PlainIncomingProtocol::reset() {
  _bytesRead = 0;
}

void PlainOutgoingProtocol::send(
  uint8_t type,
  const uint8_t* payload,
  size_t length
) {
  Serial.write(payload, length);
  Serial.write('\n');
}
