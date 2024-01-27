import java.io.IOException;
import java.net.URI;


class Handler implements URLHandler {
    int num = 0;
    String openingText="THE CHAT WILL SHOW HERE";
    String chatString="";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            if(num==0){
                num+=1;
                return String.format(openingText);
            }
            return String.format(chatString);
            
        } else if (url.getPath().contains("/add-message")) {
            String[] parameters= url.getQuery().split("=");
            int posOfAnd=0;
            for(int i=0;i<parameters[1].length();i++){
                if(parameters[1].charAt(i)=='&'){
                    posOfAnd=i;
                }
            }
           String currentUser= parameters[2];
           String currentChat= parameters[1].substring(0,posOfAnd);
           chatString= chatString+ currentUser+ ": "+ currentChat+ "\n";
           return String.format("Chat added!");
        } else {
            return "404 Not Found!";
        }
    }
}

class ChatServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}