package com.compses.util;
///*实现图片倒影(tested)*/
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
  
public class ImageUtils  
{  
  
    public static BufferedImage reflexImage(String name,int rang) throws IOException  
    {  
        // 载入一张原始图片  
    	BufferedImage image = ImageIO.read(new File(name));
  
        BufferedImage invertedImage = ImageUtils.getInvertedImage(image, 150, 255); 
        
        int imgWidth = image.getWidth(null);  
        int imgHeight = image.getHeight(null); 
        int x = (imgHeight+imgHeight/3-15-imgWidth)/2;
        
        BufferedImage refacImage = new BufferedImage(imgHeight+imgHeight/rang-15, imgHeight+imgHeight/rang+10, BufferedImage.TYPE_INT_ARGB); 
        Graphics2D g2 = (Graphics2D) refacImage.getGraphics();  
        // 将原始图像倒置输出到图像类  
        g2.drawImage(image, x, 0,x+image.getWidth(null), image.getHeight(null), 0, 0, image.getWidth(null),image.getHeight(null), null);  
        g2.drawImage(invertedImage, x, imgHeight, x+invertedImage.getWidth(null), imgHeight*2+10, 0, 0,invertedImage.getWidth(null), imgHeight*2, null);  
        return refacImage;
    } 
    
    public static BufferedImage image(String name,int rang) throws IOException  
    {  
        // 载入一张原始图片  
    	BufferedImage image = ImageIO.read(new File(name));
  
        int imgWidth = image.getWidth(null);  
        int imgHeight = image.getHeight(null); 
        int x = (imgHeight+imgHeight/3-15-imgWidth)/2;
        
        BufferedImage refacImage = new BufferedImage(imgHeight+imgHeight/rang-15, imgHeight+imgHeight/rang+10, BufferedImage.TYPE_INT_ARGB); 
        Graphics2D g2 = (Graphics2D) refacImage.getGraphics();  
        // 将原始图像倒置输出到图像类  
        g2.drawImage(image, x, 0,x+image.getWidth(null), image.getHeight(null), 0, 0, image.getWidth(null),image.getHeight(null), null);  
        return refacImage;
    }  
  
    public static BufferedImage getInvertedImage(Image img, int start, int end)  
    {  
        // 得到原始图片宽和高  
        int imgWidth = img.getWidth(null);  
        int imgHeight = img.getHeight(null);  
        // 创建一个BufferedImage图像类对象  
        BufferedImage invertedImage = new BufferedImage(imgWidth, imgHeight/3*2, BufferedImage.TYPE_INT_ARGB);  
        Graphics2D g2 = (Graphics2D) invertedImage.getGraphics();  
        // 将原始图像倒置输出到图像类  
        g2.drawImage(img, 0, 0, imgWidth, imgHeight, 0, imgHeight, imgWidth, 0, null);  
        // 创建一个由透明到不透明的渐变用以图像合成  
        Color c1 = new Color(0, 0, 0, start);  
        Color c2 = new Color(0, 0, 0, end);  
        GradientPaint mask = new GradientPaint(0, 0, c1, 0, imgHeight/2, c2);  
        g2.setPaint(mask);  
        // 合成图像  
        g2.setComposite(AlphaComposite.DstOut);  
        g2.fillRect(0, 0, imgWidth, imgHeight);  
        g2.dispose();  
        return invertedImage;  
    } 
    
    public static BufferedImage scale(BufferedImage src,int width,int height,double scale){
    //  获取一个宽、长是原来scale的图像实例   
        Image image = src.getScaledInstance((int)(width * scale), (int)(height * scale), Image.SCALE_SMOOTH);  
        
        //缩放图像
        BufferedImage tag = new BufferedImage((int)(width * scale), (int)(height * scale), BufferedImage.TRANSLUCENT);     
        Graphics2D g = tag.createGraphics();   
        
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图   
        g.dispose();   
        
        return tag;
    }
    
    public static void main(String[] args) throws IOException  
    {  
    	
    	String[] images = "picture,music".split(",");
    	for(String name:images){
    		System.out.println("/Users/lyhcn/Desktop/fy/"+name+"2.png");
    		BufferedImage reflexImage = ImageUtils.reflexImage("/Users/lyhcn/Desktop/fy/"+name+"2.png",3);
    		BufferedImage image = ImageUtils.image("/Users/lyhcn/Desktop/fy/"+name+"2.png",3);
    		BufferedImage scale = ImageUtils.scale( reflexImage,  image.getWidth(null),  image.getHeight(null), 0.6);
    		image = ImageUtils.scale( image,  image.getWidth(null),  image.getHeight(null), 0.6);
    		ImageIO.write(reflexImage, "PNG", new File("/Users/lyhcn/Desktop/fy/images1/"+name+"_max.png"));
    		ImageIO.write(scale, "PNG", new File("/Users/lyhcn/Desktop/fy/images1/"+name+"_min.png"));
    		ImageIO.write(image, "PNG", new File("/Users/lyhcn/Desktop/fy/images1/"+name+".png"));
    	}
    }  
  
} 
