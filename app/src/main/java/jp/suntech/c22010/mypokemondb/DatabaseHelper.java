package jp.suntech.c22010.mypokemondb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pokemon_list.db";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("Create TABLE pokemon_list (");
        sb.append("_id INTEGER PRIMARY KEY,");
        sb.append("name TEXT,");
        sb.append("hp INTEGER");
        sb.append(");");
        String sql = sb.toString();

        db.execSQL(sql);

        SQLiteStatement stmt;
        String name_array[] = {"フシギダネ", "フシギソウ", "フシギバナ", "ヒトカゲ", "リザード", "リザードン", "ゼニガメ", "カメール", "カメックス", "キャタピー", "トランセル", "バタフリー", "ビードル", "コクーン", "スピアー", "ポッポ", "ピジョン", "ピジョット", "コラッタ", "ラッタ", "オニスズメ", "オニドリル", "アーボ", "アーボック", "ピカチュウ", "ライチュウ"};
        int hp_array[] = {45, 60, 80, 39, 58, 78, 44, 59, 79, 45, 50, 60, 40, 45, 65, 40, 63, 83, 30, 55, 40, 65, 35, 60, 35, 60};
        for(int i = 0; i < 10; i++){
            String sqlInsert = "INSERT INTO pokemon_list (_id, name, hp) VALUES (?, ?, ?)";
            stmt = db.compileStatement(sqlInsert);

            stmt.bindLong(1, i+1);
            stmt.bindString(2, name_array[i]);
            stmt.bindLong(3, hp_array[i]);

            stmt.executeInsert();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
