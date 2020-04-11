package info.sumantv.flow.celebflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import info.sumantv.flow.userflow.Util.Common;
import org.json.JSONArray;
import org.json.JSONObject;


import info.sumantv.flow.R;
import java.io.File;

public class LocalDB {
    private final String DATABASE_NAME = "DataBase";
    private final int DATABASE_VERSION = 1;
    private DbHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;
    private String STORAGE_PATH = "";
    private String TABLE_LOGIN_ACCOUNTS = "loginAccounts";
    private String ChatCreateQuery = "CREATE TABLE IF NOT EXISTS "+TABLE_LOGIN_ACCOUNTS+" ( " + "PrimID INTEGER PRIMARY KEY AUTOINCREMENT, userId NVARCHAR(5000) NOT NULL, firstName NVARCHAR(5000) NOT NULL, lastName NVARCHAR(5000) NOT NULL, image NVARCHAR(5000) NOT NULL, emailOrMobile NVARCHAR(5000) NOT NULL );";

    private class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, STORAGE_PATH+DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ChatCreateQuery);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN_ACCOUNTS);
            onCreate(db);
            /*if (newVersion > oldVersion) {
                db.execSQL("ALTER TABLE foo ADD COLUMN new_column INTEGER DEFAULT 0");
            }*/
        }
    }
    public LocalDB(Context c) {
        ourContext = c;
    }
    public LocalDB open() throws SQLException {
        String appName = ourContext.getResources().getString(R.string.app_name);
        STORAGE_PATH = ourContext.getApplicationInfo().dataDir+"/"+appName+"/Database/";
        //STORAGE_PATH = Environment.getExternalStorageDirectory()+"/"+appName+"/Database/";
        File dir  = new File(STORAGE_PATH);
        if(!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        ourHelper.close();
    }

    public long createLogin(String userId,String firstName,String lastName,String image,String emailOrMobile) {
        ContentValues cv = new ContentValues();
        if(Common.getInstance().IsNull(image)){
            image = "";
        }
        if(checkLoginExist(userId)){
            return updateLogin(userId,firstName,lastName,image);
        }
        //
        cv.put("userId", userId);
        cv.put("firstName", Common.getInstance().convertFirstLetterToCapital(firstName));
        cv.put("lastName", Common.getInstance().convertFirstLetterToCapital(lastName));
        cv.put("image", image);
        cv.put("emailOrMobile", emailOrMobile);
        return ourDatabase.insert(TABLE_LOGIN_ACCOUNTS, null, cv);
    }
    public long updateLogin(String userId,String firstName,String lastName,String image) {
        ContentValues cv = new ContentValues();
        if(Common.getInstance().IsNull(image)){
            image = "";
        }
        //
        cv.put("firstName", Common.getInstance().convertFirstLetterToCapital(firstName));
        cv.put("lastName", Common.getInstance().convertFirstLetterToCapital(lastName));
        cv.put("image", image);
        return ourDatabase.update(TABLE_LOGIN_ACCOUNTS,cv,"userId = ?", new String[]{String.valueOf(userId)});
    }
    public JSONArray getAllLogins() {
        String searchQuery;
        searchQuery = "SELECT * FROM "+TABLE_LOGIN_ACCOUNTS+" ORDER BY PrimID";
        Cursor cursor = ourDatabase.rawQuery(searchQuery,null);
        return GetJSONArray(cursor);
    }
    private boolean checkLoginExist(String userId) {
        String searchQuery = "SELECT * FROM "+TABLE_LOGIN_ACCOUNTS+" WHERE userId = ?";
        Cursor cursor = ourDatabase.rawQuery(searchQuery, new String[]{String.valueOf(userId)});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public long DeleteLoginTableData() {
        long result;
        result = ourDatabase.delete(TABLE_LOGIN_ACCOUNTS, null, null);
        return result;
    }
    public long DeleteLoginRowByID(Integer ID) {
        long result;
        result = ourDatabase.delete(TABLE_LOGIN_ACCOUNTS, "PrimID = ?", new String[]{String.valueOf(ID)});
        return result;
    }
    private JSONArray GetJSONArray(Cursor cursor) {
        JSONArray resultSet = new JSONArray();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();
                for( int i=0 ;  i< totalColumn ; i++ )
                {
                    if( cursor.getColumnName(i) != null )
                    {
                        try
                        {
                            if( cursor.getString(i) != null )
                            {
                                rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                            }
                            else
                            {
                                rowObject.put( cursor.getColumnName(i) ,  "" );
                            }
                        }
                        catch( Exception e )
                        {
                            Log.d("TAG_NAME", e.getMessage()  );
                        }
                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
