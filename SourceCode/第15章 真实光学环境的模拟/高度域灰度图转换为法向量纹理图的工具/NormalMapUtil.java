package com.bn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.color.*;
import java.io.*;
import javax.imageio.ImageIO;

public class NormalMapUtil extends JFrame 
{
	//������ʾԴͼ��ı�ǩ����������õ�����������
	JLabel jls=new JLabel();
	JScrollPane jspz=new JScrollPane(jls);
	//������ʾĿ��ͼ��ı�ǩ����������õ�����������
	JLabel jlt=new JLabel();
	JScrollPane jspy=new JScrollPane(jlt);
	//�����ָ�񣬲����ø��Ӵ�������ʾ�Ŀؼ�
	JSplitPane jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jspz,jspy);
	//�����ļ�ѡ����
	JFileChooser jfc;

	//����һ��ͼ������
	ImageIcon ii;
	public NormalMapUtil()
	{
		String path=new File("a").getAbsolutePath();
		path=path.substring(0,path.length()-2);
		jfc=new JFileChooser(path);
		
		//����ѡ���ͼƬ��ͼ�������
		ii=this.chooserFile();
		//��ͼƬ���õ�Դ��ǩ��
		jls.setIcon(ii);
		//����������ǩ��ˮƽ����ֱ���뷽ʽ
		jls.setVerticalAlignment(JLabel.CENTER);
		jls.setHorizontalAlignment(JLabel.CENTER);
		jlt.setVerticalAlignment(JLabel.CENTER);
		jlt.setHorizontalAlignment(JLabel.CENTER);
		
		//���÷ָ����Ŀ���Լ���ʼλ��
		jsp.setDividerLocation(500);
		jsp.setDividerSize(4);
		//���ָ����ӵ�������
		this.add(jsp);

		//���ô���ı��⡢��Сλ���Լ��ɼ���
		this.setTitle("�߶���Ҷ�ͼת���ɷ���������ͼ����");
		this.setBounds(0,0,1000,500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Image iTemp=process();
		//��������ͼƬ���õ�Ŀ���ǩ��
		jlt.setIcon(new ImageIcon(iTemp));
		
		try
		{
			FileOutputStream os = new FileOutputStream("resultnt.jpg");
			System.out.println(((RenderedImage)iTemp).getColorModel());
      		ImageIO.write((RenderedImage)iTemp, "JPEG", os);
      		os.flush();
      		os.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//����ѡ��ͼƬ�ķ���
	public ImageIcon chooserFile()
	{
		//�����ļ�ѡ����
		int i=jfc.showOpenDialog(this);
		//��ȡѡ���ļ���·��
		String dir=(jfc.getSelectedFile()!=null)?(jfc.getSelectedFile().getPath()):null;
		if(dir!=null&&!dir.equals(""))
		{
			//��ָ����·������ͼƬ��ͼ������в�����
			return new ImageIcon(dir);
		}
		return null;
	}
	
	//��Դ�߶�ͼ���ɰ�͹��ͼ�ķ���
	public Image process(){		
		int width=ii.getImage().getWidth(null);//��ȡ������ͼ��Ŀ����߶�
		int height=ii.getImage().getHeight(null); 
		//��������BufferedImage����ֱ��������ô�����ͼ���봦����ͼ��     
		BufferedImage sourceBuf=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		BufferedImage targetBuf=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//��������ͼ����Ƽ��ص�ԴBufferedImage������
		Graphics graph=sourceBuf.getGraphics();
		graph.drawImage(ii.getImage(),0,0,Color.white,null);
		for(int i=0;i<height;i++){//�Դ�����ͼ�����ص���ѭ��
			for(int j=0;j<width;j++){//�Դ�����ͼ�����ص���ѭ��
				int color=sourceBuf.getRGB(j,i);//��ȡָ��λ�ô�������
				//��ֳ�RGB����ɫ��ͨ����ֵ
				int r=(color >> 16) & 0xff;int g=(color >> 8) & 0xff;int b=(color) & 0xff;
                float c=(r+g+b)/3.0f/255.0f;//������������صĸ߶�                
                if(i==0||j==width-1){//��Ϊ�����һ�л�������һ�е����ز��ü���
                	targetBuf.setRGB(j,i,0xFF8080FF);
                	continue;
                }
                //ȡ�����Ϸ����ص�ֵ������ɸ߶�
                int colorUp=sourceBuf.getRGB(j,i-1);
				int rUp=(colorUp >> 16) & 0xff;
                int gUp=(colorUp >> 8) & 0xff;
                int bUp=(colorUp) & 0xff;
                float cUp=(rUp+gUp+bUp)/3.0f/255.0f;
                //ȡ�����Ҳ����ص�ֵ������ɸ߶�
                int colorRight=sourceBuf.getRGB(j+1,i);
				int rRight=(colorRight >> 16) & 0xff;
                int gRight=(colorRight >> 8) & 0xff;
                int bRight=(colorRight) & 0xff;
                float cRight=(rRight+gRight+bRight)/3.0f/255.0f;
                //����������������
                float[] vec1={1,0,cUp-c};
                float[] vec2={0,1,cRight-c};
                //�������������õ��������
                float[] vResult=VectorUtil.getCrossProduct(vec1[0],vec1[1],vec1[2]*4,vec2[0],vec2[1],vec2[2]*4);
                vResult=VectorUtil.vectorNormal(vResult);
                //���������������ֵ���㵽0-255�ķ�Χ��
                int cResultRed=(int)(vResult[0]*128)+128;
                int cResultGreen=(int)(vResult[1]*128)+128;
                int cResultBlue=(int)(vResult[2]*128)+128;                
                cResultRed=(cResultRed>255)?255:cResultRed;
                cResultGreen=(cResultGreen>255)?255:cResultGreen;
                cResultBlue=(cResultBlue>255)?255:cResultBlue;
                //�����������������                
                int cResult=0xFF000000;
                cResult+=cResultRed<<16;
                cResult+=cResultGreen<<8;
                cResult+=cResultBlue;                
                targetBuf.setRGB(j,i,cResult);
			}
		}
		return targetBuf;
	}
	
	public static void main(String[] args)
	{
		//����Sample29_8�������
		new NormalMapUtil();
	}
}
