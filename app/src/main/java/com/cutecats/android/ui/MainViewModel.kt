package com.cutecats.android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cutecats.android.ui.base.BaseNavigator
import com.cutecats.android.ui.base.BaseViewModel
import com.cutecats.android.data.DataRepository
import com.cutecats.android.data.model.CatItem
import com.cutecats.android.data.model.CategoryItem
import com.cutecats.android.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(dataRepository: DataRepository) : BaseViewModel<BaseNavigator>(dataRepository) {

    var categoriesItems: MutableLiveData<List<CategoryItem>> = MutableLiveData()

    var catsList: MutableLiveData<List<CatItem>> = MutableLiveData()

    var page=1

    var lastSelectedCategoryName=""

    fun getCatsListByCategoryId(id: String?) {

        if(id.isNullOrEmpty()){
            navigator!!.errorView("category id not exist!",null)
            return
        }

        if(page==1){
            navigator?.loadingView()
        }else{
            navigator?.showLazyLoading()
        }

        viewModelScope.launch {
            try {
                val response = dataRepository.getCatsListByCategoryId(id,page,
                    Constants.CATS_LIST_LIMIT
                )
                catsList.value = response
                if(page==1){
                    navigator?.contentLoaded()
                }else{
                    navigator?.hideLazyLoading()
                }
                page+=1

            }
            catch (e: Exception) {
                navigator!!.errorView(e.message.toString(),e.cause)
            }
        }
    }

    fun getCatsCategories() {

        navigator!!.loadingView()

        viewModelScope.launch {

            try {
                val response = dataRepository.getCatsCategories()
                categoriesItems.value = response

                navigator!!.contentLoaded()
            }
            catch (e: Exception) {
                navigator!!.errorView(e.message.toString(),e.cause)
            }
        }
    }
}