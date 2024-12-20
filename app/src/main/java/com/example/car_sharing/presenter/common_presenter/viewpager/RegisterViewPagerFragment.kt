package com.example.car_sharing.presenter.common_presenter.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.car_sharing.R
import com.example.car_sharing.data.viewmodels.SignUpViewModel
import com.example.car_sharing.presenter.common_presenter.register.RegFinishFragment
import com.example.car_sharing.presenter.common_presenter.register.RegFirstFragment
import com.example.car_sharing.presenter.common_presenter.register.RegSecondFragment
import com.example.car_sharing.presenter.common_presenter.register.RegThirdFragment


class RegisterViewPagerFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register_view_pager, container, false)
        val fragmentList: ArrayList<Fragment> = arrayListOf(
            RegFirstFragment(),
            RegSecondFragment(),
            RegThirdFragment(),
            RegFinishFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val vp=view.findViewById<ViewPager2>(R.id.vpreg2)
//        vp.setUserInputEnabled(false);
        vp.adapter=adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewPager = view.findViewById<ViewPager2>(R.id.vpreg2)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentItem = viewPager.currentItem

                if (currentItem > 0) {
                    // Переходим на предыдущую страницу
                    viewPager.currentItem = currentItem - 1
                } else {
                    // Если мы на первой странице, разрешаем обычное поведение кнопки "Назад"
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        })
    }
}