package com.cemapp.steelframe3D;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class C_DesignListActivity extends AppCompatActivity {

    //variabel
    private static AlertDialog dialogCreateDesign;
    private static CardView cardDefault;
    public static Context appContext;
    private static Dialog dialogEditDesign;
    private static Intent intent = new Intent();

    private static Cursor cursor;
    private static G_DBHelper_Project dbHelper;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_design_list);
        setTitle(D_MainActivity.analysisName);
        appContext = getApplicationContext();
        cardDefault = findViewById(R.id.cardDefault);

        //menampilkan list design dari database
        dbHelper = new G_DBHelper_Project(getApplicationContext());
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_DESIGN +  " WHERE " +
                F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId +
                " AND " + F_ProjectContract.ProjectEntry.ID_ANALYSIS + " = " + D_MainActivity.analysisId;
        cursor = db.rawQuery(sql, null);

        RecyclerViewAdapter_3Design recyclerViewAdapter =
                new RecyclerViewAdapter_3Design(getBaseContext(), cursor);
        RecyclerView recyclerView = findViewById(R.id.recyclerDesign);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

        //menampilkan dialog
        FloatingActionButton fabCreateDesign = findViewById(R.id.fabCreateDesign);
        fabCreateDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaDialog();
            }
        });

        if(D_MainActivity.listDesign.isEmpty()){
            bukaDialog();
        }

        //menampilkan analysis (klik default)
        cardDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D_MainActivity.isAnalysis = true;
                D_MainActivity.spinDesign.setSelection(0);
                intent.setClass(getApplicationContext(), D_MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void bukaDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(C_DesignListActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_03createdesign, null);
        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialogCreateDesign = mBuilder.create();
        dialogCreateDesign.show();

        //baca objek
        final EditText txtDesignName = mView.findViewById(R.id.txtDesignName);
        final EditText txtDesignKode = mView.findViewById(R.id.txtDesignKode);
        Button btnOK = mView.findViewById(R.id.btnOKDesign);
        Button btnCancel = mView.findViewById(R.id.btnCancelDesign);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D_MainActivity.designName = txtDesignName.getText().toString().trim();
                D_MainActivity.designKode = txtDesignKode.getText().toString().trim();

                if (D_MainActivity.designName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Input design name!", Toast.LENGTH_SHORT).show();
                } else if(D_MainActivity.designKode.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Input design code!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Design is created.", Toast.LENGTH_SHORT).show();

                    //set design id
                    String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_DESIGN +  " WHERE " +
                            F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId +
                            " AND " + F_ProjectContract.ProjectEntry.ID_ANALYSIS + " = " + D_MainActivity.analysisId;
                    cursor = db.rawQuery(sql, null);
                    cursor.moveToFirst();
                    D_MainActivity.designId = cursor.getCount();
                    dbHelper.addDesign(db);
                    cursor.close();

                    //set design baru
                    D_MainActivity.isDesignNew = true;
                    D_MainActivity.isAnalysis = false;

                    dialogCreateDesign.dismiss();
                    intent.setClass(getApplicationContext(), D_MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCreateDesign.dismiss();
            }
        });
    }

    public void bukaEditDialog(View view, final int position, final TextView card_code, final TextView card_name) {
        dialogEditDesign = new Dialog(view.getContext());
        dialogEditDesign.setContentView(R.layout.dialog_03createdesign);
        dialogEditDesign.show();

        //baca objek
        final EditText txtDesignName = dialogEditDesign.findViewById(R.id.txtDesignName);
        final EditText txtDesignKode = dialogEditDesign.findViewById(R.id.txtDesignKode);
        Button btnOK = dialogEditDesign.findViewById(R.id.btnOKDesign);
        Button btnCancel = dialogEditDesign.findViewById(R.id.btnCancelDesign);

        //menampilkan kondisi terkini
        String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_DESIGN + " WHERE "
                + F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId + " AND "
                + F_ProjectContract.ProjectEntry.ID_ANALYSIS + " = " + D_MainActivity.analysisId + " AND "
                + F_ProjectContract.ProjectEntry.ID_DESIGN + " = " + position;
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if(cursor != null && cursor.moveToFirst()) {
            txtDesignKode.setText(cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_DESIGN)));
            txtDesignName.setText(cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_DESIGN)));
            cursor.close();
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D_MainActivity.designName = txtDesignName.getText().toString().trim();
                D_MainActivity.designKode = txtDesignKode.getText().toString().trim();

                if (D_MainActivity.designName.isEmpty()) {
                    Toast.makeText(C_DesignListActivity.appContext, "Input design name!", Toast.LENGTH_SHORT).show();
                } else if(D_MainActivity.designKode.isEmpty()) {
                    Toast.makeText(C_DesignListActivity.appContext, "Input design code!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(C_DesignListActivity.appContext, "Design is updated.", Toast.LENGTH_SHORT).show();

                    //set project
                    D_MainActivity.isProjectNew = false;
                    D_MainActivity.isAnalysisNew = false;
                    D_MainActivity.isDesignNew = false;
                    card_code.setText(D_MainActivity.designKode);
                    card_name.setText(D_MainActivity.designName);
                    D_MainActivity.designId = position;
                    dbHelper.updateDesign(db);

                    cursor.close();
                    db.close();
                    dbHelper.close();

                    dialogEditDesign.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditDesign.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        intent.setClass(getApplicationContext(),D_MainActivity.class);
        startActivity(intent);
        finish();
    }
}
