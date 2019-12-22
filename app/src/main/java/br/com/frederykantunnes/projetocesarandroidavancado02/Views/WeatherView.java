package br.com.frederykantunnes.projetocesarandroidavancado02.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.frederykantunnes.projetocesarandroidavancado02.Features.City.CityVO;
import br.com.frederykantunnes.projetocesarandroidavancado02.Features.City.CityVO_DB;
import br.com.frederykantunnes.projetocesarandroidavancado02.Database.AppDatabase;
import br.com.frederykantunnes.projetocesarandroidavancado02.Features.City.CityAdapter;
import br.com.frederykantunnes.projetocesarandroidavancado02.Database.FindResult;
import br.com.frederykantunnes.projetocesarandroidavancado02.R;
import br.com.frederykantunnes.projetocesarandroidavancado02.Features.City.RecyclerViewOnClickListenerHackCity;
import br.com.frederykantunnes.projetocesarandroidavancado02.Database.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherView extends AppCompatActivity {

    private ArrayList<CityVO> list = new ArrayList<>();
    LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private CityAdapter mAdapter;
    private EditText text;
    private ProgressBar progressBar;
    private String lang, unit;
    public List<CityVO_DB> cityDBList;
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recicle_list);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CityAdapter(this,list);
        recyclerView.setAdapter(mAdapter);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        text = findViewById(R.id.edtCityName);
        ImageButton btn = findViewById(R.id.btnSearch);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar();
            }
        });


        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cesar").allowMainThreadQueries().fallbackToDestructiveMigration().build();

//        Log.d("deucerto", db.cityDao().getAll().get(0).name+"");


        mAdapter.setRecyclerViewOnClickListenerHackCity(new RecyclerViewOnClickListenerHackCity() {
            @Override
            public void onClickListener(CityVO usuario) {
                final CityVO city = usuario;
                int status = 0;
                if(cityDBList.size()>0) {
                    for (int i = 0; i < cityDBList.size(); i++) {
                        if (cityDBList.get(i).id == city.id) {
                            status=1;
                        }
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(WeatherView.this);
                builder.setTitle("Favoritos");
                builder.setIcon(R.drawable.ic_star_black_24dp);
                if(status==0){
                    builder.setMessage("Deseja Adiconar "+city.name+" aos favoritos?");
                    builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CityVO_DB cityDB = new CityVO_DB();
                            cityDB.id = city.id;
                            cityDB.name = city.name;
                            db.cityDao().insert(cityDB);
                            Toast.makeText(WeatherView.this, "Local Adicionado aos Favoritos", Toast.LENGTH_LONG).show();
                            buscar();
                        }
                    });
                }else{
                    builder.setMessage("Deseja Remover "+city.name+" aos favoritos?");
                    builder.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CityVO_DB cityDB = new CityVO_DB();
                            cityDB.id = city.id;
                            cityDB.name = city.name;
                            db.cityDao().delete(cityDB);
                            Toast.makeText(WeatherView.this, "Local Removido dos Favoritos", Toast.LENGTH_LONG).show();
                            buscar();
                        }
                    });
                }

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(WeatherView.this, db.userDao().getAll().get(0).firstName, Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.config){
            startActivity(new Intent(WeatherView.this, SettingsView.class));
        }
        return true;
    }

    public boolean isDeviceConnecter(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    public void buscar(){

        final SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("lang", "pt").equalsIgnoreCase("pt")){ lang = "pt"; }else{ lang = "en"; }
        if(sharedPreferences.getString("unit", "c").equalsIgnoreCase("c")){ unit = "metric"; }else{ unit = "imperial"; }

        InputMethodManager imm = (InputMethodManager) WeatherView.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(text.getWindowToken(), 0);

        progressBar.setVisibility(View.VISIBLE);
        cityDBList = db.cityDao().getAll();
        if(text.getText().length()>0){
            if (isDeviceConnecter()) {
                RetrofitManager retrofitManager = new RetrofitManager();
                retrofitManager.getService().find(text.getText().toString(), lang, unit, RetrofitManager.API_KEY).enqueue(new Callback<FindResult>() {
                    @Override
                    public void onResponse(Call<FindResult> call, Response<FindResult> response) {
                        list.clear();
                        list.addAll(response.body().list);
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<FindResult> call, Throwable t) {
                        Toast.makeText(WeatherView.this, "Ops, algo deu errado!", Toast.LENGTH_LONG).show();
                        Log.d("SASA", call.toString() + ">>>" + t.getMessage().toString());
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            } else {
                Toast.makeText(WeatherView.this, "Device is not Connected, try again later", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }


    }else {

            String lista="";
            if(cityDBList.size()>0){
                for(int i=0; i<cityDBList.size();i++){
                    if(i==0){
                        lista = lista+cityDBList.get(i).id;
                    }else{
                        lista = lista+","+cityDBList.get(i).id;
                    }
                }

            if (isDeviceConnecter()) {
                RetrofitManager retrofitManager = new RetrofitManager();
                retrofitManager.getService().findFavorites( lista, lang, unit, RetrofitManager.API_KEY).enqueue(new Callback<FindResult>() {
                    @Override
                    public void onResponse(Call<FindResult> call, Response<FindResult> response) {
                        list.clear();
                        list.addAll(response.body().list);
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<FindResult> call, Throwable t) {
                        Toast.makeText(WeatherView.this, "Ops, algo deu errado!", Toast.LENGTH_LONG).show();
                        Log.d("SASA", call.toString() + ">>>" + t.getMessage().toString());
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }}else{
                list.clear();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(WeatherView.this, "Nenhum Favorito!", Toast.LENGTH_LONG).show();
            }
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        buscar();
    }
}
