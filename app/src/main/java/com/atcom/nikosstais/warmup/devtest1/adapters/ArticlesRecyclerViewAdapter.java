package com.atcom.nikosstais.warmup.devtest1.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atcom.nikosstais.warmup.devtest1.R;
import com.atcom.nikosstais.warmup.devtest1.activity.articles.ArticleDetailActivity;
import com.atcom.nikosstais.warmup.devtest1.activity.articles.ArticleDetailFragment;
import com.atcom.nikosstais.warmup.devtest1.activity.articles.ArticleListActivity;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ArticlesRecyclerViewAdapter
        extends RecyclerView.Adapter<ArticlesRecyclerViewAdapter.ViewHolder> {

    private final ArticleListActivity mParentActivity;
    private final List<Article> mValues;
    private final boolean mTwoPane;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Article item = (Article) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putSerializable(ArticleDetailFragment.ARG_ITEM_ID, item);
                ArticleDetailFragment fragment = new ArticleDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.article_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra(ArticleDetailFragment.ARG_ITEM_ID, item);

                context.startActivity(intent);
            }
        }
    };

    public ArticlesRecyclerViewAdapter(ArticleListActivity parent,
                                List<Article> items,
                                boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Article article = mValues.get(position);
        holder.mIdView.setText(article.getId().toString());
        holder.newsEntryTitle.setText(article.getTitle());
        holder.newsEntrySummary.setText(article.getSummary());
        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);

        if (position%2==0){
            holder.itemView.setBackgroundColor(Color.parseColor("cyan"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("white"));
        }

        Glide.with(holder.itemView.getContext())
                .load(article.getPhotoUrl())
                .dontAnimate()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESULT )
                .into(holder.newsEntryImage);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView newsEntryTitle;
        final TextView newsEntrySummary;
        final ImageView newsEntryImage;

        ViewHolder(View view) {
            super(view);
            mIdView = view.findViewById(R.id.id_text);
            newsEntryTitle = view.findViewById(R.id.firstLine);
            newsEntrySummary = view.findViewById(R.id.secondLine);
            newsEntryImage = view.findViewById(R.id.newsEntryImage);
        }
    }
}