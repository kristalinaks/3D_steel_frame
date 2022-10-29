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

public class RecyclerViewAdapter_1Project extends RecyclerView.Adapter<RecyclerViewAdapter_1Project.ViewHolder> {
    private Context context;
    private Cursor cursor;
    private static G_DBHelper_Project dbHelper;
    private static SQLiteDatabase db;

    public RecyclerViewAdapter_1Project(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.z_card_1project, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.imgEditProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = viewHolder.getAdapterPosition();
                A_ProjectListActivity a = new A_ProjectListActivity();
                a.bukaEditDialog(v, x, viewHolder.txtProjectKode, viewHolder.txtProjectName, viewHolder.txtProjectEngineer);
            }
        });

        viewHolder.imgDeleteProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = viewHolder.getAdapterPosition();

                if(x == D_MainActivity.projectId){
                    Toast.makeText(A_ProjectListActivity.appContext, "Project is opened!", Toast.LENGTH_SHORT).show();

                }else{
                    //Database
                    dbHelper = new G_DBHelper_Project(A_ProjectListActivity.appContext);
                    db = dbHelper.getWritableDatabase();
                    dbHelper.deleteProject(db, x);
                    db.close();

                    db = dbHelper.getReadableDatabase();
                    cursor.close();
                    cursor = dbHelper.readMainProject(db);
                    notifyItemRemoved(x);
                }
            }
        });

        viewHolder.cardProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = viewHolder.getAdapterPosition();

                D_MainActivity.isProjectNew = false;
                D_MainActivity.isAnalysisNew = false;
                D_MainActivity.projectId = x;
                D_MainActivity.analysisId = 0;
                D_MainActivity.isAnalysis = true;

                dbHelper = new G_DBHelper_Project(A_ProjectListActivity.appContext);
                db = dbHelper.getReadableDatabase();
                cursor = dbHelper.readMainProject(db);
                if(cursor != null && cursor.moveToPosition(D_MainActivity.projectId)){
                    D_MainActivity.projectKode = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_PROJECT));
                    D_MainActivity.projectName = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_PROJECT));
                    D_MainActivity.projectEngineer = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.ENGINEER));
                    cursor.close();
                }

                String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_ANALYSIS + " WHERE " + F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId;
                cursor = db.rawQuery(sql,null);
                int jmlAnalysis = cursor.getCount();

                Intent intent = new Intent();
                if(jmlAnalysis>0) {
                    intent.setClass(context, D_MainActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    intent.setClass(context, B_AnalysisListActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }

        String engineer = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.ENGINEER));
        String kode = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_PROJECT));
        String name = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_PROJECT));

        holder.txtProjectEngineer.setText("Engineer: " + engineer);
        holder.txtProjectKode.setText(kode);
        holder.txtProjectName.setText(name);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton imgEditProject;
        private ImageButton imgDeleteProject;
        private CardView cardProject;
        private TextView txtProjectEngineer;
        private TextView txtProjectKode;
        private TextView txtProjectName;

        public ViewHolder(View cardView) {
            super(cardView);

            cardProject = cardView.findViewById(R.id.cardProject);
            imgEditProject = cardView.findViewById(R.id.imgEditProject);
            imgDeleteProject = cardView.findViewById(R.id.imgDeleteProject);
            txtProjectEngineer = cardView.findViewById(R.id.txtProjectEngineerCard);
            txtProjectKode = cardView.findViewById(R.id.txtProjectKodeCard);
            txtProjectName = cardView.findViewById(R.id.txtProjectNameCard);
        }
    }
}
