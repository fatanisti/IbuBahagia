package com.fkc.ibubahagia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fkc.ibubahagia.databinding.ActivityContactRowBinding

class ContactListAdapter(
    private var dataList: List<Contact>
) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    var listener: RecyclerViewClickListener? = null

    inner class ContactViewHolder(
        val itemContactsRowBinding: ActivityContactRowBinding
    ) : RecyclerView.ViewHolder(itemContactsRowBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.activity_contact_row, parent, false)
    )

    override fun onBindViewHolder(holder: ContactViewHolder, i: Int) {
        val contact = dataList[i]

        holder.itemContactsRowBinding.namaKlinik.text = contact.namaKlinik
        holder.itemContactsRowBinding.alamat.text = contact.alamat
        holder.itemContactsRowBinding.telepon.text = contact.telepon
        holder.itemContactsRowBinding.emailWeb.text = contact.emailWeb
        holder.itemContactsRowBinding.spesialisasi.text = contact.spesialisasi

        holder.itemContactsRowBinding.listKontak.setOnClickListener {
            listener?.onItemClicked(it, dataList[i])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}