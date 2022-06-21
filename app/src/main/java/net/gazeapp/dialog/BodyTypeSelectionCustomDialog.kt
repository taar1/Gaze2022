package net.gazeapp.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.gazeapp.R
import net.gazeapp.data.dto.BodyTypeDto
import net.gazeapp.databinding.DialogRecyclerviewBinding
import net.gazeapp.databinding.ItemlistMultiselectBinding
import net.gazeapp.ui.addoredit.AddOrEditContactViewModel

class BodyTypeSelectionCustomDialog(
    var activity: Activity,
    val viewModel: AddOrEditContactViewModel,
    val outerView: View,
    private val bodyTypeList: List<BodyTypeDto>
) : Dialog(
    activity
), View.OnClickListener {

    init {
        setCancelable(false)
    }

    lateinit var recyclerView: RecyclerView
    lateinit var ok: Button
    lateinit var cancel: Button

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        val binding = DialogRecyclerviewBinding.bind(outerView)
        recyclerView = binding.contactsRecyclerView

        ok = binding.okButton
        cancel = binding.cancelButton
        ok.setOnClickListener(this)
        cancel.setOnClickListener(this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = BodytypeRecyclerListAdapter(activity, bodyTypeList)
            visibility = View.VISIBLE
        }
    }

    // TODO FIXME funktionieren die OK und CANCEL buttons?
    // TODO FIXME funktionieren die OK und CANCEL buttons?
    override fun onClick(v: View) {
        when (v) {
            ok -> {
                // TODO save and dismiss
                viewModel.bodyTypeList
            }
            cancel -> {
                dismiss()
            }
        }
    }
}

class BodytypeRecyclerListAdapter(val activity: Activity, val bodyTypeList: List<BodyTypeDto>) :
    RecyclerView.Adapter<BodytypeRecyclerListAdapter.BodytypeViewHolder>() {

    override fun getItemCount(): Int {
        return bodyTypeList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodytypeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemlistMultiselectBinding.inflate(inflater)

        return BodytypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BodytypeViewHolder, position: Int) =
        holder.bind(bodyTypeList[position])

//    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
//        if (viewHolder is BodytypeViewHolder) {
//            val bodytype = bodyTypeList[position]
//            val imageViewCheckbox = viewHolder.imageViewCheckbox
//            val bodytypeTextView = viewHolder.bodytypeTextView
//            val bodytypeIdTextView = viewHolder.bodytypeIdTextView
//            val linearLayout = viewHolder.linearLayout
//
//            if (bodytype.isSelected) {
//                imageViewCheckbox.setImageResource(R.drawable.ic_check)
//            } else {
//                imageViewCheckbox.setImageResource(R.drawable.ic_crop_free_black_48dp)
//            }
//            bodytypeIdTextView.text = bodytype.bodytypeId.toString()
//            bodytypeTextView.text = bodytype.bodyTypeStr
//
//            linearLayout.setOnClickListener {
//                if (bodytype.isSelected) {
//                    bodytype.isSelected = false
//                    imageViewCheckbox.setImageResource(R.drawable.ic_crop_free_black_48dp)
//                } else {
//                    bodytype.isSelected = true
//                    imageViewCheckbox.setImageResource(R.drawable.ic_check)
//                }
//            }
//        }
//    }

    inner class BodytypeViewHolder(binding: ItemlistMultiselectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var bodytypeIdTextView: TextView = binding.valueId
        var bodytypeTextView: TextView = binding.valueText
        var linearLayout: LinearLayout = binding.item
        var imageViewCheckbox: ImageView = binding.imageCheckbox

        fun bind(bodytype: BodyTypeDto) {
            if (bodytype.isSelected) {
                imageViewCheckbox.setImageResource(R.drawable.ic_check)
            } else {
                imageViewCheckbox.setImageResource(R.drawable.ic_crop)
            }
            bodytypeIdTextView.text = bodytype.bodytypeId.toString()
            bodytypeTextView.text = bodytype.bodyTypeStr

            linearLayout.setOnClickListener {
                if (bodytype.isSelected) {
                    bodytype.isSelected = false
                    imageViewCheckbox.setImageResource(R.drawable.ic_crop)
                } else {
                    bodytype.isSelected = true
                    imageViewCheckbox.setImageResource(R.drawable.ic_check)
                }
            }

        }
    }

//    fun setOnItemClickListener(myClickListener: MyClickListener?) {
//        Companion.myClickListener = myClickListener
//    }
//
//    interface MyClickListener {
//        fun onItemClick(position: Int, v: View?)
//    }


}