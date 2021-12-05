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
        // TODO - 3. Request the Person data from the ContentProviderDemo app
        // this requires the READ permission and does not require opening the ContentProviderDemo app
        // TODO - 4. Loop over the data, create a Person object, insert into the _people list
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

        TODO("7. Launch the PersonPicker activity")
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
        TODO("5. Create the PersonPicker intent")
    }


    override fun parseResult(resultCode: Int, result: Intent?) : Person? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        TODO("6. Extract the person data from the result and return a Person object")
    }
}

data class Person(var name: String, var age: Int)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LabsAppTheme {
    }
}