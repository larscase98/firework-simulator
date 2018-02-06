package my.firework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JComponent;

public class FireworkCanvas extends JComponent {
	
	private static final long serialVersionUID = 1L;	
	
	private static double SCALE_FACTOR = 2.5;
	
	private Random r = new Random();
	private static Color[] COLORS = new Color[] {Color.red, Color.green, Color.blue, Color.cyan, Color.magenta, Color.yellow};
	
	@Override
	public void paintComponent(Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();
		int groundHeight = height-(height/12);		
		
		
		//Night sky background.
		g.setColor(Program.TWILIGHT);
		g.fillRect(0, 0, width, height);
		
		//Drawing the ground.
		g.setColor(new Color(5, 30, 5));
		g.fillRect(0, groundHeight, width, height/12);		
		
		drawFirework(g);
		
	}
	
	public void drawFirework(Graphics g) {
		
		int width = this.getWidth();
		int height = this.getHeight();
		int groundHeight = height - (height/12);
		
		Graphics2D g2d = (Graphics2D) g;
		
		double fwX = width/2 + Program.UIWIDTH;
		double fwY = groundHeight;
		double fwFuseLength = Program.fuseMeasure; //in seconds
		double fwVel = Program.velMeasure; //in meters/sec
		double fwLaunchAngle = 90 + Program.angleMeasure; // In degrees, angle of elevation, and turned into radians
		fwLaunchAngle *= Math.PI/180;
		
		double t = 0; //time elapsed
		
		while (t <= fwFuseLength) {
			
			g2d.setColor(Program.trailColor);
			//-- Trajectory dots
			//-- Note: all measurements are scaled by the static SCALE_FACTOR
			fwX = (width/2 + Program.UIWIDTH/2) - SCALE_FACTOR * ((fwVel * Math.cos(fwLaunchAngle) * t));
			fwY = groundHeight - SCALE_FACTOR * (((fwVel * Math.sin(fwLaunchAngle) * t) - ((0.5)*(9.8)*(t*t))));
			
			if (fwY >= groundHeight) fwY = groundHeight - 3; //Rolls along ground instead of falling through the earth.
			
			g2d.fillOval((int)(fwX-3), (int)(fwY-3), 6, 6);
			
			t += 0.25;
		}
		
		int colorIndex = 0; //Starting color index
		
		//For the three different explosion types.
		switch (Program.explosionValue) {
			case 1:
				// "Radial"
				for(int i = 1; i <= 100; i++) {
					g2d.setColor(COLORS[colorIndex]);
					//g2d.drawOval((int)(fwX - explosionRadius/(2*i)), (int)(fwY - explosionRadius/(2*i)), explosionRadius/i, explosionRadius/i);
					int rand = (int) ((groundHeight - fwY - 140) * r.nextGaussian());
					g2d.drawOval((int)(fwX - (0.5*rand)), (int)(fwY - (0.5*rand)), rand, rand);
					
					colorIndex++;
					if(colorIndex >= COLORS.length) colorIndex = 0;
				}
				break;
			case 2:
				// "Rays"
				for(int i = 0; i <= 125; i++) {
					g2d.setColor(COLORS[colorIndex]);
					g2d.drawLine((int)fwX, (int)fwY, (int)(fwX + r.nextInt(400) - 200), (int)(fwY + r.nextInt(400) - 200));
					
					colorIndex++;
					if(colorIndex >= COLORS.length) colorIndex = 0;
				}
				break;
			case 3:
				// "Confetti"
				for (int i = 0; i <= 1000; i++) {
					int radius = r.nextInt(20) - 10;
					g2d.setColor(COLORS[colorIndex]);
					g2d.fillOval((int)(fwX + 55*r.nextGaussian()), (int)(fwY + 55*r.nextGaussian()), radius, radius);
					
					colorIndex++;
					if(colorIndex >= COLORS.length) colorIndex = 0;
				}
				g2d.setColor(Program.trailColor);
				g2d.fillOval((int)fwX-10, (int)fwY-10, 20, 20);
				break;
			default:
				//Do nothing, i.e., on application loading.
				break;
		}
		
	}
	
}
