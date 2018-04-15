package com.atcom.nikosstais.warmup.devtest1.activity.articles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.atcom.nikosstais.warmup.devtest1.R;
import com.atcom.nikosstais.warmup.devtest1.adapters.ArticlesRecyclerViewAdapter;
import com.atcom.nikosstais.warmup.devtest1.presenters.ArticleListPresenter;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * An activity representing a list of Articles. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ArticleDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ArticleListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    ArticleListActivityView {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private ArticleListPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppTheme); //this added navigation shortcut below appbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        //Replace background color
        ((ViewGroup) this.findViewById(android.R.id.content))
                .getChildAt(0)
                .setBackgroundColor(getResources().getColor(R.color.white));

        if (getSupportActionBar() == null) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        if (findViewById(R.id.article_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        presenter = new ArticleListPresenter(this, AndroidSchedulers.mainThread());

        setupDrawer();
        setupRecyclerView(null, Collections.<Article>emptyList());

    }

    @Override
    public void onResume(){
        super.onResume();

        presenter.loadNews();
        presenter.loadCategories();

    }

    private void setupDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void prepareNavigationMenu(List<Category> categories) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().clear();

        Menu menu = navigationView.getMenu();
        for (Category cat : categories) {
            MenuItem menuItem = menu.add(cat.getId() + "");
            menuItem.setTitle(cat.getName());

            Intent intent = new Intent(this.getApplicationContext(), ArticleListActivity.class);
            intent.putExtra(getString(R.string.categoryIDSelected), cat);

            menuItem.setIntent(intent);
        }
    }

    private void setupRecyclerView(Category category, List<Article> newsArticles) {

        recyclerView = findViewById(R.id.article_list);
        assert recyclerView != null;

        if (category != null) {
            getSupportActionBar().setTitle(category.getName());
        }
//        if (recyclerView.getAdapter()==null){
            recyclerView.setAdapter(
                    new ArticlesRecyclerViewAdapter(this,
                            newsArticles,
                            mTwoPane));
//        }
//        else{
//            recyclerView.getAdapter().
//            recyclerView.getAdapter().notifyDataSetChanged();
//            recyclerView.refreshDrawableState();
//
//        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        Category categoryId = (Category) item.getIntent().getExtras().get(getString(R.string.categoryIDSelected));

        presenter.loadCategoryNews(categoryId);

        return true;
    }

    @Override
    public void displayNews(List<Article> articles) {
        Category category = null;
        if (getIntent() != null && getIntent().getExtras() != null) {
            category = (Category) getIntent().getExtras().get(getString(R.string.categoryIDSelected));
        }
        setupRecyclerView(category, articles);

    }

    @Override
    public void displayNoNews() {
        //TODO show dialog and exit
    }

}
