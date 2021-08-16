package com.example.joystick30;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private ViewGroup mainLayout;
    private ImageView image;

    private int xDelta;
    private int yDelta;
    Button btn;
    RelativeLayout.LayoutParams layoutParams;
    TextView tv;
    Timer tim;
    int lastx;
    int lasty;
    int predkosc=500;
    int change=0;
    boolean istim = false;
    private int xxx=0;
    Timer timu;
    boolean istimu=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout) findViewById(R.id.main);
        tv = findViewById(R.id.textView);
        image = (ImageView) findViewById(R.id.image);
        btn = findViewById(R.id.button);
        image.setOnTouchListener(onTouchListener());
    }

    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        Toast.makeText(MainActivity.this,
                                "I'm here!", Toast.LENGTH_SHORT)
                                .show();
                        layoutParams.leftMargin = 306;
                        layoutParams.topMargin = 1092;
                        view.setLayoutParams(layoutParams);
                        if(istim)
                        tim.cancel();istim=false;
                        if(istimu)
                            timu.cancel();;istimu=false;


                        break;

                    case MotionEvent.ACTION_MOVE:
                        layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        lastx = layoutParams.leftMargin;
                        lasty=layoutParams.topMargin;
                        if(x - xDelta>27&&x - xDelta<576&&y - yDelta<1298&&y - yDelta>850) {
                            layoutParams.leftMargin = x - xDelta;
                            layoutParams.topMargin = y - yDelta;
                        }
                        else if(x - xDelta<27)
                        {
                            layoutParams.leftMargin =27;
                            if(y - yDelta<1298&&y - yDelta>850)
                            layoutParams.topMargin = y - yDelta;
                            else if(y - yDelta>1298)
                                layoutParams.topMargin = 1298;
                            else if(y - yDelta<850)
                                layoutParams.topMargin = 850;
                        }
                        else if(x - xDelta>576)
                        {
                            layoutParams.leftMargin =576;
                            if(y - yDelta<1298&&y - yDelta>850)
                                layoutParams.topMargin = y - yDelta;
                            else if(y - yDelta>1298)
                                layoutParams.topMargin = 1298;
                            else if(y - yDelta<850)
                                layoutParams.topMargin = 850;
                        }
                        else if(y - yDelta>1298)
                        {
                            if(x - xDelta<27)
                                layoutParams.leftMargin = 27;
                            else if(x - xDelta>576)
                                layoutParams.leftMargin = 576;
                            else if(x - xDelta>27&&x - xDelta<576)
                                layoutParams.leftMargin = x - xDelta;
                            layoutParams.topMargin = 1298;
                        }
                        else if(y - yDelta<850)
                        {
                            if(x - xDelta<27)
                                layoutParams.leftMargin = 27;
                            else if(x - xDelta>576)
                                layoutParams.leftMargin = 576;
                            else if(x - xDelta>27&&x - xDelta<576)
                                layoutParams.leftMargin = x - xDelta;
                            layoutParams.topMargin = 850;
                        }


                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        poruszanie(layoutParams.leftMargin,layoutParams.topMargin);
                        view.setLayoutParams(layoutParams);
                        tv.setText("X= "+Integer.toString(x - xDelta)+"\nY= "+Integer.toString(y - yDelta)+
                                "\nLX= "+Integer.toString(lastx)+"\nLY= "+Integer.toString(lasty));
                        break;
                }

                mainLayout.invalidate();
                return true;
            }
        };
    }

    public void getpos(View view)
    {
        tv.setText("X= "+Float.toString(btn.getX())+"\nY= "+Float.toString(btn.getY()));

    }
    public void poruszanie(int x,int y)
    {
        if(x>306)
        {

            if(btn.getX()>=871)
                btn.setX(871);
            else
                btn.setX(btn.getX()+10);
            if(x>=576)
                operatetime();
            else
                if(istim)
                    tim.cancel();


        }
        else if(x<306)
        {
            if(btn.getX()<=-19)
                btn.setX(-19);
            else
                btn.setX(btn.getX()-10);
            if(x<=27)
                operatetimel();
            else
                if(istim)
                    tim.cancel();
        }

        if(y<1092)
        {
            if(btn.getY()<=-4)
                btn.setY(-4);
            else
                btn.setY(btn.getY()-10);
            if(y==850)
                operatetimet();
            else
                if(istimu)
                    timu.cancel();

        }
        else if(y>1092)
        {
            if(btn.getY()>=796)
                btn.setY(796);
            else
                btn.setY(btn.getY()+10);
            if(y==1298)
                operatetimed();
            else
            if(istimu)
                timu.cancel();

        }
    }

    private void operatetimed() {
        if(istimu)
            timu.cancel();
        timu = new Timer();
        timu.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                btn.setY(btn.getY()+10);
                if(btn.getY()>=796)
                    timu.cancel();
            }
        },0,10);istimu=true;
    }

    private void operatetimet() {
        if(istimu)
            timu.cancel();
        timu = new Timer();
        timu.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                btn.setY(btn.getY()-10);
                if(btn.getY()<=-4)
                    timu.cancel();
            }
        },0,10);istimu=true;
    }

    public void operatetime()
    {
        if(istim)
            tim.cancel();
        tim = new Timer();
        tim.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                btn.setX(btn.getX()+10);
                if(btn.getX()>=871)
                    tim.cancel();

            }
        },0,10);istim=true;
    }
    public void operatetimel()
    {
        if(istim)
            tim.cancel();
        tim = new Timer();
        tim.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                btn.setX(btn.getX()-10);
                if(btn.getX()<=-19)
                    tim.cancel();
            }
        },0,10);istim=true;
    }
}