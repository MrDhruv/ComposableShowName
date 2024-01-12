package com.dhruv.displayname.tutorial5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruv.displayname.R
import com.dhruv.displayname.ui.theme.DisplayNameTheme

class ExpandCollapseListActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisplayNameTheme {
                MyAPP()
            }
        }
    }

} //End of Activity
@Composable
fun DisplayNameUsingCard(name: String,modifier: Modifier = Modifier){
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        CardContent(name)
    }
}

//As a best practice, your function should include a Modifier parameter
// that is assigned an empty Modifier by default.
@Composable
fun CardContent(name: String) {

    //Persisting the expanded state of the list items
    var  isExpanded by rememberSaveable {
        mutableStateOf(false)
    }



    Row(
        modifier = Modifier
            .padding(24.dp)
            .animateContentSize(
                animationSpec = spring(
                    Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)

        ) {
            Text(text = "Hello", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if(isExpanded){
                Text(text = ("Showing content details here ...").repeat(5))
            }
        }


            IconButton(onClick = { isExpanded=!isExpanded }) {
                Icon(imageVector = if(isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (isExpanded) stringResource(R.string.show_less) else stringResource(
                        R.string.show_more)
                )
            }
        }



}

@Composable
fun DisplayList(modifier: Modifier = Modifier,
                names: List<String> =List(100){ "$it" }){
    LazyColumn(modifier=modifier.padding(vertical = 4.dp)){
        items(items=names){
                name-> DisplayNameUsingCard(name = name)
        }

    }

}
@Composable
fun MyAPP(){
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

@Composable
fun WelcomeScreen(onContinueClicked:()->Unit,modifier: Modifier = Modifier){
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