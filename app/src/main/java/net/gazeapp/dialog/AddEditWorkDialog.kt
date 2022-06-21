package net.gazeapp.dialog

import android.app.Activity
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import net.gazeapp.R
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.Work
import net.gazeapp.databinding.IncludeAddWorkSnippetBinding
import net.gazeapp.event.WorkAddedEditedEvent
import net.gazeapp.utilities.GazeTools
import org.greenrobot.eventbus.EventBus
import java.util.*
import javax.inject.Inject

/**
 * Dialog to Add or Edit WorkEntity.
 * Accessible through Edit ContactEntity mode (for edit or add) or to add a new work directly from the ContactEntity Detail View through the floating action button.
 * Created by taar1 on 11.03.2017.
 */
class AddEditWorkDialog(
    private val work: Work,
    private val contact: Contact,
    private val activity: Activity,
    private val isEditMode: Boolean
) {
    @Inject
    lateinit var tools: GazeTools

    // TODO FIXME success toast message direkt hier drin anzeigen, da wir keine EVENTS mehr verwenden...
    // TODO FIXME success toast message direkt hier drin anzeigen, da wir keine EVENTS mehr verwenden...

    // TODO FIXME bzw snackbar.....
    //    tools.showMaterialSnackBar(
    //    requireActivity(), outerLayout,
    //    getString(R.string.success_work_added, work.profession),
    //    SnackBarType.SUCCESS

    lateinit var professionInput: TextInputEditText
    lateinit var deleteProfession: ImageView
    lateinit var employerInput: TextInputEditText
    lateinit var deleteEmployer: ImageView
    lateinit var employerAddressInput: TextInputEditText
    lateinit var deleteEmployerAddress: ImageView
    lateinit var phoneInput: TextInputEditText
    lateinit var deletePhone: ImageView
    lateinit var emailInput: TextInputEditText
    lateinit var deleteEmail: ImageView
    lateinit var startedInput: TextInputEditText
    lateinit var deleteStarted: ImageView
    lateinit var endedInput: TextInputEditText
    lateinit var deleteEnded: ImageView

    var binding: IncludeAddWorkSnippetBinding

    companion object {
        private const val TAG = "AddEditWorkDialog"
    }

    init {
        val inflater = activity.layoutInflater
        binding = IncludeAddWorkSnippetBinding.bind(
            inflater.inflate(
                R.layout.include_add_work_snippet,
                null
            )
        )

        bindViews()
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(
            activity, R.style.GazeTheme
        )
        builder.setView(binding.root)
        builder.setTitle(activity.getString(R.string.edit_work).uppercase(Locale.getDefault()))
        builder.setCancelable(false)

        if (!isEditMode) {
            work.contactId = contact.id
            builder.setTitle(
                activity.getString(R.string.add_new_work)
                    .uppercase(Locale.getDefault())
            )

            // ADD NEW WORK
            builder.setPositiveButton(R.string.save) { dialog, which ->
                // TODO EventBus loswerden...
                EventBus.getDefault()
                    .post(WorkAddedEditedEvent(work, WorkAddedEditedEvent.Action.ADD))
            }
            builder.setNegativeButton(R.string.cancel) { dialog, which ->
                // User clicked CANCEL. Just close the dialog.
            }
        } else {
            // EDIT WORK
            builder.setPositiveButton(R.string.save) { dialog, which ->
                // TODO EventBus loswerden...
                EventBus.getDefault()
                    .post(WorkAddedEditedEvent(work, WorkAddedEditedEvent.Action.UPDATE))
            }
            builder.setNeutralButton(R.string.delete) { dialog, which -> // User clicked DELETE.
                // TODO EventBus loswerden...
                EventBus.getDefault()
                    .post(WorkAddedEditedEvent(work, WorkAddedEditedEvent.Action.DELETE))
            }
            builder.setNegativeButton(R.string.cancel) { dialog, which -> // User clicked CANCEL. Just close the dialog.
                // TODO EventBus loswerden...
                EventBus.getDefault()
                    .post(WorkAddedEditedEvent(work, WorkAddedEditedEvent.Action.CANCEL))
            }
        }
        val dialog = builder.create()
        dialog.show()
        professionInput.setText(work.profession)
        professionInput.isFocusableInTouchMode = true

        deleteProfession.setOnClickListener {
            deleteProfession.visibility = View.INVISIBLE
            professionInput.setText("")
            work.profession = null
        }
        if (professionInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            deleteProfession.visibility = View.VISIBLE
        }
        val professionTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d(TAG, "Update Profession: " + work.profession)
                work.profession = professionInput.text.toString().trim { it <= ' ' }
                if (professionInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                    deleteProfession.visibility = View.VISIBLE
                } else {
                    deleteProfession.visibility = View.INVISIBLE
                }
            }
        }
        professionInput.addTextChangedListener(professionTextWatcher)
        employerInput.setText(work.employer)

        deleteEmployer.setOnClickListener {
            deleteEmployer.visibility = View.INVISIBLE
            employerInput.setText("")
            work.employer = null
        }
        if (employerInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            deleteEmployer.visibility = View.VISIBLE
        }
        val employerTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d(TAG, "Update Employer: " + work.employer)
                work.employer = employerInput.text.toString().trim { it <= ' ' }
                if (employerInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                    deleteEmployer.visibility = View.VISIBLE
                } else {
                    deleteEmployer.visibility = View.INVISIBLE
                }
            }
        }
        employerInput.addTextChangedListener(employerTextWatcher)
        employerAddressInput.setText(work.employerAddress)

        deleteEmployerAddress.setOnClickListener {
            deleteEmployerAddress.visibility = View.INVISIBLE
            employerAddressInput.setText("")
            work.employerAddress = null
        }
        if (employerAddressInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            deleteEmployerAddress.visibility = View.VISIBLE
        }
        val employerAddressTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d(TAG, "Update Employer Address: " + work.employer)
                work.employerAddress = employerAddressInput.text.toString().trim { it <= ' ' }
                if (employerAddressInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                    deleteEmployerAddress.visibility = View.VISIBLE
                } else {
                    deleteEmployerAddress.visibility = View.INVISIBLE
                }
            }
        }
        employerAddressInput.addTextChangedListener(employerAddressTextWatcher)
        phoneInput.setText(work.phone)

        deletePhone.setOnClickListener {
            deletePhone.visibility = View.INVISIBLE
            phoneInput.setText("")
            work.phone = null
        }
        if (phoneInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            deletePhone.visibility = View.VISIBLE
        }
        val phoneTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d(TAG, "Update Phone: " + work.phone)
                work.phone = phoneInput.text.toString().trim { it <= ' ' }
                if (phoneInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                    deletePhone.visibility = View.VISIBLE
                } else {
                    deletePhone.visibility = View.INVISIBLE
                }
            }
        }
        phoneInput.addTextChangedListener(phoneTextWatcher)
        emailInput.setText(work.email)

        deleteEmail.setOnClickListener {
            deleteEmail.visibility = View.INVISIBLE
            emailInput.setText("")
            work.email = null
        }
        if (emailInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            deleteEmail.visibility = View.VISIBLE
        }
        val emailTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d(TAG, "Update Email: " + work.phone)
                work.email = emailInput.text.toString().trim { it <= ' ' }
                if (emailInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                    deleteEmail.visibility = View.VISIBLE
                } else {
                    deleteEmail.visibility = View.INVISIBLE
                }
            }
        }
        emailInput.addTextChangedListener(emailTextWatcher)


        // MaterialNumberPicker: https://android-arsenal.com/details/1/3309
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]

        deleteStarted.setOnClickListener {
            startedInput.setText("")
            deleteStarted.visibility = View.INVISIBLE
            work.started = null
        }
        val started = work.started
        val startedCalender = Calendar.getInstance()
        if (started != null) {
            startedCalender.time = started
        }
        if (started != null) {
            startedInput.setText(
                tools.formatDateToPhoneLocaleMonthAndYearOnly(
                    work.started!!, true
                )
            )
        }
        if (startedInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            deleteStarted.visibility = View.VISIBLE
        }
        startedInput.setOnClickListener { view: View? ->
            val inflater12 = activity.layoutInflater
            val datePickerDialogView = inflater12.inflate(R.layout.month_year_date_picker, null)
            val builder12 = AlertDialog.Builder(
                activity, R.style.GazeTheme
            )
            builder12.setTitle(activity.getString(R.string.started).uppercase(Locale.getDefault()))
            builder12.setView(datePickerDialogView)
            builder12.setPositiveButton(R.string.ok) { dialog12, which ->
                deleteStarted.visibility = View.VISIBLE

                // TODO FIXME!! xxxxx date picker einbauen....
                // TODO FIXME!! xxxxx date picker einbauen....
                // TODO FIXME!! xxxxx date picker einbauen....
//                        MaterialNumberPicker monthPicker = datePickerDialogView.findViewById(R.id.mdtp_date_picker_month);
//                        MaterialNumberPicker yearPicker = datePickerDialogView.findViewById(R.id.mdtp_date_picker_year);
//
////                        MaterialNumberPicker monthPicker = datePickerDialogView.findViewById(R.id.picker_month);
////                        MaterialNumberPicker yearPicker = datePickerDialogView.findViewById(R.id.picker_year);
//                        Log.d(TAG, "startedInput click Positive: month: " + monthPicker.getValue() + " / year: " + yearPicker.getValue());
//
                val calStarted = Calendar.getInstance()
                //                        calStarted.set(Calendar.YEAR, yearPicker.getValue());
//                        calStarted.set(Calendar.MONTH, monthPicker.getValue() - 1);

                // Remove this...
                calStarted[Calendar.YEAR] = 2222
                calStarted[Calendar.MONTH] = 10
                startedCalender.time = calStarted.time
                work.started = calStarted.time
                startedInput.setText(
                    tools.formatDateToPhoneLocaleMonthAndYearOnly(
                        work.started!!, true
                    )
                )
            }
            builder12.setNegativeButton(R.string.cancel) { dialog12, which ->
                // User clicked CANCEL. Just close the dialog.
            }
            val dialog12 = builder12.create()
            dialog12.show()
        }

        deleteEnded.setOnClickListener { view: View? ->
            endedInput.setText("")
            deleteEnded.visibility = View.INVISIBLE
            work.ended = null
        }
        val ended = work.ended
        val endedCalender = Calendar.getInstance()
        if (ended != null) {
            endedCalender.time = ended
        }
        if (work.ended != null) {
            endedInput.setText(tools.formatDateToPhoneLocaleMonthAndYearOnly(work.ended!!, true))
        }
        if (endedInput.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            deleteEnded.visibility = View.VISIBLE
        }
        endedInput.setOnClickListener { view: View? ->
            val inflater1 = activity.layoutInflater
            val datePickerDialogView2 = inflater1.inflate(R.layout.month_year_date_picker, null)
            val builder1 = AlertDialog.Builder(
                activity, R.style.GazeTheme
            )
            builder1.setTitle(activity.getString(R.string.started).uppercase(Locale.getDefault()))
            builder1.setView(datePickerDialogView2)
            builder1.setPositiveButton(R.string.ok) { dialog1: DialogInterface?, which: Int ->
                deleteEnded.visibility = View.VISIBLE

                // TODO XXXXX replace with actual date picker
                // TODO XXXXX replace with actual date picker
//                MaterialNumberPicker monthPicker = datePickerDialogView2.findViewById(R.id.mdtp_date_picker_month);
//                MaterialNumberPicker yearPicker = datePickerDialogView2.findViewById(R.id.mdtp_date_picker_year);
//                Log.d(TAG, "endedInput click Positive: month: " + monthPicker.getValue() + " / year: " + yearPicker.getValue());
                val calEnded = Calendar.getInstance()
                //                calEnded.set(Calendar.YEAR, yearPicker.getValue());
//                calEnded.set(Calendar.MONTH, monthPicker.getValue() - 1);

                // Remove this...
                calEnded[Calendar.YEAR] = 2222
                calEnded[Calendar.MONTH] = 10
                endedCalender.time = calEnded.time
                work.ended = calEnded.time
                endedInput.setText(
                    tools.formatDateToPhoneLocaleMonthAndYearOnly(
                        work.ended!!,
                        true
                    )
                )
            }
            builder1.setNegativeButton(R.string.cancel) { _, _ ->
                // User clicked CANCEL. Just close the dialog.
            }
            val dialog1 = builder1.create()
            dialog1.show()
        }
    }

    private fun bindViews() {
        professionInput = binding.nameInput
        deleteProfession = binding.deleteProfession
        employerInput = binding.employerInput
        deleteEmployer = binding.deleteEmployer
        employerAddressInput = binding.employerAddressInput
        deleteEmployerAddress = binding.deleteEmployerAddress
        phoneInput = binding.workPhoneInput
        deletePhone = binding.deleteWorkPhone
        emailInput = binding.workEmailInput
        deleteEmail = binding.deleteWorkEmail
        startedInput = binding.startedInput
        deleteStarted = binding.deleteStarted
        endedInput = binding.endedInput
        deleteEnded = binding.deleteEnded
    }
}