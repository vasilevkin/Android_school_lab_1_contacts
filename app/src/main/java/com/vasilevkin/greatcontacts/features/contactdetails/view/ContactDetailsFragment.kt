package com.vasilevkin.greatcontacts.features.contactdetails.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContactDetailsViewModel::class.java)

        val selectContactObserver = Observer<Person> { contact ->
            first_name_edit_text.setText(contact.firstName)
            last_name_edit_text.setText(contact.lastName)
            phone_edit_text.setText(contact.phone)
            email_edit_text.setText(contact.email)

            (activity as AppCompatActivity).supportActionBar?.title =
                "${contact.firstName} ${contact.lastName}"
        }

        sharedViewModel.getSelectedContact().observe(viewLifecycleOwner, selectContactObserver)
    }
}
