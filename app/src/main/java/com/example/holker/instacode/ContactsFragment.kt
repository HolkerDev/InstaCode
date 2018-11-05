package com.example.holker.instacode

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.contacts_fragment.*

class ContactsFragment : Fragment() {

    fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var users = arrayListOf<String>()
        var arrayAdapterUser = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, users)

        var query: ParseQuery<ParseUser> = ParseUser.getQuery()
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().username)
        query.addAscendingOrder("username")

        query.findInBackground { objects, e ->
            run {
                if (e == null && objects.size > 0) {
                    for (user in objects) {
                        users.add(user.username)
                    }
                    lv_userlist.adapter = arrayAdapterUser
                } else {
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
        return inflater.inflate(R.layout.contacts_fragment, null)
    }
}