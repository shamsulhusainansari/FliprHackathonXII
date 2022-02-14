package com.knoxtech.fliprhackathonxii.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.knoxtech.fliprhackathonxii.DealerActivity;
import com.knoxtech.fliprhackathonxii.R;
import com.knoxtech.fliprhackathonxii.model.Data;

public class DataAdapter extends FirestoreRecyclerAdapter<Data, DataAdapter.DataHolder> {
    public DataAdapter(@NonNull FirestoreRecyclerOptions<Data> options) {
        super(options);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onBindViewHolder(@NonNull DataHolder holder, int position, @NonNull Data model) {
        holder.name.setText(model.getTransporter_name());
        holder.mob.setText(model.getDriver_name());
        String status = model.getStatus();
        String red = "\u26AA";
        if (status.equals("REJECTED")){
            holder.status.setTextColor(Color.RED);
            holder.status.setText(red.concat(model.getStatus()));
        }else if (status.equals("APPROVED")) {
            holder.status.setTextColor(Color.GREEN);
            holder.status.setText(red.concat(model.getStatus()));
        }
        else
        {
            holder.status.setTextColor(Color.YELLOW);
            holder.status.setText(red.concat(model.getStatus()));
        }

        holder.showMore.setOnClickListener(v -> {

            final Dialog dialog = new Dialog(v.getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.custom_dialog);

            TextView name,trans,truck_no,age,exp,capacity,from,to;
            name = dialog.findViewById(R.id.heading_cast);
            Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
            trans = dialog.findViewById(R.id.driver_trans);
            truck_no = dialog.findViewById(R.id.driver_vehicle_no);
            age = dialog.findViewById(R.id.driver_age);
            exp = dialog.findViewById(R.id.driver_exp);
            capacity = dialog.findViewById(R.id.driver_capacity);
            from = dialog.findViewById(R.id.dfrom);
            to = dialog.findViewById(R.id.dTo);
            btnSubmit.setVisibility(View.GONE);
            name.setText(model.getDriver_name());
            trans.setText(model.getTransporter_name());
            truck_no.setText(model.getTruck_no());
            age.setText("AGE\t: "+ model.getAge()+"YEARS");
            exp.setText("EXPERIENCE\t: "+ model.getExperience()+"YEARS");
            capacity.setText("CAPACITY\t:"+ model.getCapacity()+"TON");
            from.setText(model.getFrom());
            to.setText(model.getTo());
            dialog.show();

            dialog.findViewById(R.id.btnCall).setOnClickListener(v1 -> {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + model.getDriver_mob()));
                v1.getContext().startActivity(callIntent);
                dialog.dismiss();
            });
            dialog.findViewById(R.id.cancel_button).setOnClickListener(v2 -> dialog.dismiss());
        });

    }
    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_details,
                parent, false);
        return new DataHolder(v);
    }
    static class DataHolder extends RecyclerView.ViewHolder {
        TextView name,mob,status;
        Button showMore;
        public DataHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mob = itemView.findViewById(R.id.mob);
            status = itemView.findViewById(R.id.status);
            showMore = itemView.findViewById(R.id.btnShowMore);

        }
    }
}
