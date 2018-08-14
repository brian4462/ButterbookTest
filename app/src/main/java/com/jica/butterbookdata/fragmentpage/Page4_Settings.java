package com.jica.butterbookdata.fragmentpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.jica.butterbookdata.InternetPagesActivity;
import com.jica.butterbookdata.R;
import com.jica.butterbookdata.database.AppDB;
import com.jica.butterbookdata.database.dao.AdjektivDAO;
import com.jica.butterbookdata.database.dao.NomenDAO;
import com.jica.butterbookdata.database.dao.VerbenDAO;
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
    private NomenDAO nomenDAO;
    private VerbenDAO verbenDAO;
    private AdjektivDAO adjektivDAO;

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
        nomenDAO = AppDB.getInstance(getActivity()).nomenDAO();
        verbenDAO = AppDB.getInstance(getActivity()).verbenDAO();
        adjektivDAO = AppDB.getInstance(getActivity()).adjektivDAO();

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
    @OnClick(R.id.inquire_layout)
    public void onInquire(View view){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("plain/text");
        // email setting 배열로 해놔서 복수 발송 가능
        String[] address = {"brian4462@gmail.com"};
        email.putExtra(Intent.EXTRA_EMAIL, address);
        email.putExtra(Intent.EXTRA_SUBJECT,"[버터북 문의사항]제목");
        email.putExtra(Intent.EXTRA_TEXT,"오역, 문자오타, 오류사항, 버그, 건의사항, 문의사항 등을 적어주세요!\n 버그나 오류사항의 경우 핸드폰 기종을 적어주시면 감사하겠습니다.");
        startActivity(email);

    }
    @OnClick(R.id.internet_page_layout)
    public void onGotoInternet(View view){
        Intent intent = new Intent(getActivity(), InternetPagesActivity.class);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_not_move_activity);
    }
    @OnClick(R.id.setting_reset_study_layout)
    public void onClearStudy(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("정말 초기화 하겠습니까?");
        builder.setIcon(R.drawable.ic_warning_black_24dp);
        builder.setMessage("학습한 단어들이 다시 오늘의 단어에 보여집니다.(북마크와 내 단어는 유지됩니다.)");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<Word> wordList = wordDAO.getAll();
                for(Word value:wordList){
                    value.setQuizfinish(1);
                    wordDAO.update(value);
                }
                Toast.makeText(getActivity(), "학습한 단어들이 초기화되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @OnClick(R.id.setting_reset_all_layout)
    public void onClear(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("정말 초기화 하겠습니까?");
        builder.setIcon(R.drawable.ic_warning_black_24dp);
        builder.setMessage("모든 설정이 초기값으로 돌아가고 내가 저장한 단어도 사라집니다.(앱이 재실행 됩니다.)");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                wordDAO.deleteall();
                nomenDAO.deleteall();
                verbenDAO.deleteall();
                adjektivDAO.deleteall();

                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();

                restartApp(getActivity());
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
    public static void restartApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        System.exit(0);
    }
}
