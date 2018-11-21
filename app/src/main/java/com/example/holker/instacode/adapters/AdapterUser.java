package com.example.holker.instacode.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.holker.instacode.R;

import java.util.List;


public class AdapterUser extends ArrayAdapter<ItemCard> {

    Context mContext;
    int mResource;
    List<ItemCard> mData;

    public AdapterUser(Context context, int resource, List<ItemCard> data) {
        super(context, R.layout.card, data);
        this.mContext = context;
        this.mResource = resource;
        this.mData = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.card, null);

        ItemCard item = mData.get(position);

        Button buttonFollow = (Button) view.findViewById(R.id.btn_card_follow);

        buttonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Follow", Toast.LENGTH_LONG).show();
            }
        });

        return super.getView(position, convertView, parent);


    }
}
