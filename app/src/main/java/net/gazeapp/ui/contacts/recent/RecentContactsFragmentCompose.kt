package net.gazeapp.ui.contacts.recent

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.gazeapp.data.MockDataProvider

@Preview
@Composable
fun RecentScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(colorResource(id = R.color.background_color))
//            .wrapContentSize(Alignment.Center)
//    ) {
//        Text(
//            text = "Recent Screen",
//            fontWeight = FontWeight.Bold,
//            color = Color.Green,
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            textAlign = TextAlign.Center,
//            fontSize = 20.sp
//        )
//    }

    val contacts = remember { MockDataProvider.contactList }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = contacts,
            itemContent = {
                ContactListItem(contact = it)
            })
    }
}