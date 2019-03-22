package fernandes.bruna.share

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent.EXTRA_STREAM
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_outra.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btActivity.setOnClickListener {
            val intentExplicita = Intent(this, OutraActivity::class.java)
            startActivity(intentExplicita)

        }

        btShare.setOnClickListener(){
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1000)
        }

        btCamera.setOnClickListener {
            val intentFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intentFoto, 99)
        }

        imgShareText.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            with(shareIntent) {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Compartilhar")
                putExtra(Intent.EXTRA_TEXT, txtShare.text.toString())
            }
            startActivity(shareIntent)
        }


    }

    override fun onActivityResult(codigo: Int, resultado: Int, it: Intent?) {
        if (codigo == 99 && resultado == RESULT_OK) {
            val bundle = it?.extras
            if (bundle != null) {
                val bitmap = bundle.get("data") as Bitmap
                btCamera.setImageBitmap(bitmap)
            }
        } else if (codigo == 1000 && resultado == Activity.RESULT_OK) {
            val uri = it?.data
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.type = "image/*"
            startActivity(shareIntent)

        }


    }
}
