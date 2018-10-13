# DFC friendly SLF4J
The Simple Logging Facade for Java (SLF4J) serves as a simple facade or abstraction for various logging frameworks (e.g. java.util.logging, logback, log4j) allowing the end user to plug in the desired logging framework at deployment time.
More information can be found on the [SLF4J website](http://www.slf4j.org).

This is fork of [SLF4J](https://github.com/qos-ch/slf4j) with modified `log4j-over-slf4j` bridge version `1.7.25` only to be compatible with Documentum DFC libraries which will not work with original bridge.
**If you do not know what DFC is this is probably not what you looking for and you should head to orginal [SLF4J](https://github.com/qos-ch/slf4j) project instead.**