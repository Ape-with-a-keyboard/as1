package ape_with_a_keyboard.wankinvi_habittracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Vinson on 2016-9-28.
 * Habit class is to set up all the functions for
 * habits. 2 list is created for the number of days recuring
 * and completion dates. number of days is to check if the user put in
 * a day of week for a certain habit
 * UUID is used to provide unique identifier for each habit
 */
public class Habit {

    private ArrayList<Integer> days;
    private ArrayList<Date> completions;
    private UUID uuid;
    private String name;
    private Date date;

    //A habit contains the below attributes
    public Habit(UUID uuid, String name, Date date, ArrayList<Integer> days) {
        this.uuid = uuid;
        this.name = name;
        this.date = date;
        this.days = days;
        this.completions = new ArrayList<>();
    }
    //return id of habit
    public UUID getUUID() {

        return this.uuid;
    }
    //remove completion with position in list
    public void removeCompletion(int position) {

        this.completions.remove(position);
    }
    //get all completions
    public ArrayList<Date> getCompletions() {

        return this.completions;
    }
    //get habit's description/name
    public String getName() {

        return this.name;
    }
    //get date of habit
    public Date getDate() {
        return this.date;
    }
    //return days of the week of a habit
    public ArrayList<Integer> getDays() {
        return this.days;
    }
    // add completion
    public void addCompletion(Date date) {
        this.completions.add(date);
    }

}
