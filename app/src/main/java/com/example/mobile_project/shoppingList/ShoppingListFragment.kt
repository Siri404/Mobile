package com.example.mobile_project.shoppingList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mobile_project.R
import com.example.mobile_project.database.ShoppingListDatabase
import com.example.mobile_project.databinding.FragmentShoppingListBinding


class ShoppingListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentShoppingListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_shopping_list, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ShoppingListDatabase.getInstance(application).shoppingListDao

        val viewModelFactory = ShoppingListViewModelFactory(dataSource, application)

        val shoppingListViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(ShoppingListViewModel::class.java)

        binding.shoppingListViewModel = shoppingListViewModel

        binding.lifecycleOwner = this

        binding.addListButton.setOnClickListener(Navigation.createNavigateOnClickListener(
            ShoppingListFragmentDirections.actionShoppingListFragmentToAddShoppingListFragment()))

        val adapter = ShoppingListAdapter(ListClickListener {
                listId -> shoppingListViewModel.onListClicked(listId)
        },
            MenuOnClickListener {
                    item, listId ->
                when (item!!.itemId) {
                    R.id.menu1 ->
                    {
                        this.findNavController().navigate(ShoppingListFragmentDirections
                            .actionShoppingListFragmentToRenameShoppingListFragment(listId))
                        true
                    }
                    R.id.menu2 ->
                    {
                        shoppingListViewModel.removeList(listId)
                        true
                    }
                    else -> false
                }
            })

        binding.shoppingLists.adapter = adapter

        shoppingListViewModel.shoppingLists.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        shoppingListViewModel.navigateToShoppingListItem.observe(this, Observer {
            list -> list?.let {
                this.findNavController().navigate(
                    ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingListItemFragment(list))
                shoppingListViewModel.onShoppingListItemNavigated()
            }
        })

        return binding.root
    }


}
