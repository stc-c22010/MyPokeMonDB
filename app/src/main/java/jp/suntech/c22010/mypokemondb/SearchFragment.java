package jp.suntech.c22010.mypokemondb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    DatabaseHelper _helper;
    EditText et_search_id;
    EditText et_search_name;
    EditText et_search_hp;
    TextView tv_search_complete;

    ListView lv_search_res;
    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();

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
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        BtnClickListener listener = new BtnClickListener();
        Button bt_back = v.findViewById(R.id.bt_search_back);
        Button bt_search_id = v.findViewById(R.id.bt_search_id);
        Button bt_search_name = v.findViewById(R.id.bt_search_name);
        Button bt_search_hp = v.findViewById(R.id.bt_search_hp);
        et_search_id = v.findViewById(R.id.et_search_id);
        et_search_name = v.findViewById(R.id.et_search_name);
        et_search_hp = v.findViewById(R.id.et_search_hp);
        tv_search_complete = v.findViewById(R.id.tv_search_complete);
        lv_search_res = v.findViewById(R.id.lv_search_res);

        bt_back.setOnClickListener(listener);
        bt_search_id.setOnClickListener(listener);
        bt_search_name.setOnClickListener(listener);
        bt_search_hp.setOnClickListener(listener);

        return v;
    }

    private class BtnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int id = view.getId();

            if(id == R.id.bt_search_back){
                FragmentManager manager = getParentFragmentManager();
                manager.popBackStack();
            }
            //idで検索
            else if(id == R.id.bt_search_id){
                if(!et_search_id.getText().toString().equals("")) {
                    SQLiteDatabase db = _helper.getWritableDatabase();
                    int table_id = Integer.parseInt(et_search_id.getText().toString());
                    String sql = "SELECT COUNT(*) FROM pokemon_list WHERE _id = " + table_id + ";";
                    Cursor cursor = db.rawQuery(sql, null);

                    int data_num = 0;
                    while (cursor.moveToNext()) {
                        data_num = cursor.getInt(0);
                    }
                    cursor.close();


                    tv_search_complete.setText(data_num + "件のデータが見つかりました。");


                    sql = "SELECT * FROM pokemon_list WHERE _id = " + table_id + ";";
                    cursor = db.rawQuery(sql, null);
                    List<Map<String, Object>> search_res_list = new ArrayList<>();

                    String[] FROM = {"id", "name", "hp"};
                    int[] TO = {R.id.tv_id_row, R.id.tv_name_row, R.id.tv_hp_row};

                    while (cursor.moveToNext()) {
                        int idx_id = cursor.getColumnIndex("_id");
                        int idx_name = cursor.getColumnIndex("name");
                        int idx_hp = cursor.getColumnIndex("hp");

                        int res_id = cursor.getInt(idx_id);
                        String res_name = cursor.getString(idx_name);
                        int res_hp = cursor.getInt(idx_hp);

                        Map<String, Object> search_res = new HashMap<>();
                        search_res.put("id", res_id);
                        search_res.put("name", res_name);
                        search_res.put("hp", res_hp);
                        search_res_list.add(search_res);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), search_res_list, R.layout.row, FROM, TO);
                    lv_search_res.setAdapter(adapter);
                    cursor.close();
                    db.close();
                    et_search_id.setText("");
                }
            }

            else if(id == R.id.bt_search_name){
                if(!et_search_name.getText().toString().equals("")) {
                    SQLiteDatabase db = _helper.getWritableDatabase();
                    String table_name = et_search_name.getText().toString();
                    String sql = "SELECT COUNT(*) FROM pokemon_list WHERE name LIKE '%" + table_name + "%';";
                    Cursor cursor = db.rawQuery(sql, null);

                    int data_num = 0;
                    while (cursor.moveToNext()) {
                        data_num = cursor.getInt(0);
                    }
                    cursor.close();


                    tv_search_complete.setText(data_num + "件のデータが見つかりました。");


                    sql = "SELECT * FROM pokemon_list WHERE name LIKE '%" + table_name + "%';";
                    cursor = db.rawQuery(sql, null);
                    List<Map<String, Object>> search_res_list = new ArrayList<>();

                    String[] FROM = {"id", "name", "hp"};
                    int[] TO = {R.id.tv_id_row, R.id.tv_name_row, R.id.tv_hp_row};

                    while (cursor.moveToNext()) {
                        int idx_id = cursor.getColumnIndex("_id");
                        int idx_name = cursor.getColumnIndex("name");
                        int idx_hp = cursor.getColumnIndex("hp");

                        int res_id = cursor.getInt(idx_id);
                        String res_name = cursor.getString(idx_name);
                        int res_hp = cursor.getInt(idx_hp);

                        Map<String, Object> search_res = new HashMap<>();
                        search_res.put("id", res_id);
                        search_res.put("name", res_name);
                        search_res.put("hp", res_hp);
                        search_res_list.add(search_res);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), search_res_list, R.layout.row, FROM, TO);
                    lv_search_res.setAdapter(adapter);
                    cursor.close();
                    db.close();
                    et_search_name.setText("");
                }
            }

            else if(id == R.id.bt_search_hp){
                if(!et_search_hp.getText().toString().equals("")) {
                    SQLiteDatabase db = _helper.getWritableDatabase();
                    int table_hp = Integer.parseInt(et_search_hp.getText().toString());
                    String sql = "SELECT COUNT(*) FROM pokemon_list WHERE hp = " + table_hp + ";";
                    Cursor cursor = db.rawQuery(sql, null);

                    int data_num = 0;
                    while (cursor.moveToNext()) {
                        data_num = cursor.getInt(0);
                    }
                    cursor.close();


                    tv_search_complete.setText(data_num + "件のデータが見つかりました。");


                    sql = "SELECT * FROM pokemon_list WHERE hp = " + table_hp + ";";
                    cursor = db.rawQuery(sql, null);
                    List<Map<String, Object>> search_res_list = new ArrayList<>();

                    String[] FROM = {"id", "name", "hp"};
                    int[] TO = {R.id.tv_id_row, R.id.tv_name_row, R.id.tv_hp_row};

                    while (cursor.moveToNext()) {
                        int idx_id = cursor.getColumnIndex("_id");
                        int idx_name = cursor.getColumnIndex("name");
                        int idx_hp = cursor.getColumnIndex("hp");

                        int res_id = cursor.getInt(idx_id);
                        String res_name = cursor.getString(idx_name);
                        int res_hp = cursor.getInt(idx_hp);

                        Map<String, Object> search_res = new HashMap<>();
                        search_res.put("id", res_id);
                        search_res.put("name", res_name);
                        search_res.put("hp", res_hp);
                        search_res_list.add(search_res);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), search_res_list, R.layout.row, FROM, TO);
                    lv_search_res.setAdapter(adapter);
                    cursor.close();
                    db.close();
                    et_search_hp.setText("");
                }
            }
        }
    }
}