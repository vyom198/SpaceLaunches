package com.example.spacelaunches.presentation.states.viewmodel

import androidx.compose.foundation.text.selection.DisableSelection
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacelaunches.domain.usecases.CancelReminderUsecase
import com.example.spacelaunches.domain.usecases.GetRemindersUsecase
import com.example.spacelaunches.presentation.states.ReminderState
import com.example.spacelaunches.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ReminderViewmodel @Inject constructor(
    private  val cancelReminderUsecase: CancelReminderUsecase,
    private val  getRemindersUsecase: GetRemindersUsecase
):ViewModel(){
    private val _reminderState = MutableStateFlow(ReminderState())
    val reminderState = _reminderState.asStateFlow()

    private val _eventFlow: MutableSharedFlow<RemindersScreenEvent> = MutableSharedFlow()
    val eventFlow = _eventFlow.asSharedFlow()
init {
    getReminders()
}

  private  fun getReminders()  {
    viewModelScope.launch {
        _reminderState.update { it.copy(isLoading = true) }
         getRemindersUsecase()
            .catch { throwable ->
               _reminderState.update {
                    it.copy(
                        isLoading = false,
                        error = throwable.localizedMessage
                    )
                }
            }
            .collect { reminderList->
                _reminderState.update {
                    it.copy(
                       data = reminderList,
                        isLoading = false,
                        error = null
                    )
                }
            }
    }
}


    fun cancelReminders ( id :String){
        viewModelScope.launch(Dispatchers.IO) {

            cancelReminderUsecase(id).let { result->
                when(result){
                  is Resource.Error -> {
                        _eventFlow.emit(
                            RemindersScreenEvent.ReminderNotCancelled(
                                result.message.toString()
                            )
                        )

                    }

                    is Resource.Success -> {
                        _eventFlow.emit(RemindersScreenEvent.ReminderCancelled)
                    }

                }
            }
        }

    }

}
sealed class RemindersScreenEvent(
    val infoMessage: String? = null
) {
    object ReminderCancelled : RemindersScreenEvent()
    data class ReminderNotCancelled(val errorMessage: String) : RemindersScreenEvent(errorMessage)
}
