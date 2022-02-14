package com.knoxtech.fliprhackathonxii.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knoxtech.fliprhackathonxii.R;
import com.knoxtech.fliprhackathonxii.model.Data;

public class DriverAdapter extends FirestoreRecyclerAdapter<Data, DriverAdapter.DataHolder> {
    public DriverAdapter(@NonNull FirestoreRecyclerOptions<Data> options) {
        super(options);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onBindViewHolder(@NonNull DataHolder holder, int position, @NonNull Data model) {
        holder.name.setText(model.getDealer_name());
        holder.mob.setText(model.getNature_of_material());
        String status = model.getStatus();
        String docId = model.getDocId();
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

            TextView Dname,trans,truck_no,age,exp,capacity,from,to;
            Dname = dialog.findViewById(R.id.heading_cast);
            LinearLayout linearLayout = dialog.findViewById(R.id.linearBtn);
            if (status.equals("REJECTED") || status.equals("APPROVED")){
                linearLayout.setVisibility(View.GONE);
            }
            Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
            btnSubmit.setText("ACCEPT");
            btnSubmit.setOnClickListener(v12 -> {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference update = db.collection("BOOKING").document(docId);
                update
                        .update("status", "APPROVED")
                        .addOnSuccessListener(aVoid -> {
                            Log.d("TAG", "DocumentSnapshot successfully updated!");
                            Toast.makeText(v12.getContext(), "Rejected", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Log.w("TAG", "Error updating document", e);
                            Toast.makeText(v12.getContext(), "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                dialog.dismiss();
            });
            Button callBtn = dialog.findViewById(R.id.btnCall);
            callBtn.setText("Reject");
            trans = dialog.findViewById(R.id.driver_trans);
            truck_no = dialog.findViewById(R.id.driver_vehicle_no);
            age = dialog.findViewById(R.id.driver_age);
            exp = dialog.findViewById(R.id.driver_exp);
            capacity = dialog.findViewById(R.id.driver_capacity);
            from = dialog.findViewById(R.id.dfrom);
            to = dialog.findViewById(R.id.dTo);

            Dname.setText(model.getDealer_name());
            trans.setText(model.getNature_of_material());
            truck_no.setText(model.getDealer_mob());

            age.setText("WEIGHT  \t: "+ model.getDealer_weight()+"TON");
            exp.setText("QUANTITY  \t: "+ model.getQuantity());
            capacity.setVisibility(View.GONE);
            from.setText(model.getFrom());
            to.setText(model.getTo());
            dialog.show();

            callBtn.setOnClickListener(v1 -> {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference update = db.collection("BOOKING").document(docId);
                update
                        .update("status", "REJECTED")
                        .addOnSuccessListener(aVoid -> {
                            Log.d("TAG", "DocumentSnapshot successfully updated!");
                            Toast.makeText(v.getContext(), "Rejected", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Log.w("TAG", "Error updating document", e);
                            Toast.makeText(v.getContext(), "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
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

