package main.java;

import java.util.function.Function;

public class Example
{
    public Example(String operation, FormatterFunction formatFunc, String format, String parameters, TagBuilderFunction tagBuilder){
        this.operation = operation;
        this.formatFunc = formatFunc;
        this.format = format;
        this.parameters = parameters;
        this.tagBuilder = tagBuilder;
    }

    final String operation;
    final FormatterFunction formatFunc;
    final String format;
    final String parameters;
    final TagBuilderFunction tagBuilder;

    public String getOperation() {
        return operation;
    }

    public FormatterFunction getFormatFunc() {
        return formatFunc;
    }

    public String getFormat() {
        return format;
    }

    public String getParameters() {
        return parameters;
    }

    public TagBuilderFunction getTagBuilder() {
        return tagBuilder;
    }
}