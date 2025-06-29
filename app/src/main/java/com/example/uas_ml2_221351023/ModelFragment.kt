package com.example.uas_ml2_221351023

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uas_ml2_221351023.databinding.FragmentModelBinding
import androidx.navigation.fragment.findNavController

class ModelFragment : Fragment() {
    private var _binding: FragmentModelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardArchitecture.setOnClickListener {
            findNavController().navigate(R.id.action_modelFragment_to_arsitekturFragment)
        }
        binding.cardSimulation.setOnClickListener {
            findNavController().navigate(R.id.action_modelFragment_to_modelSimulationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}