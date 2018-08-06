package com.jica.butterbookdata.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.hanks.library.AnimateCheckBox;
import com.jica.butterbookdata.R;
import com.jica.butterbookdata.WordClickViewActivity;
import com.jica.butterbookdata.database.AppDB;
import com.jica.butterbookdata.database.dao.AdjektivDAO;
import com.jica.butterbookdata.database.dao.NomenDAO;
import com.jica.butterbookdata.database.dao.VerbenDAO;
import com.jica.butterbookdata.database.dao.WordDAO;
import com.jica.butterbookdata.database.entity.Adjektiv;
import com.jica.butterbookdata.database.entity.Nomen;
import com.jica.butterbookdata.database.entity.Verben;
import com.jica.butterbookdata.database.entity.Word;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodayWordAdapter extends RecyclerView.Adapter<TodayWordAdapter.RecyclerViewHolder> {
    private NomenDAO nomenDAO;
    private VerbenDAO verbenDAO;
    private AdjektivDAO adjektivDAO;
    private WordDAO wordDAO;
    private List<Word> items;
    private Context mContext;

    public TodayWordAdapter(Context context, List<Word> itemList) {
        this.mContext = context;
        items = itemList;
        wordDAO = AppDB.getInstance(mContext).wordDAO();
    }

    //뷰생성, 뷰 홀더 호출
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_today_row,viewGroup,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }
    // 필수 오버라이드 : 재활용되는 View 가 호출, Adapter 가 해당 position 에 해당하는 데이터를 결합
    @Override
    public void onBindViewHolder(final RecyclerViewHolder recyclerViewHolder, int i) {

        //단어
        recyclerViewHolder.tvTodayWord.setText(items.get(i).getWord());
        recyclerViewHolder.checkBox.setOnCheckedChangeListener(null);
        if(items.get(i).getStudy()==0){
            recyclerViewHolder.checkBox.setChecked(true);
        }
        if(items.get(i).getStudy()==1){
            recyclerViewHolder.checkBox.setChecked(false);
        }
        recyclerViewHolder.checkBox.setOnCheckedChangeListener(new AnimateCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {
                int cheked=1;
                if(isChecked){cheked=0;}
                recyclerViewHolder.setStudy(cheked);
            }
        });
    }

    // 필수 오버라이드 : 데이터 갯수 반환
    @Override
    public int getItemCount() {
        return (items==null)?0:items.size();
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvTodayWord)
        TextView tvTodayWord;
        @BindView(R.id.checkbox)
        AnimateCheckBox checkBox;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.setIsRecyclable(false);
        }
        @OnClick(R.id.tvTodayWord)
        public void onWordDetailView(View view){
            int itemPosition = getAdapterPosition();
            Word item = items.get(itemPosition);

            int category = item.getCategory();
            int categoryId = item.getCategory_id();
            int clickcnt = item.getClickcnt()+1;
            item.setClickcnt(clickcnt);
            wordDAO.update(item);
            notifyItemChanged(itemPosition);

            Intent intent = new Intent(mContext, WordClickViewActivity.class);
            switch (category){
                case 1:{
                    nomenDAO = AppDB.getInstance(mContext).nomenDAO();
                    Nomen detailItem = nomenDAO.get(categoryId);
                    String[] itemValue = new String[14];
                    itemValue[0] = item.getWid()+"";
                    itemValue[1] = detailItem.getArtikel();
                    itemValue[2] = detailItem.getNomen();
                    itemValue[3] = detailItem.getPlural();
                    itemValue[4] = detailItem.getMean_ko();
                    itemValue[5] = detailItem.getMean_en();
                    itemValue[6] = detailItem.getExample();
                    itemValue[7] = detailItem.getExample_mean();
                    itemValue[8] = item.getBookmark()+"";
                    itemValue[9] = item.getStudy()+"";
                    itemValue[10] = item.getDate();
                    itemValue[11] = clickcnt+"";
                    itemValue[12] = item.getQuizfinish()+"";
                    itemValue[13] = "1";
                    intent.putExtra("ItemValue",itemValue);
                    break;}
                case 2:{
                    verbenDAO = AppDB.getInstance(mContext).verbenDAO();
                    Verben detailItem = verbenDAO.get(categoryId);
                    String[] itemValue = new String[20];
                    itemValue[0] = item.getWid()+"";
                    itemValue[1] = detailItem.getVerb_wir();
                    itemValue[2] = detailItem.getVerb_ich();
                    itemValue[3] = detailItem.getVerb_du();
                    itemValue[4] = detailItem.getVerb_er_sie_es();
                    itemValue[5] = detailItem.getVerb_ihr();
                    itemValue[6] = detailItem.getObjectform();
                    itemValue[7] = detailItem.getPrateritum_ich();
                    itemValue[8] = detailItem.getPartizip2_hilfsverb();
                    itemValue[9] = detailItem.getPartizip2();
                    itemValue[10] = detailItem.getMean_ko();
                    itemValue[11] = detailItem.getMean_en();
                    itemValue[12] = detailItem.getExample();
                    itemValue[13] = detailItem.getExample_mean();
                    itemValue[14] = item.getBookmark()+"";
                    itemValue[15] = item.getStudy()+"";
                    itemValue[16] = item.getDate();
                    itemValue[17] = clickcnt+"";
                    itemValue[18] = item.getQuizfinish()+"";
                    itemValue[19] = "2";
                    intent.putExtra("ItemValue",itemValue);
                    break;}
                case 3:{
                    adjektivDAO = AppDB.getInstance(mContext).adjektivDAO();
                    Adjektiv detailItem = adjektivDAO.get(categoryId);
                    String[] itemValue = new String[12];
                    itemValue[0] = item.getWid()+"";
                    itemValue[1] = detailItem.getWord_adjektiv();
                    itemValue[2] = detailItem.getMean_ko();
                    itemValue[3] = detailItem.getMean_en();
                    itemValue[4] = detailItem.getExample();
                    itemValue[5] = detailItem.getExample_mean();
                    itemValue[6] = item.getBookmark()+"";
                    itemValue[7] = item.getStudy()+"";
                    itemValue[8] = item.getDate();
                    itemValue[9] = clickcnt+"";
                    itemValue[10] = item.getQuizfinish()+"";
                    itemValue[11] = "3";
                    intent.putExtra("ItemValue",itemValue);
                    break;}
            }
            mContext.startActivity(intent);
            Activity mActivity = (Activity)mContext;
            mActivity.overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_not_move_activity);
        }

        public void setStudy(int cheked) {
            int itemPosition = getAdapterPosition();
            if(cheked==0){
                items.get(itemPosition).setStudy(0);
            } else{
                items.get(itemPosition).setStudy(1);
            }
            wordDAO.update(items.get(itemPosition));
        }
    }
    public int remove(int position) {
        int item_id = items.get(position).getWid();
        items.remove(position);
        return item_id;
    }
}

