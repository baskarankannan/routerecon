package com.techacsent.route_recon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.PackageResponse;

public class PackageAdapter extends BaseRecyclerAdapter<PackageResponse.AllPackageBean,
        OnRecyclerClickListener<PackageResponse.AllPackageBean>, PackageAdapter.PackageVH> {

    public PackageAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public PackageVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PackageVH(inflate(R.layout.item_package, viewGroup));
    }

    class PackageVH extends BaseViewHolder<PackageResponse.AllPackageBean, OnRecyclerClickListener<PackageResponse.AllPackageBean>> {

        private TextView tvPackageName;
        private TextView tvPackagePrice;
        private FrameLayout frameContent;

        PackageVH(@NonNull View itemView) {
            super(itemView);
            tvPackageName = itemView.findViewById(R.id.tv_package_name);
            tvPackagePrice = itemView.findViewById(R.id.tv_price);
            frameContent = itemView.findViewById(R.id.content);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBind(PackageResponse.AllPackageBean item, @Nullable OnRecyclerClickListener<PackageResponse.AllPackageBean> listener) {
            //tvPackageName.setText(item.getPackageName() + " (" + item.getPackageType() + ")");
            tvPackageName.setText(item.getPackageUpgradeInfo());
            tvPackagePrice.setText(item.getPrice() + " " + item.getCurrency());
            frameContent.setOnClickListener(v -> {
                if(listener!=null){
                    listener.onItemClicked(item);
                }
            });

        }
    }
}
