package com.example.masoud.a2017_06_15_database_project;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.masoud.a2017_06_15_database_project.Model.*;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by masoud on 2017-06-25.
 */

public class XmlReader {


    private final static

    String
    NAME_TAG  = "name",
    PHONE_TAG = "phone",
    EMAIL_TAG = "email",
    TASK_TAG  = "task",

    A1_TAG = "a1",
    A2_TAG = "a2",
    A3_TAG = "a3",
    A4_TAG = "a4",

    AVAILABLE_TAG = "available";

    private static String name;
    private static String phone;
    private static String email;
    private static String task;

    private static String a1;
    private static String a2;
    private static String a3;
    private static String a4;

    private static boolean parsingName, parsingPhone, parsingEmail, parsingTask, parsingA1, parsingA2, parsingA3, parsingA4;

    public static ArrayList<Person> personsArraylist;

    public static void processXMLFile(Context context, String fileName){

        personsArraylist = new ArrayList<>();

        XmlPullParserFactory pullParserFactory;
        AssetManager assetManager = context.getResources().getAssets();

        try {
            pullParserFactory           = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = pullParserFactory.newPullParser();

            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(fileName));
            xmlPullParser.setInput(inputStreamReader);

            while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT){

                switch (xmlPullParser.getEventType()){

                    case XmlPullParser.START_TAG:
                        processStartTag(xmlPullParser.getName());
                        break;

                    case XmlPullParser.TEXT:
                        processText(xmlPullParser.getText());
                        break;

                    case XmlPullParser.END_TAG:
                    processEndTag(xmlPullParser.getName());
                    break;

                }
                xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processStartTag(String name) {

        if (name.equals(NAME_TAG)){
            parsingName = true;
        }
        if (name.equals(PHONE_TAG)){
            parsingPhone = true;
        }
        if (name.equals(EMAIL_TAG)){
            parsingEmail = true;
        }
        if (name.equals(TASK_TAG)){
            parsingTask = true;
        }

        //------------------------------------------ Boolean
        if (name.equals(A1_TAG)){
            parsingA1 = true;
        }
        if (name.equals(A2_TAG)){
            parsingA2 = true;
        }
        if (name.equals(A3_TAG)){
            parsingA3 = true;
        }
        if (name.equals(A4_TAG)){
            parsingA4 = true;
        }
    }

    private static void processEndTag(String nameParam) {

        if (nameParam.equals(NAME_TAG)){
            parsingName = false;
        }
        if (nameParam.equals(PHONE_TAG)){
            parsingPhone = false;
        }
        if(nameParam.equals(EMAIL_TAG)){
            parsingEmail = false;
        }
        if(nameParam.equals(TASK_TAG)){
            parsingTask = false;
        }

        //------------------------------------------ Boolean
        if (nameParam.equals("a1")){
            parsingA1 = false;
        }
        if (nameParam.equals("a2")){
            parsingA2 = false;
        }
        if (nameParam.equals("a3")){
            parsingA3 = false;
        }
        if (nameParam.equals("a4")){
            parsingA4 = false;

            //-------------------------------------- Create Person object
            Person person = new Person(name, phone, email);

            if(a1.equals("y")){
                person.addAvailability(0);
            }
            if(a2.equals("y")){
                person.addAvailability(1);
            }
            if(a3.equals("y")){
                person.addAvailability(2);
            }
            if(a4.equals("y")){
                person.addAvailability(3);
            }


            personsArraylist.add(person);
//            person.addNewTask(task);

            Log.d("XML", person.toString());
            System.out.println(person.toString());
        }
    }

    private static void processText(String text) {

        if(parsingName){
            name = text;
        }

        if (parsingPhone){
            phone = text;
        }

        if (parsingEmail){
            email = text;
        }

        if (parsingTask){
            task = text;
        }

        if (parsingA1){
            a1 = text;
        }

        if (parsingA2){
            a2 = text;
        }

        if (parsingA3){
            a3 = text;
        }

        if (parsingA4){
            a4 = text;
        }
    }


}
