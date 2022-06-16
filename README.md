# MintPermissions

[![](https://jitpack.io/v/mintrocket/MintPermissions.svg)](https://jitpack.io/#mintrocket/MintPermissions)

This library, using [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html), provides an easy way to interact with [Android Runtime Permissions](https://developer.android.com/training/permissions/requesting)

- Only Coroutines and AndroidX
- Designed for using in ViewModel/Presenter/etc
- Observe permissions statuses changes by [Coroutines Flow](https://kotlinlang.org/docs/flow.html)
- Easy handle multiple permissions requests
- No hurt with lifecycle, rotating, [DKA](https://proandroiddev.com/dont-keep-activities-d0afdf2c2d47) - just working in CoroutineScope
- No callbacks
- No additional activities
- Request queue - everything will be ok, even if you make several requests from different CoroutineScope at the same time

## Setup

```gradle
// Root build.gradle:
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}

// Target module's build.gradle:
dependencies {
    implementation "com.github.mintrocket:MintPermissions:0.0.1"
}
```

## Compatibility

**Android SDK**: Minimum API level is **21**\
**AndroidX**: this library requires [AndroidX](https://developer.android.com/jetpack/androidx/versions/)\
**Coroutines**: this library requires [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html)

## Samples

To get started, you can install Example and explore its source code

### Initializing

By default library automatically working with activities. Just add this in your Application.

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initMintPermissions()
    }
}
```

If you want manually working with activities, add config. But you should add ```initMintPermissionsManager()``` in every activities where permissions might be needed

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initMintPermissions(MintPermissionsConfig(autoInitManagers = false))
    }
}

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMintPermissionsManager()
    }
}
```

If you love and respect DI, you also can do something like that ([Koin](https://insert-koin.io/) for example)

```kotlin
val libraryModule = module {
    single { MintPermissions.controller }
    factory { MintPermissions.createManager() }
}
```

### Request example

```kotlin
class SampleViewModel(
    private val permissionsController: MintPermissionsController
) : ViewModel() {

    companion object {
        private val cameraPermissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )
    }

    // Update ui by permissions changes
    val statuses = permissionsController.observe(cameraPermissions)

    fun onActionClick() {
        viewModelScope.launch {
            val result = permissionsController.request(cameraPermissions)
            if (result.isAllGranted()) {
                // handle granted
                return@launch
            }
            val denied = result.filterDenied()
            val needsRationale = result.filterNeedsRationale()
        }
    }
}
```

## Additional info

### MintPermissionStatus

```
Library can detect these statuses
- Granted - when permission granted
- Denied - when "never requested" and "permanently denied"
- NeedsRationale - when needs rationale
- NotFound - when not found in declared permissions in app manifest
```

But in some other libraries, you might see statuses like "cancelled, denied permanently, just denied, etc." - this is impossible to detect.\
In other libraries, they combine Status and Action, which is formed when Status changes.\
In this library, these concepts are separated.

### MintPermissionAction

```
Available only in MintPermissionResult. Library can detect these actions
- Granted - when status changed "not granted" -> "granted"
- NeedsRationale - when status changed "not needs rationale" -> "needs rationale"
- DeniedPermanently - when status changed "not denied" -> "denied"
```

### MintPermissionResult

```
- status - permission status
- nullable action - action that appears if the status before and after does not match 
```

### MintPermissionsController

```
- fun observe(permission: MintPermission) - observe single permission, returns single status
- fun observe(permissions: List<MintPermission>) - observe multiple permissions, returns list of status
- fun observeAll() - observe all declared permissions, returns list of status

- fun get(permission: MintPermission) - get single permission, returns single status
- fun get(permissions: List<MintPermission>) - get multiple permissions, returns list of status
- fun getAll() - get all declared permissions, returns list of status

- fun request(permission: MintPermission) - request single permission, returns single result
- fun request(permissions: List<MintPermission>) - request single permission, returns list of result
```

If you pass non-existent or undeclared permissions, the library will return MintPermissionStatus.NotFound.\
All observe* and get* methods returns "cached" information about permissions statuses. Values are updated on focus change and onResume in activity.

### Extensions

There are more than 40 useful Extensions for working with Status, Action, Result - [Source Code](https://github.com/mintrocket/MintPermissions/tree/main/library/src/main/java/ru/mintrocket/lib/mintpermissions/ext)

## Changelog

Be sure to review the [changes list](https://github.com/mintrocket/MintPermissions/releases) before updating the version

## Contributing

If you find any bug, or you have suggestions, don't be shy to create [issues](https://github.com/mintrocket/MintPermissions/issues) or make a [PRs](https://github.com/mintrocket/MintPermissions/pulls) in the `develop` branch.
