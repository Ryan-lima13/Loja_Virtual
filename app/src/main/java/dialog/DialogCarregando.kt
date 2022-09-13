package dialog

import android.app.Activity
import android.app.AlertDialog
import com.rlds.lojavirtual.R

class DialogCarregando(private val activity:Activity) {
    lateinit var dialog:AlertDialog

    fun iniciarCArregamentoAlertDialog(){
        val builder = AlertDialog.Builder(activity)
        val layoutInfleter = activity.layoutInflater
        builder.setView(layoutInfleter.inflate(R.layout.dialog_carregando,null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()

    }
    fun liberarAlertDialog(){
        dialog.dismiss()
    }
}