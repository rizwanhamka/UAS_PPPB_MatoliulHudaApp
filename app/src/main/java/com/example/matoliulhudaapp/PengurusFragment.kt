package com.example.matoliulhudaapp.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matoliulhudaapp.Pengurus
import com.example.matoliulhudaapp.R

class PengurusFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pengurus, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvPengurus)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 5 Data Dummy Static
        val dataPengurus = listOf(
            Pengurus("KH. Ahmad Dahlan", "Ketua Yayasan", "0812-3456-7890", "ketua@matoliulhuda.com", R.drawable.ic_launcher_background), // Ganti dengan R.drawable.foto_asli jika ada
            Pengurus("Bpk. Susanto S.Pd", "Kepala Sekolah", "0813-9988-7766", "kepsek@matoliulhuda.com", R.drawable.ic_launcher_background),
            Pengurus("Ibu Siti Aminah", "Bendahara", "0811-2233-4455", "bendahara@matoliulhuda.com", R.drawable.ic_launcher_background),
            Pengurus("Bpk. Budi Santoso", "Sekretaris", "0857-1122-3344", "sekretaris@matoliulhuda.com", R.drawable.ic_launcher_background),
            Pengurus("Ust. Yusuf Mansur", "Humas & Dakwah", "0896-5566-7788", "humas@matoliulhuda.com", R.drawable.ic_launcher_background)
        )

        recyclerView.adapter = PengurusAdapter(dataPengurus)

        return view
    }
}