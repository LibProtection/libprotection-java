import com.google.gson.*;
import org.libprotection.injections.FormatResult;
import org.libprotection.injections.languages.LanguageProvider;
import org.libprotection.injections.languages.Token;
import org.libprotection.injections.languages.TokenType;
import org.libprotection.injections.languages.filepath.FilePath;
import org.libprotection.injections.languages.html.Html;
import org.libprotection.injections.languages.javascript.JavaScript;
import org.libprotection.injections.languages.sql.Sql;
import org.libprotection.injections.languages.url.Url;

import java.lang.reflect.Type;

final class TestCase
{
    /***
     * name of test case
     */
    public String Name;
    /***
     * name of language provider
     */
    public String LanguageProvider;
    /***
     * format string template
     */
    public String Format;
    /***
     * format string arguments
     */
    public String[] Arguments;
    /***
     * expected format result (should be null for new test cases)
     */
    public FormatResult Result;

    public static LanguageProvider getProviderByString(String languageProvider) throws IllegalArgumentException{
        LanguageProvider languageProviderInstance;
        switch (languageProvider){
            case "Html": languageProviderInstance = Html.INSTANCE; break;
            case "JavaScript": languageProviderInstance = JavaScript.INSTANCE; break;
            case "FilePath": languageProviderInstance = FilePath.INSTANCE; break;
            case "Sql": languageProviderInstance = Sql.INSTANCE; break;
            case "Url": languageProviderInstance = Url.INSTANCE; break;

            default: throw new IllegalArgumentException(String.format("Unknown language provider %s", languageProvider));
        }
        return languageProviderInstance;
    }

    public static TestCase deserializeTestCase(String json) throws JsonParseException{
        JsonDeserializer<FormatResult> deserializer = new JsonDeserializer<FormatResult>() {

            private Token deserializeToken(JsonObject jsonTokenObject) throws JsonParseException
            {
                boolean isTrivial = jsonTokenObject.get("IsTrivial").getAsBoolean();
                String text = jsonTokenObject.get("Text").getAsString();
                String languageProvider = jsonTokenObject.get("LanguageProvider").getAsString();
                String typeName = jsonTokenObject.get("TypeName").getAsString();
                String typeValue = jsonTokenObject.get("TypeValue").getAsString();
                int rangeLowerBound = jsonTokenObject.get("RangeLowerBound").getAsInt();
                int rangeUpperBound = jsonTokenObject.get("RangeUpperBound").getAsInt();

                Class typeClass;
                switch (typeName){
                    case "HtmlTokenType": typeClass = org.libprotection.injections.languages.html.HtmlTokenType.class; break;
                    case "JavaScriptTokenType": typeClass = org.libprotection.injections.languages.javascript.JavaScriptTokenType.class; break;
                    case "FilePathTokenType": typeClass = org.libprotection.injections.languages.filepath.FilePathTokenType.class; break;
                    case "SqlTokenType": typeClass = org.libprotection.injections.languages.sql.SqlTokenType.class; break;
                    case "UrlTokenType": typeClass = org.libprotection.injections.languages.url.UrlTokenType.class; break;
                    default: throw new JsonParseException(String.format("Invalid token type %s", typeName));
                }
                TokenType tokenType = (TokenType)Enum.valueOf(typeClass, typeValue);

                LanguageProvider languageProviderInstance;
                try {
                    languageProviderInstance = getProviderByString(languageProvider);
                }catch (IllegalArgumentException e){
                    throw new JsonParseException(String.format("Invalid token language provider %s", languageProvider));
                }
                return new Token(languageProviderInstance, tokenType, rangeLowerBound, rangeUpperBound, text, isTrivial);
            }

            @Override
            public FormatResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                JsonObject jsonObject = json.getAsJsonObject();

                JsonArray jsonTokens = jsonObject.get("Tokens").getAsJsonArray();

                Token[] tokens = new Token[jsonTokens.size()];

                for(int i = 0; i < jsonTokens.size(); i++){
                    tokens[i] = deserializeToken(jsonTokens.get(i).getAsJsonObject());
                }

                boolean isAttackDetected = jsonObject.get("IsAttackDetected").getAsBoolean();
                if(!isAttackDetected){
                    return FormatResult.Companion.success(tokens, jsonObject.get("FormattedString").getAsString());
                }else{
                    return FormatResult.Companion.fail(tokens, jsonObject.get("InjectionPointIndex").getAsInt());
                }
            }
        };

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(FormatResult.class, deserializer);
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, TestCase.class);
    }
}