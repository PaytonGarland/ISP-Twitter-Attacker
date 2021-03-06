package net.sdc.ispattack;

import java.io.BufferedInputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.TimerTask;

import javax.naming.Context;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.ISpeedTestListener;
import fr.bmartel.speedtest.SpeedTestError;

import com.citumpe.ctpTools.*;

public class TestingThread extends TimerTask{

	private GuiMain master;
	static final String FILE_URL = "http://www.example.com/speedtest/file.bin";
	static final long FILE_SIZE = 5 ; // 5MB in Kilobits *1024*8
	
	public TestingThread(GuiMain master)
	{
		this.master = master;
		
	}
	
	public long test() throws IOException
	{
		long mStart, mEnd;
		Context mContext;
		URL mUrl = new URL(FILE_URL);
		HttpURLConnection mCon = (HttpURLConnection)mUrl.openConnection();
		mCon.setChunkedStreamingMode(0);
		mCon.setDoOutput(true);

		if(mCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
		    mStart = new Date().getTime();

		    InputStream input = mCon.getInputStream();
		    File f = new File("C:\temp", "file.bin");
		    FileOutputStream fo = new FileOutputStream(f);
		    int read_len = 0;

		    byte[] buffer = new byte[10];
		    
		    while((read_len = input.read(buffer)) > 0) {
		        fo.write(buffer, 0, read_len);
		    }
		    fo.close();
		    mEnd = new Date().getTime();
		    mCon.disconnect();

		    return FILE_SIZE / ((mEnd - mStart) / 1000);	
	}
		return 0;
		
		
		
	}

	@Override
	public void run() {
		if(master.isRunning())
		{
			
			try {
				double speed = test();
				
				master.setRecentDownload(speed);
				
				if(!master.isNearAdvertisedDownload(speed))
				{
					TwitterInterface inter = new TwitterInterface();
					inter.postTweet(master.getHandle() + " Hey! I pay you good money, why can't I get the download speeds you promised?!?");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
		}
	}
	
}
