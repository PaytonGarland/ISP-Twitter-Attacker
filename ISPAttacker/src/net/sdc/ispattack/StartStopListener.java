package net.sdc.ispattack;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StartStopListener implements MouseListener{

	private GuiMain thread;
	
	public StartStopListener(GuiMain thread)
	{
		this.thread = thread;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		thread.switchRunning();
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
