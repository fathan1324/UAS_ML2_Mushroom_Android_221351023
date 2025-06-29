package com.example.uas_ml2_221351023

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment

class ModelSimulationFragment : Fragment() {

    private lateinit var modelHelper: ModelHelper

    private val featureOptions = mapOf(
        "cap-shape" to mapOf("b" to "berbentuk lonceng", "c" to "berbentuk kerucut", "x" to "berbentuk cembung"),
        "cap-surface" to mapOf("f" to "berserat", "g" to "beralur", "y" to "bersisik", "s" to "halus"),
        "cap-color" to mapOf("n" to "cokelat", "y" to "kuning", "w" to "putih", "g" to "abu-abu", "e" to "merah"),
        "bruises" to mapOf("b" to "berbentuk lonceng", "c" to "berbentuk kerucut", "x" to "berbentuk cembung"),
        "odor" to mapOf("f" to "berserat", "g" to "beralur", "y" to "bersisik", "s" to "halus"),
        "gill-attachment" to mapOf("n" to "cokelat", "y" to "kuning", "w" to "putih", "g" to "abu-abu", "e" to "merah"),
        "gill-spacing" to mapOf("b" to "berbentuk lonceng", "c" to "berbentuk kerucut", "x" to "berbentuk cembung"),
        "gill-size" to mapOf("f" to "berserat", "g" to "beralur", "y" to "bersisik", "s" to "halus"),
        "gill-color" to mapOf("n" to "cokelat", "y" to "kuning", "w" to "putih", "g" to "abu-abu", "e" to "merah"),
        "stalk-shape" to mapOf("b" to "berbentuk lonceng", "c" to "berbentuk kerucut", "x" to "berbentuk cembung"),
        "stalk-root" to mapOf("f" to "berserat", "g" to "beralur", "y" to "bersisik", "s" to "halus"),
        "stalk-surface-above-ring" to mapOf("n" to "cokelat", "y" to "kuning", "w" to "putih", "g" to "abu-abu", "e" to "merah"),
        "stalk-surface-below-ring" to mapOf("b" to "berbentuk lonceng", "c" to "berbentuk kerucut", "x" to "berbentuk cembung"),
        "stalk-color-above-ring" to mapOf("f" to "berserat", "g" to "beralur", "y" to "bersisik", "s" to "halus"),
        "stalk-color-below-ring" to mapOf("n" to "cokelat", "y" to "kuning", "w" to "putih", "g" to "abu-abu", "e" to "merah"),
        "veil-color" to mapOf("b" to "berbentuk lonceng", "c" to "berbentuk kerucut", "x" to "berbentuk cembung"),
        "ring-number" to mapOf("f" to "berserat", "g" to "beralur", "y" to "bersisik", "s" to "halus"),
        "ring-type" to mapOf("n" to "cokelat", "y" to "kuning", "w" to "putih", "g" to "abu-abu", "e" to "merah"),
        "spore-print-color" to mapOf("b" to "berbentuk lonceng", "c" to "berbentuk kerucut", "x" to "berbentuk cembung"),
        "population" to mapOf("f" to "berserat", "g" to "beralur", "y" to "bersisik", "s" to "halus"),
        "habitat" to mapOf("n" to "cokelat", "y" to "kuning", "w" to "putih", "g" to "abu-abu", "e" to "merah")
        // Tambahkan sisanya sampai 22
    )

    private val selectedValues = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_model_simulation, container, false)

        modelHelper = ModelHelper(requireContext())
        modelHelper.loadModel()

        setupSpinner(view, R.id.spinner_cap_shape, "cap-shape")
        setupSpinner(view, R.id.spinner_cap_surface, "cap-surface")
        setupSpinner(view, R.id.spinner_cap_color, "cap-color")
        setupSpinner(view, R.id.spinner_bruises, "bruises")
        setupSpinner(view, R.id.spinner_odor, "odor")
        setupSpinner(view, R.id.spinner_gill_attachment, "gill-attachment")
        setupSpinner(view, R.id.spinner_gill_spacing, "gill-spacing")
        setupSpinner(view, R.id.spinner_gill_size, "gill-size")
        setupSpinner(view, R.id.spinner_gill_color, "gill-color")
        setupSpinner(view, R.id.spinner_stalk_shape, "stalk-shape")
        setupSpinner(view, R.id.spinner_stalk_root, "stalk-root")
        setupSpinner(view, R.id.spinner_stalk_surface_above_ring, "stalk-surface-above-ring")
        setupSpinner(view, R.id.spinner_stalk_surface_below_ring, "stalk-surface-below-ring")
        setupSpinner(view, R.id.spinner_stalk_color_above_ring, "stalk-color-above-ring")
        setupSpinner(view, R.id.spinner_stalk_color_below_ring, "stalk-color-below-ring")
        setupSpinner(view, R.id.spinner_veil_color, "veil-color")
        setupSpinner(view, R.id.spinner_ring_number, "ring-number")
        setupSpinner(view, R.id.spinner_ring_type, "ring-type")
        setupSpinner(view, R.id.spinner_spore_print_color, "spore-print-color")
        setupSpinner(view, R.id.spinner_population, "population")
        setupSpinner(view, R.id.spinner_habitat, "habitat")

        val resultText = view.findViewById<TextView>(R.id.text_result)
        val predictButton = view.findViewById<Button>(R.id.button_predict)

        predictButton.setOnClickListener {
            selectedValues.clear()
            selectedValues.addAll(listOf(
                getSelectedKey(view, R.id.spinner_cap_shape, "cap-shape"),
                getSelectedKey(view, R.id.spinner_cap_surface, "cap-surface"),
                getSelectedKey(view, R.id.spinner_cap_color, "cap-color")
                // Tambahkan lainnya total 22
            ))

            // Mapping karakter ke float (one-hot encoded di Python → angka biasa untuk TFLite)
            val encoded = selectedValues.map { it[0].code.toFloat() }.toFloatArray()

            val prediction = modelHelper.predict(encoded)
            resultText.text = if (prediction > 0.5f) "Jamur Beracun" else "Jamur Aman"
        }

        return view
    }

    private fun setupSpinner(view: View, spinnerId: Int, feature: String) {
        val spinner = view.findViewById<Spinner>(spinnerId)
        val list = featureOptions[feature]?.values?.toList() ?: emptyList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun getSelectedKey(view: View, spinnerId: Int, feature: String): String {
        val spinner = view.findViewById<Spinner>(spinnerId)
        val selected = spinner.selectedItem as String
        return featureOptions[feature]?.filterValues { it == selected }?.keys?.first() ?: ""
    }

    override fun onDestroy() {
        super.onDestroy()
        modelHelper.close()
    }
}
