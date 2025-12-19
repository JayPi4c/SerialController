#ifndef PROTOCOL_INTERFACES_H
#define PROTOCOL_INTERFACES_H

#include <Arduino.h>

class IIncomingProtocol {
public:
  virtual ~IIncomingProtocol() = default;
  virtual void onByte(uint8_t byte) = 0;
  virtual void reset() = 0;
};

class IOutgoingProtocol {
public:
  virtual ~IOutgoingProtocol() = default;
  virtual void send(uint8_t type, const uint8_t* payload, size_t length) = 0;
};

#endif
