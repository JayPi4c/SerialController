#ifndef SERIAL_PROTOCOL_H
#define SERIAL_PROTOCOL_H

#include <Arduino.h>

class SerialProtocol {
public:
  SerialProtocol(int maxMsgLen = 64);

  void begin(unsigned long baud = 250000);
  void update();  // call inside loop

  void sendMessage(const uint8_t type, const char* msg);
  void setMessageHandler(void (*handler)(uint8_t, int, uint8_t*));
  static const char* ERR_NOT_IMPLEMENTED_CMD;
  static const char* ERR_UNKNOWN_CMD;
  static const char* OK;

private:
  int _maxMsgLen;
  int _expectedLength;
  int _type;
  int _bytesRead;
  uint8_t* _buffer;
  void (*_messageHandler)(uint8_t, int, uint8_t*);
  void _reset();
};

#endif
