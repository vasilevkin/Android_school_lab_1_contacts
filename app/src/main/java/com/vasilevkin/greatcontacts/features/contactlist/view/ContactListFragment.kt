package com.vasilevkin.greatcontacts.features.contactlist.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
import kotlinx.android.synthetic.main.contact_list_fragment.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class ContactListFragment : Fragment(), Filterable {

    companion object {
        fun newInstance() =
            ContactListFragment()
    }

    @Inject
    lateinit var viewModel: ContactListViewModel

    private var contactsList: List<IComparableItem> = emptyList()
    private var contactsFilteredList: List<IComparableItem> = emptyList()
    private lateinit var diffAdapter: DiffUtilCompositeAdapter
    private var contactsRecyclerView: RecyclerView? = null
    private var disposable: Disposable? = null

    private lateinit var listener: OnCallContact
    private lateinit var contactListener: OnContactSelected

    interface OnCallContact {
        fun onCallContactClicked(contact: Person)
    }

    interface OnContactSelected {
        fun onSelected(contact: Person, newContact: Boolean)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
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

        contact_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter.filter(newText)
                return false
            }
        })
    }

    override fun onStart() {
        super.onStart()
        contactsRecyclerView = view?.findViewById(R.id.contact_list_recyclerview)

        viewModel.onViewCreated()

        diffAdapter = DiffUtilCompositeAdapter.Builder()
            .add(ContactDelegateAdapter())
            .build()

        contactsRecyclerView?.run {
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
                this.contactsList = it
                displayContacts(it)
            }
    }

    override fun onStop() {
        super.onStop()
        this.disposable?.dispose()
    }

    // Menu

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contacts_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if (id == R.id.add_new_contact_menu_button) {

            val activity = activity as OnContactSelected
            activity.onSelected(Person("","","",""), true)

        }
        return super.onOptionsItemSelected(item)
    }

    // interface Filterable

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                contactsFilteredList = if (charSearch.isEmpty()) {
                    contactsList
                } else {
                    val resultList = ArrayList<IComparableItem>()
                    for (row in contactsList) {
                        if (row.content().toString().toLowerCase(Locale.ROOT).contains(
                                charSearch.toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = contactsFilteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactsFilteredList = results?.values as ArrayList<IComparableItem>
                displayContacts(contactsFilteredList)
            }
        }
    }

    // Private methods

    private fun displayContacts(list: List<IComparableItem>) {
        diffAdapter.swapData(list)
        contactsRecyclerView?.scrollToPosition(0)
    }
}
