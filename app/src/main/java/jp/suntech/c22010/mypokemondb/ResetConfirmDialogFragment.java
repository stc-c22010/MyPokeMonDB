package jp.suntech.c22010.mypokemondb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.Nullable;

public class ResetConfirmDialogFragment extends DialogFragment {
    MenuFragment menuFragment;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        menuFragment = (MenuFragment) getParentFragment();
        AlertDialog.Builder builder = new AlertDialog.Builder(menuFragment.getContext());
        builder.setTitle(R.string.dialog_title_reset);

        builder.setMessage(R.string.dialog_msg_reset);


        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_btn_ng, new DialogButtonClickListener());


        return builder.create();
    }

    public void ResetData(){
        DatabaseHelper _helper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = _helper.getWritableDatabase();

        String sql = "DELETE FROM pokemon_list";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.executeUpdateDelete();




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



        Toast.makeText(getActivity(), R.string.complete_reset, Toast.LENGTH_SHORT).show();

        FragmentManager manager = getParentFragment().getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fcon_main_list, new ListFragment());
        transaction.commit();
        db.close();
        _helper.close();
    }

    private class DialogButtonClickListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            if(i == DialogInterface.BUTTON_POSITIVE) {
                ResetData();
            }
        }
    }
}
