package jp.suntech.c22010.mypokemondb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    private DatabaseHelper _helper;

    EditText et_id;
    EditText et_name;
    EditText et_hp;
    TextView tv_alert;

    public AddFragment(){
    }
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(_helper == null){
            _helper = new DatabaseHelper(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        et_id = view.findViewById(R.id.et_id);
        et_name = view.findViewById(R.id.et_name);
        et_hp = view.findViewById(R.id.et_hp);
        tv_alert = view.findViewById(R.id.tv_alert);

        BtnClickListener listener = new BtnClickListener();
        Button bt_back = view.findViewById(R.id.bt_add_back);
        Button bt_add_save = view.findViewById(R.id.bt_add_save);

        bt_back.setOnClickListener(listener);
        bt_add_save.setOnClickListener(listener);
        return view;
    }

    private class BtnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            int id = v.getId();
            if(id == R.id.bt_add_back){
                FragmentManager manager = getParentFragmentManager();
                manager.popBackStack();
            }
            else if(id == R.id.bt_add_save){

                String msg = "";

                if(et_id.getText().toString().equals("")
                        || et_name.getText().toString().equals("")
                        || et_hp.getText().toString().equals("")){
                    msg += "空欄があります。";
                }
                if(!et_id.getText().toString().equals("")) {

                    SQLiteDatabase db = _helper.getWritableDatabase();
                    int table_id = Integer.parseInt(et_id.getText().toString());
                    String sql = "SELECT COUNT(*) FROM pokemon_list WHERE _id = " + table_id + ";";
                    Cursor cursor = db.rawQuery(sql, null);

                    int data_num = 0;
                    while(cursor.moveToNext()){
                        data_num = cursor.getInt(0);
                    }
                    cursor.close();
                    db.close();
                    if(data_num != 0){
                        msg += "\n既に存在するidです。";
                    }
                }

                tv_alert.setText(msg);

                //エラーが無ければデータの挿入処理
                if(msg.equals("")){
                    SQLiteDatabase db = _helper.getWritableDatabase();

                    String sqlInsert = "INSERT INTO pokemon_list (_id, name, hp) VALUES (?, ?, ?)";

                    SQLiteStatement stmt = db.compileStatement(sqlInsert);

                    int table_id = Integer.parseInt(et_id.getText().toString());
                    String table_name = et_name.getText().toString();
                    int table_hp = Integer.parseInt(et_hp.getText().toString());
                    stmt.bindLong(1, table_id);
                    stmt.bindString(2, table_name);
                    stmt.bindLong(3, table_hp);

                    stmt.executeInsert();

                    et_id.setText("");
                    et_name.setText("");
                    et_hp.setText("");
                }
            }
        }
    }
}