package com.charlesmuchene.semver.exceptions

class MissingVersionException(filename: String) :
    IllegalStateException("$filename does not contain a valid SemVer") {
}