package controllernode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.stream.events.StartDocument;

import hu.bme.mit.gamma.impl.controllernode.ControllerNode;
import hu.bme.mit.gamma.impl.trafficlightctrl.TrafficLightCtrlStatechart.Control;
import hu.bme.mit.ftsrg.monitoring.client.*;
public class ControllerProgram {

	ControllerNode node;
	JFrame frame;
	JPanel panel;
	JButton button, resButton;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ControllerProgram prog = new ControllerProgram();
		prog.run();
	}
	
	public void run() {
		Start client = new Start("ControllerNode");
		client.run();
		node=new ControllerNode();
		node.start();
		createGUI();		
	}
	
	void createGUI() {
		frame = new JFrame("Controller");
		panel = new JPanel();
		
		frame.setContentPane(panel);
		
		button=new JButton("Toggle");
		button.addActionListener(new ButtonListener());
		panel.add(button);
		
		resButton=new JButton("Reset");
		resButton.addActionListener(new ResetListener());
		panel.add(resButton);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new ExitListener());
		frame.setSize(200,200);
		frame.setVisible(true);
		
		
	}
	
	class ExitListener implements WindowListener{

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			node.closeTopics();
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			node.getPolice().raisePolice();
		}
		
	}
	
	class ResetListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			node.getPolice().raiseReset();
			
		}
		
	}
	
	

}
