#include "SerialProtocol.h"

const char* SerialProtocol::ERR_NOT_IMPLEMENTED_CMD = "ERR_NOT_IMPLEMENTED_CMD";
const char* SerialProtocol::ERR_UNKNOWN_CMD = "ERR_UNKNOWN_CMD";
const char* SerialProtocol::OK = "OK";



SerialProtocol::SerialProtocol(int maxMsgLen)
  : _maxMsgLen(maxMsgLen), _expectedLength(-1), _bytesRead(0), _messageHandler(nullptr), _type(-1) {
  _buffer = new uint8_t[_maxMsgLen];
}

void SerialProtocol::begin(unsigned long baud) {
  Serial.begin(baud);
}

void SerialProtocol::setMessageHandler(void (*handler)(uint8_t, int, uint8_t*)) {
  _messageHandler = handler;
}

void SerialProtocol::update() {
  while (Serial.available() > 0) {
    uint8_t b = Serial.read();

    if (_type < 0) {
      _type = b;
    } else if (_expectedLength < 0) {
      _expectedLength = b;
      _bytesRead = 0;
      if (_expectedLength > _maxMsgLen) {
        // expected message too long, discard
        // TODO: discard _expectedLength of bytes as payload should not be wrongly interpreted
        _reset();
      }
    } else {
      _buffer[_bytesRead++] = b;

      if (_bytesRead == _expectedLength) {
        if (_messageHandler != nullptr) {
          _messageHandler(_type, _expectedLength, _buffer);
        }
        _reset();
      }
    }
  }
}

void SerialProtocol::_reset() {
  _type = -1;
  _expectedLength = -1;
  _bytesRead = 0;
}

void SerialProtocol::sendMessage(const uint8_t type, const char* msg) {
  uint8_t len = strlen(msg);
  Serial.write(type);
  Serial.write(len);
  Serial.write((const uint8_t*)msg, len);
}
