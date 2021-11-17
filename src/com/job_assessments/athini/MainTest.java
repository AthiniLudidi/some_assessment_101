package com.job_assessments.athini;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @DisplayName("Should return true as the map is provided with an empty file")
    public void testLoadZeroUsers(){
        File file=new File("C:\\Users\\user\\Documents\\My_Projects\\job_assessments\\some_assessment_101\\data_files\\empty_users_list.txt");
        SortedMap<String, ArrayList<String>> userConnections=Main.loadUsersAndTheirConnections(file);
        Assertions.assertTrue(userConnections.isEmpty());
    }

    @Test
    @DisplayName("Should return true as the map is provided with 1 user-Connections in a file")
    public void testLoadOneUsersConnections(){
        File file=new File("C:\\Users\\user\\Documents\\My_Projects\\job_assessments\\some_assessment_101\\data_files\\test_one_user.txt");
        SortedMap<String, ArrayList<String>> userConnections=Main.loadUsersAndTheirConnections(file);
        Assertions.assertEquals(1,userConnections.size());
    }

    @Test
    @DisplayName("Should return true as the map is provided with more than 1 user-Connections in a file")
    public void testLoadMultipleUsersConnections(){
        File file=new File("C:\\Users\\user\\Documents\\My_Projects\\job_assessments\\some_assessment_101\\data_files\\test_multiple_users.txt");
        SortedMap<String, ArrayList<String>> userConnections=Main.loadUsersAndTheirConnections(file);
        Assertions.assertTrue(userConnections.size()>1);
    }

    @Test
    @DisplayName("Should throws relevant IO exceptions when incorrect files are provided")
    public void testIOFoundException(){
        // first test wrong file- expecting FileNotFoundException
        Assertions.assertThrows(FileNotFoundException.class,()->{
            //File file=new File("lol.txt");
            Main.loadUsersAndTheirConnections(new File("nonExistent.txt"));
        }, "FileNotFoundException was expected");
    }

    @Test
    @DisplayName("Should throws null exceptions when incorrect files are provided")
    public void testForNullFileProvided(){
        // first test wrong file- expecting FileNotFoundException
        File file=null;
        Assertions.assertThrows(NullPointerException.class,()->{
            Main.loadUsersAndTheirConnections(file);
        }, "NullPointerException was expected");
    }
}