/*/////////////////////////////////////////////////
Coding by Kristalina K S, Christopher T., N.M. Naufaldo
Last update : July 2020
//////////////////////////////////////////////// */

package com.cemapp.steelframe3D;

import android.Manifest;
import android.app.AlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class D_MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    //gempa
    public static double gempaSs = 1.0;
    public static double gempaS1 = 1.0;
    public static double gempaR = 1.0;
    public static double gempaIe = 1.0;
    public static String gempaSitus = "SA";

    //frame section
    private static String sectionCode;
    private static String sectionName;
    private static String steelName;
    private static String steelType;
    private static int bajaNo;
    private static float bajaB;
    private static float bajaH;
    private static float bajaTw;
    private static float bajaTf;
    private static float bajaR1;
    private static float bajaR2;
    private static float bajaA;
    private static float bajaCy;
    private static float bajaCz;
    private static float bajaIy;
    private static float bajaIz;
    private static float bajaRy;
    private static float bajaRz;
    private static float bajaSy;
    private static float bajaSz;
    public static float bajaFy;
    public static float bajaFu;
    public static float bajaE;
    public static boolean isAlphaRotated;

    //ada tidaknya load
    public static boolean[] isAreaLoadExist;
    public static boolean[][] isFrameLoadExist;
    public static boolean[][] isJointLoadExist;
    public static boolean isBebanGempaX;
    public static boolean isBebanGempaY;
    public static boolean isGempaXPositif;
    public static boolean isGempaYPositif;

    //ada tidaknya design section sebelum run
    public static boolean isBeamXChosen = false;
    public static boolean isBeamYChosen = false;
    public static boolean isColumnChosen = false;

    //terkait project baru
    public static boolean isCreateProject = false;
    public static boolean isAnalysis = false;
    public static boolean isDesignNew;
    public static boolean isAnalysisNew;
    public static boolean isProjectNew;

    //terpilih tidaknya elemen
    public static boolean[] isPointerNodeSelected;
    public static boolean[] isPointerElementSelected;
    public static boolean[] isPointerAreaSelected;

    public static boolean[] isFirstPositif_AxialKolom;
    public static boolean[] isFirstPositif_Shear22Kolom;
    public static boolean[] isFirstPositif_Shear33Kolom;
    public static boolean[] isFirstPositif_TorsiKolom;
    public static boolean[] isFirstPositif_Momen22Kolom;
    public static boolean[] isFirstPositif_Momen33Kolom;

    public static boolean[] isFirstPositif_AxialBalokX;
    public static boolean[] isFirstPositif_Shear22BalokX;
    public static boolean[] isFirstPositif_Shear33BalokX;
    public static boolean[] isFirstPositif_TorsiBalokX;
    public static boolean[] isFirstPositif_Momen22BalokX;
    public static boolean[] isFirstPositif_Momen33BalokX;

    public static boolean[] isFirstPositif_AxialBalokY;
    public static boolean[] isFirstPositif_Shear22BalokY;
    public static boolean[] isFirstPositif_Shear33BalokY;
    public static boolean[] isFirstPositif_TorsiBalokY;
    public static boolean[] isFirstPositif_Momen22BalokY;
    public static boolean[] isFirstPositif_Momen33BalokY;

    private static boolean isIF_Berwarna = false;

    public static boolean isStartProject;

    private static boolean isNodeSelectedFAB = false;
    private static boolean isElementSelectedFAB = false;
    private static boolean isAreaSelectedFAB = false;
    private static boolean isShowNodal = false;
    private static boolean isShowElement = false;
    private static boolean isShowInternalForceValue = false;
    private static boolean isShowDisplacementValue = false;
    public static boolean isDrawDisplacement = false;
    private static boolean isAlreadyRunning = false;
    private static boolean isRotateActive = true;
    private static boolean isPerspektif = true;
    private static boolean isTimerSleep = true;
    private static boolean isLockOpen = true;

    public static boolean isRestraintPin = false;
    public static boolean isDefineFrameSectionNow = true;
    public static boolean isRecyclerViewForDefine = true;
    public static boolean isAssignFrameSectionNow = true;
    public static boolean isDrawLoadCurrent = false;

    public static double[][][] IF_AxialKolom;
    public static double[][][] IF_Shear22Kolom;
    public static double[][][] IF_Shear33Kolom;
    public static double[][][] IF_TorsiKolom;
    public static double[][][] IF_Momen22Kolom;
    public static double[][][] IF_Momen33Kolom;

    public static double[][][] IF_AxialBalokX;
    public static double[][][] IF_Shear22BalokX;
    public static double[][][] IF_Shear33BalokX;
    public static double[][][] IF_TorsiBalokX;
    public static double[][][] IF_Momen22BalokX;
    public static double[][][] IF_Momen33BalokX;

    public static double[][][] IF_AxialBalokY;
    public static double[][][] IF_Shear22BalokY;
    public static double[][][] IF_Shear33BalokY;
    public static double[][][] IF_TorsiBalokY;
    public static double[][][] IF_Momen22BalokY;
    public static double[][][] IF_Momen33BalokY;

    public static double[][][] IF_AxialKolomFix;
    public static double[][][] IF_Shear22KolomFix;
    public static double[][][] IF_Shear33KolomFix;
    public static double[][][] IF_TorsiKolomFix;
    public static double[][][] IF_Momen22KolomFix;
    public static double[][][] IF_Momen33KolomFix;

    public static double[][][] IF_AxialBalokXFix;
    public static double[][][] IF_Shear22BalokXFix;
    public static double[][][] IF_Shear33BalokXFix;
    public static double[][][] IF_TorsiBalokXFix;
    public static double[][][] IF_Momen22BalokXFix;
    public static double[][][] IF_Momen33BalokXFix;

    public static double[][][] IF_AxialBalokYFix;
    public static double[][][] IF_Shear22BalokYFix;
    public static double[][][] IF_Shear33BalokYFix;
    public static double[][][] IF_TorsiBalokYFix;
    public static double[][][] IF_Momen22BalokYFix;
    public static double[][][] IF_Momen33BalokYFix;

    public static float alfa = 0;
    public static float beta = 0;
    public static float gama = 0;

    private static float dataProfile;
    private static float distanceTextReaction = 15;
    private static float sizeTextReaction = 10;

    private static float focalLength = 1120f;

    public static float[][] koorNode;
    public static float[][] koorNodeFix;
    public static float[][] koorNodeReal;
    public static float[][] koorNodeDeformed;
    public static float[][] koorNodeDeformedFix;
    public static float[][][] koorNodeAreaSelect;
    public static float[][][][] koorNodeJointLoad;
    public static float[][][][] koorDistributedFrameLoad;
    public static float[][][][] koorNodeJointLoadFix;
    public static float[][][][] koorDistributedFrameLoadFix;
    public static float[][][] koor3D_Axis;
    public static float[][][] koor3D_AxisFix;

    private static float panCanvasX = 0;
    private static float panCanvasY = 0;
    private static float skalaCanvas = 1;

    public static float[][][] pinRestraint;
    public static float[][][] pinRestraintFix;
    public static float[][][] tumpuanSendi2;
    public static float[][][] tumpuanSendi2Fix;

    private static float singleTapX;
    private static float singleTapY;
    private static float singleTapAcuan;

    public static float[][][] tumpuanJepit1;
    public static float[][][] tumpuanJepit2;
    public static float[][][] tumpuanJepit1Fix;
    public static float[][][] tumpuanJepit2Fix;

    public static float[][] jointLoad;
    public static float[][] frameLoad;
    public static float[] areaLoad;

    //dimensi portal
    public static float Sth;
    public static float Bwx;
    public static float Bwy;
    public static int Nst;
    public static int Nbx;
    public static int Nby;

    //id untuk sql
    public static int analysisId = -1;
    public static int designId = -1;
    public static int projectId = -1;

    //kode tampilan grafik
    public static int drawInternalForceCurrent = 0;
    //0 = Default, 1 = P, 2 = V2, 3 = V3, 4 = T, 5 = M2, 6 = M3, 7 = Reaksi
    public static int drawLoadCurrent = 0;
    //0 = Default, 1 = Joint, 2 = Frame, 3 = Area, 4 = Frame Section, 5 = SF P-M, 6 = SF Shear
    //7 = Design Section, 8 = SF P-M Design, 9 = SF Shear Design

    //internal forces kolom
    public static int[][] IF_AxialKolomNodal;
    public static int[][] IF_Shear22KolomNodal;
    public static int[][] IF_Shear33KolomNodal;
    public static int[][] IF_Momen22KolomNodal;
    public static int[][] IF_Momen33KolomNodal;

    //internal forces balok x
    public static int[][] IF_AxialBalokXNodal;
    public static int[][] IF_Shear22BalokXNodal;
    public static int[][] IF_Shear33BalokXNodal;
    public static int[][] IF_Momen22BalokXNodal;
    public static int[][] IF_Momen33BalokXNodal;

    //internal forces balok y
    public static int[][] IF_AxialBalokYNodal;
    public static int[][] IF_Shear22BalokYNodal;
    public static int[][] IF_Shear33BalokYNodal;
    public static int[][] IF_Momen22BalokYNodal;
    public static int[][] IF_Momen33BalokYNodal;

    private static int numberPhase;

    //penunjuk elemen
    public static int[][] pointerElemenBalokXZ;
    public static int[][] pointerElemenBalokYZ;
    public static int[][] pointerElemenKolom;
    public static int[][] pointerElemenStruktur;
    public static int[] pointerFrameSection;
    public static int[] pointerAreaSection;
    public static int[][] pointerAreaJoint;

    //dialog
    private static AlertDialog dialogAbout;
    private static AlertDialog dialogJointLoad;
    private static AlertDialog dialogFrameLoad;
    private static AlertDialog dialogAreaLoad;
    private static AlertDialog dialogFrameSectionInitial;
    public static AlertDialog dialogFrameSectionList;
    private static AlertDialog dialogFrameSectionAdd;
    private static AlertDialog dialogEarthQuake;
    private static AlertDialog dialogColumnSection;
    private static AlertDialog dialogXBeamSection;
    private static AlertDialog dialogYBeamSection;
    private static AlertDialog dialogShowGraph;
    private static AlertDialog dialogShowLoad;
    private static Dialog dialogFrameSectionEdit;

    private static ArrayAdapter<String> arrayAdapterAll;
    private static ArrayAdapter<String> arrayAdapterBeam;
    private static ArrayAdapter<String> arrayAdapterColumn;
    public static ArrayList<String> listAnalysis = new ArrayList<>();
    public static ArrayList<String> listDesign = new ArrayList<>();
    public static ArrayList<String> listSpinAnalysis = new ArrayList<>();
    public static ArrayList<String> listSpinDesign = new ArrayList<>();

    //list untuk design
    private static ArrayList<String> listALLCOLUMN = new ArrayList<>();
    private static ArrayList<String> listALLBEAM = new ArrayList<>();
    private static ArrayList<String> listAllColumnSection = new ArrayList<>();
    private static ArrayList<String> listAllXBeamSection = new ArrayList<>();
    private static ArrayList<String> listAllYBeamSection = new ArrayList<>();
    private static ArrayList<String> listDesignColSect = new ArrayList<>(); //untuk dialog
    private static ArrayList<String> listDesignBeamXSect = new ArrayList<>(); //untuk dialog
    private static ArrayList<String> listDesignBeamYSect = new ArrayList<>(); //untuk dialog
    public static List<SteelSection> listFrameSection;
    public static List<SteelSection> listDesignBeamXSect2; //untuk masuk class
    public static List<SteelSection> listDesignBeamYSect2; //untuk masuk class
    public static List<SteelSection> listDesignColSect2; //untuk masuk class
    public static List<SteelSection> listDesignBeamXSect2R; //untuk masuk class (rotated)
    public static List<SteelSection> listDesignBeamYSect2R; //untuk masuk class (rotated)
    public static List<SteelSection> listDesignColSect2R; //untuk masuk class (rotated)
    public static List<Y_BebanBatang> listBebanBatang;

    //list untuk profil baja
    private static ArrayList<String> WFtype = new ArrayList<>();
    private static ArrayList<String> Ltype = new ArrayList<>();
    private static ArrayList<String> Ctype = new ArrayList<>();
    private static ArrayList<String> Itype = new ArrayList<>();

    private static CardView cardToolView;
    public static Context appContext;
    public static Context baseContext;
    public static Context thisContext;
    private static DrawerLayout drawerLayout;
    private static GestureDetectorCompat gestureDetectorCompat;

    //floating action button
    public static FloatingActionMenu fabMenu;
    private static FloatingActionButton fabJointLoad;
    private static FloatingActionButton fabFrameLoad;
    private static FloatingActionButton fabAreaLoad;
    private static FloatingActionButton fabFrameSection;
    private static FloatingActionButton fabEarthQuake;
    private static FloatingActionButton fabColumnSection;
    private static FloatingActionButton fabXBeamSection;
    private static FloatingActionButton fabYBeamSection;

    //navigation
    public static BottomNavigationView bottomNavView;
    private static Menu menuDrawer;
    private static Menu menuOptionMenu;
    private static MenuItem toolRun;
    private static MenuItem toolLock;
    private static MenuItem toolUndeformed;
    private static MenuItem toolShowGraph;
    private static MenuItem toolShowLoad;
    private static MenuItem toolShowDisplacementValue;
    private static MenuItem toolShowFrameSection;
    private static MenuItem toolShowPM;
    private static MenuItem toolShowShear;
    private static NavigationView navigationView;

    private static Handler handler;

    //view 3D XY XZ YZ
    private static int tandaViewBottomNav;
    public static int viewXY_Max;
    public static int viewXZ_Max;
    public static int viewYZ_Max;
    public static int viewXY_Current = 2;
    public static int viewXZ_Current = 1;
    public static int viewYZ_Current = 1;
    private static ImageButton imgArrowBack;
    private static ImageButton imgArrowForward;
    private static TextView txtTandaAxis;

    //untuk canvas
    public static Bitmap bitmap;
    public static Canvas canvas;
    public static ImageView imgCanvas;

    //toolbar tampilan
    private static ImageButton imgViewPan;
    private static ImageButton imgViewRotate;
    private static ImageButton imgViewToggle;
    private static ImageButton imgViewZoomIn;
    private static ImageButton imgViewZoomOut;
    private static ImageButton imgViewSelectAll;
    private static ImageButton imgViewDeselectAll;

    public static InputMethodManager keyboard;
    private static Intent intent = new Intent();

    private static Paint pGaris = new Paint();
    private static Paint pGarisSelected = new Paint();
    private static Paint pNodeSelected = new Paint();
    private static Paint pGarisGray = new Paint();
    private static Paint pAreaSelected = new Paint();
    private static Paint pText = new Paint();
    private static Paint pTextNodal = new Paint();
    private static Paint pTextAxis = new Paint();
    private static Paint pTextTandaAxis = new Paint();
    private static Paint pTumpuan = new Paint();
    private static Paint pPath = new Paint();
    private static Paint pPathPositif = new Paint();
    private static Paint pPathNegatif = new Paint();
    private static Paint pPathAxis = new Paint();
    private static Paint pJointLoad = new Paint();
    private static Paint pJointLoadUjung = new Paint();
    private static Paint pFrameLoad = new Paint();
    private static Paint pAxis = new Paint();
    private static Paint pTextReaction = new Paint();
    private static Paint pPathKolom = new Paint();
    private static Paint pPathBalokX = new Paint();
    private static Paint pPathBalokY = new Paint();
    private static Paint pPathEffect = new Paint();
    private static Paint pPathEffect2 = new Paint();

    private static Path path = new Path();
    private static Runnable runnable;
    private static ScaleGestureDetector scaleGestureDetector;
    public static Spinner spinAnalysis;
    public static Spinner spinDesign;

    public static String analysisName;
    public static String analysisKode;
    public static String designName;
    public static String designKode;
    public static String projectEngineer;
    public static String projectName;
    public static String projectKode;

    private static Timer timer;
    private static Toolbar toolbar;

    //Export
    H_ExportReportPDF exportReportPDF;
    private static boolean isExportAnalysis;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    //SQLite Steel
    private static Cursor cursor;
    private static G_DBHelper_Steel dbHelperSteel;
    private static SQLiteDatabase dbSteel;

    //SQLite Project
    private static Cursor cursorProject;
    private static G_DBHelper_Project dbHelperProject;
    private static SQLiteDatabase dbProject;
    
    //Loading
    private static LinearLayout layoutAxis;
    private static LinearLayout layoutSpin;
    private static RelativeLayout loadingPanel;
    private static TextView txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_main);

        appContext = getApplicationContext();
        baseContext = getBaseContext();
        thisContext = D_MainActivity.this;

        bottomNavView = findViewById(R.id.bottom_nav_view);
        cardToolView = findViewById(R.id.cardToolView);

        imgCanvas = findViewById(R.id.imgCanvas);
        imgArrowForward = findViewById(R.id.imgArrowForward);
        imgArrowBack = findViewById(R.id.imgArrowBack);

        fabMenu = findViewById(R.id.fabMenu);
        fabJointLoad = findViewById(R.id.fabJointLoad);
        fabFrameLoad = findViewById(R.id.fabFrameLoad);
        fabAreaLoad = findViewById(R.id.fabAreaLoad);
        fabFrameSection = findViewById(R.id.fabFrameSection);
        fabEarthQuake = findViewById(R.id.fabEarthQuake);
        fabColumnSection = findViewById(R.id.fabColumnSection);
        fabXBeamSection = findViewById(R.id.fabXBeamSection);
        fabYBeamSection = findViewById(R.id.fabYBeamSection);
        layoutAxis = findViewById(R.id.layoutAxis);
        layoutSpin = findViewById(R.id.layoutSpin);
        loadingPanel = findViewById(R.id.loadingPanel);
        navigationView = findViewById(R.id.nav_view);
        spinAnalysis = findViewById(R.id.spinAnalysis);
        spinDesign = findViewById(R.id.spinDesign);
        toolbar = findViewById(R.id.toolbar);
        txtLoading = findViewById(R.id.txtLoading);
        txtTandaAxis = findViewById(R.id.txtTandaAxis);

        setSupportActionBar(toolbar);
        keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        gestureDetectorCompat = new GestureDetectorCompat(this, this);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        menuDrawer = navigationView.getMenu();

        setMenuItemEnable(menuDrawer, false,
                R.id.nav_export,
                R.id.nav_exportDesign);

        //mengatur drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        drawerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!drawerLayout.isDrawerOpen(navigationView)) {
                    return gestureDetectorCompat.onTouchEvent(event);
                } else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }
            }
        });

        navigasiSpinnerAnalysis(); //menggambarnya di sini
        navigasiBottom();
        navigasiDrawer();
        navigasiFAB();
        navigasiToolbarView();
        setPaint();
        setCanvasBitmap();
        setTimer();
        setLoading(false, "save");
    }

    //----------MENGATUR OPTION ATAS DAN TITIK TIGA----------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        menu.setGroupEnabled(R.id.toolMenu, true);
        menuOptionMenu = menu;

        //variabel icon
        toolRun = menu.findItem(R.id.toolRun);
        toolLock = menu.findItem(R.id.toolLock);
        toolUndeformed = menu.findItem(R.id.toolUndeformed);

        //variabel menu titik tiga (default)
        toolShowGraph = menu.findItem(R.id.toolShowGraph);
        toolShowLoad = menu.findItem(R.id.toolShowLoad);
        toolShowDisplacementValue = menu.findItem(R.id.toolShowDisplacementValue);

        //variabel menu titik tiga (design)
        toolShowFrameSection = menu.findItem(R.id.toolShowFrameSection);
        toolShowPM = menu.findItem(R.id.toolShowPM);
        toolShowShear = menu.findItem(R.id.toolShowShear);

        //set keadaan awal icon
        toolRun.setIcon(R.drawable.ic_toolbar_run);
        toolRun.setEnabled(true);
        toolLock.setIcon(R.drawable.ic_toolbar_unlock);
        toolLock.setEnabled(true);
        if(isAnalysis) {
            toolUndeformed.setIcon(R.drawable.ic_toolbar_undeformed_false);
            toolUndeformed.setEnabled(false);
        } else {
            toolUndeformed.setVisible(false);
        }

        //set keadaan awal menu titik tiga
        if(isAnalysis) {
            toolShowGraph.setEnabled(false);
            toolShowLoad.setEnabled(true);
            toolShowDisplacementValue.setEnabled(false);
            toolShowFrameSection.setVisible(false);
            toolShowPM.setVisible(false);
            toolShowShear.setVisible(false);
        }else {
            toolShowGraph.setVisible(false);
            toolShowLoad.setVisible(false);
            toolShowDisplacementValue.setVisible(false);
            toolShowFrameSection.setVisible(true);
            toolShowFrameSection.setEnabled(false);
            toolShowPM.setVisible(true);
            toolShowPM.setEnabled(false);
            toolShowShear.setVisible(true);
            toolShowShear.setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolRun:
                if (isAnalysis) {
                    setMainRunning();
                    isLockOpen = false;
                    item = menuOptionMenu.findItem(R.id.toolLock);
                    item.setIcon(R.drawable.ic_toolbar_lock);
                } else {
                    if (isColumnChosen && isBeamXChosen && isBeamYChosen) {
                        setDesignRunning();
                        isLockOpen = false;
                        item = menuOptionMenu.findItem(R.id.toolLock);
                        item.setIcon(R.drawable.ic_toolbar_lock);
                    } else {
                        Toast.makeText(getApplicationContext(), "Choose the design sections!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;

            case R.id.toolLock:
                if (isLockOpen) {
                    isLockOpen = false;
                    item.setIcon(R.drawable.ic_toolbar_lock);
                    setLocked();
                } else {
                    showLockDialog();
                }
                return true;

            case R.id.toolUndeformed:
                if(isDrawDisplacement) {
                    item.setIcon(R.drawable.ic_toolbar_undeformed);
                    isDrawDisplacement = false;
                    isDrawLoadCurrent = false;
                    drawLoadCurrent = 0;
                    drawInternalForceCurrent = 0;
                    setKoorToDrawLoad();
                    if (isAlreadyRunning) {
                        Running objekRun = new Running();
                        objekRun.setKoorIF_Kolom();
                        objekRun.setKoorIF_BalokX();
                        objekRun.setKoorIF_BalokY();
                        objekRun.setKoorDeformed();
                    }
                    drawGeneral();
                }else{
                    item.setIcon(R.drawable.ic_toolbar_deformed);
                    isDrawDisplacement = true;
                    setDeselectAll();
                    drawGeneral();
                }
                return true;

            case R.id.toolShowGraph:
                showShowGraphMenu();
                return true;

            case R.id.toolShowLoad:
                showShowLoadMenu();
                return true;

            case R.id.toolShowDisplacementValue:
                isShowDisplacementValue = !isShowDisplacementValue;
                return true;

            case R.id.toolShowFrameSection:
                isDrawDisplacement = false;
                isDrawLoadCurrent = true;
                drawLoadCurrent = 7;
                setDeselectAll();
                isTimerSleep = true;
                drawGeneral();
                return true;

            case R.id.toolShowPM:
                isDrawDisplacement = false;
                isDrawLoadCurrent = true;
                drawLoadCurrent = 8;
                setDeselectAll();
                isTimerSleep = true;
                drawGeneral();
                return true;

            case R.id.toolShowShear:
                isDrawDisplacement = false;
                isDrawLoadCurrent = true;
                drawLoadCurrent = 9;
                setDeselectAll();
                isTimerSleep = true;
                drawGeneral();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------SET PINDAH MENU----------//
    private void showCreateProject() {
        saveProject();
        isCreateProject = true;
        intent.setClass(getApplicationContext(), A_ProjectListActivity.class);
        startActivity(intent);
        finish();
    }

    private void showOpenProject() {
        saveProject();
        isCreateProject = false;
        intent.setClass(getApplicationContext(), A_ProjectListActivity.class);
        startActivity(intent);
        finish();
    }

    private void showOpenAnalysis() {
        saveProject();
        intent.setClass(getApplicationContext(), B_AnalysisListActivity.class);
        startActivity(intent);
        finish();
    }

    private void showOpenDesign() {
        saveProject();
        intent.setClass(getApplicationContext(), C_DesignListActivity.class);
        startActivity(intent);
        finish();
    }

    private void showAbout() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_99about, null);
        mBuilder.setView(mView);
        dialogAbout = mBuilder.create();
        dialogAbout.show();
    }

    //----------SET TAMPILAN MAIN ACTIVITY (ANALYSIS ATAU DESIGN)----------//
    private void setPenggambaranAwal() {
        setKoorNode(Nst, Nbx, Nby, Sth, Bwx, Bwy);

        if (isRestraintPin) {
            setPinRestraint(Nbx, Nby, 0.1f * 3f / Math.max(Bwx, Bwy));
            setSkala(315f, pinRestraint);
            setSkala(315f, tumpuanSendi2);
            setSkala(315f, pinRestraintFix);
            setSkala(315f, tumpuanSendi2Fix);
        } else {
            setTumpuanJepit(Nbx, Nby, 0.1f * 3f / Math.max(Bwx, Bwy));
            setSkala(315f, tumpuanJepit1);
            setSkala(315f, tumpuanJepit2);

            setSkala(315f, tumpuanJepit1Fix);
            setSkala(315f, tumpuanJepit2Fix);
        }

        setSkala(315f, koorNode);
        setSkala(315f, koorNodeFix);
        setSkala(315f, koorNodeAreaSelect);
        setSkala(315f, koor3D_Axis);
        setSkala(315f, koor3D_AxisFix);

        viewXY_Max = Nst + 1;
        viewXZ_Max = Nby + 1;
        viewYZ_Max = Nbx + 1;

        panCanvasX = 0;
        panCanvasY = 0;
        focalLength = 1120f;

        jointLoad = new float[(Nst + 1) * (Nbx + 1) * (Nby + 1)][6];
        frameLoad = new float[pointerElemenStruktur.length][3];
        areaLoad = new float[Nbx * Nby * Nst];

        koorNodeJointLoad = new float[(Nst + 1) * (Nbx + 1) * (Nby + 1)][6][6][3];
        koorNodeJointLoadFix = new float[(Nst + 1) * (Nbx + 1) * (Nby + 1)][6][6][3];
        koorDistributedFrameLoad = new float[pointerElemenStruktur.length][6][4 * 7][3];
        koorDistributedFrameLoadFix = new float[pointerElemenStruktur.length][6][4 * 7][3];

        pointerFrameSection = new int[pointerElemenStruktur.length];
        pointerAreaSection = new int[Nbx * Nby * Nst];

        //Set Boolean Initial
        isStartProject = true;
        isPointerNodeSelected = new boolean[(Nst + 1) * (Nbx + 1) * (Nby + 1)];
        isPointerElementSelected = new boolean[pointerElemenBalokXZ.length + pointerElemenBalokYZ.length + pointerElemenKolom.length];
        isPointerAreaSelected = new boolean[Nbx * Nby * Nst];
        isJointLoadExist = new boolean[(Nst + 1) * (Nbx + 1) * (Nby + 1)][6];
        isFrameLoadExist = new boolean[pointerElemenStruktur.length][3];
        isAreaLoadExist = new boolean[Nbx * Nby * Nst];

        isNodeSelectedFAB = false;
        isElementSelectedFAB = false;
        isAreaSelectedFAB = false;
        isShowNodal = false;
        isShowElement = false;
        isShowInternalForceValue = false;
        isShowDisplacementValue = false;
        isDrawDisplacement = false;
        isAlreadyRunning = false;
        isRotateActive = true;
        isPerspektif = true;
        isTimerSleep = true;
        isLockOpen = true;

        isDefineFrameSectionNow = true;
        isRecyclerViewForDefine = true;
        isAssignFrameSectionNow = true;
        isDrawLoadCurrent = false;
        //=========================================================================================

        tandaViewBottomNav = 1;
        setPaint();
        setCanvasBitmap();
        setArrowOnClickListener();
        setFloatingActionInitial();
        drawLoadCurrent = 0;
        drawInternalForceCurrent = 0;

        drawFrame3D();
    }

    private void setCreateAnalysis() {
        setLoading(false, "Generating...");
        setTitle("Project: " + projectKode);
        setPenggambaranAwal();

        listFrameSection = new ArrayList<>();
        setListSteelProfile();
        setDefaultDefineSection();

        dbHelperProject = new G_DBHelper_Project(getApplicationContext());
        dbProject = dbHelperProject.getReadableDatabase();

        cursorProject = dbHelperProject.readFrameSection(dbProject);
        dbHelperProject.addFrameSection(dbProject);
        cursorProject.close();

        cursorProject = dbHelperProject.readAssignSection(dbProject);
        dbHelperProject.addAssignSection(dbProject);
        cursorProject.close();

        dbProject.close();
        dbHelperProject.close();
    }

    private void setCreateDesign() {
        setOpenAnalysis();

        //set list
        listDesignBeamXSect = new ArrayList<>();
        listDesignBeamYSect = new ArrayList<>();
        listDesignColSect = new ArrayList<>();
        listDesignBeamXSect2 = new ArrayList<>();
        listDesignBeamYSect2 = new ArrayList<>();
        listDesignColSect2 = new ArrayList<>();
        listDesignBeamXSect2R = new ArrayList<>();
        listDesignBeamYSect2R = new ArrayList<>();
        listDesignColSect2R = new ArrayList<>();
        setListSteelProfile();
    }

    public void setOpenAnalysis() {
        //mengatur project
        dbHelperProject = new G_DBHelper_Project(getApplicationContext());
        dbProject = dbHelperProject.getReadableDatabase();
        cursorProject = dbHelperProject.readMainProject(dbProject);
        if(cursorProject != null && cursorProject.moveToPosition(projectId)){
            projectKode = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_PROJECT));
            projectName = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_PROJECT));
            projectEngineer = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ENGINEER));
            cursorProject.close();
        }
        setTitle("Project: " + projectKode);

        //mengatur analysis
        String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_ANALYSIS +  " WHERE " +
                F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + projectId + " AND " +
                F_ProjectContract.ProjectEntry.ID_ANALYSIS + " = " + analysisId;
        cursorProject = dbProject.rawQuery(sql, null);
        cursorProject.moveToFirst();
        if (cursorProject !=null && cursorProject.moveToFirst()) {
            analysisKode = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_ANALYSIS));
            analysisName = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_ANALYSIS));
            Nst = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NST));
            Nbx = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NBX));
            Nby = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NBY));
            Sth = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.STH));
            Bwx = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.BWX));
            Bwy = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.BWY));
            String sendi = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.SENDI));
            bajaFy = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.FY));
            bajaFu = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.FU));
            bajaE = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.E));
            cursorProject.close();
            isRestraintPin = sendi.equals("1");
        }

        //penggambaran
        setPenggambaranAwal();

        //Joint Load
        cursorProject = dbHelperProject.readJointLoad(dbProject);
        cursorProject.moveToFirst();
        assert cursorProject != null;
        while(cursorProject.moveToNext()){
            int ID_PROJECT = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_PROJECT));
            int ID_ANALYSIS = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_ANALYSIS));

            if(ID_PROJECT == projectId && ID_ANALYSIS == analysisId){
                int nodal = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NODAL));
                jointLoad[nodal][0] = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.FGX));
                jointLoad[nodal][1] = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.FGY));
                jointLoad[nodal][2] = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.FGZ));
                jointLoad[nodal][3] = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.MGX));
                jointLoad[nodal][4] = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.MGY));
                jointLoad[nodal][5] = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.MGZ));

                if(jointLoad[nodal][0] != 0){
                    isJointLoadExist[nodal][0] = true;
                }

                if(jointLoad[nodal][1] != 0){
                    isJointLoadExist[nodal][1] = true;
                }

                if(jointLoad[nodal][2] != 0){
                    isJointLoadExist[nodal][2] = true;
                }

                if(jointLoad[nodal][3] != 0){
                    isJointLoadExist[nodal][3] = true;
                }

                if(jointLoad[nodal][4] != 0){
                    isJointLoadExist[nodal][4] = true;
                }

                if(jointLoad[nodal][5] != 0){
                    isJointLoadExist[nodal][5] = true;
                }
            }
        }
        cursorProject.close();

        //Frame Load
        cursorProject = dbHelperProject.readFrameLoad(dbProject);
        assert cursorProject != null;
        while(cursorProject != null && cursorProject.moveToNext()){
            int ID_PROJECT = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_PROJECT));
            int ID_ANALYSIS = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_ANALYSIS));

            if(ID_PROJECT == projectId && ID_ANALYSIS == analysisId){
                int frame = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.FRAME));
                frameLoad[frame][0] = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.LGX));
                frameLoad[frame][1] = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.LGY));
                frameLoad[frame][2] = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.LGZ));
                pointerFrameSection[frame] = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.SECTION));

                if(frameLoad[frame][0] != 0){
                    isFrameLoadExist[frame][0] = true;
                }

                if(frameLoad[frame][1] != 0){
                    isFrameLoadExist[frame][1] = true;
                }

                if(frameLoad[frame][2] != 0){
                    isFrameLoadExist[frame][2] = true;
                }
            }
        }
        cursorProject.close();

        //Area Load
        cursorProject = dbHelperProject.readAreaLoad(dbProject);
        assert cursorProject != null;
        while(cursorProject != null && cursorProject.moveToNext()){
            int ID_PROJECT = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_PROJECT));
            int ID_ANALYSIS = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_ANALYSIS));

            if(ID_PROJECT == projectId && ID_ANALYSIS == analysisId){
                int area = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.AREA));
                areaLoad[area] = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.UAL));
                pointerAreaSection[area] = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.SECTION));

                if(areaLoad[area] != 0){
                    isAreaLoadExist[area] = true;
                }
            }
        }
        cursorProject.close();

        //Frame Section
        listFrameSection = new ArrayList<>();
        setListSteelProfile();
        cursorProject = dbHelperProject.readFrameSection(dbProject);
        assert cursorProject != null;
        while(cursorProject != null && cursorProject.moveToNext()){
            int ID_PROJECT = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_PROJECT));
            int ID_ANALYSIS = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_ANALYSIS));

            if(ID_PROJECT == projectId && ID_ANALYSIS == analysisId){
                sectionCode = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_FRAME_SECTION));
                sectionName = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_FRAME_SECTION));
                steelType = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.TYPE_STEEL));
                steelName = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_STEEL));
                String rotated = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ROTATE_ALPHA));
                isAlphaRotated = rotated.equals("1");

                String tabel = "'Profil" + steelType + "'";
                getProfileData(tabel, steelName);
                listFrameSection.add(new SteelSection(sectionCode, sectionName, steelType, steelName, bajaB,
                        bajaH, bajaTw, bajaTf, bajaR1, bajaR2, bajaA, bajaCy,
                        bajaCz, bajaIy, bajaIz, bajaRy,
                        bajaRz, bajaSy, bajaSz, isAlphaRotated
                ));
            }
        }
        cursorProject.close();

        //Assign Section
        cursorProject = dbHelperProject.readAssignSection(dbProject);
        cursorProject.moveToFirst();
        assert cursorProject != null;
        while(cursorProject != null && cursorProject.moveToNext()) {
            int ID_PROJECT = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_PROJECT));
            int ID_ANALYSIS = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_ANALYSIS));

            if (ID_PROJECT == projectId && ID_ANALYSIS == analysisId) {
                int batang = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NO_FRAME));
                int nomorSection = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NOMOR));
                pointerFrameSection[batang] = nomorSection;
            }
        }
        cursorProject.close();

        //Earthquake
        cursorProject = dbHelperProject.readEarthQuake(dbProject);
        cursorProject.moveToFirst();
        assert cursorProject != null;
        while(cursorProject != null && cursorProject.moveToNext()) {
            int ID_PROJECT = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_PROJECT));
            int ID_ANALYSIS = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.ID_ANALYSIS));

            if (ID_PROJECT == projectId && ID_ANALYSIS == analysisId) {
                gempaSs = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.SS));
                gempaS1 = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.S1));
                gempaIe = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.IE));
                gempaR = cursorProject.getFloat(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.R));
                gempaSitus = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.SITUS));
                int axis = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.AXIS));
                switch(axis){
                    case 0:
                        isBebanGempaX = false;
                        isBebanGempaY = false;
                        break;
                    case 1:
                        isBebanGempaX = true;
                        break;
                    case 2:
                        isBebanGempaY = false;
                        break;
                }
                int gempaXPositif = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.IS_X_POSITIF));
                int gempaYPositif = cursorProject.getInt(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.IS_Y_POSITIF));
                isGempaXPositif = gempaXPositif == 1;
                isGempaYPositif = gempaYPositif == 1;
            }
        }

        cursorProject.close();
        dbProject.close();
        dbHelperProject.close();
    }

    public void setOpenDesign() {
        setOpenAnalysis();

        //baca design
        dbHelperProject = new G_DBHelper_Project(getApplicationContext());
        dbProject = dbHelperProject.getReadableDatabase();
        String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_DESIGN +  " WHERE " +
                F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + projectId + " AND " +
                F_ProjectContract.ProjectEntry.ID_ANALYSIS + " = " + analysisId + " AND " +
                F_ProjectContract.ProjectEntry.ID_DESIGN + " = " + designId;
        cursorProject = dbProject.rawQuery(sql, null);
        cursorProject.moveToFirst();
        if (cursorProject !=null && cursorProject.moveToFirst()) {
            designKode = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.KODE_DESIGN));
            designName = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_DESIGN));
            cursorProject.close();
        }

        //set list (kosongi dulu)
        listDesignBeamXSect2 = new ArrayList<>();
        listDesignBeamYSect2 = new ArrayList<>();
        listDesignColSect2 = new ArrayList<>();
        listDesignBeamXSect2R = new ArrayList<>();
        listDesignBeamYSect2R = new ArrayList<>();
        listDesignColSect2R = new ArrayList<>();
        listDesignBeamXSect = new ArrayList<>();
        listDesignBeamYSect = new ArrayList<>();
        listDesignColSect = new ArrayList<>();
        setListSteelProfile();

        //isi list beam x
        sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_DESIGN_SECTION_BEAMX +  " WHERE " +
                F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + projectId + " AND " +
                F_ProjectContract.ProjectEntry.ID_ANALYSIS + " = " + analysisId + " AND " +
                F_ProjectContract.ProjectEntry.ID_DESIGN + " = " + designId;
        cursorProject = dbProject.rawQuery(sql, null);
        cursorProject.moveToFirst();
        if(cursorProject != null && cursorProject.moveToFirst()){
            do{
                steelName = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_STEEL));
                steelType = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.TYPE_STEEL));
                getProfileData("'Profil" + steelType + "'", steelName);
                listDesignBeamXSect2R.add(new SteelSection("DD",
                        steelName + "*", steelType, steelName, bajaB, bajaH, bajaTw,
                        bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                        bajaRz, bajaSy, bajaSz, true));
                listDesignBeamXSect2.add(new SteelSection("DD",
                        steelName, steelType, steelName, bajaB, bajaH, bajaTw,
                        bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                        bajaRz, bajaSy, bajaSz, false));
            } while (cursorProject.moveToNext());
            cursorProject.close();
        }
        for(int i=0;i<listDesignBeamXSect2.size(); i++){
            listDesignBeamXSect.add(listDesignBeamXSect2.get(i).getSectionName());
        }
        isBeamXChosen = !listDesignBeamXSect.isEmpty();

        //isi list beam y
        sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_DESIGN_SECTION_BEAMY +  " WHERE " +
                F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + projectId + " AND " +
                F_ProjectContract.ProjectEntry.ID_ANALYSIS + " = " + analysisId + " AND " +
                F_ProjectContract.ProjectEntry.ID_DESIGN + " = " + designId;
        cursorProject = dbProject.rawQuery(sql, null);
        cursorProject.moveToFirst();
        if(cursorProject != null && cursorProject.moveToFirst()){
            do{
                steelName = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_STEEL));
                steelType = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.TYPE_STEEL));
                getProfileData("'Profil" + steelType + "'", steelName);
                listDesignBeamYSect2R.add(new SteelSection("DD",
                        steelName + "*", steelType, steelName, bajaB, bajaH, bajaTw,
                        bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                        bajaRz, bajaSy, bajaSz, true));
                listDesignBeamYSect2.add(new SteelSection("DD",
                        steelName, steelType, steelName, bajaB, bajaH, bajaTw,
                        bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                        bajaRz, bajaSy, bajaSz, false));
            } while (cursorProject.moveToNext());
            cursorProject.close();
        }
        for(int i=0;i<listDesignBeamYSect2.size(); i++){
            listDesignBeamYSect.add(listDesignBeamYSect2.get(i).getSectionName());
        }
        isBeamYChosen = !listDesignBeamYSect.isEmpty();

        //isi list column
        sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_DESIGN_SECTION_COLUMN +  " WHERE " +
                F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + projectId + " AND " +
                F_ProjectContract.ProjectEntry.ID_ANALYSIS + " = " + analysisId + " AND " +
                F_ProjectContract.ProjectEntry.ID_DESIGN + " = " + designId;
        cursorProject = dbProject.rawQuery(sql, null);
        cursorProject.moveToFirst();
        if(cursorProject != null && cursorProject.moveToFirst()){
            do{
                steelName = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_STEEL));
                steelType = cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.TYPE_STEEL));
                getProfileData("'Profil" + steelType + "'", steelName);
                listDesignColSect2R.add(new SteelSection("DD",
                        steelName + "*", steelType, steelName, bajaB, bajaH, bajaTw,
                        bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                        bajaRz, bajaSy, bajaSz, true));
                listDesignColSect2.add(new SteelSection("DD",
                        steelName, steelType, steelName, bajaB, bajaH, bajaTw,
                        bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                        bajaRz, bajaSy, bajaSz, false));
            } while (cursorProject.moveToNext());
            cursorProject.close();
        }
        for(int i=0;i<listDesignColSect2.size(); i++){
            listDesignColSect.add(listDesignColSect2.get(i).getSectionName());
        }
        isColumnChosen = !listDesignColSect.isEmpty();

        dbProject.close();
        dbHelperProject.close();
    }

    private void saveProject() {
        dbHelperProject = new G_DBHelper_Project(getApplicationContext());
        dbProject = dbHelperProject.getReadableDatabase();
        cursorProject = dbHelperProject.readMainProject(dbProject);

        //save project
        if(isAnalysis) {
            if (isAnalysisNew) {
                dbHelperProject.addAnalysisData(dbProject);
                isProjectNew = false;
                isAnalysisNew = false;
            } else {
                dbHelperProject.updateAnalysisData(dbProject);
            }
        }else {
            if (isDesignNew) {
                dbHelperProject.addDesignData(dbProject);
                isDesignNew = false;
            } else {
                dbHelperProject.updateDesignData(dbProject);
            }
        }

        cursorProject.close();
        dbProject.close();
        dbHelperProject.close();
    }

    private void exportAnalysis() throws IOException, DocumentException {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            exportReportPDF = new H_ExportReportPDF(getApplicationContext());
            exportReportPDF.createAnalysisReport();
            setLoading(false, "export");
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(D_MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(D_MainActivity.this)
                    .setMessage("Permission is needed to perform export feature.")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            ActivityCompat.requestPermissions(D_MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    private void exportDesign() throws IOException, DocumentException {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            exportReportPDF = new H_ExportReportPDF(getApplicationContext());
            exportReportPDF.createDesignReport();
            setLoading(false, "export");
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(D_MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(D_MainActivity.this)
                    .setMessage("Permission is needed to perform export feature.")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            ActivityCompat.requestPermissions(D_MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                //If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(isExportAnalysis) {
                        try {
                            exportAnalysis();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            exportDesign();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Grant the permission to use export feature.", Toast.LENGTH_LONG).show();
                }
            break;
        }
    }

    //----------MENAMPILKAN DIALOG DARI FAB----------//
    private void showJointLoadDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_11jointload, null);
        mBuilder.setView(mView);
        dialogJointLoad = mBuilder.create();
        dialogJointLoad.show();
        Objects.requireNonNull(dialogJointLoad.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //baca objek
        final EditText txtForceX = mView.findViewById(R.id.txtForceX);
        final EditText txtForceY = mView.findViewById(R.id.txtForceY);
        final EditText txtForceZ = mView.findViewById(R.id.txtForceZ);
        final EditText txtMomentX = mView.findViewById(R.id.txtMomentX);
        final EditText txtMomentY = mView.findViewById(R.id.txtMomentY);
        final EditText txtMomentZ = mView.findViewById(R.id.txtMomentZ);
        Button btnOK = mView.findViewById(R.id.btnOKJointLoad);
        Button btnCancel = mView.findViewById(R.id.btnCancelJointLoad);
        final RadioGroup rgJointLoad = mView.findViewById(R.id.rgJointLoad);
        final RadioButton rbtnAdd = mView.findViewById(R.id.rbtnAddJointLoad);
        final RadioButton rbtnReplace = mView.findViewById(R.id.rbtnReplaceJointLoad);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected_id = rgJointLoad.getCheckedRadioButtonId();

                //ADD
                if (selected_id == rbtnAdd.getId()) {
                    //FX ADD
                    if (txtForceX.getText().toString().trim().equals("") ||
                            txtForceX.getText().toString().trim().equals("0")) {

                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][0] = jointLoad[x][0] + Float.parseFloat(txtForceX.getText().toString().trim());
                                isJointLoadExist[x][0] = true;
                            }
                            x++;
                        }
                    }

                    //FY ADD
                    if (txtForceY.getText().toString().trim().equals("") ||
                            txtForceY.getText().toString().trim().equals("0")) {

                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][1] = jointLoad[x][1] + Float.parseFloat(txtForceY.getText().toString().trim());
                                isJointLoadExist[x][1] = true;
                            }
                            x++;
                        }
                    }

                    //FZ ADD
                    if (txtForceZ.getText().toString().trim().equals("") ||
                            txtForceZ.getText().toString().trim().equals("0")) {

                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][2] = jointLoad[x][2] + Float.parseFloat(txtForceZ.getText().toString().trim());
                                isJointLoadExist[x][2] = true;
                            }
                            x++;
                        }
                    }

                    //MX ADD
                    if (txtMomentX.getText().toString().trim().equals("") ||
                            txtMomentX.getText().toString().trim().equals("0")) {

                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][3] = jointLoad[x][3] + Float.parseFloat(txtMomentX.getText().toString().trim());
                                isJointLoadExist[x][3] = true;
                            }
                            x++;
                        }
                    }

                    //MY ADD
                    if (txtMomentY.getText().toString().trim().equals("") ||
                            txtMomentY.getText().toString().trim().equals("0")) {

                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][4] = jointLoad[x][4] + Float.parseFloat(txtMomentY.getText().toString().trim());
                                isJointLoadExist[x][4] = true;
                            }
                            x++;
                        }
                    }

                    //MZ ADD
                    if (txtMomentZ.getText().toString().trim().equals("") ||
                            txtMomentZ.getText().toString().trim().equals("0")) {

                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][5] = jointLoad[x][5] + Float.parseFloat(txtMomentZ.getText().toString().trim());
                                isJointLoadExist[x][5] = true;
                            }
                            x++;
                        }
                    }
                    //REPLACE
                } else if (selected_id == rbtnReplace.getId()) {
                    //FX REPLACE
                    if (txtForceX.getText().toString().trim().equals("") ||
                            txtForceX.getText().toString().trim().equals("0")) {

                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][0] = 0f;
                                isJointLoadExist[x][0] = false;
                            }
                            x++;
                        }
                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][0] = Float.parseFloat(txtForceX.getText().toString().trim());
                                isJointLoadExist[x][0] = true;
                            }
                            x++;
                        }
                    }

                    //FY REPLACE
                    if (txtForceY.getText().toString().trim().equals("") ||
                            txtForceY.getText().toString().trim().equals("0")) {

                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][1] = 0f;
                                isJointLoadExist[x][1] = false;
                            }
                            x++;
                        }
                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][1] = Float.parseFloat(txtForceY.getText().toString().trim());
                                isJointLoadExist[x][1] = true;
                            }
                            x++;
                        }
                    }

                    //FZ REPLACE
                    if (txtForceZ.getText().toString().trim().equals("") ||
                            txtForceZ.getText().toString().trim().equals("0")) {

                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][2] = 0f;
                                isJointLoadExist[x][2] = false;
                            }
                            x++;
                        }
                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][2] = Float.parseFloat(txtForceZ.getText().toString().trim());
                                isJointLoadExist[x][2] = true;
                            }
                            x++;
                        }
                    }

                    //MX REPLACE
                    if (txtMomentX.getText().toString().trim().equals("") ||
                            txtMomentX.getText().toString().trim().equals("0")) {

                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][3] = 0f;
                                isJointLoadExist[x][3] = false;
                            }
                            x++;
                        }
                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][3] = Float.parseFloat(txtMomentX.getText().toString().trim());
                                isJointLoadExist[x][3] = true;
                            }
                            x++;
                        }
                    }

                    //MY REPLACE
                    if (txtMomentY.getText().toString().trim().equals("") ||
                            txtMomentY.getText().toString().trim().equals("0")) {

                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][4] = 0f;
                                isJointLoadExist[x][4] = false;
                            }
                            x++;
                        }
                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][4] = Float.parseFloat(txtMomentY.getText().toString().trim());
                                isJointLoadExist[x][4] = true;
                            }
                            x++;
                        }
                    }

                    //MZ REPLACE
                    if (txtMomentZ.getText().toString().trim().equals("") ||
                            txtMomentZ.getText().toString().trim().equals("0")) {

                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][5] = 0f;
                                isJointLoadExist[x][5] = false;
                            }
                            x++;
                        }
                    } else {
                        int x = 0;
                        for (boolean i : isPointerNodeSelected) {
                            if (i) {
                                jointLoad[x][5] = Float.parseFloat(txtMomentZ.getText().toString().trim());
                                isJointLoadExist[x][5] = true;
                            }
                            x++;
                        }
                    }

                    //DELETE
                } else {
                    int x = 0;
                    for (boolean i : isPointerNodeSelected) {
                        if (i) {
                            for (int j = 0; j < 6; j++) {
                                jointLoad[x][j] = 0f;
                                isJointLoadExist[x][j] = false;
                            }
                        }
                        x++;
                    }
                }
                isTimerSleep = true;
                setDeselectAll();
                setKoorToDrawLoad();
                isDrawLoadCurrent = true;
                drawLoadCurrent = 1;
                drawGeneral();

                dialogJointLoad.dismiss();
                Toast.makeText(getApplicationContext(), "Joint load updated.", Toast.LENGTH_SHORT).show();
                fabMenu.close(true);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogJointLoad.dismiss();
                fabMenu.close(true);
            }
        });
    }

    private void showFrameLoadDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_12frameload, null);
        mBuilder.setView(mView);
        dialogFrameLoad = mBuilder.create();
        dialogFrameLoad.show();
        dialogFrameLoad.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //baca objek
        final EditText txtLoadX = mView.findViewById(R.id.txtLoadX);
        final EditText txtLoadY = mView.findViewById(R.id.txtLoadY);
        final EditText txtLoadZ = mView.findViewById(R.id.txtLoadZ);
        Button btnOK = mView.findViewById(R.id.btnOKFrameLoad);
        Button btnCancel = mView.findViewById(R.id.btnCancelFrameLoad);
        final RadioGroup rgFrameLoad = mView.findViewById(R.id.rgFrameLoad);
        final RadioButton rbtnAdd = mView.findViewById(R.id.rbtnAddFrameLoad);
        final RadioButton rbtnReplace = mView.findViewById(R.id.rbtnReplaceFrameLoad);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected_id = rgFrameLoad.getCheckedRadioButtonId();

                //ADD
                if (selected_id == rbtnAdd.getId()) {

                    //LGX ADD
                    if (txtLoadX.getText().toString().trim().equals("") ||
                            txtLoadX.getText().toString().trim().equals("0")) {

                    } else {
                        int x = 0;
                        for (boolean i : isPointerElementSelected) {
                            if (i) {
                                frameLoad[x][0] = frameLoad[x][0] + Float.parseFloat(txtLoadX.getText().toString().trim());
                                isFrameLoadExist[x][0] = true;
                            }
                            x++;
                        }
                    }

                    //LGY ADD
                    if (txtLoadY.getText().toString().trim().equals("") ||
                            txtLoadY.getText().toString().trim().equals("0")) {

                    } else {
                        int x = 0;
                        for (boolean i : isPointerElementSelected) {
                            if (i) {
                                frameLoad[x][1] = frameLoad[x][1] + Float.parseFloat(txtLoadY.getText().toString().trim());
                                isFrameLoadExist[x][1] = true;
                            }
                            x++;
                        }
                    }

                    //LGZ ADD
                    if (txtLoadZ.getText().toString().trim().equals("") ||
                            txtLoadZ.getText().toString().trim().equals("0")) {

                    } else {
                        int x = 0;
                        for (boolean i : isPointerElementSelected) {
                            if (i) {
                                frameLoad[x][2] = frameLoad[x][2] + Float.parseFloat(txtLoadZ.getText().toString().trim());
                                isFrameLoadExist[x][2] = true;
                            }
                            x++;
                        }
                    }

                    //REPLACE
                } else if (selected_id == rbtnReplace.getId()) {

                    //LGX REPLACE
                    if (txtLoadX.getText().toString().trim().equals("") ||
                            txtLoadX.getText().toString().trim().equals("0")) {

                        int x = 0;
                        for (boolean i : isPointerElementSelected) {
                            if (i) {
                                frameLoad[x][0] = 0f;
                                isFrameLoadExist[x][0] = false;
                            }
                            x++;
                        }
                    } else {
                        int x = 0;
                        for (boolean i : isPointerElementSelected) {
                            if (i) {
                                frameLoad[x][0] = Float.parseFloat(txtLoadX.getText().toString().trim());
                                isFrameLoadExist[x][0] = true;
                            }
                            x++;
                        }
                    }

                    //LGY REPLACE
                    if (txtLoadY.getText().toString().trim().equals("") ||
                            txtLoadY.getText().toString().trim().equals("0")) {

                        int x = 0;
                        for (boolean i : isPointerElementSelected) {
                            if (i) {
                                frameLoad[x][1] = 0f;
                                isFrameLoadExist[x][1] = false;
                            }
                            x++;
                        }
                    } else {
                        int x = 0;
                        for (boolean i : isPointerElementSelected) {
                            if (i) {
                                frameLoad[x][1] = Float.parseFloat(txtLoadY.getText().toString().trim());
                                isFrameLoadExist[x][1] = true;
                            }
                            x++;
                        }
                    }

                    //LGZ REPLACE
                    if (txtLoadZ.getText().toString().trim().equals("") ||
                            txtLoadZ.getText().toString().trim().equals("0")) {

                        int x = 0;
                        for (boolean i : isPointerElementSelected) {
                            if (i) {
                                frameLoad[x][2] = 0f;
                                isFrameLoadExist[x][2] = false;
                            }
                            x++;
                        }
                    } else {
                        int x = 0;
                        for (boolean i : isPointerElementSelected) {
                            if (i) {
                                frameLoad[x][2] = Float.parseFloat(txtLoadZ.getText().toString().trim());
                                isFrameLoadExist[x][2] = true;
                            }
                            x++;
                        }
                    }

                    //DELETE
                } else {
                    int x = 0;
                    for (boolean i : isPointerElementSelected) {
                        if (i) {
                            for (int j = 0; j < 3; j++) {
                                frameLoad[x][j] = 0f;
                                isFrameLoadExist[x][j] = false;
                            }
                        }
                        x++;
                    }
                }
                isTimerSleep = true;
                setDeselectAll();
                setKoorToDrawLoad();
                isDrawLoadCurrent = true;
                drawLoadCurrent = 2;
                drawGeneral();

                dialogFrameLoad.dismiss();
                fabMenu.close(true);
                Toast.makeText(getApplicationContext(), "Frame load is updated.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFrameLoad.dismiss();
                fabMenu.close(true);
                keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }

    private void showAreaLoadDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_13areaload, null);
        mBuilder.setView(mView);
        dialogAreaLoad = mBuilder.create();
        dialogAreaLoad.show();
        dialogAreaLoad.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //baca objek
        final EditText txtAreaLoad = mView.findViewById(R.id.txtAreaLoad);
        Button btnOK = mView.findViewById(R.id.btnOKAreaLoad);
        Button btnCancel = mView.findViewById(R.id.btnCancelAreaLoad);
        final RadioGroup rgAreaLoad = mView.findViewById(R.id.rgAreaLoad);
        final RadioButton rbtnAdd = mView.findViewById(R.id.rbtnAddAreaLoad);
        final RadioButton rbtnReplace = mView.findViewById(R.id.rbtnReplaceAreaLoad);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected_id = rgAreaLoad.getCheckedRadioButtonId();

                //ADD
                if (selected_id == rbtnAdd.getId()) {

                    if (txtAreaLoad.getText().toString().trim().equals("") ||
                            txtAreaLoad.getText().toString().trim().equals("0")) {

                    } else {
                        int x = 0;
                        for (boolean i : isPointerAreaSelected) {
                            if (i) {
                                areaLoad[x] = areaLoad[x] + Float.parseFloat(txtAreaLoad.getText().toString().trim());
                                isAreaLoadExist[x] = true;
                            }
                            x++;
                        }
                    }

                    //REPLACE
                } else if (selected_id == rbtnReplace.getId()) {

                    if (txtAreaLoad.getText().toString().trim().equals("") ||
                            txtAreaLoad.getText().toString().trim().equals("0")) {

                        int x = 0;
                        for (boolean i : isPointerAreaSelected) {
                            if (i) {
                                areaLoad[x] = 0f;
                                isAreaLoadExist[x] = false;
                            }
                            x++;
                        }
                    } else {
                        int x = 0;
                        for (boolean i : isPointerAreaSelected) {
                            if (i) {
                                areaLoad[x] = Float.parseFloat(txtAreaLoad.getText().toString().trim());
                                isAreaLoadExist[x] = true;
                            }
                            x++;
                        }
                    }

                    //DELETE
                } else {
                    int x = 0;
                    for (boolean i : isPointerAreaSelected) {
                        if (i) {
                            areaLoad[x] = 0f;
                            isAreaLoadExist[x] = false;
                        }
                        x++;
                    }
                }
                isTimerSleep = true;
                setDeselectAll();
                setKoorToDrawLoad();
                isDrawLoadCurrent = true;
                drawLoadCurrent = 3;
                drawGeneral();

                dialogAreaLoad.dismiss();
                fabMenu.close(true);
                Toast.makeText(getApplicationContext(), "Area load is updated.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAreaLoad.dismiss();
                fabMenu.close(true);
            }
        });
    }

    private void showFrameSectionInitial() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_14framesection_1initial, null);
        mBuilder.setView(mView);
        dialogFrameSectionInitial = mBuilder.create();
        dialogFrameSectionInitial.show();

        //baca objek
        Button btnDefine = mView.findViewById(R.id.btnDefineFrameSection);
        Button btnAssign = mView.findViewById(R.id.btnAssignFrameSection);
        Button btnCancel = mView.findViewById(R.id.btnCancelFrameInitial);

        btnDefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrameSectionList();
                isRecyclerViewForDefine = true;
                dialogFrameSectionInitial.dismiss();
            }
        });

        btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isElementSelectedFAB) {
                    showFrameSectionList();
                    isRecyclerViewForDefine = false;
                    isAssignFrameSectionNow = true;
                    dialogFrameSectionInitial.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(),"Select the element first!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFrameSectionInitial.dismiss();
                fabMenu.close(true);
                setDeselectAll();
            }
        });
    }

    private void showFrameSectionList() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_14framesection_2list, null);
        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialogFrameSectionList = mBuilder.create();
        dialogFrameSectionList.show();

        RecyclerViewAdapter_4FrameSection recyclerViewAdapter =
                new RecyclerViewAdapter_4FrameSection(getBaseContext(), listFrameSection);
        RecyclerView recyclerView = mView.findViewById(R.id.recyclerFrameSection);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

        //baca objek
        Button btnCancel = mView.findViewById(R.id.btnCancelFSList);
        ImageButton imgAdd = mView.findViewById(R.id.imgAddFrameSection);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrameSectionAdd();
                dialogFrameSectionList.dismiss();
            }
        });

        //cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFrameSectionList.dismiss();
                fabMenu.close(true);
            }
        });
    }

    private void showFrameSectionAdd() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_14framesection_3add, null);
        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialogFrameSectionAdd = mBuilder.create();
        dialogFrameSectionAdd.show();
        isAlphaRotated=false;

        //baca objek
        Button btnOK = mView.findViewById(R.id.btnOKFS);
        Button btnCancel = mView.findViewById(R.id.btnCancelFS);
        final EditText txtSectionCode = mView.findViewById(R.id.txtSectionCode);
        final EditText txtSectionName = mView.findViewById(R.id.txtSectionName);
        final TextView txtB = mView.findViewById(R.id.txtB);
        final TextView txtH = mView.findViewById(R.id.txtH);
        final TextView txtT1 = mView.findViewById(R.id.txtT1);
        final TextView txtT2 = mView.findViewById(R.id.txtT2);
        final Spinner spinProfile = mView.findViewById(R.id.spinProfile);
        final Spinner spinType = mView.findViewById(R.id.spinType);
        final ImageView helpAlpha = mView.findViewById(R.id.helpAlpha);
        final CheckBox chkAlpha = mView.findViewById(R.id.chkAlpha);

        //data spin
        ArrayList<String> profile = new ArrayList<>(3);
        profile.add("WF Profile");
        profile.add("C Profile");
        profile.add("I Profile");
        profile.add("L Profile");
        spinProfile.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, profile));
        setListSteelProfile();

        //tampilkan data spinType
        spinProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        steelType = "WF";
                        spinType.setAdapter(new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_dropdown_item, WFtype));
                        break;
                    case 1:
                        steelType = "C";
                        spinType.setAdapter(new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_dropdown_item, Ctype));
                        break;
                    case 2:
                        steelType = "I";
                        spinType.setAdapter(new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_dropdown_item, Itype));
                        break;
                    case 3:
                        steelType = "L";
                        spinType.setAdapter(new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_dropdown_item, Ltype));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> _param1) {
            }
        });

        helpAlpha.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(D_MainActivity.this)
                        .setMessage("For column, major axis will be oriented to global y axis. For beam, major axis will align horizontally")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spinProfile.getSelectedItemPosition()) {
                    case 0:
                        getProfileData("'ProfilWF'", position);
                        break;
                    case 1:
                        getProfileData("'ProfilC'", position);
                        break;
                    case 2:
                        getProfileData("'ProfilI'", position);
                        break;
                    case 3:
                        getProfileData("'ProfilL'", position);
                        break;
                }
                txtB.setText(String.valueOf(bajaB));
                txtH.setText(String.valueOf(bajaH));
                txtT1.setText(String.valueOf(bajaTw));
                txtT2.setText(String.valueOf(bajaTf));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionCode = txtSectionCode.getText().toString().trim();
                sectionName = txtSectionName.getText().toString().trim();
                isAlphaRotated = chkAlpha.isChecked();

                listFrameSection.add(new SteelSection(sectionCode, sectionName, steelType, steelName, bajaB,
                        bajaH, bajaTw, bajaTf, bajaR1, bajaR2, bajaA, bajaCy,
                        bajaCz, bajaIy, bajaIz, bajaRy,
                        bajaRz, bajaSy, bajaSz, isAlphaRotated));

                dialogFrameSectionAdd.dismiss();
                fabMenu.close(true);
                showFrameSectionList();
                Toast.makeText(getApplicationContext(), "Frame section is updated.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFrameSectionAdd.dismiss();
                fabMenu.close(true);
                showFrameSectionList();
            }
        });
    }

    public void showFrameSectionEdit(View view, final int posisi, final TextView card_code, final TextView card_name) {
        dialogFrameSectionEdit = new Dialog(view.getContext());
        dialogFrameSectionEdit.setContentView(R.layout.dialog_14framesection_3add);
        dialogFrameSectionEdit.show();

        //baca objek
        Button btnOK = dialogFrameSectionEdit.findViewById(R.id.btnOKFS);
        Button btnCancel = dialogFrameSectionEdit.findViewById(R.id.btnCancelFS);
        final EditText txtSectionCode = dialogFrameSectionEdit.findViewById(R.id.txtSectionCode);
        final EditText txtSectionName = dialogFrameSectionEdit.findViewById(R.id.txtSectionName);
        final TextView txtB = dialogFrameSectionEdit.findViewById(R.id.txtB);
        final TextView txtH = dialogFrameSectionEdit.findViewById(R.id.txtH);
        final TextView txtT1 = dialogFrameSectionEdit.findViewById(R.id.txtT1);
        final TextView txtT2 = dialogFrameSectionEdit.findViewById(R.id.txtT2);
        final Spinner spinProfile = dialogFrameSectionEdit.findViewById(R.id.spinProfile);
        final Spinner spinType = dialogFrameSectionEdit.findViewById(R.id.spinType);
        final ImageView helpAlpha = dialogFrameSectionEdit.findViewById(R.id.helpAlpha);
        final CheckBox chkAlpha = dialogFrameSectionEdit.findViewById(R.id.chkAlpha);

        //data spin
        ArrayList<String> profile = new ArrayList<>();
        profile.add("WF Profile");
        profile.add("C Profile");
        profile.add("I Profile");
        profile.add("L Profile");
        spinProfile.setAdapter(new ArrayAdapter<>(baseContext, android.R.layout.simple_spinner_dropdown_item, profile));

        //menampilkan kondisi terkini
        txtSectionCode.setText(listFrameSection.get(posisi).getSectionCode());
        txtSectionName.setText(listFrameSection.get(posisi).getSectionName());
        chkAlpha.setChecked(listFrameSection.get(posisi).isAlphaRotated());

        //menampilkan steelType-spinProfile terkini
        steelType = listFrameSection.get(posisi).getSteelType();
        switch (steelType){
            case "WF":
                spinProfile.setSelection(0);
                spinType.setAdapter(new ArrayAdapter<>(baseContext,
                        android.R.layout.simple_spinner_dropdown_item, WFtype));
                break;
            case "C":
                spinProfile.setSelection(1);
                spinType.setAdapter(new ArrayAdapter<>(baseContext,
                        android.R.layout.simple_spinner_dropdown_item, Ctype));
                break;
            case "I":
                spinProfile.setSelection(2);
                spinType.setAdapter(new ArrayAdapter<>(baseContext,
                        android.R.layout.simple_spinner_dropdown_item, Itype));
                break;
            case "L":
                spinProfile.setSelection(3);
                spinType.setAdapter(new ArrayAdapter<>(baseContext,
                        android.R.layout.simple_spinner_dropdown_item, Ltype));
                break;
        }

        //menampilkan steelName terkini
        steelName = listFrameSection.get(posisi).getSteelName();
        getProfileData("'Profil" + steelType + "'", steelName);
        spinType.setSelection(bajaNo-1);

        //tampilkan data spinType
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                spinProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                steelType = "WF";
                                spinType.setAdapter(new ArrayAdapter<>(baseContext,
                                        android.R.layout.simple_spinner_dropdown_item, WFtype));
                                break;
                            case 1:
                                steelType = "C";
                                spinType.setAdapter(new ArrayAdapter<>(baseContext,
                                        android.R.layout.simple_spinner_dropdown_item, Ctype));
                                break;
                            case 2:
                                steelType = "I";
                                spinType.setAdapter(new ArrayAdapter<>(baseContext,
                                        android.R.layout.simple_spinner_dropdown_item, Itype));
                                break;
                            case 3:
                                steelType = "L";
                                spinType.setAdapter(new ArrayAdapter<>(baseContext,
                                        android.R.layout.simple_spinner_dropdown_item, Ltype));
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> _param1) {
                    }
                });
            }
        }, 100);

        helpAlpha.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(thisContext)
                        .setMessage("For column, major axis will be oriented to global y axis. For beam, major axis will align horizontally")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spinProfile.getSelectedItemPosition()) {
                    case 0:
                        getProfileData("'ProfilWF'", position);
                        break;
                    case 1:
                        getProfileData("'ProfilC'", position);
                        break;
                    case 2:
                        getProfileData("'ProfilI'", position);
                        break;
                    case 3:
                        getProfileData("'ProfilL'", position);
                        break;
                }
                txtB.setText(String.valueOf(bajaB));
                txtH.setText(String.valueOf(bajaH));
                txtT1.setText(String.valueOf(bajaTw));
                txtT2.setText(String.valueOf(bajaTf));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionCode = txtSectionCode.getText().toString().trim();
                sectionName = txtSectionName.getText().toString().trim();
                isAlphaRotated = chkAlpha.isChecked();

                listFrameSection.set(posisi, new SteelSection(sectionCode, sectionName, steelType, steelName, bajaB,
                        bajaH, bajaTw, bajaTf, bajaR1, bajaR2, bajaA, bajaCy,
                        bajaCz, bajaIy, bajaIz, bajaRy,
                        bajaRz, bajaSy, bajaSz, isAlphaRotated));

                card_code.setText(sectionCode);
                card_name.setText(sectionName);

                dbHelperProject = new G_DBHelper_Project(D_MainActivity.appContext);
                dbProject = dbHelperProject.getReadableDatabase();
                dbHelperProject.updateFrameSectionSatuan(dbProject);
                dbProject.close();
                dbHelperProject.close();

                dialogFrameSectionEdit.dismiss();
                fabMenu.close(true);
                Toast.makeText(appContext, "Frame section is updated.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFrameSectionEdit.dismiss();
                fabMenu.close(true);
            }
        });
    }

    private void showEarthQuakeDialog() {
        //menampilkan dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_15earthquake, null);
        mBuilder.setView(mView);
        dialogEarthQuake = mBuilder.create();
        dialogEarthQuake.show();

        //baca objek
        Button btnOK = mView.findViewById(R.id.btnOKEarthQuake);
        Button btnCancel = mView.findViewById(R.id.btnCancelEarthQuake);
        final LinearLayout layoutX = mView.findViewById(R.id.layoutXDirection);
        final LinearLayout layoutY = mView.findViewById(R.id.layoutYDirection);
        final Switch switchX = mView.findViewById(R.id.switchXDirection);
        final Switch switchY = mView.findViewById(R.id.switchYDirection);
        final EditText txtSs = mView.findViewById(R.id.txtSs);
        final EditText txtS1 = mView.findViewById(R.id.txtS1);
        final EditText txtIe = mView.findViewById(R.id.txtIe);
        final EditText txtR = mView.findViewById(R.id.txtR);
        final Spinner spinSitus = mView.findViewById(R.id.spinSitus);
        final RadioGroup rgEarthQuake = mView.findViewById(R.id.rgEarthQuake);
        final RadioButton rbtnNone = mView.findViewById(R.id.rbtnNoneEQ);
        final RadioButton rbtnX = mView.findViewById(R.id.rbtnXEQ);
        final RadioButton rbtnY = mView.findViewById(R.id.rbtnYEQ);

        //mengatur isi spinner situs
        ArrayList<String> jenisSitus = new ArrayList<>(4);
        jenisSitus.clear();
        jenisSitus.add("SA");
        jenisSitus.add("SB");
        jenisSitus.add("SC");
        jenisSitus.add("SD");
        jenisSitus.add("SE");
        spinSitus.setAdapter(new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item, jenisSitus));

        //menampilkan data terkini yang sudah dipilih jika ada
        if(isBebanGempaX){
            rgEarthQuake.check(rbtnX.getId());
            layoutX.setVisibility(View.VISIBLE);
            layoutY.setVisibility(View.VISIBLE);
            txtSs.setEnabled(true);
            txtS1.setEnabled(true);
            txtIe.setEnabled(true);
            txtR.setEnabled(true);
        }else if(isBebanGempaY){
            rgEarthQuake.check(rbtnY.getId());
            layoutX.setVisibility(View.VISIBLE);
            layoutY.setVisibility(View.VISIBLE);
            txtSs.setEnabled(true);
            txtS1.setEnabled(true);
            txtIe.setEnabled(true);
            txtR.setEnabled(true);
        }else{
            rgEarthQuake.check(rbtnNone.getId());
            layoutX.setVisibility(View.GONE);
            layoutY.setVisibility(View.GONE);
            txtSs.setEnabled(false);
            txtS1.setEnabled(false);
            txtIe.setEnabled(false);
            txtR.setEnabled(false);
        }
        txtSs.setText(new DecimalFormat("0.00").format(gempaSs));
        txtS1.setText(new DecimalFormat("0.00").format(gempaS1));
        txtIe.setText(new DecimalFormat("0.00").format(gempaIe));
        txtR.setText(new DecimalFormat("0.00").format(gempaR));
        switch(gempaSitus){
            case "SA":
                spinSitus.setSelection(0);
                break;
            case "SB":
                spinSitus.setSelection(1);
                break;
            case "SC":
                spinSitus.setSelection(2);
                break;
            case "SD":
                spinSitus.setSelection(3);
                break;
            case "SE":
                spinSitus.setSelection(4);
                break;
        }
        switchX.setChecked(isGempaXPositif);
        switchY.setChecked(isGempaYPositif);

        //switch gempa x
        rgEarthQuake.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = rgEarthQuake.getCheckedRadioButtonId();
                if(id==rbtnX.getId()){
                    isBebanGempaX = true;
                    layoutX.setVisibility(View.VISIBLE);
                    layoutY.setVisibility(View.VISIBLE);
                    txtSs.setEnabled(true);
                    txtS1.setEnabled(true);
                    txtIe.setEnabled(true);
                    txtR.setEnabled(true);
                }else if(id==rbtnY.getId()){
                    isBebanGempaY = true;
                    layoutX.setVisibility(View.VISIBLE);
                    layoutY.setVisibility(View.VISIBLE);
                    txtSs.setEnabled(true);
                    txtS1.setEnabled(true);
                    txtIe.setEnabled(true);
                    txtR.setEnabled(true);
                }else if(id==rbtnNone.getId()){
                    isBebanGempaX = false;
                    isBebanGempaY = false;
                    layoutX.setVisibility(View.GONE);
                    layoutY.setVisibility(View.GONE);
                    txtSs.setEnabled(false);
                    txtS1.setEnabled(false);
                    txtIe.setEnabled(false);
                    txtR.setEnabled(false);
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBebanGempaX || isBebanGempaY) {
                    if ((txtSs.getText().toString().trim().equals("") || txtSs.getText().toString().trim().equals("0"))
                            || (txtS1.getText().toString().trim().equals("") || txtS1.getText().toString().trim().equals("0"))
                            || (txtIe.getText().toString().trim().equals("") || txtIe.getText().toString().trim().equals("0"))
                            || (txtR.getText().toString().trim().equals("") || txtR.getText().toString().trim().equals("0"))) {
                        //input tidak lengkap
                        Toast.makeText(getApplicationContext(), "X-Axis input are incomplete!.", Toast.LENGTH_SHORT).show();
                    } else {
                        //input lengkap
                        gempaSs = Double.parseDouble(txtSs.getText().toString().trim());
                        gempaS1 = Double.parseDouble(txtS1.getText().toString().trim());
                        gempaIe = Double.parseDouble(txtIe.getText().toString().trim());
                        gempaR = Double.parseDouble(txtR.getText().toString().trim());
                        gempaSitus = spinSitus.getSelectedItem().toString();
                        isGempaXPositif = switchX.isChecked();
                        isGempaYPositif = switchY.isChecked();

                        dialogEarthQuake.dismiss();
                        fabMenu.close(true);
                        Toast.makeText(getApplicationContext(),"Earthquake is updated.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    gempaSs = 0;
                    gempaS1 = 0;
                    gempaIe = 0;
                    gempaR = 0;
                    gempaSitus = "SA";
                    dialogEarthQuake.dismiss();
                    fabMenu.close(true);
                    Toast.makeText(getApplicationContext(),"Earthquake is updated.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEarthQuake.dismiss();
                fabMenu.close(true);
            }
        });
    }

    private void showColumnSectionDialog() {
        //menampilkan dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_21columnsection, null);
        mBuilder.setView(mView);
        dialogColumnSection = mBuilder.create();
        dialogColumnSection.show();

        //baca objek
        Button btnOK = mView.findViewById(R.id.btnOKColumn);
        final CheckBox chkAll = mView.findViewById(R.id.chkColumn);
        final ListView listViewAll = mView.findViewById(R.id.listViewAllColumn);
        final ListView listViewColumn = mView.findViewById(R.id.listViewColumnSection);

        //menampilkan list
        arrayAdapterAll = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listAllColumnSection);
        listViewAll.setAdapter(arrayAdapterAll);
        arrayAdapterColumn = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDesignColSect);
        listViewColumn.setAdapter(arrayAdapterColumn);

        //memindahkan item dari list All ke list Design
        listViewAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listDesignColSect.add(listViewAll.getItemAtPosition(position).toString());
                arrayAdapterColumn.notifyDataSetChanged();
                listAllColumnSection.remove(position);
                arrayAdapterAll.notifyDataSetChanged();
            }
        });

        //memindahkan item dari list Design ke list All
        listViewColumn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAllColumnSection.add(listViewColumn.getItemAtPosition(position).toString());
                arrayAdapterAll.notifyDataSetChanged();
                listDesignColSect.remove(position);
                arrayAdapterColumn.notifyDataSetChanged();
            }
        });

        //memindahkan semua list kalau use all
        chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkAll.isChecked()){
                    listDesignColSect.clear();
                    for(int i=0;i<listALLCOLUMN.size(); i++){
                        listDesignColSect.add(listALLCOLUMN.get(i));
                        listAllColumnSection.clear();
                    }
                    arrayAdapterAll.notifyDataSetChanged();
                    arrayAdapterColumn.notifyDataSetChanged();
                } else {
                    listAllColumnSection.clear();
                    for(int i=0;i<listALLCOLUMN.size(); i++){
                        listAllColumnSection.add(listALLCOLUMN.get(i));
                        listDesignColSect.clear();
                    }
                    arrayAdapterAll.notifyDataSetChanged();
                    arrayAdapterColumn.notifyDataSetChanged();
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDesignColSect2.clear();
                listDesignColSect2R.clear();
                for (int i = 0; i < listDesignColSect.size(); i++) {
                    String namaProfil = listDesignColSect.get(i);
                    if (namaProfil.contains("WF")) {
                        getProfileData("ProfilWF", namaProfil);
                        steelType = "WF";
                    }
                    if (namaProfil.contains("I")) {
                        getProfileData("ProfilI", namaProfil);
                        steelType = "I";
                    }
                    if (namaProfil.contains("U")) {
                        getProfileData("ProfilC", namaProfil);
                        steelType = "C";
                    }
                    if (namaProfil.contains("L")) {
                        getProfileData("ProfilL", namaProfil);
                        steelType = "L";
                    }
                    listDesignColSect2.add(new SteelSection("DD",
                            steelName, steelType, steelName, bajaB, bajaH, bajaTw,
                            bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                            bajaRz, bajaSy, bajaSz, false));
                    listDesignColSect2R.add(new SteelSection("DD",
                            steelName + "*", steelType, steelName, bajaB, bajaH, bajaTw,
                            bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                            bajaRz, bajaSy, bajaSz, true));
                }

                isColumnChosen = !listDesignColSect2.isEmpty();

                Toast.makeText(getApplicationContext(), "Sections are saved.", Toast.LENGTH_SHORT).show();
                dialogColumnSection.dismiss();
                fabMenu.close(true);
            }
        });
    }

    private void showBeamXSectionDialog() {
        //menampilkan dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_22xbeamsection, null);
        mBuilder.setView(mView);
        dialogXBeamSection = mBuilder.create();
        dialogXBeamSection.show();

        //baca objek
        Button btnOK = mView.findViewById(R.id.btnOKXBeam);
        final CheckBox chkAll = mView.findViewById(R.id.chkBeamX);
        final ListView listViewAll = mView.findViewById(R.id.listViewAllXBeam);
        final ListView listViewBeam = mView.findViewById(R.id.listViewXBeamSection);

        //menampilkan list
        arrayAdapterAll = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listAllXBeamSection);
        listViewAll.setAdapter(arrayAdapterAll);
        arrayAdapterBeam = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDesignBeamXSect);
        listViewBeam.setAdapter(arrayAdapterBeam);

        //memindahkan item dari list All ke list Design
        listViewAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listDesignBeamXSect.add(listViewAll.getItemAtPosition(position).toString());
                arrayAdapterBeam.notifyDataSetChanged();
                listAllXBeamSection.remove(position);
                arrayAdapterAll.notifyDataSetChanged();
            }
        });

        //memindahkan item dari list Design ke list All
        listViewBeam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAllXBeamSection.add(listViewBeam.getItemAtPosition(position).toString());
                arrayAdapterAll.notifyDataSetChanged();
                listDesignBeamXSect.remove(position);
                arrayAdapterBeam.notifyDataSetChanged();
            }
        });

        //memindahkan semua list kalau use all
        chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkAll.isChecked()){
                    listDesignBeamXSect.clear();
                    for(int i=0;i<listALLBEAM.size(); i++){
                        listDesignBeamXSect.add(listALLBEAM.get(i));
                        listAllXBeamSection.clear();
                    }
                    arrayAdapterAll.notifyDataSetChanged();
                    arrayAdapterBeam.notifyDataSetChanged();
                } else {
                    listAllXBeamSection.clear();
                    for(int i=0;i<listALLBEAM.size(); i++){
                        listAllXBeamSection.add(listALLBEAM.get(i));
                        listDesignBeamXSect.clear();
                    }
                    arrayAdapterAll.notifyDataSetChanged();
                    arrayAdapterBeam.notifyDataSetChanged();
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDesignBeamXSect2.clear();
                listDesignBeamXSect2R.clear();
                for (int i = 0; i < listDesignBeamXSect.size(); i++) {
                    String namaProfil = listDesignBeamXSect.get(i);
                    if (namaProfil.contains("WF")) {
                        getProfileData("ProfilWF", namaProfil);
                        steelType = "WF";
                    }
                    if (namaProfil.contains("I")) {
                        getProfileData("ProfilI", namaProfil);
                        steelType = "I";
                    }
                    if (namaProfil.contains("C")) {
                        getProfileData("ProfilC", namaProfil);
                        steelType = "C";
                    }
                    if (namaProfil.contains("L")) {
                        getProfileData("ProfilL", namaProfil);
                        steelType = "L";
                    }
                    listDesignBeamXSect2.add(new SteelSection("DD",
                            steelName, steelType, steelName, bajaB, bajaH, bajaTw,
                            bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                            bajaRz, bajaSy, bajaSz,false));
                    listDesignBeamXSect2R.add(new SteelSection("DD",
                            steelName + "*", steelType, steelName, bajaB, bajaH, bajaTw,
                            bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                            bajaRz, bajaSy, bajaSz,true));
                }

                isBeamXChosen = !listDesignBeamXSect2.isEmpty();

                Toast.makeText(getApplicationContext(), "Sections are saved.", Toast.LENGTH_SHORT).show();
                dialogXBeamSection.dismiss();
                fabMenu.close(true);
            }
        });
    }

    private void showBeamYSectionDialog() {
        //menampilkan dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_23ybeamsection, null);
        mBuilder.setView(mView);
        dialogYBeamSection = mBuilder.create();
        dialogYBeamSection.show();

        //baca objek
        Button btnOK = mView.findViewById(R.id.btnOKYBeam);
        final CheckBox chkAll = mView.findViewById(R.id.chkBeamY);
        final ListView listViewAll = mView.findViewById(R.id.listViewAllYBeam);
        final ListView listViewBeam = mView.findViewById(R.id.listViewYBeamSection);

        //menampilkan list
        arrayAdapterAll = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listAllYBeamSection);
        listViewAll.setAdapter(arrayAdapterAll);
        arrayAdapterBeam = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDesignBeamYSect);
        listViewBeam.setAdapter(arrayAdapterBeam);

        //memindahkan item dari list All ke list Design
        listViewAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listDesignBeamYSect.add(listViewAll.getItemAtPosition(position).toString());
                arrayAdapterBeam.notifyDataSetChanged();
                listAllYBeamSection.remove(position);
                arrayAdapterAll.notifyDataSetChanged();
            }
        });

        //memindahkan item dari list Design ke list All
        listViewBeam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAllYBeamSection.add(listViewBeam.getItemAtPosition(position).toString());
                arrayAdapterAll.notifyDataSetChanged();
                listDesignBeamYSect.remove(position);
                arrayAdapterBeam.notifyDataSetChanged();
            }
        });

        //memindahkan semua list kalau use all
        chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkAll.isChecked()){
                    listDesignBeamYSect.clear();
                    for(int i=0;i<listALLBEAM.size(); i++){
                        listDesignBeamYSect.add(listALLBEAM.get(i));
                        listAllYBeamSection.clear();
                    }
                    arrayAdapterAll.notifyDataSetChanged();
                    arrayAdapterBeam.notifyDataSetChanged();
                } else {
                    listAllYBeamSection.clear();
                    for(int i=0;i<listALLBEAM.size(); i++){
                        listAllYBeamSection.add(listALLBEAM.get(i));
                        listDesignBeamYSect.clear();
                    }
                    arrayAdapterAll.notifyDataSetChanged();
                    arrayAdapterBeam.notifyDataSetChanged();
                }
            }
        });
        
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDesignBeamYSect2.clear();
                listDesignBeamYSect2R.clear();
                for (int i = 0; i < listDesignBeamYSect.size(); i++) {
                    String namaProfil = listDesignBeamYSect.get(i);
                    if (namaProfil.contains("WF")) {
                        getProfileData("ProfilWF", namaProfil);
                        steelType = "WF";
                    }
                    if (namaProfil.contains("I")) {
                        getProfileData("ProfilI", namaProfil);
                        steelType = "I";
                    }
                    if (namaProfil.contains("C")) {
                        getProfileData("ProfilC", namaProfil);
                        steelType = "C";
                    }
                    if (namaProfil.contains("L")) {
                        getProfileData("ProfilL", namaProfil);
                        steelType = "L";
                    }

                    listDesignBeamYSect2.add(new SteelSection("DD",
                            steelName, steelType, steelName, bajaB, bajaH, bajaTw,
                            bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                            bajaRz, bajaSy, bajaSz,false));
                    listDesignBeamYSect2R.add(new SteelSection("DD",
                            steelName + "*", steelType, steelName, bajaB, bajaH, bajaTw,
                            bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy, bajaIz, bajaRy,
                            bajaRz, bajaSy, bajaSz,true));
                }

                isBeamYChosen = !listDesignBeamYSect2.isEmpty();

                Toast.makeText(getApplicationContext(), "Sections are saved.", Toast.LENGTH_SHORT).show();
                dialogYBeamSection.dismiss();
                fabMenu.close(true);
            }
        });
    }

    //----------SET FRAME SECTION----------//
    private void setListSteelProfile() {
        WFtype.clear();
        Ltype.clear();
        Ctype.clear();
        Itype.clear();
        listAllColumnSection.clear();
        listAllXBeamSection.clear();
        listAllYBeamSection.clear();
        listALLCOLUMN.clear();
        listALLBEAM.clear();

        dbHelperSteel = new G_DBHelper_Steel(appContext);
        dbSteel = dbHelperSteel.getReadableDatabase();

        String sql = "SELECT Nama FROM 'ProfilWF'";
        cursor = dbSteel.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                WFtype.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllXBeamSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllYBeamSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllColumnSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listALLBEAM.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listALLCOLUMN.add(cursor.getString(cursor.getColumnIndex("Nama")));
            } while (cursor.moveToNext());
            cursor.close();
        }

        sql = "SELECT Nama FROM 'ProfilL'";
        cursor = dbSteel.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Ltype.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllXBeamSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllYBeamSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllColumnSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listALLBEAM.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listALLCOLUMN.add(cursor.getString(cursor.getColumnIndex("Nama")));
            } while (cursor.moveToNext());
            cursor.close();
        }

        sql = "SELECT Nama FROM 'ProfilC'";
        cursor = dbSteel.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Ctype.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllXBeamSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllYBeamSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllColumnSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listALLBEAM.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listALLCOLUMN.add(cursor.getString(cursor.getColumnIndex("Nama")));
            } while (cursor.moveToNext());
            cursor.close();
        }

        sql = "SELECT Nama FROM 'ProfilI'";
        cursor = dbSteel.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Itype.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllXBeamSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllYBeamSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listAllColumnSection.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listALLBEAM.add(cursor.getString(cursor.getColumnIndex("Nama")));
                listALLCOLUMN.add(cursor.getString(cursor.getColumnIndex("Nama")));
            } while (cursor.moveToNext());
            cursor.close();
        }

        dbSteel.close();
        dbHelperSteel.close();
    }

    private void setDefaultDefineSection() {
        getProfileData("'ProfilWF'", 0);
        listFrameSection.add(new SteelSection("K1", "KOLOM - 1", "WF",
                steelName, bajaB, bajaH, bajaTw, bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy,
                bajaIz, bajaRy, bajaRz, bajaSy, bajaSz, false));
        getProfileData("'ProfilI'", 0);
        listFrameSection.add(new SteelSection("B1", "BALOK - 1", "I",
                steelName, bajaB, bajaH, bajaTw, bajaTf, bajaR1, bajaR2, bajaA, bajaCy, bajaCz, bajaIy,
                bajaIz, bajaRy, bajaRz, bajaSy, bajaSz, false));

        int x = 0;
        for (int[] i : pointerElemenKolom) {
            pointerFrameSection[x] = 0;
            x++;
        }

        for (int[] i : pointerElemenBalokXZ) {
            pointerFrameSection[x] = 1;
            x++;
        }

        for (int[] i : pointerElemenBalokYZ) {
            pointerFrameSection[x] = 1;
            x++;
        }
    }

    private void getProfileData(String tabel, int position) {
        dbHelperSteel = new G_DBHelper_Steel(appContext);
        dbSteel = dbHelperSteel.getReadableDatabase();

        bajaNo = ++position;
        String sql = "SELECT * FROM " + tabel + " WHERE No = " + bajaNo;
        cursor = dbSteel.rawQuery(sql, null);
        cursor.moveToFirst();
        if( cursor != null && cursor.moveToFirst() ) {
            steelName = cursor.getString(cursor.getColumnIndex("Nama"));
            bajaB = cursor.getFloat(cursor.getColumnIndex("B"));
            bajaH = cursor.getFloat(cursor.getColumnIndex("H"));
            bajaTw = cursor.getFloat(cursor.getColumnIndex("t1"));
            bajaTf = cursor.getFloat(cursor.getColumnIndex("t2"));
            bajaR1 = cursor.getFloat(cursor.getColumnIndex("r1"));
            bajaR2 = cursor.getFloat(cursor.getColumnIndex("r2"));
            bajaA = cursor.getFloat(cursor.getColumnIndex("A"));
            bajaCy = cursor.getFloat(cursor.getColumnIndex("TitikBeratX"));
            bajaCz = cursor.getFloat(cursor.getColumnIndex("TitikBeratY"));
            bajaIy = cursor.getFloat(cursor.getColumnIndex("InersiaX"));
            bajaIz = cursor.getFloat(cursor.getColumnIndex("InersiaY"));
            bajaRy = cursor.getFloat(cursor.getColumnIndex("GirasiX"));
            bajaRz = cursor.getFloat(cursor.getColumnIndex("GirasiY"));
            bajaSy = cursor.getFloat(cursor.getColumnIndex("SecModX"));
            bajaSz = cursor.getFloat(cursor.getColumnIndex("SecModY"));
            cursor.close();
        }
        dbSteel.close();
        dbHelperSteel.close();
    }

    private void getProfileData(String tabel, String nama) {
        dbHelperSteel = new G_DBHelper_Steel(appContext);
        dbSteel = dbHelperSteel.getReadableDatabase();

        steelName = nama;
        String sql = "SELECT * FROM " + tabel + " WHERE Nama = '" + steelName + "'";
        cursor = dbSteel.rawQuery(sql, null);
        cursor.moveToFirst();
        if( cursor != null && cursor.moveToFirst() ) {
            bajaNo = cursor.getInt(cursor.getColumnIndex("No"));
            bajaB = cursor.getFloat(cursor.getColumnIndex("B"));
            bajaH = cursor.getFloat(cursor.getColumnIndex("H"));
            bajaTw = cursor.getFloat(cursor.getColumnIndex("t1"));
            bajaTf = cursor.getFloat(cursor.getColumnIndex("t2"));
            bajaR1 = cursor.getFloat(cursor.getColumnIndex("r1"));
            bajaR2 = cursor.getFloat(cursor.getColumnIndex("r2"));
            bajaA = cursor.getFloat(cursor.getColumnIndex("A"));
            bajaCy = cursor.getFloat(cursor.getColumnIndex("TitikBeratX"));
            bajaCz = cursor.getFloat(cursor.getColumnIndex("TitikBeratY"));
            bajaIy = cursor.getFloat(cursor.getColumnIndex("InersiaX"));
            bajaIz = cursor.getFloat(cursor.getColumnIndex("InersiaY"));
            bajaRy = cursor.getFloat(cursor.getColumnIndex("GirasiX"));
            bajaRz = cursor.getFloat(cursor.getColumnIndex("GirasiY"));
            bajaSy = cursor.getFloat(cursor.getColumnIndex("SecModX"));
            bajaSz = cursor.getFloat(cursor.getColumnIndex("SecModY"));
            cursor.close();
        }
        dbSteel.close();
        dbHelperSteel.close();
    }

    //-----------SET RUNNING ANALYSIS----------//
    private void setMainRunning() {
        if(listFrameSection.isEmpty()){
            new AlertDialog.Builder(this)
                    .setMessage("Can't find frame sections data. Use default sections?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setDefaultDefineSection();
                            setMainRunning2();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }else{
            setMainRunning2();
        }
    }

    private void setMainRunning2(){
        Running objek = new Running();
        objek.run();

        isDrawDisplacement = true;
        isAlreadyRunning = true;
        drawLoadCurrent = 0;

        //toolbar dan navigasi
        toolUndeformed.setIcon(R.drawable.ic_toolbar_deformed);
        toolUndeformed.setEnabled(true);
        toolShowGraph.setEnabled(true);
        toolShowDisplacementValue.setEnabled(true);

        setMenuItemEnable(menuDrawer, true, R.id.nav_export);

        setAnalysisRunning();
        drawGeneral();
        setLocked();
    }

    private void setAnalysisRunning() {
        //Check keamanan tiap batang
        //variabel
        int jmlhKolom = pointerElemenKolom.length;
        int jmlhBalokX = pointerElemenBalokXZ.length;
        int jmlhBalokY = pointerElemenBalokYZ.length;
        float Length;

        //mengassign ke listBebanBatang
        listBebanBatang = new ArrayList<>(pointerElemenStruktur.length);
        for (int i = 0; i < jmlhKolom; i++) {
            listBebanBatang.add(new Y_BebanBatang(i, 0)); //jenis.equals(kolom
        }
        for (int i = 0; i < jmlhBalokX; i++) {
            listBebanBatang.add(new Y_BebanBatang(i, 1)); //jenis.equals(balok x
        }
        for (int i = 0; i < jmlhBalokY; i++) {
            listBebanBatang.add(new Y_BebanBatang(i, 2)); //jenis.equals(balok y
        }

        //ANALISIS KOLOM
        Length = Sth;
        for(int i = 0; i < listFrameSection.size(); i++){
            String tipe = listFrameSection.get(i).getSteelType();
            if (tipe.equals("WF")) {
                listFrameSection.get(i).setCapacityWF(i, Length);
            }
            if (tipe.equals("L")) {
                listFrameSection.get(i).setCapacityL(i, Length);
            }
            if (tipe.equals("C")) {
                listFrameSection.get(i).setCapacityC(i, Length);
            }
            if (tipe.equals("I")) {
                listFrameSection.get(i).setCapacityI(i, Length);
            }
        }

        for(int i = 0; i < jmlhKolom; i++){
            analisisBatang(i);
        }

        //ANALISIS BALOK X
        Length = Bwx;
        for(int i = 0; i < listFrameSection.size(); i++){
            String tipe = listFrameSection.get(i).getSteelType();
            if (tipe.equals("WF")) {
                listFrameSection.get(i).setCapacityWF(i, Length);
            }
            if (tipe.equals("L")) {
                listFrameSection.get(i).setCapacityL(i, Length);
            }
            if (tipe.equals("C")) {
                listFrameSection.get(i).setCapacityC(i, Length);
            }
            if (tipe.equals("I")) {
                listFrameSection.get(i).setCapacityI(i, Length);
            }
        }

        for(int i = jmlhKolom; i < jmlhKolom+jmlhBalokX; i++){
            analisisBatang(i);
        }

        //ANALISIS BALOK Y
        Length = Bwy;
        for(int i = 0; i < listFrameSection.size(); i++){
            String tipe = listFrameSection.get(i).getSteelType();
            if (tipe.equals("WF")) {
                listFrameSection.get(i).setCapacityWF(i, Length);
            }
            if (tipe.equals("L")) {
                listFrameSection.get(i).setCapacityL(i, Length);
            }
            if (tipe.equals("C")) {
                listFrameSection.get(i).setCapacityC(i, Length);
            }
            if (tipe.equals("I")) {
                listFrameSection.get(i).setCapacityI(i, Length);
            }
        }

        for(int i = jmlhKolom+jmlhBalokX; i < jmlhKolom+jmlhBalokX+jmlhBalokY; i++){
            analisisBatang(i);
        }
    }

    private void analisisBatang(int i){
        steelType = listFrameSection.get(pointerFrameSection[i]).getSteelType();

        double PnC = listFrameSection.get(pointerFrameSection[i]).getPnC();
        double PnT = listFrameSection.get(pointerFrameSection[i]).getPnT();
        double MnMajor = listFrameSection.get(pointerFrameSection[i]).getMnMajor();
        double MnMinor = listFrameSection.get(pointerFrameSection[i]).getMnMinor();
        double VnMajor = listFrameSection.get(pointerFrameSection[i]).getVnMajor();
        double VnMinor = listFrameSection.get(pointerFrameSection[i]).getVnMinor();
        double[] tmpPM = new double[5];
        double[] tmpV  = new double[5];

        if (steelType.equals("WF") || steelType.equals("C") || steelType.equals("I")) {
            if(!listFrameSection.get(pointerFrameSection[i]).isAlphaRotated()) {
                MnMajor = listFrameSection.get(pointerFrameSection[i]).getMnMajor();
                if (listFrameSection.get(pointerFrameSection[i]).getCbStatus()) {
                    MnMajor = Math.min(listFrameSection.get(pointerFrameSection[i]).getMnMajor()
                                    * listBebanBatang.get(i).getCbMajor(),
                            listFrameSection.get(pointerFrameSection[i]).getMpMajor());
                }
                MnMinor = listFrameSection.get(pointerFrameSection[i]).getMnMinor();
            }else{
                MnMinor = listFrameSection.get(pointerFrameSection[i]).getMnMajor();
                if (listFrameSection.get(pointerFrameSection[i]).getCbStatus()) {
                    MnMajor = Math.min(listFrameSection.get(pointerFrameSection[i]).getMnMajor()
                                    * listBebanBatang.get(i).getCbMajor(),
                            listFrameSection.get(pointerFrameSection[i]).getMpMajor());
                }
                MnMajor = listFrameSection.get(pointerFrameSection[i]).getMnMinor();
            }
            //Cb MnMajor MnMinor (untuk profil L)
        } else if (steelType.equals("L")) {
            if(!listFrameSection.get(pointerFrameSection[i]).isAlphaRotated()) {
                if (listFrameSection.get(pointerFrameSection[i]).getMpMajor() < listFrameSection.get(pointerFrameSection[i]).getSy() * bajaFy) {
                    MnMajor = Math.min(listFrameSection.get(pointerFrameSection[i]).getMnMajor(),
                            (0.92 - 0.17 * listFrameSection.get(pointerFrameSection[i]).getMpMajor()
                                    * Math.max(listBebanBatang.get(i).getCbMajor(), 1.5) / (listFrameSection.get(pointerFrameSection[i]).getSy() * bajaFy))
                                    * listFrameSection.get(pointerFrameSection[i]).getMpMajor() * Math.max(listBebanBatang.get(i).getCbMajor(), 1.5));
                } else {
                    MnMajor = Math.min(Math.min(listFrameSection.get(pointerFrameSection[i]).getMnMajor(),
                            (1.92 - 1.17 * Math.sqrt((listFrameSection.get(pointerFrameSection[i]).getSy() * bajaFy)
                                    / listFrameSection.get(pointerFrameSection[i]).getMpMajor() / Math.max(listBebanBatang.get(i).getCbMajor(), 1.5)))
                                    * (listFrameSection.get(pointerFrameSection[i]).getSy() * bajaFy)),
                            1.5 * (listFrameSection.get(pointerFrameSection[i]).getSy() * bajaFy));
                }
                if (listFrameSection.get(pointerFrameSection[i]).getMpMinor() < listFrameSection.get(pointerFrameSection[i]).getSz() * bajaFy) {
                    MnMinor = Math.min(listFrameSection.get(pointerFrameSection[i]).getMnMinor(),
                            (0.92 - 0.17 * listFrameSection.get(pointerFrameSection[i]).getMpMinor() *
                                    Math.max(listBebanBatang.get(i).getCbMinor(), 1.5) / (listFrameSection.get(pointerFrameSection[i]).getSz() * bajaFy))
                                    * listFrameSection.get(pointerFrameSection[i]).getMpMinor() * Math.max(listBebanBatang.get(i).getCbMinor(), 1.5));
                } else {
                    MnMinor = Math.min(Math.min(listFrameSection.get(pointerFrameSection[i]).getMnMinor(),
                            (1.92 - 1.17 * Math.sqrt((listFrameSection.get(pointerFrameSection[i]).getSz() * bajaFy)
                                    / listFrameSection.get(pointerFrameSection[i]).getMpMinor() / Math.max(listBebanBatang.get(i).getCbMinor(), 1.5)))
                                    * (listFrameSection.get(pointerFrameSection[i]).getSz() * bajaFy)), 1.5 *
                            (listFrameSection.get(pointerFrameSection[i]).getSz() * bajaFy));
                }
            }else{
                if (listFrameSection.get(pointerFrameSection[i]).getMpMajor() < listFrameSection.get(pointerFrameSection[i]).getSy() * bajaFy) {
                    MnMinor = Math.min(listFrameSection.get(pointerFrameSection[i]).getMnMajor(),
                            (0.92 - 0.17 * listFrameSection.get(pointerFrameSection[i]).getMpMajor()
                                    * Math.max(listBebanBatang.get(i).getCbMajor(), 1.5) / (listFrameSection.get(pointerFrameSection[i]).getSy() * bajaFy))
                                    * listFrameSection.get(pointerFrameSection[i]).getMpMajor() * Math.max(listBebanBatang.get(i).getCbMajor(), 1.5));
                } else {
                    MnMinor = Math.min(Math.min(listFrameSection.get(pointerFrameSection[i]).getMnMajor(),
                            (1.92 - 1.17 * Math.sqrt((listFrameSection.get(pointerFrameSection[i]).getSy() * bajaFy)
                                    / listFrameSection.get(pointerFrameSection[i]).getMpMajor() / Math.max(listBebanBatang.get(i).getCbMajor(), 1.5)))
                                    * (listFrameSection.get(pointerFrameSection[i]).getSy() * bajaFy)),
                            1.5 * (listFrameSection.get(pointerFrameSection[i]).getSy() * bajaFy));
                }
                if (listFrameSection.get(pointerFrameSection[i]).getMpMinor() < listFrameSection.get(pointerFrameSection[i]).getSz() * bajaFy) {
                    MnMajor = Math.min(listFrameSection.get(pointerFrameSection[i]).getMnMinor(),
                            (0.92 - 0.17 * listFrameSection.get(pointerFrameSection[i]).getMpMinor() *
                                    Math.max(listBebanBatang.get(i).getCbMinor(), 1.5) / (listFrameSection.get(pointerFrameSection[i]).getSz() * bajaFy))
                                    * listFrameSection.get(pointerFrameSection[i]).getMpMinor() * Math.max(listBebanBatang.get(i).getCbMinor(), 1.5));
                } else {
                    MnMajor = Math.min(Math.min(listFrameSection.get(pointerFrameSection[i]).getMnMinor(),
                            (1.92 - 1.17 * Math.sqrt((listFrameSection.get(pointerFrameSection[i]).getSz() * bajaFy)
                                    / listFrameSection.get(pointerFrameSection[i]).getMpMinor() / Math.max(listBebanBatang.get(i).getCbMinor(), 1.5)))
                                    * (listFrameSection.get(pointerFrameSection[i]).getSz() * bajaFy)), 1.5 *
                            (listFrameSection.get(pointerFrameSection[i]).getSz() * bajaFy));
                }
            }
        }
        listBebanBatang.get(i).setAmanPMAnalysis(0);
        listBebanBatang.get(i).setAmanVAnalysis(0);
        for(int x=0;x<=4;x++) {
            if (listBebanBatang.get(i).getPu(x) < 0) {
                if (-listBebanBatang.get(i).getPu(x) / listFrameSection.get(pointerFrameSection[i]).getPnC() < 0.2) {
                    tmpPM[x] = Math.abs(listBebanBatang.get(i).getPu(x)) / PnC / 2
                            + Math.abs(listBebanBatang.get(i).getMuMajor(x)) / MnMinor
                            + Math.abs(listBebanBatang.get(i).getMuMinor(x)) / MnMajor;
                } else {
                    tmpPM[x] = Math.abs(listBebanBatang.get(i).getPu(x)) / PnC
                            + 8.0 / 9.0 * (Math.abs(listBebanBatang.get(i).getMuMajor(x)) / MnMinor
                            + Math.abs(listBebanBatang.get(i).getMuMinor(x)) / MnMajor);
                }
            } else {
                if (listBebanBatang.get(i).getPu(x) / listFrameSection.get(pointerFrameSection[i]).getPnT() < 0.2) {
                    tmpPM[x] = Math.abs(listBebanBatang.get(i).getPu(x)) / PnT / 2
                            + Math.abs(listBebanBatang.get(i).getMuMajor(x)) / MnMinor
                            + Math.abs(listBebanBatang.get(i).getMuMinor(x)) / MnMajor;
                } else {
                    tmpPM[x] = Math.abs(listBebanBatang.get(i).getPu(x)) / PnT
                            + 8.0 / 9.0 * (Math.abs(listBebanBatang.get(i).getMuMajor(x)) / MnMinor
                            + Math.abs(listBebanBatang.get(i).getMuMinor(x)) / MnMajor);
                }
            }
            tmpV[x]=( Math.abs(listBebanBatang.get(i).getVuMajor(x)) / VnMajor
                    + Math.abs(listBebanBatang.get(i).getVuMinor(x)) / VnMinor);
            listBebanBatang.get(i).setAmanPMAnalysis(Math.max(listBebanBatang.get(i).getAmanPMAnalysis(),tmpPM[x]));
            listBebanBatang.get(i).setAmanVAnalysis( Math.max(listBebanBatang.get(i).getAmanVAnalysis() ,tmpV[x] ));
        }
    }

    //-----------SET RUNNING DESIGN----------//
    private void setDesignRunning() {
        //ngerun analysis dulu biar ga error
        setMainRunning();

        //mengaktifkan/nonaktifkan menu
        toolShowFrameSection.setEnabled(true);
        toolShowPM.setEnabled(true);
        toolShowShear.setEnabled(true);
        setLocked();

        setMenuItemEnable(menuDrawer, true,
                R.id.nav_export,
                R.id.nav_exportDesign);

        //variabel
        int jmlhProfilKolom = listDesignColSect2.size();
        int jmlhProfilBalokX = listDesignBeamXSect2.size();
        int jmlhProfilBalokY = listDesignBeamYSect2.size();

        //menghitung kapasitas profil kolom
        for (int i = 0; i < jmlhProfilKolom; i++) {
            String tipe = listDesignColSect2.get(i).getSteelType();
            if (tipe.equals("WF")) {
                listDesignColSect2.get(i).setCapacityWF(i, Sth);
                listDesignColSect2R.get(i).setCapacityWF(i, Sth);
            }
            if (tipe.equals("L")) {
                listDesignColSect2.get(i).setCapacityL(i, Sth);
                listDesignColSect2R.get(i).setCapacityL(i, Sth);
            }
            if (tipe.equals("C")) {
                listDesignColSect2.get(i).setCapacityC(i, Sth);
                listDesignColSect2R.get(i).setCapacityC(i, Sth);
            }
            if (tipe.equals("I")) {
                listDesignColSect2.get(i).setCapacityI(i, Sth);
                listDesignColSect2R.get(i).setCapacityI(i, Sth);
            }
        }

        //menghitung kapasitas profil balokX
        for (int i = 0; i < jmlhProfilBalokX; i++) {
            String tipe = listDesignBeamXSect2.get(i).getSteelType();
            if (tipe.equals("WF")) {
                listDesignBeamXSect2.get(i).setCapacityWF(i, Bwx);
                listDesignBeamXSect2R.get(i).setCapacityWF(i, Bwx);
            }
            if (tipe.equals("L")) {
                listDesignBeamXSect2.get(i).setCapacityL(i, Bwx);
                listDesignBeamXSect2R.get(i).setCapacityL(i, Bwx);
            }
            if (tipe.equals("C")) {
                listDesignBeamXSect2.get(i).setCapacityC(i, Bwx);
                listDesignBeamXSect2R.get(i).setCapacityC(i, Bwx);
            }
            if (tipe.equals("I")) {
                listDesignBeamXSect2.get(i).setCapacityI(i, Bwx);
                listDesignBeamXSect2R.get(i).setCapacityI(i, Bwx);
            }
        }

        //menghitung kapasitas profil balokY
        for (int i = 0; i < jmlhProfilBalokY; i++) {
            String tipe = listDesignBeamYSect2.get(i).getSteelType();
            if (tipe.equals("WF")) {
                listDesignBeamYSect2.get(i).setCapacityWF(i, Bwy);
                listDesignBeamYSect2R.get(i).setCapacityWF(i, Bwy);
            }
            if (tipe.equals("L")) {
                listDesignBeamYSect2.get(i).setCapacityL(i, Bwy);
                listDesignBeamYSect2R.get(i).setCapacityL(i, Bwy);
            }
            if (tipe.equals("C")) {
                listDesignBeamYSect2.get(i).setCapacityC(i, Bwy);
                listDesignBeamYSect2R.get(i).setCapacityC(i, Bwy);
            }
            if (tipe.equals("I")) {
                listDesignBeamYSect2.get(i).setCapacityI(i, Bwy);
                listDesignBeamYSect2R.get(i).setCapacityI(i, Bwy);
            }
        }

        runColumnDesign();
        runBeamXDesign();
        runBeamYDesign();

        isDrawDisplacement = false;
        isDrawLoadCurrent = true;
        drawLoadCurrent = 7;
        setDeselectAll();
        isTimerSleep = true;
        drawGeneral();
    }

    private void runColumnDesign() {
        //variabel
        int jmlhBatang = pointerElemenKolom.length;
        int jmlhProfil = listDesignColSect2.size();
        double[] MnMajorBatang;
        double[] MnMinorBatang;
        double[] MnMajorBatangR;
        double[] MnMinorBatangR;
        double[] PnCBatang;
        double[] PnTBatang;
        double[] VnMajorBatang;
        double[] VnMinorBatang;
        double[] VnMajorBatangR;
        double[] VnMinorBatangR;
        MnMajorBatang = new double[jmlhProfil];
        MnMinorBatang = new double[jmlhProfil];
        MnMajorBatangR = new double[jmlhProfil];
        MnMinorBatangR = new double[jmlhProfil];
        PnCBatang = new double[jmlhProfil];
        PnTBatang = new double[jmlhProfil];
        VnMajorBatang = new double[jmlhProfil];
        VnMinorBatang = new double[jmlhProfil];
        VnMajorBatangR = new double[jmlhProfil];
        VnMinorBatangR = new double[jmlhProfil];

        //Membaca kapasitas
        for (int i = 0; i < jmlhProfil; i++) {
            PnCBatang[i]=listDesignColSect2.get(i).getPnC();
            PnTBatang[i]=listDesignColSect2.get(i).getPnT();
            VnMajorBatang[i]=listDesignColSect2.get(i).getVnMajor();
            VnMinorBatang[i]=listDesignColSect2.get(i).getVnMinor();
            VnMajorBatangR[i]=listDesignColSect2R.get(i).getVnMajor();
            VnMinorBatangR[i]=listDesignColSect2R.get(i).getVnMinor();
        }

        //Analisis tiap Batang
        for (int L = 0; L < jmlhBatang; L++) {
            //Membaca kapasitas momen
            for (int i = 0; i < jmlhProfil; i++) {
                listDesignColSect2.get(i).setMnWithCb(listBebanBatang.get(L).getCbMajor(),listBebanBatang.get(L).getCbMinor());
                MnMajorBatang[i]=listDesignColSect2.get(i).getMnMajorBatang();
                MnMinorBatang[i]=listDesignColSect2.get(i).getMnMinorBatang();
                listDesignColSect2R.get(i).setMnWithCb(listBebanBatang.get(L).getCbMajor(),listBebanBatang.get(L).getCbMinor());
                MnMajorBatangR[i]=listDesignColSect2R.get(i).getMnMajorBatang();
                MnMinorBatangR[i]=listDesignColSect2R.get(i).getMnMinorBatang();
            }

            double[] tmpPM = new double[jmlhProfil]; //ini angka aman sementara
            double[] tmpV = new double[jmlhProfil]; //ini angka aman sementara
            double[] tmptmpPM = new double[5]; //ini angka aman sementara sementara
            double[] tmptmpV = new double[5]; //ini angka aman sementara sementara

            if(Math.abs(listBebanBatang.get(L).getMuMajor(2))>Math.abs(listBebanBatang.get(L).getMuMinor(2))) {
                for(int j = 0; j<jmlhProfil; j++){
                    tmpPM[j] = 0;
                    for (int x = 0; x <= 4; x++) {
                        tmptmpPM[x] = 10;
                        double Pu = listBebanBatang.get(L).getPu(x);
                        double MuMaj = listBebanBatang.get(L).getMuMajor(x);
                        double MuMin = listBebanBatang.get(L).getMuMinor(x);
                        double VuMaj = listBebanBatang.get(L).getVuMajor(x);
                        double VuMin = listBebanBatang.get(L).getVuMinor(x);
                        if (Pu < 0) {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = -Pu / PnCBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j];
                            } else {
                                tmptmpPM[x] = -Pu / PnCBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j]);
                            }
                        } else {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = Pu / PnTBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j];
                            } else {
                                tmptmpPM[x] = Pu / PnTBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j]);
                            }
                        }
                        tmptmpV[x] = (Math.abs(VuMaj) / VnMajorBatang[j]) + (Math.abs(VuMin) / VnMinorBatang[j]);
                        tmpPM[j] = Math.max(tmpPM[j], tmptmpPM[x]);
                        tmpV[j] = Math.max(tmpV[j], tmptmpV[x]);
                    }
                }
                listBebanBatang.get(L).setProfil("No Result");
                listBebanBatang.get(L).setAmanPMDesign(-404);
                listBebanBatang.get(L).setAmanVDesign(-404);
                for(int j = 0; j<jmlhProfil; j++){
                    if(tmpPM[j]<=1&&tmpV[j]<=1){
                        if(tmpPM[j]>listBebanBatang.get(L).getAmanPMDesign()&&tmpV[j]>listBebanBatang.get(L).getAmanVDesign()) {
                            listBebanBatang.get(L).setProfil(listDesignColSect2.get(j).getSectionName());
                            listBebanBatang.get(L).setAmanPMDesign(tmpPM[j]);
                            listBebanBatang.get(L).setAmanVDesign(tmpV[j]);
                        }
                    }
                }
            }else{
                for(int j = 0; j<jmlhProfil; j++){
                    tmpPM[j] = 0;
                    for (int x = 0; x <= 4; x++) {
                        tmptmpPM[x] = 10;
                        double Pu = listBebanBatang.get(L).getPu(x);
                        double MuMaj = listBebanBatang.get(L).getMuMajor(x);
                        double MuMin = listBebanBatang.get(L).getMuMinor(x);
                        double VuMaj = listBebanBatang.get(L).getVuMajor(x);
                        double VuMin = listBebanBatang.get(L).getVuMinor(x);
                        if (Pu < 0) {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = -Pu / PnCBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j];
                            } else {
                                tmptmpPM[x] = -Pu / PnCBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j]);
                            }
                        } else {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = Pu / PnTBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j];
                            } else {
                                tmptmpPM[x] = Pu / PnTBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j]);
                            }
                        }
                        tmptmpV[x] = Math.abs(VuMaj) / VnMajorBatangR[j] + Math.abs(VuMin) / VnMinorBatangR[j];
                        tmpPM[j] = Math.max(tmpPM[j], tmptmpPM[x]);
                        tmpV[j] = Math.max(tmpV[j], tmptmpV[x]);
                    }
                }
                listBebanBatang.get(L).setProfil("No Result");
                listBebanBatang.get(L).setAmanPMDesign(-404);
                listBebanBatang.get(L).setAmanVDesign(-404);
                for(int j = 0; j<jmlhProfil; j++){
                    if(tmpPM[j]<=1&&tmpV[j]<=1){
                        if(tmpPM[j]>listBebanBatang.get(L).getAmanPMDesign()&&tmpV[j]>listBebanBatang.get(L).getAmanVDesign()) {
                            listBebanBatang.get(L).setProfil(listDesignColSect2R.get(j).getSectionName());
                            listBebanBatang.get(L).setAmanPMDesign(tmpPM[j]);
                            listBebanBatang.get(L).setAmanVDesign(tmpV[j]);
                        }
                    }
                }
            }
        }
    }

    private void runBeamXDesign() {
        //variabel
        int jmlhBatang = pointerElemenBalokXZ.length;
        int jmlhProfil = listDesignBeamXSect2.size();
        double[] MnMajorBatang;
        double[] MnMinorBatang;
        double[] MnMajorBatangR;
        double[] MnMinorBatangR;
        double[] PnCBatang;
        double[] PnTBatang;
        double[] VnMajorBatang;
        double[] VnMinorBatang;
        double[] VnMajorBatangR;
        double[] VnMinorBatangR;
        MnMajorBatang = new double[jmlhProfil];
        MnMinorBatang = new double[jmlhProfil];
        MnMajorBatangR = new double[jmlhProfil];
        MnMinorBatangR = new double[jmlhProfil];
        PnCBatang = new double[jmlhProfil];
        PnTBatang = new double[jmlhProfil];
        VnMajorBatang = new double[jmlhProfil];
        VnMinorBatang = new double[jmlhProfil];
        VnMajorBatangR = new double[jmlhProfil];
        VnMinorBatangR = new double[jmlhProfil];

        //Membaca kapasitas
        for (int i = 0; i < jmlhProfil; i++) {
            PnCBatang[i]=listDesignBeamXSect2.get(i).getPnC();
            PnTBatang[i]=listDesignBeamXSect2.get(i).getPnT();
            VnMajorBatang[i]=listDesignBeamXSect2.get(i).getVnMajor();
            VnMinorBatang[i]=listDesignBeamXSect2.get(i).getVnMinor();
            VnMajorBatangR[i]=listDesignBeamXSect2R.get(i).getVnMajor();
            VnMinorBatangR[i]=listDesignBeamXSect2R.get(i).getVnMinor();
        }

        //Analisis tiap Batang
        for (int L = pointerElemenKolom.length+0; L < pointerElemenKolom.length+jmlhBatang; L++) {
            //Membaca kapasitas momen
            for (int i = 0; i < jmlhProfil; i++) {
                listDesignBeamXSect2.get(i).setMnWithCb(listBebanBatang.get(L).getCbMajor(),listBebanBatang.get(L).getCbMinor());
                MnMajorBatang[i]=listDesignBeamXSect2.get(i).getMnMajorBatang();
                MnMinorBatang[i]=listDesignBeamXSect2.get(i).getMnMinorBatang();
                listDesignBeamXSect2R.get(i).setMnWithCb(listBebanBatang.get(L).getCbMajor(),listBebanBatang.get(L).getCbMinor());
                MnMajorBatangR[i]=listDesignBeamXSect2R.get(i).getMnMajorBatang();
                MnMinorBatangR[i]=listDesignBeamXSect2R.get(i).getMnMinorBatang();
            }

            double[] tmpPM = new double[jmlhProfil]; //ini angka aman sementara
            double[] tmpV = new double[jmlhProfil]; //ini angka aman sementara
            double[] tmptmpPM = new double[5]; //ini angka aman sementara sementara
            double[] tmptmpV = new double[5]; //ini angka aman sementara sementara

            if(Math.abs(listBebanBatang.get(L).getMuMajor(2))>Math.abs(listBebanBatang.get(L).getMuMinor(2))) {
                for(int j = 0; j<jmlhProfil; j++){
                    tmpPM[j] = 0;
                    for (int x = 0; x <= 4; x++) {
                        tmptmpPM[x] = 10;
                        double Pu = listBebanBatang.get(L).getPu(x);
                        double MuMaj = listBebanBatang.get(L).getMuMajor(x);
                        double MuMin = listBebanBatang.get(L).getMuMinor(x);
                        double VuMaj = listBebanBatang.get(L).getVuMajor(x);
                        double VuMin = listBebanBatang.get(L).getVuMinor(x);
                        if (Pu < 0) {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = -Pu / PnCBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j];
                            } else {
                                tmptmpPM[x] = -Pu / PnCBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j]);
                            }
                        } else {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = Pu / PnTBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j];
                            } else {
                                tmptmpPM[x] = Pu / PnTBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j]);
                            }
                        }
                        tmptmpV[x] = Math.abs(VuMaj) / VnMajorBatang[j] + Math.abs(VuMin) / VnMinorBatang[j];
                        tmpPM[j] = Math.max(tmpPM[j], tmptmpPM[x]);
                        tmpV[j] = Math.max(tmpV[j], tmptmpV[x]);
                    }
                }
                listBebanBatang.get(L).setProfil("No Result");
                listBebanBatang.get(L).setAmanPMDesign(-404);
                listBebanBatang.get(L).setAmanVDesign(-404);
                for(int j = 0; j<jmlhProfil; j++){
                    if(tmpPM[j]<=1&&tmpV[j]<=1){
                        if(tmpPM[j]>listBebanBatang.get(L).getAmanPMDesign()&&tmpV[j]>listBebanBatang.get(L).getAmanVDesign()) {
                            listBebanBatang.get(L).setProfil(listDesignBeamXSect2.get(j).getSectionName());
                            listBebanBatang.get(L).setAmanPMDesign(tmpPM[j]);
                            listBebanBatang.get(L).setAmanVDesign(tmpV[j]);
                        }
                    }
                }
            }else{
                for(int j = 0; j<jmlhProfil; j++){
                    tmpPM[j] = 0;
                    for (int x = 0; x <= 4; x++) {
                        tmptmpPM[x] = 10;
                        double Pu = listBebanBatang.get(L).getPu(x);
                        double MuMaj = listBebanBatang.get(L).getMuMajor(x);
                        double MuMin = listBebanBatang.get(L).getMuMinor(x);
                        double VuMaj = listBebanBatang.get(L).getVuMajor(x);
                        double VuMin = listBebanBatang.get(L).getVuMinor(x);
                        if (Pu < 0) {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = -Pu / PnCBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j];
                            } else {
                                tmptmpPM[x] = -Pu / PnCBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j]);
                            }
                        } else {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = Pu / PnTBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j];
                            } else {
                                tmptmpPM[x] = Pu / PnTBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j]);
                            }
                        }
                        tmptmpV[x] = Math.abs(VuMaj) / VnMajorBatangR[j] + Math.abs(VuMin) / VnMinorBatangR[j];
                        tmpPM[j] = Math.max(tmpPM[j], tmptmpPM[x]);
                        tmpV[j] = Math.max(tmpV[j], tmptmpV[x]);
                    }
                }
                listBebanBatang.get(L).setProfil("No Result");
                listBebanBatang.get(L).setAmanPMDesign(-404);
                listBebanBatang.get(L).setAmanVDesign(-404);
                for(int j = 0; j<jmlhProfil; j++){
                    if(tmpPM[j]<=1&&tmpV[j]<=1){
                        if(tmpPM[j]>listBebanBatang.get(L).getAmanPMDesign()&&tmpV[j]>listBebanBatang.get(L).getAmanVDesign()) {
                            listBebanBatang.get(L).setProfil(listDesignBeamXSect2R.get(j).getSectionName());
                            listBebanBatang.get(L).setAmanPMDesign(tmpPM[j]);
                            listBebanBatang.get(L).setAmanVDesign(tmpV[j]);
                        }
                    }
                }
            }
        }
    }

    private void runBeamYDesign() {
        //variabel
        int jmlhBatang = pointerElemenBalokYZ.length;
        int jmlhProfil = listDesignBeamYSect2.size();
        double[] MnMajorBatang;
        double[] MnMinorBatang;
        double[] MnMajorBatangR;
        double[] MnMinorBatangR;
        double[] PnCBatang;
        double[] PnTBatang;
        double[] VnMajorBatang;
        double[] VnMinorBatang;
        double[] VnMajorBatangR;
        double[] VnMinorBatangR;
        MnMajorBatang = new double[jmlhProfil];
        MnMinorBatang = new double[jmlhProfil];
        MnMajorBatangR = new double[jmlhProfil];
        MnMinorBatangR = new double[jmlhProfil];
        PnCBatang = new double[jmlhProfil];
        PnTBatang = new double[jmlhProfil];
        VnMajorBatang = new double[jmlhProfil];
        VnMinorBatang = new double[jmlhProfil];
        VnMajorBatangR = new double[jmlhProfil];
        VnMinorBatangR = new double[jmlhProfil];

        //Membaca kapasitas
        for (int i = 0; i < jmlhProfil; i++) {
            PnCBatang[i]=listDesignBeamYSect2.get(i).getPnC();
            PnTBatang[i]=listDesignBeamYSect2.get(i).getPnT();
            VnMajorBatang[i]=listDesignBeamYSect2.get(i).getVnMajor();
            VnMinorBatang[i]=listDesignBeamYSect2.get(i).getVnMinor();
            VnMajorBatangR[i]=listDesignBeamYSect2R.get(i).getVnMajor();
            VnMinorBatangR[i]=listDesignBeamYSect2R.get(i).getVnMinor();
        }

        //Analisis tiap Batang
        for (int L = pointerElemenKolom.length+pointerElemenBalokXZ.length+0; L < pointerElemenKolom.length+pointerElemenBalokXZ.length+jmlhBatang; L++) {
            //Membaca kapasitas momen
            for (int i = 0; i < jmlhProfil; i++) {
                listDesignBeamYSect2.get(i).setMnWithCb(listBebanBatang.get(L).getCbMajor(),listBebanBatang.get(L).getCbMinor());
                MnMajorBatang[i]=listDesignBeamYSect2.get(i).getMnMajorBatang();
                MnMinorBatang[i]=listDesignBeamYSect2.get(i).getMnMinorBatang();
                listDesignBeamYSect2R.get(i).setMnWithCb(listBebanBatang.get(L).getCbMajor(),listBebanBatang.get(L).getCbMinor());
                MnMajorBatangR[i]=listDesignBeamYSect2R.get(i).getMnMajorBatang();
                MnMinorBatangR[i]=listDesignBeamYSect2R.get(i).getMnMinorBatang();
            }

            double[] tmpPM = new double[jmlhProfil]; //ini angka aman sementara
            double[] tmpV = new double[jmlhProfil]; //ini angka aman sementara
            double[] tmptmpPM = new double[5]; //ini angka aman sementara sementara
            double[] tmptmpV = new double[5]; //ini angka aman sementara sementara

            if(Math.abs(listBebanBatang.get(L).getMuMajor(2))>Math.abs(listBebanBatang.get(L).getMuMinor(2))) {
                for(int j = 0; j<jmlhProfil; j++){
                    tmpPM[j] = 0;
                    for (int x = 0; x <= 4; x++) {
                        tmptmpPM[x] = 10;
                        double Pu = listBebanBatang.get(L).getPu(x);
                        double MuMaj = listBebanBatang.get(L).getMuMajor(x);
                        double MuMin = listBebanBatang.get(L).getMuMinor(x);
                        double VuMaj = listBebanBatang.get(L).getVuMajor(x);
                        double VuMin = listBebanBatang.get(L).getVuMinor(x);
                        if (Pu < 0) {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = -Pu / PnCBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j];
                            } else {
                                tmptmpPM[x] = -Pu / PnCBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j]);
                            }
                        } else {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = Pu / PnTBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j];
                            } else {
                                tmptmpPM[x] = Pu / PnTBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatang[j] + Math.abs(MuMin) / MnMinorBatang[j]);
                            }
                        }
                        tmptmpV[x] = Math.abs(VuMaj) / VnMajorBatang[j] + Math.abs(VuMin) / VnMinorBatang[j];
                        tmpPM[j] = Math.max(tmpPM[j], tmptmpPM[x]);
                        tmpV[j] = Math.max(tmpV[j], tmptmpV[x]);
                    }
                }
                listBebanBatang.get(L).setProfil("No Result");
                listBebanBatang.get(L).setAmanPMDesign(-404);
                listBebanBatang.get(L).setAmanVDesign(-404);
                for(int j = 0; j<jmlhProfil; j++){
                    if(tmpPM[j]<=1&&tmpV[j]<=1){
                        if(tmpPM[j]>listBebanBatang.get(L).getAmanPMDesign()&&tmpV[j]>listBebanBatang.get(L).getAmanVDesign()) {
                            listBebanBatang.get(L).setProfil(listDesignBeamYSect2.get(j).getSectionName());
                            listBebanBatang.get(L).setAmanPMDesign(tmpPM[j]);
                            listBebanBatang.get(L).setAmanVDesign(tmpV[j]);
                        }
                    }
                }
            }else{
                for(int j = 0; j<jmlhProfil; j++){
                    tmpPM[j] = 0;
                    for (int x = 0; x <= 4; x++) {
                        tmptmpPM[x] = 10;
                        double Pu = listBebanBatang.get(L).getPu(x);
                        double MuMaj = listBebanBatang.get(L).getMuMajor(x);
                        double MuMin = listBebanBatang.get(L).getMuMinor(x);
                        double VuMaj = listBebanBatang.get(L).getVuMajor(x);
                        double VuMin = listBebanBatang.get(L).getVuMinor(x);
                        if (Pu < 0) {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = -Pu / PnCBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j];
                            } else {
                                tmptmpPM[x] = -Pu / PnCBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j]);
                            }
                        } else {
                            if (-Pu / PnCBatang[j] < 0.2) {
                                tmptmpPM[x] = Pu / PnTBatang[j] / 2 + Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j];
                            } else {
                                tmptmpPM[x] = Pu / PnTBatang[j] + 8.0 / 9.0 * (Math.abs(MuMaj) / MnMajorBatangR[j] + Math.abs(MuMin) / MnMinorBatangR[j]);
                            }
                        }
                        tmptmpV[x] = Math.abs(VuMaj) / VnMajorBatangR[j] + Math.abs(VuMin) / VnMinorBatangR[j];
                        tmpPM[j] = Math.max(tmpPM[j], tmptmpPM[x]);
                        tmpV[j] = Math.max(tmpV[j], tmptmpV[x]);
                    }
                }
                listBebanBatang.get(L).setProfil("No Result");
                listBebanBatang.get(L).setAmanPMDesign(-404);
                listBebanBatang.get(L).setAmanVDesign(-404);
                for(int j = 0; j<jmlhProfil; j++){
                    if(tmpPM[j]<=1&&tmpV[j]<=1){
                        if(tmpPM[j]>listBebanBatang.get(L).getAmanPMDesign()&&tmpV[j]>listBebanBatang.get(L).getAmanVDesign()) {
                            listBebanBatang.get(L).setProfil(listDesignBeamYSect2R.get(j).getSectionName());
                            listBebanBatang.get(L).setAmanPMDesign(tmpPM[j]);
                            listBebanBatang.get(L).setAmanVDesign(tmpV[j]);
                        }
                    }
                }
            }
        }
    }

    //----------NAVIGASI----------//
    private void navigasiDrawer() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_create:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        setLoading(true,"Saving...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showCreateProject();
                            }
                        }, 400);
                        return true;

                    case R.id.nav_open:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        setLoading(true,"Saving...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showOpenProject();
                            }
                        }, 400);
                        return true;

                    case R.id.nav_save:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        setLoading(true,"Saving...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                saveProject();
                                setLoading(false, "save");
                                Toast.makeText(getApplicationContext(), "Project saved.", Toast.LENGTH_SHORT).show();
                            }
                        }, 400);
                        return true;

                    case R.id.nav_export:
                        isExportAnalysis = true;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        setLoading(true,"Exporting...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    exportAnalysis();
                                } catch (DocumentException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 400);
                        return true;

                    case R.id.nav_exportDesign:
                        isExportAnalysis = false;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        setLoading(true,"Exporting...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    exportDesign();
                                } catch (DocumentException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 400);
                        return true;

                    case R.id.nav_about:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showAbout();
                        return true;
                }
                return false;
            }
        });
    }

    //NAVIGASI SPINNER ANALYSIS DAN DESIGN
    public void navigasiSpinnerAnalysis() {
        //clearkan dulu biar ga double2
        listAnalysis.clear();
        listSpinAnalysis.clear();

        //mengatur list analysis
        dbHelperProject = new G_DBHelper_Project(getApplicationContext());
        dbProject = dbHelperProject.getReadableDatabase();
        String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_ANALYSIS +  " WHERE " +
                        F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + projectId;
        cursorProject = dbProject.rawQuery(sql, null);
        cursorProject.moveToFirst();
        if (cursorProject !=null && cursorProject.moveToFirst()) {
            do {
                listAnalysis.add(cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_ANALYSIS)));
            } while (cursorProject.moveToNext());
            cursorProject.close();
        }

        //tambahkan listnya
        listSpinAnalysis.addAll(listAnalysis);
        listSpinAnalysis.add("-add/delete analysis-");

        //tampilkan data
        spinAnalysis.setAdapter(new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item, listSpinAnalysis));

        //tampilkan data analysis terkini
        spinAnalysis.setSelection(analysisId);

        spinAnalysis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == listSpinAnalysis.size() - 1) {
                    setLoading(true,"Saving...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showOpenAnalysis();
                        }
                    }, 300);
                } else {
                    analysisId = position;
                    navigasiSpinnerDesign();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void navigasiSpinnerDesign() {
        //clearkan dulu biar ga double2
        listDesign.clear();
        listSpinDesign.clear();
        listSpinDesign.add("Default");

        //mengatur list design
        dbHelperProject = new G_DBHelper_Project(getApplicationContext());
        dbProject = dbHelperProject.getReadableDatabase();
        String sql = "SELECT * FROM " + F_ProjectContract.ProjectEntry.TABLE_DESIGN +  " WHERE " +
                F_ProjectContract.ProjectEntry.ID_PROJECT + " = " + projectId +
                " AND " + F_ProjectContract.ProjectEntry.ID_ANALYSIS + " = " + analysisId;
        cursorProject = dbProject.rawQuery(sql, null);
        cursorProject.moveToFirst();
        if (cursorProject !=null && cursorProject.moveToFirst()) {
            do {
                listDesign.add(cursorProject.getString(cursorProject.getColumnIndex(F_ProjectContract.ProjectEntry.NAME_DESIGN)));
            } while (cursorProject.moveToNext());
            cursorProject.close();
        }

        //tambahkan listnya
        listSpinDesign.addAll(listDesign);
        listSpinDesign.add("-add/delete design-");

        //tampilkan data
        spinDesign.setAdapter(new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item, listSpinDesign));

        //tampilkan data design terkini
        if(isAnalysis){
            spinDesign.setSelection(0);
        }else{
            spinDesign.setSelection(designId+1);
        }

        spinDesign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isAnalysis = true;
                    designId=-1;
                    if(!isAnalysisNew) {
                        setOpenAnalysis();
                    }else{
                        setLoading(true, "Generating...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setCreateAnalysis();
                            }
                        }, 10);
                    }
                    setFABAnalysis();
                    invalidateOptionsMenu();
                } else if (position == listSpinDesign.size() - 1) {
                    if (isAlreadyRunning || !listDesign.isEmpty() || !isAnalysisNew) {
                        setLoading(true,"Saving...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showOpenDesign();
                            }
                        }, 300);
                    } else {
                        Toast.makeText(getApplicationContext(), "Run the analysis first!", Toast.LENGTH_SHORT).show();
                        if(isAnalysis){
                            spinDesign.setSelection(0);
                        }else{
                            spinDesign.setSelection(designId+1);
                        }
                    }
                } else {
                    isAnalysis = false;
                    designId = position-1;
                    invalidateOptionsMenu();
                    setFABDesign();
                    if(isDesignNew) {
                        setCreateDesign();
                    }else{
                        setOpenDesign();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //NAVIGASI BOTTOM NAVIGATION VIEW
    private void navigasiBottom() {
        bottomNavView = findViewById(R.id.bottom_nav_view);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_3D:
                        setArrowBackAndForward(false);
                        tandaViewBottomNav = 1;
                        if (isPerspektif) {
                            drawFrame3D();
                            setDeselectAll();
                        } else {
                            drawFrame3DOrtho();
                            setDeselectAll();
                        }
                        return true;
                    case R.id.nav_XY:
                        setArrowBackAndForward(true);
                        tandaViewBottomNav = 2;
                        drawFrameXY();
                        setDeselectAll();
                        return true;
                    case R.id.nav_XZ:
                        setArrowBackAndForward(true);
                        tandaViewBottomNav = 3;
                        drawFrameXZ();
                        setDeselectAll();
                        return true;
                    case R.id.nav_YZ:
                        setArrowBackAndForward(true);
                        tandaViewBottomNav = 4;
                        drawFrameYZ();
                        setDeselectAll();
                        return true;
                }
                return false;
            }
        });
    }

    //NAVIGASI FLOATING ACTION BUTTON
    private void navigasiFAB() {
        fabJointLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJointLoadDialog();
            }
        });
        fabFrameLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrameLoadDialog();
            }
        });
        fabAreaLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAreaLoadDialog();
            }
        });
        fabFrameSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrameSectionInitial();
            }
        });
        fabEarthQuake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEarthQuakeDialog();
            }
        });
        fabColumnSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColumnSectionDialog();
            }
        });
        fabXBeamSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBeamXSectionDialog();
            }
        });
        fabYBeamSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBeamYSectionDialog();
            }
        });
    }

    private void navigasiToolbarView() {
        cardToolView.setVisibility(View.VISIBLE);
        imgViewRotate = findViewById(R.id.imgRotate);
        imgViewPan = findViewById(R.id.imgPan);
        imgViewToggle = findViewById(R.id.imgToggle);
        imgViewZoomIn = findViewById(R.id.imgZoomIn);
        imgViewZoomOut = findViewById(R.id.imgZoomOut);
        imgViewSelectAll = findViewById(R.id.imgSelectAll);
        imgViewDeselectAll = findViewById(R.id.imgDeselectAll);

        imgViewSelectAll.setEnabled(true);
        imgViewDeselectAll.setEnabled(true);

        imgViewRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgViewRotate.setImageResource(R.drawable.ic_view_3d_rotation_active);
                imgViewPan.setImageResource(R.drawable.ic_view_pan);
                isRotateActive = true;
            }
        });

        imgViewPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgViewPan.setImageResource(R.drawable.ic_view_pan_active);
                imgViewRotate.setImageResource(R.drawable.ic_view_3d_rotation);
                isRotateActive = false;
            }
        });

        imgViewToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPerspektif) {
                    isPerspektif = false;
                    imgViewToggle.setImageResource(R.drawable.ic_view_perspective);
                    drawGeneral();
                } else {
                    isPerspektif = true;
                    imgViewToggle.setImageResource(R.drawable.ic_view_perspective_active);
                    drawGeneral();
                }
            }
        });

        imgViewZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setZoomInOut(1.1f);
                skalaCanvas *= 1.1f;
            }
        });

        imgViewZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setZoomInOut(0.9f);
                skalaCanvas *= 0.9f;
            }
        });

        imgViewSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectAll();
            }
        });

        imgViewDeselectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDrawLoadCurrent = false;
                drawLoadCurrent = 0;
                drawInternalForceCurrent = 0;
                setDeselectAll();
                drawGeneral();
            }
        });
    }

    public static void setLoading(final boolean isLoading, String tulisan) {
        txtLoading.setText(tulisan);

        if (isLoading) {
            bottomNavView.setVisibility(View.INVISIBLE);
            cardToolView.setVisibility(View.INVISIBLE);
            fabMenu.setVisibility(View.INVISIBLE);
            imgCanvas.setVisibility(View.INVISIBLE);
            layoutAxis.setVisibility(View.INVISIBLE);
            loadingPanel.setVisibility(View.VISIBLE);
        } else {
            bottomNavView.setVisibility(View.VISIBLE);
            cardToolView.setVisibility(View.VISIBLE);
            fabMenu.setVisibility(View.VISIBLE);
            imgCanvas.setVisibility(View.VISIBLE);
            layoutAxis.setVisibility(View.VISIBLE);
            loadingPanel.setVisibility(View.GONE);
        }
    }

    private void setMenuItemEnable(Menu menuNavigation, boolean status, int...id){
        for(int x : id){
            menuNavigation.findItem(x).setEnabled(status);
        }
    }

    //----------MENAMPILKAN DIALOG DARI MENU TITIK TIGA----------//
    private void showShowGraphMenu() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_showgraph, null);
        mBuilder.setView(mView);
        dialogShowGraph = mBuilder.create();
        dialogShowGraph.show();

        //baca objek
        Button btnOK = mView.findViewById(R.id.btnOKShowGraph);
        Button btnCancel = mView.findViewById(R.id.btnCancelShowGraph);

        final RadioGroup rgShowGraph = mView.findViewById(R.id.rgShowGraph);
        final RadioButton rbtnAxial = mView.findViewById(R.id.rbtnAxial);
        final RadioButton rbtnShear22 = mView.findViewById(R.id.rbtnShear22);
        final RadioButton rbtnShear33 = mView.findViewById(R.id.rbtnShear33);
        final RadioButton rbtnTorsion = mView.findViewById(R.id.rbtnTorsion);
        final RadioButton rbtnMoment22 = mView.findViewById(R.id.rbtnMoment22);
        final RadioButton rbtnMoment33 = mView.findViewById(R.id.rbtnMoment33);
        final RadioButton rbtnReaction = mView.findViewById(R.id.rbtnReaction);
        final RadioButton rbtnNone = mView.findViewById(R.id.rbtnNoneGraph);

        final RadioGroup rgShowIFValue = mView.findViewById(R.id.rgShowIFValue);
        final RadioButton rbtnIFcolor = mView.findViewById(R.id.rbtnIFcolor);
        final RadioButton rbtnIFvalue = mView.findViewById(R.id.rbtnIFvalue);

        //meng-uncheck semua button
        rgShowGraph.clearCheck();
        rgShowIFValue.clearCheck();

        //menampilkan keadaan terkini
        switch (drawInternalForceCurrent) {
            case 1:
                rbtnAxial.setChecked(true);
                break;
            case 2:
                rbtnShear22.setChecked(true);
                break;
            case 3:
                rbtnShear33.setChecked(true);
                break;
            case 4:
                rbtnTorsion.setChecked(true);
                break;
            case 5:
                rbtnMoment33.setChecked(true);
                break;
            case 6:
                rbtnMoment22.setChecked(true);
                break;
            case 7:
                rbtnReaction.setChecked(true);
                break;
            default:
                rbtnNone.setChecked(true);
        }

        if(isShowInternalForceValue){
            rbtnIFvalue.setChecked(true);
        }else{
            rbtnIFcolor.setChecked(true);
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = rgShowGraph.getCheckedRadioButtonId();

                if (id == rbtnAxial.getId()) {
                    isDrawLoadCurrent = false;
                    drawInternalForceCurrent = 1;
                    isDrawDisplacement = false;
                } else if (id == rbtnShear22.getId()) {
                    isDrawLoadCurrent = false;
                    drawInternalForceCurrent = 2;
                    isDrawDisplacement = false;
                } else if (id == rbtnShear33.getId()) {
                    isDrawLoadCurrent = false;
                    drawInternalForceCurrent = 3;
                    isDrawDisplacement = false;
                } else if (id == rbtnTorsion.getId()) {
                    isDrawLoadCurrent = false;
                    drawInternalForceCurrent = 4;
                    isDrawDisplacement = false;
                } else if (id == rbtnMoment22.getId()) {
                    isDrawLoadCurrent = false;
                    drawInternalForceCurrent = 6; //karena ternyata terbalik
                    isDrawDisplacement = false;
                } else if (id == rbtnMoment33.getId()) {
                    isDrawLoadCurrent = false;
                    drawInternalForceCurrent = 5; //karena ternyata terbalik
                    isDrawDisplacement = false;
                } else if (id == rbtnReaction.getId()) {
                    isDrawLoadCurrent = false;
                    drawInternalForceCurrent = 7;
                    isDrawDisplacement = false;
                } else if (id == rbtnNone.getId()) {
                    isShowNodal = false;
                    isShowElement = false;
                    isShowInternalForceValue = false;
                    isShowDisplacementValue = false;
                    isDrawDisplacement = false;
                    isRotateActive = true;
                    isPerspektif = true;
                    isDrawLoadCurrent = false;
                    drawLoadCurrent = 0;
                    drawInternalForceCurrent = 0;
                    setKoorToDrawLoad();
                }

                id = rgShowIFValue.getCheckedRadioButtonId();
                if (id == rbtnIFcolor.getId()) {
                    isIF_Berwarna = true;
                    isShowInternalForceValue = false;
                } else if (id == rbtnIFvalue.getId()) {
                    isIF_Berwarna = false;
                    isShowInternalForceValue = true;
                }
                drawGeneral();
                toolUndeformed.setIcon(R.drawable.ic_toolbar_undeformed);
                dialogShowGraph.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShowGraph.dismiss();
            }
        });
    }

    private void showShowLoadMenu() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_showload, null);
        mBuilder.setView(mView);
        dialogShowLoad = mBuilder.create();
        dialogShowLoad.show();

        //baca objek
        Button btnOK = mView.findViewById(R.id.btnOKShowLoad);
        Button btnCancel = mView.findViewById(R.id.btnCancelShowLoad);
        final RadioGroup rgShowLoad = mView.findViewById(R.id.rgShowLoad);
        final RadioButton rbtnNoneLoad = mView.findViewById(R.id.rbtnNoneLoad);
        final RadioButton rbtnJointLoad = mView.findViewById(R.id.rbtnJointLoad);
        final RadioButton rbtnFrameLoad = mView.findViewById(R.id.rbtnFrameLoad);
        final RadioButton rbtnAreaLoad = mView.findViewById(R.id.rbtnAreaLoad);
        final RadioButton rbtnFrameSection = mView.findViewById(R.id.rbtnFrameSection);
        final RadioButton rbtnSFPM = mView.findViewById(R.id.rbtnSFPMAnalysis);
        final RadioButton rbtnSFShear = mView.findViewById(R.id.rbtnSFShearAnalysis);
        final Switch switchNodal = mView.findViewById(R.id.switchNodal);
        final Switch switchElement = mView.findViewById(R.id.switchElement);

        //mengatur tampilan terkini
        rgShowLoad.clearCheck();
        if(isDrawLoadCurrent){
            switch(drawLoadCurrent){
                case 1:
                    rbtnJointLoad.setChecked(true);
                    break;
                case 2:
                    rbtnFrameLoad.setChecked(true);
                    break;
                case 3:
                    rbtnAreaLoad.setChecked(true);
                    break;
                case 4:
                    rbtnFrameSection.setChecked(true);
                    break;
                case 5:
                    rbtnSFPM.setChecked(true);
                    break;
                case 6:
                    rbtnSFShear.setChecked(true);
                    break;
                default:
                    rbtnNoneLoad.setChecked(true);
            }
        }else{
            rbtnNoneLoad.setChecked(true);
        }

        switchNodal.setChecked(isShowNodal);
        switchElement.setChecked(isShowElement);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = rgShowLoad.getCheckedRadioButtonId();
                if (id == rbtnNoneLoad.getId()) {
                    isDrawLoadCurrent = false;
                    drawLoadCurrent = 0;
                    setDeselectAll();
                } else if (id == rbtnJointLoad.getId()) {
                    isDrawDisplacement = false;
                    isDrawLoadCurrent = true;
                    drawLoadCurrent = 1;
                    setDeselectAll();
                    isTimerSleep = true;
                } else if (id == rbtnFrameLoad.getId()) {
                    isDrawDisplacement = false;
                    isDrawLoadCurrent = true;
                    drawLoadCurrent = 2;
                    setDeselectAll();
                    isTimerSleep = true;
                } else if (id == rbtnAreaLoad.getId()) {
                    isDrawDisplacement = false;
                    isDrawLoadCurrent = true;
                    drawLoadCurrent = 3;
                    setDeselectAll();
                    isTimerSleep = true;
                    drawGeneral();
                } else if (id == rbtnFrameSection.getId()) {
                    isDrawDisplacement = false;
                    isDrawLoadCurrent = true;
                    drawLoadCurrent = 4;
                    setDeselectAll();
                    isTimerSleep = true;
                } else if (id == rbtnSFPM.getId()) {
                    if(isAlreadyRunning) {
                        isDrawDisplacement = false;
                        isDrawLoadCurrent = true;
                        drawLoadCurrent = 5;
                        setDeselectAll();
                        isTimerSleep = true;
                    }else{
                        Toast.makeText(getApplicationContext(), "Run the analysis first!", Toast.LENGTH_SHORT).show();
                    }
                } else if (id == rbtnSFShear.getId()) {
                    if(isAlreadyRunning) {
                        isDrawDisplacement = false;
                        isDrawLoadCurrent = true;
                        drawLoadCurrent = 6;
                        setDeselectAll();
                        isTimerSleep = true;
                    }else{
                        Toast.makeText(getApplicationContext(), "Run the analysis first!", Toast.LENGTH_SHORT).show();
                    }
                }

                if(!isDrawDisplacement){
                    toolUndeformed.setIcon(R.drawable.ic_toolbar_undeformed);
                }

                isShowNodal = switchNodal.isChecked();
                isShowElement = switchElement.isChecked();
                drawGeneral();
                dialogShowLoad.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShowLoad.dismiss();
            }
        });

    }

    //----------SET TOOL VIEW (SELECT/DESELECT)----------//
    private void setSelectAll() {
        if (!isDrawDisplacement) {
            int x = 0;
            for (boolean pointer : isPointerElementSelected) {
                isPointerElementSelected[x] = true;
                x++;
            }

            for (int i = 0; i < isPointerNodeSelected.length; i++) {
                isPointerNodeSelected[i] = true;
            }

            for (int i = 0; i < isPointerAreaSelected.length; i++) {
                isPointerAreaSelected[i] = true;
            }

            fabJointLoad.setEnabled(true);
            fabFrameLoad.setEnabled(true);
            fabAreaLoad.setEnabled(true);
            fabFrameSection.setEnabled(true);
            fabMenu.showMenu(true);

            isElementSelectedFAB = true;
            isNodeSelectedFAB = true;
            isAreaSelectedFAB = true;

            isTimerSleep = false;
            drawGeneral();
        }
    }

    public void setDeselectAll() {
        for (int i = 0; i < isPointerElementSelected.length; i++) {
            isPointerElementSelected[i] = false;
        }

        for (int i = 0; i < isPointerNodeSelected.length; i++) {
            isPointerNodeSelected[i] = false;
        }

        for (int i = 0; i < isPointerAreaSelected.length; i++) {
            isPointerAreaSelected[i] = false;
        }

        fabJointLoad.setEnabled(false);
        fabFrameLoad.setEnabled(false);
        fabAreaLoad.setEnabled(false);
        fabFrameSection.setEnabled(true);

        isElementSelectedFAB = false;
        isNodeSelectedFAB = false;
        isAreaSelectedFAB = false;

        isTimerSleep = true;
    }

    //----------MENGGAMBAR FRAME----------//
    private void drawGeneral() {
        pGaris.setColor(Color.BLACK);
        switch (tandaViewBottomNav) {
            case 1:
                if (isPerspektif) {
                    drawFrame3D();
                } else {
                    drawFrame3DOrtho();
                }
                break;

            case 2:
                drawFrameXY();
                break;

            case 3:
                drawFrameXZ();
                break;

            case 4:
                drawFrameYZ();
                break;
        }
    }

    private void draw3D_Axis() {
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                pPathAxis.setColor(getApplicationContext().getResources().getColor(R.color.colorRedTrans));
            } else if (i == 1) {
                pPathAxis.setColor(getApplicationContext().getResources().getColor(R.color.colorGreenTrans));
            } else {
                pPathAxis.setColor(getApplicationContext().getResources().getColor(R.color.colorBlueTrans));
            }

            for (int j = 2; j <= 37; j++) {
                float x1;
                float z1;
                if (tandaViewBottomNav == 1) {
                    if (isPerspektif) {
                        x1 = koor3D_Axis[i][0][0] / (focalLength + koor3D_Axis[i][0][1]) * focalLength;
                        z1 = koor3D_Axis[i][0][2] / (focalLength + koor3D_Axis[i][0][1]) * focalLength;
                        path.moveTo(x1, z1);
                    } else {
                        x1 = koor3D_Axis[i][0][0];
                        z1 = koor3D_Axis[i][0][2];
                        path.moveTo(x1, z1);
                    }
                } else if (tandaViewBottomNav == 2) {
                    x1 = koor3D_AxisFix[i][0][0];
                    z1 = koor3D_AxisFix[i][0][1];
                    path.moveTo(x1, z1);
                } else if (tandaViewBottomNav == 3) {
                    x1 = koor3D_AxisFix[i][0][0];
                    z1 = koor3D_AxisFix[i][0][2];
                    path.moveTo(x1, z1);
                } else {
                    x1 = koor3D_AxisFix[i][0][1];
                    z1 = koor3D_AxisFix[i][0][2];
                    path.moveTo(x1, z1);
                }

                if (j < 37) {
                    if (tandaViewBottomNav == 1) {
                        if (isPerspektif) {
                            x1 = koor3D_Axis[i][j][0] / (focalLength + koor3D_Axis[i][j][1]) * focalLength;
                            z1 = koor3D_Axis[i][j][2] / (focalLength + koor3D_Axis[i][j][1]) * focalLength;
                            path.lineTo(x1, z1);

                            x1 = koor3D_Axis[i][j + 1][0] / (focalLength + koor3D_Axis[i][j + 1][1]) * focalLength;
                            z1 = koor3D_Axis[i][j + 1][2] / (focalLength + koor3D_Axis[i][j + 1][1]) * focalLength;
                            path.lineTo(x1, z1);

                            path.close();

                            canvas.drawPath(path, pPathAxis);
                            path.reset();
                        } else {
                            x1 = koor3D_Axis[i][j][0];
                            z1 = koor3D_Axis[i][j][2];
                            path.lineTo(x1, z1);

                            x1 = koor3D_Axis[i][j + 1][0];
                            z1 = koor3D_Axis[i][j + 1][2];
                            path.lineTo(x1, z1);

                            path.close();

                            canvas.drawPath(path, pPathAxis);
                            path.reset();
                        }
                    } else if (tandaViewBottomNav == 2) {
                        x1 = koor3D_AxisFix[i][j][0];
                        z1 = koor3D_AxisFix[i][j][1];
                        path.lineTo(x1, z1);

                        x1 = koor3D_AxisFix[i][j + 1][0];
                        z1 = koor3D_AxisFix[i][j + 1][1];
                        path.lineTo(x1, z1);

                        path.close();

                        canvas.drawPath(path, pPathAxis);
                        path.reset();
                    } else if (tandaViewBottomNav == 3) {
                        x1 = koor3D_AxisFix[i][j][0];
                        z1 = koor3D_AxisFix[i][j][2];
                        path.lineTo(x1, z1);

                        x1 = koor3D_AxisFix[i][j + 1][0];
                        z1 = koor3D_AxisFix[i][j + 1][2];
                        path.lineTo(x1, z1);

                        path.close();

                        canvas.drawPath(path, pPathAxis);
                        path.reset();
                    } else {
                        x1 = koor3D_AxisFix[i][j][1];
                        z1 = koor3D_AxisFix[i][j][2];
                        path.lineTo(x1, z1);

                        x1 = koor3D_AxisFix[i][j + 1][1];
                        z1 = koor3D_AxisFix[i][j + 1][2];
                        path.lineTo(x1, z1);

                        path.close();

                        canvas.drawPath(path, pPathAxis);
                        path.reset();
                    }
                } else {
                    if (tandaViewBottomNav == 1) {
                        if (isPerspektif) {
                            x1 = koor3D_Axis[i][j][0] / (focalLength + koor3D_Axis[i][j][1]) * focalLength;
                            z1 = koor3D_Axis[i][j][2] / (focalLength + koor3D_Axis[i][j][1]) * focalLength;
                            path.lineTo(x1, z1);

                            x1 = koor3D_Axis[i][2][0] / (focalLength + koor3D_Axis[i][2][1]) * focalLength;
                            z1 = koor3D_Axis[i][2][2] / (focalLength + koor3D_Axis[i][2][1]) * focalLength;
                            path.lineTo(x1, z1);

                            path.close();

                            canvas.drawPath(path, pPathAxis);
                            path.reset();
                        } else {
                            x1 = koor3D_Axis[i][j][0];
                            z1 = koor3D_Axis[i][j][2];
                            path.lineTo(x1, z1);

                            x1 = koor3D_Axis[i][2][0];
                            z1 = koor3D_Axis[i][2][2];
                            path.lineTo(x1, z1);

                            path.close();

                            canvas.drawPath(path, pPathAxis);
                            path.reset();
                        }
                    } else if (tandaViewBottomNav == 2) {
                        x1 = koor3D_AxisFix[i][j][0];
                        z1 = koor3D_AxisFix[i][j][1];
                        path.lineTo(x1, z1);

                        x1 = koor3D_AxisFix[i][2][0];
                        z1 = koor3D_AxisFix[i][2][1];
                        path.lineTo(x1, z1);

                        path.close();

                        canvas.drawPath(path, pPathAxis);
                        path.reset();
                    } else if (tandaViewBottomNav == 3) {
                        x1 = koor3D_AxisFix[i][j][0];
                        z1 = koor3D_AxisFix[i][j][2];
                        path.lineTo(x1, z1);

                        x1 = koor3D_AxisFix[i][2][0];
                        z1 = koor3D_AxisFix[i][2][2];
                        path.lineTo(x1, z1);

                        path.close();

                        canvas.drawPath(path, pPathAxis);
                        path.reset();
                    } else {
                        x1 = koor3D_AxisFix[i][j][1];
                        z1 = koor3D_AxisFix[i][j][2];
                        path.lineTo(x1, z1);

                        x1 = koor3D_AxisFix[i][2][1];
                        z1 = koor3D_AxisFix[i][2][2];
                        path.lineTo(x1, z1);

                        path.close();

                        canvas.drawPath(path, pPathAxis);
                        path.reset();
                    }
                }
            }
        }

        //3D Koordinat
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 1; j++) {
                float x1;
                float z1;
                float x2 = 0;
                float z2 = 0;

                if (tandaViewBottomNav == 1) {
                    if (isPerspektif) {
                        x1 = koor3D_Axis[i][0][0] / (focalLength + koor3D_Axis[i][0][1]) * focalLength;
                        z1 = koor3D_Axis[i][0][2] / (focalLength + koor3D_Axis[i][0][1]) * focalLength;

                        x2 = koor3D_Axis[i][j][0] / (focalLength + koor3D_Axis[i][j][1]) * focalLength;
                        z2 = koor3D_Axis[i][j][2] / (focalLength + koor3D_Axis[i][j][1]) * focalLength;
                    } else {
                        x1 = koor3D_Axis[i][0][0];
                        z1 = koor3D_Axis[i][0][2];

                        x2 = koor3D_Axis[i][j][0];
                        z2 = koor3D_Axis[i][j][2];
                    }
                } else if (tandaViewBottomNav == 2) {
                    x1 = koor3D_AxisFix[i][0][0];
                    z1 = koor3D_AxisFix[i][0][1];

                    x2 = koor3D_AxisFix[i][j][0];
                    z2 = koor3D_AxisFix[i][j][1];
                } else if (tandaViewBottomNav == 3) {
                    x1 = koor3D_AxisFix[i][0][0];
                    z1 = koor3D_AxisFix[i][0][2];

                    x2 = koor3D_AxisFix[i][j][0];
                    z2 = koor3D_AxisFix[i][j][2];
                } else {
                    x1 = koor3D_AxisFix[i][0][1];
                    z1 = koor3D_AxisFix[i][0][2];

                    x2 = koor3D_AxisFix[i][j][1];
                    z2 = koor3D_AxisFix[i][j][2];
                }

                if (i == 0) {
                    pAxis.setColor(getApplicationContext().getResources().getColor(R.color.colorRedSolid));
                    pTextAxis.setColor(Color.RED);

                    canvas.scale(1, -1);
                    canvas.drawText("X", x1 + 5, -(z1 + 5), pTextAxis);
                    canvas.scale(1, -1);
                } else if (i == 1) {
                    pAxis.setColor(getApplicationContext().getResources().getColor(R.color.colorGreenSolid));
                    pTextAxis.setColor(Color.GREEN);

                    canvas.scale(1, -1);
                    canvas.drawText("Y", x1 + 5, -(z1 + 5), pTextAxis);
                    canvas.scale(1, -1);
                } else {
                    pAxis.setColor(getApplicationContext().getResources().getColor(R.color.colorBlueSolid));
                    pTextAxis.setColor(Color.BLUE);

                    canvas.scale(1, -1);
                    canvas.drawText("Z", x1 + 5, -(z1 + 5), pTextAxis);
                    canvas.scale(1, -1);
                }

                canvas.drawLine(x1, z1, x2, z2, pAxis);
            }
        }
    }

    private void drawFrame3D() {
        setArrowBackAndForward(false);
        canvas.drawColor(Color.parseColor("#FFFFFF"));

        float[][] koorNodeLokal;
        if (isDrawDisplacement) {
            koorNodeLokal = koorNodeDeformed;
        } else {
            koorNodeLokal = koorNode;
        }

        //LANTAI MERAH
        int x = (Nbx + 1) * (Nby + 1);
        int y = 1;
        int z = 1;

        for (float i : areaLoad) {
            float[] koor1 = koorNodeLokal[x];
            float[] koor2 = koorNodeLokal[x + 1];
            float[] koor3 = koorNodeLokal[x + Nbx + 1];
            float[] koor4 = koorNodeLokal[x + Nbx + 2];

            float koor_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
            float koor_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;
            path.moveTo(koor_X, koor_Z);

            koor_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
            koor_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;
            path.lineTo(koor_X, koor_Z);

            koor_X = koor4[0] / (focalLength + koor4[1]) * focalLength;
            koor_Z = koor4[2] / (focalLength + koor4[1]) * focalLength;
            path.lineTo(koor_X, koor_Z);

            koor_X = koor3[0] / (focalLength + koor3[1]) * focalLength;
            koor_Z = koor3[2] / (focalLength + koor3[1]) * focalLength;
            path.lineTo(koor_X, koor_Z);
            path.close();

            canvas.drawPath(path, pPath);
            path.reset();

            if (z == Nby && y == Nbx) {
                z = 1;
                y = 1;
                x += 2 + Nbx + 1;
            } else if (y == Nbx) {
                z++;
                y = 1;
                x += 2;
            } else {
                y++;
                x++;
            }
        }

        int counterElemen = 0;
        //BALOK XZ
        int L = 0;
        for (int[] pointer : pointerElemenBalokXZ) {
            pGaris.setStrokeWidth(8);
            if(drawLoadCurrent == 5){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElemen).getAmanPMAnalysis());
            }else if(drawLoadCurrent == 6){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElemen).getAmanVAnalysis());
            }else if(drawLoadCurrent == 8){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElemen).getAmanPMDesign());
            }else if(drawLoadCurrent == 9){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElemen).getAmanVDesign());
            }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

            float[] koor1 = koorNodeLokal[pointer[0]];
            float[] koor2 = koorNodeLokal[pointer[1]];

            float koor1_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
            float koor1_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;

            float koor2_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
            float koor2_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;

            canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pGaris);

            if (isShowElement) {
                canvas.scale(1, -1);

                float koorX = (koor1_X + koor2_X) / 2;
                float koorZ = (koor1_Z + koor2_Z) / 2;

                canvas.drawText(Integer.toString(pointerElemenKolom.length + counterElemen), koorX + 5, -(koorZ), pTextNodal);

                canvas.scale(1, -1);
            }
            counterElemen++;
        }

        counterElemen = 0;
        //BALOK YZ
        for (int[] pointer : pointerElemenBalokYZ) {
            pGaris.setStrokeWidth(8);
            if(drawLoadCurrent == 5){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElemen).getAmanPMAnalysis());
            }else if(drawLoadCurrent == 6){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElemen).getAmanVAnalysis());
            }else if(drawLoadCurrent == 8){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElemen).getAmanPMDesign());
            }else if(drawLoadCurrent == 9){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElemen).getAmanVDesign());
            }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

            float[] koor1 = koorNodeLokal[pointer[0]];
            float[] koor2 = koorNodeLokal[pointer[1]];

            float koor1_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
            float koor1_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;

            float koor2_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
            float koor2_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;

            canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pGaris);

            if (isShowElement) {
                canvas.scale(1, -1);

                float koorX = (koor1_X + koor2_X) / 2;
                float koorZ = (koor1_Z + koor2_Z) / 2;

                canvas.drawText(Integer.toString(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElemen), koorX + 5, -(koorZ), pTextNodal);

                canvas.scale(1, -1);
            }
            counterElemen++;
        }

        //KOLOM
        int nodalTumpuan = 0;
        counterElemen = 0;
        for (int[] pointer : pointerElemenKolom) {
            pGaris.setStrokeWidth(8);
            if(drawLoadCurrent == 5){
                setWarnaKeamanan(listBebanBatang.get(counterElemen).getAmanPMAnalysis());
            }else if(drawLoadCurrent == 6){
                setWarnaKeamanan(listBebanBatang.get(counterElemen).getAmanVAnalysis());
            }else if(drawLoadCurrent == 8){
                setWarnaKeamanan(listBebanBatang.get(counterElemen).getAmanPMDesign());
            }else if(drawLoadCurrent == 9){
                setWarnaKeamanan(listBebanBatang.get(counterElemen).getAmanVDesign());
            }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

            float[] koor1 = koorNodeLokal[pointer[0]];
            float[] koor2 = koorNodeLokal[pointer[1]];

            float koor1_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
            float koor1_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;

            float koor2_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
            float koor2_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;

            canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pGaris);

            if (isShowNodal) {
                canvas.scale(1, -1);
                canvas.drawText(Integer.toString(pointer[1]), koor2_X + 10, -(koor2_Z + 15), pTextNodal);
                canvas.scale(1, -1);
            }

            if (isShowNodal && nodalTumpuan < (Nbx + 1) * (Nby + 1)) {
                canvas.scale(1, -1);
                canvas.drawText(Integer.toString(pointer[0]), koor1_X + 10, -(koor1_Z + 15), pTextNodal);
                canvas.scale(1, -1);

                nodalTumpuan++;
            }

            if (isShowElement) {
                canvas.scale(1, -1);

                float koorX = (koor1_X + koor2_X) / 2;
                float koorZ = (koor1_Z + koor2_Z) / 2;

                canvas.drawText(Integer.toString(counterElemen), koorX + 5, -(koorZ), pTextNodal);

                canvas.scale(1, -1);
            }
            counterElemen++;
        }

        //TUMPUAN SENDI & JEPIT
        if (isRestraintPin) {
            for (int i = 0; i < pinRestraint.length; i++) {
                for (int j = 0; j < 6; j++) {
                    float[] koor1 = pinRestraint[i][j];
                    float[] koor2 = pinRestraint[i][j + 1];

                    float koor1_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
                    float koor1_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;

                    float koor2_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
                    float koor2_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;

                    canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pTumpuan);
                }
            }
        } else {
            for (int i = 0; i < tumpuanJepit1.length; i++) {
                for (int j = 0; j < 4; j++) {
                    float[] koor1 = tumpuanJepit1[i][j];
                    float[] koor2 = tumpuanJepit1[i][j + 1];

                    float koor1_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
                    float koor1_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;

                    float koor2_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
                    float koor2_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;

                    canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pTumpuan);
                }
            }

            for (int i = 0; i < tumpuanJepit2.length; i++) {
                for (int j = 0; j < 4; j++) {
                    float[] koor1 = tumpuanJepit2[i][j];
                    float[] koor2 = tumpuanJepit2[i][j + 1];

                    float koor1_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
                    float koor1_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;

                    float koor2_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
                    float koor2_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;

                    canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pTumpuan);
                }
            }
        }

        //GRID BAWAH X
        x = 0;
        y = 0;
        for (int i = 1; i <= Nbx * (Nby + 1); i++) {
            float[] koor1 = koorNodeLokal[x];
            float[] koor2 = koorNodeLokal[x + 1];

            float koor1_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
            float koor1_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;

            float koor2_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
            float koor2_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;

            canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pGarisGray);

            if (y == Nbx - 1) {
                y = 0;
                x += 2;
            } else {
                y++;
                x++;
            }
        }

        //GRID BAWAH Y
        for (int i = 0; i < Nby * (Nbx + 1); i++) {
            float[] koor1 = koorNodeLokal[i];
            float[] koor2 = koorNodeLokal[i + Nbx + 1];

            float koor1_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
            float koor1_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;

            float koor2_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
            float koor2_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;

            canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pGarisGray);
        }

        draw3D_Axis();
        if (!isDrawDisplacement) {
            drawAreaXYSelected();
            drawNodeSelected();

            if (isTimerSleep) {
                if (isDrawLoadCurrent) {
                    if (drawLoadCurrent == 1) {
                        drawJointLoad();

                    } else if (drawLoadCurrent == 2) {
                        drawFrameLoad();

                    } else if (drawLoadCurrent == 3) {
                        drawAreaLoad();

                    } else if (drawLoadCurrent == 4) {
                        drawFrameSectionText();

                    } else if (drawLoadCurrent == 5) {
                        drawSFPMText();

                    } else if (drawLoadCurrent == 6) {
                        drawSFShearText();

                    } else if (drawLoadCurrent == 7) {
                        drawDesignSectionText();

                    } else if (drawLoadCurrent == 8) {
                        drawSFPMText();

                    } else if (drawLoadCurrent == 9) {
                        drawSFShearText();
                    }
                } else {
                    if (drawInternalForceCurrent == 1) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_AxialKolom, IF_AxialKolomFix, IF_AxialKolomNodal, isFirstPositif_AxialKolom);
                            drawIF_GeneralBalokXWarna(IF_AxialBalokX, IF_AxialBalokXFix, IF_AxialBalokXNodal, isFirstPositif_AxialBalokX);
                            drawIF_GeneralBalokYWarna(IF_AxialBalokY, IF_AxialBalokYFix, IF_AxialBalokYNodal, isFirstPositif_AxialBalokY);

                        } else {
                            drawIF_GeneralKolom(IF_AxialKolom, IF_AxialKolomFix, 0);
                            drawIF_GeneralBalokX(IF_AxialBalokX, IF_AxialBalokXFix, 0);
                            drawIF_GeneralBalokY(IF_AxialBalokY, IF_AxialBalokYFix, 0);
                        }


                    } else if (drawInternalForceCurrent == 2) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Shear22Kolom, IF_Shear22KolomFix, IF_Shear22KolomNodal, isFirstPositif_Shear22Kolom);
                            drawIF_GeneralBalokXWarna(IF_Shear22BalokX, IF_Shear22BalokXFix, IF_Shear22BalokXNodal, isFirstPositif_Shear22BalokX);
                            drawIF_GeneralBalokYWarna(IF_Shear22BalokY, IF_Shear22BalokYFix, IF_Shear22BalokYNodal, isFirstPositif_Shear22BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Shear22Kolom, IF_Shear22KolomFix, 1);
                            drawIF_GeneralBalokX(IF_Shear22BalokX, IF_Shear22BalokXFix, 1);
                            drawIF_GeneralBalokY(IF_Shear22BalokY, IF_Shear22BalokYFix, 1);
                        }

                    } else if (drawInternalForceCurrent == 3) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Shear33Kolom, IF_Shear33KolomFix, IF_Shear33KolomNodal, isFirstPositif_Shear33Kolom);
                            drawIF_GeneralBalokXWarna(IF_Shear33BalokX, IF_Shear33BalokXFix, IF_Shear33BalokXNodal, isFirstPositif_Shear33BalokX);
                            drawIF_GeneralBalokYWarna(IF_Shear33BalokY, IF_Shear33BalokYFix, IF_Shear33BalokYNodal, isFirstPositif_Shear33BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Shear33Kolom, IF_Shear33KolomFix, 2);
                            drawIF_GeneralBalokX(IF_Shear33BalokX, IF_Shear33BalokXFix, 2);
                            drawIF_GeneralBalokY(IF_Shear33BalokY, IF_Shear33BalokYFix, 2);
                        }
                    } else if (drawInternalForceCurrent == 4) {
                        if (isIF_Berwarna) {
                            drawIF_TorsiKolomWarna();
                            drawIF_TorsiBalokXWarna();
                            drawIF_TorsiBalokYWarna();

                        } else {
                            drawIF_TorsiKolom();
                            drawIF_TorsiBalokX();
                            drawIF_TorsiBalokY();
                        }

                    } else if (drawInternalForceCurrent == 5) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Momen22Kolom, IF_Momen22KolomFix, IF_Momen22KolomNodal, isFirstPositif_Momen22Kolom);
                            drawIF_GeneralBalokXWarna(IF_Momen22BalokX, IF_Momen22BalokXFix, IF_Momen22BalokXNodal, isFirstPositif_Momen22BalokX);
                            drawIF_GeneralBalokYWarna(IF_Momen22BalokY, IF_Momen22BalokYFix, IF_Momen22BalokYNodal, isFirstPositif_Momen22BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Momen22Kolom, IF_Momen22KolomFix, 4);
                            drawIF_GeneralBalokX(IF_Momen22BalokX, IF_Momen22BalokXFix, 4);
                            drawIF_GeneralBalokY(IF_Momen22BalokY, IF_Momen22BalokYFix, 4);
                        }

                    } else if (drawInternalForceCurrent == 6) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Momen33Kolom, IF_Momen33KolomFix, IF_Momen33KolomNodal, isFirstPositif_Momen33Kolom);
                            drawIF_GeneralBalokXWarna(IF_Momen33BalokX, IF_Momen33BalokXFix, IF_Momen33BalokXNodal, isFirstPositif_Momen33BalokX);
                            drawIF_GeneralBalokYWarna(IF_Momen33BalokY, IF_Momen33BalokYFix, IF_Momen33BalokYNodal, isFirstPositif_Momen33BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Momen33Kolom, IF_Momen33KolomFix, 5);
                            drawIF_GeneralBalokX(IF_Momen33BalokX, IF_Momen33BalokXFix, 5);
                            drawIF_GeneralBalokY(IF_Momen33BalokY, IF_Momen33BalokYFix, 5);
                        }

                    } else if (drawInternalForceCurrent == 7) {
                        drawTextReaction();
                    }
                }
            }

            drawFrame3D_ElemenTap();
        } else {
            if (isShowDisplacementValue) {
                drawTextDisplacement();
            }
        }

        imgCanvas.setImageBitmap(bitmap);
    }

    private void drawFrame3DOrtho() {
        canvas.drawColor(Color.parseColor("#FFFFFF"));

        float[][] koorNodeLokal;
        if (isDrawDisplacement) {
            koorNodeLokal = koorNodeDeformed;
        } else {
            koorNodeLokal = koorNode;
        }

        //LANTAI MERAH
        int x = (Nbx + 1) * (Nby + 1);
        int y = 1;
        int z = 1;

        for (float i : areaLoad) {
            float[] koor1 = koorNodeLokal[x];
            float[] koor2 = koorNodeLokal[x + 1];
            float[] koor3 = koorNodeLokal[x + Nbx + 1];
            float[] koor4 = koorNodeLokal[x + Nbx + 2];

            path.moveTo(koor1[0], koor1[2]);
            path.lineTo(koor2[0], koor2[2]);
            path.lineTo(koor4[0], koor4[2]);
            path.lineTo(koor3[0], koor3[2]);
            path.close();

            canvas.drawPath(path, pPath);
            path.reset();

            if (z == Nby && y == Nbx) {
                z = 1;
                y = 1;
                x += 2 + Nbx + 1;
            } else if (y == Nbx) {
                z++;
                y = 1;
                x += 2;
            } else {
                y++;
                x++;
            }
        }

        //BALOK XZ
        int counterElemen = 0;
        for (int[] pointer : pointerElemenBalokXZ) {
            pGaris.setStrokeWidth(8);
            if(drawLoadCurrent == 5){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElemen).getAmanPMAnalysis());
            }else if(drawLoadCurrent == 6){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElemen).getAmanVAnalysis());
            }else if(drawLoadCurrent == 8){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElemen).getAmanPMDesign());
            }else if(drawLoadCurrent == 9){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElemen).getAmanVDesign());
            }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

            float[] koor1 = koorNodeLokal[pointer[0]];
            float[] koor2 = koorNodeLokal[pointer[1]];

            canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pGaris);

            if (isShowElement) {
                canvas.scale(1, -1);

                float koorX = (koor1[0] + koor2[0]) / 2;
                float koorZ = (koor1[2] + koor2[2]) / 2;

                canvas.drawText(Integer.toString(pointerElemenKolom.length + counterElemen), koorX + 5, -(koorZ), pTextNodal);

                canvas.scale(1, -1);
            }
            counterElemen++;
        }

        //BALOK YZ
        counterElemen = 0;
        for (int[] pointer : pointerElemenBalokYZ) {
            pGaris.setStrokeWidth(8);
            if(drawLoadCurrent == 5){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElemen).getAmanPMAnalysis());
            }else if(drawLoadCurrent == 6){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElemen).getAmanVAnalysis());
            }else if(drawLoadCurrent == 8){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElemen).getAmanPMDesign());
            }else if(drawLoadCurrent == 9){
                setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElemen).getAmanVDesign());
            }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

            float[] koor1 = koorNodeLokal[pointer[0]];
            float[] koor2 = koorNodeLokal[pointer[1]];

            canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pGaris);

            if (isShowElement) {
                canvas.scale(1, -1);

                float koorX = (koor1[0] + koor2[0]) / 2;
                float koorZ = (koor1[2] + koor2[2]) / 2;

                canvas.drawText(Integer.toString(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElemen), koorX + 5, -(koorZ), pTextNodal);

                canvas.scale(1, -1);
            }
            counterElemen++;
        }

        //KOLOM
        int nodalTumpuan = 0;
        for (int[] pointer : pointerElemenKolom) {
            pGaris.setStrokeWidth(8);
            if(drawLoadCurrent == 5){
                setWarnaKeamanan(listBebanBatang.get(counterElemen).getAmanPMAnalysis());
            }else if(drawLoadCurrent == 6){
                setWarnaKeamanan(listBebanBatang.get(counterElemen).getAmanVAnalysis());
            }else if(drawLoadCurrent == 8){
                setWarnaKeamanan(listBebanBatang.get(counterElemen).getAmanPMDesign());
            }else if(drawLoadCurrent == 9){
                setWarnaKeamanan(listBebanBatang.get(counterElemen).getAmanVDesign());
            }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

            float[] koor1 = koorNodeLokal[pointer[0]];
            float[] koor2 = koorNodeLokal[pointer[1]];

            canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pGaris);

            if (isShowNodal) {
                canvas.scale(1, -1);
                canvas.drawText(Integer.toString(pointer[1]), koor2[0] + 10, -(koor2[2] + 15), pTextNodal);
                canvas.scale(1, -1);
            }

            if (isShowNodal && nodalTumpuan < (Nbx + 1) * (Nby + 1)) {
                canvas.scale(1, -1);
                canvas.drawText(Integer.toString(pointer[0]), koor1[0] + 10, -(koor1[2] + 15), pTextNodal);
                canvas.scale(1, -1);

                nodalTumpuan++;
            }

            if (isShowElement) {
                canvas.scale(1, -1);

                float koorX = (koor1[0] + koor2[0]) / 2;
                float koorZ = (koor1[2] + koor2[2]) / 2;

                canvas.drawText(Integer.toString(counterElemen), koorX + 5, -(koorZ), pTextNodal);

                canvas.scale(1, -1);
            }
            counterElemen++;
        }

        //TUMPUAN SENDI & JEPIT
        if (isRestraintPin) {
            for (int i = 0; i < pinRestraint.length; i++) {
                for (int j = 0; j < 3; j++) {
                    float[] koor1 = pinRestraint[i][j];
                    float[] koor2 = pinRestraint[i][j + 1];

                    canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pTumpuan);
                }
            }

            for (int i = 0; i < tumpuanSendi2.length; i++) {
                for (int j = 0; j < 3; j++) {
                    float[] koor1 = tumpuanSendi2[i][j];
                    float[] koor2 = tumpuanSendi2[i][j + 1];

                    canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pTumpuan);
                }
            }
        } else {
            for (int i = 0; i < tumpuanJepit1.length; i++) {
                for (int j = 0; j < 4; j++) {
                    float[] koor1 = tumpuanJepit1[i][j];
                    float[] koor2 = tumpuanJepit1[i][j + 1];

                    canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pTumpuan);
                }
            }

            for (int i = 0; i < tumpuanJepit2.length; i++) {
                for (int j = 0; j < 4; j++) {
                    float[] koor1 = tumpuanJepit2[i][j];
                    float[] koor2 = tumpuanJepit2[i][j + 1];

                    canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pTumpuan);
                }
            }
        }

        //GRID BAWAH X
        x = 0;
        y = 0;
        for (int i = 1; i <= Nbx * (Nby + 1); i++) {
            float[] koor1 = koorNodeLokal[x];
            float[] koor2 = koorNodeLokal[x + 1];

            float koor1_X = koor1[0];
            float koor1_Z = koor1[2];

            float koor2_X = koor2[0];
            float koor2_Z = koor2[2];

            canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pGarisGray);

            if (y == Nbx - 1) {
                y = 0;
                x += 2;
            } else {
                y++;
                x++;
            }
        }

        //GRID BAWAH Y
        for (int i = 0; i < Nby * (Nbx + 1); i++) {
            float[] koor1 = koorNodeLokal[i];
            float[] koor2 = koorNodeLokal[i + Nbx + 1];

            float koor1_X = koor1[0];
            float koor1_Z = koor1[2];

            float koor2_X = koor2[0];
            float koor2_Z = koor2[2];

            canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pGarisGray);
        }

        draw3D_Axis();

        if (!isDrawDisplacement) {
            drawAreaXYSelected();
            drawNodeSelected();
            if (isTimerSleep) {
                if (isDrawLoadCurrent) {
                    if (drawLoadCurrent == 1) {
                        drawJointLoad();
                    } else if (drawLoadCurrent == 2) {
                        drawFrameLoad();
                    } else if (drawLoadCurrent == 3) {
                        drawAreaLoad();
                    } else if (drawLoadCurrent == 4) {
                        drawFrameSectionText();
                    } else if (drawLoadCurrent == 5) {
                        drawSFPMText();
                    } else if (drawLoadCurrent == 6) {
                        drawSFShearText();
                    } else if (drawLoadCurrent == 7) {
                        drawDesignSectionText();
                    } else if (drawLoadCurrent == 8) {
                        drawSFPMText();
                    } else if (drawLoadCurrent == 9) {
                        drawSFShearText();
                    }
                } else {
                    if (drawInternalForceCurrent == 1) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_AxialKolom, IF_AxialKolomFix, IF_AxialKolomNodal, isFirstPositif_AxialKolom);
                            drawIF_GeneralBalokXWarna(IF_AxialBalokX, IF_AxialBalokXFix, IF_AxialBalokXNodal, isFirstPositif_AxialBalokX);
                            drawIF_GeneralBalokYWarna(IF_AxialBalokY, IF_AxialBalokYFix, IF_AxialBalokYNodal, isFirstPositif_AxialBalokY);

                        } else {
                            drawIF_GeneralKolom(IF_AxialKolom, IF_AxialKolomFix, 0);
                            drawIF_GeneralBalokX(IF_AxialBalokX, IF_AxialBalokXFix, 0);
                            drawIF_GeneralBalokY(IF_AxialBalokY, IF_AxialBalokYFix, 0);
                        }


                    } else if (drawInternalForceCurrent == 2) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Shear22Kolom, IF_Shear22KolomFix, IF_Shear22KolomNodal, isFirstPositif_Shear22Kolom);
                            drawIF_GeneralBalokXWarna(IF_Shear22BalokX, IF_Shear22BalokXFix, IF_Shear22BalokXNodal, isFirstPositif_Shear22BalokX);
                            drawIF_GeneralBalokYWarna(IF_Shear22BalokY, IF_Shear22BalokYFix, IF_Shear22BalokYNodal, isFirstPositif_Shear22BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Shear22Kolom, IF_Shear22KolomFix, 1);
                            drawIF_GeneralBalokX(IF_Shear22BalokX, IF_Shear22BalokXFix, 1);
                            drawIF_GeneralBalokY(IF_Shear22BalokY, IF_Shear22BalokYFix, 1);
                        }

                    } else if (drawInternalForceCurrent == 3) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Shear33Kolom, IF_Shear33KolomFix, IF_Shear33KolomNodal, isFirstPositif_Shear33Kolom);
                            drawIF_GeneralBalokXWarna(IF_Shear33BalokX, IF_Shear33BalokXFix, IF_Shear33BalokXNodal, isFirstPositif_Shear33BalokX);
                            drawIF_GeneralBalokYWarna(IF_Shear33BalokY, IF_Shear33BalokYFix, IF_Shear33BalokYNodal, isFirstPositif_Shear33BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Shear33Kolom, IF_Shear33KolomFix, 2);
                            drawIF_GeneralBalokX(IF_Shear33BalokX, IF_Shear33BalokXFix, 2);
                            drawIF_GeneralBalokY(IF_Shear33BalokY, IF_Shear33BalokYFix, 2);
                        }
                    } else if (drawInternalForceCurrent == 4) {
                        if (isIF_Berwarna) {
                            drawIF_TorsiKolomWarna();
                            drawIF_TorsiBalokXWarna();
                            drawIF_TorsiBalokYWarna();

                        } else {
                            drawIF_TorsiKolom();
                            drawIF_TorsiBalokX();
                            drawIF_TorsiBalokY();
                        }

                    } else if (drawInternalForceCurrent == 5) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Momen22Kolom, IF_Momen22KolomFix, IF_Momen22KolomNodal, isFirstPositif_Momen22Kolom);
                            drawIF_GeneralBalokXWarna(IF_Momen22BalokX, IF_Momen22BalokXFix, IF_Momen22BalokXNodal, isFirstPositif_Momen22BalokX);
                            drawIF_GeneralBalokYWarna(IF_Momen22BalokY, IF_Momen22BalokYFix, IF_Momen22BalokYNodal, isFirstPositif_Momen22BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Momen22Kolom, IF_Momen22KolomFix, 4);
                            drawIF_GeneralBalokX(IF_Momen22BalokX, IF_Momen22BalokXFix, 4);
                            drawIF_GeneralBalokY(IF_Momen22BalokY, IF_Momen22BalokYFix, 4);
                        }

                    } else if (drawInternalForceCurrent == 6) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Momen33Kolom, IF_Momen33KolomFix, IF_Momen33KolomNodal, isFirstPositif_Momen33Kolom);
                            drawIF_GeneralBalokXWarna(IF_Momen33BalokX, IF_Momen33BalokXFix, IF_Momen33BalokXNodal, isFirstPositif_Momen33BalokX);
                            drawIF_GeneralBalokYWarna(IF_Momen33BalokY, IF_Momen33BalokYFix, IF_Momen33BalokYNodal, isFirstPositif_Momen33BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Momen33Kolom, IF_Momen33KolomFix, 5);
                            drawIF_GeneralBalokX(IF_Momen33BalokX, IF_Momen33BalokXFix, 5);
                            drawIF_GeneralBalokY(IF_Momen33BalokY, IF_Momen33BalokYFix, 5);
                        }

                    } else if (drawInternalForceCurrent == 7) {
                        drawTextReaction();
                    }
                }

            }

            drawFrame3D_ElemenTapOrtho();
        } else {
            if (isShowDisplacementValue) {
                drawTextDisplacement();
            }
        }

        imgCanvas.setImageBitmap(bitmap);
    }

    private void drawFrameXY() {
        canvas.drawColor(Color.parseColor("#FFFFFF"));

        float[][] koorNodeLokal;
        if (isDrawDisplacement) {
            koorNodeLokal = koorNodeDeformedFix;
        } else {
            koorNodeLokal = koorNodeFix;
        }

        //LANTAI MERAH
        if (viewXY_Current != 1) {
            int x = (viewXY_Current - 1) * (Nbx + 1) * (Nby + 1);
            int y = 1;
            for (int i = 0; i < Nbx * Nby; i++) {
                float[] koor1 = koorNodeLokal[x];
                float[] koor2 = koorNodeLokal[x + 1];
                float[] koor3 = koorNodeLokal[x + Nbx + 1];
                float[] koor4 = koorNodeLokal[x + Nbx + 2];

                path.moveTo(koor1[0], koor1[1]);
                path.lineTo(koor2[0], koor2[1]);
                path.lineTo(koor4[0], koor4[1]);
                path.lineTo(koor3[0], koor3[1]);
                path.close();

                canvas.drawPath(path, pPath);
                path.reset();

                if (y == Nbx) {
                    y = 1;
                    x += 2;
                } else {
                    y++;
                    x++;
                }
            }

        }

        //BALOK XZ
        int x = 0;
        if (viewXY_Current != 1) {
            for (int[] pointer : pointerElemenBalokXZ) {
                if (x - (viewXY_Current - 2) * (Nby + 1) * Nbx >= 0 && x - (viewXY_Current - 2) * (Nby + 1) * Nbx < (Nby + 1) * Nbx) {
                    pGaris.setStrokeWidth(8);
                    if(drawLoadCurrent == 5){
                        setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + x).getAmanPMAnalysis());
                    }else if(drawLoadCurrent == 6){
                        setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + x).getAmanVAnalysis());
                    }else if(drawLoadCurrent == 8){
                        setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + x).getAmanPMDesign());
                    }else if(drawLoadCurrent == 9){
                        setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + x).getAmanVDesign());
                    }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

                    float[] koor1 = koorNodeLokal[pointer[0]];
                    float[] koor2 = koorNodeLokal[pointer[1]];

                    canvas.drawLine(koor1[0], koor1[1], koor2[0], koor2[1], pGaris);

                    if (isShowElement) {
                        canvas.scale(1, -1);

                        float koorX = (koor1[0] + koor2[0]) / 2;
                        float koorZ = (koor1[1] + koor2[1]) / 2;

                        canvas.drawText(Integer.toString(pointerElemenKolom.length + x), koorX + 5, -(koorZ + 5), pTextNodal);

                        canvas.scale(1, -1);
                    }
                }
                x++;
            }
        }

        //BALOK YZ
        x = 0;
        if (viewXY_Current != 1) {
            for (int[] pointer : pointerElemenBalokYZ) {
                if (x - (viewXY_Current - 2) * (Nbx + 1) * Nby >= 0 && x - (viewXY_Current - 2) * (Nbx + 1) * Nby < (Nbx + 1) * Nby) {
                    pGaris.setStrokeWidth(8);
                    if(drawLoadCurrent == 5){
                        setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + x).getAmanPMAnalysis());
                    }else if(drawLoadCurrent == 6){
                        setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + x).getAmanVAnalysis());
                    }else if(drawLoadCurrent == 8){
                        setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + x).getAmanPMDesign());
                    }else if(drawLoadCurrent == 9){
                        setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + x).getAmanVDesign());
                    }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

                    float[] koor1 = koorNodeLokal[pointer[0]];
                    float[] koor2 = koorNodeLokal[pointer[1]];

                    canvas.drawLine(koor1[0], koor1[1], koor2[0], koor2[1], pGaris);

                    if (isShowNodal) {
                        canvas.scale(1, -1);
                        canvas.drawText(Integer.toString(pointer[0]), koor1[0] + 10, -(koor1[1] + 15), pTextNodal);
                        canvas.drawText(Integer.toString(pointer[1]), koor2[0] + 10, -(koor2[1] + 15), pTextNodal);
                        canvas.scale(1, -1);
                    }

                    if (isShowElement) {
                        canvas.scale(1, -1);

                        float koorX = (koor1[0] + koor2[0]) / 2;
                        float koorZ = (koor1[1] + koor2[1]) / 2;

                        canvas.drawText(Integer.toString(pointerElemenKolom.length + pointerElemenBalokXZ.length + x), koorX + 5, -(koorZ), pTextNodal);

                        canvas.scale(1, -1);
                    }
                }
                x++;
            }
        }

        //Tanda Axis
        String tandaAxis;
        tandaAxis = "Z = " + koorNodeReal[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2] / 1000 + " m";
        txtTandaAxis.setText(tandaAxis);
        txtTandaAxis.setVisibility(View.VISIBLE);

        if (viewXY_Current == 1) {
            //GRID BAWAH X
            x = 0;
            int y = 0;
            for (int i = 1; i <= Nbx * (Nby + 1); i++) {
                float[] koor1 = koorNodeLokal[x];
                float[] koor2 = koorNodeLokal[x + 1];

                float koor1_X = koor1[0];
                float koor1_Z = koor1[1];

                float koor2_X = koor2[0];
                float koor2_Z = koor2[1];

                canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pGarisGray);

                if (isShowNodal) {
                    canvas.scale(1, -1);
                    canvas.drawText(Integer.toString(x), koor1[0] + 10, -(koor1[1] + 15), pTextNodal);
                    canvas.scale(1, -1);
                }

                if (y == Nbx - 1) {
                    if (isShowNodal) {
                        canvas.scale(1, -1);
                        canvas.drawText(Integer.toString(x + 1), koor2[0] + 10, -(koor2[1] + 15), pTextNodal);
                        canvas.scale(1, -1);
                    }

                    y = 0;
                    x += 2;
                } else {
                    y++;
                    x++;
                }
            }

            //GRID BAWAH Y
            for (int i = 0; i < Nby * (Nbx + 1); i++) {
                float[] koor1 = koorNodeLokal[i];
                float[] koor2 = koorNodeLokal[i + Nbx + 1];

                float koor1_X = koor1[0];
                float koor1_Z = koor1[1];

                float koor2_X = koor2[0];
                float koor2_Z = koor2[1];

                canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pGarisGray);
            }

            //TUMPUAN SENDI & JEPIT
            if (isRestraintPin) {
                for (int i = 0; i < (Nbx + 1) * (Nby + 1); i++) {
                    for (int j = 0; j < 3; j++) {
                        float[] koor1 = pinRestraintFix[i][j];
                        float[] koor2 = pinRestraintFix[i][j + 1];

                        canvas.drawLine(koor1[0], koor1[1], koor2[0], koor2[1], pTumpuan);

                        koor1 = tumpuanSendi2Fix[i][j];
                        koor2 = tumpuanSendi2Fix[i][j + 1];

                        canvas.drawLine(koor1[0], koor1[1], koor2[0], koor2[1], pTumpuan);
                    }
                }
            } else {
                for (int i = 0; i < (Nbx + 1) * (Nby + 1); i++) {
                    for (int j = 0; j < 4; j++) {
                        float[] koor1 = tumpuanJepit1Fix[i][j];
                        float[] koor2 = tumpuanJepit1Fix[i][j + 1];

                        canvas.drawLine(koor1[0], koor1[1], koor2[0], koor2[1], pTumpuan);

                        koor1 = tumpuanJepit2Fix[i][j];
                        koor2 = tumpuanJepit2Fix[i][j + 1];

                        canvas.drawLine(koor1[0], koor1[1], koor2[0], koor2[1], pTumpuan);
                    }
                }
            }
        }

        draw3D_Axis();

        if (!isDrawDisplacement) {
            drawAreaXYSelected();
            drawNodeSelected();
            if (isTimerSleep) {
                if (isDrawLoadCurrent) {
                    if (drawLoadCurrent == 1) {
                        drawJointLoad();
                    } else if (drawLoadCurrent == 2) {
                        drawFrameLoad();
                    } else if (drawLoadCurrent == 3) {
                        drawAreaLoad();
                    } else if (drawLoadCurrent == 4) {
                        drawFrameSectionText();
                    } else if (drawLoadCurrent == 5) {
                        drawSFPMText();
                    } else if (drawLoadCurrent == 6) {
                        drawSFShearText();
                    } else if (drawLoadCurrent == 7) {
                        drawDesignSectionText();
                    } else if (drawLoadCurrent == 8) {
                        drawSFPMText();
                    } else if (drawLoadCurrent == 9) {
                        drawSFShearText();
                    }
                } else {
                    if (drawInternalForceCurrent == 1) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_AxialKolom, IF_AxialKolomFix, IF_AxialKolomNodal, isFirstPositif_AxialKolom);
                            drawIF_GeneralBalokXWarna(IF_AxialBalokX, IF_AxialBalokXFix, IF_AxialBalokXNodal, isFirstPositif_AxialBalokX);
                            drawIF_GeneralBalokYWarna(IF_AxialBalokY, IF_AxialBalokYFix, IF_AxialBalokYNodal, isFirstPositif_AxialBalokY);

                        } else {
                            drawIF_GeneralKolom(IF_AxialKolom, IF_AxialKolomFix, 0);
                            drawIF_GeneralBalokX(IF_AxialBalokX, IF_AxialBalokXFix, 0);
                            drawIF_GeneralBalokY(IF_AxialBalokY, IF_AxialBalokYFix, 0);
                        }


                    } else if (drawInternalForceCurrent == 2) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Shear22Kolom, IF_Shear22KolomFix, IF_Shear22KolomNodal, isFirstPositif_Shear22Kolom);
                            drawIF_GeneralBalokXWarna(IF_Shear22BalokX, IF_Shear22BalokXFix, IF_Shear22BalokXNodal, isFirstPositif_Shear22BalokX);
                            drawIF_GeneralBalokYWarna(IF_Shear22BalokY, IF_Shear22BalokYFix, IF_Shear22BalokYNodal, isFirstPositif_Shear22BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Shear22Kolom, IF_Shear22KolomFix, 1);
                            drawIF_GeneralBalokX(IF_Shear22BalokX, IF_Shear22BalokXFix, 1);
                            drawIF_GeneralBalokY(IF_Shear22BalokY, IF_Shear22BalokYFix, 1);
                        }

                    } else if (drawInternalForceCurrent == 3) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Shear33Kolom, IF_Shear33KolomFix, IF_Shear33KolomNodal, isFirstPositif_Shear33Kolom);
                            drawIF_GeneralBalokXWarna(IF_Shear33BalokX, IF_Shear33BalokXFix, IF_Shear33BalokXNodal, isFirstPositif_Shear33BalokX);
                            drawIF_GeneralBalokYWarna(IF_Shear33BalokY, IF_Shear33BalokYFix, IF_Shear33BalokYNodal, isFirstPositif_Shear33BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Shear33Kolom, IF_Shear33KolomFix, 2);
                            drawIF_GeneralBalokX(IF_Shear33BalokX, IF_Shear33BalokXFix, 2);
                            drawIF_GeneralBalokY(IF_Shear33BalokY, IF_Shear33BalokYFix, 2);
                        }
                    } else if (drawInternalForceCurrent == 4) {
                        if (isIF_Berwarna) {
                            drawIF_TorsiKolomWarna();
                            drawIF_TorsiBalokXWarna();
                            drawIF_TorsiBalokYWarna();

                        } else {
                            drawIF_TorsiKolom();
                            drawIF_TorsiBalokX();
                            drawIF_TorsiBalokY();
                        }

                    } else if (drawInternalForceCurrent == 5) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Momen22Kolom, IF_Momen22KolomFix, IF_Momen22KolomNodal, isFirstPositif_Momen22Kolom);
                            drawIF_GeneralBalokXWarna(IF_Momen22BalokX, IF_Momen22BalokXFix, IF_Momen22BalokXNodal, isFirstPositif_Momen22BalokX);
                            drawIF_GeneralBalokYWarna(IF_Momen22BalokY, IF_Momen22BalokYFix, IF_Momen22BalokYNodal, isFirstPositif_Momen22BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Momen22Kolom, IF_Momen22KolomFix, 4);
                            drawIF_GeneralBalokX(IF_Momen22BalokX, IF_Momen22BalokXFix, 4);
                            drawIF_GeneralBalokY(IF_Momen22BalokY, IF_Momen22BalokYFix, 4);
                        }

                    } else if (drawInternalForceCurrent == 6) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Momen33Kolom, IF_Momen33KolomFix, IF_Momen33KolomNodal, isFirstPositif_Momen33Kolom);
                            drawIF_GeneralBalokXWarna(IF_Momen33BalokX, IF_Momen33BalokXFix, IF_Momen33BalokXNodal, isFirstPositif_Momen33BalokX);
                            drawIF_GeneralBalokYWarna(IF_Momen33BalokY, IF_Momen33BalokYFix, IF_Momen33BalokYNodal, isFirstPositif_Momen33BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Momen33Kolom, IF_Momen33KolomFix, 5);
                            drawIF_GeneralBalokX(IF_Momen33BalokX, IF_Momen33BalokXFix, 5);
                            drawIF_GeneralBalokY(IF_Momen33BalokY, IF_Momen33BalokYFix, 5);
                        }

                    } else if (drawInternalForceCurrent == 7) {
                        drawTextReaction();
                    }
                }

            }
            drawFrameXY_ElemenTap();
        } else {
            if (isShowDisplacementValue) {
                drawTextDisplacement();
            }
        }

        imgCanvas.setImageBitmap(bitmap);
    }

    private void drawFrameXZ() {
        canvas.drawColor(Color.parseColor("#FFFFFF"));

        float[][] koorNodeLokal;
        if (isDrawDisplacement) {
            koorNodeLokal = koorNodeDeformedFix;
        } else {
            koorNodeLokal = koorNodeFix;
        }

        //BALOK
        int counterElement = 0;
        int x = 0;
        for (int[] pointer : pointerElemenBalokXZ) {
            if (x - (viewXZ_Current - 1) * Nbx >= 0 && x - (viewXZ_Current - 1) * Nbx < Nbx) {
                pGaris.setStrokeWidth(8);
                if(drawLoadCurrent == 5){
                    setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElement).getAmanPMAnalysis());
                }else if(drawLoadCurrent == 6){
                    setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElement).getAmanVAnalysis());
                }else if(drawLoadCurrent == 8){
                    setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElement).getAmanPMDesign());
                }else if(drawLoadCurrent == 9){
                    setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + counterElement).getAmanVDesign());
                }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

                float[] koor1 = koorNodeLokal[pointer[0]];
                float[] koor2 = koorNodeLokal[pointer[1]];

                canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pGaris);

                if (isShowElement) {
                    canvas.scale(1, -1);

                    float koorX = (koor1[0] + koor2[0]) / 2;
                    float koorZ = (koor1[2] + koor2[2]) / 2;

                    canvas.drawText(Integer.toString(pointerElemenKolom.length + counterElement), koorX + 5, -(koorZ + 5), pTextNodal);

                    canvas.scale(1, -1);
                }

                x++;
            } else if (x == Nbx * (Nby + 1) - 1) {
                x = 0;
            } else {
                x++;
            }

            if (viewXZ_Current == viewXZ_Max) {
                if (x - 1 == Nbx * (Nby + 1) - 1) {
                    x = 0;
                }
            }

            counterElement++;
        }

        //KOLOM
        counterElement = 0;
        x = 0;
        int y = 0;
        for (int[] pointer : pointerElemenKolom) {
            if (x - (viewXZ_Current - 1) * (Nbx + 1) >= 0 && x - (viewXZ_Current - 1) * (Nbx + 1) <= Nbx) {
                pGaris.setStrokeWidth(8);
                if(drawLoadCurrent == 5){
                    setWarnaKeamanan(listBebanBatang.get(counterElement).getAmanPMAnalysis());
                }else if(drawLoadCurrent == 6){
                    setWarnaKeamanan(listBebanBatang.get(counterElement).getAmanVAnalysis());
                }else if(drawLoadCurrent == 8){
                    setWarnaKeamanan(listBebanBatang.get(counterElement).getAmanPMDesign());
                }else if(drawLoadCurrent == 9){
                    setWarnaKeamanan(listBebanBatang.get(counterElement).getAmanVDesign());
                }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

                float[] koor1 = koorNodeLokal[pointer[0]];
                float[] koor2 = koorNodeLokal[pointer[1]];

                canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pGaris);

                if (isShowNodal) {
                    canvas.scale(1, -1);
                    canvas.drawText(Integer.toString(pointer[0]), koor1[0] + 10, -(koor1[2] + 15), pTextNodal);
                    canvas.drawText(Integer.toString(pointer[1]), koor2[0] + 10, -(koor2[2] + 15), pTextNodal);
                    canvas.scale(1, -1);
                }

                if (isShowElement) {
                    canvas.scale(1, -1);

                    float koorX = (koor1[0] + koor2[0]) / 2;
                    float koorZ = (koor1[2] + koor2[2]) / 2;

                    canvas.drawText(Integer.toString(counterElement), koorX + 5, -(koorZ), pTextNodal);

                    canvas.scale(1, -1);
                }

                y++;
                x++;
            } else if (x == ((Nbx + 1) * (Nby + 1) - 1)) {
                x = 0;
            } else {
                x++;
            }

            if (viewXZ_Current == viewXZ_Max) {
                if (x - 1 == ((Nbx + 1) * (Nby + 1) - 1)) {
                    x = 0;
                }
            }

            counterElement++;
        }

        //Tanda Axis
        String tandaAxis;
        tandaAxis = "Y = " + koorNodeReal[(viewXZ_Current - 1) * (Nbx + 1)][1] / 1000 + " m";
        txtTandaAxis.setText(tandaAxis);
        txtTandaAxis.setVisibility(View.VISIBLE);

        //TUMPUAN SENDI & JEPIT
        if (isRestraintPin) {
            for (int i = 0; i <= Nbx; i++) {
                for (int j = 0; j < 3; j++) {
                    float[] koor1 = pinRestraintFix[i][j];
                    float[] koor2 = pinRestraintFix[i][j + 1];

                    canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pTumpuan);

                    koor1 = tumpuanSendi2Fix[i][j];
                    koor2 = tumpuanSendi2Fix[i][j + 1];

                    canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pTumpuan);
                }
            }
        } else {
            for (int i = 0; i <= Nbx; i++) {
                for (int j = 0; j < 4; j++) {
                    float[] koor1 = tumpuanJepit1Fix[i][j];
                    float[] koor2 = tumpuanJepit1Fix[i][j + 1];

                    canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pTumpuan);

                    koor1 = tumpuanJepit2Fix[i][j];
                    koor2 = tumpuanJepit2Fix[i][j + 1];

                    canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pTumpuan);
                }
            }
        }

        draw3D_Axis();

        if (!isDrawDisplacement) {
            drawNodeSelected();
            if (isTimerSleep) {
                if (isDrawLoadCurrent) {
                    if (drawLoadCurrent == 1) {
                        drawJointLoad();
                    } else if (drawLoadCurrent == 2) {
                        drawFrameLoad();
                    } else if (drawLoadCurrent == 3) {
                        drawAreaLoad();
                    } else if (drawLoadCurrent == 4) {
                        drawFrameSectionText();
                    } else if (drawLoadCurrent == 5) {
                        drawSFPMText();
                    } else if (drawLoadCurrent == 6) {
                        drawSFShearText();
                    } else if (drawLoadCurrent == 7) {
                        drawDesignSectionText();
                    } else if (drawLoadCurrent == 8) {
                        drawSFPMText();
                    } else if (drawLoadCurrent == 9) {
                        drawSFShearText();
                    }
                } else {
                    if (drawInternalForceCurrent == 1) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_AxialKolom, IF_AxialKolomFix, IF_AxialKolomNodal, isFirstPositif_AxialKolom);
                            drawIF_GeneralBalokXWarna(IF_AxialBalokX, IF_AxialBalokXFix, IF_AxialBalokXNodal, isFirstPositif_AxialBalokX);
                            drawIF_GeneralBalokYWarna(IF_AxialBalokY, IF_AxialBalokYFix, IF_AxialBalokYNodal, isFirstPositif_AxialBalokY);

                        } else {
                            drawIF_GeneralKolom(IF_AxialKolom, IF_AxialKolomFix, 0);
                            drawIF_GeneralBalokX(IF_AxialBalokX, IF_AxialBalokXFix, 0);
                            drawIF_GeneralBalokY(IF_AxialBalokY, IF_AxialBalokYFix, 0);
                        }


                    } else if (drawInternalForceCurrent == 2) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Shear22Kolom, IF_Shear22KolomFix, IF_Shear22KolomNodal, isFirstPositif_Shear22Kolom);
                            drawIF_GeneralBalokXWarna(IF_Shear22BalokX, IF_Shear22BalokXFix, IF_Shear22BalokXNodal, isFirstPositif_Shear22BalokX);
                            drawIF_GeneralBalokYWarna(IF_Shear22BalokY, IF_Shear22BalokYFix, IF_Shear22BalokYNodal, isFirstPositif_Shear22BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Shear22Kolom, IF_Shear22KolomFix, 1);
                            drawIF_GeneralBalokX(IF_Shear22BalokX, IF_Shear22BalokXFix, 1);
                            drawIF_GeneralBalokY(IF_Shear22BalokY, IF_Shear22BalokYFix, 1);
                        }

                    } else if (drawInternalForceCurrent == 3) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Shear33Kolom, IF_Shear33KolomFix, IF_Shear33KolomNodal, isFirstPositif_Shear33Kolom);
                            drawIF_GeneralBalokXWarna(IF_Shear33BalokX, IF_Shear33BalokXFix, IF_Shear33BalokXNodal, isFirstPositif_Shear33BalokX);
                            drawIF_GeneralBalokYWarna(IF_Shear33BalokY, IF_Shear33BalokYFix, IF_Shear33BalokYNodal, isFirstPositif_Shear33BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Shear33Kolom, IF_Shear33KolomFix, 2);
                            drawIF_GeneralBalokX(IF_Shear33BalokX, IF_Shear33BalokXFix, 2);
                            drawIF_GeneralBalokY(IF_Shear33BalokY, IF_Shear33BalokYFix, 2);
                        }
                    } else if (drawInternalForceCurrent == 4) {
                        if (isIF_Berwarna) {
                            drawIF_TorsiKolomWarna();
                            drawIF_TorsiBalokXWarna();
                            drawIF_TorsiBalokYWarna();

                        } else {
                            drawIF_TorsiKolom();
                            drawIF_TorsiBalokX();
                            drawIF_TorsiBalokY();
                        }

                    } else if (drawInternalForceCurrent == 5) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Momen22Kolom, IF_Momen22KolomFix, IF_Momen22KolomNodal, isFirstPositif_Momen22Kolom);
                            drawIF_GeneralBalokXWarna(IF_Momen22BalokX, IF_Momen22BalokXFix, IF_Momen22BalokXNodal, isFirstPositif_Momen22BalokX);
                            drawIF_GeneralBalokYWarna(IF_Momen22BalokY, IF_Momen22BalokYFix, IF_Momen22BalokYNodal, isFirstPositif_Momen22BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Momen22Kolom, IF_Momen22KolomFix, 4);
                            drawIF_GeneralBalokX(IF_Momen22BalokX, IF_Momen22BalokXFix, 4);
                            drawIF_GeneralBalokY(IF_Momen22BalokY, IF_Momen22BalokYFix, 4);
                        }

                    } else if (drawInternalForceCurrent == 6) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Momen33Kolom, IF_Momen33KolomFix, IF_Momen33KolomNodal, isFirstPositif_Momen33Kolom);
                            drawIF_GeneralBalokXWarna(IF_Momen33BalokX, IF_Momen33BalokXFix, IF_Momen33BalokXNodal, isFirstPositif_Momen33BalokX);
                            drawIF_GeneralBalokYWarna(IF_Momen33BalokY, IF_Momen33BalokYFix, IF_Momen33BalokYNodal, isFirstPositif_Momen33BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Momen33Kolom, IF_Momen33KolomFix, 5);
                            drawIF_GeneralBalokX(IF_Momen33BalokX, IF_Momen33BalokXFix, 5);
                            drawIF_GeneralBalokY(IF_Momen33BalokY, IF_Momen33BalokYFix, 5);
                        }

                    } else if (drawInternalForceCurrent == 7) {
                        drawTextReaction();
                    }
                }

            }
            drawFrameXZ_ElemenTap();
        } else {
            if (isShowDisplacementValue) {
                drawTextDisplacement();
            }
        }

        imgCanvas.setImageBitmap(bitmap);
    }

    private void drawFrameYZ() {
        canvas.drawColor(Color.parseColor("#FFFFFF"));

        float[][] koorNodeLokal;
        if (isDrawDisplacement) {
            koorNodeLokal = koorNodeDeformedFix;
        } else {
            koorNodeLokal = koorNodeFix;
        }

        //BALOK
        int counterElement = 0;
        int x = 0;
        for (int[] pointer : pointerElemenBalokYZ) {
            if (x == viewYZ_Current - 1) {
                pGaris.setStrokeWidth(8);
                if(drawLoadCurrent == 5){
                    setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElement).getAmanPMAnalysis());
                }else if(drawLoadCurrent == 6){
                    setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElement).getAmanVAnalysis());
                }else if(drawLoadCurrent == 8){
                    setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElement).getAmanPMDesign());
                }else if(drawLoadCurrent == 9){
                    setWarnaKeamanan(listBebanBatang.get(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElement).getAmanVDesign());
                }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

                float[] koor1 = koorNodeLokal[pointer[0]];
                float[] koor2 = koorNodeLokal[pointer[1]];

                canvas.drawLine(koor1[1], koor1[2], koor2[1], koor2[2], pGaris);

                if (isShowElement) {
                    canvas.scale(1, -1);

                    float koorX = (koor1[1] + koor2[1]) / 2;
                    float koorZ = (koor1[2] + koor2[2]) / 2;

                    canvas.drawText(Integer.toString(pointerElemenKolom.length + pointerElemenBalokXZ.length + counterElement), koorX + 5, -(koorZ + 5), pTextNodal);

                    canvas.scale(1, -1);
                }

                x++;
            } else if (x == viewYZ_Max - 1) {
                x = 0;
            } else {
                x++;
            }

            if (viewYZ_Current == viewYZ_Max) {
                if (x - 1 == viewYZ_Max - 1) {
                    x = 0;
                }
            }

            counterElement++;
        }

        //KOLOM
        counterElement = 0;
        x = 0;
        int y = 0;
        for (int[] pointer : pointerElemenKolom) {
            if (x == viewYZ_Current - 1) {
                pGaris.setStrokeWidth(8);
                if(drawLoadCurrent == 5){
                    setWarnaKeamanan(listBebanBatang.get(counterElement).getAmanPMAnalysis());
                }else if(drawLoadCurrent == 6){
                    setWarnaKeamanan(listBebanBatang.get(counterElement).getAmanVAnalysis());
                }else if(drawLoadCurrent == 8){
                    setWarnaKeamanan(listBebanBatang.get(counterElement).getAmanPMDesign());
                }else if(drawLoadCurrent == 9){
                    setWarnaKeamanan(listBebanBatang.get(counterElement).getAmanVDesign());
                }else{pGaris.setColor(Color.BLACK);pGaris.setStrokeWidth(3);}

                float[] koor1 = koorNodeLokal[pointer[0]];
                float[] koor2 = koorNodeLokal[pointer[1]];

                if (isShowNodal) {
                    canvas.scale(1, -1);
                    canvas.drawText(Integer.toString(pointer[0]), koor1[1] + 10, -(koor1[2] + 15), pTextNodal);
                    canvas.drawText(Integer.toString(pointer[1]), koor2[1] + 10, -(koor2[2] + 15), pTextNodal);
                    canvas.scale(1, -1);
                }

                if (isShowElement) {
                    canvas.scale(1, -1);

                    float koorX = (koor1[1] + koor2[1]) / 2;
                    float koorZ = (koor1[2] + koor2[2]) / 2;

                    canvas.drawText(Integer.toString(counterElement), koorX + 5, -(koorZ), pTextNodal);

                    canvas.scale(1, -1);
                }

                canvas.drawLine(koor1[1], koor1[2], koor2[1], koor2[2], pGaris);

                y++;
                x++;
            } else if (x == viewYZ_Max - 1) {
                x = 0;
            } else {
                x++;
            }

            if (viewYZ_Current == viewYZ_Max) {
                if (x - 1 == viewYZ_Max - 1) {
                    x = 0;
                }
            }

            counterElement++;
        }

        //Tanda Axis
        String tandaAxis;
        tandaAxis = "X = " + koorNodeReal[(viewYZ_Current - 1)][0] / 1000 + " m";
        txtTandaAxis.setText(tandaAxis);
        txtTandaAxis.setVisibility(View.VISIBLE);

        //TUMPUAN SENDI & JEPIT
        if (isRestraintPin) {
            x = 0;
            for (int i = 0; i <= Nby; i++) {
                for (int j = 0; j < 3; j++) {
                    float[] koor1 = tumpuanSendi2Fix[i + x][j];
                    float[] koor2 = tumpuanSendi2Fix[i + x][j + 1];

                    canvas.drawLine(koor1[1], koor1[2], koor2[1], koor2[2], pTumpuan);

                    koor1 = pinRestraintFix[i + x][j];
                    koor2 = pinRestraintFix[i + x][j + 1];

                    canvas.drawLine(koor1[1], koor1[2], koor2[1], koor2[2], pTumpuan);
                }
                x = x + Nbx;
            }
        } else {
            x = 0;
            for (int i = 0; i <= Nby; i++) {
                for (int j = 0; j < 4; j++) {
                    float[] koor1 = tumpuanJepit2Fix[i + x][j];
                    float[] koor2 = tumpuanJepit2Fix[i + x][j + 1];

                    canvas.drawLine(koor1[1], koor1[2], koor2[1], koor2[2], pTumpuan);

                    koor1 = tumpuanJepit1Fix[i + x][j];
                    koor2 = tumpuanJepit1Fix[i + x][j + 1];

                    canvas.drawLine(koor1[1], koor1[2], koor2[1], koor2[2], pTumpuan);
                }
                x = x + Nbx;
            }
        }

        draw3D_Axis();

        if (!isDrawDisplacement) {
            drawNodeSelected();
            if (isTimerSleep) {
                if (isDrawLoadCurrent) {
                    if (drawLoadCurrent == 1) {
                        drawJointLoad();
                    } else if (drawLoadCurrent == 2) {
                        drawFrameLoad();
                    } else if (drawLoadCurrent == 3) {
                        drawAreaLoad();
                    } else if (drawLoadCurrent == 4) {
                        drawFrameSectionText();
                    } else if (drawLoadCurrent == 5) {
                        drawSFPMText();
                    } else if (drawLoadCurrent == 6) {
                        drawSFShearText();
                    } else if (drawLoadCurrent == 7) {
                        drawDesignSectionText();
                    } else if (drawLoadCurrent == 8) {
                        drawSFPMText();
                    } else if (drawLoadCurrent == 9) {
                        drawSFShearText();
                    }
                } else {
                    if (drawInternalForceCurrent == 1) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_AxialKolom, IF_AxialKolomFix, IF_AxialKolomNodal, isFirstPositif_AxialKolom);
                            drawIF_GeneralBalokXWarna(IF_AxialBalokX, IF_AxialBalokXFix, IF_AxialBalokXNodal, isFirstPositif_AxialBalokX);
                            drawIF_GeneralBalokYWarna(IF_AxialBalokY, IF_AxialBalokYFix, IF_AxialBalokYNodal, isFirstPositif_AxialBalokY);

                        } else {
                            drawIF_GeneralKolom(IF_AxialKolom, IF_AxialKolomFix, 0);
                            drawIF_GeneralBalokX(IF_AxialBalokX, IF_AxialBalokXFix, 0);
                            drawIF_GeneralBalokY(IF_AxialBalokY, IF_AxialBalokYFix, 0);
                        }


                    } else if (drawInternalForceCurrent == 2) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Shear22Kolom, IF_Shear22KolomFix, IF_Shear22KolomNodal, isFirstPositif_Shear22Kolom);
                            drawIF_GeneralBalokXWarna(IF_Shear22BalokX, IF_Shear22BalokXFix, IF_Shear22BalokXNodal, isFirstPositif_Shear22BalokX);
                            drawIF_GeneralBalokYWarna(IF_Shear22BalokY, IF_Shear22BalokYFix, IF_Shear22BalokYNodal, isFirstPositif_Shear22BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Shear22Kolom, IF_Shear22KolomFix, 1);
                            drawIF_GeneralBalokX(IF_Shear22BalokX, IF_Shear22BalokXFix, 1);
                            drawIF_GeneralBalokY(IF_Shear22BalokY, IF_Shear22BalokYFix, 1);
                        }

                    } else if (drawInternalForceCurrent == 3) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Shear33Kolom, IF_Shear33KolomFix, IF_Shear33KolomNodal, isFirstPositif_Shear33Kolom);
                            drawIF_GeneralBalokXWarna(IF_Shear33BalokX, IF_Shear33BalokXFix, IF_Shear33BalokXNodal, isFirstPositif_Shear33BalokX);
                            drawIF_GeneralBalokYWarna(IF_Shear33BalokY, IF_Shear33BalokYFix, IF_Shear33BalokYNodal, isFirstPositif_Shear33BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Shear33Kolom, IF_Shear33KolomFix, 2);
                            drawIF_GeneralBalokX(IF_Shear33BalokX, IF_Shear33BalokXFix, 2);
                            drawIF_GeneralBalokY(IF_Shear33BalokY, IF_Shear33BalokYFix, 2);
                        }
                    } else if (drawInternalForceCurrent == 4) {
                        if (isIF_Berwarna) {
                            drawIF_TorsiKolomWarna();
                            drawIF_TorsiBalokXWarna();
                            drawIF_TorsiBalokYWarna();

                        } else {
                            drawIF_TorsiKolom();
                            drawIF_TorsiBalokX();
                            drawIF_TorsiBalokY();
                        }

                    } else if (drawInternalForceCurrent == 5) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Momen22Kolom, IF_Momen22KolomFix, IF_Momen22KolomNodal, isFirstPositif_Momen22Kolom);
                            drawIF_GeneralBalokXWarna(IF_Momen22BalokX, IF_Momen22BalokXFix, IF_Momen22BalokXNodal, isFirstPositif_Momen22BalokX);
                            drawIF_GeneralBalokYWarna(IF_Momen22BalokY, IF_Momen22BalokYFix, IF_Momen22BalokYNodal, isFirstPositif_Momen22BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Momen22Kolom, IF_Momen22KolomFix, 4);
                            drawIF_GeneralBalokX(IF_Momen22BalokX, IF_Momen22BalokXFix, 4);
                            drawIF_GeneralBalokY(IF_Momen22BalokY, IF_Momen22BalokYFix, 4);
                        }

                    } else if (drawInternalForceCurrent == 6) {
                        if (isIF_Berwarna) {
                            drawIF_GeneralKolomWarna(IF_Momen33Kolom, IF_Momen33KolomFix, IF_Momen33KolomNodal, isFirstPositif_Momen33Kolom);
                            drawIF_GeneralBalokXWarna(IF_Momen33BalokX, IF_Momen33BalokXFix, IF_Momen33BalokXNodal, isFirstPositif_Momen33BalokX);
                            drawIF_GeneralBalokYWarna(IF_Momen33BalokY, IF_Momen33BalokYFix, IF_Momen33BalokYNodal, isFirstPositif_Momen33BalokY);

                        } else {
                            drawIF_GeneralKolom(IF_Momen33Kolom, IF_Momen33KolomFix, 5);
                            drawIF_GeneralBalokX(IF_Momen33BalokX, IF_Momen33BalokXFix, 5);
                            drawIF_GeneralBalokY(IF_Momen33BalokY, IF_Momen33BalokYFix, 5);
                        }

                    } else if (drawInternalForceCurrent == 7) {
                        drawTextReaction();
                    }
                }

            }
            drawFrameYZ_ElemenTap();
        } else {
            if (isShowDisplacementValue) {
                drawTextDisplacement();
            }
        }

        imgCanvas.setImageBitmap(bitmap);
    }

    //----------MENGGAMBAR ELEMEN TERPILIH----------//
    private void drawFrame3D_ElemenTap() {
        int x = 0;
        for (boolean pointer : isPointerElementSelected) {
            if (pointer) {
                float[] koor1 = koorNode[pointerElemenStruktur[x][0]];
                float[] koor2 = koorNode[pointerElemenStruktur[x][1]];

                float koor1_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
                float koor1_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;

                float koor2_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
                float koor2_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;

                canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pGarisSelected);
            }
            x++;
        }
    }

    private void drawFrame3D_ElemenTapOrtho() {
        int x = 0;
        for (boolean pointer : isPointerElementSelected) {
            if (pointer) {
                float[] koor1 = koorNode[pointerElemenStruktur[x][0]];
                float[] koor2 = koorNode[pointerElemenStruktur[x][1]];

                canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pGarisSelected);
            }
            x++;
        }
    }

    private void drawFrameXY_ElemenTap() {
        float dummyFloat1 = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];
        int x = 0;
        for (boolean pointer : isPointerElementSelected) {
            if (pointer) {
                float[] koor1 = koorNodeFix[pointerElemenStruktur[x][0]];
                float[] koor2 = koorNodeFix[pointerElemenStruktur[x][1]];

                if (koor1[2] == dummyFloat1 && koor2[2] == dummyFloat1) {
                    canvas.drawLine(koor1[0], koor1[1], koor2[0], koor2[1], pGarisSelected);
                }
            }
            x++;
        }
    }

    private void drawFrameXZ_ElemenTap() {
        float dummyFloat1 = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];
        int x = 0;
        for (boolean pointer : isPointerElementSelected) {
            if (pointer) {
                float[] koor1 = koorNodeFix[pointerElemenStruktur[x][0]];
                float[] koor2 = koorNodeFix[pointerElemenStruktur[x][1]];

                if (koor1[1] == dummyFloat1) {
                    canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pGarisSelected);
                }
            }
            x++;
        }
    }

    private void drawFrameYZ_ElemenTap() {
        float dummyFloat1 = koorNodeFix[viewYZ_Current - 1][0];
        int x = 0;
        for (boolean pointer : isPointerElementSelected) {
            if (pointer) {
                float[] koor1 = koorNodeFix[pointerElemenStruktur[x][0]];
                float[] koor2 = koorNodeFix[pointerElemenStruktur[x][1]];

                if (koor1[0] == dummyFloat1) {
                    canvas.drawLine(koor1[1], koor1[2], koor2[1], koor2[2], pGarisSelected);
                }
            }
            x++;
        }
    }

    //----------MENGGAMBAR AREA DAN JOINT TERPILIH----------//
    private void drawAreaXYSelected() {
        if (tandaViewBottomNav == 1) {
            for (int i = 0; i < Nst * Nbx * Nby; i++) {
                if (!isPointerAreaSelected[i]) {

                } else {
                    for (int j = 0; j < 4; j++) {
                        float[] koor1 = koorNodeAreaSelect[i][j];
                        float[] koor2 = koorNodeAreaSelect[i][j + 1];

                        if (isPerspektif) {
                            float koor1_X = koor1[0] / (focalLength + koor1[1]) * focalLength;
                            float koor1_Z = koor1[2] / (focalLength + koor1[1]) * focalLength;

                            float koor2_X = koor2[0] / (focalLength + koor2[1]) * focalLength;
                            float koor2_Z = koor2[2] / (focalLength + koor2[1]) * focalLength;

                            canvas.drawLine(koor1_X, koor1_Z, koor2_X, koor2_Z, pAreaSelected);
                        } else {
                            canvas.drawLine(koor1[0], koor1[2], koor2[0], koor2[2], pAreaSelected);
                        }
                    }

                }
            }
        } else {
            if (viewXY_Current != 1) {
                float offset = 25f;
                int x = (viewXY_Current - 1) * (Nbx + 1) * (Nby + 1);
                int y = 0;
                for (int i = 0; i < Nbx * Nby; i++) {
                    if (isPointerAreaSelected[i + (viewXY_Current - 2) * Nbx * Nby]) {
                        float x1 = koorNodeFix[x][0] + offset;
                        float y1 = koorNodeFix[x][1] + offset;

                        float x2 = koorNodeFix[x + 1][0] - offset;
                        float y2 = koorNodeFix[x + 1][1] + offset;
                        canvas.drawLine(x1, y1, x2, y2, pAreaSelected);


                        x1 = koorNodeFix[x + 1][0] - offset;
                        y1 = koorNodeFix[x + 1][1] + offset;

                        x2 = koorNodeFix[x + Nbx + 2][0] - offset;
                        y2 = koorNodeFix[x + Nbx + 2][1] - offset;
                        canvas.drawLine(x1, y1, x2, y2, pAreaSelected);


                        x1 = koorNodeFix[x + Nbx + 2][0] - offset;
                        y1 = koorNodeFix[x + Nbx + 2][1] - offset;

                        x2 = koorNodeFix[x + Nbx + 1][0] + offset;
                        y2 = koorNodeFix[x + Nbx + 1][1] - offset;
                        canvas.drawLine(x1, y1, x2, y2, pAreaSelected);


                        x1 = koorNodeFix[x + Nbx + 1][0] + offset;
                        y1 = koorNodeFix[x + Nbx + 1][1] - offset;

                        x2 = koorNodeFix[x][0] + offset;
                        y2 = koorNodeFix[x][1] + offset;
                        canvas.drawLine(x1, y1, x2, y2, pAreaSelected);
                    }

                    if (y == Nbx - 1) {
                        y = 0;
                        x += 2;

                    } else {
                        y++;
                        x++;
                    }
                }
            }
        }
    }

    private void drawNodeSelected() {

        int x = 0;
        for (boolean i : isPointerNodeSelected) {
            if (i) {
                if (tandaViewBottomNav == 1) {
                    float x1;
                    float z1;
                    float toleransi = 25f;

                    if (isPerspektif) {
                        x1 = koorNode[x][0] / (focalLength + koorNode[x][1]) * focalLength;
                        z1 = koorNode[x][2] / (focalLength + koorNode[x][1]) * focalLength;
                    } else {
                        x1 = koorNode[x][0];
                        z1 = koorNode[x][2];
                    }

                    canvas.drawLine(x1 - toleransi, z1 - toleransi, x1 + toleransi, z1 + toleransi, pNodeSelected);
                    canvas.drawLine(x1 - toleransi, z1 + toleransi, x1 + toleransi, z1 - toleransi, pNodeSelected);

                } else if (tandaViewBottomNav == 2) {
                    float y_acuan = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

                    if (koorNodeFix[x][2] == y_acuan) {
                        float x1 = koorNodeFix[x][0];
                        float y1 = koorNodeFix[x][1];
                        float toleransi = 25f;

                        canvas.drawLine(x1 - toleransi, y1 - toleransi, x1 + toleransi, y1 + toleransi, pNodeSelected);
                        canvas.drawLine(x1 - toleransi, y1 + toleransi, x1 + toleransi, y1 - toleransi, pNodeSelected);
                    }

                } else if (tandaViewBottomNav == 3) {
                    float y_acuan = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

                    if (koorNodeFix[x][1] == y_acuan) {
                        float x1 = koorNodeFix[x][0];
                        float y1 = koorNodeFix[x][2];
                        float toleransi = 25f;

                        canvas.drawLine(x1 - toleransi, y1 - toleransi, x1 + toleransi, y1 + toleransi, pNodeSelected);
                        canvas.drawLine(x1 - toleransi, y1 + toleransi, x1 + toleransi, y1 - toleransi, pNodeSelected);
                    }
                } else if (tandaViewBottomNav == 4) {
                    float y_acuan = koorNodeFix[(viewYZ_Current - 1)][0];

                    if (koorNodeFix[x][0] == y_acuan) {
                        float x1 = koorNodeFix[x][1];
                        float y1 = koorNodeFix[x][2];
                        float toleransi = 25f;

                        canvas.drawLine(x1 - toleransi, y1 - toleransi, x1 + toleransi, y1 + toleransi, pNodeSelected);
                        canvas.drawLine(x1 - toleransi, y1 + toleransi, x1 + toleransi, y1 - toleransi, pNodeSelected);
                    }
                }
            }
            x++;
        }
    }

    //----------MENGGAMBAR LOAD----------//
    private void drawJointLoad() {
        int x = 0;
        if (tandaViewBottomNav == 1) {
            for (boolean[] i : isJointLoadExist) {
                for (int j = 0; j < 6; j++) {
                    if (i[j]) {
                        for (int k = 1; k < 6; k++) {
                            float x1;
                            float z1;
                            float x2;
                            float z2;

                            if (isPerspektif) {
                                x1 = koorNodeJointLoad[x][j][0][0] / (focalLength + koorNodeJointLoad[x][j][0][1]) * focalLength;
                                z1 = koorNodeJointLoad[x][j][0][2] / (focalLength + koorNodeJointLoad[x][j][0][1]) * focalLength;

                                x2 = koorNodeJointLoad[x][j][k][0] / (focalLength + koorNodeJointLoad[x][j][k][1]) * focalLength;
                                z2 = koorNodeJointLoad[x][j][k][2] / (focalLength + koorNodeJointLoad[x][j][k][1]) * focalLength;
                            } else {
                                x1 = koorNodeJointLoad[x][j][0][0];
                                z1 = koorNodeJointLoad[x][j][0][2];

                                x2 = koorNodeJointLoad[x][j][k][0];
                                z2 = koorNodeJointLoad[x][j][k][2];
                            }

                            if (k == 1) {
                                canvas.drawLine(x1, z1, x2, z2, pJointLoad);
                            } else {
                                canvas.drawLine(x1, z1, x2, z2, pJointLoadUjung);
                            }

                            if (k == 1) {
                                float offsetY = 15f;
                                float offsetX = 10f;
                                canvas.scale(1, -1);
                                canvas.drawText(Float.toString(jointLoad[x][j]), x2 + offsetX, -(z2 + offsetY), pText);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
                x++;
            }
        } else if (tandaViewBottomNav == 2) {
            float dummyFloat1 = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            x = 0;
            for (boolean[] i : isJointLoadExist) {
                for (int j = 0; j < 6; j++) {
                    if (i[j] && koorNodeFix[x][2] == dummyFloat1) {
                        for (int k = 1; k < 6; k++) {
                            float x1;
                            float z1;
                            float x2;
                            float z2;

                            x1 = koorNodeJointLoadFix[x][j][0][0];
                            z1 = koorNodeJointLoadFix[x][j][0][1];

                            x2 = koorNodeJointLoadFix[x][j][k][0];
                            z2 = koorNodeJointLoadFix[x][j][k][1];

                            if (k == 1) {
                                canvas.drawLine(x1, z1, x2, z2, pJointLoad);
                            } else {
                                canvas.drawLine(x1, z1, x2, z2, pJointLoadUjung);
                            }

                            if (k == 1) {
                                float offsetY = 15f;
                                float offsetX = 10f;
                                canvas.scale(1, -1);
                                canvas.drawText(Float.toString(jointLoad[x][j]), x2 + offsetX, -(z2 + offsetY), pText);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
                x++;
            }
        } else if (tandaViewBottomNav == 3) {
            float dummyFloat1 = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            x = 0;
            for (boolean[] i : isJointLoadExist) {
                for (int j = 0; j < 6; j++) {
                    if (i[j] && koorNodeFix[x][1] == dummyFloat1) {
                        for (int k = 1; k < 6; k++) {
                            float x1;
                            float z1;
                            float x2;
                            float z2;

                            x1 = koorNodeJointLoadFix[x][j][0][0];
                            z1 = koorNodeJointLoadFix[x][j][0][2];

                            x2 = koorNodeJointLoadFix[x][j][k][0];
                            z2 = koorNodeJointLoadFix[x][j][k][2];

                            if (k == 1) {
                                canvas.drawLine(x1, z1, x2, z2, pJointLoad);
                            } else {
                                canvas.drawLine(x1, z1, x2, z2, pJointLoadUjung);
                            }

                            if (k == 1) {
                                float offsetY = 15f;
                                float offsetX = 10f;
                                canvas.scale(1, -1);
                                canvas.drawText(Float.toString(jointLoad[x][j]), x2 + offsetX, -(z2 + offsetY), pText);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
                x++;
            }
        } else {
            float dummyFloat1 = koorNodeFix[(viewYZ_Current - 1)][0];

            x = 0;
            for (boolean[] i : isJointLoadExist) {
                for (int j = 0; j < 6; j++) {
                    if (i[j] && koorNodeFix[x][0] == dummyFloat1) {
                        for (int k = 1; k < 6; k++) {
                            float x1;
                            float z1;
                            float x2;
                            float z2;

                            x1 = koorNodeJointLoadFix[x][j][0][1];
                            z1 = koorNodeJointLoadFix[x][j][0][2];

                            x2 = koorNodeJointLoadFix[x][j][k][1];
                            z2 = koorNodeJointLoadFix[x][j][k][2];

                            if (k == 1) {
                                canvas.drawLine(x1, z1, x2, z2, pJointLoad);
                            } else {
                                canvas.drawLine(x1, z1, x2, z2, pJointLoadUjung);
                            }

                            if (k == 1) {
                                float offsetY = 15f;
                                float offsetX = 10f;
                                canvas.scale(1, -1);
                                canvas.drawText(Float.toString(jointLoad[x][j]), x2 + offsetX, -(z2 + offsetY), pText);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
                x++;
            }
        }
    }

    private void drawFrameLoad() {
        int x = 0;
        if (tandaViewBottomNav == 1) {
            for (boolean[] i : isFrameLoadExist) {
                for (int j = 0; j < 3; j++) {
                    if (i[j]) {
                        float x1;
                        float z1;
                        float x2;
                        float z2;

                        for (int k = 0; k < 7; k++) {
                            for (int l = 1; l < 4; l++) {
                                if (isPerspektif) {
                                    x1 = koorDistributedFrameLoad[x][j][k * 4][0] / (focalLength + koorDistributedFrameLoad[x][j][k * 4][1]) * focalLength;
                                    z1 = koorDistributedFrameLoad[x][j][k * 4][2] / (focalLength + koorDistributedFrameLoad[x][j][k * 4][1]) * focalLength;

                                    x2 = koorDistributedFrameLoad[x][j][l + k * 4][0] / (focalLength + koorDistributedFrameLoad[x][j][l + k * 4][1]) * focalLength;
                                    z2 = koorDistributedFrameLoad[x][j][l + k * 4][2] / (focalLength + koorDistributedFrameLoad[x][j][l + k * 4][1]) * focalLength;

                                    canvas.drawLine(x1, z1, x2, z2, pFrameLoad);
                                } else {
                                    x1 = koorDistributedFrameLoad[x][j][k * 4][0];
                                    z1 = koorDistributedFrameLoad[x][j][k * 4][2];

                                    x2 = koorDistributedFrameLoad[x][j][l + k * 4][0];
                                    z2 = koorDistributedFrameLoad[x][j][l + k * 4][2];

                                    canvas.drawLine(x1, z1, x2, z2, pFrameLoad);
                                }
                            }
                        }
                        if (isPerspektif) {
                            x1 = koorDistributedFrameLoad[x][j][1][0] / (focalLength + koorDistributedFrameLoad[x][j][1][1]) * focalLength;
                            z1 = koorDistributedFrameLoad[x][j][1][2] / (focalLength + koorDistributedFrameLoad[x][j][1][1]) * focalLength;

                            x2 = koorDistributedFrameLoad[x][j][7 * 4 - 3][0] / (focalLength + koorDistributedFrameLoad[x][j][7 * 4 - 3][1]) * focalLength;
                            z2 = koorDistributedFrameLoad[x][j][7 * 4 - 3][2] / (focalLength + koorDistributedFrameLoad[x][j][7 * 4 - 3][1]) * focalLength;

                            canvas.drawLine(x1, z1, x2, z2, pFrameLoad);

                            x1 = koorDistributedFrameLoad[x][j][13][0] / (focalLength + koorDistributedFrameLoad[x][j][13][1]) * focalLength;
                            z1 = koorDistributedFrameLoad[x][j][13][2] / (focalLength + koorDistributedFrameLoad[x][j][13][1]) * focalLength;

                            float offsetY = 15f;
                            canvas.scale(1, -1);
                            canvas.drawText(Float.toString(frameLoad[x][j]), x1, -(z1 + offsetY), pText);
                            canvas.scale(1, -1);
                        } else {
                            x1 = koorDistributedFrameLoad[x][j][1][0];
                            z1 = koorDistributedFrameLoad[x][j][1][2];

                            x2 = koorDistributedFrameLoad[x][j][7 * 4 - 3][0];
                            z2 = koorDistributedFrameLoad[x][j][7 * 4 - 3][2];

                            canvas.drawLine(x1, z1, x2, z2, pFrameLoad);

                            x1 = koorDistributedFrameLoad[x][j][13][0];
                            z1 = koorDistributedFrameLoad[x][j][13][2];

                            float offsetY = 15f;
                            canvas.scale(1, -1);
                            canvas.drawText(Float.toString(frameLoad[x][j]), x1, -(z1 + offsetY), pText);
                            canvas.scale(1, -1);
                        }

                    }
                }
                x++;
            }
        } else if (tandaViewBottomNav == 2) {
            float dummyFloat1 = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];
            x = 0;

            for (boolean[] i : isFrameLoadExist) {
                float[] koor1 = koorNodeFix[pointerElemenStruktur[x][0]];
                float[] koor2 = koorNodeFix[pointerElemenStruktur[x][1]];

                for (int j = 0; j < 3; j++) {
                    if (i[j] && koor1[2] == dummyFloat1 && koor2[2] == dummyFloat1) {
                        float x1;
                        float z1;
                        float x2;
                        float z2;

                        for (int k = 0; k < 7; k++) {
                            for (int l = 1; l < 4; l++) {
                                x1 = koorDistributedFrameLoadFix[x][j][k * 4][0];
                                z1 = koorDistributedFrameLoadFix[x][j][k * 4][1];

                                x2 = koorDistributedFrameLoadFix[x][j][l + k * 4][0];
                                z2 = koorDistributedFrameLoadFix[x][j][l + k * 4][1];

                                canvas.drawLine(x1, z1, x2, z2, pFrameLoad);
                            }
                        }

                        x1 = koorDistributedFrameLoadFix[x][j][1][0];
                        z1 = koorDistributedFrameLoadFix[x][j][1][1];

                        x2 = koorDistributedFrameLoadFix[x][j][7 * 4 - 3][0];
                        z2 = koorDistributedFrameLoadFix[x][j][7 * 4 - 3][1];

                        canvas.drawLine(x1, z1, x2, z2, pFrameLoad);

                        x1 = koorDistributedFrameLoadFix[x][j][13][0];
                        z1 = koorDistributedFrameLoadFix[x][j][13][1];

                        float offsetY = 15f;
                        canvas.scale(1, -1);
                        canvas.drawText(Float.toString(frameLoad[x][j]), x1, -(z1 + offsetY), pText);
                        canvas.scale(1, -1);
                    }
                }
                x++;
            }
        } else if (tandaViewBottomNav == 3) {
            float dummyFloat1 = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];
            x = 0;

            for (boolean[] i : isFrameLoadExist) {
                float[] koor1 = koorNodeFix[pointerElemenStruktur[x][0]];
                float[] koor2 = koorNodeFix[pointerElemenStruktur[x][1]];

                for (int j = 0; j < 3; j++) {
                    if (i[j] && koor1[1] == dummyFloat1 && koor2[1] == dummyFloat1) {
                        float x1;
                        float z1;
                        float x2;
                        float z2;

                        for (int k = 0; k < 7; k++) {
                            for (int l = 1; l < 4; l++) {
                                x1 = koorDistributedFrameLoadFix[x][j][k * 4][0];
                                z1 = koorDistributedFrameLoadFix[x][j][k * 4][2];

                                x2 = koorDistributedFrameLoadFix[x][j][l + k * 4][0];
                                z2 = koorDistributedFrameLoadFix[x][j][l + k * 4][2];

                                canvas.drawLine(x1, z1, x2, z2, pFrameLoad);
                            }
                        }

                        x1 = koorDistributedFrameLoadFix[x][j][1][0];
                        z1 = koorDistributedFrameLoadFix[x][j][1][2];

                        x2 = koorDistributedFrameLoadFix[x][j][7 * 4 - 3][0];
                        z2 = koorDistributedFrameLoadFix[x][j][7 * 4 - 3][2];

                        canvas.drawLine(x1, z1, x2, z2, pFrameLoad);

                        x1 = koorDistributedFrameLoadFix[x][j][13][0];
                        z1 = koorDistributedFrameLoadFix[x][j][13][2];

                        float offsetY = 15f;
                        canvas.scale(1, -1);
                        canvas.drawText(Float.toString(frameLoad[x][j]), x1, -(z1 + offsetY), pText);
                        canvas.scale(1, -1);
                    }
                }
                x++;
            }
        } else if (tandaViewBottomNav == 4) {
            float dummyFloat1 = koorNodeFix[(viewYZ_Current - 1)][0];
            x = 0;

            for (boolean[] i : isFrameLoadExist) {
                float[] koor1 = koorNodeFix[pointerElemenStruktur[x][0]];
                float[] koor2 = koorNodeFix[pointerElemenStruktur[x][1]];

                for (int j = 0; j < 3; j++) {
                    if (i[j] && koor1[0] == dummyFloat1 && koor2[0] == dummyFloat1) {
                        float x1;
                        float z1;
                        float x2;
                        float z2;

                        for (int k = 0; k < 7; k++) {
                            for (int l = 1; l < 4; l++) {
                                x1 = koorDistributedFrameLoadFix[x][j][k * 4][1];
                                z1 = koorDistributedFrameLoadFix[x][j][k * 4][2];

                                x2 = koorDistributedFrameLoadFix[x][j][l + k * 4][1];
                                z2 = koorDistributedFrameLoadFix[x][j][l + k * 4][2];

                                canvas.drawLine(x1, z1, x2, z2, pFrameLoad);
                            }
                        }

                        x1 = koorDistributedFrameLoadFix[x][j][1][1];
                        z1 = koorDistributedFrameLoadFix[x][j][1][2];

                        x2 = koorDistributedFrameLoadFix[x][j][7 * 4 - 3][1];
                        z2 = koorDistributedFrameLoadFix[x][j][7 * 4 - 3][2];

                        canvas.drawLine(x1, z1, x2, z2, pFrameLoad);

                        x1 = koorDistributedFrameLoadFix[x][j][13][1];
                        z1 = koorDistributedFrameLoadFix[x][j][13][2];

                        float offsetY = 15f;
                        canvas.scale(1, -1);
                        canvas.drawText(Float.toString(frameLoad[x][j]), x1, -(z1 + offsetY), pText);
                        canvas.scale(1, -1);
                    }
                }
                x++;
            }
        }
    }

    private void drawAreaLoad() {
        if (tandaViewBottomNav == 1) {
            int x = (Nbx + 1) * (Nby + 1);
            int y = 1;
            int z = 1;
            int counter = 0;
            for (boolean i : isAreaLoadExist) {
                if (i) {
                    float x1;
                    float z1;
                    float x2;
                    float z2;

                    if (isPerspektif) {
                        x1 = koorNode[x][0] / (focalLength + koorNode[x][1]) * focalLength;
                        z1 = koorNode[x][2] / (focalLength + koorNode[x][1]) * focalLength;

                        x2 = koorNode[x + Nbx + 2][0] / (focalLength + koorNode[x + Nbx + 2][1]) * focalLength;
                        z2 = koorNode[x + Nbx + 2][2] / (focalLength + koorNode[x + Nbx + 2][1]) * focalLength;
                    } else {
                        x1 = koorNode[x][0];
                        z1 = koorNode[x][2];

                        x2 = koorNode[x + Nbx + 2][0];
                        z2 = koorNode[x + Nbx + 2][2];
                    }

                    canvas.scale(1, -1);
                    canvas.drawText(Float.toString(areaLoad[counter]), (x1 + x2) / 2f, -((z1 + z2) / 2f), pText);
                    canvas.scale(1, -1);
                }

                if (z == Nby && y == Nbx) {
                    z = 1;
                    y = 1;
                    x += 2 + Nbx + 1;
                } else if (y == Nbx) {
                    z++;
                    y = 1;
                    x += 2;
                } else {
                    y++;
                    x++;
                }

                counter++;
            }
        } else if (tandaViewBottomNav == 2) {

            if (viewXY_Current > 1) {
                int x = (viewXY_Current - 2) * (Nbx) * (Nby);
                int y = (viewXY_Current - 1) * (Nbx + 1) * (Nby + 1);
                int z = 1;
                for (int i = 0; i < Nbx * Nby; i++) {
                    if (isAreaLoadExist[i + x]) {
                        float x1;
                        float z1;
                        float x2;
                        float z2;

                        x1 = koorNodeFix[y][0];
                        z1 = koorNodeFix[y][1];

                        x2 = koorNodeFix[y + Nbx + 2][0];
                        z2 = koorNodeFix[y + Nbx + 2][1];

                        canvas.scale(1, -1);
                        canvas.drawText(Float.toString(areaLoad[i + x]), (x1 + x2) / 2f, -((z1 + z2) / 2f), pText);
                        canvas.scale(1, -1);
                    }
                    if (z == Nbx) {
                        z = 1;
                        y += 2;
                    } else {
                        z++;
                        y++;
                    }
                }
            }
        }
    }

    //----------MENGGAMBAR INTERNAL FORCES (IF)----------//
    private void drawIF_GeneralBalokX(double[][][] internalForce, double[][][] internalForceFix, int urutanIF) {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                if (isPerspektif) {
                    for (int j = 0; j < 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(internalForce[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][0]));
                        yFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][1]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][2]));
                        x2 = xFloat / (focalLength + yFloat) * focalLength;
                        z2 = zFloat / (focalLength + yFloat) * focalLength;

                        canvas.drawLine(x1, z1, x2, z2, pPathBalokX);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][0]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][2]));
                        x2 = xFloat;
                        z2 = zFloat;

                        canvas.drawLine(x1, z1, x2, z2, pPathBalokX);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 2) {
            float referensiCurrent = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                for (int j = 0; j < 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][0]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][1]));
                    referensi_2 = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][2]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.01 && selisih_2 < 0.01) {
                        canvas.drawLine(x1, z1, x2, z2, pPathBalokX);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 3) {
            float referensiCurrent = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                for (int j = 0; j < 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][0]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][2]));

                    referensi_2 = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][1]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathBalokX);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawIF_GeneralBalokXWarna(double[][][] internalForce, double[][][] internalForceFix, int[][] IF_Nodal, boolean[] isFirstPositif) {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                if (isPerspektif) {
                    int counterIF_Nodal = 0;
                    boolean warnaIF = isFirstPositif[i];

                    for (int j = 0; j <= 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(internalForce[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }

                } else {
                    int counterIF_Nodal = 0;
                    boolean warnaIF = isFirstPositif[i];

                    for (int j = 0; j <= 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 2) {
            float referensiCurrent = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            float x1;
            float z1;

            float xFloat;
            float zFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                int counterIF_Nodal = 0;
                boolean warnaIF = isFirstPositif[i];

                for (int j = 0; j <= 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    zFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));
                    x1 = xFloat;
                    z1 = zFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.01) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 3) {
            float referensiCurrent = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            float x1;
            float z1;

            float xFloat;
            float zFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                int counterIF_Nodal = 0;
                boolean warnaIF = isFirstPositif[i];

                for (int j = 0; j <= 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    zFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    x1 = xFloat;
                    z1 = zFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.001) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawIF_GeneralBalokY(double[][][] internalForce, double[][][] internalForceFix, int urutanIF) {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                if (isPerspektif) {
                    for (int j = 0; j < 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(internalForce[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][0]));
                        yFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][1]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][2]));
                        x2 = xFloat / (focalLength + yFloat) * focalLength;
                        z2 = zFloat / (focalLength + yFloat) * focalLength;

                        canvas.drawLine(x1, z1, x2, z2, pPathBalokY);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][0]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][2]));
                        x2 = xFloat;
                        z2 = zFloat;

                        canvas.drawLine(x1, z1, x2, z2, pPathBalokY);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 2) {
            float referensiCurrent = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                for (int j = 0; j < 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));

                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][0]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][1]));

                    referensi_2 = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][2]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathBalokY);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 4) {
            float referensiCurrent = koorNodeFix[(viewYZ_Current - 1)][0];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                for (int j = 0; j < 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][1]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][2]));

                    referensi_2 = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][0]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathBalokY);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawIF_GeneralBalokYWarna(double[][][] internalForce, double[][][] internalForceFix, int[][] IF_Nodal, boolean[] isFirstPositif) {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                if (isPerspektif) {
                    int counterIF_Nodal = 0;
                    boolean warnaIF = isFirstPositif[i];

                    for (int j = 0; j <= 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(internalForce[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }

                } else {
                    int counterIF_Nodal = 0;
                    boolean warnaIF = isFirstPositif[i];

                    for (int j = 0; j <= 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 2) {
            float referensiCurrent = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            float x1;
            float z1;

            float xFloat;
            float zFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                int counterIF_Nodal = 0;
                boolean warnaIF = isFirstPositif[i];

                for (int j = 0; j <= 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    zFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));
                    x1 = xFloat;
                    z1 = zFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.01) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 4) {
            float referensiCurrent = koorNodeFix[(viewYZ_Current - 1)][0];

            float x1;
            float z1;

            float xFloat;
            float zFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                int counterIF_Nodal = 0;
                boolean warnaIF = isFirstPositif[i];

                for (int j = 0; j <= 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    zFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    x1 = xFloat;
                    z1 = zFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.001) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawIF_GeneralKolom(double[][][] internalForce, double[][][] internalForceFix, int urutanIF) {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                if (isPerspektif) {
                    for (int j = 0; j < 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(internalForce[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][0]));
                        yFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][1]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][2]));
                        x2 = xFloat / (focalLength + yFloat) * focalLength;
                        z2 = zFloat / (focalLength + yFloat) * focalLength;

                        canvas.drawLine(x1, z1, x2, z2, pPathKolom);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][0]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j + 1][2]));
                        x2 = xFloat;
                        z2 = zFloat;

                        canvas.drawLine(x1, z1, x2, z2, pFrameLoad);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 3) {
            float referensiCurrent = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                for (int j = 0; j < 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][0]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][2]));

                    referensi_2 = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][1]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathKolom);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 4) {
            float referensiCurrent = koorNodeFix[(viewYZ_Current - 1)][0];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                for (int j = 0; j < 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][1]));
                    yFloat = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][2]));

                    referensi_2 = Float.parseFloat(Double.toString(internalForceFix[i][j + 1][0]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathKolom);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i][urutanIF] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i][urutanIF] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 50) {
                                if (urutanIF < 3) {
                                    text = df.format(Running.E_F[i][urutanIF + 6] / 1000);
                                    text = text + " kN";
                                } else {
                                    text = df.format(Running.E_F[i][urutanIF + 6] / 1000000);
                                    text = text + " kNm";
                                }

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawIF_GeneralKolomWarna(double[][][] internalForce, double[][][] internalForceFix, int[][] IF_Nodal, boolean[] isFirstPositif) {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                if (isPerspektif) {
                    int counterIF_Nodal = 0;
                    boolean warnaIF = isFirstPositif[i];

                    for (int j = 0; j <= 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(internalForce[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }

                } else {
                    int counterIF_Nodal = 0;
                    boolean warnaIF = isFirstPositif[i];

                    for (int j = 0; j <= 51; j++) {
                        xFloat = Float.parseFloat(Double.toString(internalForce[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(internalForce[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 3) {
            float referensiCurrent = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            float x1;
            float z1;

            float xFloat;
            float zFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                int counterIF_Nodal = 0;
                boolean warnaIF = isFirstPositif[i];

                for (int j = 0; j <= 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    zFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    x1 = xFloat;
                    z1 = zFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.001) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 4) {
            float referensiCurrent = koorNodeFix[(viewYZ_Current - 1)][0];

            float x1;
            float z1;

            float xFloat;
            float zFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                int counterIF_Nodal = 0;
                boolean warnaIF = isFirstPositif[i];

                for (int j = 0; j <= 51; j++) {
                    xFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][1]));
                    zFloat = Float.parseFloat(Double.toString(internalForceFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(internalForceFix[i][j][0]));
                    x1 = xFloat;
                    z1 = zFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.001) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            if (IF_Nodal[i][counterIF_Nodal] == j || j == 51) {
                                path.close();
                                if (warnaIF) {
                                    canvas.drawPath(path, pPathPositif);
                                } else {
                                    canvas.drawPath(path, pPathNegatif);
                                }
                                path.reset();
                                path.moveTo(x1, z1);

                                warnaIF = !warnaIF;

                                counterIF_Nodal++;
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawIF_TorsiBalokX() {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                if (isPerspektif) {
                    for (int j = 0; j < 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j + 1][0]));
                        yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j + 1][1]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j + 1][2]));
                        x2 = xFloat / (focalLength + yFloat) * focalLength;
                        z2 = zFloat / (focalLength + yFloat) * focalLength;

                        canvas.drawLine(x1, z1, x2, z2, pPathBalokX);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j + 1][0]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j + 1][2]));
                        x2 = xFloat;
                        z2 = zFloat;

                        canvas.drawLine(x1, z1, x2, z2, pPathBalokX);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 2) {
            float referensiCurrent = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                for (int j = 0; j < 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][1]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][2]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j + 1][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j + 1][1]));

                    referensi_2 = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j + 1][2]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathBalokX);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 3) {
            float referensiCurrent = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                for (int j = 0; j < 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][1]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j + 1][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j + 1][2]));

                    referensi_2 = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j + 1][1]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathBalokX);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i][3 + pointerElemenKolom.length] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i][9 + pointerElemenKolom.length] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawIF_TorsiBalokXWarna() {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                if (isPerspektif) {
                    for (int j = 0; j <= 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiBalokX[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                } else {
                    for (int j = 0; j <= 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokX[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiBalokX[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 2) {
            float referensiCurrent = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                for (int j = 0; j <= 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][1]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][2]));
                    x1 = xFloat;
                    z1 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.001) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiBalokX[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 3) {
            float referensiCurrent = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenBalokXZ.length; i++) {
                for (int j = 0; j <= 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiBalokXFix[i][j][1]));
                    x1 = xFloat;
                    z1 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.001) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiBalokX[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                }
            }
        }
    }

    private void drawIF_TorsiBalokY() {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                if (isPerspektif) {
                    for (int j = 0; j < 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j + 1][0]));
                        yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j + 1][1]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j + 1][2]));
                        x2 = xFloat / (focalLength + yFloat) * focalLength;
                        z2 = zFloat / (focalLength + yFloat) * focalLength;

                        canvas.drawLine(x1, z1, x2, z2, pPathBalokY);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j + 1][0]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j + 1][2]));
                        x2 = xFloat;
                        z2 = zFloat;

                        canvas.drawLine(x1, z1, x2, z2, pPathBalokY);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }
        } else if (tandaViewBottomNav == 2) {
            float referensiCurrent = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                for (int j = 0; j < 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][1]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][2]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j + 1][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j + 1][1]));

                    referensi_2 = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j + 1][2]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathBalokY);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 4) {
            float referensiCurrent = koorNodeFix[(viewYZ_Current - 1)][0];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                for (int j = 0; j < 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][1]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][0]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j + 1][1]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j + 1][2]));

                    referensi_2 = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j + 1][0]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathBalokY);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i + pointerElemenKolom.length + pointerElemenBalokXZ.length][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawIF_TorsiBalokYWarna() {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                if (isPerspektif) {
                    for (int j = 0; j <= 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiBalokY[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                } else {
                    for (int j = 0; j <= 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiBalokY[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiBalokY[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 2) {
            float referensiCurrent = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                for (int j = 0; j <= 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][1]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][2]));
                    x1 = xFloat;
                    z1 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.001) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiBalokY[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 4) {
            float referensiCurrent = koorNodeFix[(viewYZ_Current - 1)][0];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenBalokYZ.length; i++) {
                for (int j = 0; j <= 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][1]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiBalokYFix[i][j][0]));
                    x1 = xFloat;
                    z1 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.001) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiBalokY[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                }
            }
        }
    }

    private void drawIF_TorsiKolom() {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                if (isPerspektif) {
                    for (int j = 0; j < 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        xFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j + 1][0]));
                        yFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j + 1][1]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j + 1][2]));
                        x2 = xFloat / (focalLength + yFloat) * focalLength;
                        z2 = zFloat / (focalLength + yFloat) * focalLength;

                        canvas.drawLine(x1, z1, x2, z2, pPathKolom);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }

                } else {
                    for (int j = 0; j < 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        xFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j + 1][0]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j + 1][2]));
                        x2 = xFloat;
                        z2 = zFloat;

                        canvas.drawLine(x1, z1, x2, z2, pPathKolom);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 3) {
            float referensiCurrent = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                for (int j = 0; j < 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][1]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j + 1][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j + 1][2]));

                    referensi_2 = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j + 1][1]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathKolom);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 4) {
            float referensiCurrent = koorNodeFix[(viewYZ_Current - 1)][0];

            float x1;
            float z1;
            float x2;
            float z2;

            float xFloat;
            float yFloat;
            float referensi_1;
            float referensi_2;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                for (int j = 0; j < 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][1]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][0]));
                    x1 = xFloat;
                    z1 = yFloat;

                    xFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j + 1][1]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j + 1][2]));

                    referensi_2 = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j + 1][0]));
                    x2 = xFloat;
                    z2 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    float selisih_2 = referensi_2 - referensiCurrent;
                    selisih_2 = Math.abs(selisih_2);

                    if (selisih_1 < 0.001 && selisih_2 < 0.001) {
                        canvas.drawLine(x1, z1, x2, z2, pPathKolom);

                        if (isShowInternalForceValue) {
                            DecimalFormat df = new DecimalFormat("#.###");
                            String text;

                            if (j == 1) {
                                text = df.format(Running.E_F[i][3] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 + distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);

                            } else if (j == 2) {
                                text = df.format(Running.E_F[i][9] / 1000000);
                                text = text + " kNm";

                                canvas.scale(1, -1);
                                canvas.drawText(text, x1, -(z1 - distanceTextReaction), pTextReaction);
                                canvas.scale(1, -1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawIF_TorsiKolomWarna() {
        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                if (isPerspektif) {
                    for (int j = 0; j <= 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j][0]));
                        yFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j][1]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j][2]));
                        x1 = xFloat / (focalLength + yFloat) * focalLength;
                        z1 = zFloat / (focalLength + yFloat) * focalLength;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiKolom[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }

                } else {
                    for (int j = 0; j <= 3; j++) {
                        xFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j][0]));
                        zFloat = Float.parseFloat(Double.toString(IF_TorsiKolom[i][j][2]));
                        x1 = xFloat;
                        z1 = zFloat;

                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiKolom[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 3) {
            float referensiCurrent = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                for (int j = 0; j <= 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][0]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][1]));
                    x1 = xFloat;
                    z1 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.001) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiKolom[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                }
            }

        } else if (tandaViewBottomNav == 4) {
            float referensiCurrent = koorNodeFix[(viewYZ_Current - 1)][0];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi_1;

            for (int i = 0; i < pointerElemenKolom.length; i++) {
                for (int j = 0; j <= 3; j++) {
                    xFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][1]));
                    yFloat = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][2]));

                    referensi_1 = Float.parseFloat(Double.toString(IF_TorsiKolomFix[i][j][0]));
                    x1 = xFloat;
                    z1 = yFloat;

                    float selisih_1 = referensi_1 - referensiCurrent;
                    selisih_1 = Math.abs(selisih_1);

                    if (selisih_1 < 0.001) {
                        if (j == 0) {
                            path.moveTo(x1, z1);
                        } else if (j == 1 || j == 2) {
                            path.lineTo(x1, z1);
                        } else {
                            path.lineTo(x1, z1);

                            path.close();
                            if (isFirstPositif_TorsiKolom[i]) {
                                canvas.drawPath(path, pPathPositif);
                            } else {
                                canvas.drawPath(path, pPathNegatif);
                            }
                            path.reset();
                        }
                    }
                }
            }
        }
    }

    //----------MENGGAMBAR TEKS----------//
    private void drawTextReaction() {
        double[] R_S = Running.R_S;

        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = 0; i < (Nbx + 1) * (Nby + 1); i++) {
                if (isPerspektif) {
                    xFloat = koorNode[i][0];
                    yFloat = koorNode[i][1];
                    zFloat = koorNode[i][2];

                    x1 = xFloat / (focalLength + yFloat) * focalLength;
                    z1 = zFloat / (focalLength + yFloat) * focalLength;

                } else {
                    xFloat = koorNode[i][0];
                    zFloat = koorNode[i][2];

                    x1 = xFloat;
                    z1 = zFloat;
                }

                DecimalFormat df = new DecimalFormat("#.###");
                String text;

                text = "F1 = " + df.format(R_S[i * 6] / 1000) + " kN";
                canvas.scale(1, -1);
                canvas.drawText(text, x1, -(z1 + 5 * distanceTextReaction), pTextReaction);
                canvas.scale(1, -1);

                text = "F2 = " + df.format(R_S[i * 6 + 1] / 1000) + " kN";
                canvas.scale(1, -1);
                canvas.drawText(text, x1, -(z1 + 4 * distanceTextReaction), pTextReaction);
                canvas.scale(1, -1);

                text = "F3 = " + df.format(R_S[i * 6 + 2] / 1000) + " kN";
                canvas.scale(1, -1);
                canvas.drawText(text, x1, -(z1 + 3 * distanceTextReaction), pTextReaction);
                canvas.scale(1, -1);

                text = "M1 = " + df.format(R_S[i * 6 + 3] / 1000000) + " kNm";
                canvas.scale(1, -1);
                canvas.drawText(text, x1, -(z1 + 2 * distanceTextReaction), pTextReaction);
                canvas.scale(1, -1);

                text = "M2 = " + df.format(R_S[i * 6 + 4] / 1000000) + " kNm";
                canvas.scale(1, -1);
                canvas.drawText(text, x1, -(z1 + 1 * distanceTextReaction), pTextReaction);
                canvas.scale(1, -1);

                text = "M3 = " + df.format(R_S[i * 6 + 5] / 1000000) + " kNm";
                canvas.scale(1, -1);
                canvas.drawText(text, x1, -(z1), pTextReaction);
                canvas.scale(1, -1);
            }

        } else if (tandaViewBottomNav == 2) {
            float referensiCurrent = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi;

            for (int i = 0; i < (Nbx + 1) * (Nby + 1); i++) { //i = nodal tumpuan
                xFloat = koorNodeFix[i][0];
                yFloat = koorNodeFix[i][1];

                referensi = koorNodeFix[i][2];

                x1 = xFloat;
                z1 = yFloat;

                float selisih = referensi - referensiCurrent;
                selisih = Math.abs(selisih);

                if (selisih < 0.001) {
                    DecimalFormat df = new DecimalFormat("#.#####");
                    String text;

                    text = "F1 = " + df.format(R_S[i * 6] / 1000) + " kN";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 5 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "F2 = " + df.format(R_S[i * 6 + 1] / 1000) + " kN";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 4 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "F3 = " + df.format(R_S[i * 6 + 2] / 1000) + " kN";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 3 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "M1 = " + df.format(R_S[i * 6 + 3] / 1000000) + " kNm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 2 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "M2 = " + df.format(R_S[i * 6 + 4] / 1000000) + " kNm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 1 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "M3 = " + df.format(R_S[i * 6 + 5] / 1000000) + " kNm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1), pTextReaction);
                    canvas.scale(1, -1);
                }
            }

        } else if (tandaViewBottomNav == 3) {
            float referensiCurrent = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi;

            for (int i = 0; i < (Nbx + 1) * (Nby + 1); i++) {
                xFloat = koorNodeFix[i][0];
                yFloat = koorNodeFix[i][2];

                referensi = koorNodeFix[i][1];

                x1 = xFloat;
                z1 = yFloat;

                float selisih = referensi - referensiCurrent;
                selisih = Math.abs(selisih);

                if (selisih < 0.001) {
                    DecimalFormat df = new DecimalFormat("#.###");
                    String text;

                    text = "F1 = " + df.format(R_S[i * 6] / 1000) + " kN";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 5 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "F2 = " + df.format(R_S[i * 6 + 1] / 1000) + " kN";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 4 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "F3 = " + df.format(R_S[i * 6 + 2] / 1000) + " kN";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 3 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "M1 = " + df.format(R_S[i * 6 + 3] / 1000000) + " kNm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 2 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "M2 = " + df.format(R_S[i * 6 + 4] / 1000000) + " kNm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 1 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "M3 = " + df.format(R_S[i * 6 + 5] / 1000000) + " kNm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1), pTextReaction);
                    canvas.scale(1, -1);
                }
            }

        } else if (tandaViewBottomNav == 4) {
            float referensiCurrent = koorNodeFix[(viewYZ_Current - 1)][0];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi;

            for (int i = 0; i < (Nbx + 1) * (Nby + 1); i++) {
                xFloat = koorNodeFix[i][1];
                yFloat = koorNodeFix[i][2];

                referensi = koorNodeFix[i][0];

                x1 = xFloat;
                z1 = yFloat;

                float selisih = referensi - referensiCurrent;
                selisih = Math.abs(selisih);

                if (selisih < 0.001) {
                    DecimalFormat df = new DecimalFormat("#.###");
                    String text;

                    text = "F1 = " + df.format(R_S[i * 6] / 1000) + " kN";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 5 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "F2 = " + df.format(R_S[i * 6 + 1] / 1000) + " kN";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 4 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "F3 = " + df.format(R_S[i * 6 + 2] / 1000) + " kN";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 3 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "M1 = " + df.format(R_S[i * 6 + 3] / 1000000) + " kNm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 2 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "M2 = " + df.format(R_S[i * 6 + 4] / 1000000) + " kNm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 1 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "M3 = " + df.format(R_S[i * 6 + 5] / 1000000) + " kNm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1), pTextReaction);
                    canvas.scale(1, -1);
                }
            }
        }
    }

    private void drawTextDisplacement() {
        double[] U_S = Running.U_S;

        if (tandaViewBottomNav == 1) {
            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float zFloat;

            for (int i = (Nbx + 1) * (Nby + 1); i < koorNodeDeformed.length; i++) {
                if (isPerspektif) {
                    xFloat = koorNodeDeformed[i][0];
                    yFloat = koorNodeDeformed[i][1];
                    zFloat = koorNodeDeformed[i][2];

                    x1 = xFloat / (focalLength + yFloat) * focalLength;
                    z1 = zFloat / (focalLength + yFloat) * focalLength;

                } else {
                    xFloat = koorNodeDeformed[i][0];
                    zFloat = koorNodeDeformed[i][2];

                    x1 = xFloat;
                    z1 = zFloat;
                }

                DecimalFormat df = new DecimalFormat("#.###");
                String text;

                text = "U1 = " + df.format(U_S[i * 6]) + " mm";
                canvas.scale(1, -1);
                canvas.drawText(text, x1, -(z1 + 2 * distanceTextReaction), pTextReaction);
                canvas.scale(1, -1);

                text = "U2 = " + df.format(U_S[i * 6 + 1]) + " mm";
                canvas.scale(1, -1);
                canvas.drawText(text, x1, -(z1 + 1 * distanceTextReaction), pTextReaction);
                canvas.scale(1, -1);

                text = "U3 = " + df.format(U_S[i * 6 + 2]) + " mm";
                canvas.scale(1, -1);
                canvas.drawText(text, x1, -(z1 + 0 * distanceTextReaction), pTextReaction);
                canvas.scale(1, -1);
            }

        } else if (tandaViewBottomNav == 2) {
            float referensiCurrent = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi;

            for (int i = (Nbx + 1) * (Nby + 1); i < koorNodeDeformed.length; i++) {
                xFloat = koorNodeDeformedFix[i][0];
                yFloat = koorNodeDeformedFix[i][1];

                referensi = koorNodeFix[i][2];

                x1 = xFloat;
                z1 = yFloat;

                float selisih = referensi - referensiCurrent;
                selisih = Math.abs(selisih);

                if (selisih < 0.001) {
                    DecimalFormat df = new DecimalFormat("#.###");
                    String text;

                    text = "U1 = " + df.format(U_S[i * 6]) + " mm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 2 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "U2 = " + df.format(U_S[i * 6 + 1]) + " mm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 1 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "U3 = " + df.format(U_S[i * 6 + 2]) + " mm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 0 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);
                }
            }

        } else if (tandaViewBottomNav == 3) {
            float referensiCurrent = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi;

            for (int i = (Nbx + 1) * (Nby + 1); i < koorNodeDeformed.length; i++) {
                xFloat = koorNodeDeformedFix[i][0];
                yFloat = koorNodeDeformedFix[i][2];

                referensi = koorNodeFix[i][1];

                x1 = xFloat;
                z1 = yFloat;

                float selisih = referensi - referensiCurrent;
                selisih = Math.abs(selisih);

                if (selisih < 0.001) {
                    DecimalFormat df = new DecimalFormat("#.###");
                    String text;

                    text = "U1 = " + df.format(U_S[i * 6]) + " mm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 2 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "U2 = " + df.format(U_S[i * 6 + 1]) + " mm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 1 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "U3 = " + df.format(U_S[i * 6 + 2]) + " mm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 0 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);
                }
            }

        } else if (tandaViewBottomNav == 4) {
            float referensiCurrent = koorNodeFix[(viewYZ_Current - 1)][0];

            float x1;
            float z1;

            float xFloat;
            float yFloat;
            float referensi;

            for (int i = (Nbx + 1) * (Nby + 1); i < koorNodeDeformed.length; i++) {
                xFloat = koorNodeDeformedFix[i][1];
                yFloat = koorNodeDeformedFix[i][2];

                referensi = koorNodeFix[i][0];

                x1 = xFloat;
                z1 = yFloat;

                float selisih = referensi - referensiCurrent;
                selisih = Math.abs(selisih);

                if (selisih < 0.001) {
                    DecimalFormat df = new DecimalFormat("#.###");
                    String text;

                    text = "U1 = " + df.format(U_S[i * 6]) + " mm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 2 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "U2 = " + df.format(U_S[i * 6 + 1]) + " mm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 1 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);

                    text = "U3 = " + df.format(U_S[i * 6 + 2]) + " mm";
                    canvas.scale(1, -1);
                    canvas.drawText(text, x1, -(z1 + 0 * distanceTextReaction), pTextReaction);
                    canvas.scale(1, -1);
                }
            }
        }
    }

    private void drawFrameSectionText() {
        if (tandaViewBottomNav == 1) {
            int x = 0;
            for (int[] i : pointerElemenStruktur) {
                float x1;
                float z1;
                float x2;
                float z2;
                float xDraw;
                float zDraw;

                if (isPerspektif) {
                    x1 = koorNode[i[0]][0] / (focalLength + koorNode[i[0]][1]) * focalLength;
                    z1 = koorNode[i[0]][2] / (focalLength + koorNode[i[0]][1]) * focalLength;

                    x2 = koorNode[i[1]][0] / (focalLength + koorNode[i[1]][1]) * focalLength;
                    z2 = koorNode[i[1]][2] / (focalLength + koorNode[i[1]][1]) * focalLength;

                } else {
                    x1 = koorNode[i[0]][0];
                    z1 = koorNode[i[0]][2];

                    x2 = koorNode[i[1]][0];
                    z2 = koorNode[i[1]][2];
                }

                xDraw = (x1 + x2) / 2f;
                zDraw = (z1 + z2) / 2f;

                String text = listFrameSection.get(pointerFrameSection[x]).getSectionCode();
                canvas.scale(1, -1);
                canvas.drawText(text, xDraw, -(zDraw), pText);
                canvas.scale(1, -1);
                x++;
            }
        } else if (tandaViewBottomNav == 2) {
            float dummyFloat1 = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[2] == dummyFloat1 && koor2[2] == dummyFloat1) {
                    String text = listFrameSection.get(pointerFrameSection[x]).getSectionCode();
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[0] + koor2[0]) / 2f;
                    zDraw = (koor1[1] + koor2[1]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        } else if (tandaViewBottomNav == 3) {
            float dummyFloat1 = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[1] == dummyFloat1 && koor2[1] == dummyFloat1) {
                    String text = listFrameSection.get(pointerFrameSection[x]).getSectionCode();
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[0] + koor2[0]) / 2f;
                    zDraw = (koor1[2] + koor2[2]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        } else if (tandaViewBottomNav == 4) {
            float dummyFloat1 = koorNodeFix[(viewYZ_Current - 1)][0];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[0] == dummyFloat1 && koor2[0] == dummyFloat1) {
                    String text = listFrameSection.get(pointerFrameSection[x]).getSectionCode();
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[1] + koor2[1]) / 2f;
                    zDraw = (koor1[2] + koor2[2]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        }

    }

    private void drawDesignSectionText() {
        if (tandaViewBottomNav == 1) {
            int x = 0;
            for (int[] i : pointerElemenStruktur) {
                float x1;
                float z1;
                float x2;
                float z2;
                float xDraw;
                float zDraw;

                if (isPerspektif) {
                    x1 = koorNode[i[0]][0] / (focalLength + koorNode[i[0]][1]) * focalLength;
                    z1 = koorNode[i[0]][2] / (focalLength + koorNode[i[0]][1]) * focalLength;

                    x2 = koorNode[i[1]][0] / (focalLength + koorNode[i[1]][1]) * focalLength;
                    z2 = koorNode[i[1]][2] / (focalLength + koorNode[i[1]][1]) * focalLength;

                } else {
                    x1 = koorNode[i[0]][0];
                    z1 = koorNode[i[0]][2];

                    x2 = koorNode[i[1]][0];
                    z2 = koorNode[i[1]][2];
                }

                xDraw = (x1 + x2) / 2f;
                zDraw = (z1 + z2) / 2f;

                String text = listBebanBatang.get(x).getProfil();
                if(text==null){
                    text = "null";
                }
                canvas.scale(1, -1);
                canvas.drawText(text, xDraw, -(zDraw), pText);
                canvas.scale(1, -1);
                x++;
            }
        } else if (tandaViewBottomNav == 2) {
            float dummyFloat1 = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[2] == dummyFloat1 && koor2[2] == dummyFloat1) {
                    String text = listBebanBatang.get(x).getProfil();
                    if(text==null){
                        text = "null";
                    }
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[0] + koor2[0]) / 2f;
                    zDraw = (koor1[1] + koor2[1]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        } else if (tandaViewBottomNav == 3) {
            float dummyFloat1 = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[1] == dummyFloat1 && koor2[1] == dummyFloat1) {
                    String text = listBebanBatang.get(x).getProfil();
                    if(text==null){
                        text = "null";
                    }
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[0] + koor2[0]) / 2f;
                    zDraw = (koor1[2] + koor2[2]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        } else if (tandaViewBottomNav == 4) {
            float dummyFloat1 = koorNodeFix[(viewYZ_Current - 1)][0];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[0] == dummyFloat1 && koor2[0] == dummyFloat1) {
                    String text = listBebanBatang.get(x).getProfil();
                    if(text==null){
                        text = "null";
                    }
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[1] + koor2[1]) / 2f;
                    zDraw = (koor1[2] + koor2[2]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        }
    }

    private void drawSFPMText() {
        String text = null;
        if (tandaViewBottomNav == 1) {
            int x = 0;
            for (int[] i : pointerElemenStruktur) {
                float x1;
                float z1;
                float x2;
                float z2;
                float xDraw;
                float zDraw;

                if (isPerspektif) {
                    x1 = koorNode[i[0]][0] / (focalLength + koorNode[i[0]][1]) * focalLength;
                    z1 = koorNode[i[0]][2] / (focalLength + koorNode[i[0]][1]) * focalLength;

                    x2 = koorNode[i[1]][0] / (focalLength + koorNode[i[1]][1]) * focalLength;
                    z2 = koorNode[i[1]][2] / (focalLength + koorNode[i[1]][1]) * focalLength;

                } else {
                    x1 = koorNode[i[0]][0];
                    z1 = koorNode[i[0]][2];

                    x2 = koorNode[i[1]][0];
                    z2 = koorNode[i[1]][2];
                }

                xDraw = (x1 + x2) / 2f;
                zDraw = (z1 + z2) / 2f;

                if(isAnalysis) {
                    text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanPMAnalysis());
                } else {
                    text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanPMDesign());
                }
                canvas.scale(1, -1);
                canvas.drawText(text, xDraw, -(zDraw), pText);
                canvas.scale(1, -1);
                x++;
            }
        } else if (tandaViewBottomNav == 2) {
            float dummyFloat1 = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[2] == dummyFloat1 && koor2[2] == dummyFloat1) {
                    if(isAnalysis) {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanPMAnalysis());
                    } else {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanPMDesign());
                    }
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[0] + koor2[0]) / 2f;
                    zDraw = (koor1[1] + koor2[1]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        } else if (tandaViewBottomNav == 3) {
            float dummyFloat1 = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[1] == dummyFloat1 && koor2[1] == dummyFloat1) {
                    if(isAnalysis) {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanPMAnalysis());
                    } else {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanPMDesign());
                    }
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[0] + koor2[0]) / 2f;
                    zDraw = (koor1[2] + koor2[2]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        } else if (tandaViewBottomNav == 4) {
            float dummyFloat1 = koorNodeFix[(viewYZ_Current - 1)][0];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[0] == dummyFloat1 && koor2[0] == dummyFloat1) {
                    if(isAnalysis) {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanPMAnalysis());
                    } else {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanPMDesign());
                    }
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[1] + koor2[1]) / 2f;
                    zDraw = (koor1[2] + koor2[2]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        }
    }

    private void drawSFShearText() {
        String text = null;
        if (tandaViewBottomNav == 1) {
            int x = 0;
            for (int[] i : pointerElemenStruktur) {
                float x1;
                float z1;
                float x2;
                float z2;
                float xDraw;
                float zDraw;

                if (isPerspektif) {
                    x1 = koorNode[i[0]][0] / (focalLength + koorNode[i[0]][1]) * focalLength;
                    z1 = koorNode[i[0]][2] / (focalLength + koorNode[i[0]][1]) * focalLength;

                    x2 = koorNode[i[1]][0] / (focalLength + koorNode[i[1]][1]) * focalLength;
                    z2 = koorNode[i[1]][2] / (focalLength + koorNode[i[1]][1]) * focalLength;

                } else {
                    x1 = koorNode[i[0]][0];
                    z1 = koorNode[i[0]][2];

                    x2 = koorNode[i[1]][0];
                    z2 = koorNode[i[1]][2];
                }

                xDraw = (x1 + x2) / 2f;
                zDraw = (z1 + z2) / 2f;

                if(isAnalysis) {
                    text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanVAnalysis());
                } else {
                    text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanVDesign());
                }
                canvas.scale(1, -1);
                canvas.drawText(text, xDraw, -(zDraw), pText);
                canvas.scale(1, -1);
                x++;
            }
        } else if (tandaViewBottomNav == 2) {
            float dummyFloat1 = koorNodeFix[(viewXY_Current - 1) * (Nbx + 1) * (Nby + 1)][2];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[2] == dummyFloat1 && koor2[2] == dummyFloat1) {
                    if(isAnalysis) {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanVAnalysis());
                    } else {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanVDesign());
                    }
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[0] + koor2[0]) / 2f;
                    zDraw = (koor1[1] + koor2[1]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        } else if (tandaViewBottomNav == 3) {
            float dummyFloat1 = koorNodeFix[(viewXZ_Current - 1) * (Nbx + 1)][1];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[1] == dummyFloat1 && koor2[1] == dummyFloat1) {
                    if(isAnalysis) {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanVAnalysis());
                    } else {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanVDesign());
                    }
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[0] + koor2[0]) / 2f;
                    zDraw = (koor1[2] + koor2[2]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        } else if (tandaViewBottomNav == 4) {
            float dummyFloat1 = koorNodeFix[(viewYZ_Current - 1)][0];

            int x = 0;
            for (int[] pointer : pointerElemenStruktur) {
                float[] koor1 = koorNodeFix[pointer[0]];
                float[] koor2 = koorNodeFix[pointer[1]];

                if (koor1[0] == dummyFloat1 && koor2[0] == dummyFloat1) {
                    if(isAnalysis) {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanVAnalysis());
                    } else {
                        text = new DecimalFormat("0.00").format(listBebanBatang.get(x).getAmanVDesign());
                    }
                    float xDraw;
                    float zDraw;

                    xDraw = (koor1[1] + koor2[1]) / 2f;
                    zDraw = (koor1[2] + koor2[2]) / 2f;

                    canvas.scale(1, -1);
                    canvas.drawText(text, xDraw, -(zDraw), pText);
                    canvas.scale(1, -1);
                }
                x++;
            }
        }
    }

    //----------SET KOORDINAT----------//
    private void setKoorNode(int Nst, int Nbx, int Nby, float Sth, float Bwx, float Bwy) {
        koorNode = new float[(Nst + 1) * (Nbx + 1) * (Nby + 1)][3];
        koorNodeFix = new float[(Nst + 1) * (Nbx + 1) * (Nby + 1)][3];
        koorNodeReal = new float[(Nst + 1) * (Nbx + 1) * (Nby + 1)][3];

        koorNodeAreaSelect = new float[(Nst) * (Nbx) * (Nby)][5][3];
        koor3D_Axis = new float[3][2 + 9 * 4][3];
        koor3D_AxisFix = new float[3][2 + 9 * 4][3];

        pointerElemenKolom = new int[(Nst) * (Nbx + 1) * (Nby + 1)][2];
        pointerElemenBalokXZ = new int[Nst * Nbx * (Nby + 1)][2];
        pointerElemenBalokYZ = new int[Nst * Nby * (Nbx + 1)][2];

        int sum1 = Nst * (Nbx + 1) * (Nby + 1); //KOLOM
        int sum2 = Nst * Nbx * (Nby + 1); //BALOK XZ
        int sum3 = Nst * Nby * (Nbx + 1); //BALOK YZ
        int sum4 = sum1 + sum2 + sum3;

        pointerElemenStruktur = new int[sum4][2];
        pointerAreaJoint = new int[Nbx * Nby * Nst][4];

        float maxX = Nbx * Bwx;
        float maxY = Nby * Bwy;
        float maxZ = Nst * Sth;
        float max;
        max = Math.max(maxZ, Math.max(maxX, maxY));

        int x = 0;
        int y;
        for (int i = 0; i <= Nst; i++) {
            for (int j = 0; j <= Nby; j++) {
                for (int k = 0; k <= Nbx; k++) {
                    koorNode[x][0] = -(0.5f * maxX) / (0.5f * max) + k * Bwx / (0.5f * max);
                    koorNode[x][1] = -(0.5f * maxY) / (0.5f * max) + j * Bwy / (0.5f * max);
                    koorNode[x][2] = -(0.5f * maxZ) / (0.5f * max) + i * Sth / (0.5f * max);

                    koorNodeFix[x][0] = -(0.5f * maxX) / (0.5f * max) + k * Bwx / (0.5f * max);
                    koorNodeFix[x][1] = -(0.5f * maxY) / (0.5f * max) + j * Bwy / (0.5f * max);
                    koorNodeFix[x][2] = -(0.5f * maxZ) / (0.5f * max) + i * Sth / (0.5f * max);
                    x++;
                }
            }
        }

        x = 0;
        for (int i = 0; i <= Nst; i++) {
            for (int j = 0; j <= Nby; j++) {
                for (int k = 0; k <= Nbx; k++) {
                    koorNodeReal[x][0] = k * Bwx * 1000;
                    koorNodeReal[x][1] = j * Bwy * 1000;
                    koorNodeReal[x][2] = i * Sth * 1000;
                    x++;
                }
            }
        }

        x = (Nbx + 1) * (Nby + 1);
        y = 1;
        int z = 1;
        float offset = 25f / 315f;

        for (int i = 0; i < Nst * Nbx * Nby; i++) {
            koorNodeAreaSelect[i][0][0] = koorNode[x][0] + offset;
            koorNodeAreaSelect[i][0][1] = koorNode[x][1] + offset;
            koorNodeAreaSelect[i][0][2] = koorNode[x][2];

            koorNodeAreaSelect[i][1][0] = koorNode[x + 1][0] - offset;
            koorNodeAreaSelect[i][1][1] = koorNode[x + 1][1] + offset;
            koorNodeAreaSelect[i][1][2] = koorNode[x + 1][2];

            koorNodeAreaSelect[i][2][0] = koorNode[x + 1 + Nbx + 1][0] - offset;
            koorNodeAreaSelect[i][2][1] = koorNode[x + 1 + Nbx + 1][1] - offset;
            koorNodeAreaSelect[i][2][2] = koorNode[x + 1 + Nbx + 1][2];

            koorNodeAreaSelect[i][3][0] = koorNode[x + 1 + Nbx][0] + offset;
            koorNodeAreaSelect[i][3][1] = koorNode[x + 1 + Nbx][1] - offset;
            koorNodeAreaSelect[i][3][2] = koorNode[x + 1 + Nbx][2];

            koorNodeAreaSelect[i][4][0] = koorNode[x][0] + offset;
            koorNodeAreaSelect[i][4][1] = koorNode[x][1] + offset;
            koorNodeAreaSelect[i][4][2] = koorNode[x][2];

            if (z == Nby && y == Nbx) {
                z = 1;
                y = 1;
                x += 2 + Nbx + 1;
            } else if (y == Nbx) {
                z++;
                y = 1;
                x += 2;
            } else {
                y++;
                x++;
            }
        }

        x = (Nbx + 1) * (Nby + 1);
        y = 1;
        z = 1;

        for (int i = 0; i < Nst * Nbx * Nby; i++) {
            pointerAreaJoint[i][0] = x;
            pointerAreaJoint[i][1] = x + 1;
            pointerAreaJoint[i][2] = x + 1 + Nbx + 1;
            pointerAreaJoint[i][3] = x + 1 + Nbx;

            if (z == Nby && y == Nbx) {
                z = 1;
                y = 1;
                x += 2 + Nbx + 1;
            } else if (y == Nbx) {
                z++;
                y = 1;
                x += 2;
            } else {
                y++;
                x++;
            }
        }

        x = 0;
        int counter = 0;
        for (int i = 0; i < Nst; i++) {
            for (int j = 0; j <= Nby; j++) {
                for (int k = 0; k <= Nbx; k++) {
                    pointerElemenKolom[x][0] = x;
                    pointerElemenKolom[x][1] = x + (Nbx + 1) * (Nby + 1);

                    pointerElemenStruktur[counter][0] = x;
                    pointerElemenStruktur[counter][1] = x + (Nbx + 1) * (Nby + 1);
                    x++;
                    counter++;
                }
            }
        }

        x = 0;
        y = 0;
        for (int i = 0; i < Nst; i++) {
            for (int j = 0; j <= Nby; j++) {
                for (int k = 0; k < Nbx; k++) {
                    pointerElemenBalokXZ[x][0] = y + (Nbx + 1) * (Nby + 1);
                    pointerElemenBalokXZ[x][1] = y + (Nbx + 1) * (Nby + 1) + 1;

                    pointerElemenStruktur[counter][0] = y + (Nbx + 1) * (Nby + 1);
                    pointerElemenStruktur[counter][1] = y + (Nbx + 1) * (Nby + 1) + 1;
                    x++;
                    y++;
                    counter++;
                }
                y++;
            }
        }

        x = 0;
        y = 0;
        for (int i = 0; i < Nst; i++) {
            for (int j = 0; j < Nby; j++) {
                for (int k = 0; k <= Nbx; k++) {
                    pointerElemenBalokYZ[x][0] = y + (Nbx + 1) * (Nby + 1);
                    pointerElemenBalokYZ[x][1] = y + (Nbx + 1) * (Nby + 1) + Nbx + 1;

                    pointerElemenStruktur[counter][0] = y + (Nbx + 1) * (Nby + 1);
                    pointerElemenStruktur[counter][1] = y + (Nbx + 1) * (Nby + 1) + Nbx + 1;
                    x++;
                    y++;
                    counter++;
                }
            }
            y = y + Nbx + 1;
        }

        setKoor3D_Axis(0f, 0f, 0.5f, 0.1f, 0.03f);
    }

    private void setKoor3D_Axis(float offsetX, float offsetY, float panjang, float ujung, float ujungPendek) {
        //X
        koor3D_Axis[0][0][0] = koorNode[0][0] - offsetX + panjang;
        koor3D_Axis[0][0][1] = koorNode[0][1] - offsetY;
        koor3D_Axis[0][0][2] = koorNode[0][2];

        koor3D_Axis[0][1][0] = koorNode[0][0] - offsetX;
        koor3D_Axis[0][1][1] = koorNode[0][1] - offsetY;
        koor3D_Axis[0][1][2] = koorNode[0][2];

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopY = sin * ujungPendek;
            float offsetLoopZ = ujungPendek - cos * ujungPendek;

            koor3D_Axis[0][1 + i][0] = (koorNode[0][0] - offsetX) + panjang - ujung;
            koor3D_Axis[0][1 + i][1] = (koorNode[0][1] - offsetY) + offsetLoopY;
            koor3D_Axis[0][1 + i][2] = (koorNode[0][2]) + ujungPendek - offsetLoopZ;
        }

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopY = ujungPendek - cos * ujungPendek;
            float offsetLoopZ = sin * ujungPendek;

            koor3D_Axis[0][10 + i][0] = (koorNode[0][0] - offsetX) + panjang - ujung;
            koor3D_Axis[0][10 + i][1] = (koorNode[0][1] - offsetY) + ujungPendek - offsetLoopY;
            koor3D_Axis[0][10 + i][2] = (koorNode[0][2]) - offsetLoopZ;
        }

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopY = sin * ujungPendek;
            float offsetLoopZ = ujungPendek - cos * ujungPendek;

            koor3D_Axis[0][19 + i][0] = (koorNode[0][0] - offsetX) + panjang - ujung;
            koor3D_Axis[0][19 + i][1] = (koorNode[0][1] - offsetY) - offsetLoopY;
            koor3D_Axis[0][19 + i][2] = (koorNode[0][2]) - ujungPendek + offsetLoopZ;
        }

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopY = ujungPendek - cos * ujungPendek;
            float offsetLoopZ = sin * ujungPendek;

            koor3D_Axis[0][28 + i][0] = (koorNode[0][0] - offsetX) + panjang - ujung;
            koor3D_Axis[0][28 + i][1] = (koorNode[0][1] - offsetY) - ujungPendek + offsetLoopY;
            koor3D_Axis[0][28 + i][2] = (koorNode[0][2]) + offsetLoopZ;
        }

        //Y
        koor3D_Axis[1][0][0] = koorNode[0][0] - offsetX;
        koor3D_Axis[1][0][1] = koorNode[0][1] - offsetY + panjang;
        koor3D_Axis[1][0][2] = koorNode[0][2];

        koor3D_Axis[1][1][0] = koorNode[0][0] - offsetX;
        koor3D_Axis[1][1][1] = koorNode[0][1] - offsetY;
        koor3D_Axis[1][1][2] = koorNode[0][2];

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopX = sin * ujungPendek;
            float offsetLoopZ = ujungPendek - cos * ujungPendek;

            koor3D_Axis[1][1 + i][0] = (koorNode[0][0] - offsetX) - offsetLoopX;
            koor3D_Axis[1][1 + i][1] = (koorNode[0][1] - offsetY) + panjang - ujung;
            koor3D_Axis[1][1 + i][2] = (koorNode[0][2]) + ujungPendek - offsetLoopZ;
        }

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopX = ujungPendek - cos * ujungPendek;
            float offsetLoopZ = sin * ujungPendek;

            koor3D_Axis[1][10 + i][0] = (koorNode[0][0] - offsetX) - ujungPendek + offsetLoopX;
            koor3D_Axis[1][10 + i][1] = (koorNode[0][1] - offsetY) + panjang - ujung;
            koor3D_Axis[1][10 + i][2] = (koorNode[0][2]) - offsetLoopZ;
        }

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopX = sin * ujungPendek;
            float offsetLoopZ = ujungPendek - cos * ujungPendek;

            koor3D_Axis[1][19 + i][0] = (koorNode[0][0] - offsetX + offsetLoopX);
            koor3D_Axis[1][19 + i][1] = (koorNode[0][1] - offsetY) + panjang - ujung;
            koor3D_Axis[1][19 + i][2] = (koorNode[0][2]) - ujungPendek + offsetLoopZ;
        }

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopX = ujungPendek - cos * ujungPendek;
            float offsetLoopZ = sin * ujungPendek;

            koor3D_Axis[1][28 + i][0] = (koorNode[0][0] - offsetX) + ujungPendek - offsetLoopX;
            koor3D_Axis[1][28 + i][1] = (koorNode[0][1] - offsetY) + panjang - ujung;
            koor3D_Axis[1][28 + i][2] = (koorNode[0][2]) + offsetLoopZ;
        }

        //Z
        koor3D_Axis[2][0][0] = koorNode[0][0] - offsetX;
        koor3D_Axis[2][0][1] = koorNode[0][1] - offsetY;
        koor3D_Axis[2][0][2] = koorNode[0][2] + panjang;

        koor3D_Axis[2][1][0] = koorNode[0][0] - offsetX;
        koor3D_Axis[2][1][1] = koorNode[0][1] - offsetY;
        koor3D_Axis[2][1][2] = koorNode[0][2];

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopX = sin * ujungPendek;
            float offsetLoopY = ujungPendek - cos * ujungPendek;

            koor3D_Axis[2][1 + i][0] = (koorNode[0][0] - offsetX) - offsetLoopX;
            koor3D_Axis[2][1 + i][1] = (koorNode[0][1] - offsetY) - ujungPendek + offsetLoopY;
            koor3D_Axis[2][1 + i][2] = (koorNode[0][2]) + panjang - ujung;
        }

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopX = ujungPendek - cos * ujungPendek;
            float offsetLoopY = sin * ujungPendek;

            koor3D_Axis[2][10 + i][0] = (koorNode[0][0] - offsetX) - ujungPendek + offsetLoopX;
            koor3D_Axis[2][10 + i][1] = (koorNode[0][1] - offsetY) + offsetLoopY;
            koor3D_Axis[2][10 + i][2] = (koorNode[0][2]) + panjang - ujung;
        }

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopX = sin * ujungPendek;
            float offsetLoopY = ujungPendek - cos * ujungPendek;

            koor3D_Axis[2][19 + i][0] = (koorNode[0][0] - offsetX) + offsetLoopX;
            koor3D_Axis[2][19 + i][1] = (koorNode[0][1] - offsetY) + ujungPendek - offsetLoopY;
            koor3D_Axis[2][19 + i][2] = (koorNode[0][2]) + panjang - ujung;
        }

        for (int i = 1; i <= 9; i++) {
            float sudut = (i - 1) * 10 * Float.parseFloat(Double.toString(Math.PI / 180.0));
            float sin = Float.parseFloat(Double.toString(Math.sin(sudut)));
            float cos = Float.parseFloat(Double.toString(Math.cos(sudut)));

            float offsetLoopX = ujungPendek - cos * ujungPendek;
            float offsetLoopY = sin * ujungPendek;

            koor3D_Axis[2][28 + i][0] = (koorNode[0][0] - offsetX) + ujungPendek - offsetLoopX;
            koor3D_Axis[2][28 + i][1] = (koorNode[0][1] - offsetY) - offsetLoopY;
            koor3D_Axis[2][28 + i][2] = (koorNode[0][2]) + panjang - ujung;
        }

        int x = 0;
        for (float[][] i : koor3D_AxisFix) {
            int y = 0;
            for (float[] j : i) {
                j[0] = koor3D_Axis[x][y][0];
                j[1] = koor3D_Axis[x][y][1];
                j[2] = koor3D_Axis[x][y][2];

                y++;
            }
            x++;
        }
    }

    private void setPinRestraint(int Nbx, int Nby, float offset) {
        pinRestraint = new float[(Nbx + 1) * (Nby + 1)][7][3];
        tumpuanSendi2 = new float[(Nbx + 1) * (Nby + 1)][7][3];
        pinRestraintFix = new float[(Nbx + 1) * (Nby + 1)][7][3];
        tumpuanSendi2Fix = new float[(Nbx + 1) * (Nby + 1)][7][3];

        int x = 0;
        for (int i = 0; i <= Nby; i++) {
            for (int j = 0; j <= Nbx; j++) {
                pinRestraint[x][0][0] = koorNode[x][0];
                pinRestraint[x][0][1] = koorNode[x][1];
                pinRestraint[x][0][2] = koorNode[x][2];

                pinRestraint[x][1][0] = koorNode[x][0] + offset;
                pinRestraint[x][1][1] = koorNode[x][1];
                pinRestraint[x][1][2] = koorNode[x][2] - offset;

                pinRestraint[x][2][0] = koorNode[x][0] - offset;
                pinRestraint[x][2][1] = koorNode[x][1];
                pinRestraint[x][2][2] = koorNode[x][2] - offset;

                pinRestraint[x][3][0] = koorNode[x][0];
                pinRestraint[x][3][1] = koorNode[x][1];
                pinRestraint[x][3][2] = koorNode[x][2];

                pinRestraint[x][4][0] = koorNode[x][0];
                pinRestraint[x][4][1] = koorNode[x][1] + offset;
                pinRestraint[x][4][2] = koorNode[x][2] - offset;

                pinRestraint[x][5][0] = koorNode[x][0];
                pinRestraint[x][5][1] = koorNode[x][1] - offset;
                pinRestraint[x][5][2] = koorNode[x][2] - offset;

                pinRestraint[x][6][0] = koorNode[x][0];
                pinRestraint[x][6][1] = koorNode[x][1];
                pinRestraint[x][6][2] = koorNode[x][2];

                x++;
            }
        }

        x = 0;
        for (int i = 0; i <= Nby; i++) {
            for (int j = 0; j <= Nbx; j++) {
                pinRestraintFix[x][0][0] = koorNode[x][0];
                pinRestraintFix[x][0][1] = koorNode[x][1];
                pinRestraintFix[x][0][2] = koorNode[x][2];

                pinRestraintFix[x][1][0] = koorNode[x][0] + offset;
                pinRestraintFix[x][1][1] = koorNode[x][1];
                pinRestraintFix[x][1][2] = koorNode[x][2] - offset;

                pinRestraintFix[x][2][0] = koorNode[x][0] - offset;
                pinRestraintFix[x][2][1] = koorNode[x][1];
                pinRestraintFix[x][2][2] = koorNode[x][2] - offset;

                pinRestraintFix[x][3][0] = koorNode[x][0];
                pinRestraintFix[x][3][1] = koorNode[x][1];
                pinRestraintFix[x][3][2] = koorNode[x][2];

                pinRestraintFix[x][4][0] = koorNode[x][0];
                pinRestraintFix[x][4][1] = koorNode[x][1] + offset;
                pinRestraintFix[x][4][2] = koorNode[x][2] - offset;

                pinRestraintFix[x][5][0] = koorNode[x][0];
                pinRestraintFix[x][5][1] = koorNode[x][1] - offset;
                pinRestraintFix[x][5][2] = koorNode[x][2] - offset;

                pinRestraintFix[x][6][0] = koorNode[x][0];
                pinRestraintFix[x][6][1] = koorNode[x][1];
                pinRestraintFix[x][6][2] = koorNode[x][2];

                x++;
            }
        }

        x = 0;
        for (int i = 0; i <= Nby; i++) {
            for (int j = 0; j <= Nbx; j++) {
                tumpuanSendi2[x][0][0] = koorNode[x][0];
                tumpuanSendi2[x][0][1] = koorNode[x][1];
                tumpuanSendi2[x][0][2] = koorNode[x][2];

                tumpuanSendi2[x][1][0] = koorNode[x][0];
                tumpuanSendi2[x][1][1] = koorNode[x][1] + offset;
                tumpuanSendi2[x][1][2] = koorNode[x][2] - offset;

                tumpuanSendi2[x][2][0] = koorNode[x][0];
                tumpuanSendi2[x][2][1] = koorNode[x][1] - offset;
                tumpuanSendi2[x][2][2] = koorNode[x][2] - offset;

                tumpuanSendi2[x][3][0] = koorNode[x][0];
                tumpuanSendi2[x][3][1] = koorNode[x][1];
                tumpuanSendi2[x][3][2] = koorNode[x][2];

                tumpuanSendi2[x][4][0] = koorNode[x][0] + offset;
                tumpuanSendi2[x][4][1] = koorNode[x][1];
                tumpuanSendi2[x][4][2] = koorNode[x][2] - offset;

                tumpuanSendi2[x][5][0] = koorNode[x][0] - offset;
                tumpuanSendi2[x][5][1] = koorNode[x][1];
                tumpuanSendi2[x][5][2] = koorNode[x][2] - offset;

                tumpuanSendi2[x][6][0] = koorNode[x][0];
                tumpuanSendi2[x][6][1] = koorNode[x][1];
                tumpuanSendi2[x][6][2] = koorNode[x][2];

                x++;
            }
        }

        x = 0;
        for (int i = 0; i <= Nby; i++) {
            for (int j = 0; j <= Nbx; j++) {
                tumpuanSendi2Fix[x][0][0] = koorNode[x][0];
                tumpuanSendi2Fix[x][0][1] = koorNode[x][1];
                tumpuanSendi2Fix[x][0][2] = koorNode[x][2];

                tumpuanSendi2Fix[x][1][0] = koorNode[x][0];
                tumpuanSendi2Fix[x][1][1] = koorNode[x][1] + offset;
                tumpuanSendi2Fix[x][1][2] = koorNode[x][2] - offset;

                tumpuanSendi2Fix[x][2][0] = koorNode[x][0];
                tumpuanSendi2Fix[x][2][1] = koorNode[x][1] - offset;
                tumpuanSendi2Fix[x][2][2] = koorNode[x][2] - offset;

                tumpuanSendi2Fix[x][3][0] = koorNode[x][0];
                tumpuanSendi2Fix[x][3][1] = koorNode[x][1];
                tumpuanSendi2Fix[x][3][2] = koorNode[x][2];

                tumpuanSendi2Fix[x][4][0] = koorNode[x][0] + offset;
                tumpuanSendi2Fix[x][4][1] = koorNode[x][1];
                tumpuanSendi2Fix[x][4][2] = koorNode[x][2] - offset;

                tumpuanSendi2Fix[x][5][0] = koorNode[x][0] - offset;
                tumpuanSendi2Fix[x][5][1] = koorNode[x][1];
                tumpuanSendi2Fix[x][5][2] = koorNode[x][2] - offset;

                tumpuanSendi2Fix[x][6][0] = koorNode[x][0];
                tumpuanSendi2Fix[x][6][1] = koorNode[x][1];
                tumpuanSendi2Fix[x][6][2] = koorNode[x][2];

                x++;
            }
        }
        //System.arraycopy(pinRestraint, 0, pinRestraintFix, 0, pinRestraint.length);
    }

    private void setTumpuanJepit(int N_Bx, int N_By, float offset) {
        tumpuanJepit1 = new float[(N_Bx + 1) * (N_By + 1)][5][3];
        tumpuanJepit2 = new float[(N_Bx + 1) * (N_By + 1)][5][3];
        tumpuanJepit1Fix = new float[(N_Bx + 1) * (N_By + 1)][5][3];
        tumpuanJepit2Fix = new float[(N_Bx + 1) * (N_By + 1)][5][3];

        int x = 0;
        for (int i = 0; i <= N_By; i++) {
            for (int j = 0; j <= N_Bx; j++) {
                tumpuanJepit1[x][0][0] = koorNode[x][0] + offset;
                tumpuanJepit1[x][0][1] = koorNode[x][1];
                tumpuanJepit1[x][0][2] = koorNode[x][2];

                tumpuanJepit1[x][1][0] = koorNode[x][0] - offset;
                tumpuanJepit1[x][1][1] = koorNode[x][1];
                tumpuanJepit1[x][1][2] = koorNode[x][2];

                tumpuanJepit1[x][2][0] = koorNode[x][0] - offset;
                tumpuanJepit1[x][2][1] = koorNode[x][1];
                tumpuanJepit1[x][2][2] = koorNode[x][2] - offset;

                tumpuanJepit1[x][3][0] = koorNode[x][0] + offset;
                tumpuanJepit1[x][3][1] = koorNode[x][1];
                tumpuanJepit1[x][3][2] = koorNode[x][2] - offset;

                tumpuanJepit1[x][4][0] = koorNode[x][0] + offset;
                tumpuanJepit1[x][4][1] = koorNode[x][1];
                tumpuanJepit1[x][4][2] = koorNode[x][2];
                x++;
            }
        }

        x = 0;
        for (int i = 0; i <= N_By; i++) {
            for (int j = 0; j <= N_Bx; j++) {
                tumpuanJepit1Fix[x][0][0] = koorNode[x][0] + offset;
                tumpuanJepit1Fix[x][0][1] = koorNode[x][1];
                tumpuanJepit1Fix[x][0][2] = koorNode[x][2];

                tumpuanJepit1Fix[x][1][0] = koorNode[x][0] - offset;
                tumpuanJepit1Fix[x][1][1] = koorNode[x][1];
                tumpuanJepit1Fix[x][1][2] = koorNode[x][2];

                tumpuanJepit1Fix[x][2][0] = koorNode[x][0] - offset;
                tumpuanJepit1Fix[x][2][1] = koorNode[x][1];
                tumpuanJepit1Fix[x][2][2] = koorNode[x][2] - offset;

                tumpuanJepit1Fix[x][3][0] = koorNode[x][0] + offset;
                tumpuanJepit1Fix[x][3][1] = koorNode[x][1];
                tumpuanJepit1Fix[x][3][2] = koorNode[x][2] - offset;

                tumpuanJepit1Fix[x][4][0] = koorNode[x][0] + offset;
                tumpuanJepit1Fix[x][4][1] = koorNode[x][1];
                tumpuanJepit1Fix[x][4][2] = koorNode[x][2];
                x++;
            }
        }

        x = 0;
        for (int i = 0; i <= N_By; i++) {
            for (int j = 0; j <= N_Bx; j++) {
                tumpuanJepit2[x][0][0] = koorNode[x][0];
                tumpuanJepit2[x][0][1] = koorNode[x][1] + offset;
                tumpuanJepit2[x][0][2] = koorNode[x][2];

                tumpuanJepit2[x][1][0] = koorNode[x][0];
                tumpuanJepit2[x][1][1] = koorNode[x][1] - offset;
                tumpuanJepit2[x][1][2] = koorNode[x][2];

                tumpuanJepit2[x][2][0] = koorNode[x][0];
                tumpuanJepit2[x][2][1] = koorNode[x][1] - offset;
                tumpuanJepit2[x][2][2] = koorNode[x][2] - offset;

                tumpuanJepit2[x][3][0] = koorNode[x][0];
                tumpuanJepit2[x][3][1] = koorNode[x][1] + offset;
                tumpuanJepit2[x][3][2] = koorNode[x][2] - offset;

                tumpuanJepit2[x][4][0] = koorNode[x][0];
                tumpuanJepit2[x][4][1] = koorNode[x][1] + offset;
                tumpuanJepit2[x][4][2] = koorNode[x][2];
                x++;
            }
        }

        x = 0;
        for (int i = 0; i <= N_By; i++) {
            for (int j = 0; j <= N_Bx; j++) {
                tumpuanJepit2Fix[x][0][0] = koorNode[x][0];
                tumpuanJepit2Fix[x][0][1] = koorNode[x][1] + offset;
                tumpuanJepit2Fix[x][0][2] = koorNode[x][2];

                tumpuanJepit2Fix[x][1][0] = koorNode[x][0];
                tumpuanJepit2Fix[x][1][1] = koorNode[x][1] - offset;
                tumpuanJepit2Fix[x][1][2] = koorNode[x][2];

                tumpuanJepit2Fix[x][2][0] = koorNode[x][0];
                tumpuanJepit2Fix[x][2][1] = koorNode[x][1] - offset;
                tumpuanJepit2Fix[x][2][2] = koorNode[x][2] - offset;

                tumpuanJepit2Fix[x][3][0] = koorNode[x][0];
                tumpuanJepit2Fix[x][3][1] = koorNode[x][1] + offset;
                tumpuanJepit2Fix[x][3][2] = koorNode[x][2] - offset;

                tumpuanJepit2Fix[x][4][0] = koorNode[x][0];
                tumpuanJepit2Fix[x][4][1] = koorNode[x][1] + offset;
                tumpuanJepit2Fix[x][4][2] = koorNode[x][2];
                x++;
            }
        }
    }

    public void setKoorToDrawLoad() {
        int x = 0;
        float panjang;
        float ujung;
        float ujungPendek;
        float tinggiBeban;
        float lengthBetweenX = (koorNodeFix[1][0] - koorNodeFix[0][0]) / 6f;
        float lengthBetweenY = (koorNodeFix[Nbx + 1][1] - koorNodeFix[0][1]) / 6f;
        float lengthBetweenZ = (koorNodeFix[(Nbx + 1) * (Nby + 1)][2] - koorNodeFix[0][2]) / 6f;

        for (float[] i : koorNode) {
            i[0] = koorNodeFix[x][0];
            i[1] = koorNodeFix[x][1];
            i[2] = koorNodeFix[x][2];
            x++;
        }

        //Joint Load
        x = 0;
        for (float[] i : koorNode) {
            for (int j = 0; j < 6; j++) {
                if (isJointLoadExist[x][j]) {
                    panjang = 100f;
                    ujung = 12.5f;
                    ujungPendek = ujung / 2f;

                    if (jointLoad[x][j] < 0) {
                        panjang = -100f;
                        ujung = -25f;
                    }

                    if (j == 0) { //FX
                        koorNodeJointLoad[x][j][0][0] = i[0];
                        koorNodeJointLoad[x][j][0][1] = i[1];
                        koorNodeJointLoad[x][j][0][2] = i[2];

                        koorNodeJointLoad[x][j][1][0] = i[0] - panjang;
                        koorNodeJointLoad[x][j][1][1] = i[1];
                        koorNodeJointLoad[x][j][1][2] = i[2];

                        koorNodeJointLoad[x][j][2][0] = i[0] - ujung;
                        koorNodeJointLoad[x][j][2][1] = i[1];
                        koorNodeJointLoad[x][j][2][2] = i[2] + ujungPendek;

                        koorNodeJointLoad[x][j][3][0] = i[0] - ujung;
                        koorNodeJointLoad[x][j][3][1] = i[1] + ujungPendek;
                        koorNodeJointLoad[x][j][3][2] = i[2];

                        koorNodeJointLoad[x][j][4][0] = i[0] - ujung;
                        koorNodeJointLoad[x][j][4][1] = i[1];
                        koorNodeJointLoad[x][j][4][2] = i[2] - ujungPendek;

                        koorNodeJointLoad[x][j][5][0] = i[0] - ujung;
                        koorNodeJointLoad[x][j][5][1] = i[1] - ujungPendek;
                        koorNodeJointLoad[x][j][5][2] = i[2];

                    } else if (j == 1) { //FY
                        koorNodeJointLoad[x][j][0][0] = i[0];
                        koorNodeJointLoad[x][j][0][1] = i[1];
                        koorNodeJointLoad[x][j][0][2] = i[2];

                        koorNodeJointLoad[x][j][1][0] = i[0];
                        koorNodeJointLoad[x][j][1][1] = i[1] - panjang;
                        koorNodeJointLoad[x][j][1][2] = i[2];

                        koorNodeJointLoad[x][j][2][0] = i[0];
                        koorNodeJointLoad[x][j][2][1] = i[1] - ujung;
                        koorNodeJointLoad[x][j][2][2] = i[2] + ujungPendek;

                        koorNodeJointLoad[x][j][3][0] = i[0] - ujungPendek;
                        koorNodeJointLoad[x][j][3][1] = i[1] - ujung;
                        koorNodeJointLoad[x][j][3][2] = i[2];

                        koorNodeJointLoad[x][j][4][0] = i[0];
                        koorNodeJointLoad[x][j][4][1] = i[1] - ujung;
                        koorNodeJointLoad[x][j][4][2] = i[2] - ujungPendek;

                        koorNodeJointLoad[x][j][5][0] = i[0] + ujungPendek;
                        koorNodeJointLoad[x][j][5][1] = i[1] - ujung;
                        koorNodeJointLoad[x][j][5][2] = i[2];

                    } else if (j == 2) { //FZ
                        koorNodeJointLoad[x][j][0][0] = i[0];
                        koorNodeJointLoad[x][j][0][1] = i[1];
                        koorNodeJointLoad[x][j][0][2] = i[2];

                        koorNodeJointLoad[x][j][1][0] = i[0];
                        koorNodeJointLoad[x][j][1][1] = i[1];
                        koorNodeJointLoad[x][j][1][2] = i[2] - panjang;

                        koorNodeJointLoad[x][j][2][0] = i[0] - ujungPendek;
                        koorNodeJointLoad[x][j][2][1] = i[1];
                        koorNodeJointLoad[x][j][2][2] = i[2] - ujung;

                        koorNodeJointLoad[x][j][3][0] = i[0];
                        koorNodeJointLoad[x][j][3][1] = i[1] + ujungPendek;
                        koorNodeJointLoad[x][j][3][2] = i[2] - ujung;

                        koorNodeJointLoad[x][j][4][0] = i[0] + ujungPendek;
                        koorNodeJointLoad[x][j][4][1] = i[1];
                        koorNodeJointLoad[x][j][4][2] = i[2] - ujung;

                        koorNodeJointLoad[x][j][5][0] = i[0];
                        koorNodeJointLoad[x][j][5][1] = i[1] - ujungPendek;
                        koorNodeJointLoad[x][j][5][2] = i[2] - ujung;
                    } else if (j == 3) { //MX
                        koorNodeJointLoad[x][j][0][0] = i[0] + panjang;
                        koorNodeJointLoad[x][j][0][1] = i[1];
                        koorNodeJointLoad[x][j][0][2] = i[2];

                        koorNodeJointLoad[x][j][1][0] = i[0];
                        koorNodeJointLoad[x][j][1][1] = i[1];
                        koorNodeJointLoad[x][j][1][2] = i[2];

                        koorNodeJointLoad[x][j][2][0] = i[0] + panjang - ujung;
                        koorNodeJointLoad[x][j][2][1] = i[1];
                        koorNodeJointLoad[x][j][2][2] = i[2] + ujungPendek;

                        koorNodeJointLoad[x][j][3][0] = i[0] + panjang - ujung;
                        koorNodeJointLoad[x][j][3][1] = i[1];
                        koorNodeJointLoad[x][j][3][2] = i[2] - ujungPendek;

                        koorNodeJointLoad[x][j][4][0] = i[0] + panjang - 2 * ujung;
                        koorNodeJointLoad[x][j][4][1] = i[1];
                        koorNodeJointLoad[x][j][4][2] = i[2] + ujungPendek;

                        koorNodeJointLoad[x][j][5][0] = i[0] + panjang - 2 * ujung;
                        koorNodeJointLoad[x][j][5][1] = i[1];
                        koorNodeJointLoad[x][j][5][2] = i[2] - ujungPendek;
                    } else if (j == 4) { //MY
                        koorNodeJointLoad[x][j][0][0] = i[0];
                        koorNodeJointLoad[x][j][0][1] = i[1] + panjang;
                        koorNodeJointLoad[x][j][0][2] = i[2];

                        koorNodeJointLoad[x][j][1][0] = i[0];
                        koorNodeJointLoad[x][j][1][1] = i[1];
                        koorNodeJointLoad[x][j][1][2] = i[2];

                        koorNodeJointLoad[x][j][2][0] = i[0];
                        koorNodeJointLoad[x][j][2][1] = i[1] + panjang - ujung;
                        koorNodeJointLoad[x][j][2][2] = i[2] + ujungPendek;

                        koorNodeJointLoad[x][j][3][0] = i[0];
                        koorNodeJointLoad[x][j][3][1] = i[1] + panjang - ujung;
                        koorNodeJointLoad[x][j][3][2] = i[2] - ujungPendek;

                        koorNodeJointLoad[x][j][4][0] = i[0];
                        koorNodeJointLoad[x][j][4][1] = i[1] + panjang - 2 * ujung;
                        koorNodeJointLoad[x][j][4][2] = i[2] + ujungPendek;

                        koorNodeJointLoad[x][j][5][0] = i[0];
                        koorNodeJointLoad[x][j][5][1] = i[1] + panjang - 2 * ujung;
                        koorNodeJointLoad[x][j][5][2] = i[2] - ujungPendek;
                    } else { //MZ
                        koorNodeJointLoad[x][j][0][0] = i[0];
                        koorNodeJointLoad[x][j][0][1] = i[1];
                        koorNodeJointLoad[x][j][0][2] = i[2] + panjang;

                        koorNodeJointLoad[x][j][1][0] = i[0];
                        koorNodeJointLoad[x][j][1][1] = i[1];
                        koorNodeJointLoad[x][j][1][2] = i[2];

                        koorNodeJointLoad[x][j][2][0] = i[0] - ujungPendek;
                        koorNodeJointLoad[x][j][2][1] = i[1];
                        koorNodeJointLoad[x][j][2][2] = i[2] + panjang - ujung;

                        koorNodeJointLoad[x][j][3][0] = i[0] + ujungPendek;
                        koorNodeJointLoad[x][j][3][1] = i[1];
                        koorNodeJointLoad[x][j][3][2] = i[2] + panjang - ujung;

                        koorNodeJointLoad[x][j][4][0] = i[0] - ujungPendek;
                        koorNodeJointLoad[x][j][4][1] = i[1];
                        koorNodeJointLoad[x][j][4][2] = i[2] + panjang - 2 * ujung;

                        koorNodeJointLoad[x][j][5][0] = i[0] + ujungPendek;
                        koorNodeJointLoad[x][j][5][1] = i[1];
                        koorNodeJointLoad[x][j][5][2] = i[2] + panjang - 2 * ujung;
                    }
                }
            }
            x++;
        }

        x = 0;
        for (float[][][] i : koorNodeJointLoadFix) {
            int y = 0;
            for (float[][] j : i) {
                int z = 0;
                for (float[] k : j) {
                    k[0] = koorNodeJointLoad[x][y][z][0];
                    k[1] = koorNodeJointLoad[x][y][z][1];
                    k[2] = koorNodeJointLoad[x][y][z][2];

                    z++;
                }
                y++;
            }
            x++;
        }

        //Frame Load
        float panjangX = 0;
        float panjangY = 0;
        float panjangZ = 0;
        float ujungX = 0;
        float ujungY = 0;
        float ujungZ = 0;


        //J = 0 : Arah X
        //J = 1 : Arah Y
        //J = 2 : Arah Z

        x = 0;
        for (int[] i : pointerElemenKolom) {
            for (int j = 0; j < 3; j++) {
                if (isFrameLoadExist[x][j]) {
                    if (j == 0) {
                        panjangX = -75f;
                        panjangY = 0f;
                        panjangZ = 0f;

                        ujungX = -10f;
                        ujungY = 0f;
                        ujungZ = 10f;
                        if (frameLoad[x][j] < 0) {
                            panjangX = 75f;
                            panjangY = 0f;
                            panjangZ = 0f;

                            ujungX = 10f;
                            ujungY = 0f;
                            ujungZ = -10f;
                        }

                    } else if (j == 1) {
                        panjangX = 0f;
                        panjangY = -75f;
                        panjangZ = 0f;

                        ujungX = 0f;
                        ujungY = -10f;
                        ujungZ = 10f;
                        if (frameLoad[x][j] < 0) {
                            panjangX = 0f;
                            panjangY = 75f;
                            panjangZ = 0f;

                            ujungX = 0f;
                            ujungY = 10f;
                            ujungZ = -10f;
                        }

                    } else if (j == 2) {
                        ujungX = 10f;
                        ujungY = 0f;
                        ujungZ = 10f;
                        if (frameLoad[x][j] < 0) {
                            ujungX = 10f;
                            ujungY = 0f;
                            ujungZ = -10f;
                        }
                    }

                    if (j != 2) {
                        for (int k = 0; k < 7; k++) {
                            for (int l = 0; l < 4; l++) {
                                if (l == 0) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0];
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1];
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] + k * lengthBetweenZ;
                                } else if (l == 1) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] + panjangX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1] + panjangY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] + panjangZ + k * lengthBetweenZ;
                                } else if (l == 2) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] + ujungX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1] + ujungY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] + ujungZ + k * lengthBetweenZ;
                                } else {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] + ujungX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1] + ujungY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] - ujungZ + k * lengthBetweenZ;
                                }
                            }
                        }
                    } else {
                        for (int k = 0; k < 7; k++) {
                            for (int l = 0; l < 4; l++) {
                                if (l == 0) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0];
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1];
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] + k * lengthBetweenZ;
                                } else if (l == 1) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0];
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1];
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] + k * lengthBetweenZ;
                                } else if (l == 2) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] - ujungX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1] + ujungY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] - ujungZ + k * lengthBetweenZ;
                                } else {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] + ujungX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1] + ujungY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] - ujungZ + k * lengthBetweenZ;
                                }
                            }
                        }
                    }

                }
            }
            x++;
        }

        //J = 0 : Arah X
        //J = 1 : Arah Y
        //J = 2 : Arah Z

        for (int[] i : pointerElemenBalokXZ) {
            for (int j = 0; j < 3; j++) {
                if (isFrameLoadExist[x][j]) {
                    if (j == 0) {
                        ujungX = 10f;
                        ujungY = 0f;
                        ujungZ = 10f;
                        if (frameLoad[x][j] < 0) {
                            ujungX = 10f;
                            ujungY = 0f;
                            ujungZ = -10f;
                        }

                    } else if (j == 1) {
                        panjangX = 0f;
                        panjangY = -75f;
                        panjangZ = 0f;

                        ujungX = 10f;
                        ujungY = -10f;
                        ujungZ = 0f;
                        if (frameLoad[x][j] < 0) {
                            panjangX = 0f;
                            panjangY = 75f;
                            panjangZ = 0f;

                            ujungX = -10f;
                            ujungY = 10f;
                            ujungZ = 0f;
                        }

                    } else if (j == 2) {
                        panjangX = 0f;
                        panjangY = 0f;
                        panjangZ = -75f;

                        ujungX = 10f;
                        ujungY = 0f;
                        ujungZ = -10f;
                        if (frameLoad[x][j] < 0) {
                            panjangX = 0f;
                            panjangY = 0f;
                            panjangZ = 75f;

                            ujungX = -10f;
                            ujungY = 0f;
                            ujungZ = 10f;
                        }
                    }

                    if (j != 0) {
                        for (int k = 0; k < 7; k++) {
                            for (int l = 0; l < 4; l++) {
                                if (l == 0) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] + k * lengthBetweenX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1];
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2];
                                } else if (l == 1) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] + panjangX + k * lengthBetweenX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1] + panjangY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] + panjangZ;
                                } else if (l == 2) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] + ujungX + k * lengthBetweenX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1] + ujungY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] + ujungZ;
                                } else {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] - ujungX + k * lengthBetweenX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1] + ujungY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] + ujungZ;
                                }
                            }
                        }

                    } else {
                        for (int k = 0; k < 7; k++) {
                            for (int l = 0; l < 4; l++) {
                                if (l == 0) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] + k * lengthBetweenX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1];
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2];
                                } else if (l == 1) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] + k * lengthBetweenX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1];
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2];
                                } else if (l == 2) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] - ujungX + k * lengthBetweenX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1] + ujungY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] + ujungZ;
                                } else {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[0]][0] - ujungX + k * lengthBetweenX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[0]][1] + ujungY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[0]][2] - ujungZ;
                                }
                            }
                        }
                    }

                }
            }
            x++;
        }

        //J = 0 : Arah X
        //J = 1 : Arah Y
        //J = 2 : Arah Z

        for (int[] i : pointerElemenBalokYZ) {
            for (int j = 0; j < 3; j++) {
                if (isFrameLoadExist[x][j]) {
                    if (j == 0) {
                        panjangX = -75f;
                        panjangY = 0f;
                        panjangZ = 0f;

                        ujungX = -10f;
                        ujungY = 10f;
                        ujungZ = 0f;
                        if (frameLoad[x][j] < 0) {
                            panjangX = 75f;
                            panjangY = 0f;
                            panjangZ = 0f;

                            ujungX = 10f;
                            ujungY = -10f;
                            ujungZ = 0f;
                        }

                    } else if (j == 1) {
                        ujungX = 0f;
                        ujungY = 10f;
                        ujungZ = 10f;
                        if (frameLoad[x][j] < 0) {
                            ujungX = 0f;
                            ujungY = 10f;
                            ujungZ = -10f;
                        }

                    } else if (j == 2) {
                        panjangX = 0f;
                        panjangY = 0f;
                        panjangZ = -75f;

                        ujungX = 0f;
                        ujungY = 10f;
                        ujungZ = -10f;
                        if (frameLoad[x][j] < 0) {
                            panjangX = 0f;
                            panjangY = 0f;
                            panjangZ = 75f;

                            ujungX = 0f;
                            ujungY = -10f;
                            ujungZ = 10f;
                        }
                    }

                    if (j != 1) {
                        for (int k = 0; k < 7; k++) {
                            for (int l = 0; l < 4; l++) {
                                if (l == 0) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[1]][0];
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[1]][1] - k * lengthBetweenY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[1]][2];
                                } else if (l == 1) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[1]][0] + panjangX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[1]][1] + panjangY - k * lengthBetweenY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[1]][2] + panjangZ;
                                } else if (l == 2) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[1]][0] + ujungX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[1]][1] + ujungY - k * lengthBetweenY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[1]][2] + ujungZ;
                                } else {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[1]][0] + ujungX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[1]][1] - ujungY - k * lengthBetweenY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[1]][2] + ujungZ;
                                }
                            }
                        }

                    } else {
                        for (int k = 0; k < 7; k++) {
                            for (int l = 0; l < 4; l++) {
                                if (l == 0) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[1]][0];
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[1]][1] - k * lengthBetweenY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[1]][2];
                                } else if (l == 1) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[1]][0];
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[1]][1] - k * lengthBetweenY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[1]][2];
                                } else if (l == 2) {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[1]][0] + ujungX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[1]][1] - ujungY - k * lengthBetweenY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[1]][2] + ujungZ;
                                } else {
                                    koorDistributedFrameLoad[x][j][l + k * 4][0] = koorNode[i[1]][0] + ujungX;
                                    koorDistributedFrameLoad[x][j][l + k * 4][1] = koorNode[i[1]][1] - ujungY - k * lengthBetweenY;
                                    koorDistributedFrameLoad[x][j][l + k * 4][2] = koorNode[i[1]][2] - ujungZ;
                                }
                            }
                        }
                    }

                }
            }
            x++;
        }

        x = 0;
        for (float[][][] i : koorDistributedFrameLoadFix) {
            int y = 0;
            for (float[][] j : i) {
                int z = 0;
                for (float[] k : j) {
                    k[0] = koorDistributedFrameLoad[x][y][z][0];
                    k[1] = koorDistributedFrameLoad[x][y][z][1];
                    k[2] = koorDistributedFrameLoad[x][y][z][2];

                    z++;
                }
                y++;
            }
            x++;
        }

        //Area Select
        x = (Nbx + 1) * (Nby + 1);
        int y = 1;
        int z = 1;
        float offset = 25f;
        for (int i = 0; i < Nst * Nbx * Nby; i++) {
            koorNodeAreaSelect[i][0][0] = koorNode[x][0] + offset;
            koorNodeAreaSelect[i][0][1] = koorNode[x][1] + offset;
            koorNodeAreaSelect[i][0][2] = koorNode[x][2];

            koorNodeAreaSelect[i][1][0] = koorNode[x + 1][0] - offset;
            koorNodeAreaSelect[i][1][1] = koorNode[x + 1][1] + offset;
            koorNodeAreaSelect[i][1][2] = koorNode[x + 1][2];

            koorNodeAreaSelect[i][2][0] = koorNode[x + 1 + Nbx + 1][0] - offset;
            koorNodeAreaSelect[i][2][1] = koorNode[x + 1 + Nbx + 1][1] - offset;
            koorNodeAreaSelect[i][2][2] = koorNode[x + 1 + Nbx + 1][2];

            koorNodeAreaSelect[i][3][0] = koorNode[x + 1 + Nbx][0] + offset;
            koorNodeAreaSelect[i][3][1] = koorNode[x + 1 + Nbx][1] - offset;
            koorNodeAreaSelect[i][3][2] = koorNode[x + 1 + Nbx][2];

            koorNodeAreaSelect[i][4][0] = koorNode[x][0] + offset;
            koorNodeAreaSelect[i][4][1] = koorNode[x][1] + offset;
            koorNodeAreaSelect[i][4][2] = koorNode[x][2];

            if (z == Nby && y == Nbx) {
                z = 1;
                y = 1;
                x += 2 + Nbx + 1;
            } else if (y == Nbx) {
                z++;
                y = 1;
                x += 2;
            } else {
                y++;
                x++;
            }
        }

        if (isRestraintPin) {
            x = 0;
            for (float[][] i : pinRestraint) {
                y = 0;
                for (float[] j : i) {
                    j[0] = pinRestraintFix[x][y][0];
                    j[1] = pinRestraintFix[x][y][1];
                    j[2] = pinRestraintFix[x][y][2];
                    y++;
                }
                x++;
            }

            x = 0;
            for (float[][] i : tumpuanSendi2) {
                y = 0;
                for (float[] j : i) {
                    j[0] = tumpuanSendi2Fix[x][y][0];
                    j[1] = tumpuanSendi2Fix[x][y][1];
                    j[2] = tumpuanSendi2Fix[x][y][2];
                    y++;
                }
                x++;
            }
        } else {
            x = 0;
            for (float[][] i : tumpuanJepit1) {
                y = 0;
                for (float[] j : i) {
                    j[0] = tumpuanJepit1Fix[x][y][0];
                    j[1] = tumpuanJepit1Fix[x][y][1];
                    j[2] = tumpuanJepit1Fix[x][y][2];
                    y++;
                }
                x++;
            }

            x = 0;
            for (float[][] i : tumpuanJepit2) {
                y = 0;
                for (float[] j : i) {
                    j[0] = tumpuanJepit2Fix[x][y][0];
                    j[1] = tumpuanJepit2Fix[x][y][1];
                    j[2] = tumpuanJepit2Fix[x][y][2];
                    y++;
                }
                x++;
            }
        }

        setKoor3D_Axis(0f * 315f * skalaCanvas,
                0f * 315f * skalaCanvas,
                0.5f * 315f * skalaCanvas,
                0.1f * 315f * skalaCanvas,
                0.03f * 315f * skalaCanvas);
    }

    private void setRotasiMatrik3D(double alfa, double beta, double gama, float[][] koordinat) {
        float sinX = Float.parseFloat(Double.toString(Math.sin(alfa)));
        float cosX = Float.parseFloat(Double.toString(Math.cos(alfa)));

        float sinY = Float.parseFloat(Double.toString(Math.sin(beta)));
        float cosY = Float.parseFloat(Double.toString(Math.cos(beta)));

        float sinZ = Float.parseFloat(Double.toString(Math.sin(gama)));
        float cosZ = Float.parseFloat(Double.toString(Math.cos(gama)));

        for (float[] titikLokal : koordinat) {
            float x = titikLokal[0];
            float y = titikLokal[1];
            float z = titikLokal[2];

            titikLokal[0] = x * cosX - z * sinX;
            titikLokal[2] = z * cosX + x * sinX;

            z = titikLokal[2];

            titikLokal[1] = y * cosY - z * sinY;
            titikLokal[2] = z * cosY + y * sinY;

            x = titikLokal[0];
            y = titikLokal[1];

            titikLokal[0] = x * cosZ - y * sinZ;
            titikLokal[1] = y * cosZ + x * sinZ;
        }
    }

    private void setRotasiMatrik3D(double alfa, double beta, double gama, float[][][] koor) {
        float sinX = Float.parseFloat(Double.toString(Math.sin(alfa)));
        float cosX = Float.parseFloat(Double.toString(Math.cos(alfa)));

        float sinY = Float.parseFloat(Double.toString(Math.sin(beta)));
        float cosY = Float.parseFloat(Double.toString(Math.cos(beta)));

        float sinZ = Float.parseFloat(Double.toString(Math.sin(gama)));
        float cosZ = Float.parseFloat(Double.toString(Math.cos(gama)));

        for (float[][] titikLokal2 : koor) {
            for (float[] titikLokal : titikLokal2) {
                float x = titikLokal[0];
                float y = titikLokal[1];
                float z = titikLokal[2];

                titikLokal[0] = x * cosX - z * sinX;
                titikLokal[2] = z * cosX + x * sinX;

                z = titikLokal[2];

                titikLokal[1] = y * cosY - z * sinY;
                titikLokal[2] = z * cosY + y * sinY;

                x = titikLokal[0];
                y = titikLokal[1];

                titikLokal[0] = x * cosZ - y * sinZ;
                titikLokal[1] = y * cosZ + x * sinZ;
            }
        }
    }

    private void setRotasiMatrik3D(double alfa, double beta, double gama, double[][][] koor) {
        float sinX = Float.parseFloat(Double.toString(Math.sin(alfa)));
        float cosX = Float.parseFloat(Double.toString(Math.cos(alfa)));

        float sinY = Float.parseFloat(Double.toString(Math.sin(beta)));
        float cosY = Float.parseFloat(Double.toString(Math.cos(beta)));

        float sinZ = Float.parseFloat(Double.toString(Math.sin(gama)));
        float cosZ = Float.parseFloat(Double.toString(Math.cos(gama)));

        for (double[][] titikLokal2 : koor) {
            for (double[] titikLokal : titikLokal2) {
                double x = titikLokal[0];
                double y = titikLokal[1];
                double z = titikLokal[2];

                titikLokal[0] = x * cosX - z * sinX;
                titikLokal[2] = z * cosX + x * sinX;

                z = titikLokal[2];

                titikLokal[1] = y * cosY - z * sinY;
                titikLokal[2] = z * cosY + y * sinY;

                x = titikLokal[0];
                y = titikLokal[1];

                titikLokal[0] = x * cosZ - y * sinZ;
                titikLokal[1] = y * cosZ + x * sinZ;
            }
        }
    }

    private void setRotasiMatrik3D_Joint(double alfa, double beta, double gama, float[][][][] koor) {
        float sinX = Float.parseFloat(Double.toString(Math.sin(alfa)));
        float cosX = Float.parseFloat(Double.toString(Math.cos(alfa)));

        float sinY = Float.parseFloat(Double.toString(Math.sin(beta)));
        float cosY = Float.parseFloat(Double.toString(Math.cos(beta)));

        float sinZ = Float.parseFloat(Double.toString(Math.sin(gama)));
        float cosZ = Float.parseFloat(Double.toString(Math.cos(gama)));

        int i = 0;
        int j = 0;
        for (float[][][] titikLokal3 : koor) {
            for (float[][] titikLokal2 : titikLokal3) {
                if (isJointLoadExist[i][j]) {
                    for (float[] titikLokal : titikLokal2) {
                        float x = titikLokal[0];
                        float y = titikLokal[1];
                        float z = titikLokal[2];

                        titikLokal[0] = x * cosX - z * sinX;
                        titikLokal[2] = z * cosX + x * sinX;

                        z = titikLokal[2];

                        titikLokal[1] = y * cosY - z * sinY;
                        titikLokal[2] = z * cosY + y * sinY;

                        x = titikLokal[0];
                        y = titikLokal[1];

                        titikLokal[0] = x * cosZ - y * sinZ;
                        titikLokal[1] = y * cosZ + x * sinZ;
                    }
                }

                if (j < 5) {
                    j++;
                } else {
                    j = 0;
                }
            }
            i++;
        }
    }

    private void setRotasiMatrik3D_Frame(double alfa, double beta, double gama, float[][][][] koor) {
        float sinX = Float.parseFloat(Double.toString(Math.sin(alfa)));
        float cosX = Float.parseFloat(Double.toString(Math.cos(alfa)));

        float sinY = Float.parseFloat(Double.toString(Math.sin(beta)));
        float cosY = Float.parseFloat(Double.toString(Math.cos(beta)));

        float sinZ = Float.parseFloat(Double.toString(Math.sin(gama)));
        float cosZ = Float.parseFloat(Double.toString(Math.cos(gama)));

        int i = 0;
        int j = 0;
        for (float[][][] titikLokal3 : koor) {
            for (float[][] titikLokal2 : titikLokal3) {
                if (isFrameLoadExist[i][j]) {
                    for (float[] titikLokal : titikLokal2) {
                        float x = titikLokal[0];
                        float y = titikLokal[1];
                        float z = titikLokal[2];

                        titikLokal[0] = x * cosX - z * sinX;
                        titikLokal[2] = z * cosX + x * sinX;

                        z = titikLokal[2];

                        titikLokal[1] = y * cosY - z * sinY;
                        titikLokal[2] = z * cosY + y * sinY;

                        x = titikLokal[0];
                        y = titikLokal[1];

                        titikLokal[0] = x * cosZ - y * sinZ;
                        titikLokal[1] = y * cosZ + x * sinZ;
                    }
                }

                if (j < 2) {
                    j++;
                } else {
                    j = 0;
                }
            }
            i++;
        }
    }

    //----------SET HAL-HAL TERPILIH----------//
    private boolean setElementSelected() {
        float x1 = 0;
        float z1 = 0;
        float x2 = 0;
        float z2 = 0;
        float y_acuan = 0;

        float[] koor1;
        float[] koor2;

        float gradienY;
        float ordinatY;

        int x = 0;
        for (int[] pointer : pointerElemenStruktur) {
            if (tandaViewBottomNav == 1) {
                koor1 = koorNode[pointer[0]];
                koor2 = koorNode[pointer[1]];
            } else {
                koor1 = koorNodeFix[pointer[0]];
                koor2 = koorNodeFix[pointer[1]];
            }

            switch (tandaViewBottomNav) {
                case 1:
                    if (isPerspektif) {
                        x1 = koor1[0] / (focalLength + koor1[1]) * focalLength;
                        z1 = koor1[2] / (focalLength + koor1[1]) * focalLength;

                        x2 = koor2[0] / (focalLength + koor2[1]) * focalLength;
                        z2 = koor2[2] / (focalLength + koor2[1]) * focalLength;
                    } else {
                        x1 = koor1[0];
                        z1 = koor1[2];

                        x2 = koor2[0];
                        z2 = koor2[2];
                    }
                    break;

                case 2:
                    x1 = koor1[0];
                    z1 = koor1[1];
                    y_acuan = koor1[2];

                    x2 = koor2[0];
                    z2 = koor2[1];
                    break;

                case 3:
                    x1 = koor1[0];
                    z1 = koor1[2];
                    y_acuan = koor1[1];

                    x2 = koor2[0];
                    z2 = koor2[2];
                    break;

                case 4:
                    x1 = koor1[1];
                    z1 = koor1[2];
                    y_acuan = koor1[0];

                    x2 = koor2[1];
                    z2 = koor2[2];
                    break;
            }

            if (tandaViewBottomNav == 1) {
                if (x1 > x2) {
                    float dummy = x1;
                    x1 = x2;
                    x2 = dummy;

                    dummy = z1;
                    z1 = z2;
                    z2 = dummy;
                }

                if (x1 == x2) {
                    if (singleTapX > (x1 - 25f) && singleTapX < (x2 + 25f)) {
                        if (singleTapY > (z1 - 10f) && singleTapY < (z2 + 10f)) {
                            return setElementSelectedPointer(x);
                        }
                    }
                } else {
                    float ruangX = x2 - x1;
                    gradienY = (z2 - z1) / (ruangX);
                    float deltaX = Math.abs(singleTapX - x1);
                    ordinatY = z1 + gradienY * deltaX;
                    float offset = 10f;
                    float offsetZ;

                    if (z2 >= z1) {
                        offsetZ = 10f;
                    } else {
                        offsetZ = -10f;
                    }

                    if (singleTapX > (x1 - offset) && singleTapX < (x2 + offset)) {
                        if (z2 >= z1) {
                            if (singleTapY > (z1 - offsetZ) && singleTapY < (z2 + offsetZ)) {
                                if (ruangX > 75) {
                                    if (Math.abs(singleTapY - ordinatY) <= 30f) {
                                        return setElementSelectedPointer(x);
                                    }
                                } else {
                                    return setElementSelectedPointer(x);
                                }
                            }
                        } else {
                            if (singleTapY < (z1 - offsetZ) && singleTapY > (z2 + offsetZ)) {
                                if (ruangX > 75) {
                                    if (Math.abs(singleTapY - ordinatY) <= 30) {
                                        return setElementSelectedPointer(x);
                                    }
                                } else {
                                    return setElementSelectedPointer(x);
                                }

                            }
                        }
                    }
                }
            } else {
                if (x1 > x2) {
                    float dummy = x1;
                    x1 = x2;
                    x2 = dummy;
                }

                if (z1 > z2) {
                    float dummy = z1;
                    z1 = z2;
                    z2 = dummy;
                }

                if (!(x1 == x2 && z1 == z2)) {
                    if (z1 == z2) {
                        if (singleTapX > (x1 + 25f) && singleTapX < (x2 - 25f)) {
                            if (singleTapY > (z1 - 25f) && singleTapY < (z2 + 25f)) {
                                if (singleTapAcuan == y_acuan) {
                                    return setElementSelectedPointer(x);
                                }
                            }
                        }
                    } else {
                        if (singleTapX > (x1 - 25f) && singleTapX < (x2 + 25f)) {
                            if (singleTapY > (z1 + 25f) && singleTapY < (z2 - 25f)) {
                                if (singleTapAcuan == y_acuan) {
                                    return setElementSelectedPointer(x);
                                }
                            }
                        }
                    }

                }
            }
            x++;
        }
        return false;
    }

    private boolean setElementSelectedPointer(int x) {
        if (!isPointerElementSelected[x]) {
            isPointerElementSelected[x] = true;

            fabFrameLoad.setEnabled(true);

            if (isLockOpen) {
                fabMenu.showMenu(true);
            }

            isElementSelectedFAB = true;

            isTimerSleep = false;
            return true;

        } else {
            isPointerElementSelected[x] = false;

            boolean dummy = false;
            for (boolean i : isPointerElementSelected) {
                if (i) {
                    dummy = true;
                }
            }

            if (!dummy) {
                fabFrameLoad.setEnabled(false);
                isElementSelectedFAB = false;
            }

            if (!isElementSelectedFAB && !isNodeSelectedFAB && !isAreaSelectedFAB) {
                isTimerSleep = true;
                drawGeneral();
            }

            return false;
        }
    }

    public void setNodeSelected() {
        float x1;
        float y1;
        float toleransi = 25f;

        if (tandaViewBottomNav == 1) {
            int x = 0;
            for (float[] koor1 : koorNode) {
                if (isPerspektif) {
                    x1 = koor1[0] / (focalLength + koor1[1]) * focalLength;
                    y1 = koor1[2] / (focalLength + koor1[1]) * focalLength;
                } else {
                    x1 = koor1[0];
                    y1 = koor1[2];
                }

                if (singleTapX < x1 + toleransi && singleTapX > x1 - toleransi) {
                    if (singleTapY < y1 + toleransi && singleTapY > y1 - toleransi) {
                        setNodeSelectedPointer(x, 0);
                    }
                }
                x++;
            }

        } else if (tandaViewBottomNav == 2) {
            int x = (viewXY_Current - 1) * (Nbx + 1) * (Nby + 1);
            for (int i = 0; i < (Nbx + 1) * (Nby + 1); i++) {
                x1 = koorNodeFix[i + x][0];
                y1 = koorNodeFix[i + x][1];

                if (singleTapX < x1 + toleransi && singleTapX > x1 - toleransi) {
                    if (singleTapY < y1 + toleransi && singleTapY > y1 - toleransi) {
                        setNodeSelectedPointer(x, i);
                    }
                }
            }
        } else if (tandaViewBottomNav == 3) {
            int x = (viewXZ_Current - 1) * (Nbx + 1);
            int y = 0;
            for (int i = 1; i <= (Nbx + 1) * (Nst + 1); i++) {

                x1 = koorNodeFix[x][0];
                y1 = koorNodeFix[x][2];

                if (singleTapX < x1 + toleransi && singleTapX > x1 - toleransi) {
                    if (singleTapY < y1 + toleransi && singleTapY > y1 - toleransi) {
                        setNodeSelectedPointer(x, 0);
                    }
                }

                if (y == Nbx) {
                    y = 0;
                    x += (Nbx + 1) * (Nby + 1) - Nbx;
                } else {
                    y++;
                    x++;
                }
            }

        } else if (tandaViewBottomNav == 4) {
            int x = (viewYZ_Current - 1);
            for (int i = 1; i <= (Nby + 1) * (Nst + 1); i++) {

                x1 = koorNodeFix[x][1];
                y1 = koorNodeFix[x][2];

                if (singleTapX < x1 + toleransi && singleTapX > x1 - toleransi) {
                    if (singleTapY < y1 + toleransi && singleTapY > y1 - toleransi) {
                        setNodeSelectedPointer(x, 0);
                    }
                }

                x += Nbx + 1;
            }
        }
    }

    public void setNodeSelectedPointer(int x, int i) {
        if (!isPointerNodeSelected[x + i]) {
            isPointerNodeSelected[x + i] = true;

            fabJointLoad.setEnabled(true);

            if (isLockOpen) {
                fabMenu.showMenu(true);
            }

            isNodeSelectedFAB = true;
            isTimerSleep = false;
        } else {
            isPointerNodeSelected[x + i] = false;

            boolean dummy = false;
            for (boolean j : isPointerNodeSelected) {
                if (j) {
                    dummy = true;
                }
            }

            if (!dummy) {
                fabJointLoad.setEnabled(false);
                isNodeSelectedFAB = false;
            }

            if (!isElementSelectedFAB && !isNodeSelectedFAB && !isAreaSelectedFAB) {
                fabMenu.hideMenu(true);
                isTimerSleep = true;
                drawGeneral();
            }
        }
    }

    private void setAreaSelected() {
        float x1;
        float y1;

        float x2;
        float y2;

        float x3;
        float y3;

        float x4;
        float y4;

        float toleransi = 25f;

        int x = (viewXY_Current - 1) * (Nbx + 1) * (Nby + 1);
        int y = 1;
        for (int i = 1; i <= (Nbx) * (Nby); i++) {
            x1 = koorNodeFix[x][0];
            y1 = koorNodeFix[x][1];

            x2 = koorNodeFix[x + 1][0];
            y2 = koorNodeFix[x + 1][1];

            x3 = koorNodeFix[x + Nbx + 1][0];
            y3 = koorNodeFix[x + Nbx + 1][1];

            x4 = koorNodeFix[x + Nbx + 2][0];
            y4 = koorNodeFix[x + Nbx + 2][1];

            if (singleTapX > x1 + toleransi && singleTapY > y1 + toleransi) {
                if (singleTapX < x2 - toleransi && singleTapY > y2 + toleransi) {
                    if (singleTapX > x3 + toleransi && singleTapY < y3 - toleransi) {
                        if (singleTapX < x4 - toleransi && singleTapY < y4 - toleransi) {
                            if (!isPointerAreaSelected[i - 1 + (viewXY_Current - 2) * Nbx * Nby]) {
                                isPointerAreaSelected[i - 1 + (viewXY_Current - 2) * Nbx * Nby] = true;

                                fabAreaLoad.setEnabled(true);

                                if (isLockOpen) {
                                    fabMenu.showMenu(true);
                                }

                                isAreaSelectedFAB = true;
                                isTimerSleep = false;

                            } else {
                                isPointerAreaSelected[i - 1 + (viewXY_Current - 2) * Nbx * Nby] = false;

                                boolean dummy = false;
                                for (boolean j : isPointerAreaSelected) {
                                    if (j) {
                                        dummy = true;
                                    }
                                }

                                if (!dummy) {
                                    fabAreaLoad.setEnabled(false);
                                    isAreaSelectedFAB = false;
                                }

                                if (!isElementSelectedFAB && !isNodeSelectedFAB && !isAreaSelectedFAB) {
                                    isTimerSleep = true;
                                    drawGeneral();
                                }
                            }
                        }
                    }
                }
            }

            if (y == Nbx) {
                y = 1;
                x += 2;
            } else {
                y++;
                x++;
            }
        }
    }

    //----------SET KEADAAN AWAL----------//
    private void setFloatingActionInitial() {
        fabMenu.setVisibility(View.VISIBLE);
        fabJointLoad.setEnabled(false);
        fabFrameLoad.setEnabled(false);
        fabAreaLoad.setEnabled(false);
        fabFrameSection.setEnabled(true);
        fabEarthQuake.setEnabled(true);
    }

    private void showLockDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure want to open ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setLockOpen();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void setLockOpen() {
        isLockOpen = true;
        isAlreadyRunning = false;

        setMenuItemEnable(menuDrawer, false,
                R.id.nav_export,
                R.id.nav_exportDesign);
        setMenuItemEnable(menuOptionMenu, true, R.id.toolRun);

        //setting keadaan tool
        toolRun.setIcon(R.drawable.ic_toolbar_run);
        toolLock.setIcon(R.drawable.ic_toolbar_unlock);
        if(isAnalysis) {
            toolUndeformed.setIcon(R.drawable.ic_toolbar_undeformed_false);
            toolUndeformed.setEnabled(false);
            toolShowGraph.setEnabled(false);
            toolShowDisplacementValue.setEnabled(false);
        }

        isDrawDisplacement = false;
        isDrawLoadCurrent = false;
        drawLoadCurrent = 0;
        drawInternalForceCurrent = 0;
        isShowNodal = false;
        isShowElement = false;
        setKoorToDrawLoad();

        IF_AxialKolom = null;
        IF_Shear22Kolom = null;
        IF_Shear33Kolom = null;
        IF_TorsiKolom = null;
        IF_Momen22Kolom = null;
        IF_Momen33Kolom = null;

        IF_AxialBalokX = null;
        IF_Shear22BalokX = null;
        IF_Shear33BalokX = null;
        IF_TorsiBalokX = null;
        IF_Momen22BalokX = null;
        IF_Momen33BalokX = null;

        IF_AxialBalokY = null;
        IF_Shear22BalokY = null;
        IF_Shear33BalokY = null;
        IF_TorsiBalokY = null;
        IF_Momen22BalokY = null;
        IF_Momen33BalokY = null;

        koorNodeDeformed = null;

        drawGeneral();
        setFloatingActionInitial();
        navigasiToolbarView();
    }

    private void setLocked() {
        imgViewSelectAll = findViewById(R.id.imgSelectAll);
        imgViewDeselectAll = findViewById(R.id.imgDeselectAll);

        fabMenu.setVisibility(View.GONE);
        imgViewSelectAll.setEnabled(false);
        imgViewDeselectAll.setEnabled(false);

        setMenuItemEnable(menuOptionMenu, false, R.id.toolRun);
        toolRun.setIcon(R.drawable.ic_toolbar_run_false);
    }

    private void setFABAnalysis() {
        fabMenu.setVisibility(View.VISIBLE);
        fabJointLoad.setVisibility(View.VISIBLE);
        fabFrameLoad.setVisibility(View.VISIBLE);
        fabAreaLoad.setVisibility(View.VISIBLE);
        fabFrameSection.setVisibility(View.VISIBLE);
        fabEarthQuake.setVisibility(View.VISIBLE);
        fabColumnSection.setVisibility(View.GONE);
        fabXBeamSection.setVisibility(View.GONE);
        fabYBeamSection.setVisibility(View.GONE);
    }

    private void setFABDesign() {
        fabMenu.setVisibility(View.VISIBLE);
        fabJointLoad.setVisibility(View.GONE);
        fabFrameLoad.setVisibility(View.GONE);
        fabAreaLoad.setVisibility(View.GONE);
        fabFrameSection.setVisibility(View.GONE);
        fabEarthQuake.setVisibility(View.GONE);
        fabColumnSection.setVisibility(View.VISIBLE);
        fabXBeamSection.setVisibility(View.VISIBLE);
        fabYBeamSection.setVisibility(View.VISIBLE);
    }

    //----------SET SKALA DAN ZOOM----------//
    private void setSkala(float skala, float[][] matrik){
        for(float[] matrikLokal : matrik){
            matrikLokal[0] *= skala;
            matrikLokal[1] *= skala;
            matrikLokal[2] *= skala;
        }
    }

    private void setSkala(float skala, float[][][] matrik){
        for(float[][] matrikLokal : matrik){
            for(float[] matrikLokal2 : matrikLokal){
                matrikLokal2[0] *= skala;
                matrikLokal2[1] *= skala;
                matrikLokal2[2] *= skala;
            }
        }
    }

    private void setSkala(float skala, float[][][][] matrik){
        for(float[][][] matrikLokal3 : matrik){
            for(float[][] matrikLokal2 : matrikLokal3){
                for(float[] matrikLokal : matrikLokal2){
                    matrikLokal[0] *= skala;
                    matrikLokal[1] *= skala;
                    matrikLokal[2] *= skala;
                }
            }
        }
    }

    private void setSkala(float skala, double[][][] matrik){
        for(double[][] matrikLokal2 : matrik){
            for(double[] matrikLokal : matrikLokal2){
                matrikLokal[0] *= skala;
                matrikLokal[1] *= skala;
                matrikLokal[2] *= skala;
            }
        }
    }

    private void setZoomInOut(float skala){
        setSkala(skala, koorNode);
        setSkala(skala, koorNodeFix);
        setSkala(skala, koorNodeAreaSelect);
        setSkala(skala, koor3D_Axis);
        setSkala(skala, koor3D_AxisFix);

        if(isRestraintPin){
            setSkala(skala, pinRestraint);
            setSkala(skala, tumpuanSendi2);

            setSkala(skala, pinRestraintFix);
            setSkala(skala, tumpuanSendi2Fix);
        }else{
            setSkala(skala, tumpuanJepit1);
            setSkala(skala, tumpuanJepit2);

            setSkala(skala, tumpuanJepit1Fix);
            setSkala(skala, tumpuanJepit2Fix);
        }

        setSkala(skala, koorNodeJointLoad);
        setSkala(skala, koorNodeJointLoadFix);
        setSkala(skala, koorDistributedFrameLoad);
        setSkala(skala, koorDistributedFrameLoadFix);

        if(isAlreadyRunning){
            setSkala(skala, IF_AxialKolom);
            setSkala(skala, IF_Shear22Kolom);
            setSkala(skala, IF_Shear33Kolom);
            setSkala(skala, IF_TorsiKolom);
            setSkala(skala, IF_Momen22Kolom);
            setSkala(skala, IF_Momen33Kolom);

            setSkala(skala, IF_AxialBalokX);
            setSkala(skala, IF_Shear22BalokX);
            setSkala(skala, IF_Shear33BalokX);
            setSkala(skala, IF_TorsiBalokX);
            setSkala(skala, IF_Momen22BalokX);
            setSkala(skala, IF_Momen33BalokX);

            setSkala(skala, IF_AxialBalokY);
            setSkala(skala, IF_Shear22BalokY);
            setSkala(skala, IF_Shear33BalokY);
            setSkala(skala, IF_TorsiBalokY);
            setSkala(skala, IF_Momen22BalokY);
            setSkala(skala, IF_Momen33BalokY);

            //.........

            setSkala(skala, IF_AxialKolomFix);
            setSkala(skala, IF_Shear22KolomFix);
            setSkala(skala, IF_Shear33KolomFix);
            setSkala(skala, IF_TorsiKolomFix);
            setSkala(skala, IF_Momen22KolomFix);
            setSkala(skala, IF_Momen33KolomFix);

            setSkala(skala, IF_AxialBalokXFix);
            setSkala(skala, IF_Shear22BalokXFix);
            setSkala(skala, IF_Shear33BalokXFix);
            setSkala(skala, IF_TorsiBalokXFix);
            setSkala(skala, IF_Momen22BalokXFix);
            setSkala(skala, IF_Momen33BalokXFix);

            setSkala(skala, IF_AxialBalokYFix);
            setSkala(skala, IF_Shear22BalokYFix);
            setSkala(skala, IF_Shear33BalokYFix);
            setSkala(skala, IF_TorsiBalokYFix);
            setSkala(skala, IF_Momen22BalokYFix);
            setSkala(skala, IF_Momen33BalokYFix);

            setSkala(skala, koorNodeDeformed);
            setSkala(skala, koorNodeDeformedFix);
        }

        distanceTextReaction *= skala;
        sizeTextReaction *= skala;
        pTextReaction.setTextSize(sizeTextReaction);

        focalLength *= skala;
        drawGeneral();
    }

    //-----------SET AWALAN MENGGAMBAR----------//
    private void setPaint(){
        pGaris.setColor(Color.BLACK);
        pGaris.setStrokeWidth(3);
        pGaris.setStyle(Paint.Style.STROKE);
        pGaris.setAntiAlias(true);

        pAxis.setColor(Color.RED);
        pAxis.setStrokeWidth(4);
        pAxis.setStyle(Paint.Style.STROKE);
        pAxis.setAntiAlias(true);
        pAxis.setStrokeCap(Paint.Cap.ROUND);

        pJointLoad.setColor(Color.parseColor("#AD05FF00"));
        pJointLoad.setStrokeWidth(8f);
        pJointLoad.setStyle(Paint.Style.STROKE);
        pJointLoad.setAntiAlias(true);
        pJointLoad.setStrokeCap(Paint.Cap.ROUND);

        pJointLoadUjung.setColor(Color.parseColor("#AD05FF00"));
        pJointLoadUjung.setStrokeWidth(6f);
        pJointLoadUjung.setStyle(Paint.Style.STROKE);
        pJointLoadUjung.setAntiAlias(true);
        pJointLoadUjung.setStrokeCap(Paint.Cap.ROUND);

        pFrameLoad.setColor(Color.parseColor("#FF0066FF"));
        pFrameLoad.setStrokeWidth(4f);
        pFrameLoad.setStyle(Paint.Style.STROKE);
        pFrameLoad.setAntiAlias(true);
        pFrameLoad.setStrokeCap(Paint.Cap.ROUND);

        pGarisGray.setColor(Color.GRAY);
        pGarisGray.setStrokeWidth(2);
        pGarisGray.setStyle(Paint.Style.STROKE);
        pGarisGray.setAntiAlias(true);
        pGarisGray.setPathEffect(new DashPathEffect(new float[] {20, 10}, 0));

        pGarisSelected.setColor(Color.parseColor("#FF0066FF"));
        pGarisSelected.setStrokeWidth(6f);
        pGarisSelected.setStyle(Paint.Style.STROKE);
        pGarisSelected.setAntiAlias(true);
        pGarisSelected.setPathEffect(new DashPathEffect(new float[] {30, 15}, 0));

        pNodeSelected.setColor(Color.parseColor("#FF0066FF"));
        pNodeSelected.setStrokeWidth(4f);
        pNodeSelected.setStyle(Paint.Style.STROKE);
        pNodeSelected.setAntiAlias(true);
        pNodeSelected.setPathEffect(new DashPathEffect(new float[] {15, 7.5f}, 0));

        pAreaSelected.setColor(Color.BLACK);
        pAreaSelected.setStrokeWidth(6f);
        pAreaSelected.setStyle(Paint.Style.STROKE);
        pAreaSelected.setAntiAlias(true);
        pAreaSelected.setPathEffect(new DashPathEffect(new float[] {30, 15}, 0));
        pAreaSelected.setPathEffect(new CornerPathEffect(10));
        pAreaSelected.setStrokeCap(Paint.Cap.ROUND);

        pPathEffect.setColor(Color.WHITE);
        pPathEffect.setStrokeWidth(10f);
        pPathEffect.setStyle(Paint.Style.STROKE);
        pPathEffect.setAntiAlias(true);
        pPathEffect.setPathEffect(new DashPathEffect(new float[] {60, 60}, 0));

        pPathEffect2.setColor(Color.WHITE);
        pPathEffect2.setStrokeWidth(10f);
        pPathEffect2.setStyle(Paint.Style.STROKE);
        pPathEffect2.setAntiAlias(true);
        pPathEffect2.setPathEffect(new DashPathEffect(new float[] {60, 60}, 60));

        pText.setColor(Color.BLACK);
        pText.setStyle(Paint.Style.FILL);
        pText.setAntiAlias(true);
        pText.setTextSize(30);

        pTextNodal.setColor(Color.BLACK);
        pTextNodal.setStyle(Paint.Style.FILL);
        pTextNodal.setAntiAlias(true);
        pTextNodal.setTextSize(20);

        pTextReaction.setColor(Color.BLACK);
        pTextReaction.setStyle(Paint.Style.FILL);
        pTextReaction.setAntiAlias(true);
        pTextReaction.setTextSize(sizeTextReaction);

        pTextAxis.setColor(Color.BLACK);
        pTextAxis.setStrokeWidth(3);
        pTextAxis.setStyle(Paint.Style.FILL_AND_STROKE);
        pTextAxis.setAntiAlias(true);
        pTextAxis.setTextSize(50);

        pTumpuan.setColor(Color.GREEN);
        pTumpuan.setStrokeWidth(2);
        pTumpuan.setStyle(Paint.Style.STROKE);
        pTumpuan.setAntiAlias(true);

        pPath.setColor(getResources().getColor(R.color.colorAccent));
        pPath.setStyle(Paint.Style.FILL);
        pPath.setAntiAlias(true);

        pPathPositif.setColor(Color.parseColor("#7D59CDFF"));
        pPathPositif.setStyle(Paint.Style.FILL);
        pPathPositif.setAntiAlias(true);

        pPathNegatif.setColor(Color.parseColor("#7DFF5D5D"));
        pPathNegatif.setStyle(Paint.Style.FILL);
        pPathNegatif.setAntiAlias(true);

        pPathAxis.setColor(Color.RED);
        pPathAxis.setStyle(Paint.Style.FILL);
        pPathAxis.setAntiAlias(true);

        pPathKolom.setColor(Color.parseColor("#0091EA"));
        pPathKolom.setStrokeWidth(5f);
        pPathKolom.setStyle(Paint.Style.STROKE);
        pPathKolom.setAntiAlias(true);
        pPathKolom.setStrokeCap(Paint.Cap.ROUND);

        pPathBalokX.setColor(Color.parseColor("#AA00FF"));
        pPathBalokX.setStrokeWidth(5f);
        pPathBalokX.setStyle(Paint.Style.STROKE);
        pPathBalokX.setAntiAlias(true);
        pPathBalokX.setStrokeCap(Paint.Cap.ROUND);

        pPathBalokY.setColor(Color.parseColor("#76FF03"));
        pPathBalokY.setStrokeWidth(5f);
        pPathBalokY.setStyle(Paint.Style.STROKE);
        pPathBalokY.setAntiAlias(true);
        pPathBalokY.setStrokeCap(Paint.Cap.ROUND);
    }

    private void setWarnaKeamanan(double angkaAman){
        if(angkaAman>1.0){pGaris.setColor(Color.argb(255,255,051,051));}
        if(angkaAman<=1.0&&angkaAman>0.8){pGaris.setColor(Color.argb(255,255,153,051));}
        if(angkaAman<=0.8&&angkaAman>0.6){pGaris.setColor(Color.argb(255,255,255,051));}
        if(angkaAman<=0.6&&angkaAman>0.4){pGaris.setColor(Color.argb(255,153,255,051));}
        if(angkaAman<=0.4&&angkaAman>0.2){pGaris.setColor(Color.argb(255,051,255,051));}
        if(angkaAman<=0.2&&angkaAman>0.0){pGaris.setColor(Color.argb(255,051,255,153));}
        if(angkaAman==0.0){pGaris.setColor(Color.argb(255,051,255,255));}
    }

    private void setCanvasBitmap(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x;
        int h = size.y-470;

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.translate(w/2f, h/2f);
        canvas.scale(1,-1);
    }

    private void setTimer(){ //untuk dash effect elemen terpilih
        numberPhase = 30;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                pGarisSelected.setPathEffect(new DashPathEffect(new float[] {30, 15}, numberPhase));
                pAreaSelected.setPathEffect(new DashPathEffect(new float[] {30, 15}, numberPhase));
                pNodeSelected.setPathEffect(new DashPathEffect(new float[] {15, 7.5f}, numberPhase / 2f));
                numberPhase = numberPhase - 3;
                if(numberPhase < -10000){
                    numberPhase = 30;
                }
                drawGeneral();
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isTimerSleep){
                    handler.post(runnable);
                }
            }
        }, 1000, 100);
    }

    //----------SET PANAH UNTUK TAMPILAN XY, XZ, YZ----------//
    private void setArrowBackAndForward(boolean isActive){
        if(!isActive) {
            imgArrowBack.setVisibility(View.GONE);
            imgArrowForward.setVisibility(View.GONE);
            txtTandaAxis.setVisibility(View.GONE);
        }else {
            imgArrowBack.setVisibility(View.VISIBLE);
            imgArrowForward.setVisibility(View.VISIBLE);
            txtTandaAxis.setVisibility(View.VISIBLE);
        }
    }

    private void setArrowOnClickListener(){
        imgArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDeselectAll();
                switch (tandaViewBottomNav){
                    case 2:
                        viewXY_Current--;
                        if(viewXY_Current <= 0){
                            viewXY_Current = viewXY_Max;
                        }
                        drawFrameXY();
                        break;

                    case 3:
                        viewXZ_Current--;
                        if(viewXZ_Current <= 0){
                            viewXZ_Current = viewXZ_Max;
                        }
                        drawFrameXZ();
                        break;

                    case 4:
                        viewYZ_Current--;
                        if(viewYZ_Current <= 0){
                            viewYZ_Current = viewYZ_Max;
                        }
                        drawFrameYZ();
                        break;
                }
            }
        });

        imgArrowForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDeselectAll();
                switch (tandaViewBottomNav){
                    case 2:
                        viewXY_Current++;
                        if(viewXY_Current > viewXY_Max){
                            viewXY_Current = 1;
                        }
                        drawFrameXY();
                        break;

                    case 3:
                        viewXZ_Current++;
                        if(viewXZ_Current > viewXZ_Max){
                            viewXZ_Current = 1;
                        }
                        drawFrameXZ();
                        break;

                    case 4:
                        viewYZ_Current++;
                        if(viewYZ_Current > viewYZ_Max){
                            viewYZ_Current = 1;
                        }
                        drawFrameYZ();
                        break;
                }
            }
        });
    }

    //LAIN-LAIN
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onBackPressed() {
        intent.setClass(getApplicationContext(), A_ProjectListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if(isStartProject){
            singleTapX = e.getX() - 540f + panCanvasX;
            singleTapY = -(e.getY() - 243) + 725f - panCanvasY;

            int referensi;

            switch (tandaViewBottomNav){
                case 2:
                    referensi = (viewXY_Current - 1) * (Nbx + 1) *(Nby + 1);
                    singleTapAcuan = koorNodeFix[referensi][2];
                    break;

                case 3:
                    referensi = (viewXZ_Current - 1) * (Nbx + 1);
                    singleTapAcuan = koorNodeFix[referensi][1];
                    break;

                case 4:
                    referensi = (viewYZ_Current - 1);
                    singleTapAcuan = koorNodeFix[referensi][0];
                    break;
            }

            if(!isDrawDisplacement){
                setElementSelected();
                if(tandaViewBottomNav == 2 && viewXY_Current != 1){
                    setAreaSelected();
                }
                setNodeSelected();
            }
        }
        return false;
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(isStartProject){
            if(isRotateActive){
                if(tandaViewBottomNav == 1) {
                    double sudut;

                    if (distanceX > 15.0f || distanceX < -15.0f || distanceY > 20.0f || distanceY < -20.0f) {
                        return false;
                    }

                    sudut = Double.parseDouble(Float.toString(distanceX)) * Math.PI / 180.0;

                    //Sementara
                    gama += sudut;
                    //Sementara

                    setRotasiMatrik3D(0, 0, -sudut, koorNode);
                    setRotasiMatrik3D(0, 0, -sudut, koorNodeAreaSelect);
                    if(isRestraintPin){
                        setRotasiMatrik3D(0, 0, -sudut, pinRestraint);
                        setRotasiMatrik3D(0, 0, -sudut, tumpuanSendi2);
                    }else{
                        setRotasiMatrik3D(0, 0, -sudut, tumpuanJepit1);
                        setRotasiMatrik3D(0, 0, -sudut, tumpuanJepit2);
                    }
                    setRotasiMatrik3D_Joint(0, 0, -sudut, koorNodeJointLoad);
                    setRotasiMatrik3D_Frame(0, 0, -sudut, koorDistributedFrameLoad);
                    setRotasiMatrik3D(0, 0, -sudut, koor3D_Axis);

                    if(isAlreadyRunning){
                        setRotasiMatrik3D(0, 0, -sudut, IF_AxialKolom);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Shear22Kolom);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Shear33Kolom);
                        setRotasiMatrik3D(0, 0, -sudut, IF_TorsiKolom);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Momen22Kolom);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Momen33Kolom);

                        setRotasiMatrik3D(0, 0, -sudut, IF_AxialBalokX);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Shear22BalokX);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Shear33BalokX);
                        setRotasiMatrik3D(0, 0, -sudut, IF_TorsiBalokX);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Momen22BalokX);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Momen33BalokX);

                        setRotasiMatrik3D(0, 0, -sudut, IF_AxialBalokY);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Shear22BalokY);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Shear33BalokY);
                        setRotasiMatrik3D(0, 0, -sudut, IF_TorsiBalokY);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Momen22BalokY);
                        setRotasiMatrik3D(0, 0, -sudut, IF_Momen33BalokY);

                        setRotasiMatrik3D(0, 0, -sudut, koorNodeDeformed);
                    }

                    //.........

                    sudut = Double.parseDouble(Float.toString(distanceY)) * Math.PI / 180.0;

                    //Sementara
                    beta += sudut;
                    //Sementara

                    setRotasiMatrik3D(0, -sudut, 0, koorNode);
                    setRotasiMatrik3D(0, -sudut, 0, koorNodeAreaSelect);
                    if(isRestraintPin){
                        setRotasiMatrik3D(0, -sudut, 0, pinRestraint);
                        setRotasiMatrik3D(0, -sudut, 0, tumpuanSendi2);
                    }else{
                        setRotasiMatrik3D(0, -sudut, 0, tumpuanJepit1);
                        setRotasiMatrik3D(0, -sudut, 0, tumpuanJepit2);
                    }
                    setRotasiMatrik3D_Joint(0, -sudut, 0, koorNodeJointLoad);
                    setRotasiMatrik3D_Frame(0, -sudut, 0, koorDistributedFrameLoad);
                    setRotasiMatrik3D(0, -sudut, 0, koor3D_Axis);

                    if(isAlreadyRunning){
                        setRotasiMatrik3D(0, -sudut, 0, IF_AxialKolom);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Shear22Kolom);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Shear33Kolom);
                        setRotasiMatrik3D(0, -sudut, 0, IF_TorsiKolom);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Momen22Kolom);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Momen33Kolom);

                        setRotasiMatrik3D(0, -sudut, 0, IF_AxialBalokX);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Shear22BalokX);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Shear33BalokX);
                        setRotasiMatrik3D(0, -sudut, 0, IF_TorsiBalokX);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Momen22BalokX);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Momen33BalokX);

                        setRotasiMatrik3D(0, -sudut, 0, IF_AxialBalokY);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Shear22BalokY);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Shear33BalokY);
                        setRotasiMatrik3D(0, -sudut, 0, IF_TorsiBalokY);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Momen22BalokY);
                        setRotasiMatrik3D(0, -sudut, 0, IF_Momen33BalokY);

                        setRotasiMatrik3D(0, -sudut, 0, koorNodeDeformed);
                    }

                    if(isTimerSleep){
                        drawGeneral();
                    }
                    return false;
                }
            }else{
                panCanvasX = panCanvasX + distanceX;
                panCanvasY = panCanvasY + distanceY;

                canvas.translate(-distanceX, distanceY);

                if(isTimerSleep){
                    drawGeneral();
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            setSkala(detector.getScaleFactor(), koorNode);
            setSkala(detector.getScaleFactor(), pinRestraint);
            setSkala(detector.getScaleFactor(), tumpuanSendi2);
            setSkala(detector.getScaleFactor(), tumpuanJepit1);
            setSkala(detector.getScaleFactor(), tumpuanJepit2);
            focalLength = focalLength * detector.getScaleFactor();
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        gestureDetectorCompat.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    public static float getfocalLength(){
        return focalLength;
    }
}