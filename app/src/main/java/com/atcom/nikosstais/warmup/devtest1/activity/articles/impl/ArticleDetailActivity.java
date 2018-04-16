package com.atcom.nikosstais.warmup.devtest1.activity.articles.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.atcom.nikosstais.warmup.devtest1.R;
import com.atcom.nikosstais.warmup.devtest1.activity.articles.ArticleDetailActivityView;
import com.atcom.nikosstais.warmup.devtest1.activity.articles.ArticleDetailFragment;
import com.atcom.nikosstais.warmup.devtest1.presenters.ArticleDetailPresenter;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;

/**
 * An activity representing a single Article detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ArticleListActivity}.
 */
public class ArticleDetailActivity extends AppCompatActivity implements ArticleDetailActivityView {

    ArticleDetailPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        Article item = (Article) getIntent().getSerializableExtra(ArticleDetailFragment.ARG_ITEM_ID);
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(item.getTitle());
        }
        //over-acting with mvp?
        presenter = new ArticleDetailPresenter(this);
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            presenter.showDetailedArticle(item);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent intent = getParentActivityIntent();
            if (getIntent().getExtras() != null && getIntent().getExtras().get(getString(R.string.categoryIDSelected)) != null) {
                Category category = (Category) getIntent().getExtras().get(getString(R.string.categoryIDSelected));
                if (intent!=null){
                    intent.putExtra(getString(R.string.categoryIDSelected), category);
                }
            }

            navigateUpTo(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayArticle(Article article) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ArticleDetailFragment.ARG_ITEM_ID, article);

        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.article_detail_container, fragment)
                .commit();
    }
}
