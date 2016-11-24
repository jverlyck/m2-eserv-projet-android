package com.example.jrme.gestionarrosage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Class UpdateActivity
 * Author Jérôme Verlyck
 * Activité d'ajout d'une nouvelle plante
 */
public class UpdateActivity extends AppCompatActivity {

    private PlanteDatabase planteDatabase;
    private int id_plante;

    private EditText txt_nom, txt_frequence, txt_lieu;
    private Button btn_modifier, btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        this.planteDatabase = new PlanteDatabase(getApplicationContext());

        this.loadWidget();
        this.loadEvent();

        this.initChamps();
    }

    /**
     * Charge les différents widgets de l'activité
     */
    private void loadWidget() {
        this.txt_nom          = (EditText)  findViewById(R.id.txt_nom_update);
        this.txt_frequence    = (EditText)  findViewById(R.id.txt_frequence_update);
        this.txt_lieu         = (EditText)  findViewById(R.id.txt_lieu_update);

        this.btn_reset    = (Button) findViewById(R.id.btn_reset_update);
        this.btn_modifier = (Button) findViewById(R.id.btn_modifier_update);
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

        this.btn_modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txt_nom.getText().toString();
                String frequence = txt_frequence.getText().toString();
                String lieu = txt_lieu.getText().toString();

                if(nom.length() > 0 && frequence.length() > 0 && lieu.length() > 0) {
                    Plante plante = planteDatabase.unique(id_plante);
                    planteDatabase.update(id_plante, nom, Integer.parseInt(frequence), lieu, plante.getDernierArrosage());

                    Intent intent = new Intent();
                    intent.putExtra(DetailActivity.MESSAGE_INFO, "Plante \"" + nom + "\" modifié.");
                    UpdateActivity.this.setResult(1, intent);
                    UpdateActivity.this.finish();
                }
                else {
                    Toast.makeText(UpdateActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Initialise les champs lié à la plante dont on a récupéré l'id de
     * la précédente activité.
     */
    private void initChamps() {
        final Intent intent = getIntent();
        this.id_plante = intent.getIntExtra(MainActivity.MESSAGE_INFO, 0);

        if(this.id_plante != 0) {
            Plante plante = this.planteDatabase.unique(this.id_plante);
            this.txt_nom.setText(plante.getName());
            this.txt_frequence.setText(String.valueOf(plante.getFrequence()));
            this.txt_lieu.setText(plante.getLieu());
        }
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
