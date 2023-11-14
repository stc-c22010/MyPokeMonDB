package jp.suntech.c22010.mypokemondb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.Nullable;

public class DeleteConfirmDialogFragment extends DialogFragment {
    DeleteFragment deleteFragment;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        deleteFragment = (DeleteFragment) getParentFragment();
        String dialog_table_msg = getArguments().getString("arg1");
        AlertDialog.Builder builder = new AlertDialog.Builder(deleteFragment.getContext());
        builder.setTitle(R.string.dialog_title_del);

        builder.setMessage(getResources().getString(R.string.dialog_msg_del) + "\n" + dialog_table_msg);


        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_btn_ng, new DialogButtonClickListener());


        return builder.create();
    }

    private class DialogButtonClickListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            if(i == DialogInterface.BUTTON_POSITIVE) {
                deleteFragment.DeleteData();
            }
        }
    }
}
