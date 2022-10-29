package com.cemapp.steelframe3D;

public class SteelSection {
    private String sectionCode;
    private String sectionName;
    private String steelType;
    private String steelName;
    private double B;
    private double H;
    private double tw;
    private double tf;
    private double r1;
    private double r2;
    private double A;
    private double Cy;
    private double Cz;
    private double Iy;
    private double Iz;
    private double Imin;
    private double ry;
    private double rz;
    private double rmin;
    private double Sy;
    private double Sz;
    private double Smin;
    private double Zy;
    private double Zz;
    private double JIx;
    private double Fy;
    private double Fu;
    private double E;
    private double G;
    private double Cw;
    final private double steelDensity;
    private double loadFrameDead;

    private double ynol;
    private double znol;
    private double PnC;
    private double PnT;
    private double VnMajor;
    private double VnMinor;
    private double MnMajor;
    private double MpMajor;
    private double MnMinor;
    private double MpMinor;
    private boolean CbStatus;
    private boolean isAlphaRotated;

    private double MnMajorBatang;
    private double MnMinorBatang;

    public SteelSection(String sectionCode, String sectionName, String steelType, String steelName, double B,
                        double H, double tw, double tf, double r1, double r2, double A,
                        double Cy, double Cz, double Iy, double Iz, double ry, double rz, double Sy,
                        double Sz, boolean isAlphaRotated) {

        //ganti satuan
        this.sectionCode = sectionCode;
        this.sectionName = sectionName;
        this.steelType = steelType;
        this.steelName = steelName;
        this.B = B; //width (mm)
        this.H = H; //height (mm)
        this.tw = tw; //web thickness (mm)
        this.tf = tf; //flange thickness (mm)
        this.r1 = r1; //radius 1 (mm)
        this.r2 = r2; //radius 2 (mm)
        this.A = A*100; //Area (mm2)
        this.Cy = Cy*10; //titik berat (mm)
        this.Cz = Cz*10; //titik berat (mm)
        this.Iy = Iy*10000; //inersia (mm4)
        this.Iz = Iz*10000; //inersia (mm4)
        this.ry = ry*10; //girasi (mm)
        this.rz = rz*10; //girasi (mm)
        this.Sy = Sy*1000; //sect modulus (mm3)
        this.Sz = Sz*1000; //sect modulus (mm3)
        this.isAlphaRotated = isAlphaRotated; //if true brarti rotated 90 degree

        Fy = D_MainActivity.bajaFy; //MPa
        Fu = D_MainActivity.bajaFu; //MPa
        E = D_MainActivity.bajaE; //MPa
        rmin = Math.min(this.ry,this.rz);
        Imin = Math.min(this.Iy,this.Iz);
        Smin = Math.min(this.Sy,this.Sz);
        G = E/(2.0*1.3);
        Zy = this.B * Math.pow(this.H,2)/4.0 - (this.B - this.tw) * Math.pow((this.H - 2 * this.tf),2) / 4.0;
        Zz = this.tf * 2 * Math.pow(this.B,2)/4.0 + (this.H - this.tf * 2) * Math.pow(this.tw,2) / 4.0;
        JIx = (this.H - 2 * this.tf) * Math.pow(this.tw,3)/3.0 + 2 * this.B * Math.pow(this.tf,3) / 3.0;

        steelDensity = 7900.0; //kg/m3
        double gamaNmm = steelDensity * 9.81 / Math.pow(10, 9); //N per mm3
        loadFrameDead = gamaNmm * this.A; //N per mm
    }

