package com.udacity.aenima.bakingapp.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.aenima.bakingapp.BR;
import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Step;
import com.udacity.aenima.bakingapp.databinding.StepItemBinding;

import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {
    private final StepListAdapterInterface mStepListAdapterInterface;
    private List<Step> mStepList;

    public StepListAdapter(List<Step> stepList, StepListAdapterInterface stepListAdapterInterface){
        mStepList = stepList;
        mStepListAdapterInterface = stepListAdapterInterface;
    }
    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        StepItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.step_item, parent, false);
        return new StepViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step step = mStepList.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        private StepItemBinding stepItemBinding;
        private StepListAdapter callback;

        public StepViewHolder(View itemView) {
            super(itemView);
        }

        public StepViewHolder(StepItemBinding binding, StepListAdapter stepListAdapter) {

            super(binding.getRoot());
            stepItemBinding = binding;
            this.callback = stepListAdapter;
        }

        public void bind(Step step) {
            stepItemBinding.setVariable(BR.step, step);
            stepItemBinding.setVariable(BR.callback, this.callback);
            stepItemBinding.executePendingBindings();
        }
    }

    public void stepSelected(Step step){

    }

    public interface StepListAdapterInterface{
        void onStepSelected(Step recipe);
    }
}
