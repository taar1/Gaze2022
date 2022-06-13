package net.gazeapp.ui.addoredit

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import net.gazeapp.R
import net.gazeapp.data.dto.ClubDto
import net.gazeapp.data.model.Club
import net.gazeapp.databinding.IncludeAddClubSnippetBinding

class AddOrEditClub(
    val f: AddOrEditContactFragment
) {
    fun displayClubList(clubList: List<ClubDto>?) {
        f.clubsLayout.removeAllViews()
        f.clubsLayout.visibility = View.INVISIBLE

        clubList?.let {
            f.clubsLayout.visibility = View.VISIBLE

            for (club in clubList) {
                addClubLayoutByDto(club, false)
            }
        }
    }

    fun addClubLayoutByDto(club: ClubDto, setFocus: Boolean) {
        val binding = IncludeAddClubSnippetBinding.bind(f.viewBinding.scrollView)

        val clubEdited = Club(club.contactId)
        clubEdited.id = club.id

        val nameInput = binding.nameInput
        nameInput.setText(club.clubName)

        val addressInput = binding.addressInput
        addressInput.setText(club.address)

        val emailInput = binding.emailInput
        emailInput.setText(club.email)

        val phoneInput = binding.phoneInput
        phoneInput.setText(club.phoneNumber)

        val websiteInput = binding.websiteInput
        websiteInput.setText(club.url)

        // TODO
        // TODO
        // TODO
        // TODO
        val nameTextChangedWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                clubEdited.clubName = nameInput.text.toString().trim { it <= ' ' }
                f.viewModel.updateClub(clubEdited)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        nameInput.addTextChangedListener(nameTextChangedWatcher)

        binding.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(f.requireContext())
            builder.setMessage(R.string.are_you_sure)
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    f.viewModel.deleteClub(clubEdited)

                    // TODO prüfen ob das korrekt funktioniert...
                    dialog.dismiss()
                    f.childrenLayout.removeView(binding.root)
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
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

        f.clubsLayout.addView(binding.root)
    }

}