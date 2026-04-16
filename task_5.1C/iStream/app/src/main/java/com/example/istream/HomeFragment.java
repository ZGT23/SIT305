package com.example.istream;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.istream.database.AppDatabase;
import com.example.istream.database.PlaylistVideo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {

    private TextView tvWelcome;
    private EditText etYoutubeUrl;
    private Button btnPlay, btnAddToPlaylist, btnMyPlaylist, btnLogout;
    private WebView webViewPlayer;
    private SessionManager sessionManager;
    private AppDatabase db;

    private static final String ARG_VIDEO_URL = "video_url";

    public static HomeFragment newInstance(String videoUrl) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO_URL, videoUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvWelcome = view.findViewById(R.id.tv_welcome);
        etYoutubeUrl = view.findViewById(R.id.et_youtube_url);
        btnPlay = view.findViewById(R.id.btn_play);
        btnAddToPlaylist = view.findViewById(R.id.btn_add_to_playlist);
        btnMyPlaylist = view.findViewById(R.id.btn_my_playlist);
        btnLogout = view.findViewById(R.id.btn_logout);
        webViewPlayer = view.findViewById(R.id.webview_player);

        sessionManager = new SessionManager(requireContext());
        db = AppDatabase.getInstance(requireContext());

        tvWelcome.setText("Welcome, " + sessionManager.getUsername() + "!");

        setupWebView();

        btnPlay.setOnClickListener(v -> {
            String url = etYoutubeUrl.getText().toString().trim();
            loadVideo(url);
        });

        btnAddToPlaylist.setOnClickListener(v -> {
            String url = etYoutubeUrl.getText().toString().trim();
            if (isValidYoutubeUrl(url)) {
                PlaylistVideo video = new PlaylistVideo(sessionManager.getUserId(), url);
                db.playlistDao().insertVideo(video);
                Toast.makeText(requireContext(), "Added to playlist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            }
        });

        btnMyPlaylist.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).replaceFragment(new PlaylistFragment());
        });

        btnLogout.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).logout();
        });

        if (getArguments() != null) {
            String initialUrl = getArguments().getString(ARG_VIDEO_URL);
            if (initialUrl != null) {
                etYoutubeUrl.setText(initialUrl);
                loadVideo(initialUrl);
            }
        }

        return view;
    }

    private void setupWebView() {
        // Force Hardware Acceleration at view level
        webViewPlayer.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        WebSettings webSettings = webViewPlayer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // Custom User Agent to ensure YouTube doesn't restrict mobile webview
        String userAgent = "Mozilla/5.0 (Linux; Android 14; Pixel 7 Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.6261.119 Mobile Safari/537.36";
        webSettings.setUserAgentString(userAgent);

        CookieManager.getInstance().setAcceptThirdPartyCookies(webViewPlayer, true);
        
        webViewPlayer.setWebViewClient(new WebViewClient());
        webViewPlayer.setWebChromeClient(new WebChromeClient());
    }

    private void loadVideo(String url) {
        String videoId = extractVideoId(url);
        if (videoId != null) {
            // Use HTML content with IFrame Player API approach for maximum compatibility
            String html = "<!DOCTYPE html><html><head>" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\">" +
                    "<style>body,html {margin:0; padding:0; height:100%; width:100%; background:black;} " +
                    ".video-container {position:relative; padding-bottom:56.25%; height:0; overflow:hidden;} " +
                    ".video-container iframe {position:absolute; top:0; left:0; width:100%; height:100%;}</style>" +
                    "</head><body>" +
                    "<div class=\"video-container\">" +
                    "<iframe id=\"player\" src=\"https://www.youtube.com/embed/" + videoId + "?enablejsapi=1&origin=https://www.youtube.com&rel=0&playsinline=1&modestbranding=1\" " +
                    "frameborder=\"0\" allowfullscreen allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\"></iframe>" +
                    "</div>" +
                    "</body></html>";

            webViewPlayer.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "UTF-8", null);
        } else {
            Toast.makeText(requireContext(), "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidYoutubeUrl(String url) {
        return extractVideoId(url) != null;
    }

    private String extractVideoId(String url) {
        // Robust regex to extract YouTube video ID from various formats
        String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|%2Fv%2F|youtube.com/watch\\?v=)[^#&?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
