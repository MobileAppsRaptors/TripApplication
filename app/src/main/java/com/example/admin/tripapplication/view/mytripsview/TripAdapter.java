package com.example.admin.tripapplication.view.mytripsview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.util.Events;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.admin.tripapplication.util.CONSTANTS.OPEN_TRIP_VIEW;

/**
 * Created by Admin on 9/22/2017.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {


    ArrayList<Trip> tripList = new ArrayList<>();
    Context context;

    public TripAdapter(ArrayList<Trip> tripList) {
        this.tripList = tripList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.trip_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripAdapter.ViewHolder holder, final int position) {
        String name = tripList.get(position).getCreator().getFirstName() + " " + tripList.get(position).getCreator().getLastName();
        holder.tvCreator.setText(name);
        Date d = tripList.get(position).getDate();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        holder.tvDate.setText(month + "/" + day + "/" + year);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Events.MessageEvent event = new Events.MessageEvent(OPEN_TRIP_VIEW, tripList.get(position));
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tripList != null) {
            return tripList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCreator)
        TextView tvCreator;
        @BindView(R.id.tvDate)
        TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}