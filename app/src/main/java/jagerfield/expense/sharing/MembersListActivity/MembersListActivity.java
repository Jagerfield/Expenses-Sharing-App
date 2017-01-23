package jagerfield.expense.sharing.MembersListActivity;

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

import jagerfield.expense.sharing.R;
import jagerfield.expense.sharing.Retrofit.User;
import jagerfield.expense.sharing.Retrofit.RetrofitManager;
import jagerfield.expense.sharing.Utilities.Util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import jagerfield.mobilecontactslibrary.Contact.Contact;
import jagerfield.mobilecontactslibrary.ElementContainers.EmailContainer;
import jagerfield.mobilecontactslibrary.ImportContacts;
import jagerfield.utilities.lib.AppUtilities;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MembersListActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);

        recyclerView = (RecyclerView) findViewById(R.id.rv_membersList);
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
            Log.e(Util.TAG_LIB, android.Manifest.permission.READ_CONTACTS + " permission is missing");
            return;
        }

        //Fetch mobile contacts list
        ImportContacts importContacts = new ImportContacts(activity);
        ArrayList<Contact> contactsList = importContacts.getContacts();

        if (contactsList == null)
        {
            Log.e(Util.TAG_LIB, "contactsList is null");
            return;
        }

        ArrayList<User> membersList = new ArrayList<>();

        Iterator itrOuter = contactsList.listIterator();
        Iterator itrInner = usersList.listIterator();

        while (itrOuter.hasNext())
        {
            String contactGmail = "";
            Contact contact = (Contact) itrOuter.next();
            LinkedList<EmailContainer> emailContainer = contact.getEmails();
            for (int j = 0; j < emailContainer.size(); j++)
            {
                String email = emailContainer.get(j).getEmail();
                if (email.contains("@gmail.com"))
                {
                    contactGmail = email.trim();
                    break;
                }
            }

            itrInner = usersList.listIterator();

            if (!contactGmail.trim().isEmpty())
            {
                while (itrInner.hasNext())
                {
                    User user = (User) itrInner.next();
                    String userGmail = user.getEmail().trim();
                    if (userGmail.equals(contactGmail))
                    {
                        membersList.add(user);
                        itrOuter.remove();
                        itrInner.remove();
                        break;
                    }
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