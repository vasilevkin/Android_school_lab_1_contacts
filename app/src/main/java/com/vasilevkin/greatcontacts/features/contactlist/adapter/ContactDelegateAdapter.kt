package com.vasilevkin.greatcontacts.features.contactlist.adapter

import com.vasilevkin.greatcontacts.R
import com.vasilevkin.greatcontacts.delegateadapter.KDelegateAdapter
import com.vasilevkin.greatcontacts.features.contactlist.view.ContactListFragment
import com.vasilevkin.greatcontacts.models.localmodels.ContactLocalModel
import kotlinx.android.synthetic.main.contact_item.*


class ContactDelegateAdapter : KDelegateAdapter<ContactLocalModel>() {
    override fun onBind(item: ContactLocalModel, viewHolder: KViewHolder) =
        with(viewHolder) {
            name_text_view.text =
                item.contact.firstName + " " + item.contact.lastName
//                    .orEmpty().capitalize(java.util.Locale.getDefault())
            phone_text_view.text = item.contact.phone
            email_text_view.text = item.contact.email

            itemView.setOnClickListener {
                val activity = item.context as ContactListFragment.OnContactSelected
                activity.onSelected(item.contact)
            }

            call_image_button.setOnClickListener {
                val activity = item.context as ContactListFragment.OnCallContact
                activity.onCallContactClicked(item.contact)
            }
        }

    override fun isForViewType(items: List<*>, position: Int) =
        items[position] is ContactLocalModel

    override val layoutId: Int = R.layout.contact_item
}
