package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;


import java.util.ArrayList;
import java.util.List;

public class PropertyDataSource {
    private SQLiteDatabase db;
    private PropertyDB dbHelper;
    private String [] allColumns={PropertyTable.COLUMN_ID,PropertyTable.COLUMN_NAME,PropertyTable.COLUMN_VALUE};

    public PropertyDataSource(Context context) {
        dbHelper = new PropertyDB(context);
    }

    public void open()throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

   public void close(){
        dbHelper.close();
   }

    //Create a new property record - insert

    private Property addProperty(Property property){
        ContentValues cv = new ContentValues();

        cv.put(PropertyTable.COLUMN_NAME,PropertyTable.COLUMN_NAME);
        cv.put(PropertyTable.COLUMN_VALUE,PropertyTable.COLUMN_VALUE);
        long id =db.insert(PropertyTable.PROPERTY_TABLE,null, cv);
        Cursor cursor = db.query(PropertyTable.PROPERTY_TABLE, allColumns, PropertyTable.COLUMN_ID + "=" + id, null,null,null,null);
        cursor.moveToFirst();
        Property newProperty = cursorToNote(cursor);
        cursor.close();
        return newProperty;

    }


    //delete a record
    public void deleteProperty(Property prop){
        long id =prop.getProperty_id();
        db.delete(PropertyTable.PROPERTY_TABLE, PropertyTable.COLUMN_ID +
                " = " + id,null);
    }


   private Property cursorToNote(Cursor cursor){
        Property property = new Property();
        property.setProperty_id(cursor.getInt(0));
        property.setProperty_name(cursor.getString(1));
        property.setProperty_value(cursor.getString(2));
        return property;
    }

    //select a record
    public List<Property> getallProperty(){
        List<Property> properties =new ArrayList<Property>();

        Cursor cursor = db.query(PropertyTable.PROPERTY_TABLE,allColumns,null,null,null,
                null,null);

        if(cursor!=null && cursor.getCount() > 0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Property newproperty = cursorToNote(cursor);
                cursor.moveToNext();
            }
            cursor.close();
            return properties;
        }
        else
            return null;
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
