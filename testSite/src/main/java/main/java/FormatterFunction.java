package main.java;

import org.libprotection.injections.AttackDetectedException;

@FunctionalInterface
public interface FormatterFunction
{
    String format(String formatter, String[] parameters) throws AttackDetectedException;
}