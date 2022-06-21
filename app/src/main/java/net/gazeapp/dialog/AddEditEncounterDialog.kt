package net.gazeapp.dialog

import android.app.Activity
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import net.gazeapp.R
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.Encounter
import net.gazeapp.databinding.AddEditEncounterLayoutCBinding
import net.gazeapp.event.EncounterAddedEditedEvent
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.SpecificValues
import org.greenrobot.eventbus.EventBus
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * Dialog to Add or Edit EncounterEntity.
 * Accessible through Edit Contact mode (for edit or add) or to add a new work directly from the Contact Detail View through the floating action button.
 * Created by taar1 on 12.03.2017, updated 30.01.2022
 */
class AddEditEncounterDialog(
    private val encounter: Encounter,
    private val contact: Contact,
    private val activity: Activity,
    private val isEditMode: Boolean,
    private val fragmentManager: FragmentManager
) {
    @Inject
    lateinit var tools: GazeTools

    lateinit var meetDateInput: TextInputEditText
    lateinit var deleteMyRole: ImageView
    lateinit var myRoleInput: TextInputEditText
    lateinit var deleteLocation: ImageView
    lateinit var locationInput: TextInputEditText
    lateinit var deleteMyLoad: ImageView
    lateinit var myLoadInput: TextInputEditText
    lateinit var safeSexSwitch: SwitchCompat
    lateinit var deleteNotes: ImageView
    lateinit var notesInput: TextInputEditText
    lateinit var ratingBar: RatingBar
    lateinit var deleteHisRole: ImageView
    lateinit var hisRoleInput: TextInputEditText
    lateinit var deleteHisLoad: ImageView
    lateinit var hisLoadInput: TextInputEditText

    lateinit var binding: AddEditEncounterLayoutCBinding

    companion object {
        private const val TAG = "AddEditEncounterDialog"
    }

    fun showDialog() {
        val inflater = activity.layoutInflater
        binding = AddEditEncounterLayoutCBinding.bind(
            inflater.inflate(
                R.layout.add_edit_encounter_layout_c,
                null
            )
        )

        bindViews()

        // Used if the user clicks CANCEL after editing some fields already.
        //final EncounterEntity originalEncounterEntity = (EncounterEntity) encounter.clone();
        val encounterRoles = activity.resources.getStringArray(R.array.encounter_role)

        val builder = AlertDialog.Builder(
            activity, R.style.GazeTheme
        )
        builder.setView(binding.root)
        builder.setTitle(activity.getString(R.string.edit_encounter).uppercase(Locale.getDefault()))
        builder.setCancelable(false)

        if (!isEditMode) {
            encounter.contactId = contact.id
            encounter.meetDate = Date()
            builder.setTitle(
                activity.getString(R.string.add_new_encounter)
                    .uppercase(Locale.getDefault())
            )
            builder.setPositiveButton(R.string.save) { dialog: DialogInterface?, which: Int ->
                EventBus.getDefault().post(
                    EncounterAddedEditedEvent(
                        encounter, EncounterAddedEditedEvent.Action.ADD
                    )
                )
            }
            builder.setNegativeButton(R.string.cancel) { dialog: DialogInterface?, which: Int -> }
        } else {
            builder.setPositiveButton(R.string.save) { dialog: DialogInterface?, which: Int ->
                EventBus.getDefault().post(
                    EncounterAddedEditedEvent(
                        encounter, EncounterAddedEditedEvent.Action.UPDATE
                    )
                )
            }
            builder.setNeutralButton(R.string.delete) { dialog: DialogInterface?, which: Int ->
                // User clicked DELETE.
                EventBus.getDefault().post(
                    EncounterAddedEditedEvent(
                        encounter,
                        EncounterAddedEditedEvent.Action.DELETE
                    )
                )
            }
            builder.setNegativeButton(R.string.cancel) { dialog: DialogInterface?, which: Int ->
                // User clicked CANCEL. Just close the dialog.
                EventBus.getDefault().post(
                    EncounterAddedEditedEvent(
                        encounter,
                        EncounterAddedEditedEvent.Action.CANCEL
                    )
                )
            }
        }

        val dialog = builder.create()
        dialog.show()

        meetDateInput.setText(tools.formatDateToPhoneLocale(Date(), true))
        meetDateInput.setOnClickListener {
            val now = Calendar.getInstance()
            var yearPreselect = now[Calendar.YEAR]
            var monthPreselect = now[Calendar.MONTH]
            var dayPreselect = now[Calendar.DAY_OF_MONTH]
            val meetDate = encounter.meetDate

            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val meetDateSplits = sdf.format(meetDate).split("/").toTypedArray()
            dayPreselect = meetDateSplits[0].toInt()
            monthPreselect = meetDateSplits[1].toInt() - 1
            yearPreselect = meetDateSplits[2].toInt()

            val dpd = DatePickerDialog.newInstance(
                { _: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val dateStr = dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                    val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
                    var formattedDate: String? =
                        tools.formatDateToPhoneLocale(dateStr)
                    try {
                        val parsedEncounterEntityDate = dateFormatter.parse(dateStr)
                        encounter.meetDate = parsedEncounterEntityDate
                        formattedDate = tools.formatDateToPhoneLocale(
                            parsedEncounterEntityDate,
                            true
                        )
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    meetDateInput.setText(formattedDate)
                }, yearPreselect, monthPreselect, dayPreselect
            )
            dpd.show(fragmentManager, "MeetDatePickerDialog")
        }


        val locationInputWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                encounter.meetLocation = s.toString().trim { it <= ' ' }
                if (locationInput.text!!.isNotEmpty()) {
                    deleteLocation.visibility = View.VISIBLE
                } else {
                    deleteLocation.visibility = View.INVISIBLE
                }
            }
        }
        locationInput.addTextChangedListener(locationInputWatcher)

        deleteLocation.setOnClickListener {
            encounter.meetLocation = null
            locationInput.setText("")
            deleteLocation.visibility = View.INVISIBLE
        }

        val rolesArray = activity.resources.getStringArray(R.array.encounter_role)
        val adapter = ArrayAdapter(
            activity,
            android.R.layout.simple_list_item_1, android.R.id.text1, rolesArray
        )

        myRoleInput.setOnClickListener { v: View? ->
            val builder12 = AlertDialog.Builder(
                activity, R.style.GazeTheme
            )
            builder12.setTitle(activity.getString(R.string.my_role).uppercase(Locale.getDefault()))
            builder12.setAdapter(adapter) { dialog1: DialogInterface?, which: Int ->
                myRoleInput.setText(
                    encounterRoles[which]
                )
                deleteMyRole.visibility = View.VISIBLE
                encounter.myRole = encounterRoles[which]
            }
            val dialog14 = builder12.create()
            dialog14.show()
        }

        deleteMyRole.setOnClickListener {
            encounter.myRole = null
            myRoleInput.setText("")
            deleteMyRole.visibility = View.GONE
        }

        hisRoleInput.setOnClickListener { v: View? ->
            val builder1 = AlertDialog.Builder(
                activity, R.style.GazeTheme
            )
            builder1.setTitle(activity.getString(R.string.his_role).uppercase(Locale.getDefault()))
            builder1.setAdapter(adapter) { dialog12: DialogInterface?, which: Int ->
                hisRoleInput.setText(
                    encounterRoles[which]
                )
                deleteHisRole.visibility = View.VISIBLE
                encounter.hisRole = encounterRoles[which]
            }
            val dialog13 = builder1.create()
            dialog13.show()
        }

        deleteHisRole.setOnClickListener {
            encounter.hisRole = null
            hisRoleInput.setText("")
            deleteHisRole.visibility = View.GONE
        }

        val myLoadInputWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                encounter.myLoadWentTo = s.toString().trim { it <= ' ' }
                if (myLoadInput.text!!.isNotEmpty()) {
                    deleteMyLoad.visibility = View.VISIBLE
                } else {
                    deleteMyLoad.visibility = View.GONE
                }
            }
        }
        myLoadInput.addTextChangedListener(myLoadInputWatcher)
        deleteMyLoad.setOnClickListener {
            encounter.myLoadWentTo = null
            myLoadInput.setText("")
            deleteMyLoad.visibility = View.GONE
        }

        val hisLoadInputWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                encounter.hisLoadWentTo = s.toString().trim { it <= ' ' }
                if (hisLoadInput.text!!.isNotEmpty()) {
                    deleteHisLoad.visibility = View.VISIBLE
                } else {
                    deleteHisLoad.visibility = View.INVISIBLE
                }
            }
        }
        hisLoadInput.addTextChangedListener(hisLoadInputWatcher)
        deleteHisLoad.setOnClickListener {
            encounter.hisLoadWentTo = null
            hisLoadInput.setText("")
            deleteHisLoad.visibility = View.INVISIBLE
        }

        safeSexSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            // TODO add "unsure" checkbox.
            encounter.isSafeSex = isChecked
        }
        // TODO add "unsure" checkbox.

        ratingBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar1: RatingBar?, rating: Float, fromUser: Boolean ->
                Log.d(TAG, "EncounterEntity Rating: $rating")
                encounter.rating = (rating * 2).roundToInt()
            }

        val notesInputWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Currently not needed
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                encounter.remarks = s.toString().trim { it <= ' ' }
                if (notesInput.text!!.isNotEmpty()) {
                    deleteNotes.visibility = View.VISIBLE
                } else {
                    deleteNotes.visibility = View.INVISIBLE
                }
            }
        }
        notesInput.addTextChangedListener(notesInputWatcher)
        deleteNotes.setOnClickListener {
            encounter.remarks = null
            notesInput.setText("")
            deleteNotes.visibility = View.INVISIBLE
        }

        if (isEditMode) {
            meetDateInput.setText(
                tools.formatDateToPhoneLocale(
                    encounter.meetDate,
                    true
                )
            )
            if (encounter.meetLocation != null && encounter.meetLocation!!.isNotEmpty()) {
                locationInput.setText(encounter.meetLocation)
                deleteLocation.visibility = View.VISIBLE
            }
            if (encounter.myRole != null && encounter.myRole!!.isNotEmpty()) {
                myRoleInput.setText(encounter.myRole)
                deleteMyRole.visibility = View.VISIBLE
            }
            if (encounter.hisRole != null && encounter.hisRole!!.isNotEmpty()) {
                hisRoleInput.setText(encounter.hisRole)
                deleteHisRole.visibility = View.VISIBLE
            }
            if (encounter.myLoadWentTo != null && encounter.myLoadWentTo!!.isNotEmpty()) {
                myLoadInput.setText(encounter.myLoadWentTo)
                deleteMyLoad.visibility = View.VISIBLE
            }
            if (encounter.hisLoadWentTo != null && encounter.hisLoadWentTo!!.isNotEmpty()) {
                hisLoadInput.setText(encounter.hisLoadWentTo)
                deleteHisLoad.visibility = View.VISIBLE
            }

            // TODO add "UNSURE" checkbox
            safeSexSwitch.isChecked = encounter.isSafeSex

//            if (encounter.isSafeSex() == 3) {
            // TODO add "unsure" checkbox
//            }

            val ratingAsFloat = encounter.rating.toFloat() / 2
            ratingBar.rating = ratingAsFloat
            if (encounter.remarks != null && encounter.remarks!!.isNotEmpty()) {
                notesInput.setText(encounter.remarks)
                deleteNotes.visibility = View.VISIBLE
            }
        }

        if (!SpecificValues.SHOW_XRATED) {
            myRoleInput.visibility = View.GONE
            deleteMyRole.visibility = View.GONE
            hisRoleInput.visibility = View.GONE
            deleteHisRole.visibility = View.GONE
            myLoadInput.visibility = View.GONE
            deleteMyLoad.visibility = View.GONE
            hisLoadInput.visibility = View.GONE
            deleteHisLoad.visibility = View.GONE
        }
    }

    private fun bindViews() {
        meetDateInput = binding.meetDateInput
        deleteLocation = binding.deleteLocation
        locationInput = binding.locationInput
        deleteMyLoad = binding.deleteMyLoad
        myLoadInput = binding.myLoadInput
        safeSexSwitch = binding.safeSexSwitch
        deleteNotes = binding.deleteNotes
        notesInput = binding.notesInput
        ratingBar = binding.ratingBar
        deleteMyRole = binding.deleteMyRole
        myRoleInput = binding.myRoleInput
        deleteHisRole = binding.deleteHisRole
        hisRoleInput = binding.hisRoleInput
        deleteHisLoad = binding.deleteHisLoad
        hisLoadInput = binding.hisLoadInput
    }
}