package com.example.masoud.a2017_06_15_database_project.Model;

import com.example.masoud.a2017_06_15_database_project.Constants;

import java.util.ArrayList;

/**
 * Created by masoud on 2017-06-15.
 */

public class Person {

    private String name;
    private String phone;
    private String email;

    private ArrayList<Boolean> availabilityBooleanArrayList;
    private ArrayList<String> tasksArrayList;

    private Role role;


    public Person(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;

        //--------------------------------- Create Array Lists
        availabilityBooleanArrayList = new ArrayList<>();

        availabilityBooleanArrayList.add(0, false);
        availabilityBooleanArrayList.add(1, false);
        availabilityBooleanArrayList.add(2, false);
        availabilityBooleanArrayList.add(3, false);


        tasksArrayList = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    //---------------------------------------------- Availability
    public ArrayList<Boolean> getAvailabilityBooleanArrayList() {
        return availabilityBooleanArrayList;
    }

    public void setAvailabilityBooleanArrayList(ArrayList<Boolean> availabilityBooleanArrayList) {
        this.availabilityBooleanArrayList = availabilityBooleanArrayList;
    }


    //-----------------------------------------------------------------
    public void addAvailability(int timeSlotAvailabilityIndex){
        this.availabilityBooleanArrayList.add(timeSlotAvailabilityIndex, true);
    }


    //---------------------------------------------- TASKS
    public ArrayList<String> getTasksArrayList() {
        return tasksArrayList;
    }

    public void setTasksArrayList(ArrayList<String> tasksArrayList) {
        this.tasksArrayList = tasksArrayList;
    }

    public void addNewTask(String newTask){
        this.tasksArrayList.add(newTask);
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", availabilityBooleanArrayList=" + availabilityBooleanArrayList +
                ", role=" + role +
                '}';
    }
}
