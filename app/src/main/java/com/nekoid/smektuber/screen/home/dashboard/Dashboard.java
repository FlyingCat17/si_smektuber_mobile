package com.nekoid.smektuber.screen.home.dashboard;

import static com.nekoid.smektuber.helpers.utils.Utils.isNetworkAvailable;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterData;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.app.BaseFragment;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.models.AboutModel;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;
import com.nekoid.smektuber.helpers.thread.Threads;
import com.nekoid.smektuber.screen.home.about.AboutSchool;
import com.nekoid.smektuber.screen.home.article.ArticleViewAll;
import com.nekoid.smektuber.screen.home.ekstarkurikuler.Extrakurikuler;
import com.nekoid.smektuber.screen.home.jurusan.Jurusan;
import com.nekoid.smektuber.screen.home.maps.MapsActivity;
import com.nekoid.smektuber.screen.home.visiMisi.VisiAndMisi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends BaseFragment {

    private RecyclerView recyclerView;

    List<ArticleModel> listArticle = new ArrayList<>();

    AdapterData adapterData;

    TextView fullName;

    ShimmerFrameLayout shimmerFrameLayout;

    private boolean withAnimation = true;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init(view);
        // Inflate the layout for this fragment
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
        if (!State.articleModels.isEmpty()) {
            withAnimation = false;
            listArticle = State.articleModels;
            updateAdapter();
            getRequest();
        } else {
            getRequest();
        }
    }

    private void init(View view) {
        setVar(view);
        onLoad();
        setAdapterData();
        startShimmer();
        listener(view);
        openRequest();
    }

    private void setVar(View view) {
        fullName = view.findViewById(R.id.Fullname);
        adapterData = new AdapterData(this.getActivity(), listArticle);
        shimmerFrameLayout = view.findViewById(R.id.rvDataShimmer);
        recyclerView = view.findViewById(R.id.rvData);
        if (State.userModel != null) {
            fullName.setText(State.userModel.name);
        } else {
            fullName.setText("");
        }
    }

    private void listener(View view) {
        ConstraintLayout btn = view.findViewById(R.id.Visi_Misi);
        TextView ntm = view.findViewById(R.id.Titlelihat_semua);
        TextView jk = view.findViewById(R.id.ButtonSelengkapnya);
        ConstraintLayout n = view.findViewById(R.id.Map_Lokasi);
        ConstraintLayout extra = view.findViewById(R.id.Extrakurikuler);
        ConstraintLayout jurusan = view.findViewById(R.id.Jurusan);
        btnMessage(view);

        jurusan.setOnClickListener(v -> {
            Navigator.of(getActivity()).push(Jurusan.class);
        });
        extra.setOnClickListener(v -> {
            Navigator.of(getActivity()).push(Extrakurikuler.class);
        });
        n.setOnClickListener(v -> {
            Navigator.of(getActivity()).push(MapsActivity.class);
        });
        ntm.setOnClickListener(v -> {
            Navigator.of(getActivity()).push(ArticleViewAll.class);
        });
        btn.setOnClickListener(v -> {
            Navigator.of(getActivity()).push(VisiAndMisi.class);
        });
        jk.setOnClickListener(v -> {
            Navigator.of(getActivity()).push(AboutSchool.class);
        });
    }

    private void onClickMessage(View view) {
        String phoneNumber = State.aboutModel.schoolWhatsapp;
        if (phoneNumber.startsWith("0")) phoneNumber = phoneNumber.substring(1);
        String url = String.format("https://api.whatsapp.com/send/phone=%s", "62" + phoneNumber);
        Navigator.openApp(getActivity(), Utils.toUri(url));
    }

    private void btnMessage(View view) {
        ImageView buttonMessage = view.findViewById(R.id.IconPesan);
        AtomicBoolean isClickable = new AtomicBoolean(false);
        Threads.execute((executor, handler) -> {
            executor.execute(() -> {
                while (State.aboutModel == null) {
                    isClickable.set(false);
                    if (State.aboutModel != null) {
                        isClickable.set(true);
                        break;
                    }
                }
                handler.post(() -> buttonMessage.setOnClickListener(this::onClickMessage));
            });
        });
    }

    private void getRequest() {
        Http.get(Endpoint.LIST_ARTICLE.getUrl(), PublicApi.getHeaders(), this::getListArticles);
        Http.get(Endpoint.ABOUT.getUrl(), PublicApi.getHeaders(), this::responseAbout);
    }

    protected void getListArticles(Response response) {
        try {
            if (response.statusCode != 200) {
                return;
            }

            JSONObject responses = new JSONObject(response.body.toString());
            JSONArray arrays = responses.getJSONArray("data");

            for (int i = 0; i < arrays.length(); i++) {
                JSONObject json = arrays.getJSONObject(i);
                listArticle.add(ArticleModel.fromJson(json));
                if (i >= 5) break;
            }
            updateAdapter();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    protected void responseAbout(Response response) {
        if (response.statusCode != 200) {
            return;
        }

        try {
            JSONObject body = new JSONObject(response.body.toString());
            State.aboutModel = AboutModel.fromJson(body.getJSONObject("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setAdapterData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterData);
        recyclerView.addItemDecoration(Utils.setRecyclerPaddingRight(20));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateAdapter() {
        State.articleModels = listArticle;
        adapterData.notifyDataSetChanged();
    }

    private void onLoad() {
        Threads.execute((executor, handler) -> executor.execute(() -> {
            while (!adapterData.isLoad()) {
                // don't delete this line;
            }
            handler.post(this::stopShimmer);
        }));
    }
}