    public void setCapacityWF(int i, double L) {
        double EFy = Math.sqrt(E / Fy);
        double sayap = B / 2.0 / tf;
        double badan = (H - 2 * tf - 2 * rmin) / tw;
        double kc = Math.min(0.76, Math.max(0.35, (4 / Math.sqrt(badan))));
        double EFykc = Math.sqrt(kc * E / Fy);
        double Qs;
        double Fcr;
        double Mn;
        double kv;
        double Cv;
        double Qa;
        Cw = Math.pow((this.H - this.tf), 2) * this.Iz / 4.0;

        //tarik
        PnT = Math.min(0.9 * Fy * A, 0.75 * Fu * A) / 1000;

        //tekan
        if(sayap >= 1.03 * EFy) {
            Qs = 0.69 * E / Fy / Math.pow(sayap,2); //langsing
        } else {
            Qs = Math.min(1.415 - 0.74 * sayap / EFy, 1);
        }
        double He = Math.min(1.92 * tw * EFy * (1 - 0.34 / badan * EFy), (H - 2 * tf));
        Qa = (B * (He + 2 * tf) - (B - tw) * He) / A;
        double Fe = Math.pow(Math.PI,2) * E / (0.8 * L / rz);
        if(Qa * Qs * Fy / Fe <= 2.25) {
            Fcr = Qa * Qs * Math.pow(0.658,(Qa * Qs * Fy / Fe)) * Fy;
        } else {
            Fcr = 0.877 * Fe;
        }
        PnC = 0.9 * Fcr * A / 1000;

        //Momen Major
        double Mp = Fy * Zy;
        MnMajor = Mp;
        MpMajor = Mp;
        double Lp = 1.76 * rz * EFy;
        double rts = Math.sqrt(Math.sqrt(Iz * Cw) / Sy);
        double Lr = 1.95 * rts * E / (0.7 * Fy) * Math.sqrt(JIx / Sy / (H - tf) + Math.sqrt(JIx / Sy /
                Math.pow((H - tf),2) + 6.76 * Math.pow((0.7 * Fy / E),2)));
        if(sayap > EFy) {
            Mn = 0.9 * E * kc * Sy / sayap;
        }else {
            Mn = (Mp - (Mp - 0.7 * Fy * Sy) * (sayap - 0.38 * EFy) / (EFy - 0.38 * EFy));
        }
        MnMajor = Math.min(Mn, MnMajor);
        if(L > Lr) {
            Fcr = Math.pow(Math.PI, 2) * E / Math.pow((L / rts), 2) * Math.sqrt(1 + 0.078 * JIx / Sy /
                    (H - tf) * Math.pow((L / rts), 2));
            Mn = Fcr * Sy;
        }else {
            Mn = (Mp - (Mp - 0.7 * Fy *Sy) * (L - Lp) / (Lr - Lp));
        }
        if(MnMajor == Mn) {
            CbStatus = true;
        }
        MnMajor = 0.9 * MnMajor / 1000 / 1000;

        //Momen Minor
        Mp = Math.min(Fy * Zz, 1.6 * Fy * Sz);
        MnMinor = Mp;
        if(sayap > (0.95 * EFykc)) {
            Mn = 0.9 * E * kc * Sz / sayap;
        }else {
            Mn = (Mp - (Mp - 0.7 * Fy *Sz) * (sayap - 0.38 * EFy) / (0.95 * EFykc - 0.38 * EFy));
        }
        MnMinor = Math.min(Mn, MnMinor);
        if(sayap > EFy) {
            Mn = 0.9 * E * kc * Sz / sayap;
        }else {
            Mn = (Mp - (Mp - 0.7 * Fy *Sz) * (sayap - 0.38 * EFy) / (EFy - 0.38 * EFy));
        }
        MnMinor = 0.9 * Math.min(Mn, MnMinor) / 1000 / 1000;

        //Geser Major
        kv = 5;
        if(badan <= 2.24 * EFy && badan < 260) {
            if (badan > 1.37 * Math.sqrt(kv) * EFy) {
                Cv = 1.51 * kv * E / Math.pow(badan, 2) / Fy;
            } else {
                Cv = Math.min(1.1 * Math.sqrt(kv) * EFy / badan, 1);
            }
            VnMajor = 0.9 * 0.6 * Fy * (H * tw) * Cv / 1000;
        }else {
            VnMajor = 1E-20; //jane 0 coz profil gaboleh dipake, but males ntar bakal ada overflow klo diisi 0
        }

        //Geser Minor
        kv = 1.2;
        if(sayap < 2.24 * EFy && sayap < 260) {
            if (sayap > 1.37 * Math.sqrt(kv) * EFy) {
                Cv = 1.51 * kv * E / Math.pow(sayap, 2) / Fy;
            } else {
                Cv = Math.min(1.1 * Math.sqrt(kv) * EFy / sayap, 1);
            }
            VnMinor = 0.9 * 0.6 * Fy * (B / 2 * tf) * Cv / 1000;
        }else {
            VnMinor = 1E-20; //jane 0 coz profil gaboleh dipake, but males ntar bakal ada overflow klo diisi 0
        }
    }

