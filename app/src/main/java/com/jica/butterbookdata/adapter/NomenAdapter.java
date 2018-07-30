package com.jica.butterbookdata.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jica.butterbookdata.R;
import com.jica.butterbookdata.WordClickViewActivity;
import com.jica.butterbookdata.database.entity.Nomen;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NomenAdapter extends RecyclerView.Adapter<NomenAdapter.RecyclerViewHolder> {
    List<Nomen> items;
    Context mContext;
    int showhideWord = 0; //0:show 1:hide
    int showhideMean = 0; //0:show 1:hide

    public NomenAdapter(Context context, List<Nomen> itemList) {
        this.mContext = context;
        items = itemList;
    }

    //뷰생성, 뷰 홀더 호출
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_word_row,viewGroup,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }
    // 필수 오버라이드 : 재활용되는 View 가 호출, Adapter 가 해당 position 에 해당하는 데이터를 결합
    @Override
    public void onBindViewHolder(final RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.tvGrammerForm.setText("N");
        recyclerViewHolder.tvWord.setText(items.get(i).getArtikel()+" "+items.get(i).getNomen());
        recyclerViewHolder.tvMean.setText(items.get(i).getMean_ko());
        //북마크 변화
        if(items.get(i).getBookmark()==0){
            recyclerViewHolder.ibBookmark.setImageResource(R.drawable.ic_bookmark_white_24dp);
        } else {
            recyclerViewHolder.ibBookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }
        //단어 보이기 또는 숨기기
        if(showhideWord==0){
            recyclerViewHolder.tvWord.setVisibility(View.VISIBLE);
        }else {
            recyclerViewHolder.tvWord.setVisibility(View.INVISIBLE);
        }
        //뜻 보이기 또는 숨기기
        if(showhideMean==0){
            recyclerViewHolder.tvMean.setVisibility(View.VISIBLE);
        }else {
            recyclerViewHolder.tvMean.setVisibility(View.INVISIBLE);
        }
    }

    // 필수 오버라이드 : 데이터 갯수 반환
    @Override
    public int getItemCount() {
        return (items==null)?0:items.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tableLayout)
        TableLayout tableLayout;
        @BindView(R.id.tvGrammerForm)
        TextView tvGrammerForm;
        @BindView(R.id.tvWord)
        TextView tvWord;
        @BindView(R.id.tvMean)
        TextView tvMean;
        @BindView(R.id.ibBookmark)
        ImageButton ibBookmark;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.setIsRecyclable(false);
        }
        @OnClick(R.id.ibBookmark)
        public void onBookmarkClick(View view){
            int itemPosition = getAdapterPosition();
            int isBookmarked = items.get(itemPosition).getBookmark();
            switch (isBookmarked){
                case 0: //yes
                    items.get(itemPosition).setBookmark(1);
                    notifyDataSetChanged();
                    break;
                case 1: //no
                    items.get(itemPosition).setBookmark(0);
                    notifyDataSetChanged();
                    break;
            }
        }
        @OnClick(R.id.tableLayout)
        public void onWordDetailView(View view){
            int itemPosition = getAdapterPosition();
            Nomen item = items.get(itemPosition);
            Intent intent = new Intent(mContext, WordClickViewActivity.class);
            String[] itemValue = new String[11];
            itemValue[0] = item.getNid()+"";
            itemValue[1] = item.getArtikel();
            itemValue[2] = item.getNomen();
            itemValue[3] = item.getPlural();
            itemValue[4] = item.getMean_ko();
            itemValue[5] = item.getMean_en();
            itemValue[6] = item.getExample();
            itemValue[7] = item.getExample_mean();
            itemValue[8] = item.getBookmark()+"";
            itemValue[9] = item.getStudy()+"";
            itemValue[10] = item.getDate();
            intent.putExtra("ItemValue",itemValue);
            mContext.startActivity(intent);
            Activity mActivity = (Activity)mContext;
            mActivity.overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_not_move_activity);
        }
    }
    public int remove(int position) {
        int item_id = items.get(position).getNid();
        items.remove(position);
        return item_id;
    }
    public Nomen getNomen(int position){
        Nomen item = items.get(position);
        return item;
    }

    public void showhideWord(int isshow) {
        if(isshow==0){//show면
            showhideWord = 0;
        }else { //hide 면
            showhideWord = 1;
        }
        notifyDataSetChanged();
    }
    public void showhideMean(int isshow) {
        if(isshow==0){//show면
            showhideMean = 0;
        }else { //hide 면
            showhideMean = 1;
        }
        notifyDataSetChanged();
    }
}

