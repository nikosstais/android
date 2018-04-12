package com.atcom.nikosstais.warmup.devtest1.activity.articles;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.atcom.nikosstais.warmup.devtest1.R;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;

/**
 * A fragment representing a single Article detail screen.
 * This fragment is either contained in a {@link ArticleListActivity}
 * in two-pane mode (on tablets) or a {@link ArticleDetailActivity}
 * on handsets.
 */
public class ArticleDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Article mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = (Article) getArguments().getSerializable(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            if (activity != null) {
                CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(mItem.getTitle());
                }
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.article_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            WebView viewById = rootView.findViewById(R.id.article_detail);
            viewById.loadData(mItem.getContent().replaceAll("user-scalable=no", "user-scalable=yes"),
                    "text/html",
                    null);
            viewById.getSettings().setJavaScriptEnabled(Boolean.TRUE);
//            viewById.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//            viewById.getSettings().setLoadWithOverviewMode(true);
//            viewById.getSettings().setUseWideViewPort(true);
        }

        return rootView;
    }
}
