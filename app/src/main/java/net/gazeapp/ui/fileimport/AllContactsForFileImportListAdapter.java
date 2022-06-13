/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.gazeapp.ui.fileimport;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.gazeapp.EditContactActivity;
import net.gazeapp.R;
import net.gazeapp.data.model.Contact;
import net.gazeapp.utilities.MediaTools;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllContactsForFileImportListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = getClass().getSimpleName();

    private static Activity activity;
    private final LayoutInflater mInflater;
    private final List<Contact> mDataset;
    private final MediaTools mMediaTools;
    private final ArrayList<Uri> filesToImport;

    public AllContactsForFileImportListAdapter(Activity activity, List<Contact> myDataset, MediaTools mediaTools, ArrayList<Uri> filesToImport) {
        mInflater = LayoutInflater.from(activity);
        AllContactsForFileImportListAdapter.activity = activity;
        this.mDataset = myDataset;
        this.mMediaTools = mediaTools;
        this.filesToImport = filesToImport;
    }

    @Override
    public int getItemCount() {
        return (null != mDataset ? mDataset.size() : 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerBodyObjectViewHolder rh = new RecyclerBodyObjectViewHolder(mInflater.inflate(R.layout.search_results_contacts_listview_row, parent, false), new MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "Clicked Position: " + position);

                Contact contact = mDataset.get(position);

                // Display EDIT CONTACT Activity
                Intent intent = new Intent(activity, EditContactActivity.class);
                intent.putExtra("filesToImport", filesToImport);
                intent.putExtra("contact", contact);
                activity.startActivity(intent);
            }
        });

        return rh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        Contact contact = mDataset.get(position);

        ((RecyclerBodyObjectViewHolder) viewHolder).contactName.setText(contact.getContactName());
        ((RecyclerBodyObjectViewHolder) viewHolder).additionalInfo.setText(contact.getAdditionalInfo());

        if (contact.getAdditionalInfo() == null || contact.getAdditionalInfo().length() < 1) {
            ((RecyclerBodyObjectViewHolder) viewHolder).additionalInfo.setVisibility(View.GONE);
        }

        // Circle Profile Pic
        CircleImageView contactImage = ((RecyclerBodyObjectViewHolder) viewHolder).contactImage;

        // TODO FIXME move logic to viewmodel / room
        // TODO FIXME move logic to viewmodel / room
//        String mainPicInternalStoragePath = mMediaTools.getContactMainPicPath(contact);
//        Glide.with(activity).load(mainPicInternalStoragePath).dontAnimate().placeholder(R.drawable.silhouette).into(contactImage);
    }

    /**
     * Holder for the CONTACT VIEW BODY
     * Main Profile Pic, overlaying Profile Name, overlaying City
     * and other info
     */
    static class RecyclerBodyObjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final String TAG = getClass().getSimpleName();

        CircleImageView contactImage;
        TextView contactName;
        TextView additionalInfo;
        MyClickListener myClickListener;

        public RecyclerBodyObjectViewHolder(View itemView, MyClickListener clickListener) {
            super(itemView);
            myClickListener = clickListener;

            contactImage = itemView.findViewById(R.id.profile_image);
            contactName = itemView.findViewById(R.id.contactName);
            additionalInfo = itemView.findViewById(R.id.additionalInfo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "RecyclerBodyObjectViewHolder onClick()");
            if (v instanceof ImageView) {
                // ImageView clicked
            } else {
                myClickListener.onItemClick(getAdapterPosition(), v);
                // Something else clicked
            }
        }

    }

    public interface MyClickListener {
        // add other click listeners here, depending on the widget
        void onItemClick(int position, View v);
    }
}
