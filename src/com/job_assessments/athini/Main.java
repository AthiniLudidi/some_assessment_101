package com.job_assessments.athini;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String [] args){
        System.out.println("Hello Allan Gray Assessment");
    }


    private static void tweetAlgorithm(File users, File tweets){

        Map<String, ArrayList<String>> userConnections=getUserConnections(users);
        Map<String, ArrayList<String>> userTwits=getUsersTweets(tweets);

        


    }

    // read the users tweets file, and populate a map/dictionary
    private static Map<String, ArrayList<String>> getUsersTweets(File file){
        Map<String, ArrayList<String>> usersTweets=new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line="";
            while ((line= reader.readLine())!=null){
                //System.out.println(line);
                String userName=line.substring(0, line.indexOf('>'));
                String tweet=line.substring(line.indexOf('>')+2);

                if(!usersTweets.containsKey(userName)){
                   ArrayList<String> tweets=new ArrayList<>();
                   tweets.add(tweet);
                   usersTweets.put(userName,tweets);
                }
                else{
                    ArrayList<String> tweets=usersTweets.get(userName);
                    tweets.add(tweet);
                    usersTweets.replace(userName,tweets);
                }

            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return usersTweets;
    }

    // read the users connections file, and purpulate a map/dictionary
    private static Map<String, ArrayList<String>> getUserConnections(File file){
        Map<String, ArrayList<String>> usersConnections=new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line="";
            while ((line=reader.readLine())!=null){
                String user=line.substring(0, line.indexOf(" "));

                String follows_space="follows ";

                int followersFirstIndex=line.indexOf(" ")+follows_space.length();

                ArrayList<String> followers=new ArrayList<>();
                Collections.addAll(followers, line.substring(followersFirstIndex).split(","));
                usersConnections.put(user,followers);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return usersConnections;
    }
}


