package sample.cinema.ticket.core.util.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import sample.cinema.ticket.core.api.ApiResult
import sample.cinema.ticket.core.api.ExceptionHelper
import sample.cinema.ticket.core.api.Exceptions
import sample.cinema.ticket.core.model.NetworkViewState

abstract class BaseViewModel : ViewModel() {

    private val _networkViewState = MutableStateFlow(NetworkViewState())
    val networkViewState = _networkViewState.asStateFlow()

    private suspend fun updateNetworkViewState(
        networkViewStates: NetworkViewState
    ) {
        withContext(Dispatchers.Main) {
            _networkViewState.update {
                networkViewStates
            }
        }
    }

    protected suspend fun networkLoading(requestTag: String? = null) {
        updateNetworkViewState(
            NetworkViewState(
                showProgress = true,
                requestTag = requestTag
            )
        )
    }

    protected suspend fun networkMoreLoading(requestTag: String? = null) {
        updateNetworkViewState(
            NetworkViewState(
                showProgressMore = true,
                requestTag = requestTag
            )
        )
    }

    protected open suspend fun <T> observeNetworkState(
        vararg results: ApiResult<T>,
        requestTagList: List<String> = listOf()
    ) {
        var errorChecked = false
        var networkStateList: List<NetworkViewState> = mutableListOf()

        results.forEachIndexed { index, result ->

            // Check and get requestTag if existed
            val requestTag = requestTagList.elementAtOrNull(index)

            if (result is ApiResult.Error && !errorChecked) {
                val networkViewState = getNetworkStateResult(result, requestTag)
                updateNetworkViewState(networkViewState)
                errorChecked = true
            }

            networkStateList = networkStateList.plus(getNetworkStateResult(result, requestTag))
        }

        // When all result become Success we have to handle them
        if (!errorChecked)
            updateNetworkViewState(
                NetworkViewState(
                    showSuccess = true
                )
            )
    }

    protected open suspend fun <T> observeNetworkState(result: ApiResult<T>, requestTag: String) {
        updateNetworkViewState(getNetworkStateResult(result, requestTag))
    }

    private suspend fun <T> getNetworkStateResult(
        result: ApiResult<T>,
        requestTag: String? = null
    ): NetworkViewState {
        return when (result) {
            is ApiResult.Success -> {
                NetworkViewState(
                    showSuccess = true,
                    data = castData(result, requestTag),
                    requestTag = requestTag
                )
            }

            is ApiResult.Error -> {
                if (result.exception is Exceptions.ValidationException<*>) {
                    NetworkViewState(
                        showValidationError = true,
                        validationError = result.exception.errors,
                        requestTag = requestTag
                    )
                } else {
                    val errorView = ExceptionHelper.getError(result.exception)
                    NetworkViewState(
                        showError = true,
                        serverErrorMessage = errorView.serverErrorMessage,
                        errorMessage = errorView.message,
                        requestTag = requestTag
                    )
                }
            }
        }
    }

    protected open suspend fun <T> castData(result: ApiResult<T>, requestTag: String?): Any? {
        return (result as ApiResult.Success).data
    }
}
