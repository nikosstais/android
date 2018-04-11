package com.atcom.nikosstais.warmup.devtest1.activity.articles;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;
import com.atcom.nikosstais.warmup.devtest1.remote.managers.ContentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Articles. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ArticleDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ArticleListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private static String CATEGORY_ID_SELECTED;
    private static String CATEGORY_NAME_SELECTED;
    private boolean mTwoPane;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppTheme); //this added navigation shortcut below appbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        //Replace background color
        ((ViewGroup) this.findViewById(android.R.id.content))
                .getChildAt(0)
                .setBackgroundColor(getResources().getColor(R.color.white));

        CATEGORY_ID_SELECTED = getString(R.string.categoryIDSelected);
        CATEGORY_NAME_SELECTED = getString(R.string.categoryNameSelected);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        if (findViewById(R.id.article_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.article_list);
        assert recyclerView != null;


        Integer categoryId = null;
        if (getIntent()!=null && getIntent().getExtras() != null){

            categoryId = (Integer) getIntent().getExtras().get(CATEGORY_ID_SELECTED);
        }

        setupRecyclerView(categoryId);

        setupDrawer();

    }

    private void setupDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar()==null){
            setSupportActionBar(toolbar);
        }

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PrepareNavigationMenu(navigationView);
    }

    private void PrepareNavigationMenu(NavigationView navigationView) {
        navigationView.getMenu().clear();
        List<Category> categories = new ArrayList<>();
        try {
            categories = new ContentManager.FetchCategoriesTask().execute(getApplicationContext()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Menu menu = navigationView.getMenu();
        for (Category cat : categories){
            MenuItem menuItem = menu.add(cat.getId()+"");

            Intent intent = new Intent(this.getApplicationContext(), ArticleListActivity.class);
            intent.putExtra(CATEGORY_ID_SELECTED, cat.getId());
            intent.putExtra(CATEGORY_NAME_SELECTED, cat.getName());

            menuItem.setTitle(cat.getName());
            menuItem.setIntent(intent);
        }
    }

    private void setupRecyclerView(Integer categoryId) {

        List<Article> newsArticles = null;
        try {
            newsArticles = new ContentManager.FetchNewsTask().execute(getApplicationContext()).get();
        }catch (Exception e) {
            e.printStackTrace();
        }

        if (categoryId!=null){
            newsArticles = ContentManager.getNewsArticlesByCategory(categoryId, newsArticles);
        }
        recyclerView.setAdapter(
                new ArticlesRecyclerViewAdapter(this,
                                                    newsArticles,
                                                    mTwoPane));
        recyclerView.refreshDrawableState();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        Integer categoryId = (Integer) item.getIntent().getExtras().get(CATEGORY_ID_SELECTED);
        if (getSupportActionBar() == null){
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }else{
            getSupportActionBar().setTitle(item.getIntent().getExtras().get(CATEGORY_NAME_SELECTED).toString());
        }
        setupRecyclerView(categoryId);

        return true;
    }


}
