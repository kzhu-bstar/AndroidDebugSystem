package pig.dream.adstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pig.dream.androiddebugsystem.ADS;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ADS.initialize(getApplicationContext());
    }
}
