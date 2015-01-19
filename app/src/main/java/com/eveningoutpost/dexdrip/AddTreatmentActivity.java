package com.eveningoutpost.dexdrip;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//public class AddTreatmentActivity extends Activity implements OnItemSelectedListener {
public class AddTreatmentActivity extends Activity {
    private Spinner eating_time_spinner;
    private Button button;
    private String[] state = { "15 Minutes", "30 Minutes", "45 Minutes", "60 minutes"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treatment);
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }

    public void addListenerOnSpinnerItemSelection() {
        eating_time_spinner = (Spinner) findViewById(R.id.eating_time_spinner);
        eating_time_spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        eating_time_spinner.setSelection(position);
        String selState = (String) eating_time_spinner.getSelectedItem();
    }

    public void addListenerOnButton() {
        eating_time_spinner = (Spinner) findViewById(R.id.eating_time_spinner);
        button = (Button) findViewById(R.id.save_treatment_button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.w("Treatment added!", "MESSAGE");

            }

        });
    }


//    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

//    }

}
