package com.example.jrme.gestionarrosage.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jrme.gestionarrosage.entity.Plante;
import com.example.jrme.gestionarrosage.repository.PlanteDatabase;
import com.example.jrme.gestionarrosage.R;

/**
 * Class DetailActivity
 * Author Jérôme Verlyck
 * Activité sur le détail d'une plante en particulier.
 */
public class DetailActivity extends AppCompatActivity {

    public final static String MESSAGE_INFO = "Information";

    private PlanteDatabase planteDatabase;
    private int id_plante;

    private TextView lb_nom_detail, lb_frequence_detail, lb_lieu_detail, lb_date_arrosage;
    private Button btn_delete, btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.planteDatabase = new PlanteDatabase(getApplicationContext());

        this.loadWidget();
        this.loadEvent();

        this.initChamps();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1) {
            this.initChamps();

            String message = data.getStringExtra(MESSAGE_INFO);
            Toast.makeText(DetailActivity.this, message, Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Charge les différents widgets de l'activité
     */
    private void loadWidget() {
        this.lb_nom_detail = (TextView) findViewById(R.id.lb_nom_valeur_detail);
        this.lb_frequence_detail = (TextView) findViewById(R.id.lb_frequence_valeur_detail);
        this.lb_lieu_detail = (TextView) findViewById(R.id.lb_lieu_valeur_detail);
        this.lb_date_arrosage = (TextView) findViewById(R.id.lb_date_arrosage_valeur_detail);

        this.btn_delete = (Button) findViewById(R.id.btn_delete_detail);
        this.btn_update = (Button) findViewById(R.id.btn_update_detail);
    }

    /**
     * Charge les différents événements de l'activité
     */
    private void loadEvent() {
        this.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planteDatabase.delete(id_plante);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.MESSAGE_INFO, "Plante supprimée.");
                DetailActivity.this.setResult(1, intent);
                DetailActivity.this.finish();
            }
        });

        this.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class);
                intent.putExtra(MESSAGE_INFO, id_plante);
                startActivityForResult(intent, 0);
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
            this.lb_nom_detail.setText(plante.getName());
            String frequence = String.valueOf(plante.getFrequence()) + " jour(s)";
            this.lb_frequence_detail.setText(frequence);
            this.lb_lieu_detail.setText(plante.getLieu());
            this.lb_date_arrosage.setText(plante.getDateArrosage());
        }
    }
}
