package com.example.jrme.gestionarrosage.fixtures;

import com.example.jrme.gestionarrosage.repository.PlanteDatabase;

/**
 * Class LoadPlanteData
 * Author Jérôme Verlyck
 * Fixtures pour la génération des plantes pour le tests de l'application.
 */
public class LoadPlanteData {

    /**
     * Méthode qui stock des données de test dans la base de données.
     */
    public void load(PlanteDatabase planteDatabase) {

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
