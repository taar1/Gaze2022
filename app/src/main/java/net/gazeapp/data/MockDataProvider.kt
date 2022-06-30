package net.gazeapp.data

import net.gazeapp.data.model.Contact
import java.util.*

object MockDataProvider {

    val contactList = listOf(
        Contact(
            id = 1,
            isMe = false,
            contactName = "Peter",
            additionalInfo = "Aus Zürich",
            showMainPic = true,
            isMetInPerson = true,
            metInPersonDate = Date(),
            created = Date()
        ),
        Contact(
            id = 2,
            isMe = false,
            contactName = "Sandro",
            additionalInfo = "noch nie getroffen",
            showMainPic = true,
            isMetInPerson = false,
            metInPersonDate = Date(),
            created = Date()
        ),
        Contact(
            id = 3,
            isMe = false,
            contactName = "Nicolas",
            additionalInfo = "2 Meter gross",
            showMainPic = true,
            isMetInPerson = false,
            metInPersonDate = Date(),
            created = Date()
        ),
        Contact(
            id = 4,
            isMe = false,
            contactName = "André",
            additionalInfo = "aus Griechenland",
            showMainPic = true,
            isMetInPerson = true,
            metInPersonDate = Date(),
            created = Date()
        ),


        Contact(
            id = 5,
            isMe = false,
            contactName = "Hans",
            additionalInfo = "asdfasdfasdf",
            showMainPic = true,
            isMetInPerson = true,
            metInPersonDate = Date(),
            created = Date()
        ),
        Contact(
            id = 6,
            isMe = false,
            contactName = "Manuel",
            additionalInfo = "wasdfasdfasdd",
            showMainPic = true,
            isMetInPerson = true,
            metInPersonDate = Date(),
            created = Date()
        ),
        Contact(
            id = 7,
            isMe = false,
            contactName = "Goliath",
            additionalInfo = "ycxcv cxxcvx xvycxvy",
            showMainPic = true,
            isMetInPerson = true,
            metInPersonDate = Date(),
            created = Date()
        ),
        Contact(
            id = 8,
            isMe = false,
            contactName = "James",
            additionalInfo = "jurtsdfg  adsf adsfsdf",
            showMainPic = true,
            isMetInPerson = true,
            metInPersonDate = Date(),
            created = Date()
        ),
        Contact(
            id = 9,
            isMe = false,
            contactName = "Rudolfo",
            additionalInfo = "msrdtsdfgsdfg adsfadsf asdf asd",
            showMainPic = true,
            isMetInPerson = true,
            metInPersonDate = Date(),
            created = Date()
        )
    )

}