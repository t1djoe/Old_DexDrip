package com.eveningoutpost.dexdrip;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.eveningoutpost.dexdrip.Models.Treatments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddTreatment extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    Button button;
    private String menu_name = "Add Treatment";
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private RadioGroup radioTimeGroup;
    private RadioButton radioTimeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treatment);
        addListenerOnButton();
    }

    protected void onResume() {
        super.onResume();
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), menu_name, this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mNavigationDrawerFragment.swapContext(position);
    }

    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.save_treatment_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                radioTimeGroup = (RadioGroup) findViewById(R.id.radioTime);
                EditText bg_value = (EditText) findViewById(R.id.bg_value);
                String bg_string_value = bg_value.getText().toString();
                double bgValue = 0.0;
                if (!TextUtils.isEmpty(bg_string_value)) {
                    Log.w("bgValue = " + bgValue, "MESSAGE");
                    bgValue = Double.parseDouble(bg_string_value);
                    finish();
                } else {
                    bg_value.setError("BG Can Not be blank");
                }

                EditText carb_value = (EditText) findViewById(R.id.carb_grams);
                String carb_string_value = carb_value.getText().toString();
                double carbValue = 0.0;
                if (!TextUtils.isEmpty(carb_string_value)) {
                    Log.w("carbValue = " + carbValue, "MESSAGE");
                    carbValue = Double.parseDouble(carb_string_value);
                    finish();
                } else {
                    carb_value.setError("BG Can Not be blank");
                }

                EditText insulin_value = (EditText) findViewById(R.id.insulin_units);
                String insulin_string_value = insulin_value.getText().toString();
                double insulinValue = 0.0;
                if (!TextUtils.isEmpty(insulin_string_value)) {
                    Log.w("insulinValue = " + insulinValue, "MESSAGE");
                    insulinValue = Double.parseDouble(insulin_string_value);
                    finish();
                } else {
                    insulin_value.setError("BG Can Not be blank");
                }

                Spinner eating_time_spinner = (Spinner) findViewById(R.id.eating_time_spinner);
                String spinnerValue = eating_time_spinner.getSelectedItem().toString();
                Log.w("spinnerValue = " + spinnerValue, "MESSAGE");
                double spinnerDouble = 0.0;
                switch (spinnerValue){
                    case "Now":         spinnerDouble = 1.0;
                                        break;
                    case "15 Minutes":  spinnerDouble = 2.0;
                                        break;
                    case "30 Minutes":  spinnerDouble = 3.0;
                                        break;
                    case "45 Minutes":  spinnerDouble = 4.0;
                                        break;
                    case "60 Minutes":  spinnerDouble = 5.0;
                                        break;
                    default:            spinnerDouble = -1.0;
                                        break;
                }


                SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
                String currentDateandTime = sdf.format(new Date());
                Log.w("currentDateandTime = " + currentDateandTime, "MESSAGE");

		        // get selected radio button from radioGroup
			    int selectedId = radioTimeGroup.getCheckedRadioButtonId();
                EditText time_value = (EditText) findViewById(R.id.event_time);
			    // find the radiobutton by returned id
		        radioTimeButton = (RadioButton) findViewById(selectedId);
                Log.w("radioTimeButton = " + radioTimeButton.getText(), "MESSAGE");

                //
                if (TextUtils.equals(radioTimeButton.getText(), "Now")) {
                    time_value.setText(currentDateandTime);
                }

                String time_string_value = time_value.getText().toString();
                double treatmentTime = 0.0;
                if (!TextUtils.isEmpty(time_string_value)) {
                    Log.w("timeValue = " + time_string_value, "MESSAGE");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
                    Date convertedDate = new Date();
                    Log.w("convertedDate = " + convertedDate, "MESSAGE");
                    try {
                        convertedDate = dateFormat.parse(time_string_value);
                    } catch (java.text.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    treatmentTime = convertedDate.getTime();
                    finish();
                } else {
                    time_value.setError("Time Can Not be blank");
                }

                Treatments treatment = Treatments.create(bgValue, carbValue, insulinValue, spinnerDouble, treatmentTime, getApplicationContext());
                Log.w("Treatments treatment", "MESSAGE");
                Intent tableIntent = new Intent(v.getContext(), Home.class);
                Log.w("Intent tableintent", "MESSAGE");
                startActivity(tableIntent);
                finish();
            }
        });

    }
}
