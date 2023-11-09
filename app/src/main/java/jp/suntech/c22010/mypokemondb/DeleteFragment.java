package jp.suntech.c22010.mypokemondb;

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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteFragment extends Fragment {

    DatabaseHelper _helper;
    EditText et_del_id;
    EditText et_del_name;
    EditText et_del_hp;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeleteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteFragment newInstance(String param1, String param2) {
        DeleteFragment fragment = new DeleteFragment();
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
        View v = inflater.inflate(R.layout.fragment_delete, container, false);

        BtnClickListener listener = new BtnClickListener();
        Button bt_back = v.findViewById(R.id.bt_del_back);
        Button bt_del_id = v.findViewById(R.id.bt_del_id);
        Button bt_del_name = v.findViewById(R.id.bt_del_name);
        Button bt_del_hp = v.findViewById(R.id.bt_del_hp);
        et_del_id = v.findViewById(R.id.et_del_id);
        et_del_name = v.findViewById(R.id.et_del_name);
        et_del_hp = v.findViewById(R.id.et_del_hp);

        bt_back.setOnClickListener(listener);
        bt_del_id.setOnClickListener(listener);
        bt_del_name.setOnClickListener(listener);
        bt_del_hp.setOnClickListener(listener);

        return v;
    }

    private class BtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if(id == R.id.bt_del_back){
                FragmentManager manager = getParentFragmentManager();
                manager.popBackStack();
            }
            else if(id == R.id.bt_del_id){
                if(!et_del_id.getText().toString().equals("")){
                    SQLiteDatabase db = _helper.getWritableDatabase();
                    int table_id = Integer.parseInt(et_del_id.getText().toString());
                    String sql = "DELETE FROM pokemon_list WHERE _id = " + table_id + ";";

                    SQLiteStatement stmt = db.compileStatement(sql);
                    stmt.executeUpdateDelete();

                    et_del_id.setText("");
                    Toast.makeText(getContext(), "削除しました。", Toast.LENGTH_SHORT).show();
                }
            }
            else if(id == R.id.bt_del_name){
                if(!et_del_name.getText().toString().equals("")){
                    SQLiteDatabase db = _helper.getWritableDatabase();
                    String table_name = et_del_name.getText().toString();
                    String sql = "DELETE FROM pokemon_list WHERE name LIKE '%" + table_name + "%';";

                    SQLiteStatement stmt = db.compileStatement(sql);
                    stmt.executeUpdateDelete();

                    et_del_name.setText("");
                    Toast.makeText(getContext(), "削除しました。", Toast.LENGTH_SHORT).show();
                }
            }
            else if(id == R.id.bt_del_hp){
                if(!et_del_hp.getText().toString().equals("")){
                    SQLiteDatabase db = _helper.getWritableDatabase();
                    int table_hp = Integer.parseInt(et_del_hp.getText().toString());
                    String sql = "DELETE FROM pokemon_list WHERE hp = " + table_hp + ";";

                    SQLiteStatement stmt = db.compileStatement(sql);
                    stmt.executeUpdateDelete();

                    et_del_hp.setText("");
                    Toast.makeText(getContext(), "削除しました。", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}