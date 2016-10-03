package ape_with_a_keyboard.wankinvi_habittracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vinson on 2016-10-02.
 */

public class HabitAppTest {
    @Test
    public void testGetUUID() {
        ArrayList<Integer> days = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        days.addAll(Arrays.asList(0, 1, 2, 3, 4));
        Habit habit = new Habit(uuid, "test", new Date(), days);
        assertEquals(habit.getUUID(), uuid);
    }

    @Test
    public void testRemoveCompletion() {
        ArrayList<Integer> days = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        days.addAll(Arrays.asList(0, 1, 2, 3, 4));
        Habit habit = new Habit(uuid, "test", new Date(), days);
        Date date = new Date(3);
        habit.addCompletion(date);
        habit.removeCompletion(0);
        //check if the completion list is empty
        assertTrue(habit.getCompletions().isEmpty());
    }


    @Test
    public void testGetCompletions() {
        UUID uuid = UUID.randomUUID();
        ArrayList<Integer> days = new ArrayList<>();
        days.addAll(Arrays.asList(0, 1, 2, 3, 4));
        Habit habit = new Habit(uuid, "test", new Date(), days);
        Date date1 = new Date(1);
        Date date2 = new Date(2);
        Date date3 = new Date(3);
        Date date4 = new Date(4);
        Date date5 = new Date(5);
        habit.addCompletion(date1);
        habit.addCompletion(date2);
        habit.addCompletion(date3);
        habit.addCompletion(date4);
        habit.addCompletion(date5);
        ArrayList<Date> completions = habit.getCompletions();
        //check if the return competions is same
        assertEquals(completions.get(0), date1);
        assertEquals(completions.get(1), date2);
        assertEquals(completions.get(2), date3);
        assertEquals(completions.get(3), date4);
        assertEquals(completions.get(4), date5);
    }
    @Test
    public void testGetName() {
        UUID uuid = UUID.randomUUID();
        ArrayList<Integer> days = new ArrayList<>();
        days.addAll(Arrays.asList(0, 1, 2, 3, 4));
        Habit habit = new Habit(uuid, "test", new Date(), days);
        //check if the name of habit is same
        assertEquals(habit.getName(), "test");
    }

    @Test
    public void testGetDate() {
        UUID uuid = UUID.randomUUID();
        ArrayList<Integer> days = new ArrayList<>();
        Date date = new Date();
        days.addAll(Arrays.asList(0, 1, 2, 3 ,4));
        Habit habit = new Habit(uuid, "test", date, days);
        //check if dates are same in the habits
        assertEquals(habit.getDate(), date);
    }

    @Test
    public void testGetDays() {
        UUID uuid = UUID.randomUUID();
        ArrayList<Integer> days = new ArrayList<>();
        days.addAll(Arrays.asList(0, 1, 2, 3, 4));
        Habit habit = new Habit(uuid, "some name", new Date(), days);
        ArrayList<Integer> habitDays = habit.getDays();
        //check if size of number of days in week is same
        for (int i = 0; i < habitDays.size(); i++) {
            assertEquals(habitDays.get(i), days.get(i));
        }
    }

    @Test
    public void testAddCompletion() {
        UUID uuid = UUID.randomUUID();
        ArrayList<Integer> days = new ArrayList<>();
        days.addAll(Arrays.asList(0, 1, 2));
        Habit habit = new Habit(uuid, "some name", new Date(), days);
        Date date = new Date(1);
        habit.addCompletion(date);
        assertEquals(habit.getCompletions().get(0), date);
    }


}
