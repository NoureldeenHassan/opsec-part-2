package com.birdhunt

import android.R.integer

private var aName = ArrayList<String>()//array Decarations
private var aPassword = ArrayList<String>()
private var newUseername = ""
private var checkName : Boolean = false
private var checkPassword : Boolean = false
private var strErrorMessage = "both of the credentials are wrong"
private var username = "itsESKIIII"

class Login {

    fun AddUser(Name: String, Password: String) //Adding user to array
    {
        aName.add(Name)
        aPassword.add(Password)

    }

    fun CheckUser(Name: String, Password: String): Boolean //checking user Login
    {
        var check = false
        for (i in 0..aName.size - 1) {
            if (aName[i].equals(Name)) {
                checkName = true
                if (aPassword[i].equals(Password)) {
                    checkPassword = true
                }
            }
            if (aPassword[i].equals(Password)) {
                checkPassword = true
                if (aName[i].equals(Name)) {
                    checkName = true
                }
            }
            if ((aPassword[i].equals(Password)) && (aName[i].equals(Name))) {
                check = true
            }
        }
        return check
    }

    fun ErrorMessage(): String {

        if (checkName == false && checkPassword == true) {
            strErrorMessage = "Incorrect Username"
        } else if (checkName == true && checkPassword == false) {
            strErrorMessage = "Incorrect Password"
        }

        return strErrorMessage
    }

    fun setCheck(bFalse: Boolean) {
        checkName = bFalse
        checkPassword = bFalse
    }

    fun newDetails(newUserName :String,newPassword: String) {

        for (i in 0..aName.size - 1) {
            if (aName[i].equals(getUsername())) {
                aName[i] = newUserName
                tPassword(newPassword,newUserName,i)}
        }


    }

    fun tPassword(newPassword: String,user: String,i : Int): ArrayList<String> {

        aPassword[i]= newPassword
        return aPassword
    }

    fun getUsername(): String {
        return username;
    }

    fun setUsername(Name: String) {
        username = Name;
    }

}