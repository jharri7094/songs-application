package edu.towson.cosc435.valis.labsapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.towson.cosc435.valis.labsapp.ui.theme.LabsAppTheme

class MainActivity : ComponentActivity() {

    private var _people by mutableStateOf(listOf<Person>())
    private var _selectedPerson by mutableStateOf<Person?>(null)

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = PersonContract.Person.CONTENT_URI
        val cursor: Cursor? = this.contentResolver.query(
            uri,
            PersonContract.Person.PROJECTION_ALL,
            null,
            null,
            PersonContract.Person.SORT_ORDER_DEFAULT
        )
        // this requires the READ permission and does not require opening the ContentProviderDemo app

        if(cursor != null) {
            if(cursor.count > 0){
                while(cursor.moveToNext()){
                    //pull out the name and age
                    val name: String
                    val age: Int

                    name = cursor.getString(cursor.getColumnIndex(PersonContract.Person.NAME))
                    age = cursor.getInt(cursor.getColumnIndex(PersonContract.Person.AGE))

                    val person = Person(name, age)
                    _people = _people + listOf(person)
                }
            }
        }

        setContent {
            LabsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = { TopBar() }
                    ) {
                        MainScreen(selectedPerson = _selectedPerson, people = _people)
                    }
                }
            }
        }

        val pickPersonRequest = registerForActivityResult(PickPerson() ){ person ->
            _selectedPerson = person
        }

        pickPersonRequest.launch(Unit)

    }
}

@Composable
fun MainScreen(
    selectedPerson: Person?,
    people: List<Person>
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        if(selectedPerson != null) {
            Text(
                "You selected: ${selectedPerson.name}",
                fontSize = 24.sp
            )
        }
        LazyColumn(
            modifier = Modifier.padding(top=24.dp)
        ) {
            items(people) { person ->
                Row {
                    Text(person.name, fontSize = 20.sp)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(person.age.toString(), fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    TopAppBar(contentPadding= PaddingValues(start=16.dp)) {
        Text("Person Picker", fontSize=24.sp)
    }
}

class PickPerson : ActivityResultContract<Unit, Person?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = PersonContract.Person.CONTENT_TYPE
        return intent
    }


    override fun parseResult(resultCode: Int, result: Intent?) : Person? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }

        val name = result?.extras?.getString(PersonContract.PERSON_NAME_EXTRA, "default")
        val age = result?.extras?.getInt(PersonContract.PERSON_AGE_EXTRA, 0)

        if(name != null && age != null){
            return Person(name, age)
        }else{
            return null
        }
    }
}

data class Person(var name: String, var age: Int)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LabsAppTheme {
    }
}