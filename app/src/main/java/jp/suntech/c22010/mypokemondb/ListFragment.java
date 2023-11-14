package jp.suntech.c22010.mypokemondb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListFragment extends Fragment {
    DatabaseHelper _helper;

    ListView lv_main;


    public ListFragment() {
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
        container.removeAllViews();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        lv_main = v.findViewById(R.id.lv_main);
        TextView tv_list_desc = v.findViewById(R.id.tv_list_desc);

        tv_list_desc.setText("");

        UpdateList();

        return v;
    }

    @Override
    public void onDestroy(){
        _helper.close();
        super.onDestroy();
    }
    public void UpdateList(){
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sql = "SELECT * FROM pokemon_list;";
        Cursor cursor = db.rawQuery(sql, null);
        List<Map<String, Object>> all_list = new ArrayList<>();

        String[] FROM = {"id", "name", "hp"};
        int[] TO = {R.id.tv_id_row, R.id.tv_name_row, R.id.tv_hp_row};

        while (cursor.moveToNext()) {
            int idx_id = cursor.getColumnIndex("_id");
            int idx_name = cursor.getColumnIndex("name");
            int idx_hp = cursor.getColumnIndex("hp");

            int res_id = cursor.getInt(idx_id);
            String res_name = cursor.getString(idx_name);
            int res_hp = cursor.getInt(idx_hp);

            Map<String, Object> res = new HashMap<>();
            res.put("id", res_id);
            res.put("name", res_name);
            res.put("hp", res_hp);
            all_list.add(res);
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), all_list, R.layout.row, FROM, TO);
        lv_main.setAdapter(adapter);
        cursor.close();
        db.close();
    }
}