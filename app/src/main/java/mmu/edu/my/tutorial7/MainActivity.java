package mmu.edu.my.tutorial7;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor sensor;
    private Vibrator vibrator;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.cancel();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float accel_x = sensorEvent.values[0];
        float accel_y = sensorEvent.values[1];
        float accel_z = sensorEvent.values[2];
        double magnitude = Math.sqrt(accel_x*accel_x + accel_y*accel_y + accel_z*accel_z);
        textView.setText("Current magnitude="+String.valueOf(magnitude) + "i"+"Gravity="+String.valueOf(SensorManager.GRAVITY_EARTH));
        if(magnitude > 3*SensorManager.GRAVITY_EARTH-5) {
            long[] pattern = {0, 1000, 500, 2000, 500, 1000};
            vibrator.vibrate(pattern,1);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this,sensor);
    }
}