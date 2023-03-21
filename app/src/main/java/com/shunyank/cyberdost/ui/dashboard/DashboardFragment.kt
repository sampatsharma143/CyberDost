package com.shunyank.cyberdost.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shunyank.cyberdost.adapters.RecentArticleAdapter
import com.shunyank.cyberdost.adapters.SliderAdapter
import com.shunyank.cyberdost.databinding.FragmentDashboardBinding
import com.shunyank.cyberdost.models.SliderModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: SliderAdapter
    private lateinit var recentArticleAdapter: RecentArticleAdapter
    private lateinit var sliderModalArrayList: ArrayList<SliderModel>
    private lateinit var recentArticlesList: ArrayList<SliderModel>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        setupSliders()
        setupRecentArticles()
            return root
    }
    private fun setupSliders() {
        sliderModalArrayList = ArrayList<SliderModel>()
        sliderModalArrayList.add(
            SliderModel(
                "Here is how to protect yourself from SIM swap ",
                "https://images.pexels.com/photos/63690/pexels-photo-63690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "by mcafee",
                "https://www.mcafee.com/blogs/mobile-security/what-is-sim-swapping/"
            )
        )
        sliderModalArrayList.add(
            SliderModel(
                "How to Recognize and Avoid Phishing Scams",
                "https://images.pexels.com/photos/6964348/pexels-photo-6964348.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "by USA GOV.",
                "https://consumer.ftc.gov/articles/how-recognize-and-avoid-phishing-scams"

            )
        )
        sliderModalArrayList.add(
            SliderModel(
                "Protect Elders: Avoid Phone Scams",
                "https://images.pexels.com/photos/5926389/pexels-photo-5926389.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "by getcarefull.com",
                "https://www.getcarefull.com/articles/how-to-protect-your-aging-parents-against-scams-and-fraud"
            )
        )
        adapter = SliderAdapter(context, sliderModalArrayList)

        binding.sliderViewPager.adapter = adapter
//        adapter.setSliderModalArrayList()
        binding.dotsIndicator.attachTo(binding.sliderViewPager)

    }
    private fun setupRecentArticles() {
        recentArticlesList = ArrayList<SliderModel>()

        recentArticlesList.add(
            SliderModel(
                "How to File an FIR online",
                "https://d3l793awsc655b.cloudfront.net/blog/wp-content/uploads/2022/09/what-is-iec-certificate.png",
                "",
                "https://vakilsearch.com/blog/how-to-file-an-fir-online/"
            )
        )

        recentArticlesList.add(
            SliderModel(
                "Coupons to Friend Requests: 5 Common Online Scams & How To Avoid Them",
                "https://en-media.thebetterindia.com/uploads/2019/10/online-scam.jpg?dpr=1.0&q=70&compress=true&quality=80&w=768",
                "",
                "https://www.thebetterindia.com/198962/common-online-scams-facebook-youtube-email-phishing-identity-theft-india/"
            )
        )
        recentArticlesList.add(
            SliderModel(
                "How to Recognize and Avoid Phishing Scams",
                "https://media.istockphoto.com/id/956400244/vector/phishing-scam-hacker-attack.jpg?s=612x612&w=0&k=20&c=6adZZcKJdWO24xd05WH41Q362vGgny_w466y7Ds14Mk=",
                "by USA GOV.",
                "https://consumer.ftc.gov/articles/how-recognize-and-avoid-phishing-scams"

            )
        )
        recentArticlesList.add(
            SliderModel(
                "Protect yourself from tech support scams",
                "https://img-prod-cms-rt-microsoft-com.akamaized.net/cms/api/am/imageFileData/RE4GzSU?ver=6ea4",
                "",
                "https://support.microsoft.com/en-us/windows/protect-yourself-from-tech-support-scams-2ebf91bd-f94c-2a8a-e541-f5c800d18435"
            )
        )

        recentArticlesList.add(
            SliderModel(
                "The Dangers of Using Public Wi-Fi (and How To Stay Safe)",
                "https://assets-global.website-files.com/6082ee0e95eb6459d78fac06/63bd9f929143bc76554edaf8_Dangers%20of%20public%20Wi-Fi.png",
                "",
                "https://www.aura.com/learn/dangers-of-public-wi-fi"
            )
        )
        recentArticlesList.add(
            SliderModel(
                "What are Social Engineering Attacks and 5 Prevention Methods",
                "https://cheapsslsecurity.com/blog/wp-content/uploads/2022/05/social-engineering-attacks-1.jpg",
                "",
                "https://cheapsslsecurity.com/blog/social-engineering-attacks-and-prevention-methods/"
            )
        )
        recentArticlesList.add(
            SliderModel(
                "Ransomware explained: How it works and how to remove it",
                "https://images.idgesg.net/images/article/2020/05/ransomware_attack_worried_businessman_by_andrey_popov_gettyimages-1199291222_cso_2400x1600-100840844-large.jpg?auto=webp&quality=85,70",
                "",
                "https://www.csoonline.com/article/3236183/what-is-ransomware-how-it-works-and-how-to-remove-it.html\n"
            )
        )
        recentArticlesList.add(
            SliderModel(
                "Cyberbullying: What is it and how to stop it",
                "https://www.unicef.org/sites/default/files/styles/hero_tablet/public/SID_2020_Hero_0.jpg?itok=rZjIitOY",
                "",
                "https://www.unicef.org/end-violence/how-to-stop-cyberbullying"
            )
        )
        recentArticleAdapter = RecentArticleAdapter(context, recentArticlesList)
        val linearLayout = LinearLayoutManager(requireContext())
        binding.articleRyc.layoutManager = linearLayout
        binding.articleRyc.adapter = recentArticleAdapter
//        adapter.setSliderModalArrayList()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}