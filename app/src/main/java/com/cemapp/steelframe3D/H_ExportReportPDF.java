package com.cemapp.steelframe3D;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;

public class H_ExportReportPDF {
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;

    private Font fontHelvetica = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private Font fontHelveticaBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

    private static float counterLine = 0;
    private static float pageWidth = 595;
    private static float pageHeight = 841.5f;
    private static float marginLeftTop = 56;
    private static float marginRightBottom = 42;
    private static float initialCounterLine = marginLeftTop + marginRightBottom;
    private static int counterPageNumber = 0;
    private static boolean isReportAnalysis;

    public H_ExportReportPDF(Context context) {
        this.context = context;
    }

    public void createAnalysisReport() throws IOException, DocumentException {
        counterPageNumber = 0;
        isReportAnalysis = true;
        openDocument();
        addMetaData();

        addCover();
        addTitleProject();
        addA_ModelGeometry();
        addA_1_FrameBuildingsDimensions();
        addA_2_MaterialProperties();
        addA_3_JointCoordinate();
        addA_4_FrameCoordinate();
        addA_5_AreaCoordinate();
        addB_Joint();
        addC_Frame();
        addD_Area();
        addE_EarthQuake();
        addF_AnalysisResult();
        addG_Safety_Factor_Check();

        closeDocument();
        viewPDF();
    }

    public void createDesignReport() throws IOException, DocumentException {
        counterPageNumber = 0;
        isReportAnalysis = false;
        openDocument();
        addMetaData();

        addCover();
        addTitleProject();
        addA_ModelGeometry();
        addA_1_FrameBuildingsDimensions();
        addA_2_MaterialProperties();
        addA_3_JointCoordinate();
        addA_4_FrameCoordinate();
        addB_Frame_Section_Properties();
        addC_Frame_Section_Assignment();

        closeDocument();
        viewPDF();
    }

    private void openDocument() throws IOException, DocumentException {
        createFile();

        Rectangle pageSize = new Rectangle(pageWidth, pageHeight, 0);

        document = new Document();
        document.setPageSize(pageSize);
        document.setMargins(marginLeftTop, marginRightBottom, marginLeftTop, marginRightBottom);
        counterLine = initialCounterLine;
        pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        document.open();
    }

    private void createFile() throws IOException {
        //folder terluar
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "CEMApp 3D Frame");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        //folder project
        File folderProject = new File(folder, D_MainActivity.projectName);
        if (!folderProject.exists()){
            folderProject.mkdirs();
        }

        //folder analysis
        File folderAnalysis = new File(folderProject, D_MainActivity.analysisName);
        if(!folderAnalysis.exists()){
            folderAnalysis.mkdirs();
        }

