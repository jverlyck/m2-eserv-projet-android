package com.example.jrme.gestionarrosage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jrme.gestionarrosage.PlanteAdapter;
import com.example.jrme.gestionarrosage.R;
import com.example.jrme.gestionarrosage.entity.Plante;
import com.example.jrme.gestionarrosage.fixtures.LoadPlanteData;
import com.example.jrme.gestionarrosage.repository.PlanteDatabase;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class MainActivity
 * Author Jérôme Verlyck
 * Le point d'entrée de l'application affichant la liste des plantes.
 */
public class MainActivity extends AppCompatActivity {

    public static Date APP_DATE = new Date();

    public final static String MESSAGE_INFO = "Information";

    private PlanteDatabase planteDatabase;

    private ListView list_plantes;
    private FloatingActionButton btn_fixtures, btn_date, btn_ajout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.planteDatabase = new PlanteDatabase(getApplicationContext());

        this.loadWidget();
        this.loadEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.loadListPlantes();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1) {
            String message = data.getStringExtra(MESSAGE_INFO);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Charge les différents widgets de l'activité
     */
    private void loadWidget() {
        this.list_plantes = (ListView) findViewById(R.id.list_plantes_main);
        this.btn_fixtures = (FloatingActionButton) findViewById(R.id.btn_fixtures_main);
        this.btn_ajout    = (FloatingActionButton) findViewById(R.id.btn_ajout_main);
        this.btn_date     = (FloatingActionButton) findViewById(R.id.btn_date_main);
    }

    /**
     * Charge les différents événements de l'activité
     */
    private void loadEvent() {
        this.btn_fixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preLoadPlantes();
            }
        });

        this.btn_ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AjoutActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        this.btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DateActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        this.list_plantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plante plante = (Plante) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(MESSAGE_INFO, plante.getId());
                startActivityForResult(intent, 0);
            }
        });

        this.list_plantes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plante plante = (Plante) adapterView.getItemAtPosition(i);
                plante.arrosage(planteDatabase);

                Toast.makeText(
                    MainActivity.this,
                    "Arrosage " + plante.getName() + " effectué.",
                    Toast.LENGTH_SHORT
                ).show();

                loadListPlantes();
                return true;
            }
        });
    }

    /**
     * Charge la liste des plantes dans la listView
     */
    private void loadListPlantes() {
        List<Plante> plantes = this.planteDatabase.list();

        //Tri les plantes en fonction de la demande d'arrosage
        Collections.sort(plantes);

        PlanteAdapter adapter = new PlanteAdapter(MainActivity.this, plantes);
        this.list_plantes.setAdapter(adapter);
    }

    /**
     * Méthode d'ajout automatique de plantes à l'aide de fixtures
     */
    public void preLoadPlantes() {
        LoadPlanteData planteData = new LoadPlanteData();
        planteData.load(this.planteDatabase);

        this.loadListPlantes();

        Toast.makeText(this, "Ajout des plantes auto effectué", Toast.LENGTH_SHORT).show();
    }
}
