package pl.rzagorski.networkstatsmanager.rank;

import android.Manifest;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.rzagorski.networkstatsmanager.R;
import pl.rzagorski.networkstatsmanager.utils.NetworkStatsHelper;
import pl.rzagorski.networkstatsmanager.utils.PackageManagerHelper;

public class RankListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar mToolbar;
    RankAdapter mAdapter;
    List<RankItemInfo> mRankItemInfoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);
        mToolbar = (Toolbar) findViewById(R.id.rank_toolbar);
        setSupportActionBar(mToolbar);
        recyclerView = (RecyclerView) findViewById(R.id.rank_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RankAdapter(mRankItemInfoList);
        recyclerView.setAdapter(mAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 这里应该搞一个正在加载中
                getRankItemInfos();
            }
        }).start();
    }

    private interface CompleteCallback {
        void onSuccess(List<RankItemInfo> rankItemInfos);
    }

    private CompleteCallback mCompleteCallback = new CompleteCallback() {
        @Override
        public void onSuccess(List<RankItemInfo> rankItemInfos) {
            mRankItemInfoList = rankItemInfos;
            mAdapter.setRankAdapterData(mRankItemInfoList);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    };


    private List<RankItemInfo> getRankItemInfos() {
        List<RankItemInfo> rankItemInfoList = new ArrayList<>();

        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);
        for (PackageInfo packageInfo : packageInfoList) {
            if (packageManager.checkPermission(Manifest.permission.INTERNET,
                    packageInfo.packageName) == PackageManager.PERMISSION_DENIED) {
                continue;
            }
            RankItemInfo rankItemInfo = new RankItemInfo();

            rankItemInfo.setPackageName(packageInfo.packageName);

            ApplicationInfo ai = null;
            try {
                ai = packageManager.getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (ai == null) {
                continue;
            }
            CharSequence appName = packageManager.getApplicationLabel(ai);
            if (appName != null) {
                rankItemInfo.setName(appName.toString());
            }

            int uid = PackageManagerHelper.getPackageUid(this, packageInfo.packageName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkStatsManager networkStatsManager = (NetworkStatsManager) getApplicationContext().getSystemService(Context.NETWORK_STATS_SERVICE);
                NetworkStatsHelper networkStatsHelper = new NetworkStatsHelper(networkStatsManager, uid);

                rankItemInfo.setAllMobileRx(networkStatsHelper.getAllRxBytesMobile(this));
                rankItemInfo.setAllWifiRx(networkStatsHelper.getAllRxBytesWifi());
                rankItemInfo.setAllMobileTx(networkStatsHelper.getAllTxBytesMobile(this));
                rankItemInfo.setAllWifiTx(networkStatsHelper.getAllTxBytesWifi());

                rankItemInfo.setPackageMobileRx(networkStatsHelper.getPackageRxBytesMobile(this));
                rankItemInfo.setPackageWifiRx(networkStatsHelper.getPackageRxBytesWifi());
                rankItemInfo.setPackageMobileTx(networkStatsHelper.getPackageTxBytesMobile(this));
                rankItemInfo.setPackageWifiTx(networkStatsHelper.getPackageTxBytesWifi());
                rankItemInfo.setPackageMobileTotal(rankItemInfo.getPackageMobileRx() + rankItemInfo.getPackageMobileTx());
                rankItemInfo.setPackageWifiTotal(rankItemInfo.getPackageWifiRx() + rankItemInfo.getPackageWifiTx());
            }
            rankItemInfoList.add(rankItemInfo);
        }
        // todo 这个排列有问题
        Collections.sort(rankItemInfoList, new Comparator<RankItemInfo>() {
            @Override
            public int compare(RankItemInfo rankItemInfo, RankItemInfo t1) {
                return (int) ((rankItemInfo.getPackageMobileTotal() + rankItemInfo.getPackageWifiTotal())
                        - (t1.getPackageMobileTotal() + t1.getPackageWifiTotal()));
            }
        });

        mCompleteCallback.onSuccess(rankItemInfoList);
        return rankItemInfoList;
    }

}
