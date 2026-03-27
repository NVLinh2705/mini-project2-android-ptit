package com.btl_ptit.kiemtra2_android_ptit.listener;

import android.view.View;

import java.util.ArrayList;

public interface ItemListener {
    public <T> void onClick(ArrayList<T> list, View view, int position);

    public <T> void onLongClick(ArrayList<T> list, View view, int position);
}
