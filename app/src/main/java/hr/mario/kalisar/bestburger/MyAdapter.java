package hr.mario.kalisar.bestburger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private OnItemClickListener myListener;
    private LayoutInflater inflater;

    ArrayList<Model> models;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        myListener = listener;
    }

    public MyAdapter(Context con, ArrayList<Model> models) {
        inflater = LayoutInflater.from(con);
        this.models = models;
    }


    @Override
    public MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false); //Inflate row

        MyHolder holder = new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyHolder holder, int position) {
        Picasso.get().load(models.get(position).getImg()).into(holder.image);
        holder.title.setText(models.get(position).getTitle());
        holder.decription.setText(models.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {


        TextView title, decription;
        ImageView image;

        public MyHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTv);
            decription = itemView.findViewById(R.id.descriptionTv);
            image = itemView.findViewById(R.id.imageIv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            myListener.onItemClick(position);
                        }
                    }
                }
            });


        }


    }


}
