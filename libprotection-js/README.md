# LibProtection.JS

## Disclaimer

This project is in the state of early beta. It is stable enough for the public testing, but should be used at the production environments only at your own risk.

**Libprotection.JS** is a JS version of LibProtection library — an alternative implementation of the standard functionality of the formatted and interpolated strings. It provides a realtime automatic protection from any class of the injection attacks, which belong to the most attacked languages (HTML, URL, JavaScript, SQL and the file paths are currently supported).

<!-- fix to JS!
| Windows Build Status |
|---|
|[![Build status](https://ci.appveyor.com/api/projects/status/8y9t294ypeuu18rs/branch/dev?svg=true)](https://ci.appveyor.com/project/libprotection/libprotection-dotnet/branch/dev)|
-->

## How it works

The library considers each placeholder in a processed interpolated or format string as a potentially injection point. It performs the following actions in each of these points:

1. It decides a grammatical context of the possible injection (taking into account the island grammars, if necessary).
2. If sanitization rules are defined for the given context, then it sanitizes data, which belongs to the placeholder. Otherwise, data inserts as is.
3. It performs tokenization of the input data and counting an amount of tokens. If it exceeds 1, then an attack is reported (by throwing an exception or returning a false value, depending on a used library method).

## Quick example

The following code is vulnerable to injection attacks at three different points (provided that variables a, b and c contain values derived from the input data):

```
res.write(sf("<a href=\'{0}\' onclick=\'alert(\"{1}\");return false\''>{2}</a>", a, b, c));
```

Assume that the attacker passed the following values to the variables a, b and c:

a = ``'onmouseover='alert(`XSS`)``
b = ``");alert(`XSS`)``
c = ``<script>alert(`XSS`)</script>``

After interpolation, the resulting string will look like this:

``<a href=''onmouseover='alert(`XSS`)' onclick='alert("");alert(`XSS`)");return false'><script>alert(`XSS`)</script></a>``

Thus, the attacker has the ability to implement the XSS attack by three different ways. However, after trivial wrapping the interpolated string in the LibProtection API call, this code becomes absolutely protected:

```
res.write(LP.format(LP.Html, "<a href=\'{0}\' onclick=\'alert(\"{1}\");return false\'>{2}</a>", a, b, c));
```

In this case, after interpolation, the resulting string will look like this:

``<a href='Default.aspx' onclick='alert("Hello&#92;x20from&#92;x20embedded&#92;x20JavaScript&#92;x20code&#92;x21");return false'>This site&#39;s home page</a>``

<!-- fix to JS!
## Try it online

A test site that imitates a vulnerable application protected by the library is available [here](http://playground.libprotection.org/).
-->