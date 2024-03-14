package com.domondon.angeline.block4.p1.pahiyaspalate.activity

import HomeFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.domondon.angeline.block4.p1.pahiyaspalate.databinding.ActivityBottomNavBarBinding
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.fragment.AddRecipeFragment
import com.domondon.angeline.block4.p1.pahiyaspalate.fragment.BookmarksFragment
import com.domondon.angeline.block4.p1.pahiyaspalate.fragment.CategoriesFragment
import com.domondon.angeline.block4.p1.pahiyaspalate.fragment.ProfileFragment

class BottomNavBar : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityBottomNavBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.category -> {
                    replaceFragment(CategoriesFragment())
                    true
                }
                R.id.bookmarks -> {
                    replaceFragment(BookmarksFragment())
                    true
                }
                R.id.addrecipe -> {
                    replaceFragment(AddRecipeFragment())
                    true
                }
                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}
