package com.example.bartlomiej.highthrow.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bartlomiej.highthrow.R;
import com.example.bartlomiej.highthrow.presenter.MainPresenter;
import com.example.bartlomiej.highthrow.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private TextView sum;
    private Button clearSumButton, switchRecordMode;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUIViews();
        initListeners();

        presenter = new MainPresenter(this, this);
    }

    private void initializeUIViews() {
        sum = (TextView) findViewById(R.id.sum);
        clearSumButton = (Button) findViewById(R.id.clear_button);
        switchRecordMode = (Button) findViewById(R.id.start_recording_button);
    }

    private void initListeners() {
        switchRecordMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setIsRecording(!presenter.IsRecording());
            }
        });
        clearSumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clearSum();
                sum.setText("0 m");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.registerSensor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unregisterSensor();
    }

    @Override
    public void setNewHeightDistance(String newValue) {
        sum.setText(newValue);
    }

    @Override
    public void enableStartRecordingButton() {
        switchRecordMode.setText(R.string.start_record_button_label);
    }

    @Override
    public void disableStartRecordingButton() {
        switchRecordMode.setText(R.string.stop_record_button_label);
    }
}
