package com.mjc.mjcnotice.ui.setting.license

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.mjc.mjcnotice.databinding.ItemLicenseBinding
import com.mjc.mjcnotice.ui.setting.license.explanation.ExplanationLicenseActivity

class LicenseAdapter(licenseList: List<License>) : RecyclerView.Adapter<LicenseAdapter.LicenseViewHolder>() {
    var items = licenseList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : LicenseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLicenseBinding.inflate(layoutInflater, parent, false)
        return LicenseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LicenseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class LicenseViewHolder(private val binding: ItemLicenseBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(license: License) {
            binding.tvLibraryName.text = license.owner
            binding.layoutLibrary.setOnClickListener {
                if(license.type == "firebase"){
                    webView("https://firebase.google.com/terms?hl=ko", itemView.context)
                } else {
                    var intent = Intent(it.context, ExplanationLicenseActivity::class.java)
                    intent.putExtra("type", license.type)
                    intent.putExtra("copyright", license.copyright)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    fun webView(url: String , context: Context) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        builder.setShowTitle(true)
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}