# SerialController

SerialController is an example application to demonstrate the potential of the `JDuino` Library ([GitHub](https://github.com/schlunzis/jduino)).

SerialController provides an Arduino Library which provides the possibility to implement a custom protocol to align with the configuration using `JDuino`.
While it is planned for the library to be moved into a separate repository, the currently recommended approach of using it is to clone this repository and create a symlink from the library path to you `sketchbook/libraries` path:
```shell
ln -s /path/to/repo/arduino/libraries/SerialProtocol /path/to/your/sketchbook/libraries
```

This allows Arduino to automatically detect the latest version of the library when pulled via git.

## Protocols

While `JDuino` (as well as the SerialProtocol Arduino library) comes with the `TLV` protocol by default, this app implements a custom `LTV` protocol to show how to implement a custom protocol.

## Channel

Similar to the custom protocol, a custom `Channel` has been implemented, which does make use of Linux`s way of having character devices. Instead of using a full blown Serial Library, input and output streams on such a device are created to read and write on the channel.

---

While `JDuino` provides excellent starting modules, this app shows, an easy way to add other means of comminucation into the provided framework.