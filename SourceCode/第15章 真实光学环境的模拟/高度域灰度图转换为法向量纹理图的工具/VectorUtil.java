package com.bn;
//�������㷽���ķ�װ��
public class VectorUtil{
	//�����������Ĳ��
	public static float[] getCrossProduct(float x1,float y1,float z1,float x2,float y2,float z2){		
		//�������ʸ�����ʸ����XYZ��ķ���ABC
        float A=y1*z2-y2*z1;
        float B=z1*x2-z2*x1;
        float C=x1*y2-x2*y1;		
		return new float[]{A,B,C};
	}		
	public static float[] vectorNormal(float[] vector){//�������
		float module=(float)Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);//��������ģ	
		return new float[]{vector[0]/module,vector[1]/module,vector[2]/module};
	}	
	
	public static float mould(float[] vec){//��������ģ
		return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
	}		
}
