import java.util.*;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	static Map<String, User> userDetails = new HashMap<String, User>();
	static int bufferSize = 3; // Default buffer size
	
	public static void main(String[] args) {
		System.out.println("Welcome\n");
		getBufferSize();
		getOptionFromUser();
	}
	
	public static void getBufferSize() {
		try {
			System.out.println("Enter bufferSize (value should be greater than \"0\")\n");
			bufferSize = Integer.parseInt(sc.nextLine());
			if(bufferSize <= 0) {
				getBufferSize();
			}
		}catch(Exception e) {
			System.out.println("Exception while getting buffer size");
			getBufferSize();
		}
	}
	
	public static User createUser(String name, ArrayList<String> songList) {
		if(songList == null) {
			songList = new ArrayList<String>(bufferSize); // Array buffer of size 'N'
		}
		if(name != null && songList != null) {
			return new User(name, songList);
		}
		return null;
	}
	
	public static void getOptionFromUser() {
		System.out.println("\nEnter \"1\" to Add a new user");
		System.out.println("Enter \"2\" to View the song list of the user");
		System.out.println("");
		int actionID = -1;
		try {
			actionID = Integer.parseInt(sc.nextLine());
		} catch(Exception e) {
			System.out.println("\nPlease Enter a valid input, Enter either \"1\" (or) \"2\" \n");
			getOptionFromUser();
		}
		performActionID(actionID);
	}
	
	public static void performActionID(int actionID) {
		
		switch(actionID) {
		
		    case 1:              // Add a new user
		        setNewUser();
		        break;
		    case 2:             // View the user song list
		    	showAllUsersSongList();
		    	break;
		    default: 
		    	System.out.println("\nPlease Enter a valid input\n");
		    	getOptionFromUser();
		}
		
	}
	
	public static void setNewUser() {
		try {
			System.out.println("\nEnter a new username:\n");
			String userName = sc.nextLine().toLowerCase();
			boolean userCreated = false;
			if(userDetails.containsKey(userName)) {
				System.out.println("\nSorry this username is already taken, Please try a different name\n");
				setNewUser();
			} else {
				User newUser = createUser(userName, null);
				userDetails.put(userName, newUser);
				userCreated = true;
			}
			if(userCreated) {
				getSongToPlay(userName);
			}
			
		}catch(Exception e) {
			System.out.println("\nException while getting username: "+e);
		}	
	}
	
	public static void getSongToPlay(String userName) {
		try {
			System.out.println("\nEnter a song to play\n");
			String song = sc.nextLine().toLowerCase();
			validateAndUpdateSongsToList(userName, song);
			
			
		}catch(Exception e) {
			System.out.println("\nException while getting song to play: "+e+"\n");
		}
	}
	
	public static void validateAndUpdateSongsToList(String userName, String song) {
		try {
			User currentUser = userDetails.get(userName);
			ArrayList<String> currentUserSongList = currentUser.getSongList();
			if(currentUserSongList != null && currentUserSongList.size()>0 && !currentUserSongList.contains(song)) {
				// If the new song is not present just drop the 
				if(currentUserSongList.size() == bufferSize) {
					currentUserSongList.remove(0);	 // since the size is maximum we have to remove the 1'st one	
				}
				currentUserSongList.add(song);
			} else if(currentUserSongList!=null && currentUserSongList.size()>0 && currentUserSongList.contains(song)) {
				int songIndexInTheList = currentUserSongList.indexOf(song);
				currentUserSongList.remove(songIndexInTheList);
				currentUserSongList.add(song);
			} else if(currentUserSongList != null && currentUserSongList.size() == 0) {
				currentUserSongList.add(song);
			}
			currentUser.setSongList(currentUserSongList);
			userDetails.put(userName, currentUser);
			System.out.println("\nSong added to list\n");
			addAnotherSong(userName);
			
		}catch(Exception e) {
			System.out.println("Exception while validating and updating songs list: "+e);
		}
	}
	
	public static void showAllUsersSongList() {
		try {
			if(userDetails != null && userDetails.size()>0) {
				System.out.println("\nPlease find every users song list"+ userDetails.size());
				for(String key : userDetails.keySet()) {
					User user = userDetails.get(key);
					System.out.println(user.getName()+" "+user.getSongList());
				}
				System.out.println("\n");
				
			} else {
				System.out.println("There are no records..! Please add a user and add some songs to the user\n");
				getOptionFromUser();
			}
			getOptionFromUser();
			
		}catch(Exception e) {
			System.out.println("Exception while showing all user names and getting the required username for vieweing their song queue: "+e);
		}
	}
	
	public static void addAnotherSong(String userName) {
		System.out.println("Enter another song to add to the list else Type \"No\" to return to main");
		String nextSong = sc.nextLine().toLowerCase();
		if(nextSong.equalsIgnoreCase("NO")) {
			getOptionFromUser();
		}
		else if(isValidSongName(nextSong)) {
			validateAndUpdateSongsToList(userName, nextSong);
		} else {
			System.out.println("Something went wrong");
			getOptionFromUser();
		}
	}
	
	public static boolean isValidSongName(String song) {
		try {
			song = song.trim();
			return (song != null && song.length()>0 && !song.equalsIgnoreCase("NO"));
		}catch(Exception e) {
			System.out.println("Exception while checking it is a valid song");
		}
		return false;
	}
	
	
}

class User {
	
	private String name;
	private ArrayList<String> songList;
	
	User(String name, ArrayList<String> songList) {
		this.name = name;
		this.songList = songList;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<String> getSongList() {
		return this.songList;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSongList(ArrayList<String> songList) {
		this.songList = songList;
	}
	
}
