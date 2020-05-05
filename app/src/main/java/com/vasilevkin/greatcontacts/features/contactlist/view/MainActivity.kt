package com.vasilevkin.greatcontacts.features.contactlist.view

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vasilevkin.greatcontacts.MainApplication
import com.vasilevkin.greatcontacts.R
import com.vasilevkin.greatcontacts.di.AppComponent
import com.vasilevkin.greatcontacts.features.contactdetails.view.ContactDetailsFragment
import com.vasilevkin.greatcontacts.features.sharedviewmodel.SharedViewModel
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.utils.PERMISSION_REQUEST_CODE
import com.vasilevkin.greatcontacts.utils.TAG_CONTACT_DETAILS_FRAGMENT
import com.vasilevkin.greatcontacts.utils.TAG_CONTACT_LIST_FRAGMENT
import javax.inject.Inject


class MainActivity : AppCompatActivity(),
    ContactListFragment.OnCallContact,
    ContactListFragment.OnContactSelected {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var sharedViewModel: SharedViewModel

    // Lifecycle methods

    override fun onCreate(savedInstanceState: Bundle?) {

        appComponent = (application as MainApplication).appComponent
        (application as MainApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.addOnBackStackChangedListener {
            val stackHeight = supportFragmentManager.backStackEntryCount
            if (stackHeight > 0) {
                // if we have something on the stack (doesn't include the current shown fragment)
                supportActionBar?.setHomeButtonEnabled(true)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setHomeButtonEnabled(false)
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, ContactListFragment.newInstance(), TAG_CONTACT_LIST_FRAGMENT)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                supportFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // interface ContactListFragment.OnContactSelected

    override fun onSelected(contact: Person) {
        val detailsFragment = ContactDetailsFragment.newInstance()

        sharedViewModel.selectContact(contact)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, detailsFragment, TAG_CONTACT_DETAILS_FRAGMENT)
            .addToBackStack(TAG_CONTACT_DETAILS_FRAGMENT)
            .commit()
    }

    // interface ContactListFragment.OnCallContact

    override fun onCallContactClicked(contact: Person) {
        Toast.makeText(this@MainActivity, "Calling number... : ${contact.phone}", Toast.LENGTH_LONG)
            .show()
        callPhoneNumber(contact.phone)
    }

    // Private methods
    // Check if permission is granted, request if needed and make a call

    private fun callPhoneNumber(phone: String) {
        if (checkCallPermission()) {
            val intent =
                Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone))
            startActivity(intent)
        } else {
            requestCallPermission()
        }
    }

    private fun checkCallPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCallPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CALL_PHONE),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@MainActivity,
                    "Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        showMessageOKCancel("You need to allow access permissions, Call Phone permission is required to make phone calls",
                            DialogInterface.OnClickListener { _, _ ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestCallPermission()
                                }
                            })
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@MainActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}
