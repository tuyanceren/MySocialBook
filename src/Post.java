import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;

class Post {
    
    String text;
    Location location;
    Date created;

    ArrayList<User> tagFriends;


    public Post(String text,double e,double b,ArrayList<User> tagFriends) {
        this.text = text;
        this.location = new Location(e,b);
        this.tagFriends = tagFriends;
        this.created = new Date();
    }

 
    public String toString() {
        String output = text + "\n" 
        				+ "Date: " + created.toString() 
        				+ "\n"+ location;

        if(tagFriends!=null){
            output += "\nFriends tagged in this post: ";
            for (User user : tagFriends) 
                output += user.getUsername() + ",";
        }
        return output;
    }
}
class VideoPost extends Post {
    
    private String filename;
    private int duration;
    private int maxVideoLength = 10; 
    
    //CONSTRUCTOR
    
    public VideoPost(String text, String filename,int duration,double e,double b,ArrayList<User> tagFriends) {
        super(text, e, b, tagFriends);
        if(duration > maxVideoLength)
            throw new InvalidParameterException("Error: Your video exceeds maximum allowed duration of 10 minutes.");
    
        this.filename = filename;
        this.duration = duration;
    }

    //GETTER-SETTER
   
    public String getFilename() {
        return this.filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public int getDuration() {
        return this.duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
    	String ext= ("Video: " + filename + "\n" 
					+ "Video duration: " + duration + " minutes");
        return super.toString() + "\n" + ext;
    }
}
class ImagePost extends Post{
	
    private String filename;
    private String resolution;
    
    //CONSTRUCTOR

    public ImagePost(String text,String filename,String resolution,double e ,double b,ArrayList<User> tagFriends) {
        super(text, e, b, tagFriends);
        this.filename = filename;
        this.resolution = resolution;
    }

    //GETTER-SETTER
    public String getFilename() {
        return this.filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getResolution() {
        return this.resolution;
    }
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String toString() {
    	String ext1=("Image: " + filename + "\n" 
        			+"Image resolution: " + resolution);
        return super.toString() + "\n" + ext1 ;
    }
}

    
