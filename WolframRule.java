
import java.util.Random;
import java.io.File; 
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

public class WolframRule {
	public static int width = 2000;
	public static int height= 2000;
	public static int R = 0;
	public static int G = 153;
	public static int B = 0;
	
	public static int pattern =74;
	public static int[] pattern_2 = toBinary(pattern);
	
	public static String filename= "C:\\Users\\<USERNAME>\\Desktop\\Out.png";
	
	public static boolean triangle = true;
	public static void main(String[] args)
	{
		
		if(triangle)
			height=width/2;
		BufferedImage image = null; 
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); 
        File f = null;
        Random random = new Random();
		int[][] img = new int[width][height];
		for(int i=0; i<width; i++)
		{
			if(triangle && i==width/2)
				img[i][0]=1;
			else if(triangle)
				img[i][0]=0;
			else
				img[i][0]=1*random.nextInt(2);
			int p = 255<<24|((img[i][0]==1?R:0)<<16)|((img[i][0]==1?G:0)<<8)|(img[i][0]==1?B:0);
			image.setRGB(i, 0, p);
		}
		for(int j=1; j<height; j++)
		{
			for(int i=0; i<width; i++)
			{
				if(triangle&&(i<width/2-j||i>width/2+j))
					img[i][j]=0;
				else
					img[i][j]=i>0?i<width-1?nextColor(img[i-1][j-1], img[i][j-1], img[i+1][j-1]):0:0;
				int p = new Color(img[i][j]==1?R:0, img[i][j]==1?G:0, img[i][j]==1?B:0).getRGB();
				image.setRGB(i, j, p);
			}
		}
		try
        { 
            f = new File(filename); 
            ImageIO.write(image, "png", f); 
        } 
        catch(IOException e) 
        { 
            System.out.println("Error: " + e); 
        }
		System.out.println("Pattern "+pattern+" has been completed and placed in "+filename);
	}
	
	public static int[] toBinary(int s)
	{
		int[] binary = new int[8];
		int pow2 = 128;
		int index=0;
		while(s>0)
		{
			if(s-pow2>=0)
			{
				s-=pow2;	
				binary[index]=1;
			}
			else
				binary[index]=0;
			pow2/=2;
			index++;
		}
		System.out.println("Starting Pattern "+pattern);
		System.out.print("Binary Representation: ");
		for(int i=0; i<8; i++)
			System.out.print(binary[i]);
		System.out.println();
		return binary;
	}
	
	public static int nextColor(int a, int b, int c)
	{
		if(a==0&&b==0&&c==0)
			return pattern_2[7];
		else if(a==1&&b==0&&c==0)
			return pattern_2[3];
		else if(a==0&&b==1&&c==0)
			return pattern_2[5];
		else if(a==1&&b==1&&c==0)
			return pattern_2[1];
		else if(a==0&&b==0&&c==1)
			return pattern_2[6];
		else if(a==1&&b==0&&c==1)
			return pattern_2[2];
		else if(a==0&&b==1&&c==1)
			return pattern_2[4];
		else if(a==1&&b==1&&c==1)
			return pattern_2[0];
		return 0;
	}
}
