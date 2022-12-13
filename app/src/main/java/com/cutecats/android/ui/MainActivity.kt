package com.cutecats.android.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.navigation.ui.AppBarConfiguration
import com.cutecats.android.R
import com.cutecats.android.data.model.CatItem
import com.cutecats.android.data.model.CategoryItem
import com.cutecats.android.databinding.ActivityMainBinding
import com.cutecats.android.ui.base.BaseActivity
import com.cutecats.android.ui.adapter.CatsListAdapter
import com.cutecats.android.utils.Constants
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val mViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private var categoryItems:ArrayList<CategoryItem> = ArrayList()
    var mCatsListAdapter: CatsListAdapter? = null
    var mCatsList:ArrayList<CatItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        mViewModel.navigator = this

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()

        prepareViews()
        initObservers()
        initListeners()



    }

    private fun prepareViews(){
        binding.navView.setNavigationItemSelectedListener(this)

        mCatsListAdapter = CatsListAdapter(mCatsList)
        binding.contextMain.rcvCats.adapter = mCatsListAdapter

        mViewModel.getCatsCategories()

    }

    private fun initObservers(){
        mViewModel.categoriesItems.observe(this, Observer {

            categoryItems.clear()
            categoryItems.addAll(it)
            prepareDrawerItems()
        })

        mViewModel.catsList.observe(this, Observer {

            mCatsList.addAll(it)
            mCatsListAdapter?.notifyItemRangeChanged(mCatsList.size- Constants.CATS_LIST_LIMIT,
                Constants.CATS_LIST_LIMIT
            )
        })
    }

    private fun initListeners(){
        binding.contextMain.btnLoadMore.setOnClickListener {
            fetchCatsItemsByCategoryName(mViewModel.lastSelectedCategoryName)
        }
    }

    private fun prepareDrawerItems(){
        binding.navView.menu.clear()
        val drawerItem: Menu = binding.navView.menu
        categoryItems.forEach {
            drawerItem.add(it.name)
        }

        fetchCatsItemsByCategoryName(categoryItems.first().name)
    }

    private fun fetchCatsItemsByCategoryName(name: String?) {
        categoryItems.find { it.name==name }?.let {
            mViewModel.lastSelectedCategoryName=name ?: ""
            mViewModel.getCatsListByCategoryId(it.id)
        }
    }

    private fun setUpToolbar(){
        setSupportActionBar(binding.contextMain.toolbar)
        var toggle=ActionBarDrawerToggle(this,binding.drawerLayout,binding.contextMain.toolbar,
            R.string.categories,R.string.cats_list)
        toggle.isDrawerIndicatorEnabled = true;
        toggle.isDrawerSlideAnimationEnabled = true;
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }


    override fun loadingView() {
        binding.contextMain.rcvCats.visibility= View.GONE
        binding.contextMain.progressLoading.visibility= View.VISIBLE
    }

    override fun contentLoaded() {
        binding.contextMain.rcvCats.visibility= View.VISIBLE
        binding.contextMain.progressLoading.visibility= View.GONE
        binding.contextMain.btnLoadMore.visibility= View.VISIBLE
    }

    override fun errorView(message: Any, tr: Throwable?) {
        super.errorView(message,tr)
        binding.contextMain.rcvCats.visibility= View.GONE
        binding.contextMain.progressLoading.visibility= View.GONE
    }

    override fun showLazyLoading() {
        binding.contextMain.btnLoadMore.visibility= View.INVISIBLE
        binding.contextMain.progressLazyLoading.visibility= View.VISIBLE
    }

    override fun hideLazyLoading() {
        binding.contextMain.btnLoadMore.visibility= View.VISIBLE
        binding.contextMain.progressLazyLoading.visibility= View.GONE
    }

    private fun clearAllCats(){
        mCatsList.clear()
        mCatsListAdapter?.notifyDataSetChanged()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        clearAllCats()

        fetchCatsItemsByCategoryName(item.toString())

        binding.drawerLayout.closeDrawers()

        return false
    }
}