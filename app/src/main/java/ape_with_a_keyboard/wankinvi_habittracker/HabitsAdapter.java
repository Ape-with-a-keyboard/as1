package ape_with_a_keyboard.wankinvi_habittracker;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;




/**
 * Created by Vinson on 2016-9-28.
 * This Habitsadapter is to help showing the correct info on the main screen
 * such as if a completion was done today, or user want to delete
 * habit, it will show feedback on screen. The class uses a list for the list of
 * habit, and hashmap for the day of week storage.
 */
public class HabitsAdapter extends RecyclerView.Adapter<ape_with_a_keyboard.wankinvi_habittracker.HabitsAdapter.ViewHolder> {

    private List<Habit> habitList;
    private HashMap<Integer, String> day_of_week;

    // Set up listen for clicks in VH for a activity
    public interface myclicklistener {
        void onItemClick(View view, Habit habit);
    }
    private myclicklistener listener;

    //setting up habits and day_of_week
    public HabitsAdapter(List<Habit> habits) {
        habitList = habits;
        day_of_week = new HashMap<Integer, String>() {{
            put(Calendar.SUNDAY, "Sun");
            put(Calendar.MONDAY, "Mon");
            put(Calendar.TUESDAY, "Tue");
            put(Calendar.WEDNESDAY, "Wed");
            put(Calendar.THURSDAY, "Thu");
            put(Calendar.FRIDAY, "Fri");
            put(Calendar.SATURDAY, "Sat");
        }};
    }
    //This class holds all the functions in the habit list
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView habitText;
        public TextView habitDays;
        public Button completeButton;
        public Button deletehabitButton;
        public ImageView habitCompleted;

        public ViewHolder(final View view) {
            super(view);
            final ViewHolder viewHolder = this;


            completeButton = (Button) view.findViewById(R.id.habit_complete);
            deletehabitButton = (Button) view.findViewById(R.id.habit_delete);
            habitCompleted = (ImageView) view.findViewById(R.id.habit_completed);
            habitText = (TextView) view.findViewById(R.id.habit_name);
            habitDays = (TextView) view.findViewById(R.id.habit_days);

            //the click to list item
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(view, habitList.get(position));
                    }
                }
            });
            //the click to complete a habit
            completeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        MainActivity.saveHabit.addCompletion(habitList.get(position));
                        setcompletestatus(habitList.get(position), viewHolder);
                    }
                }
            });
            //the click to delete a habit
            deletehabitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Habit habit = habitList.get(position);
                    if (position != RecyclerView.NO_POSITION) {
                        MainActivity.saveHabit.removeHabit(habit.getUUID());
                        habitText.setText("Removed");
                    }

                }
            });
        }
    }

    //make it possible @ mainacitivity to call actions from there
    public void setClickListener(myclicklistener listener) {
        this.listener = listener;
    }



    @Override
    public HabitsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View habitView = inflater.inflate(R.layout.activity_habits, parent, false);
        return new ViewHolder(habitView);
    }

    @Override
    public void onBindViewHolder(HabitsAdapter.ViewHolder viewHolder, int position) {
        Habit habit = habitList.get(position);

        TextView habitTextView = viewHolder.habitText;
        habitTextView.setText(habit.getName());
        TextView habitDaysView = viewHolder.habitDays;
        //making sure the app won't crash if user didn't input any dayofweek
        if (habit.getDays().size() > 0) {
            String daysText = day_of_week.get(habit.getDays().get(0));
            //print out in normal text
            for (Integer day : habit.getDays().subList(1, habit.getDays().size())) {
                daysText += ", " + day_of_week.get(day);
            }
            habitDaysView.setText(daysText);
        } else {
            //shows that user didn't provide day of week
            habitDaysView.setText("Did not provide day of week");
        }
        //If the habit is completed today, the icon will turn green to tell user
        setcompletestatus(habit, viewHolder);



    }
    //function to show user that the habits are done TODAY by changing icon to green.
    public void setcompletestatus(Habit habit, ViewHolder viewHolder) {
        ImageView completedbutton = viewHolder.habitCompleted;
        ArrayList<Date> completions = habit.getCompletions();
        if (!completions.isEmpty() && DateUtils.isToday(completions.get(completions.size() - 1).getTime())) {
            completedbutton.setColorFilter(0xFF00FF00);
        } else {
            completedbutton.setColorFilter(0xFFCCCCCC);
        }
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    //refreash the habtlist
    public void setHabits(List<Habit> habits) {
        habitList = habits;
        this.notifyDataSetChanged();
    }





}


