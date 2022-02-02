# KMM Cinterop crash reproducer

[Youtrack issue](https://youtrack.jetbrains.com/issue/KT-50648)

- **CinteropRepro** - contains iOS Objective-C library project. This is the library cinterop will be working with.
- **CinteropShared** - Kotlin Multiplatform project. This is where iOS library `CinteropRepro` will be invoked from.
- **iOSApp** - iOS application project. Invokes `CinteropRepro` and `CinteropShared` iOS frameworks.

## CinteropRepro

[CinteropRepro.h](https://github.com/CityVibes/CinRepro/blob/main/CinteropRepro/CinteropRepro/CinteropRepro.h) header defines two functions that will be invoked either through `iOSApp` or `CinteropShared`:
- `subscribe` - returns typedef `Subscriber` causing a crash
- `subscribeSafe` - returns class called `ReturnType`, not causing any issues.

Both methods are otherwise identical.

## CinteropShared

[CinteropUseCase.kt](https://github.com/CityVibes/CinRepro/blob/main/CinteropShared/src/iosMain/kotlin/me/user/library/CinteropUseCase.kt) basically just wrapping functions from `CinteropRepro`:
- `subscribe`
- `subscribeSafe`

## iOSApp

[ViewController.m](https://github.com/CityVibes/CinRepro/blob/main/iOSApp/iOSApp/ViewController.m) calling into `CinteropShared` or `CinteropRepro` frameworks:
- [Invoking CinteropRepro directly](https://github.com/CityVibes/CinRepro/blob/main/iOSApp/iOSApp/ViewController.m#L17) - same function that would crash through KMM works fine invoked directly
- [KMM function call causing a crash](https://github.com/CityVibes/CinRepro/blob/main/iOSApp/iOSApp/ViewController.m#L26)
- [KMM function not causing a crash](https://github.com/CityVibes/CinRepro/blob/main/iOSApp/iOSApp/ViewController.m#L28)

Note: comment out the above function calls leaving only a single call to test which scenarios cause crash
