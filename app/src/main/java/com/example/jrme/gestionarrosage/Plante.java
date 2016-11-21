package com.example.jrme.gestionarrosage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class Plante
 * Author Jérôme Verlyck
 */
public class Plante implements Comparable<Plante> {

    private int id;
    private String name;
    private int frequence;
    private String lieu;
    private Date dernierArrosage;

    /**
     * Constructeur de la classe Plante.
     * @param id L'id de la plante
     * @param name Le nom de la plante
     * @param frequence La frequence d'arrosage de la plante
     * @param lieu L elieu où se trouve la plante
     */
    public Plante(int id, String name, int frequence, String lieu, String dernierArrosage) {
        this.id = id;
        this.name = name;
        this.frequence = frequence;
        this.lieu = lieu;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
            this.dernierArrosage = sdf.parse(dernierArrosage);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter id
     * @return int id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter name
     * @return String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter frequence
     * @return int frequence
     */
    public int getFrequence() {
        return this.frequence;
    }

    /**
     * Getter lieu
     * @return String lieu
     */
    public String getLieu() {
        return this.lieu;
    }

    /**
     * Getter dernierArrosage
     * @return Date dernierArrosage
     */
    public Date getDernierArrosage() {
        return this.dernierArrosage;
    }

    /**
     * Compare en fonction des jours non arrosé
     * @param plante La plante avec qui on compare
     * @return int
     */
    public int compareTo(Plante plante) {
        long diff = Math.abs(new Date().getTime() - plante.dernierArrosage.getTime());
        int nbJours1 = (int)(diff / (1000 * 60 * 60 * 24));
        int nbJoursPasArrose1 = plante.frequence - nbJours1;

        diff = Math.abs(new Date().getTime() - this.dernierArrosage.getTime());
        int nbJours2 = (int)(diff / (1000 * 60 * 60 * 24));
        int nbJoursPasArrose2 = this.frequence - nbJours2;

        return nbJoursPasArrose2 - nbJoursPasArrose1;
    }
}
