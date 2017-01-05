package com.example.sense.tutorial.Utilities;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.widget.Button;

import com.example.sense.tutorial.R;


public class Validation
{
    private Response response;
    private Context context;
    private final TextInputEditText editName;
    private final TextInputEditText editNumber;
    private final TextInputEditText editEmail;
    private final Button userButton;

    public Validation(Context context, TextInputEditText editName, TextInputEditText editNumber, TextInputEditText editEmail, Button userButton)
    {
        this.context = context;
        this.editName = editName;
        this.editNumber = editNumber;
        this.editEmail = editEmail;
        this.userButton = userButton;
    }


    public Response evaluate()
    {
        response = new Response();
        String name = isNameValid(editName);
        String number = isNumberValid(editNumber);
        String email = isEmailValid(editEmail);

        if(name.isEmpty() || number.isEmpty() || email.isEmpty())
        {
            response.result = false;
        }
        else
        {
            response.result = true;
            response.setName(name);
            response.setNumber(number);
            response.setEmail(email);
        }

        if (response.isValid())
        {
            userButton.setAlpha(1f);
            userButton.setEnabled(true);
        }
        else
        {
            userButton.setAlpha(0.3f);
            userButton.setEnabled(false);
        }

        return response;
    }

    public String isNameValid(TextInputEditText inputName)
    {
        String name = inputName.getText().toString().trim();

        if(name==null || name.trim().isEmpty())
        {
            return "";
        }

        if(!name.matches("^[A-Za-z']+( [A-Za-z']+)*$"))
        {
            inputName.setError("Incorrect name");
            return "";
        }

        inputName.setError(null);

        return name;
    }

    public String isNumberValid(TextInputEditText inputMobile)
    {
        String number = inputMobile.getText().toString().trim();

        if(number==null || number.trim().isEmpty())
        {
            return "";
        }

        if(!number.matches("\\d+(?:\\.\\d+)?") )
        {
            inputMobile.setError("Incorrect number");
            return "";
        }

        inputMobile.setError(null);
        return number;
    }

    public String isEmailValid(TextInputEditText editEmail)
    {
        String email = editEmail.getText().toString().trim();

        if(email==null || email.trim().isEmpty())
        {
            return "";
        }

        if(!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") )
        {
            editEmail.setTextColor(context.getResources().getColor(R.color.purple));
            return "";
        }

        editEmail.setTextColor(context.getResources().getColor(R.color.greytext));
        return email;
    }

    public class Response
    {
        private boolean result;

        public boolean isValid() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        private String name;
        private String number;
        private String email;

    }

}
