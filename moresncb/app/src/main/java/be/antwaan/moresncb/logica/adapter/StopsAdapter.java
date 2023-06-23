package be.antwaan.moresncb.logica.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Map;

import be.antwaan.moresncb.R;

public class StopsAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupItems;
    private Map<String, List<String>> childItems;

    public StopsAdapter(Context context, List<String> groupItems, Map<String, List<String>> childItems) {
        this.context = context;
        this.groupItems = groupItems;
        this.childItems = childItems;
    }

    @Override
    public int getGroupCount() {
        return groupItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupName = groupItems.get(groupPosition);
        return childItems.get(groupName).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String groupName = groupItems.get(groupPosition);
        return childItems.get(groupName).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.drop_down_item, parent, false);
        }

        ImageView iconImageView = convertView.findViewById(R.id.arrow_icon);

        int colorRes = R.color.lightblue;
        int color = ContextCompat.getColor(context, colorRes);
        iconImageView.setColorFilter(color);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.stop_item, parent, false);
        }

        TextView stopName = convertView.findViewById(R.id.stop_name);

        String childText = (String) getChild(groupPosition, childPosition);
        stopName.setText(childText);

        return convertView;    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}