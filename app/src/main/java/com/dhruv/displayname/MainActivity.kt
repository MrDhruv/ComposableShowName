package com.dhruv.displayname

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruv.displayname.ui.theme.DisplayNameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisplayNameTheme {
                MyAPP()
            }
        }
    }
}
//As a best practice, your function should include a Modifier parameter
// that is assigned an empty Modifier by default.
@Composable
fun DisplayName(name: String,modifier: Modifier=Modifier) {
    //To preserve state across recompositions, remember the mutable state using remember
   // val isExpanded=remember{mutableStateOf(false)}

    //using a by keyword instead of the =. This is a property delegate
    // that saves you from typing .value every time.
    //var isExpanded by remember{mutableStateOf(false)}

    //Persisting the expanded state of the list items
    var  isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
   // val extraPadding= if (isExpanded.value) 48.dp else 0.dp
   // val extraPadding= if (isExpanded) 48.dp else 0.dp
    val extraPadding by animateDpAsState(
        if (isExpanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )


    Surface(modifier=modifier.padding(vertical = 4.dp, horizontal = 8.dp), color = MaterialTheme.colorScheme.primary) {
       Row(modifier= modifier.padding(24.dp)) {
           Column(modifier = modifier
               .weight(1f)
               .padding(bottom = extraPadding)) {
               Text(text = "Hello", style = MaterialTheme.typography.headlineMedium)
               Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(
                   fontWeight = FontWeight.ExtraBold
               ))
           }

          /* ElevatedButton(onClick = { isExpanded.value=!isExpanded.value }) {
               Text(if(isExpanded.value) "Show Less" else "Show More")
           }*/

           /*ElevatedButton(onClick = { isExpanded=!isExpanded }) {
               Text(if(isExpanded) "Show Less" else "Show More")
           }*/

           IconButton(onClick = { isExpanded=!isExpanded }) {
               Icon(imageVector = if(isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                   contentDescription = if (isExpanded) stringResource(R.string.show_less) else stringResource(R.string.show_more)
                 )
           }
       }

    }

}
/*@Composable
fun DisplayUI(modifier: Modifier=Modifier){
    Surface(modifier = modifier , color = MaterialTheme.colorScheme.primary) {
        DisplayName(name = "DHRUV")
    }

}*/

/*@Composable
fun DisplayList(
    modifier: Modifier=Modifier,
    names: List<String> = listOf("World", "Compose")){
    Column(modifier=modifier.padding(vertical = 4.dp)
    ) {
        for (name in names){
            DisplayName(name = name)
        }
    }

}*/

@Composable
fun DisplayList(modifier: Modifier=Modifier,
                names: List<String> =List(100){ "$it" }){
    LazyColumn(modifier=modifier.padding(vertical = 4.dp)){
        items(items=names){
            name-> DisplayName(name = name)
        }

    }

}
@Composable
fun MyAPP(){
    /*var shouldShowOnBoarding by remember {
        mutableStateOf(true)
    }*/
    //Persisting the onboarding screen state
    var shouldShowOnBoarding by rememberSaveable {
        mutableStateOf(true)
    }
    if(shouldShowOnBoarding){
        //By passing a function and not a state to WelcomeScreen we are making this
        // composable more reusable and protecting the state from being mutated by
        // other composables
        WelcomeScreen(onContinueClicked={shouldShowOnBoarding=false})
    }else{
        DisplayList()
    }

}
//To display different screen  ,
/*
Now we can add this new onboarding screen to our app. We want to show it on launch and then
hide it when the user presses "Continue".

In Compose you don't hide UI elements. Instead, you simply don't add them to the composition,
so they're not added to the UI tree that Compose generates
*/

@Composable
fun WelcomeScreen(onContinueClicked:()->Unit,modifier: Modifier=Modifier){
    Column(modifier=modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Welecome to Boarding Screen")
        Button(modifier = modifier.padding(24.dp),
            onClick = onContinueClicked) {
            Text(text = "Continue")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUI() {
    DisplayNameTheme {
        MyAPP()
    }
}