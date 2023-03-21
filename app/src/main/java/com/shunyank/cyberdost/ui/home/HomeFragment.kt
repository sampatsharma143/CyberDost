package com.shunyank.cyberdost.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.shunyank.cyberdost.activities.CommentActivity
import com.shunyank.cyberdost.activities.CreatePostActivity
import com.shunyank.cyberdost.activities.PSNearMeActivity
import com.shunyank.cyberdost.activities.SmsCheckerActivity
import com.shunyank.cyberdost.activities.UrlCheckerActivity
import com.shunyank.cyberdost.adapters.HomeRecentPostAdapter
import com.shunyank.cyberdost.adapters.HomeRecentPostAdapter.RecentPostCallback
import com.shunyank.cyberdost.adapters.SliderAdapter
import com.shunyank.cyberdost.databinding.FragmentHomeBinding
import com.shunyank.cyberdost.models.PostModel
import com.shunyank.cyberdost.models.SliderModel
import com.shunyank.cyberdost.network.AppWriteHelper
import com.shunyank.cyberdost.network.callbacks.DocumentFindAndCreateListener
import com.shunyank.cyberdost.network.callbacks.DocumentListFetchListenerForKotlin
import com.shunyank.cyberdost.network.callbacks.DocumentUpdateListenerForK
import com.shunyank.cyberdost.network.utils.DatabaseUtils
import com.shunyank.cyberdost.utils.AppUtils
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Document
import io.appwrite.services.Databases
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var postAdapter: HomeRecentPostAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: SliderAdapter
    private lateinit var sliderModalArrayList: ArrayList<SliderModel>
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        Glide.with(requireActivity()).load(AppUtils.getUserProfileString()).into(binding.profileImage)
      val callbacks =   object  : RecentPostCallback{
            override fun onLikeClicked(id: String, count: Long?, pos: Int) {
                Log.e("like","clicked")
                    // add new document in  likes
              var data =  HashMap<Any,Any>()
                data.put("post_id",id)
                data.put("user_id",AppUtils.getUser().id)
                DatabaseUtils.createDocumentIfNotFound(
                    requireActivity(),
                    Databases(AppWriteHelper.getClient()), listOf(Query.equal("post_id",id),Query.equal("user_id",AppUtils.getUser().id)),
                    "collection-post-like",
                    data,
                    object: DocumentFindAndCreateListener{
                        override fun onFindSuccessfully(documents: MutableList<Document>?) {
                                // unlike
                            Log.e("found","doc")
//                            unLikeCount(id,count!!-1,pos)

                        }

                        override fun onCreatedSuccessfully(document: Document?) {
                            // liked and increase -
                            updateLikeCount(id,count!!+1,pos)
                        }

                        override fun onFailed() {
                            Log.e("failed","true")
                        }

                        override fun onException(exception: AppwriteException?) {
                            exception?.printStackTrace()
                        }

                    }
                )

            }

            override fun onVictimClicked(id: String?, count: Long?) {

            }

            override fun onCommentClicked(id: String?) {
                    startActivity(Intent(requireContext(),CommentActivity::class.java))
            }

        }
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recentPostRyc.layoutManager = linearLayoutManager
        homeViewModel.posts.observe(viewLifecycleOwner){
            Log.e("size of posts","${it.size}")
            Log.e("posts",Gson().toJson(it))
             postAdapter = HomeRecentPostAdapter(requireContext(),it)


//            it.forEach {
//                checkLike(it) { postModel, b ->
//                    postModel?.isAlreadyLiked = b
//                    postAdapter.notifyDataSetChanged()
//                }
//            }

            postAdapter.setSelectCatCallback(callbacks)
            binding.recentPostRyc.adapter = postAdapter
            binding.postProgressbar.visibility = View.GONE

        }
        loadPosts()
        setupSliders()
        binding.createPostButton.setOnClickListener{
            startActivity(Intent(activity, CreatePostActivity::class.java))
        }
        binding.psNearbyButton.setOnClickListener {
            startActivity(Intent(activity, PSNearMeActivity::class.java))

        }
        binding.urlScan.setOnClickListener {
            startActivity(Intent(activity, UrlCheckerActivity::class.java))

        }
        return root
    }

    private fun unLikeCount(id: String, l: Long, pos: Int) {
        val data = HashMap<Any,Any>()
        data.put("likes_count",l)
        DatabaseUtils.updateDocumentForK(requireActivity(),"collection-post",id,data,object :DocumentUpdateListenerForK{
            override fun onUpdatedSuccessfully(document: Document?) {
//                loadPosts()
                Log.e("unliked","succesfully")
                postAdapter.notifyItemChanged(pos)

            }

            override fun onFailed() {
            }

            override fun onException(exception: AppwriteException?) {
            }

        })
    }

    private fun updateLikeCount(id: String, l: Long, pos: Int) {
        val data = HashMap<Any,Any>()
        data.put("likes_count",l)
        DatabaseUtils.updateDocumentForK(requireActivity(),"collection-post",id,data,object :DocumentUpdateListenerForK{
            override fun onUpdatedSuccessfully(document: Document?) {
//                loadPosts()
                Log.e("liked","succesfully")
                loadPosts()
            }

            override fun onFailed() {
            }

            override fun onException(exception: AppwriteException?) {
            }

        })

    }

    fun checkLike(
        post:PostModel,
        callback:(PostModel?,Boolean)->Unit
    ){
        val userId = AppUtils.getUser().id
        DatabaseUtils.fetchDocumentsForKotlin(requireActivity(),"collection-post-like",
        listOf(Query.equal("post_id",post.id),Query.equal("user_id",userId)),object :DocumentListFetchListenerForKotlin{
                override fun onFetchSuccessfully(documents: MutableList<Document>?) {
//                        Log.e("like",""+documents?.size)
                    callback(post,true)
                }

                override fun onFailed() {
//                    Log.e("like","Failed "+post.id)
                    callback(post,false)
                }

                override fun onException(exception: AppwriteException?) {
                }

            }
            )

    }

    fun loadPosts(){
        binding.postProgressbar.visibility = View.VISIBLE
        lifecycleScope.launch {
            homeViewModel.fetchPosts(requireActivity())

        }
    }

    override fun onResume() {
        super.onResume()
        loadPosts()

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
        adapter = SliderAdapter(context,sliderModalArrayList)

        binding.sliderViewPager.adapter  = adapter
//        adapter.setSliderModalArrayList()
        binding.dotsIndicator.attachTo(binding.sliderViewPager)

        //tools

        binding.smsAiLayout.setOnClickListener {
            startActivity(Intent(activity,SmsCheckerActivity::class.java))
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}