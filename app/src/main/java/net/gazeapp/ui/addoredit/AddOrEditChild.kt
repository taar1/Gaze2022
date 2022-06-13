package net.gazeapp.ui.addoredit

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import net.gazeapp.R
import net.gazeapp.data.dto.ChildDto
import net.gazeapp.data.model.Child
import net.gazeapp.databinding.IncludeAddChildSnippetBinding

class AddOrEditChild(
    val f: AddOrEditContactFragment
) {
    fun displayChildrenList(childrenList: List<ChildDto>?) {
        f.childrenLayout.removeAllViews()
        f.childrenLayout.visibility = View.INVISIBLE

        childrenList?.let {
            f.childrenLayout.visibility = View.VISIBLE

            for (child in childrenList) {
                addChildLayoutByDto(child, false)
            }
        }
    }

    fun addChildLayoutByDto(child: ChildDto, setFocus: Boolean) {
        val binding = IncludeAddChildSnippetBinding.bind(f.viewBinding.scrollView)

        val childEdited = Child(child.contactId)
        childEdited.id = child.id

        val nameInput = binding.nameInput
        nameInput.setText(child.name)
        val nameTextChangedWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                childEdited.name = nameInput.text.toString().trim { it <= ' ' }
                f.viewModel.updateChild(childEdited)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        nameInput.addTextChangedListener(nameTextChangedWatcher)

        val pronounInput = binding.pronounInput
        pronounInput.setText(child.pronoun)
        val pronounTextChangedWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                childEdited.pronoun = pronounInput.text.toString().trim { it <= ' ' }
                f.viewModel.updateChild(childEdited)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        pronounInput.addTextChangedListener(pronounTextChangedWatcher)

        binding.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(f.requireContext())
            builder.setMessage(R.string.are_you_sure)
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { dialog, id ->
                    f.viewModel.deleteChild(childEdited)

                    // TODO prüfen ob das korrekt funktioniert...
                    dialog.dismiss()
                    f.childrenLayout.removeView(binding.root)
                }
                .setNegativeButton(R.string.no) { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        if (setFocus) {
            nameInput.isFocusableInTouchMode = true
            nameInput.requestFocus()
            f.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        f.childrenLayout.addView(binding.root)
    }

}