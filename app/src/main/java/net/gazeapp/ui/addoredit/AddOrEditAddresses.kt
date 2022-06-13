package net.gazeapp.ui.addoredit

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import net.gazeapp.data.model.Address
import net.gazeapp.databinding.IncludeAddAddressSnippetBinding

class AddOrEditAddresses(
    val f: AddOrEditContactFragment
) {
    companion object {
        private const val TAG = "AddOrEditAddresses"
    }

//    fun displayAddresses(addressList: List<AddressDto>?) {
//        f.addressLayout.removeAllViews()
//        f.addressLayout.visibility = View.INVISIBLE
//
//        addressList?.let {
//            f.addressLayout.visibility = View.VISIBLE
//
//            for (address in addressList) {
//                addAddressLayoutByDto(address, false)
//            }
//        }
//    }

    fun displayAddresses(addressList: List<Address>?) {
        f.addressLayout.removeAllViews()
        f.addressLayout.visibility = View.INVISIBLE

        addressList?.let {
            f.addressLayout.visibility = View.VISIBLE

            for (address in addressList) {
                addAddressLayout(address, false)
            }
        }
    }

    fun addAddressLayout(address: Address, setFocus: Boolean) {
        val binding = IncludeAddAddressSnippetBinding.bind(f.viewBinding.scrollView)

        val addressEdited = Address(address.contactId)
        addressEdited.id = address.id

        val addressInput = binding.addressInput
        addressInput.setText(address.address)

        if (setFocus) {
            addressInput.isFocusableInTouchMode = true
            addressInput.requestFocus()
            f.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        val addressTypeSpinner = binding.addressTypeSpinner
        addressTypeSpinner.setSelection(
            f.getIndexOfSpinner(
                addressTypeSpinner,
                address.addressType
            )
        )

        val addressTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                addressEdited.address = addressInput.text.toString().trim { it <= ' ' }
                addressEdited.addressType = addressTypeSpinner.selectedItem.toString()

                // TODO FIXME wie kann man eine adresse löschen?
                // TODO FIXME wie kann man eine adresse löschen?
                f.viewModel.updateAddress(addressEdited)
            }
        }
        addressInput.addTextChangedListener(addressTextWatcher)

        // TODO
        //changeAddressLeftIcon(a.addressType, addressInput)

        addressTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long
            ) {
                addressEdited.addressType = addressTypeSpinner.selectedItem.toString()

                // TODO
                //changeAddressLeftIcon(a.addressType, addressInput)
                f.viewModel.updateAddress(addressEdited)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                return
            }
        }
        f.addressLayout.addView(binding.root)
    }

//    fun addAddressLayoutByDto(address: AddressDto, setFocus: Boolean) {
//        val binding = IncludeAddAddressSnippetBinding.bind(f.viewBinding.scrollView)
//
//        val addressEdited = Address(address.contactId)
//        addressEdited.id = address.id
//
//        val addressInput = binding.addressInput
//        addressInput.setText(address.addressStr)
//
//        if (setFocus) {
//            addressInput.isFocusableInTouchMode = true
//            addressInput.requestFocus()
//            f.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
//        }
//
//        val addressTypeSpinner = binding.addressTypeSpinner
//        addressTypeSpinner.setSelection(
//            f.getIndexOfSpinner(
//                addressTypeSpinner,
//                address.addressType
//            )
//        )
//
//        val addressTextWatcher: TextWatcher = object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {
//                // Currently not needed
//            }
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                addressEdited.address = addressInput.text.toString().trim { it <= ' ' }
//                addressEdited.addressType = addressTypeSpinner.selectedItem.toString()
//
//                // TODO FIXME wie kann man eine adresse löschen?
//                // TODO FIXME wie kann man eine adresse löschen?
//                f.viewModel.updateAddress(addressEdited)
//            }
//        }
//        addressInput.addTextChangedListener(addressTextWatcher)
//
//        // TODO
//        //changeAddressLeftIcon(a.addressType, addressInput)
//
//        addressTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long
//            ) {
//                addressEdited.addressType = addressTypeSpinner.selectedItem.toString()
//
//                // TODO
//                //changeAddressLeftIcon(a.addressType, addressInput)
//                f.viewModel.updateAddress(addressEdited)
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//                return
//            }
//        }
//        f.addressLayout.addView(binding.root)
//    }
}