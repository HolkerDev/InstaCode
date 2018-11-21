package com.example.holker.instacode.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.holker.instacode.R
import com.example.holker.instacode.activities.Feed
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.contacts_fragment.*

class ContactsFragment : Fragment() {
    val users = arrayListOf<String>()

    fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onStart() {
        super.onStart()
        lv_userlist.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            kotlin.run {
                val intent = Intent(context!!.applicationContext, Feed::class.java)
                intent.putExtra("username", users[position])
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val arrayAdapterUser = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, users)

        val query: ParseQuery<ParseUser> = ParseUser.getQuery()
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