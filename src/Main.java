import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.util.Random;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Main {
	
	static MentionsParser mentionsParser = new MentionsParser();
	static ResponseList<Status> previousMentions = null;
	static ResponseList<Status> currentMentions = null;
	static ResponseList<Status> mentionsToUpdate = null;
	static int count;

	public static void main(String[] args){
		// TODO Auto-generated method stub

		Timer timer = new Timer();
		Tscheduler schTweets = new Tscheduler();
		timer.scheduleAtFixedRate(schTweets, 0, 900000);
		
		}
			
		public void NewTweet() {
			Twitter twitter = TwitterFactory.getSingleton();
			String screenName;
			String myTweet = "";
			Random rand = new Random();
			ResponseList<Status> mentionsToUpdate = MentionsToUpdate();
			String[] gamesToSuggest = {"Arkham City", "Xenoblade Chronicles 2", "Final Fantasy XIV", "Into the Breach", "Faster Than Light",
					"Dragon Age Origins", "Neverwinter Nights", "Fire Emblem Three Houses", "Super Smash Bros Ultimate", "Super Mario Odyssey", 
					"Legend of Zelda Breath of the Wild", "Assassins Creed Odyssey", "Ghost of Tsushima", "Uncharted", "Horizon Zero Dawn",
					"God of War", "Final Fantasy VII Remake", "Animal Crossing New Horizon", "Stardew Valley", "Donkey Kong Tropical Freeze"};
			
			for(int i = 0; i <= mentionsToUpdate.size() - 1; ++i) {
				int randInt = rand.nextInt(19);
				screenName = mentionsParser.getMentionsScreenName(mentionsToUpdate.get(i));
				myTweet = "@" + screenName + " you should play " + gamesToSuggest[randInt];
				
				//update the status
				try {
				   Status status = twitter.updateStatus(myTweet);
				   System.out.print("Sucessful " + status.getText());
				}
				catch(TwitterException e){
					e.printStackTrace();
					System.out.print("failed");
					}
			}
			
			previousMentions = currentMentions;
			mentionsToUpdate = null;
			
			} 
		
		public static ResponseList<Status> MentionsToUpdate(){ 
			
			Twitter t = TwitterFactory.getSingleton();
			try {
			currentMentions = t.getMentionsTimeline();		
			}
			catch(TwitterException e){
				e.printStackTrace();
			}
			
			try {
			mentionsToUpdate = t.getMentionsTimeline();		
			}
			catch(TwitterException e){
				e.printStackTrace();
			}
			
			if (previousMentions == null) {
				ResponseList<Status> previousMentionsHolder = null;
				try {
					previousMentionsHolder = t.getMentionsTimeline();		
					}
				catch(TwitterException e){
					e.printStackTrace();
					}
				
				previousMentions = previousMentionsHolder;
			}
			
			for(int i = previousMentions.size() - 1; i >= 0; --i){
				   for(int j = currentMentions.size() - 1; j >= 0; --j){
				      if(previousMentions.get(i).equals(currentMentions.get(j))){
				         mentionsToUpdate.remove(j);
				     }
				   }
				}
			

			return mentionsToUpdate;
		}
			
	

	

	
}
