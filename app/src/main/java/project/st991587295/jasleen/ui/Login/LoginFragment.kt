package project.st991587295.jasleen.ui.Login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import project.st991587295.jasleen.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var firebaseAuth: FirebaseAuth
private lateinit var email: String
private lateinit var password: String

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private var _binding: LoginFragment? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance()
        return inflater.inflate(project.st991587295.jasleen.R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var Login = view.findViewById(project.st991587295.jasleen.R.id.btnLogin) as Button
        Login.setOnClickListener {

            if((view?.findViewById(R.id.etLogEmail) as EditText).text.toString().isEmpty() || (view?.findViewById(R.id.etLogPass) as EditText).text.toString().isEmpty()){
                Toast.makeText(
                    context,
                    "Please enter your Credentials",
                    Toast.LENGTH_SHORT
                ).show();
            }
            else {
                email = (view?.findViewById(R.id.etLogEmail) as EditText).text.toString()
                password = (view?.findViewById(R.id.etLogPass) as EditText).text.toString()

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
                    } else {
                        Toast.makeText(
                            context,
                            "Your Credentials are not valid",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }

        }

        var signUpTxtview = view.findViewById(R.id.txtsignup) as TextView
        signUpTxtview.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}