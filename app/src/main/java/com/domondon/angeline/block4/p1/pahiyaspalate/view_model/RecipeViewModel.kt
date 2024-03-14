package com.domondon.angeline.block4.p1.pahiyaspalate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.topTenDomain

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<topTenDomain>>()
    val recipes: LiveData<List<topTenDomain>> = _recipes

    fun setRecipes(recipes: List<topTenDomain>) {
        _recipes.value = recipes
    }
}