package com.cemapp.steelframe3D;

import android.widget.Toast;

public class Running {

    //DECLARATION DEFINITION

    //N_E           = Number of Element
    //N_EN	        = Number of Nodes per Element
    //N_N_Total     = Total Number of Node
    //N_DOF	        = Number of Degrees of Freedom
    //N_DOF_TOTAL   = Number of Degrees of Freedom
    //N_BC	        = Number of Boundary Conditions
    //N_BW 	        = Number of Bandwidth

    //K_S       = Kekakuan Struktur
    //F_S       = Load Struktur Global
    //U_S       = Displacement Struktur Global
    //K_E_L     = Kekakuan Elemen Lokal
    //K_E_G     = Kekakuan Elemen Global
    //F_G       = Gaya Luar Struktur
    //E_F       = Element Force
    //E_L       = Element Length

    //EL_L      = Equivalent Load Lokal
    //EL_G      = Equivalent Load Global

    //input gempa user
    private double Ss;
    private double S1;
    private double Ie;
    private double R;
    private String Situs;
    private boolean isBebanGempaX=false;
    private boolean isBebanGempaY=false;
    private boolean isGempaXPositif=true;
    private boolean isGempaYPositif=true;

    private static int N_E;
    private static int N_EN;
    private static int N_N_Total;
    private static int N_DOF;
    private static int N_DOF_TOTAL;
    private static int N_BW;

    private static double[][] K_S;
    private static double[] F_S;
    public static double[] U_S;
    private static double[][] K_E_L;
    private static double[][] K_E_G;
    public static double[] R_S;
    private static double CNST;
    public static double[][] E_F;
    public static double[] E_L;
    private static double[][] R_Transformasi = new double[12][12];
    private static double[] AreaLoadBeam;

    public static double[][] IF_ForcesAxialKolom;
    public static double[][] IF_ForcesShear22Kolom;
    public static double[][] IF_ForcesShear33Kolom;

    public static double[][] IF_ForcesTorsiKolom;
    public static double[][] IF_ForcesMomen22Kolom;
    public static double[][] IF_ForcesMomen33Kolom;

    public static double[][] IF_ForcesAxialBalokX;
    public static double[][] IF_ForcesShear22BalokX;
    public static double[][] IF_ForcesShear33BalokX;

    public static double[][] IF_ForcesTorsiBalokX;
    public static double[][] IF_ForcesMomen22BalokX;
    public static double[][] IF_ForcesMomen33BalokX;

    public static double[][] IF_ForcesAxialBalokY;
    public static double[][] IF_ForcesShear22BalokY;
    public static double[][] IF_ForcesShear33BalokY;

    public static double[][] IF_ForcesTorsiBalokY;
    public static double[][] IF_ForcesMomen22BalokY;
    public static double[][] IF_ForcesMomen33BalokY;

    public void run(){
        N_EN = 2;
        N_DOF = 6;
        N_N_Total = D_MainActivity.koorNode.length;
        N_DOF_TOTAL = N_N_Total * N_DOF;
        N_E = D_MainActivity.pointerElemenStruktur.length;
        N_BW = ((D_MainActivity.Nbx + 1) * (D_MainActivity.Nby + 1) + 1) * N_DOF;

        K_S = new double[N_DOF_TOTAL][N_BW];
        F_S = new double[N_DOF_TOTAL];
        U_S = new double[N_DOF_TOTAL];
        R_S = new double[6 * (D_MainActivity.Nbx + 1) * (D_MainActivity.Nby + 1)];
        K_E_G = new double[12][12];
        E_F = new double[N_E][12];
        E_L = new double[N_E];
        AreaLoadBeam = new double[N_E - D_MainActivity.pointerElemenKolom.length];

        isBebanGempaX = D_MainActivity.isBebanGempaX;
        isBebanGempaY = D_MainActivity.isBebanGempaY;

        setKekakuanStruktur();
        setLoadGlobal();
        modifyForBC();
        solveBandedMatrix();
        calculateEndForce();
        calculateReaction();

        D_MainActivity objek = new D_MainActivity();
        objek.setKoorToDrawLoad();

        setKoorIF_Kolom();
        setKoorIF_BalokX();
        setKoorIF_BalokY();
        setKoorIF_Fix();
        setKoorDeformed();

        K_S = null;
        F_S = null;
        K_E_G = null;

        Toast.makeText(D_MainActivity.appContext, "Successfully analyzed.", Toast.LENGTH_LONG).show();
    }

    private void setKekakuanStruktur(){
        int NRT, NR, NCT, NC, i_node, j_node;

        for(int i=0; i<N_E; i++){
            setKekakuanElemenGlobal(i);
            for(int ii=0; ii<N_EN; ii++){
                NRT = N_DOF * (D_MainActivity.pointerElemenStruktur[i][ii]);
                for(int it=0; it<N_DOF; it++){
                    NR = NRT + it;
                    i_node = N_DOF * ii + it;
                    for(int jj=0; jj<N_EN; jj++){
                        NCT = N_DOF * (D_MainActivity.pointerElemenStruktur[i][jj]);
                        for(int jt=0; jt<N_DOF; jt++){
                            NC = (NCT - NR) + jt;
                            j_node = N_DOF * jj + jt;
                            if(NC >= 0){
                                K_S[NR][NC] = K_S[NR][NC] + K_E_G[i_node][j_node];
                            }
                        }
                    }
                }
            }
        }
    }

