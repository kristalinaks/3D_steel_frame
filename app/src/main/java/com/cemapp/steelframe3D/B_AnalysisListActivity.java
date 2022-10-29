package com.cemapp.steelframe3D;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class B_AnalysisListActivity extends AppCompatActivity {

    //variabel
    private static AlertDialog dialogCreateAnalysis;
    public static Context appContext;
    private static Dialog dialogEditAnalysis;
    private static Intent intent = new Intent();

    private static Cursor cursor;
    private static G_DBHelper_Project dbHelper;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_analysis_list);
        setTitle(D_MainActivity.projectName);
        appContext = getApplicationContext();
        TextView txtNone = findViewById(R.id.txtNoneAnalysis);

        //menampilkan list analysis dari database
        dbHelper = new G_DBHelper_Project(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_ANALYSIS +
                " WHERE " + F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId;
        cursor = db.rawQuery(sql, null);

        if(cursor.getCount() != 0){
            txtNone.setVisibility(View.GONE);
        } else {
            txtNone.setVisibility(View.VISIBLE);
        }

        RecyclerViewAdapter_2Analysis recyclerViewAdapter =
                new RecyclerViewAdapter_2Analysis(getBaseContext(), cursor);
        RecyclerView recyclerView = findViewById(R.id.recyclerAnalysis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

        //menampilkan dialog
        FloatingActionButton fabCreateAnalysis = findViewById(R.id.fabCreateAnalysis);
        fabCreateAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaDialog();
            }
        });

        if(D_MainActivity.listAnalysis.isEmpty()) {
            bukaDialog();
        }
    }

    private void bukaDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(B_AnalysisListActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_02createanalysis, null);
        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialogCreateAnalysis = mBuilder.create();
        dialogCreateAnalysis.show();

        //baca objek
        final EditText txtAnalysisName = mView.findViewById(R.id.txtAnalysisName);
        final EditText txtAnalysisKode = mView.findViewById(R.id.txtAnalysisKode);
        final EditText txtNst = mView.findViewById(R.id.txtNst);
        final EditText txtNbx = mView.findViewById(R.id.txtNbx);
        final EditText txtNby = mView.findViewById(R.id.txtNby);
        final EditText txtSth = mView.findViewById(R.id.txtSth);
        final EditText txtBwx = mView.findViewById(R.id.txtBwx);
        final EditText txtBwy = mView.findViewById(R.id.txtBwy);
        final EditText txtFy = mView.findViewById(R.id.txtFy);
        final EditText txtFu = mView.findViewById(R.id.txtFu);
        final EditText txtE = mView.findViewById(R.id.txtE);
        Button btnOK = mView.findViewById(R.id.btnOKCreateAnalysis);
        Button btnCancel = mView.findViewById(R.id.btnCancelCreateAnalysis);
        final RadioGroup rgRestraint = mView.findViewById(R.id.rgRestraint);
        final RadioButton rbtnJepit = mView.findViewById(R.id.rbtnJepit);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D_MainActivity.analysisName = txtAnalysisName.getText().toString().trim();
                D_MainActivity.analysisKode = txtAnalysisKode.getText().toString().trim();

                if((txtNst.getText().toString().trim().equals("") || txtNst.getText().toString().trim().equals("0"))
                    || (txtNbx.getText().toString().trim().equals("") || txtNbx.getText().toString().trim().equals("0"))
                    || (txtNby.getText().toString().trim().equals("") || txtNby.getText().toString().trim().equals("0"))
                    || (txtSth.getText().toString().trim().equals("") || txtSth.getText().toString().trim().equals("0"))
                    || (txtBwx.getText().toString().trim().equals("") || txtBwx.getText().toString().trim().equals("0"))
                    || (txtBwy.getText().toString().trim().equals("") || txtBwy.getText().toString().trim().equals("0"))
                    || D_MainActivity.analysisName.isEmpty()
                    || D_MainActivity.analysisKode.isEmpty()){
                    //input tidak lengkap
                    Toast.makeText(getApplicationContext(), "Input is incomplete!", Toast.LENGTH_SHORT).show();
                }else{
                    //input lengkap
                    D_MainActivity.Nst = Integer.parseInt(txtNst.getText().toString().trim());
                    D_MainActivity.Nbx = Integer.parseInt(txtNbx.getText().toString().trim());
                    D_MainActivity.Nby = Integer.parseInt(txtNby.getText().toString().trim());
                    D_MainActivity.Sth = Float.parseFloat(txtSth.getText().toString().trim());
                    D_MainActivity.Bwx = Float.parseFloat(txtBwx.getText().toString().trim());
                    D_MainActivity.Bwy = Float.parseFloat(txtBwy.getText().toString().trim());
                    D_MainActivity.isRestraintPin = !(rgRestraint.getCheckedRadioButtonId() == rbtnJepit.getId());
                    D_MainActivity.bajaFy = Float.parseFloat(txtFy.getText().toString().trim());
                    D_MainActivity.bajaFu = Float.parseFloat(txtFu.getText().toString().trim());
                    D_MainActivity.bajaE = Float.parseFloat(txtE.getText().toString().trim());

                    //set analysis id
                    String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_ANALYSIS +  " WHERE " +
                            F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId;
                    cursor = db.rawQuery(sql, null);
                    cursor.moveToFirst();
                    D_MainActivity.analysisId = cursor.getCount();
                    dbHelper.addAnalysis(db);
                    cursor.close();

                    //set analysis baru
                    D_MainActivity.isAnalysisNew = true;
                    D_MainActivity.isAnalysis = true;

                    db.close();
                    dbHelper.close();

                    Toast.makeText(getApplicationContext(), "Analysis is created.", Toast.LENGTH_SHORT).show();
                    dialogCreateAnalysis.dismiss();
                    intent.setClass(getApplicationContext(),D_MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!D_MainActivity.listAnalysis.isEmpty()) {
                    dialogCreateAnalysis.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(),"New project must contain at least 1 analysis.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void bukaEditDialog(View view, final int position, final TextView card_code, final TextView card_name) {
        dialogEditAnalysis = new Dialog(view.getContext());
        dialogEditAnalysis.setContentView(R.layout.dialog_02createanalysis);
        dialogEditAnalysis.show();

        //baca objek
        final EditText txtAnalysisName = dialogEditAnalysis.findViewById(R.id.txtAnalysisName);
        final EditText txtAnalysisKode = dialogEditAnalysis.findViewById(R.id.txtAnalysisKode);
        final EditText txtNst = dialogEditAnalysis.findViewById(R.id.txtNst);
        final EditText txtNbx = dialogEditAnalysis.findViewById(R.id.txtNbx);
        final EditText txtNby = dialogEditAnalysis.findViewById(R.id.txtNby);
        final EditText txtSth = dialogEditAnalysis.findViewById(R.id.txtSth);
        final EditText txtBwx = dialogEditAnalysis.findViewById(R.id.txtBwx);
        final EditText txtBwy = dialogEditAnalysis.findViewById(R.id.txtBwy);
        final EditText txtFy = dialogEditAnalysis.findViewById(R.id.txtFy);
        final EditText txtFu = dialogEditAnalysis.findViewById(R.id.txtFu);
        final EditText txtE = dialogEditAnalysis.findViewById(R.id.txtE);
        Button btnOK = dialogEditAnalysis.findViewById(R.id.btnOKCreateAnalysis);
        Button btnCancel = dialogEditAnalysis.findViewById(R.id.btnCancelCreateAnalysis);
        final RadioGroup rgRestraint = dialogEditAnalysis.findViewById(R.id.rgRestraint);
        final RadioButton rbtnJepit = dialogEditAnalysis.findViewById(R.id.rbtnJepit);

        //menonaktifkan edittext yang vital
        txtNst.setEnabled(false);
        txtNbx.setEnabled(false);
        txtNby.setEnabled(false);

        //menampilkan kondisi terkini
        String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_ANALYSIS + " WHERE "
                + F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId + " AND "
                + F_ProjectContract.ProjectEntry.ID_ANALYSIS + " = " + position;
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if(cursor != null && cursor.moveToFirst()) {
            txtAnalysisKode.setText(cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_ANALYSIS)));
            txtAnalysisName.setText(cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_ANALYSIS)));
            txtNst.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.NST))));
            txtNbx.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.NBX))));
            txtNby.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.NBY))));
            txtSth.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.STH))));
            txtBwx.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.BWX))));
			txtBwy.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.BWY))));
            txtFy.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.FY))));
            txtFu.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.FU))));
            txtE.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.E))));
            String sendi = cursor.getString(cursor.getColumnIndex(F_ProjectContract.ProjectEntry.SENDI));
            if(sendi.equals("1")){
                rbtnJepit.setChecked(false);
            }else{
                rbtnJepit.setChecked(true);
            }
            cursor.close();
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D_MainActivity.analysisName = txtAnalysisName.getText().toString().trim();
                D_MainActivity.analysisKode = txtAnalysisKode.getText().toString().trim();

                if((txtNst.getText().toString().trim().equals("") || txtNst.getText().toString().trim().equals("0"))
                        || (txtNbx.getText().toString().trim().equals("") || txtNbx.getText().toString().trim().equals("0"))
                        || (txtNby.getText().toString().trim().equals("") || txtNby.getText().toString().trim().equals("0"))
                        || (txtSth.getText().toString().trim().equals("") || txtSth.getText().toString().trim().equals("0"))
                        || (txtBwx.getText().toString().trim().equals("") || txtBwx.getText().toString().trim().equals("0"))
                        || (txtBwy.getText().toString().trim().equals("") || txtBwy.getText().toString().trim().equals("0"))
                        || D_MainActivity.analysisName.isEmpty()
                        || D_MainActivity.analysisKode.isEmpty()){
                    //input tidak lengkap
                    Toast.makeText(B_AnalysisListActivity.appContext, "Input is incomplete!", Toast.LENGTH_SHORT).show();
                }else{
                    //input lengkap
                    D_MainActivity.Nst = Integer.parseInt(txtNst.getText().toString().trim());
                    D_MainActivity.Nbx = Integer.parseInt(txtNbx.getText().toString().trim());
                    D_MainActivity.Nby = Integer.parseInt(txtNby.getText().toString().trim());
                    D_MainActivity.Sth = Float.parseFloat(txtSth.getText().toString().trim());
                    D_MainActivity.Bwx = Float.parseFloat(txtBwx.getText().toString().trim());
                    D_MainActivity.Bwy = Float.parseFloat(txtBwy.getText().toString().trim());
                    D_MainActivity.isRestraintPin = !(rgRestraint.getCheckedRadioButtonId() == rbtnJepit.getId());
                    D_MainActivity.bajaFy = Float.parseFloat(txtFy.getText().toString().trim());
                    D_MainActivity.bajaFu = Float.parseFloat(txtFu.getText().toString().trim());
                    D_MainActivity.bajaE = Float.parseFloat(txtE.getText().toString().trim());

                    //set project
                    D_MainActivity.isProjectNew = false;
                    D_MainActivity.isAnalysisNew = false;
                    card_code.setText(D_MainActivity.analysisKode);
                    card_name.setText(D_MainActivity.analysisName);
                    D_MainActivity.analysisId = position;
                    dbHelper.updateAnalysis(db);

                    cursor.close();
                    db.close();
                    dbHelper.close();

                    Toast.makeText(B_AnalysisListActivity.appContext, "Analysis is updated.", Toast.LENGTH_SHORT).show();
                    dialogEditAnalysis.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditAnalysis.dismiss();
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
