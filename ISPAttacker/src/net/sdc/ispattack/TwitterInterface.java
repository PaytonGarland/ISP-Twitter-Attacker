package net.sdc.ispattack;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TwitterInterface {

	private Twitter twitter;
	private AccessToken accessToken;
	private TwitterFactory factory = null;
	
	public TwitterInterface(String file)
	{
		setConfiguration(file);
		twitter = factory.getInstance();
		setAccessToken();
	}
	
	public TwitterInterface()
	{
		this(".\\conf.txt");
	}
	
	public void setConfiguration(String file)
	{
		try {
			File inputFile = new File(file);
			FileInputStream io = new FileInputStream(inputFile);
			Scanner scan = new Scanner(io);
			
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey(scan.nextLine())
			  .setOAuthConsumerSecret(scan.nextLine())
			  .setOAuthAccessToken(scan.nextLine())
			  .setOAuthAccessTokenSecret(scan.nextLine());
			factory = new TwitterFactory(cb.build());
			scan.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
		
	}
	
	public void setAccessToken()
	{
		try {
		// get request token.
        // this will throw IllegalStateException if access token is already available
        RequestToken requestToken = twitter.getOAuthRequestToken();
        System.out.println("Got request token.");
        System.out.println("Request token: " + requestToken.getToken());
        System.out.println("Request token secret: " + requestToken.getTokenSecret());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
            String pin = br.readLine();
            try {
                if (pin.length() > 0) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                } else {
                    accessToken = twitter.getOAuthAccessToken(requestToken);
                }
            } catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    System.out.println("Unable to get the access token.");
                } else {
                    te.printStackTrace();
                }
            }
        }
        System.out.println("Got access token.");
        System.out.println("Access token: " + accessToken.getToken());
        System.out.println("Access token secret: " + accessToken.getTokenSecret());
		}
		catch (IllegalStateException e)
		{
			if (!twitter.getAuthorization().isEnabled()) {
                System.out.println("OAuth consumer key/secret is not set.");
                System.exit(-1);
		}
		}
		catch (TwitterException te)
		{
			te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
		}
		catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to read the system input.");
            System.exit(-1);
        }
	}
	
	public boolean postTweet(String message)
	{
		try {
			Status status = twitter.updateStatus(message);
			return true;
		} catch (TwitterException e) {
			return false;
		}
        
	}
	
}