        //file analysis / design
        if(isReportAnalysis) {
            pdfFile = new File(folderAnalysis, D_MainActivity.analysisName + ".pdf");
            if (!pdfFile.exists()) {
                pdfFile.createNewFile();
            }
        }else{
            pdfFile = new File(folderAnalysis, D_MainActivity.designName + ".pdf");
            if (!pdfFile.exists()) {
                pdfFile.createNewFile();
            }
        }
    }

    private void addMetaData(){
        if(isReportAnalysis) {
            document.addTitle("Analysis");
            document.addSubject("CEMApp Steel Frame");
            document.addAuthor("KristalinaKS");
        }else{
            document.addTitle("Design");
            document.addSubject("CEMApp Steel Frame");
            document.addAuthor("KristalinaKS");
        }
    }

    private void addCover(){
        try {
            paragraph = new Paragraph(" ", fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(50);
            document.add(paragraph);

            /*InputStream ims = context.getAssets().open("Logo CEMApp 3D Frame Blue.png");
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            Image image = Image.getInstance(stream.toByteArray());
            image.scaleToFit(200,200);
            image.setAlignment(Element.ALIGN_CENTER);
            image.setSpacingBefore(500);
            document.add(image);*/

            paragraph = new Paragraph("CEMApp Steel Frame", fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.getFont().setSize(30);
            paragraph.getFont().setStyle(Font.BOLD);
            document.add(paragraph);

            if(isReportAnalysis) {
                paragraph = new Paragraph("Structural Analysis Report", fontHelveticaBold);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.getFont().setSize(25);
                paragraph.getFont().setStyle(Font.BOLD);
                paragraph.setSpacingBefore(150);
                document.add(paragraph);
            }else{
                paragraph = new Paragraph("Structural Design Report", fontHelveticaBold);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.getFont().setSize(25);
                paragraph.getFont().setStyle(Font.BOLD);
                paragraph.setSpacingBefore(150);
                document.add(paragraph);
            }

            paragraph = new Paragraph("Prepared by", fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.getFont().setSize(12);
            paragraph.getFont().setStyle(Font.NORMAL);
            paragraph.setSpacingBefore(10);
            document.add(paragraph);

            paragraph = new Paragraph(D_MainActivity.projectEngineer, fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.getFont().setSize(18);
            paragraph.getFont().setStyle(Font.BOLD);
            document.add(paragraph);

            paragraph = new Paragraph("Project Name", fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.getFont().setSize(12);
            paragraph.getFont().setStyle(Font.NORMAL);
            paragraph.setSpacingBefore(15);
            document.add(paragraph);

            paragraph = new Paragraph(D_MainActivity.projectName, fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.getFont().setSize(20);
            paragraph.getFont().setStyle(Font.BOLD);
            document.add(paragraph);

            paragraph = new Paragraph("Analysis Name", fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.getFont().setSize(12);
            paragraph.getFont().setStyle(Font.NORMAL);
            paragraph.setSpacingBefore(15);
            document.add(paragraph);

            paragraph = new Paragraph(D_MainActivity.analysisName, fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.getFont().setSize(20);
            paragraph.getFont().setStyle(Font.BOLD);
            document.add(paragraph);

            if(!isReportAnalysis){
                paragraph = new Paragraph("Design Name", fontHelveticaBold);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.getFont().setSize(12);
                paragraph.getFont().setStyle(Font.NORMAL);
                paragraph.setSpacingBefore(15);
                document.add(paragraph);

                paragraph = new Paragraph(D_MainActivity.designName, fontHelveticaBold);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.getFont().setSize(20);
                paragraph.getFont().setStyle(Font.BOLD);
                document.add(paragraph);
            }

            Calendar calendar = Calendar.getInstance();

            int date = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            String tanggal = date + " ";

            switch (month){
                case 0:
                    tanggal += "January ";
                    break;
                case 1:
                    tanggal += "February ";
                    break;
                case 2:
                    tanggal += "March ";
                    break;
                case 3:
                    tanggal += "April ";
                    break;
                case 4:
                    tanggal += "May ";
                    break;
                case 5:
                    tanggal += "June ";
                    break;
                case 6:
                    tanggal += "July ";
                    break;
                case 7:
                    tanggal += "August ";
                    break;
                case 8:
                    tanggal += "September ";
                    break;
                case 9:
                    tanggal += "October ";
                    break;
                case 10:
                    tanggal += "November ";
                    break;
                case 11:
                    tanggal += "December ";
                    break;
            }

            tanggal += Integer.toString(year);

            paragraph = new Paragraph(tanggal, fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.getFont().setSize(18);
            paragraph.getFont().setStyle(Font.BOLD);
            paragraph.setSpacingBefore(50);
            document.add(paragraph);

            document.newPage();

            //Set Font Normal
            fontHelveticaBold.setSize(12);
            fontHelveticaBold.setStyle(Font.BOLD);

        }catch (Exception e) {
            Log.e("addCover", e.toString());
        }
    }

    private void addTitleProject(){
        try{
            addHeaderFooter();

            if(isReportAnalysis) {
                paragraph = new Paragraph("STRUCTURAL ANALYSIS REPORT", fontHelveticaBold);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.getFont().setSize(16);
                document.add(paragraph);
                addCounterLine(paragraph);
            }else{
                paragraph = new Paragraph("STRUCTURAL DESIGN REPORT", fontHelveticaBold);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.getFont().setSize(16);
                document.add(paragraph);
                addCounterLine(paragraph);
            }

            paragraph = new Paragraph(D_MainActivity.projectName, fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.getFont().setSize(16);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph(D_MainActivity.analysisName, fontHelveticaBold);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.getFont().setSize(16);
            document.add(paragraph);
            addCounterLine(paragraph);

            if(!isReportAnalysis){
                paragraph = new Paragraph(D_MainActivity.designName, fontHelveticaBold);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.getFont().setSize(16);
                document.add(paragraph);
                addCounterLine(paragraph);
            }

            //Set Font Normal
            fontHelveticaBold.setSize(12);

        }catch (Exception e){
            Log.e("addTitleProject", e.toString());
        }
    }

    private void addA_ModelGeometry(){
        try{
            paragraph = new Paragraph("A. Model Geometry and Steel Properties", fontHelveticaBold);
            paragraph.setSpacingBefore(15);
            paragraph.getFont().setSize(14);
            document.add(paragraph);
            addCounterLine(paragraph);

            //Set Font Normal
            fontHelveticaBold.setSize(12);

        }catch (Exception e){
            Log.e("addModelGeometry", e.toString());
        }
    }

    private void addA_1_FrameBuildingsDimensions(){
        try{
            paragraph = new Paragraph("1. Frame Building Dimensions", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Number of Stories", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Chunk(Integer.toString(D_MainActivity.Nst)));
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Number of Bays, X", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Chunk(Integer.toString(D_MainActivity.Nbx)));
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Number of Bays, Y", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Chunk(Integer.toString(D_MainActivity.Nby)));
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Story Height (m)", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Chunk(": "));
            paragraph.add(new Chunk(Float.toString(D_MainActivity.Sth)));
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Bay Width, X (m)", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Chunk(": "));
            paragraph.add(new Chunk(Float.toString(D_MainActivity.Bwx)));
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Bay Width, Y (m)", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Chunk(": "));
            paragraph.add(new Chunk(Float.toString(D_MainActivity.Bwy)));
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Restraint", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            if(D_MainActivity.isRestraintPin){
                paragraph.add(new Chunk(": Pin"));
            }else{
                paragraph.add(new Chunk(": Fixed"));
            }

            document.add(paragraph);
            addCounterLine(paragraph);

        }catch (Exception e){
            Log.e("addA_1", e.toString());
        }
    }

    private void addA_2_MaterialProperties(){
        try{
            paragraph = new Paragraph("2. Steel Properties", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Fy (MPa)", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Chunk(": "));
            paragraph.add(new Chunk(Float.toString(D_MainActivity.bajaFy)));
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Fu (MPa)", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Chunk(": "));
            paragraph.add(new Chunk(Float.toString(D_MainActivity.bajaFu)));
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("E (MPa)", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Chunk(": "));
            paragraph.add(new Chunk(Float.toString(D_MainActivity.bajaE)));
            document.add(paragraph);
            addCounterLine(paragraph);

        }catch (Exception e){
            Log.e("addA_2", e.toString());
        }
    }

    private void addA_3_JointCoordinate(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("3. Joint Coordinate", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 1 - Joint Coordinate", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Joint", "X", "Y", "Z"};
            String[] note = {"", "m", "m", "m"};
            float[] widthC = {1, 1, 1, 1};
            int widthPercentage = 60;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexC; //Column
            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.koorNodeReal.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 1 - Joint Coordinate (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                for(indexC=0; indexC<3; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(Float.toString(D_MainActivity.koorNodeReal[indexR][indexC] / 1000), fontHelvetica));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfPCell.setFixedHeight(20);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addA_3", e.toString());
        }
    }

    private void addA_4_FrameCoordinate(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("4. Frame Coordinate", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 2 - Frame Coordinate", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Frame", "Joint", "Joint"};
            String[] note = {"", "i", "j"};
            float[] widthC = {1, 1, 1};
            int widthPercentage = 45;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.pointerElemenStruktur.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine > pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 2 - Frame Coordinate (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(D_MainActivity.pointerElemenStruktur[indexR][0] + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(D_MainActivity.pointerElemenStruktur[indexR][1] + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);
            }

            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addA_4", e.toString());
        }
    }

    private void addA_5_AreaCoordinate(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("5. Area Coordinate", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 3 - Area Coordinate", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Area", "Joint", "Joint", "Joint", "Joint"};
            String[] note = {"","i", "j", "k", "l"};
            float[] widthC = {1, 1, 1, 1, 1};
            int widthPercentage = 45;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);
            int indexR; //Row
            int indexC; //Column

            for(indexR=0; indexR<D_MainActivity.pointerAreaJoint.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine > pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 3 - Area Coordinate (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                for(indexC=0; indexC<4; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(Integer.toString(D_MainActivity.pointerAreaJoint[indexR][indexC]), fontHelvetica));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfPCell.setFixedHeight(20);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addA_5", e.toString());
        }
    }

    private void addB_Joint(){
        try{
            cekMinimalOneBarisBig();

            paragraph = new Paragraph("B. Joint", fontHelveticaBold);
            paragraph.setSpacingBefore(15);
            paragraph.getFont().setSize(14);
            document.add(paragraph);
            counterLine += 12 * 1.5f + 15;

            //Set Font Normal
            fontHelveticaBold.setSize(12);

        }catch (Exception e){
            Log.e("addJoint", e.toString());
        }

        addB_1_JointLoadAssignment();
    }

    private void addB_1_JointLoadAssignment(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("1. Joint Load Assignment", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 4 - Joint Load Assignment", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Joint", "FGX", "FGY", "FGZ", "MGX", "MGY", "MGZ"};
            String[] note = {"", "kN", "kN", "kN", "kNm", "kNm", "kNm"};
            float[] widthC = {1, 1, 1, 1, 1, 1, 1};
            int widthPercentage = 100;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexC; //Column
            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.koorNode.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 4 - Joint Load Assignment (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                for(indexC=0; indexC<6; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(Float.toString(D_MainActivity.jointLoad[indexR][indexC]), fontHelvetica));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfPCell.setFixedHeight(20);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addB_1", e.toString());
        }
    }

    private void addC_Frame(){
        try{
            cekMinimalOneBarisBig();

            paragraph = new Paragraph("C. Frame", fontHelveticaBold);
            paragraph.setSpacingBefore(15);
            paragraph.getFont().setSize(14);
            document.add(paragraph);
            counterLine += 12 * 1.5f + 15;

            //Set Font Normal
            fontHelveticaBold.setSize(12);

        }catch (Exception e){
            Log.e("addFrame", e.toString());
        }

        addC_1_FrameSectionProperties();
        addC_2_FrameSectionAssignment();
        addC_3_FrameLoadAssignment();
    }

    private void addC_1_FrameSectionProperties(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("1. Frame Section Properties", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 5 - Frame Section Properties", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Code", "Name", "Steel Name", "B", "H", "tw", "tf", "Rotated"};
            String[] note = {"", "", "", "mm", "mm", "mm", "mm", ""};
            float[] widthC = {1, 2.5f, 3, 1, 1, 1, 1, 1.5f};
            int widthPercentage = 100;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.listFrameSection.size(); indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 5 - Frame Section Properties (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(D_MainActivity.listFrameSection.get(indexR).getSectionCode(), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(D_MainActivity.listFrameSection.get(indexR).getSectionName(), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(D_MainActivity.listFrameSection.get(indexR).getSteelName(), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listFrameSection.get(indexR).getWidth()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listFrameSection.get(indexR).getDepth()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listFrameSection.get(indexR).getTw()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listFrameSection.get(indexR).getTf()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                boolean isAlpha = D_MainActivity.listFrameSection.get(indexR).isAlphaRotated();
                String alpha;
                if(isAlpha){
                    alpha = "TRUE";
                }else{
                    alpha = "FALSE";
                }

                pdfPCell = new PdfPCell(new Phrase(alpha, fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addB_1", e.toString());
        }
    }

    private void addC_2_FrameSectionAssignment(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("2. Frame Section Assignment", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 6 - Frame Section Assignment", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Frame", "Section"};
            String[] note = {"", "Code"};
            float[] widthC = {1, 1};
            int widthPercentage = 30;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.pointerElemenStruktur.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 6 - Frame Section Assignment (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[indexR]).getSectionCode(), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addB_1", e.toString());
        }
    }

    private void addC_3_FrameLoadAssignment(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("3. Frame Load Assignment", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 7 - Frame Load Assignment", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Frame", "LGX", "LGY", "LGZ"};
            String[] note = {"", "kN/m", "kN/m", "kN/m"};
            float[] widthC = {1, 1, 1, 1};
            int widthPercentage = 60;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexC; //Column
            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.pointerElemenStruktur.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 7 - Frame Load Assignment (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                for(indexC=0; indexC<3; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(Float.toString(D_MainActivity.frameLoad[indexR][indexC]), fontHelvetica));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfPCell.setFixedHeight(20);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addB_1", e.toString());
        }
    }

    private void addD_Area(){
        try{
            cekMinimalOneBarisBig();

            paragraph = new Paragraph("D. Area", fontHelveticaBold);
            paragraph.setSpacingBefore(15);
            paragraph.getFont().setSize(14);
            document.add(paragraph);
            counterLine += 12 * 1.5f + 15;

            //Set Font Normal
            fontHelveticaBold.setSize(12);

        }catch (Exception e){
            Log.e("addArea", e.toString());
        }

        addD_1_AreaLoadAssignment();
    }

    private void addD_1_AreaLoadAssignment(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("1. Area Load Assignment", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 8 - Area Load Assignment", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Area", "UAL"};
            String[] note = {"", "kN/m^2"};
            float[] widthC = {1, 1};
            int widthPercentage = 30;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.areaLoad.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 8 - Area Load Assignment (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Float.toString(D_MainActivity.areaLoad[indexR]), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addB_1", e.toString());
        }
    }

    private void addE_EarthQuake(){
        try{
            cekMinimalOneBarisBig();

            paragraph = new Paragraph("E. Earthquake (Static Equivalent)", fontHelveticaBold);
            paragraph.setSpacingBefore(15);
            paragraph.getFont().setSize(14);
            document.add(paragraph);
            counterLine += 12 * 1.5f + 15;

            //Set Font Normal
            fontHelveticaBold.setSize(12);

        }catch (Exception e){
            Log.e("addEQ", e.toString());
        }
        addE_1_EarthQuakeAssignment();
    }

    private void addE_1_EarthQuakeAssignment(){
        try{
            paragraph = new Paragraph("1. Earthquake Assignment", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            String axisgempa;
            if(D_MainActivity.isBebanGempaX){
                axisgempa = "x-axis";
            }else if(D_MainActivity.isBebanGempaY){
                axisgempa = "y-axis";
            }else{
                axisgempa = "none";
            }

            paragraph = new Paragraph("Axis", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Chunk(": "));
            paragraph.add(new Chunk(axisgempa));
            document.add(paragraph);
            addCounterLine(paragraph);

            if(!axisgempa.equals("none")){
                String Xdirect;
                String Ydirect;

                if(D_MainActivity.isGempaXPositif){
                    Xdirect = "positive";
                } else {
                    Xdirect = "negative";
                }

                if(D_MainActivity.isGempaYPositif){
                    Ydirect = "positive";
                } else {
                    Ydirect = "negative";
                }

                paragraph = new Paragraph("X earthquake direction", fontHelvetica);
                paragraph.setFirstLineIndent(40);
                paragraph.setTabSettings(new TabSettings(25));
                paragraph.add(Chunk.TABBING);
                paragraph.add(new Chunk(": "));
                paragraph.add(new Chunk(Xdirect));
                document.add(paragraph);
                addCounterLine(paragraph);

                paragraph = new Paragraph("Y earthquake direction", fontHelvetica);
                paragraph.setFirstLineIndent(40);
                paragraph.setTabSettings(new TabSettings(25));
                paragraph.add(Chunk.TABBING);
                paragraph.add(new Chunk(": "));
                paragraph.add(new Chunk(Ydirect));
                document.add(paragraph);
                addCounterLine(paragraph);

                paragraph = new Paragraph("Ss", fontHelvetica);
                paragraph.setFirstLineIndent(40);
                paragraph.setTabSettings(new TabSettings(25));
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(new Chunk(": "));
                paragraph.add(new Chunk(Double.toString(D_MainActivity.gempaSs)));
                document.add(paragraph);
                addCounterLine(paragraph);

                paragraph = new Paragraph("S1", fontHelvetica);
                paragraph.setFirstLineIndent(40);
                paragraph.setTabSettings(new TabSettings(25));
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(new Chunk(": "));
                paragraph.add(new Chunk(Double.toString(D_MainActivity.gempaS1)));
                document.add(paragraph);
                addCounterLine(paragraph);

                paragraph = new Paragraph("Ie", fontHelvetica);
                paragraph.setFirstLineIndent(40);
                paragraph.setTabSettings(new TabSettings(25));
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(new Chunk(": "));
                paragraph.add(new Chunk(Double.toString(D_MainActivity.gempaIe)));
                document.add(paragraph);
                addCounterLine(paragraph);

                paragraph = new Paragraph("R", fontHelvetica);
                paragraph.setFirstLineIndent(40);
                paragraph.setTabSettings(new TabSettings(25));
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(new Chunk(": "));
                paragraph.add(new Chunk(Double.toString(D_MainActivity.gempaR)));
                document.add(paragraph);
                addCounterLine(paragraph);

                paragraph = new Paragraph("Site", fontHelvetica);
                paragraph.setFirstLineIndent(40);
                paragraph.setTabSettings(new TabSettings(25));
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(Chunk.TABBING);
                paragraph.add(new Chunk(": "));
                paragraph.add(new Chunk(D_MainActivity.gempaSitus));
                document.add(paragraph);
                addCounterLine(paragraph);
            }

        }catch (Exception e){
            Log.e("addE_1", e.toString());
        }
    }

    private void addF_AnalysisResult(){
        try{
            cekMinimalOneBarisBig();

            paragraph = new Paragraph("F. Analysis Result", fontHelveticaBold);
            paragraph.setSpacingBefore(15);
            paragraph.getFont().setSize(14);
            document.add(paragraph);
            counterLine += 12 * 1.5f + 15;

            //Set Font Normal
            fontHelveticaBold.setSize(12);

        }catch (Exception e){
            Log.e("addAnalysisResult", e.toString());
        }

        addF_1_JointDisplacement();
        addF_2_JointReaction();
        addF_3_End_Force();
    }

    private void addF_1_JointDisplacement(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("1. Joint Displacement", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 9 - Joint Displacement", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Joint", "U1", "U2", "U3"};
            String[] note = {"", "mm", "mm", "mm"};
            float[] widthC = {1, 1, 1, 1};
            int widthPercentage = 60;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexC; //Column
            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.koorNode.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 9 - Joint Displacement (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                DecimalFormat df = new DecimalFormat("#.###");
                for(indexC=0; indexC<3; indexC++){
                    String U = df.format(Running.U_S[indexR * 6 + indexC]);

                    if(U.equals("-0")){
                        U = "0";
                    }

                    pdfPCell = new PdfPCell(new Phrase(U, fontHelvetica));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfPCell.setFixedHeight(20);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addA_2", e.toString());
        }
    }

    private void addF_2_JointReaction(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("2. Joint Reaction", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 10 - Joint Reaction", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Joint", "F1", "F2", "F3", "M1", "M2", "M3"};
            String[] note = {"", "kN", "kN", "kN", "kNm", "kNm", "kNm"};
            float[] widthC = {1, 1, 1, 1, 1, 1, 1};
            int widthPercentage = 100;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexC; //Column
            int indexR; //Row
            int tumpuan = (D_MainActivity.Nbx + 1) * (D_MainActivity.Nby + 1); //Special

            for(indexR=0; indexR<tumpuan; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 10 - Joint Reaction (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                DecimalFormat df = new DecimalFormat("#.###");
                for(indexC=0; indexC<6; indexC++){

                    String R;
                    if(indexC < 3){
                        R = df.format(Running.R_S[indexR * 6 + indexC] / 1000);
                    }else{
                        R = df.format(Running.R_S[indexR * 6 + indexC] / 1000 / 1000);
                    }

                    pdfPCell = new PdfPCell(new Phrase(R, fontHelvetica));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfPCell.setFixedHeight(20);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addA_2", e.toString());
        }
    }

    private void addF_3_End_Force(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("3. End Force", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 11 - End Force", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Frame", "Station", "P", "V2", "V3", "T", "M2", "M3"};
            String[] note = {"", "m", "kN", "kN", "kN", "kNm", "kNm", "kNm"};
            float[] widthC = {1, 1, 1, 1, 1, 1, 1, 1};
            int widthPercentage = 100;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexC; //Column
            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.pointerElemenStruktur.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 11 - End Force (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase("0", fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                DecimalFormat df = new DecimalFormat("#.###");
                for(indexC=0; indexC<6; indexC++){

                    String value;
                    if(indexC < 3){
                        value = df.format(Running.E_F[indexR][indexC] / 1000);
                    }else{
                        value = df.format(Running.E_F[indexR][indexC] / 1000 / 1000);
                    }

                    pdfPCell = new PdfPCell(new Phrase(value, fontHelvetica));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfPCell.setFixedHeight(20);
                    pdfPTable.addCell(pdfPCell);
                }

                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 13 - End Force (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(df.format(Running.E_L[indexR] / 1000), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                for(indexC=6; indexC<12; indexC++){

                    String value;
                    if(indexC < 9){
                        value = df.format(Running.E_F[indexR][indexC] / 1000);
                    }else{
                        value = df.format(Running.E_F[indexR][indexC] / 1000 / 1000);
                    }

                    pdfPCell = new PdfPCell(new Phrase(value, fontHelvetica));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfPCell.setFixedHeight(20);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addA_2", e.toString());
        }
    }

    private void addG_Safety_Factor_Check(){
        try{
            cekMinimalOneBarisBig();

            paragraph = new Paragraph("G. Safety Factor Check", fontHelveticaBold);
            paragraph.setSpacingBefore(15);
            paragraph.getFont().setSize(14);
            document.add(paragraph);
            counterLine += 12 * 1.5f + 15;

            //Set Font Normal
            fontHelveticaBold.setSize(12);

        }catch (Exception e){
            Log.e("addSF", e.toString());
        }

        addG_1_Safety_Factor();
    }

    private void addG_1_Safety_Factor(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("1. Safety Factor", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 12 - Safety Factor", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Frame", "Section", "P-M Safety Factor", "Shear Safety Factor"};
            String[] note = {"", "Code", "SF", "SF"};
            float[] widthC = {1, 1, 1.5f, 1.5f};
            int widthPercentage = 100;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.pointerElemenStruktur.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 12 - Safety Factor (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(D_MainActivity.listFrameSection.get(D_MainActivity.pointerFrameSection[indexR]).getSectionCode(), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                double amanPM = D_MainActivity.listBebanBatang.get(indexR).getAmanPMAnalysis();
                double amanV = D_MainActivity.listBebanBatang.get(indexR).getAmanVAnalysis();

                pdfPCell = new PdfPCell(new Phrase(new DecimalFormat("0.0000").format(amanPM), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(new DecimalFormat("0.0000").format(amanV), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addF_1", e.toString());
        }
    }

    //-----UNTUK DESIGN-----//
    private void addB_Frame_Section_Properties(){
        try{
            cekMinimalOneBarisBig();

            paragraph = new Paragraph("B. Frame Section Properties", fontHelveticaBold);
            paragraph.setSpacingBefore(15);
            paragraph.getFont().setSize(14);
            document.add(paragraph);
            counterLine += 12 * 1.5f + 15;

            //Set Font Normal
            fontHelveticaBold.setSize(12);

        }catch (Exception e){
            Log.e("addDesignSection", e.toString());
        }

        addB_1_Column_Section();
        addB_2_BeamX_Section();
        addB_3_BeamY_Section();
    }

    private void addB_1_Column_Section(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("1. Column Section Properties", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 4 - Column Section Properties", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Frame Section", "B", "H", "tw", "tf"};
            String[] note = {"", "mm", "mm", "mm", "mm"};
            float[] widthC = {3, 1, 1, 1, 1};
            int widthPercentage = 100;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.listDesignColSect2.size(); indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 4 - Column Section Properties (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(D_MainActivity.listDesignColSect2.get(indexR).getSectionName(), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignColSect2.get(indexR).getWidth()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignColSect2.get(indexR).getDepth()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignColSect2.get(indexR).getTw()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignColSect2.get(indexR).getTf()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addB_1Design", e.toString());
        }
    }

    private void addB_2_BeamX_Section(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("2. Beam X Section Properties", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 5 - Beam X Section Properties", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Frame Section", "B", "H", "tw", "tf"};
            String[] note = {"", "mm", "mm", "mm", "mm"};
            float[] widthC = {3, 1, 1, 1, 1};
            int widthPercentage = 100;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.listDesignBeamXSect2.size(); indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 5 - Beam X Section Properties (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(D_MainActivity.listDesignBeamXSect2.get(indexR).getSectionName(), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignBeamXSect2.get(indexR).getWidth()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignBeamXSect2.get(indexR).getDepth()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignBeamXSect2.get(indexR).getTw()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignBeamXSect2.get(indexR).getTf()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addB_2Design", e.toString());
        }
    }

    private void addB_3_BeamY_Section(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("3. Beam Y Section Properties", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 6 - Beam Y Section Properties", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Frame Section", "B", "H", "tw", "tf"};
            String[] note = {"", "mm", "mm", "mm", "mm"};
            float[] widthC = {3, 1, 1, 1, 1};
            int widthPercentage = 100;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.listDesignBeamYSect2.size(); indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 6 - Beam Y Section Properties (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(D_MainActivity.listDesignBeamYSect2.get(indexR).getSectionName(), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignBeamYSect2.get(indexR).getWidth()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignBeamYSect2.get(indexR).getDepth()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignBeamYSect2.get(indexR).getTw()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(Double.toString(D_MainActivity.listDesignBeamYSect2.get(indexR).getTf()), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);
            }
            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addB_3Design", e.toString());
        }
    }

    private void addC_Frame_Section_Assignment(){
        try{
            cekMinimalOneBarisBig();

            paragraph = new Paragraph("C. Frame Section Assignment", fontHelveticaBold);
            paragraph.setSpacingBefore(15);
            paragraph.getFont().setSize(14);
            document.add(paragraph);
            counterLine += 12 * 1.5f + 15;

            //Set Font Normal
            fontHelveticaBold.setSize(12);

        }catch (Exception e){
            Log.e("addDesignSection", e.toString());
        }
        addC_1_Frame_Section_Assignment();
    }

    private void addC_1_Frame_Section_Assignment(){
        try{
            cekMinimalOneBaris();

            paragraph = new Paragraph("1. Frame Section Assignment", fontHelveticaBold);
            paragraph.setSpacingBefore(7.5f);
            paragraph.setFirstLineIndent(15);
            document.add(paragraph);
            addCounterLine(paragraph);

            paragraph = new Paragraph("Table 7 - Frame Section Assignment", fontHelvetica);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            addCounterLine(paragraph);

            String[] header = {"Frame", "Section", "P-M Safety Factor", "Shear Safety Factor"};
            String[] note = {"", "C", "SF", "SF"};
            float[] widthC = {1, 2, 1.5f, 1.5f};
            int widthPercentage = 100;

            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            add_HeadNote(header, note, widthC, widthPercentage);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);

            int indexR; //Row

            for(indexR=0; indexR<D_MainActivity.pointerElemenStruktur.length; indexR++){
                counterLine += pdfPCell.getFixedHeight();

                if(counterLine >= pageHeight){
                    document.add(pdfPTable);

                    counterLine = initialCounterLine;
                    document.newPage();
                    addHeaderFooter();

                    pdfPTable = new PdfPTable(header.length);
                    pdfPTable.setWidthPercentage(widthPercentage);
                    pdfPTable.setWidths(widthC);

                    paragraph = new Paragraph("Table 7 - Frame Section Assignment (Continued)", fontHelvetica);
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    paragraph.setSpacingAfter(5f);
                    document.add(paragraph);
                    addCounterLine(paragraph);

                    add_HeadNote(header, note, widthC, widthPercentage);
                    counterLine += pdfPCell.getFixedHeight();
                }

                pdfPCell = new PdfPCell(new Phrase(Integer.toString(indexR + 1), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(D_MainActivity.listBebanBatang.get(indexR).getProfil(), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                double amanPM = D_MainActivity.listBebanBatang.get(indexR).getAmanPMDesign();
                double amanV = D_MainActivity.listBebanBatang.get(indexR).getAmanVDesign();

                pdfPCell = new PdfPCell(new Phrase(new DecimalFormat("0.0000").format(amanPM), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Phrase(new DecimalFormat("0.0000").format(amanV), fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);
            }

            document.add(pdfPTable);

            paragraph = new Paragraph("Note: section with * is rotated 90 degree", fontHelvetica);
            paragraph.setFirstLineIndent(40);
            paragraph.setTabSettings(new TabSettings(25));;
            document.add(paragraph);
            addCounterLine(paragraph);

        }catch (Exception e){
            Log.e("addF_1", e.toString());
        }
    }

    private void addHeaderFooter(){
        PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
        Phrase header;
        Phrase footer;

        //Set Font
        fontHelvetica.setSize(10);

        //Header Kiri
        header = new Phrase("Project Code : " + D_MainActivity.projectKode, fontHelvetica);
        ColumnText.showTextAligned( pdfContentByte,
                Element.ALIGN_LEFT,
                header,
                document.left(),
                document.top() + 10, 0);

        //Header Kanan
        header = new Phrase("CEMApp Steel Frame", fontHelvetica);
        ColumnText.showTextAligned( pdfContentByte,
                Element.ALIGN_RIGHT,
                header,
                document.right(),
                document.top() + 10, 0);

        //Footer Kiri
        footer = new Phrase(D_MainActivity.projectEngineer, fontHelvetica);
        ColumnText.showTextAligned( pdfContentByte,
                Element.ALIGN_LEFT,
                footer,
                document.left(),
                document.bottom() - 28, 0);

        //Footer Kanan
        footer = new Phrase("Page " + ++counterPageNumber, fontHelvetica);
        ColumnText.showTextAligned( pdfContentByte,
                Element.ALIGN_RIGHT,
                footer,
                document.right(),
                document.bottom() - 28, 0);

        //Set Font
        fontHelvetica.setSize(12);
    }

    private void add_HeadNote(String[] header, String[] note, float[] widthC, int widthPercentage){
        try{
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(widthPercentage);
            pdfPTable.setWidths(widthC);

            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setFixedHeight(20);
            int indexC; //Column

            indexC = 0;
            while (indexC < header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fontHelveticaBold));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPCell.setBackgroundColor(BaseColor.CYAN);
                pdfPTable.addCell(pdfPCell);
            }
            counterLine += pdfPCell.getFixedHeight();

            indexC = 0;
            while (indexC < note.length){
                pdfPCell = new PdfPCell(new Phrase(note[indexC++], fontHelvetica));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setFixedHeight(20);
                pdfPCell.setBackgroundColor(BaseColor.CYAN);
                pdfPTable.addCell(pdfPCell);
            }
            counterLine += pdfPCell.getFixedHeight();

            document.add(pdfPTable);

        }catch (Exception e){
            Log.e("addParagraph", e.toString());
        }
    }

    private void cekMinimalOneBarisBig(){
        //Cek Minimal 1 Baris per Tabel
        if(true){
            float a = 12 * 1.5f + 7.5f;
            float b = 12 * 1.5f + 5f;
            float c = 3 * 20;
            float d = 14 * 1.5f + 15;

            if(counterLine + a + b + c + d> pageHeight){
                counterLine = initialCounterLine;
                document.newPage();
                addHeaderFooter();
            }
        }
    }

    private void cekMinimalOneBaris(){
        //Cek Minimal 1 Baris per Tabel
        if(true){
            float a = 12 * 1.5f + 7.5f;
            float b = 12 * 1.5f + 5f;
            float c = 3 * 20;

            if(counterLine + a + b + c > pageHeight){
                counterLine = initialCounterLine;
                document.newPage();
                addHeaderFooter();
            }
        }
    }

    private void addCounterLine(Paragraph paragraph){
        counterLine += paragraph.getFont().getSize() * 1.5f;
        counterLine += paragraph.getSpacingBefore();
        counterLine += paragraph.getSpacingAfter();
    }

    private void closeDocument(){
        document.close();
    }

    private void viewPDF(){
        Intent intent = new Intent(context, E_ViewPDFActivity.class);
        intent.putExtra("path", pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

