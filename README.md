# Adhoc Semver

A gradle plugin to bump a project's version.

## Structure

This plugin adds two tasks to the graph:

* BumpSemVerTask - Bumps the version in the configured properties file

* SetSemVerTask - Sets the given version in the configured properties file

## Usage

Configure the plugin:

```kotlin
adhocSemVer {
    versionFile = layout.projectDirectory.file("gradle.properties")
    shouldRevertVersionAfterExecution = true
    runBefore = <some-task>
    bumpType = BumpType.MAJOR
}
```

The tasks can be ran independenly or commonly configured to depend on existing tasks for instance a `publish` task.

> ./gradlew adhocSemVer
>
> or
>
> ./gradlew publish

## License

```markdown
    Copyright (c) 2024 Charles Muchene
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```
