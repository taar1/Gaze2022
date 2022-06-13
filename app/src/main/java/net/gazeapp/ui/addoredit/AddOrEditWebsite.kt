package net.gazeapp.ui.addoredit

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import net.gazeapp.data.model.Website
import net.gazeapp.databinding.IncludeAddWebsiteSnippetBinding

class AddOrEditWebsite(
    val f: AddOrEditContactFragment
) {
    companion object {
        private const val TAG = "AddOrEditWebsite"
    }

    fun addWebsiteLayout(w: Website, setFocus: Boolean) {
        f.websitesLayout.visibility = View.VISIBLE

        w.contactId = f.contactWithDetails.contact.id

        val binding = IncludeAddWebsiteSnippetBinding.bind(f.viewBinding.scrollView)
        val websiteLayout: ConstraintLayout = binding.websiteLayout
        val websiteInput: TextInputEditText = binding.websiteInput

        websiteInput.setText(w.website)
        if (setFocus) {
            websiteInput.isFocusableInTouchMode = true
            websiteInput.requestFocus()
            f.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        val deleteWebsite: ImageView = binding.deleteWebsite
        deleteWebsite.setOnClickListener {
            Log.d(TAG, "Remove WebsiteEntity: " + w.website)
            f.viewModel.deleteWebsite(w)
            f.websitesLayout.visibility = View.GONE
        }

        val websiteTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d(TAG, "Update WebsiteEntity: " + w.website)
                w.website = websiteInput.text.toString().trim { it <= ' ' }
                if (websiteInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                    deleteWebsite.visibility = View.VISIBLE
                } else {
                    deleteWebsite.visibility = View.INVISIBLE
                }

                f.viewModel.updateWebsite(w)
            }
        }
        websiteInput.addTextChangedListener(websiteTextWatcher)
        websiteLayout.addView(binding.root)
    }
}