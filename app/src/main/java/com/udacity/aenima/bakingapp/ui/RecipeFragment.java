package com.udacity.aenima.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.adapter.RecipeListAdapter;
import com.udacity.aenima.bakingapp.data.BakingAppAPI;
import com.udacity.aenima.bakingapp.data.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRecipeSelectedListener} interface
 * to handle interaction events.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {
    private List<Recipe> mRecipeList;
    private OnRecipeSelectedListener mListener;

    @BindView(R.id.recipe_list_rv)
    public RecyclerView recipeRecyclerView;

    public RecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeFragment newInstance(String param1, String param2) {
        RecipeFragment fragment = new RecipeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);
        if(savedInstanceState == null){
            int columns = (int) getResources().getDimension(R.dimen.grid_layout_column_count);
            
            GridLayoutManager layoutManager =  new GridLayoutManager(this.getContext(),  columns);
            recipeRecyclerView.setLayoutManager(layoutManager);

        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mRecipeList == null){
            Call<List<Recipe>> recipeCallback = BakingAppAPI.getRecipes();
            recipeCallback.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, final Response<List<Recipe>> response) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecipeListAdapter listAdapter = new RecipeListAdapter(response.body());
                            recipeRecyclerView.setAdapter(listAdapter);
                        }
                    });

                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    showErrorMessage();
                }
            });
        }
    }

    void showErrorMessage(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), getString(R.string.no_connection_error_message), Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeSelectedListener) {
            mListener = (OnRecipeSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeSelectedListener");
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
    public interface OnRecipeSelectedListener {
        void onRecipeSelected(int position);
    }
}