    public void setCapacityL(int i, double L) {
        double EFy = Math.sqrt(E / Fy);
        double sayap = B / tf;
        double badan = H / tw;
        double kc = Math.min(0.76, Math.max(0.35, (4 / Math.sqrt(badan))));
        double EFykc = Math.sqrt(kc * E / Fy);
        double Qs;
        double Fcr;
        double Mn;
        double kv;
        double Cv;
        double Qa;

        //Tarik
        PnT = Math.min(0.9 * Fy * A, 0.75 * Fu * A) / 1000;

        //Tekan
        if (sayap > 0.91 * EFy) {
            Qs = 0.53 * E / Fy / Math.pow(sayap, 2);
        } else {
            Qs = Math.min(1.34 - 0.76 * sayap / EFy, 1);
        }
        Qa = 1;

        double Fe = Math.pow(Math.PI, 2) * E / (0.8 * L / rz);
        if (Qa * Qs * Fy / Fe <= 2.25) {
            Fcr = Qa * Qs * Math.pow(0.658, (Qa * Qs * Fy / Fe)) * Fy;
        } else {
            Fcr = 0.877 * Fe;
        }
        PnC = 0.9 * Fcr * A / 1000;

        //Momen Major
        Mn = Fy * Math.min(Zy, Sy * 1.5);
        MnMajor = Mn;
        double Mee = 0.46 * E * (H * B) * (tw * tf) / L;
        MpMajor = Mee;
        double Sc = 0.8 * Sy;
        Fcr = 0.71 * E / Math.pow(Math.max(sayap, badan), 2);
        if (sayap > 0.91 * EFy) {
            Mn = Fcr * Sc;
        } else if (sayap > 0.54 * EFy) {
            Mn = Fy * Sc * (2.43 - 1.72 * sayap / EFy);
        }
        MnMajor = Math.min(Mn, MnMajor);
        if (badan > 0.91 * EFy) {
            Mn = Fcr * Sc;
        } else if (badan > 0.54 * EFy) {
            Mn = Fy * Sc * (2.43 - 1.72 * badan / EFy);
        }
        MnMajor = Math.min(Mn, MnMajor);
        MnMajor = 0.9 * MnMajor / 1000 / 1000;

        //Momen Minor
        Mn = Fy * Math.min(Zz, Sz * 1.5);
        MnMinor = Mn;
        Mee = 0.46 * E * (H * B) * (tw * tf) / L;
        MpMinor = Mee;
        Sc = 0.8 * Sy;
        Fcr = 0.71 * E / Math.pow(Math.max(sayap, badan), 2);
        if (sayap > 0.91 * EFy) {
            Mn = Fcr * Sc;
        } else if (sayap > 0.54 * EFy) {
            Mn = Fy * Sc * (2.43 - 1.72 * sayap / EFy);
        }
        MnMinor = Math.min(Mn, MnMinor);
        if (badan > 0.91 * EFy) {
            Mn = Fcr * Sc;
        } else if (badan > 0.54 * EFy) {
            Mn = Fy * Sc * (2.43 - 1.72 * badan / EFy);
        }
        MnMinor = Math.min(Mn, MnMinor);
        MnMinor = 0.9 * MnMinor / 1000 / 1000;

        //Geser Major
        kv = 1.2;
        if(badan < 2.24 * EFy && badan < 260) {
            if(badan >1.37 * Math.sqrt(kv) * EFy) {
                Cv = 1.51 * kv * E / Math.pow(badan,2) / Fy;
            }else {
                Cv = Math.min(1.1 * Math.sqrt(kv) * EFy / badan, 1);
            }
            if(Fy <345) {
                Cv = 1;
            }
            VnMajor = 0.9 * 0.6 * Fy * (H * tw) *Cv / 1000;
        }else {
            VnMajor = 1E-20; //jane 0 coz profil gaboleh dipake, but males ntar bakal ada overflow klo diisi 0
        }

        //Geser Minor
        kv = 1.2;
        if(sayap < 2.24 * EFy && sayap < 260){
            if(sayap > 1.37 * Math.sqrt(kv) * EFy) {
                Cv = 1.51 * kv * E / Math.pow(sayap, 2) / Fy;
            }else {
                Cv = Math.min(1.1 * Math.sqrt(kv) * EFy / sayap, 1);
            }
            if(Fy < 345) { Cv = 1; }
            VnMinor = 0.9 * 0.6 * Fy * (B * tf) * Cv / 1000;
        }else{
            VnMinor = 1E-20; //jane 0 coz profil gaboleh dipake, but males ntar bakal ada overflow klo diisi 0
        }
    }

