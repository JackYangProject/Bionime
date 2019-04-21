package com.bionime.bionimedemo.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bionime.bionimedemo.BaseCollectionAdapterDelegate;
import com.bionime.bionimedemo.R;

import java.util.ArrayList;
import java.util.List;

public class Listadpter extends  RecyclerView.Adapter<Listadpter.ViewHolder>{
    private List<Myaqi> myaqis = new ArrayList<>();
    private BaseCollectionAdapterDelegate delegate;
    private Context context;
    public Listadpter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Myaqi myaqi = myaqis.get(position);
        holder.myaqis_sitename.setText(myaqi.getSiteName());
        holder.myaqis_county.setText(myaqi.getCounty());
        holder.myaqis_aqi.setText(myaqi.getAqi());
        holder.myaqis_status.setText(myaqi.getStatus());
        holder.myaqis_pollutant.setText(myaqi.getPollutant());
        int myaqiColor = Integer.valueOf(myaqi.getAqi());
        if (myaqiColor <= 50){
            holder.myaqis_status.setTextColor(context.getResources().getColor(R.color.green));
        }else if ( 51 <= myaqiColor  && myaqiColor < 100){
            holder.myaqis_status.setTextColor(context.getResources().getColor(R.color.yellow));
        }else if (101 <= myaqiColor  && myaqiColor < 150){
            holder.myaqis_status.setTextColor(context.getResources().getColor(R.color.orange));
        }else {
            holder.myaqis_status.setTextColor(context.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return myaqis.size();
    }

    public void setMyaqis(List<Myaqi> myaqis) {
        this.myaqis = myaqis;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myaqis_sitename,myaqis_county,myaqis_aqi,myaqis_status,myaqis_pollutant;

        ViewHolder(View itemView) {
            super(itemView);
            myaqis_sitename = itemView.findViewById(R.id.sitename);
            myaqis_county = itemView.findViewById(R.id.county);
            myaqis_aqi = itemView.findViewById(R.id.aqi);
            myaqis_status = itemView.findViewById(R.id.status);
            myaqis_pollutant = itemView.findViewById(R.id.pollutant);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (delegate != null) {
                delegate.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public void setupDelegate(BaseCollectionAdapterDelegate delegate) {
        this.delegate = delegate;
    }
}
