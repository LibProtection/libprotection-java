package org.libprotection.injections

class AttackDetectedException : Exception()

class LanguageNotSupportedException(message : String) : Exception(message)

class InvalidArgumentException(exceptionMessage : String, val arguments : Array<String?>? = null) : Exception(exceptionMessage)