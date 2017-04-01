package net.sdc.ispattack;

import java.util.TimerTask;

public class TestingThread extends TimerTask{

	private GuiMain master;
	
	public TestingThread(GuiMain master)
	{
		this.master = master;
		
	}

	@Override
	public void run() {
		
		if(master.isRunning())
		{
			//TODO test the network speed
			
		}
		
	}
	
	
}
