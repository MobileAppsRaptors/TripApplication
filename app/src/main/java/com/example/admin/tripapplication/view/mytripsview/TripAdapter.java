package com.example.admin.tripapplication.view.mytripsview;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.util.Events;
import com.example.admin.tripapplication.util.NormalButtonIcon;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
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
        String date = new SimpleDateFormat("MM/dd/yyyy").format(tripList.get(position).getDate());
        holder.tvDate.setText(date);

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = new ArrayList<>();
        double lat = tripList.get(position).getOrigin().getLat();
        double lng = tripList.get(position).getOrigin().getLng();
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses.get(0) != null) {
            holder.tvOrigin.setText(addresses.get(0).getAddressLine(0));
        } else {
            holder.tvOrigin.setText(lat + " ," + lng);
        }

        geocoder = new Geocoder(context, Locale.getDefault());
        addresses = new ArrayList<>();
        lat = tripList.get(position).getDestination().getLat();
        lng = tripList.get(position).getDestination().getLng();
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses.get(0) != null) {
            holder.tvDestination.setText(addresses.get(0).getAddressLine(0));
        } else {
            holder.tvDestination.setText(lat + " ," + lng);
        }

        //TODO rating system dosn't work!
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
        @BindView(R.id.tvOrigin)
        TextView tvOrigin;
        @BindView(R.id.tvDestination)
        TextView tvDestination;
        @BindViews({ R.id.btnOneStar, R.id.btnTwoStar, R.id.btnThreeStar, R.id.btnFourStar, R.id.btnFiveStar })
        List<NormalButtonIcon> nameViews;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}