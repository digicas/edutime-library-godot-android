package cz.edukids.sdk.godot

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import cz.edukids.sdk.EduSdk
import cz.edukids.sdk.EduSdkInstance
import cz.edukids.sdk.godot.signal.EduSdkSignals
import cz.edukids.sdk.model.EduMissionFinishParams
import cz.edukids.sdk.model.EduMissionStartParams
import cz.edukids.sdk.model.ScreenTimeCategorySuggestion
import kotlinx.coroutines.launch
import org.godotengine.godot.Godot

@Suppress(
    "unused", // used in manifest entry
    "MemberVisibilityCanBePrivate" // members are using reflection probably
)
class EduSdkGodotPlugin(godot: Godot) : ScopedPlugin(godot) {

    companion object {
        private const val TAG = "EduKidsSDK" // do not change, occurrence also in manifest!
    }

    private lateinit var instance: EduSdkInstance
    private val mission inline get() = instance.getMission()

    // --- Plugin init

    override fun onMainCreate(activity: Activity?): View? {

        EduSdk().runCatching { getNewInstance(activity?.intent ?: Intent()) }
            .onSuccess { instance = it }
            .onFailure { Log.e(TAG, "Edu Sdk cannot be initialized", it) }

        return super.onMainCreate(activity)
    }

    // --- Overrides

    override fun getPluginName() =
        TAG

    override fun getPluginMethods() = listOf(
        this::getTimeConstraints.name,
        this::getScreenTimeCategoryConstraints.name,
        this::getCurrencyStats.name,
        this::getSkillLevel.name,
        this::startMission.name,
        this::finishMission.name,
        this::completeMissions.name,
        this::suggestCorrectCategory.name
    )

    override fun getPluginSignals() = EduSdkSignals.collect()

    // --- EduSdkInstance async

    fun getTimeConstraints() {
        scope.launch {
            instance.getTimeConstraints().onSuccess {
                emitSignal(EduSdkSignals.timeConstraints.name, it.toDictionary())
            }.onFailure {
                emitSignal(EduSdkSignals.timeConstraintsError.name, it.toDictionary())
            }
        }
    }

    fun getScreenTimeCategoryConstraints() {
        scope.launch {
            instance.getScreenTimeCategoryConstraints().onSuccess {
                emitSignal(EduSdkSignals.categoryConstraints.name, it.toDictionary())
            }.onFailure {
                emitSignal(EduSdkSignals.categoryConstraintsError.name, it.toDictionary())
            }
        }
    }

    fun getCurrencyStats() {
        scope.launch {
            instance.getCurrencyStats().onSuccess {
                emitSignal(EduSdkSignals.currencyStats.name, it.toDictionary())
            }.onFailure {
                emitSignal(EduSdkSignals.currencyStatsError.name, it.toDictionary())
            }
        }
    }

    fun getSkillLevel() {
        scope.launch {
            instance.getSkillLevel().onSuccess {
                emitSignal(EduSdkSignals.skillLevel.name, it.toDictionary())
            }.onFailure {
                emitSignal(EduSdkSignals.skillLevelError.name, it.toDictionary())
            }
        }
    }

    // --- EduSdkInstance sync

    fun suggestCorrectCategory(id: String) {
        val suggestion = ScreenTimeCategorySuggestion(id)
        instance.suggestCorrectCategory(suggestion)
    }

    // --- EduSdkInstance mission

    fun startMission(
        isRetry: Boolean,
        skills: Array<String>,
        eduTaskType: String,
        disciplines: Array<String>?,
        dataBundle: String?
    ) {
        val startParams = EduMissionStartParams(
            isRetry = isRetry,
            skills = skills.toList(),
            eduTaskType = eduTaskType,
            disciplines = disciplines?.toList(),
            dataBundle = dataBundle
        )
        scope.launch {
            mission.start(startParams).onSuccess {
                emitSignal(EduSdkSignals.startMission.name, it.toDictionary())
            }.onFailure {
                emitSignal(EduSdkSignals.startMissionError.name, it.toDictionary())
            }
        }
    }

    fun finishMission(
        contractId: String,
        isSuccess: Boolean,
        pointsAcquired: Int,
        dataBundle: String?
    ) {
        val finishParams = EduMissionFinishParams(
            contractId = contractId,
            isSuccess = isSuccess,
            pointsAcquired = pointsAcquired,
            dataBundle = dataBundle

        )
        mission.finish(finishParams)
    }

    fun completeMissions() {
        mission.complete()
    }

}