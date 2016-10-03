package ape_with_a_keyboard.wankinvi_habittracker;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vinson on 2016-10-01.
 * This CompletionAdapter is for keep tracking the data
 * for completing a single habit, this adapter is called
 * when we make any changes to the completion data,
 * adding or deleting a habit's completion data.
 */

public class CompletionsAdapter extends RecyclerView.Adapter<ape_with_a_keyboard.wankinvi_habittracker.CompletionsAdapter.ViewHolder> {

    private Context context;
    private Habit habit;

    public CompletionsAdapter(Habit myHabit, Context context) {
        this.habit = myHabit;
        this.context = context;
        setHasStableIds(true);
    }
    //class for both delete and complete habit views
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView completionDateView;
        public Button deletecompletionView;

        public ViewHolder(final View view) {
            super(view);
            completionDateView = (TextView) view.findViewById(R.id.completion_date);
            deletecompletionView = (Button) view.findViewById(R.id.completion_delete);


            deletecompletionView.setOnClickListener(new View.OnClickListener() {
                @Override
                //clicklistener for removing completions
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //check if it's null
                    if (position != RecyclerView.NO_POSITION) {
                        MainActivity.saveHabit.removeCompletion(habit, position);
                        setHabit(habit);
                        TextView completionsText = (TextView) ((Activity) context).findViewById(R.id.details_completion_title);
                        completionsText.setText("Completions History                  " + String.valueOf(habit.getCompletions().size()) + "times");
                    }
                }
            });
        }
    }
    //counts the number of competions of a habit
    @Override
    public int getItemCount() {
        return habit.getCompletions().size();
    }
    //return id for completions
    @Override
    public long getItemId(int position) {
        return habit.getCompletions().get(position).hashCode();
    }

    @Override
    public ape_with_a_keyboard.wankinvi_habittracker.CompletionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View completionView = inflater.inflate(R.layout.activity_complete, parent, false);
        return new ape_with_a_keyboard.wankinvi_habittracker.CompletionsAdapter.ViewHolder(completionView);
    }

    @Override
    public void onBindViewHolder(ape_with_a_keyboard.wankinvi_habittracker.CompletionsAdapter.ViewHolder viewHolder, int position) {
        Date completion = habit.getCompletions().get(position);

        TextView completionDate = viewHolder.completionDateView;
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd '@' h:mm a");
        completionDate.setText(f.format(completion));
    }


    //refreash view and changes
    public void setHabit(Habit myHabit) {
        habit = myHabit;
        this.notifyDataSetChanged();
    }
}
