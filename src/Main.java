import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Main 
{
	private static BufferedImage img;
	private static Robot robot;
	private static int retry;
	private static boolean error;
	
	public static void main(String[] argv)
	{
		retry = 0;
		error = false;
		try 
		{
			robot = new Robot();
			Thread.sleep(3000);
			while (true)
			{
				Thread.sleep(370);
				next();				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void next()
	{
		if (retry != 0)
		{
			System.out.println("retry:" + retry);
		}
 		if (retry > 10)
 		{
 			System.exit(1);
 		}
 		
		Rectangle rect = new Rectangle(715, 335, 485, 485);
		img = robot.createScreenCapture(rect);
		//saveImg();
		if (gameover())
		{
	 		int key = KeyEvent.VK_SPACE;
	 		System.out.println("Failed again...");
			robot.keyPress(key);
			robot.keyRelease(key);
			return;
		}
		
		Board b = new Board(0);

		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				b.set(i, j, getValue(i, j));
			}
		}

		if (error == true)
		{
			retry++;
			error = false;
			return;
		}

		b.setValue();

//		b.print();
//		System.out.println(b.score());
// 		System.exit(1);
 		
		int move = b.move();
// 		System.exit(1);
		int key;
		
		switch (move)
		{
		 	case 0:
		 		key = KeyEvent.VK_LEFT;
		 		break;
			case 1:
				key = KeyEvent.VK_RIGHT;
				break;
		 	case 2:
		 		key = KeyEvent.VK_UP;
		 		break;
		 	case 3:
		 		key = KeyEvent.VK_DOWN;
		 		break;
		 	default:
		 		System.out.println("Something went wrong...");
		 		retry++;
		 		return;		
		}
		
		robot.keyPress(key);
		robot.keyRelease(key);
		retry = 0;
		error = false;
		//b.print();
	}
	
	private static boolean gameover() 
	{
		int rgb = img.getRGB(480, 480);
//		Color c = new Color(rgb);
//		System.out.println("Invalid color: " + rgb + " (" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")");
//		System.exit(1);
		return rgb == -2766403;
	}

	private static void saveImg()
	{
		File outputfile = new File("image.jpg");
		try 
		{
			ImageIO.write(img, "jpg", outputfile);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static int getValue(int i, int j)
	{
		int x = 55 + 120 * i;
		int y = 85 + 120 * j;
		int rgb = img.getRGB(x, y);
		
		switch(rgb)
		{
			case -1194689:
				return 10;
			case -1193904:
				return 9;
			case -1192863:
				return 8;
			case -1192078:
				return 7;
			case -631237:
				return 6;
			case -623521:
				return 5;
			case -682653:
				return 4;
			case -872071:
				return 3;
			case -1187640:
				return 2;
			case -1121062:
				return 1;
			case -3358541:
				return 0;
			default:
				Color c = new Color(rgb);
				System.out.println("Invalid color: " + rgb + " (" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")  x: " + x + " y: " + y);
				error = true;
				return -1;
		}
		
	}
	
}
