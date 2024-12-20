package com.startup.histour.presentation.main.viewmodel

import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.domain.usecase.character.GetAllCharactersUseCase
import com.startup.histour.domain.usecase.member.ChangeCharacterOfUserUseCase
import com.startup.histour.domain.usecase.member.GetMyUserDataUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.main.model.CharacterViewEvent
import com.startup.histour.presentation.main.model.CharacterViewState
import com.startup.histour.presentation.main.model.CharacterViewStateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    getAllCharactersUseCase: GetAllCharactersUseCase,
    private val changeCharacterOfUserUseCase: ChangeCharacterOfUserUseCase,
    private val getMyUserDataUseCase: GetMyUserDataUseCase
) : BaseViewModel() {
    init {
        getAllCharactersUseCase.executeOnViewModel(
            onMap = { dto ->
                dto.characterResponses?.map { it.toCharacter() } ?: emptyList()
            },
            onEach = { characters ->
                _state.characterList.update { characters }
            },
            onError = {
            }
        )
    }

    fun selectCharacter(characterId: Int, isFirst: Boolean) {
        changeCharacterOfUserUseCase.executeOnViewModel(
            params = characterId,
            onEach = {
                getMyUserDataUseCase.executeOnViewModel(
                    onEach = {
                        notifyEvent(if (isFirst) CharacterViewEvent.SuccessChangedCharacterAndPlaceSelect else CharacterViewEvent.SuccessChangedCharacter)
                    },
                    onError = {}
                )
            },
            onError = {}
        )
    }

    private val _state = CharacterViewStateImpl()
    override val state: CharacterViewState = _state
}