package com.atcom.nikosstais.warmup.devtest1.fragments.articles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.atcom.nikosstais.warmup.devtest1.R;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Article detail screen.
 * This fragment is either contained in a {@link com.atcom.nikosstais.warmup.devtest1.activity.articles.impl.ArticleListActivity}
 * in two-pane mode (on tablets) or a {@link com.atcom.nikosstais.warmup.devtest1.activity.articles.impl.ArticleDetailActivity}
 * on handsets.
 */
public class ArticleDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Article mItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = (Article) getArguments().getSerializable(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.article_detail, container, false);

        if (mItem != null) {
            try {
                Picasso.with(rootView.getContext())
                        .load(mItem.getPhotoUrl())
                        .into((ImageView) rootView.findViewById(R.id.article_detail_image));
            } catch (Exception e) {
                e.printStackTrace();
            }

            WebView webView = rootView.findViewById(R.id.article_webview);
            webView.getSettings().setDefaultTextEncodingName(getString(R.string.characterEncoding));

            webView.loadData(mItem.getContent(),
                    getString(R.string.htmlMimeType),
                    getString(R.string.characterEncoding));
            webView.getSettings().setJavaScriptEnabled(Boolean.TRUE);
//            viewById.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//            viewById.getSettings().setLoadWithOverviewMode(true);
//            viewById.getSettings().setUseWideViewPort(true);
        }

        return rootView;
    }
}
