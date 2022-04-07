package com.maciejkozuch.dht21pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static com.maciejkozuch.dht21pi4j.SensorState.*;

public class DHT21Pi4j {

    private SensorState state;
    private Instant sensorStarted;

    private Context context;

    public DHT21Pi4j() {
        sensorStarted = Instant.now();
        state = WAITING_TO_START;

        context = Pi4J.newAutoContext();
    }

    public TemperatureAndHumidityValue readValue() {
        waitIfNotInitialized();

        return new TemperatureAndHumidityValue();
    }

    private void waitIfNotInitialized() {
        if(state == WAITING_TO_START) {
            if(Instant.now().getEpochSecond() - sensorStarted.getEpochSecond() >= 1) {
                state = READY;
            } else {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {}
            }
        }
    }

}
