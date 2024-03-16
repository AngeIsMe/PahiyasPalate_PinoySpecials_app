package com.domondon.angeline.block4.p1.pahiyaspalate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.DinnerDomain

class DinnerViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<DinnerDomain>>()
    val recipes: LiveData<List<DinnerDomain>> = _recipes

    fun setRecipes(recipes: List<DinnerDomain>) {
        _recipes.value = recipes
    }
}