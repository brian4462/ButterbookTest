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

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.RecyclerViewHolder> {
    private NomenDAO nomenDAO;
    private VerbenDAO verbenDAO;
    private AdjektivDAO adjektivDAO;
    private WordDAO wordDAO;
    private List<Word> items;
    private Context mContext;

    int showhideWord = 0; //0:show 1:hide
    int showhideMean = 0; //0:show 1:hide
    private long backKeyPressedTime = 0;

    public WordAdapter(Context context, List<Word> itemList) {
        this.mContext = context;
        items = itemList;
        wordDAO = AppDB.getInstance(mContext).wordDAO();
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
        //명사,동사,형용사 구분
        if(items.get(i).getCategory()==1){
            recyclerViewHolder.tvGrammerForm.setText("N");
        }else if(items.get(i).getCategory()==2){
            recyclerViewHolder.tvGrammerForm.setText("V");
        }else {
            recyclerViewHolder.tvGrammerForm.setText("A");
        }
        //단어와 뜻
        recyclerViewHolder.tvWord.setText(items.get(i).getWord());
        recyclerViewHolder.tvMean.setText(items.get(i).getMean());
        //북마크 변화
        switch(items.get(i).getBookmark()){
            case 0:{
                recyclerViewHolder.ibBookmark.setImageResource(R.drawable.ic_bookmark_white_24dp);
                break;
            }
            case 1:{
                recyclerViewHolder.ibBookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                break;
            }
            case 2:{
                recyclerViewHolder.ibBookmark.setImageResource(R.drawable.ic_delete_orange_24dp);
                break;
            }
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
                    wordDAO.update(items.get(itemPosition));
                    notifyDataSetChanged();
                    break;
                case 1: //no
                    items.get(itemPosition).setBookmark(0);
                    wordDAO.update(items.get(itemPosition));
                    notifyDataSetChanged();
                    break;
                case 2:
                    if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                        backKeyPressedTime = System.currentTimeMillis();
                        Toast.makeText(mContext, "한번더 클릭하면 삭제됩니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                        wordDAO.delete(items.get(itemPosition));
                        removeAt(itemPosition);
                        Toast.makeText(mContext, "단어가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged();
                    break;
            }
        }
        @OnClick(R.id.tableLayout)
        public void onWordDetailView(View view){
            int itemPosition = getAdapterPosition();
            Word item = items.get(itemPosition);

            int category = item.getCategory();
            int categoryId = item.getCategory_id();
            int clickcnt = item.getClickcnt()+1;
            item.setClickcnt(clickcnt);
            wordDAO.update(item);
            notifyDataSetChanged();

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
    private void removeAt(int position) {
        items.remove(position);
        notifyItemRemoved(position-1);
    }
}

