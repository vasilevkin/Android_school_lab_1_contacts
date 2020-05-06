package com.vasilevkin.greatcontacts.features.contactdetails.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vasilevkin.greatcontacts.R
import com.vasilevkin.greatcontacts.features.contactdetails.viewmodel.ContactDetailsViewModel
import com.vasilevkin.greatcontacts.features.contactlist.view.MainActivity
import com.vasilevkin.greatcontacts.features.sharedviewmodel.SharedViewModel
import com.vasilevkin.greatcontacts.models.Person
import kotlinx.android.synthetic.main.contact_details_fragment.*
import javax.inject.Inject


class ContactDetailsFragment : Fragment() {

    companion object {
        fun newInstance() =
            ContactDetailsFragment()
    }

    @Inject
    lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: ContactDetailsViewModel

    // Fragment Lifecycle methods

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContactDetailsViewModel::class.java)

        makeFormEditable(false)

        val selectContactObserver = Observer<Person> { contact ->
            first_name_edit_text.setText(contact.firstName)
            last_name_edit_text.setText(contact.lastName)
            phone_edit_text.setText(contact.phone)
            email_edit_text.setText(contact.email)

            val title = if (sharedViewModel.newContact) {
                "New Contact"
            } else {
                "${contact.firstName} ${contact.lastName}"
            }

            (activity as AppCompatActivity).supportActionBar?.title = title
        }

        sharedViewModel.getSelectedContact().observe(viewLifecycleOwner, selectContactObserver)
    }

    // Menu

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if (sharedViewModel.newContact) {
            inflater.inflate(R.menu.new_contact_details_menu, menu)
            makeFormEditable(true)
        } else {
            inflater.inflate(R.menu.contact_details_menu, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if (id == R.id.edit_contact_details_menu_button) {
            makeFormEditable(true)
        }
        if (id == R.id.save_contact_details_menu_button) {
            Toast.makeText(activity, "TODO: Implement save new contact", Toast.LENGTH_LONG)
                .show()
        }
        return super.onOptionsItemSelected(item)
    }

    // Private methods

    private fun makeFormEditable(editable: Boolean) {
        first_name_edit_text.isEnabled = editable
        last_name_edit_text.isEnabled = editable
        phone_edit_text.isEnabled = editable
        email_edit_text.isEnabled = editable
    }
}
