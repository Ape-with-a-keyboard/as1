package ape_with_a_keyboard.wankinvi_habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;
import android.widget.AdapterView.OnItemLongClickListener;


//This contains the activity for changing the view for different buttons
//and also change view for today/anyday for habit list.
public class MainActivity extends AppCompatActivity {

    static SaveHabit saveHabit;
    //widget spinner is used to change view between today and all other days.
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private HabitsAdapter habitsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set up a container for habit
        saveHabit = new SaveHabit(this);
        //this is the floating button for adding more habits
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newHabit();
            }
        });

        RecyclerView habitsView = (RecyclerView) findViewById(R.id.habits);
        Integer dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        habitsAdapter = new HabitsAdapter(saveHabit.getHabits(dayOfWeek));
        habitsView.setAdapter(habitsAdapter);
        habitsView.setLayoutManager(new LinearLayoutManager(this));

        habitsAdapter.setClickListener(new HabitsAdapter.myclicklistener() {
            @Override
            public void onItemClick(View view, Habit habit) {

                viewHabitDetails(habit.getUUID());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //adds items to the action bar if present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //getting id for today or anyday
        MenuItem item = menu.findItem(R.id.spinner);
        //just setting up the button to drop down etc.
        spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, Arrays.asList("Today", "Any day"));
        spinnerAdapter.setDropDownViewResource(R.layout.item_dropdown);
        spinner.setAdapter(spinnerAdapter);
        //button listen to change from today and anyday
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Today
                if (position == 0) {
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    habitsAdapter.setHabits(saveHabit.getHabits(day));
                } else if (position == 1) {
                    // Any day
                    habitsAdapter.setHabits(saveHabit.getHabits());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                habitsAdapter.setHabits(saveHabit.getHabits(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)));
            }
        });
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (spinner != null) {
            int selected = spinner.getSelectedItemPosition();
            // Today
            if (selected == 0) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                habitsAdapter.setHabits(saveHabit.getHabits(day));
            } else if (selected == 1) {
                // Any day
                habitsAdapter.setHabits(saveHabit.getHabits());
            }
        }
    }
    //open the habit detail page
    public void viewHabitDetails(UUID uuid) {
        Intent intent = new Intent(this, HabitDetails.class);
        intent.putExtra("UUID", uuid);
        startActivity(intent);
    }
    //open the new habit page to add habit
    public void newHabit() {
        Intent intent = new Intent(this, NewHabit.class);
        startActivity(intent);
    }


}
