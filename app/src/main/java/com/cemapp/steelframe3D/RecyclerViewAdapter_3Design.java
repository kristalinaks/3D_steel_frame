package com.cemapp.steelframe3D;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerViewAdapter_3Design extends RecyclerView.Adapter<RecyclerViewAdapter_3Design.ViewHolder> {
    private Context context;
    private Cursor cursor;
    private static G_DBHelper_Project dbHelper;
    private static SQLiteDatabase db;

    public RecyclerViewAdapter_3Design(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.z_card_3design, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.imgEditDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = viewHolder.getAdapterPosition();
                C_DesignListActivity c = new C_DesignListActivity();
                c.bukaEditDialog(v, x, viewHolder.txtDesignKode, viewHolder.txtDesignName);
            }
        });

        viewHolder.imgDeleteDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = viewHolder.getAdapterPosition();

                if(x == D_MainActivity.designId){
                    Toast.makeText(C_DesignListActivity.appContext, "Design is opened!", Toast.LENGTH_SHORT).show();

                }else{
                    //Database
                    dbHelper = new G_DBHelper_Project(C_DesignListActivity.appContext);
                    db = dbHelper.getWritableDatabase();
                    dbHelper.deleteDesign(db, D_MainActivity.projectId, D_MainActivity.analysisId, x);
                    db.close();

                    db = dbHelper.getReadableDatabase();
                    cursor.close();
                    cursor = dbHelper.readMainDesign(db);
                    notifyItemRemoved(x);
                }
            }
        });

        viewHolder.cardDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = viewHolder.getAdapterPosition();

                D_MainActivity.isProjectNew = false;
                D_MainActivity.isAnalysisNew = false;
                D_MainActivity.isDesignNew = false;
                D_MainActivity.isAnalysis = false;
                D_MainActivity.designId = x;

                Intent intent = new Intent();
                intent.setClass(context,D_MainActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }

        String kode = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_DESIGN));
        String name = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_DESIGN));

        holder.txtDesignKode.setText(kode);
        holder.txtDesignName.setText(name);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton imgEditDesign;
        private ImageButton imgDeleteDesign;
        private CardView cardDesign;
        private TextView txtDesignKode;
        private TextView txtDesignName;

        public ViewHolder(View cardView) {
            super(cardView);

            cardDesign = cardView.findViewById(R.id.cardDesign);
            imgEditDesign = cardView.findViewById(R.id.imgEditDesign);
            imgDeleteDesign = cardView.findViewById(R.id.imgDeleteDesign);
            txtDesignKode = cardView.findViewById(R.id.txtDesignKodeCard);
            txtDesignName = cardView.findViewById(R.id.txtDesignNameCard);
        }
    }
}
