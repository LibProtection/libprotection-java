import com.ptsecurity.libprotection.injections.languages.filepath.FilePath
import com.ptsecurity.libprotection.injections.languages.html.Html
import com.ptsecurity.libprotection.injections.languages.javascript.JavaScript
import com.ptsecurity.libprotection.injections.languages.sql.Sql
import com.ptsecurity.libprotection.injections.languages.url.Url
import org.apache.commons.io.input.BOMInputStream
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.BufferedReader
import java.io.File
import kotlin.streams.toList

class TokenizationTests {
    @Test
    fun `general filepath`() {
        doTest("filepath", "general.filepath")
    }

    @Test
    fun `general html`() {
        doTest("html", "general.html")
    }

    @Test
    fun `general javascript`() {
        doTest("javascript", "general.javascript")
    }

    @Test
    fun `general sql`() {
        doTest("sql", "general.sql")
    }

    @Test
    fun `general url`() {
        doTest("url", "general.url")
    }

    @Test
    fun `ntfs-attributes filepath`() {
        doTest("filepath", "ntfs-attributes.filepath")
    }

    @Test
    fun `nullbyte filepath`() {
        doTest("filepath", "nullbyte.filepath")
    }

    @Test
    fun `slashes filepath`() {
        doTest("filepath", "slashes.filepath")
    }

    @Test
    fun `traversal filepath`() {
        doTest("filepath", "traversal.filepath")
    }

    @Test
    fun `unc filepath`() {
        doTest("filepath", "unc.filepath")
    }

    @Test
    fun `unclosed-quotes html`() {
        doTest("html", "unclosed-quotes.html")
    }

    @Test
    fun `unclosed-tag html`() {
        doTest("html", "unclosed-tag.html")
    }

    private fun doTest(languageName: String, caseFileName: String) {
        val caseText = readFile(caseFileName).readText()

        val languageProvider = when (languageName) {
            "html" -> Html
            "javascript" -> JavaScript
            "filepath" -> FilePath
            "sql" -> Sql
            "url" -> Url
            else -> throw IllegalArgumentException("Language not supported $languageName")
        }

        val tokens = languageProvider.tokenize(caseText)
        val obtainedTokens = tokens.map { token ->
            val obtainedText = caseText.substring(
                    token.range.lowerBound,
                    token.range.upperBound
            )
            if (obtainedText != token.text) throw RuntimeException("Expected at ${token.range}: ${token.text}, obtained: $obtainedText")
            "${token.languageProvider.name}:$token".replace("\r", "\\r").replace("\n", "\\n")
        }

        val expectedTokens = readFile("$caseFileName.tokens").lines().toList()

        assertEquals(expectedTokens, obtainedTokens)
    }

    private fun readFile(caseFileName: String): BufferedReader {
        val inputStream = BOMInputStream(this.javaClass.classLoader.getResourceAsStream("tokenization-test-cases/$caseFileName"))
        return inputStream.bufferedReader()
    }

}