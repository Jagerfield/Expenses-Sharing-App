package jagerfield.expense.sharing.AddUserFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import jagerfield.expense.sharing.R;
import jagerfield.expense.sharing.Retrofit.User;
import jagerfield.expense.sharing.Retrofit.RetrofitManager;
import jagerfield.expense.sharing.UsersListFragment.UsersListFragment;
import jagerfield.expense.sharing.Utilities.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class AddUserFragment extends Fragment implements View.OnClickListener {

    private UserEntryEvaluation userEntryEvaluation;
    private TextInputEditText inputName;
    private TextInputEditText inputMobile;
    private TextInputEditText inputEmail;
    private FloatingActionButton fabAddUser;
    private ImageView imageView;
    private Button insertUserButton;
    private File image = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_new_user, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        instantiateFields(view);
    }

    @Override
    public void onClick(View v)
    {
        if (v == fabAddUser)
        {
            EasyImage.openChooserWithGallery(this, "Choose profile picture", Util.CHOOSE_PHOTO_CODE);
        }
        else if (v== insertUserButton)
        {
            if (userEntryEvaluation == null) { return; }

            /**
             * Evaluate text vallues an load if valid to entryValues
             */
            UserEntryEvaluation.EntryValues entryValues = userEntryEvaluation.evaluate();

            if (entryValues.isValid())
            {
                final Activity activity = getActivity();
                RetrofitManager.getNewInstance(activity).addUserToDatabase(entryValues, new RetrofitManager.IRetrofitCallback() {

                    @Override
                    public void getAllUsersList(ArrayList<User> usersList)
                    {  }

                    @Override
                    public void getOnlyAddedUsersList(ArrayList<User> addedUsersList)
                    {
                        if (addedUsersList != null)
                        {
                            if (activity == null){return;}
                            Toast.makeText(activity, "Member : " + addedUsersList.get(0).getName() + " is saved", Toast.LENGTH_LONG).show();
                            Log.e(Util.TAG_LIB, "Member : " + addedUsersList.get(0).getName() + " is saved");
                            String str = "";
                        }
                    }
                });

                returnToUserList();
            }
            else
            {
                Toast.makeText(getActivity(), "Fields are not valid", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void instantiateFields(View view)
    {
        imageView = (ImageView) view.findViewById(R.id.ivProfilePic);
        inputName = (TextInputEditText) view.findViewById(R.id.inputName);
        inputMobile = (TextInputEditText) view.findViewById(R.id.inputMobile);
        inputEmail = (TextInputEditText) view.findViewById(R.id.inputEmail);
        insertUserButton = (Button) view.findViewById(R.id.button);
        insertUserButton.setOnClickListener(this);
        fabAddUser = (FloatingActionButton) view.findViewById(R.id.fabAddUser);
        fabAddUser.setOnClickListener(this);

        /**
         *  userEntryEvaluation will monitor text changes in the relevant fields and highlights errors
         */
        if(userEntryEvaluation == null) { userEntryEvaluation = new UserEntryEvaluation(getActivity(), inputName, inputMobile, inputEmail, insertUserButton); }

        inputName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!userEntryEvaluation.isNameValid(inputName).trim().equals(""))
                {
                    userEntryEvaluation.evaluate();
                }

            }
        });


        inputMobile.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!userEntryEvaluation.isNumberValid(inputMobile).trim().equals(""))
                {
                    userEntryEvaluation.evaluate();
                }
            }
        });

        inputEmail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!userEntryEvaluation.isEmailValid(inputEmail).trim().equals(""))
                {
                    userEntryEvaluation.evaluate();
                }
            }
        });
    }

    /**
     * Called when a picture is taken or selected
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback()
        {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                image = null;
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                //Handle the images
                if (type == Util.CHOOSE_PHOTO_CODE)
                {
                    image = imagesFiles.get(0);
                    if (image==null)
                    {
                        return;
                    }

                    userEntryEvaluation.setImageFile(image);

                    Glide.with(getActivity())
                            .load(image)
                            .error(R.drawable.ic_person_black_128dp)
                            .into(imageView);
                }
            }
        });
    }

    public void onFragmentBackPress()
    {
        returnToUserList();
    }

    private void returnToUserList()
    {
        Util.launchFragment(((AppCompatActivity) getActivity()), new UsersListFragment());
    }

}


















