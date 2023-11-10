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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    DatabaseHelper _helper;

    ListView lv_main;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        lv_main = v.findViewById(R.id.lv_main);

        return v;
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