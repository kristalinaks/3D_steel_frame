package com.cemapp.steelframe3D;

import android.provider.BaseColumns;

public final class F_ProjectContract {

    //To prevent someone from accidentally instantiating the contract class
    //Make sure the constructor private
    private F_ProjectContract(){}

    //Inner class that define the table contents
    public static final class ProjectEntry implements BaseColumns {

        public static final String NOMOR = "nomor";

        //Project
        public static final String TABLE_PROJECT = "project_info";
        public static final String ID_PROJECT = "id_project";
        public static final String KODE_PROJECT = "kode_project";
        public static final String NAME_PROJECT = "name_project";
        public static final String ENGINEER = "engineer";

        //Analysis
        public static final String TABLE_ANALYSIS = "analysis_info";
        public static final String ID_ANALYSIS = "id_analysis";
        public static final String KODE_ANALYSIS = "kode_analysis";
        public static final String NAME_ANALYSIS = "name_analysis";
        public static final String NST = "nst";
        public static final String STH = "sth";
        public static final String NBX = "nbx";
        public static final String BWX = "bwx";
        public static final String NBY = "nby";
        public static final String BWY = "bwy";
        public static final String SENDI = "sendi";
        public static final String FY = "fy";
        public static final String FU = "fu";
        public static final String E = "e";

        //Joint Load
        public static final String TABLE_JOINT_LOAD = "joint_load";
        public static final String NODAL = "nodal";
        public static final String FGX = "fgx";
        public static final String FGY = "fgy";
        public static final String FGZ = "fgz";
        public static final String MGX = "mgx";
        public static final String MGY = "mgy";
        public static final String MGZ = "mgz";

        //Frame Load & Assign
        public static final String TABLE_FRAME_LOAD_AND_ASSIGN = "frame_load";
        public static final String FRAME = "frame";
        public static final String LGX = "lgx";
        public static final String LGY = "lgy";
        public static final String LGZ = "lgz";

        //Area Load & Assign
        public static final String TABLE_AREA_LOAD_AND_ASSIGN = "area_load";
        public static final String AREA = "area";
        public static final String UAL = "ual";

        //Frame Section
        public static final String TABLE_FRAME_SECTION = "frame_section";
        public static final String KODE_FRAME_SECTION = "kode_frame_section";
        public static final String NAME_FRAME_SECTION = "name_frame_section";
        public static final String ROTATE_ALPHA = "rotate_alpha";
        public static final String TYPE_STEEL = "type_steel";
        public static final String NAME_STEEL = "name_steel";
        public static final String SECTION = "section";

        //Assign Frame Section
        public static final String TABLE_ASSIGN_SECTION = "assign_section";
        public static final String NO_FRAME = "frame_number";

        //Earth Quake
        public static final String TABLE_EARTH_QUAKE = "dynamic_load";
        public static final String SS = "ss";
        public static final String S1 = "s1";
        public static final String IE = "ie";
        public static final String R = "r";
        public static final String SITUS = "situs";
        public static final String AXIS = "axis";
        public static final String IS_X_POSITIF = "is_x_positif";
        public static final String IS_Y_POSITIF = "is_y_positif";

        //Design
        public static final String TABLE_DESIGN = "design_info";
        public static final String ID_DESIGN = "id_design";
        public static final String KODE_DESIGN = "kode_design";
        public static final String NAME_DESIGN = "name_design";

        //Design Section
        public static final String TABLE_DESIGN_SECTION_COLUMN = "design_section_column";
        public static final String TABLE_DESIGN_SECTION_BEAMX = "design_section_beamx";
        public static final String TABLE_DESIGN_SECTION_BEAMY = "design_section_beamy";
    }
}