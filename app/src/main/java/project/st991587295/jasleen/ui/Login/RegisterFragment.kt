package project.st991587295.jasleen.ui.Login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import project.st991587295.jasleen.R
import project.st991587295.jasleen.databinding.FragmentRegisterBinding
import project.st991587295.jasleen.model.User
import java.util.HashMap
import java.util.Objects
import kotlin.collections.Map as Map1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var userID: String
    private lateinit var documentRef: DocumentReference
    private lateinit var fname: String
    private lateinit var lname: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        fStore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
        return inflater.inflate(
            project.st991587295.jasleen.R.layout.fragment_register,
            container,
            false
        )


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        var register = view.findViewById(project.st991587295.jasleen.R.id.btnRegister) as Button
        register.setOnClickListener {

            gatherInfo()
           validations()


        }
    }



    fun gatherInfo() {
        fname = (view?.findViewById(R.id.etRegFirstName) as EditText).text.toString()
        lname = (view?.findViewById(R.id.etRegLastName) as EditText).text.toString()
        email = (view?.findViewById(R.id.etRegEmail) as EditText).text.toString()
        password = (view?.findViewById(R.id.etRegPass) as EditText).text.toString()
        confirmPassword = (view?.findViewById(R.id.etRegConfirmPass) as EditText).text.toString()
    }


    fun validations(){
        if (fname.isNotEmpty() || lname.isNotEmpty() ||  email.isNotEmpty() || password.isNotEmpty() || confirmPassword.isNotEmpty()) {
            if (password == confirmPassword) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {

                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        storeUser()

                    } }
            }
            else {
                Toast.makeText(context, "Your Password doesn't match", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    fun storeUser(){
        val user = User(
            (view?.findViewById(R.id.etRegFirstName) as EditText).text.toString(),
            (view?.findViewById(R.id.etRegLastName) as EditText).text.toString(),
                (view?.findViewById(R.id.etRegEmail) as EditText).text.toString()
        )
        userID = firebaseAuth.currentUser?.uid.toString()
        documentRef = fStore.collection("user").document(userID)
        documentRef.set(user).addOnSuccessListener {
            Log.d(TAG, "onSuccess: user Profile is created for "+ userID)

        }


    }
}

