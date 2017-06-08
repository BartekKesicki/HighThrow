package com.example.bartlomiej.highthrow.Sensor;

/**
 * Created by Bartlomiej on 2017-06-08.
 */
public class DistanceCalculator {
    public static double calculateDistance(double accelerate) {
        double result = 0;
        accelerate = (accelerate < 0) ? - accelerate : accelerate;
        result = (accelerate * 0.2) / 2;
        return result;
    }
}
