package com.example.ortalama1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.yeni_ders.*
import kotlinx.android.synthetic.main.yeni_ders.view.*


class MainActivity : AppCompatActivity() {
    private val dersler = arrayOf("matematik", "fizik", "veri yapıları", "sayısal analiz", "algoritma")
    private var tumDersBilgileri: ArrayList<Dersler> = ArrayList(3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dersler)
        etxtgiris.setAdapter(adapter)

        if (rootLayout.childCount == 0) {
            btn2.visibility = View.INVISIBLE
        } else {
            btn2.visibility = View.VISIBLE
        }

        btn1.setOnClickListener {
            if (!etxtgiris.text.isNullOrEmpty()) {
                var inflater = LayoutInflater.from(this)
                var yeniDersView = inflater.inflate(R.layout.yeni_ders, null)

                var notAdi = etxtgiris.text.toString()
                var notHarfi = spinner2.selectedItem.toString()
                var notKredi = spinner1.selectedItem.toString()

                yeniDersView.yenietxtgiris.setText(notAdi)
                yeniDersView.yenispinner1.setSelection(spinnerDegerinIndexiniBul(spinner1, notKredi))
                yeniDersView.yenispinner2.setSelection(spinnerDegerinIndexiniBul(spinner2, notHarfi))

                yeniDersView.yenibtn1.setOnClickListener {
                    rootLayout.removeView(yeniDersView)
                    if (rootLayout.childCount == 0) {
                        btn2.visibility = View.INVISIBLE
                    } else {
                        btn2.visibility = View.VISIBLE
                    }
                }
                rootLayout.addView(yeniDersView)
                etxtgiris.text = null
                spinner1.setSelection(0)
                spinner2.setSelection(0)

                btn2.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "ders adı giriniz", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun spinnerDegerinIndexiniBul(spinner: Spinner, aranacakDeger: String): Int {
        var index = 0

        for (i in 0..spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(aranacakDeger)) {
                index = i
                break
            }
        }
        return index
    }

        fun ortalamaHesapla(view: View) {
            var toplamNot: Double = 0.0
            var toplamKredi: Double = 0.0

            for (i in 0 until rootLayout.childCount-1) {
                var tekSatir = rootLayout.getChildAt(i)

                var geciciDers = Dersler(tekSatir.yenietxtgiris.text.toString(),
                    tekSatir.yenispinner2.selectedItem.toString(), ((tekSatir.yenispinner1.selectedItemPosition)+1).toString())
                tumDersBilgileri.add(geciciDers)
            }

            for (oankiDers in tumDersBilgileri) {
                toplamNot += (harfiNotaCevir(oankiDers.harf)) * (oankiDers.kredi.toDouble())
                toplamKredi += oankiDers.kredi.toDouble()
            }
            Toast.makeText(this, "ORTALAMANIZ:" + toplamNot/toplamKredi , Toast.LENGTH_LONG).show()
            tumDersBilgileri.clear()
        }

    fun harfiNotaCevir(gelenNotHarfDegeri: String): Double {
        var deger=0.0
        when(gelenNotHarfDegeri){
            "AA"-> deger=4.0
            "BA"-> deger=3.5
            "BB"-> deger=3.0
            "CB"-> deger=2.5
            "CC"-> deger=2.0
            "DC"-> deger=1.5
            "DD"-> deger=1.0
            "FF"-> deger=0.0
        }
        return deger
    }

}