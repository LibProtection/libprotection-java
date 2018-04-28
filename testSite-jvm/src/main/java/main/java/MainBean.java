package main.java;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.File;
import java.nio.file.Files;
import java.sql.*;
import java.text.MessageFormat;
import java.util.*;

import org.libprotection.injections.*;
import org.libprotection.injections.languages.LanguageProvider;
import org.libprotection.injections.languages.filepath.FilePath;
import org.libprotection.injections.languages.html.Html;
import org.libprotection.injections.languages.javascript.JavaScript;
import org.libprotection.injections.languages.sql.Sql;
import org.libprotection.injections.languages.url.Url;
import org.sqlite.SQLiteConfig;

@ManagedBean (name="Main")
@RequestScoped
public class MainBean
{
    private static final Map<String, Example> examples;

    static {
        examples = new HashMap<>();
        examples.put("Html",
                new Example(
                        "Renders the given HTML markup on the client side.",
                        (format, parameters) -> MainBean.formatHelper(Html.INSTANCE, format, parameters),
                        "<a href=''{0}'' onclick=''alert(\"{1}\");return false''>{2}</a>",
                        "Default.aspx\r\nHello from embedded JavaScript code!\r\nThis site's home page",
                        s -> s));

        examples.put("JavaScript",
                new Example(
                        "Executes the given JavaScript code on the client side.",
                        (format, parameters) -> MainBean.formatHelper(JavaScript.INSTANCE, format, parameters),
                        "operationResult.innerText = ''{0}'';",
                        "Hello from internal JavaScript code!",
                        s -> String.format("<script>\r\n{%s}\r\n</script>", s)));

        examples.put("Sql",
                new Example(
                        "Executes the given SQL query on the sever side and outputs its results.",
                        (format, parameters) -> MainBean.formatHelper(Sql.INSTANCE, format, parameters),
                        "SELECT * FROM myTable WHERE id = {0} AND myColumn = ''{1}''",
                        "1\r\nvalue1",
                        MainBean::sqlRequestTagBuilder));

        examples.put("Url",
                new Example(
                        "Uses the given URL on the client side to load and execute an external JavaScript code.",
                        (format, parameters) -> MainBean.formatHelper(Url.INSTANCE, format, parameters),
                        "{0}/{1}",
                        "Assets\r\njsFile.js",
                        s -> String.format("<script src=\"%s\"></script>", s)));

        examples.put("FilePath",
                new Example(
                        "Reads content of the given local file on the server side and outputs it.",
                        (format, parameters) -> MainBean.formatHelper(FilePath.INSTANCE, format, parameters),
                        String.format("%s%s{0}", getApplicationAssetsFile().getPath(), File.separator),
                        "textFile.txt",
                        s -> new String(Files.readAllBytes(new File(s).toPath()))));
    }

    //helpers and builders
    private static String formatHelper(LanguageProvider provider, String format, Object[] parameters) throws AttackDetectedException
    {
        return SafeString.format(provider, format, parameters);
    }

    private static File getApplicationAssetsFile(){
        File file = new File(MainBean.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        while(file != null && file.listFiles() != null && !Arrays.stream(file.listFiles()).anyMatch(contentFile -> contentFile.getName().equals("Assets"))){
            file = file.getParentFile();
        }
        assert file != null;
        return new File(file, "Assets");
    }

    private static String sqlRequestTagBuilder(String request)
    {
        if (request == null || request.isEmpty()) { return ""; }

        String fileName = new File(getApplicationAssetsFile(), "Database.sqlite").getPath();
        String connectionString = MessageFormat.format("jdbc:sqlite:{0}", fileName);

        String result;

        try {

            SQLiteConfig config = new SQLiteConfig();
            //config.setReadOnly(true); it seems it does not work :(
            config.setUserVersion(3);

            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString, config.toProperties());
            assert conn.isValid(1000);

            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery(request);

            StringBuilder builder = new StringBuilder();
            while(rs.next()){
                builder.append(MessageFormat.format("Id: {0}, myColumn: ''{1}''<br>", rs.getString("Id"), rs.getString("myColumn")));
            }

            result = builder.toString();

            rs.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            result = e.toString();
        }

        return result;
    }

    private String id;
    private Map<String, String> requestParameters;
    private FormatResult cachedResult;

    private Map<String, String> getRequestParameters(){
        if(requestParameters != null) { return requestParameters; }
        ExternalContext fc = FacesContext.getCurrentInstance().getExternalContext();
        requestParameters = fc.getRequestParameterMap();
        return requestParameters;
    }

    public void onLoad(){
        ExternalContext fc = FacesContext.getCurrentInstance().getExternalContext();
        fc.addResponseHeader("X-XSS-Protection", "0");
    }

    public Set<String> getExampleKeys(){
        return examples.keySet();
    }

    public String getId(){
        if(id != null) { return id; }
        id = getRequestParameters().getOrDefault("Id", examples.keySet().iterator().next());
        return id;
    }

    public String getFormat(){
        String userDefinedFormat = getRequestParameters().getOrDefault("Format", null);
        return userDefinedFormat == null || userDefinedFormat.isEmpty() || getInputsAreDisabled() ? getCurrentExample().format : userDefinedFormat;
    }

    public String getParameters(){
        String userDefinedParameters = getRequestParameters().getOrDefault("Parameters", null);
        return userDefinedParameters == null ? getCurrentExample().parameters : userDefinedParameters;
    }

    public Example getCurrentExample(){
        return examples.get(getId());
    }

    public boolean getInputsAreDisabled(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        String disableArbitraryFormatFeature = ctx.getExternalContext().getInitParameter("DisableArbitraryFormatFeature");
        return "true".equals(disableArbitraryFormatFeature);
    }

    public int getYear(){
        return java.time.LocalDate.now().getYear();
    }

    public FormatResult getFormatResult(){

        if(cachedResult != null){ return cachedResult; }

        try{
            String[] parameters = getParameters().split("\\r?\\n");
            String formatResult = getCurrentExample().getFormatFunc().format(getFormat(), parameters);
            String operationResult = getCurrentExample().getTagBuilder().build(formatResult);
            cachedResult = new FormatResult(operationResult, formatResult, FormatResult.FormatStatus.Success);
        }catch (AttackDetectedException e){
            cachedResult = new FormatResult(e.getMessage(), null, FormatResult.FormatStatus.AttackDetected);
        }catch (Exception e){
            cachedResult = new FormatResult(e.toString(), null, FormatResult.FormatStatus.Exception);
        }
        return cachedResult;
    }
}
