package com.bn.Sample18_6;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MyActivity extends Activity {
	//SensorManager��������
	SensorManager mySensorManager;	
	
	Sensor mySensor; 	//����������
	
	MySurfaceView mySurfaceView;
	


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		//����Ϊ��ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        //��̬������
        mySensor=mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        
        
        mySurfaceView = new MySurfaceView(this);
        this.setContentView(mySurfaceView);       
        //��ȡ����
        mySurfaceView.requestFocus();
        //����Ϊ�ɴ���
        mySurfaceView.setFocusableInTouchMode(true);

    }
    
	private SensorEventListener mySensorListener = 
		new SensorEventListener(){//����ʵ����SensorEventListener�ӿڵĴ�����������
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy){}
		@Override
		public void onSensorChanged(SensorEvent event){
			float []values=event.values;//��ȡ�����᷽���ϵ�ֵ
			
			float directionDotXYZ[]=RotateUtil.getDirectionDot
			(
					new double[]{values[0],values[1],values[2]} 
		    );
			//��׼��xyλ����
			double mLength=directionDotXYZ[0]*directionDotXYZ[0]+
			            directionDotXYZ[1]*directionDotXYZ[1];
			mLength=Math.sqrt(mLength);
			
			if(mLength==0)
			{
				return;
			}
			if( directionDotXYZ[2]<0)
			{
				Constant.SPANX=(float)((directionDotXYZ[1]/mLength)*0.08f);
				Constant.SPANZ=(float)((directionDotXYZ[0]/mLength)*0.08f);
			}
			else
			{
				Constant.SPANX=(float)((directionDotXYZ[1]/mLength)*0.08f);
				Constant.SPANZ=-(float)((directionDotXYZ[0]/mLength)*0.08f);
			}
		}
	};

	@Override
	protected void onResume() {						//��дonResume����
		mySensorManager.registerListener(			//ע�������
				mySensorListener, 					//����������
				mySensor,	//����������
				SensorManager.SENSOR_DELAY_NORMAL		//�������¼����ݵ�Ƶ��
				);

		super.onResume();
	}
	@Override
	protected void onPause() {									//��дonPause����
		mySensorManager.unregisterListener(mySensorListener);	//ȡ��ע�������
		super.onPause();
	}
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)
	{
		switch(keyCode)
	    	{
		case 4:
			System.exit(0);
			break;
	    	}
		return true;
	}

	
	
}