actual fun encodeHtmlText(text : String) = org.owasp.encoder.Encode.forHtml(text)

actual fun encodeHtmlAttribute(text : String) = org.owasp.encoder.Encode.forHtmlAttribute(text)

actual fun encodeJavaScript(text : String) = org.owasp.encoder.Encode.forJavaScript(text)

actual fun encodeUriComponent(text : String) = org.owasp.encoder.Encode.forUriComponent(text)