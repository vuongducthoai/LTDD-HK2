package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fragment.databinding.FragementNeworderBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NewOrderFragment extends Fragment {
    FragementNeworderBinding binding;
    public NewOrderFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NotNull LayoutInflater inflater, @NotNull ViewGroup container, @Nullable Bundle saveInstanceState){
        //Inflate the layout for this fragment
        binding = FragementNeworderBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
