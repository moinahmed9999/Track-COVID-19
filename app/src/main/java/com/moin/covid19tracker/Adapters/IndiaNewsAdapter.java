package com.moin.covid19tracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moin.covid19tracker.Models.NewsResult;
import com.moin.covid19tracker.R;

import java.util.List;

public class IndiaNewsAdapter extends RecyclerView.Adapter<IndiaNewsAdapter.IndiaNewsViewHoler>{

    private List<NewsResult> results;
    private Context context;
    private OnNewsClickListener mOnNewsClickListener;

    public interface OnNewsClickListener {
        void onNewsClick(int position);
    }

    public void setOnNewsClickListener (OnNewsClickListener newsClickListener) {
        mOnNewsClickListener = newsClickListener;
    }

    public IndiaNewsAdapter(List<NewsResult> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public IndiaNewsViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        return  new IndiaNewsViewHoler(view, mOnNewsClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IndiaNewsViewHoler holder, int position) {
        NewsResult result = results.get(position);

        Glide.with(context)
                .load(result.getImage())
                .fitCenter()
                .into(holder.news_image);

        holder.title.setText(result.getTitle());
        holder.snippet.setText(result.getSnippet());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class IndiaNewsViewHoler extends RecyclerView.ViewHolder {

        public ImageView news_image;
        public TextView title;
        public TextView snippet;

        public IndiaNewsViewHoler(@NonNull View itemView, final OnNewsClickListener newsClickListener) {
            super(itemView);

            news_image = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.title_tv);
            snippet = itemView.findViewById(R.id.snippet_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (newsClickListener!=null) {
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {
                            newsClickListener.onNewsClick(position);
                        }
                    }
                }
            });
        }
    }
}
