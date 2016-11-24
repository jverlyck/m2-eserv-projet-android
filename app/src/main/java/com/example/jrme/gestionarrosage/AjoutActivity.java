package com.example.jrme.gestionarrosage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Class AjoutActivity
 * Author Jérôme Verlyck
 * Activité d'ajout d'une nouvelle plante
 */
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
        this.txt_nom          = (EditText)  findViewById(R.id.txt_nom_ajout);
        this.txt_frequence    = (EditText)  findViewById(R.id.txt_frequence_ajout);
        this.txt_lieu         = (EditText)  findViewById(R.id.txt_lieu_ajout);

        this.btn_reset   = (Button) findViewById(R.id.btn_reset_ajout);
        this.btn_ajouter = (Button) findViewById(R.id.btn_ajouter_ajout);
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
                String nom = txt_nom.getText().toString();
                String frequence = txt_frequence.getText().toString();
                String lieu = txt_lieu.getText().toString();

                if(nom.length() > 0 && frequence.length() > 0 && lieu.length() > 0) {
                    planteDatabase.add(nom,Integer.parseInt(frequence), lieu);

                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.MESSAGE_INFO, "Plante \"" + nom + "\" ajouté.");
                    AjoutActivity.this.setResult(1, intent);
                    AjoutActivity.this.finish();
                }
                else {
                    Toast.makeText(AjoutActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
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
