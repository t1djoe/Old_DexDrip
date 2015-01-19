package com.eveningoutpost.dexdrip.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.eveningoutpost.dexdrip.Sensor;
import com.eveningoutpost.dexdrip.UtilityModels.BgSendQueue;
import com.eveningoutpost.dexdrip.UtilityModels.CalibrationSendQueue;
import com.eveningoutpost.dexdrip.UtilityModels.Notifications;
import com.eveningoutpost.dexdrip.UtilityModels.TreatmentSendQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by stephenblack on 10/29/14.
 */
@Table(name = "Treatments", id = BaseColumns._ID)
public class Treatments extends Model {
    private final static String TAG = Treatments.class.getSimpleName();

    @Expose
    @Column(name = "bg")
    public double bg;

    @Expose
    @Column(name = "carbs")
    public double carbs;

    @Expose
    @Column(name = "insulin")
    public double insulin;

    @Expose
    @Column(name = "eating_time")
    public double eating_time;

    @Expose
    @Column(name = "treatment_time")
    public double treatment_time;

    public static Treatments create(double bg, double carbs, double insulin, double eatTime, double treatTime, Context context) {

        Treatments treatment = new Treatments();

        treatment.bg = bg;
        treatment.carbs = carbs;
        treatment.insulin = insulin;
        treatment.eating_time = eatTime;
        treatment.treatment_time = treatTime;

        TreatmentSendQueue.addToQueue(treatment, "update", context);
        return(treatment);
    }

    public static Treatments last() {
        Sensor sensor = Sensor.currentSensor();
        return new Select()
                .from(Treatments.class)
                .where("Sensor = ? ", sensor.getId())
                .orderBy("_ID desc")
                .executeSingle();
    }

    public static Treatments first() {
        Sensor sensor = Sensor.currentSensor();
        return new Select()
                .from(Treatments.class)
                .where("Sensor = ? ", sensor.getId())
                .orderBy("_ID asc")
                .executeSingle();
    }
    public static double max() {
        Sensor sensor = Sensor.currentSensor();
        Treatments calibration = new Select()
                .from(Treatments.class)
                .where("Sensor = ? ", sensor.getId())
                .where("slope_confidence != 0")
                .where("sensor_confidence != 0")
                .where("timestamp > ?", (new Date().getTime() - (60000 * 60 * 24 * 5)))
                .orderBy("bg asc")
                .executeSingle();
        return calibration.bg;
    }

    public static double min() {
        Sensor sensor = Sensor.currentSensor();
        Treatments calibration = new Select()
                .from(Treatments.class)
                .where("Sensor = ? ", sensor.getId())
                .where("slope_confidence != 0")
                .where("sensor_confidence != 0")
                .where("timestamp > ?", (new Date().getTime() - (60000 * 60 * 24 * 5)))
                .orderBy("bg asc")
                .executeSingle();
        return calibration.bg;
    }

    public static List<Treatments> latest(int number) {
        Sensor sensor = Sensor.currentSensor();
        if (sensor == null) { return null; }
        return new Select()
                .from(Treatments.class)
                .where("Sensor = ? ", sensor.getId())
                .orderBy("_ID desc")
                .limit(number)
                .execute();
    }

    public static List<Treatments> allForSensor() {
        Sensor sensor = Sensor.currentSensor();
        if (sensor == null) { return null; }
        return new Select()
                .from(Treatments.class)
                .where("Sensor = ? ", sensor.getId())
                .where("slope_confidence != 0")
                .where("sensor_confidence != 0")
                .orderBy("_ID desc")
                .execute();
    }

    public static List<Treatments> allForSensorInLastFiveDays() {
        Sensor sensor = Sensor.currentSensor();
        if (sensor == null) { return null; }
        return new Select()
                .from(Treatments.class)
                .where("Sensor = ? ", sensor.getId())
                .where("slope_confidence != 0")
                .where("sensor_confidence != 0")
                .where("timestamp > ?", (new Date().getTime() - (60000 * 60 * 24 * 5)))
                .orderBy("_ID desc")
                .execute();
    }

//    private static List<Treatments> calibrations_for_sensor(Sensor sensor) {
//        return new Select()
//                .from(Treatments.class)
//                .where("Sensor = ?", sensor.getId())
//                .where("slope_confidence != 0")
//                .where("sensor_confidence != 0")
//                .orderBy("_ID desc")
//                .execute();
//    }

    public String toS() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .serializeSpecialFloatingPointValues()
                .create();
        return gson.toJson(this);
    }

}