    public void setCapacityI(int i, double L) {
        setCapacityWF(i,L);
    }

    public void setCapacityC(int i, double L) {
        double EFy = Math.sqrt(E / Fy);
        double sayap = B / tf;
        double badan = (H - 2 * tf - 2 * rmin) / tw; //cek lagi
        double kc = Math.min(0.76, Math.max(0.35, (4 / Math.sqrt(badan))));
        double EFykc = Math.sqrt(kc * E / Fy);
        double Qs;
        double Fcr;
        double Mn;
        double kv;
        double Cv;
        double Qa;
        double alph = 1.0/(2+(this.H -this.tf)*this.tw/3/this.tf/(this.B - this.tw/2));
        Cw = Math.pow((this.H -this.tf), 2)*Math.pow((this.B - this.tw/2), 2) * this.tf*
                ((1-3*alph)/6+Math.pow(alph,2)/2*(1+(this.H -this.tf)*this.tw/6.0/this.tf/(this.B - this.tw/2)));

        //Tarik
        PnT = Math.min(0.9 * Fy * A, 0.75 * Fu * A) / 1000;

        //Tekan
        if(sayap > 1.03 * EFy) {
            Qs = 0.69 * E / Fy / Math.pow(sayap,2);
        }else {
            Qs = Math.min(1.415 - 0.74 * sayap / EFy, 1);
        }
        double He = Math.min(1.92 * tw * EFy * (1 - 0.34 / badan * EFy), (H - 2 * tf));
        Qa = (B * (He + 2 * tf) - (B - tw) * He) / A;
        double Fe = Math.pow(Math.PI,2) * E / (0.8 * L / rz);
        if(Qa * Qs * Fy / Fe <= 2.25) {
            Fcr = Qa * Qs * Math.pow(0.658,(Qa * Qs * Fy / Fe)) * Fy;
        }else {
            Fcr = 0.877 * Fe;
        }
        PnC = 0.9 * Fcr * A / 1000;

        //Momen Major
        double Mp = Fy * Zy;
        MnMajor = Mp;
        MpMajor = Mp;
        double Lp = 1.76 * rz * EFy;
        double rts = Math.sqrt(Math.sqrt(Iz * Cw) / Sy);
        double Lr = 1.95 * rts * E / (0.7 * Fy) * Math.sqrt(JIx / Sy / (H - tf)
                + Math.sqrt(Math.pow((JIx / Sy / (H - tf)),2) + 6.76 * Math.pow((0.7 * Fy / E),2)));
        if(sayap > 0.95 * EFykc) {
            Mn = 0.9 * E * kc * Sy / sayap;
        }else {
            Mn = (Mp - (Mp - 0.7 * Fy *Sy) * (sayap - 0.38 * EFy) / (0.95 * EFykc - 0.38 * EFy));
        }
        MnMajor = Math.min(Mn, MnMajor);
        if(sayap > EFy) {
            Mn = 0.9 * E * kc * Sy / sayap;
        }else {
            Mn = (Mp - (Mp - 0.7 * Fy *Sy) * (sayap - 0.38 * EFy) / (EFy - 0.38 * EFy));
        }
        MnMajor = Math.min(Mn, MnMajor);
        if(L > Lr) {
            Fcr = Math.pow(Math.PI,2) * E / Math.pow((L / rts),2)
                    * Math.sqrt(1 + 0.078 *JIx / Sy / (H - tf) *Math.pow((L / rts),2));
            Mn = Fcr * Sy;
        }else {
            Mn = (Mp - (Mp - 0.7 * Fy *Sy) * (L - Lp) / (Lr - Lp));
        }
        MnMajor = Math.min(Mn, MnMajor);
        if(MnMajor == Mn) { CbStatus = true; }
        MnMajor = 0.9 * MnMajor / 1000 / 1000;

        //Momen Minor
        Mp = Math.min(Fy * Zz, 1.6 * Fy * Sz);
        MnMinor = Mp;
        if(sayap > 0.95 * EFykc) {
            Mn = 0.9 * E * kc * Sz / sayap;
        }else {
            Mn = (Mp - (Mp - 0.7 * Fy *Sz) * (sayap - 0.38 * EFy) / (0.95 * EFykc - 0.38 * EFy));
        }
        MnMinor = Math.min(Mn, MnMinor);
        if(sayap > EFy) {
            Mn = 0.9 * E * kc * Sz / sayap;
        }else {
            Mn = (Mp - (Mp - 0.7 * Fy *Sz) * (sayap - 0.38 * EFy) / (EFy - 0.38 * EFy));
        }
        MnMinor = 0.9 * Math.min(Mn, MnMinor) / 1000 / 1000;

        //Geser Major
        kv = 5;
        if(badan < 2.24 * EFy && badan < 260){
            if(badan > 1.37 * Math.sqrt(kv) * EFy) {
                Cv = 1.51 * kv * E / Math.pow(badan,2) / Fy;
            }else {
                Cv = Math.min(1.1 * Math.sqrt(kv) * EFy / badan, 1);
            }
            if(Fy < 345){
                Cv = 1;
            }
            VnMajor = 0.9 * 0.6 * Fy * (H * tw) * Cv / 1000;
        }else {
            VnMajor = 1E-20; //jane 0 coz profil gaboleh dipake, but males ntar bakal ada overflow klo diisi 0
        }

        //Geser Minor
        kv = 1.2;
        if(sayap < 2.24 * EFy && sayap < 260) {
            if (sayap > 1.37 * Math.sqrt(kv) * EFy) {
                Cv = 1.51 * kv * E / Math.pow(sayap, 2) / Fy;
            } else {
                Cv = Math.min(1.1 * Math.sqrt(kv) * EFy / sayap, 1);
            }
            if (Fy < 345) {
                Cv = 1;
            }
            VnMinor = 0.9 * 0.6 * Fy * (B / 2 * tf) * Cv / 1000;
        }else {
            VnMinor = 1E-20; //jane 0 coz profil gaboleh dipake, but males ntar bakal ada overflow klo diisi 0
        }
    }

