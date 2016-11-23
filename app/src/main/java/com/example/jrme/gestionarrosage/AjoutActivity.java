package com.example.jrme.gestionarrosage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AjoutActivity extends AppCompatActivity {

    private PlanteDatabase planteDatabase;

    private EditText txt_nom, txt_frequence, txt_lieu;
    private Button btn_ajouter, btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        this.planteDatabase = new PlanteDatabase(getApplicationContext());

        this.loadWidget();
        this.loadEvent();
    }

    /**
     * Charge les différents widgets de l'activité
     */
    private void loadWidget() {
        this.txt_nom          = (EditText)  findViewById(R.id.txt_nom);
        this.txt_frequence    = (EditText)  findViewById(R.id.txt_frequence);
        this.txt_lieu         = (EditText)  findViewById(R.id.txt_lieu);

        this.btn_reset   = (Button) findViewById(R.id.btn_reset);
        this.btn_ajouter = (Button) findViewById(R.id.btn_ajouter);
    }

    /**
     * Charge les différents événements de l'activité
     */
    private void loadEvent() {
        this.btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        this.btn_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planteDatabase.add(
                    txt_nom.getText().toString(),
                    Integer.parseInt(txt_frequence.getText().toString()),
                    txt_lieu.getText().toString()
                );

                Intent intent = new Intent();
                intent.putExtra(MainActivity.MESSAGE_INFO, "Plante \"" + txt_nom.getText() + "\" ajouté.");
                AjoutActivity.this.setResult(1, intent);
                AjoutActivity.this.finish();
            }
        });
    }

    /**
     * Méthode de remise des champs vide
     */
    private void reset() {
        this.txt_nom.setText("");
        this.txt_frequence.setText("");
        this.txt_lieu.setText("");
    }
}
