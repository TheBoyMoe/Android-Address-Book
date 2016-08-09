/***
 Copyright (c) 2015 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain	a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 From _The Busy Coder's Guide to Android Development_
 https://commonsware.com/Android
 */

package com.example.demo.data.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.demo.data.ModelItemAdapter;

public abstract class ChoiceCapableAdapter<T extends RecyclerView.ViewHolder>
            extends RecyclerView.Adapter<T>{

    private final RecyclerView mRecyclerView;
    private final ChoiceMode mChoiceMode;

    public ChoiceCapableAdapter(RecyclerView recyclerView, ChoiceMode choiceMode) {
        super();
        mRecyclerView = recyclerView;
        mChoiceMode = choiceMode;
    }

    public void onChecked(int position, boolean isChecked) {
        if (mChoiceMode.isSingleChoice()) {
            // retrieve the checked item, uncheck it
            int checked = mChoiceMode.getCheckedPosition();
            if (checked >= 0) {
                ModelItemAdapter.ViewHolder row =
                    (ModelItemAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(checked);
                if (row != null) {
                    row.setChecked(false);
                }
            }
        }
        mChoiceMode.setChecked(position, isChecked);
    }

    public boolean isChecked(int position) {
        return mChoiceMode.isChecked(position);
    }

    public void onSaveInstanceState(Bundle state) {
        mChoiceMode.onSaveInstanceState(state);
    }

    public void onRestoreInstanceState(Bundle state) {
        mChoiceMode.onRestoreInstanceState(state);
    }

    @Override
    public void onViewAttachedToWindow(T holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getAdapterPosition() != mChoiceMode.getCheckedPosition()) {
            ((ModelItemAdapter.ViewHolder)holder).setChecked(false);
        }
    }
}
