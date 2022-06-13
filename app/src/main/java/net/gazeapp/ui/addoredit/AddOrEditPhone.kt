package net.gazeapp.ui.addoredit

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import com.google.android.material.textfield.TextInputEditText
import net.gazeapp.data.model.Phone
import net.gazeapp.databinding.IncludeAddPhoneSnippetBinding

class AddOrEditPhone(
    val f: AddOrEditContactFragment
) {
    fun addPhoneLayout(p: Phone, setFocus: Boolean) {
        f.phoneLayout.visibility = View.VISIBLE

        p.contactId = f.contactWithDetails.contact.id

        val binding = IncludeAddPhoneSnippetBinding.bind(f.viewBinding.scrollView)

        val phoneNumberInput: TextInputEditText = binding.phoneInput
        phoneNumberInput.setText(p.phoneNumber)

        if (setFocus) {
            phoneNumberInput.isFocusableInTouchMode = true
            phoneNumberInput.requestFocus()
            f.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
        val phoneTypeSpinner = binding.phoneTypeSpinner
        phoneTypeSpinner.setSelection(f.getIndexOfSpinner(phoneTypeSpinner, p.phoneNumberType))

        val phoneTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                p.phoneNumber = phoneNumberInput.text.toString().trim { it <= ' ' }
                p.phoneNumberType = phoneTypeSpinner.selectedItem.toString()
                f.viewModel.updatePhone(p)
            }
        }
        phoneNumberInput.addTextChangedListener(phoneTextWatcher)
        phoneTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val spinnerValue = phoneTypeSpinner.selectedItem.toString()
                p.phoneNumberType = spinnerValue
                f.viewModel.deletePhone(p)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                return
            }
        }
        f.phoneLayout.addView(binding.root)
    }
}