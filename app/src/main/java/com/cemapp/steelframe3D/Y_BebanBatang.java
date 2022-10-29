package com.cemapp.steelframe3D;

public class Y_BebanBatang {
    private int Batang;
    private double[] Pu;
    private double[] VuMajor;
    private double[] VuMinor;
    private double[] MuMajor;
    private double[] MuMinor;
    private double MuMajorMax;
    private double MuMinorMax;
    private double CbMajor;
    private double CbMinor; //ini buat profil L
    private String profil;
    private double amanPMDesign;
    private double amanVDesign;
    private double amanPMAnalysis;
    private double amanVAnalysis;

    public Y_BebanBatang(int nmrBatang, int jenisBatang) {
        Pu = new double[5];
        VuMajor = new double[5];
        VuMinor = new double[5];
        MuMajor = new double[5];
        MuMinor = new double[5];

        this.Batang = Batang;

        for (int i = 0; i < 5; i++) {
            switch (jenisBatang) {
                case 0:
                    Pu[i] = Running.IF_ForcesAxialKolom[nmrBatang][i]/1000;
                    VuMajor[i] = Running.IF_ForcesShear22Kolom[nmrBatang][i]/1000;
                    VuMinor[i] = Running.IF_ForcesShear33Kolom[nmrBatang][i]/1000;
                    MuMajor[i] = Running.IF_ForcesMomen22Kolom[nmrBatang][i]/1000/1000;
                    MuMinor[i] = Running.IF_ForcesMomen33Kolom[nmrBatang][i]/1000/1000;
                    break;
                case 1:
                    Pu[i] = Running.IF_ForcesAxialBalokX[nmrBatang][i]/1000;
                    VuMajor[i] = Running.IF_ForcesShear22BalokX[nmrBatang][i]/1000;
                    VuMinor[i] = Running.IF_ForcesShear33BalokX[nmrBatang][i]/1000;
                    MuMajor[i] = Running.IF_ForcesMomen22BalokX[nmrBatang][i]/1000/1000;
                    MuMinor[i] = Running.IF_ForcesMomen33BalokX[nmrBatang][i]/1000/1000;
                    break;
                case 2:
                    Pu[i] = Running.IF_ForcesAxialBalokY[nmrBatang][i]/1000;
                    VuMajor[i] = Running.IF_ForcesShear22BalokY[nmrBatang][i]/1000;
                    VuMinor[i] = Running.IF_ForcesShear33BalokY[nmrBatang][i]/1000;
                    MuMajor[i] = Running.IF_ForcesMomen22BalokY[nmrBatang][i]/1000/1000;
                    MuMinor[i] = Running.IF_ForcesMomen33BalokY[nmrBatang][i]/1000/1000;
                    break;
            }
            MuMajorMax = Math.max(MuMajorMax,Math.abs(MuMajor[i]));
            MuMinorMax = Math.max(MuMinorMax,Math.abs(MuMinor[i]));
        }
        CbMajor = 12.5*MuMajorMax/(2.5*MuMajorMax+3*MuMajor[1]+4*MuMajor[2]+3*MuMajor[3]);
        CbMinor = 12.5*MuMinorMax/(2.5*MuMinorMax+3*MuMinor[1]+4*MuMinor[2]+3*MuMinor[3]);
    }

    public double getCbMajor() {
        return CbMajor;
    }

    public double getCbMinor() {
        return CbMinor;
    }

    public void setCbMajor(double cb) {
        CbMajor = cb;
    }

    public void setCbMinor(double cb) {
        CbMinor = cb;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public double getAmanPMDesign() {
        return amanPMDesign;
    }

    public void setAmanPMDesign(double amanPM) {
        this.amanPMDesign = amanPM;
    }

    public double getAmanVDesign() {
        return amanVDesign;
    }

    public void setAmanVDesign(double amanV) {
        this.amanVDesign = amanV;
    }

    public double getAmanPMAnalysis() { return amanPMAnalysis; }

    public void setAmanPMAnalysis(double amanPM) { this.amanPMAnalysis = amanPM; }

    public double getAmanVAnalysis() { return amanVAnalysis; }

    public void setAmanVAnalysis(double amanV) { this.amanVAnalysis = amanV; }

    public double getMuMajor(int position) {
        return MuMajor[position];
    }

    public double getMuMinor(int position) {
        return MuMinor[position];
    }

    public double getVuMajor(int position) {
        return VuMajor[position];
    }

    public double getVuMinor(int position) {
        return VuMinor[position];
    }

    public double getPu(int position) {
        return Pu[position];
    }

}
