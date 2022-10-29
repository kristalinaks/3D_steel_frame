package com.cemapp.steelframe3D;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cemapp.steelframe3D.F_ProjectContract.*;

public class G_DBHelper_Project extends SQLiteOpenHelper {

    //If you change the database schema, you must increment the database version
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "projectDatabase.db";

    public G_DBHelper_Project(Context context) {
        //Create Database
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating a table project
        final String SQL_CREATE_PROJECT = "CREATE TABLE " +
                ProjectEntry.TABLE_PROJECT + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER, " +
                ProjectEntry.KODE_PROJECT + " TEXT, " +
                ProjectEntry.NAME_PROJECT + " TEXT, " +
                ProjectEntry.ENGINEER + " TEXT" +
                ");";

        //Creating a table analysis
        final String SQL_CREATE_ANALYSIS = "CREATE TABLE " +
                ProjectEntry.TABLE_ANALYSIS + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER," +
                ProjectEntry.ID_ANALYSIS + " NUMBER," +
                ProjectEntry.KODE_ANALYSIS + " TEXT," +
                ProjectEntry.NAME_ANALYSIS + " TEXT," +
                ProjectEntry.NST + " NUMBER, " +
                ProjectEntry.NBX + " NUMBER, " +
                ProjectEntry.NBY + " NUMBER, " +
                ProjectEntry.STH + " NUMBER, " +
                ProjectEntry.BWX + " NUMBER, " +
                ProjectEntry.BWY + " NUMBER, " +
                ProjectEntry.SENDI + " TEXT," +
                ProjectEntry.FY + " NUMBER, " +
                ProjectEntry.FU + " NUMBER, " +
                ProjectEntry.E + " NUMBER" +
                ");";

        //Creating a table joint load
        final String SQL_CREATE_JOINT_LOAD = "CREATE TABLE " +
                ProjectEntry.TABLE_JOINT_LOAD + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER," +
                ProjectEntry.ID_ANALYSIS + " NUMBER, " +
                ProjectEntry.NODAL + " NUMBER, " +
                ProjectEntry.FGX + " NUMBER, " +
                ProjectEntry.FGY + " NUMBER, " +
                ProjectEntry.FGZ + " NUMBER, " +
                ProjectEntry.MGX + " NUMBER, " +
                ProjectEntry.MGY + " NUMBER, " +
                ProjectEntry.MGZ + " NUMBER" +
                ");";

        //Creating a table frame load & assign
        final String SQL_CREATE_FRAME_LOAD_AND_ASSIGN = "CREATE TABLE " +
                ProjectEntry.TABLE_FRAME_LOAD_AND_ASSIGN + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER," +
                ProjectEntry.ID_ANALYSIS + " NUMBER, " +
                ProjectEntry.FRAME + " NUMBER, " +
                ProjectEntry.LGX + " NUMBER, " +
                ProjectEntry.LGY + " NUMBER, " +
                ProjectEntry.LGZ + " NUMBER, " +
                ProjectEntry.SECTION + " NUMBER" +
                ");";

        //Creating a table area load & assign
        final String SQL_CREATE_AREA_LOAD_AND_ASSIGN = "CREATE TABLE " +
                ProjectEntry.TABLE_AREA_LOAD_AND_ASSIGN + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER," +
                ProjectEntry.ID_ANALYSIS + " NUMBER, " +
                ProjectEntry.AREA + " NUMBER, " +
                ProjectEntry.UAL + " NUMBER, " +
                ProjectEntry.SECTION + " NUMBER" +
                ");";

        //Creating a table frame section
        final String SQL_CREATE_FRAME_SECTION = "CREATE TABLE " +
                ProjectEntry.TABLE_FRAME_SECTION + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER, " +
                ProjectEntry.ID_ANALYSIS + " NUMBER, " +
                ProjectEntry.NOMOR + " NUMBER, " +
                ProjectEntry.KODE_FRAME_SECTION + " NUMBER, " +
                ProjectEntry.NAME_FRAME_SECTION + " TEXT, " +
                ProjectEntry.ROTATE_ALPHA + " TEXT, " +
                ProjectEntry.TYPE_STEEL + " TEXT, " +
                ProjectEntry.NAME_STEEL + " TEXT" +
                ");";

        //Creating a table assign section
        final String SQL_CREATE_ASSIGN_SECTION = "CREATE TABLE " +
                ProjectEntry.TABLE_ASSIGN_SECTION + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER, " +
                ProjectEntry.ID_ANALYSIS + " NUMBER, " +
                ProjectEntry.NO_FRAME + " NUMBER, " +
                ProjectEntry.NOMOR + " NUMBER" +
                ");";

        //Creating a table earth quake
        final String SQL_CREATE_EARTH_QUAKE = "CREATE TABLE " +
                ProjectEntry.TABLE_EARTH_QUAKE + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER, " +
                ProjectEntry.ID_ANALYSIS + " NUMBER, " +
                ProjectEntry.SS + " NUMBER, " +
                ProjectEntry.S1 + " NUMBER, " +
                ProjectEntry.IE + " NUMBER, " +
                ProjectEntry.R + " NUMBER, " +
                ProjectEntry.SITUS + " TEXT, " +
                ProjectEntry.AXIS + " NUMBER, " +
                ProjectEntry.IS_X_POSITIF + " TEXT, " +
                ProjectEntry.IS_Y_POSITIF + " TEXT" +
                ");";

        //Creating a table design
        final String SQL_CREATE_DESIGN = "CREATE TABLE " +
                ProjectEntry.TABLE_DESIGN + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER," +
                ProjectEntry.ID_ANALYSIS + " NUMBER," +
                ProjectEntry.ID_DESIGN + " NUMBER," +
                ProjectEntry.KODE_DESIGN + " TEXT," +
                ProjectEntry.NAME_DESIGN + " TEXT" +
                ");";

        //Creating a table design section (column)
        final String SQL_CREATE_DESIGN_SECTION_COLUMN = "CREATE TABLE " +
                ProjectEntry.TABLE_DESIGN_SECTION_COLUMN + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER, " +
                ProjectEntry.ID_ANALYSIS + " NUMBER, " +
                ProjectEntry.ID_DESIGN + " NUMBER," +
                ProjectEntry.NOMOR + " NUMBER, " +
                ProjectEntry.TYPE_STEEL + " TEXT, " +
                ProjectEntry.NAME_STEEL + " TEXT" +
                ");";

        //Creating a table design section (beam x)
        final String SQL_CREATE_DESIGN_SECTION_BEAMX = "CREATE TABLE " +
                ProjectEntry.TABLE_DESIGN_SECTION_BEAMX + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER, " +
                ProjectEntry.ID_ANALYSIS + " NUMBER, " +
                ProjectEntry.ID_DESIGN + " NUMBER," +
                ProjectEntry.NOMOR + " NUMBER, " +
                ProjectEntry.TYPE_STEEL + " TEXT, " +
                ProjectEntry.NAME_STEEL + " TEXT" +
                ");";

        //Creating a table design section (beam y)
        final String SQL_CREATE_DESIGN_SECTION_BEAMY = "CREATE TABLE " +
                ProjectEntry.TABLE_DESIGN_SECTION_BEAMY + " (" +
                ProjectEntry.ID_PROJECT + " NUMBER, " +
                ProjectEntry.ID_ANALYSIS + " NUMBER, " +
                ProjectEntry.ID_DESIGN + " NUMBER," +
                ProjectEntry.NOMOR + " NUMBER, " +
                ProjectEntry.TYPE_STEEL + " TEXT, " +
                ProjectEntry.NAME_STEEL + " TEXT" +
                ");";
        
        //Create Table
        db.execSQL(SQL_CREATE_PROJECT);
        db.execSQL(SQL_CREATE_ANALYSIS);
        db.execSQL(SQL_CREATE_JOINT_LOAD);
        db.execSQL(SQL_CREATE_FRAME_LOAD_AND_ASSIGN);
        db.execSQL(SQL_CREATE_AREA_LOAD_AND_ASSIGN);
        db.execSQL(SQL_CREATE_FRAME_SECTION);
        db.execSQL(SQL_CREATE_ASSIGN_SECTION);
        db.execSQL(SQL_CREATE_EARTH_QUAKE);
        db.execSQL(SQL_CREATE_DESIGN);
        db.execSQL(SQL_CREATE_DESIGN_SECTION_COLUMN);
        db.execSQL(SQL_CREATE_DESIGN_SECTION_BEAMX);
        db.execSQL(SQL_CREATE_DESIGN_SECTION_BEAMY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_PROJECT);
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_ANALYSIS);
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_JOINT_LOAD);
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_FRAME_LOAD_AND_ASSIGN);
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_AREA_LOAD_AND_ASSIGN);
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_FRAME_SECTION);
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_ASSIGN_SECTION);
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_DESIGN);
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_DESIGN_SECTION_COLUMN);
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_DESIGN_SECTION_BEAMX);
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_DESIGN_SECTION_BEAMY);
        onCreate(db);
    }

    public void addProject(SQLiteDatabase db) {
        //Create a new map of values, where column name are the keys
        ContentValues cvProject = new ContentValues();

        //Project
        cvProject.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
        cvProject.put(ProjectEntry.KODE_PROJECT, D_MainActivity.projectKode);
        cvProject.put(ProjectEntry.NAME_PROJECT, D_MainActivity.projectName);
        cvProject.put(ProjectEntry.ENGINEER, D_MainActivity.projectEngineer);
        db.insert(ProjectEntry.TABLE_PROJECT, null, cvProject);
        cvProject.clear();
    }

    public void addAnalysis(SQLiteDatabase db) {
        ContentValues cvAnalysis = new ContentValues();

        //Analysis
        cvAnalysis.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
        cvAnalysis.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
        cvAnalysis.put(ProjectEntry.KODE_ANALYSIS, D_MainActivity.analysisKode);
        cvAnalysis.put(ProjectEntry.NAME_ANALYSIS, D_MainActivity.analysisName);
        cvAnalysis.put(ProjectEntry.NST, D_MainActivity.Nst);
        cvAnalysis.put(ProjectEntry.NBX, D_MainActivity.Nbx);
        cvAnalysis.put(ProjectEntry.NBY, D_MainActivity.Nby);
        cvAnalysis.put(ProjectEntry.STH, D_MainActivity.Sth);
        cvAnalysis.put(ProjectEntry.BWX, D_MainActivity.Bwx);
        cvAnalysis.put(ProjectEntry.BWY, D_MainActivity.Bwy);
        cvAnalysis.put(ProjectEntry.SENDI, D_MainActivity.isRestraintPin);
        cvAnalysis.put(ProjectEntry.FY, D_MainActivity.bajaFy);
        cvAnalysis.put(ProjectEntry.FU, D_MainActivity.bajaFu);
        cvAnalysis.put(ProjectEntry.E, D_MainActivity.bajaE);
        db.insert(ProjectEntry.TABLE_ANALYSIS, null, cvAnalysis);
        cvAnalysis.clear();
    }

    public void addAnalysisData(SQLiteDatabase db) {
        addJointLoad(db);
        addFrameLoad(db);
        addAreaLoad(db);
        addEarthQuake(db);
    }

    public void addDesign(SQLiteDatabase db) {
        ContentValues cvDesign = new ContentValues();

        cvDesign.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
        cvDesign.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
        cvDesign.put(ProjectEntry.ID_DESIGN, D_MainActivity.designId);
        cvDesign.put(ProjectEntry.KODE_DESIGN, D_MainActivity.designKode);
        cvDesign.put(ProjectEntry.NAME_DESIGN, D_MainActivity.designName);
        db.insert(ProjectEntry.TABLE_DESIGN, null, cvDesign);
        cvDesign.clear();
    }

    public void addDesignData(SQLiteDatabase db) {
        ContentValues cvColumnSect = new ContentValues();
        ContentValues cvBeamXSect = new ContentValues();
        ContentValues cvBeamYSect = new ContentValues();

        for (int i = 0; i < D_MainActivity.listDesignColSect2.size(); i++) {
            cvColumnSect.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
            cvColumnSect.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
            cvColumnSect.put(ProjectEntry.ID_DESIGN, D_MainActivity.designId);
            cvColumnSect.put(ProjectEntry.NOMOR, i);
            cvColumnSect.put(ProjectEntry.TYPE_STEEL,
                    D_MainActivity.listDesignColSect2.get(i).getSteelType());
            cvColumnSect.put(ProjectEntry.NAME_STEEL,
                    D_MainActivity.listDesignColSect2.get(i).getSteelName());
            db.insert(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, null, cvColumnSect);
            cvColumnSect.clear();
        }

        for (int i = 0; i < D_MainActivity.listDesignBeamXSect2.size(); i++) {
            cvBeamXSect.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
            cvBeamXSect.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
            cvBeamXSect.put(ProjectEntry.ID_DESIGN, D_MainActivity.designId);
            cvBeamXSect.put(ProjectEntry.NOMOR, i);
            cvBeamXSect.put(ProjectEntry.TYPE_STEEL,
                    D_MainActivity.listDesignBeamXSect2.get(i).getSteelType());
            cvBeamXSect.put(ProjectEntry.NAME_STEEL,
                    D_MainActivity.listDesignBeamXSect2.get(i).getSteelName());
            db.insert(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, null, cvBeamXSect);
            cvBeamXSect.clear();
        }

        for (int i = 0; i < D_MainActivity.listDesignBeamYSect2.size(); i++) {
            cvBeamYSect.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
            cvBeamYSect.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
            cvBeamYSect.put(ProjectEntry.ID_DESIGN, D_MainActivity.designId);
            cvBeamYSect.put(ProjectEntry.NOMOR, i);
            cvBeamYSect.put(ProjectEntry.TYPE_STEEL,
                    D_MainActivity.listDesignBeamYSect2.get(i).getSteelType());
            cvBeamYSect.put(ProjectEntry.NAME_STEEL,
                    D_MainActivity.listDesignBeamYSect2.get(i).getSteelName());
            db.insert(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, null, cvBeamYSect);
            cvBeamYSect.clear();
        }
    }

    public void addJointLoad(SQLiteDatabase db) {
        ContentValues cvJointLoad = new ContentValues();

        //Joint Load
        for (int i = 0; i < D_MainActivity.koorNode.length; i++) {
            cvJointLoad.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
            cvJointLoad.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
            cvJointLoad.put(ProjectEntry.NODAL, i);
            cvJointLoad.put(ProjectEntry.FGX, D_MainActivity.jointLoad[i][0]);
            cvJointLoad.put(ProjectEntry.FGY, D_MainActivity.jointLoad[i][1]);
            cvJointLoad.put(ProjectEntry.FGZ, D_MainActivity.jointLoad[i][2]);
            cvJointLoad.put(ProjectEntry.MGX, D_MainActivity.jointLoad[i][3]);
            cvJointLoad.put(ProjectEntry.MGY, D_MainActivity.jointLoad[i][4]);
            cvJointLoad.put(ProjectEntry.MGZ, D_MainActivity.jointLoad[i][5]);
            db.insert(ProjectEntry.TABLE_JOINT_LOAD, null, cvJointLoad);
            cvJointLoad.clear();
        }
    }

    public void addFrameLoad(SQLiteDatabase db) {
        ContentValues cvFrameLoad = new ContentValues();

        //Frame Load
        for (int i = 0; i < D_MainActivity.pointerElemenStruktur.length; i++) {
            cvFrameLoad.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
            cvFrameLoad.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
            cvFrameLoad.put(ProjectEntry.FRAME, i);
            cvFrameLoad.put(ProjectEntry.LGX, D_MainActivity.frameLoad[i][0]);
            cvFrameLoad.put(ProjectEntry.LGY, D_MainActivity.frameLoad[i][1]);
            cvFrameLoad.put(ProjectEntry.LGZ, D_MainActivity.frameLoad[i][2]);
            cvFrameLoad.put(ProjectEntry.SECTION, D_MainActivity.pointerFrameSection[i]);
            db.insert(ProjectEntry.TABLE_FRAME_LOAD_AND_ASSIGN, null, cvFrameLoad);
            cvFrameLoad.clear();
        }
    }

    public void addAreaLoad(SQLiteDatabase db) {
        ContentValues cvAreaLoad = new ContentValues();

        //Area Load
        int counterArea = 0;
        for (boolean i : D_MainActivity.isAreaLoadExist) {
            cvAreaLoad.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
            cvAreaLoad.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
            cvAreaLoad.put(ProjectEntry.AREA, counterArea);
            cvAreaLoad.put(ProjectEntry.UAL, D_MainActivity.areaLoad[counterArea]);
            cvAreaLoad.put(ProjectEntry.SECTION, D_MainActivity.pointerAreaSection[counterArea]);
            db.insert(ProjectEntry.TABLE_AREA_LOAD_AND_ASSIGN, null, cvAreaLoad);
            cvAreaLoad.clear();
            counterArea++;
        }
    }

    public void addFrameSection(SQLiteDatabase db) {
        ContentValues cvFrameSection = new ContentValues();

        //Frame Section
        for (int i = 0; i < D_MainActivity.listFrameSection.size(); i++) {
            cvFrameSection.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
            cvFrameSection.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
            cvFrameSection.put(ProjectEntry.NOMOR, i);
            cvFrameSection.put(ProjectEntry.KODE_FRAME_SECTION,
                    D_MainActivity.listFrameSection.get(i).getSectionCode());
            cvFrameSection.put(ProjectEntry.NAME_FRAME_SECTION,
                    D_MainActivity.listFrameSection.get(i).getSectionName());
            cvFrameSection.put(ProjectEntry.ROTATE_ALPHA,
                    D_MainActivity.listFrameSection.get(i).isAlphaRotated());
            cvFrameSection.put(ProjectEntry.TYPE_STEEL,
                    D_MainActivity.listFrameSection.get(i).getSteelType());
            cvFrameSection.put(ProjectEntry.NAME_STEEL,
                    D_MainActivity.listFrameSection.get(i).getSteelName());
            db.insert(ProjectEntry.TABLE_FRAME_SECTION, null, cvFrameSection);
            cvFrameSection.clear();
        }
    }

    public void addAssignSection(SQLiteDatabase db) {
        ContentValues cvAssignSection = new ContentValues();

        //Assign Section
        for (int i = 0; i < D_MainActivity.pointerFrameSection.length; i++) {
            cvAssignSection.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
            cvAssignSection.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
            cvAssignSection.put(ProjectEntry.NO_FRAME, i);
            cvAssignSection.put(ProjectEntry.NOMOR, D_MainActivity.pointerFrameSection[i]);
            db.insert(ProjectEntry.TABLE_ASSIGN_SECTION, null, cvAssignSection);
            cvAssignSection.clear();
        }
    }

    public void addEarthQuake(SQLiteDatabase db) {
        ContentValues cvEarthQuake = new ContentValues();

        //Earth Quake
        int axis;
        if(D_MainActivity.isBebanGempaX){
            axis = 1;
        }else if(D_MainActivity.isBebanGempaY){
            axis = 2;
        }else{
            axis = 0;
        }
        cvEarthQuake.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
        cvEarthQuake.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
        cvEarthQuake.put(ProjectEntry.SS, D_MainActivity.gempaSs);
        cvEarthQuake.put(ProjectEntry.S1, D_MainActivity.gempaS1);
        cvEarthQuake.put(ProjectEntry.IE, D_MainActivity.gempaIe);
        cvEarthQuake.put(ProjectEntry.R, D_MainActivity.gempaR);
        cvEarthQuake.put(ProjectEntry.SITUS, D_MainActivity.gempaSitus);
        cvEarthQuake.put(ProjectEntry.AXIS, axis);
        cvEarthQuake.put(ProjectEntry.IS_X_POSITIF, D_MainActivity.isGempaXPositif);
        cvEarthQuake.put(ProjectEntry.IS_Y_POSITIF, D_MainActivity.isGempaYPositif);
        db.insert(ProjectEntry.TABLE_EARTH_QUAKE, null, cvEarthQuake);
        cvEarthQuake.clear();
    }

    public void updateProject(SQLiteDatabase db) {
        //Create a new map of values, where column name are the keys
        ContentValues cvProject = new ContentValues();

        String selectionProject = ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId;
        cvProject.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
        cvProject.put(ProjectEntry.KODE_PROJECT, D_MainActivity.projectKode);
        cvProject.put(ProjectEntry.NAME_PROJECT, D_MainActivity.projectName);
        cvProject.put(ProjectEntry.ENGINEER, D_MainActivity.projectEngineer);
        db.update(ProjectEntry.TABLE_PROJECT, cvProject, selectionProject, null);
        cvProject.clear();
    }

    public void updateAnalysis(SQLiteDatabase db) {
        ContentValues cvAnalysis = new ContentValues();

        String selectionAnalysis = ProjectEntry.ID_ANALYSIS + " = " + D_MainActivity.analysisId +
                " AND " + ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId;
        cvAnalysis.put(ProjectEntry.KODE_ANALYSIS, D_MainActivity.analysisKode);
        cvAnalysis.put(ProjectEntry.NAME_ANALYSIS, D_MainActivity.analysisName);
        cvAnalysis.put(ProjectEntry.NST, D_MainActivity.Nst);
        cvAnalysis.put(ProjectEntry.NBX, D_MainActivity.Nbx);
        cvAnalysis.put(ProjectEntry.NBY, D_MainActivity.Nby);
        cvAnalysis.put(ProjectEntry.STH, D_MainActivity.Sth);
        cvAnalysis.put(ProjectEntry.BWX, D_MainActivity.Bwx);
        cvAnalysis.put(ProjectEntry.BWY, D_MainActivity.Bwy);
        cvAnalysis.put(ProjectEntry.SENDI, D_MainActivity.isRestraintPin);
        cvAnalysis.put(ProjectEntry.FY, D_MainActivity.bajaFy);
        cvAnalysis.put(ProjectEntry.FU, D_MainActivity.bajaFu);
        cvAnalysis.put(ProjectEntry.E, D_MainActivity.bajaE);
        db.update(ProjectEntry.TABLE_ANALYSIS, cvAnalysis, selectionAnalysis, null);
        cvAnalysis.clear();
    }

    public void updateAnalysisData(SQLiteDatabase db) {
        updateJointLoad(db);
        updateFrameLoad(db);
        updateAreaLoad(db);
        updateFrameSection(db);
        updateAssignSection(db);
        updateEarthQuake(db);
    }

    public void updateJointLoad(SQLiteDatabase db){
        ContentValues cvJointLoad = new ContentValues();

        //Joint Load ==============================================================================
        int counterNodal = 0;
        String selectionJoint =
                ProjectEntry.ID_PROJECT + " = ? AND " +
                        ProjectEntry.ID_ANALYSIS + " = ? AND " +
                        ProjectEntry.NODAL + " = ?";

        String[] selectionArgs = new String[3];
        for(boolean[] i : D_MainActivity.isJointLoadExist){
            cvJointLoad.put(ProjectEntry.FGX, D_MainActivity.jointLoad[counterNodal][0]);
            cvJointLoad.put(ProjectEntry.FGY, D_MainActivity.jointLoad[counterNodal][1]);
            cvJointLoad.put(ProjectEntry.FGZ, D_MainActivity.jointLoad[counterNodal][2]);
            cvJointLoad.put(ProjectEntry.MGX, D_MainActivity.jointLoad[counterNodal][3]);
            cvJointLoad.put(ProjectEntry.MGY, D_MainActivity.jointLoad[counterNodal][4]);
            cvJointLoad.put(ProjectEntry.MGZ, D_MainActivity.jointLoad[counterNodal][5]);

            selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
            selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
            selectionArgs[2] = Integer.toString(counterNodal);

            db.update(ProjectEntry.TABLE_JOINT_LOAD, cvJointLoad, selectionJoint, selectionArgs);
            cvJointLoad.clear();
            counterNodal++;
        }
    }

    public void updateFrameLoad(SQLiteDatabase db){
        ContentValues cvFrameLoad = new ContentValues();

        //Frame Load ==============================================================================
        int counterFrame = 0;
        String selectionFrameLoad =
                ProjectEntry.ID_PROJECT + " = ? AND " +
                        ProjectEntry.ID_ANALYSIS + " = ? AND " +
                        ProjectEntry.FRAME + " = ?";

        String[] selectionArgs = new String[3];
        for(boolean[] i : D_MainActivity.isFrameLoadExist){
            cvFrameLoad.put(ProjectEntry.LGX, D_MainActivity.frameLoad[counterFrame][0]);
            cvFrameLoad.put(ProjectEntry.LGY, D_MainActivity.frameLoad[counterFrame][1]);
            cvFrameLoad.put(ProjectEntry.LGZ, D_MainActivity.frameLoad[counterFrame][2]);
            cvFrameLoad.put(ProjectEntry.SECTION, D_MainActivity.pointerFrameSection[counterFrame]);

            selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
            selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
            selectionArgs[2] = Integer.toString(counterFrame);

            db.update(ProjectEntry.TABLE_FRAME_LOAD_AND_ASSIGN, cvFrameLoad, selectionFrameLoad, selectionArgs);
            cvFrameLoad.clear();
            counterFrame++;
        }
    }

    public void updateAreaLoad(SQLiteDatabase db){
        ContentValues cvAreaLoad = new ContentValues();

        //Area Load ===============================================================================
        int counterArea = 0;
        String selectionAreaLoad =
                ProjectEntry.ID_PROJECT + " = ? AND " +
                        ProjectEntry.ID_ANALYSIS + " = ? AND " +
                        ProjectEntry.AREA + " = ?";

        String[] selectionArgs = new String[3];
        for(boolean i : D_MainActivity.isAreaLoadExist){
            cvAreaLoad.put(ProjectEntry.UAL, D_MainActivity.areaLoad[counterArea]);
            cvAreaLoad.put(ProjectEntry.SECTION, D_MainActivity.pointerAreaSection[counterArea]);

            selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
            selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
            selectionArgs[2] = Integer.toString(counterArea);

            db.update(ProjectEntry.TABLE_AREA_LOAD_AND_ASSIGN, cvAreaLoad, selectionAreaLoad, selectionArgs);
            cvAreaLoad.clear();
            counterArea++;
        }
    }

    public void updateFrameSection(SQLiteDatabase db){
        ContentValues cvFrameSection = new ContentValues();

        String[] selectionArgs = new String[3];
        //Frame Section ===================================================================
        Cursor cursorFrameSection = readFrameSection(db);
        int sizeFrameSection = 0;

        while(cursorFrameSection.moveToNext()){
            int ID_PROJECT = cursorFrameSection.
                    getInt(cursorFrameSection.getColumnIndex(ProjectEntry.ID_PROJECT));
            int ID_ANALYSIS = cursorFrameSection.
                    getInt(cursorFrameSection.getColumnIndex(ProjectEntry.ID_ANALYSIS));

            if(ID_PROJECT == D_MainActivity.projectId &&
                    ID_ANALYSIS == D_MainActivity.analysisId){
                sizeFrameSection++;
            }
        }

        if(D_MainActivity.listFrameSection.size() == sizeFrameSection){
            //Update
            String selectionFrameSection =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<D_MainActivity.listFrameSection.size(); i++){
                cvFrameSection.put(ProjectEntry.KODE_FRAME_SECTION,
                        D_MainActivity.listFrameSection.get(i).getSectionCode());
                cvFrameSection.put(ProjectEntry.NAME_FRAME_SECTION,
                        D_MainActivity.listFrameSection.get(i).getSectionName());
                cvFrameSection.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listFrameSection.get(i).getSteelType());
                cvFrameSection.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listFrameSection.get(i).getSteelName());
                cvFrameSection.put(ProjectEntry.ROTATE_ALPHA,
                        D_MainActivity.listFrameSection.get(i).isAlphaRotated());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_FRAME_SECTION,
                        cvFrameSection, selectionFrameSection, selectionArgs);
                cvFrameSection.clear();
            }

        }else if(D_MainActivity.listFrameSection.size() > sizeFrameSection){
            //Update
            String selectionFrameSection =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<sizeFrameSection; i++){
                cvFrameSection.put(ProjectEntry.KODE_FRAME_SECTION,
                        D_MainActivity.listFrameSection.get(i).getSectionCode());
                cvFrameSection.put(ProjectEntry.NAME_FRAME_SECTION,
                        D_MainActivity.listFrameSection.get(i).getSectionName());
                cvFrameSection.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listFrameSection.get(i).getSteelType());
                cvFrameSection.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listFrameSection.get(i).getSteelName());
                cvFrameSection.put(ProjectEntry.ROTATE_ALPHA,
                        D_MainActivity.listFrameSection.get(i).isAlphaRotated());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_FRAME_SECTION, cvFrameSection, selectionFrameSection, selectionArgs);
                cvFrameSection.clear();
            }

            //Add
            for(int i=sizeFrameSection; i<D_MainActivity.listFrameSection.size(); i++){
                cvFrameSection.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
                cvFrameSection.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
                cvFrameSection.put(ProjectEntry.NOMOR, i);
                cvFrameSection.put(ProjectEntry.KODE_FRAME_SECTION,
                        D_MainActivity.listFrameSection.get(i).getSectionCode());
                cvFrameSection.put(ProjectEntry.NAME_FRAME_SECTION,
                        D_MainActivity.listFrameSection.get(i).getSectionName());
                cvFrameSection.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listFrameSection.get(i).getSteelType());
                cvFrameSection.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listFrameSection.get(i).getSteelName());
                cvFrameSection.put(ProjectEntry.ROTATE_ALPHA,
                        D_MainActivity.listFrameSection.get(i).isAlphaRotated());

                db.insert(ProjectEntry.TABLE_FRAME_SECTION, null, cvFrameSection);
                cvFrameSection.clear();
            }

        }else{
            //Update
            String selectionFrameSection =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<D_MainActivity.listFrameSection.size(); i++){
                cvFrameSection.put(ProjectEntry.KODE_FRAME_SECTION,
                        D_MainActivity.listFrameSection.get(i).getSectionCode());
                cvFrameSection.put(ProjectEntry.NAME_FRAME_SECTION,
                        D_MainActivity.listFrameSection.get(i).getSectionName());
                cvFrameSection.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listFrameSection.get(i).getSteelType());
                cvFrameSection.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listFrameSection.get(i).getSteelName());
                cvFrameSection.put(ProjectEntry.ROTATE_ALPHA,
                        D_MainActivity.listFrameSection.get(i).isAlphaRotated());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_FRAME_SECTION, cvFrameSection, selectionFrameSection, selectionArgs);
                cvFrameSection.clear();
            }

            //Delete
            for(int i=D_MainActivity.listFrameSection.size(); i<sizeFrameSection; i++){
                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(i);
                db.delete(ProjectEntry.TABLE_FRAME_SECTION, selectionFrameSection, selectionArgs);
            }
        }
    }
    public void updateFrameSectionSatuan(SQLiteDatabase db){
        ContentValues cvFrameSection = new ContentValues();

        //Update
        String selectionFrameSection =
                ProjectEntry.ID_PROJECT + " = ? AND " +
                        ProjectEntry.ID_ANALYSIS + " = ? AND " +
                        ProjectEntry.NOMOR + " = ?";

        String[] selectionArgs = new String[3];
        for(int i=0; i<D_MainActivity.listFrameSection.size(); i++){
            cvFrameSection.put(ProjectEntry.KODE_FRAME_SECTION,
                    D_MainActivity.listFrameSection.get(i).getSectionCode());
            cvFrameSection.put(ProjectEntry.NAME_FRAME_SECTION,
                    D_MainActivity.listFrameSection.get(i).getSectionName());
            cvFrameSection.put(ProjectEntry.TYPE_STEEL,
                    D_MainActivity.listFrameSection.get(i).getSteelType());
            cvFrameSection.put(ProjectEntry.NAME_STEEL,
                    D_MainActivity.listFrameSection.get(i).getSteelName());
            cvFrameSection.put(ProjectEntry.ROTATE_ALPHA,
                    D_MainActivity.listFrameSection.get(i).isAlphaRotated());

            selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
            selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
            selectionArgs[2] = Integer.toString(i);

            db.update(ProjectEntry.TABLE_FRAME_SECTION,
                    cvFrameSection, selectionFrameSection, selectionArgs);
            cvFrameSection.clear();
        }
    }

    public void updateAssignSection(SQLiteDatabase db){
        ContentValues cvAssignSection = new ContentValues();
        //Assign Section
        String selectionAssign =
                ProjectEntry.ID_PROJECT + " = ? AND " +
                        ProjectEntry.ID_ANALYSIS + " = ? AND " +
                        ProjectEntry.NO_FRAME + " = ?";

        String[] selectionArgs = new String[3];
        for(int i=0; i<D_MainActivity.pointerFrameSection.length; i++){
            cvAssignSection.put(ProjectEntry.NOMOR, D_MainActivity.pointerFrameSection[i]);

            selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
            selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
            selectionArgs[2] = Integer.toString(i);

            db.update(ProjectEntry.TABLE_ASSIGN_SECTION, cvAssignSection, selectionAssign, selectionArgs);
            cvAssignSection.clear();
        }
    }

    public void updateEarthQuake(SQLiteDatabase db){
        ContentValues cvEarthQuake = new ContentValues();

        //Earth Quake ============================================================================
        int axis;
        if(D_MainActivity.isBebanGempaX){
            axis = 1;
        }else if(D_MainActivity.isBebanGempaY){
            axis = 2;
        }else{
            axis = 0;
        }
        String selectionEQ = ProjectEntry.ID_ANALYSIS + " = " + D_MainActivity.analysisId +
                " AND " + ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId;
        cvEarthQuake.put(ProjectEntry.SS, D_MainActivity.gempaSs);
        cvEarthQuake.put(ProjectEntry.S1, D_MainActivity.gempaS1);
        cvEarthQuake.put(ProjectEntry.IE, D_MainActivity.gempaIe);
        cvEarthQuake.put(ProjectEntry.R, D_MainActivity.gempaR);
        cvEarthQuake.put(ProjectEntry.SITUS, D_MainActivity.gempaSitus);
        cvEarthQuake.put(ProjectEntry.AXIS, axis);
        cvEarthQuake.put(ProjectEntry.IS_X_POSITIF, D_MainActivity.isGempaXPositif);
        cvEarthQuake.put(ProjectEntry.IS_Y_POSITIF, D_MainActivity.isGempaYPositif);
        db.update(ProjectEntry.TABLE_EARTH_QUAKE, cvEarthQuake, selectionEQ, null);
        cvEarthQuake.clear();
    }

    public void updateDesign(SQLiteDatabase db) {
        ContentValues cvDesign = new ContentValues();

        String selectionDesign = ProjectEntry.ID_DESIGN + " = " + D_MainActivity.designId +
                " AND " + ProjectEntry.ID_ANALYSIS + " = " + D_MainActivity.analysisId +
                " AND " + ProjectEntry.ID_PROJECT + " = " + D_MainActivity.projectId;
        cvDesign.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
        cvDesign.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
        cvDesign.put(ProjectEntry.ID_DESIGN, D_MainActivity.designId);
        cvDesign.put(ProjectEntry.KODE_DESIGN, D_MainActivity.designKode);
        cvDesign.put(ProjectEntry.NAME_DESIGN, D_MainActivity.designName);
        db.update(ProjectEntry.TABLE_DESIGN, cvDesign, selectionDesign, null);
        cvDesign.clear();
    }
    
    public void updateDesignData(SQLiteDatabase db) {
        ContentValues cvColumnSect = new ContentValues();
        ContentValues cvBeamXSect = new ContentValues();
        ContentValues cvBeamYSect = new ContentValues();

        String[] selectionArgs = new String[4];

        //Column Section ===================================================================
        Cursor cursorColumn = readDesignSectionColumn(db);
        int sizeColumnSection = 0;

        while(cursorColumn.moveToNext()){
            int ID_PROJECT = cursorColumn.
                    getInt(cursorColumn.getColumnIndex(ProjectEntry.ID_PROJECT));
            int ID_ANALYSIS = cursorColumn.
                    getInt(cursorColumn.getColumnIndex(ProjectEntry.ID_ANALYSIS));
            int ID_DESIGN = cursorColumn.
                    getInt(cursorColumn.getColumnIndex(ProjectEntry.ID_DESIGN));

            if(ID_PROJECT == D_MainActivity.projectId &&
                    ID_ANALYSIS == D_MainActivity.analysisId &&
                    ID_DESIGN == D_MainActivity.designId){
                sizeColumnSection++;
            }
        }

        if(D_MainActivity.listDesignColSect2.size() == sizeColumnSection){
            //Update
            String selectionDesignCol =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.ID_DESIGN + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<D_MainActivity.listDesignColSect2.size(); i++){
                cvColumnSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignColSect2.get(i).getSteelType());
                cvColumnSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignColSect2.get(i).getSteelName());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN,
                        cvColumnSect, selectionDesignCol, selectionArgs);
                cvColumnSect.clear();
            }

        }else if(D_MainActivity.listDesignColSect2.size() > sizeColumnSection){
            //Update
            String selectionDesignCol =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.ID_DESIGN + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<sizeColumnSection; i++){
                cvColumnSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignColSect2.get(i).getSteelType());
                cvColumnSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignColSect2.get(i).getSteelName());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, cvColumnSect, selectionDesignCol, selectionArgs);
                cvColumnSect.clear();
            }

            //Add
            for(int i=sizeColumnSection; i<D_MainActivity.listDesignColSect2.size(); i++){
                cvColumnSect.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
                cvColumnSect.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
                cvColumnSect.put(ProjectEntry.ID_DESIGN, D_MainActivity.designId);
                cvColumnSect.put(ProjectEntry.NOMOR, i);
                cvColumnSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignColSect2.get(i).getSteelType());
                cvColumnSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignColSect2.get(i).getSteelName());

                db.insert(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, null, cvColumnSect);
                cvColumnSect.clear();
            }

        }else{
            //Update
            String selectionDesignCol =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.ID_DESIGN + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<D_MainActivity.listDesignColSect2.size(); i++){
                cvColumnSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignColSect2.get(i).getSteelType());
                cvColumnSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignColSect2.get(i).getSteelName());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, cvColumnSect, selectionDesignCol, selectionArgs);
                cvColumnSect.clear();
            }

            //Delete
            for(int i=D_MainActivity.listDesignColSect2.size(); i<sizeColumnSection; i++){
                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);
                db.delete(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, selectionDesignCol, selectionArgs);
            }
        }

        //Beam X Section ===================================================================
        Cursor cursorBeamX = readDesignSectionBeamX(db);
        int sizeBeamXSection = 0;

        while(cursorBeamX.moveToNext()){
            int ID_PROJECT = cursorBeamX.
                    getInt(cursorBeamX.getColumnIndex(ProjectEntry.ID_PROJECT));
            int ID_ANALYSIS = cursorBeamX.
                    getInt(cursorBeamX.getColumnIndex(ProjectEntry.ID_ANALYSIS));
            int ID_DESIGN = cursorBeamX.
                    getInt(cursorBeamX.getColumnIndex(ProjectEntry.ID_DESIGN));

            if(ID_PROJECT == D_MainActivity.projectId &&
                    ID_ANALYSIS == D_MainActivity.analysisId &&
                    ID_DESIGN == D_MainActivity.designId){
                sizeBeamXSection++;
            }
        }

        if(D_MainActivity.listDesignBeamXSect2.size() == sizeBeamXSection){
            //Update
            String selectionDesignBeamX =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.ID_DESIGN + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<D_MainActivity.listDesignBeamXSect2.size(); i++){
                cvBeamXSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignBeamXSect2.get(i).getSteelType());
                cvBeamXSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignBeamXSect2.get(i).getSteelName());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX,
                        cvBeamXSect, selectionDesignBeamX, selectionArgs);
                cvBeamXSect.clear();
            }

        }else if(D_MainActivity.listDesignBeamXSect2.size() > sizeBeamXSection){
            //Update
            String selectionDesignBeamX =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.ID_DESIGN + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<sizeBeamXSection; i++){
                cvBeamXSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignBeamXSect2.get(i).getSteelType());
                cvBeamXSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignBeamXSect2.get(i).getSteelName());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, cvBeamXSect, selectionDesignBeamX, selectionArgs);
                cvBeamXSect.clear();
            }

            //Add
            for(int i=sizeBeamXSection; i<D_MainActivity.listDesignBeamXSect2.size(); i++){
                cvBeamXSect.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
                cvBeamXSect.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
                cvBeamXSect.put(ProjectEntry.ID_DESIGN, D_MainActivity.designId);
                cvBeamXSect.put(ProjectEntry.NOMOR, i);
                cvBeamXSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignBeamXSect2.get(i).getSteelType());
                cvBeamXSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignBeamXSect2.get(i).getSteelName());

                db.insert(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, null, cvBeamXSect);
                cvBeamXSect.clear();
            }

        }else{
            //Update
            String selectionDesignBeamX =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.ID_DESIGN + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<D_MainActivity.listDesignBeamXSect2.size(); i++){
                cvBeamXSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignBeamXSect2.get(i).getSteelType());
                cvBeamXSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignBeamXSect2.get(i).getSteelName());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, cvBeamXSect, selectionDesignBeamX, selectionArgs);
                cvBeamXSect.clear();
            }

            //Delete
            for(int i=D_MainActivity.listDesignBeamXSect2.size(); i<sizeBeamXSection; i++){
                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);
                db.delete(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, selectionDesignBeamX, selectionArgs);
            }
        }

        //Beam Y Section ===================================================================
        Cursor cursorBeamY = readDesignSectionBeamY(db);
        int sizeBeamYSection = 0;

        while(cursorBeamY.moveToNext()){
            int ID_PROJECT = cursorBeamY.
                    getInt(cursorBeamY.getColumnIndex(ProjectEntry.ID_PROJECT));
            int ID_ANALYSIS = cursorBeamY.
                    getInt(cursorBeamY.getColumnIndex(ProjectEntry.ID_ANALYSIS));
            int ID_DESIGN = cursorBeamY.
                    getInt(cursorBeamY.getColumnIndex(ProjectEntry.ID_DESIGN));

            if(ID_PROJECT == D_MainActivity.projectId &&
                    ID_ANALYSIS == D_MainActivity.analysisId &&
                    ID_DESIGN == D_MainActivity.designId){
                sizeBeamYSection++;
            }
        }

        if(D_MainActivity.listDesignBeamYSect2.size() == sizeBeamYSection){
            //Update
            String selectionDesignBeamY =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.ID_DESIGN + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<D_MainActivity.listDesignBeamYSect2.size(); i++){
                cvBeamYSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignBeamYSect2.get(i).getSteelType());
                cvBeamYSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignBeamYSect2.get(i).getSteelName());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY,
                        cvBeamYSect, selectionDesignBeamY, selectionArgs);
                cvBeamYSect.clear();
            }

        }else if(D_MainActivity.listDesignBeamYSect2.size() > sizeBeamYSection){
            //Update
            String selectionDesignBeamY =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.ID_DESIGN + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<sizeBeamYSection; i++){
                cvBeamYSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignBeamYSect2.get(i).getSteelType());
                cvBeamYSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignBeamYSect2.get(i).getSteelName());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, cvBeamYSect, selectionDesignBeamY, selectionArgs);
                cvBeamYSect.clear();
            }

            //Add
            for(int i=sizeBeamYSection; i<D_MainActivity.listDesignBeamYSect2.size(); i++){
                cvBeamYSect.put(ProjectEntry.ID_PROJECT, D_MainActivity.projectId);
                cvBeamYSect.put(ProjectEntry.ID_ANALYSIS, D_MainActivity.analysisId);
                cvBeamYSect.put(ProjectEntry.ID_DESIGN, D_MainActivity.designId);
                cvBeamYSect.put(ProjectEntry.NOMOR, i);
                cvBeamYSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignBeamYSect2.get(i).getSteelType());
                cvBeamYSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignBeamYSect2.get(i).getSteelName());

                db.insert(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, null, cvBeamYSect);
                cvBeamYSect.clear();
            }

        }else{
            //Update
            String selectionDesignBeamY =
                    ProjectEntry.ID_PROJECT + " = ? AND " +
                            ProjectEntry.ID_ANALYSIS + " = ? AND " +
                            ProjectEntry.ID_DESIGN + " = ? AND " +
                            ProjectEntry.NOMOR + " = ?";

            for(int i=0; i<D_MainActivity.listDesignBeamYSect2.size(); i++){
                cvBeamYSect.put(ProjectEntry.TYPE_STEEL,
                        D_MainActivity.listDesignBeamYSect2.get(i).getSteelType());
                cvBeamYSect.put(ProjectEntry.NAME_STEEL,
                        D_MainActivity.listDesignBeamYSect2.get(i).getSteelName());

                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);

                db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, cvBeamYSect, selectionDesignBeamY, selectionArgs);
                cvBeamYSect.clear();
            }

            //Delete
            for(int i=D_MainActivity.listDesignBeamYSect2.size(); i<sizeBeamYSection; i++){
                selectionArgs[0] = Integer.toString(D_MainActivity.projectId);
                selectionArgs[1] = Integer.toString(D_MainActivity.analysisId);
                selectionArgs[2] = Integer.toString(D_MainActivity.designId);
                selectionArgs[3] = Integer.toString(i);
                db.delete(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, selectionDesignBeamY, selectionArgs);
            }
        }
    }
    
    public Cursor readMainProject(SQLiteDatabase db) {
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.KODE_PROJECT,
                ProjectEntry.NAME_PROJECT,
                ProjectEntry.ENGINEER
        };

        Cursor cursor = db.query(ProjectEntry.TABLE_PROJECT, projections,
                null, null, null, null, null);
        return cursor;
    }

    public Cursor readMainAnalysis(SQLiteDatabase db) {
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.KODE_ANALYSIS,
                ProjectEntry.NAME_ANALYSIS,
                ProjectEntry.NST,
                ProjectEntry.NBX,
                ProjectEntry.NBY,
                ProjectEntry.STH,
                ProjectEntry.BWX,
                ProjectEntry.BWY,
                ProjectEntry.SENDI,
                ProjectEntry.FY,
                ProjectEntry.FU,
                ProjectEntry.E
        };
        Cursor cursor = db.query(ProjectEntry.TABLE_ANALYSIS, projections,
                null, null, null, null, null);
        return cursor;
    }

    public Cursor readJointLoad(SQLiteDatabase db){
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.NODAL,
                ProjectEntry.FGX,
                ProjectEntry.FGY,
                ProjectEntry.FGZ,
                ProjectEntry.MGX,
                ProjectEntry.MGY,
                ProjectEntry.MGZ
        };
        Cursor cursor = db.query(ProjectEntry.TABLE_JOINT_LOAD, projections,
                null, null, null, null, null);
        return cursor;
    }

    public Cursor readFrameLoad(SQLiteDatabase db){
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.FRAME,
                ProjectEntry.LGX,
                ProjectEntry.LGY,
                ProjectEntry.LGZ,
                ProjectEntry.SECTION
        };
        Cursor cursor = db.query(ProjectEntry.TABLE_FRAME_LOAD_AND_ASSIGN, projections,
                null, null, null, null, null);
        return cursor;
    }

    public Cursor readAreaLoad(SQLiteDatabase db){
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.AREA,
                ProjectEntry.UAL,
                ProjectEntry.SECTION
        };
        return db.query(ProjectEntry.TABLE_AREA_LOAD_AND_ASSIGN, projections,
                null, null, null, null, null);
    }

    public Cursor readFrameSection(SQLiteDatabase db){
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.NOMOR,
                ProjectEntry.KODE_FRAME_SECTION,
                ProjectEntry.NAME_FRAME_SECTION,
                ProjectEntry.ROTATE_ALPHA,
                ProjectEntry.TYPE_STEEL,
                ProjectEntry.NAME_STEEL
        };
        return db.query(ProjectEntry.TABLE_FRAME_SECTION, projections,
                null, null, null, null, null);
    }

    public Cursor readAssignSection(SQLiteDatabase db){
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.NO_FRAME,
                ProjectEntry.NOMOR
        };
        return db.query(ProjectEntry.TABLE_ASSIGN_SECTION, projections,
                null, null, null, null, null);
    }

    public Cursor readEarthQuake(SQLiteDatabase db){
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.SS,
                ProjectEntry.S1,
                ProjectEntry.IE,
                ProjectEntry.R,
                ProjectEntry.SITUS,
                ProjectEntry.AXIS,
                ProjectEntry.IS_X_POSITIF,
                ProjectEntry.IS_Y_POSITIF
        };
        return db.query(ProjectEntry.TABLE_EARTH_QUAKE, projections,
                null, null, null, null, null);
    }

    public Cursor readMainDesign(SQLiteDatabase db) {
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.ID_DESIGN,
                ProjectEntry.KODE_DESIGN,
                ProjectEntry.NAME_DESIGN
        };
        return db.query(ProjectEntry.TABLE_DESIGN, projections,
                null, null, null, null, null);
    }

    public Cursor readDesignSectionColumn(SQLiteDatabase db){
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.ID_DESIGN,
                ProjectEntry.NOMOR,
                ProjectEntry.TYPE_STEEL,
                ProjectEntry.NAME_STEEL
        };
        return db.query(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, projections,
                null, null, null, null, null);
    }

    public Cursor readDesignSectionBeamX(SQLiteDatabase db){
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.ID_DESIGN,
                ProjectEntry.NOMOR,
                ProjectEntry.TYPE_STEEL,
                ProjectEntry.NAME_STEEL
        };
        return db.query(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, projections,
                null, null, null, null, null);
    }

    public Cursor readDesignSectionBeamY(SQLiteDatabase db){
        String[] projections = {
                ProjectEntry.ID_PROJECT,
                ProjectEntry.ID_ANALYSIS,
                ProjectEntry.ID_DESIGN,
                ProjectEntry.NOMOR,
                ProjectEntry.TYPE_STEEL,
                ProjectEntry.NAME_STEEL
        };
        return db.query(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, projections,
                null, null, null, null, null);
    }

    public void deleteProject(SQLiteDatabase db, int id){
        String selection = ProjectEntry.ID_PROJECT + " = " + id;

        db.delete(ProjectEntry.TABLE_PROJECT, selection, null);
        db.delete(ProjectEntry.TABLE_ANALYSIS, selection, null);
        db.delete(ProjectEntry.TABLE_JOINT_LOAD, selection, null);
        db.delete(ProjectEntry.TABLE_FRAME_LOAD_AND_ASSIGN, selection, null);
        db.delete(ProjectEntry.TABLE_AREA_LOAD_AND_ASSIGN, selection, null);
        db.delete(ProjectEntry.TABLE_FRAME_SECTION, selection, null);
        db.delete(ProjectEntry.TABLE_ASSIGN_SECTION, selection, null);
        db.delete(ProjectEntry.TABLE_EARTH_QUAKE, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, selection, null);

        updateProjectCausedByDelete(db, id);
    }

    public void deleteAnalysis(SQLiteDatabase db, int id_project, int id_analysis){
        String selection =
                ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                ProjectEntry.ID_ANALYSIS + " = " + id_analysis;

        db.delete(ProjectEntry.TABLE_ANALYSIS, selection, null);
        db.delete(ProjectEntry.TABLE_JOINT_LOAD, selection, null);
        db.delete(ProjectEntry.TABLE_FRAME_LOAD_AND_ASSIGN, selection, null);
        db.delete(ProjectEntry.TABLE_AREA_LOAD_AND_ASSIGN, selection, null);
        db.delete(ProjectEntry.TABLE_FRAME_SECTION, selection, null);
        db.delete(ProjectEntry.TABLE_ASSIGN_SECTION, selection, null);
        db.delete(ProjectEntry.TABLE_EARTH_QUAKE, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, selection, null);

        updateAnalysisCausedByDelete(db, id_project, id_analysis);
    }

    public void deleteDesign(SQLiteDatabase db, int id_project, int id_analysis, int id_design){
        String selection =
                ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                ProjectEntry.ID_ANALYSIS + " = " + id_analysis + " AND " +
                ProjectEntry.ID_DESIGN + " = " + id_design;

        db.delete(ProjectEntry.TABLE_DESIGN, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, selection, null);
        db.delete(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, selection, null);

        updateDesignCausedByDelete(db, id_project, id_analysis, id_design);
    }

    public void updateProjectCausedByDelete(SQLiteDatabase db, int projectDeleted){
        //Cursor Database
        Cursor cursorMainProject = readMainProject(db);
        Cursor cursorMainAnalysis = readMainAnalysis(db);
        Cursor cursorJointLoad = readJointLoad(db);
        Cursor cursorFrameLoad = readFrameLoad(db);
        Cursor cursorAreaLoad = readAreaLoad(db);
        Cursor cursorFrameSection = readFrameSection(db);
        Cursor cursorAssignSection = readAssignSection(db);
        Cursor cursorEarthQuake = readEarthQuake(db);
        Cursor cursorMainDesign = readMainDesign(db);
        Cursor cursorColumnSection = readDesignSectionColumn(db);
        Cursor cursorBeamXSection = readDesignSectionBeamX(db);
        Cursor cursorBeamYSection = readDesignSectionBeamY(db);

        int sizeMainProject = cursorMainProject.getCount();
        int sizeMainAnalysis = cursorMainAnalysis.getCount();
        int sizeJointLoad = cursorJointLoad.getCount();
        int sizeFrameLoad = cursorFrameLoad.getCount();
        int sizeAreaLoad = cursorAreaLoad.getCount();
        int sizeFrameSection = cursorFrameSection.getCount();
        int sizeAssignSection = cursorAssignSection.getCount();
        int sizeEarthQuake = cursorEarthQuake.getCount();
        int sizeMainDesign = cursorMainDesign.getCount();
        int sizeColumnSection = cursorColumnSection.getCount();
        int sizeBeamXSection = cursorBeamXSection.getCount();
        int sizeBeamYSection = cursorBeamYSection.getCount();

        //Create a new map of values, where column name are the keys
        ContentValues cvProject = new ContentValues();
        ContentValues cvAnalysis = new ContentValues();
        ContentValues cvDesign = new ContentValues();
        ContentValues cvJointLoad = new ContentValues();
        ContentValues cvFrameLoad = new ContentValues();
        ContentValues cvAreaLoad = new ContentValues();
        ContentValues cvFrameSection = new ContentValues();
        ContentValues cvAssignSection = new ContentValues();
        ContentValues cvEarthQuake = new ContentValues();
        ContentValues cvColumnSect = new ContentValues();
        ContentValues cvBeamXSect = new ContentValues();
        ContentValues cvBeamYSect = new ContentValues();

        //Main Project
        for(int i=projectDeleted; i<sizeMainProject; i++){
            String selection = ProjectEntry.ID_PROJECT + " = " + (i + 1);
            cvProject.put(ProjectEntry.ID_PROJECT, i);

            db.update(ProjectEntry.TABLE_PROJECT, cvProject, selection, null);
            cvProject.clear();
        }

        //Main Analysis
        for(int i=0; i<sizeMainAnalysis; i++){
            if(cursorMainAnalysis.moveToPosition(i)) {
                int ID = cursorMainAnalysis.getInt(cursorMainAnalysis.getColumnIndex(ProjectEntry.ID_PROJECT));

                if (ID > projectDeleted) {
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvAnalysis.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_ANALYSIS, cvAnalysis, selection, null);

                    cvAnalysis.clear();
                }
            }
        }

        //Joint Load
        for(int i=0; i<sizeJointLoad; i++){
            if(cursorJointLoad.moveToPosition(i)){
                int ID = cursorJointLoad.getInt(cursorJointLoad.getColumnIndex(ProjectEntry.ID_PROJECT));

                if(ID > projectDeleted){
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvJointLoad.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_JOINT_LOAD, cvJointLoad, selection, null);

                    cvJointLoad.clear();
                }
            }
        }

        //Frame Load
        for(int i=0; i<sizeFrameLoad; i++){
            if(cursorFrameLoad.moveToPosition(i)){
                int ID = cursorFrameLoad.getInt(cursorFrameLoad.getColumnIndex(ProjectEntry.ID_PROJECT));

                if(ID > projectDeleted){
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvFrameLoad.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_FRAME_LOAD_AND_ASSIGN, cvFrameLoad, selection, null);

                    cvFrameLoad.clear();
                }
            }
        }


        //Area Load
        for(int i=0; i<sizeAreaLoad; i++){
            if(cursorAreaLoad.moveToPosition(i)){
                int ID = cursorAreaLoad.getInt(cursorAreaLoad.getColumnIndex(ProjectEntry.ID_PROJECT));

                if(ID > projectDeleted){
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvAreaLoad.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_AREA_LOAD_AND_ASSIGN, cvAreaLoad, selection, null);

                    cvAreaLoad.clear();
                }
            }
        }


        //Frame Section
        for(int i=0; i<sizeFrameSection; i++){
            if(cursorFrameSection.moveToPosition(i)){
                int ID = cursorFrameSection.
                        getInt(cursorFrameSection.getColumnIndex(ProjectEntry.ID_PROJECT));

                if(ID > projectDeleted){
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvFrameSection.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_FRAME_SECTION, cvFrameSection, selection, null);

                    cvFrameSection.clear();
                }
            }
        }

        //Assign Section
        for(int i=0; i<sizeAssignSection; i++){
            if(cursorAssignSection.moveToPosition(i)){
                int ID = cursorAssignSection.getInt(cursorAssignSection.getColumnIndex(ProjectEntry.ID_PROJECT));

                if(ID > projectDeleted) {
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvAssignSection.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_ASSIGN_SECTION, cvAssignSection, selection, null);

                    cvAssignSection.clear();
                }
            }
        }

        //Earth Quake
        for(int i=0; i<sizeEarthQuake; i++){
            if(cursorEarthQuake.moveToPosition(i)){
                int ID = cursorEarthQuake.
                        getInt(cursorEarthQuake.getColumnIndex(ProjectEntry.ID_PROJECT));

                if(ID > projectDeleted){
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvEarthQuake.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_EARTH_QUAKE, cvEarthQuake, selection, null);

                    cvEarthQuake.clear();
                }
            }
        }

        //Main Design
        for(int i=0; i<sizeMainDesign; i++){
            if(cursorMainDesign.moveToPosition(i)) {
                int ID = cursorMainDesign.getInt(cursorMainDesign.getColumnIndex(ProjectEntry.ID_PROJECT));

                if (ID > projectDeleted) {
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvDesign.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN, cvDesign, selection, null);

                    cvDesign.clear();
                }
            }
        }

        //Design Section Column
        for(int i=0; i<sizeColumnSection; i++){
            if(cursorColumnSection.moveToPosition(i)){
                int ID = cursorColumnSection.
                        getInt(cursorColumnSection.getColumnIndex(ProjectEntry.ID_PROJECT));

                if(ID > projectDeleted){
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvColumnSect.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, cvColumnSect, selection, null);

                    cvColumnSect.clear();
                }
            }
        }

        //Design Section Beam X
        for(int i=0; i<sizeBeamXSection; i++){
            if(cursorBeamXSection.moveToPosition(i)){
                int ID = cursorBeamXSection.
                        getInt(cursorBeamXSection.getColumnIndex(ProjectEntry.ID_PROJECT));

                if(ID > projectDeleted){
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvBeamXSect.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, cvBeamXSect, selection, null);

                    cvBeamXSect.clear();
                }
            }
        }

        //Design Section Beam Y
        for(int i=0; i<sizeBeamYSection; i++){
            if(cursorBeamYSection.moveToPosition(i)){
                int ID = cursorBeamYSection.
                        getInt(cursorBeamYSection.getColumnIndex(ProjectEntry.ID_PROJECT));

                if(ID > projectDeleted){
                    String selection = ProjectEntry.ID_PROJECT + " = " + ID;
                    cvBeamYSect.put(ProjectEntry.ID_PROJECT, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, cvBeamYSect, selection, null);

                    cvBeamYSect.clear();
                }
            }
        }
    }

    public void updateAnalysisCausedByDelete(SQLiteDatabase db, int id_project, int analysisDeleted){

        //Cursor Database
        Cursor cursorMainAnalysis = readMainAnalysis(db);
        Cursor cursorJointLoad = readJointLoad(db);
        Cursor cursorFrameLoad = readFrameLoad(db);
        Cursor cursorAreaLoad = readAreaLoad(db);
        Cursor cursorFrameSection = readFrameSection(db);
        Cursor cursorAssignSection = readAssignSection(db);
        Cursor cursorEarthQuake = readEarthQuake(db);
        Cursor cursorMainDesign = readMainDesign(db);
        Cursor cursorColumnSection = readDesignSectionColumn(db);
        Cursor cursorBeamXSection = readDesignSectionBeamX(db);
        Cursor cursorBeamYSection = readDesignSectionBeamY(db);

        int sizeMainAnalysis = cursorMainAnalysis.getCount();
        int sizeJointLoad = cursorJointLoad.getCount();
        int sizeFrameLoad = cursorFrameLoad.getCount();
        int sizeAreaLoad = cursorAreaLoad.getCount();
        int sizeFrameSection = cursorFrameSection.getCount();
        int sizeAssignSection = cursorAssignSection.getCount();
        int sizeEarthQuake = cursorEarthQuake.getCount();
        int sizeMainDesign = cursorMainDesign.getCount();
        int sizeColumnSection = cursorColumnSection.getCount();
        int sizeBeamXSection = cursorBeamXSection.getCount();
        int sizeBeamYSection = cursorBeamYSection.getCount();

        //Create a new map of values, where column name are the keys
        ContentValues cvAnalysis = new ContentValues();
        ContentValues cvDesign = new ContentValues();
        ContentValues cvJointLoad = new ContentValues();
        ContentValues cvFrameLoad = new ContentValues();
        ContentValues cvAreaLoad = new ContentValues();
        ContentValues cvFrameSection = new ContentValues();
        ContentValues cvAssignSection = new ContentValues();
        ContentValues cvEarthQuake = new ContentValues();
        ContentValues cvColumnSect = new ContentValues();
        ContentValues cvBeamXSect = new ContentValues();
        ContentValues cvBeamYSect = new ContentValues();

        //Main Analysis
        for(int i=analysisDeleted; i<sizeMainAnalysis; i++){
            String selection =
                    ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                    ProjectEntry.ID_ANALYSIS + " = " + (i + 1);
            cvAnalysis.put(ProjectEntry.ID_ANALYSIS, i);

            db.update(ProjectEntry.TABLE_ANALYSIS, cvAnalysis, selection, null);
            cvAnalysis.clear();
        }

        //Joint Load
        for(int i=0; i<sizeJointLoad; i++){
            if(cursorJointLoad.moveToPosition(i)){
                int ID = cursorJointLoad.getInt(cursorJointLoad.getColumnIndex(ProjectEntry.ID_ANALYSIS));

                if(ID > analysisDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + ID;
                    cvJointLoad.put(ProjectEntry.ID_ANALYSIS, ID - 1);

                    db.update(ProjectEntry.TABLE_JOINT_LOAD, cvJointLoad, selection, null);

                    cvJointLoad.clear();
                }
            }
        }

        //Frame Load
        for(int i=0; i<sizeFrameLoad; i++){
            if(cursorFrameLoad.moveToPosition(i)){
                int ID = cursorFrameLoad.getInt(cursorFrameLoad.getColumnIndex(ProjectEntry.ID_ANALYSIS));

                if(ID > analysisDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + ID;
                    cvFrameLoad.put(ProjectEntry.ID_ANALYSIS, ID - 1);

                    db.update(ProjectEntry.TABLE_FRAME_LOAD_AND_ASSIGN, cvFrameLoad, selection, null);

                    cvFrameLoad.clear();
                }
            }
        }


        //Area Load
        for(int i=0; i<sizeAreaLoad; i++){
            if(cursorAreaLoad.moveToPosition(i)){
                int ID = cursorAreaLoad.getInt(cursorAreaLoad.getColumnIndex(ProjectEntry.ID_ANALYSIS));

                if(ID > analysisDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + ID;
                    cvAreaLoad.put(ProjectEntry.ID_ANALYSIS, ID - 1);

                    db.update(ProjectEntry.TABLE_AREA_LOAD_AND_ASSIGN, cvAreaLoad, selection, null);

                    cvAreaLoad.clear();
                }
            }
        }


        //Frame Section
        for(int i=0; i<sizeFrameSection; i++){
            if(cursorFrameSection.moveToPosition(i)){
                int ID = cursorFrameSection.
                        getInt(cursorFrameSection.getColumnIndex(ProjectEntry.ID_ANALYSIS));

                if(ID > analysisDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + ID;
                    cvFrameSection.put(ProjectEntry.ID_ANALYSIS, ID - 1);

                    db.update(ProjectEntry.TABLE_FRAME_SECTION, cvFrameSection, selection, null);

                    cvFrameSection.clear();
                }
            }
        }

        //Assign Section
        for(int i=0; i<sizeAssignSection; i++){
            if(cursorAssignSection.moveToPosition(i)){
                int ID = cursorAssignSection.getInt(cursorAssignSection.getColumnIndex(ProjectEntry.ID_ANALYSIS));

                if(ID > analysisDeleted) {
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + ID;
                    cvAssignSection.put(ProjectEntry.ID_ANALYSIS, ID - 1);

                    db.update(ProjectEntry.TABLE_ASSIGN_SECTION, cvAssignSection, selection, null);

                    cvAssignSection.clear();
                }
            }
        }

        //Earth Quake
        for(int i=0; i<sizeEarthQuake; i++){
            if(cursorEarthQuake.moveToPosition(i)){
                int ID = cursorEarthQuake.
                        getInt(cursorEarthQuake.getColumnIndex(ProjectEntry.ID_ANALYSIS));

                if(ID > analysisDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                                    ProjectEntry.ID_ANALYSIS + " = " + ID;
                    cvEarthQuake.put(ProjectEntry.ID_ANALYSIS, ID - 1);

                    db.update(ProjectEntry.TABLE_EARTH_QUAKE, cvEarthQuake, selection, null);

                    cvEarthQuake.clear();
                }
            }
        }

        //Main Design
        for(int i=0; i<sizeMainDesign; i++){
            if(cursorMainDesign.moveToPosition(i)) {
                int ID = cursorMainDesign.getInt(cursorMainDesign.getColumnIndex(ProjectEntry.ID_ANALYSIS));

                if (ID > analysisDeleted) {
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + ID;
                    cvDesign.put(ProjectEntry.ID_ANALYSIS, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN, cvDesign, selection, null);

                    cvDesign.clear();
                }
            }
        }

        //Design Section Column
        for(int i=0; i<sizeColumnSection; i++){
            if(cursorColumnSection.moveToPosition(i)){
                int ID = cursorColumnSection.
                        getInt(cursorColumnSection.getColumnIndex(ProjectEntry.ID_ANALYSIS));

                if(ID > analysisDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + ID;
                    cvColumnSect.put(ProjectEntry.ID_ANALYSIS, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, cvColumnSect, selection, null);

                    cvColumnSect.clear();
                }
            }
        }

        //Design Section Beam X
        for(int i=0; i<sizeBeamXSection; i++){
            if(cursorBeamXSection.moveToPosition(i)){
                int ID = cursorBeamXSection.
                        getInt(cursorBeamXSection.getColumnIndex(ProjectEntry.ID_ANALYSIS));

                if(ID > analysisDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + ID;
                    cvBeamXSect.put(ProjectEntry.ID_ANALYSIS, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, cvBeamXSect, selection, null);

                    cvBeamXSect.clear();
                }
            }
        }

        //Design Section Beam Y
        for(int i=0; i<sizeBeamYSection; i++){
            if(cursorBeamYSection.moveToPosition(i)){
                int ID = cursorBeamYSection.
                        getInt(cursorBeamYSection.getColumnIndex(ProjectEntry.ID_ANALYSIS));

                if(ID > analysisDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + ID;
                    cvBeamYSect.put(ProjectEntry.ID_ANALYSIS, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, cvBeamYSect, selection, null);

                    cvBeamYSect.clear();
                }
            }
        }
    }

    public void updateDesignCausedByDelete(SQLiteDatabase db, int id_project, int id_analysis, int designDeleted){

        //Cursor Database
        Cursor cursorMainDesign = readMainDesign(db);
        Cursor cursorColumnSection = readDesignSectionColumn(db);
        Cursor cursorBeamXSection = readDesignSectionBeamX(db);
        Cursor cursorBeamYSection = readDesignSectionBeamY(db);

        int sizeMainDesign = cursorMainDesign.getCount();
        int sizeColumnSection = cursorColumnSection.getCount();
        int sizeBeamXSection = cursorBeamXSection.getCount();
        int sizeBeamYSection = cursorBeamYSection.getCount();

        //Create a new map of values, where column name are the keys
        ContentValues cvDesign = new ContentValues();
        ContentValues cvColumnSect = new ContentValues();
        ContentValues cvBeamXSect = new ContentValues();
        ContentValues cvBeamYSect = new ContentValues();

        //Main Design
        for(int i=designDeleted; i<sizeMainDesign; i++){
            String selection =
                    ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                    ProjectEntry.ID_ANALYSIS + " = " + id_analysis + " AND " +
                    ProjectEntry.ID_DESIGN + " = " + (i + 1);
            cvDesign.put(ProjectEntry.ID_DESIGN, i);

            db.update(ProjectEntry.TABLE_DESIGN, cvDesign, selection, null);
            cvDesign.clear();
        }

        //Design Section Column
        for(int i=0; i<sizeColumnSection; i++){
            if(cursorColumnSection.moveToPosition(i)){
                int ID = cursorColumnSection.
                        getInt(cursorColumnSection.getColumnIndex(ProjectEntry.ID_DESIGN));

                if(ID > designDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + id_analysis + " AND " +
                            ProjectEntry.ID_DESIGN + " = " + ID;
                    cvColumnSect.put(ProjectEntry.ID_DESIGN, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN_SECTION_COLUMN, cvColumnSect, selection, null);

                    cvColumnSect.clear();
                }
            }
        }

        //Design Section Beam X
        for(int i=0; i<sizeBeamXSection; i++){
            if(cursorBeamXSection.moveToPosition(i)){
                int ID = cursorBeamXSection.
                        getInt(cursorBeamXSection.getColumnIndex(ProjectEntry.ID_DESIGN));

                if(ID > designDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + id_analysis + " AND " +
                            ProjectEntry.ID_DESIGN + " = " + ID;
                    cvBeamXSect.put(ProjectEntry.ID_DESIGN, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMX, cvBeamXSect, selection, null);

                    cvBeamXSect.clear();
                }
            }
        }

        //Design Section Beam Y
        for(int i=0; i<sizeBeamYSection; i++){
            if(cursorBeamYSection.moveToPosition(i)){
                int ID = cursorBeamYSection.
                        getInt(cursorBeamYSection.getColumnIndex(ProjectEntry.ID_DESIGN));

                if(ID > designDeleted){
                    String selection =
                            ProjectEntry.ID_PROJECT + " = " + id_project + " AND " +
                            ProjectEntry.ID_ANALYSIS + " = " + id_analysis + " AND " +
                            ProjectEntry.ID_DESIGN + " = " + ID;
                    cvBeamYSect.put(ProjectEntry.ID_DESIGN, ID - 1);

                    db.update(ProjectEntry.TABLE_DESIGN_SECTION_BEAMY, cvBeamYSect, selection, null);

                    cvBeamYSect.clear();
                }
            }
        }
    }
}

