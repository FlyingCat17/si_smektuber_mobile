package com.nekoid.smektuber.screen.home.dashboard;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterData;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.LocalStorage;
import com.nekoid.smektuber.models.ArticleModel;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {

    private RecyclerView recyclerView;
    AdapterData adapterData;

    List<ArticleModel> listArticle = new ArrayList<ArticleModel>();

    TextView fullName;

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
        recyclerView = view.findViewById(R.id.rvData);
        ConstraintLayout btn = view.findViewById(R.id.Visi_Misi);
        TextView ntm = view.findViewById(R.id.Titlelihat_semua);
        TextView jk = view.findViewById(R.id.ButtonSelengkapnya);
        ConstraintLayout n = view.findViewById(R.id.Map_Lokasi);
        ConstraintLayout extra = view.findViewById(R.id.Extrakurikuler);
        ConstraintLayout jurusan = view.findViewById(R.id.Jurusan);
        fullName = view.findViewById(R.id.Fullname);

        if (LocalStorage.userModel != null) {
            fullName.setText(LocalStorage.userModel.name);
        } else {
            fullName.setText("");
        }

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
        request();

        // Inflate the layout for this fragment
        return view;
    }

    protected void request() {
        StringRequest getArticle = PublicApi.get(Endpoint.LIST_ARTICLE.getUrl(), listArticles());
        PublicApi.addParams(null);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getArticle);
    }

    protected Response.Listener<String> listArticles() {
        return response -> {
            try {
                JSONObject responses = new JSONObject(response);
                if (responses.getInt("status") != 200) {
                    return;
                }
                JSONArray arrays = responses.getJSONArray("data");
                for (int i = 0; i < arrays.length(); i++) {
                    listArticle.add(ArticleModel.fromJson(new JSONObject(arrays.getString(i))));
                    if (i == 5) break;
                }
                setAdapterData();
            } catch (JSONException e) {
            }
        };
    }

    protected void setAdapterData() {
        adapterData = new AdapterData(getActivity(), listArticle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterData);
    }
}