@file:Suppress("CanBeVal")

package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.material.icons.filled.Menu
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.myapplication.api.ApiConnection
import com.example.myapplication.model.Persona
import com.example.myapplication.model.Registro
import com.example.myapplication.ui.MyApplicationTheme
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun App() {
    MyApplicationTheme {
        mainScreen()
    }
}

@Composable
fun mainScreen() {

    val context = ContextAmbient.current

    /* Used to show loading components*/
    val popUpStringContent: MutableState<String> = state { "" }
    val showPopUp: MutableState<Boolean> = state { false }
    val receivedResponse: MutableState<Boolean> = state { false }
    val obtainingData: MutableState<Boolean> = state { false }
    val invalidFieldsResponse: MutableState<Boolean> = state { false }

    /* State of bottomBar*/
    val bottomBarState: MutableState<Int> = state { 0 }

    /* list for postActivities*/
    val postsList =
        listOf(Intent(context, PostRegistro::class.java), Intent(context, PostPersona::class.java))

    /* lists that storage data from fetch actions.*/
    val registros: MutableState<List<Registro>> = state { emptyList<Registro>() }
    val personas: MutableState<List<Persona>> = state { emptyList<Persona>() }

    val fabShape = RoundedCornerShape(50)
    var drawerExpanded by state { ScaffoldState(DrawerState.Closed) }
    val menuIcon = @Composable {
        IconButton(onClick = { drawerExpanded = ScaffoldState(DrawerState.Opened) }) {
            Icon(asset = Icons.Filled.Menu)
        }
    }

    Scaffold(
        scaffoldState = drawerExpanded,
        topBar = {
            TopAppBar(title = {
                Text(
                    context.resources.getString(R.string.app_name)
                )
            }, navigationIcon = {
                menuIcon.invoke()
            })
        }, drawerContent = {
            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        popUpStringContent.value =
                            context.resources.getString(R.string.logout_placeholder)
                        receivedResponse.value = false
                        obtainingData.value = true
                        invalidFieldsResponse.value = false
                        showPopUp.value = true

                        ApiConnection.logout(
                            context,
                            receivedResponse,
                            obtainingData
                        )
                    },
                    modifier = Modifier.absolutePadding(
                        0.dp,
                        0.dp,
                        0.dp,
                        10.dp
                    ) + Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = context.resources.getString(R.string.logout_button)
                    )
                }

                Divider()
            }
        }
        , floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                // We specify the rounded shape.
                shape = fabShape
            ) {
                IconButton(onClick = {
                    val intent = postsList[bottomBarState.value]
                    context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                }) {
                    Icon(asset = Icons.Filled.Add)
                }
            }
        },
        floatingActionButtonPosition = Scaffold.FabPosition.End,
        bodyContent = {
            if (bottomBarState.value == 0) {
                VisitsScreen(
                    context,
                    registros,
                    popUpStringContent,
                    showPopUp,
                    receivedResponse,
                    obtainingData,
                    invalidFieldsResponse
                )

            } else if (bottomBarState.value == 1) {
                PeopleScreen(
                    context,
                    personas,
                    popUpStringContent,
                    showPopUp,
                    receivedResponse,
                    obtainingData,
                    invalidFieldsResponse
                )
            }

            val onPopupDismissed = { showPopUp.value = false }
            Utility.LoadingComponent(
                popUpStringContent.value,
                showPopUp,
                onPopupDismissed,
                obtainingData,
                receivedResponse,
                invalidFieldsResponse,
                context
            )

        },
        bottomBar = {
            Column {
                Utility.BottomNavigationBar(context, bottomBarState)
            }
        }
    )
}

