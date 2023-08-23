package com.ranaturker.readopia.ui.list.model

import com.ranaturker.readopia.network.model.Result

sealed class UIState {
    object Loading : UIState()
    data class Success(val data: List<Result>) : UIState()
}
