package com.udacity.aenima.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by marina on 25/03/2018.
 */

public class Ingredient implements Parcelable{
    private final String QUANTITY_JSON_HEADER      = "quantity";
    private final String MEASURE_JSON_HEADER       = "measure";
    private final String INGREDIENT_JSON_HEADER    = "ingredient";


    @SerializedName(QUANTITY_JSON_HEADER)
    public float quantity;
    @SerializedName(MEASURE_JSON_HEADER)
    public String measure;
    @SerializedName(INGREDIENT_JSON_HEADER)
    public String ingredient;

    public static Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            float quantity = parcel.readFloat();
            String measure = parcel.readString();
            String ingredient = parcel.readString();
            return new Ingredient(quantity, measure, ingredient);
        }

        @Override
        public Ingredient[] newArray(int i) {
            return new Ingredient[0];
        }
    };

    public Ingredient(float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.quantity);
        parcel.writeString(this.measure);
        parcel.writeString(this.ingredient);
    }
}
