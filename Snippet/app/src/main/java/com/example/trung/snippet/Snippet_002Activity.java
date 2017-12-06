package com.example.trung.snippet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Snippet_002Activity extends AppCompatActivity {
    Button btn_Top;
    TextView tv_MeasuredWidth;
    TextView tv_Width;
    TextView tv_LayoutParam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet_002);
        //get components
        tv_MeasuredWidth = (TextView) findViewById(R.id.txtMeasuredWidth);
        tv_Width = (TextView) findViewById(R.id.txtWidth);
        tv_LayoutParam = (TextView) findViewById(R.id.txtLayoutParam);
        btn_Top = (Button) findViewById(R.id.button);
        Button btn_Edit = (Button) findViewById(R.id.btnEdit);
        //Main Process
        //GET
        ViewTreeObserver vto = btn_Top.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                btn_Top.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width  = btn_Top.getMeasuredWidth();
                tv_MeasuredWidth.setText(String.valueOf(width));
                int width1 = btn_Top.getWidth();
                tv_Width.setText(String.valueOf(width1));
                int width2 = btn_Top.getLayoutParams().width;
                tv_LayoutParam.setText(String.valueOf(width2));
            }
        });
        //SET
        final float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,500,getResources().getDisplayMetrics());
        btn_Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    btn_Top.setWidth((int) pixels);
                    btn_Top.setLayoutParams(new LinearLayout.LayoutParams(-1, -2)); //width, height

                }
            }
        );

    }
}
