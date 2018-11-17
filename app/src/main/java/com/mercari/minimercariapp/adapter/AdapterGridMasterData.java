package com.mercari.minimercariapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mercari.minimercariapp.R;
import com.mercari.minimercariapp.model.DetailMasterData;

import java.util.List;

public class AdapterGridMasterData extends BaseAdapter
{

    Context context;
    List<DetailMasterData> detailMasterDataList;

    LayoutInflater inflter;
    ProgressDialog progressDialog;

    private Typeface iconFont;
    int count = 0;

    public AdapterGridMasterData(Context context, List<DetailMasterData> detailMasterDataList, ProgressDialog progressDialog)
    {
        this.context = context;
        this.detailMasterDataList = detailMasterDataList;
        this.inflter = (LayoutInflater.from(context));
        this.progressDialog = progressDialog;
    }

    @Override
    public int getCount() {
        return detailMasterDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.grid_item_master_data, null); // inflate the layout
/*        ImageView icon = (ImageView) convertView.findViewById(R.id.icon); // get the reference of ImageView
        icon.setImageResource(logos[i]); // set logo images*/

        final DetailMasterData detailMasterData = (DetailMasterData) detailMasterDataList.get(position);



        final ImageView pro_image = (ImageView) convertView.findViewById(R.id.pro_image);
        try {
            Glide.with(context).load(detailMasterData.getPhoto()).into(pro_image);
        }catch(Exception fi)
        {
            //fi.printStackTrace();
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.preview_image)).into(pro_image);
        }

        final TextView txt_Name = (TextView) convertView.findViewById(R.id.txt_Name);
        txt_Name.setText(detailMasterData.getName());

        final TextView txt_like = (TextView) convertView.findViewById(R.id.txt_like);
        txt_like.setText(detailMasterData.getNum_likes());
        final TextView txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
        txt_comment.setText(detailMasterData.getNum_comments());

        final TextView txt_price = (TextView) convertView.findViewById(R.id.txt_price);
        txt_price.setText(detailMasterData.getPrice());


        return convertView;
    }


}
