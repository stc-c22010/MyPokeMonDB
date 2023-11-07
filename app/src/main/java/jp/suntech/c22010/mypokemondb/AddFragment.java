package jp.suntech.c22010.mypokemondb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        BtnClickListener listener = new BtnClickListener();
        Button bt_back = view.findViewById(R.id.bt_back);
        Button bt_add_save = view.findViewById(R.id.bt_add_save);

        bt_back.setOnClickListener(listener);
        bt_add_save.setOnClickListener(listener);
        return view;
    }

    private class BtnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            int id = v.getId();
            if(id == R.id.bt_back){
                FragmentManager manager = getParentFragmentManager();
                manager.popBackStack();
            }
            else if(id == R.id.bt_add_save){
                String msg = "";
                EditText et_id = v.findViewById(R.id.et_id);
                EditText et_name = v.findViewById(R.id.et_name);
                EditText et_hp = v.findViewById(R.id.et_hp);
                if(et_id.getText().toString() == ""
                        || et_name.getText().toString() == ""
                        || et_hp.getText().toString() == ""){
                    msg += "空欄があります。";
                }
                if(et_id.getText().toString() != "" ) {
                    int table_id = Integer.parseInt(et_id.getText().toString());
                    String sql = "SELECT COUNT(*) FROM pokemon_list WHERE _id = " + table_id + ";";

                }
            }
        }
    }
}