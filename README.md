# Adhoc Semver

A gradle plugin to bump a project's version.

## Structure

The plugin adds two tasks to the task graph:

* SemVerBumpTask - Bumps the version in the specified in _properties file_

* SemVerSetTask - Sets the given version in the configured properties file: defaults to version specified in _properties file_

## Usage

Apply the plugin:

```kotlin
plugins {
    ...
    id("com.charlesmuchene.temp.semver.bump")
}
```

Configure the plugin:

```kotlin
tempSemVerBump {
    versionFile = layout.projectDirectory.file("gradle.properties")
    targetTaskName = "publishToMavenLocal"
    bumpType = BumpType.PATCH
}
```

By default, the plugin is set configure the task dependency as follows:

* if the `maven-publish` plugin is applied to the project, run the tasks in this order: `SemVerBumpTask` > `<target-task-name>` > `SemVerSetTask`: (`targetTaskName` defaults to `publishToMavenLocal`)

* if the `maven-publish` plugin is not applied to the project, no task dependency is set.

> For more info on usage, checkout the [sample project](./sample).

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
