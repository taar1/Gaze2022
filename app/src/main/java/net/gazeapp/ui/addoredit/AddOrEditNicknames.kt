package net.gazeapp.ui.addoredit

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import net.gazeapp.data.model.Nickname
import net.gazeapp.databinding.IncludeAddNicknameSnippetBinding

class AddOrEditNicknames(
    val f: AddOrEditContactFragment
) {
    companion object {
        private const val TAG = "AddOrEditNicknames"
    }

    fun addNicknameLayout(n: Nickname, setFocus: Boolean) {
        f.nicknameLayout.visibility = View.VISIBLE
        n.contactId = f.contactWithDetails.contact.id

        val binding = IncludeAddNicknameSnippetBinding.bind(f.viewBinding.scrollView)

        val nLayout = binding.nicknameLayout

        val nicknameInput = binding.nicknameInput
        nicknameInput.apply {
            setText(n.nickname)
            if (setFocus) {
                isFocusableInTouchMode = true
                requestFocus()
                f.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }
        }

        val deleteNickname = binding.deleteNickname
        deleteNickname.setOnClickListener {
            Log.d(TAG, "Remove NicknameEntity: " + n.nickname)

            f.viewModel.deleteNickname(n)
            nLayout.visibility = View.GONE
        }

        val nicknameTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                n.nickname = nicknameInput.text.toString().trim()

                f.viewModel.updateNickname(n)
            }
        }
        nicknameInput.addTextChangedListener(nicknameTextWatcher)
        f.nicknameLayout.addView(binding.root)
    }
}