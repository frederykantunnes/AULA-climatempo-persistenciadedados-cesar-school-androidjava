package br.com.frederykantunnes.projetocesarandroidavancado02.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.com.frederykantunnes.projetocesarandroidavancado02.R;

public class SettingsView extends AppCompatActivity {

    private final String settings = "settings";
    private final String unn = "unit";
    private final String lg = "lang";
    RadioGroup language, unit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences sharedPreferences = getSharedPreferences(settings, Context.MODE_PRIVATE);

        unit=findViewById(R.id.rg_unit);
        language=findViewById(R.id.rg_language);

        if(sharedPreferences.getString(lg, "pt").equalsIgnoreCase("pt")){
            language.check(R.id.pt);
        }else{
            language.check(R.id.en);
        }

        if(sharedPreferences.getString(unn, "c").equalsIgnoreCase("c")){
            unit.check(R.id.c);
        }else{
            unit.check(R.id.f);
        }



        unit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(i == R.id.c){
                    editor.putString(unn, "c");
                    editor.apply();
                }
                if(i == R.id.f){
                    editor.putString(unn, "f");
                    editor.apply();
                }
            }
        });

        language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(i == R.id.en){
                    editor.putString(lg, "en");
                    editor.apply();
                }
                if(i == R.id.pt){
                    editor.putString(lg, "pt");
                    editor.apply();
                }

            }
        });
    }
}
