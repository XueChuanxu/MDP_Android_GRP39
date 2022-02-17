package com.example.MDP_Android.ui.main;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.MDP_Android.MainActivity;
import com.example.MDP_Android.R;

public class DirectionFragment extends DialogFragment {

    private static final String TAG = "DirectionFragment";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Button saveBtn, cancelDirectionBtn;
    String direction = "";
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_direction, container, false);
        super.onCreate(savedInstanceState);

        getDialog().setTitle("Change Direction");
        sharedPreferences = getActivity().getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        saveBtn = rootView.findViewById(R.id.saveBtn);
        cancelDirectionBtn = rootView.findViewById(R.id.cancelDirectionBtn);

        direction = sharedPreferences.getString("direction","");

        if (savedInstanceState != null)
            direction = savedInstanceState.getString("direction");


        final Spinner spinr = (Spinner) rootView.findViewById(R.id.directionDropdownSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinr.setAdapter(adapter);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String direction = spinr.getSelectedItem().toString();
                editor.putString("direction",direction);
                ((MainActivity)getActivity()).refreshDirection(direction);
                Toast.makeText(getActivity(), "Saving direction.", Toast.LENGTH_SHORT).show();
                editor.commit();
                getDialog().dismiss();
            }
        });

        cancelDirectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked cancel direction button");
                getDialog().dismiss();
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveBtn = rootView.findViewById(R.id.saveBtn);
        outState.putString(TAG, direction);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private void showLog(String message) {
        Log.d(TAG, message);
    }
}
