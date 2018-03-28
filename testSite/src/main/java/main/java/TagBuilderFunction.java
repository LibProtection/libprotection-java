package main.java;

@FunctionalInterface
public interface TagBuilderFunction
{
    String build(String argument) throws Exception;
}
