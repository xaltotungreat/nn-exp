package framework.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

public class GraphFrame extends JFrame {

	public static final Logger LOG = Logger.getLogger(GraphFrame.class);
	private JPanel contentPane;
	
	Map<Double, Double> function1;
	Color function1Color;
	Map<Double, Double> function2;
	Color function2Color;

	public static class GraphPanel extends JPanel {

		Map<Double, Double> function1;
		Color function1Color;
		Map<Double, Double> function2;
		Color function2Color;
		
		double minX, maxX, minY, maxY;
		
		public GraphPanel(Map<Double, Double> f1, Color f1c, Map<Double, Double> f2, Color f2c) {
			function1 = f1;
			function1Color = f1c;
			function2 = f2;
			function2Color = f2c;
			
			Double minf1X = Collections.min(function1.keySet());
			Double minf2X = Collections.min(function2.keySet());
			minX = Math.min(minf1X, minf2X);
			
			Double maxf1X = Collections.max(function1.keySet());
			Double maxf2X = Collections.max(function2.keySet());
			maxX = Math.max(maxf1X, maxf2X);
			
			Double minf1Y = Collections.min(function1.values());
			Double minf2Y = Collections.min(function2.values());
			minY = Math.min(minf1Y, minf2Y);
			
			Double maxf1Y = Collections.max(function1.values());
			Double maxf2Y = Collections.max(function2.values());
			maxY = Math.max(maxf1Y, maxf2Y);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Integer xAxisLoc = null;
			Integer yAxisLoc = null;
			//Rectangle bounds = g.getClipBounds();
			if ((minX <= 0) && (maxX >= 0)) {
				xAxisLoc = (int)(((double)getWidth())/(maxX - minX)*Math.abs(minX));
			}
			
			if (xAxisLoc != null) {
				g.drawLine(xAxisLoc, 0, xAxisLoc, getHeight());
			}
			
			if ((minY <= 0) && (maxY >= 0)) {
				yAxisLoc = (int)(((double)getHeight())/(maxY - minY)*Math.abs(minY));
			}
			
			if (yAxisLoc != null) {
				g.drawLine(0, yAxisLoc, getWidth(), yAxisLoc);
			}
			
			Integer prevX = null;
			Integer prevY = null;
			
			for (Map.Entry<Double, Double> currF1 : function1.entrySet()) {
				int xC = (int)(((double)getWidth())/(maxX - minX)*Math.abs(currF1.getKey() - minX));
				int yC = (int)(((double)getHeight())/(maxY - minY)*Math.abs(maxY - currF1.getValue()));
				g.setColor(function1Color);
				g.drawLine(xC, yC, xC, yC);
				if ((prevX != null) && (prevY != null)) {
					g.drawLine(prevX.intValue(), prevY.intValue(), xC, yC);
				}
				prevX = xC;
				prevY = yC;
			}
			
			prevX = null;
			prevY = null;
			
			for (Map.Entry<Double, Double> currF2 : function2.entrySet()) {
				int xC = (int)(((double)getWidth())/(maxX - minX)*Math.abs(currF2.getKey() - minX));
				int yC = (int)(((double)getHeight())/(maxY - minY)*Math.abs(maxY - currF2.getValue()));
				g.setColor(function2Color);
				g.drawLine(xC, yC, xC, yC);
				if ((prevX != null) && (prevY != null)) {
					g.drawLine(prevX.intValue(), prevY.intValue(), xC, yC);
				}
				prevX = xC;
				prevY = yC;
			}
			
		}
		
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Map<Double, Double> ff1 = new HashMap<>();
					ff1.put(-1d, -0.5);
					ff1.put(0.7, 0.4);
					Map<Double, Double> ff2 = new HashMap<>();
					ff2.put(-0.3, -0.1);
					ff2.put(0.1, 0.8);
					
					GraphFrame frame = new GraphFrame(ff1, Color.BLUE, ff2, Color.MAGENTA);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GraphFrame(Map<Double, Double> f1, Color f1c, Map<Double, Double> f2, Color f2c) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 796, 632);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		GraphPanel jpGraph = new GraphPanel(f1, f1c, f2, f2c);
		contentPane.add(jpGraph, BorderLayout.CENTER);
		
		
	}

}
