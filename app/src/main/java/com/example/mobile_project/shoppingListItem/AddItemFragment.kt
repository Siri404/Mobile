package com.example.mobile_project.shoppingListItem


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.example.mobile_project.R
import com.example.mobile_project.database.ShoppingListDatabase
import com.example.mobile_project.databinding.FragmentAddItemBinding

/**
 * A simple [Fragment] subclass.
 */
class AddItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentAddItemBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_item, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ShoppingListDatabase.getInstance(application).shoppingListItemDao

        val args = AddItemFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory = ShoppingListItemViewModelFactory(dataSource, args.listId, application)

        val shoppingListItemViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(ShoppingListItemViewModel::class.java)

        binding.shoppingListItemViewModel = shoppingListItemViewModel

        binding.lifecycleOwner = this
        binding.saveItemButton.setOnClickListener {
            if(binding.itemNameEditText.text.isEmpty() or binding.priceEditText.text.isEmpty()){
                Toast.makeText(binding.root.context, "Fields must not be emtpy!", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.shoppingListItemViewModel!!.addItem(
                    binding.itemNameEditText.text.toString(),
                    binding.priceEditText.text.toString().toInt())
                Navigation.findNavController(view!!).navigate(
                    AddItemFragmentDirections.actionAddItemFragmentToShoppingListItemFragment(args.listId, args.listName))
            }


        }

        return binding.root
    }


}
