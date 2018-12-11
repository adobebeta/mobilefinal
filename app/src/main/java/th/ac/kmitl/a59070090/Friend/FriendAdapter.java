package th.ac.kmitl.a59070090.Friend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import th.ac.kmitl.a59070090.R;

public class FriendAdapter extends ArrayAdapter {
    ArrayList<JSONObject> friendList;
    Context context;

    public FriendAdapter(Context context, int resource, ArrayList<JSONObject> objects)
    {
        super(context, resource, objects);
        this.friendList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View friendItem = LayoutInflater.from(context).inflate(R.layout.fragment_friend_list_item, parent, false);
        JSONObject postObj = friendList.get(position);
        TextView postHeader = friendItem.findViewById(R.id.post_list_item_header);
        TextView postBody  = friendItem.findViewById(R.id.post_list_item_body);
        try
        {
            postHeader.setText(postObj.getString("id") + " : " + postObj.getString("title"));
            postBody.setText(postObj.getString("body"));
        }
        catch (JSONException e)
        {
            Log.d("test", "catch JSONException : " + e.getMessage());
        }
        return friendItem;
    }
}

