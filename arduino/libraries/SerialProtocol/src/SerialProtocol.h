#ifndef SERIAL_PROTOCOL_H
#define SERIAL_PROTOCOL_H

#include <Arduino.h>
#include "ProtocolInterfaces.h"

class SerialProtocol {
public:
  SerialProtocol(IIncomingProtocol* in, IOutgoingProtocol* out);

  void begin(unsigned long baud = 9600);
  void update();
  void send(uint8_t type, const uint8_t* data, size_t len);
  void send(uint8_t type, const char* msg);

private:
  IIncomingProtocol* _incoming;
  IOutgoingProtocol* _outgoing;
};

#endif
