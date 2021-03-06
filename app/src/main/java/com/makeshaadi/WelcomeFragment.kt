package com.makeshaadi


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        welcomeViewPager.adapter = WelcomeViewPagerAdapter(this)
        pageIndicator.setViewPager2(welcomeViewPager)

//        nextPageBtn.setOnClickListener {
//            val c = welcomeViewPager.currentItem
//            if (c != 3) welcomeViewPager.currentItem = c + 1
//        }

        welcomeViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 3) {
//                    nextPageBtn.visibility = View.GONE
                    getStartedBtn.visibility = View.VISIBLE
                } else {
//                    nextPageBtn.visibility = View.VISIBLE
                    getStartedBtn.visibility = View.GONE
                }
            }
        })

        getStartedBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                        ),
                        1
                    )
                }
            }
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
    }
}

private const val ARG_RES_ID = "image_res_id"
private val imageResources=
    listOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
private val imgDesc = listOf(
    "Find your soulmate on India's first Dowry-free Matrimonial App",
    "Dowry FREE, Always!",
    "Aadhar Verified Profiles",
    "Secure & Trustable!"
)

class WelcomeViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment = WelcomePageFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_RES_ID, imageResources[position])
            putString("imgDesc", imgDesc[position])
        }

        return fragment
    }
}