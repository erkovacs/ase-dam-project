package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PropertyDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Myproperties.db" ;
    private static final int DATABASE_VERSION = 1 ;

    private SQLiteDatabase db;


    public PropertyDB(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_PROPERTY_TABLE ="CREATE TABLE " +
                PropertyTable.PROPERTY_TABLE + "(" +
                PropertyTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PropertyTable.COLUMN_NAME + " VARCHAR(128), " +
                PropertyTable.COLUMN_VALUE + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_PROPERTY_TABLE);
        fillPropertyTable();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PropertyTable.PROPERTY_TABLE);
        onCreate(db);

    }

    private void fillPropertyTable() {
        Property p1 = new Property(1, "Name", "Value"); //hardcoded for now ..
    }

    //Create a new property record - insert

    private void addProperty(Property property){
        ContentValues cv = new ContentValues();
       // cv.put(PropertyTable.COLUMN_ID,property.getProperty_id())  this autoincrements
        cv.put(PropertyTable.COLUMN_NAME,property.getProperty_name());
        cv.put(PropertyTable.COLUMN_VALUE,property.getProperty_value());
        db.insert(PropertyTable.PROPERTY_TABLE, null, cv);
    }


    //delete a record
    public void deleteProperty(Property prop){
        long id =prop.getProperty_id();
        db.delete(PropertyTable.PROPERTY_TABLE, PropertyTable.COLUMN_ID + " = " + id,null);
    }

    //select a record
    public Cursor getallProperties(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PropertyTable.PROPERTY_TABLE,
                null, null,null,null,null,null);
        return cursor;
    }

    //update record
    public boolean updateProperty(Property property){
        //define the new values
        ContentValues cv = new ContentValues();
        cv.put(PropertyTable.COLUMN_NAME,property.getProperty_name());
        cv.put(PropertyTable.COLUMN_VALUE,property.getProperty_value());

        //define the WHERE clause
        long id = property.getProperty_id();
        final String whereClause = PropertyTable.COLUMN_ID + " =?";
        String [] clauseArg = new String[] {String.valueOf(id)};

        //execute update
        int noRecords = db.update(PropertyTable.PROPERTY_TABLE, cv, whereClause, clauseArg);

        //check the results
        if(noRecords > 0){
            return true;
        }
        else{
            return false;
        }
    }
}
