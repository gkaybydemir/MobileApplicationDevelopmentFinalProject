// CampingPlaceAdapter.java
package com.example.finalproject;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import androidx.fragment.app.FragmentManager;
import com.squareup.picasso.Picasso;

public class CampingPlaceAdapter extends RecyclerView.Adapter<CampingPlaceAdapter.ViewHolder> {

    private List<CampingPlace> campingPlaces;
    private FragmentManager fragmentManager; // Reference to FragmentManager

    public CampingPlaceAdapter(List<CampingPlace> campingPlaces, FragmentManager fragmentManager) {
        this.campingPlaces = campingPlaces;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_camping_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CampingPlace campingPlace = campingPlaces.get(position);
        holder.bind(campingPlace);
    }

    @Override
    public int getItemCount() {
        return campingPlaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView campingPlaceImageView;
        private TextView campingPlaceNameTextView;
        private TextView campingPlacePriceTextView;
        private TextView campingPlaceLocationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            campingPlaceImageView = itemView.findViewById(R.id.campingPlaceImageView);
            campingPlaceNameTextView = itemView.findViewById(R.id.campingPlaceNameTextView);
            campingPlacePriceTextView = itemView.findViewById(R.id.campingPlacePriceTextView);
            campingPlaceLocationTextView = itemView.findViewById(R.id.campingPlaceLocationTextView);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    CampingPlace clickedPlace = campingPlaces.get(position);
                    openCampingDetailsFragment(clickedPlace);
                }
            });
        }

        public void bind(CampingPlace campingPlace) {
            Picasso.with(campingPlaceImageView.getContext()).load(campingPlace.getPhotoLink()).into(campingPlaceImageView);
            campingPlaceNameTextView.setText(campingPlace.getName());
            campingPlacePriceTextView.setText("Price: " + campingPlace.getPrice() + "₺");
            campingPlaceLocationTextView.setText("Location: " + campingPlace.getLocation());
        }
    }

    private void openCampingDetailsFragment(CampingPlace campingPlace) {
        CampingDetailsFragment detailsFragment = new CampingDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("campingPlace", campingPlace);
        detailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}