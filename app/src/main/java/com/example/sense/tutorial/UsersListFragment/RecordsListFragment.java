package com.example.sense.tutorial.UsersListFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.sense.tutorial.R;
import com.example.sense.tutorial.RetrofitApi.API.Models.User;
import com.example.sense.tutorial.RetrofitManager.RetrofitManager;
import com.example.sense.tutorial.UserDetailFragment.addUserFragment;
import com.example.sense.tutorial.Utilities.C;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class RecordsListFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddUser;

    public RecordsListFragment() {
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);
        Context context = view.getContext();

        fabAddUser = (FloatingActionButton) view.findViewById(R.id.fabAddUser);

        /**
         * Add User to the DB
         */
        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                C.launchFragment(((AppCompatActivity) getActivity()), new addUserFragment());
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.contactListFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        /**
         * Register Eventbus
         */
        EventBus.getDefault().register(this);

        /**
         * Get User's list from DB using RetrofitManager
         * RetrofitManager will send back the results with EventBus
         */

        /**
         * Need to start a progressDialog
         */
        RetrofitManager retrofitManager = new RetrofitManager(getActivity());
        retrofitManager.getRecordsFromDatabase();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGreenRobotEvent(ArrayList<User> RecordList) {

        if (RecordList == null) {
            return;
        }

        /**
         * Need to stop a progressDialog, if th eprogressDialog lasts for more than a certain period then it should terminate.
         */
        recyclerView.setAdapter(new ContactListViewAdapter(this, RecordList));

    }

    /**
     * Fragment User List Adapter
     *
     *
     */
    private class ContactListViewAdapter extends RecyclerView.Adapter<ContactListViewAdapter.ViewHolder> {

        private RecordsListFragment fragment;
        private Context context;
        private ArrayList<User> userList = new ArrayList<>();

        public ContactListViewAdapter(RecordsListFragment fragment, ArrayList<User> userList) {
            this.fragment = fragment;
            context = fragment.getActivity();
            this.userList = userList;
        }

        @Override
        public ContactListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_record_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ContactListViewAdapter.ViewHolder holder, final int position) {
            holder.vhUser = userList.get(position);
            holder.name.setText(holder.vhUser.getName());

            String imageUri = userList.get(position).getImage();
            Glide.with(context)
                    .load(imageUri)
                    .error(R.drawable.person)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.image);
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView name;
            public final ImageView image;
            public User vhUser;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                name = (TextView) view.findViewById(R.id.tv_name);
                image = (ImageView) view.findViewById(R.id.iv_user_Image);
            }
        }
    }
}
