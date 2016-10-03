package ape_with_a_keyboard.wankinvi_habittracker;

import android.content.DialogInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.UUID;

//This class is for a habit's detail,where
//use get all details from a certain habit.
//This class uses the completionsadapter for
//getting all the neccessary details for completions.
public class HabitDetails extends AppCompatActivity {

    private CompletionsAdapter completionsAdapter;
    private Habit habit;
    private HashMap<Integer, String> day_of_week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_detail);
        habit = MainActivity.saveHabit.gethabitbyUUID((UUID) this.getIntent().getSerializableExtra("UUID"));
        day_of_week = new HashMap<Integer, String>() {{
            put(Calendar.SUNDAY, "Sun");
            put(Calendar.MONDAY, "Mon");
            put(Calendar.TUESDAY, "Tue");
            put(Calendar.WEDNESDAY, "Wed");
            put(Calendar.THURSDAY, "Thu");
            put(Calendar.FRIDAY, "Fri");
            put(Calendar.SATURDAY, "Sat");
        }};
        //gets all the info for a habit then print it on screen.
        TextView habitTitle = (TextView) findViewById(R.id.details_habit_title);
        TextView habitDate = (TextView) findViewById(R.id.details_date);
        habitTitle.setText(habit.getName());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        habitDate.setText(format.format(habit.getDate()));
        TextView daysView = (TextView) findViewById(R.id.details_days);
        //this is to make sure app won't crash if a user input no week of days for a habit
        if (habit.getDays().size() > 0) {
            String daysText = day_of_week.get(habit.getDays().get(0));
            for (Integer day : habit.getDays().subList(1, habit.getDays().size())) {
                daysText += ", " + day_of_week.get(day);
            }
            daysView.setText(daysText);
        }
        //this is to show the number of completions of a habit
        TextView completionsText = (TextView) findViewById(R.id.details_completion_title);
        completionsText.setText("Completions History                  " + String.valueOf(habit.getCompletions().size()) + "times");
        completionsAdapter = new CompletionsAdapter(habit, this);
        RecyclerView completionsView = (RecyclerView) findViewById(R.id.completions);
        completionsView.setAdapter(completionsAdapter);
        completionsView.setLayoutManager(new LinearLayoutManager(this));

    }
}