    private void setKekakuanElemenGlobal(int elemen_ke){
        int i_node, j_node;
        double x_ij, y_ij, z_ij;
        double E, A, JIx, Iy, Iz, G;
        double EL, EAL, EIyL, EIzL, GIxL;
        double[][] K_E_G_Dummy = new double[12][12];
        K_E_L = new double[12][12];

        //mengambil nodal i dan j
        i_node = D_MainActivity.pointerElemenStruktur[elemen_ke][0];
        j_node = D_MainActivity.pointerElemenStruktur[elemen_ke][1];

        //menghitung koordinat x y z (?)
        x_ij = D_MainActivity.koorNodeReal[j_node][0] - D_MainActivity.koorNodeReal[i_node][0];
        y_ij = D_MainActivity.koorNodeReal[j_node][1] - D_MainActivity.koorNodeReal[i_node][1];
        z_ij = D_MainActivity.koorNodeReal[j_node][2] - D_MainActivity.koorNodeReal[i_node][2];

        //menghitung panjang elemen (?)
        EL = Math.pow((x_ij * x_ij) + (y_ij * y_ij) + (z_ij * z_ij), 0.5);
        E_L[elemen_ke] = EL;

        //mengambil properti dari listFrameSection -> SteelSection
        E = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[elemen_ke]).getE();
        A = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[elemen_ke]).getA();
        JIx = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[elemen_ke]).getJIx();
        Iy = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[elemen_ke]).getIy();
        Iz = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[elemen_ke]).getIz();
        G = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[elemen_ke]).getG();

        EAL = E * A / EL;
        EIyL = E * Iy / EL;
        EIzL = E * Iz / EL;
        GIxL = G * JIx / EL;

        //mengisi matriks kekakuan elemen dengan 0 semua
        for(int i=0; i<12; i++){
            for(int j=0; j<12; j++){
                K_E_L[i][j] = 0;
            }
        }

        //mengisi matriks kekakuan elemen dengan nilai (ngisinya biasa 12x12 (bukan banded))
        //0
        K_E_L[0][0] = EAL ;
        K_E_L[0][6] = -EAL;

        //1
        K_E_L[1][1] = 12 * EIzL / EL / EL;
        K_E_L[1][5] = 6 * EIzL / EL;
        K_E_L[1][7] = -12 * EIzL / EL / EL;
        K_E_L[1][11] = 6 * EIzL / EL;

        //2
        K_E_L[2][2] = 12 * EIyL / EL / EL;
        K_E_L[2][4] = -6 * EIyL / EL;
        K_E_L[2][8] = -12 * EIyL / EL / EL;
        K_E_L[2][10] = -6 * EIyL / EL;

        //3
        K_E_L[3][3] = GIxL;
        K_E_L[3][9] = -GIxL;

        //4
        K_E_L[4][2] = -6 * EIyL / EL;
        K_E_L[4][4] = 4 * EIyL;
        K_E_L[4][8] = 6 * EIyL / EL;
        K_E_L[4][10] = 2 * EIyL;

        //5
        K_E_L[5][1] = 6 * EIzL / EL;
        K_E_L[5][5] = 4 * EIzL;
        K_E_L[5][7] = -6 * EIzL / EL;
        K_E_L[5][11] = 2 * EIzL;

        //6
        K_E_L[6][0] = -EAL;
        K_E_L[6][6] = EAL;

        //7
        K_E_L[7][1] = -12 * EIzL / EL / EL;
        K_E_L[7][5] = -6 * EIzL / EL;
        K_E_L[7][7] = 12 * EIzL / EL / EL;
        K_E_L[7][11] = -6 * EIzL / EL;

        //8
        K_E_L[8][2] = -12 * EIyL / EL / EL;
        K_E_L[8][4] = 6 * EIyL / EL;
        K_E_L[8][8] = 12 * EIyL / EL / EL;
        K_E_L[8][10] = 6 * EIyL / EL;

        //9
        K_E_L[9][3] = -GIxL;
        K_E_L[9][9] = GIxL;

        //10
        K_E_L[10][2] = -6 * EIyL / EL;
        K_E_L[10][4] = 2 * EIyL;
        K_E_L[10][8] = 6 * EIyL / EL;
        K_E_L[10][10] = 4 * EIyL;

        //11
        K_E_L[11][1] = 6 * EIzL / EL;
        K_E_L[11][5] = 2 * EIzL;
        K_E_L[11][7] = -6 * EIzL / EL;
        K_E_L[11][11] = 4 * EIzL;

        setMatriksTransformasi(elemen_ke);

        for(int i=0; i<12; i++){
            for(int j=0; j<12; j++){
                for(int k=0; k<12; k++){
                    K_E_G_Dummy[i][j] = K_E_G_Dummy[i][j] + K_E_L[i][k] * R_Transformasi[k][j];
                }
            }
        }

        for(int i=0; i<12; i++){
            for(int j=0; j<12; j++){
                K_E_G[i][j] = 0;
            }
        }

        for(int i=0; i<12; i++){
            for(int j=0; j<12; j++){
                for(int k=0; k<12; k++){
                    K_E_G[i][j] = K_E_G[i][j] + R_Transformasi[k][i] * K_E_G_Dummy[k][j];
                }
            }
        }
    }

    private void setMatriksTransformasi(int elemen_ke){
        int i_node, j_node;
        double x_ij, y_ij, z_ij;
        double EL;
        double lamdaX, lamdaY, lamdaZ;
        double[][] Ra = new double[3][3];
        double[][] Rb = new double[3][3];
        double[][] Rc = new double[3][3];
        double[][] R_Abc = new double[3][3];
        double[][] R_Dummy = new double[3][3];

        //mengambil node i dan j
        i_node = D_MainActivity.pointerElemenStruktur[elemen_ke][0];
        j_node = D_MainActivity.pointerElemenStruktur[elemen_ke][1];

        //menghitung koordinat x, y, z (?)
        x_ij = D_MainActivity.koorNodeReal[j_node][0] - D_MainActivity.koorNodeReal[i_node][0];
        y_ij = D_MainActivity.koorNodeReal[j_node][1] - D_MainActivity.koorNodeReal[i_node][1];
        z_ij = D_MainActivity.koorNodeReal[j_node][2] - D_MainActivity.koorNodeReal[i_node][2];

        //menghitung panjang elemen (?)
        EL = Math.pow((x_ij * x_ij) + (y_ij * y_ij) + (z_ij * z_ij), 0.5);

        lamdaX = x_ij / EL;
        lamdaY = y_ij / EL;
        lamdaZ = z_ij / EL;

        //Matrix Alpha
        Ra[0][0] = 1; Ra[0][1] = 0; Ra[0][2] = 0;
        Ra[1][0] = 0; Ra[1][1] = 1; Ra[1][2] = 0;
        Ra[2][0] = 0; Ra[2][1] = 0; Ra[2][2] = 1;

        //Matrix Beta
        //0
        if(lamdaX == 0 && lamdaZ == 0) {
            Rb[0][0] = 1;
            Rb[0][1] = 0;
            Rb[0][2] = 0;
        }else {
            Rb[0][0] = lamdaX / Math.sqrt(lamdaX * lamdaX + lamdaZ * lamdaZ);
            Rb[0][1] = 0;
            Rb[0][2] = lamdaZ / Math.sqrt(lamdaX * lamdaX + lamdaZ * lamdaZ);
        }

        //1
        Rb[1][0] = 0;
        Rb[1][1] = 1;
        Rb[1][2] = 0;

        //2
        Rb[2][0] = -Rb[0][2];
        Rb[2][1] = 0;
        Rb[2][2] = Rb[0][0];

        //Matrix Gama
        //0
        Rc[0][0] = Math.sqrt(lamdaX * lamdaX + lamdaZ * lamdaZ);
        Rc[0][1] = lamdaY;
        Rc[0][2] = 0;

        //1
        Rc[1][0] = -Rc[0][1];
        Rc[1][1] = Rc[0][0];
        Rc[1][2] = 0;

        //2
        Rc[2][0] = 0;
        Rc[2][1] = 0;
        Rc[2][2] = 1;

        //menyusun matriks [R]
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                for(int k=0; k<3;k++){
                    R_Dummy[i][j] = R_Dummy[i][j] + Rc[i][k] * Rb[k][j];
                }
            }
        }
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                for(int k=0; k<3; k++){
                    R_Abc[i][j] = R_Abc[i][j] + Ra[i][k] * R_Dummy[k][j];
                }
            }
        }

        //menyusun matriks [T] = kumpulan [R]
        for(int k=0; k<4; k++){
            int lompatan = 3 * k;

            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    R_Transformasi[i + lompatan][j + lompatan] = R_Abc[i][j];
                }
            }
        }
    }

    private void setLoadGlobal(){
        double[] EL_G = new double[12];
        int i_node, j_node;
        int elemenKolom = D_MainActivity.pointerElemenKolom.length;
        int elemenBalokXZ = D_MainActivity.pointerElemenBalokXZ.length;
        double BW_X = D_MainActivity.Bwx * 1000; //mm
        double BW_Y = D_MainActivity.Bwy * 1000; //mm

        //Joint Load
        for(int i=0; i<N_N_Total; i++){ // i = nomor nodal
            for(int j=0; j<3; j++){
                F_S[i * N_DOF + j] = D_MainActivity.jointLoad[i][j] * 1000; //Newton
            }
            for(int j=3; j<6; j++){
                F_S[i * N_DOF + j] = D_MainActivity.jointLoad[i][j] * 1000 * 1000; //Newton mm
            }
        }

        //EARTHQUAKE LOAD
        Ss = D_MainActivity.gempaSs;
        S1 = D_MainActivity.gempaS1;
        Ie = D_MainActivity.gempaIe;
        R = D_MainActivity.gempaR;
        Situs = D_MainActivity.gempaSitus;
        isGempaXPositif = D_MainActivity.isGempaXPositif;
        isGempaYPositif = D_MainActivity.isGempaYPositif;

        if(isBebanGempaX){
            new Y_BebanGempa(Ss,S1,Ie,R,Situs);
            for(int i=0; i<N_N_Total; i++){ // i = nomor nodal
                if(isGempaXPositif){
                    F_S[i * N_DOF + 0] = F_S[i * N_DOF + 0] + Y_BebanGempa.EL_G_EQ[i]*1000;
                }else{
                    F_S[i * N_DOF + 0] = F_S[i * N_DOF + 0] - Y_BebanGempa.EL_G_EQ[i]*1000;
                }
                if(isGempaYPositif){
                    F_S[i * N_DOF + 1] = F_S[i * N_DOF + 1] + 0.3* Y_BebanGempa.EL_G_EQ[i]*1000; //gempa x harus + 0.3 gempa y
                }else{
                    F_S[i * N_DOF + 1] = F_S[i * N_DOF + 1] - 0.3* Y_BebanGempa.EL_G_EQ[i]*1000; //gempa x harus + 0.3 gempa y
                }
            }
        }
        if(isBebanGempaY){
            new Y_BebanGempa(Ss,S1,Ie,R,Situs);
            for(int i=0; i<N_N_Total; i++){ // i = nomor nodal
                if(isGempaYPositif){
                    F_S[i * N_DOF + 1] = F_S[i * N_DOF + 1] + Y_BebanGempa.EL_G_EQ[i]*1000;
                }else{
                    F_S[i * N_DOF + 1] = F_S[i * N_DOF + 1] - Y_BebanGempa.EL_G_EQ[i]*1000;
                }
                if(isGempaXPositif){
                    F_S[i * N_DOF + 0] = F_S[i * N_DOF + 0] + 0.3* Y_BebanGempa.EL_G_EQ[i]*1000; //gempa x harus + 0.3 gempa y
                }else{
                    F_S[i * N_DOF + 0] = F_S[i * N_DOF + 0] - 0.3* Y_BebanGempa.EL_G_EQ[i]*1000; //gempa x harus + 0.3 gempa y
                }
            }
            
        }

        //Frame Load Dead
        for(int i=0; i<N_E; i++) { //i = nomor elemen
            //mengambil nodal i dan j
            i_node = D_MainActivity.pointerElemenStruktur[i][0];
            j_node = D_MainActivity.pointerElemenStruktur[i][1];

            //mengambil data beban dari listFrameSection -> SteelSection
            double loadFrameDead = D_MainActivity.listFrameSection.
                    get(D_MainActivity.pointerFrameSection[i]).getLoadFrameDead(); //N per mm

            //memasukkan beban ke matriks beban
            if(i < elemenKolom){
                F_S[i_node * N_DOF + 2] = F_S[i_node * N_DOF + 2] - loadFrameDead * E_L[i];

            }else if(i < elemenKolom + elemenBalokXZ){
                double FEM = loadFrameDead * BW_X * BW_X / 12; //N-mm

                F_S[i_node * N_DOF + 2] = F_S[i_node * N_DOF + 2] - loadFrameDead * E_L[i] / 2;
                F_S[j_node * N_DOF + 2] = F_S[j_node * N_DOF + 2] - loadFrameDead * E_L[i] / 2;

                F_S[i_node * N_DOF + 4] = F_S[i_node * N_DOF + 4] + FEM;
                F_S[j_node * N_DOF + 4] = F_S[j_node * N_DOF + 4] - FEM;
            }else{
                double FEM = loadFrameDead * BW_Y * BW_Y / 12; //N-mm

                F_S[i_node * N_DOF + 2] = F_S[i_node * N_DOF + 2] - loadFrameDead * E_L[i] / 2;
                F_S[j_node * N_DOF + 2] = F_S[j_node * N_DOF + 2] - loadFrameDead * E_L[i] / 2;

                F_S[i_node * N_DOF + 3] = F_S[i_node * N_DOF + 3] - FEM;
                F_S[j_node * N_DOF + 3] = F_S[j_node * N_DOF + 3] + FEM;
            }
        }

        //Frame Load Additional
        for(int i=0; i<N_E; i++){
            i_node = D_MainActivity.pointerElemenStruktur[i][0];
            j_node = D_MainActivity.pointerElemenStruktur[i][1];

            //Node i ======================================
            //Force
            EL_G[0] = D_MainActivity.frameLoad[i][0] * E_L[i] / 2;
            EL_G[1] = D_MainActivity.frameLoad[i][1] * E_L[i] / 2;
            EL_G[2] = D_MainActivity.frameLoad[i][2] * E_L[i] / 2;

            //Node j ======================================
            //Force
            EL_G[6] = D_MainActivity.frameLoad[i][0] * E_L[i] / 2;
            EL_G[7] = D_MainActivity.frameLoad[i][1] * E_L[i] / 2;
            EL_G[8] = D_MainActivity.frameLoad[i][2] * E_L[i] / 2;

            if(i < elemenKolom){
                //Momen
                EL_G[3] = -D_MainActivity.frameLoad[i][1] * E_L[i] * E_L[i]  / 12;
                EL_G[4] = D_MainActivity.frameLoad[i][0] * E_L[i] * E_L[i]  / 12;
                EL_G[5] = 0;

                EL_G[9] = -EL_G[3];
                EL_G[10] = -EL_G[4];
                EL_G[11] = -EL_G[5];
                //=============================================
            }else if(i < elemenKolom + elemenBalokXZ){
                //Momen
                EL_G[3] = 0;
                EL_G[4] = -D_MainActivity.frameLoad[i][2] * E_L[i] * E_L[i]  / 12;
                EL_G[5] = D_MainActivity.frameLoad[i][1] * E_L[i] * E_L[i]  / 12;

                EL_G[9] = -EL_G[3];
                EL_G[10] = -EL_G[4];
                EL_G[11] = -EL_G[5];
                //=============================================
            }else{
                //Momen
                EL_G[3] = D_MainActivity.frameLoad[i][2] * E_L[i] * E_L[i]  / 12;
                EL_G[4] = 0;
                EL_G[5] = -D_MainActivity.frameLoad[i][0] * E_L[i] * E_L[i]  / 12;

                EL_G[9] = -EL_G[3];
                EL_G[10] = -EL_G[4];
                EL_G[11] = -EL_G[5];
            }

            for(int j=0; j<6; j++){
                F_S[N_DOF * i_node + j] = F_S[N_DOF * i_node + j] + EL_G[j];
                F_S[N_DOF * j_node + j] = F_S[N_DOF * j_node + j] + EL_G[j + N_DOF];
            }
        }

        //Area Load Dead & Live
        double loadArea;
        double loadAreaLive;
        double MinBalok = Math.min(BW_X, BW_Y); //mm
        double HalfBalok = MinBalok / 2; //mm
        double a = MinBalok / 2; //mm

        double SisiAtasTrapesiumX = BW_X - MinBalok; //mm
        double SisiAtasTrapesiumY = BW_Y - MinBalok; //mm
        double EquiLoad;
        double FEM;

        int x_elemen;
        int x = elemenKolom;
        int y = 1;
        for(int i=0; i<D_MainActivity.areaLoad.length; i++){
            loadArea = 0;
            loadAreaLive = D_MainActivity.areaLoad[i] / 1000 * HalfBalok; //N per mm

            loadArea += loadAreaLive;

            x_elemen = x;
            for(int j=1; j<=2; j++){
                AreaLoadBeam[x_elemen - elemenKolom] = AreaLoadBeam[x_elemen - elemenKolom] + loadArea;

                i_node = D_MainActivity.pointerElemenStruktur[x_elemen][0];
                j_node = D_MainActivity.pointerElemenStruktur[x_elemen][1];

                EquiLoad = 0.5 * (BW_X + SisiAtasTrapesiumX) / 2.0 * loadArea;
                FEM = loadArea / (12.0 * BW_X) * (Math.pow(BW_X, 3) - 2.0 * a * a * BW_X + Math.pow(a, 3));

                F_S[i_node * N_DOF + 2] = F_S[i_node * N_DOF + 2] - EquiLoad;
                F_S[i_node * N_DOF + 4] = F_S[i_node * N_DOF + 4] + FEM;

                F_S[j_node * N_DOF + 2] = F_S[j_node * N_DOF + 2] - EquiLoad;
                F_S[j_node * N_DOF + 4] = F_S[j_node * N_DOF + 4] - FEM;

                x_elemen = x + D_MainActivity.Nbx;
            }

            if(y == D_MainActivity.Nbx * D_MainActivity.Nby){
                y = 1;
                x += D_MainActivity.Nbx + 1;
            }else{
                x++;
                y++;
            }
        }


        x = elemenKolom + elemenBalokXZ;
        y = 1;
        for(int i=0; i<D_MainActivity.areaLoad.length; i++){
            loadArea = 0;
            loadAreaLive = D_MainActivity.areaLoad[i] / 1000 * HalfBalok; //N per mm

            loadArea += loadAreaLive;

            x_elemen = x;
            for(int j=1; j<=2; j++){
                AreaLoadBeam[x_elemen - elemenKolom] = AreaLoadBeam[x_elemen - elemenKolom] + loadArea;

                i_node = D_MainActivity.pointerElemenStruktur[x_elemen][0];
                j_node = D_MainActivity.pointerElemenStruktur[x_elemen][1];

                EquiLoad = 0.5 * (BW_Y + SisiAtasTrapesiumY) / 2.0 * loadArea;
                FEM = loadArea / (12.0 * BW_Y) * (Math.pow(BW_Y, 3) - 2.0 * a * a * BW_Y + Math.pow(a, 3));

                F_S[i_node * N_DOF + 2] = F_S[i_node * N_DOF + 2] - EquiLoad;
                F_S[i_node * N_DOF + 3] = F_S[i_node * N_DOF + 3] - FEM;

                F_S[j_node * N_DOF + 2] = F_S[j_node * N_DOF + 2] - EquiLoad;
                F_S[j_node * N_DOF + 3] = F_S[j_node * N_DOF + 3] + FEM;

                x_elemen = x + 1;
            }

            if(y == D_MainActivity.Nbx){
                y = 1;
                x += 2;
            }else{
                x++;
                y++;
            }
        }
    }

    private void modifyForBC(){
        //Decide Penalty Parameter CNST
        CNST = 0;
        for(int i=0; i<N_DOF_TOTAL; i++){
            if(CNST < K_S[i][0]){
                CNST = K_S[i][0];
            }
        }
        CNST = CNST * 10000;

        //Modify for Boundary Conditions - Displacement
        if(D_MainActivity.isRestraintPin){
            for(int i = 0; i<(D_MainActivity.Nbx + 1)*(D_MainActivity.Nby + 1); i++){
                for(int j=0; j<3; j++){
                    K_S[i * N_DOF + j][0] = CNST;
                }
            }
        }else{
            for(int i = 0; i<(D_MainActivity.Nbx + 1)*(D_MainActivity.Nby + 1); i++){
                for(int j=0; j<6; j++){
                    K_S[i * N_DOF + j][0] = CNST;
                }
            }
        }
    }

    private void solveBandedMatrix(){
        //Forward Elimination
        for(int k=0; k<N_DOF_TOTAL; k++){
            int N_K = N_DOF_TOTAL - k;

            if(N_K > N_BW){
                N_K = N_BW;
            }

            for(int i=1; i<N_K; i++){
                int i_1 = k + i;

                double dummy = K_S[k][i] / K_S[k][0];

                for(int j=i; j<N_K; j++){
                    int j_1 = j - i;
                    K_S[i_1][j_1] = K_S[i_1][j_1] - dummy * K_S[k][j];
                }
                F_S[i_1] = F_S[i_1] - dummy * F_S[k];
            }
        }

        //Back Substitution (Displacement)
        U_S[N_DOF_TOTAL - 1] = F_S[N_DOF_TOTAL - 1] / K_S[N_DOF_TOTAL - 1][0];

        for(int k=N_DOF_TOTAL-2; k>=0; k--){
            U_S[k] = F_S[k] / K_S[k][0];

            int N_K = N_DOF_TOTAL - k;
            if(N_K > N_BW){
                N_K = N_BW;
            }

            for(int j=1; j<N_K; j++){
                U_S[k] = U_S[k] - K_S[k][j] * U_S[k + j] / K_S[k][0];
            }

        }
    }

    private void calculateEndForce(){ //Reaksi Tumpuan
        int i_node, j_node;
        double[] U_E = new double[12];
        double FEM; //Fixed End Moment
        int elemenKolom = D_MainActivity.pointerElemenKolom.length;
        int elemenBalokXZ = D_MainActivity.pointerElemenBalokXZ.length;

        for(int n=0; n<N_E; n++){ //n = number of element
            i_node = D_MainActivity.pointerElemenStruktur[n][0];
            j_node = D_MainActivity.pointerElemenStruktur[n][1];
            double loadFrameDead = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[n]).getLoadFrameDead(); //N per mm
            double[] EL_G = new double[12];
            double[] U_E_LOKAL = new double[12];

            if(n < elemenKolom){
                //Frame Load Dead ==================================================================
                EL_G[0] =  EL_G[0] - loadFrameDead * E_L[n];
                // =================================================================================

                //Frame Load Additional ============================================================
                //Force
                EL_G[0] = EL_G[0] + D_MainActivity.frameLoad[n][2] * E_L[n] / 2;
                EL_G[6] = EL_G[6] + D_MainActivity.frameLoad[n][2] * E_L[n] / 2;

                EL_G[1] = EL_G[1] + D_MainActivity.frameLoad[n][1] * E_L[n] / 2;
                EL_G[7] = EL_G[7] + D_MainActivity.frameLoad[n][1] * E_L[n] / 2;

                EL_G[2] = EL_G[2] - D_MainActivity.frameLoad[n][0] * E_L[n] / 2;
                EL_G[8] = EL_G[8] - D_MainActivity.frameLoad[n][0] * E_L[n] / 2;

                //Moment
                FEM = D_MainActivity.frameLoad[n][0] * E_L[n] * E_L[n]  / 12;
                EL_G[4] = EL_G[4] + FEM;
                EL_G[10] = EL_G[10] - FEM;

                FEM = D_MainActivity.frameLoad[n][0] * E_L[n] * E_L[n]  / 12;
                EL_G[5] =  EL_G[5] + FEM;
                EL_G[11] = EL_G[11] - FEM;
                // =================================================================================

            }else if(n < elemenKolom + elemenBalokXZ){
                //Frame Load Dead ==================================================================
                //Force
                EL_G[2] = EL_G[2] - loadFrameDead * E_L[n] / 2;
                EL_G[8] = EL_G[8] - loadFrameDead * E_L[n] / 2;

                //Moment
                FEM = loadFrameDead * E_L[n] * E_L[n] / 12; //N-mm
                EL_G[4] = EL_G[4] + FEM;
                EL_G[10] = EL_G[10] - FEM;
                //==================================================================================

                //Frame Load Additional ============================================================
                //Force
                EL_G[0] = EL_G[0] + D_MainActivity.frameLoad[n][0] * E_L[n] / 2;
                EL_G[6] = EL_G[6] + D_MainActivity.frameLoad[n][0] * E_L[n] / 2;

                EL_G[1] = EL_G[1] + D_MainActivity.frameLoad[n][1] * E_L[n] / 2;
                EL_G[7] = EL_G[7] + D_MainActivity.frameLoad[n][1] * E_L[n] / 2;

                EL_G[2] = EL_G[2] + D_MainActivity.frameLoad[n][2] * E_L[n] / 2;
                EL_G[8] = EL_G[8] + D_MainActivity.frameLoad[n][2] * E_L[n] / 2;

                //Moment
                FEM = D_MainActivity.frameLoad[n][2] * E_L[n] * E_L[n] / 12; //N-mm
                EL_G[4] = EL_G[4] - FEM;
                EL_G[10] = EL_G[10] + FEM;

                FEM = D_MainActivity.frameLoad[n][1] * E_L[n] * E_L[n] / 12; //N-mm
                EL_G[5] = EL_G[5] + FEM;
                EL_G[11] = EL_G[11] - FEM;
                // =================================================================================
            }else{
                //Frame Load Dead ==================================================================
                //Force
                EL_G[2] = EL_G[2] - loadFrameDead * E_L[n] / 2;
                EL_G[8] = EL_G[8] - loadFrameDead * E_L[n] / 2;

                //Moment
                FEM = loadFrameDead * E_L[n] * E_L[n] / 12; //N-mm
                EL_G[4] = EL_G[4] + FEM;
                EL_G[10] = EL_G[10] - FEM;
                //==================================================================================

                //Frame Load Additional ============================================================
                //Force
                EL_G[0] = EL_G[0] + D_MainActivity.frameLoad[n][1] * E_L[n] / 2;
                EL_G[6] = EL_G[6] + D_MainActivity.frameLoad[n][1] * E_L[n] / 2;

                EL_G[1] = EL_G[1] - D_MainActivity.frameLoad[n][0] * E_L[n] / 2;
                EL_G[7] = EL_G[7] - D_MainActivity.frameLoad[n][0] * E_L[n] / 2;

                EL_G[2] = EL_G[2] + D_MainActivity.frameLoad[n][2] * E_L[n] / 2;
                EL_G[8] = EL_G[8] + D_MainActivity.frameLoad[n][2] * E_L[n] / 2;

                //Moment
                FEM = D_MainActivity.frameLoad[n][2] * E_L[n] * E_L[n] / 12; //N-mm
                EL_G[4] = EL_G[4] - FEM;
                EL_G[10] = EL_G[10] + FEM;

                FEM = D_MainActivity.frameLoad[n][0] * E_L[n] * E_L[n] / 12; //N-mm
                EL_G[5] = EL_G[5] - FEM;
                EL_G[11] = EL_G[11] + FEM;
                // =================================================================================
            }

            for(int i=0; i<6; i++){
                U_E[i] = U_S[6 * i_node + i];
                U_E[i + 6] = U_S[6 * j_node + i];
            }

            //Set Kekakuan & Transformasi
            setKekakuanElemenGlobal(n);

            for(int i=0; i<12; i++) {
                for(int j=0; j<12; j++) {
                    U_E_LOKAL[i] = U_E_LOKAL[i] + R_Transformasi[i][j] * U_E[j];
                }
            }

            //member forces (E_F)
            for(int i=0; i<12; i++) {
                E_F[n][i] = - EL_G[i];
                for(int j=0; j<12; j++) {
                    E_F[n][i] = E_F[n][i] + K_E_L[i][j] * U_E_LOKAL[j];
                }
            }
        }

        //Area Load Dead & Live
        double loadArea;
        double loadAreaLive;
        double MinBalok = Math.min(D_MainActivity.Bwx, D_MainActivity.Bwy) * 1000; //mm
        double HalfBalok = MinBalok / 2; //mm
        double a = MinBalok / 2; //mm
        double SisiAtasTrapesiumX = D_MainActivity.Bwx * 1000 - MinBalok; //mm
        double SisiAtasTrapesiumY = D_MainActivity.Bwy * 1000 - MinBalok;
        double EquiLoad;

        int x_elemen;
        int x = elemenKolom;
        int y = 1;
        for(int i=0; i<D_MainActivity.areaLoad.length; i++){
            loadArea = D_MainActivity.areaLoad[i] / 1000 * HalfBalok; //N per mm

            x_elemen = x;
            for(int j=1; j<=2; j++){
                double[] EL_G = new double[12];

                EquiLoad = 0.5 * (E_L[x_elemen] + SisiAtasTrapesiumX) / 2.0 * loadArea;
                FEM = loadArea / (12.0 * E_L[x_elemen]) * (Math.pow(E_L[x_elemen], 3) - 2.0 * a * a * E_L[x_elemen] + Math.pow(a, 3));

                EL_G[2] = EL_G[2] - EquiLoad;
                EL_G[8] = EL_G[8] - EquiLoad;

                EL_G[4] = EL_G[4] + FEM;
                EL_G[10] = EL_G[10] - FEM;

                //member forces (E_F)
                for(int k=0; k<12; k++) {
                    E_F[x_elemen][k] = E_F[x_elemen][k] - EL_G[k];
                }

                x_elemen = x + D_MainActivity.Nbx;
            }

            if(y == D_MainActivity.Nbx * D_MainActivity.Nby){
                y = 1;
                x += D_MainActivity.Nbx + 1;
            }else{
                x++;
                y++;
            }
        }

        x = elemenKolom + elemenBalokXZ;
        y = 1;
        for(int i=0; i<D_MainActivity.areaLoad.length; i++){
            loadArea = D_MainActivity.areaLoad[i] / 1000 * HalfBalok; //N per mm

            x_elemen = x;
            for(int j=1; j<=2; j++){
                double[] EL_G = new double[12];

                EquiLoad = 0.5 * (E_L[x_elemen] + SisiAtasTrapesiumY) / 2.0 * loadArea;
                FEM = loadArea / (12.0 * E_L[x_elemen]) * (Math.pow(E_L[x_elemen], 3) - 2.0 * a * a * E_L[x_elemen] + Math.pow(a, 3));

                EL_G[2] = EL_G[2] - EquiLoad;
                EL_G[8] = EL_G[8] - EquiLoad;

                EL_G[4] = EL_G[4] + FEM;
                EL_G[10] = EL_G[10] - FEM;

                //member forces (E_F)
                for(int k=0; k<12; k++) {
                    E_F[x_elemen][k] = E_F[x_elemen][k] - EL_G[k];
                }

                x_elemen = x + 1;
            }

            if(y == D_MainActivity.Nbx){
                y = 1;
                x += 2;
            }else{
                x++;
                y++;
            }
        }
    }

    private void calculateReaction(){
        if(D_MainActivity.isRestraintPin){
            for(int i = 0; i<(D_MainActivity.Nbx + 1)*(D_MainActivity.Nby + 1); i++){ // i = nodal tumpuan
                for(int j=0; j<3; j++){
                    R_S[i * N_DOF + j] = CNST * (-U_S[i * N_DOF + j]);
                }

                for(int j=3; j<6; j++){
                    R_S[i * N_DOF + j] = 0;
                }
            }
        }else{
            for(int i=0; i<R_S.length; i++){
                R_S[i] = CNST * (-U_S[i]);
            }
        }
    }

    public void setKoorIF_Kolom(){
        D_MainActivity.IF_AxialKolom = new double[D_MainActivity.pointerElemenKolom.length][52][3];
        D_MainActivity.IF_Shear22Kolom = new double[D_MainActivity.pointerElemenKolom.length][52][3];
        D_MainActivity.IF_Shear33Kolom = new double[D_MainActivity.pointerElemenKolom.length][52][3];

        D_MainActivity.IF_TorsiKolom = new double[D_MainActivity.pointerElemenKolom.length][4][3];
        D_MainActivity.IF_Momen22Kolom = new double[D_MainActivity.pointerElemenKolom.length][52][3];
        D_MainActivity.IF_Momen33Kolom = new double[D_MainActivity.pointerElemenKolom.length][52][3];

        //************************************************

        D_MainActivity.IF_AxialKolomNodal = new int[D_MainActivity.pointerElemenKolom.length][5];
        D_MainActivity.IF_Shear22KolomNodal = new int[D_MainActivity.pointerElemenKolom.length][5];
        D_MainActivity.IF_Shear33KolomNodal = new int[D_MainActivity.pointerElemenKolom.length][5];

        D_MainActivity.IF_Momen22KolomNodal = new int[D_MainActivity.pointerElemenKolom.length][5];
        D_MainActivity.IF_Momen33KolomNodal = new int[D_MainActivity.pointerElemenKolom.length][5];

        //************************************************

        D_MainActivity.isFirstPositif_AxialKolom = new boolean[D_MainActivity.pointerElemenKolom.length];
        D_MainActivity.isFirstPositif_Shear22Kolom = new boolean[D_MainActivity.pointerElemenKolom.length];
        D_MainActivity.isFirstPositif_Shear33Kolom = new boolean[D_MainActivity.pointerElemenKolom.length];

        D_MainActivity.isFirstPositif_TorsiKolom = new boolean[D_MainActivity.pointerElemenKolom.length];
        D_MainActivity.isFirstPositif_Momen22Kolom = new boolean[D_MainActivity.pointerElemenKolom.length];
        D_MainActivity.isFirstPositif_Momen33Kolom = new boolean[D_MainActivity.pointerElemenKolom.length];

        double max = 0;
        double lengthKolom = D_MainActivity.koorNode[(D_MainActivity.Nbx + 1) * (D_MainActivity.Nby + 1)][2] -
                D_MainActivity.koorNode[0][2];
        lengthKolom = Math.abs(lengthKolom);

        double lengthDraw = lengthKolom / 3.0;
        double lengthReal = D_MainActivity.Sth * 1000; //mm
        int elemenKolom = D_MainActivity.pointerElemenKolom.length;
        int elemenStruktur = D_MainActivity.pointerElemenStruktur.length;

        //Pointer
        double[][][] IF_AxialKolom = D_MainActivity.IF_AxialKolom;
        double[][][] IF_Shear22Kolom = D_MainActivity.IF_Shear22Kolom;
        double[][][] IF_Shear33Kolom = D_MainActivity.IF_Shear33Kolom;

        double[][][] IF_TorsiKolom = D_MainActivity.IF_TorsiKolom;
        double[][][] IF_Momen22Kolom = D_MainActivity.IF_Momen22Kolom;
        double[][][] IF_Momen33Kolom = D_MainActivity.IF_Momen33Kolom;

        IF_ForcesAxialKolom= new double[elemenKolom][5];
        IF_ForcesShear22Kolom= new double[elemenKolom][5];
        IF_ForcesShear33Kolom= new double[elemenKolom][5];

        IF_ForcesTorsiKolom= new double[elemenKolom][5];
        IF_ForcesMomen22Kolom= new double[elemenKolom][5];
        IF_ForcesMomen33Kolom= new double[elemenKolom][5];

        double tmpForces;
        //Axial ===================================================================================
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][0]));
            max = Math.max(max, Math.abs(E_F[n][6]));
        }

        for(int n=0; n<elemenKolom; n++){
            double loadFrameDead = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[n]).getLoadFrameDead(); //N per mm
            double frameLoad;
            if(D_MainActivity.isFrameLoadExist[n][2]){
                frameLoad = D_MainActivity.frameLoad[n][2]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0] + E_F[n][0] / max * lengthDraw; //Done
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            //Cek IF Positif
            if(E_F[n][0] > 0){
                D_MainActivity.isFirstPositif_AxialKolom[n] = false;
            }else{
                D_MainActivity.isFirstPositif_AxialKolom[n] = true;
            }

            IF_AxialKolom[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];
            IF_AxialKolom[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];
            IF_AxialKolom[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            IF_ForcesAxialKolom[n][0] =-E_F[n][0];
            IF_ForcesAxialKolom[n][4] = E_F[n][6];
            for(int j=1; j<51; j++){
                IF_AxialKolom[n][j][0] = startX - (loadFrameDead * ((j - 1) / 49.0 * lengthReal) / max) * lengthDraw; //Done
                tmpForces =  IF_ForcesAxialKolom[n][0] - loadFrameDead * ((j - 1) / 49.0 * lengthReal);
                IF_AxialKolom[n][j][0] = IF_AxialKolom[n][j][0] + (frameLoad * ((j - 1) / 49.0 * lengthReal) / max) * lengthDraw; //Done
                tmpForces = tmpForces - frameLoad * ((j - 1) / 49.0 * lengthReal);
                IF_AxialKolom[n][j][1] = startY;
                IF_AxialKolom[n][j][2] = startZ + (j - 1) / 49.0 * lengthKolom;
                if(j==13){IF_ForcesAxialKolom[n][1]=tmpForces;}
                if(j==26){IF_ForcesAxialKolom[n][2]=tmpForces;}
                if(j==39){IF_ForcesAxialKolom[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];

            for(int j=1; j<50; j++){
                if(IF_AxialKolom[n][j][0] > koorNodeAcuan && IF_AxialKolom[n][j + 1][0] < koorNodeAcuan){
                    D_MainActivity.IF_AxialKolomNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_AxialKolom[n][j][0] < koorNodeAcuan && IF_AxialKolom[n][j + 1][0] > koorNodeAcuan){
                    D_MainActivity.IF_AxialKolomNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_AxialKolom[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][0];
            IF_AxialKolom[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][1];
            IF_AxialKolom[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/

        //Shear 22 ================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][1]));
            max = Math.max(max, Math.abs(E_F[n][7]));
        }

        for(int n=0; n<elemenKolom; n++){
            double frameLoad;

            if(D_MainActivity.isFrameLoadExist[n][1]){
                frameLoad = D_MainActivity.frameLoad[n][1]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1] + E_F[n][1] / max * lengthDraw; //Done
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            //Cek IF Positif
            if(E_F[n][1] > 0){
                D_MainActivity.isFirstPositif_Shear22Kolom[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Shear22Kolom[n] = false;
            }

            IF_Shear22Kolom[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];
            IF_Shear22Kolom[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];
            IF_Shear22Kolom[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            IF_ForcesShear22Kolom[n][0] = E_F[n][1];
            IF_ForcesShear22Kolom[n][4] =-E_F[n][7];
            for(int j=1; j<51; j++){
                IF_Shear22Kolom[n][j][0] = startX;
                IF_Shear22Kolom[n][j][1] = startY + (frameLoad * ((j - 1) / 49.0 * lengthReal) / max) * lengthDraw; //Done
                tmpForces = IF_ForcesShear22Kolom[n][0] + frameLoad * ((j - 1) / 49.0 * lengthReal);
                IF_Shear22Kolom[n][j][2] = startZ + (j - 1) / 49.0 * lengthKolom;
                if(j==13){IF_ForcesShear22Kolom[n][1]=tmpForces;}
                if(j==26){IF_ForcesShear22Kolom[n][2]=tmpForces;}
                if(j==39){IF_ForcesShear22Kolom[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];

            for(int j=1; j<50; j++){
                if(IF_Shear22Kolom[n][j][1] > koorNodeAcuan && IF_Shear22Kolom[n][j + 1][1] < koorNodeAcuan){
                    D_MainActivity.IF_Shear22KolomNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Shear22Kolom[n][j][1] < koorNodeAcuan && IF_Shear22Kolom[n][j + 1][1] > koorNodeAcuan){
                    D_MainActivity.IF_Shear22KolomNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Shear22Kolom[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][0];
            IF_Shear22Kolom[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][1];
            IF_Shear22Kolom[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/

        //Shear 33 ================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][2]));
            max = Math.max(max, Math.abs(E_F[n][8]));
        }

        for(int n=0; n<elemenKolom; n++){
            double frameLoad;

            if(D_MainActivity.isFrameLoadExist[n][0]){
                frameLoad = D_MainActivity.frameLoad[n][0]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0] - E_F[n][2] / max * lengthDraw; //Done
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            //Cek IF Positif
            if(E_F[n][2] > 0){
                D_MainActivity.isFirstPositif_Shear33Kolom[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Shear33Kolom[n] = false;
            }

            IF_Shear33Kolom[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];
            IF_Shear33Kolom[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];
            IF_Shear33Kolom[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            IF_ForcesShear33Kolom[n][0] =-E_F[n][2];
            IF_ForcesShear33Kolom[n][4] = E_F[n][8];
            for(int j=1; j<51; j++){
                IF_Shear33Kolom[n][j][0] = startX + (frameLoad * ((j - 1) / 49.0 * lengthReal) / max) * lengthDraw; //Done
                tmpForces = IF_ForcesShear33Kolom[n][0] + frameLoad * ((j - 1) / 49.0 * lengthReal);
                IF_Shear33Kolom[n][j][1] = startY;
                IF_Shear33Kolom[n][j][2] = startZ + (j - 1) / 49.0 * lengthKolom;
                if(j==13){IF_ForcesShear33Kolom[n][1]=tmpForces;}
                if(j==26){IF_ForcesShear33Kolom[n][2]=tmpForces;}
                if(j==39){IF_ForcesShear33Kolom[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];

            for(int j=1; j<50; j++){
                if(IF_Shear33Kolom[n][j][1] > koorNodeAcuan && IF_Shear33Kolom[n][j + 1][1] < koorNodeAcuan){
                    D_MainActivity.IF_Shear33KolomNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Shear33Kolom[n][j][1] < koorNodeAcuan && IF_Shear33Kolom[n][j + 1][1] > koorNodeAcuan){
                    D_MainActivity.IF_Shear33KolomNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Shear33Kolom[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][0];
            IF_Shear33Kolom[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][1];
            IF_Shear33Kolom[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/

        //Torsi ===================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][3]));
            max = Math.max(max, Math.abs(E_F[n][9]));
        }

        for(int n=0; n<elemenKolom; n++){
            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0] - E_F[n][3] / max * lengthDraw; //Done
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            //Cek IF Positif
            if(E_F[n][3] > 0){
                D_MainActivity.isFirstPositif_TorsiKolom[n] = true;
            }else{
                D_MainActivity.isFirstPositif_TorsiKolom[n] = false;
            }

            IF_TorsiKolom[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];
            IF_TorsiKolom[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];
            IF_TorsiKolom[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            IF_TorsiKolom[n][1][0] = startX;
            IF_TorsiKolom[n][1][1] = startY;
            IF_TorsiKolom[n][1][2] = startZ;

            startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][0] + E_F[n][9] / max * lengthDraw; //Done
            startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][1];
            startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][2];

            IF_TorsiKolom[n][2][0] = startX;
            IF_TorsiKolom[n][2][1] = startY;
            IF_TorsiKolom[n][2][2] = startZ;

            IF_TorsiKolom[n][3][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][0];
            IF_TorsiKolom[n][3][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][1];
            IF_TorsiKolom[n][3][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][2];

            IF_ForcesTorsiKolom[n][0] =-E_F[n][3];
            IF_ForcesTorsiKolom[n][4] = E_F[n][9];
            IF_ForcesTorsiKolom[n][1] = 0.25*IF_ForcesTorsiKolom[n][0]+0.75*IF_ForcesTorsiKolom[n][4];
            IF_ForcesTorsiKolom[n][2] = 0.50*IF_ForcesTorsiKolom[n][0]+0.50*IF_ForcesTorsiKolom[n][4];
            IF_ForcesTorsiKolom[n][3] = 0.75*IF_ForcesTorsiKolom[n][0]+0.25*IF_ForcesTorsiKolom[n][4];
        }
        /*=========================================================================================
        //=======================================================================================*/

        //Momen 22 ================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][4]));
            max = Math.max(max, Math.abs(E_F[n][10]));
        }

        for(int n=0; n<elemenKolom; n++){
            double frameLoad;
            double shear33 = E_F[n][2];
            double momenMerata;
            double momenShear;
            double L;

            if(D_MainActivity.isFrameLoadExist[n][0]){
                frameLoad = D_MainActivity.frameLoad[n][0]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0] - E_F[n][4] / max * lengthDraw; //Done
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            //Cek IF Positif
            if(E_F[n][4] > 0){
                D_MainActivity.isFirstPositif_Momen22Kolom[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Momen22Kolom[n] = false;
            }

            IF_Momen22Kolom[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];
            IF_Momen22Kolom[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];
            IF_Momen22Kolom[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            IF_ForcesMomen22Kolom[n][0]=-E_F[n][4];
            IF_ForcesMomen22Kolom[n][4]= E_F[n][10];

            for(int j=1; j<51; j++){
                L = (j - 1) / 49.0 * lengthReal;
                momenMerata = frameLoad * L * L / 2.0;
                momenShear = shear33 * L;
                IF_Momen22Kolom[n][j][0] = startX - (momenShear - momenMerata) / max  * lengthDraw; //Done
                tmpForces = IF_ForcesMomen22Kolom[n][0] - (momenShear - momenMerata);
                IF_Momen22Kolom[n][j][1] = startY;
                IF_Momen22Kolom[n][j][2] = startZ + (j - 1) / 49.0 * lengthKolom;
                if(j==13){IF_ForcesMomen22Kolom[n][1]=tmpForces;}
                if(j==26){IF_ForcesMomen22Kolom[n][2]=tmpForces;}
                if(j==39){IF_ForcesMomen22Kolom[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];

            for(int j=1; j<50; j++){
                if(IF_Momen22Kolom[n][j][0] > koorNodeAcuan && IF_Momen22Kolom[n][j + 1][0] < koorNodeAcuan){
                    D_MainActivity.IF_Momen22KolomNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Momen22Kolom[n][j][0] < koorNodeAcuan && IF_Momen22Kolom[n][j + 1][0] > koorNodeAcuan){
                    D_MainActivity.IF_Momen22KolomNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Momen22Kolom[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][0];
            IF_Momen22Kolom[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][1];
            IF_Momen22Kolom[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/

        //Momen 33 ================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][5]));
            max = Math.max(max, Math.abs(E_F[n][11]));
        }

        for(int n=0; n<elemenKolom; n++){
            double frameLoad;
            double shear33 = E_F[n][1];
            double momenMerata;
            double momenShear;
            double L;

            if(D_MainActivity.isFrameLoadExist[n][0]){
                frameLoad = D_MainActivity.frameLoad[n][1]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1] + E_F[n][5] / max * lengthDraw;
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            //Cek IF Positif
            if(E_F[n][5] > 0){
                D_MainActivity.isFirstPositif_Momen33Kolom[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Momen33Kolom[n] = false;
            }

            IF_Momen33Kolom[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][0];
            IF_Momen33Kolom[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];
            IF_Momen33Kolom[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][2];

            IF_ForcesMomen33Kolom[n][0]= E_F[n][5];
            IF_ForcesMomen33Kolom[n][4]=-E_F[n][11];

            for(int j=1; j<51; j++){
                L = (j - 1) / 49.0 * lengthReal;
                momenMerata = frameLoad * L * L / 2.0;
                momenShear = shear33 * L;
                IF_Momen33Kolom[n][j][0] = startX;
                IF_Momen33Kolom[n][j][1] = startY - (momenShear + momenMerata) / max  * lengthDraw; //Done
                tmpForces = IF_ForcesMomen33Kolom[n][0] - (momenShear + momenMerata);
                IF_Momen33Kolom[n][j][2] = startZ + (j - 1) / 49.0 * lengthKolom;
                if(j==13){IF_ForcesMomen33Kolom[n][1]=tmpForces;}
                if(j==26){IF_ForcesMomen33Kolom[n][2]=tmpForces;}
                if(j==39){IF_ForcesMomen33Kolom[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][0]][1];

            for(int j=1; j<50; j++){
                if(IF_Momen33Kolom[n][j][1] > koorNodeAcuan && IF_Momen33Kolom[n][j + 1][1] < koorNodeAcuan){
                    D_MainActivity.IF_Momen33KolomNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Momen33Kolom[n][j][1] < koorNodeAcuan && IF_Momen33Kolom[n][j + 1][1] > koorNodeAcuan){
                    D_MainActivity.IF_Momen33KolomNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Momen33Kolom[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][0];
            IF_Momen33Kolom[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][1];
            IF_Momen33Kolom[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenKolom[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/
    }

    public void setKoorIF_BalokX(){
        D_MainActivity.IF_AxialBalokX = new double[D_MainActivity.pointerElemenBalokXZ.length][52][3];
        D_MainActivity.IF_Shear22BalokX = new double[D_MainActivity.pointerElemenBalokXZ.length][52][3];
        D_MainActivity.IF_Shear33BalokX = new double[D_MainActivity.pointerElemenBalokXZ.length][52][3];

        D_MainActivity.IF_TorsiBalokX = new double[D_MainActivity.pointerElemenBalokXZ.length][4][3];
        D_MainActivity.IF_Momen22BalokX = new double[D_MainActivity.pointerElemenBalokXZ.length][52][3];
        D_MainActivity.IF_Momen33BalokX = new double[D_MainActivity.pointerElemenBalokXZ.length][52][3];

        //************************************************

        D_MainActivity.IF_AxialBalokXNodal = new int[D_MainActivity.pointerElemenBalokXZ.length][5];
        D_MainActivity.IF_Shear22BalokXNodal = new int[D_MainActivity.pointerElemenBalokXZ.length][5];
        D_MainActivity.IF_Shear33BalokXNodal = new int[D_MainActivity.pointerElemenBalokXZ.length][5];

        D_MainActivity.IF_Momen22BalokXNodal = new int[D_MainActivity.pointerElemenBalokXZ.length][5];
        D_MainActivity.IF_Momen33BalokXNodal = new int[D_MainActivity.pointerElemenBalokXZ.length][5];

        //************************************************

        D_MainActivity.isFirstPositif_AxialBalokX = new boolean[D_MainActivity.pointerElemenBalokXZ.length];
        D_MainActivity.isFirstPositif_Shear22BalokX = new boolean[D_MainActivity.pointerElemenBalokXZ.length];
        D_MainActivity.isFirstPositif_Shear33BalokX = new boolean[D_MainActivity.pointerElemenBalokXZ.length];

        D_MainActivity.isFirstPositif_TorsiBalokX = new boolean[D_MainActivity.pointerElemenBalokXZ.length];
        D_MainActivity.isFirstPositif_Momen22BalokX = new boolean[D_MainActivity.pointerElemenBalokXZ.length];
        D_MainActivity.isFirstPositif_Momen33BalokX = new boolean[D_MainActivity.pointerElemenBalokXZ.length];

        double max;
        double lengthBalokX = D_MainActivity.koorNode[1][0] -
                D_MainActivity.koorNode[0][0];
        lengthBalokX = Math.abs(lengthBalokX);

        double lengthBalokY = D_MainActivity.koorNode[D_MainActivity.Nbx + 1][1] -
                D_MainActivity.koorNode[0][1];
        lengthBalokY = Math.abs(lengthBalokY);

        double lengthKolom = D_MainActivity.koorNode[(D_MainActivity.Nbx + 1) * (D_MainActivity.Nby + 1)][2] -
                D_MainActivity.koorNode[0][2];
        lengthKolom = Math.abs(lengthKolom);

        double lengthDraw = lengthKolom / 3.0;
        double lengthReal = D_MainActivity.Bwx * 1000; //mm
        int elemenKolom = D_MainActivity.pointerElemenKolom.length;
        int elemenBalokX = D_MainActivity.pointerElemenBalokXZ.length;
        int elemenStruktur = D_MainActivity.pointerElemenStruktur.length;

        double BW_X = D_MainActivity.Bwx * 1000; //mm
        double BW_Y = D_MainActivity.Bwy * 1000; //mm


        double MinBalok = Math.min(BW_X, BW_Y); //mm
        double a = MinBalok / 2; //mm

        double MinBalokDraw = Math.min(lengthBalokX, lengthBalokY);
        double aDraw = MinBalokDraw / 2;

        double SisiAtasTrapesiumX = BW_X - MinBalok; //mm

        double SisiAtasTraXDraw = lengthBalokX - MinBalokDraw;

        //Pointer
        double[][][] IF_AxialBalokX = D_MainActivity.IF_AxialBalokX;
        double[][][] IF_Shear22BalokX = D_MainActivity.IF_Shear22BalokX;
        double[][][] IF_Shear33BalokX = D_MainActivity.IF_Shear33BalokX;

        double[][][] IF_TorsiBalokX = D_MainActivity.IF_TorsiBalokX;
        double[][][] IF_Momen22BalokX = D_MainActivity.IF_Momen22BalokX;
        double[][][] IF_Momen33BalokX = D_MainActivity.IF_Momen33BalokX;

        IF_ForcesAxialBalokX= new double[elemenBalokX][5];
        IF_ForcesShear22BalokX= new double[elemenBalokX][5];
        IF_ForcesShear33BalokX= new double[elemenBalokX][5];

        IF_ForcesTorsiBalokX= new double[elemenBalokX][5];
        IF_ForcesMomen22BalokX= new double[elemenBalokX][5];
        IF_ForcesMomen33BalokX= new double[elemenBalokX][5];

        double tmpForces;
        //Axial ===================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][0]));
            max = Math.max(max, Math.abs(E_F[n][6]));
        }

        for(int n=0; n<elemenBalokX; n++){
            double frameLoad;

            if(D_MainActivity.isFrameLoadExist[n + elemenKolom][0]){
                frameLoad = D_MainActivity.frameLoad[n + elemenKolom][0]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2] - E_F[n + elemenKolom][0] / max * lengthDraw; //Done

            //Cek IF Positif
            if(E_F[n + elemenKolom][0] > 0){
                D_MainActivity.isFirstPositif_AxialBalokX[n] = false;
            }else{
                D_MainActivity.isFirstPositif_AxialBalokX[n] = true;
            }

            IF_AxialBalokX[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            IF_AxialBalokX[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];
            IF_AxialBalokX[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            IF_ForcesAxialBalokX[n][0] = E_F[n + elemenKolom][0];
            IF_ForcesAxialBalokX[n][4] =-E_F[n + elemenKolom][6];
            for(int j=1; j<51; j++){
                IF_AxialBalokX[n][j][0] = startX + (j - 1) / 49.0 * lengthBalokX;
                IF_AxialBalokX[n][j][1] = startY;
                IF_AxialBalokX[n][j][2] = startZ - (frameLoad * ((j - 1) / 49.0 * lengthReal) / max) * lengthDraw; //Done
                tmpForces = IF_ForcesAxialBalokX[n][0] + frameLoad * ((j - 1) / 49.0 * lengthReal);
                if(j==13){IF_ForcesAxialBalokX[n][1]=tmpForces;}
                if(j==26){IF_ForcesAxialBalokX[n][2]=tmpForces;}
                if(j==39){IF_ForcesAxialBalokX[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            for(int j=1; j<50; j++){
                if(IF_AxialBalokX[n][j][2] > koorNodeAcuan && IF_AxialBalokX[n][j + 1][2] < koorNodeAcuan){
                    D_MainActivity.IF_AxialBalokXNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_AxialBalokX[n][j][2] < koorNodeAcuan && IF_AxialBalokX[n][j + 1][2] > koorNodeAcuan){
                    D_MainActivity.IF_AxialBalokXNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_AxialBalokX[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][0];
            IF_AxialBalokX[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][1];
            IF_AxialBalokX[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/



        //Shear 22 ================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][1]));
            max = Math.max(max, Math.abs(E_F[n][7]));
        }

        for(int n=0; n<elemenBalokX; n++){
            double frameLoad;
            if(D_MainActivity.isFrameLoadExist[n + elemenKolom][1]){
                frameLoad = D_MainActivity.frameLoad[n + elemenKolom][1]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1] + E_F[n + elemenKolom][1] / max * lengthDraw; //Done
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            //Cek IF Positif
            if(E_F[n + elemenKolom][1] > 0){
                D_MainActivity.isFirstPositif_Shear22BalokX[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Shear22BalokX[n] = false;
            }

            IF_Shear22BalokX[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            IF_Shear22BalokX[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];
            IF_Shear22BalokX[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            IF_ForcesShear22BalokX[n][0] = E_F[n + elemenKolom][1];
            IF_ForcesShear22BalokX[n][4] =-E_F[n + elemenKolom][7];
            for(int j=1; j<51; j++){
                IF_Shear22BalokX[n][j][0] = startX + (j - 1) / 49.0 * lengthBalokX;
                IF_Shear22BalokX[n][j][1] = startY + (frameLoad * ((j - 1) / 49.0 * lengthReal) / max) * lengthDraw; //Done
                tmpForces = IF_ForcesShear22BalokX[n][0] + frameLoad * ((j - 1) / 49.0 * lengthReal);
                IF_Shear22BalokX[n][j][2] = startZ;
                if(j==13){IF_ForcesShear22BalokX[n][1]=tmpForces;}
                if(j==26){IF_ForcesShear22BalokX[n][2]=tmpForces;}
                if(j==39){IF_ForcesShear22BalokX[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];

            for(int j=1; j<50; j++){
                if(IF_Shear22BalokX[n][j][1] > koorNodeAcuan && IF_Shear22BalokX[n][j + 1][1] < koorNodeAcuan){
                    D_MainActivity.IF_Shear22BalokXNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Shear22BalokX[n][j][1] < koorNodeAcuan && IF_Shear22BalokX[n][j + 1][1] > koorNodeAcuan){
                    D_MainActivity.IF_Shear22BalokXNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Shear22BalokX[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][0];
            IF_Shear22BalokX[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][1];
            IF_Shear22BalokX[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/



        //Shear 33 ================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][2]));
            max = Math.max(max, Math.abs(E_F[n][8]));
        }

        for(int n=0; n<elemenBalokX; n++){
            double loadFrameDead = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[n + elemenKolom]).getLoadFrameDead(); //N per mm
            double frameLoad;
            if(D_MainActivity.isFrameLoadExist[n + elemenKolom][2]){
                frameLoad = D_MainActivity.frameLoad[n + elemenKolom][2]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2] + E_F[n + elemenKolom][2] / max * lengthDraw; //Done

            //Cek IF Positif
            if(E_F[n + elemenKolom][2] > 0){
                D_MainActivity.isFirstPositif_Shear33BalokX[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Shear33BalokX[n] = false;
            }

            IF_Shear33BalokX[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            IF_Shear33BalokX[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];
            IF_Shear33BalokX[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            IF_ForcesShear33BalokX[n][0] = E_F[n + elemenKolom][2];
            IF_ForcesShear33BalokX[n][4] =-E_F[n + elemenKolom][8];

            for(int j=1; j<51; j++){
                IF_Shear33BalokX[n][j][0] = startX + (j - 1) / 49.0 * lengthBalokX;
                IF_Shear33BalokX[n][j][1] = startY;
                IF_Shear33BalokX[n][j][2] = startZ + ((frameLoad - loadFrameDead)* ((j - 1) / 49.0 * lengthReal) / max) * lengthDraw; //Done
                tmpForces = IF_ForcesShear33BalokX[n][0] + (frameLoad - loadFrameDead)* ((j - 1) / 49.0 * lengthReal);

                double LDraw = (j - 1) / 49.0 * lengthBalokX;
                double LReal = (j - 1) / 49.0 * lengthReal;
                if(LDraw < aDraw){
                    double YInterpolasi = AreaLoadBeam[n] / a * LReal;
                    IF_Shear33BalokX[n][j][2] = IF_Shear33BalokX[n][j][2] - (0.5 * LReal * YInterpolasi) / max * lengthDraw;
                    tmpForces = tmpForces - (0.5 * LReal * YInterpolasi);
                }else if(LDraw < aDraw + SisiAtasTraXDraw){
                    IF_Shear33BalokX[n][j][2] = IF_Shear33BalokX[n][j][2] - (0.5 * a * AreaLoadBeam[n] + (LReal - a) * AreaLoadBeam[n]) / max * lengthDraw;
                    tmpForces = tmpForces - (0.5 * a * AreaLoadBeam[n] + (LReal - a) * AreaLoadBeam[n]);
                }else{
                    IF_Shear33BalokX[n][j][2] = IF_Shear33BalokX[n][j][2] - (0.5 * a * AreaLoadBeam[n] + SisiAtasTrapesiumX * AreaLoadBeam[n]) / max * lengthDraw;
                    tmpForces = tmpForces - (0.5 * a * AreaLoadBeam[n] + SisiAtasTrapesiumX * AreaLoadBeam[n]);
                    double ASegitigaTotal = 0.5 * a * AreaLoadBeam[n];
                    double aParsial = a - (LReal - a - SisiAtasTrapesiumX);
                    double YInterpolasiParsial = AreaLoadBeam[n] / a * aParsial;
                    double ASegitigaParsial = 0.5 * aParsial * YInterpolasiParsial;
                    IF_Shear33BalokX[n][j][2] = IF_Shear33BalokX[n][j][2] - (ASegitigaTotal - ASegitigaParsial) / max * lengthDraw;
                    tmpForces = tmpForces - (ASegitigaTotal - ASegitigaParsial);
                }
                if(j==13){IF_ForcesShear33BalokX[n][1]=tmpForces;}
                if(j==26){IF_ForcesShear33BalokX[n][2]=tmpForces;}
                if(j==39){IF_ForcesShear33BalokX[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            for(int j=1; j<50; j++){
                if(IF_Shear33BalokX[n][j][2] > koorNodeAcuan && IF_Shear33BalokX[n][j + 1][2] < koorNodeAcuan){
                    D_MainActivity.IF_Shear33BalokXNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Shear33BalokX[n][j][2] < koorNodeAcuan && IF_Shear33BalokX[n][j + 1][2] > koorNodeAcuan){
                    D_MainActivity.IF_Shear33BalokXNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Shear33BalokX[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][0];
            IF_Shear33BalokX[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][1];
            IF_Shear33BalokX[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/



        //Torsi ===================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][3]));
            max = Math.max(max, Math.abs(E_F[n][9]));
        }

        for(int n=0; n<elemenBalokX; n++){
            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2] + E_F[n + elemenKolom][3] / max * lengthDraw; //Done

            //Cek IF Positif
            if(E_F[n + elemenKolom][3] > 0){
                D_MainActivity.isFirstPositif_TorsiBalokX[n] = true;
            }else{
                D_MainActivity.isFirstPositif_TorsiBalokX[n] = false;
            }

            IF_TorsiBalokX[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            IF_TorsiBalokX[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];
            IF_TorsiBalokX[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            IF_TorsiBalokX[n][1][0] = startX;
            IF_TorsiBalokX[n][1][1] = startY;
            IF_TorsiBalokX[n][1][2] = startZ;

            startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][0];
            startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][1];
            startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][2] - E_F[n + elemenKolom][9] / max * lengthDraw; //Done

            IF_TorsiBalokX[n][2][0] = startX;
            IF_TorsiBalokX[n][2][1] = startY;
            IF_TorsiBalokX[n][2][2] = startZ;

            IF_TorsiBalokX[n][3][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][0];
            IF_TorsiBalokX[n][3][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][1];
            IF_TorsiBalokX[n][3][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][2];

            IF_ForcesTorsiBalokX[n][0] = E_F[n + elemenKolom][3];
            IF_ForcesTorsiBalokX[n][4] =-E_F[n + elemenKolom][9];
            IF_ForcesTorsiBalokX[n][1] = 0.25*IF_ForcesTorsiBalokX[n][0]+0.75*IF_ForcesTorsiBalokX[n][4];
            IF_ForcesTorsiBalokX[n][2] = 0.50*IF_ForcesTorsiBalokX[n][0]+0.50*IF_ForcesTorsiBalokX[n][4];
            IF_ForcesTorsiBalokX[n][3] = 0.75*IF_ForcesTorsiBalokX[n][0]+0.25*IF_ForcesTorsiBalokX[n][4];
        }
        /*=========================================================================================
        //=======================================================================================*/



        //Momen 22 ================================================================================ //Done
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][4]));
            max = Math.max(max, Math.abs(E_F[n][10]));
        }

        for(int n=0; n<elemenBalokX; n++){
            double loadFrameDead = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[n + elemenKolom]).getLoadFrameDead(); //N per mm
            double frameLoad;
            double shear33 = E_F[n + elemenKolom][2];

            if(D_MainActivity.isFrameLoadExist[n + elemenKolom][2]){
                frameLoad = D_MainActivity.frameLoad[n + elemenKolom][2]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2] + E_F[n + elemenKolom][4] / max * lengthDraw;

            //Cek IF Positif
            if(E_F[n + elemenKolom][4] > 0){
                D_MainActivity.isFirstPositif_Momen22BalokX[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Momen22BalokX[n] = false;
            }

            IF_Momen22BalokX[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            IF_Momen22BalokX[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];
            IF_Momen22BalokX[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            IF_ForcesMomen22BalokX[n][0]= E_F[n + elemenKolom][4];
            IF_ForcesMomen22BalokX[n][4]=-E_F[n + elemenKolom][10];

            for(int j=1; j<51; j++){
                double LDraw = (j - 1) / 49.0 * lengthBalokX;
                double LReal = (j - 1) / 49.0 * lengthReal;
                double momenMerata = (frameLoad - loadFrameDead) * LReal * LReal / 2;
                double momenShear = shear33 * LReal;

                IF_Momen22BalokX[n][j][0] = startX + LDraw;
                IF_Momen22BalokX[n][j][1] = startY;
                IF_Momen22BalokX[n][j][2] = startZ + (momenMerata + momenShear) / max * lengthDraw;
                tmpForces = IF_ForcesMomen22BalokX[n][0] + (momenMerata + momenShear);

                if(LDraw < aDraw){
                    double YInterpolasi = AreaLoadBeam[n] / a * LReal;
                    double momenSegitigaParsial = (0.5 * LReal * YInterpolasi) * LReal / 3;

                    IF_Momen22BalokX[n][j][2] = IF_Momen22BalokX[n][j][2] - momenSegitigaParsial / max * lengthDraw;
                    tmpForces = tmpForces - momenSegitigaParsial;

                }else if(LDraw < aDraw + SisiAtasTraXDraw){
                    double momenSegitiga = (0.5 * a * AreaLoadBeam[n]) * (LReal - a + a / 3);
                    double momenPersegiParsial = (LReal - a) * AreaLoadBeam[n] * (LReal - a) / 2;

                    IF_Momen22BalokX[n][j][2] = IF_Momen22BalokX[n][j][2] - (momenSegitiga + momenPersegiParsial) / max * lengthDraw;
                    tmpForces = tmpForces - (momenSegitiga + momenPersegiParsial);

                }else{
                    double momenSegitiga = (0.5 * a * AreaLoadBeam[n]) * (LReal - a + a / 3);
                    double momenPersegi = SisiAtasTrapesiumX * AreaLoadBeam[n] * (LReal - a - SisiAtasTrapesiumX / 2);

                    IF_Momen22BalokX[n][j][2] = IF_Momen22BalokX[n][j][2] - (momenSegitiga + momenPersegi) / max * lengthDraw;
                    tmpForces = tmpForces - (momenSegitiga + momenPersegi);

                    double ASegitigaTotal = 0.5 * a * AreaLoadBeam[n];
                    double aRealKanan = LReal - a - SisiAtasTrapesiumX;
                    double aParsial = a - aRealKanan;
                    double YInterpolasiParsial = AreaLoadBeam[n] / a * aParsial;
                    double ASegitigaParsial = 0.5 * aParsial * YInterpolasiParsial;

                    double APersegiRealBawah = aRealKanan * YInterpolasiParsial;
                    double ASegitigaRealAtas = (ASegitigaTotal - ASegitigaParsial) - APersegiRealBawah;

                    double momenSegitigaRealAtas = ASegitigaRealAtas * aRealKanan * (2.0 / 3.0);
                    double momenPersegiRealBawah = APersegiRealBawah * aRealKanan / 2;

                    IF_Momen22BalokX[n][j][2] = IF_Momen22BalokX[n][j][2] - (momenSegitigaRealAtas + momenPersegiRealBawah) / max * lengthDraw;
                    tmpForces = tmpForces - (momenSegitigaRealAtas + momenPersegiRealBawah);
                }
                if(j==13){IF_ForcesMomen22BalokX[n][1]=tmpForces;}
                if(j==26){IF_ForcesMomen22BalokX[n][2]=tmpForces;}
                if(j==39){IF_ForcesMomen22BalokX[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            for(int j=1; j<50; j++){
                if(IF_Momen22BalokX[n][j][2] > koorNodeAcuan && IF_Momen22BalokX[n][j + 1][2] < koorNodeAcuan){
                    D_MainActivity.IF_Momen22BalokXNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Momen22BalokX[n][j][2] < koorNodeAcuan && IF_Momen22BalokX[n][j + 1][2] > koorNodeAcuan){
                    D_MainActivity.IF_Momen22BalokXNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Momen22BalokX[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][0];
            IF_Momen22BalokX[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][1];
            IF_Momen22BalokX[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/



        //Momen 33 ================================================================================ //Done
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][5]));
            max = Math.max(max, Math.abs(E_F[n][11]));
        }

        for(int n=0; n<elemenBalokX; n++){
            double frameLoad;
            double shear22 = E_F[n + elemenKolom][1];

            if(D_MainActivity.isFrameLoadExist[n + elemenKolom][1]){
                frameLoad = D_MainActivity.frameLoad[n + elemenKolom][1]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1] + E_F[n + elemenKolom][5] / max * lengthDraw;
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            //Cek IF Positif
            if(E_F[n + elemenKolom][5] > 0){
                D_MainActivity.isFirstPositif_Momen33BalokX[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Momen33BalokX[n] = false;
            }

            IF_Momen33BalokX[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][0];
            IF_Momen33BalokX[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];
            IF_Momen33BalokX[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][2];

            IF_ForcesMomen33BalokX[n][0]= E_F[n + elemenKolom][5];
            IF_ForcesMomen33BalokX[n][4]=-E_F[n + elemenKolom][11];

            for(int j=1; j<51; j++){
                double LDraw = (j - 1) / 49.0 * lengthBalokX;
                double LReal = (j - 1) / 49.0 * lengthReal;
                double momenMerata = frameLoad * LReal * LReal / 2;
                double momenShear = shear22 * LReal;

                IF_Momen33BalokX[n][j][0] = startX + LDraw;
                IF_Momen33BalokX[n][j][1] = startY - (momenMerata + momenShear) / max * lengthDraw;
                tmpForces=IF_ForcesMomen33BalokX[n][0] - (momenMerata + momenShear);
                IF_Momen33BalokX[n][j][2] = startZ;
                if(j==13){IF_ForcesMomen33BalokX[n][1]=tmpForces;}
                if(j==26){IF_ForcesMomen33BalokX[n][2]=tmpForces;}
                if(j==39){IF_ForcesMomen33BalokX[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][0]][1];

            for(int j=1; j<50; j++){
                if(IF_Momen33BalokX[n][j][1] > koorNodeAcuan && IF_Momen33BalokX[n][j + 1][1] < koorNodeAcuan){
                    D_MainActivity.IF_Momen33BalokXNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Momen33BalokX[n][j][1] < koorNodeAcuan && IF_Momen33BalokX[n][j + 1][1] > koorNodeAcuan){
                    D_MainActivity.IF_Momen33BalokXNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Momen33BalokX[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][0];
            IF_Momen33BalokX[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][1];
            IF_Momen33BalokX[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokXZ[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/
    }

    public void setKoorIF_BalokY(){
        D_MainActivity.IF_AxialBalokY = new double[D_MainActivity.pointerElemenBalokYZ.length][52][3];
        D_MainActivity.IF_Shear22BalokY = new double[D_MainActivity.pointerElemenBalokYZ.length][52][3];
        D_MainActivity.IF_Shear33BalokY = new double[D_MainActivity.pointerElemenBalokYZ.length][52][3];

        D_MainActivity.IF_TorsiBalokY = new double[D_MainActivity.pointerElemenBalokYZ.length][4][3];
        D_MainActivity.IF_Momen22BalokY = new double[D_MainActivity.pointerElemenBalokYZ.length][52][3];
        D_MainActivity.IF_Momen33BalokY = new double[D_MainActivity.pointerElemenBalokYZ.length][52][3];

        //************************************************

        D_MainActivity.IF_AxialBalokYNodal = new int[D_MainActivity.pointerElemenBalokYZ.length][5];
        D_MainActivity.IF_Shear22BalokYNodal = new int[D_MainActivity.pointerElemenBalokYZ.length][5];
        D_MainActivity.IF_Shear33BalokYNodal = new int[D_MainActivity.pointerElemenBalokYZ.length][5];

        D_MainActivity.IF_Momen22BalokYNodal = new int[D_MainActivity.pointerElemenBalokYZ.length][5];
        D_MainActivity.IF_Momen33BalokYNodal = new int[D_MainActivity.pointerElemenBalokYZ.length][5];

        //************************************************

        D_MainActivity.isFirstPositif_AxialBalokY = new boolean[D_MainActivity.pointerElemenBalokYZ.length];
        D_MainActivity.isFirstPositif_Shear22BalokY = new boolean[D_MainActivity.pointerElemenBalokYZ.length];
        D_MainActivity.isFirstPositif_Shear33BalokY = new boolean[D_MainActivity.pointerElemenBalokYZ.length];

        D_MainActivity.isFirstPositif_TorsiBalokY = new boolean[D_MainActivity.pointerElemenBalokYZ.length];
        D_MainActivity.isFirstPositif_Momen22BalokY = new boolean[D_MainActivity.pointerElemenBalokYZ.length];
        D_MainActivity.isFirstPositif_Momen33BalokY = new boolean[D_MainActivity.pointerElemenBalokYZ.length];

        double max;
        double lengthBalokX = D_MainActivity.koorNode[1][0] -
                D_MainActivity.koorNode[0][0];
        lengthBalokX = Math.abs(lengthBalokX);

        double lengthBalokY = D_MainActivity.koorNode[D_MainActivity.Nbx + 1][1] -
                D_MainActivity.koorNode[0][1];
        lengthBalokY = Math.abs(lengthBalokY);

        double lengthKolom = D_MainActivity.koorNode[(D_MainActivity.Nbx + 1) * (D_MainActivity.Nby + 1)][2] -
                D_MainActivity.koorNode[0][2];
        lengthKolom = Math.abs(lengthKolom);

        double lengthDraw = lengthKolom / 3.0;
        double lengthReal = D_MainActivity.Bwy * 1000; //mm
        int elemenKolom = D_MainActivity.pointerElemenKolom.length;
        int elemenBalokX = D_MainActivity.pointerElemenBalokXZ.length;
        int elemenBalokY = D_MainActivity.pointerElemenBalokYZ.length;
        int elemenStruktur = D_MainActivity.pointerElemenStruktur.length;

        double BW_X = D_MainActivity.Bwx * 1000; //mm
        double BW_Y = D_MainActivity.Bwy * 1000; //mm

        double MinBalok = Math.min(BW_X, BW_Y); //mm
        double a = MinBalok / 2; //mm

        double MinBalokDraw = Math.min(lengthBalokX, lengthBalokY);
        double aDraw = MinBalokDraw / 2;

        double SisiAtasTrapesiumY = BW_Y - MinBalok; //mm

        double SisiAtasTraYDraw = lengthBalokY - MinBalokDraw;

        //Pointer
        double[][][] IF_AxialBalokY = D_MainActivity.IF_AxialBalokY;
        double[][][] IF_Shear22BalokY = D_MainActivity.IF_Shear22BalokY;
        double[][][] IF_Shear33BalokY = D_MainActivity.IF_Shear33BalokY;

        double[][][] IF_TorsiBalokY = D_MainActivity.IF_TorsiBalokY;
        double[][][] IF_Momen22BalokY = D_MainActivity.IF_Momen22BalokY;
        double[][][] IF_Momen33BalokY = D_MainActivity.IF_Momen33BalokY;

        IF_ForcesAxialBalokY= new double[elemenBalokY][5];
        IF_ForcesShear22BalokY= new double[elemenBalokY][5];
        IF_ForcesShear33BalokY= new double[elemenBalokY][5];

        IF_ForcesTorsiBalokY= new double[elemenBalokY][5];
        IF_ForcesMomen22BalokY= new double[elemenBalokY][5];
        IF_ForcesMomen33BalokY= new double[elemenBalokY][5];

        double tmpForces;

        //Axial =================================================================================== //Done
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][0]));
            max = Math.max(max, Math.abs(E_F[n][6]));
        }

        for(int n=0; n<elemenBalokY; n++){
            double frameLoad;

            if(D_MainActivity.isFrameLoadExist[n + elemenKolom + elemenBalokX][1]){
                frameLoad = D_MainActivity.frameLoad[n + elemenKolom + elemenBalokX][1]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2] - E_F[n + elemenKolom + elemenBalokX][0] / max * lengthDraw;

            //Cek IF Positif
            if(E_F[n + elemenKolom + elemenBalokX][0] > 0){
                D_MainActivity.isFirstPositif_AxialBalokY[n] = false;
            }else{
                D_MainActivity.isFirstPositif_AxialBalokY[n] = true;
            }

            IF_AxialBalokY[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];
            IF_AxialBalokY[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            IF_AxialBalokY[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            IF_ForcesAxialBalokY[n][0] = E_F[n + elemenKolom + elemenBalokX][0];
            IF_ForcesAxialBalokY[n][4] =-E_F[n + elemenKolom + elemenBalokX][6];
            for(int j=1; j<51; j++){
                IF_AxialBalokY[n][j][0] = startX;
                IF_AxialBalokY[n][j][1] = startY + (j - 1) / 49.0 * lengthBalokY;
                IF_AxialBalokY[n][j][2] = startZ - (frameLoad * ((j - 1) / 49.0 * lengthReal) / max) * lengthDraw;
                tmpForces = IF_ForcesAxialBalokY[n][0] + frameLoad * ((j - 1) / 49.0 * lengthReal);
                if(j==13){IF_ForcesAxialBalokY[n][1]=tmpForces;}
                if(j==26){IF_ForcesAxialBalokY[n][2]=tmpForces;}
                if(j==39){IF_ForcesAxialBalokY[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            for(int j=1; j<50; j++){
                if(IF_AxialBalokY[n][j][2] > koorNodeAcuan && IF_AxialBalokY[n][j + 1][2] < koorNodeAcuan){
                    D_MainActivity.IF_AxialBalokYNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_AxialBalokY[n][j][2] < koorNodeAcuan && IF_AxialBalokY[n][j + 1][2] > koorNodeAcuan){
                    D_MainActivity.IF_AxialBalokYNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_AxialBalokY[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][0];
            IF_AxialBalokY[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][1];
            IF_AxialBalokY[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/



        //Shear 22 ================================================================================ //Done
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][1]));
            max = Math.max(max, Math.abs(E_F[n][7]));
        }

        for(int n=0; n<elemenBalokY; n++){
            double frameLoad;
            if(D_MainActivity.isFrameLoadExist[n + elemenKolom + elemenBalokX][0]){
                frameLoad = D_MainActivity.frameLoad[n + elemenKolom + elemenBalokX][0]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0] - E_F[n + elemenKolom + elemenBalokX][1] / max * lengthDraw;
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            //Cek IF Positif
            if(E_F[n + elemenKolom + elemenBalokX][1] > 0){
                D_MainActivity.isFirstPositif_Shear22BalokY[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Shear22BalokY[n] = false;
            }

            IF_Shear22BalokY[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];
            IF_Shear22BalokY[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            IF_Shear22BalokY[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            IF_ForcesShear22BalokY[n][0] =-E_F[n + elemenKolom + elemenBalokX][1];
            IF_ForcesShear22BalokY[n][4] = E_F[n + elemenKolom + elemenBalokX][7];
            for(int j=1; j<51; j++){
                IF_Shear22BalokY[n][j][0] = startX + (frameLoad * ((j - 1) / 49.0 * lengthReal) / max) * lengthDraw;
                tmpForces = IF_ForcesShear22BalokY[n][0] + frameLoad * ((j - 1) / 49.0 * lengthReal);
                IF_Shear22BalokY[n][j][1] = startY + (j - 1) / 49.0 * lengthBalokY;
                IF_Shear22BalokY[n][j][2] = startZ;
                if(j==13){IF_ForcesShear22BalokY[n][1]=tmpForces;}
                if(j==26){IF_ForcesShear22BalokY[n][2]=tmpForces;}
                if(j==39){IF_ForcesShear22BalokY[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];

            for(int j=1; j<50; j++){
                if(IF_Shear22BalokY[n][j][0] > koorNodeAcuan && IF_Shear22BalokY[n][j + 1][0] < koorNodeAcuan){
                    D_MainActivity.IF_Shear22BalokYNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Shear22BalokY[n][j][0] < koorNodeAcuan && IF_Shear22BalokY[n][j + 1][0] > koorNodeAcuan){
                    D_MainActivity.IF_Shear22BalokYNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Shear22BalokY[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][0];
            IF_Shear22BalokY[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][1];
            IF_Shear22BalokY[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/



        //Shear 33 ================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][2]));
            max = Math.max(max, Math.abs(E_F[n][8]));
        }

        for(int n=0; n<elemenBalokY; n++){
            double loadFrameDead = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[n + elemenKolom + elemenBalokX]).getLoadFrameDead(); //N per mm
            double frameLoad;
            if(D_MainActivity.isFrameLoadExist[n + elemenKolom + elemenBalokX][2]){
                frameLoad = D_MainActivity.frameLoad[n + elemenKolom + elemenBalokX][2]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2] + E_F[n + elemenKolom + elemenBalokX][2] / max * lengthDraw;

            //Cek IF Positif
            if(E_F[n + elemenKolom + elemenBalokX][2] > 0){
                D_MainActivity.isFirstPositif_Shear33BalokY[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Shear33BalokY[n] = false;
            }

            IF_Shear33BalokY[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];
            IF_Shear33BalokY[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            IF_Shear33BalokY[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            IF_ForcesShear33BalokY[n][0] = E_F[n + elemenKolom + elemenBalokX][2];
            IF_ForcesShear33BalokY[n][4] =-E_F[n + elemenKolom + elemenBalokX][8];

            for(int j=1; j<51; j++){
                IF_Shear33BalokY[n][j][0] = startX;
                IF_Shear33BalokY[n][j][1] = startY + (j - 1) / 49.0 * lengthBalokY;
                IF_Shear33BalokY[n][j][2] = startZ + ((frameLoad - loadFrameDead)* ((j - 1) / 49.0 * lengthReal) / max) * lengthDraw;
                tmpForces = IF_ForcesShear33BalokY[n][0] + (frameLoad - loadFrameDead)* ((j - 1) / 49.0 * lengthReal);

                double LDraw = (j - 1) / 49.0 * lengthBalokY;
                double LReal = (j - 1) / 49.0 * lengthReal;
                if(LDraw < aDraw){
                    double YInterpolasi = AreaLoadBeam[n + elemenBalokX] / a * LReal;
                    IF_Shear33BalokY[n][j][2] = IF_Shear33BalokY[n][j][2] - (0.5 * LReal * YInterpolasi) / max * lengthDraw;
                    tmpForces = tmpForces - (0.5 * LReal * YInterpolasi);
                }else if(LDraw < aDraw + SisiAtasTraYDraw){
                    IF_Shear33BalokY[n][j][2] = IF_Shear33BalokY[n][j][2] - (0.5 * a * AreaLoadBeam[n + elemenBalokX] + (LReal - a) * AreaLoadBeam[n + elemenBalokX]) / max * lengthDraw;
                    tmpForces = tmpForces - (0.5 * a * AreaLoadBeam[n + elemenBalokX] + (LReal - a) * AreaLoadBeam[n + elemenBalokX]);
                }else{
                    IF_Shear33BalokY[n][j][2] = IF_Shear33BalokY[n][j][2] - (0.5 * a * AreaLoadBeam[n + elemenBalokX] + SisiAtasTrapesiumY * AreaLoadBeam[n + elemenBalokX]) / max * lengthDraw;
                    tmpForces = tmpForces - (0.5 * a * AreaLoadBeam[n + elemenBalokX] + SisiAtasTrapesiumY * AreaLoadBeam[n + elemenBalokX]);
                    double ASegitigaTotal = 0.5 * a * AreaLoadBeam[n + elemenBalokX];
                    double aParsial = a - (LReal - a - SisiAtasTrapesiumY);
                    double YInterpolasiParsial = AreaLoadBeam[n + elemenBalokX] / a * aParsial;
                    double ASegitigaParsial = 0.5 * aParsial * YInterpolasiParsial;
                    IF_Shear33BalokY[n][j][2] = IF_Shear33BalokY[n][j][2] - (ASegitigaTotal - ASegitigaParsial) / max * lengthDraw;
                    tmpForces = tmpForces - (ASegitigaTotal - ASegitigaParsial);
                }
                if(j==13){IF_ForcesShear33BalokY[n][1]=tmpForces;}
                if(j==26){IF_ForcesShear33BalokY[n][2]=tmpForces;}
                if(j==39){IF_ForcesShear33BalokY[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            for(int j=1; j<50; j++){
                if(IF_Shear33BalokY[n][j][2] > koorNodeAcuan && IF_Shear33BalokY[n][j + 1][2] < koorNodeAcuan){
                    D_MainActivity.IF_Shear33BalokYNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Shear33BalokY[n][j][2] < koorNodeAcuan && IF_Shear33BalokY[n][j + 1][2] > koorNodeAcuan){
                    D_MainActivity.IF_Shear33BalokYNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Shear33BalokY[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][0];
            IF_Shear33BalokY[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][1];
            IF_Shear33BalokY[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/



        //Torsi ===================================================================================
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][3]));
            max = Math.max(max, Math.abs(E_F[n][9]));
        }

        for(int n=0; n<elemenBalokY; n++){
            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2] + E_F[n + elemenKolom + elemenBalokX][3] / max * lengthDraw;

            //Cek IF Positif
            if(E_F[n + elemenKolom + elemenBalokX][3] > 0){
                D_MainActivity.isFirstPositif_TorsiBalokY[n] = true;
            }else{
                D_MainActivity.isFirstPositif_TorsiBalokY[n] = false;
            }

            IF_TorsiBalokY[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];
            IF_TorsiBalokY[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            IF_TorsiBalokY[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            IF_TorsiBalokY[n][1][0] = startX;
            IF_TorsiBalokY[n][1][1] = startY;
            IF_TorsiBalokY[n][1][2] = startZ;

            startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][0];
            startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][1];
            startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][2] - E_F[n + elemenKolom + elemenBalokX][9] / max * lengthDraw;

            IF_TorsiBalokY[n][2][0] = startX;
            IF_TorsiBalokY[n][2][1] = startY;
            IF_TorsiBalokY[n][2][2] = startZ;

            IF_TorsiBalokY[n][3][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][0];
            IF_TorsiBalokY[n][3][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][1];
            IF_TorsiBalokY[n][3][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][2];

            IF_ForcesTorsiBalokY[n][0] = E_F[n + elemenKolom + elemenBalokX][3];
            IF_ForcesTorsiBalokY[n][4] =-E_F[n + elemenKolom + elemenBalokX][9];
            IF_ForcesTorsiBalokY[n][1] = 0.25*IF_ForcesTorsiBalokY[n][0]+0.75*IF_ForcesTorsiBalokY[n][4];
            IF_ForcesTorsiBalokY[n][2] = 0.50*IF_ForcesTorsiBalokY[n][0]+0.50*IF_ForcesTorsiBalokY[n][4];
            IF_ForcesTorsiBalokY[n][3] = 0.75*IF_ForcesTorsiBalokY[n][0]+0.25*IF_ForcesTorsiBalokY[n][4];
        }
        /*=========================================================================================
        //=======================================================================================*/



        //Momen 22 ================================================================================ //Done
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][4]));
            max = Math.max(max, Math.abs(E_F[n][10]));
        }

        for(int n=0; n<elemenBalokY; n++){
            double loadFrameDead = D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[n + elemenKolom + elemenBalokX]).getLoadFrameDead(); //N per mm
            double frameLoad;
            double shear33 = E_F[n + elemenKolom + elemenBalokX][2];

            if(D_MainActivity.isFrameLoadExist[n + elemenKolom + elemenBalokX][2]){
                frameLoad = D_MainActivity.frameLoad[n + elemenKolom + elemenBalokX][2]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2] + E_F[n + elemenKolom + elemenBalokX][4] / max * lengthDraw;

            //Cek IF Positif
            if(E_F[n + elemenKolom + elemenBalokX][4] > 0){
                D_MainActivity.isFirstPositif_Momen22BalokY[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Momen22BalokY[n] = false;
            }

            IF_Momen22BalokY[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];
            IF_Momen22BalokY[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            IF_Momen22BalokY[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            IF_ForcesMomen22BalokY[n][0]= E_F[n + elemenKolom + elemenBalokX][4];
            IF_ForcesMomen22BalokY[n][4]=-E_F[n + elemenKolom + elemenBalokX][10];

            for(int j=1; j<51; j++){
                double LDraw = (j - 1) / 49.0 * lengthBalokY;
                double LReal = (j - 1) / 49.0 * lengthReal;
                double momenMerata = (frameLoad - loadFrameDead) * LReal * LReal / 2;
                double momenShear = shear33 * LReal;

                IF_Momen22BalokY[n][j][0] = startX;
                IF_Momen22BalokY[n][j][1] = startY + LDraw;
                IF_Momen22BalokY[n][j][2] = startZ + (momenMerata + momenShear) / max * lengthDraw;
                tmpForces = IF_ForcesMomen22BalokY[n][0] + (momenMerata + momenShear);

                if(LDraw < aDraw){
                    double YInterpolasi = AreaLoadBeam[n + elemenBalokX] / a * LReal;
                    double momenSegitigaParsial = (0.5 * LReal * YInterpolasi) * LReal / 3;

                    IF_Momen22BalokY[n][j][2] = IF_Momen22BalokY[n][j][2] - momenSegitigaParsial / max * lengthDraw;
                    tmpForces = tmpForces - momenSegitigaParsial;

                }else if(LDraw < aDraw + SisiAtasTraYDraw){
                    double momenSegitiga = (0.5 * a * AreaLoadBeam[n + elemenBalokX]) * (LReal - a + a / 3);
                    double momenPersegiParsial = (LReal - a) * AreaLoadBeam[n + elemenBalokX] * (LReal - a) / 2;

                    IF_Momen22BalokY[n][j][2] = IF_Momen22BalokY[n][j][2] - (momenSegitiga + momenPersegiParsial) / max * lengthDraw;
                    tmpForces = tmpForces - (momenSegitiga + momenPersegiParsial);

                }else{
                    double momenSegitiga = (0.5 * a * AreaLoadBeam[n + elemenBalokX]) * (LReal - a + a / 3);
                    double momenPersegi = SisiAtasTrapesiumY * AreaLoadBeam[n + elemenBalokX] * (LReal - a - SisiAtasTrapesiumY / 2);

                    IF_Momen22BalokY[n][j][2] = IF_Momen22BalokY[n][j][2] - (momenSegitiga + momenPersegi) / max * lengthDraw;
                    tmpForces = tmpForces - (momenSegitiga + momenPersegi);

                    double ASegitigaTotal = 0.5 * a * AreaLoadBeam[n + elemenBalokX];
                    double aRealKanan = LReal - a - SisiAtasTrapesiumY;
                    double aParsial = a - aRealKanan;
                    double YInterpolasiParsial = AreaLoadBeam[n + elemenBalokX] / a * aParsial;
                    double ASegitigaParsial = 0.5 * aParsial * YInterpolasiParsial;

                    double APersegiRealBawah = aRealKanan * YInterpolasiParsial;
                    double ASegitigaRealAtas = (ASegitigaTotal - ASegitigaParsial) - APersegiRealBawah;

                    double momenSegitigaRealAtas = ASegitigaRealAtas * aRealKanan * (2.0 / 3.0);
                    double momenPersegiRealBawah = APersegiRealBawah * aRealKanan / 2;

                    IF_Momen22BalokY[n][j][2] = IF_Momen22BalokY[n][j][2] - (momenSegitigaRealAtas + momenPersegiRealBawah) / max * lengthDraw;
                    tmpForces = tmpForces - (momenSegitigaRealAtas + momenPersegiRealBawah);
                }
                if(j==13){IF_ForcesMomen22BalokY[n][1]=tmpForces;}
                if(j==26){IF_ForcesMomen22BalokY[n][2]=tmpForces;}
                if(j==39){IF_ForcesMomen22BalokY[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            for(int j=1; j<50; j++){
                if(IF_Momen22BalokY[n][j][2] > koorNodeAcuan && IF_Momen22BalokY[n][j + 1][2] < koorNodeAcuan){
                    D_MainActivity.IF_Momen22BalokYNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Momen22BalokY[n][j][2] < koorNodeAcuan && IF_Momen22BalokY[n][j + 1][2] > koorNodeAcuan){
                    D_MainActivity.IF_Momen22BalokYNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Momen22BalokY[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][0];
            IF_Momen22BalokY[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][1];
            IF_Momen22BalokY[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/



        //Momen 33 ================================================================================ //Done
        max = 0;
        for(int n=0; n<elemenStruktur; n++){
            max = Math.max(max, Math.abs(E_F[n][5]));
            max = Math.max(max, Math.abs(E_F[n][11]));
        }

        for(int n=0; n<elemenBalokY; n++){
            double frameLoad;
            double shear22 = E_F[n + elemenKolom + elemenBalokX][1];

            if(D_MainActivity.isFrameLoadExist[n + elemenKolom + elemenBalokX][0]){
                frameLoad = D_MainActivity.frameLoad[n + elemenKolom + elemenBalokX][0]; //N per mm
            }else{
                frameLoad = 0;
            }

            double startX = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0] - E_F[n + elemenKolom + elemenBalokX][5] / max * lengthDraw;
            double startY = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            double startZ = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            //Cek IF Positif
            if(E_F[n + elemenKolom + elemenBalokX][5] > 0){
                D_MainActivity.isFirstPositif_Momen33BalokY[n] = true;
            }else{
                D_MainActivity.isFirstPositif_Momen33BalokY[n] = false;
            }

            IF_Momen33BalokY[n][0][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];
            IF_Momen33BalokY[n][0][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][1];
            IF_Momen33BalokY[n][0][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][2];

            IF_ForcesMomen33BalokY[n][0]=-E_F[n + elemenKolom + elemenBalokX][5];
            IF_ForcesMomen33BalokY[n][4]= E_F[n + elemenKolom + elemenBalokX][11];

            for(int j=1; j<51; j++){
                double LDraw = (j - 1) / 49.0 * lengthBalokY;
                double LReal = (j - 1) / 49.0 * lengthReal;
                double momenMerata = frameLoad * LReal * LReal / 2;
                double momenShear = shear22 * LReal;

                IF_Momen33BalokY[n][j][0] = startX - (momenMerata - momenShear) / max * lengthDraw;
                tmpForces = IF_ForcesMomen33BalokY[n][0] - (momenMerata - momenShear);
                IF_Momen33BalokY[n][j][1] = startY + LDraw;
                IF_Momen33BalokY[n][j][2] = startZ;
                if(j==13){IF_ForcesMomen33BalokY[n][1]=tmpForces;}
                if(j==26){IF_ForcesMomen33BalokY[n][2]=tmpForces;}
                if(j==39){IF_ForcesMomen33BalokY[n][3]=tmpForces;}
            }

            int counterIF_Nodal = 0;
            float koorNodeAcuan = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][0]][0];

            for(int j=1; j<50; j++){
                if(IF_Momen33BalokY[n][j][0] > koorNodeAcuan && IF_Momen33BalokY[n][j + 1][0] < koorNodeAcuan){
                    D_MainActivity.IF_Momen33BalokYNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }else if(IF_Momen33BalokY[n][j][0] < koorNodeAcuan && IF_Momen33BalokY[n][j + 1][0] > koorNodeAcuan){
                    D_MainActivity.IF_Momen33BalokYNodal[n][counterIF_Nodal] = j;

                    counterIF_Nodal++;
                }
            }

            IF_Momen33BalokY[n][51][0] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][0];
            IF_Momen33BalokY[n][51][1] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][1];
            IF_Momen33BalokY[n][51][2] = D_MainActivity.koorNode[D_MainActivity.pointerElemenBalokYZ[n][1]][2];
        }
        /*=========================================================================================
        //=======================================================================================*/
    }

    public void setKoorIF_Fix(){
        D_MainActivity.IF_AxialKolomFix = new double[D_MainActivity.pointerElemenKolom.length][52][3];
        D_MainActivity.IF_Shear22KolomFix = new double[D_MainActivity.pointerElemenKolom.length][52][3];
        D_MainActivity.IF_Shear33KolomFix = new double[D_MainActivity.pointerElemenKolom.length][52][3];

        D_MainActivity.IF_TorsiKolomFix = new double[D_MainActivity.pointerElemenKolom.length][4][3];
        D_MainActivity.IF_Momen22KolomFix = new double[D_MainActivity.pointerElemenKolom.length][52][3];
        D_MainActivity.IF_Momen33KolomFix = new double[D_MainActivity.pointerElemenKolom.length][52][3];

        //*****************************************************************************************

        D_MainActivity.IF_AxialBalokXFix = new double[D_MainActivity.pointerElemenBalokXZ.length][52][3];
        D_MainActivity.IF_Shear22BalokXFix = new double[D_MainActivity.pointerElemenBalokXZ.length][52][3];
        D_MainActivity.IF_Shear33BalokXFix = new double[D_MainActivity.pointerElemenBalokXZ.length][52][3];

        D_MainActivity.IF_TorsiBalokXFix = new double[D_MainActivity.pointerElemenBalokXZ.length][4][3];
        D_MainActivity.IF_Momen22BalokXFix = new double[D_MainActivity.pointerElemenBalokXZ.length][52][3];
        D_MainActivity.IF_Momen33BalokXFix = new double[D_MainActivity.pointerElemenBalokXZ.length][52][3];

        //*****************************************************************************************

        D_MainActivity.IF_AxialBalokYFix = new double[D_MainActivity.pointerElemenBalokYZ.length][52][3];
        D_MainActivity.IF_Shear22BalokYFix = new double[D_MainActivity.pointerElemenBalokYZ.length][52][3];
        D_MainActivity.IF_Shear33BalokYFix = new double[D_MainActivity.pointerElemenBalokYZ.length][52][3];

        D_MainActivity.IF_TorsiBalokYFix = new double[D_MainActivity.pointerElemenBalokYZ.length][4][3];
        D_MainActivity.IF_Momen22BalokYFix = new double[D_MainActivity.pointerElemenBalokYZ.length][52][3];
        D_MainActivity.IF_Momen33BalokYFix = new double[D_MainActivity.pointerElemenBalokYZ.length][52][3];

        int x;
        int y;

        x = 0;
        for(double[][] i : D_MainActivity.IF_AxialKolomFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_AxialKolom[x][y][0];
                j[1] = D_MainActivity.IF_AxialKolom[x][y][1];
                j[2] = D_MainActivity.IF_AxialKolom[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Shear22KolomFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Shear22Kolom[x][y][0];
                j[1] = D_MainActivity.IF_Shear22Kolom[x][y][1];
                j[2] = D_MainActivity.IF_Shear22Kolom[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Shear33KolomFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Shear33Kolom[x][y][0];
                j[1] = D_MainActivity.IF_Shear33Kolom[x][y][1];
                j[2] = D_MainActivity.IF_Shear33Kolom[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_TorsiKolomFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_TorsiKolom[x][y][0];
                j[1] = D_MainActivity.IF_TorsiKolom[x][y][1];
                j[2] = D_MainActivity.IF_TorsiKolom[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Momen22KolomFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Momen22Kolom[x][y][0];
                j[1] = D_MainActivity.IF_Momen22Kolom[x][y][1];
                j[2] = D_MainActivity.IF_Momen22Kolom[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Momen33KolomFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Momen33Kolom[x][y][0];
                j[1] = D_MainActivity.IF_Momen33Kolom[x][y][1];
                j[2] = D_MainActivity.IF_Momen33Kolom[x][y][2];

                y++;
            }
            x++;
        }

        //************************************************

        x = 0;
        for(double[][] i : D_MainActivity.IF_AxialBalokXFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_AxialBalokX[x][y][0];
                j[1] = D_MainActivity.IF_AxialBalokX[x][y][1];
                j[2] = D_MainActivity.IF_AxialBalokX[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Shear22BalokXFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Shear22BalokX[x][y][0];
                j[1] = D_MainActivity.IF_Shear22BalokX[x][y][1];
                j[2] = D_MainActivity.IF_Shear22BalokX[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Shear33BalokXFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Shear33BalokX[x][y][0];
                j[1] = D_MainActivity.IF_Shear33BalokX[x][y][1];
                j[2] = D_MainActivity.IF_Shear33BalokX[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_TorsiBalokXFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_TorsiBalokX[x][y][0];
                j[1] = D_MainActivity.IF_TorsiBalokX[x][y][1];
                j[2] = D_MainActivity.IF_TorsiBalokX[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Momen22BalokXFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Momen22BalokX[x][y][0];
                j[1] = D_MainActivity.IF_Momen22BalokX[x][y][1];
                j[2] = D_MainActivity.IF_Momen22BalokX[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Momen33BalokXFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Momen33BalokX[x][y][0];
                j[1] = D_MainActivity.IF_Momen33BalokX[x][y][1];
                j[2] = D_MainActivity.IF_Momen33BalokX[x][y][2];

                y++;
            }
            x++;
        }

        //************************************************

        x = 0;
        for(double[][] i : D_MainActivity.IF_AxialBalokYFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_AxialBalokY[x][y][0];
                j[1] = D_MainActivity.IF_AxialBalokY[x][y][1];
                j[2] = D_MainActivity.IF_AxialBalokY[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Shear22BalokYFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Shear22BalokY[x][y][0];
                j[1] = D_MainActivity.IF_Shear22BalokY[x][y][1];
                j[2] = D_MainActivity.IF_Shear22BalokY[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Shear33BalokYFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Shear33BalokY[x][y][0];
                j[1] = D_MainActivity.IF_Shear33BalokY[x][y][1];
                j[2] = D_MainActivity.IF_Shear33BalokY[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_TorsiBalokYFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_TorsiBalokY[x][y][0];
                j[1] = D_MainActivity.IF_TorsiBalokY[x][y][1];
                j[2] = D_MainActivity.IF_TorsiBalokY[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Momen22BalokYFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Momen22BalokY[x][y][0];
                j[1] = D_MainActivity.IF_Momen22BalokY[x][y][1];
                j[2] = D_MainActivity.IF_Momen22BalokY[x][y][2];

                y++;
            }
            x++;
        }

        x = 0;
        for(double[][] i : D_MainActivity.IF_Momen33BalokYFix){
            y = 0;
            for(double[] j : i){
                j[0] = D_MainActivity.IF_Momen33BalokY[x][y][0];
                j[1] = D_MainActivity.IF_Momen33BalokY[x][y][1];
                j[2] = D_MainActivity.IF_Momen33BalokY[x][y][2];

                y++;
            }
            x++;
        }
    }

    public void setKoorDeformed(){
        D_MainActivity.koorNodeDeformed = new float[D_MainActivity.koorNode.length][3];
        D_MainActivity.koorNodeDeformedFix = new float[D_MainActivity.koorNode.length][3];

        float[][] koorNodeDeformed = D_MainActivity.koorNodeDeformed;
        float[][] koorNodeDeformedFix = D_MainActivity.koorNodeDeformedFix;

        int x;
        double U_S_Max = 0;
        double skalaDeformed;

        x = 0;
        for(float[] i : koorNodeDeformed){
            if(Math.abs(U_S[x * N_DOF]) > U_S_Max){
                U_S_Max = Math.abs(U_S[x * N_DOF]);
            }

            if(Math.abs(U_S[x * N_DOF + 1]) > U_S_Max){
                U_S_Max = Math.abs(U_S[x * N_DOF + 1]);
            }

            if(Math.abs(U_S[x * N_DOF + 2]) > U_S_Max){
                U_S_Max = Math.abs(U_S[x * N_DOF + 2]);
            }

            x++;
        }

        skalaDeformed = 150 / U_S_Max;

        x = 0;
        for(float[] i : koorNodeDeformed){
            i[0] = D_MainActivity.koorNode[x][0] + Float.parseFloat(Double.toString(U_S[x * N_DOF] * skalaDeformed));
            i[1] = D_MainActivity.koorNode[x][1] + Float.parseFloat(Double.toString(U_S[x * N_DOF + 1] * skalaDeformed));
            i[2] = D_MainActivity.koorNode[x][2] + Float.parseFloat(Double.toString(U_S[x * N_DOF + 2] * skalaDeformed));

            x++;
        }

        x = 0;
        for(float[] i : koorNodeDeformedFix){
            i[0] = D_MainActivity.koorNode[x][0] + Float.parseFloat(Double.toString(U_S[x * N_DOF] * skalaDeformed));
            i[1] = D_MainActivity.koorNode[x][1] + Float.parseFloat(Double.toString(U_S[x * N_DOF + 1] * skalaDeformed));
            i[2] = D_MainActivity.koorNode[x][2] + Float.parseFloat(Double.toString(U_S[x * N_DOF + 2] * skalaDeformed));

            x++;
        }
    }
}