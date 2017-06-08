package com.example.bartlomiej.highthrow.presenter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.bartlomiej.highthrow.Sensor.DistanceCalculator;
import com.example.bartlomiej.highthrow.Sensor.SensorConstants;
import com.example.bartlomiej.highthrow.view.MainView;

/**
 * Created by Bartlomiej on 2017-06-08.
 */
public class MainPresenter implements SensorEventListener {

    private boolean isRecording = false;
    private MainView mainView;
    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;
    private double sum = 0.0;

    public MainPresenter(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;
        initializeSensors();
    }

    private void initializeSensors() {
        sensorManager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        registerSensor();
    }

    public void registerSensor() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterSensor() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isRecording) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            double forceMatch = calculateSummary(x, y, z);
            if (forceMatch < SensorConstants.LOW_MARGIN) {
                double summaryTempValue = DistanceCalculator.calculateDistance(forceMatch - SensorConstants.ACCELERATE);
                sum += summaryTempValue / 4;
                sum *= 100;
                sum = Math.round(sum);
                sum = sum / 100;
                mainView.setNewHeightDistance(sum + "m");
            }
        }
    }

    public void clearSum() {
        sum = 0.0;

    }

    private double calculateSummary(float x, float y, float z) {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    public void setIsRecording(boolean isRecording) {
        if (isRecording) {
            mainView.disableStartRecordingButton();
        } else {
            mainView.enableStartRecordingButton();
        }
        this.isRecording = isRecording;
    }

    public boolean IsRecording() {
        return isRecording;
    }
}
