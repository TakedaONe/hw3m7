package com.projectx.homework3_7month.domain.repository

import com.projectx.homework3_7month.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskManagerRepository {

    suspend fun insertTask(taskModel: TaskModel): Result<Unit>
    suspend fun getAllNotes(): Flow<Result<List<TaskModel>>>
    suspend fun getTaskByName(taskName: String): Result<TaskModel?>
    suspend fun updateTask(taskModel: TaskModel): Result<Unit>
    suspend fun deleteTask(task: TaskModel): Result<Unit>
    suspend fun getTask(id:Int): Result<TaskModel?>

}