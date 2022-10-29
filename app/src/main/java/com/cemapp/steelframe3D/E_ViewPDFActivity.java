package com.cemapp.steelframe3D;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.barteksc.pdfviewer.PDFView;
import java.io.File;

public class E_ViewPDFActivity extends AppCompatActivity {

    private PDFView pdfView;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_viewpdf);

        pdfView = findViewById(R.id.pdfView);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            file = new File(bundle.getString("path", ""));
        }

        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();

    }
}

