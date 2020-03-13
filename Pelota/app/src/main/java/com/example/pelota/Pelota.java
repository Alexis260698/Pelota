package com.example.pelota;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


public class Pelota extends View implements SensorEventListener {

Paint pincel= new Paint();
int alto,ancho;
int tamaño=20;
int borde=0;
float ejeX=0,ejeY=0;
int J1,J2;
int tamPorteria;
Bitmap imagen,imagenFinal;

public Pelota(Context interfaz){
    super(interfaz);
    SensorManager smAdministrador=(SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    Sensor snsRotacion=smAdministrador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    smAdministrador.registerListener(this,snsRotacion,SensorManager.SENSOR_DELAY_FASTEST);

    Display pantalla=((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    ancho=pantalla.getWidth();
    alto=pantalla.getHeight();

    imagen = BitmapFactory.decodeResource(interfaz.getResources(), R.drawable.campo);
    imagenFinal = Bitmap.createScaledBitmap(imagen,ancho,(alto-160),false);

    J1=0;
    J2=0;
    ejeY=((alto-160)/2);
    ejeX=ancho/2;
    tamPorteria = ancho/5;
    Toast.makeText(interfaz, "Ancho = "+ancho+" - alto = "+(alto-160),Toast.LENGTH_SHORT).show();
}

@Override
    public void onSensorChanged(SensorEvent cambio){
    ejeX+=((-1)*cambio.values[0]);
    if(ejeX<(tamaño+borde)){
        ejeX=(tamaño+borde);
    }else if(ejeX>(ancho-(tamaño+borde))){
        ejeX=ancho-(tamaño+borde);
    }
    ejeY+=cambio.values[1];
    if(ejeY<(tamaño+borde)){
        ejeY=(tamaño+borde);
        if (ejeX >=((tamPorteria*2)) && ejeX <=((tamPorteria*3))){
            J2++;
            ejeY=((alto-160)/2);
            ejeX=ancho/2;
        }
    }else if(ejeY>(alto-tamaño-160)){
        ejeY=(alto-tamaño-160);
        if (ejeX >=((tamPorteria*2)) && ejeX <=((tamPorteria*3))){
            J1++;
            ejeY=((alto-160)/2);
            ejeX=ancho/2;
        }
    }

    invalidate();
}

@Override
    public void  onAccuracyChanged(Sensor sensor, int accuracy){
}

@Override
    public void  onDraw(Canvas lienzo){
    lienzo.drawBitmap(imagenFinal,0,0,pincel);
    pincel.setColor(Color.BLACK);
    lienzo.drawCircle(ejeX,ejeY,tamaño,pincel);
    lienzo.drawText("",ejeX-35,ejeY+3,pincel);
    pincel.setTextSize(30);

    lienzo.drawText("J1:"+J1,14, ((alto-160)/2)-12,pincel);
    lienzo.drawText("J2:"+J2,14, ((alto-160)/2)+34,pincel);
}






}

