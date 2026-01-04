# SerialController

SerialController is an example application to demonstrate the potential of the `JDuino`
Library ([GitHub](https://github.com/schlunzis/jduino)).

To connect with a microcontroller, you can use the (SerialProtocol)[https://github.com/JayPi4c/SerialProtocol] Arduino
library, which provides a simple way to communicate over a serial connection. See its documentation for more details.

## Protocols

While `JDuino` (as well as the SerialProtocol Arduino library) comes with the `TLV` protocol by default, this app
implements a custom `LTV` protocol to show how to implement a custom protocol.

## Channel

Similar to the custom protocol, a custom `Channel` has been implemented, which does make use of Linux’s way of having
character devices. Instead of using a full-blown Serial Library, input and output streams on such a device are created
to read and write on the channel.

---

While `JDuino` provides excellent starting modules, this app shows an easy way to add other means of communication into
the provided framework.