package com.example.jrme.gestionarrosage.fixtures;

import android.content.Context;

import com.example.jrme.gestionarrosage.PlanteDatabase;

/**
 * Class LoadPlanteData
 * Author Jérôme Verlyck
 */
public class LoadPlanteData {

    /**
     * Méthode qui stock des données de test dans la base de données
     */
    public void load(Context context) {

        PlanteDatabase planteDatabase = new PlanteDatabase(context);

        String[][] plantes = {
            { "Acacia", "5", "Jardin Gauche"},
            { "Azalée", "3", "Jardin Gauche"},
            { "Bleuet", "10", "Jardin Droite"},
            { "Camélia", "1", "Jardin Droite"},
            { "Capucine", "5", "Jardin Bord"},
            { "Coquelicot", "8", "Jardin"},
            { "Phalaenopsis mauve", "7", "Séjour"},
            { "Anthurium", "1", "Toilette"},
            { "Guzmania", "5", "Couloir"},
            { "Hibiscus jaune", "15", "Cuisine"},
            { "Hoya australis", "4", "Salle de bain"},
            { "Vanda", "1", "Salle à manger"},
        };

        for(int i = 0; i < plantes.length; i++) {
            planteDatabase.add(
                plantes[i][0],
                Integer.parseInt(plantes[i][1]),
                plantes[i][2]
            );
        }
    }
}
