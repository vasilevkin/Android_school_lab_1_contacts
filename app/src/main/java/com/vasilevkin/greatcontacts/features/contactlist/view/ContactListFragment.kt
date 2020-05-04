package com.vasilevkin.greatcontacts.features.contactlist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vasilevkin.greatcontacts.R
import com.vasilevkin.greatcontacts.delegateadapter.diff.DiffUtilCompositeAdapter
import com.vasilevkin.greatcontacts.delegateadapter.diff.IComparableItem
import com.vasilevkin.greatcontacts.features.contactlist.adapter.ContactDelegateAdapter
import com.vasilevkin.greatcontacts.features.contactlist.viewmodel.ContactListViewModel
import com.vasilevkin.greatcontacts.models.Person
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ComputationScheduler
import javax.inject.Inject


class ContactListFragment : Fragment() {

    companion object {
        fun newInstance() =
            ContactListFragment()
    }

    @Inject
    lateinit var viewModel: ContactListViewModel

    private var contacts: List<IComparableItem> = emptyList()
    private lateinit var diffAdapter: DiffUtilCompositeAdapter
    private var contactsList: RecyclerView? = null
    private var disposable: Disposable? = null

    private lateinit var listener: OnCallContact
    private lateinit var contactListener: OnContactSelected

    interface OnCallContact {
        fun onCallContactClicked(contact: Person)
    }

    interface OnContactSelected {
        fun onSelected(contact: Person)
    }

    // Fragment Lifecycle methods

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity).appComponent.inject(this)

        if (context is OnContactSelected) {
            contactListener = context
        } else {
            throw ClassCastException(
                "$context must implement OnContactSelected."
            )
        }

        if (context is OnCallContact) {
            listener = context
        } else {
            throw ClassCastException(
                "$context must implement OnCallContact."
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Contacts"

        viewModel.view = activity as Context
    }

    override fun onStart() {
        super.onStart()
        contactsList = view?.findViewById(R.id.contact_list_recyclerview)

        viewModel.onViewCreated()

        diffAdapter = DiffUtilCompositeAdapter.Builder()
            .add(ContactDelegateAdapter())
            .build()

        contactsList?.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = diffAdapter

            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        this.disposable = viewModel.contactList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(ComputationScheduler())
            .subscribe {
                displayContacts(it)
            }
    }

    override fun onStop() {
        super.onStop()
        this.disposable?.dispose()
    }

    // Private methods

    private fun displayContacts(list: List<IComparableItem>) {
        this.contacts = list

        diffAdapter.swapData(contacts)
        contactsList?.scrollToPosition(0)
    }
}
