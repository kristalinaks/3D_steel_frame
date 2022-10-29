package com.cemapp.steelframe3D;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class A_ProjectListActivity extends AppCompatActivity {

    //variabel
    private static AlertDialog dialogCreateProject;
    public static  Context appContext;
    private static Dialog dialogEditProject;
    public static LayoutInflater layoutInflater;
    private static Intent intent = new Intent();

    private static Cursor cursor;
    private static G_DBHelper_Project dbHelper;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_project_list);
        appContext = getApplicationContext();
        layoutInflater = getLayoutInflater();
        TextView txtNone = findViewById(R.id.txtNoneProject);

        //menampilkan list project dari database
        dbHelper = new G_DBHelper_Project(getApplicationContext());
        db = dbHelper.getReadableDatabase();
        cursor = dbHelper.readMainProject(db);

        if(cursor.getCount() != 0){
            txtNone.setVisibility(View.GONE);
        } else {
            txtNone.setVisibility(View.VISIBLE);
        }

        RecyclerViewAdapter_1Project recyclerViewAdapter =
                new RecyclerViewAdapter_1Project(getBaseContext(), cursor);
        RecyclerView recyclerView = findViewById(R.id.recyclerProject);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

        //menampilkan dialog
        FloatingActionButton fabCreateProject = findViewById(R.id.fabCreateProject);
        fabCreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaDialog();
            }
        });

        if(D_MainActivity.isCreateProject) {
            bukaDialog();
        }
    }

    public void bukaDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(A_ProjectListActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_01createproject, null);
        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialogCreateProject = mBuilder.create();
        dialogCreateProject.show();

        //baca objek
        final EditText txtProjectEngineer = mView.findViewById(R.id.txtProjectEngineer);
        final EditText txtProjectKode = mView.findViewById(R.id.txtProjectKode);
        final EditText txtProjectName = mView.findViewById(R.id.txtProjectName);
        Button btnOK = mView.findViewById(R.id.btnOKCreateProject);
        Button btnCancel = mView.findViewById(R.id.btnCancelCreateProject);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //assign variabel
                D_MainActivity.projectEngineer = txtProjectEngineer.getText().toString().trim();
                D_MainActivity.projectKode = txtProjectKode.getText().toString().trim();
                D_MainActivity.projectName = txtProjectName.getText().toString().trim();

                if (D_MainActivity.projectName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Input project name!", Toast.LENGTH_SHORT).show();
                } else if(D_MainActivity.projectKode.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input project code!", Toast.LENGTH_SHORT).show();
                } else if(D_MainActivity.projectEngineer.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input project engineer!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Project is created. Add your analysis.", Toast.LENGTH_SHORT).show();

                    //set project baru
                    D_MainActivity.isProjectNew = true;
                    D_MainActivity.projectId = cursor.getCount();
                    D_MainActivity.listAnalysis.clear();
                    dbHelper.addProject(db);

                    cursor.close();
                    db.close();
                    dbHelper.close();

                    dialogCreateProject.dismiss();
                    intent.setClass(getApplicationContext(), B_AnalysisListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCreateProject.dismiss();
            }
        });
    }

    public void bukaEditDialog(View view, final int position, final TextView card_code, final TextView card_name,
                               final TextView card_engineer){
        dialogEditProject = new Dialog(view.getContext());
        dialogEditProject.setContentView(R.layout.dialog_01createproject);
        dialogEditProject.show();
        
        //baca objek
        final EditText txtProjectEngineer = dialogEditProject.findViewById(R.id.txtProjectEngineer);
        final EditText txtProjectKode = dialogEditProject.findViewById(R.id.txtProjectKode);
        final EditText txtProjectName = dialogEditProject.findViewById(R.id.txtProjectName);
        Button btnOK = dialogEditProject.findViewById(R.id.btnOKCreateProject);
        Button btnCancel = dialogEditProject.findViewById(R.id.btnCancelCreateProject);

        //menampilkan kondisi terkini
        String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_PROJECT + " WHERE "
                + F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + position;
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if(cursor != null && cursor.moveToFirst()) {
            txtProjectKode.setText(cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_PROJECT)));
            txtProjectName.setText(cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_PROJECT)));
            txtProjectEngineer.setText(cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.ENGINEER)));
            cursor.close();
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //assign variabel
                D_MainActivity.projectEngineer = txtProjectEngineer.getText().toString().trim();
                D_MainActivity.projectKode = txtProjectKode.getText().toString().trim();
                D_MainActivity.projectName = txtProjectName.getText().toString().trim();

                if (D_MainActivity.projectName.isEmpty()) {
                    Toast.makeText(A_ProjectListActivity.appContext, "Input project name!", Toast.LENGTH_SHORT).show();
                } else if(D_MainActivity.projectKode.isEmpty()){
                    Toast.makeText(A_ProjectListActivity.appContext, "Input project code!", Toast.LENGTH_SHORT).show();
                } else if(D_MainActivity.projectEngineer.isEmpty()){
                    Toast.makeText(A_ProjectListActivity.appContext, "Input project engineer!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(A_ProjectListActivity.appContext, "Project is updated.", Toast.LENGTH_SHORT).show();

                    //set project
                    D_MainActivity.isProjectNew = false;
                    card_code.setText(D_MainActivity.projectKode);
                    card_name.setText(D_MainActivity.projectName);
                    card_engineer.setText(D_MainActivity.projectEngineer);
                    D_MainActivity.projectId = position;
                    dbHelper.updateProject(db);

                    cursor.close();
                    db.close();
                    dbHelper.close();

                    dialogEditProject.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditProject.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (D_MainActivity.projectId != -1) {
            intent.setClass(getApplicationContext(), D_MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
