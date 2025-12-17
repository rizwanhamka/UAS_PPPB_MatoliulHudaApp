package com.example.matoliulhudaapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.matoliulhudaapp.adapter.ImageSliderAdapter
import com.example.matoliulhudaapp.components.*
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var dotsLayout: LinearLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ImageSliderAdapter // Simpan adapter sebagai variabel global
    private var dots: Array<ImageView?>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- 1. SETUP CAROUSEL ---
        viewPager = view.findViewById(R.id.imageSlider)
        dotsLayout = view.findViewById(R.id.dotsLayout)

        // Inisialisasi adapter dengan list kosong terlebih dahulu
        adapter = ImageSliderAdapter(emptyList())
        viewPager.adapter = adapter

        // Panggil fungsi ambil data API
        fetchGalleryFromApi()

        // Listener saat slide berubah
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentDot(position)
            }
        })

        // --- 2. LOGIC MENU LAINNYA ---
        val btnBerita = view.findViewById<LinearLayout>(R.id.menuBerita)
        val btnGallery = view.findViewById<LinearLayout>(R.id.menuGallery)
        val btnInput = view.findViewById<LinearLayout>(R.id.menuInput)
        val btnPengurus = view.findViewById<LinearLayout>(R.id.menuPengurus)
        val btnContact = view.findViewById<LinearLayout>(R.id.menuContact)
        val btnLogout = view.findViewById<LinearLayout>(R.id.menuLogout)

        btnBerita.setOnClickListener { loadFragment(NewsFragment()) }
        btnGallery.setOnClickListener { loadFragment(GalleryFragment()) }
        btnInput.setOnClickListener { loadFragment(InputFragment()) }
        btnPengurus.setOnClickListener { loadFragment(PengurusFragment()) }
        btnContact.setOnClickListener { loadFragment(ContactFragment()) }

        btnLogout?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show()
            loadFragment(LoginFragment())
        }
    }

    // --- FUNGSI AMBIL DATA API ---
    private fun fetchGalleryFromApi() {
        ApiClient.instance.getGallery().enqueue(object : Callback<List<GalleryImage>> {
            override fun onResponse(
                call: Call<List<GalleryImage>>,
                response: Response<List<GalleryImage>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val allImages = response.body()!!

                    // Ambil 3 gambar terakhir (atau 3 pertama, sesuaikan kebutuhan)
                    // .take(3) mengambil 3 data pertama.
                    // Jika ingin 3 data TERBARU (asumsi data urut lama->baru), pakai .takeLast(3).reversed()
                    val limitedImages = allImages.takeLast(3).reversed()

                    if (limitedImages.isNotEmpty()) {
                        // Update Adapter
                        adapter.updateData(limitedImages)

                        // Update Dots
                        setupDots(limitedImages.size)
                        setCurrentDot(0)
                    }
                } else {
                    Toast.makeText(context, "Gagal memuat gambar slider", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<GalleryImage>>, t: Throwable) {
                Log.e("HomeFragment", "Error: ${t.message}")
                // Jangan crash aplikasi, biarkan kosong atau tampilkan toast
            }
        })
    }

    private fun setupDots(count: Int) {
        if (!isAdded) return // Cek jika fragment masih aktif

        dots = arrayOfNulls(count)
        dotsLayout.removeAllViews()

        for (i in 0 until count) {
            dots!![i] = ImageView(requireContext())
            dots!![i]?.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_dot_inactive)
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            dotsLayout.addView(dots!![i], params)
        }
    }

    private fun setCurrentDot(position: Int) {
        if (dotsLayout.childCount > 0 && position < dotsLayout.childCount) {
            for (i in 0 until dotsLayout.childCount) {
                val imageView = dotsLayout.getChildAt(i) as ImageView
                if (i == position) {
                    imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green_main))
                } else {
                    imageView.setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}