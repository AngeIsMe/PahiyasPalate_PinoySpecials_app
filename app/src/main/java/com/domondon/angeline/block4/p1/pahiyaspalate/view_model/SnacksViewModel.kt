package com.domondon.angeline.block4.p1.pahiyaspalate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.domondon.angeline.block4.p1.pahiyaspalate.domain.SnacksDomain

class SnacksViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<SnacksDomain>>()
    val recipes: LiveData<List<SnacksDomain>> = _recipes

    fun setRecipes(recipes: List<SnacksDomain>) {
        _recipes.value = recipes
    }
}