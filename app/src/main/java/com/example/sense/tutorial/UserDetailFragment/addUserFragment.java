package com.example.sense.tutorial.UserDetailFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.sense.tutorial.R;
import com.example.sense.tutorial.RetrofitManager.RetrofitManager;
import com.example.sense.tutorial.UsersListFragment.RecordsListFragment;
import com.example.sense.tutorial.Utilities.C;
import com.example.sense.tutorial.Utilities.UserEntry;
import java.io.File;
import java.util.List;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class addUserFragment extends Fragment implements View.OnClickListener {

    private UserEntry userEntry;
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
        return inflater.inflate(R.layout.fragment_record_details, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        instantiateFields(view);

//        userEntry.evaluate();
    }

    @Override
    public void onClick(View v)
    {
        if (v == fabAddUser)
        {
            EasyImage.openChooserWithGallery(this, "Choose profile picture", C.CHOOSE_PHOTO_CODE);
        }
        else if (v== insertUserButton)
        {
            if (userEntry == null) { return; }

            /**
             * Evaluate text vallues and load if valid then load them to entryValues
             */
            UserEntry.EntryValues entryValues = userEntry.evaluate();

            if (entryValues.isValid())
            {
                (new RetrofitManager(getActivity())).addRecordToDatabase(entryValues);
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
         *  userEntry will monitor text changes in the relevant fields and highlights errors
         */
        if(userEntry == null) { userEntry = new UserEntry(getActivity(), inputName, inputMobile, inputEmail, insertUserButton); }

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
                if(!userEntry.isNameValid(inputName).trim().equals(""))
                {
                    userEntry.evaluate();
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
                if(!userEntry.isNumberValid(inputMobile).trim().equals(""))
                {
                    userEntry.evaluate();
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
                if(!userEntry.isEmailValid(inputEmail).trim().equals(""))
                {
                    userEntry.evaluate();
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
                if (type == C.CHOOSE_PHOTO_CODE)
                {
                    image = imagesFiles.get(0);
                    if (image==null)
                    {
                        return;
                    }

                    userEntry.setImageFile(image);

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
        C.launchFragment(((AppCompatActivity) getActivity()), new RecordsListFragment());
    }

}


















