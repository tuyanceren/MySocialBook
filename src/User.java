import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class User{

    private String name;
    private String username;
    private String password;
    private String school;
    private int userID;

    Date dateOfBirth;
    Date logInDate;

    ArrayList<User> friends;
    ArrayList<Post> posts;
    private ArrayList<User> blocked_users;
    
    public User(int userID,String name,String username,String password,Date dateOfBirth,String school) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.school = school;
        this.userID = userID;
        
        this.friends = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.blocked_users = new ArrayList<>();
    }
    public int getUserID() {
        return this.userID;
    }
    public String getName() {
        return this.name;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    

    public void signIn(){
        this.logInDate = new Date();
        System.out.println("You have successfully signed in.");
    }

    public void signOut(){
        System.out.println("You have successfully signed out.");
    }

    public void showPosts(){
        if (posts==null) {
            System.out.println(username + " does not have any posts yet.");
            return;
        }
        
        System.out.println("************************" +"\n"
        					+username + "'s Posts"+"\n"
        					+"************************");

        for (Post post : posts) {
            System.out.println(post +"\n"+"---------------------------");
        }
    }
    
    public void update(String name,Date dateOfBirth,String school){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.school = school;  
    }

    public void changePassword(String oldP,String newP){
        if (!password.equals(oldP)) {
            System.out.println("Password mismatch!");
            return;
        }
        this.password = newP;
    }

    public void addFriend(User friend){
        if (friends.contains(friend)) {
            System.out.println("This user is already in your friend list!");
            return;
        }
        friends.add(friend);
        System.out.println(friend.getUsername() + " has been successfully added to your friend list.");
    }

    public void removeFriend(User friend){
        if (!friends.contains(friend)) {
            System.out.println("No such friend!");
            return;
        }
        friends.remove(friend);
        System.out.println(friend.getUsername() + " has been successfully removed from your friend list.");
    }
    public void addTextPost(String text,double b,double e,ArrayList<User> tagFriends){
    
        Iterator<User> iterator = tagFriends.iterator();
        while (iterator.hasNext()) {
            User i = iterator.next();
            if (!friends.contains(i)){
                System.out.println(i.getUsername() + " is not your friend, and will not be tagged!");
                iterator.remove();
            }
        }
        posts.add(new Post(text,e,b,tagFriends));
        System.out.println("The post has been successfully added.");
    }


    public void addImagePost(String text,String filename,String resolution,double b,double e,ArrayList<User> tagFriends){

        Iterator<User> itr = tagFriends.iterator();
        while (itr.hasNext()) {
            User u = itr.next();
            if (!friends.contains(u)){
                System.out.println(u.getUsername() + " is not your friend, and will not be tagged!");
                itr.remove();
            }
        }
        posts.add(new ImagePost(text,filename,resolution,e,b,tagFriends));
        System.out.println("The post has been successfully added.");
    }

    public void addVideoPost(String text,String filename,int duration,double b,double e,ArrayList<User> tagFriends){

        Iterator<User> itr = tagFriends.iterator();
        while (itr.hasNext()) {
            User i = itr.next();
            if (!friends.contains(i)){
                System.out.println(i.getUsername() + " is not your friend, and will not be tagged!");
                itr.remove();
            }
        }

        try {
            posts.add(new VideoPost(text,filename,duration,e,b,tagFriends));
            System.out.println("The post has been successfully added.");
        } catch (InvalidParameterException ıpe) {
            System.out.println(ıpe.getMessage());
        }
    }   

    public void removePost(){
        if (posts==null) {
            System.out.println("Error: You do not have any post.");
            return;
        }
        posts.remove(posts.get(posts.size()-1));
        System.out.println("Your last post has been successfully removed.");
    }

    public void blockUser(User user){
        this.blocked_users.add(user);
        System.out.println(user.getUsername() + " has been successfully blocked.");
    }


    public void unblockUser(User user){
        if(!blocked_users.contains(user)) {
            System.out.println("No such user in your blocked-user list!");
            return;
        }
        this.blocked_users.remove(user);
        System.out.println(user.getUsername() + " has been successfully unblocked.");
    }


    public void listFriends(){
        if(friends==null) {
            System.out.println("You have not added any friend yet!");
            return;
        }

        for (User user : friends) {
            System.out.println(user);
        }
    }

    public void listBlockedFriends(){
        boolean search = false;
        for (User user : blocked_users) {
            if(friends.contains(user)) {
                search = true;
                System.out.println(user);
            }
            else {
            	System.out.println("You haven't blocked any friend yet!");
            }
        }     
    }

 
    public void listBlockedUsers(){
        if(blocked_users==null){
            System.out.println("You haven't blocked any user yet!");
            return;
        }
        for (User user : blocked_users) 
            System.out.println(user);
    }

    @Override
    public String toString() {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(dateOfBirth);
        String a = "Name: " + name + "\n" +
                   "Username: " + username + "\n" +
	               "Date of Birth: " + date + "\n" + 
	               "School: " + school + "\n" + 
	               "--------------------------------";
        return a;
    }
}