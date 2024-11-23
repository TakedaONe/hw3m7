package com.projectx.homework3_7month.data.repositoryImpl

import com.projectx.homework3_7month.data.database.dao.TaskManagerDao
import com.projectx.homework3_7month.data.dto.toData
import com.projectx.homework3_7month.data.dto.toDomain
import com.projectx.homework3_7month.domain.model.TaskModel
import com.projectx.homework3_7month.domain.repository.TaskManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.catch

class TaskManagerRepositoryImpl(private val taskManagerDao: TaskManagerDao) : TaskManagerRepository {

    override suspend fun insertTask(taskModel: TaskModel): Result<Unit> {
        return try {
            taskManagerDao.insertTask(taskModel.toData())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllNotes(): Flow<Result<List<TaskModel>>> {
        return taskManagerDao.getAllNotes()
            .map { list -> Result.success(list.map { it.toDomain() }) }
            .catch { e -> emit(Result.failure(e)) }
    }

    override suspend fun getTaskByName(taskName: String): Result<TaskModel?> {
        return try {
            val task = taskManagerDao.getTaskByName(taskName)?.toDomain()
            Result.success(task)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTask(taskModel: TaskModel): Result<Unit> {
        return try {
            taskManagerDao.updateTask(taskModel.toData())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTask(task: TaskModel): Result<Unit> {
        return try {
            taskManagerDao.deleteTask(task.toData())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTask(id: Int): Result<TaskModel?> {
        return try {
            val task = taskManagerDao.getTaskById(id)?.toDomain()
            Result.success(task)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
