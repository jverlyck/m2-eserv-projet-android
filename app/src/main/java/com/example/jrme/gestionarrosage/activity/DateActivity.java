package com.example.jrme.gestionarrosage.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.jrme.gestionarrosage.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class DateActivity
 * Author Jérôme Verlyck
 * Activité pour le changement de date utilisé pour les tests de l'application.
 */
public class DateActivity extends AppCompatActivity {

    private DatePicker dp_date;
    private Button btn_confirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        this.loadWidget();
        this.loadEvent();
        this.init();
    }

    /**
     * Charge les différents widgets de l'activité
     */
    private void loadWidget() {
        this.dp_date       = (DatePicker) findViewById(R.id.dp_select_date);
        this.btn_confirmer = (Button) findViewById(R.id.btn_confirmer_date);
    }

    /**
     * Charge les différents événements de l'activité
     */
    private void loadEvent() {
        this.btn_confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jour = dp_date.getDayOfMonth();
                int mois = dp_date.getMonth();
                int annee = dp_date.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(annee, mois, jour);

                MainActivity.APP_DATE = calendar.getTime();

                Intent intent = new Intent();
                intent.putExtra(MainActivity.MESSAGE_INFO, "Date changée");
                DateActivity.this.setResult(1, intent);
                DateActivity.this.finish();
            }
        });
    }

    /**
     * Initialise le datepicker avec la date choisi
     */
    private void init() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(MainActivity.APP_DATE);
        this.dp_date.updateDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
    }
}
