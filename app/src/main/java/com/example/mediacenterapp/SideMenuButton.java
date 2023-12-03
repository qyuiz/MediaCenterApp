package com.example.mediacenterapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mediacenterapp.radio.AmFragment;
import com.example.mediacenterapp.radio.FmFragment;

public class SideMenuButton extends androidx.appcompat.widget.AppCompatButton {
    private static final String TAG = "SideMenuButton";

    private static final float TO_SMALL_RATE = 0.85f; // 縮小率

    private Context mContext;
    private MainActivity mMainActivity;

    public SideMenuButton(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        mContext = context;
        mMainActivity = (MainActivity) mContext;

        setBackgroundResource(R.drawable.side_menu_button_shape);
        toChangeDefaultTextColor(this);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonId = v.getId();
                Log.d(TAG, "onClick # ID = " + buttonId);
                Log.d(TAG, "onClick # ID = " + getResources().getResourceEntryName(buttonId));
                if (mMainActivity.getNowFragmentButtonId() != buttonId){
                    Log.d(TAG, "clicked on");
                    toChangeClickedTextColor((SideMenuButton) v);
                    checkFragmentResource(v.getId());
                    mMainActivity.setNowFragmentButtonId(buttonId);
                }
                setOtherButtonTextColor(buttonId);
            }
        });
    }

    private void setOtherButtonTextColor(int selectedId){
        Log.d(TAG, "setOtherButtonTextColor # selectedId = " + selectedId);

        LinearLayout sideMenuBar = mMainActivity.getSideMenuBar();
        for (int i = 0; i < sideMenuBar.getChildCount(); i++){
            View childView = sideMenuBar.getChildAt(i);
            if (childView instanceof SideMenuButton && childView.getId() != selectedId){
                Log.d(TAG, "setOtherButtonTextColor # id = " + childView.getId());
                SideMenuButton sideMenuButton = (SideMenuButton) childView;
                toChangeDefaultTextColor(sideMenuButton);
            }
        }
    }

    private boolean checkFragmentResource(int resourceId){
        if (resourceId == R.id.fm_button_side_menu){
            return replaceFragment(new FmFragment());
        }
        else if (resourceId == R.id.am_button_side_menu){
            return replaceFragment(new AmFragment());
        }
        else if (resourceId == R.id.bluetooth_button_side_menu){

        }
        else if (resourceId == R.id.usb_button_side_menu){

        }
        else if (resourceId == R.id.local_player_side_menu){

        }
        return false;
    }

    private boolean replaceFragment(Fragment fragment) {
        try {
            FragmentManager fragmentManager = mMainActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } catch (Exception e){
            Log.d(TAG, "Error:" + e.getMessage());
            return false;
        }
        return true;
    }

    private void removeTopFragment() {
        FragmentManager fragmentManager = mMainActivity.getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void toChangeClickedTextColor(SideMenuButton sideMenuButton) {
        // この方法じゃないと色が変わらない
        sideMenuButton.setTextColor(ContextCompat.getColor(mContext, R.color.side_menu_button_fore_clicked));
    }
    private void toChangePressedTextColor(SideMenuButton sideMenuButton) {
        sideMenuButton.setTextColor(ContextCompat.getColor(mContext, R.color.side_menu_button_fore_pressed));
    }

    private void toChangeDefaultTextColor(SideMenuButton sideMenuButton){
        sideMenuButton.setTextColor(ContextCompat.getColor(mContext, R.color.side_menu_button_fore));
    }


    private void outLogColor(){
        String hexColor = String.format("#%08X", getCurrentTextColor());
        Log.d(TAG, hexColor);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);

        Log.d(TAG, "setPressed = " + pressed);
        if (pressed) {
            /* 押してる時 */
            setBackgroundResource(R.drawable.side_menu_button_shape_pressed);
            toChangePressedTextColor(this);
            this.startAnimation(toSmall());
        } else {
            /* 放した時 */
            setBackgroundResource(R.drawable.side_menu_button_shape);
            if (this.getId() == mMainActivity.getNowFragmentButtonId()){
                toChangeClickedTextColor(this);
            }
            else {
                toChangeDefaultTextColor(this);
            }
            this.startAnimation(toDefault());
        }
    }

    private ScaleAnimation toSmall(){
        /* 縮小アニメーション */
        // https://qiita.com/nhiroyasu/items/7756a59ec7eb3cb8b998
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, TO_SMALL_RATE / 1.0f, 1.0f, TO_SMALL_RATE / 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(150);
        scaleAnimation.setRepeatCount(0);
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }
    private ScaleAnimation toDefault(){
        /* 戻るアニメーション */
        // https://qiita.com/nhiroyasu/items/7756a59ec7eb3cb8b998
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                TO_SMALL_RATE / 1.0f, 1.0f, TO_SMALL_RATE / 1.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(150);
        scaleAnimation.setRepeatCount(0);
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }
}
