# Storage

Simplify reading and writing files to external storage

## Installation

```
implementation 'com.graceens.foresee:storage:1.0.1'
```

## Usage

### Read or Write for cache

```kotlin
"test.txt".also { filename ->
    StorageWriter.write(getAppCachePath(filename), content)
    StorageWriter.write(getAppFilesPath(filename), content)

    Log.i(TAG, "from cache: ${StorageReader.read(getAppCachePath(filename))}")
    Log.i(TAG, "from files: ${StorageReader.read(getAppFilesPath(filename))}")
}
```

### Read or Write for media store

#### For Downloads

```kotlin
val content = "Hello world!"
"test.txt".also { filename ->
    MediaStoreWriter.write(
        this,
        MediaType.DOWNLOADS,
        "text/plain",
        filename = filename,
        content = content
    )
    Log.i(
        TAG,
        "from Downloads: ${
            MediaStoreReader.read(
                this,
                MediaType.DOWNLOADS,
                filename = filename
            )
        }"
    )
}
```

#### For Images

```kotlin
val content = "Hello world!"
"test.png".also { filename ->
    MediaStoreWriter.write(
        this,
        MediaType.IMAGES,
        "image/*",
        filename = filename,
        content = content
    )
    Log.i(
        TAG,
        "from Images: ${MediaStoreReader.read(this, MediaType.IMAGES, filename = filename)}"
    )
}
```

### License

```
Copyright 2023 Grace

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