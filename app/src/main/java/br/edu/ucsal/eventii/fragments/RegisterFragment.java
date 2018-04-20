package br.edu.ucsal.eventii.fragments;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import br.edu.ucsal.eventii.R;
import br.edu.ucsal.eventii.activity.EventiiActivity;

public class RegisterFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private ViewPager viewPager;

    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextTelefone;
    private EditText editTextNome;
    private Button btnRegister;

    Drawable drawable;

    @SuppressLint("ValidFragment")
    public RegisterFragment(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public RegisterFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView  = (ViewGroup) inflater.inflate(R.layout.fragment_register_slide,container,false);

        editTextEmail = rootView.findViewById(R.id.fragment_register_textEmail);
        editTextSenha = rootView.findViewById(R.id.fragment_register_textPassword);
        editTextNome = rootView.findViewById(R.id.fragment_register_textNome);
        editTextTelefone = rootView.findViewById(R.id.fragment_register_textTelefone);

        btnRegister = rootView.findViewById(R.id.fragment_register_btnRegister);


        editTextEmail.addTextChangedListener(this);

        btnRegister.setOnClickListener(this);

        return rootView;

    }

    public void register(){



        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        String telefone = editTextTelefone.getText().toString();
        String username = editTextNome.getText().toString();

        registrarUsuarioParse( email, senha, telefone, username);

    }

    private void registrarUsuarioParse(String email, String senha, String telefone, String username) {

        final ParseUser user = new ParseUser();

        //User
        user.setEmail(email);
        user.setPassword(senha);
        user.setUsername(username);
        user.put("telefone", telefone);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if(e == null){


                    Toast.makeText(getActivity(),"Registrado com sucesso!",Toast.LENGTH_SHORT).show();

                    try {
                        finalizarRegistro();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                }else{
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void finalizarRegistro() throws IOException {

        final Uri uri = resourceToUri(getContext(),R.drawable.user_padrao);

        Bitmap userImage = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);

        byte[] byteProfile = getBytesFromBitmap(userImage);

        ParseFile parseFile = new ParseFile("user.png", byteProfile);

        ParseObject parseObject = new ParseObject("Avatar");

        parseObject.put("idUser", ParseUser.getCurrentUser().getObjectId());
        parseObject.put("avatar", parseFile);

        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e == null){
                    Log.d("Avatar", "deu certo");
                    abrirTelaInicio();
                }else{
                    Log.d("Avatar", "Deu errado");
                }

            }
        });

    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private void abrirTelaInicio() {

        startActivity(new Intent(getActivity(), EventiiActivity.class));
        getActivity().finish();

    }

    //URI RESOLVER

    public static Uri resourceToUri(Context context, int resID) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID) );
    }

    //LISTENER CLICK

    @Override
    public void onClick(View v) {

        if(v == btnRegister){

            register();

        }

    }

    //TEXT IMPLEMENTS

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if(!isValidEmail(editTextEmail.getText().toString())){

            editTextEmail.setError("Email inválido");

        }

        if(!isValidPhone(editTextTelefone.getText().toString())){

            editTextTelefone.setError("Telefone inválido");

        }

    }

    //VALIDACOES

    private boolean isValidEmail(String s) {

        if(s == null){
            return false;
        }else{
            return Patterns.EMAIL_ADDRESS.matcher(s).matches();
        }

    }

    private boolean isValidPhone(String s){

        if(s == null){
            return false;
        }else{
            return Patterns.PHONE.matcher(s).matches();
        }

    }
}
