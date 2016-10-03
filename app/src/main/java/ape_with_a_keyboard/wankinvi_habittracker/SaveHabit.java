package ape_with_a_keyboard.wankinvi_habittracker;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Created by Vinson on 2016-10-01.
 * This class uses Hashmap to store habits with UUID
 * it outputs to habits.sav. Alot of helper functions are created
 * for modifying the data.
 */
public class SaveHabit {
    private HashMap<UUID, Habit> habits;
    private static final String FILENAME = "habits.sav";
    private Context actContext;

    public SaveHabit(Context context) {
        actContext = context;
        loadHabits();
    }

    public void addHabit(String name, Date date, ArrayList<Integer> days) {
        UUID uuid = UUID.randomUUID();
        habits.put(uuid, new Habit(uuid, name, date, days));
        saveHabits();
    }

    public void removeHabit(UUID uuid) {
        habits.remove(uuid);

        saveHabits();

    }

    public void addCompletion(Habit habit) {
        habit.addCompletion(new Date());
        saveHabits();
    }

    public void removeCompletion(Habit habit, int position) {
        habit.removeCompletion(position);
        saveHabits();
    }

    public ArrayList<Habit> getHabits() {
        return new ArrayList<>(habits.values());
    }

    public ArrayList<Habit> getHabits(Integer day) {
        ArrayList<Habit> todayHabits = new ArrayList<>();
        for (Habit habit: habits.values()) {
            if (habit.getDays().contains(day)) {
                todayHabits.add(habit);
            }
        }
        return todayHabits;
    }

    public Habit gethabitbyUUID(UUID uuid) {
        return habits.get(uuid);
    }

    private void loadHabits() {
        try {
            FileInputStream fis = actContext.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<LinkedHashMap<UUID, Habit>>(){}.getType();
            habits = gson.fromJson(in, listType);
        } catch (FileNotFoundException f) {
            // no file yet, we chillin'
            habits = new LinkedHashMap<>();
        }
    }

    private void saveHabits() {
        try {
            FileOutputStream fos = actContext.openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(habits, writer);
            writer.flush();
        } catch (IOException e) {
            // 's fine
        }
    }
}
