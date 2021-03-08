package com.fkc.ibubahagia

import android.view.View

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, contact: Contact)
}