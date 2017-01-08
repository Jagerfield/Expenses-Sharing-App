package com.example.sense.tutorial.AddGroupMembers;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.sense.tutorial.R;
import com.example.sense.tutorial.RetrofitApi.API.Models.User;
import com.example.sense.tutorial.RetrofitManager.RetrofitManager;
import com.example.sense.tutorial.Utilities.C;
import java.util.ArrayList;
import java.util.LinkedList;
import jagerfield.mobilecontactslibrary.Contact.Contact;
import jagerfield.mobilecontactslibrary.ElementContainers.EmailContainer;
import jagerfield.mobilecontactslibrary.ImportContacts;
import jagerfield.utilities.lib.AppUtilities;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class AppUserListActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);

        recyclerView = (RecyclerView) findViewById(R.id.rv_usertList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        activity = this;

        RetrofitManager.getNewInstance(this).getUsersFromDatabase(new RetrofitManager.IRetrofitCallback()
        {
            @Override
            public void getAllUsersList(ArrayList<User> usersList)
            {
                if (usersList != null)
                {
                    prepareMembersList(activity, usersList);
                }
            }

            @Override
            public void getOnlyAddedUsersList(ArrayList<User> usersList) {

            }
        });

    }

    public void prepareMembersList(Activity activity, ArrayList<User> usersList)
    {
        //Check permission
        if (!AppUtilities.getPermissionUtil(this).isPermissionGranted(android.Manifest.permission.READ_CONTACTS).isGranted())
        {
            Log.e(C.TAG_LIB, android.Manifest.permission.READ_CONTACTS + " permission is missing");
            return;
        }

        //Fetch mobile contacts list
        ImportContacts importContacts = new ImportContacts(activity);
        ArrayList<Contact> contactsList = importContacts.getContacts();

        if (contactsList == null)
        {
            Log.e(C.TAG_LIB, "contactsList is null");
            return;
        }

        ArrayList<User> membersList = new ArrayList<>();

        //Create the memberlist by finding which users in the app user list exist in the contact list
        for (int i = 0; i < contactsList.size(); i++)
        {
            //Compare according to the gamil email, because the
            String gmail = "";
            LinkedList<EmailContainer> emailContainer = contactsList.get(i).getEmails();
            for (int j = 0; j < emailContainer.size(); j++)
            {
                String email = emailContainer.get(j).getEmail();
                if (email.contains("@gmail.com"))
                {
                    gmail = email.trim();
                    break;
                }
            }

            if (gmail.trim().isEmpty()){return;}

            for (int k = 0; k < usersList.size(); k++)
            {
                if (usersList.get(k).getEmail().trim().equals(gmail))
                {
                    membersList.add(usersList.get(k));
                }
            }
        }

        recyclerView.setAdapter(new MembersListAdapter(activity, membersList));
    }

    private class MembersListAdapter extends RecyclerView.Adapter<MembersListAdapter.ViewHolder> {

        private Activity activity;
        private ArrayList<User> membersList = new ArrayList<>();

        public MembersListAdapter(Activity activity, ArrayList<User> membersList) {
            this.activity = activity;
            this.membersList = membersList;
        }

        @Override
        public MembersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_db_users_list_item, parent, false);
            return new MembersListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MembersListAdapter.ViewHolder holder, final int position) {
            holder.vhUser = membersList.get(position);
            holder.name.setText(holder.vhUser.getName());

            String imageUri = membersList.get(position).getImage();
            Glide.with(activity)
                    .load(imageUri)
                    .error(R.drawable.person)
                    .bitmapTransform(new CropCircleTransformation(activity))
                    .into(holder.image);
        }

        @Override
        public int getItemCount() {
            return membersList.size();
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