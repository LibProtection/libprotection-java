import org.apache.commons.io.FileUtils;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.libprotection.injections.FormatResult;
import org.libprotection.injections.SafeString;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Theories.class)
final public class FormatCasesTest {

    private static File getTestDirectory(){
        File currentDirectory  = new File(FormatCasesTest.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        while(Arrays.stream(Objects.requireNonNull(currentDirectory.listFiles())).noneMatch(it -> it.getName().equals("build.gradle"))){
            currentDirectory = currentDirectory.getParentFile();
        }
        return new File(new File(new File(new File(currentDirectory, "src"), "test"), "resources"), "format-test-cases");
    }

    private static TestCase[] getFormatCases() {

        File[] testFiles = getTestDirectory().listFiles();
        TestCase[] result = new TestCase[testFiles.length];

        for(int i = 0; i < testFiles.length; i++){
            try {
                String jsonContent = FileUtils.readFileToString(testFiles[i], java.nio.charset.Charset.defaultCharset());
                result[i] = TestCase.deserializeTestCase(jsonContent);
            } catch (IOException e) {
                fail(String.format("could not read file %s", testFiles[i].getAbsolutePath()));
            }
        }
        return result;
    }

    @DataPoints
    public static TestCase[] dataPointsNoAttack = getFormatCases();

    @Theory
    public void TestFormat(final TestCase testCase)
    {
        FormatResult formatResult = SafeString.Companion.formatEx(
                TestCase.getProviderByString(testCase.LanguageProvider),
                Locale.getDefault(Locale.Category.FORMAT),
                testCase.Format.replace("'", "''"),
                (Object[])testCase.Arguments);

        assertEquals(formatResult, testCase.Result);
    }
}
