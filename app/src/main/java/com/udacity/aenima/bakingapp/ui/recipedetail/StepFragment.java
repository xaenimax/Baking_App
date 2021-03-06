package com.udacity.aenima.bakingapp.ui.recipedetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.adapter.StepListAdapter;
import com.udacity.aenima.bakingapp.data.Recipe;
import com.udacity.aenima.bakingapp.data.Step;
import com.udacity.aenima.bakingapp.ui.ingredient.IngredientActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepFragment.OnStepSelectedListener} interface
 * to handle interaction events.
 * Use the {@link StepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RECIPE_PARAM = "recipe";
    private static final String LAYOUT_MANAGER_STATE = "layout_manager_state";

    private Recipe mRecipe;

    @BindView(R.id.ingredient_card_view)
    CardView ingredientCardView;

    @BindView(R.id.step_list_rv)
    RecyclerView stepListRecyclerView;

    LinearLayoutManager layoutManager;

    private OnStepSelectedListener mListener;

    public StepFragment() {
        // Required empty public constructor
    }

    public void setmOnStepSelectedListener(OnStepSelectedListener mOnStepSelectedListener) {
        this.mOnStepSelectedListener = mOnStepSelectedListener;
    }

    private OnStepSelectedListener mOnStepSelectedListener;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipe Recipe
     * @return A new instance of fragment StepFragment.
     */
    public static StepFragment newInstance(Recipe recipe) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_PARAM, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(RECIPE_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        if(savedInstanceState != null && savedInstanceState.containsKey(LAYOUT_MANAGER_STATE)){
            layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE));
        }
        ingredientCardView.setVisibility(mRecipe.ingredients.size() > 0 ? View.VISIBLE : View.GONE);
        ingredientCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IngredientActivity.class);
                intent.putParcelableArrayListExtra(IngredientActivity.INGREDIENT_EXTRA, (ArrayList<? extends Parcelable>) mRecipe.ingredients);
                startActivity(intent);
            }
        });

        stepListRecyclerView.setLayoutManager(layoutManager);
        StepListAdapter stepListAdapter = new StepListAdapter(mRecipe.steps, new StepListAdapter.StepListAdapterInterface() {

            @Override
            public void onStepSelected(Step step) {
                mOnStepSelectedListener.onStepSelectedListener(step);
            }
        });
        stepListRecyclerView.setAdapter(stepListAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepSelectedListener) {
            mListener = (OnStepSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepSelectedListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStepSelectedListener {
        void onStepSelectedListener(Step selectedStep);
    }
}
