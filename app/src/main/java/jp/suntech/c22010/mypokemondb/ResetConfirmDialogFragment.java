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


        AlertDialog dialog = builder.create();
        return dialog;
    }

    public void ResetData(){
        DatabaseHelper _helper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = _helper.getWritableDatabase();

        String sql = "DELETE FROM pokemon_list";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.executeUpdateDelete();

        Toast.makeText(getActivity(), R.string.complete_reset, Toast.LENGTH_SHORT).show();

        FragmentManager manager = getParentFragment().getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fcon_main_list, new ListFragment());
        transaction.commit();
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
