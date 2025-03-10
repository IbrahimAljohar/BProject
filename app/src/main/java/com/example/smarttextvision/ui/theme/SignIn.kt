package com.example.smarttextvision.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttextvision.data.AdminViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign in to your Account",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email TextField
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    focusedBorderColor = Color(0xFF007AFF),
                    unfocusedBorderColor = Color.Gray
                ),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )

            // Password TextField with Lock Icon and Visibility Toggle
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    focusedBorderColor = Color(0xFF007AFF),
                    unfocusedBorderColor = Color.Gray
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (passwordVisibility) "Toggle password visibility",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )

            Button(
                onClick = {
                    authViewModel.signIn(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
            ) {
                Text("Log In", fontSize = 20.sp, color = Color.White)
            }

            TextButton(
                onClick = { /* Handle forgot password */ },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("Forgot Your Password ?", color = Color(0xFF007AFF), fontSize = 14.sp)
            }

            TextButton(
                onClick = { navController.navigate("sign_up") }
            ) {
                Text("Don’t have an account? Sign Up", color = Color(0xFF007AFF), fontSize = 14.sp)
            }
        }
    }
}