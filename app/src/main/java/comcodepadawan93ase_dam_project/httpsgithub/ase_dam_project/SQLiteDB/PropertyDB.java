package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PropertyDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Myproperties.db" ;
    private static final int DATABASE_VERSION = 1 ;

    //private SQLiteDatabase db;


    public PropertyDB(Context context) {

        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        PropertyTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        PropertyTable.onUpgrade(db,oldVersion,newVersion);
    }
}
