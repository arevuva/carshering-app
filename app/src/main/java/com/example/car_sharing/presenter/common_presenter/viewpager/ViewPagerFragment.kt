package com.example.car_sharing.presenter.common_presenter.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.car_sharing.R
import com.example.car_sharing.presenter.common_presenter.onboarding_list.FirstFragment
import com.example.car_sharing.presenter.common_presenter.onboarding_list.SecondFragment
import com.example.car_sharing.presenter.common_presenter.onboarding_list.ThirdFragment
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class ViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        val dotsIndicator = view.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator);
        val fragmentList: ArrayList<Fragment> = arrayListOf(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        val vp=view.findViewById<ViewPager2>(R.id.vp2)
        vp.setUserInputEnabled(false);
        vp.adapter=adapter
        dotsIndicator.attachTo(vp)
        return view
    }


}