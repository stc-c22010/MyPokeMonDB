package jp.suntech.c22010.mypokemondb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class DeleteFragment extends Fragment {

    DatabaseHelper _helper;
    EditText et_del_id;
    EditText et_del_name;
    EditText et_del_hp;



    public DeleteFragment() {
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
        View v = inflater.inflate(R.layout.fragment_delete, container, false);

        BtnClickListener listener = new BtnClickListener();
        Button bt_back = v.findViewById(R.id.bt_del_back);
        et_del_id = v.findViewById(R.id.et_del_id);
        et_del_name = v.findViewById(R.id.et_del_name);
        et_del_hp = v.findViewById(R.id.et_del_hp);
        Button bt_del_execute = v.findViewById(R.id.bt_del_execute);

        bt_del_execute.setOnClickListener(listener);
        bt_back.setOnClickListener(listener);

        return v;
    }

    @Override
    public void onDestroy(){
        _helper.close();
        super.onDestroy();
    }

    private String SelectData(){
        SQLiteDatabase db = _helper.getWritableDatabase();

        String sql_condition = "";
        if(!et_del_id.getText().toString().equals("")) {
            int table_id = Integer.parseInt(et_del_id.getText().toString());
            sql_condition += " _id = " + table_id;
        }
        if(!et_del_name.getText().toString().equals("")) {
            if(!sql_condition.equals("")){
                sql_condition += " AND";
            }
            String table_name = et_del_name.getText().toString();
            sql_condition += " name LIKE '%" + table_name + "%'";
        }
        if(!et_del_hp.getText().toString().equals("")) {
            if(!sql_condition.equals("")){
                sql_condition += " AND";
            }
            int table_hp = Integer.parseInt(et_del_hp.getText().toString());
            sql_condition += " hp = " + table_hp;
        }

        if(!sql_condition.equals("")){
            sql_condition = " WHERE" + sql_condition;
        }

        String sql = "SELECT * FROM pokemon_list" + sql_condition + ";";

        Cursor cursor = db.rawQuery(sql, null);

        String dialog_table_msg = "";
        while (cursor.moveToNext()) {
            int idx_id = cursor.getColumnIndex("_id");
            int idx_name = cursor.getColumnIndex("name");
            int idx_hp = cursor.getColumnIndex("hp");

            int res_id = cursor.getInt(idx_id);
            String res_name = cursor.getString(idx_name);
            int res_hp = cursor.getInt(idx_hp);

            dialog_table_msg += "id: " + res_id + "  名前: " + res_name + "  hp: " + res_hp + "\n";
        }
        cursor.close();
        db.close();

        return dialog_table_msg;
    }

    public void DeleteData(){
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sql_condition = "";

        if(!et_del_id.getText().toString().equals("")) {
            int table_id = Integer.parseInt(et_del_id.getText().toString());
            sql_condition += " _id = " + table_id;
        }
        if(!et_del_name.getText().toString().equals("")) {
            if(!sql_condition.equals("")){
                sql_condition += " AND";
            }
            String table_name = et_del_name.getText().toString();
            sql_condition += " name LIKE '%" + table_name + "%'";
        }
        if(!et_del_hp.getText().toString().equals("")) {
            if(!sql_condition.equals("")){
                sql_condition += " AND";
            }
            int table_hp = Integer.parseInt(et_del_hp.getText().toString());
            sql_condition += " hp = " + table_hp;
        }

        if(!sql_condition.equals("")){
            sql_condition = " WHERE" + sql_condition;
        }

        String sql = "DELETE FROM pokemon_list" + sql_condition + ";";

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
            else if(id == R.id.bt_del_execute){
                String dialog_table_msg = SelectData();
                MakeDialog(dialog_table_msg);
            }
        }
    }


}