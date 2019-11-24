package com.example.mobile_project.shoppingListItem


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.example.mobile_project.R
import com.example.mobile_project.database.ShoppingListDatabase
import com.example.mobile_project.databinding.FragmentShoppingListBinding
import com.example.mobile_project.databinding.FragmentShoppingListItemBinding
import com.example.mobile_project.shoppingList.ShoppingListAdapter
import com.example.mobile_project.shoppingList.ShoppingListFragmentDirections
import com.example.mobile_project.shoppingList.ShoppingListViewModel
import com.example.mobile_project.shoppingList.ShoppingListViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class ShoppingListItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentShoppingListItemBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_shopping_list_item, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ShoppingListDatabase.getInstance(application).shoppingListItemDao

        val args = ShoppingListItemFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory = ShoppingListItemViewModelFactory(dataSource, args.listId, application)

        val shoppingListItemViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(ShoppingListItemViewModel::class.java)

        binding.shoppingListItemViewModel = shoppingListItemViewModel

        binding.lifecycleOwner = this

        binding.addItem.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                ShoppingListItemFragmentDirections.actionShoppingListItemFragmentToAddItemFragment(args.listId, args.listName)))

        val adapter = ShoppingListItemAdapter(ItemClickListener {
                itemId -> Toast.makeText(binding.root.context, "$itemId", Toast.LENGTH_LONG).show()
        },
            MenuOnClickListener {
                    item, itemId ->
                when (item!!.itemId) {
                    R.id.menu1 ->
                    {
                        this.findNavController().navigate(ShoppingListItemFragmentDirections
                            .actionShoppingListItemFragmentToEditItemFragment(args.listId, itemId, args.listName))
                        true
                    }
                    R.id.menu2 ->
                    {
                        shoppingListItemViewModel.removeItem(itemId)
                        true
                    }
                    else -> false
                }
            })
        binding.items.adapter = adapter

        shoppingListItemViewModel.items.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

//        val simpleItemTouchCallback = object :
//            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
//                binding.shoppingListItemViewModel!!.removeItem(adapter.currentList[viewHolder.adapterPosition])
//                //adapter.notifyItemRemoved(viewHolder.adapterPosition)
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
//        itemTouchHelper.attachToRecyclerView(binding.items)

        return binding.root
    }


}
