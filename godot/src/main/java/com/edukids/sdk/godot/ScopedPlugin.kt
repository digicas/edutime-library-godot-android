package com.edukids.sdk.godot

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin

abstract class ScopedPlugin(godot: Godot) : GodotPlugin(godot) {

    protected val scope = CoroutineScope(Dispatchers.Main)

    override fun onMainDestroy() {
        super.onMainDestroy()
        scope.cancel()
    }

}