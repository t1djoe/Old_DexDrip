package com.eveningoutpost.dexdrip.Models;

import android.content.Context;
import android.provider.BaseColumns;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.eveningoutpost.dexdrip.Sensor;
import com.eveningoutpost.dexdrip.UtilityModels.TreatmentSendQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by stephenblack on 10/29/14.
 */
@Table(name = "Treatments", id = BaseColumns._ID)
public class Treatments extends Model {

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
    @Column(name = "treatment_time", index = true)
    public double treatment_time;

    public static Treatments create(double bg, double carbs, double insulin, double eatTime, double treatTime, Context context) {

        Treatments treatment = new Treatments();

        treatment.bg = bg;
        treatment.carbs = carbs;
        treatment.insulin = insulin;
        treatment.eating_time = eatTime;
        treatment.treatment_time = treatTime;
        treatment.save();

        TreatmentSendQueue.addToQueue(treatment, "create", context);
        return(treatment);
    }

}