    public double getLoadFrameDead() {
        return loadFrameDead;
    }

    public double getSy() {
        if(!isAlphaRotated){return Sy;}else{return Sz;}
    }

    public double getIy() {
        if(!isAlphaRotated){return Iy;}else{return Iz;}
    }

    public double getIz() {
        if(!isAlphaRotated){return Iz;}else{return Iy;}
    }

    public double getSz() {
        if(!isAlphaRotated){return Sz;}else{return Sy;}
    }

    public double getJIx() {
        return JIx;
    }

    public double getA() {
        return A;
    }

    //public void setA(double a) { A = a; }

    public double getG() {
        return G;
    }

    public double getDepth() {
        return H;
    }

    public double getWidth() { return B; }

    public void setWidth(float width) { this.B = width; }

    public double getE() { return E; }

    public double getDensity() { return steelDensity; }

    public String getSectionCode() { return sectionCode; }

    public String getSectionName() { return sectionName; }

    public String getSteelType() { return steelType; }

    public String getSteelName() { return steelName; }

    public double getYnol() {
        return ynol;
    }

    public void setYnol(double ynol) {
        this.ynol = ynol;
    }

    public double getZnol() {
        return znol;
    }

    public void setZnol(double znol) {
        this.znol = znol;
    }

    public double getPnC() {
        return PnC;
    }

    public void setPnC(double pnC) {
        PnC = pnC;
    }

    public double getPnT() {
        return PnT;
    }

    public void setPnT(double pnT) {
        PnT = pnT;
    }

    public double getVnMajor() {
        if(!isAlphaRotated){
            return VnMajor;
        }else{
            return VnMinor;
        }
    }

    public void setVnMajor(double vnMajor) {
        VnMajor = vnMajor;
    }

    public double getVnMinor() {
        if(!isAlphaRotated){
            return VnMinor;
        }else{
            return VnMajor;
        }
    }

    public void setVnMinor(double vnMinor) {
        VnMinor = vnMinor;
    }

    public double getMnMajor() {
        return MnMajor;
    }

