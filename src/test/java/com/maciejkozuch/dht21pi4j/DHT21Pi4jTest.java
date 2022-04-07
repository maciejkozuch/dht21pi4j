package com.maciejkozuch.dht21pi4j;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class DHT21Pi4jTest {

    @Test
    public void canReadValueFromSensor() {
        DHT21Pi4j dht21Pi4j = new DHT21Pi4j();
    }
}
