#include "SerialProtocol.h"

const char* SerialProtocol::ERR_NOT_IMPLEMENTED_CMD = "ERR_NOT_IMPLEMENTED_CMD";
const char* SerialProtocol::ERR_UNKNOWN_CMD = "ERR_UNKNOWN_CMD";
const char* SerialProtocol::OK = "OK";


SerialProtocol::SerialProtocol(
  IIncomingProtocol* in,
  IOutgoingProtocol* out
)
  : _incoming(in), _outgoing(out) {}

void SerialProtocol::begin(unsigned long baud) {
  Serial.begin(baud);
}

void SerialProtocol::update() {
  while (Serial.available() > 0) {
    _incoming->onByte(Serial.read());
  }
}

void SerialProtocol::send(uint8_t type, const uint8_t* data, size_t len) {
  _outgoing->send(type, data, len);
}

void SerialProtocol::send(uint8_t type, const char* msg){
  uint8_t len = strlen(msg);
  send(type, (const uint8_t*)msg, len);
}
