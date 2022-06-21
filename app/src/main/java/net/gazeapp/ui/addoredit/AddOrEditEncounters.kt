package net.gazeapp.ui.addoredit

import android.view.View
import net.gazeapp.R
import net.gazeapp.data.model.Encounter
import net.gazeapp.databinding.IncludeEncounterSnippetItemBinding
import net.gazeapp.dialog.AddEditEncounterDialog

class AddOrEditEncounters(
    val f: AddOrEditContactFragment
) {
    fun fillEncountersOverviewLayout(encountersArrayList: MutableList<Encounter>?) {
        encountersArrayList?.sortWith { o1: Encounter, o2: Encounter ->
            o2.meetDate.compareTo(o1.meetDate)
        }

        f.encounterLayout.visibility = View.VISIBLE
        f.encounterLayout.removeAllViews()

        if (encountersArrayList != null) {
            for (enc in encountersArrayList) {
                val binding = IncludeEncounterSnippetItemBinding.bind(f.viewBinding.scrollView)

                binding.encounterLayoutSnippet.setOnClickListener {
                    val addEditWorkDialog =
                        AddEditEncounterDialog(
                            enc,
                            f.contactWithDetails.contact,
                            f.requireActivity(),
                            true,
                            f.parentFragmentManager
                        )
                    addEditWorkDialog.showDialog()
                }

                binding.encounterMeetDate.text =
                    f.tools.formatDateToPhoneLocale(enc.meetDate, true)

                if (enc.meetLocation.isNullOrEmpty()) {
                    binding.encounterMeetLocation.visibility = View.GONE
                    binding.encounterMeetDate.setTextAppearance(R.style.Regular)
                } else {
                    binding.encounterMeetLocation.text = enc.meetLocation
                }
                f.encounterLayout.addView(binding.root)
            }
        } else {
            f.encounterLayout.visibility = View.GONE
        }
    }

}