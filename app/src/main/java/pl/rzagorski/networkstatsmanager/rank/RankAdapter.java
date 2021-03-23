package pl.rzagorski.networkstatsmanager.rank;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.rzagorski.networkstatsmanager.R;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    List<RankItemInfo> mRankItemInfos;

    public RankAdapter(List<RankItemInfo> rankItemInfos) {
        mRankItemInfos = rankItemInfos;
    }

    public void setRankAdapterData(List<RankItemInfo> rankItemInfos){
        this.mRankItemInfos = rankItemInfos;
    }


    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_card, parent, false);
        return new RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankViewHolder holder, int position) {
        RankItemInfo rankItemInfo = mRankItemInfos.get(position);
        holder.name.setText(rankItemInfo.getName());
        holder.wifi.setText("WIFI: " + rankItemInfo.getPackageWifiTotal() + " B");
        holder.mobile.setText("Mobile: " + rankItemInfo.getPackageMobileTotal() + " B");
        try {
            holder.icon.setImageDrawable(holder.context.getPackageManager().getApplicationIcon(rankItemInfo.getPackageName()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mRankItemInfos.size();
    }

    public class RankViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView wifi;
        TextView mobile;
        TextView name;
        AppCompatImageView icon;

        public RankViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = (TextView) itemView.findViewById(R.id.rank_name);
            wifi = (TextView) itemView.findViewById(R.id.package_wifi);
            mobile = (TextView) itemView.findViewById(R.id.package_mobile);
            icon = (AppCompatImageView) itemView.findViewById(R.id.rank_icon);
        }
    }

}
