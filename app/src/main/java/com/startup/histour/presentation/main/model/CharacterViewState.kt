package com.startup.histour.presentation.main.model

import com.startup.histour.presentation.base.BaseEvent
import com.startup.histour.presentation.base.State
import com.startup.histour.presentation.model.CharacterModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CharacterViewStateImpl : CharacterViewState {
    override val characterList = MutableStateFlow<List<CharacterModel>>(emptyList())
    override val currentCharacter = MutableStateFlow<CharacterModel>(CharacterModel.orEmpty())
}

interface CharacterViewState : State {
    val characterList: StateFlow<List<CharacterModel>>
    val currentCharacter : StateFlow<CharacterModel>
}

sealed interface CharacterViewEvent : BaseEvent {
    data object SuccessChangedCharacter : CharacterViewEvent
    data object SuccessChangedCharacterAndPlaceSelect : CharacterViewEvent
}