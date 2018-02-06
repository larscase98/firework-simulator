package my.firework;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Program extends JFrame implements ActionListener, ChangeListener, ItemListener{
	private static final long serialVersionUID = 1L;
	
	public static int WIDTH = 1600;
	public static int HEIGHT = WIDTH / 16 * 9;
	public static Dimension RES = new Dimension(WIDTH, HEIGHT);
	
	private JPanel fwPanel;
	private JComponent fireworkCanvas;
	
	private JPanel uiPanel;
	public static int UIWIDTH = 470;
	//-- Declaring JComponents for controls
		private JPanel titlePanel;
			private JLabel titleLabel;
		private JPanel anglePanel;
			private JLabel angleLabel;
			public static JSlider angleSlider;
			public static int angleMeasure = 0; //The stored value for the angle, between -90 & 90
		private JPanel velPanel;
			private JLabel velLabel;
			public static JSlider velSlider;
			public static int velMeasure = 40; //Stored value for the velocity, between 0 and 100.
		private JPanel fusePanel;
			private JLabel fuseLabel;
			public static JSlider fuseSlider;
			public static double fuseMeasure = 3;
		private JPanel colorPanel;
			private JLabel colorLabel;
			private ButtonGroup colorGroup;
			public static JRadioButton redTrailRadio;
			public static JRadioButton greenTrailRadio;
			public static JRadioButton cyanTrailRadio;
			public static JRadioButton yellowTrailRadio;
			public static Color trailColor = Color.red;
		private JPanel explosionPanel;
			private JLabel explosionLabel;
			private ButtonGroup explosionGroup;
			public static JRadioButton radialRadio;
			public static JRadioButton arcsRadio;
			public static JRadioButton ovalsRadio;
			public static int explosionValue = 1; //1 = radial, 2 = arcs, 3 = ovals
		//private JButton startButton;	
		
		private static final Font SMALL_FONT = new Font("Verdana", Font.PLAIN, 17);
		private static final Color TRANSPARENT = new Color(255, 255, 255, 0);
		public static Color TWILIGHT = new Color(59, 44, 119);
	
	public Program() {
		setTitle("FireworkSimulator 2017");
		setSize(WIDTH, HEIGHT);
		setBackground(Color.cyan);
		
		fireworkCanvas = new FireworkCanvas();
		
		fwPanel = new JPanel();
		fwPanel.setBackground(Color.white);
		fwPanel.add(fireworkCanvas);
		
		uiPanel = new JPanel();
		uiPanel.setBounds(25, 25, UIWIDTH, 700);
		uiPanel.setBackground(new Color(175, 122, 7, 150));
		uiPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5), Color.black));
		uiPanel.setLayout(new FlowLayout());
		
		
		titlePanel = new JPanel();
		titlePanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5), Color.black));
		anglePanel = new JPanel();
		anglePanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5), Color.black));
		anglePanel.setLayout(new FlowLayout());
		velPanel = new JPanel();
		velPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5), Color.black));
		fusePanel = new JPanel();
		fusePanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5), Color.black));
		colorPanel = new JPanel();
		colorPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5), Color.black));
		explosionPanel = new JPanel();
		explosionPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5), Color.black));
		
		//Title pane
		titleLabel = new JLabel("Controls");
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 36));
		
		titlePanel.add(titleLabel);
		
		// Making the angle-setting pane
		angleLabel = new JLabel("Angle = 0\u00b0");
		angleLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		angleSlider = new JSlider();
		
		angleSlider.setMinimum(-60);
		angleSlider.setMaximum(60);
		angleSlider.setValue(0);
		angleSlider.setPaintTicks(true);
		angleSlider.setMajorTickSpacing(30);
		angleSlider.setMinorTickSpacing(15);
		angleSlider.setPaintLabels(true);
		
		angleSlider.addChangeListener(this);
		
		anglePanel.add(angleLabel);
		anglePanel.add(angleSlider);
		
		//Making the velocity-setting pane
		velLabel = new JLabel("Velocity = 40 m/s");
		velLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		velSlider = new JSlider();
		velSlider.setMaximum(100);
		velSlider.setMinimum(0);
		velSlider.setValue(40);
		velSlider.setPaintTicks(true);
		velSlider.setMajorTickSpacing(25);
		velSlider.setMinorTickSpacing(10);
		velSlider.setPaintLabels(true);
		
		velSlider.addChangeListener(this);
		
		velPanel.add(velLabel);
		velPanel.add(velSlider);
		
		//Making the fuse-setting pane
		fuseLabel = new JLabel("Fuse timer = 3 sec");
		fuseLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		fuseSlider = new JSlider();
		fuseSlider.setMaximum(15);
		fuseSlider.setMinimum(0);
		fuseSlider.setValue(3);
		fuseSlider.setPaintTicks(true);
		fuseSlider.setMajorTickSpacing(5);
		fuseSlider.setMinorTickSpacing(1);
		fuseSlider.setPaintLabels(true);
		fuseSlider.addChangeListener(this);
		fuseSlider.setOpaque(false);
		
		fusePanel.add(fuseLabel);
		fusePanel.add(fuseSlider);
		
		colorGroup = new ButtonGroup(); //For all radio buttons for trail color
		
		colorLabel = new JLabel("Firework Trail Color");
		colorLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		colorLabel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(10), TRANSPARENT));
		redTrailRadio = new JRadioButton("Red");
		redTrailRadio.addItemListener(this);
		redTrailRadio.setSelected(true);
		redTrailRadio.setFont(SMALL_FONT);
		greenTrailRadio = new JRadioButton("Green");
		greenTrailRadio.addItemListener(this);
		greenTrailRadio.setFont(SMALL_FONT);
		cyanTrailRadio = new JRadioButton("Cyan");
		cyanTrailRadio.addItemListener(this);
		cyanTrailRadio.setFont(SMALL_FONT);
		yellowTrailRadio = new JRadioButton("Yellow");
		yellowTrailRadio.addItemListener(this);
		yellowTrailRadio.setFont(SMALL_FONT);
		
		colorGroup.add(redTrailRadio);
		colorGroup.add(greenTrailRadio);
		colorGroup.add(cyanTrailRadio);
		colorGroup.add(yellowTrailRadio);
		
		colorPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints colGBC = new GridBagConstraints();
		colGBC.fill = GridBagConstraints.BOTH;
		colGBC.weightx = 1;
		colGBC.weighty = 1;
        colGBC.gridwidth = GridBagConstraints.REMAINDER;
		
		colorPanel.add(colorLabel, colGBC);
		colorPanel.add(redTrailRadio, colGBC);
		colorPanel.add(greenTrailRadio, colGBC);
		colorPanel.add(cyanTrailRadio, colGBC);
		colorPanel.add(yellowTrailRadio, colGBC);
		
		explosionGroup = new ButtonGroup();
		
		explosionLabel = new JLabel("Explosion Type");
		explosionLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		explosionLabel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(10), TRANSPARENT));
		
		radialRadio = new JRadioButton("Radial");
		radialRadio.addItemListener(this);
		radialRadio.setSelected(true);
		radialRadio.setFont(SMALL_FONT);
		arcsRadio = new JRadioButton("Rays");
		arcsRadio.addItemListener(this);
		arcsRadio.setFont(SMALL_FONT);
		ovalsRadio = new JRadioButton("Confetti");
		ovalsRadio.addItemListener(this);
		ovalsRadio.setFont(SMALL_FONT);
		explosionGroup.add(radialRadio);
		explosionGroup.add(arcsRadio);
		explosionGroup.add(ovalsRadio);
		
		explosionPanel.setLayout(new GridBagLayout());
		explosionPanel.add(explosionLabel, colGBC);
		explosionPanel.add(radialRadio, colGBC);
		explosionPanel.add(arcsRadio, colGBC);
		explosionPanel.add(ovalsRadio, colGBC);
		
		/*
		startButton = new JButton("Go!");
		//startButton.setToolTipText("Update the simulation with the current parameters.");
		startButton.setFont(new Font("Trebuchet", Font.BOLD, 30));
		startButton.setBackground(TWILIGHT);
		startButton.setForeground(Color.black);
		startButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 5), BorderFactory.createLineBorder(TRANSPARENT, 20)));
		
		startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		startButton.addActionListener(this);
		*/
		
		uiPanel.add(titlePanel);
		uiPanel.add(anglePanel);
		uiPanel.add(velPanel);
		uiPanel.add(fusePanel);
		uiPanel.add(colorPanel);
		uiPanel.add(explosionPanel);
		//uiPanel.add(startButton);
		
		add(uiPanel);
		add(fireworkCanvas);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}	
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(angleSlider)) {
			angleMeasure = angleSlider.getValue();
			angleLabel.setText("Angle = " + angleMeasure + "\u00b0"); //Unicode char for degrees symbol
		} else if (e.getSource().equals(velSlider)) {
			velMeasure = velSlider.getValue();
			velLabel.setText("Velocity = " + velMeasure + " m/s");
		} else if (e.getSource().equals(fuseSlider)) {
			fuseMeasure = fuseSlider.getValue();
			fuseLabel.setText("Fuse timer = " + fuseMeasure + " sec");
		}
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*if (e.getSource().equals(startButton)) {
			fireworkCanvas.repaint();
		}*/
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		//For trail colors
		if(e.getSource().equals(redTrailRadio)) {
			trailColor = Color.red;
			//fireworkCanvas.repaint();
		} else if (e.getSource().equals(greenTrailRadio)) {
			trailColor = Color.green;
			//fireworkCanvas.repaint();
		} else if (e.getSource().equals(cyanTrailRadio)) {
			trailColor = Color.cyan;
			//fireworkCanvas.repaint();
		} else if (e.getSource().equals(yellowTrailRadio)) {
			trailColor = Color.yellow;
			//fireworkCanvas.repaint();
		}
		//For explosion types
		else if (e.getSource().equals(radialRadio)) {
			explosionValue = 1;
		} else if (e.getSource().equals(arcsRadio)) {
			explosionValue = 2;
		} else if (e.getSource().equals(ovalsRadio)) {
			explosionValue = 3;
		}
		repaint();
	}
	
	
	public static void main(String[] args) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
	    catch (UnsupportedLookAndFeelException|ClassNotFoundException|InstantiationException|IllegalAccessException e) {}
		
		new Program();
	}

}
