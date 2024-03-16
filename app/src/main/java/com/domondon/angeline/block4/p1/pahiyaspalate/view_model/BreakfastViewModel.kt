package com.domondon.angeline.block4.p1.pahiyaspalate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.BreakfastDomain

class BreakfastViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<BreakfastDomain>>()
    val recipes: LiveData<List<BreakfastDomain>> = _recipes

    fun setRecipes(recipes: List<BreakfastDomain>) {
        _recipes.value = recipes
    }
}