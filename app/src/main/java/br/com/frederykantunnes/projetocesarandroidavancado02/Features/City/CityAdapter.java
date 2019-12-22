package br.com.frederykantunnes.projetocesarandroidavancado02.Features.City;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import br.com.frederykantunnes.projetocesarandroidavancado02.Database.AppDatabase;
import br.com.frederykantunnes.projetocesarandroidavancado02.R;

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CityVO> mCities;
    private RecyclerViewOnClickListenerHackCity mRecyclerViewOnClickListenerHackCity;
    private AppDatabase db;
    private List<CityVO_DB> cityVODbList;

    public CityAdapter(Context context, ArrayList<CityVO> users) {
        this.context = context;
        mCities = users;
        db = Room.databaseBuilder(context,
                AppDatabase.class, "cesar").allowMainThreadQueries().fallbackToDestructiveMigration().build();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tx_principal, tx_secondary, temp, unit, wind;
        private ImageView img, imgButton;
        private ViewHolder(final View itemView) {
            super(itemView);
            tx_principal =  itemView.findViewById(R.id.tvName);
            tx_secondary =  itemView.findViewById(R.id.tvLastMessage);
            temp =  itemView.findViewById(R.id.textView3);
            unit =  itemView.findViewById(R.id.textView5);
            wind =  itemView.findViewById(R.id.textView4);
            img = itemView.findViewById(R.id.imgUser);
            imgButton = itemView.findViewById(R.id.imageButton);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_city, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            final SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
            cityVODbList = db.cityDao().getAll();

            if(sharedPreferences.getString("unit", "c").equalsIgnoreCase("c")){
                vh.unit.setText("ºC");
            }else{
                vh.unit.setText("ºF");
            }

            if(sharedPreferences.getString("lang", "pt").equalsIgnoreCase("pt")){
                vh.wind.setText("Vento "+String.format("%.1f",mCities.get(position).wind.speed)+"m/s  |  Nuvens "+mCities.get(position).clouds.all+"%  |  "+mCities.get(position).main.pressure+" hPa");
            }else{
                vh.wind.setText("Wind "+String.format("%.1f",mCities.get(position).wind.speed)+"m/s  |  Clouds "+mCities.get(position).clouds.all+"%  |  "+mCities.get(position).main.pressure+" hPa");
            }


            Picasso.with(context).load(R.drawable.ic_star_border_black_24dp).error(R.drawable.ic_star_border_black_24dp).into(vh.imgButton);

            for (int i = 0; i < cityVODbList.size(); i++) {
                if (cityVODbList.get(i).id == mCities.get(position).id) {
                    Picasso.with(context).load(R.drawable.ic_star_black_24dp).error(R.drawable.ic_star_black_24dp).into(vh.imgButton);
                }
            }

            vh.temp.setText(String.format("%.0f", mCities.get(position).main.temp));
            vh.tx_principal.setText(mCities.get(position).name+", "+mCities.get(position).sys.country);
            vh.tx_secondary.setText(mCities.get(position).weather.get(0).description);
            Picasso.with(context).load("http://openweathermap.org/img/w/"+mCities.get(position).weather.get(0).icon+".png").error(R.drawable.ic_launcher_background).into(vh.img);

            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerViewOnClickListenerHackCity.onClickListener(mCities.get(position));
                }
            });
        }
    }

    public void setRecyclerViewOnClickListenerHackCity(RecyclerViewOnClickListenerHackCity response){
        mRecyclerViewOnClickListenerHackCity = response;
    }

    @Override
    public int getItemCount() {
        return mCities != null ? mCities.size() : 0;
    }

}
