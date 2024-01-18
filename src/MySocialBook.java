import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;  
import java.time.LocalDate;
import java.time.ZoneId;

public class MySocialBook {
    
    public static void main(String[] args) {

        ArrayList<User> users = new ArrayList<>();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        
        try {
            Scanner scanner = new Scanner(new File("users.txt"));
            String[] line;
            int ID = 1; 


            while (scanner.hasNextLine()){
                line = scanner.nextLine().split("\t");
                String[] d = line[3].split("/");
                LocalDate localDate = LocalDate.of(Integer.parseInt(d[2]),Integer.parseInt(d[0]),Integer.parseInt(d[1]));
                Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
                users.add(new User(ID,line[0],line[1],line[2],date,line[4]));
                ID++;
            }
            scanner.close();

            scanner = new Scanner(new File("commands.txt"));
            String signedInUser = ""; 

        
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                String[] splited = command.split("\t");
                System.out.println("-----------------------");
                System.out.println("Command: "+ command );
                switch (splited[0]) {
                	case "ADDUSER" ->  {
                		String[] d = splited[4].split("/");
                		LocalDate localDate = LocalDate.of(Integer.parseInt(d[2]),Integer.parseInt(d[0]),Integer.parseInt(d[1]));
                        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
                		users.add(new User(ID,splited[1],splited[2],splited[3],date,splited[5]));
                		
                		System.out.println(splited[1] + " has been successfully added.");
                		ID++;
                	}
                	case "REMOVEUSER" -> {
                		String user[] = command.split("\t");
                		int id = Integer.parseInt(user[1]);
                        User u = findUser(users,id);
    
                        if (u != null) {
                            users.remove(u);
                            System.out.println("User has been successfully removed.");
                        }else
                            System.out.println("No such user!");
                    }
                	case "SIGNIN" -> {
                		String username = splited[1];
                        String password = splited[2];
                        User u = findUserName(users, username);
                   
                        if (u == null || !u.getPassword().equals(password)){
                  
                            if (!signedInUser.equals("")) {
                                System.out.println("Only one user can sign in at a time!");
                            }
                        }
                        else if  ( u !=null ) {
                            u.signIn();
                            signedInUser = username;
                        }
                    }       
                	case "LISTUSERS" -> { 
                		if (signedInUser.equals("")) {
                			System.out.println("Error: Please sign in and try again.");
                		}
                		else {
                			for (User user : users) {
                				System.out.println(user);
                			}
                		}	
                	}
                	case "UPDATEPROFILE" -> {
                    String name = splited[1];
                    String school = splited[3];
                    String[] d = splited[2].split("/");
                    LocalDate localDate = LocalDate.of(Integer.parseInt(d[2]),Integer.parseInt(d[0]),Integer.parseInt(d[1]));
                    Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

                    if (signedInUser.equals(""))
                        System.out.println("Error: Please sign in and try again.");
                    else {
                        User u = findUserName(users, signedInUser);
                        u.update(name,date,school);
                    }
                }
					case "CHPASS" -> {
						String oldp = splited[1];
	                    String newp= splited[2];
	                    	           
	                    if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.changePassword(oldp,newp);
	                    }					                		
					}
					case "ADDFRIEND" -> {
						String username = splited[1];
	                    User friend = findUserName(users, username);

	                    if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                    
	                    else if (friend == null)
	                        System.out.println("No such user!");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.addFriend(friend);
	                    }	
					}
					case "REMOVEFRIEND" -> {
						String username = splited[1];
	                    User friend = findUserName(users, username);

	                    if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                   
	                    else if (friend == null)
	                        System.out.println("No such user!");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.removeFriend(friend);
	                    }
					}
					case "LISTFRIENDS" -> {
						
	                    if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.listFriends();
	                    }
					}
					case "ADDPOST-TEXT" -> {
						 String text = splited[1];
		                    double longt = Double.parseDouble(splited[2]);
		                    double lat = Double.parseDouble(splited[3]);

		                    String tags[] = splited[4].split(":");
		                    ArrayList<User> taggedFriends = new ArrayList<>();

		                    for(String tag: tags){
		                        User u = findUserName(users,tag);
		                        if (u != null)
		                            taggedFriends.add(u);
		                        else
		                            System.out.println(tag + " is not your friend, and will not be tagged!");
		                    }
		                    if (signedInUser.equals(""))
		                        System.out.println("Error: Please sign in and try again.");
		                    else {
		                        User u = findUserName(users, signedInUser);
		                        u.addTextPost(text,longt,lat,taggedFriends);
		                    }
					}
					case "ADDPOST-IMAGE" -> {
						String text = splited[1];
	                    double longt = Double.parseDouble(splited[2]);
	                    double lat = Double.parseDouble(splited[3]);
	                    String filename = splited[5];
	                    String resolution = splited[6];

	                    String tags[] = splited[4].split(":");
	                    ArrayList<User> taggedFriends = new ArrayList<>();
	                    
	                    for(String tag: tags){
	                        User u = findUserName(users,tag);
	                        if (u != null)
	                            taggedFriends.add(u);
	                        else
	                            System.out.println(tag + " is not your friend, and will not be tagged!");
	                    }
	                    if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.addImagePost(text,filename,resolution,longt,lat,taggedFriends);
	                    }						
					}
					case "ADDPOST-VIDEO" -> {
						String text = splited[1];
	                    double longt = Double.parseDouble(splited[2]);
	                    double lat = Double.parseDouble(splited[3]);
	                    String filename = splited[5];
	                    int duration = Integer.parseInt(splited[6]);

	                    String tags[] = splited[4].split(":");
	                    ArrayList<User> taggedFriends = new ArrayList<>();
	                    for(String tag: tags){
	                        User u = findUserName(users,tag);
	                        if (u != null)
	                            taggedFriends.add(u);
	                        else
	                            System.out.println(tag + " is not your friend, and will not be tagged!");
	                    }
	                    if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.addVideoPost(text,filename,duration,longt,lat,taggedFriends);
	                    }
					}
					case "REMOVELASTPOST" -> {
	                    if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.removePost();
	                    }
					}
					case "SHOWPOSTS" -> {
						String username = splited [1];
	                    User u = findUserName(users,username);

	                    if (u != null)  {
	                        u.showPosts();
	                    }
	                    else {
	                        System.out.println("No such user!");
	                    }
	                }
					case "BLOCK" -> {
						String username = splited[1];
	                    User block = findUserName(users,username);

	                    if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                    else if (block == null)
	                        System.out.println("No such user!");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.blockUser(block);
	                    }	
					}
					case "SHOWBLOCKEDFRIENDS" -> {
						if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.listBlockedFriends();
	                    }
					}
					case "UNBLOCK" -> {
						String username = splited[1];
	                    User unblock = findUserName(users,username);

	                    if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.unblockUser(unblock);
	                    }
	                }
					case "SHOWBLOCKEDUSERS" -> {
						if (signedInUser.equals(""))
	                        System.out.println("Error: Please sign in and try again.");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.listBlockedUsers();
	                    }
					}
					case "SIGNOUT" -> {
						if (signedInUser.equals(""))
	                        System.out.println("You can't sign out if you didn't sign in.");
	                    else {
	                        User u = findUserName(users, signedInUser);
	                        u.signOut();
	                        signedInUser = "";
	                    }
					}
        }
        }
            
            scanner.close();

        } catch (FileNotFoundException error) {
            System.out.println("users.txt not found");
        }
    }
    
    public static User findUser(ArrayList<User> users, int ID){
        for (User user : users) {
            if(user.getUserID() == ID)
                return user;
        }
        return null;
    }

    public static User findUserName(ArrayList<User> users, String username){
        for (User user : users) {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }
}
