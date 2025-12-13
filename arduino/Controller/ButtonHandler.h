#ifndef BUTTON_HANDLER_H
#define BUTTON_HANDLER_H

#include <Arduino.h>

class ButtonHandler {
public:
  ButtonHandler(uint8_t pin, unsigned long debounceMs = 50);

  void begin();      // call in setup
  bool isPressed();  // call in loop to check flag

private:
  uint8_t _pin;
  unsigned long _debounceDelay;
  volatile bool _pressedFlag;
  volatile unsigned long _lastInterruptTime;

  static void isrWrapper();  // static wrapper for ISR
  void handleISR();

  static ButtonHandler* instance;  // for ISR static access
};

#endif