@Composable
fun DeleteRegistroDialog(
    registro: Registro,
    registros: MutableState<List<Registro>>,
    delRegistros: MutableList<Registro>,
    show: Boolean,
    dismiss: (Boolean) -> Unit,
    context: Context
) {
    AlertDialog(
        onCloseRequest = { dismiss(false) },
        text = { Text(context.resources.getString(R.string.delete_registro_dialog)) },
        confirmButton = {
            Button(onClick = {
                /* Finds index of the selected element */
                var deletedRegisterIndex: Int = -1
                Log.e("idDeletedRegistro", registro.id.toString())
                for (index in 0 until delRegistros.size) {
                    Log.e(
                        "registroDeleted - actualIndex",
                        "${registro.id} - ${delRegistros[index].id}"
                    )
                    if (delRegistros[index].id == registro.id) {
                        Log.d("match", "match! ${registro.id} - ${delRegistros[index].id}")
                        deletedRegisterIndex = index
                        break
                    }
                }

                /* Deletes the element from registros list */
                delRegistros.removeAt(deletedRegisterIndex)

                /* Assigns modified MutableList to registros*/
                registros.value = delRegistros

                /* Deletes the registro from backend*/
                ApiConnection.deleteRegistro(registro.id)

                dismiss(false)
            }) {
                Text(context.resources.getString(R.string.confirm))
            }
        },
        dismissButton = {
            Button(onClick = { dismiss(false) }) {
                Text(context.resources.getString(R.string.cancel))
            }
        }
    )
}
/*
@Composable
fun EditRegistroDialog(
    registro: Registro,
    show: Boolean,
    dismiss: (Boolean) -> Unit,
    context: Context
) {
    val relOptions = listOf<String>(
        context.resources.getString(R.string.relation_family),
        context.resources.getString(R.string.relation_ext),
        context.resources.getString(R.string.relation_company)
    )

    var relSelection by state { context.resources.getString(R.string.relation_ext) };
    var isShippingCompany by state { false };
    var update by state { false }

    Dialog(
        onCloseRequest = { dismiss(false) },
        children = {
            MyApplicationTheme {
                Surface(shape = shapes.small) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        RadioGroup(
                            options = relOptions,
                            selectedOption = relSelection,
                            onSelectedChange = { str -> relSelection = str }
                        )

                        Row {
                            Checkbox(
                                checked = false,
                                onCheckedChange = {b -> isShippingCompany = b},
                                enabled = relSelection.equals(relOptions[2])
                            )
                            Text(text = context.resources.getString(R.string.is_shipping_company))
                        }

                        Row {
                            Button(onClick = { update = true }) {
                                Text(context.resources.getString(R.string.update))
                            }
                            Button(onClick = {
                                update = false
                                dismiss(false)
                            }) {
                                Text(context.resources.getString(R.string.not_update))
                            }
                        }
                    }
                }
            }
        }
    )

    if (update) {
        when (relSelection) {
            context.resources.getString(R.string.relation_family) -> registro.parentesco =
                "Familiar"
            context.resources.getString(R.string.relation_ext) -> registro.parentesco = "Externo"
            context.resources.getString(R.string.relation_company) -> registro.parentesco =
                "Empresa"
        }
        registro.empresaEntrega = isShippingCompany

        ApiConnection.updateRegistro(registro)
    }
}

 */

@Composable
fun VisitsScreen(
    context: Context,
    registros: MutableState<List<Registro>>,
    popUpStringContent: MutableState<String>,
    showPopUp: MutableState<Boolean>,
    receivedResponse: MutableState<Boolean>,
    obtainingData: MutableState<Boolean>,
    invalidFieldsResponse: MutableState<Boolean>
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        val baseState by state { mutableListOf<Registro>() }
        Button(
            onClick = {
                popUpStringContent.value = context.resources.getString(R.string.obtaining_data_ph)
                registros.value = baseState
                showPopUp.value = true
                receivedResponse.value = false
                obtainingData.value = true
                invalidFieldsResponse.value = false

                ApiConnection.fetchRegistros(
                    registros,
                    receivedResponse,
                    obtainingData
                )
            },
            modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.fetch_registers_btn)
            )
        }

        Divider()

        var showPopupEdit by state { false }
        var showPopupDelete by state { false }
        LazyColumnItems(
            items = registros.value,
            modifier = Modifier.absolutePadding(0.dp, 8.dp, 0.dp, 8.dp)
        ) { registro ->
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.absolutePadding(0.dp, 4.dp, 0.dp, 4.dp)
                    .clickable(onClick = { showPopupEdit = true },
                        onLongClick = { showPopupDelete = true })
            ) {
                ListItem(
                    text = registro.fecha.toString(),
                    secondaryText = "${registro.parentesco} - rut: ${registro.persona?.rut} - numero: ${registro.departamento?.numero}"
                )
            }

            /*if (showPopupEdit) {
                EditRegistroDialog(
                registro = registro,
                show = showPopupEdit,
                dismiss = { b -> showPopupEdit = b },
                context = context
            )
            }*/

            if (showPopupDelete) {
                DeleteRegistroDialog(
                    registro = registro,
                    registros = registros,
                    delRegistros = registros.value.toMutableList(),
                    show = showPopupDelete,
                    dismiss = { b -> showPopupDelete = b },
                    context = context
                )
            }
        }

    }
}

@Composable
fun PeopleScreen(
    context: Context,
    personas: MutableState<List<Persona>>,
    popUpStringContent: MutableState<String>,
    showPopUp: MutableState<Boolean>,
    receivedResponse: MutableState<Boolean>,
    obtainingData: MutableState<Boolean>,
    invalidFieldsResponse: MutableState<Boolean>
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        val baseState by state { mutableListOf<Persona>() }
        Button(
            onClick = {
                popUpStringContent.value = context.resources.getString(R.string.obtaining_data_ph)
                personas.value = baseState
                showPopUp.value = true
                receivedResponse.value = false
                obtainingData.value = true
                invalidFieldsResponse.value = false

                ApiConnection.fetchPersonas(
                    personas,
                    receivedResponse,
                    obtainingData
                )
            },
            modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.fetch_people_btn)
            )
        }

        Divider()

        LazyColumnItems(
            items = personas.value,
            modifier = Modifier.absolutePadding(0.dp, 8.dp, 0.dp, 8.dp)
        ) { persona ->
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.absolutePadding(0.dp, 4.dp, 0.dp, 4.dp)
            ) {
                ListItem(
                    text = persona.nombre.toString(),
                    secondaryText = "RUT: ${persona.rut} - telefono: ${persona.telefono}"
                )
            }
        }

    }
}
