package ir.mehdisp.routine.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import ir.mehdisp.routine.data.models.Resource
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> = liveData(Dispatchers.IO) {
    emit(Resource.loading())
    val source = databaseQuery.invoke().map { Resource.success(it) }
    emitSource(source)

    val response = networkCall.invoke()

    if (response.status == Resource.Status.SUCCESS) {
        saveCallResult(response.data!!)
    } else if (response.status == Resource.Status.ERROR) {
        emit(Resource.error(response.message!!))
        emitSource(source)
    }
}

fun <T> performRemoteGetOperation(
    networkCall: suspend () -> Resource<T>
) = liveData(Dispatchers.IO){
    emit(Resource.loading())
    emit(networkCall.invoke())
}

fun <T> performLocalGetOperation(
    databaseQuery: () -> LiveData<T>
): LiveData<Resource<T>> = liveData(Dispatchers.IO) {
    emit(Resource.loading())

    val source = databaseQuery.invoke().map { Resource.success(it) }

    emitSource(source)
}