package com.ruyue.todolist.view;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ruyue.todolist.R;
import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;
import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private List<Task> data;
    private LayoutInflater layoutInflater;
    LocalDataSource localDataSource;
    Context context;

    public TaskAdapter(Context context, List<Task> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        localDataSource = LocalDataSource.getInstance(context);
    }

    class ViewHolder {
        CheckBox isFinished;
        TextView title;
        TextView date;
    }

    @Override
    public int getCount() {
        if(data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_task, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.isFinished = convertView.findViewById(R.id.is_finished);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.date = convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(data.get(position).getTitle());

        String[] dateStrList = data.get(position).getDate().split("-");
        String displayDate = dateStrList[1] + "ÔÂ" + dateStrList[2] + "ÈÕ";
        viewHolder.date.setText(displayDate);

        viewHolder.isFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Task task = data.get(position);
                if (isChecked) {
                    viewHolder.title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.title.setTextColor(context.getResources().getColor(R.color.delete_text));
                    task.setFinished(true);

                } else {
                    viewHolder.title.getPaint().setFlags(0);
                    viewHolder.title.setTextColor(context.getResources().getColor(R.color.btn_text_color));
                    task.setFinished(false);
                }
                notifyDataSetChanged();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        localDataSource.taskDao().updateTask(task);
                    }
                }).start();
            }
        });
        viewHolder.isFinished.setChecked(data.get(position).getFinished());
        return convertView;
    }
}
