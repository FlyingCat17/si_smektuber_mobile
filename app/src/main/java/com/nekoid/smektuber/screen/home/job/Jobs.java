package com.nekoid.smektuber.screen.home.job;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterData;
import com.nekoid.smektuber.adapter.AdapterDataArticleViewAll;
import com.nekoid.smektuber.adapter.AdapterDataJobs;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.app.BaseFragment;
import com.nekoid.smektuber.helpers.listener.ScrollListener;
import com.nekoid.smektuber.helpers.thread.Threads;
import com.nekoid.smektuber.helpers.utils.Network;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.models.JobsModel;
import com.nekoid.smektuber.models.Pagination;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Jobs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Jobs extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private boolean withAnimation = true;
    private RecyclerView recyclerView;
    private List<JobsModel> jobsModels = new ArrayList<>();
    private AdapterDataJobs adapterDataJobs;
    private ShimmerFrameLayout shimmerFrameLayout;
    private Pagination pagination;
    private boolean isScroll = false, isFromState = false, isLoading = false;
    public Jobs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Jobs.
     */
    // TODO: Rename and change types and number of parameters
    public static Jobs newInstance(String param1, String param2) {
        Jobs fragment = new Jobs();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);
        init(view);
        new Network(getActivity(), new Network.Listener() {
            @Override
            public void onNetworkAvailable() {
                openRequest();
            }

            @Override
            public void onNetworkUnavailable() {

            }
        });
        return view;
    }
    private void startShimmer() {

    }

    private void stopShimmer() {
        if (withAnimation) recyclerView.setAnimation(Utils.animation());
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void openRequest() {
        if (!State.jobsModels.isEmpty()) {
            withAnimation = false;
            jobsModels = State.jobsModels;
            updateAdapter();
//            getRequest();
        } else {
            getRequest();
        }
    }

    private void init(View view) {
        startShimmer();
        setVar(view);
        setAdapterDataJob();
        onLoad();
        openRequest();
        setupScrollListener();
    }

    private void setVar(View view) {
        adapterDataJobs = new AdapterDataJobs(getActivity(), jobsModels);
        shimmerFrameLayout = view.findViewById(R.id.rvDataShimmerJobs);
        recyclerView = view.findViewById(R.id.DataLowongan);
    }

    private void onLoad() {
        Threads.execute((executor, handler) -> executor.execute(() -> {
            while (!adapterDataJobs.isLoad()) {
                // don't delete this line;
            }
            handler.post(this::stopShimmer);
        }));
    }



    private void getRequest() {
        Http.get(Endpoint.LIST_JOBS.getUrl(), PublicApi.getHeaders(), this::listJobs);
    }

    protected void listJobs(Response response) {
        if (response.statusCode != 200) {
            return;
        }

        try {
            JSONObject responses = new JSONObject(response.body.toString());
            pagination = Pagination.fromJson(responses.getJSONObject("pagination"));
            JSONArray arrays = responses.getJSONArray("data");
            for (int i = 0; i < arrays.length(); i++) {
                jobsModels.add(JobsModel.fromJson(new JSONObject(arrays.getString(i))));
            }
            updateAdapter();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setAdapterDataJob() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterDataJobs);
        recyclerView.addItemDecoration(Utils.setRecyclerPaddingRight(20));
        recyclerView.addOnScrollListener(new ScrollListener() {
            @Override
            public void onScroll(@NonNull RecyclerView recyclerView, int horizontal, int vertical, int newState) {
                if (!recyclerView.canScrollVertically(2) && isScroll) {
                    onLastScroll();
                }
            }
        });

    }
    protected void onLastScroll() {
        loadMoreData();
    }
    private void loadMoreData() {
        if (pagination != null && pagination.nextPageUrl != null && !pagination.nextPageUrl.isEmpty() && !pagination.nextPageUrl.equals("null") && !isLoading) {
            isLoading = true;
            Http.get(pagination.nextPageUrl, PublicApi.getHeaders(), response -> {
                listJobs(response);
                isLoading = false;
            });
        }
    }
    private void setupScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == jobsModels.size() - 1) {
                    loadMoreData();
                }
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    protected void updateAdapter() {
        adapterDataJobs.notifyDataSetChanged();
        stopShimmer();
        isScroll = true;
    }


}