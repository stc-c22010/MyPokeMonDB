package jp.suntech.c22010.mypokemondb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchFragment extends Fragment {
    DatabaseHelper _helper;
    EditText et_search_id;
    EditText et_search_name;
    EditText et_search_hp;
    TextView tv_list_desc;

    ListView lv_main;
    public SearchFragment() {
        // Required empty public constructor
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
        et_search_id = v.findViewById(R.id.et_search_id);
        et_search_name = v.findViewById(R.id.et_search_name);
        et_search_hp = v.findViewById(R.id.et_search_hp);
        Button bt_search_execute = v.findViewById(R.id.bt_search_execute);
        tv_list_desc = getActivity().findViewById(R.id.tv_list_desc);
        lv_main = getActivity().findViewById(R.id.lv_main);

        bt_search_execute.setOnClickListener(listener);
        bt_back.setOnClickListener(listener);


        return v;
    }

    @Override
    public void onPause(){
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fcon_main_list, new ListFragment());
        transaction.commit();
        super.onPause();
    }

    @Override
    public void onDestroy(){
        _helper.close();
        super.onDestroy();
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
            else if(id == R.id.bt_search_execute){
                String sql_condition = "";
                if(!et_search_id.getText().toString().equals("")) {
                    int table_id = Integer.parseInt(et_search_id.getText().toString());
                    sql_condition += " _id = " + table_id;
                }
                if(!et_search_name.getText().toString().equals("")) {
                    if(!sql_condition.equals("")){
                        sql_condition += " AND";
                    }
                    String table_name = et_search_name.getText().toString();
                    sql_condition += " name LIKE '%" + table_name + "%'";
                }
                if(!et_search_hp.getText().toString().equals("")) {
                    if(!sql_condition.equals("")){
                        sql_condition += " AND";
                    }
                    int table_hp = Integer.parseInt(et_search_hp.getText().toString());
                    sql_condition += " hp = " + table_hp;
                }

                if(!sql_condition.equals("")){
                    sql_condition = " WHERE" + sql_condition;
                }

                SQLiteDatabase db = _helper.getWritableDatabase();
                String sql = "SELECT COUNT(*) FROM pokemon_list" + sql_condition + ";";
                Cursor cursor = db.rawQuery(sql, null);

                int data_num = 0;
                while (cursor.moveToNext()) {
                    data_num = cursor.getInt(0);
                }
                cursor.close();


                sql = "SELECT * FROM pokemon_list" + sql_condition + ";";
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
                lv_main.setAdapter(adapter);
                cursor.close();
                db.close();

                String data_num_str = data_num + getResources().getString(R.string.complete_search);
                tv_list_desc.setText(data_num_str);
            }

        }
    }
}