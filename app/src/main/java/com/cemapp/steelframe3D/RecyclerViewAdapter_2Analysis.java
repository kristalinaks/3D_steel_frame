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

public class RecyclerViewAdapter_2Analysis extends RecyclerView.Adapter<RecyclerViewAdapter_2Analysis.ViewHolder> {
    private Context context;
    private Cursor cursor;
    private static G_DBHelper_Project dbHelper;
    private static SQLiteDatabase db;

    public RecyclerViewAdapter_2Analysis(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public RecyclerViewAdapter_2Analysis.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.z_card_2analysis, parent, false);
        final RecyclerViewAdapter_2Analysis.ViewHolder viewHolder = new RecyclerViewAdapter_2Analysis.ViewHolder(view);

        viewHolder.imgEditAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = viewHolder.getAdapterPosition();
                B_AnalysisListActivity b = new B_AnalysisListActivity();
                b.bukaEditDialog(v, x, viewHolder.txtAnalysisKode, viewHolder.txtAnalysisName);
            }
        });

        viewHolder.imgDeleteAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = viewHolder.getAdapterPosition();

                if(x == D_MainActivity.analysisId){
                    Toast.makeText(B_AnalysisListActivity.appContext, "Analysis is opened!", Toast.LENGTH_SHORT).show();

                }else{
                    //Database
                    dbHelper = new G_DBHelper_Project(B_AnalysisListActivity.appContext);
                    db = dbHelper.getWritableDatabase();
                    dbHelper.deleteAnalysis(db, D_MainActivity.projectId, x);
                    db.close();

                    db = dbHelper.getReadableDatabase();
                    cursor.close();
                    cursor = dbHelper.readMainAnalysis(db);
                    notifyItemRemoved(x);
                }
            }
        });

        viewHolder.cardAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = viewHolder.getAdapterPosition();

                D_MainActivity.isProjectNew = false;
                D_MainActivity.isAnalysisNew = false;
                D_MainActivity.analysisId = x;
                D_MainActivity.isAnalysis = true;

                Intent intent = new Intent();
                intent.setClass(context,D_MainActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_2Analysis.ViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_ANALYSIS));
        String kode = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_ANALYSIS));

        holder.txtAnalysisName.setText(name);
        holder.txtAnalysisKode.setText(kode);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton imgEditAnalysis;
        private ImageButton imgDeleteAnalysis;
        private CardView cardAnalysis;
        private TextView txtAnalysisKode;
        private TextView txtAnalysisName;

        public ViewHolder(View cardView) {
            super(cardView);

            cardAnalysis = cardView.findViewById(R.id.cardAnalysis);
            imgEditAnalysis = cardView.findViewById(R.id.imgEditAnalysis);
            imgDeleteAnalysis = cardView.findViewById(R.id.imgDeleteAnalysis);
            txtAnalysisKode = cardView.findViewById(R.id.txtAnalysisKodeCard);
            txtAnalysisName = cardView.findViewById(R.id.txtAnalysisNameCard);
        }
    }
}
