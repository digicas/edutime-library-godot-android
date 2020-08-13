package com.edukids.sdk.godot.dictionary

import com.edukids.sdk.model.*
import org.godotengine.godot.Dictionary
import java.util.*
import kotlin.reflect.KProperty0

fun TimeConstraints.toDictionary() = Dictionary().also {
    it += this::startTimestamp
    it += this::stopTimestamp
}

fun ScreenTimeCategoryConstraints.toDictionary() = Dictionary().also {
    it += this::assignedCategory to assignedCategory.toDictionary()
    it += this::availableCategories to availableCategories.map { it.toDictionary() }
    it += this::currentCategory to currentCategory.toDictionary()
}

fun ScreenTimeCategory.toDictionary() = Dictionary().also {
    it += this::description
    it += this::id
    it += this::isLocked
    it += this::isSelected
    it += this::name
}

fun CurrencyStats.toDictionary() = Dictionary().also {
    it += this::currentAmount
    it += this::earnedInInstance
}

fun SkillLevel.toDictionary() = Dictionary().also {
    it += this::firstTry to firstTry.toDictionary()
    it += this::retry to retry.toDictionary()
}

fun SkillSet.toDictionary() = Dictionary().also {
    fun Map.Entry<String, Int>.mapToString() = "${key}=${value}"

    it += this::failed to failed.map { it.mapToString() }
    it += this::succeeded to succeeded.map { it.mapToString() }
}

fun EduMissionContract.toDictionary() = Dictionary().also {
    it += this::id
}

fun Throwable.toDictionary() = Dictionary().also {
    it["message"] = message
    when (this) {
        is EduError -> it += this::code
    }
}

// --- Helpers

private val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()

private fun String.camelToSnakeCase() = camelRegex
    .replace(this) { "_${it.value}" }
    .toLowerCase(Locale.getDefault())

@JvmName("putNumber")
private operator fun <T : Number> Dictionary.plusAssign(property: KProperty0<T>) {
    this[property.name.camelToSnakeCase()] = property.get()
}

@JvmName("putString")
private operator fun <T : CharSequence> Dictionary.plusAssign(property: KProperty0<T>) {
    this[property.name.camelToSnakeCase()] = property.get()
}

@JvmName("putStringArray")
private operator fun <T : CharSequence> Dictionary.plusAssign(pair: Pair<KProperty0<Any>, List<T>>) {
    this[pair.first.name.camelToSnakeCase()] = pair.second
}

@JvmName("putBoolean")
private operator fun Dictionary.plusAssign(property: KProperty0<Boolean>) {
    this[property.name.camelToSnakeCase()] = property.get()
}

@JvmName("putAny")
private operator fun <T> Dictionary.plusAssign(pair: Pair<KProperty0<T>, Dictionary>) {
    this[pair.first.name.camelToSnakeCase()] = pair.second
}

@JvmName("putAnyArray")
private operator fun <T> Dictionary.plusAssign(pair: Pair<KProperty0<T>, List<Dictionary>>) {
    this[pair.first.name.camelToSnakeCase()] = pair.second
}