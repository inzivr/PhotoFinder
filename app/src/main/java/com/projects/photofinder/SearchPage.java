package com.projects.photofinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.projects.photofinder.datamodels.Result;
import com.projects.photofinder.pexelsapi.PexelsApiCall;
import com.projects.photofinder.pexelsapi.PexelsApiClient;
import com.projects.photofinder.ui.GridDisplayAdapter;

import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPage extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private GridDisplayAdapter gridDisplayAdapter;
    private GridLayoutManager gridLayoutManager;

    private EditText searchBar;
    private Button searchBtn;

    private PexelsApiClient client;

    private Result result;

    private boolean isScrolling = false;
    private int currentItems;
    private int totalItems;
    private int scrolledOutItems;

    private final int grid_rows = 2;

    private String search;
    private final int perPage = 30;
    private int current_page;

    Flowable<Result> flowable;

    boolean isComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        initialize();
        searchBtn.setOnClickListener(this::onClick);
    }

    protected void initialize(){
        recyclerView = (RecyclerView) findViewById(R.id.img_list);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),grid_rows);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setVisibility(View.GONE);

        searchBar = (EditText) findViewById(R.id.txt_search);
        searchBtn = (Button) findViewById(R.id.btn_search);

        client = PexelsApiCall.getApiCall().create(PexelsApiClient.class);
        result = new Result();
    }

    protected void fetchData(String search, int page) {
        flowable = client.getResults(search, perPage, page);
        flowable.toObservable().subscribeOn(Schedulers.io()).subscribe(new Observer<Result>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result data) {
                result = data;
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                isComplete = true;
            }
        });
    }

    protected void display(){
        gridDisplayAdapter = new GridDisplayAdapter(getApplicationContext(),result.getPhotos());
        recyclerView.setAdapter(gridDisplayAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = gridLayoutManager.getChildCount();
                totalItems = gridLayoutManager.getItemCount();
                scrolledOutItems = gridLayoutManager.findFirstCompletelyVisibleItemPosition();

                if (isScrolling && (currentItems + scrolledOutItems == totalItems))
                {
                    isScrolling = false;
                    fetchData(search, (current_page + 1));
                    gridDisplayAdapter.addImages(result.getPhotos());
                    gridDisplayAdapter.notifyDataSetChanged();
                    isComplete = false;
                }
            }
        });
        isComplete = false;
    }

    @Override
    public void onClick(View view) {
        String checkIfEmpty = searchBar.getText().toString();
        if (checkIfEmpty.matches("")) {
            // If user clicks on search button without any string for search
            Toast.makeText(getApplicationContext(), "Type something to Search", Toast.LENGTH_SHORT).show();
        } else {
            search = searchBar.getText().toString();
            current_page = 1;
            fetchData(search, current_page);
            while (!isComplete)
            {
                Toast.makeText(getApplicationContext(),"Loading ...",Toast.LENGTH_SHORT).show();
            }
            display();
        }
    }
}