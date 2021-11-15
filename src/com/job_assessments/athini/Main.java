package com.job_assessments.athini;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String [] args){
        System.out.println("Hello Allan Gray Assessment");
    }


    private static void tweetAlgorithm(File users, File tweetsFile){

        Map<String, ArrayList<String>> userConnections=getUserConnections(users);
        Map<String, ArrayList<String>> userTwits=getUsersTweets(tweetsFile);

        userConnections.forEach((user, connections) -> {
            if(userTwits.containsKey(user)) {
                System.out.println(user);
            }
            connections.forEach(conn -> {
                if(userTwits.containsKey(conn)){
                    System.out.println(conn);
                }
            });

            // first print all the tweets by this user
            if (userTwits.containsKey(user)) {
                userTwits.get(user).forEach(tweet->{
                    System.out.println("\t@" + user + ": " + tweet);
                });
            }

            // now print all the tweets by the users this user follows
            connections.forEach(connection ->
            {
                if (userTwits.containsKey(connection)) {
                    System.out.println("\t@" + connection + ": " + userTwits.get(connection));
                }
            });

        });


    }

    // read the users tweets file, and populate a map/dictionary
    private static SortedMap<String, ArrayList<String>> getUsersTweets(File file){
        SortedMap<String, ArrayList<String>> usersTweets=new TreeMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
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

    // read the users connections file, and populate a map/dictionary
    private static SortedMap<String, ArrayList<String>> getUserConnections(File file){
        SortedMap<String, ArrayList<String>> usersConnections=new TreeMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line=reader.readLine())!=null){
                String user=line.substring(0, line.indexOf(" "));
                String follows_space="follows ";
                int followersFirstIndex=line.indexOf(" ")+follows_space.length();
                String [] followsArray=line.substring(followersFirstIndex).split(",");


                if(!usersConnections.containsKey(user)){
                    ArrayList<String> followers=new ArrayList<>();
                    Collections.addAll(followers, followsArray);
                    usersConnections.put(user,followers);
                }
                else{
                    ArrayList<String> current=usersConnections.get(user);
                    for(int x=0; x<=followsArray.length-1;x++){
                        if(!current.contains(followsArray[x])){
                            current.add(followsArray[x]);
                        }
                    }
                    usersConnections.replace(user,current);
                }

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return usersConnections;
    }
}


