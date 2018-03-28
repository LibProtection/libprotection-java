package main.java;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="Template")
@RequestScoped
public class TemplateBean {

    boolean alreadyShowed = false;

    public Boolean getShowDisclaimer() {
        if(alreadyShowed) { return false; }
        alreadyShowed = true;
        ExternalContext fc = FacesContext.getCurrentInstance().getExternalContext();
        boolean result = fc.getRequestCookieMap().getOrDefault("showDisclaimer", "true") == "true";
        fc.addResponseCookie("showDisclaimer", "false", null);
        return result;
    }
}
