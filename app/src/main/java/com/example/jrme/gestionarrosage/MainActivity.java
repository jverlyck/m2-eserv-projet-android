package com.example.jrme.gestionarrosage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jrme.gestionarrosage.fixtures.LoadPlanteData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private ListView list_plantes;
    private Button btn_fixtures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = getApplicationContext();

        this.loadWidget();
        this.loadListPlantes();
    }

    /**
     * Charge les différents widgets de l'activité
     */
    private void loadWidget() {
        this.list_plantes = (ListView) findViewById(R.id.list_plantes);
        this.btn_fixtures = (Button) findViewById(R.id.btn_fixtures);
    }

    /**
     * Charge la liste des plantes dans la listView
     */
    private void loadListPlantes() {
        PlanteDatabase planteDatabase = new PlanteDatabase(this.context);
        List<Plante> plantes = planteDatabase.list();

        Collections.sort(plantes);

        PlanteAdapter adapter = new PlanteAdapter(MainActivity.this, plantes);
        this.list_plantes.setAdapter(adapter);
    }

    /**
     * Méthode d'ajout automatique de plantes à l'aide de fixtures
     * @param view
     */
    public void preLoadPlantes(View view) {
        LoadPlanteData planteData = new LoadPlanteData();
        planteData.load(this.context);

        this.loadListPlantes();

        Toast.makeText(this, "Ajout des plantes auto effectué", Toast.LENGTH_SHORT).show();
    }
}
