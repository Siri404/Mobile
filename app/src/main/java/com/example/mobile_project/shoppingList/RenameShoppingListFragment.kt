package com.example.mobile_project.shoppingList


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
import com.example.mobile_project.databinding.FragmentRenameShoppingListBinding

/**
 * A simple [Fragment] subclass.
 */
class RenameShoppingListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRenameShoppingListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_rename_shopping_list, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ShoppingListDatabase.getInstance(application).shoppingListDao

        val viewModelFactory = ShoppingListViewModelFactory(dataSource, application)

        val shoppingListViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(ShoppingListViewModel::class.java)

        val args = RenameShoppingListFragmentArgs.fromBundle(arguments!!)

        binding.shoppingListViewModel = shoppingListViewModel

        binding.lifecycleOwner = this

        binding.saveButton.setOnClickListener {
            if(binding.listNameEditText.text.isEmpty()){
                Toast.makeText(binding.root.context, "Fields must not be emtpy!", Toast.LENGTH_SHORT).show()
            }
            else {
                binding.shoppingListViewModel!!.updateList(
                    binding.listNameEditText.text.toString(), args.listId
                )
                Navigation.findNavController(view!!).navigate(
                    RenameShoppingListFragmentDirections.actionRenameShoppingListFragmentToShoppingListFragment()
                )
            }

        }

        return binding.root
    }


}
