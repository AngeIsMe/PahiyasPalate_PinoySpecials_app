package com.domondon.angeline.block4.p1.pahiyaspalate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.BreakfastDomain
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.LunchDomain

class LunchViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<LunchDomain>>()
    val recipes: LiveData<List<LunchDomain>> = _recipes

    fun setRecipes(recipes: List<LunchDomain>) {
        _recipes.value = recipes
    }
}