package com.atcom.nikosstais.warmup.devtest1;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atcom.nikosstais.warmup.devtest1.remote.data.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by nikos on 09/04/18.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private List<Article> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView newsEntryTitle;
        public TextView newsEntrySummary;
        public ImageView newsEntryImage;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            newsEntryTitle = v.findViewById(R.id.firstLine);
            newsEntrySummary = v.findViewById(R.id.secondLine);
            newsEntryImage = v.findViewById(R.id.newsEntryImage);
        }
    }

    public NewsListAdapter(List<Article> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.news_entry_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Article article = values.get(position);


        if (position%2==0){
            holder.layout.setBackgroundColor(Color.parseColor("cyan"));
        }else{
            holder.layout.setBackgroundColor(Color.parseColor("white"));
        }

        holder.newsEntryTitle.setText(article.getTitle());
        holder.newsEntrySummary.setText(article.getSummary());

        Glide.with(holder.layout.getContext())
                .load(article.getPhotoUrl())
                .dontAnimate()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESULT )
                .into(holder.newsEntryImage);

        holder.newsEntryTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO call detail
            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}

