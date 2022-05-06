package com.example.mobile30_03.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mobile30_03.R;
import com.example.mobile30_03.databinding.FragmentPlaylistBinding;
import com.example.mobile30_03.utils.PlaylistsAdapter;

public class PlaylistFragment extends Fragment {
    private String TAG = "PlaylistFragment";
    private FragmentPlaylistBinding binding;
    public PlaylistFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        //Action bar
        if (activity != null) {
            activity.setSupportActionBar(binding.tbPlaylists);
        }
        binding.rvPlaylists.setAdapter(new PlaylistsAdapter());
        binding.rvPlaylists.setHasFixedSize(true);
        binding.rvPlaylists.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.playlist_fragment_toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAdd:
                PopupWindow popupWindow = new PopupWindow(getContext());
                View view3 = LayoutInflater.from(getContext()).inflate(R.layout.popup_new_playlist, null);
                popupWindow.setContentView(view3);
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}