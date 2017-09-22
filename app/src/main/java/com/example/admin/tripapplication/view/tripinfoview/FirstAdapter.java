package com.example.admin.tripapplication.view.tripinfoview;

import android.content.Context;
import android.media.Rating;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapWell;
import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.util.Events;
import com.example.admin.tripapplication.util.NormalButtonIcon;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static com.example.admin.tripapplication.util.CONSTANTS.*;

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.ViewHolder>{
    private static final String TAG = "FirstAdapter";
    List<User> users;
    Context context;
    private int lastPosition = -1;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    private String unit;

    public FirstAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_users_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final User item = users.get(position);

        holder.tvName.setText(item.getFirstName() + " " + item.getLastName());
        holder.tvPhone.setText(item.getPhoneNumber());
        if(position == 0)
            holder.tvRole.setText("Driver");
        else
            holder.tvRole.setText("Passenger");
        holder.tvStars.setText("5.0");

        //TODO yes we need to store every image fb/google too for this step
        Picasso.with(context).load(item.getImageURL()).into(holder.img);

        holder.btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Events.MessageEvent event = new Events.MessageEvent(START_PHONE, holder.tvPhone.getText());
                EventBus.getDefault().post(event);
            }
        });

        holder.btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Events.MessageEvent event = new Events.MessageEvent(START_MESSAGE_DIALOG, holder.tvPhone.getText());
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.scroll)
        ScrollView scroll;

        @Nullable
        @BindView(R.id.scroll_parent)
        FrameLayout scroll_parent;

        @Nullable
        @BindView(R.id.tvNameParent)
        BootstrapWell tvNameParent;

        @Nullable
        @BindView(R.id.img)
        BootstrapCircleThumbnail img;

        @Nullable
        @BindView(R.id.tvName)
        TextView tvName;

        @Nullable
        @BindView(R.id.tvPhone)
        TextView tvPhone;

        @Nullable
        @BindView(R.id.tvRole)
        TextView tvRole;

        @Nullable
        @BindView(R.id.tvStars)
        TextView tvStars;

        @Nullable
        @BindView(R.id.btnPhone)
        NormalButtonIcon btnPhone;

        @Nullable
        @BindView(R.id.btnMessage)
        NormalButtonIcon btnMessage;

        @Nullable
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
