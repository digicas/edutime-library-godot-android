package cz.edukids.sdk.godot.signal

import org.godotengine.godot.plugin.SignalInfo

object EduSdkSignals {

    val timeConstraints = SignalInfo("time_constraints", Object::class.java)
    val timeConstraintsError = SignalInfo("time_constraints_error", Object::class.java)
    val categoryConstraints = SignalInfo("time_category_constraints", Object::class.java)
    val categoryConstraintsError = SignalInfo("time_category_constraints_error", Object::class.java)
    val currencyStats = SignalInfo("currency_stats", Object::class.java)
    val currencyStatsError = SignalInfo("currency_stats_error", Object::class.java)
    val skillLevel = SignalInfo("skill_level", Object::class.java)
    val skillLevelError = SignalInfo("skill_level_error", Object::class.java)
    val startMission = SignalInfo("start_mission", Object::class.java)
    val startMissionError = SignalInfo("start_mission_error", Object::class.java)

    fun collect() = setOf(
        timeConstraints,
        timeConstraintsError,
        categoryConstraints,
        categoryConstraintsError,
        currencyStats,
        currencyStatsError,
        skillLevel,
        skillLevelError,
        startMission,
        startMissionError
    )

}