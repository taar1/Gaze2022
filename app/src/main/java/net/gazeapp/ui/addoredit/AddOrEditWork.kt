package net.gazeapp.ui.addoredit

import android.view.View
import net.gazeapp.data.dto.WorkDto
import net.gazeapp.databinding.IncludeWorkSnippetItemBinding
import net.gazeapp.dialog.AddEditWorkDialog

class AddOrEditWork(
    val f: AddOrEditContactFragment
) {
    fun displayWork(workList: List<WorkDto>) {
        if (workList.isNotEmpty()) {
            f.workLayout.visibility = View.VISIBLE
            f.workLayout.removeAllViews()

            for (workDtoItem in workList) {
                val binding = IncludeWorkSnippetItemBinding.bind(f.viewBinding.scrollView)

                binding.workLayoutSnippet.setOnClickListener {
                    val addEditWorkDialog = AddEditWorkDialog(
                        workDtoItem.work, f.contactWithDetails.contact, f.requireActivity(), true
                    )
                    addEditWorkDialog.showDialog()
                }

                binding.profession.text = workDtoItem.profession
                binding.timeline.text = workDtoItem.workDuration

                f.workLayout.addView(binding.root)
            }
        } else {
            f.workLayout.visibility = View.GONE
        }
    }
}