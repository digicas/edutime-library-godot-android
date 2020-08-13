# Godot Android extension for [EduSdk](https://github.com/digicas/edutime-library-android)

## Installation

1) Download [EduKidsSDK.gdap](EduKidsSDK.gdap.sample)
2) Follow [official docs](https://docs.godotengine.org/en/stable/tutorials/plugins/android/android_plugin.html#loading-and-using-an-android-plugin) on how to import .gdap file

---

## Core References

### Available method declarations

See [EduSdkGodotPlugin](godot/src/main/java/com/edukids/sdk/godot/EduSdkGodotPlugin.kt) and search for `getPluginMethods`.

### Available signals

See [EduSdkSignals](godot/src/main/java/com/edukids/sdk/godot/signal/EduSdkSignals.kt).

If method returns `Object` then Godot most probably receives a `Dictionary`

### Data structures

Refer to the [mother repository](https://github.com/digicas/edutime-library-android/tree/master/sdk-model/src/main/java/com/edukids/sdk/model) for Android.
