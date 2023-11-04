package com.eco.citizen.core.util.viewModel

import androidx.lifecycle.ViewModel
import com.eco.citizen.features.shop.data.entity.ShopChargeEntity
import com.eco.citizen.features.shop.data.entity.ShopInternetEntity
import com.eco.citizen.features.shop.data.entity.ShopInternetPackEntity
import com.eco.citizen.features.shop.data.model.ShopInternetPeriodModel
import com.eco.citizen.features.user.data.entity.UserGuildTypeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _onSelectedStringValue = MutableStateFlow<String?>(null)
    val onSelectedStringValue = _onSelectedStringValue.asStateFlow()

    fun setOnSelectedStringValue(value: String?) {
        _onSelectedStringValue.value = value
    }


    private val _onSelectedDoubleValue = MutableStateFlow<Double?>(null)
    val onSelectedDoubleValue = _onSelectedDoubleValue.asStateFlow()

    fun setOnSelectedDoubleValue(value: Double?) {
        _onSelectedDoubleValue.value = value
    }


    private val _onSelectedShopChargeOperator = MutableStateFlow<ShopChargeEntity?>(null)
    val onSelectedShopChargeOperator = _onSelectedShopChargeOperator.asStateFlow()

    fun setOnSelectedShopChargeOperator(value: ShopChargeEntity?) {
        _onSelectedShopChargeOperator.value = value
    }


    private val _onSelectedShopInternetOperator = MutableStateFlow<ShopInternetEntity?>(null)
    val onSelectedShopInternetOperator = _onSelectedShopInternetOperator.asStateFlow()

    fun setOnSelectedShopInternetOperator(value: ShopInternetEntity?) {
        _onSelectedShopInternetOperator.value = value
    }


    private val _onSelectedShopInternetPeriod = MutableStateFlow<ShopInternetPeriodModel?>(null)
    val onSelectedShopInternetPeriod = _onSelectedShopInternetPeriod.asStateFlow()

    fun setOnSelectedShopInternetPeriod(value: ShopInternetPeriodModel?) {
        _onSelectedShopInternetPeriod.value = value
    }


    private val _onSelectedShopInternetPack = MutableStateFlow<ShopInternetPackEntity?>(null)
    val onSelectedShopInternetPack = _onSelectedShopInternetPack.asStateFlow()

    fun setOnSelectedShopInternetPack(value: ShopInternetPackEntity?) {
        _onSelectedShopInternetPack.value = value
    }


    private val _onSelectedUserGuildType = MutableStateFlow<UserGuildTypeEntity?>(null)
    val onSelectedUserGuildType = _onSelectedUserGuildType.asStateFlow()

    fun setOnSelectedUserGuildType(value: UserGuildTypeEntity?) {
        _onSelectedUserGuildType.value = value
    }
}
