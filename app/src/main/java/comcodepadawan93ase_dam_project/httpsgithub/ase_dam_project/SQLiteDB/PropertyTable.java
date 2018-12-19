package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB;

import android.database.sqlite.SQLiteDatabase;

public class PropertyTable {
    public static final String PROPERTY_TABLE="properties";
    public static final String COLUMN_ID = "property_id";
    public static final String COLUMN_NAME= "property_name";
    public static final String COLUMN_VALUE="property_value";


    private static final String SQL_CREATE_PROPERTY_TABLE ="CREATE TABLE IF NOT EXISTS " +
            PropertyTable.PROPERTY_TABLE + "(" +
            PropertyTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PropertyTable.COLUMN_NAME + " VARCHAR(128), " +
            PropertyTable.COLUMN_VALUE + " TEXT" +
            ")";

    public static void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_PROPERTY_TABLE);
        //fillPropertyTable();
    }


    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS " + PROPERTY_TABLE);
        onCreate(db);

    }

    //private void fillPropertyTable() {
      //  Property p1 = new Property(1, "Name", "Value"); //hardcoded for now ..
    //}


}
