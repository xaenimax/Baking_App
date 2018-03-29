package com.udacity.aenima.bakingapp.data;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseLongArray;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;
import com.udacity.aenima.bakingapp.R;

import java.util.List;

/**
 * Created by marina on 25/03/2018.
 */

public class Recipe implements Parcelable{
    private final String ID_JSON_HEADER             = "id";
    private final String NAME_JSON_HEADER           = "name";
    private final String INGREDIENTS_JSON_HEADER    = "ingredients";
    private final String STEPS_JSON_HEADER          = "steps";
    private final String SERVINGS_JSON_HEADER       = "servings";
    private final String IMAGE_JSON_HEADER          = "image";

    @SerializedName(ID_JSON_HEADER)
    public long id;
    @SerializedName(NAME_JSON_HEADER)
    public String name;
    @SerializedName(INGREDIENTS_JSON_HEADER)
    public List<Ingredient> ingredients;
    @SerializedName(STEPS_JSON_HEADER)
    public List<Step> steps;
    @SerializedName(SERVINGS_JSON_HEADER)
    public int servings;
    @SerializedName(IMAGE_JSON_HEADER)
    public String image;


    @BindingAdapter("app:imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl){
        if(imageUrl.isEmpty()){
            imageView.setImageResource(R.drawable.cupcake_placeholder);
        }else {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.cupcake_placeholder)
                    .into(imageView);
        }
    }

    public static Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }
}
