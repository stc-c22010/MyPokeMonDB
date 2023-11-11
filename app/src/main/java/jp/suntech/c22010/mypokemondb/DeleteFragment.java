package jp.suntech.c22010.mypokemondb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

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

    String del_column = "";

    int table_id = 0;
    String table_name = "";
    int table_hp = 0;

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

    @Override
    public void onDestroy(){
        _helper.close();
        super.onDestroy();
    }

    private String SelectData(){
        SQLiteDatabase db = _helper.getWritableDatabase();

        String sql = "";
        if(del_column.equals("_id")) {
            sql = "SELECT * FROM pokemon_list WHERE _id = " + table_id + ";";
        }
        else if(del_column.equals("name")){
            sql = "SELECT * FROM pokemon_list WHERE name = '" + table_name + "';";
        }
        else if(del_column.equals("hp")){
            sql = "SELECT * FROM pokemon_list WHERE hp = " + table_hp + ";";
        }

        Cursor cursor = db.rawQuery(sql, null);

        String dialog_table_msg = "";
        int cnt = 0;
        while (cursor.moveToNext()) {
            int idx_id = cursor.getColumnIndex("_id");
            int idx_name = cursor.getColumnIndex("name");
            int idx_hp = cursor.getColumnIndex("hp");

            int res_id = cursor.getInt(idx_id);
            String res_name = cursor.getString(idx_name);
            int res_hp = cursor.getInt(idx_hp);

            dialog_table_msg += "id: " + idx_id + "  名前: " + res_name + "  hp: " + res_hp + "\n";
        }
        cursor.close();
        db.close();

        return dialog_table_msg;
    }

    public void DeleteData(){
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sql = "";
        if(del_column.equals("_id")) {
            sql = "DELETE FROM pokemon_list WHERE _id = " + table_id + ";";
        }
        else if(del_column.equals("name")){
            sql = "DELETE FROM pokemon_list WHERE name = '" + table_name + "';";
        }
        else if(del_column.equals("hp")){
            sql = "DELETE FROM pokemon_list WHERE hp = " + table_hp + ";";
        }

        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.executeUpdateDelete();

        Toast.makeText(getContext(), "削除しました。", Toast.LENGTH_SHORT).show();
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fcon_main_list, new ListFragment());
        transaction.commit();
    }

    private void MakeDialog (String dialog_table_msg){
        if(dialog_table_msg.equals("")){
            Toast.makeText(getContext(), R.string.not_exist_err, Toast.LENGTH_SHORT).show();
        }
        else {
            DeleteConfirmDialogFragment dialog_fragment = new DeleteConfirmDialogFragment();
            Bundle args = new Bundle();
            args.putString("arg1", dialog_table_msg);
            dialog_fragment.setArguments(args);
            dialog_fragment.show(getChildFragmentManager(), "DeleteConfirmDialogFragment");
        }
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
                    del_column = "_id";
                    table_id = Integer.parseInt(et_del_id.getText().toString());

                    String dialog_table_msg = SelectData();
                    MakeDialog(dialog_table_msg);

                    et_del_id.setText("");
                }
            }
            else if(id == R.id.bt_del_name){
                if(!et_del_name.getText().toString().equals("")){
                    del_column = "name";
                    table_name = et_del_name.getText().toString();

                    String dialog_table_msg = SelectData();
                    MakeDialog(dialog_table_msg);

                    et_del_name.setText("");
                }
            }
            else if(id == R.id.bt_del_hp){
                if(!et_del_hp.getText().toString().equals("")){
                    del_column = "hp";
                    table_hp = Integer.parseInt(et_del_hp.getText().toString());

                    String dialog_table_msg = SelectData();
                    MakeDialog(dialog_table_msg);

                    et_del_hp.setText("");
                }
            }
        }
    }


}