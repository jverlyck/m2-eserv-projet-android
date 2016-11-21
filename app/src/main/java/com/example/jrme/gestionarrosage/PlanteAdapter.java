package com.example.jrme.gestionarrosage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class PlanteAdapter extends ArrayAdapter<Plante> {

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
            viewHolder.lieu = (TextView) convertView.findViewById(R.id.txt_lieu);
            convertView.setTag(viewHolder);
        }

        Plante plante = getItem(position);

        viewHolder.name.setText(plante.getName());
        viewHolder.lieu.setText(plante.getLieu());

        convertView.setBackgroundColor(this.getColorBackground(plante));

        return convertView;
    }

    private int getColorBackground(Plante plante) {
        long diff = Math.abs(new Date().getTime() - plante.getDernierArrosage().getTime());
        int nbJours = (int)(diff / (1000 * 60 * 60 * 24));

        int nbJoursPasArrose = plante.getFrequence() - nbJours;
        if(nbJoursPasArrose > 1) {
            return Color.rgb(173, 207, 79);
        }
        else if(nbJoursPasArrose == 1) {
            return Color.rgb(252, 127, 60);
        }

        return Color.rgb(255, 72, 61);
    }

    private class PlanteViewHolder {
        public TextView name;
        public TextView lieu;
    }
}
