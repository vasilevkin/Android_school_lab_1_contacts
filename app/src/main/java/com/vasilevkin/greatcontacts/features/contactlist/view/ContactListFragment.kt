package com.vasilevkin.greatcontacts.features.contactlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vasilevkin.greatcontacts.R
import com.vasilevkin.greatcontacts.features.contactlist.viewmodel.ContactListViewModel


class ContactListFragment : Fragment() {

    companion object {
        fun newInstance() =
            ContactListFragment()
    }

    private lateinit var viewModel: ContactListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    private lateinit var listener: OnCallContact


    interface OnCallContact {
        fun onCallContactClicked(contact: Person)
    }

    // Fragment Lifecycle methods

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCallContact) {
            listener = context
        } else {
            throw ClassCastException(
                "$context must implement OnCallContact."
            )
        }
    }
        return inflater.inflate(R.layout.contact_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContactListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
