#ifndef TLV_PROTOCOL_H
#define TLV_PROTOCOL_H

#include <Arduino.h>
#include "ProtocolInterfaces.h"

class TLVIncomingProtocol : public IIncomingProtocol {
public:
  using MessageHandler = void (*)(uint8_t, int, uint8_t*);

  TLVIncomingProtocol(int maxLen, MessageHandler handler);

  void onByte(uint8_t byte) override;
  void reset() override;

private:
  int _maxLen;
  int _expectedLength;
  int _type;
  int _bytesRead;
  uint8_t* _buffer;
  MessageHandler _handler;
};

class TLVOutgoingProtocol : public IOutgoingProtocol {
public:
  void send(uint8_t type, const uint8_t* payload, size_t length) override;
};

#endif
