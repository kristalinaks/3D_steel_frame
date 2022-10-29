package com.cemapp.steelframe3D;

public class Y_BebanGempa {
    private double Cs;
    private double LKolom;
    private double LBalokX;
    private double LBalokY;
    private int NKolom; //jumlah tingkat
    private int NBalokX;
    private int NBalokY;
    private int JmlhKolom;
    private int JmlhBalokX;
    private int JmlhBalokY;
    private double T;
    private double Ss;
    private double S1;
    private double Fa;
    private double Fv;
    private double Sds;
    private double Sd1;
    private double Sa;
    private double Ie;
    private double R;
    private String Situs;
    private double k;
    private double Vgempa;
    private double[] WLantai;
    private double WTotal;

    public static double[] EL_G_EQ;

    public Y_BebanGempa(
            double SsA,
            double S1A,
            double IeA, //faktor keutamaan gempa (dikali)
            double RA, //koef modif respons (dibagi)
            String SitusA //Kelas SA, SB, SC, SD, SE
    ){
        Ie = IeA;
        R = RA;
        Situs = SitusA;
        NKolom = D_MainActivity.Nst;
        NBalokX = D_MainActivity.Nbx;
        NBalokY = D_MainActivity.Nby;
        JmlhKolom = D_MainActivity.pointerElemenKolom.length;
        JmlhBalokX = D_MainActivity.pointerElemenBalokXZ.length;
        JmlhBalokY = D_MainActivity.pointerElemenBalokYZ.length;
        LKolom = D_MainActivity.Sth;
        LBalokX = D_MainActivity.Bwx;
        LBalokY = D_MainActivity.Bwy;

        WTotal=0; //kN
        T = 0.0724*Math.pow(LKolom*NKolom,0.8); //angka ini buat rangka baja
        WLantai = new double[NKolom+1]; //kN
        int JumlahKolompadaSuatuLantai = (NBalokX+1)*(NBalokY+1);
        int JumlahBalokXpadaSuatuLantai = (NBalokX)*(NBalokY+1);
        int JumlahBalokYpadaSuatuLantai = (NBalokX+1)*(NBalokY);
        int JumlahAreapadaSuatuLantai = (NBalokX)*(NBalokY);
        //LANTAI 1 HINGGA 1 LANTAI SEBELUM LANTAI TERAKHIR (i = NKolom)
        for(int i=1; i<NKolom; i++){
            //KOLOM
            //masukin masa kolom 0.5 bawah lantai (i-1) & 0.5 atas lantai (i+1)
            for(int j=(i-1)*JumlahKolompadaSuatuLantai; j<(i+1)*JumlahKolompadaSuatuLantai; j++){
                WLantai[i]=WLantai[i]
                        +LKolom*D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[j]).getLoadFrameDead()/2
                        +LKolom*D_MainActivity.frameLoad[j][2]/2.0;
            }
            //BALOK X
            //hanya pada lantai itu (i-1) sampai i
            for(int j=D_MainActivity.pointerElemenKolom.length+(i-1)*JumlahBalokXpadaSuatuLantai; j<D_MainActivity.pointerElemenKolom.length+i*JumlahBalokXpadaSuatuLantai; j++){
                WLantai[i]=WLantai[i]
                        +LBalokX*D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[j]).getLoadFrameDead()
                        +LBalokX*D_MainActivity.frameLoad[j][2]/2.0;
            }
            //BALOK Y
            //hanya pada lantai itu (i-1) sampai i
            for(int j=D_MainActivity.pointerElemenKolom.length+D_MainActivity.pointerElemenBalokXZ.length+(i-1)*JumlahBalokYpadaSuatuLantai; j<D_MainActivity.pointerElemenKolom.length+D_MainActivity.pointerElemenBalokXZ.length+i*JumlahBalokYpadaSuatuLantai; j++){
                WLantai[i]=WLantai[i]
                        +LBalokY*D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[j]).getLoadFrameDead()
                        +LBalokY*D_MainActivity.frameLoad[j][2]/2.0;
            }
            //AREA
            //hanya pada lantai itu (i-1) sampai i
            for(int j=(i-1)*JumlahAreapadaSuatuLantai;j<i*JumlahAreapadaSuatuLantai ; j++){
                WLantai[i]=WLantai[i]
                        +LBalokX*LBalokY*D_MainActivity.areaLoad[j];
            }
        }
        //LANTAI TERAKHIR (i = NKolom)
        int i = NKolom;
        //KOLOM
        //masukin masa kolom 0.5 bawah lantai (i-1) sampai i
        for(int j=(i-1)*JumlahKolompadaSuatuLantai; j<(i)*JumlahKolompadaSuatuLantai; j++){
            WLantai[i]=WLantai[i]
                    +Running.E_L[j]*D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[j]).getLoadFrameDead()/2/1000
                    +Running.E_L[j]*D_MainActivity.frameLoad[j][2]/2;
        }
        //BALOK X
        //hanya pada lantai itu (i-1) sampai i
        for(int j=D_MainActivity.pointerElemenKolom.length+(i-1)*JumlahBalokXpadaSuatuLantai; j<D_MainActivity.pointerElemenKolom.length+i*JumlahBalokXpadaSuatuLantai; j++){
            WLantai[i]=WLantai[i]
                    +Running.E_L[j]*D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[j]).getLoadFrameDead()/1000
                    +Running.E_L[j]*D_MainActivity.frameLoad[j][2]/2;
        }
        //BALOK Y
        //hanya pada lantai itu (i-1) sampai i
        for(int j=D_MainActivity.pointerElemenKolom.length+D_MainActivity.pointerElemenBalokXZ.length+(i-1)*JumlahBalokYpadaSuatuLantai; j<D_MainActivity.pointerElemenKolom.length+D_MainActivity.pointerElemenBalokXZ.length+i*JumlahBalokYpadaSuatuLantai; j++){
            WLantai[i]=WLantai[i]
                    +Running.E_L[j]*D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[j]).getLoadFrameDead()/1000
                    +Running.E_L[j]*D_MainActivity.frameLoad[j][2]/2;
        }
        //AREA
        //hanya pada lantai itu (i-1) sampai i
        for(int j=(i-1)*JumlahAreapadaSuatuLantai;j<i*JumlahAreapadaSuatuLantai ; j++){
            WLantai[i]=WLantai[i]
                    +LBalokX*LBalokY*D_MainActivity.areaLoad[j];
        }
        //LANTAI 0
        i = 0;
        //KOLOM
        //masukin masa kolom 0.5 atas lantai (i) sampai (i+1)
        for(int j=(i)*JumlahKolompadaSuatuLantai; j<(i+1)*JumlahKolompadaSuatuLantai; j++){
            WLantai[i]=WLantai[i]
                    +Running.E_L[j]*D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[j]).getLoadFrameDead()/2/1000
                    +Running.E_L[j]*D_MainActivity.frameLoad[j][2]/2;
        }

        //Menghitung WTotal Struktur
        for(i=0; i<=NKolom; i++){
            WTotal = WTotal + WLantai[i];
        }
        //SNI
        Ss = SsA;
        S1 = S1A;
        switch (Situs) {
            case "SA":
                Fa = 0.8; //sama semua
                Fv = 0.8; //sama semua
                break;
            case "SB":
                Fa = 1.0; //sama semua
                Fv = 1.0; //sama semua
                break;
            case "SC":
                if (Ss <= 0.5) {
                    Fa = 1.2; //dibawah Ss=0.5 sama semua
                } else if (Ss >= 1.0) {
                    Fa = 1.0; //diatas  Ss=1.0 sama semua
                } else {
                    Fa = (Ss - 0.5) / (1.0 - 0.5) * (1.0 - 1.2) + 1.2; //selain itu interpolasi
                }
                if (S1 <= 0.1) {
                    Fv = 1.7; //dibawah S1=0.1 harus 1.7
                } else if (S1 >= 0.5) {
                    Fv = 1.3; //diatas  S1=0.5 harus 1.3
                } else {
                    Fv = (S1 - 0.1) / (0.5 - 0.1) * (1.3 - 1.7) + 1.7; //selain itu interpolasi
                }
                break;
            case "SD":
                if (Ss <= 0.25) {
                    Fa = 1.6; //dibawah Ss=0.25 harus 1.6
                } else if (Ss >= 1.25) {
                    Fa = 1.0; //diatas  Ss=1.25 harus 1.0
                } else if (Ss <= 0.75) {
                    Fa = (Ss - 0.25) / (0.75 - 0.25) * (1.2 - 1.6) + 1.6; //saat Ss=0.25-0.75 interpolasi sama (interval 0.2)
                } else {
                    Fa = (Ss - 0.75) / (1.25 - 0.75) * (1.0 - 1.2) + 1.2; //saat Ss=0.75-1.25 interpolasi sama (interval 0.1)
                }
                if (S1 <= 0.1) {
                    Fv = 2.4; //dibawah S1=0.1 harus 2.4
                } else if (S1 >= 0.5) {
                    Fv = 1.5; //diatas  S1=0.5 harus 1.5
                } else if (S1 <= 0.2) {
                    Fv = (S1 - 0.1) / (0.2 - 0.1) * (2.0 - 2.4) + 2.4; //saat Ss=0.1-0.2 interpolasi sama (interval 0.4)
                } else if (S1 <= 0.4) {
                    Fv = (S1 - 0.2) / (0.4 - 0.2) * (1.6 - 2.0) + 2.0; //saat Ss=0.2-0.4 interpolasi sama (interval 0.2)
                } else {
                    Fv = (S1 - 0.4) / (0.5 - 0.4) * (1.5 - 1.6) + 1.6; //saat Ss=0.4-0.5 interpolasi sama (interval 0.1)
                }
                break;
            case "SE":
                if (Ss <= 0.25) {
                    Fa = 2.5; //dibawah Ss=0.25 harus 2.5
                } else if (Ss >= 1.0) {
                    Fa = 0.9; //diatas  Ss=1.0 sama semua
                } else if (Ss <= 0.50) {
                    Fa = (Ss - 0.25) / (0.50 - 0.25) * (1.7 - 2.5) + 2.5;
                } else if (Ss <= 0.75) {
                    Fa = (Ss - 0.50) / (0.75 - 0.50) * (1.2 - 1.7) + 1.7;
                } else {
                    Fa = (Ss - 0.75) / (1.00 - 0.75) * (0.9 - 1.2) + 1.2;
                }
                if (S1 <= 0.1) {
                    Fv = 3.5; //dibawah S1=0.25 harus 2.5
                } else if (S1 >= 0.4) {
                    Fv = 2.4; //diatas  S1=1.0 sama semua
                } else if (S1 <= 0.2) {
                    Fv = (S1 - 0.1) / (0.2 - 0.1) * (3.2 - 3.5) + 3.5;
                } else {
                    Fv = (S1 - 0.2) / (0.4 - 0.2) * (2.4 - 3.2) + 3.2;
                }
                break;
        }
        Sds = 2.0/3.0*Fa*Ss;
        Sd1 = 2.0/3.0*Fv*S1;
        if(T<0.2*Sd1/Sds){
            Sa = Sds*(0.4+0.6*T/(0.2*Sd1/Sds));
        }else if(T<Sd1/Sds){
            Sa = Sds;
        }else{
            Sa = Sd1/T;
        }
        Cs=Math.max(Math.min(Sds/(R/Ie),Sd1/T/(R/Ie)),Math.max(0.044*Sds*Ie,0.01));
        if(S1>=0.6){Cs=Math.max(0.5*S1/(R/Ie),Cs);}
        Vgempa = WTotal*Cs;
        Static_Equivalen();
    }

    public void Static_Equivalen(){
        if(T<=0.5){
            k = 1;
        } else if(T>=2.5){
            k = 2;
        } else {
            k = 1+(T-0.5)/(2.5-0.5)*(2.0-1.0);
        }

        double[] Wik;
        double[] Cvx;
        double[] FxLantai;
        double Wiktot=0;
        Wik = new double[NKolom+1];
        Cvx = new double[NKolom+1];
        FxLantai = new double[NKolom+1];
        //Menghitung Wik Tiap Lantai (Wik = W * Elevasi^k)
        for(int i=1; i<=NKolom; i++){
            Wik[i]=WLantai[i]*Math.pow(LKolom*i,k);
            Wiktot=Wiktot + Wik[i];
        }
        //Menghitung Fx lateral Tiap Lantai
        for(int i=1; i<=NKolom; i++){
            Cvx[i]=Wik[i]/Wiktot;
            FxLantai[i]=Cvx[i]*Vgempa;
        }
        //DISTRIBUSI BEBAN GEMPA KE BEBAN NODAL UNTUK MATRIKS BEBAN
        int JumlahNodalpadaSuatuLantai = (NBalokX+1)*(NBalokY+1);
        EL_G_EQ = new double[(NKolom+1)*JumlahNodalpadaSuatuLantai];
        for(int i = 1; i<=NKolom; i++){
            for(int j = i*JumlahNodalpadaSuatuLantai;j<(i+1)*JumlahNodalpadaSuatuLantai;j++){
                //Fx pada posisi + 0
                EL_G_EQ[j] = FxLantai[i]/JumlahNodalpadaSuatuLantai; //kN
            }
        }
    }
    public void Response_Spectrum(){

    }
}

