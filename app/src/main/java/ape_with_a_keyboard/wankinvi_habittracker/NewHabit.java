package ape_with_a_keyboard.wankinvi_habittracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

//This NewHabit public class contains backbones for picking the date
//and creating the habit, the class extends AppCompatactivity so It can
// Perform initialization of all fragments and loaders for android etc.
public class NewHabit extends AppCompatActivity {

    private ArrayList<Integer> day_of_week;
    private Date date;

    //The instancestate is protected so it will not be
    //tampered.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting up the new habit page view
        setContentView(R.layout.activity_new_habit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //make a new array for days of the week
        day_of_week = new ArrayList<>();
        day_of_week.addAll(Arrays.asList(
                Calendar.SUNDAY,
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY,
                Calendar.FRIDAY,
                Calendar.SATURDAY
        ));
        date = new Date();
        TextView currentDateView = (TextView) findViewById(R.id.new_habit_date_current);
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        currentDateView.setText(f.format(date));
    }
    //this functions is called when the floating add habit button was pressed
    //it sends all the input info back to SaveHabit
    public void createHabit(View view) {
        EditText editText = (EditText) findViewById(R.id.new_habit_name_message);
        String name = editText.getText().toString();
        ArrayList<Integer> days = new ArrayList<>();

        for (Integer day : day_of_week) {
            int id = getResources().getIdentifier("toggleButton" + String.valueOf(day), "id", getPackageName());
            ToggleButton dayButton = (ToggleButton) findViewById(id);
            if (dayButton.isChecked()) {
                days.add(Integer.valueOf(day));
            }
        }
        MainActivity.saveHabit.addHabit(name, date, days);
        this.finish();
    }
    //This defaults pick the current date for user, user can change if wanted
    //Referenced :http://www.tutorialspoint.com/android/android_datepicker_control.htm
    public void pickDate(View view) {
        final Calendar c = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);
                date = c.getTime();
                TextView currentDateView = (TextView) findViewById(R.id.new_habit_date_current);
                DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                currentDateView.setText(f.format(date));
            }
        };

        new DatePickerDialog(NewHabit.this, listener, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
    }
}
