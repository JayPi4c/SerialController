#include "LTVProtocol.h"

LTVIncomingProtocol::LTVIncomingProtocol(
  int maxLen,
  MessageHandler handler
)
  : _maxLen(maxLen), _handler(handler) {
  _buffer = new uint8_t[_maxLen];
  reset();
}

void LTVIncomingProtocol::onByte(uint8_t b) {
  if (_expectedLength < 0) {
    _expectedLength = b;
    _bytesRead = 0;
    if (_expectedLength > _maxLen) {
      reset();
    }
  }
  else if (_type < 0) {
    _type = b;
  }
  else {
    _buffer[_bytesRead++] = b;
    if (_bytesRead == _expectedLength) {
      _handler(_type, _expectedLength, _buffer);
      reset();
    }
  }
}

void LTVIncomingProtocol::reset() {
  _expectedLength = -1;
  _type = -1;
  _bytesRead = 0;
}

void LTVOutgoingProtocol::send(
  uint8_t type,
  const uint8_t* payload,
  size_t length
) {
  Serial.write((uint8_t)length);
  Serial.write(type);
  Serial.write(payload, length);
}
