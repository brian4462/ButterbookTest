package com.jica.butterbookdata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jica.butterbookdata.R;
import com.jica.butterbookdata.database.entity.Nomen;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jica.butterbookdata.R.drawable.ic_bookmark_border_black_24dp;

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
        if(showhideWord==1){
            recyclerViewHolder.tvWord.setVisibility(View.INVISIBLE);
        }else {
            recyclerViewHolder.tvWord.setVisibility(View.VISIBLE);
        }
        //뜻 보이기 또는 숨기기
        if(showhideMean==1){
            recyclerViewHolder.tvMean.setVisibility(View.INVISIBLE);
        }else {
            recyclerViewHolder.tvMean.setVisibility(View.VISIBLE);
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
                    Toast.makeText(mContext, "북마크해제", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    break;
                case 1: //no
                    items.get(itemPosition).setBookmark(0);
                    Toast.makeText(mContext, "북마크설정", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    break;
            }
        }
        @OnClick(R.id.tableLayout)
        public void onShowWordView(View view){
            int itemPosition = getAdapterPosition();

        }
    }
    public int remove(int position) {
        int item_id = items.get(position).getNid();
        items.remove(position);
        return item_id;
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

