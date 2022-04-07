package com.maciejkozuch.dht21pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static com.maciejkozuch.dht21pi4j.SensorState.*;

public class DHT21Pi4j implements AutoCloseable {

    private SensorState state;
    private Instant sensorStarted;

    private Context context;

    final Logger logger = LoggerFactory.getLogger(DHT21Pi4j.class);

    public DHT21Pi4j() {
        logger.trace("The DHT21 sensor is being initialized.");
        sensorStarted = Instant.now();
        state = WAITING_TO_START;

        context = Pi4J.newAutoContext();
        logger.debug("The DHT21 sensor was initialized.");
    }

    public TemperatureAndHumidityValue readValue() {
        waitIfNotInitialized();

        logger.debug("The temperature and humidity was red from the sensor.");
        return new TemperatureAndHumidityValue();
    }

    @Override
    public void close() {
        context.shutdown();
        logger.debug("The DHT21 sensor was closed.");
    }

    public static void main(String[] args) {
        try (DHT21Pi4j dht21Pi4j = new DHT21Pi4j()) {
            dht21Pi4j.readValue();
        }
    }

    private void waitIfNotInitialized() {
        if(state == WAITING_TO_START) {
            if(Instant.now().getEpochSecond() - sensorStarted.getEpochSecond() >= 1) {
                state = READY;
            } else {
                logger.debug("The system is waiting 1s to prepare the DHT21 sensor.");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {}
            }
        }
    }
}
