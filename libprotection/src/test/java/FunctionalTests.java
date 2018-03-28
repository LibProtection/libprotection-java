import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.libprotection.injections.AttackDetectedException;
import org.libprotection.injections.SafeString;
import org.libprotection.injections.languages.LanguageProvider;
import org.libprotection.injections.languages.filepath.FilePath;
import org.libprotection.injections.languages.html.Html;
import org.libprotection.injections.languages.javascript.JavaScript;
import org.libprotection.injections.languages.sql.Sql;
import org.libprotection.injections.languages.url.Url;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class FunctionalTests {

    private static class DataPoint{
        LanguageProvider provider;
        String result;
        String format;
        Object[] arguments;

        boolean isAttack(){
            return result == null;
        }

        static DataPoint getDataPoint(LanguageProvider provider, String result, String format, Object... arguments){
            DataPoint point = new DataPoint();
            point.provider = provider;
            point.result = result;
            point.format = format;
            point.arguments = arguments;
            return point;
        }
    }

    @DataPoints
    public static DataPoint[] dataPointsNoAttack = new DataPoint[]{
            //Valid
            DataPoint.getDataPoint(Html.INSTANCE, "<a href='Default.aspx' onclick='alert(\"Hello from embedded JavaScript code!\");return false'>This site&#39;s home page</a>", "<a href=''{0}'' onclick=''alert(\"{1}\");return false''>{2}</a>", "Default.aspx", "Hello from embedded JavaScript code!", "This site's home page"),
            DataPoint.getDataPoint(JavaScript.INSTANCE, "operationResult.innerText = 'operationResult.innerText = \\x27Hello from internal JavaScript code!\\x27;';", "operationResult.innerText = ''{0}'';", "operationResult.innerText = 'Hello from internal JavaScript code!';"),
            DataPoint.getDataPoint(Sql.INSTANCE, "SELECT * FROM myTable WHERE id = 1 AND myColumn = 'value1'", "SELECT * FROM myTable WHERE id = {0} AND myColumn = ''{1}''",  1, "value1"),
            DataPoint.getDataPoint(Url.INSTANCE, "Assets/jsFile.js", "{0}/{1}", "Assets", "jsFile.js"),
            DataPoint.getDataPoint(FilePath.INSTANCE, "C:\\inetpub\\playground.libprotection.org\\Assets\\textFile.txt", "C:\\inetpub\\playground.libprotection.org\\Assets\\{0}", "textFile.txt"),
            //Attacks
            DataPoint.getDataPoint(Html.INSTANCE, null, "<a href={0} />", "<br>"),
            DataPoint.getDataPoint(JavaScript.INSTANCE, null, "operationResult.innerText = {0};", "' <br>"),
            DataPoint.getDataPoint(Sql.INSTANCE, null, "SELECT * FROM myTable WHERE id = {0}", "1 OR 1==1 --"),
            DataPoint.getDataPoint(Url.INSTANCE, null, "{0}/{1}", "../Asserts", "jsFile.js"),
            DataPoint.getDataPoint(FilePath.INSTANCE, null, "C:\\Assets\\{0}", "..\\jsFile.js"),
            //safe modifier
            DataPoint.getDataPoint(Html.INSTANCE, ":safe", ":safe"),
            DataPoint.getDataPoint(Html.INSTANCE, ":safe&lt;br&gt;", "{0}", ":safe<br>"),
            DataPoint.getDataPoint(Html.INSTANCE, "&lt;br&gt;xxx:safe", "{0}xxx:safe", "<br>"),
            DataPoint.getDataPoint(Html.INSTANCE, "<br>", "{0}:safe", "<br>"),
            DataPoint.getDataPoint(Html.INSTANCE, "<br>", "{0}:SaFe", "<br>"),
            DataPoint.getDataPoint(Html.INSTANCE, "<br>:safe", "{0}:safe:safe", "<br>"),
            DataPoint.getDataPoint(Html.INSTANCE, "<br>&lt;br&gt;", "{0}:safe{1}", "<br>", "<br>"),
            DataPoint.getDataPoint(Html.INSTANCE, "&lt;br&gt;<br>&lt;br&gt;", "{0}{1}:safe{2}", "<br>", "<br>", "<br>"),
    };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Theory
    public void FunctionalTest(final DataPoint dataPoint) throws AttackDetectedException{

        Optional<String> tryFormatResult = SafeString.tryFormat(dataPoint.provider, dataPoint.format, dataPoint.arguments);

        if(dataPoint.isAttack()){
            assertFalse(tryFormatResult.isPresent());

            thrown.expect(AttackDetectedException.class);
            SafeString.format(dataPoint.provider, dataPoint.format, dataPoint.arguments);
        }else{
            assertTrue(tryFormatResult.isPresent());
            assertEquals(tryFormatResult.get(), dataPoint.result);

            String formatResult = SafeString.format(dataPoint.provider, dataPoint.format, dataPoint.arguments);
            assertEquals(tryFormatResult.get(), formatResult);
        }
    }
}
