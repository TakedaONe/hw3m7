package com.projectx.homework3_7month.presentation.fragments.taskList

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.projectx.homework3_7month.domain.usecase.GetAllNotesUseCase
import com.projectx.homework3_7month.domain.usecase.GetTaskUseCase
import com.projectx.homework3_7month.domain.usecase.InsertTaskUseCase
import com.projectx.homework3_7month.domain.usecase.TaskDelete
import com.projectx.homework3_7month.domain.usecase.UpdateTaskUseCase
import com.projectx.homework3_7month.presentation.fragments.base.BaseViewModel
import com.projectx.homework3_7month.presentation.model.TaskUI
import com.projectx.homework3_7month.presentation.model.toDomain
import com.projectx.homework3_7month.presentation.model.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TaskViewModel(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val delete: TaskDelete,
    private val getTaskUseCase: GetTaskUseCase
): BaseViewModel() {

    private val _tasksStateFlow = MutableStateFlow<List<TaskUI>>(emptyList())
    val taskFlow: StateFlow<List<TaskUI>> = _tasksStateFlow.asStateFlow()


    fun fetchTask() {
        runLaunchIO {
            val task = getTaskUseCase(1)
            task?.let {
            }
        }
    }

    fun loadTasks() {
        runLaunchIO {
            getAllNotesUseCase().onEach { taskList ->
                _tasksStateFlow.value = taskList.map { it.toUi() }
            }.collect()
        }
    }


    fun deleteTask(taskUI: TaskUI) {
        viewModelScope.launch {
            try {
                delete.deleteTask(taskUI.toDomain())
                loadTasks()
            } catch (e: Exception) {
                Log.e("sydyman", "Ошибка : ${e.message}")
            }
        }
    }
}
