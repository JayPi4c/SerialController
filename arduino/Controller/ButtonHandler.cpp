#include "ButtonHandler.h"

ButtonHandler* ButtonHandler::instance = nullptr;

ButtonHandler::ButtonHandler(uint8_t pin, unsigned long debounceMs)
  : _pin(pin), _debounceDelay(debounceMs), _pressedFlag(false), _lastInterruptTime(0) {
  instance = this;  // assign singleton for ISR
}

void ButtonHandler::begin() {
  pinMode(_pin, INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(_pin), isrWrapper, FALLING);
}

bool ButtonHandler::isPressed() {
  if (_pressedFlag) {
    _pressedFlag = false;
    return true;
  }
  return false;
}

void ButtonHandler::isrWrapper() {
  if (instance != nullptr) {
    instance->handleISR();
  }
}

void ButtonHandler::handleISR() {
  unsigned long now = millis();
  if (now - _lastInterruptTime > _debounceDelay) {
    _pressedFlag = true;
    _lastInterruptTime = now;
  }
}
