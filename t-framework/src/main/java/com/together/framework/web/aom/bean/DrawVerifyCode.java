package com.together.framework.web.aom.bean;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;

import com.together.framework.constants.FrameworkConstants;
import com.together.framework.web.aom.base.impl.CoreBaseUIBean;


/***
 * <p>
 * @author LingMin 
 * @date 2014-11-3<br>
 * @version 1.0<br>
 */
@ManagedBean(name="drawVerifyCode", scope=ManagedBeanScope.REQUEST)
public class DrawVerifyCode extends CoreBaseUIBean{
	
	/*****日志log******/
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(DrawVerifyCode.class);
	
	private Random random = new Random();//随机数对象
	
    private String randCode = null;

    /**
     * 产生随机验证码
     * @return
     */
	private String randCode() {
        if (this.randCode == null) {
            randCode = "";
            for (int i = 0; i < 4; i++) {
                randCode += String.valueOf(random.nextInt(10));
            }
        }
        log.info("randCode()=="+randCode);
        //将验证码设置到session 中
        this.getSession().setAttribute(FrameworkConstants.VALIDATECODE_KEY, randCode);
        return randCode;
    }
	//验证码图片宽度
    public static final int WIDTH = 60;
    //验证码图片高度
    public static final int HEIGHT = 20;

    //@Bind(id="codeImage",attribute="width")
    public String getWidth() {
        return String.valueOf(WIDTH);

    }

    //@Bind(id="codeImage",attribute="height")
    public String getHeight() {
        return String.valueOf(HEIGHT);
    }

    /**
     * 验证码图片颜色
     * @param fc
     * @param bc
     * @return
     */
    private Color randColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
  
    /**
     * 验证码生成 方法
     * @param g
     * @param width
     * @param height
     */
    public void draw(Graphics g, int width, int height) {
        g.setColor(randColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        g.setColor(randColor(160, 200));

        // 随机产生155条干扰线，使图像中的验证码不易被识别
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 将验证码显示在图像中
        String code = randCode();
        for (int i = 0; i < 4; i++) {
            g.setColor(randColor(20, 130));
            g.drawString(code.substring(i, i + 1), 13 * i + 6, 16);
        }
    }
  
    
    
    
    /*******************以下两个方法为图表************************/
    public void drawShape(String userName , Graphics g, int width, int height) {
        g.setColor(new Color(128, 128, 128));
        g.fillRect(0, 0, width / 2, height);

        int x = 10, y = 50;
        g.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        g.setColor(new Color(0, 0, 220));
        g.drawString("Hello", x, y);

        x = 10;
        y = 100;
        g.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        g.setColor(new Color(50, 50, 50));
        g.drawString(userName, x + 2, y + 2);
        g.setColor(new Color(220, 220, 220));
        g.drawString(userName, x, y);
    }

    public void drawChart(Graphics g, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // 填充整个背景
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 绘制阴影，由灰色渐进圆角矩形组成
        GradientPaint grayGP = new GradientPaint(0, 0, Color.GRAY, width,
                height, new Color(218, 214, 212), false);
        g2d.setPaint(grayGP);
        RoundRectangle2D.Float bgRR = new RoundRectangle2D.Float(5, 5,
                width - 5, height - 5, 20, 20);
        g2d.fill(bgRR);

        // 绘制渐进蓝色圆角矩形背景
        GradientPaint blueGP = new GradientPaint(0, 0, new Color(14, 97, 147),
                0, height, new Color(240, 243, 247), false);
        g2d.setPaint(blueGP);
        g2d.fillRoundRect(0, 0, width - 5, height - 5, 20, 20);

        // 绘制深蓝色圆角矩形轮廓
        BasicStroke bs = new BasicStroke(1.2f);
        g2d.setStroke(bs);
        g2d.setPaint(new Color(55, 71, 105));
        g2d.drawRoundRect(0, 0, width - 5, height - 5, 20, 20);

        // 绘制图表绘图区域背景的阴影效果
        Rectangle2D.Float drawArea = new Rectangle2D.Float(63, 48, 400, 300);
        g2d.setPaint(Color.GRAY);
        g2d.fill(drawArea);

        // 填充图表绘图区域背景
        g2d.setPaint(Color.WHITE);
        drawArea = new Rectangle2D.Float(60, 45, 400, 300);
        g2d.fill(drawArea);

        // 描绘图表绘图区域的轮廓
        g2d.setPaint(Color.BLACK);
        g2d.draw(drawArea);
    
        // 创建虚线笔划
        float[] dashes = { 3.f };
        bs = new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND, 10, dashes, 0);
        g2d.setStroke(bs);

        String str = "";
        int stringLength = 0;
        g2d.setPaint(Color.BLACK);
        g2d.setFont(new Font("宋体", Font.PLAIN, 12));
        for (int i = 1; i <= 12; i++) {
            // 绘制垂直方向虚线
            g2d.drawLine(60 + i * 400 / 13, 45, 60 + i * 400 / 13, 345);

            // 绘制横轴上月份的说明文字
            str += i + "月";
            stringLength = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, 60 + i * 400 / 13 - stringLength / 2, 360);

            // 重置月份说明文字
            str = "";
        }

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        str = "";
        int stringHeight = 0;

        for (int i = 0; i <= 300; i += 30) {
            // 绘制水平方向虚线
            g2d.drawLine(60, 45 + i, 460, 45 + i);

            // 绘制纵轴上销售量的说明文字
            str += 100 - i / 3;
            stringHeight = g2d.getFontMetrics().getAscent();
            stringLength = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, 55 - stringLength, 45 + i + stringHeight / 2);
            str = "";
        }

        Color[] bookColor = { new Color(230, 111, 71), new Color(107, 165, 239) };
        double[] sales = new double[12];
        int[] month = new int[12];

        g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
        g2d.setStroke(new BasicStroke());
        Rectangle2D.Double bar;

        for (int i = 0; i < 2; i++) {
            // 初始化绘制数据
            double bookSales = 0.0;

            for (int j = 0; j < sales.length; j++) {
                bookSales = 1 + Math.random() * 295;
                sales[j] = 345 - bookSales;
                month[j] = 60 + ((j + 1) * 400) / 13;
                bar = new Rectangle2D.Double(month[j] - 6 + i * 10,
                        sales[j] + 2, 10, bookSales - 2);

                // 填充直方图阴影
                g2d.setPaint(Color.GRAY);
                g2d.fill(bar);

                bar = new Rectangle2D.Double(month[j] - 10 + i * 10, sales[j],
                        10, bookSales);
                GradientPaint drawGP = new GradientPaint(
                        month[j] - 10 + i * 10, (int) sales[j], bookColor[i],
                        month[j] - 10 + i * 10, 345, bookColor[i].brighter(),
                        false);

                // 填充直方图
                g2d.setPaint(drawGP);
                g2d.fill(bar);

                // 描绘直方图轮廓
                g2d.setPaint(Color.BLACK);
                g2d.draw(bar);
            }
        }
    }

    /**
	 * 获取功能的名称<p>
	 */
	public String getFunctionName() {
		return "console.drawVerifyCode";
	}

}