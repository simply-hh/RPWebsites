package sg.edu.rp.c346.rpwebsites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Spinner spnCat;
    Spinner spnSub;
    Button btnGo;
    ArrayList<String> alSub;
    ArrayAdapter<String> aaSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alSub = new ArrayList<>();
        spnCat = findViewById(R.id.spinnerCategory);
        spnSub = findViewById(R.id.spinnerSub);
        btnGo = findViewById(R.id.buttonGo);
        aaSub = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alSub);
        spnSub.setAdapter(aaSub);
        spnCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        alSub.clear();
                        String[] strSub = getResources().getStringArray(R.array.RP_sub);
                        alSub.addAll(Arrays.asList(strSub));
                        break;
                    case 1:
                        alSub.clear();
                        strSub = getResources().getStringArray(R.array.SOI_sub);
                        alSub.addAll(Arrays.asList(strSub));
                        break;
                }
                aaSub.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), WebActivity.class);
                String[][] sites = {
                        {
                                "https://www.rp.edu.sg",
                                "https://www.rp.edu.sg/student-life",
                        },
                        {
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47",
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12"
                        }
                };
                String url = sites[spnCat.getSelectedItemPosition()][spnSub.getSelectedItemPosition()];
                intent.putExtra("site", url);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        int posCat = spnCat.getSelectedItemPosition();
        int posSub = spnSub.getSelectedItemPosition();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putInt("cat", posCat);
        prefEdit.putInt("sub", posSub);
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int posCat = prefs.getInt("cat", 0);
        int posSub = prefs.getInt("sub", 0);
        spnCat.setSelection(posCat);
        alSub.clear();
        if (posCat == 0) {
            String [] strSub = getResources().getStringArray(R.array.RP_sub);
            alSub.addAll(Arrays.asList(strSub));
        }
        else if (posCat == 1){
            String [] strSub = getResources().getStringArray(R.array.SOI_sub);
            alSub.addAll(Arrays.asList(strSub));
        }
        spnSub.setSelection(posSub);

    }
}
