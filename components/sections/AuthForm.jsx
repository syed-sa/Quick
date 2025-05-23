"use client"

import { useState } from "react"

const AuthForm = () => {
  const [isLogin, setIsLogin] = useState(true)

  const handleSubmit = (e) => {
    e.preventDefault()
    // Handle form submit here (API call, validation, etc.)
  }

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center px-4">
      <div className="bg-white rounded-2xl shadow-lg w-full max-w-md p-8">
        {/* Tabs */}
        <div className="flex justify-center mb-6">
          <button
            className={`px-4 py-2 font-medium ${
              isLogin ? "text-red-500 border-b-2 border-red-500" : "text-gray-500"
            }`}
            onClick={() => setIsLogin(true)}
          >
            Login
          </button>
          <button
            className={`ml-6 px-4 py-2 font-medium ${
              !isLogin ? "text-red-500 border-b-2 border-red-500" : "text-gray-500"
            }`}
            onClick={() => setIsLogin(false)}
          >
            Sign Up
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          {!isLogin && (
            <div>
              <label className="text-sm font-medium text-gray-700">Name</label>
              <input
                type="text"
                className="w-full mt-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-red-400"
                placeholder="Your Name"
                required
              />
            </div>
          )}

          <div>
            <label className="text-sm font-medium text-gray-700">Email</label>
            <input
              type="email"
              className="w-full mt-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-red-400"
              placeholder="example@mail.com"
              required
            />
          </div>

          <div>
            <label className="text-sm font-medium text-gray-700">Password</label>
            <input
              type="password"
              className="w-full mt-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-red-400"
              placeholder="••••••••"
              required
            />
          </div>

          <button
            type="submit"
            className="w-full bg-red-500 text-white py-3 rounded-lg font-medium hover:bg-red-600 transition"
          >
            {isLogin ? "Login" : "Create Account"}
          </button>
        </form>

        {isLogin && (
          <p className="text-sm text-center text-gray-500 mt-4">
            Don't have an account?{" "}
            <button className="text-red-500 font-medium" onClick={() => setIsLogin(false)}>
              Sign Up
            </button>
          </p>
        )}

        {!isLogin && (
          <p className="text-sm text-center text-gray-500 mt-4">
            Already have an account?{" "}
            <button className="text-red-500 font-medium" onClick={() => setIsLogin(true)}>
              Login
            </button>
          </p>
        )}
      </div>
    </div>
  )
}

export default AuthForm
