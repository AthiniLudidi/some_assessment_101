package com.job_assessments.athini;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String [] args){
        //System.out.println("Hello Allan Gray Assessment");

        File userTweetsFile=new File("C:\\Users\\user\\Documents\\My_Projects\\job_assessments\\some_assessment_101\\data_files\\tweet.txt");
        File userConnectionsFile=new File("C:\\Users\\user\\Documents\\My_Projects\\job_assessments\\some_assessment_101\\data_files\\user.txt");


        SortedMap<String, ArrayList<String>> userConnectionPairs= loadUsersAndTheirConnections(userConnectionsFile);

        System.out.println(userConnectionPairs);


        tweetsFeedAlgorithm(userConnectionsFile,userTweetsFile);
    }


    /** A method to display tweets (user's tweets, and tweets from users they follow) on a user's feed in order.
     * @param usersFile - a text file containing users and their connections (users this user follows)
     * @param tweetsFile - a text file containing ordered users tweets
     * */
    private static void tweetsFeedAlgorithm(File usersFile, File tweetsFile){
        Map<String, ArrayList<String>> userConnections= loadUsersAndTheirConnections(usersFile);
        ArrayList<String> tweeters=new ArrayList<>(); //to hold people who tweeted
        ArrayList<String> tweets=new ArrayList<>(); // to hold tweets corresponding to tweeters
        loadUsersTweets(tweeters,tweets,tweetsFile);

        userConnections.forEach((user, connections)->{
            SortedSet<String> sortedUserConnections=new TreeSet<>();
            sortedUserConnections.add(user);
            sortedUserConnections.addAll(connections);


            // first display names (unique) of users (connections) who tweeted (or whose connections have tweeted)
            SortedSet<String> sortedUsersSet=new TreeSet<>();
            if(tweeters.contains(user))
                sortedUsersSet.add(user);
            connections.forEach(u->{
                if(tweeters.contains(u)) {
                    sortedUsersSet.add(user);
                    sortedUsersSet.add(u);
                }
            });
            sortedUsersSet.forEach(System.out::println);

            // now display the tweets, in the order they appear
            for(int x=0; x<=tweets.size()-1; x++) {
                if (sortedUserConnections.contains(tweeters.get(x)))
                    System.out.println("\t@" + tweeters.get(x) + ": " + tweets.get(x));
            }
        });
    }



    /** A method that reads the tweets text file into 2 lists (could not use Maps, need to have duplicates)
     * @param tweeters - the arrayList to hold users who have twitted
     * @param tweets - the ArrayList to hold tweets corresponding to twitters in the tweeters arrayList
     * @param file - a text file containing ordered users tweets
     * */
    private static void loadUsersTweets(ArrayList<String> tweeters, ArrayList<String> tweets, File file){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line= reader.readLine())!=null){
                String userName=line.substring(0, line.indexOf('>'));
                String tweet=line.substring(line.indexOf('>')+2);

                // ensure only twits with <= 140 characters are saved
                if(tweet.length()<=140){
                    tweeters.add(userName);
                    tweets.add(tweet);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** A method that loads the users (and their connections) into a Map (k=users, v=peopleTheyFollow)
     * @param file - the text file containing users and the people they follow
     * @return - a Map containing use-connections pairs
     * */
    private static SortedMap<String, ArrayList<String>> loadUsersAndTheirConnections(File file){
        SortedMap<String, ArrayList<String>> usersConnections=new TreeMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line=reader.readLine())!=null){
                String user=line.substring(0, line.indexOf(" "));
                String follows_space="follows ";
                int followersFirstIndex=line.indexOf(" ")+follows_space.length();
                String [] followsArray=line.substring(followersFirstIndex).split(",");

                //now remove the spaces around the names in the array
                for(int x=0; x<=followsArray.length-1;x++)
                    followsArray[x]=followsArray[x].trim();


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


