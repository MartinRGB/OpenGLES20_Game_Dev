package com.bn.Sample18_6;
/*
 * ����Ϊ��̬�����࣬�ṩ��̬����������
 * С��Ӧ�õ��˶�����
 */
public class RotateUtil
{
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] pitchRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��x����ת�任����
		{
		   {1,0,0,0},
		   {0,Math.cos(angle),Math.sin(angle),0},		   
		   {0,-Math.sin(angle),Math.cos(angle),0},		   //ԭ��Ϊ��{0,-Math.sin(angle),Math.cos(angle),0},
		   {0,0,0,1}	
		};
		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		
		return gVector;
	}
	
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] rollRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��y����ת�任����
		{
		   {Math.cos(angle),0,-Math.sin(angle),0},
		   {0,1,0,0},
		   {Math.sin(angle),0,Math.cos(angle),0},
		   {0,0,0,1}	
		};
		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		
		return gVector;
	}		
	
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] yawRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��z����ת�任����
		{
		   {Math.cos(angle),Math.sin(angle),0,0},		   
		   {-Math.sin(angle),Math.cos(angle),0,0},
		   {0,0,1,0},
		   {0,0,0,1}	
		};
		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		
		return gVector;
	}
	
	
	public static float[] getDirectionDot(double[] values)
	{
		double yawAngle=-Math.toRadians(values[0]);//��ȡYaw����ת�ǶȻ���
		double pitchAngle=-Math.toRadians(values[1]);//��ȡPitch����ת�ǶȻ���
		double rollAngle=-Math.toRadians(values[2]);//��ȡRoll����ת�ǶȻ���
		
		//����һ����������
		double[] gVector={0,0,-100,1};
		
		//yaw��ָ�
		gVector=RotateUtil.yawRotate(yawAngle,gVector);
		
		//pitch��ָ�
		gVector=RotateUtil.pitchRotate(pitchAngle,gVector);	
		
		//roll��ָ�
		gVector=RotateUtil.rollRotate(rollAngle,gVector);
		
		double mapX=gVector[0];
		double mapY=gVector[1];		
		double mapZ=gVector[2];	
		
		float[] result={(float) mapX,(float) mapY,(float) mapZ};
		return result;
	}
}