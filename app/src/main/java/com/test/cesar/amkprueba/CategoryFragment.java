package com.test.cesar.amkprueba;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.cesar.amkprueba.adapters.CategoryAdapter;
import com.test.cesar.amkprueba.model.Category;
import com.test.cesar.amkprueba.utils.ICommunication;

import java.util.ArrayList;

/**
 * Created by Cesar on 26/08/2017
 */

public class CategoryFragment extends Fragment {

    private RecyclerView rvCategories;
    private ArrayList<Category> categories;

    ICommunication mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (ICommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ICommunication");
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.category_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).updateToolbarTitle("Categorias");
        rvCategories=view.findViewById(R.id.rv_categories);

        categories=new ArrayList<>();
        categories.add(new Category("Rock", ContextCompat.getDrawable(getContext(), R.drawable.rock)));
        categories.add(new Category("Pop", ContextCompat.getDrawable(getContext(), R.drawable.pop)));
        categories.add(new Category("Country", ContextCompat.getDrawable(getContext(), R.drawable.country)));
        categories.add(new Category("Folk", ContextCompat.getDrawable(getContext(), R.drawable.folk)));
        CategoryAdapter categoryAdapter=new CategoryAdapter(categories,mCallback);
        rvCategories.setHasFixedSize(true);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategories.setAdapter(categoryAdapter);

    }
}
