package com.example.jrme.gestionarrosage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jrme.gestionarrosage.fixtures.LoadPlanteData;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PlanteDatabase planteDatabase;

    private ListView list_plantes;
    private Button btn_fixtures;
    private Button btn_ajout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.planteDatabase = new PlanteDatabase(getApplicationContext());

        this.loadWidget();
        this.loadEvent();
        this.loadListPlantes();
    }

    /**
     * Charge les différents widgets de l'activité
     */
    private void loadWidget() {
        this.list_plantes = (ListView) findViewById(R.id.list_plantes);
        this.btn_fixtures = (Button) findViewById(R.id.btn_fixtures);
        this.btn_ajout    = (Button) findViewById(R.id.btn_ajout);
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

            }
        });

        this.list_plantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                /*if(o instanceof Plante)
                    Toast.makeText(MainActivity.this, "oui", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "non", Toast.LENGTH_SHORT).show();*/
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