    public void setMnMajor(double mnMajor) {
        MnMajor = mnMajor;
    }

    public double getMpMajor() {
        return MpMajor;
    }

    public void setMpMajor(double mpMajor) {
        MpMajor = mpMajor;
    }

    public double getMnMinor() {
        return MnMinor;
    }

    public void setMnMinor(double mnMinor) {
        MnMinor = mnMinor;
    }

    public double getMpMinor() {
        return MpMinor;
    }

    public void setMpMinor(double mpMinor) {
        MpMinor = mpMinor;
    }

    public boolean getCbStatus() {
        return CbStatus;
    }

    public void setCbStatus(boolean cbStatus) {
        CbStatus = cbStatus;
    }

    public double getFy() {
        return Fy;
    }

    public void setFy(double fy) {
        Fy = fy;
    }

    public double getTw() {
        return tw;
    }

    public void setTw(double tw) {
        this.tw = tw;
    }

    public double getTf() {
        return tf;
    }

    public void setTf(double tf) {
        this.tf = tf;
    }

    public boolean isAlphaRotated() {
        return isAlphaRotated;
    }

    public void setMnWithCb(double CbMajor, double CbMinor) {
        if (steelType.equals("WF") || steelType.equals("C") || steelType.equals("I")) {
            if(!isAlphaRotated()) {
                MnMajorBatang = MnMajor;
                if (CbStatus) {
                    MnMajorBatang = Math.min(MnMajor*CbMajor,MpMajor);
                }
                MnMinorBatang = MnMinor;
            }else{
                MnMinorBatang = MnMajor;
                if (CbStatus) {
                    MnMinorBatang = Math.min(MnMajor*CbMinor,MpMajor);
                }
                MnMajorBatang = MnMinor;
            }
        } else if (steelType.equals("L")) {
            if(!isAlphaRotated()) {
                if (MpMajor < Sy * Fy) {
                    MnMajorBatang = Math.min(MnMajor, (0.92 - 0.17 * MpMajor * Math.max(CbMajor, 1.5) / (Sy * Fy)) * MpMajor * Math.max(CbMajor, 1.5));
                } else {
                    MnMajorBatang = Math.min(Math.min(MnMajor, (1.92 - 1.17 * Math.sqrt((Sy * Fy) / MpMajor / Math.max(CbMajor, 1.5))) * (Sy * Fy)), 1.5 * (Sy * Fy));
                }
                if (MpMajor < Sy * Fy) {
                    MnMinorBatang = Math.min(MnMinor, (0.92 - 0.17 * MpMinor * Math.max(CbMinor, 1.5) / (Sz * Fy)) * MpMinor * Math.max(CbMinor, 1.5));
                } else {
                    MnMinorBatang = Math.min(Math.min(MnMinor, (1.92 - 1.17 * Math.sqrt((Sz * Fy) / MpMinor / Math.max(CbMinor, 1.5))) * (Sz * Fy)), 1.5 * (Sz * Fy));
                }
            }else{
                if (MpMajor < Sy * Fy) {
                    MnMinorBatang = Math.min(MnMajor, (0.92 - 0.17 * MpMajor * Math.max(CbMinor, 1.5) / (Sy * Fy)) * MpMajor * Math.max(CbMinor, 1.5));
                } else {
                    MnMinorBatang = Math.min(Math.min(MnMajor, (1.92 - 1.17 * Math.sqrt((Sy * Fy) / MpMajor / Math.max(CbMinor, 1.5))) * (Sy * Fy)), 1.5 * (Sy * Fy));
                }
                if (MpMajor < Sy * Fy) {
                    MnMajorBatang = Math.min(MnMinor, (0.92 - 0.17 * MpMinor * Math.max(CbMajor, 1.5) / (Sz * Fy)) * MpMinor * Math.max(CbMajor, 1.5));
                } else {
                    MnMajorBatang = Math.min(Math.min(MnMinor, (1.92 - 1.17 * Math.sqrt((Sz * Fy) / MpMinor / Math.max(CbMajor, 1.5))) * (Sz * Fy)), 1.5 * (Sz * Fy));
                }
            }
        }
    }

    public double getMnMajorBatang() {
        return MnMajorBatang;
    }

    public double getMnMinorBatang() {
        return MnMinorBatang;
    }
}