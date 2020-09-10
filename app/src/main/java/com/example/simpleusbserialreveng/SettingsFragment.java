package com.example.simpleusbserialreveng;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Button;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private Button quainButton;
    private Button writeButton;
    private Button readButton;

    public SettingsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        /* set the buttonPress function for the button */
        quainButton = view.findViewById(R.id.quainButton);
        quainButton.setOnClickListener(v -> buttonClick(view));

        /* write button */
        writeButton = view.findViewById(R.id.writeButton);
        writeButton.setOnClickListener(v -> writeTestFile(view));

        /* read button */
        readButton = view.findViewById(R.id.readButton);
        readButton.setOnClickListener(v -> readTestFile(view));

        return view;
    }

    /* button functions */

    public void buttonClick(View view){
        Toast.makeText(view.getContext(), "quain", Toast.LENGTH_SHORT).show();
    }

    public void writeTestFile(View view){
        Activity thisActivity = getActivity();
        try {
            File file = new File(thisActivity.getFilesDir(), "testFiles");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                File gpxfile = new File(file, "bruh");
                FileWriter writer = new FileWriter(gpxfile);
                writer.append("quain");
                writer.flush();
                writer.close();
                Toast.makeText(thisActivity, "Saved to " + gpxfile.getPath(), Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(view.getContext(), "failed writing file", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex){
            Toast.makeText(view.getContext(), "failed to getFilesDir", Toast.LENGTH_SHORT).show();
        }
    }

    public void readTestFile(View view){
        Activity thisActivity = getActivity();
        try {
            File file = new File(thisActivity.getFilesDir(), "testFiles");
            if(file.exists()){
                try {
                    File textfile = new File(file, "bruh");
                    FileReader reader = new FileReader(textfile);
                    char[] buffer = new char[256];
                    reader.read(buffer, 0, 255);
                    reader.close();
                    Toast.makeText(view.getContext(), "> " + new String(buffer), Toast.LENGTH_LONG).show();
                }
                catch(Exception ex) {
                    Toast.makeText(view.getContext(), "failed reading file", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(view.getContext(), "directory does not exist", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex){
            Toast.makeText(view.getContext(), "failed to getFilesDir", Toast.LENGTH_SHORT).show();
        }
    }

}