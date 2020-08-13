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
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.myapplication.api.ApiConnection
import com.example.myapplication.model.Registro
import com.example.myapplication.ui.MyApplicationTheme
import com.example.myapplication.ui.shapes
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun App() {
    MyApplicationTheme {
        indexRegisters()
    }
}

@Composable
fun indexRegisters() {

    val context = ContextAmbient.current
    var popUpStringContent = ""

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        val registros: MutableState<List<Registro>> = state { emptyList<Registro>() }
        val baseState by state { mutableListOf<Registro>() }
        val showPopUp: MutableState<Boolean> = state { false }
        val registrosResponse: MutableState<Boolean> = state { false }
        val obtainingData: MutableState<Boolean> = state { false }
        Button(
            onClick = {
                popUpStringContent = context.resources.getString(R.string.obtaining_data_ph)
                registros.value = baseState
                showPopUp.value = true
                registrosResponse.value = false
                obtainingData.value = true

                ApiConnection.fetchRegistros(
                    registros,
                    registrosResponse,
                    obtainingData
                )
            },
            modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.fetch_registers_btn)
            )
        }

        Button(
            onClick = {
                val intent = Intent(context, PostRegistro::class.java)
                context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            },
            modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.create_register)
            )

        }

        val showPopUpLogout: MutableState<Boolean> = state { false }
        val logoutResponse: MutableState<Boolean> = state { false }
        val logoutState: MutableState<Boolean> = state { false }
        Button(
            onClick = {
                popUpStringContent = context.resources.getString(R.string.logout_placeholder)
                showPopUpLogout.value = true
                logoutResponse.value = false
                logoutState.value = true

                ApiConnection.logout(
                    context,
                    logoutResponse,
                    logoutState
                )
            },
            modifier = Modifier.absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.logout_button)
            )
        }

        Divider(color = Color.Black)

        val onPopupDismissed = { showPopUp.value = false }
        Utility.LoadingComponent(
            popUpStringContent,
            showPopUp,
            onPopupDismissed,
            obtainingData,
            registrosResponse,
            context
        )

        var showPopupEdit by state { false }
        var showPopupDelete by state { false }
        LazyColumnItems(
            items = registros.value,
            modifier = Modifier.padding(0.dp)
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
/*
            if (showPopupEdit) {
                EditRegistroDialog(
                    registro = registro,
                    show = showPopupEdit,
                    dismiss = { b -> showPopupEdit = b },
                    context = context
                )
            }

 */
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