package main.java;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.libprotection.injections.*;
import org.libprotection.injections.languages.html.Html;

import java.text.MessageFormat;

@ManagedBean (name="Main")
@SessionScoped
public class MainBean
{
    private String format = "<a href='{0}' onclick='alert(\"{1}\");return false'>{2}</a>";
    private String arguments = "Default.aspx\n" +
            "Hello from embedded JavaScript code!\n" +
            "This site's home page";

    private String[] getArgumentsArray(){
        return getArguments().split(System.getProperty("line.separator"));
    }

    public String getSafeString() throws AttackDetectedException{
        return SafeString.format(Html.INSTANCE, getFormat(), (Object[])getArgumentsArray());
    }

    public String getUnsafeString(){
        return new MessageFormat(getFormat().replace("'", "''")).format(getArgumentsArray());
    }

    public String getFormat(){
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getArguments() {
        return this.arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    /*
    public String checkLogin(){
        if(login.equalsIgnoreCase("alex") && password.equalsIgnoreCase("qwerty")){
	        return "loginsuccess?faces-redirect=true";
        } else {
	        return "loginfailed?faces-redirect=true";
	    }
    }
*/
}
