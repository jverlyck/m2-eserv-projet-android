package com.example.jrme.gestionarrosage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jrme.gestionarrosage.activity.MainActivity;
import com.example.jrme.gestionarrosage.entity.Plante;

import java.util.List;

/**
 * Class PlanteAdapter
 * Author Jérôme Verlyck
 * Adapter pour la liste des plantes.
 */
public class PlanteAdapter extends ArrayAdapter<Plante> {

    /**
     * Constructeur PlanteAdapter
     * @param context Le contexte
     * @param plantes La liste des plantes
     */
    public PlanteAdapter(Context context, List<Plante> plantes) {
        super(context, 0, plantes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_plante, parent, false);
        }

        PlanteViewHolder viewHolder = (PlanteViewHolder) convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new PlanteViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.lieu = (TextView) convertView.findViewById(R.id.txt_lieu_ajout);
            convertView.setTag(viewHolder);
        }

        Plante plante = getItem(position);

        viewHolder.name.setText(plante.getName());
        viewHolder.lieu.setText(plante.getLieu());

        convertView.setBackgroundColor(this.getColorBackground(plante));

        return convertView;
    }

    /**
     * Retourne la couleur selon le besoin d'arrosage :
     * - plus d'un jour avant arrosage => Couleur Verte
     * - un jour avant arrosage => Couleur Orange
     * - en retard d'arrosage => Couleur Rouge
     * @param plante La plante que l'on souhaite connaître la couleur
     * @return int Couleur que l'on souhaite mettre
     */
    private int getColorBackground(Plante plante) {
        long diff = Math.abs(MainActivity.APP_DATE.getTime() - plante.getDernierArrosage().getTime());
        int nbJours = (int)(diff / (1000 * 60 * 60 * 24));

        int nbJoursPasArrose = plante.getFrequence() - nbJours;
        if(nbJoursPasArrose > 1) {
            return Color.rgb(173, 207, 79);
        }
        else if(nbJoursPasArrose == 1 || nbJoursPasArrose == 0) {
            return Color.rgb(252, 127, 60);
        }

        return Color.rgb(255, 72, 61);
    }

    private class PlanteViewHolder {
        public TextView name;
        public TextView lieu;
    }
}
