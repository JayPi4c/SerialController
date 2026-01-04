#ifndef PLAIN_PROTOCOL_H
#define PLAIN_PROTOCOL_H

#include <Arduino.h>
#include "ProtocolInterfaces.h"

class PlainIncomingProtocol : public IIncomingProtocol {
public:
  using MessageHandler = void (*)(uint8_t, int, uint8_t*);

  PlainIncomingProtocol(int maxLen, MessageHandler handler);
  void onByte(uint8_t byte) override;
  void reset() override;
private:
  uint8_t _bytesRead;
  uint8_t* _buffer;
  MessageHandler _handler;
};

class PlainOutgoingProtocol : public IOutgoingProtocol {
public:
  void send(uint8_t type, const uint8_t* payload, size_t length) override;
};

#endif
