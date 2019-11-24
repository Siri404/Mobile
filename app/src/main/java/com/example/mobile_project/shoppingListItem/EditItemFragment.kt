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
import com.example.mobile_project.databinding.FragmentEditItemBinding

/**
 * A simple [Fragment] subclass.
 */
class EditItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentEditItemBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_edit_item, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ShoppingListDatabase.getInstance(application).shoppingListItemDao

        val args = EditItemFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory = ShoppingListItemViewModelFactory(dataSource, args.listId, application)

        val shoppingListItemViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(ShoppingListItemViewModel::class.java)

        binding.shoppingListItemViewModel = shoppingListItemViewModel

        binding.lifecycleOwner = this


        binding.saveButton.setOnClickListener {
            if(binding.itemNameEditText.text.isEmpty() or binding.priceEditText.text.isEmpty()){
                Toast.makeText(binding.root.context, "Fields must not be emtpy!", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.shoppingListItemViewModel!!.updateItem(
                    binding.itemNameEditText.text.toString(),
                    args.itemId,
                    args.listId,
                    binding.priceEditText.text.toString().toInt())
                Navigation.findNavController(view!!).navigate(
                    EditItemFragmentDirections.actionEditItemFragmentToShoppingListItemFragment(args.listId, args.listName))
            }
        }

        return binding.root
    }


}
