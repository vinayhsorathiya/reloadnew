package com.example.android.firstproject;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administer on 01-06-2017.
 */

public class ListviewAdapter extends BaseAdapter {
    private final Activity activity;
    private ListItemClickListener mOnClickListener;
    private List<Recorddata> recorddata = Collections.emptyList();
    private TextView mtextView1;
    private TextView mtextView2;
    private TextView mtextView3;
    private ImageView imageView;
    private View itemView;

    public ListviewAdapter(List<Recorddata> recorddata, Activity activity, ListItemClickListener listener) {
        this.recorddata = recorddata;
        this.activity = activity;
        this.mOnClickListener = listener;
    }

    @Override
    public int getCount() {
        return recorddata.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;
        if (convertView == null) {
            itemView = inflater.inflate(R.layout.row_layout, null);
        } else {
            itemView = convertView;
        }

        mtextView1 = (TextView) itemView.findViewById(R.id.textView1);
        mtextView2 = (TextView) itemView.findViewById(R.id.textView2);
        mtextView3 = (TextView) itemView.findViewById(R.id.textView3);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);

        final Recorddata Temp = recorddata.get(position);
//        final Recorddata Temp1=Temp.getPost_imagesList().get(0);

        String[] post_images = Arrays.copyOf(Temp.getPost_images(), Temp.getPost_images().length);
        Log.d("vinay",Temp.getPost_imagesSize()+" AND "+Temp.getPost_images().length);

//        ArrayList<String> post_imagesList = new ArrayList<String>(Temp.getPost_imagesList());
        List<String> post_imagesList = new ArrayList<String>(Temp.getPost_imagesList());

//       Collections.copy(post_imagesList,Temp.getPost_imagesList());


        Log.d("vinay","AND "+post_imagesList.size());
//        Log.d("vinay","AND "+post_imagesList.size()+" AND "+post_imagesList.get(0));

        mtextView1.setText(Temp.getPost_title());
        mtextView2.setText(Temp.getPost_id());
        mtextView3.setText(Temp.getFull_name());

        Glide.with(activity).load(Uri.parse("http://192.168.0.16/mp28/" + post_images[0]))
                .thumbnail(0.5f)
                .crossFade()
                .centerCrop()
                .into(imageView);
//        imageView.setImageURI(Uri.parse("http://192.168.0.16/mp28/"+Temp.getPost_images()));

//        itemView.setOnClickListener(this);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(activity, Temp.getPost_no()+"", Toast.LENGTH_SHORT).show();
                mOnClickListener.onListItemClick(Integer.valueOf(Temp.getPost_id()), Temp);

            }
        });
        return itemView;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex, Recorddata recorddata);
    }

}
