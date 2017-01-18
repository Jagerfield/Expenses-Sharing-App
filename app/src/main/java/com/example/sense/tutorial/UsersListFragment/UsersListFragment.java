package com.example.sense.tutorial.UsersListFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.sense.tutorial.MembersListActivity.MembersListActivity;
import com.example.sense.tutorial.R;
import com.example.sense.tutorial.Retrofit.User;
import com.example.sense.tutorial.Retrofit.RetrofitManager;
import com.example.sense.tutorial.AddUserFragment.AddUserFragment;
import com.example.sense.tutorial.Utilities.Util;

import java.util.ArrayList;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UsersListFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddUser;
    private FloatingActionButton fabShowMembers;

    public UsersListFragment() {
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_db_users_list, container, false);
        Context context = view.getContext();

        fabAddUser = (FloatingActionButton) view.findViewById(R.id.fabAddUser);
        fabShowMembers = (FloatingActionButton) view.findViewById(R.id.fabMembersList);

        /**
         * Add Member
         */
        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Util.launchFragment(((AppCompatActivity) getActivity()), new AddUserFragment());
            }
        });

        final Activity activity = getActivity();

        fabShowMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activity != null)
                {
                    Intent i = new Intent(activity, MembersListActivity.class);
                    activity.startActivity(i);
                }

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_usertList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        /**
         * Get Member's list from DB using RetrofitManager
         * RetrofitManager will send back the results with a callback
         */

        final UsersListFragment fragment = this;

        RetrofitManager.getNewInstance(getActivity()).getUsersFromDatabase(new RetrofitManager.IRetrofitCallback() {

            @Override
            public void getAllUsersList(ArrayList<User> usersList)
            {
                if (usersList != null)
                {
                    recyclerView.setAdapter(new ContactListViewAdapter(fragment, usersList));
                }
            }

            @Override
            public void getOnlyAddedUsersList(ArrayList<User> usersList) {

            }
        });

        return view;
    }


    /**
     * Fragment Member List Adapter
     *
     *
     */
    private class ContactListViewAdapter extends RecyclerView.Adapter<ContactListViewAdapter.ViewHolder> {

        private UsersListFragment fragment;
        private Context context;
        private ArrayList<User> userList = new ArrayList<>();

        public ContactListViewAdapter(UsersListFragment fragment, ArrayList<User> userList) {
            this.fragment = fragment;
            context = fragment.getActivity();
            this.userList = userList;
        }

        @Override
        public ContactListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_db_users_list_item, parent, false);
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
