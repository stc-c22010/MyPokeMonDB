package jp.suntech.c22010.mypokemondb;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MenuFragment extends Fragment {

    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        BtnClickListener listener = new BtnClickListener();

        Button bt_add = view.findViewById(R.id.bt_add);
        Button bt_search = view.findViewById(R.id.bt_search);
        Button bt_del = view.findViewById(R.id.bt_del);
        Button bt_reset = view.findViewById(R.id.bt_reset);

        bt_add.setOnClickListener(listener);
        bt_search.setOnClickListener(listener);
        bt_del.setOnClickListener(listener);
        bt_reset.setOnClickListener(listener);

        return view;
    }

    private class BtnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if(id == R.id.bt_reset){
                ResetConfirmDialogFragment dialogFragment = new ResetConfirmDialogFragment();
                dialogFragment.show(getChildFragmentManager(), "ResetConfirmDialogFragment");
                return;
            }

            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.addToBackStack("Only List");


            if(id == R.id.bt_add){
                transaction.replace(R.id.fcon_main, new AddFragment());
            }
            else if(id == R.id.bt_search){
                transaction.replace(R.id.fcon_main, new SearchFragment());
            }
            else if(id == R.id.bt_del){
                transaction.replace(R.id.fcon_main, new DeleteFragment());
            }

            transaction.commit();
        }
    }
}