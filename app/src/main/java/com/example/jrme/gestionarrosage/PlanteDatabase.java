package com.example.jrme.gestionarrosage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Class PlanteDatabase
 * Author Jérôme Verlyck
 */
public class PlanteDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gestionArrosage";

    static class PlanteTable implements BaseColumns {
        public static final String TABLE_NAME = "Plante";
        public static final String PLANTE_NAME = "name";
        public static final String PLANTE_FREQUENCE = "frequence";
        public static final String PLANTE_LIEU = "lieu";
        public static final String PLANTE_DERNIER_ARROSAGE = "dernier_arrosage";
    }

    /**
     * Constructeur de la classe PlanteDatabase
     * @param context
     */
    public PlanteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + PlanteTable.TABLE_NAME + "(" +
            PlanteTable.TABLE_NAME + " INTEGER PRIMARY KEY," +
            PlanteTable.PLANTE_NAME + " VARCHAR(50)," +
            PlanteTable.PLANTE_FREQUENCE + " SMALLINT" +
            PlanteTable.PLANTE_LIEU + " VARCHAR(50)" +
            PlanteTable.PLANTE_DERNIER_ARROSAGE + " DATE DEFAULT CURRENT_DATE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /**
     * Retourne la liste des plantes de la base de données.
     * @return List La liste des plantes
     */
    public List<Plante> list() {
        List<Plante> plantes = new ArrayList<Plante>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {
            PlanteTable._ID,
            PlanteTable.PLANTE_NAME,
            PlanteTable.PLANTE_FREQUENCE,
            PlanteTable.PLANTE_LIEU,
            PlanteTable.PLANTE_DERNIER_ARROSAGE
        };

        Cursor cursor = sqLiteDatabase.query(
            PlanteTable.TABLE_NAME,
            columns,
            null, null, null, null, null
        );

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Plante plante = new Plante(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getString(4)
            );
            plantes.add(plante);

            cursor.moveToNext();
        }

        cursor.close();

        return plantes;
    }

    /**
     * Retourne la plante dont l'id est passé en paramètre.
     * @param id L'id de la plante à récupérer
     * @return Object
     */
    public Plante unique(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {
            PlanteTable._ID,
            PlanteTable.PLANTE_NAME,
            PlanteTable.PLANTE_FREQUENCE,
            PlanteTable.PLANTE_LIEU,
            PlanteTable.PLANTE_DERNIER_ARROSAGE
        };

        String where = PlanteTable._ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };

        Cursor cursor = sqLiteDatabase.query(
                PlanteTable.TABLE_NAME,
                columns,
                where,
                whereArgs,
                null, null, null
        );

        cursor.moveToFirst();
        Plante plante = new Plante(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getInt(2),
            cursor.getString(3),
            cursor.getString(4)
        );

        cursor.close();

        return plante;
    }

    /**
     * Méthode d'ajout d'une plante dans la base de donnée
     * dont les informations de la plante sont passées en paramètre.
     * @param name Le nom de la plante
     * @param frequence La fréquence d'arrosage de la plante
     * @param lieu Le lieu où se trouve la plante
     */
    public void add(String name, int frequence, String lieu) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PlanteTable.PLANTE_NAME, name);
        values.put(PlanteTable.PLANTE_FREQUENCE, frequence);
        values.put(PlanteTable.PLANTE_LIEU, lieu);

        sqLiteDatabase.insert(PlanteTable.TABLE_NAME, null, values);
    }

    /**
     * Méthode de modification d'une plante dans la base de données
     * dont les informations de la plante à modifier sont passées en
     * paramètre.
     * @param id  L'id de la plante à modifier
     * @param name Le nom de la plante
     * @param frequence La fréquence d'arrosage de la plante
     * @param lieu Le lieu où se trouve la plante
     */
    public void update(int id, String name, int frequence, String lieu) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(PlanteTable.PLANTE_NAME, name);
        values.put(PlanteTable.PLANTE_FREQUENCE, frequence);
        values.put(PlanteTable.PLANTE_LIEU, lieu);

        String where = PlanteTable._ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };

        sqLiteDatabase.update(
            PlanteTable.TABLE_NAME,
            values,
            where,
            whereArgs
        );
    }

    /**
     * Méthode de suppresion d'une plante dans la base de données
     * dont l'id est celui passé en paramètre.
     * @param id L'id de la plante à supprimer
     */
    public void delete(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String where = PlanteTable._ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };

        sqLiteDatabase.delete(PlanteTable.TABLE_NAME, where, whereArgs);
    }
}
