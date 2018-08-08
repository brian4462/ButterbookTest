package com.jica.butterbookdata.fragmentpage;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.jica.butterbookdata.R;
import com.jica.butterbookdata.ViewActivity;
import com.jica.butterbookdata.database.AppDB;
import com.jica.butterbookdata.database.dao.WordDAO;
import com.jica.butterbookdata.database.entity.Word;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Page4_Settings extends Fragment {
    private Unbinder unbinder;
    private SharedPreferences settings;
    private WordDAO wordDAO;

    @BindView(R.id.tvNomencount)
    TextView tvNomencount;
    @BindView(R.id.tvVerbencount)
    TextView tvVerbencount;
    @BindView(R.id.tvAdjektivcount)
    TextView tvAdjektivcount;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_4_settings,container,false);
        ButterKnife.bind(this,view);
        settings = getActivity().getSharedPreferences("myPref",0);
        wordDAO = AppDB.getInstance(getActivity()).wordDAO();

        tvNomencount.setText(settings.getInt("NomenCount",5)+"개");
        tvVerbencount.setText(settings.getInt("VerbenCount",3)+"개");
        tvAdjektivcount.setText(settings.getInt("AdjektivCount",2)+"개");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null)
            unbinder.unbind();
    }
    @OnClick(R.id.setting_nomen_layout)
    public void onNomencountPick(View view){
        numberpickDialog(view, 1);
    }
    @OnClick(R.id.setting_verben_layout)
    public void onVerbencountPick(View view){
        numberpickDialog(view, 2);
    }
    @OnClick(R.id.setting_adjektiv_layout)
    public void onAdjektivcountPick(View view){
        numberpickDialog(view, 3);
    }

    public void numberpickDialog(View view, final int category) {
        final Dialog numberpickDialog = new Dialog(getActivity());
        numberpickDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        numberpickDialog.setContentView(R.layout.dialog_numberpick);

        Button btnok = (Button) numberpickDialog.findViewById(R.id.btn_number_ok);
        Button btncancel = (Button) numberpickDialog.findViewById(R.id.btn_number_cancel);

        final NumberPicker np = (NumberPicker) numberpickDialog.findViewById(R.id.numberPicker);
        np.setMinValue(0);
        np.setMaxValue(10);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(np, android.R.color.white);
        np.setWrapSelectorWheel(false);
        switch (category){
            case 1:{
                np.setValue(settings.getInt("NomenCount",5));
                break;
            }
            case 2:{
                np.setValue(settings.getInt("VerbenCount",3));
                break;
            }
            case 3:{
                np.setValue(settings.getInt("AdjektivCount",2));
                break;
            }
        }
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {

            }
        });
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (category){
                    case 1:{
                        tvNomencount.setText(String.valueOf(np.getValue()+"개"));
                        numberpickDialog.dismiss();
                        settings = getActivity().getSharedPreferences("myPref",0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("NomenCount",np.getValue());
                        editor.commit();
                        List<Word> items = wordDAO.getAllbyCategory_Finish(1,1);
                        int size = (items==null)?0:items.size();
                        if(size<settings.getInt("NomenCount",5)){
                            editor = settings.edit();
                            editor.putInt("NomenCount",size);
                            editor.commit();
                            tvNomencount.setText(settings.getInt("NomenCount",5)+"개");
                            Toast.makeText(getActivity(), "선택리스트에 항목이 적어 추천으로 받을 할일 개수가 변경됩니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "변경사항은 내일부터 적용됩니다", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case 2:{
                        tvVerbencount.setText(String.valueOf(np.getValue()+"개"));
                        numberpickDialog.dismiss();
                        settings = getActivity().getSharedPreferences("myPref",0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("VerbenCount",np.getValue());
                        editor.commit();
                        List<Word> items = wordDAO.getAllbyCategory_Finish(2,1);
                        int size = (items==null)?0:items.size();
                        if(size<settings.getInt("VerbenCount",3)){
                            editor = settings.edit();
                            editor.putInt("VerbenCount",size);
                            editor.commit();
                            tvVerbencount.setText(settings.getInt("VerbenCount",3)+"개");
                            Toast.makeText(getActivity(), "선택리스트에 항목이 적어 추천으로 받을 할일 개수가 변경됩니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "변경사항은 내일부터 적용됩니다", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case 3:{
                        tvAdjektivcount.setText(String.valueOf(np.getValue()+"개"));
                        numberpickDialog.dismiss();
                        settings = getActivity().getSharedPreferences("myPref",0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("AdjektivCount",np.getValue());
                        editor.commit();
                        List<Word> items = wordDAO.getAllbyCategory_Finish(3,1);
                        int size = (items==null)?0:items.size();
                        if(size<settings.getInt("AdjektivCount",2)){
                            editor = settings.edit();
                            editor.putInt("AdjektivCount",size);
                            editor.commit();
                            tvAdjektivcount.setText(settings.getInt("AdjektivCount",2)+"개");
                            Toast.makeText(getActivity(), "선택리스트에 항목이 적어 추천으로 받을 할일 개수가 변경됩니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "변경사항은 내일부터 적용됩니다", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberpickDialog.dismiss();
            }
        });
        numberpickDialog.show();
    }

    private void setDividerColor(NumberPicker picker, int color) {
        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for(java.lang.reflect.Field pf: pickerFields){
            if(pf.getName().equals("mSelectDivider")){
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker,colorDrawable);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
