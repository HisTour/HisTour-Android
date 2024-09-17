package com.startup.histour.presentation.mission.viewmodel

import androidx.lifecycle.viewModelScope
import com.startup.histour.data.dto.mission.RequestQuizGrade
import com.startup.histour.domain.usecase.mission.GetMissionOfQuizUseCase
import com.startup.histour.domain.usecase.mission.GradeQuizUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.mission.util.MissionValues
import com.startup.histour.presentation.mission.util.MissionValues.INTRO_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskMissionViewModel @Inject constructor(
    private val getMissionOfQuizUseCase: GetMissionOfQuizUseCase,
    private val gradeQuizUseCase: GradeQuizUseCase
) : BaseViewModel() {

    private val _state = TaskMissionStateImpl()
    override val state: TaskMissionState = _state

    private val _clearedQuizCount = MutableStateFlow(0)
    val clearedQuizCount: StateFlow<Int> = _clearedQuizCount.asStateFlow()

    fun initClearedQuizCount(initialCount: Int) {
        _clearedQuizCount.value = initialCount
    }

    fun getTasks(missionId: Int) {
        getMissionOfQuizUseCase.executeOnViewModel(
            params = missionId.toString(),
            onMap = {
                val submissionName = it.missionName ?: ""
                val missions = it.quizzes ?: listOf()
                val subMissionType = it.missionType ?: INTRO_TYPE
                _state.subMissionName.update { submissionName }
                _state.quizzesList.update { missions }
                _state.subMissionType.update { subMissionType }
            },
            onEach = { it ->
            },
            onError = {
            }
        )
    }

    fun showAlreadyAnsweredToast() {
        viewModelScope.launch {
            notifyEvent(
                TaskMissionEvent.ShowToast(
                    "완료한 미션은 수정할 수 없어요.\n" +
                            "미션 카드를 넘겨 다음 미션을 시작해 볼까요?"
                )
            )
        }
    }

    fun checkAnswer(isLast: Boolean, taskId: Int, answer: String = "") {
        gradeQuizUseCase.executeOnViewModel(
            params = RequestQuizGrade(quizId = taskId, memberAnswer = answer, isLastTask = isLast),
            onMap = { response ->
                //TODO 중복일 경우 증가 시키면 안됨
                if (response.isCorrect) {
                    _clearedQuizCount.value++

                    // 서버에서 해당 값이 null로 오냐 안오냐로 판단
                    if (response.clearedMissionCount != null) {
                        viewModelScope.launch {
                            val clearType = when (response.clearedMissionCount + 1) {
                                // 다음 미션이 파이널만 남은 상황
                                response.requireMissionCount - 1 -> {
                                    MissionValues.LAST_SUBMISSION
                                }
                                // 지금 미션이 파이널인 상황
                                response.requireMissionCount -> {
                                    MissionValues.FINAL_MISSION
                                }
                                // 그냥 다음 미션
                                else -> {
                                    MissionValues.SUBMISSION
                                }
                            }
                            notifyEvent(
                                TaskMissionEvent.MoveToClearScreen(
                                    clearType = clearType,
                                    subMissionType = _state.subMissionType.value,
                                    requiredCount = response.requireMissionCount,
                                    completedCount = response.clearedMissionCount
                                )
                            )
                            return@launch
                        }
                    } else {
                        return@executeOnViewModel
                    }
                } else {
                    viewModelScope.launch {
                        notifyEvent(TaskMissionEvent.ShowToast("정답이 아니예요! 힌트를 눌러 정답을 확인해 볼까요?"))
                        return@launch
                    }
                }

            },
            onEach = { it ->
            },
            onError = {

            }
        )
    }
}