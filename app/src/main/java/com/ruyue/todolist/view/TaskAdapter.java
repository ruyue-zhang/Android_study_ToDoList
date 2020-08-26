package com.ruyue.todolist.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ruyue.todolist.R;
import com.ruyue.todolist.models.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private List<Task> data;
    private LayoutInflater layoutInflater;

    public TaskAdapter(Context context, List<Task> data) {
        this.data = data;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        viewHolder.isFinished.setChecked(data.get(position).getFinished());
        viewHolder.title.setText(data.get(position).getTitle());
        String[] dateStrList = data.get(position).getDate().split("-");
        String displayDate = dateStrList[1] + "ÔÂ" + dateStrList[2] + "ÈÕ";
        viewHolder.date.setText(displayDate);
        return convertView;
    }
}
