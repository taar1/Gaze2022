package net.gazeapp.ui.addoredit

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import net.gazeapp.data.model.Email
import net.gazeapp.databinding.IncludeAddEmailSnippetBinding

class AddOrEditEmails(
    val f: AddOrEditContactFragment
) {
    fun addEmailLayout(e: Email, setFocus: Boolean) {
        f.emailLayout.visibility = View.VISIBLE

        e.contactId = f.contactWithDetails.contact.id

//        val inflater =
//            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val v = inflater.inflate(R.layout.include_add_email_snippet, viewBinding.scrollView)
//        val emailInput: TextInputEditText = v.findViewById(R.id.email_input)

        val binding = IncludeAddEmailSnippetBinding.bind(f.viewBinding.scrollView)
        val emailInput = binding.emailInput

        emailInput.setText(e.email)

        if (setFocus) {
            emailInput.isFocusableInTouchMode = true
            emailInput.requestFocus()
            f.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        //val emailTypeSpinner = v.findViewById<Spinner>(R.id.email_type_spinner)
        val emailTypeSpinner = binding.emailTypeSpinner
        emailTypeSpinner.setSelection(f.getIndexOfSpinner(emailTypeSpinner, e.emailType))

        val emailTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                e.email = emailInput.text.toString().trim { it <= ' ' }
                e.emailType = emailTypeSpinner.selectedItem.toString()

                f.viewModel.modifyEmail(e)
            }
        }
        emailInput.addTextChangedListener(emailTextWatcher)

        emailTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val spinnerValue = emailTypeSpinner.selectedItem.toString()
                e.emailType = spinnerValue
                f.viewModel.modifyEmail(e)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                return
            }
        }
        f.emailLayout.addView(binding.root)
    }
}