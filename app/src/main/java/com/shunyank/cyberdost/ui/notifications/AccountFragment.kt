package com.shunyank.cyberdost.ui.notifications

import android.accounts.Account
import android.app.AlertDialog
import android.content.DialogInterface
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
import com.shunyank.cyberdost.App
import com.shunyank.cyberdost.activities.LoginActivity
import com.shunyank.cyberdost.adapters.AccountMyPostAdapter
import com.shunyank.cyberdost.adapters.HomeRecentPostAdapter
import com.shunyank.cyberdost.databinding.FragmentAccountBinding
import com.shunyank.cyberdost.network.AppWriteHelper
import com.shunyank.cyberdost.utils.AppUtils
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel
    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         accountViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val account :io.appwrite.models.Account = AppUtils.getUser()
        val profilePic = AppUtils.getUserProfileString()

        Glide.with(requireActivity()).load(profilePic).into(binding.userpicImageview)


        val handle = AppUtils.getUserHandle(account.email)
        binding.usersname.text = account.name
        binding.email.text  = account.email
        binding.userhandle.text = handle


//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val callback =object : AccountMyPostAdapter.RecentPostCallback {
            override fun onLikeClicked(id: String?, count: Long?) {
            }

            override fun onVictimClicked(id: String?, count: Long?) {
            }

            override fun onCommentClicked(id: String?) {
            }

        }
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.mypostRyc.layoutManager = linearLayoutManager
        accountViewModel.posts.observe(viewLifecycleOwner){
//            Log.e("size of posts","${it.size}")
            Log.e("posts", Gson().toJson(it))
            val postAdapter = AccountMyPostAdapter(requireContext(),it)
            postAdapter.setSelectCatCallback(callback)
            binding.mypostRyc.adapter = postAdapter
            if(it.size==0){
                binding.noPost.visibility = View.VISIBLE
            }else{
                binding.noPost.visibility = View.GONE

            }

        }
        binding.logoutButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setMessage(" Do you really want to logout?")
            alertDialog.setPositiveButton("Yes"
            ) { p0, p1 ->
                lifecycleScope.launch {
                    p0?.dismiss()
                    logout()

                }

            }
            alertDialog.setNegativeButton("no",object :DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0?.dismiss()
                }

            })
            alertDialog.show()
        }
        loadPosts()
        return root
    }

    private suspend fun logout() {
        val account = io.appwrite.services.Account(AppWriteHelper.getClient())
        account.deleteSessions()
        AppUtils.setUserProfile("")
        AppUtils.setUser("")
        startActivity(Intent(requireContext(),LoginActivity::class.java))
        requireActivity().finish()
    }

    fun loadPosts(){
        lifecycleScope.launch {
            accountViewModel.fetchMyPosts(requireActivity(),AppUtils.getUser().id)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}