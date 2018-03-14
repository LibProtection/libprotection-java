package main.java;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean (name="Main")
@SessionScoped
public class MainBean
{
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
	}

	public String getPassword() {
	    return password;
	}

	public void setPassword(String password) {
	    this.password = password;
	}
 
    public String checkLogin(){
        if(login.equalsIgnoreCase("alex") && password.equalsIgnoreCase("qwerty")){
	        return "loginsuccess?faces-redirect=true";
        } else {
	        return "loginfailed?faces-redirect=true";
	    }
    }
}
