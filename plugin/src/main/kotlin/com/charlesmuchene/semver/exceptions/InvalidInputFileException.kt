package com.charlesmuchene.semver.exceptions

class InvalidInputFileException(filename: String) :
    IllegalArgumentException("Invalid input file provided: $filename")